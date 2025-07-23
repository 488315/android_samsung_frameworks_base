package com.android.graphics.libgui.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean allocateBufferPriority() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean applyPictureProfiles() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqAlwaysUseMaxDequeuedBufferCount() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqConsumerAttachCallback() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqExtendedallocate() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqGlFenceCleanup() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqProducerThrottlesOnlyAsyncMode() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bqSetframerate() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean bufferReleaseChannel() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean frametimestampsPreviousrelease() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean traceFrameRateOverride() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbCamera3AndProcessors() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbConsumerBaseOwnsBq() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbLibcameraservice() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbMediaMigration() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbPlatformApiImprovements() {
        return true;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbRingBuffer() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbStreamSplitter() {
        return false;
    }

    @Override // com.android.graphics.libgui.flags.FeatureFlags
    public boolean wbUnlimitedSlots() {
        return true;
    }
}
