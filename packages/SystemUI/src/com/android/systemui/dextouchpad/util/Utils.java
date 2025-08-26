package com.android.systemui.dextouchpad.util;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.core.CoreSaLogger;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class Utils {
    public static int mDesktopDisplayId = -1;
    public static boolean mIsSpenDetached = false;
    public static boolean mIsTouchpadEnabled = false;
    public static int mLatestRotation;

    private Utils() {
    }

    public static PendingIntent getPendingIntent(Context context, String str) {
        return PendingIntent.getBroadcast(context, 0, new Intent(str).setPackage("com.android.systemui"), 33554432);
    }

    public static Intent getTouchActivityIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setPackage("com.android.systemui");
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.dextouchpad.activity.TouchpadActivity"));
        intent.setFlags(805306368);
        return intent;
    }

    public static void sendSALogging(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("det", str3);
        CoreSaLogger.logForDexWithScreenId(str, str2, map);
    }
}
