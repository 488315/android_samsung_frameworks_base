package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean accumulateBatteryUsageStats() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean addBatteryUsageStatsSliceAtom() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean batteryStatsScreenStateEvent() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean batteryUsageStatsByPowerAndScreenState() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean disableCompositeBatteryUsageStatsAtoms() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean disableSystemServicePowerAttr() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean extendedBatteryHistoryCompressionEnabled() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean extendedBatteryHistoryContinuousCollectionEnabled() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean onewayBatteryStatsService() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean powerMonitorApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedBatteryStats() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedConnectivityBatteryStats() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedMiscBatteryStats() {
        return true;
    }
}
