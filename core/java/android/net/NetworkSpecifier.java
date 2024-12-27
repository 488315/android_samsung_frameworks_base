package android.net;

import android.annotation.SystemApi;

public abstract class NetworkSpecifier {
    @SystemApi
    public boolean canBeSatisfiedBy(NetworkSpecifier other) {
        return false;
    }

    @SystemApi
    public NetworkSpecifier redact() {
        return this;
    }
}
