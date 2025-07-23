package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Validation {
    public static String checkSizeLimit(int i, String str) {
        if (str == null || str.length() <= i) {
            return str;
        }
        Debug.LogENG("length over, target: " + str + ", limit: " + i);
        return str.substring(0, i);
    }

    public static Map checkSizeLimit(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                Debug.LogENG("key is empty");
            } else {
                hashMap.put(checkSizeLimit(100, str), checkSizeLimit(1024, str2));
            }
        }
        return hashMap;
    }
}
