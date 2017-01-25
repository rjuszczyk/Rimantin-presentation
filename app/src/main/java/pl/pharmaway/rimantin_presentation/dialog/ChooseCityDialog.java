package pl.pharmaway.rimantin_presentation.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import pl.pharmaway.rimantin_presentation.database.DatabaseHelper;
import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;

/**
 * Created by Radek on 2017-01-14.
 */

public class ChooseCityDialog extends ChooseElementDialog{

    private String nazwisko_przedstawiciela;
    private String imie_przedstawiciela;

    @Override
    public String getRowText(PharmacyDataRow row) {
        return row.miasto;
    }

    public static ChooseCityDialog create(
            String imie_przedstawiciela,
            String nazwisko_przedstawiciela) {
        ChooseCityDialog dialog = new ChooseCityDialog();
        Bundle arguments = new Bundle();
        arguments.putString("imie_przedstawiciela", imie_przedstawiciela);
        arguments.putString("nazwisko_przedstawiciela", nazwisko_przedstawiciela);
        dialog.setArguments(arguments);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        imie_przedstawiciela = getArguments().getString("imie_przedstawiciela");
        nazwisko_przedstawiciela = getArguments().getString("nazwisko_przedstawiciela");

        super.onCreate(savedInstanceState);
    }

    @Override
    public List<PharmacyDataRow> getRows(Context context) {
        return DatabaseHelper.rowsForPrzedstawiciel(imie_przedstawiciela, nazwisko_przedstawiciela, context);
    }

    @Override
    public void onRowSelected(PharmacyDataRow row) {
        mCityDialogListener.onCitySelected(row.miasto);
    }

    CityDialogListener mCityDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CityDialogListener) {
            mCityDialogListener = (CityDialogListener) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName() + " must implement "
                    + CityDialogListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCityDialogListener = null;
    }

    public interface CityDialogListener {
        void onCitySelected(String city);
    }
}
