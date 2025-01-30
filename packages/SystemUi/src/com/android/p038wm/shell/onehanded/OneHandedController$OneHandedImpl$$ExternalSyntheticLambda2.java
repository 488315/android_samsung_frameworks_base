package com.android.p038wm.shell.onehanded;

import com.android.p038wm.shell.onehanded.OneHandedController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(OneHandedController.OneHandedImpl oneHandedImpl, int i) {
        this.f$0 = oneHandedImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.stopOneHanded(this.f$1);
    }
}
