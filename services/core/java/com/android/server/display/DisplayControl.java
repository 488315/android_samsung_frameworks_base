package com.android.server.display;

import android.os.IBinder;
import java.util.Objects;

/* loaded from: classes2.dex */
public abstract class DisplayControl {
  private static native IBinder nativeCreateDisplay(String str, boolean z, float f);

  private static native void nativeDestroyDisplay(IBinder iBinder);

  private static native boolean nativeGetHdrOutputConversionSupport();

  private static native int[] nativeGetHdrOutputTypesWithLatency();

  private static native long[] nativeGetPhysicalDisplayIds();

  private static native IBinder nativeGetPhysicalDisplayToken(long j);

  private static native int[] nativeGetSupportedHdrOutputTypes();

  private static native void nativeOverrideHdrTypes(IBinder iBinder, int[] iArr);

  private static native int nativeSetHdrConversionMode(int i, int i2, int[] iArr, int i3);

  public static IBinder createDisplay(String str, boolean z) {
    Objects.requireNonNull(str, "name must not be null");
    return nativeCreateDisplay(str, z, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static IBinder createDisplay(String str, boolean z, float f) {
    Objects.requireNonNull(str, "name must not be null");
    return nativeCreateDisplay(str, z, f);
  }

  public static void destroyDisplay(IBinder iBinder) {
    if (iBinder == null) {
      throw new IllegalArgumentException("displayToken must not be null");
    }
    nativeDestroyDisplay(iBinder);
  }

  public static void overrideHdrTypes(IBinder iBinder, int[] iArr) {
    nativeOverrideHdrTypes(iBinder, iArr);
  }

  public static long[] getPhysicalDisplayIds() {
    return nativeGetPhysicalDisplayIds();
  }

  public static IBinder getPhysicalDisplayToken(long j) {
    return nativeGetPhysicalDisplayToken(j);
  }

  public static int setHdrConversionMode(int i, int i2, int[] iArr) {
    return nativeSetHdrConversionMode(i, i2, iArr, iArr != null ? iArr.length : 0);
  }

  public static int[] getSupportedHdrOutputTypes() {
    return nativeGetSupportedHdrOutputTypes();
  }

  public static int[] getHdrOutputTypesWithLatency() {
    return nativeGetHdrOutputTypesWithLatency();
  }

  public static boolean getHdrOutputConversionSupport() {
    return nativeGetHdrOutputConversionSupport();
  }
}
