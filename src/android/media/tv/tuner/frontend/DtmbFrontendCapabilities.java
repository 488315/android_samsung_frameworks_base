package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class DtmbFrontendCapabilities extends FrontendCapabilities {
    private final int mBandwidthCap;
    private final int mCodeRateCap;
    private final int mGuardIntervalCap;
    private final int mModulationCap;
    private final int mTimeInterleaveModeCap;
    private final int mTransmissionModeCap;

    private DtmbFrontendCapabilities(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mModulationCap = i;
        this.mTransmissionModeCap = i2;
        this.mGuardIntervalCap = i3;
        this.mTimeInterleaveModeCap = i4;
        this.mCodeRateCap = i5;
        this.mBandwidthCap = i6;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public int getTransmissionModeCapability() {
        return this.mTransmissionModeCap;
    }

    public int getGuardIntervalCapability() {
        return this.mGuardIntervalCap;
    }

    public int getTimeInterleaveModeCapability() {
        return this.mTimeInterleaveModeCap;
    }

    public int getCodeRateCapability() {
        return this.mCodeRateCap;
    }

    public int getBandwidthCapability() {
        return this.mBandwidthCap;
    }
}
