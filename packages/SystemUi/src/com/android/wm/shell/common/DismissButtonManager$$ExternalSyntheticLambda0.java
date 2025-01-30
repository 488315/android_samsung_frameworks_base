package com.android.wm.shell.common;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DismissButtonManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DismissButtonManager f$0;

    public /* synthetic */ DismissButtonManager$$ExternalSyntheticLambda0(DismissButtonManager dismissButtonManager) {
        this.f$0 = dismissButtonManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.cleanUpDismissTarget();
    }
}
