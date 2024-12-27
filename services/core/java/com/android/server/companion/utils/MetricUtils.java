package com.android.server.companion.utils;

import android.util.ArrayMap;
import java.util.Collections;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class MetricUtils {
    public static final Map METRIC_DEVICE_PROFILE;

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(null, 0);
        arrayMap.put("android.app.role.COMPANION_DEVICE_WATCH", 1);
        arrayMap.put("android.app.role.COMPANION_DEVICE_APP_STREAMING", 2);
        arrayMap.put("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION", 3);
        arrayMap.put("android.app.role.COMPANION_DEVICE_COMPUTER", 4);
        arrayMap.put("android.app.role.COMPANION_DEVICE_GLASSES", 5);
        arrayMap.put("android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING", 6);
        METRIC_DEVICE_PROFILE = Collections.unmodifiableMap(arrayMap);
    }
}
