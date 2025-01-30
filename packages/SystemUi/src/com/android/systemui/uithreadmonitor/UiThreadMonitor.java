package com.android.systemui.uithreadmonitor;

import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Printer;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.LogUtil;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UiThreadMonitor implements Dumpable {
    public static final boolean DEBUG_LOG;
    public static final boolean ENABLE_PAUSE;
    public int anrCount;
    public int awakeCount;
    public final Handler bgHandler;
    public final DisplayManager displayManager;
    public final DumpManager dumpManager;
    public final Handler handler;
    public long lastAsyncMsgHandledTimed;
    public long lastAwakeTime;
    public int lastChoreographerLogCount;
    public long lastChoreographerLogTime;
    public int lastChoreographerTotalDrawCount;
    public int lastDisplayState;
    public String lastStackTrace;
    public long lastStackTraceTime;
    public final LooperSlowLogController looperLogController;
    public boolean looperMsgLog;
    public boolean looperSlowLog;
    public final LinkedBlockingDeque blockingDeque = new LinkedBlockingDeque(1);
    public final Lazy mainThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$mainThread$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UiThreadMonitor.this.looper.getThread();
        }
    });
    public final Lazy monitorThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2.1
                /* JADX WARN: Can't wrap try/catch for region: R(22:2|(2:4|(1:6))(2:71|(1:73))|7|(2:9|(17:11|12|(1:16)|17|(1:19)(1:65)|20|(1:22)|23|(1:25)|26|27|(1:29)|30|(4:33|(1:35)|36|37)|38|(1:40)|(3:43|44|45)(1:42)))(2:68|(18:70|67|12|(2:14|16)|17|(0)(0)|20|(0)|23|(0)|26|27|(0)|30|(4:33|(0)|36|37)|38|(0)|(0)(0)))|66|67|12|(0)|17|(0)(0)|20|(0)|23|(0)|26|27|(0)|30|(0)|38|(0)|(0)(0)) */
                /* JADX WARN: Code restructure failed: missing block: B:48:0x013f, code lost:
                
                    r0 = move-exception;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:64:0x0239, code lost:
                
                    throw r0;
                 */
                /* JADX WARN: Removed duplicated region for block: B:14:0x006c  */
                /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x00b1  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00bd  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x00d8 A[Catch: all -> 0x013f, TryCatch #1 {all -> 0x013f, blocks: (B:27:0x00c4, B:29:0x00d8, B:30:0x00db), top: B:26:0x00c4 }] */
                /* JADX WARN: Removed duplicated region for block: B:32:0x00e7 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0128 A[LOOP:1: B:34:0x0126->B:35:0x0128, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:40:0x01c3  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x01ce A[LOOP:0: B:2:0x0018->B:42:0x01ce, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:43:0x01ca A[SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:65:0x00a4  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    long j;
                    long j2;
                    long j3;
                    boolean z;
                    boolean z2;
                    boolean z3;
                    UiThreadMonitor uiThreadMonitor2 = UiThreadMonitor.this;
                    boolean z4 = false;
                    uiThreadMonitor2.isPaused = false;
                    Process.setThreadPriority(19);
                    while (true) {
                        boolean z5 = SystemProperties.getBoolean("debug.sysui.looper.msg_log", z4);
                        Looper looper = uiThreadMonitor2.looper;
                        if (z5) {
                            if (!uiThreadMonitor2.looperMsgLog) {
                                looper.setMessageLogging(new Printer() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$updateLooperMsgLog$1
                                    @Override // android.util.Printer
                                    public final void println(String str) {
                                        Log.d("UiThreadMonitor", str);
                                    }
                                });
                                uiThreadMonitor2.looperMsgLog = true;
                            }
                        } else if (uiThreadMonitor2.looperMsgLog) {
                            looper.setMessageLogging(null);
                            uiThreadMonitor2.looperMsgLog = z4;
                        }
                        if (SystemProperties.getBoolean("debug.sysui.looper.slow_log", z4)) {
                            if (!uiThreadMonitor2.looperSlowLog) {
                                long j4 = SystemProperties.getLong("debug.sysui.looper.slow_dispatch", 30L);
                                j2 = SystemProperties.getLong("debug.sysui.looper.slow_delivery", 30L);
                                uiThreadMonitor2.looperSlowLog = true;
                                j3 = j4;
                                if (j3 > -1 && j2 > -1) {
                                    StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("updateShowLooperSlowLog dispatch=", j3, " ms, delivery=");
                                    m17m.append(j2);
                                    m17m.append(" ms");
                                    Log.d("UiThreadMonitor", m17m.toString());
                                    long j5 = j3;
                                    ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(0, j5, j5, 0L, false, null);
                                }
                                z = uiThreadMonitor2.isPaused;
                                long j6 = z ? 86400000L : 6000L;
                                String m86m = AbstractC0866xb1ce8deb.m86m("run isPaused=", z);
                                z2 = UiThreadMonitor.DEBUG_LOG;
                                if (z2) {
                                    Log.d("UiThreadMonitor", m86m);
                                }
                                Handler handler = uiThreadMonitor2.handler;
                                handler.removeCallbacks(uiThreadMonitor2.runnable);
                                if (!z) {
                                    handler.postDelayed(uiThreadMonitor2.runnable, 3000L);
                                }
                                String str = "wait " + j6;
                                if (z2) {
                                    Log.d("UiThreadMonitor", str);
                                }
                                Boolean bool = (Boolean) uiThreadMonitor2.blockingDeque.poll(j6, TimeUnit.MILLISECONDS);
                                if (!z && bool == null) {
                                    uiThreadMonitor2.anrCount++;
                                    uiThreadMonitor2.lastStackTraceTime = System.currentTimeMillis();
                                    StackTraceElement[] stackTrace = ((Thread) uiThreadMonitor2.mainThread$delegate.getValue()).getStackTrace();
                                    StringBuilder sb = new StringBuilder();
                                    int i = uiThreadMonitor2.anrCount;
                                    String makeTimeStr = LogUtil.makeTimeStr(uiThreadMonitor2.lastStackTraceTime);
                                    long j7 = uiThreadMonitor2.lastStackTraceTime - uiThreadMonitor2.lastAsyncMsgHandledTimed;
                                    StringBuilder m61m = AbstractC0662xaf167275.m61m("*** Traced call stack: count=", i, ",", makeTimeStr, "(");
                                    m61m.append(j7);
                                    m61m.append(" ms) ***\n");
                                    Log.i("UiThreadMonitor", m61m.toString());
                                    for (StackTraceElement stackTraceElement : stackTrace) {
                                        sb.append("   " + stackTraceElement + "\n");
                                    }
                                    String sb2 = sb.toString();
                                    Log.i("UiThreadMonitor", sb2);
                                    uiThreadMonitor2.lastStackTrace = sb2;
                                }
                                z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                if (z3) {
                                    Log.d("UiThreadMonitor", "disabled");
                                }
                                if (!z3) {
                                    uiThreadMonitor2.isPaused = true;
                                    return;
                                }
                                z4 = false;
                            }
                        } else if (uiThreadMonitor2.looperSlowLog) {
                            uiThreadMonitor2.looperSlowLog = z4;
                            j = 0;
                            j2 = j;
                            j3 = j2;
                            if (j3 > -1) {
                                StringBuilder m17m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("updateShowLooperSlowLog dispatch=", j3, " ms, delivery=");
                                m17m2.append(j2);
                                m17m2.append(" ms");
                                Log.d("UiThreadMonitor", m17m2.toString());
                                long j52 = j3;
                                ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(0, j52, j52, 0L, false, null);
                            }
                            z = uiThreadMonitor2.isPaused;
                            if (z) {
                            }
                            String m86m2 = AbstractC0866xb1ce8deb.m86m("run isPaused=", z);
                            z2 = UiThreadMonitor.DEBUG_LOG;
                            if (z2) {
                            }
                            Handler handler2 = uiThreadMonitor2.handler;
                            handler2.removeCallbacks(uiThreadMonitor2.runnable);
                            if (!z) {
                            }
                            String str2 = "wait " + j6;
                            if (z2) {
                            }
                            Boolean bool2 = (Boolean) uiThreadMonitor2.blockingDeque.poll(j6, TimeUnit.MILLISECONDS);
                            if (!z) {
                                uiThreadMonitor2.anrCount++;
                                uiThreadMonitor2.lastStackTraceTime = System.currentTimeMillis();
                                StackTraceElement[] stackTrace2 = ((Thread) uiThreadMonitor2.mainThread$delegate.getValue()).getStackTrace();
                                StringBuilder sb3 = new StringBuilder();
                                int i2 = uiThreadMonitor2.anrCount;
                                String makeTimeStr2 = LogUtil.makeTimeStr(uiThreadMonitor2.lastStackTraceTime);
                                long j72 = uiThreadMonitor2.lastStackTraceTime - uiThreadMonitor2.lastAsyncMsgHandledTimed;
                                StringBuilder m61m2 = AbstractC0662xaf167275.m61m("*** Traced call stack: count=", i2, ",", makeTimeStr2, "(");
                                m61m2.append(j72);
                                m61m2.append(" ms) ***\n");
                                Log.i("UiThreadMonitor", m61m2.toString());
                                while (r11 < r9) {
                                }
                                String sb22 = sb3.toString();
                                Log.i("UiThreadMonitor", sb22);
                                uiThreadMonitor2.lastStackTrace = sb22;
                            }
                            z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                            if (z3) {
                            }
                            if (!z3) {
                            }
                        }
                        j = -1;
                        j2 = j;
                        j3 = j2;
                        if (j3 > -1) {
                        }
                        z = uiThreadMonitor2.isPaused;
                        if (z) {
                        }
                        String m86m22 = AbstractC0866xb1ce8deb.m86m("run isPaused=", z);
                        z2 = UiThreadMonitor.DEBUG_LOG;
                        if (z2) {
                        }
                        Handler handler22 = uiThreadMonitor2.handler;
                        handler22.removeCallbacks(uiThreadMonitor2.runnable);
                        if (!z) {
                        }
                        String str22 = "wait " + j6;
                        if (z2) {
                        }
                        Boolean bool22 = (Boolean) uiThreadMonitor2.blockingDeque.poll(j6, TimeUnit.MILLISECONDS);
                        if (!z) {
                        }
                        z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                        if (z3) {
                        }
                        if (!z3) {
                        }
                    }
                }
            });
            thread.setName("UiThreadMonitor");
            return thread;
        }
    });
    public final Looper looper = Looper.getMainLooper();
    public volatile boolean isPaused = true;
    public final Lazy display$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$display$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UiThreadMonitor.this.displayManager.getDisplay(0);
        }
    });
    public final Function2 onChoreographerLog = new Function2() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$onChoreographerLog$1
        {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            int i;
            String str = (String) obj2;
            long currentTimeMillis = System.currentTimeMillis();
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            int i2 = uiThreadMonitor.lastChoreographerTotalDrawCount + 1;
            uiThreadMonitor.lastChoreographerTotalDrawCount = i2;
            if (currentTimeMillis - uiThreadMonitor.lastChoreographerLogTime >= 1000 && (i = uiThreadMonitor.lastChoreographerLogCount) < 10) {
                uiThreadMonitor.lastChoreographerLogTime = currentTimeMillis;
                int i3 = i + 1;
                uiThreadMonitor.lastChoreographerLogCount = i3;
                ExifInterface$$ExternalSyntheticOutline0.m35m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("DOZE_SUSPEND draw count=", i3, " totalCount=", i2, "\n"), str, "UiThreadMonitor");
            }
            return Unit.INSTANCE;
        }
    };
    public final UiThreadMonitor$runnable$1 runnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.setAwake(0);
        }
    };
    public final UiThreadMonitor$asyncRunnable$1 asyncRunnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.getClass();
            if (UiThreadMonitor.DEBUG_LOG) {
                Log.d("UiThreadMonitor", "handleAsyncMsg");
            }
            UiThreadMonitor.this.lastAsyncMsgHandledTimed = System.currentTimeMillis();
            if (UiThreadMonitor.this.isPaused) {
                return;
            }
            UiThreadMonitor uiThreadMonitor2 = UiThreadMonitor.this;
            Handler handler = uiThreadMonitor2.handler;
            UiThreadMonitor$asyncRunnable$1 uiThreadMonitor$asyncRunnable$1 = uiThreadMonitor2.asyncRunnable;
            handler.removeCallbacks(uiThreadMonitor$asyncRunnable$1);
            Message obtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
            obtain.setAsynchronous(true);
            handler.sendMessageDelayed(obtain, 3000L);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        DEBUG_LOG = Log.isLoggable("UiThreadMonitor", 3);
        ENABLE_PAUSE = Intrinsics.areEqual("user", Build.TYPE) && !Debug.semIsProductDev();
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1] */
    public UiThreadMonitor(Handler handler, Handler handler2, DisplayManager displayManager, DumpManager dumpManager, LooperSlowLogController looperSlowLogController) {
        this.handler = handler;
        this.bgHandler = handler2;
        this.displayManager = displayManager;
        this.dumpManager = dumpManager;
        this.looperLogController = looperSlowLogController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str = "UiThreadMonitor state:\n  monitorThread state=" + ((Thread) this.monitorThread$delegate.getValue()).getState() + ", paused=" + this.isPaused + ", count=" + this.anrCount + "\n  mainThread state=" + ((Thread) this.mainThread$delegate.getValue()).getState() + "\n  lastAsyncMsgHandledTime=" + LogUtil.makeTimeStr(this.lastAsyncMsgHandledTimed);
        String str2 = this.lastStackTrace;
        if (str2 != null) {
            str = ((Object) str) + "\n  lastStackTrace=[\n" + str2 + "  ], " + LogUtil.makeTimeStr(this.lastStackTraceTime);
        }
        printWriter.println(str);
    }

    public final void setAwake(int i) {
        int i2;
        if (i == 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (DEBUG_LOG || (i2 = this.lastDisplayState) == 2 || i2 == 3) {
                Log.i("UiThreadMonitor", "onTick count=" + this.awakeCount + " " + (elapsedRealtime - this.lastAwakeTime) + " " + (System.currentTimeMillis() - this.lastAsyncMsgHandledTimed));
            }
            this.lastAwakeTime = elapsedRealtime;
            this.awakeCount++;
        }
        try {
            this.blockingDeque.put(Boolean.TRUE);
        } catch (Throwable unused) {
        }
    }
}
