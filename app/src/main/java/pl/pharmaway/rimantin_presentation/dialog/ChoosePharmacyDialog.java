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

public class ChoosePharmacyDialog extends ChooseElementDialog{

    private String nazwisko_przedstawiciela;
    private String imie_przedstawiciela;
    private String miasto;

    @Override
    public boolean isTwoLines() {
        return true;
    }

    @Override
    public String getRowText(PharmacyDataRow row) {
        return row.nazwa_apteki;
    }

    public static ChoosePharmacyDialog create(
            String imie_przedstawiciela,
            String nazwisko_przedstawiciela,
            String miasto) {
        ChoosePharmacyDialog dialog = new ChoosePharmacyDialog();
        Bundle arguments = new Bundle();
        arguments.putString("imie_przedstawiciela", imie_przedstawiciela);
        arguments.putString("nazwisko_przedstawiciela", nazwisko_przedstawiciela);
        arguments.putString("miasto", miasto);
        dialog.setArguments(arguments);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        imie_przedstawiciela = getArguments().getString("imie_przedstawiciela");
        nazwisko_przedstawiciela = getArguments().getString("nazwisko_przedstawiciela");
        miasto = getArguments().getString("miasto");

        super.onCreate(savedInstanceState);
    }

    @Override
    public List<PharmacyDataRow> getRows(Context context) {
        return DatabaseHelper.rowsForPrzedstawiciel(imie_przedstawiciela, nazwisko_przedstawiciela, miasto, context);
    }

    @Override
    public void onRowSelected(PharmacyDataRow row) {
        mPharmacyDialogListener.onPharmacySelected(row.nazwa_apteki, row);
    }

    PharmacyDialogListener mPharmacyDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PharmacyDialogListener) {
            mPharmacyDialogListener = (PharmacyDialogListener) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName() + " must implement "
                    + PharmacyDialogListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPharmacyDialogListener = null;
    }

    public interface PharmacyDialogListener {
        void onPharmacySelected(String pharmacyName, PharmacyDataRow row);
    }
}
