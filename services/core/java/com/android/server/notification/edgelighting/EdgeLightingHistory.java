package com.android.server.notification.edgelighting;

import android.os.Debug;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public final class EdgeLightingHistory {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final boolean IS_DEV_DEBUG;
    public static EdgeLightingHistory mInstance;
    public final Object mLock = new Object();
    public final ArrayList mHostHistory = new ArrayList();
    public final ArrayList mListenerHistory = new ArrayList();
    public final ArrayList mEdgeLightingHistory = new ArrayList();
    public final ArrayList mEventHistory = new ArrayList();
    public final ArrayList mRejectHistory = new ArrayList();

    static {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        IS_DEV_DEBUG = string != null && string.contains("debug");
    }

    public static synchronized EdgeLightingHistory getInstance() {
        EdgeLightingHistory edgeLightingHistory;
        synchronized (EdgeLightingHistory.class) {
            edgeLightingHistory = mInstance;
            if (edgeLightingHistory == null) {
                edgeLightingHistory = new EdgeLightingHistory();
                mInstance = edgeLightingHistory;
            }
        }
        return edgeLightingHistory;
    }

    public static String toTimestampFormat(String str) {
        Calendar calendar = Calendar.getInstance();
        return String.format(
                Locale.US,
                "[%02d-%02d %02d:%02d:%02d.%03d] %s",
                Integer.valueOf(calendar.get(2) + 1),
                Integer.valueOf(calendar.get(5)),
                Integer.valueOf(calendar.get(11)),
                Integer.valueOf(calendar.get(12)),
                Integer.valueOf(calendar.get(13)),
                Integer.valueOf(calendar.get(14)),
                str);
    }

    public final void updateEdgeLightingHistory(String str) {
        if (IS_DEV_DEBUG || DEBUG) {
            if (DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "updateEdgeLightingHistory log = ", str, "EdgeLightingHistory");
            }
            String timestampFormat = toTimestampFormat(str);
            synchronized (this.mLock) {
                try {
                    this.mEdgeLightingHistory.add(timestampFormat);
                    while (this.mEdgeLightingHistory.size() > 30) {
                        this.mEdgeLightingHistory.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void updateHostHistory(String str, String str2) {
        synchronized (this.mLock) {
            try {
                this.mHostHistory.add(str + ':' + toTimestampFormat(str2));
                while (this.mHostHistory.size() > 20) {
                    this.mHostHistory.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateListenerHistory(String str, String str2) {
        synchronized (this.mLock) {
            try {
                this.mListenerHistory.add(str + ':' + toTimestampFormat(str2));
                while (this.mListenerHistory.size() > 20) {
                    this.mListenerHistory.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateRejectHistory(String str) {
        if (IS_DEV_DEBUG || DEBUG) {
            if (DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "updateRejectHistory log = ", str, "EdgeLightingHistory");
            }
            String timestampFormat = toTimestampFormat(str);
            synchronized (this.mLock) {
                try {
                    this.mRejectHistory.add(timestampFormat);
                    while (this.mRejectHistory.size() > 30) {
                        this.mRejectHistory.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
