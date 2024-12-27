package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.net.Uri;
import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

public final class DLSAPIClient implements AsyncTaskClient {
    public static final API REALTIME_API = API.SEND_LOG;
    public static final API RTB_API = API.SEND_BUFFERED_LOG;
    public AsyncTaskCallback asyncTaskCallback;
    public HttpsURLConnection conn;
    public Boolean isBatch;
    public LogType logType;
    public Queue logs;
    public SimpleLog simpleLog;
    public int timeoutInMilliseconds;
    public String trid;

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
            sb.append(simpleLog.timestamp);
            sb.append("");
            asyncTaskCallback.onFail(sb.toString(), simpleLog.data, simpleLog.type.getAbbrev());
            return;
        }
        while (!this.logs.isEmpty()) {
            SimpleLog simpleLog2 = (SimpleLog) this.logs.poll();
            asyncTaskCallback.onFail(
                    simpleLog2.timestamp + "", simpleLog2.data, simpleLog2.type.getAbbrev());
        }
    }

    public final void cleanUp$1(BufferedReader bufferedReader) {
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

    public final String getBody() {
        if (!this.isBatch.booleanValue()) {
            return this.simpleLog.data;
        }
        Iterator it = this.logs.iterator();
        String str = ((SimpleLog) it.next()).data;
        while (it.hasNext()) {
            str = str + "\u000e" + ((SimpleLog) it.next()).data;
        }
        return str;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        int i;
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                int responseCode = this.conn.getResponseCode();
                BufferedReader bufferedReader3 =
                        new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
                try {
                    String string = new JSONObject(bufferedReader3.readLine()).getString("rc");
                    if (responseCode == 200 && string.equalsIgnoreCase("1000")) {
                        Debug.LogD(
                                "[DLS Sender] send result success : "
                                        + responseCode
                                        + " "
                                        + string);
                        i = 1;
                    } else {
                        Debug.LogD(
                                "[DLS Sender] send result fail : " + responseCode + " " + string);
                        i = -7;
                    }
                    callback(responseCode, string);
                    cleanUp$1(bufferedReader3);
                    bufferedReader = string;
                } catch (Exception e) {
                    e = e;
                    bufferedReader2 = bufferedReader3;
                    Debug.LogE("[DLS Client] Send fail.");
                    Debug.LogENG("[DLS Client] " + e.getMessage());
                    callback(0, "");
                    cleanUp$1(bufferedReader2);
                    i = -41;
                    bufferedReader = bufferedReader2;
                    return i;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader3;
                    cleanUp$1(bufferedReader);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        String str = this.trid;
        Boolean bool = this.isBatch;
        try {
            API api = bool.booleanValue() ? RTB_API : REALTIME_API;
            Uri.Builder buildUpon = Uri.parse(api.getUrl()).buildUpon();
            String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());
            buildUpon
                    .appendQueryParameter("ts", format)
                    .appendQueryParameter("type", this.logType.getAbbrev())
                    .appendQueryParameter("tid", str)
                    .appendQueryParameter(
                            "hc", Validation.sha256(str + format + "RSSAV1wsc2s314SAamk"));
            HttpsURLConnection httpsURLConnection =
                    (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.conn = httpsURLConnection;
            httpsURLConnection.setSSLSocketFactory(
                    CertificateManager.Singleton.instance.sslContext.getSocketFactory());
            this.conn.setRequestMethod(api.getMethod());
            this.conn.addRequestProperty("Content-Encoding", bool.booleanValue() ? "gzip" : "text");
            this.conn.setConnectTimeout(this.timeoutInMilliseconds);
            String body = getBody();
            if (!TextUtils.isEmpty(body)) {
                this.conn.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream =
                        bool.booleanValue()
                                ? new BufferedOutputStream(
                                        new GZIPOutputStream(this.conn.getOutputStream()))
                                : new BufferedOutputStream(this.conn.getOutputStream());
                bufferedOutputStream.write(body.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            Debug.LogENG("[DLS Client] Send to DLS : " + body);
        } catch (Exception e) {
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
        }
    }
}
