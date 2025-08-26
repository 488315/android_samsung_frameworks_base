package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.HashMap;
import java.util.Map;

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
        HashMap map2 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                Debug.LogENG("key is empty");
            } else {
                map2.put(checkSizeLimit(100, str), checkSizeLimit(1024, str2));
            }
        }
        return map2;
    }
}
