package com.android.systemui.bixby2.util;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class MediaParamsParser {
    private static final String FOCUSED_APP = "focusedApp";
    private static final String MEDIA_ACTIVE = "media_control";
    private static final String MUSIC_ACTIVE = "music_active";
    private static final String TAG = "MediaParamsParser";
    private static final String TIME_INFO = "time";

    /* JADX WARN: Removed duplicated region for block: B:31:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MediaModeInfoBixby getMediaInfoFromJson(String str) {
        boolean zEquals;
        boolean zEquals2;
        String string = "";
        long j = -1;
        try {
            JSONArray jSONArray = new JSONArray(String.valueOf(str));
            int length = jSONArray.length();
            zEquals = false;
            zEquals2 = false;
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                    if (jSONObjectOptJSONObject.has(MUSIC_ACTIVE)) {
                        zEquals = jSONObjectOptJSONObject.get(MUSIC_ACTIVE).toString().equals("true");
                    } else if (jSONObjectOptJSONObject.has(MEDIA_ACTIVE)) {
                        zEquals2 = jSONObjectOptJSONObject.get(MEDIA_ACTIVE).toString().equals("true");
                    } else if (jSONObjectOptJSONObject.has(FOCUSED_APP)) {
                        string = jSONObjectOptJSONObject.get(FOCUSED_APP).toString();
                    } else if (jSONObjectOptJSONObject.has(TIME_INFO)) {
                        j = Long.parseLong(jSONObjectOptJSONObject.get(TIME_INFO).toString()) * 1000;
                    }
                } catch (JSONException e) {
                    e = e;
                    Log.e(TAG, "JSONException: " + e.toString());
                    MediaModeInfoBixby mediaModeInfoBixby = new MediaModeInfoBixby();
                    mediaModeInfoBixby.isMediaActive = !zEquals2 || zEquals;
                    mediaModeInfoBixby.focusedApp = string;
                    mediaModeInfoBixby.time = j;
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("isMediaActive ", TAG, zEquals2);
                    return mediaModeInfoBixby;
                }
            }
        } catch (JSONException e2) {
            e = e2;
            zEquals = false;
            zEquals2 = false;
        }
        MediaModeInfoBixby mediaModeInfoBixby2 = new MediaModeInfoBixby();
        mediaModeInfoBixby2.isMediaActive = !zEquals2 || zEquals;
        mediaModeInfoBixby2.focusedApp = string;
        mediaModeInfoBixby2.time = j;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isMediaActive ", TAG, zEquals2);
        return mediaModeInfoBixby2;
    }
}
