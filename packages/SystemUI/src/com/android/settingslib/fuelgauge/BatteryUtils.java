package com.android.settingslib.fuelgauge;

import android.os.SystemProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BatteryUtils {
    public static Boolean sChargingStringV2Enabled;

    public static void setChargingStringV2Enabled(Boolean bool) {
        setChargingStringV2Enabled(bool, true);
    }

    public static void setChargingStringV2Enabled(Boolean bool, boolean z) {
        if (z) {
            SystemProperties.set("charging_string.apply_v2", bool == null ? "" : String.valueOf(bool));
        }
        sChargingStringV2Enabled = bool;
    }
}
