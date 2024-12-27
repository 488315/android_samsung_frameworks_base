package com.android.systemui.qs.bar;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda1(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        BarItemImpl barItemImpl = (BarItemImpl) obj;
        switch (i) {
            case 0:
                barItemImpl.setListening(z);
                break;
            case 1:
                barItemImpl.setExpanded(z);
                break;
            default:
                barItemImpl.setListening(z);
                break;
        }
    }
}
