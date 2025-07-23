package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.os.IBinder;
import android.util.Slog;
import dalvik.system.PathClassLoader;

/* loaded from: classes6.dex */
public class SemInputDeviceManagerLoader {
    private static final String SECINPUTDEV_SERVICE_CLASS = "com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService";
    private static final String SECINPUTDEV_SERVICE_JAR_PATH = "/system/framework/secinputdev-service.jar";
    private static final String TAG = "SemInputDeviceManagerLoader";
    private static Class secinputdevClass;

    public static void classLoadFromJar() throws Throwable {
        secinputdevClass = new PathClassLoader(SECINPUTDEV_SERVICE_JAR_PATH, ClassLoader.getSystemClassLoader()).loadClass(SECINPUTDEV_SERVICE_CLASS);
    }

    private static void classLoadFromServices() throws Throwable {
        secinputdevClass = Class.forName(SECINPUTDEV_SERVICE_CLASS);
    }

    public static IBinder getService(Context context) throws Throwable {
        if (secinputdevClass == null) {
            classLoadFromJar();
        }
        return (IBinder) secinputdevClass.getConstructor(Context.class).newInstance(context);
    }

    public static void systemReady() throws Throwable {
        Class cls = secinputdevClass;
        if (cls != null) {
            Class[] clsArr = new Class[0];
            cls.getMethod("systemReady", null).invoke(secinputdevClass, null);
        } else {
            Slog.e(TAG, "systemReady: secinpudevclass is null");
        }
    }
}
