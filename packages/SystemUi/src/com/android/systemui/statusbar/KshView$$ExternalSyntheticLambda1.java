package com.android.systemui.statusbar;

import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
