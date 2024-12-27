package com.android.server.chimera;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
