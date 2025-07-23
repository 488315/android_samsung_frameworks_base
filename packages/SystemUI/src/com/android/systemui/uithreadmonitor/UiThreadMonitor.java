package com.android.systemui.uithreadmonitor;

import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.LogUtil;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingDeque;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            long currentTimeMillis = System.currentTimeMillis();
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            int i2 = uiThreadMonitor.lastChoreographerTotalDrawCount + 1;
            uiThreadMonitor.lastChoreographerTotalDrawCount = i2;
            if (currentTimeMillis - uiThreadMonitor.lastChoreographerLogTime >= 1000 && (i = uiThreadMonitor.lastChoreographerLogCount) < 10) {
                uiThreadMonitor.lastChoreographerLogTime = currentTimeMillis;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            /* JADX WARN: Removed duplicated region for block: B:41:0x01dd  */
                            /* JADX WARN: Removed duplicated region for block: B:49:0x01e2 A[SYNTHETIC] */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 601
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1.run():void");
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
                            @Override // java.lang.Runnable
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 601
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1.run():void");
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
                            @Override // java.lang.Runnable
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 601
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2$1.run():void");
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
