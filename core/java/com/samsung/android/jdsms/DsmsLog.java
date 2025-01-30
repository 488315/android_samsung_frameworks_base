package com.samsung.android.jdsms;

import android.os.Build;
import android.util.Log;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class DsmsLog {
  private static final String ENG_BUILD = "eng";
  private static final boolean IS_ENG = ENG_BUILD.equals(Build.TYPE);
  public static final String TAG = "DSMS-FRAMEWORK";

  public static boolean isDebuggable() {
    return IS_ENG && Log.isLoggable(TAG, 3);
  }

  /* renamed from: d */
  public static void m278d(String msg) {
    if (isDebuggable()) {
      Log.m94d(TAG, msg);
    }
  }

  /* renamed from: e */
  public static void m280e(String msg) {
    Log.m96e(TAG, msg);
  }

  /* renamed from: w */
  public static void m284w(String msg) {
    Log.m102w(TAG, msg);
  }

  /* renamed from: i */
  public static void m282i(String msg) {
    Log.m98i(TAG, msg);
  }

  /* renamed from: d */
  public static void m279d(String subtag, String msg) {
    if (isDebuggable()) {
      println(3, TAG, subtag, msg);
    }
  }

  /* renamed from: e */
  public static void m281e(String subtag, String msg) {
    println(6, TAG, subtag, msg);
  }

  /* renamed from: i */
  public static void m283i(String subtag, String msg) {
    println(4, TAG, subtag, msg);
  }

  /* renamed from: w */
  public static void m285w(String subtag, String msg) {
    println(5, TAG, subtag, msg);
  }

  private static void println(int priority, String tag, String subtag, String msg) {
    Log.println(
        priority,
        tag,
        String.format("[%s] %s", Objects.toString(subtag, ""), Objects.toString(msg, "")));
  }
}
