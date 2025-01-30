package com.android.server.battery;

import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public abstract class BattFeatures {
    public static final boolean EUREKA_PROJECT;
    public static final boolean FEATURE_FULL_BATTERY_CYCLE;
    public static final String PRODUCT_NAME;
    public static final boolean SUPPORT_ECO_BATTERY;
    public static final String TAG = "BattFeatures";
    public static String cachedData;

    static {
        String trim = SystemProperties.get("ro.product.vendor.device", "NONE").trim();
        PRODUCT_NAME = trim;
        boolean z = trim.startsWith("e1") || trim.startsWith("e2") || trim.startsWith("e3");
        EUREKA_PROJECT = z;
        FEATURE_FULL_BATTERY_CYCLE = z || isSupport("BFSU");
        SUPPORT_ECO_BATTERY = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY_FEATURE");
    }

    public static String readSupportNode() {
        if (cachedData == null) {
            cachedData = BattUtils.readNode("/sys/class/power_supply/battery/support_functions");
        }
        Slog.d(TAG, "cachedData = " + cachedData);
        return cachedData;
    }

    public static boolean isSupport(String str) {
        String readSupportNode = readSupportNode();
        if (readSupportNode == null || !readSupportNode.contains(str)) {
            return false;
        }
        Slog.d(TAG, "isSupport(" + str + ") = true");
        return true;
    }
}
