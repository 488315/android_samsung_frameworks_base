package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean addSfSkippedFramesToTrace() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean adpfFmqSf() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean adpfGpuSf() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean adpfNativeSessionManager() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean allowNVsyncsInTargeter() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean arrSetframerateApi() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean arrSetframerateGteEnum() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean arrSurfacecontrolSetframerateApi() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean begoneBrightHlg() {
        return true;
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
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean connectedDisplayHdr() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean correctDpiWithDisplaySize() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean deprecateFrameTracker() {
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
    public boolean disableSyntheticVsyncForPerformance() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean displayConfigErrorHal() {
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
    public boolean filterFramesBeforeTraceStarts() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean flushBufferSlotsToUncache() {
        return true;
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
    public boolean graphiteRenderenginePreviewRollout() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpLevelHal() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpNegotiation() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hotplug2() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean increaseMissedFrameJankThreshold() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean latchUnsignaledWithAutoRefreshChanged() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean localTonemapScreenshots() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean misc1() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean monitorBufferFences() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean multithreadedPresent() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean noVsyncsOnScreenOff() {
        return false;
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
    public boolean rejectDupeLayerstacks() {
        return true;
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
    public boolean skipInvisibleWindowsInInput() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean stableEdidIds() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean syncedResolutionSwitch() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean trueHdrScreenshots() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean useKnownRefreshRateForFpsConsistency() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean viewSetRequestedFrameRateMrr() {
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
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vsyncPredictorRecovery() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vulkanRenderengine() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean windowBlurKawase2() {
        return false;
    }
}
