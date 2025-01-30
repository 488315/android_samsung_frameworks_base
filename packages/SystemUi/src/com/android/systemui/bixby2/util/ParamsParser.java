package com.android.systemui.bixby2.util;

import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ParamsParser {
    private static final String ACTIVITY_NAME = "activityName";
    private static final String ACTIVITY_NAME2 = "activityName2";
    private static final int CNT_NEED_KEY = 2;
    private static final String MSG_STRING = "msgString";
    private static final String NOTI_ID = "notiID";
    private static final String PACKAGE_NAME = "packageName";
    private static final String PACKAGE_NAME2 = "packageName2";
    private static final String POSITION = "position";
    private static final String POSITION2 = "position2";
    private static final String TAG = "ParamsParser";
    private static final String TASK_ID = "task_id";
    private static final String TYPE = "type";

    private static void geJsonInfo(JSONObject jSONObject, String str, ArrayList<String> arrayList) {
        if (jSONObject == null || !jSONObject.has(str) || arrayList == null) {
            return;
        }
        try {
            arrayList.add(jSONObject.getString(str));
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.toString());
        }
    }

    public static void getListInfoFromJson(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str) {
        try {
            JSONArray jSONArray = new JSONArray(String.valueOf(str));
            int length = jSONArray.length();
            int i = 0;
            if (arrayList2 == null) {
                while (i < length) {
                    geJsonInfo(jSONArray.optJSONObject(i), "packageName", arrayList);
                    i++;
                }
                return;
            }
            while (i < length && i < length - 1) {
                JSONObject optJSONObject = jSONArray.optJSONObject(i);
                JSONObject optJSONObject2 = jSONArray.optJSONObject(i + 1);
                geJsonInfo(optJSONObject, ACTIVITY_NAME, arrayList2);
                geJsonInfo(optJSONObject2, "packageName", arrayList);
                i += 2;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.toString());
        }
    }

    public static PackageInfoBixby getPackageInfoFromJson(String str) {
        HashMap hashMap = new HashMap();
        int i = 0;
        try {
            new JsonParser();
            if (JsonParser.parseString(str) instanceof JsonArray) {
                JSONArray jSONArray = new JSONArray(String.valueOf(str));
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    Iterator<String> keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String obj = keys.next().toString();
                        hashMap.put(obj, jSONObject.getString(obj));
                    }
                }
            } else {
                JSONObject jSONObject2 = new JSONObject(str);
                Iterator<String> keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String obj2 = keys2.next().toString();
                    hashMap.put(obj2, jSONObject2.getString(obj2));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.toString());
        }
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        for (String str11 : hashMap.keySet()) {
            if (str11.equals("packageName")) {
                str2 = (String) hashMap.get(str11);
            } else if (str11.equals(ACTIVITY_NAME)) {
                str3 = (String) hashMap.get(str11);
            } else if (str11.equals(PACKAGE_NAME2)) {
                str4 = (String) hashMap.get(str11);
            } else if (str11.equals(ACTIVITY_NAME2)) {
                str5 = (String) hashMap.get(str11);
            } else if (str11.equals("type")) {
                str6 = (String) hashMap.get(str11);
            } else if (str11.equals(MSG_STRING)) {
                str7 = (String) hashMap.get(str11);
            } else if (str11.equals(NOTI_ID)) {
                str8 = (String) hashMap.get(str11);
            } else if (str11.equals(POSITION)) {
                str9 = (String) hashMap.get(str11);
            } else if (str11.equals(POSITION2)) {
                str10 = (String) hashMap.get(str11);
            } else if (str11.equals(TASK_ID)) {
                i = Integer.parseInt((String) hashMap.get(str11));
            }
        }
        PackageInfoBixby packageInfoBixby = new PackageInfoBixby();
        packageInfoBixby.ActivityName = str3;
        packageInfoBixby.PackageName = str2;
        packageInfoBixby.ActivityName2 = str5;
        packageInfoBixby.PackageName2 = str4;
        packageInfoBixby.Type = str6;
        packageInfoBixby.MsgStr = str7;
        packageInfoBixby.notiID = str8;
        packageInfoBixby.Position = str9;
        packageInfoBixby.Position2 = str10;
        packageInfoBixby.taskId = i;
        return packageInfoBixby;
    }
}
