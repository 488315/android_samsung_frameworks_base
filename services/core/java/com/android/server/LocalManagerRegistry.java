package com.android.server;

import android.annotation.SystemApi;
import android.util.ArrayMap;

import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public final class LocalManagerRegistry {
    public static final Map sManagers = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ManagerNotFoundException extends Exception {
        public ManagerNotFoundException(Class cls) {
            super("Local manager " + cls.getName() + " does not exist or is not ready");
        }
    }

    public static void addManager(Class cls, Object obj) {
        Objects.requireNonNull(cls, "managerClass");
        Objects.requireNonNull(obj, "manager");
        Map map = sManagers;
        synchronized (map) {
            try {
                if (((ArrayMap) map).containsKey(cls)) {
                    throw new IllegalStateException(cls.getName().concat(" is already registered"));
                }
                ((ArrayMap) map).put(cls, obj);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static Object getManager(Class cls) {
        Object obj;
        Map map = sManagers;
        synchronized (map) {
            obj = ((ArrayMap) map).get(cls);
        }
        return obj;
    }

    public static Object getManagerOrThrow(Class cls) throws ManagerNotFoundException {
        Object manager = getManager(cls);
        if (manager != null) {
            return manager;
        }
        throw new ManagerNotFoundException(cls);
    }
}
