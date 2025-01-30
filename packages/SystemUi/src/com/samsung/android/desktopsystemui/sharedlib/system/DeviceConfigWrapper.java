package com.samsung.android.desktopsystemui.sharedlib.system;

import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DeviceConfigWrapper {
    public static void addOnPropertiesChangedListener(String str, Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.addOnPropertiesChangedListener(str, executor, onPropertiesChangedListener);
    }

    public static boolean getBoolean(String str, String str2, boolean z) {
        return DeviceConfig.getBoolean(str, str2, z);
    }

    public static float getFloat(String str, String str2, float f) {
        return DeviceConfig.getFloat(str, str2, f);
    }

    public static int getInt(String str, String str2, int i) {
        return DeviceConfig.getInt(str, str2, i);
    }

    public static long getLong(String str, String str2, long j) {
        return DeviceConfig.getLong(str, str2, j);
    }

    public static String getProperty(String str, String str2) {
        return DeviceConfig.getProperty(str, str2);
    }

    public static String getString(String str, String str2, String str3) {
        return DeviceConfig.getString(str, str2, str3);
    }

    public static void removeOnPropertiesChangedListener(DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
    }

    public static boolean setProperty(String str, String str2, String str3, boolean z) {
        return DeviceConfig.setProperty(str, str2, str3, z);
    }
}
