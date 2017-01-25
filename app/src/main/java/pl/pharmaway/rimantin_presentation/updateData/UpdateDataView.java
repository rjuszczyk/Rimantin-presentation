package pl.pharmaway.rimantin_presentation.updateData;

public interface UpdateDataView {
    void showProgress();
    void hideProgress();
    void showSuccess();
    void showFailed(ErrorType errorType);

    enum ErrorType {
        NO_NETWORK,
        UNKNOWN_ERROR
    }
}
