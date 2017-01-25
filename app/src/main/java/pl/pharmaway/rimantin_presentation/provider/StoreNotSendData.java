package pl.pharmaway.rimantin_presentation.provider;

import java.util.List;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;

public interface StoreNotSendData {
    void storeNotSendUserData(NotSendUserData userData);
    void deleteNotSendUserData(NotSendUserData userData);
    List<NotSendUserData > getNotSendUserData();
}
