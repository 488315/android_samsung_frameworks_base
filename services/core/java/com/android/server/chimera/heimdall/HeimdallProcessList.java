package com.android.server.chimera.heimdall;

import android.os.SystemClock;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public final class HeimdallProcessList {
    public HashSet mInProgressProcesses;
    public HashSet mProtectedProcesses;
    public Queue mReportedProcesses;
    public int mTimeoutReportProtectedHour;

    public final void pushProcessToReported(HeimdallProcessData heimdallProcessData) {
        HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                new HeimdallAlwaysRunningProcInfo(heimdallProcessData);
        heimdallAlwaysRunningProcInfo.reportTime = SystemClock.elapsedRealtime();
        ((LinkedList) this.mReportedProcesses).offer(heimdallAlwaysRunningProcInfo);
        Heimdall.log(
                String.format(
                        "Report-protecting (%dh) starts. "
                                + heimdallAlwaysRunningProcInfo.toDumpString(),
                        Integer.valueOf(this.mTimeoutReportProtectedHour)));
    }
}
