package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

public interface FeatureFlags {
    boolean batteryUsageStatsByPowerAndScreenState();

    boolean disableSystemServicePowerAttr();

    boolean onewayBatteryStatsService();

    boolean powerMonitorApi();

    boolean streamlinedBatteryStats();

    boolean streamlinedConnectivityBatteryStats();

    boolean streamlinedMiscBatteryStats();
}
