package com.android.systemui.power.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.power.constants.PowerUiConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PowerUtils {
    private PowerUtils() {
    }

    public static NowBarItem getNowBarItem() {
        NowBarItem nowBarItem = new NowBarItem();
        nowBarItem.setNowBarKey("charging_vi_now_bar_key");
        nowBarItem.setNowBarPackage("com.android.systemui");
        nowBarItem.setNowBarViewStyle(6);
        Bundle bundle = new Bundle();
        bundle.putInt("nowbar_key_screen_type", 3);
        nowBarItem.setExtraData(bundle);
        return nowBarItem;
    }

    public static boolean isShutdownOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "SHOULD_SHUT_DOWN", 0) == 1;
    }

    public static PendingIntent pendingBroadcast(Context context, String str) {
        return PendingIntent.getBroadcastAsUser(context, 0, new Intent(str).setPackage(context.getPackageName()).setFlags(268435456), 67108864, UserHandle.CURRENT);
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
