package pl.pharmaway.rimantin_presentation.network;

import pl.pharmaway.rimantin_presentation.Constants;

public class ServerConfiguration {
    public String getBaseUrl() {
        return Constants.API2;
    }
    public String getAddResult() {
        return "addResult.php";
    }

    public String getAddResultEndPoint() {
        return getBaseUrl() + getAddResult();
    }
}
