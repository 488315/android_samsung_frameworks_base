package com.android.internal.util;

/* loaded from: classes5.dex */
public class FastMath {
  public static int round(float value) {
    long lx = (long) (1.6777216E7f * value);
    return (int) ((8388608 + lx) >> 24);
  }
}
