package com.android.systemui.util;

import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda1(MemoryMonitor memoryMonitor, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MemoryMonitor memoryMonitor = this.f$0;
                boolean z = this.f$1;
                memoryMonitor.getClass();
                new Thread(new MemoryMonitor$$ExternalSyntheticLambda1(memoryMonitor, z, 1), "MemoryMonitor").start();
                break;
            default:
                MemoryMonitor memoryMonitor2 = this.f$0;
                memoryMonitor2.printMemoryInfo(this.f$1);
                memoryMonitor2.mIsInCalcMemInfo = false;
                memoryMonitor2.mLastMemoryInfoLogTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
                memoryMonitor2.mLastMemoryInfoCalcTime = Long.valueOf(SystemClock.elapsedRealtime());
                break;
        }
    }
}
