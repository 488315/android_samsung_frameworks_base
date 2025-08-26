package com.sec.ims.extensions;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ReflectionUtils {
    public static List<Field> getAllFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, cls.getDeclaredFields());
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            arrayList.addAll(getAllFields(superclass));
        }
        return arrayList;
    }

    public static <T> Class<T> getClassOf(T t) {
        return (Class<T>) t.getClass();
    }

    public static Field getField(Class<?> cls, String str) throws NoSuchFieldException {
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
            Log.d("ReflectionUtils", "Could not find field " + str + " in " + cls);
            if (superclass != null) {
                return getField(superclass, str);
            }
            return null;
        }
    }

    public static Class<?> getGenericType(Field field) {
        return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
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
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new IllegalStateException("Could not read value from Field!", e);
        }
    }

    public static void invoke(Method method, Object obj, Object... objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method == null) {
            return;
        }
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            throw new IllegalStateException("Could not invoke " + method.getName(), e);
        }
    }

    public static <T> T invoke2(Method method, Object obj, Object... objArr) {
        if (method == null) {
            return null;
        }
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return (T) method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            throw new IllegalStateException("Could not invoke " + method.getName(), e);
        }
    }

    public static boolean setValueOf(Field field, Object obj, Object obj2) throws IllegalAccessException, IllegalArgumentException {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(obj, obj2);
            return true;
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new IllegalStateException("Could not read value from Field!", e);
        }
    }

    public static <T> T getValueOf(String str, Object obj) throws NoSuchFieldException {
        Field field = getField(obj.getClass(), str);
        if (field != null) {
            return (T) getValueOf(field, obj);
        }
        return null;
    }

    public static <T> T getValueOf(String str, Class<?> cls) throws NoSuchFieldException {
        Field field = getField(cls, str);
        if (field != null) {
            return (T) getValueOf(field, (Object) null);
        }
        return null;
    }
}
