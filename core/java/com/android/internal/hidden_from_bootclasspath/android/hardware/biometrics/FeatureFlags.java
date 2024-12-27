package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

public interface FeatureFlags {
    boolean addKeyAgreementCryptoObject();

    boolean customBiometricPrompt();

    boolean getOpIdCryptoObject();

    boolean lastAuthenticationTime();

    boolean mandatoryBiometrics();
}
