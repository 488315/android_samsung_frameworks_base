package com.android.server.knox.dar.sdp;

import android.os.SystemProperties;
import android.util.Log;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class SDPLog {
  public static final boolean DEBUG;

  static {
    DEBUG =
        "userdebug".equals(SystemProperties.get("ro.build.type"))
            || "eng".equals(SystemProperties.get("ro.build.type"));
  }

  /* renamed from: d */
  public static void m38d(String str) {
    m39d(null, str);
  }

  /* renamed from: d */
  public static void m39d(String str, String str2) {
    SDPLogger.enqMessage(SDPLogUtil.makeDebugMessage(str2));
    if (str2 != null) {
      if (str == null) {
        str = "SDPLog";
      }
      Log.d(str, str2);
    }
  }

  /* renamed from: i */
  public static void m43i(String str) {
    m45i(null, str, new Throwable());
  }

  /* renamed from: i */
  public static void m44i(String str, String str2) {
    m45i(str, str2, new Throwable());
  }

  /* renamed from: i */
  public static void m45i(String str, String str2, Throwable th) {
    for (String str3 : SDPLogUtil.makeInfoMessages(str2, th)) {
      SDPLogger.enqMessage(str3);
    }
    if (str2 != null) {
      if (str == null) {
        str = "SDPLog";
      }
      Log.i(str, str2);
    }
  }

  /* renamed from: e */
  public static void m40e(Exception exc) {
    m41e(null, exc);
  }

  /* renamed from: e */
  public static void m41e(String str, Exception exc) {
    m42e(null, str, exc);
  }

  /* renamed from: e */
  public static void m42e(String str, String str2, Exception exc) {
    for (String str3 : SDPLogUtil.makeErrorMessages(str2, exc)) {
      SDPLogger.enqMessage(str3);
    }
    if (str2 != null) {
      if (str == null) {
        str = "SDPLog";
      }
      Log.e(str, str2);
      exc.printStackTrace();
    }
  }

  /* renamed from: p */
  public static void m46p(Object... objArr) {
    if (DEBUG) {
      String currentTime = SDPLogUtil.getCurrentTime();
      String makePairs = SDPLogUtil.makePairs(objArr);
      SDPLogger.enqMessage(SDPLogUtil.makeParamMessage(currentTime, makePairs));
      Log.d("SDPLog.p", makePairs);
    }
  }

  public static void dump(PrintWriter printWriter) {
    SDPLogger.dump(printWriter);
  }
}
