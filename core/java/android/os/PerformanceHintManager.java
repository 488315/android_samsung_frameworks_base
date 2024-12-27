package android.os;

import android.content.Context;

import com.android.internal.util.Preconditions;

import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.util.Objects;

public final class PerformanceHintManager {
    private final long mNativeManagerPtr;

    private static native long nativeAcquireManager();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeCloseSession(long j);

    private static native long nativeCreateSession(long j, int[] iArr, long j2);

    private static native long nativeGetPreferredUpdateRateNanos(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int[] nativeGetThreadIds(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReportActualWorkDuration(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReportActualWorkDuration(
            long j, long j2, long j3, long j4, long j5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSendHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPreferPowerEfficiency(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetThreads(long j, int[] iArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUpdateTargetWorkDuration(long j, long j2);

    public static PerformanceHintManager create() throws ServiceManager.ServiceNotFoundException {
        long nativeManagerPtr = nativeAcquireManager();
        if (nativeManagerPtr == 0) {
            throw new ServiceManager.ServiceNotFoundException(Context.PERFORMANCE_HINT_SERVICE);
        }
        return new PerformanceHintManager(nativeManagerPtr);
    }

    private PerformanceHintManager(long nativeManagerPtr) {
        this.mNativeManagerPtr = nativeManagerPtr;
    }

    public long getPreferredUpdateRateNanos() {
        return nativeGetPreferredUpdateRateNanos(this.mNativeManagerPtr);
    }

    public Session createHintSession(int[] tids, long initialTargetWorkDurationNanos) {
        Objects.requireNonNull(tids, "tids cannot be null");
        if (tids.length == 0) {
            throw new IllegalArgumentException("thread id list can't be empty.");
        }
        Preconditions.checkArgumentPositive(
                initialTargetWorkDurationNanos, "the hint target duration should be positive.");
        long nativeSessionPtr =
                nativeCreateSession(this.mNativeManagerPtr, tids, initialTargetWorkDurationNanos);
        if (nativeSessionPtr == 0) {
            return null;
        }
        return new Session(nativeSessionPtr);
    }

    public static class Session implements Closeable {
        public static final int CPU_LOAD_DOWN = 1;
        public static final int CPU_LOAD_RESET = 2;
        public static final int CPU_LOAD_RESUME = 3;
        public static final int CPU_LOAD_UP = 0;
        public static final int GPU_LOAD_DOWN = 6;
        public static final int GPU_LOAD_RESET = 7;
        public static final int GPU_LOAD_UP = 5;
        private long mNativeSessionPtr;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Hint {}

        public Session(long nativeSessionPtr) {
            this.mNativeSessionPtr = nativeSessionPtr;
        }

        protected void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        public void updateTargetWorkDuration(long targetDurationNanos) {
            Preconditions.checkArgumentPositive(
                    targetDurationNanos, "the hint target duration should be positive.");
            PerformanceHintManager.nativeUpdateTargetWorkDuration(
                    this.mNativeSessionPtr, targetDurationNanos);
        }

        public void reportActualWorkDuration(long actualDurationNanos) {
            Preconditions.checkArgumentPositive(
                    actualDurationNanos, "the actual duration should be positive.");
            PerformanceHintManager.nativeReportActualWorkDuration(
                    this.mNativeSessionPtr, actualDurationNanos);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.mNativeSessionPtr != 0) {
                PerformanceHintManager.nativeCloseSession(this.mNativeSessionPtr);
                this.mNativeSessionPtr = 0L;
            }
        }

        public void sendHint(int hint) {
            Preconditions.checkArgumentNonNegative(hint, "the hint ID should be at least zero.");
            try {
                PerformanceHintManager.nativeSendHint(this.mNativeSessionPtr, hint);
            } finally {
                Reference.reachabilityFence(this);
            }
        }

        public void setPreferPowerEfficiency(boolean enabled) {
            PerformanceHintManager.nativeSetPreferPowerEfficiency(this.mNativeSessionPtr, enabled);
        }

        public void setThreads(int[] tids) {
            if (this.mNativeSessionPtr == 0) {
                return;
            }
            Objects.requireNonNull(tids, "tids cannot be null");
            if (tids.length == 0) {
                throw new IllegalArgumentException("Thread id list can't be empty.");
            }
            PerformanceHintManager.nativeSetThreads(this.mNativeSessionPtr, tids);
        }

        public int[] getThreadIds() {
            return PerformanceHintManager.nativeGetThreadIds(this.mNativeSessionPtr);
        }

        public void reportActualWorkDuration(WorkDuration workDuration) {
            if (workDuration.mWorkPeriodStartTimestampNanos <= 0) {
                throw new IllegalArgumentException(
                        "the work period start timestamp should be greater than zero.");
            }
            if (workDuration.mActualTotalDurationNanos <= 0) {
                throw new IllegalArgumentException(
                        "the actual total duration should be greater than zero.");
            }
            if (workDuration.mActualCpuDurationNanos < 0) {
                throw new IllegalArgumentException(
                        "the actual CPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualGpuDurationNanos < 0) {
                throw new IllegalArgumentException(
                        "the actual GPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualCpuDurationNanos + workDuration.mActualGpuDurationNanos <= 0) {
                throw new IllegalArgumentException(
                        "either the actual CPU duration or the actual GPU duration should be"
                            + " greaterthan zero.");
            }
            PerformanceHintManager.nativeReportActualWorkDuration(
                    this.mNativeSessionPtr,
                    workDuration.mWorkPeriodStartTimestampNanos,
                    workDuration.mActualTotalDurationNanos,
                    workDuration.mActualCpuDurationNanos,
                    workDuration.mActualGpuDurationNanos);
        }
    }
}
