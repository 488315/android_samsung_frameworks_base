package com.android.server.feature.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.feature.flags.FeatureFlags
    public boolean enableReadDropboxPermission() {
        return true;
    }
}
