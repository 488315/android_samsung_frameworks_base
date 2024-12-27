package com.android.server.wm;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ DisplayContent f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda6(
            DisplayContent displayContent, int i, int i2) {
        this.f$0 = displayContent;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.applyRotation(this.f$1, this.f$2);
    }
}
