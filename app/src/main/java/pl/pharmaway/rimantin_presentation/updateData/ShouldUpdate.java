package pl.pharmaway.rimantin_presentation.updateData;


import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public interface ShouldUpdate {
    Cancelable checkForUpdate(Callback callback);
    interface Callback {
        void onResult(boolean should);
    }
}
