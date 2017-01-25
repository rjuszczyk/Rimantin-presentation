package pl.pharmaway.rimantin_presentation.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;
import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataResponse;
import pl.pharmaway.rimantin_presentation.provider.StoreData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class StoreDataImpl implements StoreData {

    private final SQLiteDatabase mDatabase;

    public StoreDataImpl(SQLiteDatabase database) {
        mDatabase = database;
    }

    @Override
    public Cancelable storeData(PharmacyDataResponse pharmacyData, Callback callback) {
        ProviderCancelable providerCancelable = new ProviderCancelable(mDatabase);
        providerCancelable.start(pharmacyData, callback);
        return providerCancelable;
    }

    class ProviderCancelable implements Cancelable {
        SQLiteDatabase mDatabase;
        boolean canceled = false;

        ProviderCancelable(SQLiteDatabase database) {
            mDatabase = database;
        }

        void start(PharmacyDataResponse pharmacyData, Callback callback) {
            mDatabase.beginTransaction();
            List<PharmacyDataRow> pharmacyDataList = pharmacyData.getPharmacyDataList();
            cupboard().withDatabase(mDatabase).delete(PharmacyDataRow.class, "1");
            cupboard().withDatabase(mDatabase).put(pharmacyDataList);
            if (!canceled) {
                mDatabase.setTransactionSuccessful();
            }
            mDatabase.endTransaction();
            if (!canceled) {
                callback.onSuccess();
            }
        }

        @Override
        public void cancel() {
            canceled = true;
        }
    }
}
