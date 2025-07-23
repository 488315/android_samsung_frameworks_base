package com.android.systemui.bixby2.controller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;
    public final /* synthetic */ Boolean[] f$1;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda0(MWBixbyController mWBixbyController, Boolean[] boolArr, int i) {
        this.$r8$classId = i;
        this.f$0 = mWBixbyController;
        this.f$1 = boolArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$isVerticalDivision$8(this.f$1);
                break;
            default:
                this.f$0.lambda$isSplitScreenVisible$5(this.f$1);
                break;
        }
    }
}
