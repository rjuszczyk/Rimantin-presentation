package pl.pharmaway.rimantin_presentation.sending;

import dagger.Component;
import pl.pharmaway.rimantin_presentation.database.DatabaseModule;
import pl.pharmaway.rimantin_presentation.network.NetworkModule;

@Component(
        modules = {
                SendingModule.class,
                DatabaseModule.class,
                NetworkModule.class

        }
)
public interface SendingComponent {
        void inject(SendActivity sendActivity);
}
