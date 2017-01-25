package pl.pharmaway.rimantin_presentation.provider;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public interface DataSender {
    Cancelable sendData(UserData userData, DataSender.Callback callback);
    Cancelable sendData(NotSendUserData userData, DataSender.Callback callback);

    interface Callback {
        void onSuccess();
        void onError(NotSendUserData notSendUserData, Throwable throwable);
    }
}
