package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class LogBuilders$EventBuilder extends LogBuilders$LogBuilder {
    public final Map build() {
        if (!((HashMap) this.logs).containsKey("en")) {
            Utils.throwException("Failure to build Log : Event name cannot be null");
        }
        set("t", "ev");
        set("ts", String.valueOf(System.currentTimeMillis()));
        return this.logs;
    }

    public final void setDimension(Map map) {
        set("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map), Utils.Depth.TWO_DEPTH));
    }

    public final void setEventName(String str) {
        if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build Log : Event id cannot be null");
        }
        set("en", str);
    }

    public final void setPersonalizedData(Map map) {
        HashMap map2 = new HashMap();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            sb.setLength(0);
            for (String str : (String[]) entry.getValue()) {
                if (sb.length() != 0) {
                    sb.append(Utils.Depth.THREE_DEPTH.getCollectionDLM());
                }
                sb.append(str);
            }
            map2.put((String) entry.getKey(), sb.toString());
        }
        set("pd", Utils.makeDelimiterString(map2, Utils.Depth.TWO_DEPTH));
    }

    public final void setScreenView(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        set("pn", str);
    }
}
