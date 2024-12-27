package com.android.systemui.statusbar;

import java.util.List;

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
