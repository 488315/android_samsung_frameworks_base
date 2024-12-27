package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean batteryUsageStatsByPowerAndScreenState() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean disableSystemServicePowerAttr() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean onewayBatteryStatsService() {
        return false;
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
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedMiscBatteryStats() {
        return false;
    }
}
