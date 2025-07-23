package com.android.internal.hidden_from_bootclasspath.android.net.platform.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean deprecateNetworkPolicyCallback() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean mdnsImprovementFor25q2() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean poweredOffFindingPlatform() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean registerNsdOffloadEngine() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean vpnTypeOemServiceAndLegacy() {
        return false;
    }
}
