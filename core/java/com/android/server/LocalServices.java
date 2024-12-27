package com.android.server;

import android.util.ArrayMap;

public final class LocalServices {
    private static final ArrayMap<Class<?>, Object> sLocalServiceObjects = new ArrayMap<>();

    private LocalServices() {}

    public static <T> T getService(Class<T> cls) {
        T t;
        synchronized (sLocalServiceObjects) {
            t = (T) sLocalServiceObjects.get(cls);
        }
        return t;
    }

    public static <T> void addService(Class<T> type, T service) {
        synchronized (sLocalServiceObjects) {
            if (sLocalServiceObjects.containsKey(type)) {
                throw new IllegalStateException("Overriding service registration");
            }
            sLocalServiceObjects.put(type, service);
        }
    }

    public static <T> void removeServiceForTest(Class<T> type) {
        synchronized (sLocalServiceObjects) {
            sLocalServiceObjects.remove(type);
        }
    }

    public static void removeAllServicesForTest() {
        synchronized (sLocalServiceObjects) {
            sLocalServiceObjects.clear();
        }
    }
}
