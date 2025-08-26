package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.AuthUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.ClientUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Queue;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DLSAPIClient implements AsyncTaskClient {
    public static final API REALTIME_API = API.SEND_LOG;
    public static final API RTB_API = API.SEND_BUFFERED_LOG;
    public final AsyncTaskCallback asyncTaskCallback;
    public HttpsURLConnection conn;
    public final Boolean isBatch;
    public final LogType logType;
    public final Queue logs;
    public final SimpleLog simpleLog;
    public final String trid;

    public DLSAPIClient(SimpleLog simpleLog, String str, AsyncTaskCallback asyncTaskCallback) {
        this.conn = null;
        this.isBatch = Boolean.FALSE;
        this.simpleLog = simpleLog;
        this.trid = str;
        this.asyncTaskCallback = asyncTaskCallback;
        this.logType = simpleLog.type;
    }

    public final void callback(int i, String str) {
        AsyncTaskCallback asyncTaskCallback = this.asyncTaskCallback;
        if (asyncTaskCallback == null) {
            return;
        }
        if (i == 200 && str.equalsIgnoreCase("1000")) {
            return;
        }
        if (!this.isBatch.booleanValue()) {
            StringBuilder sb = new StringBuilder();
            SimpleLog simpleLog = this.simpleLog;
            asyncTaskCallback.onFail(MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(simpleLog.timestamp, "", sb), simpleLog.data, simpleLog.type.getAbbrev());
        } else {
            while (!this.logs.isEmpty()) {
                SimpleLog simpleLog2 = (SimpleLog) this.logs.poll();
                asyncTaskCallback.onFail(MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(simpleLog2.timestamp, "", new StringBuilder()), simpleLog2.data, simpleLog2.type.getAbbrev());
            }
        }
    }

    public final void cleanUp$1(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Debug.LogENG("[DLS Client] " + e.getMessage());
                return;
            }
        }
        HttpsURLConnection httpsURLConnection = this.conn;
        if (httpsURLConnection != null) {
            httpsURLConnection.disconnect();
        }
    }

    public final String getBody() {
        if (!this.isBatch.booleanValue()) {
            return this.simpleLog.data;
        }
        Iterator it = this.logs.iterator();
        StringBuilder sb = new StringBuilder(((SimpleLog) it.next()).data);
        while (it.hasNext()) {
            SimpleLog simpleLog = (SimpleLog) it.next();
            sb.append("\u000e");
            sb.append(simpleLog.data);
        }
        return sb.toString();
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final int onFinish() throws Throwable {
        int responseCode;
        BufferedReader bufferedReader;
        int i;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                responseCode = this.conn.getResponseCode();
                bufferedReader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            String string = new JSONObject(bufferedReader.readLine()).getString("rc");
            if (responseCode == 200 && string.equalsIgnoreCase("1000")) {
                Debug.LogD("[DLS Sender] send result success : " + responseCode + " " + string);
                i = 1;
            } else {
                Debug.LogD("[DLS Sender] send result fail : " + responseCode + " " + string);
                i = -7;
            }
            callback(responseCode, string);
            cleanUp$1(bufferedReader);
            return i;
        } catch (Exception e2) {
            e = e2;
            bufferedReader2 = bufferedReader;
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
            callback(0, "");
            cleanUp$1(bufferedReader2);
            return -41;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            cleanUp$1(bufferedReader2);
            throw th;
        }
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        String str = this.trid;
        try {
            API api = this.isBatch.booleanValue() ? RTB_API : REALTIME_API;
            Uri.Builder builderBuildUpon = Uri.parse(api.getUrl()).buildUpon();
            String strValueOf = String.valueOf(System.currentTimeMillis());
            builderBuildUpon.appendQueryParameter("ts", strValueOf).appendQueryParameter("type", this.logType.getAbbrev()).appendQueryParameter("tid", str).appendQueryParameter("hc", AuthUtil.sha256(str + strValueOf + ClientUtil.SALT));
            URL url = new URL(builderBuildUpon.build().toString());
            String body = getBody();
            if (TextUtils.isEmpty(body)) {
                Log.w("SamsungAnalytics605073", "[DLS Client] body is empty");
            } else {
                upload(url, body, api.getMethod());
                Debug.LogENG("[DLS Client] Send to DLS : ".concat(body));
            }
        } catch (Exception e) {
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
        }
    }

    public final void upload(URL url, String str, String str2) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        this.conn = httpsURLConnection;
        httpsURLConnection.setSSLSocketFactory(CertificateManager.Singleton.instance.sslContext.getSocketFactory());
        this.conn.setRequestMethod(str2);
        HttpsURLConnection httpsURLConnection2 = this.conn;
        Boolean bool = this.isBatch;
        httpsURLConnection2.addRequestProperty("Content-Encoding", bool.booleanValue() ? "gzip" : "text");
        this.conn.setConnectTimeout(3000);
        this.conn.setDoOutput(true);
        BufferedOutputStream bufferedOutputStream = bool.booleanValue() ? new BufferedOutputStream(new GZIPOutputStream(this.conn.getOutputStream())) : new BufferedOutputStream(this.conn.getOutputStream());
        bufferedOutputStream.write(str.getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public DLSAPIClient(LogType logType, Queue<SimpleLog> queue, String str, AsyncTaskCallback asyncTaskCallback) {
        this.conn = null;
        this.logs = queue;
        this.trid = str;
        this.asyncTaskCallback = asyncTaskCallback;
        this.isBatch = Boolean.TRUE;
        this.logType = logType;
    }
}
