package com.android.systemui.bixby2.controller;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
