package com.android.internal.util;

import android.os.Debug;
import android.os.StrictMode;

/* loaded from: classes5.dex */
public final class MemInfoReader {
  private static final int LIGHT_MEMINFO_COUNT = 14;
  final long[] mInfos = new long[36];
  private final long[] mLightInfos = new long[14];

  public void readMemInfo() {
    StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
    try {
      Debug.getMemInfo(this.mInfos);
    } finally {
      StrictMode.setThreadPolicy(savedPolicy);
    }
  }

  public void readLightMemInfo() {
    StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
    try {
      Debug.getMemInfo(this.mLightInfos);
      for (int i = 0; i < 14; i++) {
        this.mInfos[i] = this.mLightInfos[i];
      }
    } finally {
      StrictMode.setThreadPolicy(savedPolicy);
    }
  }

  public long getAvailableSize() {
    return this.mInfos[23] * 1024;
  }

  public long getCachedSizeLegacy() {
    return this.mInfos[3] * 1024;
  }

  public long getRbinTotalSize() {
    return this.mInfos[4] * 1024;
  }

  public long getRbinAllocedSize() {
    return this.mInfos[5] * 1024;
  }

  public long getActiveFileSizeKb() {
    return this.mInfos[26];
  }

  public long getInactiveFileSizeKb() {
    return this.mInfos[27];
  }

  public long getRbinFreeSizeKb() {
    return this.mInfos[6];
  }

  public long getRbinCachedSizeKb() {
    return this.mInfos[7];
  }

  public long getKReclaimableSizeKb() {
    return this.mInfos[19];
  }

  public long getSReclaimableSizeKb() {
    return this.mInfos[10];
  }

  public long getSUnreclaimSizeKb() {
    return this.mInfos[11];
  }

  public long getZram0SizeKb() {
    return this.mInfos[31];
  }

  public long getPageTablesSizeKb() {
    return this.mInfos[17];
  }

  public long getVmAllocUsedSizeKb() {
    return this.mInfos[16];
  }

  public long getMappedSizeKb() {
    return this.mInfos[15];
  }

  public long getAnonPagesSizeKb() {
    return this.mInfos[30];
  }

  public long getShmemSizeKb() {
    return this.mInfos[8];
  }

  public long getGpuTotalSizeKb() {
    return this.mInfos[32];
  }

  public long getGpuSwapSizeKb() {
    return this.mInfos[33];
  }

  public long getSystemCachedSizeKb() {
    return this.mInfos[34];
  }

  public long getSystemUncachedSizeKb() {
    return this.mInfos[35];
  }

  public long getTotalSize() {
    return this.mInfos[0] * 1024;
  }

  public long getFreeSize() {
    return this.mInfos[1] * 1024;
  }

  public long getCachedSize() {
    return getCachedSizeKb() * 1024;
  }

  public long getKernelUsedSize() {
    return getKernelUsedSizeKb() * 1024;
  }

  public long getTotalSizeKb() {
    return this.mInfos[0];
  }

  public long getFreeSizeKb() {
    return this.mInfos[1];
  }

  public long getCachedSizeKb() {
    long[] jArr = this.mInfos;
    long kReclaimable = jArr[19];
    if (kReclaimable == 0) {
      kReclaimable = jArr[10];
    }
    return ((jArr[2] + kReclaimable) + jArr[3]) - jArr[15];
  }

  public long getKernelUsedSizeKb() {
    long[] jArr = this.mInfos;
    long size = jArr[8] + jArr[11] + jArr[16] + jArr[17];
    if (!Debug.isVmapStack()) {
      return size + this.mInfos[18];
    }
    return size;
  }

  public long getSwapTotalSizeKb() {
    return this.mInfos[12];
  }

  public long getSwapFreeSizeKb() {
    return this.mInfos[13];
  }

  public long getZramTotalSizeKb() {
    return this.mInfos[14];
  }

  public long[] getRawInfo() {
    return this.mInfos;
  }
}
