package com.android.server.biometrics;

import android.util.Slog;

public final /* synthetic */ class BiometricService$2$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ BiometricService.AnonymousClass2 f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ BiometricService$2$$ExternalSyntheticLambda4(
            BiometricService.AnonymousClass2 anonymousClass2, long j, int i, int i2) {
        this.f$0 = anonymousClass2;
        this.f$1 = j;
        this.f$2 = i;
        this.f$3 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BiometricService.AnonymousClass2 anonymousClass2 = this.f$0;
        long j = this.f$1;
        int i = this.f$2;
        int i2 = this.f$3;
        BiometricService biometricService = BiometricService.this;
        AuthSession authSession = biometricService.mAuthSession;
        if (authSession == null) {
            Slog.i("BiometricService", "handleOnErrorFromSysUi: AuthSession is null");
            return;
        }
        if (i == 3) {
            i2 = 2;
        } else if (i != 10) {
            i2 = 5;
        }
        authSession.mErrorEscrow = i2;
        authSession.mVendorCodeEscrow = 0;
        biometricService.handleOnDismissed(j, null, 5);
    }
}
