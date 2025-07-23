package android.os;

import android.media.MediaMetrics;
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d7 A[Catch: all -> 0x01aa, Exception -> 0x01ac, TryCatch #0 {Exception -> 0x01ac, blocks: (B:36:0x00d0, B:38:0x00d7, B:40:0x00dc), top: B:35:0x00d0, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00dc A[Catch: all -> 0x01aa, Exception -> 0x01ac, TRY_LEAVE, TryCatch #0 {Exception -> 0x01ac, blocks: (B:36:0x00d0, B:38:0x00d7, B:40:0x00dc), top: B:35:0x00d0, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e5 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean loopOnce(android.os.Looper r24, long r25, int r27) {
        /*
            Method dump skipped, instructions count: 444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Looper.loopOnce(android.os.Looper, long, int):boolean");
    }

    public static void loop() {
        Looper myLooper = myLooper();
        if (myLooper == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        if (myLooper.mInLoop) {
            Slog.w(TAG, "Loop again would have the queued messages be executed before this one completed.");
        }
        myLooper.mInLoop = true;
        Binder.clearCallingIdentity();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int thresholdOverride = getThresholdOverride();
        myLooper.mSlowDeliveryDetected = false;
        while (loopOnce(myLooper, clearCallingIdentity, thresholdOverride)) {
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
        String myProcessName = Process.myProcessName();
        String threadGroupToString = threadGroupToString(getThreadGroup());
        boolean z = myLooper() == getMainLooper();
        boolean isPerfLogEnable = myLooper().isPerfLogEnable();
        Slog.w(TAG, "Slow " + str + " took " + j4 + "ms " + Thread.currentThread().getName() + " app=" + myProcessName + " main=" + z + " group=" + threadGroupToString + " h=" + message.target.getClass().getName() + " c=" + message.callback + " m=" + message.what);
        if (isPerfLogEnable) {
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
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mThread.getName());
        protoOutputStream.write(1112396529666L, this.mThread.getId());
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue != null) {
            messageQueue.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.end(start);
    }

    public String toString() {
        return "Looper (" + this.mThread.getName() + ", tid " + this.mThread.getId() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
