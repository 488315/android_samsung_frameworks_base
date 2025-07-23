package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DvbcFrontendCapabilities extends FrontendCapabilities {
    private final int mAnnexCap;
    private final long mFecCap;
    private final int mModulationCap;

    private DvbcFrontendCapabilities(int i, long j, int i2) {
        this.mModulationCap = i;
        this.mFecCap = j;
        this.mAnnexCap = i2;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    @Deprecated
    public int getFecCapability() {
        long j = this.mFecCap;
        if (j > 2147483647L) {
            return 0;
        }
        return (int) j;
    }

    public long getCodeRateCapability() {
        return this.mFecCap;
    }

    public int getAnnexCapability() {
        return this.mAnnexCap;
    }
}
