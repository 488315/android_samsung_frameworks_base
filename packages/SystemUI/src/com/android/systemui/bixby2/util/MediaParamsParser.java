package com.android.systemui.bixby2.util;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class MediaParamsParser {
    private static final String FOCUSED_APP = "focusedApp";
    private static final String MEDIA_ACTIVE = "media_control";
    private static final String MUSIC_ACTIVE = "music_active";
    private static final String TAG = "MediaParamsParser";
    private static final String TIME_INFO = "time";

    public static MediaModeInfoBixby getMediaInfoFromJson(String str) {
        boolean z;
        boolean z2;
        String str2 = "";
        long j = -1;
        try {
            JSONArray jSONArray = new JSONArray(String.valueOf(str));
            int length = jSONArray.length();
            z = false;
            z2 = false;
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject.has(MUSIC_ACTIVE)) {
                        z = optJSONObject.get(MUSIC_ACTIVE).toString().equals("true");
                    } else if (optJSONObject.has(MEDIA_ACTIVE)) {
                        z2 = optJSONObject.get(MEDIA_ACTIVE).toString().equals("true");
                    } else if (optJSONObject.has(FOCUSED_APP)) {
                        str2 = optJSONObject.get(FOCUSED_APP).toString();
                    } else if (optJSONObject.has(TIME_INFO)) {
                        j = Long.parseLong(optJSONObject.get(TIME_INFO).toString()) * 1000;
                    }
                } catch (JSONException e) {
                    e = e;
                    Log.e(TAG, "JSONException: " + e.toString());
                    MediaModeInfoBixby mediaModeInfoBixby = new MediaModeInfoBixby();
                    mediaModeInfoBixby.isMediaActive = !z2 || z;
                    mediaModeInfoBixby.focusedApp = str2;
                    mediaModeInfoBixby.time = j;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isMediaActive ", TAG, z2);
                    return mediaModeInfoBixby;
                }
            }
        } catch (JSONException e2) {
            e = e2;
            z = false;
            z2 = false;
        }
        MediaModeInfoBixby mediaModeInfoBixby2 = new MediaModeInfoBixby();
        mediaModeInfoBixby2.isMediaActive = !z2 || z;
        mediaModeInfoBixby2.focusedApp = str2;
        mediaModeInfoBixby2.time = j;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isMediaActive ", TAG, z2);
        return mediaModeInfoBixby2;
    }
}
