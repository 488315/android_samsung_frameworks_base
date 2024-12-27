package com.android.server.biometrics;

import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricService$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ BiometricService$$ExternalSyntheticLambda4(int i, long j, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                BiometricService biometricService = (BiometricService) this.f$0;
                AuthSession authSessionIfCurrent =
                        biometricService.getAuthSessionIfCurrent(this.f$1);
                if (authSessionIfCurrent != null) {
                    Slog.e("BiometricService", "Session: " + authSessionIfCurrent);
                    try {
                        i = authSessionIfCurrent.mState;
                    } catch (RemoteException e) {
                        Slog.e("BiometricService/AuthSession", "Remote Exception: " + e);
                    }
                    if (i != 2 && i != 3) {
                        authSessionIfCurrent.mStatusBarService.hideAuthenticationDialog(
                                authSessionIfCurrent.mRequestId);
                        AuthSession authSession = biometricService.mAuthSession;
                        if (authSession != null) {
                            authSession.destroy();
                        }
                        biometricService.mAuthSession = null;
                        break;
                    } else {
                        authSessionIfCurrent.mState = 10;
                        authSessionIfCurrent.cancelAllSensors();
                        break;
                    }
                } else {
                    Slog.w("BiometricService", "handleClientDied: AuthSession is not current");
                    break;
                }
            default:
                BiometricService.BiometricServiceWrapper biometricServiceWrapper =
                        (BiometricService.BiometricServiceWrapper) this.f$0;
                long j = this.f$1;
                BiometricService biometricService2 = BiometricService.this;
                AuthSession authSessionIfCurrent2 = biometricService2.getAuthSessionIfCurrent(j);
                if (authSessionIfCurrent2 != null) {
                    if (authSessionIfCurrent2.onCancelAuthSession(false)) {
                        Slog.d(
                                "BiometricService",
                                "handleCancelAuthentication: AuthSession finished");
                        AuthSession authSession2 = biometricService2.mAuthSession;
                        if (authSession2 != null) {
                            authSession2.destroy();
                        }
                        biometricService2.mAuthSession = null;
                        break;
                    }
                } else {
                    Slog.w(
                            "BiometricService",
                            "handleCancelAuthentication: AuthSession is not current");
                    break;
                }
                break;
        }
    }
}
