package com.android.systemui.scrim;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScrimView$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ ScrimView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2 = false;

    public /* synthetic */ ScrimView$$ExternalSyntheticLambda4(ScrimView scrimView, int i) {
        this.f$0 = scrimView;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ScrimView scrimView = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        if (scrimView.mTintColor == i) {
            return;
        }
        scrimView.mTintColor = i;
        scrimView.updateColorWithTint(z);
    }
}
