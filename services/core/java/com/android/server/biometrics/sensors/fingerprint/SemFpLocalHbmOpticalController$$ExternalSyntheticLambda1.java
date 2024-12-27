package com.android.server.biometrics.sensors.fingerprint;

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
