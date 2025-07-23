package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

/* loaded from: classes5.dex */
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
    public boolean effectiveUserBp() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean identityCheckApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean mandatoryBiometrics() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean privateSpaceBp() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean screenOffUnlockUdfps() {
        return true;
    }
}
