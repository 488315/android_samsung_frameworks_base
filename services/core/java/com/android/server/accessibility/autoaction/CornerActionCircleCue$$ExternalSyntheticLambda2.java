package com.android.server.accessibility.autoaction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CornerActionCircleCue$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ CornerActionCircleCue f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ CornerActionCircleCue$$ExternalSyntheticLambda2(
            CornerActionCircleCue cornerActionCircleCue, boolean z) {
        this.f$0 = cornerActionCircleCue;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CornerActionCircleCue cornerActionCircleCue = this.f$0;
        if (this.f$1) {
            cornerActionCircleCue.mView.setVisibility(0);
        } else {
            cornerActionCircleCue.mView.setVisibility(8);
        }
    }
}
