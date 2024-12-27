package com.android.server.power.stats;

import com.android.internal.os.KernelSingleProcessCpuThreadReader;

import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SystemServerCpuThreadReader {
    public final SystemServiceCpuThreadTimes mDeltaCpuThreadTimes;
    public final KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
    public long[] mLastBinderThreadCpuTimesUs;
    public long[] mLastThreadCpuTimesUs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemServiceCpuThreadTimes {
        public long[] binderThreadCpuTimesUs;
        public long[] threadCpuTimesUs;
    }

    public SystemServerCpuThreadReader(
            int i, KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader)
            throws IOException {
        this(new KernelSingleProcessCpuThreadReader(i, cpuTimeInStateReader));
    }

    public SystemServerCpuThreadReader(
            KernelSingleProcessCpuThreadReader kernelSingleProcessCpuThreadReader) {
        this.mDeltaCpuThreadTimes = new SystemServiceCpuThreadTimes();
        this.mKernelCpuThreadReader = kernelSingleProcessCpuThreadReader;
    }

    public final SystemServiceCpuThreadTimes readDelta() {
        int cpuFrequencyCount = this.mKernelCpuThreadReader.getCpuFrequencyCount();
        long[] jArr = this.mLastThreadCpuTimesUs;
        SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = this.mDeltaCpuThreadTimes;
        if (jArr == null) {
            this.mLastThreadCpuTimesUs = new long[cpuFrequencyCount];
            this.mLastBinderThreadCpuTimesUs = new long[cpuFrequencyCount];
            systemServiceCpuThreadTimes.threadCpuTimesUs = new long[cpuFrequencyCount];
            systemServiceCpuThreadTimes.binderThreadCpuTimesUs = new long[cpuFrequencyCount];
        }
        KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage =
                this.mKernelCpuThreadReader.getProcessCpuUsage();
        if (processCpuUsage == null) {
            return null;
        }
        for (int i = cpuFrequencyCount - 1; i >= 0; i--) {
            long j = processCpuUsage.threadCpuTimesMillis[i] * 1000;
            long j2 = processCpuUsage.selectedThreadCpuTimesMillis[i] * 1000;
            systemServiceCpuThreadTimes.threadCpuTimesUs[i] =
                    Math.max(0L, j - this.mLastThreadCpuTimesUs[i]);
            systemServiceCpuThreadTimes.binderThreadCpuTimesUs[i] =
                    Math.max(0L, j2 - this.mLastBinderThreadCpuTimesUs[i]);
            this.mLastThreadCpuTimesUs[i] = j;
            this.mLastBinderThreadCpuTimesUs[i] = j2;
        }
        return systemServiceCpuThreadTimes;
    }
}
