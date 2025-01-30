package com.android.p038wm.shell.onehanded;

import com.android.p038wm.shell.onehanded.OneHandedController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2 = false;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(OneHandedController.OneHandedImpl oneHandedImpl, boolean z) {
        this.f$0 = oneHandedImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        OneHandedController.this.setLockedDisabled(this.f$1, this.f$2);
    }
}
