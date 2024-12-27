package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
