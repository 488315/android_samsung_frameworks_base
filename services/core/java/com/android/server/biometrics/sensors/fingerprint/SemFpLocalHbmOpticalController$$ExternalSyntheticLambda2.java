package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;

public final /* synthetic */ class SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ SemFpLocalHbmOpticalController f$0;
    public final /* synthetic */ Pair f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2(
            SemFpLocalHbmOpticalController semFpLocalHbmOpticalController,
            Pair pair,
            int i,
            long j) {
        this.f$0 = semFpLocalHbmOpticalController;
        this.f$1 = pair;
        this.f$2 = i;
        this.f$3 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handleTouchEventInLhbm(this.f$1, this.f$2, this.f$3);
    }
}
