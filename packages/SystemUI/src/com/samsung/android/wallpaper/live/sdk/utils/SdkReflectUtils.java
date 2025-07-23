package com.samsung.android.wallpaper.live.sdk.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdkReflectUtils {
    public static Object getFieldValue(Field field, Object obj) {
        if (field == null) {
            SdkLog.w("SdkReflectUtils", "getFieldValue error, field is null");
            return 0;
        }
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            SdkLog.e("SdkReflectUtils", "getFieldValue: " + e);
            return 0;
        } catch (IllegalArgumentException e2) {
            SdkLog.e("SdkReflectUtils", "getFieldValue: " + e2);
            return 0;
        }
    }

    public static Method getMethod(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            SdkLog.e("SdkReflectUtils", "Cannot load method: " + e.getMessage());
            return null;
        }
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        if (method == null || obj == null) {
            SdkLog.w("SdkReflectUtils", "method or callerInstance is null");
            return null;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException unused) {
            SdkLog.e("SdkReflectUtils", method.getName() + " invoke IllegalAccessException");
            return null;
        } catch (InvocationTargetException unused2) {
            SdkLog.e("SdkReflectUtils", method.getName() + " invoke InvocationTargetException");
            return null;
        }
    }
}
