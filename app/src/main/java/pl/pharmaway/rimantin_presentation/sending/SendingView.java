package pl.pharmaway.rimantin_presentation.sending;

public interface SendingView {
    void showProgress(boolean isShown);

    void goToNext();

    void showRetry(boolean isShown);

    void showGoToNextButton(boolean isShown);
}
