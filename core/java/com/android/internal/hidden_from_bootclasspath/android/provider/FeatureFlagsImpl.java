package com.android.internal.hidden_from_bootclasspath.android.provider;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean a11yStandaloneGestureEnabled() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean backupTasksSettingsScreen() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean systemSettingsDefault() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean userKeys() {
        return true;
    }
}
