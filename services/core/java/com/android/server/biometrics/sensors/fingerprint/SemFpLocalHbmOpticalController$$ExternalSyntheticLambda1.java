package com.android.server.biometrics.sensors.fingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ SemFpLocalHbmOpticalController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1(
            SemFpLocalHbmOpticalController semFpLocalHbmOpticalController, int i) {
        this.f$0 = semFpLocalHbmOpticalController;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handleLocalHbm(this.f$1);
    }
}
