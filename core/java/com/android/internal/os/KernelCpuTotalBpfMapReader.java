package com.android.internal.os;

/* loaded from: classes5.dex */
public final class KernelCpuTotalBpfMapReader {
  private static native long[] readInternal();

  private KernelCpuTotalBpfMapReader() {}

  public static long[] read() {
    if (!KernelCpuBpfTracking.startTracking()) {
      return null;
    }
    return readInternal();
  }
}
