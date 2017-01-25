package pl.pharmaway.rimantin_presentation.provider;

import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataResponse;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public interface StoreData {
    Cancelable storeData(PharmacyDataResponse pharmacyData, Callback callback);

    interface Callback {
        void onSuccess();
        void onFailed(Throwable throwable);
    }
}
