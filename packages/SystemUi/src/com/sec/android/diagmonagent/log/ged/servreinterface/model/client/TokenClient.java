package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.content.Context;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TokenClient {
    public final HttpURLConnection mURLConnection;
    public final Response response;

    public TokenClient(Context context, String str) {
        this.mURLConnection = null;
        try {
            String str2 = RestUtils.DEVICE_KEY;
            URL url = new URL("https://diagmon-serviceapi.samsungdm.com".concat(str));
            this.response = new Response();
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection = httpURLConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            AppLog.m269d("getJwtAuth(): " + RestUtils.makeAuth(context, str));
            httpURLConnection.setRequestProperty("Authorization", RestUtils.makeAuth(context, str));
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setDoInput(true);
        } catch (IOException e) {
            AppLog.m270e(e + " constructor?");
        }
    }
}
