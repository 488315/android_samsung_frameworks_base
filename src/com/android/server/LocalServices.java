package com.android.server;

import android.util.ArrayMap;

/* loaded from: classes6.dex */
public final class LocalServices {
    private static final ArrayMap<Class<?>, Object> sLocalServiceObjects = new ArrayMap<>();

    private LocalServices() {
    }

    public static <T> T getService(Class<T> cls) {
        T t;
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            t = (T) arrayMap.get(cls);
        }
        return t;
    }

    public static <T> void addService(Class<T> cls, T t) {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            if (arrayMap.containsKey(cls)) {
                throw new IllegalStateException("Overriding service registration");
            }
            arrayMap.put(cls, t);
        }
    }

    public static <T> void removeServiceForTest(Class<T> cls) {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            arrayMap.remove(cls);
        }
    }

    public static void removeAllServicesForTest() {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            arrayMap.clear();
        }
    }
}
