package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class Atsc3FrontendCapabilities extends FrontendCapabilities {
    private final int mBandwidthCap;
    private final int mCodeRateCap;
    private final int mDemodOutputFormatCap;
    private final int mFecCap;
    private final int mModulationCap;
    private final int mTimeInterleaveModeCap;

    private Atsc3FrontendCapabilities(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mBandwidthCap = i;
        this.mModulationCap = i2;
        this.mTimeInterleaveModeCap = i3;
        this.mCodeRateCap = i4;
        this.mFecCap = i5;
        this.mDemodOutputFormatCap = i6;
    }

    public int getBandwidthCapability() {
        return this.mBandwidthCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public int getTimeInterleaveModeCapability() {
        return this.mTimeInterleaveModeCap;
    }

    public int getPlpCodeRateCapability() {
        return this.mCodeRateCap;
    }

    public int getFecCapability() {
        return this.mFecCap;
    }

    public int getDemodOutputFormatCapability() {
        return this.mDemodOutputFormatCap;
    }
}
