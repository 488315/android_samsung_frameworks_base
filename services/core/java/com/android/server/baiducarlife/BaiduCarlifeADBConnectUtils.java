package com.android.server.baiducarlife;

import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemProperties;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class BaiduCarlifeADBConnectUtils {
  public static Intent repackIntent(Intent intent) {
    if (intent.getComponent() != null) {
      String packageName = intent.getComponent().getPackageName();
      String className = intent.getComponent().getClassName();
      if ("com.baidu.carlife".equals(packageName)
          && "com.baidu.carlife.CarlifeActivity".equals(className)) {
        intent.setPackage("com.samsung.android.carlink");
        intent.setClassName(
            "com.samsung.android.carlink",
            "com.samsung.android.carlink.carlife.CarLifeUsbConnectionActivity");
        intent.setComponent(
            new ComponentName(
                "com.samsung.android.carlink",
                "com.samsung.android.carlink.carlife.CarLifeUsbConnectionActivity"));
        intent.setAction("com.samsung.android.carlink.carlife.USB_ADB_ATTACHED");
        intent.putExtra("usb_type", "adb");
      }
    }
    return intent;
  }

  public static boolean isCarlifeForceConnect() {
    return "true".equals(SystemProperties.get("persist.sys.adb.config.carlife_force"));
  }

  public static boolean isCarLinkIntent(Intent intent) {
    if (intent == null || intent.getComponent() == null) {
      return false;
    }
    return "com.samsung.android.carlink".equals(intent.getComponent().getPackageName());
  }

  public static boolean isBaiduCarlifePkg(String str) {
    if (str != null) {
      return "com.baidu.carlife".equals(str);
    }
    return false;
  }

  public static boolean isSamsungCarlifePkg(String str) {
    if (str != null) {
      return "com.samsung.android.carlink".equals(str);
    }
    return false;
  }

  public static void printForCarlifeStart(PrintWriter printWriter) {
    printWriter.println("Starting: Intent { cmp=com.baidu.carlife/.CarlifeActivity }");
  }

  public static void printForCarlifeStartWarning(PrintWriter printWriter) {
    printWriter.println(
        "Warning: Activity not started, its current task has been brought to the front.");
  }

  public static void printCarlifePkgInfo(PrintWriter printWriter) {
    printWriter.println("Package [com.baidu.carlife] (5d2de):");
    printWriter.println("pkg=Package{e6e9cbf com.baidu.carlife}");
    printWriter.println("applicationInfo=ApplicationInfo{fe0a00a com.baidu.carlife}");
    printWriter.println("dataDir=/data/user/0/com.baidu.carlife");
  }

  public static void printCarlifePathInfo(PrintWriter printWriter) {
    printWriter.print(
        "package:/data/app/~~iNjKNe-7WKMgdZXJDtvyIw==/com.baidu.carlife-TV26JIouWEDzMsoGghKuXg==/base.apk");
  }

  public static void printCarlifePkgName(PrintWriter printWriter) {
    printWriter.print("package:com.baidu.carlife");
  }

  public static void printCarlifeDumpActivity(PrintWriter printWriter) {
    printWriter.print("TASK com.baidu.carlife id=23203 userId=0\n");
    printWriter.print("ACTIVITY com.baidu.carlife/.CarlifeActivity 5b92975 pid=18428\n");
    printWriter.print("Local Activity bae17e9 State:\n");
    printWriter.print("mResumed=true mStopped=false mFinished=false\n");
    printWriter.print("mChangingConfigurations=false\n");
  }
}
