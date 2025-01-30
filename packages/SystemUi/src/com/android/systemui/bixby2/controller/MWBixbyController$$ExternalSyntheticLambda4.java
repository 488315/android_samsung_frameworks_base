package com.android.systemui.bixby2.controller;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda4(MWBixbyController mWBixbyController, int i) {
        this.$r8$classId = i;
        this.f$0 = mWBixbyController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$changeLayoutOfSplitScreen$1();
                break;
            default:
                this.f$0.lambda$exchangePositionOfSplitScreen$0();
                break;
        }
    }
}
