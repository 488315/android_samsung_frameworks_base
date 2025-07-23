package com.android.keyguard;

import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.samsung.android.bio.face.SemBioFaceManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(Object obj, int i) {
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
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardSecUpdateMonitorImpl.getClass();
                Log.i("KeyguardFingerprint", "Waiting window focus change");
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                }
                keyguardSecUpdateMonitorImpl.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED);
                break;
            case 1:
                ((LooperSlowLogControllerImpl) ((KeyguardSecUpdateMonitorImpl) obj).mLooperSlowLogController).enable(1, 10L, 20L, 0L, false, null);
                break;
            case 2:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardSecUpdateMonitorImpl) obj).updateFingerprintListeningState(2);
                break;
            case 3:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl2 = (KeyguardSecUpdateMonitorImpl) obj;
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardSecUpdateMonitorImpl2.getClass();
                keyguardSecUpdateMonitorImpl2.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED);
                break;
            case 4:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl3 = (KeyguardSecUpdateMonitorImpl) obj;
                SemBioFaceManager semBioFaceManager4 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardSecUpdateMonitorImpl3.getClass();
                keyguardSecUpdateMonitorImpl3.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET);
                break;
            case 5:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl4 = (KeyguardSecUpdateMonitorImpl) obj;
                keyguardSecUpdateMonitorImpl4.mFastUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(keyguardSecUpdateMonitorImpl4, 6), "PowerManager#userActivity"));
                break;
            case 6:
                ((KeyguardSecUpdateMonitorImpl) obj).mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
                break;
            case 7:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl5 = (KeyguardSecUpdateMonitorImpl) obj;
                SemBioFaceManager semBioFaceManager5 = KeyguardSecUpdateMonitorImpl.sFaceManager;
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
            case 8:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl6 = (KeyguardSecUpdateMonitorImpl) obj;
                SemBioFaceManager semBioFaceManager6 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                boolean z = keyguardSecUpdateMonitorImpl6.mDeviceInteractive;
                if (!keyguardSecUpdateMonitorImpl6.isEnabledWof() && !z && keyguardSecUpdateMonitorImpl6.isFingerprintOptionEnabled() && (fingerprintManager = keyguardSecUpdateMonitorImpl6.mFpm) != null) {
                    fingerprintManager.requestSessionOpen();
                }
                if (keyguardSecUpdateMonitorImpl6.isUnlockCompleted() && !z && keyguardSecUpdateMonitorImpl6.isFaceOptionEnabled() && (faceManager = keyguardSecUpdateMonitorImpl6.mFaceManager) != null) {
                    faceManager.semSessionOpen();
                    break;
                }
                break;
            default:
                ((KeyguardFastBioUnlockController) obj).setEnabled();
                break;
        }
    }
}
