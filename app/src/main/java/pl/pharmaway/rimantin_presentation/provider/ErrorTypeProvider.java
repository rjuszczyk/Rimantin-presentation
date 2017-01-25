package pl.pharmaway.rimantin_presentation.provider;

public interface ErrorTypeProvider<T> {
    T getErrorType(Throwable throwable);
}
