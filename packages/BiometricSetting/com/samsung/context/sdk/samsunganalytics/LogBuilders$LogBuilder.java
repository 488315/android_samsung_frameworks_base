package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$LogBuilder;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class LogBuilders$LogBuilder<T extends LogBuilders$LogBuilder> {
    protected Map<String, String> logs = new HashMap();

    protected LogBuilders$LogBuilder() {
    }

    public Map<String, String> build() {
        long currentTimeMillis;
        switch (((LogBuilders$EventBuilder) this).$r8$classId) {
            case 0:
                currentTimeMillis = System.currentTimeMillis();
                break;
            default:
                currentTimeMillis = System.currentTimeMillis();
                break;
        }
        set("ts", String.valueOf(currentTimeMillis));
        return this.logs;
    }

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
