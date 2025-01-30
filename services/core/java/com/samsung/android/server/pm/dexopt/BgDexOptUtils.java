package com.samsung.android.server.pm.dexopt;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.os.BatteryManagerInternal;
import android.util.Log;
import com.android.server.LocalServices;

/* loaded from: classes2.dex */
public abstract class BgDexOptUtils {
    public static boolean isBatteryFullyCharged(Context context) {
        if (isRunningBgDexOptCTS(context)) {
            Log.d("BackgroundDexOptService", "Keep running");
            return true;
        }
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(BatteryManager.class);
        if (getBatteryLevel() < 100) {
            return batteryManager != null && batteryManager.computeChargeTimeRemaining() <= 0;
        }
        return true;
    }

    public static int getBatteryLevel() {
        return ((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class)).getBatteryLevel();
    }

    public static boolean isRunningBgDexOptCTS(Context context) {
        PackageInfo packageInfo;
        if (context == null) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo("android.compilation.cts", 0);
        } catch (Exception unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }
}
