package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class BiometricModality {
    public static final /* synthetic */ BiometricModality[] $VALUES;
    public static final BiometricModality Face;
    public static final BiometricModality Fingerprint;
    public static final BiometricModality None;

    static {
        BiometricModality biometricModality = new BiometricModality("None", 0);
        None = biometricModality;
        BiometricModality biometricModality2 = new BiometricModality("Fingerprint", 1);
        Fingerprint = biometricModality2;
        BiometricModality biometricModality3 = new BiometricModality("Face", 2);
        Face = biometricModality3;
        BiometricModality[] biometricModalityArr = {biometricModality, biometricModality2, biometricModality3};
        $VALUES = biometricModalityArr;
        EnumEntriesKt.enumEntries(biometricModalityArr);
    }

    private BiometricModality(String str, int i) {
    }

    public static BiometricModality valueOf(String str) {
        return (BiometricModality) Enum.valueOf(BiometricModality.class, str);
    }

    public static BiometricModality[] values() {
        return (BiometricModality[]) $VALUES.clone();
    }
}
