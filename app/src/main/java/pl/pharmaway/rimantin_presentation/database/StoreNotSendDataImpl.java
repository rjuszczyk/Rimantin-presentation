package pl.pharmaway.rimantin_presentation.database;

import android.database.sqlite.SQLiteDatabase;
import java.util.List;

import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class StoreNotSendDataImpl implements StoreNotSendData {

    private final SQLiteDatabase mDatabase;

    public StoreNotSendDataImpl(SQLiteDatabase database) {
        mDatabase = database;
    }

    @Override
    public void storeNotSendUserData(NotSendUserData userData) {
        cupboard().withDatabase(mDatabase).put(userData);
    }

    @Override
    public void deleteNotSendUserData(NotSendUserData userData) {
       cupboard().withDatabase(mDatabase).delete(userData);
    }

    @Override
    public List<NotSendUserData> getNotSendUserData() {
        return cupboard().withDatabase(mDatabase).query(NotSendUserData.class).list();
    }
}
