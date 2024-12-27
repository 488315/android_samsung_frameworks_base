package com.android.internal.hidden_from_bootclasspath.android.net.platform.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean ipsecTransformState() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean poweredOffFindingPlatform() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean registerNsdOffloadEngine() {
        return false;
    }
}
