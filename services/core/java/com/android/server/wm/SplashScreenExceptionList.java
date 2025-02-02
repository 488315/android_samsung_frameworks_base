package com.android.server.wm;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.DeviceConfig;
import android.util.Slog;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SplashScreenExceptionList {
  public static final boolean DEBUG = Build.isDebuggable();
  public final HashSet mDeviceConfigExcludedPackages = new HashSet();
  public final Object mLock = new Object();
  final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;

  public SplashScreenExceptionList(Executor executor) {
    updateDeviceConfig(
        DeviceConfig.getString("window_manager", "splash_screen_exception_list", ""));
    DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener =
        new DeviceConfig
            .OnPropertiesChangedListener() { // from class:
                                             // com.android.server.wm.SplashScreenExceptionList$$ExternalSyntheticLambda0
          public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            SplashScreenExceptionList.this.lambda$new$0(properties);
          }
        };
    this.mOnPropertiesChangedListener = onPropertiesChangedListener;
    DeviceConfig.addOnPropertiesChangedListener(
        "window_manager", executor, onPropertiesChangedListener);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
    updateDeviceConfig(properties.getString("splash_screen_exception_list", ""));
  }

  public void updateDeviceConfig(String str) {
    parseDeviceConfigPackageList(str);
  }

  public boolean isException(String str, int i, Supplier supplier) {
    if (i > 35) {
      return false;
    }
    synchronized (this.mLock) {
      if (DEBUG) {
        Slog.v(
            "SplashScreenExceptionList",
            String.format(
                Locale.US,
                "SplashScreen checking exception for package %s (target sdk:%d) -> %s",
                str,
                Integer.valueOf(i),
                Boolean.valueOf(this.mDeviceConfigExcludedPackages.contains(str))));
      }
      if (this.mDeviceConfigExcludedPackages.contains(str)) {
        return !isOptedOut(supplier);
      }
      return false;
    }
  }

  public static boolean isOptedOut(Supplier supplier) {
    ApplicationInfo applicationInfo;
    Bundle bundle;
    return (supplier == null
            || (applicationInfo = (ApplicationInfo) supplier.get()) == null
            || (bundle = applicationInfo.metaData) == null
            || !bundle.getBoolean("android.splashscreen.exception_opt_out", false))
        ? false
        : true;
  }

  public final void parseDeviceConfigPackageList(String str) {
    synchronized (this.mLock) {
      this.mDeviceConfigExcludedPackages.clear();
      for (String str2 : str.split(",")) {
        String trim = str2.trim();
        if (!trim.isEmpty()) {
          this.mDeviceConfigExcludedPackages.add(trim);
        }
      }
    }
  }
}
