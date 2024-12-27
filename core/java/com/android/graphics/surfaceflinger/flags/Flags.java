package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE = "com.android.graphics.surfaceflinger.flags.add_sf_skipped_frames_to_trace";
    public static final String FLAG_ADPF_GPU_SF = "com.android.graphics.surfaceflinger.flags.adpf_gpu_sf";
    public static final String FLAG_ALLOW_N_VSYNCS_IN_TARGETER = "com.android.graphics.surfaceflinger.flags.allow_n_vsyncs_in_targeter";
    public static final String FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED = "com.android.graphics.surfaceflinger.flags.cache_when_source_crop_layer_only_moved";
    public static final String FLAG_CE_FENCE_PROMISE = "com.android.graphics.surfaceflinger.flags.ce_fence_promise";
    public static final String FLAG_COMMIT_NOT_COMPOSITED = "com.android.graphics.surfaceflinger.flags.commit_not_composited";
    public static final String FLAG_CONNECTED_DISPLAY = "com.android.graphics.surfaceflinger.flags.connected_display";
    public static final String FLAG_DEPRECATE_VSYNC_SF = "com.android.graphics.surfaceflinger.flags.deprecate_vsync_sf";
    public static final String FLAG_DETACHED_MIRROR = "com.android.graphics.surfaceflinger.flags.detached_mirror";
    public static final String FLAG_DISPLAY_PROTECTED = "com.android.graphics.surfaceflinger.flags.display_protected";
    public static final String FLAG_DONT_SKIP_ON_EARLY_RO = "com.android.graphics.surfaceflinger.flags.dont_skip_on_early_ro";
    public static final String FLAG_ENABLE_FRO_DEPENDENT_FEATURES = "com.android.graphics.surfaceflinger.flags.enable_fro_dependent_features";
    public static final String FLAG_ENABLE_LAYER_COMMAND_BATCHING = "com.android.graphics.surfaceflinger.flags.enable_layer_command_batching";
    public static final String FLAG_ENABLE_SMALL_AREA_DETECTION = "com.android.graphics.surfaceflinger.flags.enable_small_area_detection";
    public static final String FLAG_FLUSH_BUFFER_SLOTS_TO_UNCACHE = "com.android.graphics.surfaceflinger.flags.flush_buffer_slots_to_uncache";
    public static final String FLAG_FORCE_COMPILE_GRAPHITE_RENDERENGINE = "com.android.graphics.surfaceflinger.flags.force_compile_graphite_renderengine";
    public static final String FLAG_FP16_CLIENT_TARGET = "com.android.graphics.surfaceflinger.flags.fp16_client_target";
    public static final String FLAG_FRAME_RATE_CATEGORY_MRR = "com.android.graphics.surfaceflinger.flags.frame_rate_category_mrr";
    public static final String FLAG_GAME_DEFAULT_FRAME_RATE = "com.android.graphics.surfaceflinger.flags.game_default_frame_rate";
    public static final String FLAG_GRAPHITE_RENDERENGINE = "com.android.graphics.surfaceflinger.flags.graphite_renderengine";
    public static final String FLAG_HDCP_LEVEL_HAL = "com.android.graphics.surfaceflinger.flags.hdcp_level_hal";
    public static final String FLAG_HOTPLUG2 = "com.android.graphics.surfaceflinger.flags.hotplug2";
    public static final String FLAG_LATCH_UNSIGNALED_WITH_AUTO_REFRESH_CHANGED = "com.android.graphics.surfaceflinger.flags.latch_unsignaled_with_auto_refresh_changed";
    public static final String FLAG_LOCAL_TONEMAP_SCREENSHOTS = "com.android.graphics.surfaceflinger.flags.local_tonemap_screenshots";
    public static final String FLAG_MISC1 = "com.android.graphics.surfaceflinger.flags.misc1";
    public static final String FLAG_MULTITHREADED_PRESENT = "com.android.graphics.surfaceflinger.flags.multithreaded_present";
    public static final String FLAG_OVERRIDE_TRUSTED_OVERLAY = "com.android.graphics.surfaceflinger.flags.override_trusted_overlay";
    public static final String FLAG_PROTECTED_IF_CLIENT = "com.android.graphics.surfaceflinger.flags.protected_if_client";
    public static final String FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY = "com.android.graphics.surfaceflinger.flags.refresh_rate_overlay_on_external_display";
    public static final String FLAG_RENDERABLE_BUFFER_USAGE = "com.android.graphics.surfaceflinger.flags.renderable_buffer_usage";
    public static final String FLAG_RESTORE_BLUR_STEP = "com.android.graphics.surfaceflinger.flags.restore_blur_step";
    public static final String FLAG_SCREENSHOT_FENCE_PRESERVATION = "com.android.graphics.surfaceflinger.flags.screenshot_fence_preservation";
    public static final String FLAG_SINGLE_HOP_SCREENSHOT = "com.android.graphics.surfaceflinger.flags.single_hop_screenshot";
    public static final String FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY = "com.android.graphics.surfaceflinger.flags.use_known_refresh_rate_for_fps_consistency";
    public static final String FLAG_VRR_BUGFIX_24Q4 = "com.android.graphics.surfaceflinger.flags.vrr_bugfix_24q4";
    public static final String FLAG_VRR_BUGFIX_DROPPED_FRAME = "com.android.graphics.surfaceflinger.flags.vrr_bugfix_dropped_frame";
    public static final String FLAG_VRR_CONFIG = "com.android.graphics.surfaceflinger.flags.vrr_config";
    public static final String FLAG_VULKAN_RENDERENGINE = "com.android.graphics.surfaceflinger.flags.vulkan_renderengine";

    public static boolean addSfSkippedFramesToTrace() {
        return FEATURE_FLAGS.addSfSkippedFramesToTrace();
    }

    public static boolean adpfGpuSf() {
        return FEATURE_FLAGS.adpfGpuSf();
    }

    public static boolean allowNVsyncsInTargeter() {
        return FEATURE_FLAGS.allowNVsyncsInTargeter();
    }

    public static boolean cacheWhenSourceCropLayerOnlyMoved() {
        return FEATURE_FLAGS.cacheWhenSourceCropLayerOnlyMoved();
    }

    public static boolean ceFencePromise() {
        return FEATURE_FLAGS.ceFencePromise();
    }

    public static boolean commitNotComposited() {
        return FEATURE_FLAGS.commitNotComposited();
    }

    public static boolean connectedDisplay() {
        return FEATURE_FLAGS.connectedDisplay();
    }

    public static boolean deprecateVsyncSf() {
        return FEATURE_FLAGS.deprecateVsyncSf();
    }

    public static boolean detachedMirror() {
        return FEATURE_FLAGS.detachedMirror();
    }

    public static boolean displayProtected() {
        return FEATURE_FLAGS.displayProtected();
    }

    public static boolean dontSkipOnEarlyRo() {
        return FEATURE_FLAGS.dontSkipOnEarlyRo();
    }

    public static boolean enableFroDependentFeatures() {
        return FEATURE_FLAGS.enableFroDependentFeatures();
    }

    public static boolean enableLayerCommandBatching() {
        return FEATURE_FLAGS.enableLayerCommandBatching();
    }

    public static boolean enableSmallAreaDetection() {
        return FEATURE_FLAGS.enableSmallAreaDetection();
    }

    public static boolean flushBufferSlotsToUncache() {
        return FEATURE_FLAGS.flushBufferSlotsToUncache();
    }

    public static boolean forceCompileGraphiteRenderengine() {
        return FEATURE_FLAGS.forceCompileGraphiteRenderengine();
    }

    public static boolean fp16ClientTarget() {
        return FEATURE_FLAGS.fp16ClientTarget();
    }

    public static boolean frameRateCategoryMrr() {
        return FEATURE_FLAGS.frameRateCategoryMrr();
    }

    public static boolean gameDefaultFrameRate() {
        return FEATURE_FLAGS.gameDefaultFrameRate();
    }

    public static boolean graphiteRenderengine() {
        return FEATURE_FLAGS.graphiteRenderengine();
    }

    public static boolean hdcpLevelHal() {
        return FEATURE_FLAGS.hdcpLevelHal();
    }

    public static boolean hotplug2() {
        return FEATURE_FLAGS.hotplug2();
    }

    public static boolean latchUnsignaledWithAutoRefreshChanged() {
        return FEATURE_FLAGS.latchUnsignaledWithAutoRefreshChanged();
    }

    public static boolean localTonemapScreenshots() {
        return FEATURE_FLAGS.localTonemapScreenshots();
    }

    public static boolean misc1() {
        return FEATURE_FLAGS.misc1();
    }

    public static boolean multithreadedPresent() {
        return FEATURE_FLAGS.multithreadedPresent();
    }

    public static boolean overrideTrustedOverlay() {
        return FEATURE_FLAGS.overrideTrustedOverlay();
    }

    public static boolean protectedIfClient() {
        return FEATURE_FLAGS.protectedIfClient();
    }

    public static boolean refreshRateOverlayOnExternalDisplay() {
        return FEATURE_FLAGS.refreshRateOverlayOnExternalDisplay();
    }

    public static boolean renderableBufferUsage() {
        return FEATURE_FLAGS.renderableBufferUsage();
    }

    public static boolean restoreBlurStep() {
        return FEATURE_FLAGS.restoreBlurStep();
    }

    public static boolean screenshotFencePreservation() {
        return FEATURE_FLAGS.screenshotFencePreservation();
    }

    public static boolean singleHopScreenshot() {
        return FEATURE_FLAGS.singleHopScreenshot();
    }

    public static boolean useKnownRefreshRateForFpsConsistency() {
        return FEATURE_FLAGS.useKnownRefreshRateForFpsConsistency();
    }

    public static boolean vrrBugfix24q4() {
        return FEATURE_FLAGS.vrrBugfix24q4();
    }

    public static boolean vrrBugfixDroppedFrame() {
        return FEATURE_FLAGS.vrrBugfixDroppedFrame();
    }

    public static boolean vrrConfig() {
        return FEATURE_FLAGS.vrrConfig();
    }

    public static boolean vulkanRenderengine() {
        return FEATURE_FLAGS.vulkanRenderengine();
    }
}
