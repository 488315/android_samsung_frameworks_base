package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.samsung.android.bio.face.SemBioFaceManager;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
        switch (this.$r8$classId) {
            case 0:
                keyguardUpdateMonitorCallback.onOwnerInfoChanged();
                break;
            case 1:
                keyguardUpdateMonitorCallback.onLockModeChanged();
                break;
            case 2:
                keyguardUpdateMonitorCallback.onUnlocking();
                break;
            case 3:
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
                break;
            case 4:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FACE);
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
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onBiometricLockoutChanged(true);
                break;
            default:
                keyguardUpdateMonitorCallback.onBiometricLockoutChanged(false);
                break;
        }
    }
}
