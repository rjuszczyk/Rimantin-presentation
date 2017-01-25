package pl.pharmaway.rimantin_presentation.provider;

import java.util.List;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public interface NotSendDataSender {
    Cancelable sendData(List<NotSendUserData> notSendUserDatas, DataSender.Callback callback);

    interface Callback {
        void onSuccess();
        void onError(NotSendUserData notSendUserData, Throwable throwable);
    }
}
