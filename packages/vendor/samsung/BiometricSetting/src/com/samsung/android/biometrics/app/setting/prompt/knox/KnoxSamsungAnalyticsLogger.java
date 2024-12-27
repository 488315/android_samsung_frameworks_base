package com.samsung.android.biometrics.app.setting.prompt.knox;

import java.util.HashMap;

public abstract class KnoxSamsungAnalyticsLogger {
    public static HashMap addEvent(int i, int i2, Object obj) {
        HashMap hashMap = new HashMap();
        hashMap.put("viewID", Integer.valueOf(i));
        hashMap.put("eventID", Integer.valueOf(i2));
        hashMap.put("detail", obj);
        hashMap.put("type", "event");
        return hashMap;
    }
}
