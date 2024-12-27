package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean addSfSkippedFramesToTrace() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean adpfGpuSf() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean allowNVsyncsInTargeter() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean cacheWhenSourceCropLayerOnlyMoved() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean ceFencePromise() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean commitNotComposited() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean connectedDisplay() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean deprecateVsyncSf() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean detachedMirror() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean displayProtected() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableFroDependentFeatures() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableLayerCommandBatching() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableSmallAreaDetection() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean flushBufferSlotsToUncache() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean forceCompileGraphiteRenderengine() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean fp16ClientTarget() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean frameRateCategoryMrr() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean graphiteRenderengine() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpLevelHal() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hotplug2() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean latchUnsignaledWithAutoRefreshChanged() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean localTonemapScreenshots() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean misc1() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean multithreadedPresent() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean overrideTrustedOverlay() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean protectedIfClient() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean refreshRateOverlayOnExternalDisplay() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean renderableBufferUsage() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean restoreBlurStep() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean screenshotFencePreservation() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean singleHopScreenshot() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean useKnownRefreshRateForFpsConsistency() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrBugfix24q4() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrBugfixDroppedFrame() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrConfig() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vulkanRenderengine() {
        return false;
    }
}
