package com.android.systemui.power;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.PowerUiRune;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BatteryProtectionUtil {
    public static final BatteryProtectionUtil INSTANCE = new BatteryProtectionUtil();

    private BatteryProtectionUtil() {
    }

    public static final int getProtectBatteryValue(Context context) {
        INSTANCE.getClass();
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_PROTECT_BATTERY, Settings.Global.getInt(context.getContentResolver(), "battery_protection_default_value", 0));
    }

    public static final boolean isMaximumProtectionEnabled(Context context) {
        int protectBatteryValue = getProtectBatteryValue(context);
        return PowerUiRune.PROTECT_BATTERY_CUTOFF && (protectBatteryValue == 1 || protectBatteryValue == 2);
    }

    public static final boolean isProtectedFullyByMaximum(int i) {
        return (i & 16777216) != 0;
    }

    public static final boolean isSleepChargingOn(Context context) {
        INSTANCE.getClass();
        return Settings.Global.getInt(context.getContentResolver(), "key_sleep_charging", 0) > 0;
    }

    public static final void setSleepChargingStatus(int i, Context context) {
        Settings.Global.putInt(context.getContentResolver(), "key_sleep_charging", i);
        if (i == 0) {
            INSTANCE.getClass();
            Settings.Global.putString(context.getContentResolver(), "sleep_charging_finish_time", "");
        }
    }
}
