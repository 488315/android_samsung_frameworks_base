package com.android.systemui.qp.util;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SubscreenUtil$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SubscreenUtil f$0;

    public /* synthetic */ SubscreenUtil$$ExternalSyntheticLambda1(SubscreenUtil subscreenUtil, int i) {
        this.$r8$classId = i;
        this.f$0 = subscreenUtil;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SubscreenUtil subscreenUtil = this.f$0;
        switch (i) {
            case 0:
                subscreenUtil.mSubScreenQuickPanelWindowController.collapsePanel();
                break;
            default:
                subscreenUtil.closeSubscreenPanel();
                break;
        }
    }
}
