package com.android.server.wm;

public final /* synthetic */ class CoverPolicy$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ CoverPolicy f$0;

    public /* synthetic */ CoverPolicy$$ExternalSyntheticLambda0(CoverPolicy coverPolicy) {
        this.f$0 = coverPolicy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.mDisplayContent.mDisplayRotation.updateOrientationListener();
    }
}
