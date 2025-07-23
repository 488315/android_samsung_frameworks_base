package com.android.internal.hidden_from_bootclasspath.android.os;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADPF_25Q2_METRICS = "android.os.adpf_25q2_metrics";
    public static final String FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION = "android.os.adpf_gpu_report_actual_work_duration";
    public static final String FLAG_ADPF_GRAPHICS_PIPELINE = "android.os.adpf_graphics_pipeline";
    public static final String FLAG_ADPF_HWUI_GPU = "android.os.adpf_hwui_gpu";
    public static final String FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST = "android.os.adpf_measure_during_input_event_boost";
    public static final String FLAG_ADPF_OBTAINVIEW_BOOST = "android.os.adpf_obtainview_boost";
    public static final String FLAG_ADPF_PLATFORM_POWER_EFFICIENCY = "android.os.adpf_platform_power_efficiency";
    public static final String FLAG_ADPF_PREFER_POWER_EFFICIENCY = "android.os.adpf_prefer_power_efficiency";
    public static final String FLAG_ADPF_USE_FMQ_CHANNEL = "android.os.adpf_use_fmq_channel";
    public static final String FLAG_ADPF_USE_FMQ_CHANNEL_FIXED = "android.os.adpf_use_fmq_channel_fixed";
    public static final String FLAG_ADPF_USE_LOAD_HINTS = "android.os.adpf_use_load_hints";
    public static final String FLAG_ALLOW_CONSENTLESS_BUGREPORT_DELEGATED_CONSENT = "android.os.allow_consentless_bugreport_delegated_consent";
    public static final String FLAG_ALLOW_PRIVATE_PROFILE = "android.os.allow_private_profile";
    public static final String FLAG_ALLOW_THERMAL_HAL_SKIN_FORECAST = "android.os.allow_thermal_hal_skin_forecast";
    public static final String FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS = "android.os.allow_thermal_headroom_thresholds";
    public static final String FLAG_ALLOW_THERMAL_THRESHOLDS_CALLBACK = "android.os.allow_thermal_thresholds_callback";
    public static final String FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM = "android.os.android_os_build_vanilla_ice_cream";
    public static final String FLAG_API_FOR_BACKPORTED_FIXES = "android.os.api_for_backported_fixes";
    public static final String FLAG_APP_ZYGOTE_RETRY_START = "android.os.app_zygote_retry_start";
    public static final String FLAG_BATTERY_PART_STATUS_API = "android.os.battery_part_status_api";
    public static final String FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API = "android.os.battery_saver_supported_check_api";
    public static final String FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND = "android.os.battery_service_support_current_adb_command";
    public static final String FLAG_BINDER_FROZEN_STATE_CHANGE_CALLBACK = "android.os.binder_frozen_state_change_callback";
    public static final String FLAG_CPU_GPU_HEADROOMS = "android.os.cpu_gpu_headrooms";
    public static final String FLAG_DISABLE_MADVISE_ARTFILE_DEFAULT = "android.os.disable_madvise_artfile_default";
    public static final String FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION = "android.os.disallow_cellular_null_ciphers_restriction";
    public static final String FLAG_ENABLE_ANGLE_ALLOW_LIST = "android.os.enable_angle_allow_list";
    public static final String FLAG_ENABLE_HAS_BINDERS = "android.os.enable_has_binders";
    public static final String FLAG_FORCE_CONCURRENT_MESSAGE_QUEUE = "android.os.force_concurrent_message_queue";
    public static final String FLAG_GET_PRIVATE_SPACE_SETTINGS = "android.os.get_private_space_settings";
    public static final String FLAG_IPC_DATA_CACHE_TEST_APIS = "android.os.ipc_data_cache_test_apis";
    public static final String FLAG_MAINLINE_VCN_PLATFORM_API = "android.os.mainline_vcn_platform_api";
    public static final String FLAG_MATERIAL_COLORS_10_2024 = "android.os.material_colors_10_2024";
    public static final String FLAG_MATERIAL_MOTION_TOKENS = "android.os.material_motion_tokens";
    public static final String FLAG_MATERIAL_SHAPE_TOKENS = "android.os.material_shape_tokens";
    public static final String FLAG_MESSAGE_QUEUE_FORCE_LEGACY = "android.os.message_queue_force_legacy";
    public static final String FLAG_MESSAGE_QUEUE_TAIL_TRACKING = "android.os.message_queue_tail_tracking";
    public static final String FLAG_MESSAGE_QUEUE_TESTABILITY = "android.os.message_queue_testability";
    public static final String FLAG_NETWORK_TIME_USES_SHARED_MEMORY = "android.os.network_time_uses_shared_memory";
    public static final String FLAG_ORDERED_BROADCAST_MULTIPLE_PERMISSIONS = "android.os.ordered_broadcast_multiple_permissions";
    public static final String FLAG_PARCEL_MARSHALL_BYTEBUFFER = "android.os.parcel_marshall_bytebuffer";
    public static final String FLAG_PERFETTO_SDK_TRACING = "android.os.perfetto_sdk_tracing";
    public static final String FLAG_PERFETTO_SDK_TRACING_V2 = "android.os.perfetto_sdk_tracing_v2";
    public static final String FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION = "android.os.remove_app_profiler_pss_collection";
    public static final String FLAG_SECURITY_STATE_SERVICE = "android.os.security_state_service";
    public static final String FLAG_STATE_OF_HEALTH_PUBLIC = "android.os.state_of_health_public";
    public static final String FLAG_STORAGE_LIFETIME_API = "android.os.storage_lifetime_api";
    public static final String FLAG_STRICT_MODE_RESTRICTED_NETWORK = "android.os.strict_mode_restricted_network";
    public static final String FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION = "android.os.telemetry_apis_framework_initialization";
    public static final String FLAG_UPDATE_ENGINE_API = "android.os.update_engine_api";

    public static boolean adpf25q2Metrics() {
        return FEATURE_FLAGS.adpf25q2Metrics();
    }

    public static boolean adpfGpuReportActualWorkDuration() {
        return FEATURE_FLAGS.adpfGpuReportActualWorkDuration();
    }

    public static boolean adpfGraphicsPipeline() {
        return FEATURE_FLAGS.adpfGraphicsPipeline();
    }

    public static boolean adpfHwuiGpu() {
        return FEATURE_FLAGS.adpfHwuiGpu();
    }

    public static boolean adpfMeasureDuringInputEventBoost() {
        return FEATURE_FLAGS.adpfMeasureDuringInputEventBoost();
    }

    public static boolean adpfObtainviewBoost() {
        return FEATURE_FLAGS.adpfObtainviewBoost();
    }

    public static boolean adpfPlatformPowerEfficiency() {
        return FEATURE_FLAGS.adpfPlatformPowerEfficiency();
    }

    public static boolean adpfPreferPowerEfficiency() {
        return FEATURE_FLAGS.adpfPreferPowerEfficiency();
    }

    public static boolean adpfUseFmqChannel() {
        return FEATURE_FLAGS.adpfUseFmqChannel();
    }

    public static boolean adpfUseFmqChannelFixed() {
        return FEATURE_FLAGS.adpfUseFmqChannelFixed();
    }

    public static boolean adpfUseLoadHints() {
        return FEATURE_FLAGS.adpfUseLoadHints();
    }

    public static boolean allowConsentlessBugreportDelegatedConsent() {
        return FEATURE_FLAGS.allowConsentlessBugreportDelegatedConsent();
    }

    public static boolean allowPrivateProfile() {
        return FEATURE_FLAGS.allowPrivateProfile();
    }

    public static boolean allowThermalHalSkinForecast() {
        return FEATURE_FLAGS.allowThermalHalSkinForecast();
    }

    public static boolean allowThermalHeadroomThresholds() {
        return FEATURE_FLAGS.allowThermalHeadroomThresholds();
    }

    public static boolean allowThermalThresholdsCallback() {
        return FEATURE_FLAGS.allowThermalThresholdsCallback();
    }

    public static boolean androidOsBuildVanillaIceCream() {
        return FEATURE_FLAGS.androidOsBuildVanillaIceCream();
    }

    public static boolean apiForBackportedFixes() {
        return FEATURE_FLAGS.apiForBackportedFixes();
    }

    public static boolean appZygoteRetryStart() {
        return FEATURE_FLAGS.appZygoteRetryStart();
    }

    public static boolean batteryPartStatusApi() {
        return FEATURE_FLAGS.batteryPartStatusApi();
    }

    public static boolean batterySaverSupportedCheckApi() {
        return FEATURE_FLAGS.batterySaverSupportedCheckApi();
    }

    public static boolean batteryServiceSupportCurrentAdbCommand() {
        return FEATURE_FLAGS.batteryServiceSupportCurrentAdbCommand();
    }

    public static boolean binderFrozenStateChangeCallback() {
        return FEATURE_FLAGS.binderFrozenStateChangeCallback();
    }

    public static boolean cpuGpuHeadrooms() {
        return FEATURE_FLAGS.cpuGpuHeadrooms();
    }

    public static boolean disableMadviseArtfileDefault() {
        return FEATURE_FLAGS.disableMadviseArtfileDefault();
    }

    public static boolean disallowCellularNullCiphersRestriction() {
        return FEATURE_FLAGS.disallowCellularNullCiphersRestriction();
    }

    public static boolean enableAngleAllowList() {
        return FEATURE_FLAGS.enableAngleAllowList();
    }

    public static boolean enableHasBinders() {
        return FEATURE_FLAGS.enableHasBinders();
    }

    public static boolean forceConcurrentMessageQueue() {
        return FEATURE_FLAGS.forceConcurrentMessageQueue();
    }

    public static boolean getPrivateSpaceSettings() {
        return FEATURE_FLAGS.getPrivateSpaceSettings();
    }

    public static boolean ipcDataCacheTestApis() {
        return FEATURE_FLAGS.ipcDataCacheTestApis();
    }

    public static boolean mainlineVcnPlatformApi() {
        return FEATURE_FLAGS.mainlineVcnPlatformApi();
    }

    public static boolean materialColors102024() {
        return FEATURE_FLAGS.materialColors102024();
    }

    public static boolean materialMotionTokens() {
        return FEATURE_FLAGS.materialMotionTokens();
    }

    public static boolean materialShapeTokens() {
        return FEATURE_FLAGS.materialShapeTokens();
    }

    public static boolean messageQueueForceLegacy() {
        return FEATURE_FLAGS.messageQueueForceLegacy();
    }

    public static boolean messageQueueTailTracking() {
        return FEATURE_FLAGS.messageQueueTailTracking();
    }

    public static boolean messageQueueTestability() {
        return FEATURE_FLAGS.messageQueueTestability();
    }

    public static boolean networkTimeUsesSharedMemory() {
        return FEATURE_FLAGS.networkTimeUsesSharedMemory();
    }

    public static boolean orderedBroadcastMultiplePermissions() {
        return FEATURE_FLAGS.orderedBroadcastMultiplePermissions();
    }

    public static boolean parcelMarshallBytebuffer() {
        return FEATURE_FLAGS.parcelMarshallBytebuffer();
    }

    public static boolean perfettoSdkTracing() {
        return FEATURE_FLAGS.perfettoSdkTracing();
    }

    public static boolean perfettoSdkTracingV2() {
        return FEATURE_FLAGS.perfettoSdkTracingV2();
    }

    public static boolean removeAppProfilerPssCollection() {
        return FEATURE_FLAGS.removeAppProfilerPssCollection();
    }

    public static boolean securityStateService() {
        return FEATURE_FLAGS.securityStateService();
    }

    public static boolean stateOfHealthPublic() {
        return FEATURE_FLAGS.stateOfHealthPublic();
    }

    public static boolean storageLifetimeApi() {
        return FEATURE_FLAGS.storageLifetimeApi();
    }

    public static boolean strictModeRestrictedNetwork() {
        return FEATURE_FLAGS.strictModeRestrictedNetwork();
    }

    public static boolean telemetryApisFrameworkInitialization() {
        return FEATURE_FLAGS.telemetryApisFrameworkInitialization();
    }

    public static boolean updateEngineApi() {
        return FEATURE_FLAGS.updateEngineApi();
    }
}
