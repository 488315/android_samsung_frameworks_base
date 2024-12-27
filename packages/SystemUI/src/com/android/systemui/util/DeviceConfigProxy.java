package com.android.systemui.util;

import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class DeviceConfigProxy {
    public void addOnPropertiesChangedListener(String str, Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.addOnPropertiesChangedListener(str, executor, onPropertiesChangedListener);
    }

    public boolean getBoolean(String str, String str2, boolean z) {
        return DeviceConfig.getBoolean(str, str2, z);
    }

    public float getFloat(String str, String str2, float f) {
        return DeviceConfig.getFloat(str, str2, f);
    }

    public int getInt(String str, String str2, int i) {
        return DeviceConfig.getInt(str, str2, i);
    }

    public long getLong(String str, String str2, long j) {
        return DeviceConfig.getLong(str, str2, j);
    }

    public String getProperty(String str, String str2) {
        return DeviceConfig.getProperty(str, str2);
    }

    public String getString(String str, String str2, String str3) {
        return DeviceConfig.getString(str, str2, str3);
    }

    public void removeOnPropertiesChangedListener(DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
    }

    public void resetToDefaults(int i, String str) {
        DeviceConfig.resetToDefaults(i, str);
    }

    public boolean setProperty(String str, String str2, String str3, boolean z) {
        return DeviceConfig.setProperty(str, str2, str3, z);
    }
}
