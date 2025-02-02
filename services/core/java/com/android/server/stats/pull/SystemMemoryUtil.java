package com.android.server.stats.pull;

import android.os.Debug;

/* loaded from: classes3.dex */
public abstract class SystemMemoryUtil {

  public final class Metrics {
    public int activeAnonKb;
    public int activeFileKb;
    public int activeKb;
    public int availableKb;
    public int cmaFreeKb;
    public int cmaTotalKb;
    public int dmaBufTotalExportedKb;
    public int freeKb;
    public int gpuPrivateAllocationsKb;
    public int gpuTotalUsageKb;
    public int inactiveAnonKb;
    public int inactiveFileKb;
    public int inactiveKb;
    public int kernelStackKb;
    public int pageTablesKb;
    public int shmemKb;
    public int swapFreeKb;
    public int swapTotalKb;
    public int totalIonKb;
    public int totalKb;
    public int unaccountedKb;
    public int unreclaimableSlabKb;
    public int vmallocUsedKb;
  }

  public static Metrics getMetrics() {
    long j;
    int dmabufHeapTotalExportedKb = (int) Debug.getDmabufHeapTotalExportedKb();
    int gpuTotalUsageKb = (int) Debug.getGpuTotalUsageKb();
    int gpuPrivateMemoryKb = (int) Debug.getGpuPrivateMemoryKb();
    int dmabufTotalExportedKb = (int) Debug.getDmabufTotalExportedKb();
    long[] jArr = new long[36];
    Debug.getMemInfo(jArr);
    long j2 = jArr[19];
    if (j2 == 0) {
      j2 = jArr[10];
    }
    long j3 =
        jArr[1] + jArr[14] + jArr[2] + jArr[20] + jArr[21] + jArr[22] + jArr[11] + j2 + jArr[16]
            + jArr[17];
    if (!Debug.isVmapStack()) {
      j3 += jArr[18];
    }
    if (dmabufTotalExportedKb < 0 || gpuPrivateMemoryKb < 0) {
      j3 += Math.max(0, gpuTotalUsageKb);
      if (dmabufTotalExportedKb < 0) {
        if (dmabufHeapTotalExportedKb >= 0) {
          j = dmabufHeapTotalExportedKb;
        }
        Metrics metrics = new Metrics();
        metrics.unreclaimableSlabKb = (int) jArr[11];
        metrics.vmallocUsedKb = (int) jArr[16];
        metrics.pageTablesKb = (int) jArr[17];
        metrics.kernelStackKb = (int) jArr[18];
        metrics.shmemKb = (int) jArr[8];
        long j4 = jArr[0];
        metrics.totalKb = (int) j4;
        metrics.freeKb = (int) jArr[1];
        metrics.availableKb = (int) jArr[23];
        metrics.activeKb = (int) jArr[20];
        metrics.inactiveKb = (int) jArr[21];
        metrics.activeAnonKb = (int) jArr[24];
        metrics.inactiveAnonKb = (int) jArr[25];
        metrics.activeFileKb = (int) jArr[26];
        metrics.inactiveFileKb = (int) jArr[27];
        metrics.swapTotalKb = (int) jArr[12];
        metrics.swapFreeKb = (int) jArr[13];
        metrics.cmaTotalKb = (int) jArr[28];
        metrics.cmaFreeKb = (int) jArr[29];
        metrics.totalIonKb = dmabufHeapTotalExportedKb;
        metrics.gpuTotalUsageKb = gpuTotalUsageKb;
        metrics.gpuPrivateAllocationsKb = gpuPrivateMemoryKb;
        metrics.dmaBufTotalExportedKb = dmabufTotalExportedKb;
        metrics.unaccountedKb = (int) (j4 - j3);
        return metrics;
      }
      j = dmabufTotalExportedKb;
    } else {
      j = dmabufTotalExportedKb + gpuPrivateMemoryKb;
    }
    j3 += j;
    Metrics metrics2 = new Metrics();
    metrics2.unreclaimableSlabKb = (int) jArr[11];
    metrics2.vmallocUsedKb = (int) jArr[16];
    metrics2.pageTablesKb = (int) jArr[17];
    metrics2.kernelStackKb = (int) jArr[18];
    metrics2.shmemKb = (int) jArr[8];
    long j42 = jArr[0];
    metrics2.totalKb = (int) j42;
    metrics2.freeKb = (int) jArr[1];
    metrics2.availableKb = (int) jArr[23];
    metrics2.activeKb = (int) jArr[20];
    metrics2.inactiveKb = (int) jArr[21];
    metrics2.activeAnonKb = (int) jArr[24];
    metrics2.inactiveAnonKb = (int) jArr[25];
    metrics2.activeFileKb = (int) jArr[26];
    metrics2.inactiveFileKb = (int) jArr[27];
    metrics2.swapTotalKb = (int) jArr[12];
    metrics2.swapFreeKb = (int) jArr[13];
    metrics2.cmaTotalKb = (int) jArr[28];
    metrics2.cmaFreeKb = (int) jArr[29];
    metrics2.totalIonKb = dmabufHeapTotalExportedKb;
    metrics2.gpuTotalUsageKb = gpuTotalUsageKb;
    metrics2.gpuPrivateAllocationsKb = gpuPrivateMemoryKb;
    metrics2.dmaBufTotalExportedKb = dmabufTotalExportedKb;
    metrics2.unaccountedKb = (int) (j42 - j3);
    return metrics2;
  }
}
