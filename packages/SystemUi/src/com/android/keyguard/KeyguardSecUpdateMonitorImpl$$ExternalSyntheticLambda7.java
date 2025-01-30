package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onUnlocking();
                break;
            case 1:
                ((KeyguardUpdateMonitorCallback) obj).onLockModeChanged();
                break;
            case 2:
                ((KeyguardUpdateMonitorCallback) obj).onOwnerInfoChanged();
                break;
            case 3:
                ((KeyguardUpdateMonitorCallback) obj).onLocaleChanged();
                break;
            case 4:
                ((KeyguardUpdateMonitorCallback) obj).onSystemDialogsShowing();
                break;
            case 5:
                ((KeyguardUpdateMonitorCallback) obj).onOfflineStateChanged();
                break;
            case 6:
                ((KeyguardUpdateMonitorCallback) obj).onRemoteLockInfoChanged();
                break;
            case 7:
                ((KeyguardUpdateMonitorCallback) obj).onUdfpsFingerDown$1();
                break;
            case 8:
                ((KeyguardUpdateMonitorCallback) obj).onUdfpsFingerUp$1();
                break;
            case 9:
                ((KeyguardUpdateMonitorCallback) obj).onFailedUnlockAttemptChanged();
                break;
            case 10:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
                break;
            case 11:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAuthFailed(BiometricSourceType.FACE);
                break;
            case 12:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricLockoutChanged(true);
                break;
            case 13:
                ((KeyguardUpdateMonitorCallback) obj).onUserUnlocked();
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricLockoutChanged(false);
                break;
        }
    }
}
