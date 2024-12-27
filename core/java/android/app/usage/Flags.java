package android.app.usage;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DISABLE_IDLE_CHECK = "android.app.usage.disable_idle_check";
    public static final String FLAG_FILTER_BASED_EVENT_QUERY_API =
            "android.app.usage.filter_based_event_query_api";
    public static final String FLAG_GET_APP_BYTES_BY_DATA_TYPE_API =
            "android.app.usage.get_app_bytes_by_data_type_api";
    public static final String FLAG_REPORT_USAGE_STATS_PERMISSION =
            "android.app.usage.report_usage_stats_permission";
    public static final String FLAG_USER_INTERACTION_TYPE_API =
            "android.app.usage.user_interaction_type_api";
    public static final String FLAG_USE_DEDICATED_HANDLER_THREAD =
            "android.app.usage.use_dedicated_handler_thread";
    public static final String FLAG_USE_PARCELED_LIST = "android.app.usage.use_parceled_list";

    public static boolean disableIdleCheck() {
        return FEATURE_FLAGS.disableIdleCheck();
    }

    public static boolean filterBasedEventQueryApi() {
        return FEATURE_FLAGS.filterBasedEventQueryApi();
    }

    public static boolean getAppBytesByDataTypeApi() {
        return FEATURE_FLAGS.getAppBytesByDataTypeApi();
    }

    public static boolean reportUsageStatsPermission() {
        return FEATURE_FLAGS.reportUsageStatsPermission();
    }

    public static boolean useDedicatedHandlerThread() {
        return FEATURE_FLAGS.useDedicatedHandlerThread();
    }

    public static boolean useParceledList() {
        return FEATURE_FLAGS.useParceledList();
    }

    public static boolean userInteractionTypeApi() {
        return FEATURE_FLAGS.userInteractionTypeApi();
    }
}
