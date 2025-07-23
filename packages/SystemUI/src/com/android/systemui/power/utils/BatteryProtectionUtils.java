package com.android.systemui.power.utils;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.PowerUiRune;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BatteryProtectionUtils {
    public static final BatteryProtectionUtils INSTANCE = new BatteryProtectionUtils();

    private BatteryProtectionUtils() {
    }

    public static final int getProtectBatteryValue(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_PROTECT_BATTERY, SettingsUtils.globalGetInt(context, "battery_protection_default_value", 0));
    }

    public static final boolean isMaximumProtectionEnabled(Context context) {
        int protectBatteryValue = getProtectBatteryValue(context);
        if (PowerUiRune.PROTECT_BATTERY_CUTOFF) {
            return protectBatteryValue == 1 || protectBatteryValue == 2;
        }
        return false;
    }

    public static final boolean isProtectedFullyByMaximum(int i) {
        return (i & 16777216) != 0;
    }

    public static final boolean isSleepChargingOn(Context context) {
        INSTANCE.getClass();
        return SettingsUtils.globalGetInt(context, "key_sleep_charging", 0) > 0;
    }

    public static final void setSleepChargingStatus(int i, Context context) {
        Settings.Global.putInt(context.getContentResolver(), "key_sleep_charging", i);
        if (i == 0) {
            INSTANCE.getClass();
            Settings.Global.putString(context.getContentResolver(), "sleep_charging_finish_time", "");
        }
    }
}
