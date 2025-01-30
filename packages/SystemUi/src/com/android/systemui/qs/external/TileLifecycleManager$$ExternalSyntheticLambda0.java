package com.android.systemui.qs.external;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TileLifecycleManager f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ TileLifecycleManager$$ExternalSyntheticLambda0(TileLifecycleManager tileLifecycleManager, boolean z) {
        this.f$0 = tileLifecycleManager;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.setBindService(this.f$1);
    }
}
