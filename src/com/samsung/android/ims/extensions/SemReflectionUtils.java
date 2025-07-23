package com.samsung.android.ims.extensions;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class SemReflectionUtils {
    public static Field getField(Class<?> cls, String str) {
        Class<? super Object> superclass = cls.getSuperclass();
        try {
            Field declaredField = cls.getDeclaredField(str);
            if (declaredField != null) {
                return declaredField;
            }
            if (superclass != null) {
                return getField(superclass, str);
            }
            return null;
        } catch (NoSuchFieldException unused) {
            Log.d("SemReflectionUtils", "Could not find field " + str + " in " + cls);
            if (superclass != null) {
                return getField(superclass, str);
            }
            return null;
        }
    }

    public static <T> T getValueOf(Field field, Object obj) {
        if (field == null) {
            return null;
        }
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not read value from Field!", e);
        }
    }

    public static <T> T getValueOf(String str, Class<?> cls) {
        Field field = getField(cls, str);
        if (field != null) {
            return (T) getValueOf(field, (Object) null);
        }
        return null;
    }

    public static void invoke(Method method, Object obj, Object... objArr) {
        if (method == null) {
            return;
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not invoke method!", e);
        } catch (InvocationTargetException e2) {
            throw new IllegalStateException("Could not invoke method!", e2);
        }
    }

    public static <T> T invoke2(Method method, Object obj, Object... objArr) {
        if (method == null) {
            return null;
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            return (T) method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not invoke method!", e);
        } catch (InvocationTargetException e2) {
            throw new IllegalStateException("Could not invoke method!", e2);
        }
    }
}
