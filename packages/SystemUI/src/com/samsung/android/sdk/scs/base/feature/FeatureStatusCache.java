package com.samsung.android.sdk.scs.base.feature;

import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class FeatureStatusCache {
    public static final HashMap statusMap;

    static {
        HashMap map = new HashMap();
        statusMap = map;
        map.put("FEATURE_IMAGE_GET_BOUNDARIES", -1000);
        map.put("FEATURE_IMAGE_GET_LARGEST_BOUNDARY", -1000);
        map.put("FEATURE_SUGGESTION_SUGGEST_KEYWORD", -1000);
        map.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY", -1000);
        map.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY_DETAILS", -1000);
        map.put("FEATURE_SUGGESTION_SUGGEST_FOLDER_NAME", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_DATETIME_NUMERAL", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_DATETIME_SEARCH", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_PHONE_NUMBER", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_POI", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_BANK", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_IS_MAPPABLE", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_IS_RELATIVE", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_IS_SPECIAL_DAY", -1000);
        map.put("FEATURE_TEXT_GET_ENTITY_HAS_YEAR_MONTH_DAY", -1000);
        map.put("FEATURE_TEXT_GET_EVENT", -1000);
        map.put("FEATURE_TEXT_GET_EVENT_HAS_YEAR_MONTH_DAY", -1000);
        map.put("FEATURE_TEXT_GET_EVENT_INDEX", -1000);
        map.put("FEATURE_TEXT_GET_KEY_PHRASE", -1000);
        map.put("FEATURE_TEXT_GET_KEY_PHRASE_EVENT_TITLE", -1000);
        map.put("FEATURE_TEXT_GET_DOCUMENT_CATEGORY", -1000);
        map.put("FEATURE_TEXT_GET_BNLP", -1000);
        map.put("FEATURE_TEXT_DETECT_LANGUAGE", -1000);
        map.put("FEATURE_TEXT_CONVERT_UNIT", -1000);
        map.put("FEATURE_TEXT_GET_REMINDER_ENTITY", -1000);
        map.put("FEATURE_TEXT_GET_EVENT_CATEGORY", -1000);
        map.put("FEATURE_NATURAL_LANGUAGE_QUERY", -1000);
    }

    public static void setStatus(int i, String str) {
        statusMap.put(str, Integer.valueOf(i));
        Log.i("ScsApi@FeatureHolder", "setStatus() : " + str + " : " + i);
    }
}
