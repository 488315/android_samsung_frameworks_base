package android.os;

import android.content.Context;
import android.os.ServiceManager;
import com.android.internal.util.Preconditions;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.util.Objects;

/* loaded from: classes3.dex */
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
    public static native void nativeReportActualWorkDuration(long j, long j2, long j3, long j4, long j5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSendHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPreferPowerEfficiency(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetThreads(long j, int[] iArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUpdateTargetWorkDuration(long j, long j2);

    public static PerformanceHintManager create() throws ServiceManager.ServiceNotFoundException {
        long jNativeAcquireManager = nativeAcquireManager();
        if (jNativeAcquireManager == 0) {
            throw new ServiceManager.ServiceNotFoundException(Context.PERFORMANCE_HINT_SERVICE);
        }
        return new PerformanceHintManager(jNativeAcquireManager);
    }

    private PerformanceHintManager(long j) {
        this.mNativeManagerPtr = j;
    }

    public long getPreferredUpdateRateNanos() {
        return nativeGetPreferredUpdateRateNanos(this.mNativeManagerPtr);
    }

    public Session createHintSession(int[] iArr, long j) {
        Objects.requireNonNull(iArr, "tids cannot be null");
        if (iArr.length == 0) {
            throw new IllegalArgumentException("thread id list can't be empty.");
        }
        Preconditions.checkArgumentPositive(j, "the hint target duration should be positive.");
        long jNativeCreateSession = nativeCreateSession(this.mNativeManagerPtr, iArr, j);
        if (jNativeCreateSession == 0) {
            return null;
        }
        return new Session(jNativeCreateSession);
    }

    public static class Session implements Closeable {
        public static final int CPU_LOAD_DOWN = 1;
        public static final int CPU_LOAD_RESET = 2;
        public static final int CPU_LOAD_RESUME = 3;
        public static final int CPU_LOAD_UP = 0;
        public static final int GPU_LOAD_DOWN = 6;
        public static final int GPU_LOAD_RESET = 7;
        public static final int GPU_LOAD_UP = 5;
        public long mNativeSessionPtr;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Hint {
        }

        public Session(long j) {
            this.mNativeSessionPtr = j;
        }

        protected void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        public void updateTargetWorkDuration(long j) {
            Preconditions.checkArgumentPositive(j, "the hint target duration should be positive.");
            PerformanceHintManager.nativeUpdateTargetWorkDuration(this.mNativeSessionPtr, j);
        }

        public void reportActualWorkDuration(long j) {
            Preconditions.checkArgumentPositive(j, "the actual duration should be positive.");
            PerformanceHintManager.nativeReportActualWorkDuration(this.mNativeSessionPtr, j);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            long j = this.mNativeSessionPtr;
            if (j != 0) {
                PerformanceHintManager.nativeCloseSession(j);
                this.mNativeSessionPtr = 0L;
            }
        }

        public void sendHint(int i) {
            Preconditions.checkArgumentNonNegative(i, "the hint ID should be at least zero.");
            try {
                PerformanceHintManager.nativeSendHint(this.mNativeSessionPtr, i);
            } finally {
                Reference.reachabilityFence(this);
            }
        }

        public void setPreferPowerEfficiency(boolean z) {
            PerformanceHintManager.nativeSetPreferPowerEfficiency(this.mNativeSessionPtr, z);
        }

        public void setThreads(int[] iArr) {
            if (this.mNativeSessionPtr == 0) {
                return;
            }
            Objects.requireNonNull(iArr, "tids cannot be null");
            if (iArr.length == 0) {
                throw new IllegalArgumentException("Thread id list can't be empty.");
            }
            PerformanceHintManager.nativeSetThreads(this.mNativeSessionPtr, iArr);
        }

        public int[] getThreadIds() {
            return PerformanceHintManager.nativeGetThreadIds(this.mNativeSessionPtr);
        }

        public void reportActualWorkDuration(WorkDuration workDuration) {
            if (workDuration.mWorkPeriodStartTimestampNanos <= 0) {
                throw new IllegalArgumentException("the work period start timestamp should be greater than zero.");
            }
            if (workDuration.mActualTotalDurationNanos <= 0) {
                throw new IllegalArgumentException("the actual total duration should be greater than zero.");
            }
            if (workDuration.mActualCpuDurationNanos < 0) {
                throw new IllegalArgumentException("the actual CPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualGpuDurationNanos < 0) {
                throw new IllegalArgumentException("the actual GPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualCpuDurationNanos + workDuration.mActualGpuDurationNanos <= 0) {
                throw new IllegalArgumentException("either the actual CPU duration or the actual GPU duration should be greaterthan zero.");
            }
            PerformanceHintManager.nativeReportActualWorkDuration(this.mNativeSessionPtr, workDuration.mWorkPeriodStartTimestampNanos, workDuration.mActualTotalDurationNanos, workDuration.mActualCpuDurationNanos, workDuration.mActualGpuDurationNanos);
        }
    }
}
