package com.android.systemui.p016qs.external;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TileLifecycleManager f$0;

    public /* synthetic */ TileLifecycleManager$$ExternalSyntheticLambda1(TileLifecycleManager tileLifecycleManager, int i) {
        this.$r8$classId = i;
        this.f$0 = tileLifecycleManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setBindService(true);
                break;
            case 1:
                TileLifecycleManager tileLifecycleManager = this.f$0;
                if (tileLifecycleManager.mBound.get()) {
                    Log.d("TileLifecycleManager", "Trying to rebind");
                    tileLifecycleManager.setBindService(true);
                    break;
                }
                break;
            case 2:
                TileLifecycleManager tileLifecycleManager2 = this.f$0;
                tileLifecycleManager2.mUnbindImmediate.set(true);
                tileLifecycleManager2.setBindService(true);
                break;
            default:
                TileLifecycleManager tileLifecycleManager3 = this.f$0;
                if (tileLifecycleManager3.mUnbindImmediate.get()) {
                    tileLifecycleManager3.mUnbindImmediate.set(false);
                    tileLifecycleManager3.setBindService(false);
                    break;
                }
                break;
        }
    }
}
