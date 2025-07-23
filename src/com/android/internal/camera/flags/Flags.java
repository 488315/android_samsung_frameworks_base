package com.android.internal.camera.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_AE_PRIORITY = "com.android.internal.camera.flags.ae_priority";
    public static final String FLAG_ANALYTICS_24Q3 = "com.android.internal.camera.flags.analytics_24q3";
    public static final String FLAG_API1_RELEASE_BINDERLOCK_BEFORE_CAMERASERVICE_DISCONNECT = "com.android.internal.camera.flags.api1_release_binderlock_before_cameraservice_disconnect";
    public static final String FLAG_BUMP_PREVIEW_FRAME_SPACE_PRIORITY = "com.android.internal.camera.flags.bump_preview_frame_space_priority";
    public static final String FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST = "com.android.internal.camera.flags.camera_ae_mode_low_light_boost";
    public static final String FLAG_CAMERA_DEVICE_SETUP = "com.android.internal.camera.flags.camera_device_setup";
    public static final String FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET = "com.android.internal.camera.flags.camera_extensions_characteristics_get";
    public static final String FLAG_CAMERA_HEIF_GAINMAP = "com.android.internal.camera.flags.camera_heif_gainmap";
    public static final String FLAG_CAMERA_HSUM_PERMISSION = "com.android.internal.camera.flags.camera_hsum_permission";
    public static final String FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL = "com.android.internal.camera.flags.camera_manual_flash_strength_control";
    public static final String FLAG_CAMERA_MULTI_CLIENT = "com.android.internal.camera.flags.camera_multi_client";
    public static final String FLAG_CAMERA_PRIVACY_ALLOWLIST = "com.android.internal.camera.flags.camera_privacy_allowlist";
    public static final String FLAG_COLOR_TEMPERATURE = "com.android.internal.camera.flags.color_temperature";
    public static final String FLAG_CONCERT_MODE = "com.android.internal.camera.flags.concert_mode";
    public static final String FLAG_DATA_DELIVERY_PERMISSION_CHECKS = "com.android.internal.camera.flags.data_delivery_permission_checks";
    public static final String FLAG_DEPTH_JPEG_EXTENSIONS = "com.android.internal.camera.flags.depth_jpeg_extensions";
    public static final String FLAG_DESKTOP_EFFECTS = "com.android.internal.camera.flags.desktop_effects";
    public static final String FLAG_DUMPSYS_REQUEST_STREAM_IDS = "com.android.internal.camera.flags.dumpsys_request_stream_ids";
    public static final String FLAG_ENABLE_HAL_ABORT_FROM_CAMERASERVICEWATCHDOG = "com.android.internal.camera.flags.enable_hal_abort_from_cameraservicewatchdog";
    public static final String FLAG_ENABLE_STREAM_RECONFIGURATION_FOR_UNCHANGED_STREAMS = "com.android.internal.camera.flags.enable_stream_reconfiguration_for_unchanged_streams";
    public static final String FLAG_EXTENSION_10_BIT = "com.android.internal.camera.flags.extension_10_bit";
    public static final String FLAG_FEATURE_COMBINATION_BAKLAVA = "com.android.internal.camera.flags.feature_combination_baklava";
    public static final String FLAG_FEATURE_COMBINATION_QUERY = "com.android.internal.camera.flags.feature_combination_query";
    public static final String FLAG_FMQ_METADATA = "com.android.internal.camera.flags.fmq_metadata";
    public static final String FLAG_INJECT_SESSION_PARAMS = "com.android.internal.camera.flags.inject_session_params";
    public static final String FLAG_METADATA_RESIZE_FIX = "com.android.internal.camera.flags.metadata_resize_fix";
    public static final String FLAG_MIRROR_MODE_SHARED_SURFACES = "com.android.internal.camera.flags.mirror_mode_shared_surfaces";
    public static final String FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG = "com.android.internal.camera.flags.multiresolution_imagereader_usage_config";
    public static final String FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_PUBLIC = "com.android.internal.camera.flags.multiresolution_imagereader_usage_public";
    public static final String FLAG_MULTI_RES_RAW_REPROCESSING = "com.android.internal.camera.flags.multi_res_raw_reprocessing";
    public static final String FLAG_NIGHT_MODE_INDICATOR = "com.android.internal.camera.flags.night_mode_indicator";
    public static final String FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS = "com.android.internal.camera.flags.return_buffers_outside_locks";
    public static final String FLAG_SINGLE_THREAD_EXECUTOR_NAMING = "com.android.internal.camera.flags.single_thread_executor_naming";
    public static final String FLAG_ZOOM_METHOD = "com.android.internal.camera.flags.zoom_method";

    public static boolean aePriority() {
        return FEATURE_FLAGS.aePriority();
    }

    public static boolean analytics24q3() {
        return FEATURE_FLAGS.analytics24q3();
    }

    public static boolean api1ReleaseBinderlockBeforeCameraserviceDisconnect() {
        return FEATURE_FLAGS.api1ReleaseBinderlockBeforeCameraserviceDisconnect();
    }

    public static boolean bumpPreviewFrameSpacePriority() {
        return FEATURE_FLAGS.bumpPreviewFrameSpacePriority();
    }

    public static boolean cameraAeModeLowLightBoost() {
        return FEATURE_FLAGS.cameraAeModeLowLightBoost();
    }

    public static boolean cameraDeviceSetup() {
        return FEATURE_FLAGS.cameraDeviceSetup();
    }

    public static boolean cameraExtensionsCharacteristicsGet() {
        return FEATURE_FLAGS.cameraExtensionsCharacteristicsGet();
    }

    public static boolean cameraHeifGainmap() {
        return FEATURE_FLAGS.cameraHeifGainmap();
    }

    public static boolean cameraHsumPermission() {
        return FEATURE_FLAGS.cameraHsumPermission();
    }

    public static boolean cameraManualFlashStrengthControl() {
        return FEATURE_FLAGS.cameraManualFlashStrengthControl();
    }

    public static boolean cameraMultiClient() {
        return FEATURE_FLAGS.cameraMultiClient();
    }

    public static boolean cameraPrivacyAllowlist() {
        return FEATURE_FLAGS.cameraPrivacyAllowlist();
    }

    public static boolean colorTemperature() {
        return FEATURE_FLAGS.colorTemperature();
    }

    public static boolean concertMode() {
        return FEATURE_FLAGS.concertMode();
    }

    public static boolean dataDeliveryPermissionChecks() {
        return FEATURE_FLAGS.dataDeliveryPermissionChecks();
    }

    public static boolean depthJpegExtensions() {
        return FEATURE_FLAGS.depthJpegExtensions();
    }

    public static boolean desktopEffects() {
        return FEATURE_FLAGS.desktopEffects();
    }

    public static boolean dumpsysRequestStreamIds() {
        return FEATURE_FLAGS.dumpsysRequestStreamIds();
    }

    public static boolean enableHalAbortFromCameraservicewatchdog() {
        return FEATURE_FLAGS.enableHalAbortFromCameraservicewatchdog();
    }

    public static boolean enableStreamReconfigurationForUnchangedStreams() {
        return FEATURE_FLAGS.enableStreamReconfigurationForUnchangedStreams();
    }

    public static boolean extension10Bit() {
        return FEATURE_FLAGS.extension10Bit();
    }

    public static boolean featureCombinationBaklava() {
        return FEATURE_FLAGS.featureCombinationBaklava();
    }

    public static boolean featureCombinationQuery() {
        return FEATURE_FLAGS.featureCombinationQuery();
    }

    public static boolean fmqMetadata() {
        return FEATURE_FLAGS.fmqMetadata();
    }

    public static boolean injectSessionParams() {
        return FEATURE_FLAGS.injectSessionParams();
    }

    public static boolean metadataResizeFix() {
        return FEATURE_FLAGS.metadataResizeFix();
    }

    public static boolean mirrorModeSharedSurfaces() {
        return FEATURE_FLAGS.mirrorModeSharedSurfaces();
    }

    public static boolean multiResRawReprocessing() {
        return FEATURE_FLAGS.multiResRawReprocessing();
    }

    public static boolean multiresolutionImagereaderUsageConfig() {
        return FEATURE_FLAGS.multiresolutionImagereaderUsageConfig();
    }

    public static boolean multiresolutionImagereaderUsagePublic() {
        return FEATURE_FLAGS.multiresolutionImagereaderUsagePublic();
    }

    public static boolean nightModeIndicator() {
        return FEATURE_FLAGS.nightModeIndicator();
    }

    public static boolean returnBuffersOutsideLocks() {
        return FEATURE_FLAGS.returnBuffersOutsideLocks();
    }

    public static boolean singleThreadExecutorNaming() {
        return FEATURE_FLAGS.singleThreadExecutorNaming();
    }

    public static boolean zoomMethod() {
        return FEATURE_FLAGS.zoomMethod();
    }
}
