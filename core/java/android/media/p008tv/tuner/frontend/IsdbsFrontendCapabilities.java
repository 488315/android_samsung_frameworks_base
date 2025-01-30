package android.media.p008tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public class IsdbsFrontendCapabilities extends FrontendCapabilities {
    private final int mCodeRateCap;
    private final int mModulationCap;

    private IsdbsFrontendCapabilities(int modulationCap, int codeRateCap) {
        this.mModulationCap = modulationCap;
        this.mCodeRateCap = codeRateCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public int getCodeRateCapability() {
        return this.mCodeRateCap;
    }
}
