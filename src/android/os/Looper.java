package android.os;

import android.media.MediaMetrics;
import android.util.Log;
import android.util.PerfLog;
import android.util.Printer;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Looper {
    private static final String TAG = "Looper";
    private static Looper sMainLooper;
    private static Observer sObserver;
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private boolean mInLoop;
    private Printer mLogging;
    final MessageQueue mQueue;
    private boolean mSlowDeliveryDetected;
    private long mSlowDeliveryThresholdMs;
    private long mSlowDispatchThresholdMs;
    private long mTraceTag;
    private boolean mPerfLogStart = false;
    final Thread mThread = Thread.currentThread();

    public interface Observer {
        void dispatchingThrewException(Object obj, Message message, Exception exc);

        Object messageDispatchStarting();

        void messageDispatched(Object obj, Message message);
    }

    private static int getThresholdOverride$ravenwood() {
        return -1;
    }

    private static class NoImagePreloadHolder {
        private static final boolean sVerboseLogging = SystemProperties.getBoolean("log.looper.slow.verbose", false);

        private NoImagePreloadHolder() {
        }
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean z) {
        ThreadLocal<Looper> threadLocal = sThreadLocal;
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper(z));
    }

    @Deprecated
    public static void prepareMainLooper() {
        prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    public static Looper getMainLooper() {
        Looper looper;
        synchronized (Looper.class) {
            looper = sMainLooper;
        }
        return looper;
    }

    public void setPerfLogEnable() {
        synchronized (Looper.class) {
            this.mPerfLogStart = true;
        }
    }

    public boolean isPerfLogEnable() {
        boolean z;
        synchronized (Looper.class) {
            z = this.mPerfLogStart;
        }
        return z;
    }

    public static void setMainLooperForTest(Looper looper) {
        synchronized (Looper.class) {
            sMainLooper = (Looper) Objects.requireNonNull(looper);
        }
    }

    public static void clearMainLooperForTest() {
        synchronized (Looper.class) {
            sMainLooper = null;
        }
    }

    public static void setObserver(Observer observer) {
        sObserver = observer;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d7 A[Catch: all -> 0x01aa, Exception -> 0x01ac, TryCatch #0 {Exception -> 0x01ac, blocks: (B:47:0x00d0, B:49:0x00d7, B:51:0x00dc), top: B:92:0x00d0, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dc A[Catch: all -> 0x01aa, Exception -> 0x01ac, TRY_LEAVE, TryCatch #0 {Exception -> 0x01ac, blocks: (B:47:0x00d0, B:49:0x00d7, B:51:0x00dc), top: B:92:0x00d0, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e5 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0156  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean loopOnce(Looper looper, long j, int i) {
        long j2;
        long jUptimeMillis;
        boolean z;
        boolean z2;
        Object objMessageDispatchStarting;
        long uid;
        long j3;
        long jClearCallingIdentity;
        boolean zShowSlowLog;
        Message next = looper.mQueue.next();
        if (next == null) {
            return false;
        }
        PerfettoTrace.begin(PerfettoTrace.MQ_CATEGORY, "message_queue_receive").beginProto().beginNested(2004L).addField(1L, next.mSendingThreadName).endNested().endProto().setTerminatingFlow(next.mEventId.get()).emit();
        Printer printer = looper.mLogging;
        if (printer != null) {
            printer.println(">>>>> Dispatching to " + next.target + " " + next.callback + ": " + next.what);
        }
        Observer observer = sObserver;
        long j4 = looper.mTraceTag;
        long j5 = looper.mSlowDispatchThresholdMs;
        long j6 = looper.mSlowDeliveryThresholdMs;
        boolean z3 = i >= 0;
        if (z3) {
            j6 = i;
            j2 = j6;
        } else {
            j2 = j5;
        }
        try {
            try {
                if (j6 > 0 || z3) {
                    jUptimeMillis = 0;
                    if (next.when > 0) {
                        z = true;
                    }
                    z2 = j2 <= jUptimeMillis || z3;
                    boolean z4 = !z || z2;
                    if (j4 != jUptimeMillis && Trace.isTagEnabled(j4)) {
                        Trace.traceBegin(j4, next.target.getTraceName(next));
                    }
                    long jUptimeMillis2 = !z4 ? SystemClock.uptimeMillis() : jUptimeMillis;
                    objMessageDispatchStarting = observer == null ? observer.messageDispatchStarting() : null;
                    uid = ThreadLocalWorkSource.setUid(next.workSourceUid);
                    next.target.dispatchMessage(next);
                    if (observer != null) {
                        observer.messageDispatched(objMessageDispatchStarting, next);
                    }
                    if (z2) {
                        jUptimeMillis = SystemClock.uptimeMillis();
                    }
                    if (z) {
                        j3 = jUptimeMillis2;
                    } else {
                        if (!looper.mSlowDeliveryDetected || NoImagePreloadHolder.sVerboseLogging) {
                            long j7 = jUptimeMillis2;
                            zShowSlowLog = showSlowLog(j6, next.when, j7, "delivery", next);
                            j3 = j7;
                        } else {
                            zShowSlowLog = false;
                            j3 = jUptimeMillis2;
                        }
                        if (looper.mSlowDeliveryDetected) {
                            if (!zShowSlowLog && j3 - next.when <= 10) {
                                Slog.w(TAG, "Drained");
                                looper.mSlowDeliveryDetected = false;
                            }
                        } else if (zShowSlowLog) {
                            looper.mSlowDeliveryDetected = true;
                        }
                    }
                    if (z2) {
                        showSlowLog(j2, j3, jUptimeMillis, "dispatch", next);
                    }
                    if (printer != null) {
                        printer.println("<<<<< Finished to " + next.target + " " + next.callback);
                    }
                    jClearCallingIdentity = Binder.clearCallingIdentity();
                    if (j != jClearCallingIdentity) {
                        Log.wtf(TAG, "Thread identity changed from 0x" + Long.toHexString(j) + " to 0x" + Long.toHexString(jClearCallingIdentity) + " while dispatching to " + next.target.getClass().getName() + " " + next.callback + " what=" + next.what);
                    }
                    PerfettoTrace.end(PerfettoTrace.MQ_CATEGORY).emit();
                    next.recycleUnchecked();
                    return true;
                }
                jUptimeMillis = 0;
                next.target.dispatchMessage(next);
                if (observer != null) {
                }
                if (z2) {
                }
                if (z) {
                }
                if (z2) {
                }
                if (printer != null) {
                }
                jClearCallingIdentity = Binder.clearCallingIdentity();
                if (j != jClearCallingIdentity) {
                }
                PerfettoTrace.end(PerfettoTrace.MQ_CATEGORY).emit();
                next.recycleUnchecked();
                return true;
            } catch (Exception e) {
                if (observer != null) {
                    observer.dispatchingThrewException(objMessageDispatchStarting, next, e);
                }
                throw e;
            }
        } finally {
            ThreadLocalWorkSource.restore(uid);
            if (j4 != jUptimeMillis) {
                Trace.traceEnd(j4);
            }
        }
        z = false;
        if (j2 <= jUptimeMillis) {
        }
        if (z) {
        }
        if (j4 != jUptimeMillis) {
            Trace.traceBegin(j4, next.target.getTraceName(next));
        }
        if (!z4) {
        }
        if (observer == null) {
        }
        uid = ThreadLocalWorkSource.setUid(next.workSourceUid);
    }

    public static void loop() {
        Looper looperMyLooper = myLooper();
        if (looperMyLooper == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        if (looperMyLooper.mInLoop) {
            Slog.w(TAG, "Loop again would have the queued messages be executed before this one completed.");
        }
        looperMyLooper.mInLoop = true;
        Binder.clearCallingIdentity();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        int thresholdOverride = getThresholdOverride();
        looperMyLooper.mSlowDeliveryDetected = false;
        while (loopOnce(looperMyLooper, jClearCallingIdentity, thresholdOverride)) {
        }
    }

    private static int getThresholdOverride() {
        int i;
        if (myLooper() == getMainLooper() && (i = SystemProperties.getInt("log.looper.any.main.slow", -1)) >= 0) {
            return i;
        }
        int i2 = SystemProperties.getInt("log.looper." + Process.myUid() + ".any.slow", -1);
        if (i2 >= 0) {
            return i2;
        }
        return SystemProperties.getInt("log.looper." + Process.myUid() + MediaMetrics.SEPARATOR + Thread.currentThread().getName() + ".slow", -1);
    }

    private static int getThreadGroup() {
        if (Process.isIsolated()) {
            return -1;
        }
        return Process.getProcessGroup(Process.myTid());
    }

    private static String threadGroupToString(int i) {
        switch (i) {
            case 0:
                return "BACKGROUND";
            case 1:
                return "FOREGROUND";
            case 2:
                return "SYSTEM";
            case 3:
                return "AUDIO_APP";
            case 4:
                return "AUDIO_SYS";
            case 5:
                return "TOP_APP";
            case 6:
                return "RT_APP";
            case 7:
                return "RESTRICTED";
            default:
                return "UNKNOWN";
        }
    }

    private static boolean showSlowLog(long j, long j2, long j3, String str, Message message) {
        long j4 = j3 - j2;
        if (j4 < j) {
            return false;
        }
        String strMyProcessName = Process.myProcessName();
        String strThreadGroupToString = threadGroupToString(getThreadGroup());
        boolean z = myLooper() == getMainLooper();
        boolean zIsPerfLogEnable = myLooper().isPerfLogEnable();
        Slog.w(TAG, "Slow " + str + " took " + j4 + "ms " + Thread.currentThread().getName() + " app=" + strMyProcessName + " main=" + z + " group=" + strThreadGroupToString + " h=" + message.target.getClass().getName() + " c=" + message.callback + " m=" + message.what);
        if (zIsPerfLogEnable) {
            PerfLog.d(6, " Slow" + str + " took " + j4 + "ms " + Thread.currentThread().getName() + " h=" + message.target.getClass().getName() + " c=" + message.callback + " m=" + message.what);
        }
        return true;
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static MessageQueue myQueue() {
        return myLooper().mQueue;
    }

    private Looper(boolean z) {
        this.mQueue = new MessageQueue(z);
    }

    public boolean isCurrentThread() {
        return Thread.currentThread() == this.mThread;
    }

    public void setMessageLogging(Printer printer) {
        this.mLogging = printer;
    }

    public void setTraceTag(long j) {
        this.mTraceTag = j;
    }

    public void setSlowLogThresholdMs(long j, long j2) {
        this.mSlowDispatchThresholdMs = j;
        this.mSlowDeliveryThresholdMs = j2;
    }

    public void quit() {
        this.mQueue.quit(false);
    }

    public void quitSafely() {
        this.mQueue.quit(true);
    }

    public Thread getThread() {
        return this.mThread;
    }

    public MessageQueue getQueue() {
        return this.mQueue;
    }

    public void dump(Printer printer, String str) {
        printer.println(str + toString());
        this.mQueue.dump(printer, str + "  ", null);
    }

    public void dump(Printer printer, String str, Handler handler) {
        printer.println(str + toString());
        this.mQueue.dump(printer, str + "  ", handler);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mThread.getName());
        protoOutputStream.write(1112396529666L, this.mThread.getId());
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue != null) {
            messageQueue.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.end(jStart);
    }

    public String toString() {
        return "Looper (" + this.mThread.getName() + ", tid " + this.mThread.getId() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
