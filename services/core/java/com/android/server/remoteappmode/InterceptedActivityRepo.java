package com.android.server.remoteappmode;

import java.util.LinkedHashMap;
import java.util.Map;

public final class InterceptedActivityRepo {
    public final Object mLock = new Object();
    public final LinkedHashMap mInterceptedActivityInfoMap =
            new LinkedHashMap() { // from class:
                                  // com.android.server.remoteappmode.InterceptedActivityRepo.1
                @Override // java.util.LinkedHashMap
                public final boolean removeEldestEntry(Map.Entry entry) {
                    return size() > 10;
                }
            };
}
