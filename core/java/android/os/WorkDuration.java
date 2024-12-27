package android.os;

import java.util.Objects;

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

    public WorkDuration(
            long workPeriodStartTimestampNanos,
            long actualTotalDurationNanos,
            long actualCpuDurationNanos,
            long actualGpuDurationNanos) {
        this.mActualTotalDurationNanos = 0L;
        this.mWorkPeriodStartTimestampNanos = 0L;
        this.mActualCpuDurationNanos = 0L;
        this.mActualGpuDurationNanos = 0L;
        this.mActualTotalDurationNanos = actualTotalDurationNanos;
        this.mWorkPeriodStartTimestampNanos = workPeriodStartTimestampNanos;
        this.mActualCpuDurationNanos = actualCpuDurationNanos;
        this.mActualGpuDurationNanos = actualGpuDurationNanos;
    }

    public void setActualTotalDurationNanos(long actualTotalDurationNanos) {
        if (actualTotalDurationNanos <= 0) {
            throw new IllegalArgumentException(
                    "the actual total duration should be greater than zero.");
        }
        this.mActualTotalDurationNanos = actualTotalDurationNanos;
    }

    public void setWorkPeriodStartTimestampNanos(long workPeriodStartTimestampNanos) {
        if (workPeriodStartTimestampNanos <= 0) {
            throw new IllegalArgumentException(
                    "the work period start timestamp should be greater than zero.");
        }
        this.mWorkPeriodStartTimestampNanos = workPeriodStartTimestampNanos;
    }

    public void setActualCpuDurationNanos(long actualCpuDurationNanos) {
        if (actualCpuDurationNanos < 0) {
            throw new IllegalArgumentException(
                    "the actual CPU duration should be greater than or equal to zero.");
        }
        this.mActualCpuDurationNanos = actualCpuDurationNanos;
    }

    public void setActualGpuDurationNanos(long actualGpuDurationNanos) {
        if (actualGpuDurationNanos < 0) {
            throw new IllegalArgumentException(
                    "the actual GPU duration should be greater than or equal to zero.");
        }
        this.mActualGpuDurationNanos = actualGpuDurationNanos;
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
        return workDuration.mActualTotalDurationNanos == this.mActualTotalDurationNanos
                && workDuration.mWorkPeriodStartTimestampNanos
                        == this.mWorkPeriodStartTimestampNanos
                && workDuration.mActualCpuDurationNanos == this.mActualCpuDurationNanos
                && workDuration.mActualGpuDurationNanos == this.mActualGpuDurationNanos;
    }

    public int hashCode() {
        return Objects.hash(
                Long.valueOf(this.mWorkPeriodStartTimestampNanos),
                Long.valueOf(this.mActualTotalDurationNanos),
                Long.valueOf(this.mActualCpuDurationNanos),
                Long.valueOf(this.mActualGpuDurationNanos));
    }
}
