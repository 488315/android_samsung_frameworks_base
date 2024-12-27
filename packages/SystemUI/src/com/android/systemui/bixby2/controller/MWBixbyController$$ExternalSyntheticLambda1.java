package com.android.systemui.bixby2.controller;

public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;
    public final /* synthetic */ Boolean[] f$1;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda1(MWBixbyController mWBixbyController, Boolean[] boolArr, int i) {
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
