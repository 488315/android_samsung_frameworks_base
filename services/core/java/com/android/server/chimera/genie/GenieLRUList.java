package com.android.server.chimera.genie;

import java.util.LinkedHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
