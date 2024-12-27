package com.android.internal.protolog;

import android.util.ArrayMap;
import android.util.proto.ProtoInputStream;

import com.android.internal.protolog.common.ILogger;

import java.io.IOException;
import java.util.Map;

/* loaded from: classes5.dex */
public class ProtoLogViewerConfigReader {
    private Map<Long, String> mLogMessageMap = null;
    private final ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;

    public ProtoLogViewerConfigReader(
            ViewerConfigInputStreamProvider viewerConfigInputStreamProvider) {
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
    }

    public synchronized String getViewerString(long messageHash) {
        if (this.mLogMessageMap == null) {
            return null;
        }
        return this.mLogMessageMap.get(Long.valueOf(messageHash));
    }

    public synchronized void loadViewerConfig(ILogger logger) {
        if (this.mLogMessageMap != null) {
            return;
        }
        try {
            doLoadViewerConfig();
            logger.log("Loaded " + this.mLogMessageMap.size() + " log definitions");
        } catch (IOException e) {
            logger.log(
                    "Unable to load log definitions: IOException while processing viewer config"
                            + e);
        }
    }

    public synchronized void unloadViewerConfig() {
        this.mLogMessageMap = null;
    }

    private void doLoadViewerConfig() throws IOException {
        this.mLogMessageMap = new ArrayMap();
        ProtoInputStream pis = this.mViewerConfigInputStreamProvider.getInputStream();
        while (pis.nextField() != -1) {
            if (pis.getFieldNumber() == 1) {
                long inMessageToken = pis.start(2246267895809L);
                long messageId = 0;
                String message = null;
                while (pis.nextField() != -1) {
                    switch (pis.getFieldNumber()) {
                        case 1:
                            messageId = pis.readLong(1125281431553L);
                            break;
                        case 2:
                            message = pis.readString(1138166333442L);
                            break;
                    }
                }
                if (messageId == 0) {
                    throw new IOException("Failed to get message id");
                }
                if (message == null) {
                    throw new IOException("Failed to get message string");
                }
                this.mLogMessageMap.put(Long.valueOf(messageId), message);
                pis.end(inMessageToken);
            }
        }
    }
}
