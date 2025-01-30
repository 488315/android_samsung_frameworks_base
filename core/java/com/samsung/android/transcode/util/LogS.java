package com.samsung.android.transcode.util;

import android.util.Log;
import java.io.File;

/* loaded from: classes5.dex */
public class LogS {
  public static final String TAG = "TranscodeLib";
  private static final String DEBUG_FILE = "/storage/emulated/0/DCIM/transcodelib.debug";
  private static boolean DEBUG = new File(DEBUG_FILE).exists();

  /* renamed from: v */
  public static void m476v(String tag, String log) {
    if (DEBUG) {
      Log.m98i(tag, log);
    } else {
      Log.m100v(tag, log);
    }
  }

  /* renamed from: d */
  public static void m473d(String tag, String log) {
    if (DEBUG) {
      Log.m98i(tag, log);
    } else {
      Log.m94d(tag, log);
    }
  }

  /* renamed from: i */
  public static void m475i(String tag, String log) {
    Log.m98i(tag, log);
  }

  /* renamed from: w */
  public static void m477w(String tag, String log) {
    Log.m102w(tag, log);
  }

  /* renamed from: e */
  public static void m474e(String tag, String log) {
    Log.m96e(tag, log);
  }

  public static void stackTrace(String tag) {
    StringBuilder stacktrace = new StringBuilder();
    StackTraceElement[] stackTrace = new Exception().getStackTrace();
    int len = stackTrace.length;
    for (int x = 2; x < len; x++) {
      stacktrace.append(stackTrace[x].toString()).append('\n');
    }
    Log.m98i(tag, "------------ Stacktrace ---------------");
    Log.m98i(tag, stacktrace.toString());
  }
}
