package com.android.server.flags;

import java.util.HashMap;
import java.util.Map;

public final class FlagCache {
    public final FlagCache$$ExternalSyntheticLambda0 mNewHashMap =
            new FlagCache$$ExternalSyntheticLambda0();
    public final Map mCache = new HashMap();

    public final boolean contains(String str, String str2) {
        boolean z;
        synchronized (this.mCache) {
            try {
                Map map = (Map) ((HashMap) this.mCache).get(str);
                z = map != null && map.containsKey(str2);
            } finally {
            }
        }
        return z;
    }

    public final Object getOrNull(String str, String str2) {
        synchronized (this.mCache) {
            try {
                Map map = (Map) ((HashMap) this.mCache).get(str);
                if (map == null) {
                    return null;
                }
                return map.get(str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setIfChanged(String str, String str2, Object obj) {
        synchronized (this.mCache) {
            try {
                Map map = (Map) ((HashMap) this.mCache).computeIfAbsent(str, this.mNewHashMap);
                Object obj2 = map.get(str2);
                if (obj2 != null && obj2.equals(obj)) {
                    return;
                }
                map.put(str2, obj);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
