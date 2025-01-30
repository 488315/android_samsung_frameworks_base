package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LogBuilders$EventBuilder extends LogBuilders$LogBuilder {
    public final Map build() {
        Map map = this.logs;
        if (!((HashMap) map).containsKey("en")) {
            Utils.throwException("Failure to build Log : Event name cannot be null");
        }
        set("t", "ev");
        set("ts", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public final void setDimension(Map map) {
        set("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map), Utils.Depth.TWO_DEPTH));
    }

    public final void setEventName(String str) {
        if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build Log : Event name cannot be null");
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
            hashMap.put(entry.getKey(), sb.toString());
        }
        set("pd", Utils.makeDelimiterString(hashMap, Utils.Depth.TWO_DEPTH));
    }

    public final void setScreenView(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        set("pn", str);
    }

    @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders$LogBuilder
    public final LogBuilders$LogBuilder getThis() {
        return this;
    }
}
