package com.android.systemui.biometrics.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BiometricModalityKt {
    public static final BiometricModality asBiometricModality(int i) {
        return i != 2 ? i != 8 ? BiometricModality.None : BiometricModality.Face : BiometricModality.Fingerprint;
    }
}
