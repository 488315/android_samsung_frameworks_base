package com.android.server.signedconfig;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SignedConfig {
    public final List perSdkConfig;
    public final int version;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PerSdkConfig {
        public final int maxSdk;
        public final int minSdk;
        public final Map values;

        public PerSdkConfig(int i, int i2, Map map) {
            this.minSdk = i;
            this.maxSdk = i2;
            this.values = Collections.unmodifiableMap(map);
        }
    }

    public SignedConfig(int i, List list) {
        this.version = i;
        this.perSdkConfig = Collections.unmodifiableList(list);
    }

    public static SignedConfig parse(String str) {
        Set set = GlobalSettingsConfigApplicator.ALLOWED_KEYS;
        Map map = GlobalSettingsConfigApplicator.KEY_VALUE_MAPPERS;
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("version");
            JSONArray jSONArray = jSONObject.getJSONArray("config");
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(parsePerSdkConfig(jSONArray.getJSONObject(i2), set, map));
            }
            return new SignedConfig(i, arrayList);
        } catch (JSONException e) {
            throw new InvalidConfigException("Could not parse JSON", e);
        }
    }

    public static PerSdkConfig parsePerSdkConfig(JSONObject jSONObject, Set set, Map map)
            throws JSONException, InvalidConfigException {
        int i = jSONObject.getInt("min_sdk");
        int i2 = jSONObject.getInt("max_sdk");
        JSONObject jSONObject2 = jSONObject.getJSONObject("values");
        HashMap hashMap = new HashMap();
        for (String str : jSONObject2.keySet()) {
            Object obj = jSONObject2.get(str);
            String obj2 = (obj == JSONObject.NULL || obj == null) ? null : obj.toString();
            if (!set.contains(str)) {
                throw new InvalidConfigException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Config key ", str, " is not allowed"));
            }
            if (map.containsKey(str)) {
                Map map2 = (Map) map.get(str);
                if (!map2.containsKey(obj2)) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Config key ", str, " contains unsupported value ");
                    m.append((Object) (obj2 == null ? "null" : "\"" + ((Object) obj2) + "\""));
                    throw new InvalidConfigException(m.toString());
                }
                obj2 = (String) map2.get(obj2);
            }
            hashMap.put(str, obj2);
        }
        return new PerSdkConfig(i, i2, hashMap);
    }
}
