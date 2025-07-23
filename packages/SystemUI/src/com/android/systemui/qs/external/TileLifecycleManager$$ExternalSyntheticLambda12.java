package com.android.systemui.qs.external;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ TileLifecycleManager f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ TileLifecycleManager$$ExternalSyntheticLambda12(TileLifecycleManager tileLifecycleManager, boolean z) {
        this.f$0 = tileLifecycleManager;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TileLifecycleManager tileLifecycleManager = this.f$0;
        boolean z = this.f$1;
        int i = TileLifecycleManager.$r8$clinit;
        tileLifecycleManager.setBindService(z);
    }
}
