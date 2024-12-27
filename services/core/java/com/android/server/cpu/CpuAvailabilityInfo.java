package com.android.server.cpu;

import com.android.internal.util.Preconditions;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CpuAvailabilityInfo {
    public final int cpuset;
    public final long dataTimestampUptimeMillis;
    public final int latestAvgAvailabilityPercent;
    public final int pastNMillisAvgAvailabilityPercent;
    public final long pastNMillisDuration;

    public CpuAvailabilityInfo(int i, int i2, int i3, long j, long j2) {
        this.cpuset = Preconditions.checkArgumentInRange(i, 1, 2, "cpuset");
        this.dataTimestampUptimeMillis = Preconditions.checkArgumentNonnegative(j);
        this.latestAvgAvailabilityPercent = Preconditions.checkArgumentNonnegative(i2);
        this.pastNMillisAvgAvailabilityPercent = i3;
        this.pastNMillisDuration = Preconditions.checkArgumentNonnegative(j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CpuAvailabilityInfo)) {
            return false;
        }
        CpuAvailabilityInfo cpuAvailabilityInfo = (CpuAvailabilityInfo) obj;
        return this.cpuset == cpuAvailabilityInfo.cpuset
                && this.dataTimestampUptimeMillis == cpuAvailabilityInfo.dataTimestampUptimeMillis
                && this.latestAvgAvailabilityPercent
                        == cpuAvailabilityInfo.latestAvgAvailabilityPercent
                && this.pastNMillisAvgAvailabilityPercent
                        == cpuAvailabilityInfo.pastNMillisAvgAvailabilityPercent
                && this.pastNMillisDuration == cpuAvailabilityInfo.pastNMillisDuration;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.cpuset),
                Long.valueOf(this.dataTimestampUptimeMillis),
                Integer.valueOf(this.latestAvgAvailabilityPercent),
                Integer.valueOf(this.pastNMillisAvgAvailabilityPercent),
                Long.valueOf(this.pastNMillisDuration));
    }

    public final String toString() {
        return "CpuAvailabilityInfo{cpuset = "
                + this.cpuset
                + ", dataTimestampUptimeMillis = "
                + this.dataTimestampUptimeMillis
                + ", latestAvgAvailabilityPercent = "
                + this.latestAvgAvailabilityPercent
                + ", pastNMillisAvgAvailabilityPercent = "
                + this.pastNMillisAvgAvailabilityPercent
                + ", pastNMillisDuration = "
                + this.pastNMillisDuration
                + '}';
    }
}
