package pl.pharmaway.rimantin_presentation.entry;

import dagger.Component;
import pl.pharmaway.rimantin_presentation.database.DatabaseModule;
import pl.pharmaway.rimantin_presentation.network.NetworkModule;

@Component(
        modules = {
                NetworkModule.class,
                DatabaseModule.class,
                EntryModule.class
        }
)
public interface EntryComponent {
    void inject(EntryActivity entryActivity);
}
