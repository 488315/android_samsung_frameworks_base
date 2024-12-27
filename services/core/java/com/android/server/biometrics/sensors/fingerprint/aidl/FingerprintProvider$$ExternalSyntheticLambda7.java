package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.IInvalidationCallback;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda7(
            FingerprintProvider fingerprintProvider, int i, int i2, Object obj, int i3) {
        this.$r8$classId = i3;
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                IInvalidationCallback iInvalidationCallback = (IInvalidationCallback) this.f$3;
                fingerprintProvider.getClass();
                fingerprintProvider.scheduleForSensor$1(
                        i,
                        new FingerprintInvalidationClient(
                                fingerprintProvider.mContext,
                                ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i))
                                        .mLazySession,
                                i2,
                                i,
                                fingerprintProvider.createLogger$1(
                                        0, 0, fingerprintProvider.mAuthenticationStatsCollector),
                                fingerprintProvider.mBiometricContext,
                                ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i))
                                        .mAuthenticatorIds,
                                iInvalidationCallback));
                break;
            default:
                FingerprintProvider fingerprintProvider2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                byte[] bArr = (byte[]) this.f$3;
                fingerprintProvider2.getClass();
                FingerprintResetLockoutClient fingerprintResetLockoutClient =
                        new FingerprintResetLockoutClient(
                                fingerprintProvider2.mContext,
                                ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(i3))
                                        .mLazySession,
                                i4,
                                fingerprintProvider2.mContext.getOpPackageName(),
                                i3,
                                fingerprintProvider2.createLogger$1(
                                        0, 0, fingerprintProvider2.mAuthenticationStatsCollector),
                                fingerprintProvider2.mBiometricContext,
                                bArr,
                                ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(i3))
                                        .getLockoutTracker(false),
                                fingerprintProvider2.mLockoutResetDispatcher,
                                Utils.getCurrentStrength(i3));
                FingerprintProvider.AnonymousClass1 anonymousClass1 =
                        new FingerprintProvider.AnonymousClass1(fingerprintProvider2, 2);
                BaseClientMonitor semGetCurrentClient = fingerprintProvider2.semGetCurrentClient();
                Slog.d("FingerprintProvider", "cur client = " + semGetCurrentClient);
                if ((semGetCurrentClient instanceof SemFingerprintAuthenticationClient)
                        && ((SemFingerprintAuthenticationClient) semGetCurrentClient).mState != 4) {
                    fingerprintResetLockoutClient.start(anonymousClass1);
                    int i5 = fingerprintResetLockoutClient.mSensorId;
                    int i6 = fingerprintResetLockoutClient.mTargetUserId;
                    LockoutTracker lockoutTracker = fingerprintResetLockoutClient.mLockoutCache;
                    LockoutResetDispatcher lockoutResetDispatcher =
                            fingerprintResetLockoutClient.mLockoutResetDispatcher;
                    AuthSessionCoordinator authSessionCoordinator =
                            ((BiometricContextProvider)
                                            fingerprintResetLockoutClient.mBiometricContext)
                                    .mAuthSessionCoordinator;
                    int i7 = fingerprintResetLockoutClient.mBiometricStrength;
                    long j = fingerprintResetLockoutClient.mRequestId;
                    lockoutTracker.resetFailedAttemptsForUser(i6, true);
                    lockoutTracker.setLockoutModeForUser(i6, 0);
                    lockoutResetDispatcher.notifyLockoutResetCallbacks(i5);
                    authSessionCoordinator.resetLockoutFor(i6, i7, j);
                    fingerprintResetLockoutClient.mCallback.onClientFinished(
                            fingerprintResetLockoutClient, true);
                    break;
                } else {
                    fingerprintProvider2.scheduleForSensor$1(
                            i3, fingerprintResetLockoutClient, anonymousClass1);
                    break;
                }
                break;
        }
    }
}
