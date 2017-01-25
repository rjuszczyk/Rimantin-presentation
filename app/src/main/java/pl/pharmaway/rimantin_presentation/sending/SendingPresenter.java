package pl.pharmaway.rimantin_presentation.sending;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.provider.DataSender;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public class SendingPresenter {

    @NonNull
    private final DataSender mDataSender;
    @NonNull
    private final SendingView mSendingView;
    @NonNull
    private final UserData mUserData;
    @Nullable
    private Cancelable mCancelable;
    private NotSendUserData mNotSendUserData;

    public SendingPresenter(DataSender dataSender, SendingView sendingView, UserData userData) {
        mDataSender = dataSender;
        mSendingView = sendingView;
        mUserData = userData;
    }

    public void start() {
        mSendingView.showProgress(true);

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("[sending] send data")
                .putContentType("Action")
                .putContentId("3"));

        mCancelable = mDataSender.sendData(mUserData, new DataSender.Callback() {
            @Override
            public void onSuccess() {
                mSendingView.showProgress(false);
                mSendingView.goToNext();
            }

            @Override
            public void onError(NotSendUserData notSendUserData, Throwable throwable) {
                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName("[sending] send data failed")
                        .putContentType("Result")
                        .putContentId("4"));

                mNotSendUserData = notSendUserData;
                mSendingView.showProgress(false);
                mSendingView.showRetry(true);
                mSendingView.showGoToNextButton(true);
            }
        });
    }

    public void retry() {
        mSendingView.showRetry(false);
        mSendingView.showGoToNextButton(false);
        mSendingView.showProgress(true);
        mCancelable = mDataSender.sendData(mNotSendUserData, new DataSender.Callback() {
            @Override
            public void onSuccess() {
                mSendingView.showProgress(false);
                mSendingView.goToNext();
            }

            @Override
            public void onError(NotSendUserData notSendUserData, Throwable throwable) {
                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName("[sending] retry send data failed")
                        .putContentType("Result")
                        .putContentId("9"));
                mSendingView.showProgress(false);
                mSendingView.showRetry(true);
                mSendingView.showGoToNextButton(true);
            }
        });
    }

    public void stop() {
        if(mCancelable != null) {
            mCancelable.cancel();
        }
    }
}
