package com.android.server.accessibility.autoaction;

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
