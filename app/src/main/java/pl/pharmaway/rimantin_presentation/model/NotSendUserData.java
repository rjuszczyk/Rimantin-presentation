package pl.pharmaway.rimantin_presentation.model;

import java.io.Serializable;

public class NotSendUserData implements Serializable{
    public Long _id;
    public String data;

    public NotSendUserData() {
    }

    public NotSendUserData(String userDataToSend) {
        data = userDataToSend;
    }

    public String getDataToSend() {
        return data;
    }
}
