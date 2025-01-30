package android.media.p008tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public class IptvFrontendCapabilities extends FrontendCapabilities {
    private final int mProtocolCap;

    private IptvFrontendCapabilities(int protocolCap) {
        this.mProtocolCap = protocolCap;
    }

    public int getProtocolCapability() {
        return this.mProtocolCap;
    }
}
