package com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean deprecateDpmSupervisionApis() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableAppApproval() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionAppService() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionPinRecoveryScreen() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionSettingsScreen() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSyncWithDpm() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableWebContentFiltersScreen() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionApiOnWear() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionManagerApis() {
        return false;
    }
}
