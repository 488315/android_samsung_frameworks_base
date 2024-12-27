package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ SemFpLocalHbmOpticalController f$0;
    public final /* synthetic */ Pair f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2(SemFpLocalHbmOpticalController semFpLocalHbmOpticalController, Pair pair, int i, long j) {
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
