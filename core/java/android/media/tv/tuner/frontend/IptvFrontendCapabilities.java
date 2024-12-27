package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
public class IptvFrontendCapabilities extends FrontendCapabilities {
    private final int mProtocolCap;

    private IptvFrontendCapabilities(int protocolCap) {
        this.mProtocolCap = protocolCap;
    }

    public int getProtocolCapability() {
        return this.mProtocolCap;
    }
}
