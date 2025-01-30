package com.android.systemui.biometrics.domain.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BiometricModalityKt {
    public static final BiometricModality asBiometricModality(int i) {
        return i != 2 ? i != 8 ? BiometricModality.None : BiometricModality.Face : BiometricModality.Fingerprint;
    }
}
