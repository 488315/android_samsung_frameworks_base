package com.android.systemui.keyguard.data.repository;

import kotlin.enums.EnumEntriesKt;

public final class BiometricType {
    public static final /* synthetic */ BiometricType[] $VALUES;
    public static final BiometricType FACE;
    public static final BiometricType REAR_FINGERPRINT;
    public static final BiometricType SIDE_FINGERPRINT;
    public static final BiometricType UNDER_DISPLAY_FINGERPRINT;
    public static final BiometricType UNKNOWN;
    private final boolean isFingerprint;

    static {
        BiometricType biometricType = new BiometricType("UNKNOWN", 0, false);
        UNKNOWN = biometricType;
        BiometricType biometricType2 = new BiometricType("REAR_FINGERPRINT", 1, true);
        REAR_FINGERPRINT = biometricType2;
        BiometricType biometricType3 = new BiometricType("UNDER_DISPLAY_FINGERPRINT", 2, true);
        UNDER_DISPLAY_FINGERPRINT = biometricType3;
        BiometricType biometricType4 = new BiometricType("SIDE_FINGERPRINT", 3, true);
        SIDE_FINGERPRINT = biometricType4;
        BiometricType biometricType5 = new BiometricType("FACE", 4, false);
        FACE = biometricType5;
        BiometricType[] biometricTypeArr = {biometricType, biometricType2, biometricType3, biometricType4, biometricType5};
        $VALUES = biometricTypeArr;
        EnumEntriesKt.enumEntries(biometricTypeArr);
    }

    private BiometricType(String str, int i, boolean z) {
        this.isFingerprint = z;
    }

    public static BiometricType valueOf(String str) {
        return (BiometricType) Enum.valueOf(BiometricType.class, str);
    }

    public static BiometricType[] values() {
        return (BiometricType[]) $VALUES.clone();
    }

    public final boolean isFingerprint() {
        return this.isFingerprint;
    }
}
