package com.samsung.android.biometrics.app.setting.prompt.knox;

import java.util.HashMap;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
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
