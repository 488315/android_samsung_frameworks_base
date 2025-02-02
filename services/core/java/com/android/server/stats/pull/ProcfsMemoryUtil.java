package com.android.server.stats.pull;

import android.os.Process;
import android.os.IInstalld;
import android.util.SparseArray;

/* loaded from: classes3.dex */
public abstract class ProcfsMemoryUtil {
  public static final int[] CMDLINE_OUT = {IInstalld.FLAG_USE_QUOTA};
  public static final String[] STATUS_KEYS = {
    "Uid:", "VmHWM:", "VmRSS:", "RssAnon:", "RssShmem:", "VmSwap:"
  };
  public static final String[] VMSTAT_KEYS = {"oom_kill"};

  public final class MemorySnapshot {
    public int anonRssInKilobytes;
    public int rssHighWaterMarkInKilobytes;
    public int rssInKilobytes;
    public int rssShmemKilobytes;
    public int swapInKilobytes;
    public int uid;
  }

  public final class VmStat {
    public int oomKillCount;
  }

  public static MemorySnapshot readMemorySnapshotFromProcfs(int i) {
    String[] strArr = STATUS_KEYS;
    long[] jArr = new long[strArr.length];
    jArr[0] = -1;
    jArr[3] = -1;
    jArr[4] = -1;
    jArr[5] = -1;
    Process.readProcLines("/proc/" + i + "/status", strArr, jArr);
    if (jArr[0] == -1 || jArr[3] == -1 || jArr[4] == -1 || jArr[5] == -1) {
      return null;
    }
    MemorySnapshot memorySnapshot = new MemorySnapshot();
    memorySnapshot.uid = (int) jArr[0];
    memorySnapshot.rssHighWaterMarkInKilobytes = (int) jArr[1];
    memorySnapshot.rssInKilobytes = (int) jArr[2];
    memorySnapshot.anonRssInKilobytes = (int) jArr[3];
    memorySnapshot.rssShmemKilobytes = (int) jArr[4];
    memorySnapshot.swapInKilobytes = (int) jArr[5];
    return memorySnapshot;
  }

  public static String readCmdlineFromProcfs(int i) {
    String[] strArr = new String[1];
    StringBuilder sb = new StringBuilder();
    sb.append("/proc/");
    sb.append(i);
    sb.append("/cmdline");
    return !Process.readProcFile(sb.toString(), CMDLINE_OUT, strArr, null, null) ? "" : strArr[0];
  }

  public static SparseArray getProcessCmdlines() {
    int[] pids = Process.getPids("/proc", new int[1024]);
    SparseArray sparseArray = new SparseArray(pids.length);
    for (int i : pids) {
      if (i < 0) {
        break;
      }
      String readCmdlineFromProcfs = readCmdlineFromProcfs(i);
      if (!readCmdlineFromProcfs.isEmpty()) {
        sparseArray.append(i, readCmdlineFromProcfs);
      }
    }
    return sparseArray;
  }

  public static VmStat readVmStat() {
    String[] strArr = VMSTAT_KEYS;
    long[] jArr = new long[strArr.length];
    jArr[0] = -1;
    Process.readProcLines("/proc/vmstat", strArr, jArr);
    if (jArr[0] == -1) {
      return null;
    }
    VmStat vmStat = new VmStat();
    vmStat.oomKillCount = (int) jArr[0];
    return vmStat;
  }
}
