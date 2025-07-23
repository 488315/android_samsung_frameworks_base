package com.android.internal.os;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.os.FeatureFlags
    public boolean applicationSharedMemoryEnabled() {
        return true;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean debugStoreEnabled() {
        return true;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean enableApacheHttpLegacyPreload() {
        return true;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean enableMediaAndLocationPreload() {
        return true;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRo1() {
        return false;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRo2() {
        return false;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRw1() {
        return false;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRw2() {
        return false;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean useTransactionCodesForUnknownMethods() {
        return true;
    }
}
