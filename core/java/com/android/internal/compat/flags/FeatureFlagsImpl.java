package com.android.internal.compat.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.compat.flags.FeatureFlags
    public boolean skipOldAndDisabledCompatLogging() {
        return true;
    }
}
