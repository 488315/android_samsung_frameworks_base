package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class IptvFrontendCapabilities extends FrontendCapabilities {
    private final int mProtocolCap;

    private IptvFrontendCapabilities(int i) {
        this.mProtocolCap = i;
    }

    public int getProtocolCapability() {
        return this.mProtocolCap;
    }
}
