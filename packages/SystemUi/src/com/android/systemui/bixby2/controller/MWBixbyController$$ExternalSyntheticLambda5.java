package com.android.systemui.bixby2.controller;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;
    public final /* synthetic */ Boolean[] f$1;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda5(MWBixbyController mWBixbyController, Boolean[] boolArr, int i) {
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
