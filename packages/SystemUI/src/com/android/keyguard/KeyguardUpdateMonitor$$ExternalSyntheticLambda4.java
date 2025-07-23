package com.android.keyguard;

import android.hardware.biometrics.BiometricManager;
import android.os.BatteryManager;
import android.os.Trace;
import android.telephony.ServiceState;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardUpdateMonitor f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda4(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardUpdateMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2 = this.$r8$classId;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
        switch (i2) {
            case 0:
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogBuffer.log$default(keyguardUpdateMonitorLogger.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.ERROR, "Fp cancellation not received, transitioning to STOPPED");
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
                int i3 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
                break;
            case 2:
                keyguardUpdateMonitor.mLogger.d("Retrying fingerprint listening after power pressed error.");
                keyguardUpdateMonitor.updateFingerprintListeningState(0);
                break;
            case 3:
                int i4 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                Trace.beginSection("#startBiometricWatchdog");
                if (keyguardUpdateMonitor.mFaceManager != null) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                    keyguardUpdateMonitorLogger2.getClass();
                    LogBuffer.log$default(keyguardUpdateMonitorLogger2.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("face"));
                    keyguardUpdateMonitor.mFaceManager.scheduleWatchdog();
                }
                if (keyguardUpdateMonitor.mFpm != null) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = keyguardUpdateMonitor.mLogger;
                    keyguardUpdateMonitorLogger3.getClass();
                    LogBuffer.log$default(keyguardUpdateMonitorLogger3.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("fingerprint"));
                    keyguardUpdateMonitor.mFpm.scheduleWatchdog();
                }
                Trace.endSection();
                break;
            case 4:
                int i5 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                List subscriptionInfo = keyguardUpdateMonitor.getSubscriptionInfo(true);
                Log.i("KeyguardUpdateMonitor", "onSubscriptionInfoChanged(): list size is " + ((ArrayList) subscriptionInfo).size());
                keyguardUpdateMonitor.mMainExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda24(keyguardUpdateMonitor, subscriptionInfo, 2));
                break;
            case 5:
                BatteryManager batteryManager = (BatteryManager) keyguardUpdateMonitor.mContext.getSystemService(BatteryManager.class);
                if (batteryManager == null || (i = batteryManager.getIntProperty(4)) < 0 || i > 100) {
                    i = -1;
                }
                keyguardUpdateMonitor.mMainExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda24(keyguardUpdateMonitor, new BatteryStatus(1, i, 0, 1, 0, true), 0));
                break;
            case 6:
                int i6 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.getSubscriptionInfo(true);
                break;
            case 7:
                int i7 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                Log.d("KeyguardUpdateMonitor", "start mTelephonyManager.getActiveModemCount() : " + keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount());
                for (int i8 = 0; i8 < keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount(); i8++) {
                    int[] subscriptionIds = keyguardUpdateMonitor.mSubscriptionManager.getSubscriptionIds(i8);
                    if (subscriptionIds != null) {
                        for (int i9 : subscriptionIds) {
                            ServiceState serviceStateForSubscriber = keyguardUpdateMonitor.mTelephonyManager.getServiceStateForSubscriber(i9);
                            KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
                            anonymousClass16.sendMessage(anonymousClass16.obtainMessage(330, i9, 0, serviceStateForSubscriber));
                        }
                    }
                }
                break;
            case 8:
                int i10 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                break;
            case 9:
                keyguardUpdateMonitor.mTrustManager.registerTrustListener(keyguardUpdateMonitor);
                keyguardUpdateMonitor.setStrongAuthTracker(keyguardUpdateMonitor.mStrongAuthTracker);
                break;
            case 10:
                BiometricManager biometricManager = keyguardUpdateMonitor.mBiometricManager;
                if (biometricManager != null) {
                    biometricManager.registerEnabledOnKeyguardCallback(keyguardUpdateMonitor.mBiometricEnabledCallback);
                    break;
                }
                break;
            case 11:
                keyguardUpdateMonitor.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(keyguardUpdateMonitor.mPhoneStateListener);
                for (int i11 = 0; i11 < keyguardUpdateMonitor.mTelephonyManager.getActiveModemCount(); i11++) {
                    int simState = keyguardUpdateMonitor.mTelephonyManager.getSimState(i11);
                    int[] subscriptionIds2 = keyguardUpdateMonitor.mSubscriptionManager.getSubscriptionIds(i11);
                    if (subscriptionIds2 != null) {
                        for (int i12 : subscriptionIds2) {
                            keyguardUpdateMonitor.mHandler.obtainMessage(304, i12, i11, Integer.valueOf(simState)).sendToTarget();
                        }
                    }
                }
                break;
            case 12:
                int i13 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.updateFingerprintListeningState(2);
                break;
            default:
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger4 = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger4.getClass();
                LogBuffer.log$default(keyguardUpdateMonitorLogger4.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.ERROR, "Face cancellation not received, transitioning to STOPPED");
                keyguardUpdateMonitor.mFaceRunningState = 0;
                keyguardUpdateMonitor.updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED);
                break;
        }
    }
}
