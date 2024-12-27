package com.android.internal.foldables.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldGracePeriodEnabled() {
        return true;
    }

    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldLockSettingEnabled() {
        return true;
    }
}
