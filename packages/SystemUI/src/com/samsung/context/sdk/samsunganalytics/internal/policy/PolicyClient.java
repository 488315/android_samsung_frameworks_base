package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PolicyClient implements AsyncTaskClient {
    public final API api;
    public final Callback callback;
    public HttpsURLConnection conn = null;
    public final SharedPreferences pref;
    public final Map qParams;

    public PolicyClient(API api, Map<String, String> map, SharedPreferences sharedPreferences, Callback callback) {
        this.api = api;
        this.qParams = map;
        this.pref = sharedPreferences;
        this.callback = callback;
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

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final int onFinish() {
        int i;
        Callback callback;
        BufferedReader bufferedReader = null;
        try {
            try {
                if (this.conn.getResponseCode() != 200) {
                    Debug.LogE("Fail to get Policy. Response code : " + this.conn.getResponseCode());
                    i = -61;
                } else {
                    i = 0;
                }
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
                try {
                    String readLine = bufferedReader2.readLine();
                    Debug.LogENG(readLine);
                    JSONObject jSONObject = new JSONObject(readLine);
                    int i2 = jSONObject.getInt("rc");
                    if (i2 == 1000) {
                        Debug.LogD("GetPolicyClient", "Get Policy Success");
                        if (TextUtils.isEmpty(this.pref.getString("lgt", "")) && (callback = this.callback) != null && jSONObject.getString("lgt").equals("rtb")) {
                            callback.onResult(Boolean.TRUE);
                        }
                        save(jSONObject);
                    } else if (i2 == 1201) {
                        Debug.LogD("GetPolicyClient", "Result code : 1201, quota should be changed to zero");
                        this.pref.edit().putInt("oq-3g", 0).putInt("dq-3g", 0).putInt("oq-w", 0).putInt("dq-w", 0).putLong("policy_received_date", System.currentTimeMillis()).apply();
                    } else {
                        Debug.logwingE("Fail to get Policy; Invalid Message. Result code : " + i2);
                        i = -61;
                    }
                    cleanUp(bufferedReader2);
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    Debug.LogE("Fail to get Policy");
                    cleanUp(bufferedReader);
                    i = -61;
                    boolean isEmpty = TextUtils.isEmpty(this.pref.getString("dom", ""));
                    if (i == -61) {
                        this.pref.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
                    }
                    return i;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    cleanUp(bufferedReader);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
        }
        boolean isEmpty2 = TextUtils.isEmpty(this.pref.getString("dom", ""));
        if (i == -61 && !isEmpty2) {
            this.pref.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
        }
        return i;
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        API api = this.api;
        try {
            Uri.Builder buildUpon = Uri.parse(api.getUrl()).buildUpon();
            for (String str : this.qParams.keySet()) {
                buildUpon.appendQueryParameter(str, (String) this.qParams.get(str));
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.conn = httpsURLConnection;
            httpsURLConnection.setSSLSocketFactory(CertificateManager.Singleton.instance.sslContext.getSocketFactory());
            this.conn.setRequestMethod(api.getMethod());
            this.conn.setConnectTimeout(3000);
        } catch (Exception unused) {
            Debug.LogE("Fail to get Policy");
        }
    }

    public final void save(JSONObject jSONObject) {
        try {
            this.pref.edit().putInt("oq-3g", jSONObject.getInt("oq-3g") * 1024).putInt("dq-3g", jSONObject.getInt("dq-3g") * 1024).putInt("oq-w", jSONObject.getInt("oq-w") * 1024).putInt("dq-w", jSONObject.getInt("dq-w") * 1024).putString("dom", "https://" + jSONObject.getString("dom")).putString("uri", jSONObject.getString("uri")).putString("bat-uri", jSONObject.getString("bat-uri")).putString("lgt", jSONObject.getString("lgt")).putInt("rint", jSONObject.getInt("rint")).putLong("policy_received_date", System.currentTimeMillis()).apply();
            Domain.DLS.setDomain("https://" + jSONObject.getString("dom"));
            Directory.DLS_DIR.setDirectory(jSONObject.getString("uri"));
            Directory.DLS_DIR_BAT.setDirectory(jSONObject.getString("bat-uri"));
            Debug.LogENG("dq-3g: " + (jSONObject.getInt("dq-3g") * 1024) + ", dq-w: " + (jSONObject.getInt("dq-w") * 1024) + ", oq-3g: " + (jSONObject.getInt("oq-3g") * 1024) + ", oq-w: " + (jSONObject.getInt("oq-w") * 1024));
        } catch (JSONException e) {
            Debug.logwingE("Fail to save policy" + e.getMessage());
            Debug.LogENG("[GetPolicyClient] " + e.getMessage());
        }
    }
}
