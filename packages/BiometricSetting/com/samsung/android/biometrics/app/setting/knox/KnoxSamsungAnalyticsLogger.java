package com.samsung.android.biometrics.app.setting.knox;

import java.util.HashMap;

/* loaded from: classes.dex */
public final class KnoxSamsungAnalyticsLogger {
    public static HashMap<String, Object> addEvent(int i, int i2, Object obj) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("viewID", Integer.valueOf(i));
        hashMap.put("eventID", Integer.valueOf(i2));
        hashMap.put("detail", obj);
        hashMap.put("type", "event");
        return hashMap;
    }
}
