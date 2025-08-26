package com.samsung.android.feature;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class SecCarrier {
    private static final String FEATURE_GROUP_KEY = "CarrierFeature_Common_CarrierGroup";
    private static final String TAG_CARRIER_ID = "canonical_id";
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_FEATURE = "feature";
    private static final String TAG_MAPPED_CID_VER = "mapped_cid_version";
    private static final String TAG_NAME = "carrier_group";
    private static final String TAG_SPECIFIC = "specific";
    private static final String TAG_VERSION = "version";
    private static final String VERSION_DEFAULT = "-1";
    private Map<String, String> feature;
    private boolean isCarrierGroupValid;
    private String mapped_cid_version;
    private String version;

    SecCarrier(String str, String str2, int i) throws JSONException {
        String str3;
        JSONArray jSONArray;
        this.isCarrierGroupValid = false;
        this.feature = new LinkedHashMap();
        JSONObject jSONObject = new JSONObject(str);
        this.version = jSONObject.getString("version");
        if (jSONObject.has(TAG_MAPPED_CID_VER)) {
            this.mapped_cid_version = jSONObject.getString(TAG_MAPPED_CID_VER);
        } else {
            this.mapped_cid_version = VERSION_DEFAULT;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!jSONObject.has(TAG_SPECIFIC) || (jSONArray = jSONObject.getJSONArray(TAG_SPECIFIC)) == null) {
            str3 = str2;
        } else {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                if (jSONArray.getJSONObject(i2).getInt(TAG_CARRIER_ID) == i) {
                    Iterator<String> itKeys = jSONArray.getJSONObject(i2).getJSONObject("feature").keys();
                    str3 = str2;
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        String string = jSONArray.getJSONObject(i2).getJSONObject("feature").getString(next);
                        if (next.equals(FEATURE_GROUP_KEY)) {
                            str3 = string;
                        }
                        linkedHashMap.put(next, string);
                    }
                }
            }
            str3 = str2;
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.put("version", this.version);
        JSONArray jSONArray2 = jSONObject.getJSONArray(TAG_CUSTOMER);
        if (jSONArray2 != null) {
            for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                String string2 = jSONArray2.getJSONObject(i3).getString(TAG_NAME);
                if (str2.equals(string2)) {
                    this.isCarrierGroupValid = true;
                }
                if (str3.equals(string2)) {
                    Iterator<String> itKeys2 = jSONArray2.getJSONObject(i3).getJSONObject("feature").keys();
                    while (itKeys2.hasNext()) {
                        String next2 = itKeys2.next();
                        linkedHashMap2.put(next2, jSONArray2.getJSONObject(i3).getJSONObject("feature").getString(next2));
                    }
                }
            }
        }
        linkedHashMap2.putAll(linkedHashMap);
        this.feature = linkedHashMap2;
    }

    Map<String, String> getFeature() {
        return this.feature;
    }

    int getVersion() {
        return Integer.parseInt(this.version);
    }

    int getMappedCidVersion() {
        return Integer.parseInt(this.mapped_cid_version);
    }

    boolean isCarrierGroupValid() {
        return this.isCarrierGroupValid;
    }
}
