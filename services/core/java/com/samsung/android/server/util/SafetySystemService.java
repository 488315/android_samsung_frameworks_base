package com.samsung.android.server.util;

import android.content.Context;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SafetySystemService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final Manager sSingleton = new Manager();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
