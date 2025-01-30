package com.android.keyguard;

import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.systemui.LsRune;
import com.android.systemui.log.LogLevel;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardUpdateMonitor f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda3(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardUpdateMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                break;
            case 1:
                this.f$0.updateFingerprintListeningState(2);
                break;
            case 2:
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.f$0;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor2.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                keyguardUpdateMonitorLogger.log("Fp cancellation not received, transitioning to STOPPED", LogLevel.ERROR);
                boolean z = keyguardUpdateMonitor2.mFingerprintRunningState == 3;
                keyguardUpdateMonitor2.mFingerprintRunningState = 0;
                if (z) {
                    keyguardUpdateMonitor2.updateFingerprintListeningState(2);
                    break;
                } else {
                    keyguardUpdateMonitor2.updateFingerprintListeningState(1);
                    break;
                }
            case 3:
                KeyguardUpdateMonitor keyguardUpdateMonitor3 = this.f$0;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor3.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                keyguardUpdateMonitorLogger2.log("Face cancellation not received, transitioning to STOPPED", LogLevel.ERROR);
                keyguardUpdateMonitor3.mFaceRunningState = 0;
                keyguardUpdateMonitor3.updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED);
                break;
            case 4:
                KeyguardUpdateMonitor keyguardUpdateMonitor4 = this.f$0;
                keyguardUpdateMonitor4.getClass();
                int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
                ServiceState serviceStateForSubscriber = keyguardUpdateMonitor4.mTelephonyManager.getServiceStateForSubscriber(defaultSubscriptionId);
                KeyguardUpdateMonitor.HandlerC080015 handlerC080015 = keyguardUpdateMonitor4.mHandler;
                handlerC080015.sendMessage(handlerC080015.obtainMessage(LsRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE ? VolteConstants.ErrorCode.CALL_END_REASON_TELEPHONY_NOT_RESPONDING : 330, defaultSubscriptionId, 0, serviceStateForSubscriber));
                break;
            case 5:
                KeyguardUpdateMonitor keyguardUpdateMonitor5 = this.f$0;
                keyguardUpdateMonitor5.mTrustManager.registerTrustListener(keyguardUpdateMonitor5);
                keyguardUpdateMonitor5.setStrongAuthTracker(keyguardUpdateMonitor5.mStrongAuthTracker);
                break;
            case 6:
                KeyguardUpdateMonitor keyguardUpdateMonitor6 = this.f$0;
                keyguardUpdateMonitor6.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(keyguardUpdateMonitor6.mPhoneStateListener);
                for (int i = 0; i < keyguardUpdateMonitor6.mTelephonyManager.getActiveModemCount(); i++) {
                    int simState = keyguardUpdateMonitor6.mTelephonyManager.getSimState(i);
                    int[] subscriptionIds = keyguardUpdateMonitor6.mSubscriptionManager.getSubscriptionIds(i);
                    if (subscriptionIds != null) {
                        for (int i2 : subscriptionIds) {
                            keyguardUpdateMonitor6.mHandler.obtainMessage(304, i2, i, Integer.valueOf(simState)).sendToTarget();
                        }
                    }
                }
                break;
            case 7:
                KeyguardUpdateMonitor keyguardUpdateMonitor7 = this.f$0;
                keyguardUpdateMonitor7.getClass();
                keyguardUpdateMonitor7.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
                break;
            default:
                KeyguardUpdateMonitor keyguardUpdateMonitor8 = this.f$0;
                keyguardUpdateMonitor8.mLogger.m84d("Retrying fingerprint listening after power pressed error.");
                keyguardUpdateMonitor8.updateFingerprintListeningState(0);
                break;
        }
    }
}
