package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedController;

/* loaded from: classes3.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(OneHandedController.OneHandedImpl oneHandedImpl, int i) {
        this.f$0 = oneHandedImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.stopOneHanded(this.f$1);
    }
}
