package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
public class AtscFrontendCapabilities extends FrontendCapabilities {
    private final int mModulationCap;

    private AtscFrontendCapabilities(int modulationCap) {
        this.mModulationCap = modulationCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }
}
