package com.android.internal.hidden_from_bootclasspath.android.content.pm;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES =
            "android.content.pm.allow_sdk_sandbox_query_intent_activities";
    public static final String FLAG_ARCHIVING = "android.content.pm.archiving";
    public static final String FLAG_ASL_IN_APK_APP_METADATA_SOURCE =
            "android.content.pm.asl_in_apk_app_metadata_source";
    public static final String FLAG_COMPONENT_STATE_CHANGED_METRICS =
            "android.content.pm.component_state_changed_metrics";
    public static final String FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS =
            "android.content.pm.disallow_sdk_libs_to_be_apps";
    public static final String FLAG_EMERGENCY_INSTALL_PERMISSION =
            "android.content.pm.emergency_install_permission";
    public static final String FLAG_ENCODE_APP_INTENT = "android.content.pm.encode_app_intent";
    public static final String FLAG_FIX_DUPLICATED_FLAGS =
            "android.content.pm.fix_duplicated_flags";
    public static final String FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME =
            "android.content.pm.fix_system_apps_first_install_time";
    public static final String FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH =
            "android.content.pm.force_multi_arch_native_libs_match";
    public static final String FLAG_GET_PACKAGE_INFO = "android.content.pm.get_package_info";
    public static final String FLAG_GET_PACKAGE_INFO_WITH_FD =
            "android.content.pm.get_package_info_with_fd";
    public static final String FLAG_GET_PACKAGE_STORAGE_STATS =
            "android.content.pm.get_package_storage_stats";
    public static final String FLAG_GET_RESOLVED_APK_PATH =
            "android.content.pm.get_resolved_apk_path";
    public static final String FLAG_IMPROVE_HOME_APP_BEHAVIOR =
            "android.content.pm.improve_home_app_behavior";
    public static final String FLAG_IMPROVE_INSTALL_DONT_KILL =
            "android.content.pm.improve_install_dont_kill";
    public static final String FLAG_IMPROVE_INSTALL_FREEZE =
            "android.content.pm.improve_install_freeze";
    public static final String FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE =
            "android.content.pm.introduce_media_processing_type";
    public static final String FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION =
            "android.content.pm.lightweight_invisible_label_detection";
    public static final String FLAG_MIN_TARGET_SDK_24 = "android.content.pm.min_target_sdk_24";
    public static final String FLAG_NULLABLE_DATA_DIR = "android.content.pm.nullable_data_dir";
    public static final String FLAG_PACKAGE_RESTART_QUERY_DISABLED_BY_DEFAULT =
            "android.content.pm.package_restart_query_disabled_by_default";
    public static final String FLAG_PROVIDE_INFO_OF_APK_IN_APEX =
            "android.content.pm.provide_info_of_apk_in_apex";
    public static final String FLAG_QUARANTINED_ENABLED = "android.content.pm.quarantined_enabled";
    public static final String FLAG_READ_INSTALL_INFO = "android.content.pm.read_install_info";
    public static final String FLAG_RECOVERABILITY_DETECTION =
            "android.content.pm.recoverability_detection";
    public static final String FLAG_RELATIVE_REFERENCE_INTENT_FILTERS =
            "android.content.pm.relative_reference_intent_filters";
    public static final String FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS =
            "android.content.pm.restrict_nonpreloads_system_shareduids";
    public static final String FLAG_ROLLBACK_LIFETIME = "android.content.pm.rollback_lifetime";
    public static final String FLAG_SDK_LIB_INDEPENDENCE =
            "android.content.pm.sdk_lib_independence";
    public static final String FLAG_SET_PRE_VERIFIED_DOMAINS =
            "android.content.pm.set_pre_verified_domains";
    public static final String FLAG_STAY_STOPPED = "android.content.pm.stay_stopped";
    public static final String FLAG_USE_ART_SERVICE_V2 = "android.content.pm.use_art_service_v2";
    public static final String FLAG_USE_PIA_V2 = "android.content.pm.use_pia_v2";
    public static final String FLAG_WAIT_APPLICATION_KILLED =
            "android.content.pm.wait_application_killed";

    public static boolean allowSdkSandboxQueryIntentActivities() {
        return FEATURE_FLAGS.allowSdkSandboxQueryIntentActivities();
    }

    public static boolean archiving() {
        return FEATURE_FLAGS.archiving();
    }

    public static boolean aslInApkAppMetadataSource() {
        return FEATURE_FLAGS.aslInApkAppMetadataSource();
    }

    public static boolean componentStateChangedMetrics() {
        return FEATURE_FLAGS.componentStateChangedMetrics();
    }

    public static boolean disallowSdkLibsToBeApps() {
        return FEATURE_FLAGS.disallowSdkLibsToBeApps();
    }

    public static boolean emergencyInstallPermission() {
        return FEATURE_FLAGS.emergencyInstallPermission();
    }

    public static boolean encodeAppIntent() {
        return FEATURE_FLAGS.encodeAppIntent();
    }

    public static boolean fixDuplicatedFlags() {
        return FEATURE_FLAGS.fixDuplicatedFlags();
    }

    public static boolean fixSystemAppsFirstInstallTime() {
        return FEATURE_FLAGS.fixSystemAppsFirstInstallTime();
    }

    public static boolean forceMultiArchNativeLibsMatch() {
        return FEATURE_FLAGS.forceMultiArchNativeLibsMatch();
    }

    public static boolean getPackageInfo() {
        return FEATURE_FLAGS.getPackageInfo();
    }

    public static boolean getPackageInfoWithFd() {
        return FEATURE_FLAGS.getPackageInfoWithFd();
    }

    public static boolean getPackageStorageStats() {
        return FEATURE_FLAGS.getPackageStorageStats();
    }

    public static boolean getResolvedApkPath() {
        return FEATURE_FLAGS.getResolvedApkPath();
    }

    public static boolean improveHomeAppBehavior() {
        return FEATURE_FLAGS.improveHomeAppBehavior();
    }

    public static boolean improveInstallDontKill() {
        return FEATURE_FLAGS.improveInstallDontKill();
    }

    public static boolean improveInstallFreeze() {
        return FEATURE_FLAGS.improveInstallFreeze();
    }

    public static boolean introduceMediaProcessingType() {
        return FEATURE_FLAGS.introduceMediaProcessingType();
    }

    public static boolean lightweightInvisibleLabelDetection() {
        return FEATURE_FLAGS.lightweightInvisibleLabelDetection();
    }

    public static boolean minTargetSdk24() {
        return FEATURE_FLAGS.minTargetSdk24();
    }

    public static boolean nullableDataDir() {
        return FEATURE_FLAGS.nullableDataDir();
    }

    public static boolean packageRestartQueryDisabledByDefault() {
        return FEATURE_FLAGS.packageRestartQueryDisabledByDefault();
    }

    public static boolean provideInfoOfApkInApex() {
        return FEATURE_FLAGS.provideInfoOfApkInApex();
    }

    public static boolean quarantinedEnabled() {
        return FEATURE_FLAGS.quarantinedEnabled();
    }

    public static boolean readInstallInfo() {
        return FEATURE_FLAGS.readInstallInfo();
    }

    public static boolean recoverabilityDetection() {
        return FEATURE_FLAGS.recoverabilityDetection();
    }

    public static boolean relativeReferenceIntentFilters() {
        return FEATURE_FLAGS.relativeReferenceIntentFilters();
    }

    public static boolean restrictNonpreloadsSystemShareduids() {
        return FEATURE_FLAGS.restrictNonpreloadsSystemShareduids();
    }

    public static boolean rollbackLifetime() {
        return FEATURE_FLAGS.rollbackLifetime();
    }

    public static boolean sdkLibIndependence() {
        return FEATURE_FLAGS.sdkLibIndependence();
    }

    public static boolean setPreVerifiedDomains() {
        return FEATURE_FLAGS.setPreVerifiedDomains();
    }

    public static boolean stayStopped() {
        return FEATURE_FLAGS.stayStopped();
    }

    public static boolean useArtServiceV2() {
        return FEATURE_FLAGS.useArtServiceV2();
    }

    public static boolean usePiaV2() {
        return FEATURE_FLAGS.usePiaV2();
    }

    public static boolean waitApplicationKilled() {
        return FEATURE_FLAGS.waitApplicationKilled();
    }
}
