package com.samsung.sesl.sep.reflect;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SepBaseReflector {
    public static final SepBaseReflector INSTANCE = new SepBaseReflector();

    private SepBaseReflector() {
    }

    public static Method getDeclaredMethod(Class cls, String str, Class... clsArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, (Class[]) Arrays.copyOf(clsArr, clsArr.length));
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException unused) {
            Log.w("SepBaseReflector", "Reflector did not find method = ".concat(str));
            return null;
        }
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, Arrays.copyOf(objArr, objArr.length));
        } catch (IllegalAccessException e) {
            Log.e("SepBaseReflector", method.getName() + " IllegalAccessException", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.e("SepBaseReflector", method.getName() + " IllegalArgumentException", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e("SepBaseReflector", method.getName() + " InvocationTargetException", e3);
            return null;
        }
    }
}
