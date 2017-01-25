package pl.pharmaway.rimantin_presentation.network;

import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataResponse;
import pl.pharmaway.rimantin_presentation.network.model.PharmacyDataVersion;
import retrofit.Call;
import retrofit.http.GET;

public interface PharmacyDataApi {
    @GET("baza_aptek1.php")
    Call<PharmacyDataResponse> getPharmacyData();

    @GET("version.php")
    Call<PharmacyDataVersion> getPharmacyDataVersion();
}
