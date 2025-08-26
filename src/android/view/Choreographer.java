package android.view;

import android.animation.AnimationHandler;
import android.app.ActivityThread;
import android.app.jank.AppJankStats;
import android.graphics.FrameInfo;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TimeUtils;
import android.view.DisplayEventReceiver;
import android.view.animation.AnimationUtils;
import android.view.flags.Flags;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class Choreographer {
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_COMMIT = 4;
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_INSETS_ANIMATION = 2;
    private static final int CALLBACK_LAST = 4;
    public static final int CALLBACK_TRAVERSAL = 3;
    private static final boolean DEBUG_FRAMES = false;
    private static final boolean DEBUG_JANK = false;
    private static final long DEFAULT_FRAME_DELAY = 10;
    private static final long DEFAULT_THRESHOLD_BG_DELAY = 4000;
    private static final String IDS_TAG = "IDS_TAG";
    private static final int MSG_DO_FRAME = 0;
    private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
    private static final int MSG_DO_SCHEDULE_VSYNC = 1;
    private static final int MSG_UPDATE_ACTIVITY_STATE = 3;
    private static final String TAG = "Choreographer";
    private static volatile Choreographer mMainInstance = null;
    private static volatile long sFrameDelay = 10;
    public final int DO_AID;
    public final int DO_DOT;
    public final int DO_IDS;
    public final int DO_STB;
    private final boolean ENABLE_STB_ANIMATION;
    private boolean mBgWaitingDelaySetting;
    private final BufferStuffingState mBufferStuffingState;
    private CallbackRecord mCallbackPool;
    private final CallbackQueue[] mCallbackQueues;
    private boolean mCallbacksRunning;
    private int mDebugCallStackCnt;
    private BiConsumer<String, String> mDebugCallbackConsumer;
    private int mDebugDispatchThresholdMs;
    private boolean mDebugPrintNextFrameTimeDelta;
    private final FrameDisplayEventReceiver mDisplayEventReceiver;
    private boolean mEnabledDebugCallback;
    private int mFPSDivisor;
    private final FrameData mFrameData;
    FrameInfo mFrameInfo;

    @Deprecated
    private long mFrameIntervalNanos;
    private boolean mFrameScheduled;
    private long mFramesSinceSTB;
    private final FrameHandler mHandler;
    private volatile boolean mInDoFrameCallback;
    private boolean mIsFg;
    private boolean mIsFirstBBA;
    private boolean mIsVisible;
    private long mLastFrameIntervalNanos;
    private long mLastFrameTimeNanos;
    private long mLastNoOffsetFrameTimeNanos;
    private final DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
    private final Object mLock;
    private final Looper mLooper;
    private long mSTBCount;
    private static final ThreadLocal<Choreographer> sThreadInstance = new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper == null) {
                throw new IllegalStateException("The current thread must have a looper!");
            }
            Choreographer choreographer = new Choreographer(looperMyLooper, 0);
            if (looperMyLooper == Looper.getMainLooper()) {
                Choreographer.mMainInstance = choreographer;
            }
            return choreographer;
        }
    };
    private static final ThreadLocal<Choreographer> sSfThreadInstance = new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper == null) {
                throw new IllegalStateException("The current thread must have a looper!");
            }
            return new Choreographer(looperMyLooper, 1);
        }
    };
    private static final boolean USE_VSYNC = SystemProperties.getBoolean("debug.choreographer.vsync", true);
    private static final boolean USE_FRAME_TIME = SystemProperties.getBoolean("debug.choreographer.frametime", true);
    private static final int SKIPPED_FRAME_WARNING_LIMIT = SystemProperties.getInt("debug.choreographer.skipwarning", 30);
    private static final Object FRAME_CALLBACK_TOKEN = new Object() { // from class: android.view.Choreographer.3
        public String toString() {
            return "FRAME_CALLBACK_TOKEN";
        }
    };
    private static final Object VSYNC_CALLBACK_TOKEN = new Object() { // from class: android.view.Choreographer.4
        public String toString() {
            return "VSYNC_CALLBACK_TOKEN";
        }
    };
    private static final String[] CALLBACK_TRACE_TITLES = {"input", AppJankStats.WIDGET_CATEGORY_ANIMATION, "insets_animation", "traversal", "commit"};

    public interface FrameCallback {
        void doFrame(long j);
    }

    public interface VsyncCallback {
        void onVsync(FrameData frameData);
    }

    private static class BufferStuffingState {
        public boolean isRecovering;
        public AtomicBoolean isStuffed;
        public int numberWaitsForNextVsync;

        enum RecoveryAction {
            NONE,
            OFFSET,
            DELAY_FRAME
        }

        private BufferStuffingState() {
            this.isStuffed = new AtomicBoolean(false);
            this.isRecovering = false;
            this.numberWaitsForNextVsync = 0;
        }

        public void reset() {
            this.isStuffed.set(false);
            this.isRecovering = false;
            this.numberWaitsForNextVsync = 0;
        }
    }

    public void onWaitForBufferRelease(long j) {
        if (j > this.mLastFrameIntervalNanos / 2) {
            this.mBufferStuffingState.isStuffed.set(true);
        }
    }

    private Choreographer(Looper looper, int i) {
        this(looper, i, 0L);
    }

    private Choreographer(Looper looper, int i, long j) {
        this.DO_AID = 0;
        this.DO_DOT = 1;
        this.DO_IDS = 2;
        this.DO_STB = 3;
        this.mFramesSinceSTB = Long.MIN_VALUE;
        this.mSTBCount = 0L;
        this.ENABLE_STB_ANIMATION = SystemProperties.getBoolean("debug.stb.animation", true);
        this.mIsFg = true;
        this.mIsFirstBBA = true;
        this.mIsVisible = true;
        this.mBgWaitingDelaySetting = false;
        this.mLock = new Object();
        this.mFPSDivisor = 1;
        this.mLastVsyncEventData = new DisplayEventReceiver.VsyncEventData();
        this.mFrameData = new FrameData();
        this.mInDoFrameCallback = false;
        this.mBufferStuffingState = new BufferStuffingState();
        this.mFrameInfo = new FrameInfo();
        this.mEnabledDebugCallback = false;
        this.mDebugCallbackConsumer = null;
        this.mDebugCallStackCnt = 5;
        this.mDebugDispatchThresholdMs = 20;
        this.mLooper = looper;
        this.mHandler = new FrameHandler(looper);
        this.mDisplayEventReceiver = USE_VSYNC ? new FrameDisplayEventReceiver(looper, i, j) : null;
        this.mLastFrameTimeNanos = Long.MIN_VALUE;
        this.mFrameIntervalNanos = (long) (1.0E9f / getRefreshRate());
        this.mCallbackQueues = new CallbackQueue[5];
        for (int i2 = 0; i2 <= 4; i2++) {
            this.mCallbackQueues[i2] = new CallbackQueue();
        }
        setFPSDivisor(SystemProperties.getInt(ThreadedRenderer.DEBUG_FPS_DIVISOR, 1));
    }

    private static float getRefreshRate() {
        return DisplayManagerGlobal.getInstance().getDisplayInfo(0).getRefreshRate();
    }

    public static Choreographer getInstance() {
        Choreographer choreographer = sThreadInstance.get();
        if (mMainInstance != null && choreographer != mMainInstance) {
            choreographer.mIsFg = true;
        }
        return choreographer;
    }

    @Deprecated
    public static Choreographer getSfInstance() {
        return sSfThreadInstance.get();
    }

    static Choreographer getInstanceForSurfaceControl(long j, Looper looper) {
        if (looper == null) {
            throw new IllegalStateException("The current thread must have a looper!");
        }
        return new Choreographer(looper, 0, j);
    }

    public static Choreographer getMainThreadInstance() {
        return mMainInstance;
    }

    public static void releaseInstance() {
        ThreadLocal<Choreographer> threadLocal = sThreadInstance;
        Choreographer choreographer = threadLocal.get();
        threadLocal.remove();
        choreographer.dispose();
    }

    private void dispose() {
        this.mDisplayEventReceiver.dispose();
    }

    void invalidate() {
        dispose();
    }

    boolean isTheLooperSame(Looper looper) {
        return this.mLooper == looper;
    }

    public Looper getLooper() {
        return this.mLooper;
    }

    public static long getFrameDelay() {
        return sFrameDelay;
    }

    public static void setFrameDelay(long j) {
        sFrameDelay = j;
    }

    public static long subtractFrameDelay(long j) {
        long j2 = sFrameDelay;
        if (j <= j2) {
            return 0L;
        }
        return j - j2;
    }

    public long getFrameIntervalNanos() {
        long j;
        synchronized (this.mLock) {
            j = this.mLastFrameIntervalNanos;
        }
        return j;
    }

    void dump(String str, PrintWriter printWriter) {
        String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println("Choreographer:");
        printWriter.print(str2);
        printWriter.print("mFrameScheduled=");
        printWriter.println(this.mFrameScheduled);
        printWriter.print(str2);
        printWriter.print("mLastFrameTime=");
        printWriter.println(TimeUtils.formatUptime(this.mLastFrameTimeNanos / 1000000));
    }

    public void postCallback(int i, Runnable runnable, Object obj) {
        postCallbackDelayed(i, runnable, obj, 0L);
    }

    public void postCallbackDelayed(int i, Runnable runnable, Object obj, long j) {
        if (runnable == null) {
            throw new IllegalArgumentException("action must not be null");
        }
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("callbackType is invalid");
        }
        postCallbackDelayedInternal(i, runnable, obj, j);
    }

    public void setIsFg(boolean z) {
        if (this.mIsFg == z) {
            return;
        }
        long maxAnimationCallbackDuration = AnimationHandler.getInstance().getMaxAnimationCallbackDuration() + DEFAULT_THRESHOLD_BG_DELAY;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(3);
            if (!z) {
                this.mBgWaitingDelaySetting = true;
                Message messageObtainMessage = this.mHandler.obtainMessage();
                messageObtainMessage.what = 3;
                messageObtainMessage.arg1 = !z ? 1 : 0;
                messageObtainMessage.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(messageObtainMessage, SystemClock.uptimeMillis() + maxAnimationCallbackDuration);
                Log.d("BBA2", "setIsFg isFg = " + z + "; delayValue " + maxAnimationCallbackDuration + "ms");
            } else {
                this.mIsFg = z;
                this.mIsVisible = true;
                this.mIsFirstBBA = true;
                this.mBgWaitingDelaySetting = false;
                Log.d("BBA2", "setIsFg isFg = " + z);
            }
        }
    }

    private void postCallbackDelayedInternal(int i, Object obj, Object obj2, long j) {
        synchronized (this.mLock) {
            boolean z = this.mIsFg;
            if (z == this.mBgWaitingDelaySetting && (i == 0 || i == 4)) {
                if (!z) {
                    Log.d(TAG, "BBA2 receive callback when in bg : " + i);
                }
                this.mIsFg = true;
                this.mIsFirstBBA = true;
                this.mIsVisible = true;
                if (this.mBgWaitingDelaySetting) {
                    this.mHandler.removeMessages(3);
                }
                this.mBgWaitingDelaySetting = false;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            long j2 = jUptimeMillis + j;
            if (this.mEnabledDebugCallback) {
                this.mCallbackQueues[i].addCallbackLocked(j2, obj, obj2, Debug.getCallers(this.mDebugCallStackCnt, " "));
            } else {
                this.mCallbackQueues[i].addCallbackLocked(j2, obj, obj2, null);
            }
            if (j2 <= jUptimeMillis) {
                scheduleFrameLocked(jUptimeMillis);
            } else {
                Message messageObtainMessage = this.mHandler.obtainMessage(2, obj);
                messageObtainMessage.arg1 = i;
                messageObtainMessage.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(messageObtainMessage, j2);
            }
        }
    }

    public void postVsyncCallback(VsyncCallback vsyncCallback) {
        if (vsyncCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        postCallbackDelayedInternal(1, vsyncCallback, VSYNC_CALLBACK_TOKEN, 0L);
    }

    public void removeCallbacks(int i, Runnable runnable, Object obj) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("callbackType is invalid");
        }
        removeCallbacksInternal(i, runnable, obj);
    }

    private void removeCallbacksInternal(int i, Object obj, Object obj2) {
        synchronized (this.mLock) {
            this.mCallbackQueues[i].removeCallbacksLocked(obj, obj2);
            if (obj != null && obj2 == null) {
                this.mHandler.removeMessages(2, obj);
            }
        }
    }

    public void postFrameCallback(FrameCallback frameCallback) {
        postFrameCallbackDelayed(frameCallback, 0L);
    }

    public void postFrameCallbackDelayed(FrameCallback frameCallback, long j) {
        if (frameCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        postCallbackDelayedInternal(1, frameCallback, FRAME_CALLBACK_TOKEN, j);
    }

    public void removeFrameCallback(FrameCallback frameCallback) {
        if (frameCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        removeCallbacksInternal(1, frameCallback, FRAME_CALLBACK_TOKEN);
    }

    public void removeVsyncCallback(VsyncCallback vsyncCallback) {
        if (vsyncCallback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        removeCallbacksInternal(1, vsyncCallback, VSYNC_CALLBACK_TOKEN);
    }

    public long getFrameTime() {
        return getFrameTimeNanos() / 1000000;
    }

    public long getFrameTimeNanos() {
        long jNanoTime;
        synchronized (this.mLock) {
            if (!this.mCallbacksRunning) {
                throw new IllegalStateException("This method must only be called as part of a callback while a frame is in progress.");
            }
            jNanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
        }
        return jNanoTime;
    }

    public long getLastFrameTimeNanos() {
        long jNanoTime;
        synchronized (this.mLock) {
            jNanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
        }
        return jNanoTime;
    }

    public long getExpectedPresentationTimeNanos() {
        return this.mFrameData.getPreferredFrameTimeline().getExpectedPresentationTimeNanos();
    }

    public long getExpectedPresentationTimeMillis() {
        return getExpectedPresentationTimeNanos() / 1000000;
    }

    public long getLatestExpectedPresentTimeNanos() {
        FrameDisplayEventReceiver frameDisplayEventReceiver = this.mDisplayEventReceiver;
        if (frameDisplayEventReceiver == null) {
            return System.nanoTime();
        }
        return frameDisplayEventReceiver.getLatestVsyncEventData().preferredFrameTimeline().expectedPresentationTime;
    }

    private void scheduleFrameLocked(long j) {
        if (this.mFrameScheduled) {
            return;
        }
        this.mFrameScheduled = true;
        if (USE_VSYNC) {
            if (isRunningOnLooperThreadLocked()) {
                scheduleVsyncLocked();
                return;
            }
            Message messageObtainMessage = this.mHandler.obtainMessage(1);
            messageObtainMessage.setAsynchronous(true);
            this.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage);
            return;
        }
        long jMax = Math.max((this.mLastFrameTimeNanos / 1000000) + sFrameDelay, j);
        Message messageObtainMessage2 = this.mHandler.obtainMessage(0);
        messageObtainMessage2.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(messageObtainMessage2, jMax);
    }

    public long getVsyncId() {
        if (!this.mInDoFrameCallback && Trace.isTagEnabled(8L)) {
            Trace.instant(8L, String.format(Locale.getDefault(), "unsync-vsync-id=%d isSfChoreo=%s", Long.valueOf(this.mLastVsyncEventData.preferredFrameTimeline().vsyncId), Boolean.valueOf(this == getSfInstance())));
        }
        return this.mLastVsyncEventData.preferredFrameTimeline().vsyncId;
    }

    public long getFrameDeadline() {
        return this.mLastVsyncEventData.preferredFrameTimeline().deadline;
    }

    void setFPSDivisor(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.mFPSDivisor = i;
        ThreadedRenderer.setFPSDivisor(i);
    }

    private void traceMessage(String str) {
        Trace.traceBegin(8L, str);
        Trace.traceEnd(8L);
    }

    public DisplayMetrics getMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        DisplayInfo displayInfo = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
        if (displayInfo == null) {
            return null;
        }
        displayInfo.getAppMetrics(displayMetrics);
        return displayMetrics;
    }

    long getSTBCount() {
        return this.mSTBCount;
    }

    void resetSTBCount() {
        this.mSTBCount = 0L;
    }

    BufferStuffingState.RecoveryAction updateBufferStuffingState(long j, DisplayEventReceiver.VsyncEventData vsyncEventData) {
        if (!this.mBufferStuffingState.isRecovering) {
            if (!this.mBufferStuffingState.isStuffed.getAndSet(false)) {
                return BufferStuffingState.RecoveryAction.NONE;
            }
            this.mBufferStuffingState.isRecovering = true;
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackBegin(8L, "Buffer stuffing recovery", "Thread " + Process.myTid() + ", recover frame", 0);
            }
            return BufferStuffingState.RecoveryAction.DELAY_FRAME;
        }
        int i = this.mBufferStuffingState.numberWaitsForNextVsync + 2;
        long j2 = this.mLastFrameIntervalNanos;
        if ((j2 > 0 ? (j - this.mLastNoOffsetFrameTimeNanos) / j2 : 0L) > i) {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, "Buffer stuffing recovery", 0);
            }
            this.mBufferStuffingState.reset();
            return BufferStuffingState.RecoveryAction.NONE;
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.instantForTrack(8L, "Buffer stuffing recovery", "Negative offset added to animation");
        }
        return BufferStuffingState.RecoveryAction.OFFSET;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cd A[Catch: all -> 0x00e2, TryCatch #0 {all -> 0x00e2, blocks: (B:46:0x00c7, B:48:0x00cd, B:49:0x00cf, B:45:0x00b1), top: B:155:0x00b1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void doFrame(long j, int i, DisplayEventReceiver.VsyncEventData vsyncEventData) throws Throwable {
        long j2;
        long j3;
        boolean z;
        boolean z2;
        Object obj;
        Object obj2;
        long j4;
        long j5;
        long j6;
        boolean z3;
        boolean z4;
        long j7;
        ActivityThread.currentActivityThread().getIdsController().closeIdsWindow();
        long j8 = vsyncEventData.frameInterval;
        if (Flags.bufferStuffingRecovery()) {
            int iOrdinal = updateBufferStuffingState(j, vsyncEventData).ordinal();
            if (iOrdinal != 1) {
                if (iOrdinal == 2) {
                    scheduleVsyncLocked();
                    return;
                }
                j2 = j;
            } else {
                j2 = j - j8;
            }
        } else {
            j2 = j;
        }
        try {
            FrameTimeline frameTimelineUpdate = this.mFrameData.update(j2, vsyncEventData);
            if (Trace.isTagEnabled(8L)) {
                try {
                    Trace.traceBegin(8L, "Choreographer#doFrame " + frameTimelineUpdate.mVsyncId);
                    this.mInDoFrameCallback = true;
                    obj = this.mLock;
                    synchronized (obj) {
                        try {
                            if (this.mFrameScheduled) {
                                this.mLastNoOffsetFrameTimeNanos = j;
                                j3 = 8;
                                try {
                                    long jNanoTime = System.nanoTime();
                                    long j9 = jNanoTime - j;
                                    if (j9 < j8) {
                                        j4 = j8;
                                        j5 = 8;
                                        j6 = j;
                                        z3 = false;
                                    } else if (j8 == 0) {
                                        try {
                                            Log.i(TAG, "Vsync data empty due to timeout");
                                            j4 = j8;
                                            j7 = jNanoTime;
                                            j5 = 8;
                                            if (this.mBufferStuffingState.isRecovering) {
                                                j7 -= j4;
                                            }
                                            long j10 = j7;
                                            frameTimelineUpdate = this.mFrameData.update(j10, this.mDisplayEventReceiver, j9);
                                            j6 = j10;
                                            z3 = true;
                                        } catch (Throwable th) {
                                            th = th;
                                            obj2 = obj;
                                            z2 = false;
                                            z = false;
                                            while (true) {
                                                try {
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                    }
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                }
                                            }
                                        }
                                    } else {
                                        try {
                                            j7 = jNanoTime - (j9 % j8);
                                            j4 = j8;
                                            long j11 = j9 / j4;
                                            j5 = 8;
                                            if (j11 >= SKIPPED_FRAME_WARNING_LIMIT) {
                                                try {
                                                    Log.i(TAG, "Skipped " + j11 + " frames!  The application may be doing too much work on its main thread.");
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    obj2 = obj;
                                                    j3 = j5;
                                                }
                                            }
                                            if (this.mBufferStuffingState.isRecovering) {
                                            }
                                            long j102 = j7;
                                            frameTimelineUpdate = this.mFrameData.update(j102, this.mDisplayEventReceiver, j9);
                                            j6 = j102;
                                            z3 = true;
                                        } catch (Throwable th5) {
                                            th = th5;
                                            obj2 = obj;
                                            z2 = false;
                                            z = false;
                                            while (true) {
                                                throw th;
                                            }
                                        }
                                    }
                                    FrameTimeline frameTimeline = frameTimelineUpdate;
                                    try {
                                        long j12 = this.mLastFrameTimeNanos;
                                        try {
                                            if (j6 < j12) {
                                                traceMessage("Frame time goes backward");
                                                if (this.mBufferStuffingState.isRecovering) {
                                                    this.mBufferStuffingState.numberWaitsForNextVsync++;
                                                }
                                                scheduleVsyncLocked();
                                                AnimationUtils.unlockAnimationClock();
                                                this.mInDoFrameCallback = false;
                                                if (z3) {
                                                    Trace.traceEnd(j5);
                                                }
                                                Trace.traceEnd(j5);
                                                return;
                                            }
                                            int i2 = this.mFPSDivisor;
                                            boolean z5 = true;
                                            if (i2 > 1) {
                                                long j13 = j6 - j12;
                                                if (j13 >= i2 * j4 || j13 <= 0) {
                                                    z4 = false;
                                                    z5 = true;
                                                } else {
                                                    try {
                                                        traceMessage("Frame skipped due to FPSDivisor");
                                                        if (this.mBufferStuffingState.isRecovering) {
                                                            this.mBufferStuffingState.numberWaitsForNextVsync++;
                                                        }
                                                        scheduleVsyncLocked();
                                                        AnimationUtils.unlockAnimationClock();
                                                        this.mInDoFrameCallback = false;
                                                        if (z3) {
                                                            Trace.traceEnd(j5);
                                                        }
                                                        Trace.traceEnd(j5);
                                                        return;
                                                    } catch (Throwable th6) {
                                                        th = th6;
                                                        z = false;
                                                        obj2 = obj;
                                                        z2 = z3;
                                                        j3 = j5;
                                                    }
                                                }
                                            } else {
                                                z4 = false;
                                            }
                                            try {
                                                long j14 = j6;
                                                obj2 = obj;
                                                long j15 = j4;
                                                z = false;
                                                try {
                                                    this.mFrameInfo.setVsync(j, j14, vsyncEventData.preferredFrameTimeline().vsyncId, vsyncEventData.preferredFrameTimeline().deadline, jNanoTime, vsyncEventData.frameInterval);
                                                    this.mFrameScheduled = false;
                                                    this.mLastFrameTimeNanos = j14;
                                                    this.mLastFrameIntervalNanos = j15;
                                                    this.mLastVsyncEventData.copyFrom(vsyncEventData);
                                                    if (z3) {
                                                        try {
                                                            if (Trace.isTagEnabled(j5)) {
                                                                j3 = j5;
                                                                try {
                                                                    Trace.traceBegin(j3, String.format("Choreographer#doFrame - resynced to %d in %.1fms", Long.valueOf(frameTimeline.mVsyncId), Float.valueOf((frameTimeline.mDeadlineNanos - jNanoTime) * 1.0E-6f)));
                                                                } catch (Throwable th7) {
                                                                    th = th7;
                                                                    z2 = z3;
                                                                    AnimationUtils.unlockAnimationClock();
                                                                    this.mInDoFrameCallback = z;
                                                                    if (z2) {
                                                                    }
                                                                    Trace.traceEnd(j3);
                                                                    throw th;
                                                                }
                                                            } else {
                                                                j3 = j5;
                                                            }
                                                        } catch (Throwable th8) {
                                                            th = th8;
                                                            j3 = j5;
                                                            z2 = z3;
                                                            AnimationUtils.unlockAnimationClock();
                                                            this.mInDoFrameCallback = z;
                                                            if (z2) {
                                                            }
                                                            Trace.traceEnd(j3);
                                                            throw th;
                                                        }
                                                    }
                                                    AnimationUtils.lockAnimationClock(j14 / 1000000, frameTimeline.mExpectedPresentationTimeNanos);
                                                    this.mFrameInfo.markInputHandlingStart();
                                                    doCallbacks(0, j15);
                                                    this.mFrameInfo.markAnimationsStart();
                                                    if (this.mIsFg || this.mIsVisible) {
                                                        doCallbacks(1, j15);
                                                    }
                                                    if (!this.mIsFg && !this.mIsVisible && this.mIsFirstBBA) {
                                                        this.mIsFirstBBA = false;
                                                        Log.d(TAG, "CoreRune.SYSPERF_ACTIVE_APP_BBA_ENABLE : stop animation in background states");
                                                    }
                                                    doCallbacks(2, j15);
                                                    this.mFrameInfo.markPerformTraversalsStart();
                                                    doCallbacks(3, j15);
                                                    doCallbacks(4, j15);
                                                    AnimationUtils.unlockAnimationClock();
                                                    this.mInDoFrameCallback = false;
                                                    if (z3) {
                                                        Trace.traceEnd(j3);
                                                    }
                                                    Trace.traceEnd(j3);
                                                    return;
                                                } catch (Throwable th9) {
                                                    th = th9;
                                                    j3 = j5;
                                                    z2 = z3;
                                                    while (true) {
                                                        throw th;
                                                    }
                                                }
                                            } catch (Throwable th10) {
                                                th = th10;
                                                z = z4;
                                                obj2 = obj;
                                            }
                                        } catch (Throwable th11) {
                                            th = th11;
                                            obj2 = obj;
                                            z2 = z3;
                                            j3 = j5;
                                        }
                                    } catch (Throwable th12) {
                                        th = th12;
                                        obj2 = obj;
                                        j3 = j5;
                                        z = false;
                                    }
                                } catch (Throwable th13) {
                                    th = th13;
                                    obj2 = obj;
                                    z = false;
                                    z2 = false;
                                    while (true) {
                                        throw th;
                                    }
                                }
                            } else {
                                try {
                                    traceMessage("Frame not scheduled");
                                    AnimationUtils.unlockAnimationClock();
                                    this.mInDoFrameCallback = false;
                                    Trace.traceEnd(8L);
                                    return;
                                } catch (Throwable th14) {
                                    th = th14;
                                    obj2 = obj;
                                    j3 = 8;
                                }
                            }
                            z2 = false;
                            z = false;
                        } catch (Throwable th15) {
                            th = th15;
                            obj2 = obj;
                            j3 = 8;
                        }
                        while (true) {
                            throw th;
                        }
                    }
                } catch (Throwable th16) {
                    th = th16;
                    j3 = 8;
                    z2 = false;
                    z = false;
                }
            } else {
                obj = this.mLock;
                synchronized (obj) {
                }
            }
        } catch (Throwable th17) {
            th = th17;
            j3 = 8;
            z = false;
            z2 = false;
        }
        AnimationUtils.unlockAnimationClock();
        this.mInDoFrameCallback = z;
        if (z2) {
            Trace.traceEnd(j3);
        }
        Trace.traceEnd(j3);
        throw th;
    }

    void doCallbacks(int i, long j) {
        long j2 = this.mFrameData.mFrameTimeNanos;
        synchronized (this.mLock) {
            long jNanoTime = System.nanoTime();
            CallbackRecord callbackRecordExtractDueCallbacksLocked = this.mCallbackQueues[i].extractDueCallbacksLocked(jNanoTime / 1000000);
            if (callbackRecordExtractDueCallbacksLocked == null) {
                return;
            }
            this.mCallbacksRunning = true;
            long j3 = 0;
            if (i == 4) {
                long j4 = jNanoTime - j2;
                Trace.traceCounter(8L, "jitterNanos", (int) j4);
                if (j > 0 && j4 >= 2 * j) {
                    long j5 = jNanoTime - ((j4 % j) + j);
                    this.mLastFrameTimeNanos = j5;
                    this.mFrameData.update(j5, this.mDisplayEventReceiver, j4);
                }
            }
            try {
                Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[i]);
                CallbackRecord callbackRecord = callbackRecordExtractDueCallbacksLocked;
                while (callbackRecord != null) {
                    long jElapsedRealtime = this.mEnabledDebugCallback ? SystemClock.elapsedRealtime() : j3;
                    callbackRecord.run(this.mFrameData);
                    if (this.mEnabledDebugCallback && this.mDebugCallbackConsumer != null && callbackRecord.log != null) {
                        long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                        if (jElapsedRealtime2 >= this.mDebugDispatchThresholdMs) {
                            this.mDebugCallbackConsumer.accept("RunCallback: type=" + i + ", action=" + callbackRecord.action + ", token=" + callbackRecord.token + ", latencyMillis=" + (SystemClock.uptimeMillis() - callbackRecord.dueTime) + ", dur=" + jElapsedRealtime2 + "ms\n", callbackRecord.log);
                        }
                    }
                    callbackRecord = callbackRecord.next;
                    j3 = 0;
                }
                synchronized (this.mLock) {
                    this.mCallbacksRunning = false;
                    while (true) {
                        CallbackRecord callbackRecord2 = callbackRecordExtractDueCallbacksLocked.next;
                        recycleCallbackLocked(callbackRecordExtractDueCallbacksLocked);
                        if (callbackRecord2 != null) {
                            callbackRecordExtractDueCallbacksLocked = callbackRecord2;
                        }
                    }
                }
                Trace.traceEnd(8L);
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    this.mCallbacksRunning = false;
                    while (true) {
                        CallbackRecord callbackRecord3 = callbackRecordExtractDueCallbacksLocked.next;
                        recycleCallbackLocked(callbackRecordExtractDueCallbacksLocked);
                        if (callbackRecord3 == null) {
                            Trace.traceEnd(8L);
                            throw th;
                        }
                        callbackRecordExtractDueCallbacksLocked = callbackRecord3;
                    }
                }
            }
        }
    }

    void doScheduleVsync() {
        synchronized (this.mLock) {
            if (this.mFrameScheduled) {
                scheduleVsyncLocked();
            }
        }
    }

    void doScheduleCallback(int i) {
        synchronized (this.mLock) {
            if (!this.mFrameScheduled) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                if (this.mCallbackQueues[i].hasDueCallbacksLocked(jUptimeMillis)) {
                    scheduleFrameLocked(jUptimeMillis);
                }
            }
        }
    }

    public void scheduleVsyncSS(int i) {
        synchronized (this.mLock) {
            this.mDisplayEventReceiver.onVsyncSS(i);
        }
    }

    void setActivityState(boolean z) {
        synchronized (this.mLock) {
            this.mIsFg = z;
            this.mBgWaitingDelaySetting = false;
        }
    }

    public void setViewVisible(boolean z) {
        this.mIsVisible = z;
    }

    private void scheduleVsyncLocked() {
        try {
            Trace.traceBegin(8L, "Choreographer#scheduleVsyncLocked");
            this.mDisplayEventReceiver.scheduleVsync();
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private boolean isRunningOnLooperThreadLocked() {
        return Looper.myLooper() == this.mLooper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CallbackRecord obtainCallbackLocked(long j, Object obj, Object obj2, String str) {
        CallbackRecord callbackRecord = this.mCallbackPool;
        if (callbackRecord == null) {
            callbackRecord = new CallbackRecord();
        } else {
            this.mCallbackPool = callbackRecord.next;
            callbackRecord.next = null;
        }
        callbackRecord.dueTime = j;
        callbackRecord.action = obj;
        callbackRecord.token = obj2;
        callbackRecord.log = str;
        return callbackRecord;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleCallbackLocked(CallbackRecord callbackRecord) {
        callbackRecord.action = null;
        callbackRecord.token = null;
        callbackRecord.next = this.mCallbackPool;
        callbackRecord.log = null;
        this.mCallbackPool = callbackRecord;
    }

    public void setEnabledDebugCallback(boolean z, BiConsumer<String, String> biConsumer, int i, int i2) {
        this.mEnabledDebugCallback = z;
        this.mDebugCallbackConsumer = biConsumer;
        this.mDebugCallStackCnt = i;
        this.mDebugDispatchThresholdMs = i2;
    }

    public static class FrameTimeline {
        private long mVsyncId = -1;
        private long mExpectedPresentationTimeNanos = -1;
        private long mDeadlineNanos = -1;
        private boolean mInCallback = false;

        FrameTimeline() {
        }

        void setInCallback(boolean z) {
            this.mInCallback = z;
        }

        private void checkInCallback() {
            if (!this.mInCallback) {
                throw new IllegalStateException("FrameTimeline is not valid outside of the vsync callback");
            }
        }

        void update(long j, long j2, long j3) {
            this.mVsyncId = j;
            this.mExpectedPresentationTimeNanos = j2;
            this.mDeadlineNanos = j3;
        }

        public long getVsyncId() {
            checkInCallback();
            return this.mVsyncId;
        }

        public long getExpectedPresentationTimeNanos() {
            checkInCallback();
            return this.mExpectedPresentationTimeNanos;
        }

        public long getDeadlineNanos() {
            checkInCallback();
            return this.mDeadlineNanos;
        }
    }

    public static class FrameData {
        private long mFrameTimeNanos;
        private FrameTimeline[] mFrameTimelines;
        private boolean mInCallback = false;
        private int mPreferredFrameTimelineIndex;

        FrameData() {
            allocateFrameTimelines(7);
        }

        public long getFrameTimeNanos() {
            checkInCallback();
            return this.mFrameTimeNanos;
        }

        public FrameTimeline[] getFrameTimelines() {
            checkInCallback();
            return this.mFrameTimelines;
        }

        public FrameTimeline getPreferredFrameTimeline() {
            checkInCallback();
            return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
        }

        void setInCallback(boolean z) {
            this.mInCallback = z;
            int i = 0;
            while (true) {
                FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
                if (i >= frameTimelineArr.length) {
                    return;
                }
                frameTimelineArr[i].setInCallback(z);
                i++;
            }
        }

        private void checkInCallback() {
            if (!this.mInCallback) {
                throw new IllegalStateException("FrameData is not valid outside of the vsync callback");
            }
        }

        private void allocateFrameTimelines(int i) {
            int iMax = Math.max(1, i);
            FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
            if (frameTimelineArr != null && frameTimelineArr.length == iMax) {
                return;
            }
            this.mFrameTimelines = new FrameTimeline[iMax];
            int i2 = 0;
            while (true) {
                FrameTimeline[] frameTimelineArr2 = this.mFrameTimelines;
                if (i2 >= frameTimelineArr2.length) {
                    return;
                }
                frameTimelineArr2[i2] = new FrameTimeline();
                i2++;
            }
        }

        FrameTimeline update(long j, DisplayEventReceiver.VsyncEventData vsyncEventData) {
            allocateFrameTimelines(vsyncEventData.frameTimelinesLength);
            this.mFrameTimeNanos = j;
            this.mPreferredFrameTimelineIndex = vsyncEventData.preferredFrameTimelineIndex;
            int i = 0;
            while (true) {
                FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
                if (i < frameTimelineArr.length) {
                    DisplayEventReceiver.VsyncEventData.FrameTimeline frameTimeline = vsyncEventData.frameTimelines[i];
                    this.mFrameTimelines[i].update(frameTimeline.vsyncId, frameTimeline.expectedPresentationTime, frameTimeline.deadline);
                    i++;
                } else {
                    return frameTimelineArr[this.mPreferredFrameTimelineIndex];
                }
            }
        }

        FrameTimeline update(long j, DisplayEventReceiver displayEventReceiver, long j2) {
            long j3 = this.mFrameTimelines[this.mPreferredFrameTimelineIndex].mDeadlineNanos + j2;
            int i = 0;
            while (true) {
                FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
                if (i >= frameTimelineArr.length - 1 || frameTimelineArr[i].mDeadlineNanos >= j3) {
                    break;
                }
                i++;
            }
            if (this.mFrameTimelines[i].mDeadlineNanos < j3) {
                DisplayEventReceiver.VsyncEventData latestVsyncEventData = displayEventReceiver.getLatestVsyncEventData();
                if (latestVsyncEventData == null) {
                    Log.w(Choreographer.TAG, "Could not get latest VsyncEventData. Did SurfaceFlinger crash?");
                } else {
                    update(j, latestVsyncEventData);
                }
            } else {
                update(j, i);
            }
            return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
        }

        void update(long j, int i) {
            this.mFrameTimeNanos = j;
            this.mPreferredFrameTimelineIndex = i;
        }
    }

    private final class FrameHandler extends Handler {
        public FrameHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Throwable {
            int i = message.what;
            if (i == 0) {
                Choreographer.this.doFrame(System.nanoTime(), 0, new DisplayEventReceiver.VsyncEventData());
                return;
            }
            if (i == 1) {
                Choreographer.this.doScheduleVsync();
            } else if (i == 2) {
                Choreographer.this.doScheduleCallback(message.arg1);
            } else {
                if (i != 3) {
                    return;
                }
                Choreographer.this.setActivityState(message.arg1 == 0);
            }
        }
    }

    private final class FrameDisplayEventReceiver extends DisplayEventReceiver implements Runnable {
        private int mFrame;
        private boolean mHavePendingVsync;
        private final DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
        private long mTimestampNanos;

        FrameDisplayEventReceiver(Looper looper, int i, long j) {
            super(looper, i, 0, j);
            this.mLastVsyncEventData = new DisplayEventReceiver.VsyncEventData();
        }

        public void onVsyncSS(int i) {
            this.mTimestampNanos = System.nanoTime();
            this.mFrame = 0;
            Message messageObtain = Message.obtain(Choreographer.this.mHandler, this);
            messageObtain.setAsynchronous(true);
            DisplayEventReceiver.VsyncEventData latestVsyncEventData = getLatestVsyncEventData();
            if (latestVsyncEventData != null && latestVsyncEventData.frameTimelinesLength > 0) {
                this.mLastVsyncEventData.preferredFrameTimelineIndex = latestVsyncEventData.preferredFrameTimelineIndex;
                this.mLastVsyncEventData.frameTimelinesLength = latestVsyncEventData.frameTimelinesLength;
                this.mLastVsyncEventData.frameInterval = latestVsyncEventData.frameInterval;
                for (int i2 = 0; i2 < latestVsyncEventData.frameTimelinesLength; i2++) {
                    this.mLastVsyncEventData.frameTimelines[i2].copyFrom(latestVsyncEventData.frameTimelines[i2]);
                }
                if (i == 0) {
                    Choreographer.this.mHandler.sendMessage(messageObtain);
                    return;
                } else {
                    if (i == 1 || i == 2 || i == 3) {
                        Choreographer.this.mHandler.sendMessageAtFrontOfQueue(messageObtain);
                        return;
                    }
                    return;
                }
            }
            Log.w(Choreographer.IDS_TAG, "Could not get FrameData");
        }

        private void scheduleSTB() {
            try {
                if (Trace.isTagEnabled(8L)) {
                    Trace.traceBegin(8L, "STB invocation");
                }
                Choreographer choreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
                if (choreographer != null) {
                    choreographer.scheduleVsyncSS(3);
                    Choreographer.this.mSTBCount++;
                    Choreographer.this.mFramesSinceSTB = 0L;
                }
            } finally {
                Trace.traceEnd(8L);
            }
        }

        @Override // android.view.DisplayEventReceiver
        public void onVsync(long j, long j2, int i, DisplayEventReceiver.VsyncEventData vsyncEventData) {
            try {
                if (Trace.isTagEnabled(8L)) {
                    Trace.traceBegin(8L, "Choreographer#onVsync " + vsyncEventData.preferredFrameTimeline().vsyncId);
                }
                long jNanoTime = System.nanoTime();
                if (j > jNanoTime) {
                    Log.w(Choreographer.TAG, "Frame time is " + ((j - jNanoTime) * 1.0E-6f) + " ms in the future!  Check that graphics HAL is generating vsync timestamps using the correct timebase.");
                    j = jNanoTime;
                }
                if (this.mHavePendingVsync) {
                    Log.w(Choreographer.TAG, "Already have a pending vsync event.  There should only be one at a time.");
                } else {
                    this.mHavePendingVsync = true;
                }
                this.mTimestampNanos = j;
                this.mFrame = i;
                this.mLastVsyncEventData.copyFrom(vsyncEventData);
                Message messageObtain = Message.obtain(Choreographer.this.mHandler, this);
                messageObtain.setAsynchronous(true);
                Choreographer.this.mHandler.sendMessageAtTime(messageObtain, j / 1000000);
            } finally {
                Trace.traceEnd(8L);
            }
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            this.mHavePendingVsync = false;
            Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame, this.mLastVsyncEventData);
        }
    }

    private static final class CallbackRecord {
        public Object action;
        public long dueTime;
        public String log;
        public CallbackRecord next;
        public Object token;

        private CallbackRecord() {
        }

        public void run(long j) {
            if (this.token == Choreographer.FRAME_CALLBACK_TOKEN) {
                ((FrameCallback) this.action).doFrame(j);
            } else {
                ((Runnable) this.action).run();
            }
        }

        void run(FrameData frameData) {
            frameData.setInCallback(true);
            if (this.token == Choreographer.VSYNC_CALLBACK_TOKEN) {
                ((VsyncCallback) this.action).onVsync(frameData);
            } else {
                run(frameData.getFrameTimeNanos());
            }
            frameData.setInCallback(false);
        }
    }

    private final class CallbackQueue {
        private CallbackRecord mHead;

        private CallbackQueue() {
        }

        public boolean hasDueCallbacksLocked(long j) {
            CallbackRecord callbackRecord = this.mHead;
            return callbackRecord != null && callbackRecord.dueTime <= j;
        }

        public CallbackRecord extractDueCallbacksLocked(long j) {
            CallbackRecord callbackRecord = this.mHead;
            if (callbackRecord == null || callbackRecord.dueTime > j) {
                return null;
            }
            CallbackRecord callbackRecord2 = callbackRecord.next;
            CallbackRecord callbackRecord3 = callbackRecord;
            while (true) {
                if (callbackRecord2 == null) {
                    break;
                }
                if (callbackRecord2.dueTime > j) {
                    callbackRecord3.next = null;
                    break;
                }
                callbackRecord3 = callbackRecord2;
                callbackRecord2 = callbackRecord2.next;
            }
            this.mHead = callbackRecord2;
            return callbackRecord;
        }

        public void addCallbackLocked(long j, Object obj, Object obj2, String str) {
            CallbackRecord callbackRecordObtainCallbackLocked = Choreographer.this.obtainCallbackLocked(j, obj, obj2, str);
            CallbackRecord callbackRecord = this.mHead;
            if (callbackRecord == null) {
                this.mHead = callbackRecordObtainCallbackLocked;
                return;
            }
            if (j < callbackRecord.dueTime) {
                callbackRecordObtainCallbackLocked.next = callbackRecord;
                this.mHead = callbackRecordObtainCallbackLocked;
                return;
            }
            while (true) {
                if (callbackRecord.next == null) {
                    break;
                }
                if (j < callbackRecord.next.dueTime) {
                    callbackRecordObtainCallbackLocked.next = callbackRecord.next;
                    break;
                }
                callbackRecord = callbackRecord.next;
            }
            callbackRecord.next = callbackRecordObtainCallbackLocked;
        }

        public void removeCallbacksLocked(Object obj, Object obj2) {
            CallbackRecord callbackRecord = this.mHead;
            CallbackRecord callbackRecord2 = null;
            while (callbackRecord != null) {
                CallbackRecord callbackRecord3 = callbackRecord.next;
                if ((obj == null || callbackRecord.action == obj) && (obj2 == null || callbackRecord.token == obj2)) {
                    if (callbackRecord2 != null) {
                        callbackRecord2.next = callbackRecord3;
                    } else {
                        this.mHead = callbackRecord3;
                    }
                    Choreographer.this.recycleCallbackLocked(callbackRecord);
                } else {
                    callbackRecord2 = callbackRecord;
                }
                callbackRecord = callbackRecord3;
            }
        }
    }
}
