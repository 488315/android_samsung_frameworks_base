package com.android.systemui.bixby2.controller;

public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda5(MWBixbyController mWBixbyController, int i) {
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
