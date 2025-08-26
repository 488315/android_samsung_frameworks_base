package com.samsung.android.wallpaper.live.sdk.utils;

import android.content.Context;
import android.os.PowerManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SdkDeviceUtils {
    public static final Method sMethodIsDozeAfterScreenOff = SdkReflectUtils.getMethod(PowerManager.class, "isDozeAfterScreenOff", new Class[0]);

    public static boolean isDozeAfterScreenOff(int i, Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method;
        int i2;
        if (context != null && (method = sMethodIsDozeAfterScreenOff) != null && ((i2 = i & 60) == 4 || i2 == 16)) {
            try {
                Object objInvoke = method.invoke((PowerManager) context.getSystemService("power"), null);
                if (objInvoke == null) {
                    return false;
                }
                if (!((Boolean) objInvoke).booleanValue()) {
                    return false;
                }
            } catch (Exception e) {
                SdkLog.e("SdkDeviceUtils", "isDozeAfterScreenOff: " + e);
            }
        }
        return true;
    }
}
