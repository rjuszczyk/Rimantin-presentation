package pl.pharmaway.rimantin_presentation.database;

import android.content.SharedPreferences;

import pl.pharmaway.rimantin_presentation.network.PharmacyDataApi;
import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataVersion;
import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;
import pl.pharmaway.rimantin_presentation.updateData.ShouldUpdate;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by Radek on 16.01.2017.
 */

public class ShouldUpdateImpl implements ShouldUpdate {
    PharmacyDataApi mPharmacyDataApi;
    SharedPreferences mSharedPreferences;
    public ShouldUpdateImpl(PharmacyDataApi pharmacyDataApi, SharedPreferences sharedPreferences) {
        mPharmacyDataApi = pharmacyDataApi;
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public Cancelable checkForUpdate(final Callback callback) {
        Call<PharmacyDataVersion> call = mPharmacyDataApi.getPharmacyDataVersion();
        RetrofitCancelable retrofitCancelable = new RetrofitCancelable(call);
        call.enqueue(new retrofit.Callback<PharmacyDataVersion>() {
            @Override
            public void onResponse(Response<PharmacyDataVersion> response) {
                int currentVersion = mSharedPreferences.getInt("version", 0);
                if(response.body().getVersion() > currentVersion) {
                    mSharedPreferences.edit().putInt("version_temp", response.body().getVersion()).commit();
                    callback.onResult(true);
                } else {
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResult(false);
            }
        });
        return retrofitCancelable;
    }

    class RetrofitCancelable implements Cancelable {
        public RetrofitCancelable(Call<PharmacyDataVersion> mCall) {
            this.mCall = mCall;
        }

        Call<PharmacyDataVersion> mCall;

        @Override
        public void cancel() {
            mCall.cancel();
        }
    }
}
