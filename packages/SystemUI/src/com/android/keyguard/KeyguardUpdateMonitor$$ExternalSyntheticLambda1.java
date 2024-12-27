package com.android.keyguard;

import android.hardware.biometrics.BiometricManager;
import android.os.Trace;
import android.telephony.ServiceState;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardUpdateMonitor f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda1(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardUpdateMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
        switch (i) {
            case 0:
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                keyguardUpdateMonitorLogger.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.ERROR, "Fp cancellation not received, transitioning to STOPPED", null);
                boolean z = keyguardUpdateMonitor.mFingerprintRunningState == 3;
                keyguardUpdateMonitor.mFingerprintRunningState = 0;
                keyguardUpdateMonitor.mFingerprintDetectRunning = false;
                if (z) {
                    keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    break;
                } else {
                    keyguardUpdateMonitor.updateFingerprintListeningState(1);
                    break;
                }
            case 1:
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                break;
            case 2:
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
                break;
            case 3:
                keyguardUpdateMonitor.mLogger.d("Retrying fingerprint listening after power pressed error.");
                keyguardUpdateMonitor.updateFingerprintListeningState(0);
                break;
            case 4:
                keyguardUpdateMonitor.getClass();
                Trace.beginSection("#startBiometricWatchdog");
                if (keyguardUpdateMonitor.mFaceManager != null) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                    keyguardUpdateMonitorLogger2.getClass();
                    keyguardUpdateMonitorLogger2.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("face"), null);
                    keyguardUpdateMonitor.mFaceManager.scheduleWatchdog();
                }
                if (keyguardUpdateMonitor.mFpm != null) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = keyguardUpdateMonitor.mLogger;
                    keyguardUpdateMonitorLogger3.getClass();
                    keyguardUpdateMonitorLogger3.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("fingerprint"), null);
                    keyguardUpdateMonitor.mFpm.scheduleWatchdog();
                }
                Trace.endSection();
                break;
            case 5:
                Log.d("KeyguardUpdateMonitor", "start mTelephonyManager.getActiveModemCount() : " + keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount());
                for (int i2 = 0; i2 < keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount(); i2++) {
                    int[] subscriptionIds = keyguardUpdateMonitor.mSubscriptionManager.getSubscriptionIds(i2);
                    if (subscriptionIds != null) {
                        for (int i3 : subscriptionIds) {
                            ServiceState serviceStateForSubscriber = keyguardUpdateMonitor.mTelephonyManager.getServiceStateForSubscriber(i3);
                            KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
                            anonymousClass16.sendMessage(anonymousClass16.obtainMessage(330, i3, 0, serviceStateForSubscriber));
                        }
                    }
                }
                break;
            case 6:
                keyguardUpdateMonitor.mTrustManager.registerTrustListener(keyguardUpdateMonitor);
                keyguardUpdateMonitor.setStrongAuthTracker(keyguardUpdateMonitor.mStrongAuthTracker);
                break;
            case 7:
                BiometricManager biometricManager = keyguardUpdateMonitor.mBiometricManager;
                if (biometricManager != null) {
                    biometricManager.registerEnabledOnKeyguardCallback(keyguardUpdateMonitor.mBiometricEnabledCallback);
                    break;
                }
                break;
            case 8:
                keyguardUpdateMonitor.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(keyguardUpdateMonitor.mPhoneStateListener);
                for (int i4 = 0; i4 < keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount(); i4++) {
                    int simState = keyguardUpdateMonitor.mTelephonyManager.getSimState(i4);
                    int[] subscriptionIds2 = keyguardUpdateMonitor.mSubscriptionManager.getSubscriptionIds(i4);
                    if (subscriptionIds2 != null) {
                        for (int i5 : subscriptionIds2) {
                            keyguardUpdateMonitor.mHandler.obtainMessage(304, i5, i4, Integer.valueOf(simState)).sendToTarget();
                        }
                    }
                }
                break;
            case 9:
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger4 = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger4.getClass();
                keyguardUpdateMonitorLogger4.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.ERROR, "Face cancellation not received, transitioning to STOPPED", null);
                keyguardUpdateMonitor.mFaceRunningState = 0;
                keyguardUpdateMonitor.updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED);
                break;
            default:
                keyguardUpdateMonitor.updateFingerprintListeningState(2);
                break;
        }
    }
}
