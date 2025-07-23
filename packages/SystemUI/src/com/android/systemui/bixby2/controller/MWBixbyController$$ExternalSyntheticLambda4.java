package com.android.systemui.bixby2.controller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int i = this.$r8$classId;
        MWBixbyController mWBixbyController = this.f$0;
        switch (i) {
            case 0:
                mWBixbyController.lambda$changeLayoutOfSplitScreen$1();
                break;
            default:
                mWBixbyController.lambda$exchangePositionOfSplitScreen$0();
                break;
        }
    }
}
