package android.chre.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND =
            "android.chre.flags.abort_if_no_context_hub_found";
    public static final String FLAG_BUG_FIX_REDUCE_LOCK_HOLDING_PERIOD =
            "android.chre.flags.bug_fix_reduce_lock_holding_period";
    public static final String FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED =
            "android.chre.flags.context_hub_callback_uuid_enabled";
    public static final String FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS =
            "android.chre.flags.flag_log_nanoapp_load_metrics";
    public static final String FLAG_METRICS_REPORTER_IN_THE_DAEMON =
            "android.chre.flags.metrics_reporter_in_the_daemon";
    public static final String FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART =
            "android.chre.flags.reconnect_host_endpoints_after_hal_restart";
    public static final String FLAG_REDUCE_LOCK_HOLDING_PERIOD =
            "android.chre.flags.reduce_lock_holding_period";
    public static final String FLAG_RELIABLE_MESSAGE = "android.chre.flags.reliable_message";
    public static final String FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE =
            "android.chre.flags.reliable_message_duplicate_detection_service";
    public static final String FLAG_RELIABLE_MESSAGE_IMPLEMENTATION =
            "android.chre.flags.reliable_message_implementation";
    public static final String FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE =
            "android.chre.flags.reliable_message_retry_support_service";
    public static final String FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR =
            "android.chre.flags.reliable_message_test_mode_behavior";
    public static final String FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT =
            "android.chre.flags.remove_ap_wakeup_metric_report_limit";
    public static final String FLAG_WAIT_FOR_PRELOADED_NANOAPP_START =
            "android.chre.flags.wait_for_preloaded_nanoapp_start";

    public static boolean abortIfNoContextHubFound() {
        return FEATURE_FLAGS.abortIfNoContextHubFound();
    }

    public static boolean bugFixReduceLockHoldingPeriod() {
        return FEATURE_FLAGS.bugFixReduceLockHoldingPeriod();
    }

    public static boolean contextHubCallbackUuidEnabled() {
        return FEATURE_FLAGS.contextHubCallbackUuidEnabled();
    }

    public static boolean flagLogNanoappLoadMetrics() {
        return FEATURE_FLAGS.flagLogNanoappLoadMetrics();
    }

    public static boolean metricsReporterInTheDaemon() {
        return FEATURE_FLAGS.metricsReporterInTheDaemon();
    }

    public static boolean reconnectHostEndpointsAfterHalRestart() {
        return FEATURE_FLAGS.reconnectHostEndpointsAfterHalRestart();
    }

    public static boolean reduceLockHoldingPeriod() {
        return FEATURE_FLAGS.reduceLockHoldingPeriod();
    }

    public static boolean reliableMessage() {
        return FEATURE_FLAGS.reliableMessage();
    }

    public static boolean reliableMessageDuplicateDetectionService() {
        return FEATURE_FLAGS.reliableMessageDuplicateDetectionService();
    }

    public static boolean reliableMessageImplementation() {
        return FEATURE_FLAGS.reliableMessageImplementation();
    }

    public static boolean reliableMessageRetrySupportService() {
        return FEATURE_FLAGS.reliableMessageRetrySupportService();
    }

    public static boolean reliableMessageTestModeBehavior() {
        return FEATURE_FLAGS.reliableMessageTestModeBehavior();
    }

    public static boolean removeApWakeupMetricReportLimit() {
        return FEATURE_FLAGS.removeApWakeupMetricReportLimit();
    }

    public static boolean waitForPreloadedNanoappStart() {
        return FEATURE_FLAGS.waitForPreloadedNanoappStart();
    }
}
