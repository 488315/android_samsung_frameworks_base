package com.samsung.android.sdk.scs.base.utils;

import com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class FeatureHelper {
    public static FeatureConfig getFeatureConfig(String str) {
        JSONObject jSONObject = new JSONObject(str);
        String strOptString = jSONObject.optString(FeatureConfig.JSON_KEY_APP_VERSION, "");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(FeatureConfig.JSON_KEY_FEATURES);
        HashMap map = new HashMap();
        if (jSONObjectOptJSONObject != null) {
            Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, Integer.valueOf(jSONObjectOptJSONObject.optInt(next, 0)));
            }
        }
        return new FeatureConfig(strOptString, map);
    }
}
