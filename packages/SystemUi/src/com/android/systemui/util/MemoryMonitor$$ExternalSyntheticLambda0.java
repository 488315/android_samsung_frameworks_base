package com.android.systemui.util;

import android.os.Debug;
import android.util.Slog;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda0(MemoryMonitor memoryMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MemoryMonitor memoryMonitor = this.f$0;
                memoryMonitor.mCurrentNotiCount = ((NotifPipeline) memoryMonitor.mNotifCollection).getAllNotifs().size();
                break;
            case 1:
                this.f$0.startMonitoring(3600000, true);
                break;
            case 2:
                MemoryMonitor memoryMonitor2 = this.f$0;
                memoryMonitor2.getClass();
                new Thread(new MemoryMonitor$$ExternalSyntheticLambda0(memoryMonitor2, 3), "printMemoryInfo").start();
                break;
            default:
                MemoryMonitor memoryMonitor3 = this.f$0;
                memoryMonitor3.getClass();
                Slog.d("MemoryMonitor", "check again after GC");
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                if (!memoryMonitor3.isLeakSuspect(memoryInfo, Debug.countInstancesOfClass(View.class), Debug.countInstancesOfClass(ViewRootImpl.class))) {
                    Slog.d("MemoryMonitor", "no issue");
                    break;
                } else {
                    memoryMonitor3.mHeapDumpHelper.dump(memoryMonitor3.mReason);
                    break;
                }
        }
    }
}
