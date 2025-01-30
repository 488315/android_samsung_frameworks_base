package com.android.internal.p029os;

import android.p009os.Process;

/* loaded from: classes5.dex */
public final class SelectedProcessCpuThreadReader {
  private final String[] mCmdline;
  private KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
  private int mPid;

  public SelectedProcessCpuThreadReader(String cmdline) {
    this.mCmdline = new String[] {cmdline};
  }

  public KernelSingleProcessCpuThreadReader.ProcessCpuUsage readAbsolute() {
    int[] pids = Process.getPidsForCommands(this.mCmdline);
    if (pids == null || pids.length != 1) {
      return null;
    }
    int pid = pids[0];
    if (this.mPid == pid) {
      return this.mKernelCpuThreadReader.getProcessCpuUsage();
    }
    this.mPid = pid;
    KernelSingleProcessCpuThreadReader create = KernelSingleProcessCpuThreadReader.create(pid);
    this.mKernelCpuThreadReader = create;
    create.startTrackingThreadCpuTimes();
    return null;
  }
}
