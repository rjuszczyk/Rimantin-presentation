package pl.pharmaway.rimantin_presentation.dialog;

import android.content.Context;

import java.util.List;

import pl.pharmaway.rimantin_presentation.database.DatabaseHelper;
import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;

/**
 * Created by Radek on 2017-01-14.
 */

public class ChooseAgentDialog extends ChooseElementDialog{

    @Override
    public String getRowText(PharmacyDataRow row) {
        return row.imie_przedstawiciela + " " + row.nazwisko_przedstawiciela;
    }

    @Override
    public List<PharmacyDataRow> getRows(Context context) {
        return DatabaseHelper.rowsForPrzedstawiciel(context);
    }

    public static ChooseAgentDialog create() {
        ChooseAgentDialog dialog = new ChooseAgentDialog();
        return dialog;
    }

    @Override
    public void onRowSelected(PharmacyDataRow row) {
        mAgentDialogListener.onAgentSelected(
                row.imie_przedstawiciela,
                row.nazwisko_przedstawiciela);
    }

    AgentDialogListener mAgentDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AgentDialogListener) {
            mAgentDialogListener = (AgentDialogListener) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName() + " must implement "
                    + AgentDialogListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAgentDialogListener = null;
    }

    public interface AgentDialogListener {
        void onAgentSelected(
                String imie_przedstawiciela,
                String nazwisko_przedstawiciela
        );
    }
}