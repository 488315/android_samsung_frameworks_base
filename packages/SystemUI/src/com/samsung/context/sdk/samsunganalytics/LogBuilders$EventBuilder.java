package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        HashMap hashMap = new HashMap();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            sb.setLength(0);
            for (String str : (String[]) entry.getValue()) {
                if (sb.length() != 0) {
                    sb.append(Utils.Depth.THREE_DEPTH.getCollectionDLM());
                }
                sb.append(str);
            }
            hashMap.put((String) entry.getKey(), sb.toString());
        }
        set("pd", Utils.makeDelimiterString(hashMap, Utils.Depth.TWO_DEPTH));
    }

    public final void setScreenView(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        set("pn", str);
    }
}
