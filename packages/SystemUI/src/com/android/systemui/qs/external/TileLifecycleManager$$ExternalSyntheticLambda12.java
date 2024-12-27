package com.android.systemui.qs.external;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.f$0.setBindService(this.f$1);
    }
}
