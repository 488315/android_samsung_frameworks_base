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
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
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
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class UiThreadMonitor implements Dumpable {
    public static final boolean DEBUG_LOG;
    public static final boolean ENABLE_PAUSE;
    public int anrCount;
    public int awakeCount;
    public final Handler bgHandler;
    public final Lazy display$delegate;
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
    public final Lazy mainThread$delegate;
    public final Lazy monitorThread$delegate;
    public final LinkedBlockingDeque blockingDeque = new LinkedBlockingDeque(1);
    public final Looper looper = Looper.getMainLooper();
    public volatile boolean isPaused = true;
    public final UiThreadMonitor$$ExternalSyntheticLambda3 onChoreographerLog = new Function2() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            int i;
            String str = (String) obj2;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            long jCurrentTimeMillis = System.currentTimeMillis();
            UiThreadMonitor uiThreadMonitor = this.f$0;
            int i2 = uiThreadMonitor.lastChoreographerTotalDrawCount + 1;
            uiThreadMonitor.lastChoreographerTotalDrawCount = i2;
            if (jCurrentTimeMillis - uiThreadMonitor.lastChoreographerLogTime >= 1000 && (i = uiThreadMonitor.lastChoreographerLogCount) < 10) {
                uiThreadMonitor.lastChoreographerLogTime = jCurrentTimeMillis;
                int i3 = i + 1;
                uiThreadMonitor.lastChoreographerLogCount = i3;
                ExifInterface$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(i3, i2, "DOZE_SUSPEND draw count=", " totalCount=", "\n"), str, "UiThreadMonitor");
            }
            return Unit.INSTANCE;
        }
    };
    public final UiThreadMonitor$runnable$1 runnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = this.this$0;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.setAwake(0);
        }
    };
    public final UiThreadMonitor$asyncRunnable$1 asyncRunnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = this.this$0;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.getClass();
            if (UiThreadMonitor.DEBUG_LOG) {
                Log.d("UiThreadMonitor", "handleAsyncMsg");
            }
            this.this$0.lastAsyncMsgHandledTimed = System.currentTimeMillis();
            if (this.this$0.isPaused) {
                return;
            }
            UiThreadMonitor uiThreadMonitor2 = this.this$0;
            Handler handler = uiThreadMonitor2.handler;
            UiThreadMonitor$asyncRunnable$1 uiThreadMonitor$asyncRunnable$1 = uiThreadMonitor2.asyncRunnable;
            handler.removeCallbacks(uiThreadMonitor$asyncRunnable$1);
            Message messageObtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
            messageObtain.setAsynchronous(true);
            handler.sendMessageDelayed(messageObtain, 3000L);
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DEBUG_LOG = Log.isLoggable("UiThreadMonitor", 3);
        ENABLE_PAUSE = "user".equals(Build.TYPE) && !Debug.semIsProductDev();
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda3] */
    public UiThreadMonitor(Handler handler, Handler handler2, DisplayManager displayManager, DumpManager dumpManager, LooperSlowLogController looperSlowLogController) {
        this.handler = handler;
        this.bgHandler = handler2;
        this.displayManager = displayManager;
        this.dumpManager = dumpManager;
        this.looperLogController = looperSlowLogController;
        final int i = 0;
        this.mainThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda0
            public final /* synthetic */ UiThreadMonitor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final UiThreadMonitor uiThreadMonitor = this.f$0;
                switch (i) {
                    case 0:
                        return uiThreadMonitor.looper.getThread();
                    case 1:
                        boolean z = UiThreadMonitor.DEBUG_LOG;
                        Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1
                            /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
                            /* JADX WARN: Removed duplicated region for block: B:56:0x01dd  */
                            /* JADX WARN: Removed duplicated region for block: B:74:0x01e2 A[SYNTHETIC] */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() throws SecurityException, IllegalArgumentException {
                                long j;
                                long j2;
                                boolean z2;
                                boolean z3;
                                Boolean bool;
                                UiThreadMonitor uiThreadMonitor2 = uiThreadMonitor;
                                boolean z4 = false;
                                uiThreadMonitor2.isPaused = false;
                                Process.setThreadPriority(19);
                                do {
                                    if (SystemProperties.getBoolean("debug.sysui.looper.msg_log", z4)) {
                                        if (!uiThreadMonitor2.looperMsgLog) {
                                            uiThreadMonitor2.looper.setMessageLogging(new Printer() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$updateLooperMsgLog$1
                                                @Override // android.util.Printer
                                                public final void println(String str) {
                                                    Log.d("UiThreadMonitor", str);
                                                }
                                            });
                                            uiThreadMonitor2.looperMsgLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperMsgLog) {
                                        uiThreadMonitor2.looper.setMessageLogging(null);
                                        uiThreadMonitor2.looperMsgLog = z4;
                                    }
                                    if (SystemProperties.getBoolean("debug.sysui.looper.slow_log", z4)) {
                                        if (uiThreadMonitor2.looperSlowLog) {
                                            j2 = -1;
                                            j = -1;
                                        } else {
                                            j = SystemProperties.getLong("debug.sysui.looper.slow_dispatch", 30L);
                                            j2 = SystemProperties.getLong("debug.sysui.looper.slow_delivery", 30L);
                                            uiThreadMonitor2.looperSlowLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperSlowLog) {
                                        uiThreadMonitor2.looperSlowLog = z4;
                                        j = 0;
                                        j2 = 0;
                                    }
                                    if (j > -1 && j2 > -1) {
                                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("updateShowLooperSlowLog dispatch=", j, " ms, delivery=");
                                        sbM.append(j2);
                                        sbM.append(" ms");
                                        Log.d("UiThreadMonitor", sbM.toString());
                                        long j3 = j;
                                        ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(0, j3, j3, 0L, false, null);
                                    }
                                    boolean z5 = uiThreadMonitor2.isPaused;
                                    long j4 = z5 ? 86400000L : 6000L;
                                    String strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("run isPaused=", z5);
                                    boolean z6 = UiThreadMonitor.DEBUG_LOG;
                                    if (z6) {
                                        Log.d("UiThreadMonitor", strM);
                                    }
                                    Handler handler3 = uiThreadMonitor2.handler;
                                    handler3.removeCallbacks(uiThreadMonitor2.runnable);
                                    if (z5) {
                                        z2 = true;
                                    } else {
                                        z2 = true;
                                        handler3.postDelayed(uiThreadMonitor2.runnable, 3000L);
                                    }
                                    try {
                                        String str = "wait " + j4;
                                        if (z6) {
                                            Log.d("UiThreadMonitor", str);
                                        }
                                        bool = (Boolean) uiThreadMonitor2.blockingDeque.poll(j4, TimeUnit.MILLISECONDS);
                                    } finally {
                                        try {
                                            if (!z5) {
                                                String string = sb.toString();
                                                Log.i("UiThreadMonitor", string);
                                                uiThreadMonitor2.lastStackTrace = string;
                                            }
                                            z4 = false;
                                            z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                            if (!z3) {
                                            }
                                        } catch (Throwable th) {
                                        }
                                    }
                                    if (!z5 && bool == null) {
                                        uiThreadMonitor2.anrCount++;
                                        uiThreadMonitor2.lastStackTraceTime = System.currentTimeMillis();
                                        StackTraceElement[] stackTrace = ((Thread) uiThreadMonitor2.mainThread$delegate.getValue()).getStackTrace();
                                        StringBuilder sb = new StringBuilder();
                                        int i2 = uiThreadMonitor2.anrCount;
                                        String strMakeTimeStr = LogUtil.makeTimeStr(uiThreadMonitor2.lastStackTraceTime);
                                        long j5 = uiThreadMonitor2.lastStackTraceTime - uiThreadMonitor2.lastAsyncMsgHandledTimed;
                                        StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i2, "*** Traced call stack: count=", ",", strMakeTimeStr, "(");
                                        sbM2.append(j5);
                                        sbM2.append(" ms) ***\n");
                                        Log.i("UiThreadMonitor", sbM2.toString());
                                        ArrayIterator arrayIterator = new ArrayIterator(stackTrace);
                                        while (arrayIterator.hasNext()) {
                                            sb.append("    at " + ((StackTraceElement) arrayIterator.next()) + "\n");
                                        }
                                        String string2 = sb.toString();
                                        Log.i("UiThreadMonitor", string2);
                                        uiThreadMonitor2.lastStackTrace = string2;
                                    }
                                    z4 = false;
                                    z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                    if (!z3) {
                                        Log.d("UiThreadMonitor", "disabled");
                                    }
                                } while (!z3);
                                uiThreadMonitor2.isPaused = z2;
                            }
                        });
                        thread.setName("UiThreadMonitor");
                        return thread;
                    default:
                        return uiThreadMonitor.displayManager.getDisplay(0);
                }
            }
        });
        final int i2 = 1;
        this.monitorThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda0
            public final /* synthetic */ UiThreadMonitor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final UiThreadMonitor uiThreadMonitor = this.f$0;
                switch (i2) {
                    case 0:
                        return uiThreadMonitor.looper.getThread();
                    case 1:
                        boolean z = UiThreadMonitor.DEBUG_LOG;
                        Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1
                            /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
                            /* JADX WARN: Removed duplicated region for block: B:56:0x01dd  */
                            /* JADX WARN: Removed duplicated region for block: B:74:0x01e2 A[SYNTHETIC] */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() throws SecurityException, IllegalArgumentException {
                                long j;
                                long j2;
                                boolean z2;
                                boolean z3;
                                Boolean bool;
                                UiThreadMonitor uiThreadMonitor2 = uiThreadMonitor;
                                boolean z4 = false;
                                uiThreadMonitor2.isPaused = false;
                                Process.setThreadPriority(19);
                                do {
                                    if (SystemProperties.getBoolean("debug.sysui.looper.msg_log", z4)) {
                                        if (!uiThreadMonitor2.looperMsgLog) {
                                            uiThreadMonitor2.looper.setMessageLogging(new Printer() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$updateLooperMsgLog$1
                                                @Override // android.util.Printer
                                                public final void println(String str) {
                                                    Log.d("UiThreadMonitor", str);
                                                }
                                            });
                                            uiThreadMonitor2.looperMsgLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperMsgLog) {
                                        uiThreadMonitor2.looper.setMessageLogging(null);
                                        uiThreadMonitor2.looperMsgLog = z4;
                                    }
                                    if (SystemProperties.getBoolean("debug.sysui.looper.slow_log", z4)) {
                                        if (uiThreadMonitor2.looperSlowLog) {
                                            j2 = -1;
                                            j = -1;
                                        } else {
                                            j = SystemProperties.getLong("debug.sysui.looper.slow_dispatch", 30L);
                                            j2 = SystemProperties.getLong("debug.sysui.looper.slow_delivery", 30L);
                                            uiThreadMonitor2.looperSlowLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperSlowLog) {
                                        uiThreadMonitor2.looperSlowLog = z4;
                                        j = 0;
                                        j2 = 0;
                                    }
                                    if (j > -1 && j2 > -1) {
                                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("updateShowLooperSlowLog dispatch=", j, " ms, delivery=");
                                        sbM.append(j2);
                                        sbM.append(" ms");
                                        Log.d("UiThreadMonitor", sbM.toString());
                                        long j3 = j;
                                        ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(0, j3, j3, 0L, false, null);
                                    }
                                    boolean z5 = uiThreadMonitor2.isPaused;
                                    long j4 = z5 ? 86400000L : 6000L;
                                    String strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("run isPaused=", z5);
                                    boolean z6 = UiThreadMonitor.DEBUG_LOG;
                                    if (z6) {
                                        Log.d("UiThreadMonitor", strM);
                                    }
                                    Handler handler3 = uiThreadMonitor2.handler;
                                    handler3.removeCallbacks(uiThreadMonitor2.runnable);
                                    if (z5) {
                                        z2 = true;
                                    } else {
                                        z2 = true;
                                        handler3.postDelayed(uiThreadMonitor2.runnable, 3000L);
                                    }
                                    try {
                                        String str = "wait " + j4;
                                        if (z6) {
                                            Log.d("UiThreadMonitor", str);
                                        }
                                        bool = (Boolean) uiThreadMonitor2.blockingDeque.poll(j4, TimeUnit.MILLISECONDS);
                                    } finally {
                                        try {
                                            if (!z5) {
                                                String string2 = sb.toString();
                                                Log.i("UiThreadMonitor", string2);
                                                uiThreadMonitor2.lastStackTrace = string2;
                                            }
                                            z4 = false;
                                            z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                            if (!z3) {
                                            }
                                        } catch (Throwable th) {
                                        }
                                    }
                                    if (!z5 && bool == null) {
                                        uiThreadMonitor2.anrCount++;
                                        uiThreadMonitor2.lastStackTraceTime = System.currentTimeMillis();
                                        StackTraceElement[] stackTrace = ((Thread) uiThreadMonitor2.mainThread$delegate.getValue()).getStackTrace();
                                        StringBuilder sb = new StringBuilder();
                                        int i22 = uiThreadMonitor2.anrCount;
                                        String strMakeTimeStr = LogUtil.makeTimeStr(uiThreadMonitor2.lastStackTraceTime);
                                        long j5 = uiThreadMonitor2.lastStackTraceTime - uiThreadMonitor2.lastAsyncMsgHandledTimed;
                                        StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i22, "*** Traced call stack: count=", ",", strMakeTimeStr, "(");
                                        sbM2.append(j5);
                                        sbM2.append(" ms) ***\n");
                                        Log.i("UiThreadMonitor", sbM2.toString());
                                        ArrayIterator arrayIterator = new ArrayIterator(stackTrace);
                                        while (arrayIterator.hasNext()) {
                                            sb.append("    at " + ((StackTraceElement) arrayIterator.next()) + "\n");
                                        }
                                        String string22 = sb.toString();
                                        Log.i("UiThreadMonitor", string22);
                                        uiThreadMonitor2.lastStackTrace = string22;
                                    }
                                    z4 = false;
                                    z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                    if (!z3) {
                                        Log.d("UiThreadMonitor", "disabled");
                                    }
                                } while (!z3);
                                uiThreadMonitor2.isPaused = z2;
                            }
                        });
                        thread.setName("UiThreadMonitor");
                        return thread;
                    default:
                        return uiThreadMonitor.displayManager.getDisplay(0);
                }
            }
        });
        final int i3 = 2;
        this.display$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda0
            public final /* synthetic */ UiThreadMonitor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final UiThreadMonitor uiThreadMonitor = this.f$0;
                switch (i3) {
                    case 0:
                        return uiThreadMonitor.looper.getThread();
                    case 1:
                        boolean z = UiThreadMonitor.DEBUG_LOG;
                        Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1
                            /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
                            /* JADX WARN: Removed duplicated region for block: B:56:0x01dd  */
                            /* JADX WARN: Removed duplicated region for block: B:74:0x01e2 A[SYNTHETIC] */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() throws SecurityException, IllegalArgumentException {
                                long j;
                                long j2;
                                boolean z2;
                                boolean z3;
                                Boolean bool;
                                UiThreadMonitor uiThreadMonitor2 = uiThreadMonitor;
                                boolean z4 = false;
                                uiThreadMonitor2.isPaused = false;
                                Process.setThreadPriority(19);
                                do {
                                    if (SystemProperties.getBoolean("debug.sysui.looper.msg_log", z4)) {
                                        if (!uiThreadMonitor2.looperMsgLog) {
                                            uiThreadMonitor2.looper.setMessageLogging(new Printer() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$updateLooperMsgLog$1
                                                @Override // android.util.Printer
                                                public final void println(String str) {
                                                    Log.d("UiThreadMonitor", str);
                                                }
                                            });
                                            uiThreadMonitor2.looperMsgLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperMsgLog) {
                                        uiThreadMonitor2.looper.setMessageLogging(null);
                                        uiThreadMonitor2.looperMsgLog = z4;
                                    }
                                    if (SystemProperties.getBoolean("debug.sysui.looper.slow_log", z4)) {
                                        if (uiThreadMonitor2.looperSlowLog) {
                                            j2 = -1;
                                            j = -1;
                                        } else {
                                            j = SystemProperties.getLong("debug.sysui.looper.slow_dispatch", 30L);
                                            j2 = SystemProperties.getLong("debug.sysui.looper.slow_delivery", 30L);
                                            uiThreadMonitor2.looperSlowLog = true;
                                        }
                                    } else if (uiThreadMonitor2.looperSlowLog) {
                                        uiThreadMonitor2.looperSlowLog = z4;
                                        j = 0;
                                        j2 = 0;
                                    }
                                    if (j > -1 && j2 > -1) {
                                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("updateShowLooperSlowLog dispatch=", j, " ms, delivery=");
                                        sbM.append(j2);
                                        sbM.append(" ms");
                                        Log.d("UiThreadMonitor", sbM.toString());
                                        long j3 = j;
                                        ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(0, j3, j3, 0L, false, null);
                                    }
                                    boolean z5 = uiThreadMonitor2.isPaused;
                                    long j4 = z5 ? 86400000L : 6000L;
                                    String strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("run isPaused=", z5);
                                    boolean z6 = UiThreadMonitor.DEBUG_LOG;
                                    if (z6) {
                                        Log.d("UiThreadMonitor", strM);
                                    }
                                    Handler handler3 = uiThreadMonitor2.handler;
                                    handler3.removeCallbacks(uiThreadMonitor2.runnable);
                                    if (z5) {
                                        z2 = true;
                                    } else {
                                        z2 = true;
                                        handler3.postDelayed(uiThreadMonitor2.runnable, 3000L);
                                    }
                                    try {
                                        String str = "wait " + j4;
                                        if (z6) {
                                            Log.d("UiThreadMonitor", str);
                                        }
                                        bool = (Boolean) uiThreadMonitor2.blockingDeque.poll(j4, TimeUnit.MILLISECONDS);
                                    } finally {
                                        try {
                                            if (!z5) {
                                                String string22 = sb.toString();
                                                Log.i("UiThreadMonitor", string22);
                                                uiThreadMonitor2.lastStackTrace = string22;
                                            }
                                            z4 = false;
                                            z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                            if (!z3) {
                                            }
                                        } catch (Throwable th) {
                                        }
                                    }
                                    if (!z5 && bool == null) {
                                        uiThreadMonitor2.anrCount++;
                                        uiThreadMonitor2.lastStackTraceTime = System.currentTimeMillis();
                                        StackTraceElement[] stackTrace = ((Thread) uiThreadMonitor2.mainThread$delegate.getValue()).getStackTrace();
                                        StringBuilder sb = new StringBuilder();
                                        int i22 = uiThreadMonitor2.anrCount;
                                        String strMakeTimeStr = LogUtil.makeTimeStr(uiThreadMonitor2.lastStackTraceTime);
                                        long j5 = uiThreadMonitor2.lastStackTraceTime - uiThreadMonitor2.lastAsyncMsgHandledTimed;
                                        StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i22, "*** Traced call stack: count=", ",", strMakeTimeStr, "(");
                                        sbM2.append(j5);
                                        sbM2.append(" ms) ***\n");
                                        Log.i("UiThreadMonitor", sbM2.toString());
                                        ArrayIterator arrayIterator = new ArrayIterator(stackTrace);
                                        while (arrayIterator.hasNext()) {
                                            sb.append("    at " + ((StackTraceElement) arrayIterator.next()) + "\n");
                                        }
                                        String string222 = sb.toString();
                                        Log.i("UiThreadMonitor", string222);
                                        uiThreadMonitor2.lastStackTrace = string222;
                                    }
                                    z4 = false;
                                    z3 = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
                                    if (!z3) {
                                        Log.d("UiThreadMonitor", "disabled");
                                    }
                                } while (!z3);
                                uiThreadMonitor2.isPaused = z2;
                            }
                        });
                        thread.setName("UiThreadMonitor");
                        return thread;
                    default:
                        return uiThreadMonitor.displayManager.getDisplay(0);
                }
            }
        });
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
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (DEBUG_LOG || (i2 = this.lastDisplayState) == 2 || i2 == 3) {
                Log.i("UiThreadMonitor", "onTick count=" + this.awakeCount + " " + (jElapsedRealtime - this.lastAwakeTime) + " " + (System.currentTimeMillis() - this.lastAsyncMsgHandledTimed));
            }
            this.lastAwakeTime = jElapsedRealtime;
            this.awakeCount++;
        }
        try {
            this.blockingDeque.put(Boolean.TRUE);
        } catch (Throwable unused) {
        }
    }
}
