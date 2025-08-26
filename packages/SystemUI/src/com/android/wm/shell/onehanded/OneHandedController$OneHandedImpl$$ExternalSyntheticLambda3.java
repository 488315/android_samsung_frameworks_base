package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedController;

/* loaded from: classes3.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3(OneHandedController.OneHandedImpl oneHandedImpl, boolean z) {
        this.f$0 = oneHandedImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.setLockedDisabled(this.f$1, false);
    }
}
