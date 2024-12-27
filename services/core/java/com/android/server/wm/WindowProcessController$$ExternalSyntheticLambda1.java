package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ WindowProcessController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ WindowProcessController$$ExternalSyntheticLambda1(
            WindowProcessController windowProcessController, boolean z) {
        this.f$0 = windowProcessController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowProcessController windowProcessController = this.f$0;
        windowProcessController.mListener.setRunningRemoteAnimation(this.f$1);
    }
}
