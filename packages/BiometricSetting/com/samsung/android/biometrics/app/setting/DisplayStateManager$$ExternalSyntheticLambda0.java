package com.samsung.android.biometrics.app.setting;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayStateManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager f$0;

    public /* synthetic */ DisplayStateManager$$ExternalSyntheticLambda0(DisplayStateManager displayStateManager, int i) {
        this.$r8$classId = i;
        this.f$0 = displayStateManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.releaseRefreshRateForSeamlessMode();
                break;
            default:
                DisplayStateManager.$r8$lambda$6_8cZpCi3B4a7EP8WkuwIxNQLP0(this.f$0);
                break;
        }
    }
}
