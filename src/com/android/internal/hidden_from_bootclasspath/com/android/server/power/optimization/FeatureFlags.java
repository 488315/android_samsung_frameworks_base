package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean accumulateBatteryUsageStats();

    boolean addBatteryUsageStatsSliceAtom();

    boolean batteryStatsScreenStateEvent();

    boolean batteryUsageStatsByPowerAndScreenState();

    boolean disableCompositeBatteryUsageStatsAtoms();

    boolean disableSystemServicePowerAttr();

    boolean extendedBatteryHistoryCompressionEnabled();

    boolean extendedBatteryHistoryContinuousCollectionEnabled();

    boolean onewayBatteryStatsService();

    boolean powerMonitorApi();

    boolean streamlinedBatteryStats();

    boolean streamlinedConnectivityBatteryStats();

    boolean streamlinedMiscBatteryStats();
}
