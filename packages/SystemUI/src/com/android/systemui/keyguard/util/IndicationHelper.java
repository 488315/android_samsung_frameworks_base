package com.android.systemui.keyguard.util;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IndicationHelper {
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public IndicationHelper(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final boolean shouldSuppressErrorMsg(BiometricSourceType biometricSourceType, int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[biometricSourceType.ordinal()];
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        if (i2 != 1) {
            if (i2 != 2) {
                return false;
            }
            if ((keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) || i == 7 || i == 9) && i != 5 && i != 2) {
                return false;
            }
        } else if ((keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) || i == 7 || i == 9) && i != 5 && i != 10 && i != 19) {
            return false;
        }
        return true;
    }
}
