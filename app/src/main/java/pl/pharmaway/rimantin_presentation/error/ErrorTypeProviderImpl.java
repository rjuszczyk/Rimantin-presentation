package pl.pharmaway.rimantin_presentation.error;

import pl.pharmaway.rimantin_presentation.provider.ErrorTypeProvider;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataView;

public class ErrorTypeProviderImpl implements ErrorTypeProvider<UpdateDataView.ErrorType>{
    @Override
    public UpdateDataView.ErrorType getErrorType(Throwable throwable) {
        return UpdateDataView.ErrorType.UNKNOWN_ERROR;
    }
}
