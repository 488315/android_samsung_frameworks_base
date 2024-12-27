package com.android.keyguard;

import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda14 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda14(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FaceManager faceManager;
        FingerprintManager fingerprintManager;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = (KeyguardSecUpdateMonitorImpl) obj;
                keyguardSecUpdateMonitorImpl.getClass();
                Log.i("KeyguardFingerprint", "Waiting window focus change");
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                    break;
                }
                break;
            case 1:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl2 = (KeyguardSecUpdateMonitorImpl) obj;
                keyguardSecUpdateMonitorImpl2.mFastUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda14(keyguardSecUpdateMonitorImpl2, 7), "PowerManager#userActivity"));
                break;
            case 2:
                ((LooperSlowLogControllerImpl) ((KeyguardSecUpdateMonitorImpl) obj).mLooperSlowLogController).enable(1, 10L, 20L, 0L, false, null);
                break;
            case 3:
                ((KeyguardSecUpdateMonitorImpl) obj).updateFingerprintListeningState(2);
                break;
            case 4:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl3 = (KeyguardSecUpdateMonitorImpl) obj;
                keyguardSecUpdateMonitorImpl3.getClass();
                keyguardSecUpdateMonitorImpl3.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED);
                break;
            case 5:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl4 = (KeyguardSecUpdateMonitorImpl) obj;
                keyguardSecUpdateMonitorImpl4.getClass();
                keyguardSecUpdateMonitorImpl4.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET);
                break;
            case 6:
                ((KeyguardSecUpdateMonitorImpl) obj).mFastUnlockController.logLapTime("onFpAuthenticationSucceeded end", new Object[0]);
                break;
            case 7:
                ((KeyguardSecUpdateMonitorImpl) obj).mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
                break;
            case 8:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl5 = (KeyguardSecUpdateMonitorImpl) obj;
                if (keyguardSecUpdateMonitorImpl5.isFaceOptionEnabled()) {
                    if (keyguardSecUpdateMonitorImpl5.isFaceDetectionRunning()) {
                        keyguardSecUpdateMonitorImpl5.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_SESSION_CLOSE);
                    }
                    keyguardSecUpdateMonitorImpl5.setFaceAuthenticated(false);
                    if (keyguardSecUpdateMonitorImpl5.mFaceManager != null) {
                        Log.d("KeyguardFace", "requestSessionClose()");
                        keyguardSecUpdateMonitorImpl5.mFaceManager.semSessionClose();
                        break;
                    }
                }
                break;
            case 9:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl6 = (KeyguardSecUpdateMonitorImpl) obj;
                boolean z = keyguardSecUpdateMonitorImpl6.mDeviceInteractive;
                if (!keyguardSecUpdateMonitorImpl6.isEnabledWof() && !z && keyguardSecUpdateMonitorImpl6.isFingerprintOptionEnabled() && (fingerprintManager = keyguardSecUpdateMonitorImpl6.mFpm) != null) {
                    fingerprintManager.requestSessionOpen();
                }
                if (keyguardSecUpdateMonitorImpl6.isUnlockCompleted() && !z && keyguardSecUpdateMonitorImpl6.isFaceOptionEnabled() && (faceManager = keyguardSecUpdateMonitorImpl6.mFaceManager) != null) {
                    faceManager.semSessionOpen();
                    break;
                }
                break;
            case 10:
                ((KeyguardSecUpdateMonitorImpl) obj).mFastUnlockController.logLapTime("onFaceAuthenticationSucceeded end", new Object[0]);
                break;
            default:
                ((KeyguardFastBioUnlockController) obj).setEnabled();
                break;
        }
    }
}
