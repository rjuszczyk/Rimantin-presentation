package pl.pharmaway.rimantin_presentation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;
import pl.pharmaway.rimantin_presentation.model.NotSendUserData;
import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static final int VERSION = 1;

    static {
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());

        cupboard().register(PharmacyDataRow.class);
        cupboard().register(NotSendUserData.class);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard()
                .withDatabase(sqLiteDatabase)
                .createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        cupboard()
                .withDatabase(sqLiteDatabase)
                .upgradeTables();
    }

    public static List<PharmacyDataRow> rowsForPrzedstawiciel(
            String imie,
            String nazwisko,
            Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard()
                .withDatabase(db)
                .query(PharmacyDataRow.class)
                .withSelection(
                        "imie_przedstawiciela LIKE ? and " +
                        "nazwisko_przedstawiciela LIKE ?",
                        new String[]{imie, nazwisko}
                )
                .groupBy("miasto")
                .list();
    }

    public static List<PharmacyDataRow> rowsForPrzedstawiciel(
            String imie,
            String nazwisko,
            String miasto,
            Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard()
                .withDatabase(db)
                .query(PharmacyDataRow.class)
                .withSelection(
                        "imie_przedstawiciela LIKE ? and " +
                        "nazwisko_przedstawiciela LIKE ? and " +
                        "miasto LIKE ?",
                        new String[]{imie, nazwisko, miasto})
                .list();
    }

    public static List<PharmacyDataRow> rowsForPrzedstawiciel(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard()
                .withDatabase(db)
                .query(PharmacyDataRow.class)
                .groupBy("imie_przedstawiciela, nazwisko_przedstawiciela")
                .list();
    }
}