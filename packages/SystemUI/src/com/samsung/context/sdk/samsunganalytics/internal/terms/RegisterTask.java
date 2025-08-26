package com.samsung.context.sdk.samsunganalytics.internal.terms;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.AuthUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.ClientUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class RegisterTask implements AsyncTaskClient {
    public final AsyncTaskCallback callback;
    public final String deviceID;
    public final long timestamp;
    public final String trid;
    public final API api = API.DATA_DELETE;
    public HttpsURLConnection conn = null;

    public RegisterTask(String str, String str2, long j, AsyncTaskCallback asyncTaskCallback) {
        this.trid = str;
        this.deviceID = str2;
        this.timestamp = j;
        this.callback = asyncTaskCallback;
    }

    public final void cleanUp(BufferedReader bufferedReader, InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Debug.LogENG("[Register Client] " + e.getMessage());
                return;
            }
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        HttpsURLConnection httpsURLConnection = this.conn;
        if (httpsURLConnection != null) {
            httpsURLConnection.disconnect();
        }
    }

    public final String makeRequestBody() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tid", this.trid);
            jSONObject.put("lid", this.deviceID);
            jSONObject.put("ts", String.valueOf(this.timestamp));
        } catch (JSONException e) {
            Debug.logwingW("failed to make body" + e.getMessage());
        }
        return jSONObject.toString();
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final int onFinish() throws Throwable {
        InputStream errorStream;
        int responseCode;
        BufferedReader bufferedReader;
        AsyncTaskCallback asyncTaskCallback = this.callback;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                responseCode = this.conn.getResponseCode();
                errorStream = responseCode >= 400 ? this.conn.getErrorStream() : this.conn.getInputStream();
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(errorStream));
                } catch (Exception unused) {
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused2) {
            errorStream = null;
        } catch (Throwable th2) {
            th = th2;
            errorStream = null;
        }
        try {
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
            cleanUp(bufferedReader, errorStream);
            return 0;
        } catch (Exception unused3) {
            bufferedReader2 = bufferedReader;
            if (asyncTaskCallback != null) {
                asyncTaskCallback.onFail("", "", "");
            }
            cleanUp(bufferedReader2, errorStream);
            return 0;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader2 = bufferedReader;
            cleanUp(bufferedReader2, errorStream);
            throw th;
        }
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        String str = this.trid;
        try {
            Uri.Builder builderBuildUpon = Uri.parse(this.api.getUrl()).buildUpon();
            String strValueOf = String.valueOf(System.currentTimeMillis());
            builderBuildUpon.appendQueryParameter("tid", str).appendQueryParameter("ts", strValueOf).appendQueryParameter("hc", AuthUtil.sha256(str + strValueOf + ClientUtil.SALT));
            URL url = new URL(builderBuildUpon.build().toString());
            String strMakeRequestBody = makeRequestBody();
            if (TextUtils.isEmpty(strMakeRequestBody)) {
                Log.w("SamsungAnalytics605073", "[Register Client] body is empty");
            } else {
                upload(url, strMakeRequestBody);
            }
        } catch (Exception e) {
            Debug.LogENG("[Register Client] " + e.getMessage());
        }
    }

    public final void upload(URL url, String str) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        this.conn = httpsURLConnection;
        httpsURLConnection.setSSLSocketFactory(CertificateManager.Singleton.instance.sslContext.getSocketFactory());
        this.conn.setRequestMethod(this.api.getMethod());
        this.conn.setConnectTimeout(3000);
        this.conn.setRequestProperty("Content-Type", "application/json");
        this.conn.setDoOutput(true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.conn.getOutputStream());
        bufferedOutputStream.write(str.getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}
