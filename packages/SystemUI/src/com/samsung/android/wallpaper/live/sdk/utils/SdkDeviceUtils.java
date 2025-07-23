package com.samsung.android.wallpaper.live.sdk.utils;

import android.content.Context;
import android.os.PowerManager;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdkDeviceUtils {
    public static final Method sMethodIsDozeAfterScreenOff = SdkReflectUtils.getMethod(PowerManager.class, "isDozeAfterScreenOff", new Class[0]);

    public static boolean isDozeAfterScreenOff(int i, Context context) {
        Method method;
        int i2;
        if (context != null && (method = sMethodIsDozeAfterScreenOff) != null && ((i2 = i & 60) == 4 || i2 == 16)) {
            try {
                Object invoke = method.invoke((PowerManager) context.getSystemService("power"), null);
                if (invoke == null) {
                    return false;
                }
                if (!((Boolean) invoke).booleanValue()) {
                    return false;
                }
            } catch (Exception e) {
                SdkLog.e("SdkDeviceUtils", "isDozeAfterScreenOff: " + e);
            }
        }
        return true;
    }
}
