package com.android.internal.os;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.os.FeatureFlags
    public boolean enableApacheHttpLegacyPreload() {
        return true;
    }
}
