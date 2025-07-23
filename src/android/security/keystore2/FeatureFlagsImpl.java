package android.security.keystore2;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.security.keystore2.FeatureFlags
    public boolean attestModules() {
        return true;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean disableLegacyKeystoreGet() {
        return false;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean disableLegacyKeystorePutV2() {
        return false;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean importPreviouslyEmulatedKeys() {
        return false;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean useBlobStateColumn() {
        return true;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean walDbJournalmodeV3() {
        return false;
    }
}
