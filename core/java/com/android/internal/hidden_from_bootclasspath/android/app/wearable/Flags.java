package com.android.internal.hidden_from_bootclasspath.android.app.wearable;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_DATA_REQUEST_OBSERVER_API =
            "android.app.wearable.enable_data_request_observer_api";
    public static final String FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API =
            "android.app.wearable.enable_hotword_wearable_sensing_api";
    public static final String FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API =
            "android.app.wearable.enable_provide_wearable_connection_api";
    public static final String FLAG_ENABLE_RESTART_WSS_PROCESS =
            "android.app.wearable.enable_restart_wss_process";
    public static final String FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE =
            "android.app.wearable.enable_unsupported_operation_status_code";

    public static boolean enableDataRequestObserverApi() {
        return FEATURE_FLAGS.enableDataRequestObserverApi();
    }

    public static boolean enableHotwordWearableSensingApi() {
        return FEATURE_FLAGS.enableHotwordWearableSensingApi();
    }

    public static boolean enableProvideWearableConnectionApi() {
        return FEATURE_FLAGS.enableProvideWearableConnectionApi();
    }

    public static boolean enableRestartWssProcess() {
        return FEATURE_FLAGS.enableRestartWssProcess();
    }

    public static boolean enableUnsupportedOperationStatusCode() {
        return FEATURE_FLAGS.enableUnsupportedOperationStatusCode();
    }
}
