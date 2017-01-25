package pl.pharmaway.rimantin_presentation.entry;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import java.util.List;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.provider.DataSender;
import pl.pharmaway.rimantin_presentation.provider.NotSendDataSender;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;
import pl.pharmaway.rimantin_presentation.updateData.ShouldUpdate;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataPresenter;
import pl.pharmaway.rimantin_presentation.updater.IsDataDownloaded;

public class EntryPresenter {
    @NonNull
    private final IsDataDownloaded mIsDataDownloaded;
    @NonNull
    private final EntryView mEntryView;
    @NonNull
    private final UpdateDataPresenter mUpdateDataPresenter;
    @NonNull
    private final NotSendDataSender mNotSendDataSender;
    @NonNull
    private final StoreNotSendData mStoreNotSendData;
    @NonNull
    private final ShouldUpdate mShouldUpdate;

    public EntryPresenter(
            @NonNull IsDataDownloaded isDataDownloaded,
            @NonNull EntryView entryView,
            @NonNull UpdateDataPresenter updateDataPresenter,
            @NonNull NotSendDataSender notSendDataSender,
            @NonNull StoreNotSendData storeNotSendData,
            @NonNull ShouldUpdate shouldUpdate
    ) {
        mIsDataDownloaded = isDataDownloaded;
        mEntryView = entryView;
        mUpdateDataPresenter = updateDataPresenter;
        mNotSendDataSender = notSendDataSender;
        mStoreNotSendData = storeNotSendData;
        mShouldUpdate = shouldUpdate;
    }

    Cancelable mSenderCancelable;
    public void start() {
        boolean isDataDownloaded = mIsDataDownloaded.isDataDownloaded();



        if(isDataDownloaded) {

            mSenderCancelable = mShouldUpdate.checkForUpdate(new ShouldUpdate.Callback() {
                @Override
                public void onResult(boolean should) {
                    if(should) {
                        mEntryView.showDownloadingData();
                        mUpdateDataPresenter.start();
                    } else {
                        List<NotSendUserData> notSendData = mStoreNotSendData.getNotSendUserData();
                        if(notSendData.size()>0) {
                            mEntryView.showSendingData();
                            Answers.getInstance().logContentView(new ContentViewEvent()
                                    .putContentName("auto retry send data")
                                    .putContentType("Action")
                                    .putContentId("6"));
                            mSenderCancelable = mNotSendDataSender.sendData(notSendData, new DataSender.Callback() {
                                @Override
                                public void onSuccess() {
                                    mEntryView.goToNextScreen();
                                }

                                @Override
                                public void onError(NotSendUserData notSendUserData, Throwable throwable) {
                                    mEntryView.goToNextScreen();
                                }
                            });
                        } else {
                            mEntryView.goToNextScreen();
                        }
                    }
                }
            });


        } else {
            mEntryView.showDownloadingData();
            mUpdateDataPresenter.start();
        }
    }

    public void stop() {
        mUpdateDataPresenter.stop();
    }
}
