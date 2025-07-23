package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean allowRescuePartyFlagResets() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean deprecateFlagsAndSettingsResets() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean enableCrashrecovery() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean recoverabilityDetection() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean refactorCrashrecovery() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean synchronousRebootInRescueParty() {
        return false;
    }
}
