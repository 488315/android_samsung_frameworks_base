package com.android.server.chimera;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RestartImmediatePackages {
    public static RestartImmediatePackages INSTANCE;
    public final Map sPackages = new ConcurrentHashMap();

    public static synchronized RestartImmediatePackages getInstance() {
        RestartImmediatePackages restartImmediatePackages;
        synchronized (RestartImmediatePackages.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new RestartImmediatePackages();
                }
                restartImmediatePackages = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return restartImmediatePackages;
    }
}
