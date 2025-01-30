package com.android.server.cpu;

import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes.dex */
public final class CpuAvailabilityInfo {
    public final int cpuset;
    public final long dataTimestampUptimeMillis;
    public final int latestAvgAvailabilityPercent;
    public final int pastNMillisAvgAvailabilityPercent;
    public final long pastNMillisDuration;

    public String toString() {
        return "CpuAvailabilityInfo{cpuset = " + this.cpuset + ", dataTimestampUptimeMillis = " + this.dataTimestampUptimeMillis + ", latestAvgAvailabilityPercent = " + this.latestAvgAvailabilityPercent + ", pastNMillisAvgAvailabilityPercent = " + this.pastNMillisAvgAvailabilityPercent + ", pastNMillisDuration = " + this.pastNMillisDuration + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CpuAvailabilityInfo)) {
            return false;
        }
        CpuAvailabilityInfo cpuAvailabilityInfo = (CpuAvailabilityInfo) obj;
        return this.cpuset == cpuAvailabilityInfo.cpuset && this.dataTimestampUptimeMillis == cpuAvailabilityInfo.dataTimestampUptimeMillis && this.latestAvgAvailabilityPercent == cpuAvailabilityInfo.latestAvgAvailabilityPercent && this.pastNMillisAvgAvailabilityPercent == cpuAvailabilityInfo.pastNMillisAvgAvailabilityPercent && this.pastNMillisDuration == cpuAvailabilityInfo.pastNMillisDuration;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.cpuset), Long.valueOf(this.dataTimestampUptimeMillis), Integer.valueOf(this.latestAvgAvailabilityPercent), Integer.valueOf(this.pastNMillisAvgAvailabilityPercent), Long.valueOf(this.pastNMillisDuration));
    }

    public CpuAvailabilityInfo(int i, long j, int i2, int i3, long j2) {
        this.cpuset = Preconditions.checkArgumentInRange(i, 1, 2, "cpuset");
        this.dataTimestampUptimeMillis = Preconditions.checkArgumentNonnegative(j);
        this.latestAvgAvailabilityPercent = Preconditions.checkArgumentNonnegative(i2);
        this.pastNMillisAvgAvailabilityPercent = i3;
        this.pastNMillisDuration = Preconditions.checkArgumentNonnegative(j2);
    }
}
