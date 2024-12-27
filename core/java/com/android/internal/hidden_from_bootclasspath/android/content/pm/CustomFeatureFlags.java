package com.android.internal.hidden_from_bootclasspath.android.content.pm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES,
                            Flags.FLAG_ARCHIVING,
                            Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE,
                            Flags.FLAG_COMPONENT_STATE_CHANGED_METRICS,
                            Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS,
                            Flags.FLAG_EMERGENCY_INSTALL_PERMISSION,
                            Flags.FLAG_ENCODE_APP_INTENT,
                            Flags.FLAG_FIX_DUPLICATED_FLAGS,
                            Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME,
                            Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH,
                            Flags.FLAG_GET_PACKAGE_INFO,
                            Flags.FLAG_GET_PACKAGE_INFO_WITH_FD,
                            Flags.FLAG_GET_PACKAGE_STORAGE_STATS,
                            Flags.FLAG_GET_RESOLVED_APK_PATH,
                            Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR,
                            Flags.FLAG_IMPROVE_INSTALL_DONT_KILL,
                            Flags.FLAG_IMPROVE_INSTALL_FREEZE,
                            Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE,
                            Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION,
                            Flags.FLAG_MIN_TARGET_SDK_24,
                            Flags.FLAG_NULLABLE_DATA_DIR,
                            Flags.FLAG_PACKAGE_RESTART_QUERY_DISABLED_BY_DEFAULT,
                            Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX,
                            Flags.FLAG_QUARANTINED_ENABLED,
                            Flags.FLAG_READ_INSTALL_INFO,
                            Flags.FLAG_RECOVERABILITY_DETECTION,
                            Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS,
                            Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS,
                            Flags.FLAG_ROLLBACK_LIFETIME,
                            Flags.FLAG_SDK_LIB_INDEPENDENCE,
                            Flags.FLAG_SET_PRE_VERIFIED_DOMAINS,
                            Flags.FLAG_STAY_STOPPED,
                            Flags.FLAG_USE_ART_SERVICE_V2,
                            Flags.FLAG_USE_PIA_V2,
                            Flags.FLAG_WAIT_APPLICATION_KILLED,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean allowSdkSandboxQueryIntentActivities() {
        return getValue(
                Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowSdkSandboxQueryIntentActivities();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean archiving() {
        return getValue(
                Flags.FLAG_ARCHIVING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).archiving();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean aslInApkAppMetadataSource() {
        return getValue(
                Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).aslInApkAppMetadataSource();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean componentStateChangedMetrics() {
        return getValue(
                Flags.FLAG_COMPONENT_STATE_CHANGED_METRICS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).componentStateChangedMetrics();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean disallowSdkLibsToBeApps() {
        return getValue(
                Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disallowSdkLibsToBeApps();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean emergencyInstallPermission() {
        return getValue(
                Flags.FLAG_EMERGENCY_INSTALL_PERMISSION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).emergencyInstallPermission();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean encodeAppIntent() {
        return getValue(
                Flags.FLAG_ENCODE_APP_INTENT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).encodeAppIntent();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean fixDuplicatedFlags() {
        return getValue(
                Flags.FLAG_FIX_DUPLICATED_FLAGS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixDuplicatedFlags();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean fixSystemAppsFirstInstallTime() {
        return getValue(
                Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixSystemAppsFirstInstallTime();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean forceMultiArchNativeLibsMatch() {
        return getValue(
                Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).forceMultiArchNativeLibsMatch();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean getPackageInfo() {
        return getValue(
                Flags.FLAG_GET_PACKAGE_INFO,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getPackageInfo();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean getPackageInfoWithFd() {
        return getValue(
                Flags.FLAG_GET_PACKAGE_INFO_WITH_FD,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getPackageInfoWithFd();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean getPackageStorageStats() {
        return getValue(
                Flags.FLAG_GET_PACKAGE_STORAGE_STATS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getPackageStorageStats();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean getResolvedApkPath() {
        return getValue(
                Flags.FLAG_GET_RESOLVED_APK_PATH,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getResolvedApkPath();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean improveHomeAppBehavior() {
        return getValue(
                Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).improveHomeAppBehavior();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean improveInstallDontKill() {
        return getValue(
                Flags.FLAG_IMPROVE_INSTALL_DONT_KILL,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).improveInstallDontKill();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean improveInstallFreeze() {
        return getValue(
                Flags.FLAG_IMPROVE_INSTALL_FREEZE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).improveInstallFreeze();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean introduceMediaProcessingType() {
        return getValue(
                Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).introduceMediaProcessingType();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean lightweightInvisibleLabelDetection() {
        return getValue(
                Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).lightweightInvisibleLabelDetection();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean minTargetSdk24() {
        return getValue(
                Flags.FLAG_MIN_TARGET_SDK_24,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).minTargetSdk24();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean nullableDataDir() {
        return getValue(
                Flags.FLAG_NULLABLE_DATA_DIR,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).nullableDataDir();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean packageRestartQueryDisabledByDefault() {
        return getValue(
                Flags.FLAG_PACKAGE_RESTART_QUERY_DISABLED_BY_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).packageRestartQueryDisabledByDefault();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean provideInfoOfApkInApex() {
        return getValue(
                Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).provideInfoOfApkInApex();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean quarantinedEnabled() {
        return getValue(
                Flags.FLAG_QUARANTINED_ENABLED,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).quarantinedEnabled();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean readInstallInfo() {
        return getValue(
                Flags.FLAG_READ_INSTALL_INFO,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).readInstallInfo();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean recoverabilityDetection() {
        return getValue(
                Flags.FLAG_RECOVERABILITY_DETECTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).recoverabilityDetection();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean relativeReferenceIntentFilters() {
        return getValue(
                Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).relativeReferenceIntentFilters();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean restrictNonpreloadsSystemShareduids() {
        return getValue(
                Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).restrictNonpreloadsSystemShareduids();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean rollbackLifetime() {
        return getValue(
                Flags.FLAG_ROLLBACK_LIFETIME,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).rollbackLifetime();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean sdkLibIndependence() {
        return getValue(
                Flags.FLAG_SDK_LIB_INDEPENDENCE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).sdkLibIndependence();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean setPreVerifiedDomains() {
        return getValue(
                Flags.FLAG_SET_PRE_VERIFIED_DOMAINS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setPreVerifiedDomains();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean stayStopped() {
        return getValue(
                Flags.FLAG_STAY_STOPPED,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).stayStopped();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean useArtServiceV2() {
        return getValue(
                Flags.FLAG_USE_ART_SERVICE_V2,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useArtServiceV2();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean usePiaV2() {
        return getValue(
                Flags.FLAG_USE_PIA_V2,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).usePiaV2();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags
    public boolean waitApplicationKilled() {
        return getValue(
                Flags.FLAG_WAIT_APPLICATION_KILLED,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.content.pm.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).waitApplicationKilled();
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
        return Arrays.asList(
                Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES,
                Flags.FLAG_ARCHIVING,
                Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE,
                Flags.FLAG_COMPONENT_STATE_CHANGED_METRICS,
                Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS,
                Flags.FLAG_EMERGENCY_INSTALL_PERMISSION,
                Flags.FLAG_ENCODE_APP_INTENT,
                Flags.FLAG_FIX_DUPLICATED_FLAGS,
                Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME,
                Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH,
                Flags.FLAG_GET_PACKAGE_INFO,
                Flags.FLAG_GET_PACKAGE_INFO_WITH_FD,
                Flags.FLAG_GET_PACKAGE_STORAGE_STATS,
                Flags.FLAG_GET_RESOLVED_APK_PATH,
                Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR,
                Flags.FLAG_IMPROVE_INSTALL_DONT_KILL,
                Flags.FLAG_IMPROVE_INSTALL_FREEZE,
                Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE,
                Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION,
                Flags.FLAG_MIN_TARGET_SDK_24,
                Flags.FLAG_NULLABLE_DATA_DIR,
                Flags.FLAG_PACKAGE_RESTART_QUERY_DISABLED_BY_DEFAULT,
                Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX,
                Flags.FLAG_QUARANTINED_ENABLED,
                Flags.FLAG_READ_INSTALL_INFO,
                Flags.FLAG_RECOVERABILITY_DETECTION,
                Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS,
                Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS,
                Flags.FLAG_ROLLBACK_LIFETIME,
                Flags.FLAG_SDK_LIB_INDEPENDENCE,
                Flags.FLAG_SET_PRE_VERIFIED_DOMAINS,
                Flags.FLAG_STAY_STOPPED,
                Flags.FLAG_USE_ART_SERVICE_V2,
                Flags.FLAG_USE_PIA_V2,
                Flags.FLAG_WAIT_APPLICATION_KILLED);
    }
}
