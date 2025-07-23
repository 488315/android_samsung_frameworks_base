package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT = "android.hardware.biometrics.add_key_agreement_crypto_object";
    public static final String FLAG_CUSTOM_BIOMETRIC_PROMPT = "android.hardware.biometrics.custom_biometric_prompt";
    public static final String FLAG_EFFECTIVE_USER_BP = "android.hardware.biometrics.effective_user_bp";
    public static final String FLAG_GET_OP_ID_CRYPTO_OBJECT = "android.hardware.biometrics.get_op_id_crypto_object";
    public static final String FLAG_IDENTITY_CHECK_API = "android.hardware.biometrics.identity_check_api";
    public static final String FLAG_MANDATORY_BIOMETRICS = "android.hardware.biometrics.mandatory_biometrics";
    public static final String FLAG_PRIVATE_SPACE_BP = "android.hardware.biometrics.private_space_bp";
    public static final String FLAG_SCREEN_OFF_UNLOCK_UDFPS = "android.hardware.biometrics.screen_off_unlock_udfps";

    public static boolean addKeyAgreementCryptoObject() {
        return FEATURE_FLAGS.addKeyAgreementCryptoObject();
    }

    public static boolean customBiometricPrompt() {
        return FEATURE_FLAGS.customBiometricPrompt();
    }

    public static boolean effectiveUserBp() {
        return FEATURE_FLAGS.effectiveUserBp();
    }

    public static boolean getOpIdCryptoObject() {
        return FEATURE_FLAGS.getOpIdCryptoObject();
    }

    public static boolean identityCheckApi() {
        return FEATURE_FLAGS.identityCheckApi();
    }

    public static boolean mandatoryBiometrics() {
        return FEATURE_FLAGS.mandatoryBiometrics();
    }

    public static boolean privateSpaceBp() {
        return FEATURE_FLAGS.privateSpaceBp();
    }

    public static boolean screenOffUnlockUdfps() {
        return FEATURE_FLAGS.screenOffUnlockUdfps();
    }
}
