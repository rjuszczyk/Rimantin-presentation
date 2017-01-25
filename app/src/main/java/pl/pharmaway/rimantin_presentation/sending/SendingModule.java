package pl.pharmaway.rimantin_presentation.sending;

import dagger.Module;
import dagger.Provides;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.provider.DataSender;

@Module
public class SendingModule {

    private final UserData mUserData;
    private final SendingView mSendingView;

    public SendingModule(SendingView sendingView, UserData userData) {
        mSendingView = sendingView;
        mUserData = userData;
    }


    @Provides
    SendingPresenter provideSendingPresenter(DataSender dataSender) {
        return new SendingPresenter(dataSender, mSendingView, mUserData);
    }



}
