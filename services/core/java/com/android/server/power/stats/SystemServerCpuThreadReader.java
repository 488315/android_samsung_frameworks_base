package com.android.server.power.stats;

import android.os.Process;
import com.android.internal.os.KernelSingleProcessCpuThreadReader;

/* loaded from: classes3.dex */
public class SystemServerCpuThreadReader {
  public final SystemServiceCpuThreadTimes mDeltaCpuThreadTimes;
  public final KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
  public long[] mLastBinderThreadCpuTimesUs;
  public long[] mLastThreadCpuTimesUs;

  public class SystemServiceCpuThreadTimes {
    public long[] binderThreadCpuTimesUs;
    public long[] threadCpuTimesUs;
  }

  public static SystemServerCpuThreadReader create() {
    return new SystemServerCpuThreadReader(
        KernelSingleProcessCpuThreadReader.create(Process.myPid()));
  }

  public SystemServerCpuThreadReader(
      int i, KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader) {
    this(new KernelSingleProcessCpuThreadReader(i, cpuTimeInStateReader));
  }

  public SystemServerCpuThreadReader(
      KernelSingleProcessCpuThreadReader kernelSingleProcessCpuThreadReader) {
    this.mDeltaCpuThreadTimes = new SystemServiceCpuThreadTimes();
    this.mKernelCpuThreadReader = kernelSingleProcessCpuThreadReader;
  }

  public void startTrackingThreadCpuTime() {
    this.mKernelCpuThreadReader.startTrackingThreadCpuTimes();
  }

  public void setBinderThreadNativeTids(int[] iArr) {
    this.mKernelCpuThreadReader.setSelectedThreadIds(iArr);
  }

  public SystemServiceCpuThreadTimes readDelta() {
    int cpuFrequencyCount = this.mKernelCpuThreadReader.getCpuFrequencyCount();
    if (this.mLastThreadCpuTimesUs == null) {
      this.mLastThreadCpuTimesUs = new long[cpuFrequencyCount];
      this.mLastBinderThreadCpuTimesUs = new long[cpuFrequencyCount];
      SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = this.mDeltaCpuThreadTimes;
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
      this.mDeltaCpuThreadTimes.threadCpuTimesUs[i] =
          Math.max(0L, j - this.mLastThreadCpuTimesUs[i]);
      this.mDeltaCpuThreadTimes.binderThreadCpuTimesUs[i] =
          Math.max(0L, j2 - this.mLastBinderThreadCpuTimesUs[i]);
      this.mLastThreadCpuTimesUs[i] = j;
      this.mLastBinderThreadCpuTimesUs[i] = j2;
    }
    return this.mDeltaCpuThreadTimes;
  }

  public SystemServiceCpuThreadTimes readAbsolute() {
    int cpuFrequencyCount = this.mKernelCpuThreadReader.getCpuFrequencyCount();
    KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage =
        this.mKernelCpuThreadReader.getProcessCpuUsage();
    if (processCpuUsage == null) {
      return null;
    }
    SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = new SystemServiceCpuThreadTimes();
    systemServiceCpuThreadTimes.threadCpuTimesUs = new long[cpuFrequencyCount];
    systemServiceCpuThreadTimes.binderThreadCpuTimesUs = new long[cpuFrequencyCount];
    for (int i = 0; i < cpuFrequencyCount; i++) {
      systemServiceCpuThreadTimes.threadCpuTimesUs[i] =
          processCpuUsage.threadCpuTimesMillis[i] * 1000;
      systemServiceCpuThreadTimes.binderThreadCpuTimesUs[i] =
          processCpuUsage.selectedThreadCpuTimesMillis[i] * 1000;
    }
    return systemServiceCpuThreadTimes;
  }
}
