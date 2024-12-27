package com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadEnabledPlatform() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadUserRestrictionEnabled() {
        return false;
    }
}
