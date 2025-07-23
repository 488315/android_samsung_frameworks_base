package com.android.libcore.readonly;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.libcore.readonly.FeatureFlags
    public boolean nativeMetrics() {
        return true;
    }

    @Override // com.android.libcore.readonly.FeatureFlags
    public boolean postCleanupApis() {
        return true;
    }
}
