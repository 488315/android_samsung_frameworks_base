package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DvbtFrontendCapabilities extends FrontendCapabilities {
    private final int mBandwidthCap;
    private final int mCodeRateCap;
    private final int mConstellationCap;
    private final int mGuardIntervalCap;
    private final int mHierarchyCap;
    private final boolean mIsMisoSupported;
    private final boolean mIsT2Supported;
    private final int mTransmissionModeCap;

    private DvbtFrontendCapabilities(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        this.mTransmissionModeCap = i;
        this.mBandwidthCap = i2;
        this.mConstellationCap = i3;
        this.mCodeRateCap = i4;
        this.mHierarchyCap = i5;
        this.mGuardIntervalCap = i6;
        this.mIsT2Supported = z;
        this.mIsMisoSupported = z2;
    }

    public int getTransmissionModeCapability() {
        return this.mTransmissionModeCap;
    }

    public int getBandwidthCapability() {
        return this.mBandwidthCap;
    }

    public int getConstellationCapability() {
        return this.mConstellationCap;
    }

    public int getCodeRateCapability() {
        return this.mCodeRateCap;
    }

    public int getHierarchyCapability() {
        return this.mHierarchyCap;
    }

    public int getGuardIntervalCapability() {
        return this.mGuardIntervalCap;
    }

    public boolean isT2Supported() {
        return this.mIsT2Supported;
    }

    public boolean isMisoSupported() {
        return this.mIsMisoSupported;
    }
}
