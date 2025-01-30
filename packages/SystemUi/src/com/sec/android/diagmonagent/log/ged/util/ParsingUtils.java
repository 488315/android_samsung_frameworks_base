package com.sec.android.diagmonagent.log.ged.util;

import android.util.Log;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.TokenResponse;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ParsingUtils {
    public static TokenResponse parseTokenResponse(String str) {
        TokenResponse tokenResponse = new TokenResponse();
        try {
            tokenResponse.token = new JSONObject(str).getString("token");
        } catch (JSONException unused) {
            Log.e(DeviceUtils.TAG, "JSONException occurred while parsing token response");
        }
        return tokenResponse;
    }
}
