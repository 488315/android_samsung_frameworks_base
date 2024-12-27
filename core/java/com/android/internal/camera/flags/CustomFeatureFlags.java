package com.android.internal.camera.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ANALYTICS_24Q3, Flags.FLAG_CACHE_PERMISSION_SERVICES, Flags.FLAG_CALCULATE_PERF_OVERRIDE_DURING_SESSION_SUPPORT, Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, Flags.FLAG_CAMERA_DEVICE_SETUP, Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, Flags.FLAG_CAMERA_HSUM_PERMISSION, Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, Flags.FLAG_CHECK_SESSION_SUPPORT_BEFORE_SESSION_CHAR, Flags.FLAG_CONCERT_MODE, Flags.FLAG_CONCERT_MODE_API, Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION, Flags.FLAG_EXTENSION_10_BIT, Flags.FLAG_FEATURE_COMBINATION_QUERY, Flags.FLAG_INJECT_SESSION_PARAMS, Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE, Flags.FLAG_LOG_ULTRAWIDE_USAGE, Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE, Flags.FLAG_MULTI_RES_RAW_REPROCESSING, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, Flags.FLAG_REALTIME_PRIORITY_BUMP, Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, Flags.FLAG_SESSION_HAL_BUF_MANAGER, Flags.FLAG_SINGLE_THREAD_EXECUTOR, Flags.FLAG_SURFACE_IPC, Flags.FLAG_SURFACE_LEAK_FIX, Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION, Flags.FLAG_USE_SYSTEM_API_FOR_VNDK_VERSION, Flags.FLAG_WATCH_FOREGROUND_CHANGES, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean analytics24q3() {
        return getValue(Flags.FLAG_ANALYTICS_24Q3, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).analytics24q3();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cachePermissionServices() {
        return getValue(Flags.FLAG_CACHE_PERMISSION_SERVICES, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cachePermissionServices();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean calculatePerfOverrideDuringSessionSupport() {
        return getValue(Flags.FLAG_CALCULATE_PERF_OVERRIDE_DURING_SESSION_SUPPORT, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).calculatePerfOverrideDuringSessionSupport();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraAeModeLowLightBoost() {
        return getValue(Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraAeModeLowLightBoost();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraDeviceSetup() {
        return getValue(Flags.FLAG_CAMERA_DEVICE_SETUP, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraDeviceSetup();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraExtensionsCharacteristicsGet() {
        return getValue(Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraExtensionsCharacteristicsGet();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraHsumPermission() {
        return getValue(Flags.FLAG_CAMERA_HSUM_PERMISSION, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraHsumPermission();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraManualFlashStrengthControl() {
        return getValue(Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraManualFlashStrengthControl();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraPrivacyAllowlist() {
        return getValue(Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraPrivacyAllowlist();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean checkSessionSupportBeforeSessionChar() {
        return getValue(Flags.FLAG_CHECK_SESSION_SUPPORT_BEFORE_SESSION_CHAR, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkSessionSupportBeforeSessionChar();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean concertMode() {
        return getValue(Flags.FLAG_CONCERT_MODE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concertMode();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean concertModeApi() {
        return getValue(Flags.FLAG_CONCERT_MODE_API, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concertModeApi();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean delayLazyHalInstantiation() {
        return getValue(Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delayLazyHalInstantiation();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean extension10Bit() {
        return getValue(Flags.FLAG_EXTENSION_10_BIT, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extension10Bit();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean featureCombinationQuery() {
        return getValue(Flags.FLAG_FEATURE_COMBINATION_QUERY, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).featureCombinationQuery();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean injectSessionParams() {
        return getValue(Flags.FLAG_INJECT_SESSION_PARAMS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).injectSessionParams();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean lazyAidlWaitForService() {
        return getValue(Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).lazyAidlWaitForService();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean logUltrawideUsage() {
        return getValue(Flags.FLAG_LOG_ULTRAWIDE_USAGE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).logUltrawideUsage();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean logZoomOverrideUsage() {
        return getValue(Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).logZoomOverrideUsage();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiResRawReprocessing() {
        return getValue(Flags.FLAG_MULTI_RES_RAW_REPROCESSING, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiResRawReprocessing();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiresolutionImagereaderUsageConfig() {
        return getValue(Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiresolutionImagereaderUsageConfig();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean realtimePriorityBump() {
        return getValue(Flags.FLAG_REALTIME_PRIORITY_BUMP, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).realtimePriorityBump();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean returnBuffersOutsideLocks() {
        return getValue(Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).returnBuffersOutsideLocks();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean sessionHalBufManager() {
        return getValue(Flags.FLAG_SESSION_HAL_BUF_MANAGER, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sessionHalBufManager();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean singleThreadExecutor() {
        return getValue(Flags.FLAG_SINGLE_THREAD_EXECUTOR, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).singleThreadExecutor();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean surfaceIpc() {
        return getValue(Flags.FLAG_SURFACE_IPC, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).surfaceIpc();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean surfaceLeakFix() {
        return getValue(Flags.FLAG_SURFACE_LEAK_FIX, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).surfaceLeakFix();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean useRoBoardApiLevelForVndkVersion() {
        return getValue(Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useRoBoardApiLevelForVndkVersion();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean useSystemApiForVndkVersion() {
        return getValue(Flags.FLAG_USE_SYSTEM_API_FOR_VNDK_VERSION, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useSystemApiForVndkVersion();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean watchForegroundChanges() {
        return getValue(Flags.FLAG_WATCH_FOREGROUND_CHANGES, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).watchForegroundChanges();
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
        return Arrays.asList(Flags.FLAG_ANALYTICS_24Q3, Flags.FLAG_CACHE_PERMISSION_SERVICES, Flags.FLAG_CALCULATE_PERF_OVERRIDE_DURING_SESSION_SUPPORT, Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, Flags.FLAG_CAMERA_DEVICE_SETUP, Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, Flags.FLAG_CAMERA_HSUM_PERMISSION, Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, Flags.FLAG_CHECK_SESSION_SUPPORT_BEFORE_SESSION_CHAR, Flags.FLAG_CONCERT_MODE, Flags.FLAG_CONCERT_MODE_API, Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION, Flags.FLAG_EXTENSION_10_BIT, Flags.FLAG_FEATURE_COMBINATION_QUERY, Flags.FLAG_INJECT_SESSION_PARAMS, Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE, Flags.FLAG_LOG_ULTRAWIDE_USAGE, Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE, Flags.FLAG_MULTI_RES_RAW_REPROCESSING, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, Flags.FLAG_REALTIME_PRIORITY_BUMP, Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, Flags.FLAG_SESSION_HAL_BUF_MANAGER, Flags.FLAG_SINGLE_THREAD_EXECUTOR, Flags.FLAG_SURFACE_IPC, Flags.FLAG_SURFACE_LEAK_FIX, Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION, Flags.FLAG_USE_SYSTEM_API_FOR_VNDK_VERSION, Flags.FLAG_WATCH_FOREGROUND_CHANGES);
    }
}
