package com.android.server.contextualsearch.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.contextualsearch.flags.FeatureFlags
    public boolean enableExcludePersistentUi() {
        return false;
    }
}
