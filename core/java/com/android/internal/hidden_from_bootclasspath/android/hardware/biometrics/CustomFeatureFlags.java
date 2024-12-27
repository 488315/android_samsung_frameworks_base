package com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT,
                            Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT,
                            Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT,
                            Flags.FLAG_LAST_AUTHENTICATION_TIME,
                            Flags.FLAG_MANDATORY_BIOMETRICS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean addKeyAgreementCryptoObject() {
        return getValue(
                Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addKeyAgreementCryptoObject();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean customBiometricPrompt() {
        return getValue(
                Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).customBiometricPrompt();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return getValue(
                Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getOpIdCryptoObject();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean lastAuthenticationTime() {
        return getValue(
                Flags.FLAG_LAST_AUTHENTICATION_TIME,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).lastAuthenticationTime();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.FeatureFlags
    public boolean mandatoryBiometrics() {
        return getValue(
                Flags.FLAG_MANDATORY_BIOMETRICS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).mandatoryBiometrics();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT,
                Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT,
                Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT,
                Flags.FLAG_LAST_AUTHENTICATION_TIME,
                Flags.FLAG_MANDATORY_BIOMETRICS);
    }
}
