package com.android.server.power.feature.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DISABLE_FROZEN_PROCESS_WAKELOCKS = "com.android.server.power.feature.flags.disable_frozen_process_wakelocks";
    public static final String FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR = "com.android.server.power.feature.flags.enable_early_screen_timeout_detector";
    public static final String FLAG_ENABLE_SCREEN_TIMEOUT_POLICY_LISTENER_API = "com.android.server.power.feature.flags.enable_screen_timeout_policy_listener_api";
    public static final String FLAG_FRAMEWORK_WAKELOCK_INFO = "com.android.server.power.feature.flags.framework_wakelock_info";
    public static final String FLAG_IMPROVE_WAKELOCK_LATENCY = "com.android.server.power.feature.flags.improve_wakelock_latency";
    public static final String FLAG_MOVE_WSC_LOGGING_TO_NOTIFIER = "com.android.server.power.feature.flags.move_wsc_logging_to_notifier";
    public static final String FLAG_PER_DISPLAY_WAKE_BY_TOUCH = "com.android.server.power.feature.flags.per_display_wake_by_touch";
    public static final String FLAG_POLICY_REASON_IN_DISPLAY_POWER_REQUEST = "com.android.server.power.feature.flags.policy_reason_in_display_power_request";
    public static final String FLAG_WAKELOCK_ATTRIBUTION_VIA_WORKCHAIN = "com.android.server.power.feature.flags.wakelock_attribution_via_workchain";

    public static boolean disableFrozenProcessWakelocks() {
        return FEATURE_FLAGS.disableFrozenProcessWakelocks();
    }

    public static boolean enableEarlyScreenTimeoutDetector() {
        return FEATURE_FLAGS.enableEarlyScreenTimeoutDetector();
    }

    public static boolean enableScreenTimeoutPolicyListenerApi() {
        return FEATURE_FLAGS.enableScreenTimeoutPolicyListenerApi();
    }

    public static boolean frameworkWakelockInfo() {
        return FEATURE_FLAGS.frameworkWakelockInfo();
    }

    public static boolean improveWakelockLatency() {
        return FEATURE_FLAGS.improveWakelockLatency();
    }

    public static boolean moveWscLoggingToNotifier() {
        return FEATURE_FLAGS.moveWscLoggingToNotifier();
    }

    public static boolean perDisplayWakeByTouch() {
        return FEATURE_FLAGS.perDisplayWakeByTouch();
    }

    public static boolean policyReasonInDisplayPowerRequest() {
        return FEATURE_FLAGS.policyReasonInDisplayPowerRequest();
    }

    public static boolean wakelockAttributionViaWorkchain() {
        return FEATURE_FLAGS.wakelockAttributionViaWorkchain();
    }
}
