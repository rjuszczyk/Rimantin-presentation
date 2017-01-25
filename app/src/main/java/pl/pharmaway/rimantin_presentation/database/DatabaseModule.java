package pl.pharmaway.rimantin_presentation.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import pl.pharmaway.rimantin_presentation.provider.StoreData;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.updater.IsDataDownloaded;

@Module
public class DatabaseModule {

    Context mContext;

    public DatabaseModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }

    @Provides
    public SQLiteDatabase provideSQLiteDatabase(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getWritableDatabase();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("Multilac-1-min.prefs", Context.MODE_PRIVATE);
    }

    @Provides
    public IsDataDownloaded provideIsDataDownloaded(SharedPreferences sharedPreferences) {
        return new IsDataDownloadedImpl(sharedPreferences);
    }

    @Provides
    StoreData provideStoreData(SQLiteDatabase database) {
        return new StoreDataImpl(database);
    }

    @Provides
    StoreNotSendData provideStoreNotSendData(SQLiteDatabase database) {
        return new StoreNotSendDataImpl(database);
    }
}