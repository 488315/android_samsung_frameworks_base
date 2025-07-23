package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean addSfSkippedFramesToTrace();

    boolean adpfFmqSf();

    boolean adpfGpuSf();

    boolean adpfNativeSessionManager();

    boolean allowNVsyncsInTargeter();

    boolean arrSetframerateApi();

    boolean arrSetframerateGteEnum();

    boolean arrSurfacecontrolSetframerateApi();

    boolean begoneBrightHlg();

    boolean cacheWhenSourceCropLayerOnlyMoved();

    boolean ceFencePromise();

    boolean commitNotComposited();

    boolean connectedDisplay();

    boolean connectedDisplayHdr();

    boolean correctDpiWithDisplaySize();

    boolean deprecateFrameTracker();

    boolean deprecateVsyncSf();

    boolean detachedMirror();

    boolean disableSyntheticVsyncForPerformance();

    boolean displayConfigErrorHal();

    boolean displayProtected();

    boolean dontSkipOnEarlyRo();

    boolean enableFroDependentFeatures();

    boolean enableLayerCommandBatching();

    boolean enableSmallAreaDetection();

    boolean filterFramesBeforeTraceStarts();

    boolean flushBufferSlotsToUncache();

    boolean forceCompileGraphiteRenderengine();

    boolean fp16ClientTarget();

    boolean frameRateCategoryMrr();

    boolean gameDefaultFrameRate();

    boolean graphiteRenderengine();

    boolean graphiteRenderenginePreviewRollout();

    boolean hdcpLevelHal();

    boolean hdcpNegotiation();

    boolean hotplug2();

    boolean increaseMissedFrameJankThreshold();

    boolean latchUnsignaledWithAutoRefreshChanged();

    boolean localTonemapScreenshots();

    boolean misc1();

    boolean monitorBufferFences();

    boolean multithreadedPresent();

    boolean noVsyncsOnScreenOff();

    boolean overrideTrustedOverlay();

    boolean protectedIfClient();

    boolean refreshRateOverlayOnExternalDisplay();

    boolean rejectDupeLayerstacks();

    boolean renderableBufferUsage();

    boolean restoreBlurStep();

    boolean screenshotFencePreservation();

    boolean singleHopScreenshot();

    boolean skipInvisibleWindowsInInput();

    boolean stableEdidIds();

    boolean syncedResolutionSwitch();

    boolean trueHdrScreenshots();

    boolean useKnownRefreshRateForFpsConsistency();

    boolean viewSetRequestedFrameRateMrr();

    boolean vrrBugfix24q4();

    boolean vrrBugfixDroppedFrame();

    boolean vrrConfig();

    boolean vsyncPredictorRecovery();

    boolean vulkanRenderengine();

    boolean windowBlurKawase2();
}
