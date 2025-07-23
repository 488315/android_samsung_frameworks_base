package android.view;

import android.Manifest;
import android.app.ActivityThread;
import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.os.SystemProperties;
import android.os.Trace;

/* loaded from: classes4.dex */
public final class ScrollTripleBufferingManager {
    private static final long FLING_TIME_THRESHOLD_NANOS = 3000000000L;
    private static final long ONE_MILLIS = 1000000;
    static final long STB_FRAME_INTERVAL_FLOOR_120 = 8;
    private static BLASTBufferQueue sBlastBufferQueue = null;
    private static Choreographer sChoreographer = null;
    private static boolean sFlingSTBFlag = false;
    private static long sFlingStartTime = 0;
    private static boolean sIsAnimationUpdateEnabled = false;
    private static long sLastAnimationTime;
    private static long sLastFrameIntervalNanos;
    private static final boolean ENABLE_STB = SystemProperties.getBoolean("debug.enable.stb", true);
    private static final int MAX_QUEUEUED_BUFFER_COUNT = SystemProperties.getInt("debug.stb.maxqueued", 4);

    private ScrollTripleBufferingManager() {
    }

    static void setChoreographer(Choreographer choreographer) {
        sChoreographer = choreographer;
    }

    static void setBlastBufferQueue(BLASTBufferQueue bLASTBufferQueue) {
        sBlastBufferQueue = bLASTBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.setFlingStbFlag(true);
        }
    }

    static int getQueuedBufferCount() {
        BLASTBufferQueue bLASTBufferQueue = sBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            return bLASTBufferQueue.getQueuedBufferCount();
        }
        return -1;
    }

    static long getTimeSpentPreviouslyWithoutBuffer() {
        BLASTBufferQueue bLASTBufferQueue = sBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            return bLASTBufferQueue.getTimeSpentPreviouslyWithoutBuffer();
        }
        return -1L;
    }

    static long getLastBufferConsumedTime() {
        BLASTBufferQueue bLASTBufferQueue = sBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            return bLASTBufferQueue.getLastBufferConsumedTime();
        }
        return -1L;
    }

    static void adjustAnimationTimeIfNeeded(long j) {
        Choreographer choreographer = sChoreographer;
        if (choreographer != null) {
            sLastFrameIntervalNanos = choreographer.getFrameIntervalNanos();
            long timeSpentPreviouslyWithoutBuffer = getTimeSpentPreviouslyWithoutBuffer();
            long lastBufferConsumedTime = getLastBufferConsumedTime();
            if (timeSpentPreviouslyWithoutBuffer <= 0) {
                timeSpentPreviouslyWithoutBuffer = 0;
            }
            long j2 = timeSpentPreviouslyWithoutBuffer + (lastBufferConsumedTime > 0 ? j - lastBufferConsumedTime : 0L);
            long j3 = j2 > 0 ? j2 - sLastFrameIntervalNanos : 0L;
            if (j3 > 0) {
                long j4 = sLastFrameIntervalNanos;
                if (j4 > 0) {
                    sLastAnimationTime += (j3 / j4) * j4;
                }
            }
        }
    }

    public static long updateAnimationTime(long j) {
        if (sIsAnimationUpdateEnabled) {
            long j2 = sLastAnimationTime;
            if (j2 == 0) {
                sLastAnimationTime = j * 1000000;
            } else {
                sLastAnimationTime = j2 + sLastFrameIntervalNanos;
            }
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, "sLastAnimationTime: " + String.valueOf(sLastAnimationTime));
        }
        sIsAnimationUpdateEnabled = false;
        return sLastAnimationTime;
    }

    static long getLastAnimationTime() {
        return sLastAnimationTime;
    }

    static boolean isFrameBufferCountNotFull() {
        int queuedBufferCount = getQueuedBufferCount();
        return queuedBufferCount >= 0 && queuedBufferCount < MAX_QUEUEUED_BUFFER_COUNT;
    }

    public static void setFlingStbFlag(boolean z) {
        ActivityThread currentActivityThread;
        Context baseContext;
        if (sChoreographer == null || (currentActivityThread = ActivityThread.currentActivityThread()) == null || (baseContext = currentActivityThread.getApplication().getBaseContext()) == null || baseContext.getPackageManager().checkPermission(Manifest.permission.STATUS_BAR_SERVICE, currentActivityThread.getProcessName()) == 0) {
            return;
        }
        long frameIntervalNanos = sChoreographer.getFrameIntervalNanos() / 1000000;
        if (ENABLE_STB) {
            if (frameIntervalNanos == 8 || !z) {
                sFlingSTBFlag = z;
                BLASTBufferQueue bLASTBufferQueue = sBlastBufferQueue;
                if (bLASTBufferQueue != null) {
                    bLASTBufferQueue.setFlingStbFlag(false);
                }
                sIsAnimationUpdateEnabled = false;
                sLastAnimationTime = 0L;
                sFlingStartTime = 0L;
                sBlastBufferQueue = null;
                sLastFrameIntervalNanos = 0L;
                if (sFlingSTBFlag) {
                    sFlingStartTime = System.nanoTime();
                    sIsAnimationUpdateEnabled = true;
                }
            }
        }
    }

    public static boolean isStbNeeded() {
        return sFlingSTBFlag && System.nanoTime() - sFlingStartTime < FLING_TIME_THRESHOLD_NANOS && sBlastBufferQueue != null && sChoreographer != null;
    }

    static boolean isFlinging() {
        return sFlingSTBFlag;
    }

    static boolean isBlastBufferQueueSet() {
        return sBlastBufferQueue != null;
    }

    static void setAnimationUpdateEnabled(boolean z) {
        sIsAnimationUpdateEnabled = z;
    }
}
