package com.android.server.power.feature.flags;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean disableFrozenProcessWakelocks() {
        return false;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableEarlyScreenTimeoutDetector() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableScreenTimeoutPolicyListenerApi() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean frameworkWakelockInfo() {
        return false;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean improveWakelockLatency() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean moveWscLoggingToNotifier() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean perDisplayWakeByTouch() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean policyReasonInDisplayPowerRequest() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean wakelockAttributionViaWorkchain() {
        return true;
    }
}
