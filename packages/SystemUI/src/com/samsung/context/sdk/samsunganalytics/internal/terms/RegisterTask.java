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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void cleanUp(BufferedReader bufferedReader, InputStream inputStream) {
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

    public final String makeRequestBody() {
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
    public final int onFinish() {
        InputStream inputStream;
        AsyncTaskCallback asyncTaskCallback = this.callback;
        BufferedReader bufferedReader = null;
        try {
            try {
                int responseCode = this.conn.getResponseCode();
                inputStream = responseCode >= 400 ? this.conn.getErrorStream() : this.conn.getInputStream();
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        String string = new JSONObject(bufferedReader2.readLine()).getString("rc");
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
                        cleanUp(bufferedReader2, inputStream);
                        return 0;
                    } catch (Exception unused) {
                        bufferedReader = bufferedReader2;
                        if (asyncTaskCallback != null) {
                            asyncTaskCallback.onFail("", "", "");
                        }
                        cleanUp(bufferedReader, inputStream);
                        return 0;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        cleanUp(bufferedReader, inputStream);
                        throw th;
                    }
                } catch (Exception unused2) {
                }
            } catch (Exception unused3) {
                inputStream = null;
            } catch (Throwable th2) {
                th = th2;
                inputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        String str = this.trid;
        try {
            Uri.Builder buildUpon = Uri.parse(this.api.getUrl()).buildUpon();
            String valueOf = String.valueOf(System.currentTimeMillis());
            buildUpon.appendQueryParameter("tid", str).appendQueryParameter("ts", valueOf).appendQueryParameter("hc", AuthUtil.sha256(str + valueOf + ClientUtil.SALT));
            URL url = new URL(buildUpon.build().toString());
            String makeRequestBody = makeRequestBody();
            if (TextUtils.isEmpty(makeRequestBody)) {
                Log.w("SamsungAnalytics605073", "[Register Client] body is empty");
            } else {
                upload(url, makeRequestBody);
            }
        } catch (Exception e) {
            Debug.LogENG("[Register Client] " + e.getMessage());
        }
    }

    public final void upload(URL url, String str) {
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
