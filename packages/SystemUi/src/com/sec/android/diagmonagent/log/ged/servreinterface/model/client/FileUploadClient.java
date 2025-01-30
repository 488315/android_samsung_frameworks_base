package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.util.Log;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FileUploadClient {
    public final HttpURLConnection mURLConnection;

    public FileUploadClient(String str) {
        this.mURLConnection = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLDecoder.decode(str, "utf-8")).openConnection();
            this.mURLConnection = httpURLConnection;
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoOutput(true);
        } catch (IOException e) {
            Log.d(DeviceUtils.TAG, "constructor?" + e);
        }
    }
}
