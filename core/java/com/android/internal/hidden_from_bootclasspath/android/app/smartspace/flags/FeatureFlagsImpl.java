package com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.FeatureFlags
    public boolean accessSmartspace() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.FeatureFlags
    public boolean remoteViews() {
        return true;
    }
}
