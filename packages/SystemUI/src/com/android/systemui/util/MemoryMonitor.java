package com.android.systemui.util;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.Dumpable;
import com.android.systemui.HeapDumpHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class MemoryMonitor implements Dumpable {
    private static final int ALLOWED_NOTI_COUNT = 100;
    private static final String DATE_FORMAT = "MM-dd HH:mm:ss.SSS";
    private static final int HIGHER_PSS = 819200;
    private static final int HOUR = 3600000;
    private static final int MAX_BROADCAST_RECEIVER_COUNT = 1000;
    private static final int MAX_GRAPHICS_HEAP = 512000;
    private static final int MAX_JAVA_HEAP = 225280;
    private static final int MAX_NATIVE_HEAP = 819200;
    private static final int MAX_PSS = 1024000;
    private static final int MAX_VIEWROOT_COUNT = 50;
    private static final int MAX_VIEW_COUNT = 20000;
    private static final int MB = 1024;
    private static final long MEMINFO_GC_DELAY = 5000;
    private static final long MEMINFO_PRINT_DELAY = 3000;
    private static final long MEMINFO_RESET_DURATION = 180000;
    private static final int MINUTE = 60000;
    private static final int REDUCED_DELAY = 3600000;
    private static final int SECOND = 1000;
    private static final int SHORT_DELAY = 900000;
    private static final int STANDARD_DELAY = 10800000;
    private static final int STANDARD_PSS = 512000;
    private static final String TAG = "MemoryMonitor";
    private BootCompleteCache mBootCompleteCache;
    private HeapDumpHelper mHeapDumpHelper;
    private String mLastMemoryInfoLogTime;
    private final Handler mMainHandler;
    private final CommonNotifCollection mNotifCollection;
    private final UncaughtExceptionPreHandlerManager mPreHandlerManager;
    private Long mLastMemoryInfoCalcTime = -180000L;
    private boolean mIsInCalcMemInfo = false;
    private String mReason = "";
    private int mCurrentNotiCount = 0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class SystemUIExceptionHandler implements Thread.UncaughtExceptionHandler {
        public /* synthetic */ SystemUIExceptionHandler(MemoryMonitor memoryMonitor, int i) {
            this();
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        lambda$printMemoryInfo$3(true, 3600000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$printMemoryInfo$1() {
        String str = TAG;
        Slog.d(str, "check again after GC");
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        if (isLeakSuspect(memoryInfo, Debug.countInstancesOfClass(View.class), Debug.countInstancesOfClass(ViewRootImpl.class))) {
            this.mHeapDumpHelper.dump(this.mReason);
        } else {
            Slog.d(str, "no issue");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$printMemoryInfo$2() {
        new Thread(new MemoryMonitor$$ExternalSyntheticLambda0(this, 0), "printMemoryInfo").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startMonitoring$4(boolean z) {
        printMemoryInfo(z);
        this.mIsInCalcMemInfo = false;
        this.mLastMemoryInfoLogTime = new SimpleDateFormat(DATE_FORMAT).format(new Date(System.currentTimeMillis()));
        this.mLastMemoryInfoCalcTime = Long.valueOf(SystemClock.elapsedRealtime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startMonitoring$5(boolean z) {
        new Thread(new MemoryMonitor$$ExternalSyntheticLambda2(this, z, 0), TAG).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printMemoryInfo(final boolean z) {
        String str = TAG;
        Slog.d(str, " - Memory Information -");
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        long countInstancesOfClass = Debug.countInstancesOfClass(View.class);
        long countInstancesOfClass2 = Debug.countInstancesOfClass(ViewRootImpl.class);
        long countInstancesOfClass3 = Debug.countInstancesOfClass(Notification.class);
        long countInstancesOfClass4 = Debug.countInstancesOfClass(BroadcastReceiver.class);
        Slog.d(str, "Dalvik Heap : " + getDalvikHeapTotal(memoryInfo));
        Slog.d(str, "Native Heap : " + getNativeHeapTotal(memoryInfo));
        Slog.d(str, "Graphics : " + memoryInfo.getMemoryStat("summary.graphics"));
        Slog.d(str, "Total PSS : " + memoryInfo.getMemoryStat("summary.total-pss"));
        Slog.d(str, " - View count -");
        Slog.d(str, "View=" + countInstancesOfClass);
        Slog.d(str, "ViewRootImpl=" + countInstancesOfClass2);
        Slog.d(str, "Notification=" + countInstancesOfClass3);
        Slog.d(str, "BroadcastReceivers=" + countInstancesOfClass4);
        if (isLeakSuspect(memoryInfo, countInstancesOfClass, countInstancesOfClass2)) {
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
            this.mMainHandler.postDelayed(new MemoryMonitor$$ExternalSyntheticLambda0(this, 2), MEMINFO_GC_DELAY);
        } else if (countInstancesOfClass4 > 1000) {
            this.mReason = ValueAnimator$$ExternalSyntheticOutline0.m("BR=", countInstancesOfClass4);
            Slog.d(str, "isLeakSuspect :" + this.mReason);
            this.mHeapDumpHelper.dump(this.mReason);
        }
        if (z) {
            int parseInt = Integer.parseInt(memoryInfo.getMemoryStat("summary.total-pss"));
            final int i = parseInt < 512000 ? STANDARD_DELAY : parseInt < 819200 ? 3600000 : SHORT_DELAY;
            this.mMainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.MemoryMonitor$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    MemoryMonitor.this.lambda$printMemoryInfo$3(z, i);
                }
            }, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: startMonitoring, reason: merged with bridge method [inline-methods] */
    public void lambda$printMemoryInfo$3(boolean z, int i) {
        if (this.mIsInCalcMemInfo) {
            Slog.d(TAG, "Already in calculating memory info. So skip this.");
            return;
        }
        this.mIsInCalcMemInfo = true;
        Slog.d(TAG, "Starting getMemoryInfo in MemoryInfoReporter thread. Delay Time: " + i);
        this.mMainHandler.post(new MemoryMonitor$$ExternalSyntheticLambda0(this, 3));
        this.mMainHandler.postDelayed(new MemoryMonitor$$ExternalSyntheticLambda2(this, z, 1), MEMINFO_PRINT_DELAY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeNotificationCount() {
        this.mCurrentNotiCount = ((NotifPipeline) this.mNotifCollection).getAllNotifs().size();
    }

    public void dispatchTrimMemory() {
        if (!((BootCompleteCacheImpl) this.mBootCompleteCache).bootComplete.get()) {
            Slog.d(TAG, "didn't receive BOOT_COMPLETED");
            return;
        }
        if (this.mLastMemoryInfoCalcTime.longValue() + MEMINFO_RESET_DURATION <= SystemClock.elapsedRealtime()) {
            lambda$printMemoryInfo$3(false, 0);
            return;
        }
        Slog.d(TAG, "Last Info is " + this.mLastMemoryInfoLogTime + ". It still remains until reset time. So skip this.");
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        if (this.mHeapDumpHelper.isDumped) {
            StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "SystemUI Leak Info", "    isLeakSuspect : ");
            m.append(this.mReason);
            printWriter.println(m.toString());
            CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("    path : "), this.mHeapDumpHelper.mHeapDumpFilePath, printWriter);
        }
    }

    public int getDalvikHeapTotal(Debug.MemoryInfo memoryInfo) {
        int i = memoryInfo.dalvikPrivateDirty;
        int i2 = memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut;
        return i + i2 + memoryInfo.getOtherPrivateDirty(0) + (memoryInfo.hasSwappedOutPss ? memoryInfo.getOtherSwappedOutPss(0) : memoryInfo.getOtherSwappedOut(0));
    }

    public int getNativeHeapTotal(Debug.MemoryInfo memoryInfo) {
        return Integer.valueOf(memoryInfo.getMemoryStat("summary.native-heap")).intValue() + (memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut);
    }

    public boolean isLeakSuspect(Debug.MemoryInfo memoryInfo, long j, long j2) {
        Object obj;
        if (this.mHeapDumpHelper.isDumped) {
            return false;
        }
        int parseInt = Integer.parseInt(memoryInfo.getMemoryStat("summary.java-heap"));
        int nativeHeapTotal = getNativeHeapTotal(memoryInfo);
        int parseInt2 = Integer.parseInt(memoryInfo.getMemoryStat("summary.graphics"));
        int parseInt3 = Integer.parseInt(memoryInfo.getMemoryStat("summary.total-pss"));
        if (parseInt <= MAX_JAVA_HEAP && nativeHeapTotal <= 819200 && parseInt2 <= 512000 && parseInt3 <= MAX_PSS && ((j <= WakeLock.DEFAULT_MAX_TIMEOUT || this.mCurrentNotiCount >= 100) && j2 <= 50)) {
            return false;
        }
        StringBuilder sb = new StringBuilder("J=");
        sb.append(parseInt > MAX_JAVA_HEAP ? Integer.valueOf(parseInt) : Boolean.FALSE);
        sb.append(", N=");
        sb.append(nativeHeapTotal > 819200 ? Integer.valueOf(nativeHeapTotal) : Boolean.FALSE);
        sb.append(", G=");
        sb.append(parseInt2 > 512000 ? Integer.valueOf(parseInt2) : Boolean.FALSE);
        sb.append(", T=");
        sb.append(parseInt3 > MAX_PSS ? Integer.valueOf(parseInt3) : Boolean.FALSE);
        sb.append(", V=");
        if (j > WakeLock.DEFAULT_MAX_TIMEOUT) {
            obj = j + "/" + this.mCurrentNotiCount;
        } else {
            obj = Boolean.FALSE;
        }
        sb.append(obj);
        sb.append(", VR=");
        sb.append(j2 > 50 ? Long.valueOf(j2) : Boolean.FALSE);
        this.mReason = sb.toString();
        Slog.d(TAG, "isLeakSuspect :" + this.mReason);
        return true;
    }

    public void registerUncaughtException() {
        this.mPreHandlerManager.registerHandler(new SystemUIExceptionHandler(this, 0));
    }
}
