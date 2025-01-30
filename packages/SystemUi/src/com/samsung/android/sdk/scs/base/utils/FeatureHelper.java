package com.samsung.android.sdk.scs.base.utils;

import com.samsung.android.scs.p047ai.sdkcommon.feature.FeatureConfig;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FeatureHelper {
    public static FeatureConfig getFeatureConfig(String str) {
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString(FeatureConfig.JSON_KEY_APP_VERSION, "");
        JSONObject optJSONObject = jSONObject.optJSONObject(FeatureConfig.JSON_KEY_FEATURES);
        HashMap hashMap = new HashMap();
        if (optJSONObject != null) {
            Iterator<String> keys = optJSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, Integer.valueOf(optJSONObject.optInt(next, 0)));
            }
        }
        return new FeatureConfig(optString, hashMap);
    }
}
