package com.android.server.chimera;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class RestartImmediatePackages {
    public static RestartImmediatePackages INSTANCE;
    public final Map sPackages = new ConcurrentHashMap();

    public static synchronized RestartImmediatePackages getInstance() {
        RestartImmediatePackages restartImmediatePackages;
        synchronized (RestartImmediatePackages.class) {
            if (INSTANCE == null) {
                INSTANCE = new RestartImmediatePackages();
            }
            restartImmediatePackages = INSTANCE;
        }
        return restartImmediatePackages;
    }

    public void addPackage(String str) {
        if (str == null) {
            return;
        }
        this.sPackages.put(str, Boolean.FALSE);
    }

    public void addPackage(String str, Boolean bool) {
        if (str == null || bool == null) {
            return;
        }
        this.sPackages.put(str, bool);
    }

    public void clearAll() {
        this.sPackages.clear();
    }

    public Boolean getPackage(String str) {
        if (str == null) {
            return Boolean.FALSE;
        }
        return (Boolean) this.sPackages.get(str);
    }
}
