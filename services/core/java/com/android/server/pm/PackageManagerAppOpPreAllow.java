package com.android.server.pm;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.SystemProperties;
import android.text.TextUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class PackageManagerAppOpPreAllow {
  public static void appOpPreAllow(boolean z, boolean z2, Context context) {
    if (z || z2) {
      String str = SystemProperties.get("persist.omc.sales_code");
      if (TextUtils.isEmpty(str)) {
        str = SystemProperties.get("ro.csc.sales_code");
      }
      if (!TextUtils.isEmpty(str) && "VZW".equals(str)) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("com.verizon.mips.services");
        arrayList.add("com.vzw.hss.myverizon");
        arrayList.add("com.verizon.pushtotalkplus");
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        for (String str2 : arrayList) {
          try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str2, 0);
            if (packageInfo != null && packageInfo.applicationInfo.isSystemApp()) {
              appOpsManager.setMode(
                  "android:system_alert_window", packageInfo.applicationInfo.uid, str2, 0);
            }
          } catch (Exception unused) {
          }
        }
      }
    }
    if (z || z2) {
      String str3 = SystemProperties.get("persist.omc.sales_code");
      if (TextUtils.isEmpty(str3)) {
        str3 = SystemProperties.get("ro.csc.sales_code");
      }
      if (!TextUtils.isEmpty(str3) && "DSG".equals(str3)) {
        ArrayList<String> arrayList2 = new ArrayList();
        arrayList2.add("com.dish.wireless.activation");
        AppOpsManager appOpsManager2 =
            (AppOpsManager) context.getSystemService(AppOpsManager.class);
        for (String str4 : arrayList2) {
          try {
            PackageInfo packageInfo2 = context.getPackageManager().getPackageInfo(str4, 0);
            if (packageInfo2 != null && packageInfo2.applicationInfo.isSystemApp()) {
              appOpsManager2.setMode(
                  "android:system_alert_window", packageInfo2.applicationInfo.uid, str4, 0);
            }
          } catch (Exception unused2) {
          }
        }
      }
    }
    if (z) {
      try {
        PackageInfo packageInfo3 =
            context.getPackageManager().getPackageInfo("com.sec.android.app.samsungapps", 0);
        if (packageInfo3 == null || !packageInfo3.applicationInfo.isSystemApp()) {
          return;
        }
        ((AppOpsManager) context.getSystemService(AppOpsManager.class))
            .setMode(
                "android:system_alert_window",
                packageInfo3.applicationInfo.uid,
                "com.sec.android.app.samsungapps",
                0);
      } catch (Exception unused3) {
      }
    }
  }
}
