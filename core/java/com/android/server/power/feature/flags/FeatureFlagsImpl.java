package com.android.server.power.feature.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableEarlyScreenTimeoutDetector() {
        return true;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean improveWakelockLatency() {
        return false;
    }
}
