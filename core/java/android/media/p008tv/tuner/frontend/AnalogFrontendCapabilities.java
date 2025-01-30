package android.media.p008tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public class AnalogFrontendCapabilities extends FrontendCapabilities {
    private final int mSifStandardCap;
    private final int mTypeCap;

    private AnalogFrontendCapabilities(int typeCap, int sifStandardCap) {
        this.mTypeCap = typeCap;
        this.mSifStandardCap = sifStandardCap;
    }

    public int getSignalTypeCapability() {
        return this.mTypeCap;
    }

    public int getSifStandardCapability() {
        return this.mSifStandardCap;
    }
}
