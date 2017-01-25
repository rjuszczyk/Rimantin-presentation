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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.network.ServerConfiguration;
import pl.pharmaway.rimantin_presentation.provider.DataSender;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public class DataSenderImpl implements DataSender {

    ServerConfiguration mServerConfiguration = new ServerConfiguration();
    StoreNotSendData mStoreNotSendData;

    public DataSenderImpl(StoreNotSendData storeNotSendData) {
        mStoreNotSendData = storeNotSendData;
    }

    @Override
    public Cancelable sendData(UserData userData, Callback callback) {
        SendCancelable sendCancelable = new SendCancelable(userData, callback, mStoreNotSendData);
        sendCancelable.send();
        return sendCancelable;
    }

    @Override
    public Cancelable sendData(NotSendUserData notSendUserData, Callback callback) {
        SendCancelable sendCancelable = new SendCancelable(notSendUserData, callback, mStoreNotSendData);
        sendCancelable.send();
        return sendCancelable;
    }

    class SendCancelable implements Cancelable {

        boolean canceled = false;
        private DataSender.Callback mCallback;
        UserData mUserData;
        Handler mHandler;
        StoreNotSendData mStoreNotSendData;
        NotSendUserData mNotSendUserData;

        SendCancelable(UserData userData, DataSender.Callback callback, StoreNotSendData storeNotSendData) {
            mUserData = userData;
            mCallback = callback;
            mStoreNotSendData = storeNotSendData;
        }

        SendCancelable(NotSendUserData notSendUserData, DataSender.Callback callback, StoreNotSendData storeNotSendData) {
            mNotSendUserData = notSendUserData;
            mCallback = callback;
            mStoreNotSendData = storeNotSendData;
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
            String dataToSend;
            if(mUserData != null) {
                dataToSend = createDataToSend(mUserData);
            } else if(mNotSendUserData != null){
                dataToSend = mNotSendUserData.getDataToSend();
            } else {
                throw new RuntimeException("mUSerData == null && mNotSendUserDataList == null");
            }

            String resultData = writeDataToServer(dataToSend, mServerConfiguration.getAddResultEndPoint());

            Log.d("result", "r = " + resultData);

            if(!resultData.contains("OK")) {
                if(mUserData != null) {
                    NotSendUserData notSendData = storeNotSendUserData(dataToSend);
                    onFailed(notSendData);
                } else {
                    onFailed(mNotSendUserData);
                }
                return;
            }

            if(mNotSendUserData != null) {
                deleteNotSendUserData(mNotSendUserData);
            }

            onSuccess();
        }

        private String createDataToSend(UserData userData) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            String data_utworzenia = sdf.format(new Date()).toString();

            StringBuilder data = new StringBuilder();
            data.append(prepareUrlPair("agent", userData.getRow().getImie_przedstawiciela() + " " + userData.getRow().getNazwisko_przedstawiciela()));
            data.append("&");
            data.append(prepareUrlPair("city", userData.getRow().getMiasto() ));
            data.append("&");
            data.append(prepareUrlPair("pharmacy", userData.getRow().getNazwa_apteki()));
            data.append("&");
            data.append(prepareUrlPair("participantNumber", ""+userData.getParticipantNumber() ));
            data.append("&");
            data.append(prepareUrlPair("timeSpendInApp", ""+userData.getTimeSpendInApp() ));
            data.append("&");
            data.append(prepareUrlPair("createDate", data_utworzenia ));
            return data.toString();
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
