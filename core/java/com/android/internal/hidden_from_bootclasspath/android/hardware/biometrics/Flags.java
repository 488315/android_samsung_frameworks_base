package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT =
            "android.hardware.biometrics.add_key_agreement_crypto_object";
    public static final String FLAG_CUSTOM_BIOMETRIC_PROMPT =
            "android.hardware.biometrics.custom_biometric_prompt";
    public static final String FLAG_GET_OP_ID_CRYPTO_OBJECT =
            "android.hardware.biometrics.get_op_id_crypto_object";
    public static final String FLAG_LAST_AUTHENTICATION_TIME =
            "android.hardware.biometrics.last_authentication_time";
    public static final String FLAG_MANDATORY_BIOMETRICS =
            "android.hardware.biometrics.mandatory_biometrics";

    public static boolean addKeyAgreementCryptoObject() {
        return FEATURE_FLAGS.addKeyAgreementCryptoObject();
    }

    public static boolean customBiometricPrompt() {
        return FEATURE_FLAGS.customBiometricPrompt();
    }

    public static boolean getOpIdCryptoObject() {
        return FEATURE_FLAGS.getOpIdCryptoObject();
    }

    public static boolean lastAuthenticationTime() {
        return FEATURE_FLAGS.lastAuthenticationTime();
    }

    public static boolean mandatoryBiometrics() {
        return FEATURE_FLAGS.mandatoryBiometrics();
    }
}
