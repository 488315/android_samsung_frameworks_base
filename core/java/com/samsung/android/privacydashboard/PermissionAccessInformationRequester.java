package com.samsung.android.privacydashboard;

import android.app.AppOpsManager;
import android.content.Context;

/* loaded from: classes5.dex */
public class PermissionAccessInformationRequester {
  private static final String VERSION = "1.0.0";

  public static String getVersion() {
    return "1.0.0";
  }

  public static void request(Context context) throws Exception {
    AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
    appOpsManager.requestPermissionAccessInformation();
  }
}
