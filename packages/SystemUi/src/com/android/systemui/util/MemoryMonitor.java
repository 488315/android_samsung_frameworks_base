package com.android.systemui.util;

import android.app.Notification;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.Dumpable;
import com.android.systemui.HeapDumpHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.io.PrintWriter;
import java.lang.Thread;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MemoryMonitor implements Dumpable {
    public final BootCompleteCache mBootCompleteCache;
    public final HeapDumpHelper mHeapDumpHelper;
    public String mLastMemoryInfoLogTime;
    public final Handler mMainHandler;
    public final CommonNotifCollection mNotifCollection;
    public final UncaughtExceptionPreHandlerManager mPreHandlerManager;
    public Long mLastMemoryInfoCalcTime = -180000L;
    public boolean mIsInCalcMemInfo = false;
    public String mReason = "";
    public int mCurrentNotiCount = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SystemUIExceptionHandler implements Thread.UncaughtExceptionHandler {
        public /* synthetic */ SystemUIExceptionHandler(MemoryMonitor memoryMonitor, int i) {
            this();
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(Thread thread, Throwable th) {
            if (th instanceof OutOfMemoryError) {
                MemoryMonitor.this.mHeapDumpHelper.dump("OutOfMemoryError");
            }
            MemoryMonitor.this.printMemoryInfo(false);
        }

        private SystemUIExceptionHandler() {
        }
    }

    public MemoryMonitor(Handler handler, BootCompleteCache bootCompleteCache, HeapDumpHelper heapDumpHelper, DumpManager dumpManager, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, CommonNotifCollection commonNotifCollection) {
        this.mMainHandler = handler;
        this.mBootCompleteCache = bootCompleteCache;
        this.mHeapDumpHelper = heapDumpHelper;
        dumpManager.registerDumpable(this);
        this.mPreHandlerManager = uncaughtExceptionPreHandlerManager;
        this.mNotifCollection = commonNotifCollection;
        handler.postDelayed(new MemoryMonitor$$ExternalSyntheticLambda0(this, 1), 3600000L);
    }

    public final void dispatchTrimMemory() {
        if (!((BootCompleteCacheImpl) this.mBootCompleteCache).isBootComplete()) {
            Slog.d("MemoryMonitor", "didn't receive BOOT_COMPLETED");
        } else if (this.mLastMemoryInfoCalcTime.longValue() + 180000 > SystemClock.elapsedRealtime()) {
            Slog.d("MemoryMonitor", String.format("Last Info is %s. It still remains until reset time. So skip this.", this.mLastMemoryInfoLogTime));
        } else {
            startMonitoring(0, false);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        HeapDumpHelper heapDumpHelper = this.mHeapDumpHelper;
        if (heapDumpHelper.isDumped) {
            StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "SystemUI Leak Info", "    isLeakSuspect : ");
            m75m.append(this.mReason);
            printWriter.println(m75m.toString());
            KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("    path : "), heapDumpHelper.mHeapDumpFilePath, printWriter);
        }
    }

    public final boolean isLeakSuspect(Debug.MemoryInfo memoryInfo, long j, long j2) {
        Object obj;
        if (this.mHeapDumpHelper.isDumped) {
            return false;
        }
        int parseInt = Integer.parseInt(memoryInfo.getMemoryStat("summary.java-heap"));
        int intValue = Integer.valueOf(memoryInfo.getMemoryStat("summary.native-heap")).intValue() + (memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut);
        int parseInt2 = Integer.parseInt(memoryInfo.getMemoryStat("summary.graphics"));
        int parseInt3 = Integer.parseInt(memoryInfo.getMemoryStat("summary.total-pss"));
        if (parseInt <= 225280 && intValue <= 819200 && parseInt2 <= 512000 && parseInt3 <= 1024000 && ((j <= 20000 || this.mCurrentNotiCount >= 100) && j2 <= 50)) {
            return false;
        }
        StringBuilder sb = new StringBuilder("J=");
        sb.append(parseInt > 225280 ? Integer.valueOf(parseInt) : Boolean.FALSE);
        sb.append(", N=");
        sb.append(intValue > 819200 ? Integer.valueOf(intValue) : Boolean.FALSE);
        sb.append(", G=");
        sb.append(parseInt2 > 512000 ? Integer.valueOf(parseInt2) : Boolean.FALSE);
        sb.append(", T=");
        sb.append(parseInt3 > 1024000 ? Integer.valueOf(parseInt3) : Boolean.FALSE);
        sb.append(", V=");
        if (j > 20000) {
            obj = j + "/" + this.mCurrentNotiCount;
        } else {
            obj = Boolean.FALSE;
        }
        sb.append(obj);
        sb.append(", VR=");
        sb.append(j2 > 50 ? Long.valueOf(j2) : Boolean.FALSE);
        this.mReason = sb.toString();
        Slog.d("MemoryMonitor", "isLeakSuspect :" + this.mReason);
        return true;
    }

    public final void printMemoryInfo(final boolean z) {
        Slog.d("MemoryMonitor", " - Memory Information -");
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        long countInstancesOfClass = Debug.countInstancesOfClass(View.class);
        long countInstancesOfClass2 = Debug.countInstancesOfClass(ViewRootImpl.class);
        long countInstancesOfClass3 = Debug.countInstancesOfClass(Notification.class);
        StringBuilder sb = new StringBuilder("Dalvik Heap : ");
        sb.append(memoryInfo.dalvikPrivateDirty + (memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut) + memoryInfo.getOtherPrivateDirty(0) + (memoryInfo.hasSwappedOutPss ? memoryInfo.getOtherSwappedOutPss(0) : memoryInfo.getOtherSwappedOut(0)));
        Slog.d("MemoryMonitor", sb.toString());
        StringBuilder sb2 = new StringBuilder("Native Heap : ");
        sb2.append(Integer.valueOf(memoryInfo.getMemoryStat("summary.native-heap")).intValue() + (memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut));
        Slog.d("MemoryMonitor", sb2.toString());
        Slog.d("MemoryMonitor", "Graphics : " + memoryInfo.getMemoryStat("summary.graphics"));
        Slog.d("MemoryMonitor", "Total PSS : " + memoryInfo.getMemoryStat("summary.total-pss"));
        Slog.d("MemoryMonitor", " - View count -");
        Slog.d("MemoryMonitor", "View=" + countInstancesOfClass);
        Slog.d("MemoryMonitor", "ViewRootImpl=" + countInstancesOfClass2);
        Slog.d("MemoryMonitor", "Notification=" + countInstancesOfClass3);
        boolean isLeakSuspect = isLeakSuspect(memoryInfo, countInstancesOfClass, countInstancesOfClass2);
        Handler handler = this.mMainHandler;
        if (isLeakSuspect) {
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
            handler.postDelayed(new MemoryMonitor$$ExternalSyntheticLambda0(this, 2), 5000L);
        }
        if (z) {
            int parseInt = Integer.parseInt(memoryInfo.getMemoryStat("summary.total-pss"));
            final int i = parseInt < 512000 ? 10800000 : parseInt < 819200 ? 3600000 : 900000;
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.util.MemoryMonitor$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MemoryMonitor.this.startMonitoring(i, z);
                }
            }, i);
        }
    }

    public final void startMonitoring(int i, boolean z) {
        if (this.mIsInCalcMemInfo) {
            Slog.d("MemoryMonitor", "Already in calculating memory info. So skip this.");
            return;
        }
        this.mIsInCalcMemInfo = true;
        Slog.d("MemoryMonitor", "Starting getMemoryInfo in MemoryInfoReporter thread. Delay Time: " + i);
        MemoryMonitor$$ExternalSyntheticLambda0 memoryMonitor$$ExternalSyntheticLambda0 = new MemoryMonitor$$ExternalSyntheticLambda0(this, 0);
        Handler handler = this.mMainHandler;
        handler.post(memoryMonitor$$ExternalSyntheticLambda0);
        handler.postDelayed(new MemoryMonitor$$ExternalSyntheticLambda1(this, z, 0), 3000L);
    }
}
