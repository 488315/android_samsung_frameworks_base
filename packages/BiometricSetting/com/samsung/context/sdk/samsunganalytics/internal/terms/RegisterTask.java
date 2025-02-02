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

/* loaded from: classes.dex */
public final class RegisterTask implements AsyncTaskClient {
    private AsyncTaskCallback callback;
    private String deviceID;
    private long timestamp;
    private String trid;
    private final API api = API.DATA_DELETE;
    private HttpsURLConnection conn = null;

    public RegisterTask(String str, String str2, long j, AsyncTaskCallback asyncTaskCallback) {
        this.trid = str;
        this.deviceID = str2;
        this.timestamp = j;
        this.callback = asyncTaskCallback;
    }

    private void callback(int i, String str) {
        if (this.callback == null) {
            return;
        }
        if (i == 200 && str.equalsIgnoreCase("1000")) {
            this.callback.onSuccess();
        } else {
            this.callback.onFail(str, "", "");
        }
    }

    private void cleanUp(BufferedReader bufferedReader) {
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

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
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
                callback(responseCode, string);
            } catch (Exception unused) {
                callback(0, "");
            }
            return 0;
        } finally {
            cleanUp(bufferedReader);
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
            httpsURLConnection.setSSLSocketFactory(CertificateManager.getInstance().getSSLContext().getSocketFactory());
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
}
