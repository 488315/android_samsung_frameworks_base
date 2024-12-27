package com.samsung.android.hardware.secinputdev.hal;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import java.lang.reflect.Constructor;

/* loaded from: classes.dex */
public class SysinputHALFactory {
    private static final String TAG = "SysinputHALFactory";
    private static volatile SysinputHALInterface hal = null;
    private static final String halPackageName =
            "com.samsung.android.hardware.secinputdev.hal.SysinputHAL";

    public static int registerCallback() {
        if (hal == null) {
            Log.e(TAG, "registerCallback: HAL service is not initialized");
            return -3;
        }
        SysinputHALCallback callback = SysinputHALCallback.getInstance(hal);
        if (Float.compare(hal.getVersion(), 1.2f) >= 0) {
            return hal.registerCallback(callback);
        }
        Log.e(TAG, "registerCallback: HAL service is not connected");
        return -3;
    }

    public static SysinputHALInterface connectHAL() {
        if (hal != null) {
            return hal;
        }
        SysinputHALInterface hal2 = connectAidl();
        if (hal2 != null) {
            return hal2;
        }
        return connectHidl();
    }

    private static SysinputHALInterface connectAidl() {
        if (hal == null) {
            synchronized (SysinputHALFactory.class) {
                if (hal == null) {
                    hal =
                            getInterface(
                                    "com.samsung.android.hardware.secinputdev.hal.SysinputHAL_AIDL");
                }
            }
        }
        return hal;
    }

    private static SysinputHALInterface connectHidl() {
        if (hal == null) {
            synchronized (SysinputHALFactory.class) {
                if (hal == null) {
                    hal = connectHidlV1_3();
                    if (hal != null) {
                        return hal;
                    }
                    hal = connectHidlV1_2();
                    if (hal != null) {
                        return hal;
                    }
                    hal = new SysinputHALEmpty();
                }
            }
        }
        return hal;
    }

    private static SysinputHALInterface connectHidlV1_3() {
        return getInterface("com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_3");
    }

    private static SysinputHALInterface connectHidlV1_2() {
        return getInterface("com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2");
    }

    private static SysinputHALInterface getInterface(String packageName) {
        try {
            Class sysinputHALClass = Class.forName(packageName);
            Log.i(TAG, "getInterface: " + sysinputHALClass.getSimpleName() + " found");
            Constructor constructor = sysinputHALClass.getConstructor(new Class[0]);
            return (SysinputHALInterface) constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "getInterface: " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "getInterface: Exception at " + packageName);
            SemInputDeviceManagerService.loggingException(TAG, "getInterface", e2);
            return null;
        }
    }
}
