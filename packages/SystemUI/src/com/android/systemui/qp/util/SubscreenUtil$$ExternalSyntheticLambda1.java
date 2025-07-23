package com.android.systemui.qp.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
