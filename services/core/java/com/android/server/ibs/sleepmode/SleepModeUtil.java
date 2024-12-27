package com.android.server.ibs.sleepmode;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import com.android.server.power.Slog;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public abstract class SleepModeUtil {
    public static final boolean DEBUG;

    static {
        String str = Build.TYPE;
        DEBUG = "eng".equals(str) || "userdebug".equals(str);
    }

    public static long getTime(String str) {
        String[] split = str.split(":");
        return (Long.valueOf(split[0]).longValue() * 60) + Long.valueOf(split[1]).longValue();
    }

    public static boolean handlePowerSavingModeViaApi(Context context, boolean z) {
        String str = z ? "psm_turn_on" : "psm_turn_off";
        try {
            Bundle bundle = new Bundle();
            bundle.putString("request_id", "sleepmode");
            Bundle call =
                    context.getContentResolver()
                            .call("com.samsung.android.sm.dcapi", str, (String) null, bundle);
            if (call == null) {
                Slog.d("SleepModeUtil", "wrong result");
                return false;
            }
            boolean z2 = call.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT);
            if (!z2) {
                Slog.e(
                        "SleepModeUtil",
                        "result "
                                + z2
                                + ", errId "
                                + call.getInt("error_id", -1)
                                + ", errMsg "
                                + call.getString("error_msg", ""));
            }
            Slog.d("SleepModeUtil", str + " " + z2 + ", version " + call.getInt("version", -1));
            return z2;
        } catch (Error | NullPointerException e) {
            Slog.e("SleepModeUtil", "Error : " + e);
            return false;
        }
    }

    public static boolean isDeviceIdleMode(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        boolean isDeviceIdleMode = powerManager != null ? powerManager.isDeviceIdleMode() : false;
        Slog.d("SleepModeUtil", "isIdle is " + isDeviceIdleMode);
        return isDeviceIdleMode;
    }

    public static boolean isPowerConnected(Context context) {
        Intent registerReceiver =
                context.registerReceiver(
                        null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), 2);
        boolean z = false;
        if (registerReceiver == null) {
            return false;
        }
        int intExtra = registerReceiver.getIntExtra("plugged", -1);
        if (DEBUG
                ? intExtra == 1 || intExtra == 4
                : intExtra == 1 || intExtra == 2 || intExtra == 4) {
            z = true;
        }
        Slog.d("SleepModeUtil", "charging is " + z);
        return z;
    }

    public static boolean isScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        boolean isInteractive = powerManager != null ? powerManager.isInteractive() : false;
        Slog.d("SleepModeUtil", "screenOn = " + isInteractive);
        return isInteractive;
    }
}
