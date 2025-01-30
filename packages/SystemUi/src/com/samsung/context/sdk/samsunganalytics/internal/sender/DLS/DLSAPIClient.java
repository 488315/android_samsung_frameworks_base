package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.net.Uri;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
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
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DLSAPIClient implements AsyncTaskClient {
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
            sb.append(simpleLog.timestamp);
            sb.append("");
            asyncTaskCallback.onFail(sb.toString(), simpleLog.data, simpleLog.type.getAbbrev());
            return;
        }
        while (true) {
            Queue queue = this.logs;
            if (queue.isEmpty()) {
                return;
            }
            SimpleLog simpleLog2 = (SimpleLog) queue.poll();
            asyncTaskCallback.onFail(simpleLog2.timestamp + "", simpleLog2.data, simpleLog2.type.getAbbrev());
        }
    }

    public final String getBody() {
        if (!this.isBatch.booleanValue()) {
            return this.simpleLog.data;
        }
        Iterator it = this.logs.iterator();
        String str = ((SimpleLog) it.next()).data;
        while (it.hasNext()) {
            SimpleLog simpleLog = (SimpleLog) it.next();
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "\u000e");
            m2m.append(simpleLog.data);
            str = m2m.toString();
        }
        return str;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        BufferedReader bufferedReader;
        int responseCode;
        int i;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                responseCode = this.conn.getResponseCode();
                bufferedReader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader2;
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
            try {
                bufferedReader.close();
                HttpsURLConnection httpsURLConnection = this.conn;
                if (httpsURLConnection == null) {
                    return i;
                }
                httpsURLConnection.disconnect();
                return i;
            } catch (IOException unused) {
                return i;
            }
        } catch (Exception e2) {
            e = e2;
            bufferedReader2 = bufferedReader;
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
            callback(0, "");
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException unused2) {
                    return -41;
                }
            }
            HttpsURLConnection httpsURLConnection2 = this.conn;
            if (httpsURLConnection2 != null) {
                httpsURLConnection2.disconnect();
            }
            return -41;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException unused3) {
                    throw th;
                }
            }
            HttpsURLConnection httpsURLConnection3 = this.conn;
            if (httpsURLConnection3 != null) {
                httpsURLConnection3.disconnect();
            }
            throw th;
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
            buildUpon.appendQueryParameter("ts", format).appendQueryParameter("type", this.logType.getAbbrev()).appendQueryParameter("tid", str).appendQueryParameter("hc", Validation.sha256(str + format + "RSSAV1wsc2s314SAamk"));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.conn = httpsURLConnection;
            httpsURLConnection.setSSLSocketFactory(CertificateManager.Singleton.instance.sslContext.getSocketFactory());
            this.conn.setRequestMethod(api.getMethod());
            this.conn.addRequestProperty("Content-Encoding", bool.booleanValue() ? "gzip" : "text");
            this.conn.setConnectTimeout(3000);
            String body = getBody();
            if (!TextUtils.isEmpty(body)) {
                this.conn.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = bool.booleanValue() ? new BufferedOutputStream(new GZIPOutputStream(this.conn.getOutputStream())) : new BufferedOutputStream(this.conn.getOutputStream());
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

    public DLSAPIClient(LogType logType, Queue<SimpleLog> queue, String str, AsyncTaskCallback asyncTaskCallback) {
        this.conn = null;
        this.isBatch = Boolean.FALSE;
        this.logs = queue;
        this.trid = str;
        this.asyncTaskCallback = asyncTaskCallback;
        this.isBatch = Boolean.TRUE;
        this.logType = logType;
    }
}
