package com.android.server.remoteappmode;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
