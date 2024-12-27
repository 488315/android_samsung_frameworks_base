package com.android.systemui.statusbar;

import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KshView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ KshView f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ KshView$$ExternalSyntheticLambda1(KshView kshView, List list) {
        this.f$0 = kshView;
        this.f$1 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.showKshDialog(this.f$1);
    }
}
