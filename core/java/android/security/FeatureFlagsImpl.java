package android.security;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.security.FeatureFlags
    public boolean asmOptSystemIntoEnforcement() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean binaryTransparencySepolicyHash() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean deprecateFsvSig() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean dumpAttestationVerifications() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean extendVbChainToUpdatedApk() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean fixUnlockedDeviceRequiredKeysV2() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean frpEnforcement() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean fsverityApi() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean reportPrimaryAuthAttempts() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean significantPlaces() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean unlockedStorageApi() {
        return false;
    }
}
