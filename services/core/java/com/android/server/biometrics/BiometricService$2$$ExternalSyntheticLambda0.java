package com.android.server.biometrics;

import android.hardware.biometrics.PromptInfo;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricService$2$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda0(
            BiometricService.AnonymousClass1 anonymousClass1, long j, int i) {
        this.f$0 = anonymousClass1;
        this.f$1 = j;
        this.f$2 = i;
    }

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda0(
            BiometricService.AnonymousClass2 anonymousClass2, long j, int i) {
        this.f$0 = anonymousClass2;
        this.f$1 = j;
        this.f$2 = i;
    }

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda0(
            BiometricService.BiometricServiceWrapper biometricServiceWrapper, long j, int i) {
        this.f$0 = biometricServiceWrapper;
        this.f$1 = j;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                BiometricService.AnonymousClass2 anonymousClass2 =
                        (BiometricService.AnonymousClass2) this.f$0;
                long j = this.f$1;
                int i = this.f$2;
                BiometricService biometricService = BiometricService.this;
                biometricService.getClass();
                Slog.d("BiometricService", "onSystemEvent: " + i);
                AuthSession authSessionIfCurrent = biometricService.getAuthSessionIfCurrent(j);
                if (authSessionIfCurrent == null) {
                    Slog.w("BiometricService", "handleOnSystemEvent: AuthSession is not current");
                    break;
                } else if (authSessionIfCurrent.hasAuthenticatedAndConfirmed()) {
                    Slog.d("BiometricService/AuthSession", "onSystemEvent after successful auth");
                    break;
                } else if (authSessionIfCurrent.mPromptInfo.isReceiveSystemEvents()) {
                    try {
                        authSessionIfCurrent.mClientReceiver.onSystemEvent(i);
                        break;
                    } catch (RemoteException e) {
                        Slog.e("BiometricService/AuthSession", "RemoteException", e);
                        return;
                    }
                }
                break;
            case 1:
                BiometricService.BiometricServiceWrapper biometricServiceWrapper =
                        (BiometricService.BiometricServiceWrapper) this.f$0;
                long j2 = this.f$1;
                int i2 = this.f$2;
                AuthSession authSessionIfCurrent2 =
                        BiometricService.this.getAuthSessionIfCurrent(j2);
                if (authSessionIfCurrent2 == null) {
                    Slog.w(
                            "BiometricService",
                            "handleOnReadyForAuthentication: AuthSession is not current");
                    break;
                } else if (authSessionIfCurrent2.mCancelled) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i2,
                            "Received cookie but already cancelled (ignoring): ",
                            "BiometricService/AuthSession");
                    break;
                } else if (authSessionIfCurrent2.hasAuthenticatedAndConfirmed()) {
                    Slog.d(
                            "BiometricService/AuthSession",
                            "onCookieReceived after successful auth");
                    break;
                } else {
                    if (Utils.DEBUG) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i2, "onCookieReceived: ", ", state = "),
                                authSessionIfCurrent2.mState,
                                "BiometricService/AuthSession");
                    }
                    if (i2 != 0) {
                        for (BiometricSensor biometricSensor :
                                authSessionIfCurrent2.mPreAuthInfo.eligibleSensors) {
                            if (i2 == biometricSensor.mCookie) {
                                Slog.d(
                                        "BiometricService/Sensor",
                                        "Sensor(" + biometricSensor.id + ") matched cookie: " + i2);
                                biometricSensor.mSensorState = 2;
                            }
                        }
                        if (!authSessionIfCurrent2.allCookiesReceived()) {
                            Slog.v(
                                    "BiometricService/AuthSession",
                                    "onCookieReceived: still waiting");
                            break;
                        } else {
                            authSessionIfCurrent2.mStartTimeMs = System.currentTimeMillis();
                            authSessionIfCurrent2.startAllPreparedSensors(
                                    new AuthSession$$ExternalSyntheticLambda1(3));
                            if (authSessionIfCurrent2.mState == 5) {
                                authSessionIfCurrent2.mState = 3;
                                authSessionIfCurrent2.startAllPreparedSensors(
                                        new AuthSession$$ExternalSyntheticLambda1(1));
                                break;
                            } else {
                                try {
                                    Iterator it =
                                            authSessionIfCurrent2.mPreAuthInfo.eligibleSensors
                                                    .iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            z = false;
                                        } else if (authSessionIfCurrent2.isConfirmationRequired(
                                                (BiometricSensor) it.next())) {
                                            z = true;
                                        }
                                    }
                                    authSessionIfCurrent2.mSensors =
                                            new int
                                                    [authSessionIfCurrent2.mPreAuthInfo
                                                            .eligibleSensors.size()];
                                    for (int i3 = 0;
                                            i3
                                                    < authSessionIfCurrent2.mPreAuthInfo
                                                            .eligibleSensors.size();
                                            i3++) {
                                        authSessionIfCurrent2.mSensors[i3] =
                                                ((BiometricSensor)
                                                                authSessionIfCurrent2.mPreAuthInfo
                                                                        .eligibleSensors.get(i3))
                                                        .id;
                                    }
                                    SemBiometricSysUiWrapper semBiometricSysUiWrapper =
                                            authSessionIfCurrent2.mStatusBarService;
                                    PromptInfo promptInfo = authSessionIfCurrent2.mPromptInfo;
                                    int[] iArr = authSessionIfCurrent2.mSensors;
                                    PreAuthInfo preAuthInfo = authSessionIfCurrent2.mPreAuthInfo;
                                    str = "BiometricService/AuthSession";
                                    try {
                                        semBiometricSysUiWrapper.showAuthenticationDialog(
                                                promptInfo,
                                                iArr,
                                                preAuthInfo.credentialRequested
                                                        && preAuthInfo.credentialAvailable,
                                                z,
                                                authSessionIfCurrent2.mUserId,
                                                authSessionIfCurrent2.mOperationId,
                                                authSessionIfCurrent2.mOpPackageName,
                                                authSessionIfCurrent2.mRequestId);
                                        authSessionIfCurrent2.mState = 2;
                                        break;
                                    } catch (RemoteException e2) {
                                        e = e2;
                                        Slog.e(str, "Remote exception", e);
                                        return;
                                    }
                                } catch (RemoteException e3) {
                                    e = e3;
                                    str = "BiometricService/AuthSession";
                                }
                            }
                        }
                    }
                }
                break;
            default:
                BiometricService.AnonymousClass1 anonymousClass1 =
                        (BiometricService.AnonymousClass1) this.f$0;
                long j3 = this.f$1;
                int i4 = this.f$2;
                BiometricService biometricService2 = BiometricService.this;
                biometricService2.getClass();
                Slog.v("BiometricService", "handleAuthenticationRejected()");
                AuthSession authSessionIfCurrent3 = biometricService2.getAuthSessionIfCurrent(j3);
                if (authSessionIfCurrent3 == null) {
                    Slog.w(
                            "BiometricService",
                            "handleAuthenticationRejected: AuthSession is not current");
                    break;
                } else if (authSessionIfCurrent3.hasAuthenticatedAndConfirmed()) {
                    Slog.d(
                            "BiometricService/AuthSession",
                            "onAuthenticationRejected after successful auth");
                    break;
                } else {
                    try {
                        authSessionIfCurrent3.mStatusBarService.onBiometricError(
                                authSessionIfCurrent3.sensorIdToModality(i4), 100, 0);
                        if (authSessionIfCurrent3.pauseSensorIfSupported(i4)) {
                            authSessionIfCurrent3.mState = 4;
                        }
                        authSessionIfCurrent3.mClientReceiver.onAuthenticationFailed();
                        break;
                    } catch (RemoteException e4) {
                        Slog.e("BiometricService/AuthSession", "RemoteException", e4);
                    }
                }
        }
    }
}
