package com.android.wm.shell.pip;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda8(PipTaskOrganizer pipTaskOrganizer, int i, boolean z) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = i;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        pipTaskOrganizer.mPipTransitionState.setTransitionState(4);
        pipTaskOrganizer.exitPip(i, z);
    }
}
