package com.android.internal.hidden_from_bootclasspath.android.companion;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean associationTag() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean companionTransportApis() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean devicePresence() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean newAssociationBuilder() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean ongoingPermSync() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean permSyncUserConsent() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean unpairAssociatedDevice() {
        return false;
    }
}
