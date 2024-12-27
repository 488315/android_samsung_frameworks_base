package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean addSfSkippedFramesToTrace();

    boolean adpfGpuSf();

    boolean allowNVsyncsInTargeter();

    boolean cacheWhenSourceCropLayerOnlyMoved();

    boolean ceFencePromise();

    boolean commitNotComposited();

    boolean connectedDisplay();

    boolean deprecateVsyncSf();

    boolean detachedMirror();

    boolean displayProtected();

    boolean dontSkipOnEarlyRo();

    boolean enableFroDependentFeatures();

    boolean enableLayerCommandBatching();

    boolean enableSmallAreaDetection();

    boolean flushBufferSlotsToUncache();

    boolean forceCompileGraphiteRenderengine();

    boolean fp16ClientTarget();

    boolean frameRateCategoryMrr();

    boolean gameDefaultFrameRate();

    boolean graphiteRenderengine();

    boolean hdcpLevelHal();

    boolean hotplug2();

    boolean latchUnsignaledWithAutoRefreshChanged();

    boolean localTonemapScreenshots();

    boolean misc1();

    boolean multithreadedPresent();

    boolean overrideTrustedOverlay();

    boolean protectedIfClient();

    boolean refreshRateOverlayOnExternalDisplay();

    boolean renderableBufferUsage();

    boolean restoreBlurStep();

    boolean screenshotFencePreservation();

    boolean singleHopScreenshot();

    boolean useKnownRefreshRateForFpsConsistency();

    boolean vrrBugfix24q4();

    boolean vrrBugfixDroppedFrame();

    boolean vrrConfig();

    boolean vulkanRenderengine();
}
