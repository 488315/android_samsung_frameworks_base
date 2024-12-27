package com.samsung.android.server.battery;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
