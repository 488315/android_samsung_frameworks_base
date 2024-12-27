package com.samsung.android.knox.analytics.util;

import android.os.Bundle;
import android.text.TextUtils;
import com.android.server.clipboard.ClipboardService;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KnoxAnalyticsDataConverter {
    public static final String EVENT = "e";
    public static final String EVENT_ID = "eID";
    public static final String FEATURE = "f";
    public static final String PAYLOAD = "p";
    public static final String SCHEMA_VERSION = "sV";
    public static final String TAG = "[KnoxAnalytics] KnoxAnalyticsDataConverter";
    public static final String TIMESTAMP = "t";
    public static final String VERSIONING_BLOB_EVENT = "versioningBlob";
    public static final String VERSIONING_ID_PARAMETER = "vId";

    public static JSONObject convertToJSON(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            try {
                if (bundle.get(str) instanceof Bundle) {
                    jSONObject.put(str, convertToJSON(bundle.getBundle(str)));
                } else {
                    jSONObject.put(str, bundle.get(str));
                }
            } catch (JSONException unused) {
                Log.e(TAG, "convertToJSON(): JSONException");
            }
        }
        return jSONObject;
    }

    public static List formatBulkEvents(long j, List list) {
        ArrayList arrayList = new ArrayList();
        int size = list.size() - 1;
        arrayList.add("[" + updateEventId(j, (KnoxAnalyticsData) list.get(0)));
        long j2 = j + 1;
        for (int i = 1; i < size; i++) {
            arrayList.add(", " + updateEventId(j2, (KnoxAnalyticsData) list.get(i)));
            j2++;
        }
        arrayList.add(", " + updateEventId(j2, (KnoxAnalyticsData) list.get(size)) + "]");
        return arrayList;
    }

    public static long generateTimestamp() {
        return (Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis() / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    }

    public static String generateVersioningBlobEvent(String str, long j, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(EVENT_ID, j);
            jSONObject.put(FEATURE, "KNOX_ANALYTICS");
            jSONObject.put(SCHEMA_VERSION, 1);
            jSONObject.put(EVENT, VERSIONING_BLOB_EVENT);
            jSONObject.put(TIMESTAMP, generateTimestamp());
            JSONObject jSONObject2 = new JSONObject(str);
            jSONObject2.put(VERSIONING_ID_PARAMETER, i);
            jSONObject.put(PAYLOAD, jSONObject2);
        } catch (JSONException unused) {
            Log.e(TAG, "generateVersioningBlobEvent(): JSONException");
        }
        return jSONObject.toString();
    }

    public static String getVersioningBlobData(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(PAYLOAD)) {
                if (jSONObject.getJSONObject(PAYLOAD).has(VERSIONING_ID_PARAMETER)) {
                    jSONObject.getJSONObject(PAYLOAD).remove(VERSIONING_ID_PARAMETER);
                }
                return jSONObject.getJSONObject(PAYLOAD).toString();
            }
            if (jSONObject.has(VERSIONING_ID_PARAMETER)) {
                jSONObject.remove(VERSIONING_ID_PARAMETER);
            }
            return jSONObject.toString();
        } catch (JSONException unused) {
            Log.e(TAG, "getVersioningBlobData(): JSONException");
            return str;
        }
    }

    public static boolean isJSONEqual(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject(str2);
            if (jSONObject.length() != jSONObject2.length()) {
                return false;
            }
            for (String str3 : jSONObject.keySet()) {
                if (!jSONObject.getString(str3).equals(jSONObject2.getString(str3))) {
                    return false;
                }
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static boolean isJSONValid(String str) {
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static JSONObject toJSONObject(KnoxAnalyticsData knoxAnalyticsData) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(EVENT_ID, knoxAnalyticsData.getEventId());
            jSONObject.put(FEATURE, knoxAnalyticsData.getFeature());
            jSONObject.put(SCHEMA_VERSION, knoxAnalyticsData.getSchemaVersion());
            jSONObject.put(EVENT, knoxAnalyticsData.getEvent());
            jSONObject.put(TIMESTAMP, knoxAnalyticsData.getTimestamp());
            JSONObject jSONObject2 = new JSONObject();
            Bundle payload = knoxAnalyticsData.getPayload();
            for (String str : payload.keySet()) {
                if (payload.get(str) instanceof Bundle) {
                    jSONObject2.put(str, convertToJSON(payload.getBundle(str)));
                } else if ((payload.get(str) instanceof String) && isJSONValid(payload.getString(str))) {
                    jSONObject2.put(str, new JSONObject(payload.getString(str)));
                } else {
                    jSONObject2.put(str, payload.get(str));
                }
            }
            jSONObject.put(PAYLOAD, jSONObject2);
        } catch (JSONException unused) {
            Log.e(TAG, "toJSONObject(): JSONException");
        }
        return jSONObject;
    }

    public static String toJSONString(KnoxAnalyticsData knoxAnalyticsData) {
        return toJSONObject(knoxAnalyticsData).toString();
    }

    public static String updateEventId(long j, KnoxAnalyticsData knoxAnalyticsData) {
        JSONObject jSONObject = toJSONObject(knoxAnalyticsData);
        try {
            jSONObject.put(EVENT_ID, j);
        } catch (JSONException unused) {
            Log.d(TAG, "updateEventId(): JSONException");
        }
        return jSONObject.toString();
    }
}
