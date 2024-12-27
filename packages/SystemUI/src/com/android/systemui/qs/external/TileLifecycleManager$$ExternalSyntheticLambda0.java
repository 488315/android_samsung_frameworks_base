package com.android.systemui.qs.external;

import android.app.ActivityManager;
import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.WakeLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TileLifecycleManager f$0;

    public /* synthetic */ TileLifecycleManager$$ExternalSyntheticLambda0(TileLifecycleManager tileLifecycleManager, int i) {
        this.$r8$classId = i;
        this.f$0 = tileLifecycleManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TileLifecycleManager tileLifecycleManager = this.f$0;
        switch (i) {
            case 0:
                if (tileLifecycleManager.mBound.get()) {
                    Log.d("TileLifecycleManager", "Trying to rebind " + tileLifecycleManager.mIntent.getComponent());
                    tileLifecycleManager.setBindService(true);
                    break;
                }
                break;
            case 1:
                if (tileLifecycleManager.mBound.get()) {
                    tileLifecycleManager.setBindService(true);
                }
                tileLifecycleManager.isDeathRebindScheduled.set(false);
                break;
            case 2:
                if (tileLifecycleManager.mIsBound.get()) {
                    Log.d("TileLifecycleManager", "handleDeath " + tileLifecycleManager.mIntent.getComponent());
                    tileLifecycleManager.unbindService();
                    if (tileLifecycleManager.mBound.get() && tileLifecycleManager.checkComponentState() && tileLifecycleManager.isDeathRebindScheduled.compareAndSet(false, true)) {
                        DelayableExecutor delayableExecutor = tileLifecycleManager.mExecutor;
                        TileLifecycleManager$$ExternalSyntheticLambda0 tileLifecycleManager$$ExternalSyntheticLambda0 = new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 1);
                        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                        tileLifecycleManager.mActivityManager.getMemoryInfo(memoryInfo);
                        long j = memoryInfo.lowMemory ? WakeLock.DEFAULT_MAX_TIMEOUT : tileLifecycleManager.mBindRetryDelay;
                        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Rebinding with a delay=", j, " - ");
                        m.append(tileLifecycleManager.mIntent.getComponent());
                        Log.i("TileLifecycleManager", m.toString());
                        delayableExecutor.executeDelayed(tileLifecycleManager$$ExternalSyntheticLambda0, j);
                        break;
                    }
                }
                break;
            case 3:
                tileLifecycleManager.mUnbindImmediate.set(true);
                tileLifecycleManager.setBindService(true);
                break;
            default:
                if (tileLifecycleManager.mUnbindImmediate.get()) {
                    tileLifecycleManager.mUnbindImmediate.set(false);
                    tileLifecycleManager.setBindService(false);
                    break;
                }
                break;
        }
    }
}
