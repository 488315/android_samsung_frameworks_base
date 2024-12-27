package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
        switch (this.$r8$classId) {
            case 0:
                keyguardUpdateMonitorCallback.onUnlocking();
                break;
            case 1:
                keyguardUpdateMonitorCallback.onOwnerInfoChanged();
                break;
            case 2:
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
                break;
            case 3:
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FACE);
                break;
            case 4:
                keyguardUpdateMonitorCallback.onLockModeChanged();
                break;
            case 5:
                keyguardUpdateMonitorCallback.onOfflineStateChanged();
                break;
            case 6:
                keyguardUpdateMonitorCallback.onUserUnlocked();
                break;
            case 7:
                keyguardUpdateMonitorCallback.onSystemDialogsShowing();
                break;
            case 8:
                keyguardUpdateMonitorCallback.onRemoteLockInfoChanged();
                break;
            case 9:
                keyguardUpdateMonitorCallback.onFailedUnlockAttemptChanged();
                break;
            case 10:
                keyguardUpdateMonitorCallback.onUdfpsFingerDown();
                break;
            case 11:
                keyguardUpdateMonitorCallback.onLocaleChanged();
                break;
            case 12:
                keyguardUpdateMonitorCallback.onUdfpsFingerUp();
                break;
            case 13:
                keyguardUpdateMonitorCallback.onBiometricLockoutChanged(true);
                break;
            default:
                keyguardUpdateMonitorCallback.onBiometricLockoutChanged(false);
                break;
        }
    }
}
