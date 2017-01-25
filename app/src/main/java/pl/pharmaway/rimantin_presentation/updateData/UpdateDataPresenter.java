package pl.pharmaway.rimantin_presentation.updateData;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataResponse;
import pl.pharmaway.rimantin_presentation.provider.DataProvider;
import pl.pharmaway.rimantin_presentation.provider.ErrorTypeProvider;
import pl.pharmaway.rimantin_presentation.provider.StoreData;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;
import pl.pharmaway.rimantin_presentation.updater.IsDataDownloaded;
import pl.pharmaway.rimantin_presentation.updater.PharmacyDataProvider;

public class UpdateDataPresenter {

    @NonNull
    private final UpdateDataView mUpdateDataView;
    @NonNull
    private final PharmacyDataProvider mPharmacyDataProvider;
    @NonNull
    private final StoreData mStoreData;
    @NonNull
    private final ErrorTypeProvider<UpdateDataView.ErrorType> mErrorTypeProvider;
    @NonNull
    private final IsDataDownloaded mIsDataDownloaded;

    @NonNull
    private final SharedPreferences mSharedPreferences;
    @Nullable
    private Cancelable mCancelable;


    public UpdateDataPresenter(
            @NonNull UpdateDataView updateDataView,
            @NonNull PharmacyDataProvider pharmacyDataProvider,
            @NonNull StoreData storeData,
            @NonNull ErrorTypeProvider<UpdateDataView.ErrorType> errorTypeProvider,
            @NonNull IsDataDownloaded isDataDownloaded,
            @NonNull SharedPreferences sharedPreferences) {
        mUpdateDataView = updateDataView;
        mPharmacyDataProvider = pharmacyDataProvider;
        mStoreData = storeData;
        mErrorTypeProvider = errorTypeProvider;
        mIsDataDownloaded = isDataDownloaded;
        mSharedPreferences = sharedPreferences;
    }

    public void start() {
        mUpdateDataView.showProgress();
        mCancelable = mPharmacyDataProvider.provideData(new DataProvider.Callback<PharmacyDataResponse>() {
            @Override
            public void onSuccess(PharmacyDataResponse result) {
                mCancelable = mStoreData.storeData(result, new StoreData.Callback() {
                    @Override
                    public void onSuccess() {
                        int vTemp =  mSharedPreferences.getInt("version_temp", 0);
                        mSharedPreferences.edit().putInt("version", vTemp).commit();

                        mIsDataDownloaded.setDataDownloaded();
                        mUpdateDataView.hideProgress();
                        mUpdateDataView.showSuccess();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        callOnFailed(throwable);
                    }
                });
            }

            void callOnFailed(Throwable throwable) {
                onFailed(throwable);
            }

            @Override
            public void onFailed(Throwable throwable) {
                mUpdateDataView.hideProgress();
                UpdateDataView.ErrorType errorType = mErrorTypeProvider.getErrorType(throwable);
                mUpdateDataView.showFailed(errorType);
            }
        });
    }

    public void stop() {
        if (mCancelable != null) {
            mCancelable.cancel();
        }
    }
}