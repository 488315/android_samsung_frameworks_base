package com.android.server.biometrics;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BiometricService$2$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda7(
            BiometricService.AnonymousClass2 anonymousClass2, int i) {
        this.f$0 = anonymousClass2;
        this.f$1 = i;
    }

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda7(
            BiometricService.BiometricServiceWrapper biometricServiceWrapper, int i) {
        this.f$0 = biometricServiceWrapper;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BiometricService.AnonymousClass2 anonymousClass2 =
                        (BiometricService.AnonymousClass2) this.f$0;
                int i = this.f$1;
                AuthSession authSession = BiometricService.this.mAuthSession;
                if (authSession == null) {
                    Slog.e(
                            "BiometricService",
                            "handleOnSwitchingSensorPressed: AuthSession is null");
                    break;
                } else {
                    for (BiometricSensor biometricSensor :
                            authSession.mPreAuthInfo.eligibleSensors) {
                        try {
                            if (biometricSensor.modality == i) {
                                authSession.setSensorsToStateWaitingForCookie(
                                        biometricSensor, false);
                            } else if (biometricSensor.mCookie != 0) {
                                IBinder iBinder = authSession.mToken;
                                String str = authSession.mOpPackageName;
                                long j = authSession.mRequestId;
                                if (biometricSensor.mSensorState != 4) {
                                    biometricSensor.impl.cancelAuthenticationFromService(
                                            iBinder, str, j);
                                    biometricSensor.mSensorState = 4;
                                }
                                biometricSensor.mSensorState = 0;
                                biometricSensor.mCookie = 0;
                            }
                            authSession.mState = 5;
                        } catch (RemoteException e) {
                            Slog.e(
                                    "BiometricService/AuthSession",
                                    "onSwitchingSensorPressed, sensor: " + biometricSensor,
                                    e);
                        }
                    }
                    break;
                }
            default:
                BiometricService.this.mBiometricContext.mAuthSessionCoordinator.resetLockoutFor(
                        this.f$1, 15, -1L);
                break;
        }
    }
}
