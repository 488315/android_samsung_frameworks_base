package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
public class DvbcFrontendCapabilities extends FrontendCapabilities {
    private final int mAnnexCap;
    private final long mFecCap;
    private final int mModulationCap;

    private DvbcFrontendCapabilities(int modulationCap, long fecCap, int annexCap) {
        this.mModulationCap = modulationCap;
        this.mFecCap = fecCap;
        this.mAnnexCap = annexCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    @Deprecated
    public int getFecCapability() {
        if (this.mFecCap > 2147483647L) {
            return 0;
        }
        return (int) this.mFecCap;
    }

    public long getCodeRateCapability() {
        return this.mFecCap;
    }

    public int getAnnexCapability() {
        return this.mAnnexCap;
    }
}
