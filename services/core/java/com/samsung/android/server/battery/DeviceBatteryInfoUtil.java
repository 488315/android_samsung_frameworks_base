package com.samsung.android.server.battery;

import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;

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
            String replaceAll = str.replaceAll(XmlUtils.STRING_ARRAY_SEPARATOR, "");
            return replaceAll.substring(0, 6) + "_" + replaceAll.substring(11);
        } catch (Exception e) {
            Slog.e("DeviceBatteryInfoUtil", "getAddressForLog(Exception occurred) : " + e);
            return "unknown";
        }
    }
}
