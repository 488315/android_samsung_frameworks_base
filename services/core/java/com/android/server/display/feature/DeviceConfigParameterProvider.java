package com.android.server.display.feature;

import android.provider.DeviceConfigInterface;
import android.util.Slog;

public final class DeviceConfigParameterProvider {
    public final DeviceConfigInterface mDeviceConfig;

    public DeviceConfigParameterProvider(DeviceConfigInterface deviceConfigInterface) {
        this.mDeviceConfig = deviceConfigInterface;
    }

    public final int[] getIntArrayProperty(String str) {
        String string = this.mDeviceConfig.getString("display_manager", str, (String) null);
        if (string == null) {
            return null;
        }
        String[] split = string.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            try {
                iArr[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                Slog.e("DisplayFeatureProvider", "Incorrect format for array: '" + string + "'", e);
                return null;
            }
        }
        return iArr;
    }
}
