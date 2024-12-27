package com.android.server.location.gnss.sec;

import android.os.Build;
import android.os.SystemProperties;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssVendorConfig {
    public static GnssVendorConfig mInstance;

    public static synchronized GnssVendorConfig getInstance() {
        GnssVendorConfig gnssVendorConfig;
        synchronized (GnssVendorConfig.class) {
            try {
                if (mInstance == null) {
                    mInstance = new GnssVendorConfig();
                }
                gnssVendorConfig = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return gnssVendorConfig;
    }

    public static boolean isBroadcomGnss() {
        return BatteryService$$ExternalSyntheticOutline0.m45m("vendor/etc/gnss/gps.xml");
    }

    public static boolean isIzatServiceEnabled() {
        return (!"qcom".equals(Build.HARDWARE)
                        || isBroadcomGnss()
                        || isLsiGnss()
                        || BatteryService$$ExternalSyntheticOutline0.m45m(
                                "vendor/etc/gnss/mnl.prop"))
                ? false
                : true;
    }

    public static boolean isLsiGnss() {
        return BatteryService$$ExternalSyntheticOutline0.m45m("vendor/etc/gnss/gps.cfg");
    }

    public static boolean isUnisocGnss() {
        return SystemProperties.get("ro.hardware.chipname", "Unknown")
                .toLowerCase()
                .contains("unisoc");
    }
}
