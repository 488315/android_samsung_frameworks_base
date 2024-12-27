package com.android.graphics.surfaceflinger.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE, Flags.FLAG_ADPF_GPU_SF, Flags.FLAG_ALLOW_N_VSYNCS_IN_TARGETER, Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED, Flags.FLAG_CE_FENCE_PROMISE, Flags.FLAG_COMMIT_NOT_COMPOSITED, Flags.FLAG_CONNECTED_DISPLAY, Flags.FLAG_DEPRECATE_VSYNC_SF, Flags.FLAG_DETACHED_MIRROR, Flags.FLAG_DISPLAY_PROTECTED, Flags.FLAG_DONT_SKIP_ON_EARLY_RO, Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES, Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING, Flags.FLAG_ENABLE_SMALL_AREA_DETECTION, Flags.FLAG_FLUSH_BUFFER_SLOTS_TO_UNCACHE, Flags.FLAG_FORCE_COMPILE_GRAPHITE_RENDERENGINE, Flags.FLAG_FP16_CLIENT_TARGET, Flags.FLAG_FRAME_RATE_CATEGORY_MRR, Flags.FLAG_GAME_DEFAULT_FRAME_RATE, Flags.FLAG_GRAPHITE_RENDERENGINE, Flags.FLAG_HDCP_LEVEL_HAL, Flags.FLAG_HOTPLUG2, Flags.FLAG_LATCH_UNSIGNALED_WITH_AUTO_REFRESH_CHANGED, Flags.FLAG_LOCAL_TONEMAP_SCREENSHOTS, Flags.FLAG_MISC1, Flags.FLAG_MULTITHREADED_PRESENT, Flags.FLAG_OVERRIDE_TRUSTED_OVERLAY, Flags.FLAG_PROTECTED_IF_CLIENT, Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY, Flags.FLAG_RENDERABLE_BUFFER_USAGE, Flags.FLAG_RESTORE_BLUR_STEP, Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION, Flags.FLAG_SINGLE_HOP_SCREENSHOT, Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY, Flags.FLAG_VRR_BUGFIX_24Q4, Flags.FLAG_VRR_BUGFIX_DROPPED_FRAME, Flags.FLAG_VRR_CONFIG, Flags.FLAG_VULKAN_RENDERENGINE, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean addSfSkippedFramesToTrace() {
        return getValue(Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addSfSkippedFramesToTrace();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean adpfGpuSf() {
        return getValue(Flags.FLAG_ADPF_GPU_SF, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfGpuSf();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean allowNVsyncsInTargeter() {
        return getValue(Flags.FLAG_ALLOW_N_VSYNCS_IN_TARGETER, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowNVsyncsInTargeter();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean cacheWhenSourceCropLayerOnlyMoved() {
        return getValue(Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheWhenSourceCropLayerOnlyMoved();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean ceFencePromise() {
        return getValue(Flags.FLAG_CE_FENCE_PROMISE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ceFencePromise();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean commitNotComposited() {
        return getValue(Flags.FLAG_COMMIT_NOT_COMPOSITED, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).commitNotComposited();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean connectedDisplay() {
        return getValue(Flags.FLAG_CONNECTED_DISPLAY, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).connectedDisplay();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean deprecateVsyncSf() {
        return getValue(Flags.FLAG_DEPRECATE_VSYNC_SF, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateVsyncSf();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean detachedMirror() {
        return getValue(Flags.FLAG_DETACHED_MIRROR, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).detachedMirror();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean displayProtected() {
        return getValue(Flags.FLAG_DISPLAY_PROTECTED, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayProtected();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo() {
        return getValue(Flags.FLAG_DONT_SKIP_ON_EARLY_RO, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dontSkipOnEarlyRo();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableFroDependentFeatures() {
        return getValue(Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFroDependentFeatures();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableLayerCommandBatching() {
        return getValue(Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLayerCommandBatching();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableSmallAreaDetection() {
        return getValue(Flags.FLAG_ENABLE_SMALL_AREA_DETECTION, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSmallAreaDetection();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean flushBufferSlotsToUncache() {
        return getValue(Flags.FLAG_FLUSH_BUFFER_SLOTS_TO_UNCACHE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).flushBufferSlotsToUncache();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean forceCompileGraphiteRenderengine() {
        return getValue(Flags.FLAG_FORCE_COMPILE_GRAPHITE_RENDERENGINE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceCompileGraphiteRenderengine();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean fp16ClientTarget() {
        return getValue(Flags.FLAG_FP16_CLIENT_TARGET, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fp16ClientTarget();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean frameRateCategoryMrr() {
        return getValue(Flags.FLAG_FRAME_RATE_CATEGORY_MRR, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).frameRateCategoryMrr();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return getValue(Flags.FLAG_GAME_DEFAULT_FRAME_RATE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gameDefaultFrameRate();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean graphiteRenderengine() {
        return getValue(Flags.FLAG_GRAPHITE_RENDERENGINE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).graphiteRenderengine();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpLevelHal() {
        return getValue(Flags.FLAG_HDCP_LEVEL_HAL, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdcpLevelHal();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hotplug2() {
        return getValue(Flags.FLAG_HOTPLUG2, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hotplug2();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean latchUnsignaledWithAutoRefreshChanged() {
        return getValue(Flags.FLAG_LATCH_UNSIGNALED_WITH_AUTO_REFRESH_CHANGED, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).latchUnsignaledWithAutoRefreshChanged();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean localTonemapScreenshots() {
        return getValue(Flags.FLAG_LOCAL_TONEMAP_SCREENSHOTS, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).localTonemapScreenshots();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean misc1() {
        return getValue(Flags.FLAG_MISC1, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).misc1();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean multithreadedPresent() {
        return getValue(Flags.FLAG_MULTITHREADED_PRESENT, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multithreadedPresent();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean overrideTrustedOverlay() {
        return getValue(Flags.FLAG_OVERRIDE_TRUSTED_OVERLAY, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).overrideTrustedOverlay();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean protectedIfClient() {
        return getValue(Flags.FLAG_PROTECTED_IF_CLIENT, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).protectedIfClient();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean refreshRateOverlayOnExternalDisplay() {
        return getValue(Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refreshRateOverlayOnExternalDisplay();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean renderableBufferUsage() {
        return getValue(Flags.FLAG_RENDERABLE_BUFFER_USAGE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).renderableBufferUsage();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean restoreBlurStep() {
        return getValue(Flags.FLAG_RESTORE_BLUR_STEP, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restoreBlurStep();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean screenshotFencePreservation() {
        return getValue(Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).screenshotFencePreservation();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean singleHopScreenshot() {
        return getValue(Flags.FLAG_SINGLE_HOP_SCREENSHOT, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).singleHopScreenshot();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean useKnownRefreshRateForFpsConsistency() {
        return getValue(Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useKnownRefreshRateForFpsConsistency();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrBugfix24q4() {
        return getValue(Flags.FLAG_VRR_BUGFIX_24Q4, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vrrBugfix24q4();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrBugfixDroppedFrame() {
        return getValue(Flags.FLAG_VRR_BUGFIX_DROPPED_FRAME, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vrrBugfixDroppedFrame();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrConfig() {
        return getValue(Flags.FLAG_VRR_CONFIG, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vrrConfig();
            }
        });
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vulkanRenderengine() {
        return getValue(Flags.FLAG_VULKAN_RENDERENGINE, new Predicate() { // from class: com.android.graphics.surfaceflinger.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vulkanRenderengine();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE, Flags.FLAG_ADPF_GPU_SF, Flags.FLAG_ALLOW_N_VSYNCS_IN_TARGETER, Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED, Flags.FLAG_CE_FENCE_PROMISE, Flags.FLAG_COMMIT_NOT_COMPOSITED, Flags.FLAG_CONNECTED_DISPLAY, Flags.FLAG_DEPRECATE_VSYNC_SF, Flags.FLAG_DETACHED_MIRROR, Flags.FLAG_DISPLAY_PROTECTED, Flags.FLAG_DONT_SKIP_ON_EARLY_RO, Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES, Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING, Flags.FLAG_ENABLE_SMALL_AREA_DETECTION, Flags.FLAG_FLUSH_BUFFER_SLOTS_TO_UNCACHE, Flags.FLAG_FORCE_COMPILE_GRAPHITE_RENDERENGINE, Flags.FLAG_FP16_CLIENT_TARGET, Flags.FLAG_FRAME_RATE_CATEGORY_MRR, Flags.FLAG_GAME_DEFAULT_FRAME_RATE, Flags.FLAG_GRAPHITE_RENDERENGINE, Flags.FLAG_HDCP_LEVEL_HAL, Flags.FLAG_HOTPLUG2, Flags.FLAG_LATCH_UNSIGNALED_WITH_AUTO_REFRESH_CHANGED, Flags.FLAG_LOCAL_TONEMAP_SCREENSHOTS, Flags.FLAG_MISC1, Flags.FLAG_MULTITHREADED_PRESENT, Flags.FLAG_OVERRIDE_TRUSTED_OVERLAY, Flags.FLAG_PROTECTED_IF_CLIENT, Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY, Flags.FLAG_RENDERABLE_BUFFER_USAGE, Flags.FLAG_RESTORE_BLUR_STEP, Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION, Flags.FLAG_SINGLE_HOP_SCREENSHOT, Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY, Flags.FLAG_VRR_BUGFIX_24Q4, Flags.FLAG_VRR_BUGFIX_DROPPED_FRAME, Flags.FLAG_VRR_CONFIG, Flags.FLAG_VULKAN_RENDERENGINE);
    }
}
