package com.android.systemui.power;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PowerUtils {
    private PowerUtils() {
    }

    public static String getFormattedTime(Context context, long j) {
        int i;
        int i2;
        long j2 = j / 1000;
        int i3 = 0;
        if (j2 >= 3600) {
            i = (int) (j2 / 3600);
            j2 -= i * 3600;
        } else {
            i = 0;
        }
        if (j2 >= 60) {
            i2 = (int) (j2 / 60);
            j2 -= i2 * 60;
        } else {
            i2 = 0;
        }
        int i4 = (int) j2;
        if (i != 0 || i2 < 2 || i4 < 30 || (i2 = i2 + 1) != 60) {
            i3 = i2;
        } else {
            i = 1;
        }
        return (i <= 0 || i3 <= 0) ? i > 0 ? context.getString(R.string.battery_notification_charging_text_h, Integer.valueOf(i)) : i3 > 0 ? context.getResources().getConfiguration().locale.getLanguage().equals("el") ? i3 == 1 ? context.getString(R.string.battery_notification_charging_text_m, Integer.valueOf(i3)) : context.getString(R.string.durationMinutes, Integer.valueOf(i3)) : context.getString(R.string.battery_notification_charging_text_m, Integer.valueOf(i3)) : "" : context.getString(R.string.battery_notification_charging_text_h_m, Integer.valueOf(i), Integer.valueOf(i3));
    }

    public static int getProtectBatteryValue(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "protect_battery", 0);
    }

    public static Context getSubDisplayContext(Context context) {
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Displays : "), displays.length, "PowerUi.PowerUtils");
        Display display = displays[1];
        Log.d("PowerUi.PowerUtils", "SubDisplay id : " + display.getDisplayId());
        return context.createDisplayContext(display);
    }

    public static boolean isMaximumProtectionEnabled(Context context) {
        int protectBatteryValue = getProtectBatteryValue(context);
        return PowerUiRune.PROTECT_BATTERY_CUTOFF && (protectBatteryValue == 1 || protectBatteryValue == 2);
    }

    public static boolean isSleepChargingOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "key_sleep_charging", 0) > 0;
    }

    public static boolean isViewCoverClosed() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        if (!keyguardUpdateMonitor.isCoverClosed() || keyguardUpdateMonitor.getCoverState() == null) {
            return false;
        }
        int type = keyguardUpdateMonitor.getCoverState().getType();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("View Cover is covered and closed, cover type : ", type, "PowerUi.PowerUtils");
        if (type != 15) {
            return false;
        }
        Log.i("PowerUi.PowerUtils", "S view cover is enabled, so we do not show this hv enable popup");
        return true;
    }

    public static PendingIntent pendingBroadcast(Context context, String str) {
        return PendingIntent.getBroadcastAsUser(context, 0, new Intent(str).setPackage(context.getPackageName()).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, UserHandle.CURRENT);
    }

    public static void sendIntentToDc(Context context, String str) {
        Intent intent = new Intent(str);
        intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
        try {
            context.sendBroadcastAsUser(intent, UserHandle.ALL);
        } catch (Exception e) {
            Log.e("PowerUi.PowerUtils", "Error", e);
        }
    }
}
