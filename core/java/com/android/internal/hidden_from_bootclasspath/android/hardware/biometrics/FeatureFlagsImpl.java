package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean addKeyAgreementCryptoObject() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean customBiometricPrompt() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean lastAuthenticationTime() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean mandatoryBiometrics() {
        return true;
    }
}
