package pl.pharmaway.rimantin_presentation.provider;

import pl.pharmaway.rimantin_presentation.network.PharmacyDataApi;
import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataResponse;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;
import pl.pharmaway.rimantin_presentation.updater.PharmacyDataProvider;
import retrofit.Call;
import retrofit.Response;

public class PharmacyDataProviderImpl implements PharmacyDataProvider{
    private PharmacyDataApi mPharmacyDataApi;

    public PharmacyDataProviderImpl(PharmacyDataApi pharmacyDataApi) {
        mPharmacyDataApi = pharmacyDataApi;
    }

    @Override
    public Cancelable provideData(Callback<PharmacyDataResponse> callback) {
        ProviderCancelable providerCancelable = new ProviderCancelable(mPharmacyDataApi, callback);
        providerCancelable.start();
        return providerCancelable;
    }

    private class ProviderCancelable implements Cancelable {

        Callback<PharmacyDataResponse> mCallback;
        PharmacyDataApi mPharmacyDataApi;
        private Call<PharmacyDataResponse> mCall;

        ProviderCancelable(PharmacyDataApi pharmacyDataApi,
                           Callback<PharmacyDataResponse> callback) {
            mPharmacyDataApi = pharmacyDataApi;
            mCallback = callback;
        }

        void start() {
            mCall = mPharmacyDataApi.getPharmacyData();
            mCall.enqueue(new retrofit.Callback<PharmacyDataResponse>() {
                @Override
                public void onResponse(Response<PharmacyDataResponse> response) {
                    mCallback.onSuccess(response.body());
                }

                @Override
                public void onFailure(Throwable t) {
                    mCallback.onFailed(t);
                }
            });
        }

        @Override
        public void cancel() {
            mCall.cancel();
        }
    }
}