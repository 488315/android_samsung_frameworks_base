package com.android.internal.hidden_from_bootclasspath.android.os;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADPF_25Q2_METRICS, Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION, Flags.FLAG_ADPF_GRAPHICS_PIPELINE, Flags.FLAG_ADPF_HWUI_GPU, Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST, Flags.FLAG_ADPF_OBTAINVIEW_BOOST, Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY, Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY, Flags.FLAG_ADPF_USE_FMQ_CHANNEL, Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED, Flags.FLAG_ADPF_USE_LOAD_HINTS, Flags.FLAG_ALLOW_CONSENTLESS_BUGREPORT_DELEGATED_CONSENT, Flags.FLAG_ALLOW_PRIVATE_PROFILE, Flags.FLAG_ALLOW_THERMAL_HAL_SKIN_FORECAST, Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS, Flags.FLAG_ALLOW_THERMAL_THRESHOLDS_CALLBACK, Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM, Flags.FLAG_API_FOR_BACKPORTED_FIXES, Flags.FLAG_APP_ZYGOTE_RETRY_START, Flags.FLAG_BATTERY_PART_STATUS_API, Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API, Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND, Flags.FLAG_BINDER_FROZEN_STATE_CHANGE_CALLBACK, Flags.FLAG_CPU_GPU_HEADROOMS, Flags.FLAG_DISABLE_MADVISE_ARTFILE_DEFAULT, Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION, Flags.FLAG_ENABLE_ANGLE_ALLOW_LIST, Flags.FLAG_ENABLE_HAS_BINDERS, Flags.FLAG_FORCE_CONCURRENT_MESSAGE_QUEUE, Flags.FLAG_GET_PRIVATE_SPACE_SETTINGS, Flags.FLAG_IPC_DATA_CACHE_TEST_APIS, Flags.FLAG_MAINLINE_VCN_PLATFORM_API, Flags.FLAG_MATERIAL_COLORS_10_2024, Flags.FLAG_MATERIAL_MOTION_TOKENS, Flags.FLAG_MATERIAL_SHAPE_TOKENS, Flags.FLAG_MESSAGE_QUEUE_FORCE_LEGACY, Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING, Flags.FLAG_MESSAGE_QUEUE_TESTABILITY, Flags.FLAG_NETWORK_TIME_USES_SHARED_MEMORY, Flags.FLAG_ORDERED_BROADCAST_MULTIPLE_PERMISSIONS, Flags.FLAG_PARCEL_MARSHALL_BYTEBUFFER, Flags.FLAG_PERFETTO_SDK_TRACING, Flags.FLAG_PERFETTO_SDK_TRACING_V2, Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION, Flags.FLAG_SECURITY_STATE_SERVICE, Flags.FLAG_STATE_OF_HEALTH_PUBLIC, Flags.FLAG_STORAGE_LIFETIME_API, Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK, Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION, Flags.FLAG_UPDATE_ENGINE_API, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpf25q2Metrics() {
        return getValue(Flags.FLAG_ADPF_25Q2_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpf25q2Metrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfGpuReportActualWorkDuration() {
        return getValue(Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfGpuReportActualWorkDuration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfGraphicsPipeline() {
        return getValue(Flags.FLAG_ADPF_GRAPHICS_PIPELINE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfGraphicsPipeline();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfHwuiGpu() {
        return getValue(Flags.FLAG_ADPF_HWUI_GPU, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfHwuiGpu();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfMeasureDuringInputEventBoost() {
        return getValue(Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfMeasureDuringInputEventBoost();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfObtainviewBoost() {
        return getValue(Flags.FLAG_ADPF_OBTAINVIEW_BOOST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfObtainviewBoost();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfPlatformPowerEfficiency() {
        return getValue(Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfPlatformPowerEfficiency();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfPreferPowerEfficiency() {
        return getValue(Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfPreferPowerEfficiency();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfUseFmqChannel() {
        return getValue(Flags.FLAG_ADPF_USE_FMQ_CHANNEL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfUseFmqChannel();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfUseFmqChannelFixed() {
        return getValue(Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfUseFmqChannelFixed();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfUseLoadHints() {
        return getValue(Flags.FLAG_ADPF_USE_LOAD_HINTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adpfUseLoadHints();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowConsentlessBugreportDelegatedConsent() {
        return getValue(Flags.FLAG_ALLOW_CONSENTLESS_BUGREPORT_DELEGATED_CONSENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowConsentlessBugreportDelegatedConsent();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowPrivateProfile() {
        return getValue(Flags.FLAG_ALLOW_PRIVATE_PROFILE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowPrivateProfile();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowThermalHalSkinForecast() {
        return getValue(Flags.FLAG_ALLOW_THERMAL_HAL_SKIN_FORECAST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowThermalHalSkinForecast();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowThermalHeadroomThresholds() {
        return getValue(Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowThermalHeadroomThresholds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowThermalThresholdsCallback() {
        return getValue(Flags.FLAG_ALLOW_THERMAL_THRESHOLDS_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowThermalThresholdsCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean androidOsBuildVanillaIceCream() {
        return getValue(Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).androidOsBuildVanillaIceCream();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean apiForBackportedFixes() {
        return getValue(Flags.FLAG_API_FOR_BACKPORTED_FIXES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apiForBackportedFixes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean appZygoteRetryStart() {
        return getValue(Flags.FLAG_APP_ZYGOTE_RETRY_START, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appZygoteRetryStart();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batteryPartStatusApi() {
        return getValue(Flags.FLAG_BATTERY_PART_STATUS_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).batteryPartStatusApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batterySaverSupportedCheckApi() {
        return getValue(Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).batterySaverSupportedCheckApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batteryServiceSupportCurrentAdbCommand() {
        return getValue(Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).batteryServiceSupportCurrentAdbCommand();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean binderFrozenStateChangeCallback() {
        return getValue(Flags.FLAG_BINDER_FROZEN_STATE_CHANGE_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).binderFrozenStateChangeCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean cpuGpuHeadrooms() {
        return getValue(Flags.FLAG_CPU_GPU_HEADROOMS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cpuGpuHeadrooms();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean disableMadviseArtfileDefault() {
        return getValue(Flags.FLAG_DISABLE_MADVISE_ARTFILE_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableMadviseArtfileDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean disallowCellularNullCiphersRestriction() {
        return getValue(Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disallowCellularNullCiphersRestriction();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean enableAngleAllowList() {
        return getValue(Flags.FLAG_ENABLE_ANGLE_ALLOW_LIST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAngleAllowList();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean enableHasBinders() {
        return getValue(Flags.FLAG_ENABLE_HAS_BINDERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHasBinders();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean forceConcurrentMessageQueue() {
        return getValue(Flags.FLAG_FORCE_CONCURRENT_MESSAGE_QUEUE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceConcurrentMessageQueue();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean getPrivateSpaceSettings() {
        return getValue(Flags.FLAG_GET_PRIVATE_SPACE_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getPrivateSpaceSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean ipcDataCacheTestApis() {
        return getValue(Flags.FLAG_IPC_DATA_CACHE_TEST_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ipcDataCacheTestApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean mainlineVcnPlatformApi() {
        return getValue(Flags.FLAG_MAINLINE_VCN_PLATFORM_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mainlineVcnPlatformApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean materialColors102024() {
        return getValue(Flags.FLAG_MATERIAL_COLORS_10_2024, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).materialColors102024();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean materialMotionTokens() {
        return getValue(Flags.FLAG_MATERIAL_MOTION_TOKENS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).materialMotionTokens();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean materialShapeTokens() {
        return getValue(Flags.FLAG_MATERIAL_SHAPE_TOKENS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).materialShapeTokens();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean messageQueueForceLegacy() {
        return getValue(Flags.FLAG_MESSAGE_QUEUE_FORCE_LEGACY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).messageQueueForceLegacy();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean messageQueueTailTracking() {
        return getValue(Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).messageQueueTailTracking();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean messageQueueTestability() {
        return getValue(Flags.FLAG_MESSAGE_QUEUE_TESTABILITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).messageQueueTestability();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean networkTimeUsesSharedMemory() {
        return getValue(Flags.FLAG_NETWORK_TIME_USES_SHARED_MEMORY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).networkTimeUsesSharedMemory();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean orderedBroadcastMultiplePermissions() {
        return getValue(Flags.FLAG_ORDERED_BROADCAST_MULTIPLE_PERMISSIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).orderedBroadcastMultiplePermissions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean parcelMarshallBytebuffer() {
        return getValue(Flags.FLAG_PARCEL_MARSHALL_BYTEBUFFER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).parcelMarshallBytebuffer();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean perfettoSdkTracing() {
        return getValue(Flags.FLAG_PERFETTO_SDK_TRACING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoSdkTracing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean perfettoSdkTracingV2() {
        return getValue(Flags.FLAG_PERFETTO_SDK_TRACING_V2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perfettoSdkTracingV2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean removeAppProfilerPssCollection() {
        return getValue(Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeAppProfilerPssCollection();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean securityStateService() {
        return getValue(Flags.FLAG_SECURITY_STATE_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).securityStateService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean stateOfHealthPublic() {
        return getValue(Flags.FLAG_STATE_OF_HEALTH_PUBLIC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stateOfHealthPublic();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean storageLifetimeApi() {
        return getValue(Flags.FLAG_STORAGE_LIFETIME_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).storageLifetimeApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean strictModeRestrictedNetwork() {
        return getValue(Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).strictModeRestrictedNetwork();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean telemetryApisFrameworkInitialization() {
        return getValue(Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telemetryApisFrameworkInitialization();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean updateEngineApi() {
        return getValue(Flags.FLAG_UPDATE_ENGINE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateEngineApi();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADPF_25Q2_METRICS, Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION, Flags.FLAG_ADPF_GRAPHICS_PIPELINE, Flags.FLAG_ADPF_HWUI_GPU, Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST, Flags.FLAG_ADPF_OBTAINVIEW_BOOST, Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY, Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY, Flags.FLAG_ADPF_USE_FMQ_CHANNEL, Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED, Flags.FLAG_ADPF_USE_LOAD_HINTS, Flags.FLAG_ALLOW_CONSENTLESS_BUGREPORT_DELEGATED_CONSENT, Flags.FLAG_ALLOW_PRIVATE_PROFILE, Flags.FLAG_ALLOW_THERMAL_HAL_SKIN_FORECAST, Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS, Flags.FLAG_ALLOW_THERMAL_THRESHOLDS_CALLBACK, Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM, Flags.FLAG_API_FOR_BACKPORTED_FIXES, Flags.FLAG_APP_ZYGOTE_RETRY_START, Flags.FLAG_BATTERY_PART_STATUS_API, Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API, Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND, Flags.FLAG_BINDER_FROZEN_STATE_CHANGE_CALLBACK, Flags.FLAG_CPU_GPU_HEADROOMS, Flags.FLAG_DISABLE_MADVISE_ARTFILE_DEFAULT, Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION, Flags.FLAG_ENABLE_ANGLE_ALLOW_LIST, Flags.FLAG_ENABLE_HAS_BINDERS, Flags.FLAG_FORCE_CONCURRENT_MESSAGE_QUEUE, Flags.FLAG_GET_PRIVATE_SPACE_SETTINGS, Flags.FLAG_IPC_DATA_CACHE_TEST_APIS, Flags.FLAG_MAINLINE_VCN_PLATFORM_API, Flags.FLAG_MATERIAL_COLORS_10_2024, Flags.FLAG_MATERIAL_MOTION_TOKENS, Flags.FLAG_MATERIAL_SHAPE_TOKENS, Flags.FLAG_MESSAGE_QUEUE_FORCE_LEGACY, Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING, Flags.FLAG_MESSAGE_QUEUE_TESTABILITY, Flags.FLAG_NETWORK_TIME_USES_SHARED_MEMORY, Flags.FLAG_ORDERED_BROADCAST_MULTIPLE_PERMISSIONS, Flags.FLAG_PARCEL_MARSHALL_BYTEBUFFER, Flags.FLAG_PERFETTO_SDK_TRACING, Flags.FLAG_PERFETTO_SDK_TRACING_V2, Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION, Flags.FLAG_SECURITY_STATE_SERVICE, Flags.FLAG_STATE_OF_HEALTH_PUBLIC, Flags.FLAG_STORAGE_LIFETIME_API, Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK, Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION, Flags.FLAG_UPDATE_ENGINE_API);
    }
}
