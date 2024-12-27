package com.android.server.knox;

import android.content.SharedPreferences;
import android.text.TextUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class JsonHelper {
    public static JSONObject createNewJSONObjectForEvent(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("event_id", i);
            jSONObject.put("event_key", str);
            jSONObject.put("count", 1);
            jSONObject.put("save_date", DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()));
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public static long shallSaveOrReturnCount(int i, String str, SharedPreferences sharedPreferences) {
        JSONArray jSONArray;
        String str2;
        long j;
        String format = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        try {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            String string = sharedPreferences.getString("eventJSONArray", "");
            if (TextUtils.isEmpty(string)) {
                jSONArray = new JSONArray();
                jSONArray.put(createNewJSONObjectForEvent(i, str));
                str2 = null;
                j = 0;
            } else {
                jSONArray = new JSONArray(string);
                boolean z = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= jSONArray.length()) {
                        str2 = null;
                        j = 0;
                        break;
                    }
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    if (jSONObject.getInt("event_id") == i && jSONObject.getString("event_key").equals(str)) {
                        j = jSONObject.getLong("count");
                        String string2 = jSONObject.getString("save_date");
                        if (string2 == null) {
                            jSONObject.put("save_date", format);
                        } else if (string2.equals(format)) {
                            jSONObject.put("count", j + 1);
                        } else {
                            jSONObject.put("save_date", format);
                            jSONObject.put("count", 1);
                        }
                        str2 = string2;
                        z = true;
                    }
                    i2++;
                }
                if (!z) {
                    jSONArray.put(createNewJSONObjectForEvent(i, str));
                }
            }
            edit.remove("eventJSONArray").putString("eventJSONArray", jSONArray.toString()).apply();
            if (str2 != null) {
                if (!str2.equals(format)) {
                    return j;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
