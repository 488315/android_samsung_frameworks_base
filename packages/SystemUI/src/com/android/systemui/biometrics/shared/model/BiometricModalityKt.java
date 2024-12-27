package com.android.systemui.biometrics.shared.model;

public abstract class BiometricModalityKt {
    public static final BiometricModality asBiometricModality(int i) {
        return i != 2 ? i != 8 ? BiometricModality.None : BiometricModality.Face : BiometricModality.Fingerprint;
    }
}
