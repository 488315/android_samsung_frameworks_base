package com.samsung.context.sdk.samsunganalytics.internal.terms;

import android.net.Uri;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RegisterTask implements AsyncTaskClient {
    public final AsyncTaskCallback callback;
    public final String deviceID;
    public final long timestamp;
    public final String trid;
    public final API api = API.DATA_DELETE;
    public HttpsURLConnection conn = null;

    public RegisterTask(String str, String str2, long j) {
        this.trid = "";
        this.deviceID = "";
        this.trid = str;
        this.deviceID = str2;
        this.timestamp = j;
    }

    public final void cleanUp(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
                return;
            }
        }
        HttpsURLConnection httpsURLConnection = this.conn;
        if (httpsURLConnection != null) {
            httpsURLConnection.disconnect();
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        AsyncTaskCallback asyncTaskCallback = this.callback;
        BufferedReader bufferedReader = null;
        try {
            try {
                int responseCode = this.conn.getResponseCode();
                bufferedReader = responseCode >= 400 ? new BufferedReader(new InputStreamReader(this.conn.getErrorStream())) : new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
                String string = new JSONObject(bufferedReader.readLine()).getString("rc");
                if (responseCode == 200 && string.equalsIgnoreCase("1000")) {
                    Debug.LogENG("Success : " + responseCode + " " + string);
                } else {
                    Debug.LogENG("Fail : " + responseCode + " " + string);
                }
                if (asyncTaskCallback != null) {
                    if (responseCode == 200 && string.equalsIgnoreCase("1000")) {
                        asyncTaskCallback.onSuccess();
                    } else {
                        asyncTaskCallback.onFail(string, "", "");
                    }
                }
            } catch (Exception unused) {
                if (asyncTaskCallback != null) {
                    asyncTaskCallback.onFail("", "", "");
                }
            }
            cleanUp(bufferedReader);
            return 0;
        } catch (Throwable th) {
            cleanUp(null);
            throw th;
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        API api = this.api;
        try {
            Uri.Builder buildUpon = Uri.parse(api.getUrl()).buildUpon();
            String format = SimpleDateFormat.getTimeInstance(2).format(new Date());
            buildUpon.appendQueryParameter("ts", format).appendQueryParameter("hc", Validation.sha256(format + "RSSAV1wsc2s314SAamk"));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.conn = httpsURLConnection;
            httpsURLConnection.setSSLSocketFactory(CertificateManager.Singleton.instance.sslContext.getSocketFactory());
            this.conn.setRequestMethod(api.getMethod());
            this.conn.setConnectTimeout(3000);
            this.conn.setRequestProperty("Content-Type", "application/json");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("tid", this.trid);
                jSONObject.put("lid", this.deviceID);
                jSONObject.put("ts", this.timestamp);
            } catch (JSONException unused) {
            }
            String jSONObject2 = jSONObject.toString();
            if (TextUtils.isEmpty(jSONObject2)) {
                return;
            }
            this.conn.setDoOutput(true);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.conn.getOutputStream());
            bufferedOutputStream.write(jSONObject2.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception unused2) {
        }
    }

    public RegisterTask(String str, String str2, long j, AsyncTaskCallback asyncTaskCallback) {
        this.trid = "";
        this.deviceID = "";
        this.trid = str;
        this.deviceID = str2;
        this.timestamp = j;
        this.callback = asyncTaskCallback;
    }
}
