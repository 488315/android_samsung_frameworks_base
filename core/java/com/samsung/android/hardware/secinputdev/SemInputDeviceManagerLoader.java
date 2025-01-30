package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.os.IBinder;
import android.util.Slog;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: classes5.dex */
public class SemInputDeviceManagerLoader {
  private static final String SECINPUTDEV_SERVICE_CLASS =
      "com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService";
  private static final String SECINPUTDEV_SERVICE_JAR_PATH =
      "/system/framework/secinputdev-service.jar";
  private static final String TAG = "SemInputDeviceManagerLoader";
  private static Class secinputdevClass = null;

  public static void classLoadFromJar() throws Throwable {
    PathClassLoader classLoader =
        new PathClassLoader(SECINPUTDEV_SERVICE_JAR_PATH, ClassLoader.getSystemClassLoader());
    secinputdevClass = classLoader.loadClass(SECINPUTDEV_SERVICE_CLASS);
  }

  private static void classLoadFromServices() throws Throwable {
    secinputdevClass = Class.forName(SECINPUTDEV_SERVICE_CLASS);
  }

  public static IBinder getService(Context context) throws Throwable {
    if (secinputdevClass == null) {
      classLoadFromJar();
    }
    Constructor constructor = secinputdevClass.getConstructor(Context.class);
    return (IBinder) constructor.newInstance(context);
  }

  public static void systemReady() throws Throwable {
    Class cls = secinputdevClass;
    if (cls != null) {
      Method method = cls.getMethod("systemReady", new Class[0]);
      method.invoke(secinputdevClass, new Object[0]);
    } else {
      Slog.m115e(TAG, "systemReady: secinpudevclass is null");
    }
  }
}
