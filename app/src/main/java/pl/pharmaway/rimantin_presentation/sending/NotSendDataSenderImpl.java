package pl.pharmaway.rimantin_presentation.sending;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.network.ServerConfiguration;
import pl.pharmaway.rimantin_presentation.provider.DataSender;
import pl.pharmaway.rimantin_presentation.provider.NotSendDataSender;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public class NotSendDataSenderImpl implements NotSendDataSender {
    ServerConfiguration mServerConfiguration = new ServerConfiguration();
    StoreNotSendData mStoreNotSendData;

    public NotSendDataSenderImpl(StoreNotSendData mStoreNotSendData) {
        this.mStoreNotSendData = mStoreNotSendData;
    }

    @Override
    public Cancelable sendData(List<NotSendUserData> notSendUserDatas, DataSender.Callback callback) {
        NotSendDataSenderImpl.SendCancelable sendCancelable = new NotSendDataSenderImpl.SendCancelable(notSendUserDatas, callback);
        sendCancelable.send();
        return sendCancelable;
    }




    class SendCancelable implements Cancelable {

        boolean canceled = false;
        private DataSender.Callback mCallback;
        Handler mHandler;
        List<NotSendUserData> mNotSendUserDataList;


        SendCancelable(List<NotSendUserData> notSendUserData, DataSender.Callback callback) {
            mNotSendUserDataList = notSendUserData;
            mCallback = callback;
        }

        void send() {
            mHandler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendData();
                }
            }).start();
        }

        void onSuccess() {
            if(!canceled) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onSuccess();
                    }
                });

            }
        }

        void onFailed(final NotSendUserData notSendData) {
            if(!canceled) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(notSendData, new Throwable("error"));
                    }
                });
            }
        }

        @Override
        public void cancel() {
            canceled = true;
        }

        public void sendData() {
            for (NotSendUserData notSendUserData : mNotSendUserDataList) {
                String dataToSend = notSendUserData.getDataToSend();

                String resultData = writeDataToServer(dataToSend, mServerConfiguration.getAddResultEndPoint());

                Log.d("result", "r = " + resultData);

                if(!resultData.contains("OK")) {

                    onFailed(notSendUserData);
                    return;
                }

                deleteNotSendUserData(notSendUserData);
            }

            onSuccess();
        }

        private String writeDataToServer(String data, String addResultEndPoint) {
            BufferedReader reader = null;
            try {
                URL url = new URL(addResultEndPoint);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data.toString());
                wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "ERROR";
        }

        private void deleteNotSendUserData(NotSendUserData userDataToDelete) {
            mStoreNotSendData.deleteNotSendUserData(userDataToDelete);
        }

        private NotSendUserData storeNotSendUserData(String userDataToSend) {
            NotSendUserData notSendUserData = new NotSendUserData(userDataToSend);
            mStoreNotSendData.storeNotSendUserData(notSendUserData);
            return notSendUserData;
        }

        private String prepareUrlPair(String key, String value) {
            try {
                return URLEncoder.encode(key, "UTF-8")
                        + "=" + URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
