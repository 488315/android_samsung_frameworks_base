package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class IsdbtFrontendCapabilities extends FrontendCapabilities {
    private final int mBandwidthCap;
    private final int mCodeRateCap;
    private final int mGuardIntervalCap;
    private final boolean mIsFullSegmentSupported;
    private final boolean mIsSegmentAutoSupported;
    private final int mModeCap;
    private final int mModulationCap;
    private final int mTimeInterleaveCap;

    private IsdbtFrontendCapabilities(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        this.mModeCap = i;
        this.mBandwidthCap = i2;
        this.mModulationCap = i3;
        this.mCodeRateCap = i4;
        this.mGuardIntervalCap = i5;
        this.mTimeInterleaveCap = i6;
        this.mIsSegmentAutoSupported = z;
        this.mIsFullSegmentSupported = z2;
    }

    public int getModeCapability() {
        return this.mModeCap;
    }

    public int getBandwidthCapability() {
        return this.mBandwidthCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public int getCodeRateCapability() {
        return this.mCodeRateCap;
    }

    public int getGuardIntervalCapability() {
        return this.mGuardIntervalCap;
    }

    public int getTimeInterleaveModeCapability() {
        return this.mTimeInterleaveCap;
    }

    public boolean isSegmentAutoSupported() {
        return this.mIsSegmentAutoSupported;
    }

    public boolean isFullSegmentSupported() {
        return this.mIsFullSegmentSupported;
    }
}
