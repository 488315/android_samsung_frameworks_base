package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;

import java.util.HashMap;
import java.util.Map;

public final class LogBuilders$EventBuilder {
    public final Map logs = new HashMap();

    public final void set(String str, String str2) {
        ((HashMap) this.logs).put(str, str2);
    }

    public final void setDimension(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                Debug.LogENG("cd key is empty");
            } else {
                if (str.length() > 40) {
                    Debug.LogENG("cd key length over:".concat(str));
                    str = str.substring(0, 40);
                }
                if (str2 != null && str2.length() > 1024) {
                    Debug.LogENG("cd value length over:".concat(str2));
                    str2 = str2.substring(0, 1024);
                }
                hashMap.put(str, str2);
            }
        }
        set("cd", Delimiter.makeDelimiterString(hashMap, Delimiter.Depth.TWO_DEPTH));
    }
}
