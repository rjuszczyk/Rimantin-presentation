package pl.pharmaway.rimantin_presentation.entry;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import pl.pharmaway.rimantin_presentation.provider.ErrorTypeProvider;
import pl.pharmaway.rimantin_presentation.provider.NotSendDataSender;
import pl.pharmaway.rimantin_presentation.provider.StoreData;
import pl.pharmaway.rimantin_presentation.provider.StoreNotSendData;
import pl.pharmaway.rimantin_presentation.updateData.ShouldUpdate;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataPresenter;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataView;
import pl.pharmaway.rimantin_presentation.updater.IsDataDownloaded;
import pl.pharmaway.rimantin_presentation.updater.PharmacyDataProvider;

@Module
public class EntryModule {
    EntryView mEntryView;
    UpdateDataView mUpdateDataView;

    public EntryModule(EntryView entryView, UpdateDataView updateDataView) {
        mEntryView = entryView;
        mUpdateDataView = updateDataView;
    }

    @Provides
    EntryPresenter provideEntryPresenter(IsDataDownloaded isDataDownloaded,
                                         PharmacyDataProvider pharmacyDataProvider,
                                         StoreData storeData,
                                         ErrorTypeProvider<UpdateDataView.ErrorType> errorTypeProvider,
                                         NotSendDataSender notSendDataSender,
                                         StoreNotSendData storeNotSendData,
                                         ShouldUpdate shouldUpdate,
                                         SharedPreferences sharedPreferences
    ) {
        return new EntryPresenter(
                isDataDownloaded,
                mEntryView,
                new UpdateDataPresenter(mUpdateDataView, pharmacyDataProvider, storeData, errorTypeProvider, isDataDownloaded, sharedPreferences),
                notSendDataSender,
                storeNotSendData,
                shouldUpdate
        );
    }


}
