package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class GetPolicyClient implements AsyncTaskClient {
    public final API api;
    public final Callback callback;
    public HttpsURLConnection conn = null;
    public final SharedPreferences pref;
    public final Map qParams;

    public GetPolicyClient(API api, Map<String, String> map, SharedPreferences sharedPreferences, Callback callback) {
        this.api = api;
        this.qParams = map;
        this.pref = sharedPreferences;
        this.callback = callback;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00c7 A[ADDED_TO_REGION] */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onFinish() {
        BufferedReader bufferedReader;
        int i;
        Callback callback;
        String string;
        SharedPreferences sharedPreferences = this.pref;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                if (this.conn.getResponseCode() != 200) {
                    Debug.LogE("Fail to get Policy. Response code : " + this.conn.getResponseCode());
                    i = -61;
                } else {
                    i = 0;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
            } catch (Exception unused) {
            }
        } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader2;
        }
        try {
            String readLine = bufferedReader.readLine();
            Debug.LogENG(readLine);
            JSONObject jSONObject = new JSONObject(readLine);
            int i2 = jSONObject.getInt("rc");
            if (i2 != 1000) {
                Debug.LogE("Fail to get Policy; Invalid Message. Result code : " + i2);
                i = -61;
            } else {
                Debug.LogD("GetPolicyClient", "Get Policy Success");
                if (TextUtils.isEmpty(sharedPreferences.getString("lgt", "")) && (callback = this.callback) != null && (string = jSONObject.getString("lgt")) != null && string.equals("rtb")) {
                    callback.onResult(Boolean.TRUE);
                }
                save(jSONObject);
            }
            HttpsURLConnection httpsURLConnection = this.conn;
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
            try {
                bufferedReader.close();
                HttpsURLConnection httpsURLConnection2 = this.conn;
                if (httpsURLConnection2 != null) {
                    httpsURLConnection2.disconnect();
                }
            } catch (IOException unused2) {
            }
        } catch (Exception unused3) {
            bufferedReader2 = bufferedReader;
            Debug.LogE("Fail to get Policy");
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException unused4) {
                    i = -61;
                    boolean isEmpty = TextUtils.isEmpty(sharedPreferences.getString("dom", ""));
                    if (i == -61) {
                        sharedPreferences.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
                    }
                    return i;
                }
            }
            HttpsURLConnection httpsURLConnection3 = this.conn;
            if (httpsURLConnection3 != null) {
                httpsURLConnection3.disconnect();
            }
            i = -61;
            boolean isEmpty2 = TextUtils.isEmpty(sharedPreferences.getString("dom", ""));
            if (i == -61) {
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException unused5) {
                    throw th;
                }
            }
            HttpsURLConnection httpsURLConnection4 = this.conn;
            if (httpsURLConnection4 != null) {
                httpsURLConnection4.disconnect();
            }
            throw th;
        }
        boolean isEmpty22 = TextUtils.isEmpty(sharedPreferences.getString("dom", ""));
        if (i == -61 && !isEmpty22) {
            sharedPreferences.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
        }
        return i;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        Map map = this.qParams;
        API api = this.api;
        try {
            Uri.Builder buildUpon = Uri.parse(api.getUrl()).buildUpon();
            for (String str : map.keySet()) {
                buildUpon.appendQueryParameter(str, (String) map.get(str));
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
            Debug.LogE("Fail to get Policy");
            Debug.LogENG("[GetPolicyClient] " + e.getMessage());
        }
    }
}
