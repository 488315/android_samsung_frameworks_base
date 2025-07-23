package com.android.media.projection.flags;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean appContentSharing() {
        return false;
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean mediaProjectionConnectedDisplay() {
        return true;
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean mediaProjectionConnectedDisplayNoVirtualDevice() {
        return true;
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean showStopDialogPostCallEnd() {
        return true;
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean stopMediaProjectionOnCallEnd() {
        return true;
    }
}
