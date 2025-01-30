package com.android.server.alarm;

import android.os.Build;
import java.io.PrintWriter;

/* compiled from: AppSyncInfo.java */
/* loaded from: classes.dex */
public abstract class AppSyncWrapper {
  public static final boolean LOG = "eng".equals(Build.TYPE);

  /* compiled from: AppSyncInfo.java */
  public enum SET_TYPE {
    APPSYNC3P_PACKAGES,
    SUSPICIOUS_PACKAGES,
    CSC_PACKAGES,
    ALLOWLIST_PACKAGES,
    ALLOWLIST3P_PACKAGES,
    PRELOADED_PACKAGES,
    SUSPICIOUS_PACKAGES_FROM_CONFIG,
    ALLOWLIST_PACKAGES_FROM_CONFIG,
    ALLOWLIST3P_PACKAGES_FROM_CONFIG,
    NONE
  }

  public abstract void dump(PrintWriter printWriter, String str);

  public abstract long getWindowLength();

  public abstract boolean isAdjustableAlarm(int i, long j, long j2, long j3, int i2, String str);
}
