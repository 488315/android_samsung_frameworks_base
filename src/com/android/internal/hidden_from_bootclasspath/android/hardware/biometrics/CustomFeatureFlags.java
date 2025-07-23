package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT, Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT, Flags.FLAG_EFFECTIVE_USER_BP, Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT, Flags.FLAG_IDENTITY_CHECK_API, Flags.FLAG_MANDATORY_BIOMETRICS, Flags.FLAG_PRIVATE_SPACE_BP, Flags.FLAG_SCREEN_OFF_UNLOCK_UDFPS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean addKeyAgreementCryptoObject() {
        return getValue(Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addKeyAgreementCryptoObject();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean customBiometricPrompt() {
        return getValue(Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).customBiometricPrompt();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean effectiveUserBp() {
        return getValue(Flags.FLAG_EFFECTIVE_USER_BP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).effectiveUserBp();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return getValue(Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getOpIdCryptoObject();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean identityCheckApi() {
        return getValue(Flags.FLAG_IDENTITY_CHECK_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).identityCheckApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean mandatoryBiometrics() {
        return getValue(Flags.FLAG_MANDATORY_BIOMETRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mandatoryBiometrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean privateSpaceBp() {
        return getValue(Flags.FLAG_PRIVATE_SPACE_BP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).privateSpaceBp();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean screenOffUnlockUdfps() {
        return getValue(Flags.FLAG_SCREEN_OFF_UNLOCK_UDFPS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).screenOffUnlockUdfps();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT, Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT, Flags.FLAG_EFFECTIVE_USER_BP, Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT, Flags.FLAG_IDENTITY_CHECK_API, Flags.FLAG_MANDATORY_BIOMETRICS, Flags.FLAG_PRIVATE_SPACE_BP, Flags.FLAG_SCREEN_OFF_UNLOCK_UDFPS);
    }
}
