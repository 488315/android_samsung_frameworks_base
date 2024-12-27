package com.android.systemui.keyguard.shared.model;

import android.hardware.biometrics.BiometricSourceType;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class BiometricUnlockSource {
    public static final /* synthetic */ BiometricUnlockSource[] $VALUES;
    public static final Companion Companion;
    public static final BiometricUnlockSource FACE_SENSOR;
    public static final BiometricUnlockSource FINGERPRINT_SENSOR;

    public final class Companion {

        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[BiometricSourceType.values().length];
                try {
                    iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[BiometricSourceType.FACE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[BiometricSourceType.IRIS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public static BiometricUnlockSource fromBiometricSourceType(BiometricSourceType biometricSourceType) {
            int i = biometricSourceType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[biometricSourceType.ordinal()];
            if (i == 1) {
                return BiometricUnlockSource.FINGERPRINT_SENSOR;
            }
            if (i == 2) {
                return BiometricUnlockSource.FACE_SENSOR;
            }
            if (i != 3) {
                return null;
            }
            return BiometricUnlockSource.FACE_SENSOR;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        BiometricUnlockSource biometricUnlockSource = new BiometricUnlockSource("FINGERPRINT_SENSOR", 0);
        FINGERPRINT_SENSOR = biometricUnlockSource;
        BiometricUnlockSource biometricUnlockSource2 = new BiometricUnlockSource("FACE_SENSOR", 1);
        FACE_SENSOR = biometricUnlockSource2;
        BiometricUnlockSource[] biometricUnlockSourceArr = {biometricUnlockSource, biometricUnlockSource2};
        $VALUES = biometricUnlockSourceArr;
        EnumEntriesKt.enumEntries(biometricUnlockSourceArr);
        Companion = new Companion(null);
    }

    private BiometricUnlockSource(String str, int i) {
    }

    public static BiometricUnlockSource valueOf(String str) {
        return (BiometricUnlockSource) Enum.valueOf(BiometricUnlockSource.class, str);
    }

    public static BiometricUnlockSource[] values() {
        return (BiometricUnlockSource[]) $VALUES.clone();
    }
}
