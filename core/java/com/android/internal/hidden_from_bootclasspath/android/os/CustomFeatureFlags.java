package com.android.internal.hidden_from_bootclasspath.android.os;

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
                            Flags.FLAG_ADPF_FMQ_EAGER_SEND,
                            Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION,
                            Flags.FLAG_ADPF_HWUI_GPU,
                            Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST,
                            Flags.FLAG_ADPF_OBTAINVIEW_BOOST,
                            Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY,
                            Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY,
                            Flags.FLAG_ADPF_USE_FMQ_CHANNEL,
                            Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED,
                            Flags.FLAG_ALLOW_PRIVATE_PROFILE,
                            Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS,
                            Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM,
                            Flags.FLAG_BATTERY_PART_STATUS_API,
                            Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API,
                            Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND,
                            Flags.FLAG_BUGREPORT_MODE_MAX_VALUE,
                            Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION,
                            Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING,
                            Flags.FLAG_PERFETTO_SDK_TRACING,
                            Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION,
                            Flags.FLAG_SECURITY_STATE_SERVICE,
                            Flags.FLAG_STATE_OF_HEALTH_PUBLIC,
                            Flags.FLAG_STORAGE_LIFETIME_API,
                            Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK,
                            Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfFmqEagerSend() {
        return getValue(
                Flags.FLAG_ADPF_FMQ_EAGER_SEND,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfFmqEagerSend();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfGpuReportActualWorkDuration() {
        return getValue(
                Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfGpuReportActualWorkDuration();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfHwuiGpu() {
        return getValue(
                Flags.FLAG_ADPF_HWUI_GPU,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfHwuiGpu();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfMeasureDuringInputEventBoost() {
        return getValue(
                Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfMeasureDuringInputEventBoost();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfObtainviewBoost() {
        return getValue(
                Flags.FLAG_ADPF_OBTAINVIEW_BOOST,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfObtainviewBoost();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfPlatformPowerEfficiency() {
        return getValue(
                Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfPlatformPowerEfficiency();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfPreferPowerEfficiency() {
        return getValue(
                Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfPreferPowerEfficiency();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfUseFmqChannel() {
        return getValue(
                Flags.FLAG_ADPF_USE_FMQ_CHANNEL,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfUseFmqChannel();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean adpfUseFmqChannelFixed() {
        return getValue(
                Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).adpfUseFmqChannelFixed();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowPrivateProfile() {
        return getValue(
                Flags.FLAG_ALLOW_PRIVATE_PROFILE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowPrivateProfile();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean allowThermalHeadroomThresholds() {
        return getValue(
                Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowThermalHeadroomThresholds();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean androidOsBuildVanillaIceCream() {
        return getValue(
                Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).androidOsBuildVanillaIceCream();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batteryPartStatusApi() {
        return getValue(
                Flags.FLAG_BATTERY_PART_STATUS_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).batteryPartStatusApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batterySaverSupportedCheckApi() {
        return getValue(
                Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).batterySaverSupportedCheckApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean batteryServiceSupportCurrentAdbCommand() {
        return getValue(
                Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).batteryServiceSupportCurrentAdbCommand();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean bugreportModeMaxValue() {
        return getValue(
                Flags.FLAG_BUGREPORT_MODE_MAX_VALUE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).bugreportModeMaxValue();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean disallowCellularNullCiphersRestriction() {
        return getValue(
                Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disallowCellularNullCiphersRestriction();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean messageQueueTailTracking() {
        return getValue(
                Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).messageQueueTailTracking();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean perfettoSdkTracing() {
        return getValue(
                Flags.FLAG_PERFETTO_SDK_TRACING,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).perfettoSdkTracing();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean removeAppProfilerPssCollection() {
        return getValue(
                Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removeAppProfilerPssCollection();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean securityStateService() {
        return getValue(
                Flags.FLAG_SECURITY_STATE_SERVICE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).securityStateService();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean stateOfHealthPublic() {
        return getValue(
                Flags.FLAG_STATE_OF_HEALTH_PUBLIC,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).stateOfHealthPublic();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean storageLifetimeApi() {
        return getValue(
                Flags.FLAG_STORAGE_LIFETIME_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).storageLifetimeApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean strictModeRestrictedNetwork() {
        return getValue(
                Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).strictModeRestrictedNetwork();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.os.FeatureFlags
    public boolean telemetryApisFrameworkInitialization() {
        return getValue(
                Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.os.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).telemetryApisFrameworkInitialization();
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
                Flags.FLAG_ADPF_FMQ_EAGER_SEND,
                Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION,
                Flags.FLAG_ADPF_HWUI_GPU,
                Flags.FLAG_ADPF_MEASURE_DURING_INPUT_EVENT_BOOST,
                Flags.FLAG_ADPF_OBTAINVIEW_BOOST,
                Flags.FLAG_ADPF_PLATFORM_POWER_EFFICIENCY,
                Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY,
                Flags.FLAG_ADPF_USE_FMQ_CHANNEL,
                Flags.FLAG_ADPF_USE_FMQ_CHANNEL_FIXED,
                Flags.FLAG_ALLOW_PRIVATE_PROFILE,
                Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS,
                Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM,
                Flags.FLAG_BATTERY_PART_STATUS_API,
                Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API,
                Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND,
                Flags.FLAG_BUGREPORT_MODE_MAX_VALUE,
                Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION,
                Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING,
                Flags.FLAG_PERFETTO_SDK_TRACING,
                Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION,
                Flags.FLAG_SECURITY_STATE_SERVICE,
                Flags.FLAG_STATE_OF_HEALTH_PUBLIC,
                Flags.FLAG_STORAGE_LIFETIME_API,
                Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK,
                Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION);
    }
}
