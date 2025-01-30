package com.android.server.ledcoverapp;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.Log;

/* loaded from: classes2.dex */
public class LedCoverAppService extends Binder {
  public LedCoverAppService(Context context) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addDataScheme("package");
    intentFilter.addDataSchemeSpecificPart("com.samsung.android.app.ledcoverdream", 0);
    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter.setPriority(1000);
    context.registerReceiver(new UpdateReceiver(), intentFilter);
    backgroundAllowlist(context);
  }

  public class UpdateReceiver extends BroadcastReceiver {
    public UpdateReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action;
      if (intent == null || (action = intent.getAction()) == null) {
        return;
      }
      if (action.equals("android.intent.action.PACKAGE_REPLACED")
          || action.equals("android.intent.action.PACKAGE_ADDED")) {
        LedCoverAppService.backgroundAllowlist(context);
      }
    }
  }

  public static void backgroundAllowlist(Context context) {
    try {
      int i =
          context
              .getPackageManager()
              .getApplicationInfo("com.samsung.android.app.ledcoverdream", 0)
              .uid;
      String num = Integer.toString(i);
      if (i >= 1000 && num != null) {
        if (hasValidSignature(context, "com.samsung.android.app.ledcoverdream")) {
          ActivityManager.getService().backgroundAllowlistUid(i);
          Log.d("LedCoverAppService", "backgroundAllowlist successfully called");
          return;
        }
        return;
      }
      Log.e("LedCoverAppService", "backgroundAllowlist: bad uid: " + i + ", uidString: " + num);
    } catch (Exception e) {
      Log.e("LedCoverAppService", "backgroundAllowlist exception ", e);
    }
  }

  public static boolean hasValidSignature(Context context, String str) {
    try {
      PackageManager packageManager = context.getPackageManager();
      if (packageManager
          .getPackageInfo("android", 64)
          .signatures[0]
          .equals(packageManager.getPackageInfo(str, 64).signatures[0])) {
        Log.d("LedCoverAppService", "hasValidSignature, VALID : " + str);
        return true;
      }
    } catch (PackageManager.NameNotFoundException | NullPointerException e) {
      Log.e("LedCoverAppService", "hasValidSignature, exception " + e.toString());
    }
    return false;
  }
}
