package android.os;

import java.util.Objects;

/* loaded from: classes3.dex */
public final class WorkDuration {
    long mActualCpuDurationNanos;
    long mActualGpuDurationNanos;
    long mActualTotalDurationNanos;
    long mWorkPeriodStartTimestampNanos;

    public WorkDuration() {
        this.mActualTotalDurationNanos = 0L;
        this.mWorkPeriodStartTimestampNanos = 0L;
        this.mActualCpuDurationNanos = 0L;
        this.mActualGpuDurationNanos = 0L;
    }

    public WorkDuration(long j, long j2, long j3, long j4) {
        this.mActualTotalDurationNanos = j2;
        this.mWorkPeriodStartTimestampNanos = j;
        this.mActualCpuDurationNanos = j3;
        this.mActualGpuDurationNanos = j4;
    }

    public void setActualTotalDurationNanos(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("the actual total duration should be greater than zero.");
        }
        this.mActualTotalDurationNanos = j;
    }

    public void setWorkPeriodStartTimestampNanos(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("the work period start timestamp should be greater than zero.");
        }
        this.mWorkPeriodStartTimestampNanos = j;
    }

    public void setActualCpuDurationNanos(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("the actual CPU duration should be greater than or equal to zero.");
        }
        this.mActualCpuDurationNanos = j;
    }

    public void setActualGpuDurationNanos(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("the actual GPU duration should be greater than or equal to zero.");
        }
        this.mActualGpuDurationNanos = j;
    }

    public long getActualTotalDurationNanos() {
        return this.mActualTotalDurationNanos;
    }

    public long getWorkPeriodStartTimestampNanos() {
        return this.mWorkPeriodStartTimestampNanos;
    }

    public long getActualCpuDurationNanos() {
        return this.mActualCpuDurationNanos;
    }

    public long getActualGpuDurationNanos() {
        return this.mActualGpuDurationNanos;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WorkDuration)) {
            return false;
        }
        WorkDuration workDuration = (WorkDuration) obj;
        return workDuration.mActualTotalDurationNanos == this.mActualTotalDurationNanos && workDuration.mWorkPeriodStartTimestampNanos == this.mWorkPeriodStartTimestampNanos && workDuration.mActualCpuDurationNanos == this.mActualCpuDurationNanos && workDuration.mActualGpuDurationNanos == this.mActualGpuDurationNanos;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mWorkPeriodStartTimestampNanos), Long.valueOf(this.mActualTotalDurationNanos), Long.valueOf(this.mActualCpuDurationNanos), Long.valueOf(this.mActualGpuDurationNanos));
    }
}
