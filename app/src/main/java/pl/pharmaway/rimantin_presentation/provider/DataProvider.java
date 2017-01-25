package pl.pharmaway.rimantin_presentation.provider;

import pl.pharmaway.rimantin_presentation.provider.cancelable.Cancelable;

public interface DataProvider<T> {
    Cancelable provideData(Callback<T> callback);

    interface Callback<T> {
        void onSuccess(T result);
        void onFailed(Throwable throwable);
    }
}
