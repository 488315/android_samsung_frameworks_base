package com.android.server.enterprise.plm.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class Utils {
  public static final String TAG = "Utils";

  public static boolean hasPackage(Context context, String str) {
    try {
      PackageManager packageManager = context.getPackageManager();
      if (packageManager != null) {
        packageManager.getPackageInfo(str, 0);
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("found package ");
        sb.append(str);
        sb.append(" ");
        sb.append(packageManager.getApplicationEnabledSetting(str) == 1 ? "enabled" : "disabled");
        Log.i(str2, sb.toString());
        return true;
      }
    } catch (Throwable th) {
      Log.e(TAG, th.toString());
    }
    Log.i(TAG, "failed to find package " + str);
    return false;
  }

  public static boolean hasService(Context context, String str, String str2) {
    try {
      PackageManager packageManager = context.getPackageManager();
      if (packageManager != null && str2 != null) {
        packageManager.getPackageInfo(str, 0);
        ServiceInfo[] serviceInfoArr = packageManager.getPackageInfo(str, 4).services;
        if (serviceInfoArr != null) {
          for (ServiceInfo serviceInfo : serviceInfoArr) {
            if (TextUtils.equals(serviceInfo.name, str2)) {
              boolean isEnabled = serviceInfo.isEnabled();
              String str3 = TAG;
              StringBuilder sb = new StringBuilder();
              sb.append("found service ");
              sb.append(str2);
              sb.append(" ");
              sb.append(isEnabled ? "enabled" : "disabled");
              Log.i(str3, sb.toString());
              return isEnabled;
            }
          }
        }
      }
    } catch (Throwable th) {
      Log.e(TAG, th.toString());
    }
    Log.i(TAG, "failed to find service " + str2);
    return false;
  }

  public static int getEnabledState(Context context, String str) {
    try {
      return context.getPackageManager().getApplicationEnabledSetting(str);
    } catch (Throwable th) {
      Log.e(TAG, th.toString());
      return 0;
    }
  }

  public static void setEnabledState(Context context, String str, boolean z) {
    try {
      PackageManager packageManager = context.getPackageManager();
      int i = z ? 1 : 2;
      if (packageManager == null || packageManager.getApplicationEnabledSetting(str) == i) {
        return;
      }
      String str2 = TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("set package ");
      sb.append(str);
      sb.append(z ? " enabled" : " disabled");
      Log.i(str2, sb.toString());
      packageManager.setApplicationEnabledSetting(str, i, 0);
    } catch (Throwable th) {
      Log.e(TAG, th.toString());
    }
  }
}
