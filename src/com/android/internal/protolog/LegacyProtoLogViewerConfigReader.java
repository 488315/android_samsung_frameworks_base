package com.android.internal.protolog;

import com.android.internal.protolog.common.ILogger;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class LegacyProtoLogViewerConfigReader {
    private static final String TAG = "ProtoLogViewerConfigReader";
    private Map<Long, String> mLogMessageMap = null;

    public synchronized String getViewerString(long j) {
        Map<Long, String> map = this.mLogMessageMap;
        if (map == null) {
            return null;
        }
        return map.get(Long.valueOf(j));
    }

    public synchronized void loadViewerConfig(ILogger iLogger, String str) {
        try {
            try {
                try {
                    loadViewerConfig(new GZIPInputStream(new FileInputStream(str)));
                    iLogger.log("Loaded " + this.mLogMessageMap.size() + " log definitions from " + str);
                } catch (IOException e) {
                    iLogger.log("Unable to load log definitions: IOException while reading " + str + ". " + e);
                }
            } catch (FileNotFoundException e2) {
                iLogger.log("Unable to load log definitions: File " + str + " not found." + e2);
            }
        } catch (JSONException e3) {
            iLogger.log("Unable to load log definitions: JSON parsing exception while reading " + str + ". " + e3);
        }
    }

    public synchronized void loadViewerConfig(InputStream inputStream) throws IOException, JSONException {
        if (this.mLogMessageMap != null) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            sb.append(readLine);
            sb.append('\n');
        }
        bufferedReader.close();
        JSONObject jSONObject = new JSONObject(sb.toString()).getJSONObject("messages");
        this.mLogMessageMap = new TreeMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                long parseLong = Long.parseLong(next);
                this.mLogMessageMap.put(Long.valueOf(parseLong), jSONObject.getJSONObject(next).getString("message"));
            } catch (NumberFormatException unused) {
            }
        }
    }

    public synchronized int knownViewerStringsNumber() {
        Map<Long, String> map = this.mLogMessageMap;
        if (map == null) {
            return 0;
        }
        return map.size();
    }
}
