package pl.pharmaway.rimantin_presentation.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;

public class PharmacyDataResponse {

    @SerializedName("response")
    List<PharmacyDataRow> mResponse;

    public List<PharmacyDataRow> getPharmacyDataList() {
        return mResponse;
    }
}
