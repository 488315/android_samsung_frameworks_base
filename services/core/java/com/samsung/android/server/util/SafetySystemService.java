package com.samsung.android.server.util;

import android.content.Context;
import android.util.Slog;

public abstract class SafetySystemService {

    public abstract class LazyHolder {
        public static final Manager sSingleton = new Manager();
    }

    public final class Manager {
        public Context mSystemContext;
    }

    public static Object getSystemService(Class cls) {
        Context context;
        Manager manager = LazyHolder.sSingleton;
        synchronized (manager) {
            context = manager.mSystemContext;
        }
        if (context != null) {
            return context.getSystemService(cls);
        }
        Slog.w(
                "SafetySystemService",
                cls.getSimpleName().concat(" should be called after system ready."));
        return null;
    }
}
