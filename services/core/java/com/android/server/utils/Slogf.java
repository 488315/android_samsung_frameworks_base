package com.android.server.utils;

import android.util.Slog;
import android.util.TimingsTraceLog;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class Slogf {
  private static final Formatter sFormatter;
  private static final StringBuilder sMessageBuilder;

  static {
    TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SLog", 524288L);
    timingsTraceLog.traceBegin("static_init");
    StringBuilder sb = new StringBuilder();
    sMessageBuilder = sb;
    sFormatter = new Formatter(sb, Locale.ENGLISH);
    timingsTraceLog.traceEnd();
  }

  private Slogf() {
    throw new UnsupportedOperationException("provides only static methods");
  }

  /* renamed from: v */
  public static int m98v(String str, String str2) {
    return Slog.v(str, str2);
  }

  /* renamed from: v */
  public static int m99v(String str, String str2, Throwable th) {
    return Slog.v(str, str2, th);
  }

  /* renamed from: d */
  public static int m86d(String str, String str2) {
    return Slog.d(str, str2);
  }

  /* renamed from: d */
  public static int m87d(String str, String str2, Throwable th) {
    return Slog.d(str, str2, th);
  }

  /* renamed from: i */
  public static int m94i(String str, String str2) {
    return Slog.i(str, str2);
  }

  /* renamed from: i */
  public static int m95i(String str, String str2, Throwable th) {
    return Slog.i(str, str2, th);
  }

  /* renamed from: w */
  public static int m102w(String str, String str2) {
    return Slog.w(str, str2);
  }

  /* renamed from: w */
  public static int m103w(String str, String str2, Throwable th) {
    return Slog.w(str, str2, th);
  }

  /* renamed from: w */
  public static int m104w(String str, Throwable th) {
    return Slog.w(str, th);
  }

  /* renamed from: e */
  public static int m90e(String str, String str2) {
    return Slog.e(str, str2);
  }

  /* renamed from: e */
  public static int m91e(String str, String str2, Throwable th) {
    return Slog.e(str, str2, th);
  }

  public static int wtf(String str, String str2) {
    return Slog.wtf(str, str2);
  }

  public static void wtfQuiet(String str, String str2) {
    Slog.wtfQuiet(str, str2);
  }

  public static int wtfStack(String str, String str2) {
    return Slog.wtfStack(str, str2);
  }

  public static int wtf(String str, Throwable th) {
    return Slog.wtf(str, th);
  }

  public static int wtf(String str, String str2, Throwable th) {
    return Slog.wtf(str, str2, th);
  }

  public static int println(int i, String str, String str2) {
    return Slog.println(i, str, str2);
  }

  /* renamed from: v */
  public static void m100v(String str, String str2, Object... objArr) {
    m98v(str, getMessage(str2, objArr));
  }

  /* renamed from: v */
  public static void m101v(String str, Throwable th, String str2, Object... objArr) {
    m99v(str, getMessage(str2, objArr), th);
  }

  /* renamed from: d */
  public static void m88d(String str, String str2, Object... objArr) {
    m86d(str, getMessage(str2, objArr));
  }

  /* renamed from: d */
  public static void m89d(String str, Throwable th, String str2, Object... objArr) {
    m87d(str, getMessage(str2, objArr), th);
  }

  /* renamed from: i */
  public static void m96i(String str, String str2, Object... objArr) {
    m94i(str, getMessage(str2, objArr));
  }

  /* renamed from: i */
  public static void m97i(String str, Throwable th, String str2, Object... objArr) {
    m95i(str, getMessage(str2, objArr), th);
  }

  /* renamed from: w */
  public static void m105w(String str, String str2, Object... objArr) {
    m102w(str, getMessage(str2, objArr));
  }

  /* renamed from: w */
  public static void m106w(String str, Throwable th, String str2, Object... objArr) {
    m103w(str, getMessage(str2, objArr), th);
  }

  /* renamed from: e */
  public static void m92e(String str, String str2, Object... objArr) {
    m90e(str, getMessage(str2, objArr));
  }

  /* renamed from: e */
  public static void m93e(String str, Throwable th, String str2, Object... objArr) {
    m91e(str, getMessage(str2, objArr), th);
  }

  public static void wtf(String str, String str2, Object... objArr) {
    wtf(str, getMessage(str2, objArr));
  }

  public static void wtf(String str, Throwable th, String str2, Object... objArr) {
    wtf(str, getMessage(str2, objArr), th);
  }

  private static String getMessage(String str, Object... objArr) {
    String sb;
    StringBuilder sb2 = sMessageBuilder;
    synchronized (sb2) {
      sFormatter.format(str, objArr);
      sb = sb2.toString();
      sb2.setLength(0);
    }
    return sb;
  }
}
