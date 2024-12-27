package com.android.systemui.qs.bar;

import com.android.systemui.qs.bar.BarController.AnonymousClass4;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BarController f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda0(BarController barController, int i) {
        this.$r8$classId = i;
        this.f$0 = barController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BarController barController = this.f$0;
        BarItemImpl barItemImpl = (BarItemImpl) obj;
        switch (i) {
            case 0:
                barItemImpl.mBGColorHelper = barController.mBGColorHelper;
                break;
            default:
                barController.getClass();
                barItemImpl.setCallback(barController.new AnonymousClass4());
                break;
        }
    }
}
