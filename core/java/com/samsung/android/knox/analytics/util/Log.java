package com.samsung.android.knox.analytics.util;

import android.p009os.SemSystemProperties;

/* loaded from: classes5.dex */
public final class Log {
  static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

  /* renamed from: d */
  public static void m286d(String tag, String message) {
    if (DEBUG) {
      android.util.Log.m94d(tag, message);
    }
  }

  /* renamed from: e */
  public static void m287e(String tag, String message) {
    if (DEBUG) {
      android.util.Log.m96e(tag, message);
    }
  }

  /* renamed from: e */
  public static void m288e(String tag, String message, Throwable tr) {
    if (DEBUG) {
      android.util.Log.m97e(tag, message, tr);
    }
  }
}
