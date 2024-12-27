package com.android.server.chimera.genie;

import java.util.LinkedHashMap;

public final class GenieLRUList extends LinkedHashMap {
    public static GenieLRUList instance;
    public static final Object mLock = new Object();

    private GenieLRUList() {
        super(GenieConfigurations.MODEL_COUNT + 1, 1.0f, true);
    }

    public static GenieLRUList getInstance() {
        synchronized (mLock) {
            try {
                if (instance == null) {
                    instance = new GenieLRUList();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return instance;
    }
}
