package com.samsung.android.server.battery;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

public abstract class DeviceBatteryInfoUtil {
    public static String getAddressForLog(String str) {
        if (str == null) {
            return "null";
        }
        try {
            if (str.length() != 17) {
                return "unknown";
            }
            String replaceAll = str.replaceAll(":", "");
            return replaceAll.substring(0, 6) + "_" + replaceAll.substring(11);
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e, "getAddressForLog(Exception occurred) : ", "DeviceBatteryInfoUtil");
            return "unknown";
        }
    }
}
