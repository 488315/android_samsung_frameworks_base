package com.android.systemui.bixby2.util;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isMediaActive ", z2, TAG);
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isMediaActive ", z2, TAG);
        return mediaModeInfoBixby2;
    }
}
