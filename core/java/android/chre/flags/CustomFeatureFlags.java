package android.chre.flags;

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
                            Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND,
                            Flags.FLAG_BUG_FIX_REDUCE_LOCK_HOLDING_PERIOD,
                            Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED,
                            Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS,
                            Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON,
                            Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART,
                            Flags.FLAG_REDUCE_LOCK_HOLDING_PERIOD,
                            Flags.FLAG_RELIABLE_MESSAGE,
                            Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE,
                            Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION,
                            Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE,
                            Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR,
                            Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT,
                            Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean abortIfNoContextHubFound() {
        return getValue(
                Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).abortIfNoContextHubFound();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixReduceLockHoldingPeriod() {
        return getValue(
                Flags.FLAG_BUG_FIX_REDUCE_LOCK_HOLDING_PERIOD,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).bugFixReduceLockHoldingPeriod();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean contextHubCallbackUuidEnabled() {
        return getValue(
                Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).contextHubCallbackUuidEnabled();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean flagLogNanoappLoadMetrics() {
        return getValue(
                Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).flagLogNanoappLoadMetrics();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean metricsReporterInTheDaemon() {
        return getValue(
                Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).metricsReporterInTheDaemon();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reconnectHostEndpointsAfterHalRestart() {
        return getValue(
                Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reconnectHostEndpointsAfterHalRestart();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reduceLockHoldingPeriod() {
        return getValue(
                Flags.FLAG_REDUCE_LOCK_HOLDING_PERIOD,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reduceLockHoldingPeriod();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return getValue(
                Flags.FLAG_RELIABLE_MESSAGE,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reliableMessage();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageDuplicateDetectionService() {
        return getValue(
                Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reliableMessageDuplicateDetectionService();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageImplementation() {
        return getValue(
                Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reliableMessageImplementation();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageRetrySupportService() {
        return getValue(
                Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reliableMessageRetrySupportService();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageTestModeBehavior() {
        return getValue(
                Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reliableMessageTestModeBehavior();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeApWakeupMetricReportLimit() {
        return getValue(
                Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removeApWakeupMetricReportLimit();
                    }
                });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean waitForPreloadedNanoappStart() {
        return getValue(
                Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START,
                new Predicate() { // from class:
                    // android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).waitForPreloadedNanoappStart();
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
                Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND,
                Flags.FLAG_BUG_FIX_REDUCE_LOCK_HOLDING_PERIOD,
                Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED,
                Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS,
                Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON,
                Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART,
                Flags.FLAG_REDUCE_LOCK_HOLDING_PERIOD,
                Flags.FLAG_RELIABLE_MESSAGE,
                Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE,
                Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION,
                Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE,
                Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR,
                Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT,
                Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START);
    }
}
