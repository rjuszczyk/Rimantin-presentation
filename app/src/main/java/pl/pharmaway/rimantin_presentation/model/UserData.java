package pl.pharmaway.rimantin_presentation.model;

import java.io.Serializable;

public class UserData implements Serializable{
    private PharmacyDataRow row;
    private int participantNumber = 0;
    private long timeInApp = 0;

    public void setRow(PharmacyDataRow row) {
        this.row = row;
    }

    public PharmacyDataRow getRow() {
        return row;
    }

    public int getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(int participantNumber) {
        this.participantNumber = participantNumber;
    }

    public void setTimeSpendInApp(long time) {
        timeInApp = time;
    }

    public long getTimeSpendInApp() {
        return timeInApp;
    }
}
