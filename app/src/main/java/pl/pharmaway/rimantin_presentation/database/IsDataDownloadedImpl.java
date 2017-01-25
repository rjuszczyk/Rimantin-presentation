package pl.pharmaway.rimantin_presentation.database;

import android.content.SharedPreferences;

import pl.pharmaway.rimantin_presentation.updater.IsDataDownloaded;

public class IsDataDownloadedImpl implements IsDataDownloaded {

    public static final String IS_DATA_DOWNLOADED = "IS_DATA_DOWNLOADED";
    private final SharedPreferences mSharedPreferences;

    public IsDataDownloadedImpl(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isDataDownloaded() {
        return mSharedPreferences.getBoolean(IS_DATA_DOWNLOADED, false);
    }

    @Override
    public void setDataDownloaded() {
        mSharedPreferences
                .edit()
                .putBoolean(IS_DATA_DOWNLOADED, true)
                .commit();
    }
}
