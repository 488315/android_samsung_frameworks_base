package com.samsung.android.feature;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
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

    SecCarrier(String jsonText, String salesCode, int carrierId) throws JSONException {
        JSONArray specificFeatureObject;
        this.isCarrierGroupValid = false;
        this.feature = new LinkedHashMap();
        JSONObject jsonObject = new JSONObject(jsonText);
        this.version = jsonObject.getString("version");
        if (jsonObject.has(TAG_MAPPED_CID_VER)) {
            this.mapped_cid_version = jsonObject.getString(TAG_MAPPED_CID_VER);
        } else {
            this.mapped_cid_version = VERSION_DEFAULT;
        }
        String carrierGroup = salesCode;
        Map<? extends String, ? extends String> linkedHashMap = new LinkedHashMap<>();
        if (jsonObject.has(TAG_SPECIFIC) && (specificFeatureObject = jsonObject.getJSONArray(TAG_SPECIFIC)) != null) {
            int i = 0;
            while (true) {
                if (i >= specificFeatureObject.length()) {
                    break;
                }
                int ccid = specificFeatureObject.getJSONObject(i).getInt(TAG_CARRIER_ID);
                if (ccid != carrierId) {
                    i++;
                } else {
                    Iterator<String> keys = specificFeatureObject.getJSONObject(i).getJSONObject("feature").keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String value = specificFeatureObject.getJSONObject(i).getJSONObject("feature").getString(key);
                        if (key.equals(FEATURE_GROUP_KEY)) {
                            carrierGroup = value;
                        }
                        linkedHashMap.put(key, value);
                    }
                }
            }
        }
        Map<String, String> custFeatures = new LinkedHashMap<>();
        custFeatures.put("version", this.version);
        JSONArray custFeatureObject = jsonObject.getJSONArray(TAG_CUSTOMER);
        if (custFeatureObject != null) {
            for (int i2 = 0; i2 < custFeatureObject.length(); i2++) {
                String cGroup = custFeatureObject.getJSONObject(i2).getString(TAG_NAME);
                if (salesCode.equals(cGroup)) {
                    this.isCarrierGroupValid = true;
                }
                if (carrierGroup.equals(cGroup)) {
                    Iterator<String> keys2 = custFeatureObject.getJSONObject(i2).getJSONObject("feature").keys();
                    while (keys2.hasNext()) {
                        String key2 = keys2.next();
                        custFeatures.put(key2, custFeatureObject.getJSONObject(i2).getJSONObject("feature").getString(key2));
                    }
                }
            }
        }
        custFeatures.putAll(linkedHashMap);
        this.feature = custFeatures;
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
