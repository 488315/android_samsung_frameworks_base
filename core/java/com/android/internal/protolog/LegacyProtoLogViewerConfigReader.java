package com.android.internal.protolog;

import com.android.internal.protolog.common.ILogger;

import org.json.JSONException;
import org.json.JSONObject;

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

public class LegacyProtoLogViewerConfigReader {
    private static final String TAG = "ProtoLogViewerConfigReader";
    private Map<Long, String> mLogMessageMap = null;

    public synchronized String getViewerString(long messageHash) {
        if (this.mLogMessageMap == null) {
            return null;
        }
        return this.mLogMessageMap.get(Long.valueOf(messageHash));
    }

    public synchronized void loadViewerConfig(ILogger logger, String viewerConfigFilename) {
        try {
            try {
                loadViewerConfig(new GZIPInputStream(new FileInputStream(viewerConfigFilename)));
                logger.log(
                        "Loaded "
                                + this.mLogMessageMap.size()
                                + " log definitions from "
                                + viewerConfigFilename);
            } catch (IOException e) {
                logger.log(
                        "Unable to load log definitions: IOException while reading "
                                + viewerConfigFilename
                                + ". "
                                + e);
            } catch (JSONException e2) {
                logger.log(
                        "Unable to load log definitions: JSON parsing exception while reading "
                                + viewerConfigFilename
                                + ". "
                                + e2);
            }
        } catch (FileNotFoundException e3) {
            logger.log(
                    "Unable to load log definitions: File "
                            + viewerConfigFilename
                            + " not found."
                            + e3);
        }
    }

    public synchronized void loadViewerConfig(InputStream viewerConfigInputStream)
            throws IOException, JSONException {
        if (this.mLogMessageMap != null) {
            return;
        }
        InputStreamReader config = new InputStreamReader(viewerConfigInputStream);
        BufferedReader reader = new BufferedReader(config);
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            } else {
                builder.append(line).append('\n');
            }
        }
        reader.close();
        JSONObject json = new JSONObject(builder.toString());
        JSONObject messages = json.getJSONObject("messages");
        this.mLogMessageMap = new TreeMap();
        Iterator it = messages.keys();
        while (it.hasNext()) {
            String key = it.next();
            try {
                long hash = Long.parseLong(key);
                JSONObject val = messages.getJSONObject(key);
                String msg = val.getString("message");
                this.mLogMessageMap.put(Long.valueOf(hash), msg);
            } catch (NumberFormatException e) {
            }
        }
    }

    public synchronized int knownViewerStringsNumber() {
        if (this.mLogMessageMap == null) {
            return 0;
        }
        return this.mLogMessageMap.size();
    }
}
