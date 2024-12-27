package com.samsung.android.sdk.scs.base.utils;

import com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public abstract class FeatureHelper {
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
