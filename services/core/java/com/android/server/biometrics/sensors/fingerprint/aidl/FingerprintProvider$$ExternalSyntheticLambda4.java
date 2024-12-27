package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener;

import java.util.Iterator;

public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                FingerprintProvider fingerprintProvider = (FingerprintProvider) obj;
                fingerprintProvider.mDaemon = null;
                for (int i2 = 0;
                        i2 < fingerprintProvider.mFingerprintSensors.mSensors.size();
                        i2++) {
                    Sensor sensor =
                            (Sensor) fingerprintProvider.mFingerprintSensors.mSensors.valueAt(i2);
                    PerformanceTracker instanceForSensorId =
                            PerformanceTracker.getInstanceForSensorId(
                                    fingerprintProvider.mFingerprintSensors.mSensors.keyAt(i2));
                    if (instanceForSensorId != null) {
                        instanceForSensorId.mHALDeathCount++;
                    } else {
                        Slog.w(
                                fingerprintProvider.getTag$1(),
                                "Performance tracker is null. Not counting HAL death.");
                    }
                    BaseClientMonitor currentClient = sensor.mScheduler.getCurrentClient();
                    if (currentClient instanceof ErrorConsumer) {
                        Slog.e(
                                "Sensor",
                                "Sending fingerprint hardware unavailable error for client: "
                                        + currentClient);
                        ((ErrorConsumer) currentClient).onError(1, 0);
                        FrameworkStatsLog.write(148, 1, 1, -1);
                    } else if (currentClient != 0) {
                        currentClient.cancel();
                    }
                    sensor.mScheduler.recordCrashState();
                    sensor.mScheduler.reset();
                    sensor.mCurrentSession = null;
                }
                fingerprintProvider.mSehFingerprint = null;
                fingerprintProvider.mIsHalStarted = false;
                Iterator it = fingerprintProvider.mLifecycleListeners.iterator();
                while (it.hasNext()) {
                    ((SemFpHalLifecycleListener) it.next()).getClass();
                }
                break;
            case 1:
                FingerprintProvider fingerprintProvider2 = (FingerprintProvider) obj;
                for (int i3 = 0;
                        i3 < fingerprintProvider2.mFingerprintSensors.mSensors.size();
                        i3++) {
                    BaseClientMonitor currentClient2 =
                            ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.valueAt(i3))
                                    .mScheduler.getCurrentClient();
                    if (Utils.DEBUG) {
                        Slog.d(
                                fingerprintProvider2.getTag$1(),
                                "onOneHandModeEnabled: [" + i3 + "] = " + currentClient2);
                    }
                    if (currentClient2 instanceof SemUdfpsConstraintStatusListener) {
                        Slog.d(
                                fingerprintProvider2.getTag$1(),
                                "handle OneHandMode, client: "
                                        .concat(Utils.getClientName(currentClient2)));
                        ((SemUdfpsConstraintStatusListener) currentClient2).onOneHandModeEnabled();
                    }
                }
                break;
            case 2:
                FingerprintProvider fingerprintProvider3 = (FingerprintProvider) obj;
                int keyAt = fingerprintProvider3.mFingerprintSensors.mSensors.keyAt(0);
                fingerprintProvider3.scheduleForSensor$1(
                        keyAt,
                        new SemFpBaseRequestClient(
                                fingerprintProvider3.mContext,
                                fingerprintProvider3.mBiometricContext,
                                ((Sensor)
                                                fingerprintProvider3.mFingerprintSensors.mSensors
                                                        .get(keyAt))
                                        .mLazySession,
                                null,
                                null,
                                keyAt,
                                ((Sensor)
                                                fingerprintProvider3.mFingerprintSensors.mSensors
                                                        .get(keyAt))
                                        .getCurrentSessionUserId(),
                                "FingerprintRequestClient",
                                true,
                                2,
                                0,
                                null,
                                null));
                break;
            case 3:
                ((FingerprintProvider) obj).getHalInstance();
                break;
            case 4:
                FingerprintProvider fingerprintProvider4 = (FingerprintProvider) obj;
                fingerprintProvider4.mIsHalStarted = true;
                Iterator it2 = fingerprintProvider4.mLifecycleListeners.iterator();
                while (it2.hasNext()) {
                    ((SemFpHalLifecycleListener) it2.next()).onHalStarted(fingerprintProvider4);
                }
                break;
            default:
                FingerprintProvider.BiometricTaskStackListener biometricTaskStackListener =
                        (FingerprintProvider.BiometricTaskStackListener) obj;
                for (int i4 = 0;
                        i4 < FingerprintProvider.this.mFingerprintSensors.mSensors.size();
                        i4++) {
                    BaseClientMonitor currentClient3 =
                            ((Sensor)
                                            FingerprintProvider.this.mFingerprintSensors.mSensors
                                                    .valueAt(i4))
                                    .mScheduler.getCurrentClient();
                    if (!(currentClient3 instanceof AuthenticationClient)) {
                        Slog.e(
                                FingerprintProvider.this.getTag$1(),
                                "Task stack changed for client: " + currentClient3);
                    } else if (!Utils.isKeyguard(
                                    FingerprintProvider.this.mContext, currentClient3.mOwner)
                            && !Utils.isSystem(
                                    FingerprintProvider.this.mContext, currentClient3.mOwner)
                            && !((AuthenticationClient) currentClient3)
                                    .semIsAllowedBackgroundAuthentication()
                            && Utils.isBackground(currentClient3.mOwner)
                            && !currentClient3.mAlreadyDone) {
                        Slog.e(
                                FingerprintProvider.this.getTag$1(),
                                "Stopping background authentication, currentClient: "
                                        + currentClient3);
                        ((Sensor) FingerprintProvider.this.mFingerprintSensors.mSensors.valueAt(i4))
                                .mScheduler.cancelAuthenticationOrDetection(
                                        currentClient3.mToken, currentClient3.mRequestId);
                    }
                }
                break;
        }
    }
}
