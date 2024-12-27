package com.android.server.wm;

import android.os.Build;
import android.provider.DeviceConfig;

import java.util.HashSet;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SplashScreenExceptionList {
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
                        SplashScreenExceptionList splashScreenExceptionList =
                                SplashScreenExceptionList.this;
                        splashScreenExceptionList.getClass();
                        splashScreenExceptionList.updateDeviceConfig(
                                properties.getString("splash_screen_exception_list", ""));
                    }
                };
        this.mOnPropertiesChangedListener = onPropertiesChangedListener;
        DeviceConfig.addOnPropertiesChangedListener(
                "window_manager", executor, onPropertiesChangedListener);
    }

    public void updateDeviceConfig(String str) {
        synchronized (this.mLock) {
            try {
                this.mDeviceConfigExcludedPackages.clear();
                for (String str2 : str.split(",")) {
                    String trim = str2.trim();
                    if (!trim.isEmpty()) {
                        this.mDeviceConfigExcludedPackages.add(trim);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
