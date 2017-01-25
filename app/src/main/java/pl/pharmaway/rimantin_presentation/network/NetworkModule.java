package pl.pharmaway.rimantin_presentation.network;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import pl.pharmaway.rimantin_presentation.Constants;
import pl.pharmaway.rimantin_presentation.database.ShouldUpdateImpl;
import pl.pharmaway.rimantin_presentation.error.ErrorTypeProviderImpl;
import pl.pharmaway.rimantin_presentation.provider.DataSender;
import pl.pharmaway.rimantin_presentation.provider.ErrorTypeProvider;
import pl.pharmaway.rimantin_presentation.provider.NotSendDataSender;
import pl.pharmaway.rimantin_presentation.provider.PharmacyDataProviderImpl;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.sending.DataSenderImpl;
import pl.pharmaway.rimantin_presentation.sending.NotSendDataSenderImpl;
import pl.pharmaway.rimantin_presentation.updateData.ShouldUpdate;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataView;
import pl.pharmaway.rimantin_presentation.updater.PharmacyDataProvider;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module
public class NetworkModule {

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    PharmacyDataApi providePharmacyDataApi(Retrofit retrofit) {
        return retrofit.create(PharmacyDataApi.class);
    }

    @Provides
    PharmacyDataProvider providePharmacyDataProvider(PharmacyDataApi pharmacyDataApi) {
        return new PharmacyDataProviderImpl(pharmacyDataApi);
    }

    @Provides
    ErrorTypeProvider<UpdateDataView.ErrorType> provideErrorTypeProvider() {
        return new ErrorTypeProviderImpl();
    }

    @Provides
    DataSender provideDataSender(StoreNotSendData storeNotSendData) {
        return new DataSenderImpl(storeNotSendData);
    }

    @Provides
    NotSendDataSender provideNotSendDataSender(StoreNotSendData storeNotSendData) {
        return new NotSendDataSenderImpl(storeNotSendData);
    }

    @Provides
    ShouldUpdate provideShouldUpdate(PharmacyDataApi pharmacyDataApi, SharedPreferences sharedPreferences) {
        return new ShouldUpdateImpl(pharmacyDataApi, sharedPreferences);
    }
}
