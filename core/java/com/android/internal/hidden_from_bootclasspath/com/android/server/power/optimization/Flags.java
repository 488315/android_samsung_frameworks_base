package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_BATTERY_USAGE_STATS_BY_POWER_AND_SCREEN_STATE = "com.android.server.power.optimization.battery_usage_stats_by_power_and_screen_state";
    public static final String FLAG_DISABLE_SYSTEM_SERVICE_POWER_ATTR = "com.android.server.power.optimization.disable_system_service_power_attr";
    public static final String FLAG_ONEWAY_BATTERY_STATS_SERVICE = "com.android.server.power.optimization.oneway_battery_stats_service";
    public static final String FLAG_POWER_MONITOR_API = "com.android.server.power.optimization.power_monitor_api";
    public static final String FLAG_STREAMLINED_BATTERY_STATS = "com.android.server.power.optimization.streamlined_battery_stats";
    public static final String FLAG_STREAMLINED_CONNECTIVITY_BATTERY_STATS = "com.android.server.power.optimization.streamlined_connectivity_battery_stats";
    public static final String FLAG_STREAMLINED_MISC_BATTERY_STATS = "com.android.server.power.optimization.streamlined_misc_battery_stats";

    public static boolean batteryUsageStatsByPowerAndScreenState() {
        return FEATURE_FLAGS.batteryUsageStatsByPowerAndScreenState();
    }

    public static boolean disableSystemServicePowerAttr() {
        return FEATURE_FLAGS.disableSystemServicePowerAttr();
    }

    public static boolean onewayBatteryStatsService() {
        return FEATURE_FLAGS.onewayBatteryStatsService();
    }

    public static boolean powerMonitorApi() {
        return FEATURE_FLAGS.powerMonitorApi();
    }

    public static boolean streamlinedBatteryStats() {
        return FEATURE_FLAGS.streamlinedBatteryStats();
    }

    public static boolean streamlinedConnectivityBatteryStats() {
        return FEATURE_FLAGS.streamlinedConnectivityBatteryStats();
    }

    public static boolean streamlinedMiscBatteryStats() {
        return FEATURE_FLAGS.streamlinedMiscBatteryStats();
    }
}
