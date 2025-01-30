package com.samsung.android.server.continuity.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.Log;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class Utils {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static int mIsHighPowerConsumptionChipset = -1;
    public static int mIsWatch = -1;

    public static boolean isPackageInstalled(Context context, String str) {
        if (str == null) {
            return false;
        }
        Iterator<ApplicationInfo> it = context.getPackageManager().getInstalledApplications(128).iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMac(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return Pattern.compile("^([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])$").matcher(str).find();
    }

    public static String secureMac(String str) {
        if (str == null) {
            return "";
        }
        if (DEBUG || !isMac(str)) {
            return str;
        }
        return "($m)" + str.substring(9, 14);
    }

    public static String secureName(String str) {
        if (str == null) {
            return "";
        }
        if (DEBUG) {
            return str;
        }
        int length = str.length();
        if (length > 10) {
            return str.charAt(0) + "*" + str.charAt(3) + "*" + str.substring(length - 3, length);
        }
        if (length > 7) {
            return str.charAt(0) + "*" + str.charAt(3) + "*" + str.substring(length - 2, length);
        }
        if (length > 5) {
            return str.charAt(0) + "*" + str.substring(length - 2, length);
        }
        if (length <= 3) {
            return str;
        }
        return str.charAt(0) + "*";
    }

    public static boolean isHighPowerConsumptionChipset() {
        if (mIsHighPowerConsumptionChipset == -1) {
            String str = SystemProperties.get("vendor.bluetooth_fw_ver");
            if (str != null) {
                str = str.toUpperCase(Locale.ENGLISH);
            }
            String substring = (str == null || str.length() <= 5 || !str.startsWith("UTC")) ? null : str.substring(4);
            if (substring != null && (substring.startsWith("LEMAN") || substring.startsWith("LASSEN") || substring.startsWith("NEUS"))) {
                mIsHighPowerConsumptionChipset = 1;
            } else {
                mIsHighPowerConsumptionChipset = 0;
            }
        }
        Log.d("[MCF_DS_SYS]_Utils", "isHighPowerConsumptionChipset : " + mIsHighPowerConsumptionChipset);
        return mIsHighPowerConsumptionChipset == 1;
    }

    public static void clearHighPowerConsumptionChipset() {
        mIsHighPowerConsumptionChipset = -1;
    }

    public static boolean isWatch() {
        if (mIsWatch == -1) {
            String str = SystemProperties.get("ro.build.characteristics");
            if (str != null && str.contains("watch")) {
                mIsWatch = 1;
            } else {
                mIsWatch = 0;
            }
        }
        return mIsWatch == 1;
    }
}
