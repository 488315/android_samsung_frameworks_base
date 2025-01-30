package com.android.server.ibs.sleepmode;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.power.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.Calendar;

/* loaded from: classes2.dex */
public abstract class SleepModeUtil {
  public static final boolean DEBUG;

  static {
    String str = Build.TYPE;
    DEBUG = "eng".equals(str) || "userdebug".equals(str);
  }

  public static boolean isPsmEnabled(Context context) {
    return Settings.Global.getInt(context.getContentResolver(), "low_power", 0) == 1;
  }

  public static boolean isEmergencyModeEnabled(Context context) {
    return Settings.System.getInt(context.getContentResolver(), "emergency_mode", 0) == 1;
  }

  public static boolean isUpsmEnabled(Context context) {
    return Settings.System.getInt(context.getContentResolver(), "ultra_powersaving_mode", 0) == 1;
  }

  public static boolean isDeviceIdleMode(Context context) {
    PowerManager powerManager = (PowerManager) context.getSystemService("power");
    boolean isDeviceIdleMode = powerManager != null ? powerManager.isDeviceIdleMode() : false;
    Slog.m72d("SleepModeUtil", "isIdle is " + isDeviceIdleMode);
    return isDeviceIdleMode;
  }

  public static boolean isScreenOn(Context context) {
    PowerManager powerManager = (PowerManager) context.getSystemService("power");
    boolean isInteractive = powerManager != null ? powerManager.isInteractive() : false;
    Slog.m72d("SleepModeUtil", "screenOn = " + isInteractive);
    return isInteractive;
  }

  public static boolean isPowerConnected(Context context) {
    Intent registerReceiver =
        context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    boolean z = false;
    if (registerReceiver == null) {
      return false;
    }
    int intExtra = registerReceiver.getIntExtra("plugged", -1);
    if (DEBUG ? intExtra == 1 || intExtra == 4 : intExtra == 1 || intExtra == 2 || intExtra == 4) {
      z = true;
    }
    Slog.m72d("SleepModeUtil", "charging is " + z);
    return z;
  }

  public static boolean handlePowerSavingModeViaApi(Context context, boolean z) {
    String str = z ? "psm_turn_on" : "psm_turn_off";
    try {
      Bundle bundle = new Bundle();
      bundle.putString("request_id", "sleepmode");
      Bundle call =
          context
              .getContentResolver()
              .call("com.samsung.android.sm.dcapi", str, (String) null, bundle);
      if (call == null) {
        Slog.m72d("SleepModeUtil", "wrong result");
        return false;
      }
      boolean z2 = call.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT);
      if (!z2) {
        Slog.m74e(
            "SleepModeUtil",
            "result "
                + z2
                + ", errId "
                + call.getInt("error_id", -1)
                + ", errMsg "
                + call.getString("error_msg", ""));
      }
      Slog.m72d("SleepModeUtil", str + " " + z2 + ", version " + call.getInt("version", -1));
      return z2;
    } catch (Error | NullPointerException e) {
      Slog.m74e("SleepModeUtil", "Error : " + e);
      return false;
    }
  }

  public static boolean isLimitAppsAndHome(Context context) {
    return Settings.Global.getInt(
            context.getContentResolver(), "sem_power_mode_limited_apps_and_home_screen", 0)
        == 1;
  }

  public static long getTime(String str) {
    String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
    return (Long.valueOf(split[0]).longValue() * 60) + Long.valueOf(split[1]).longValue();
  }

  public static long getCurTime() {
    return Calendar.getInstance().getTimeInMillis();
  }
}
