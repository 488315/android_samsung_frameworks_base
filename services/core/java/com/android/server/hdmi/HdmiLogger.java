package com.android.server.hdmi;

import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class HdmiLogger {
    public static final boolean DEBUG = Log.isLoggable("HDMI", 3);
    public static final ThreadLocal sLogger = new ThreadLocal();
    public final HashMap mWarningTimingCache = new HashMap();
    public final HashMap mErrorTimingCache = new HashMap();

    public static final void warning(String str, Object... objArr) {
        getLogger().warningInternal(toLogString(str, objArr));
    }

    public final void warningInternal(String str) {
        String updateLog = updateLog(this.mWarningTimingCache, str);
        if (updateLog.isEmpty()) {
            return;
        }
        Slog.w("HDMI", updateLog);
    }

    public static final void error(String str, Object... objArr) {
        getLogger().errorInternal(toLogString(str, objArr));
    }

    public static final void error(String str, Exception exc, Object... objArr) {
        getLogger().errorInternal(toLogString(str + exc, objArr));
    }

    public final void errorInternal(String str) {
        String updateLog = updateLog(this.mErrorTimingCache, str);
        if (updateLog.isEmpty()) {
            return;
        }
        Slog.e("HDMI", updateLog);
    }

    public static final void debug(String str, Object... objArr) {
        getLogger().debugInternal(toLogString(str, objArr));
    }

    public final void debugInternal(String str) {
        if (DEBUG) {
            Slog.d("HDMI", str);
        }
    }

    public static final String toLogString(String str, Object[] objArr) {
        return objArr.length > 0 ? String.format(str, objArr) : str;
    }

    public static HdmiLogger getLogger() {
        ThreadLocal threadLocal = sLogger;
        HdmiLogger hdmiLogger = (HdmiLogger) threadLocal.get();
        if (hdmiLogger != null) {
            return hdmiLogger;
        }
        HdmiLogger hdmiLogger2 = new HdmiLogger();
        threadLocal.set(hdmiLogger2);
        return hdmiLogger2;
    }

    public static String updateLog(HashMap hashMap, String str) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Pair pair = (Pair) hashMap.get(str);
        if (shouldLogNow(pair, uptimeMillis)) {
            String buildMessage = buildMessage(str, pair);
            hashMap.put(str, new Pair(Long.valueOf(uptimeMillis), 1));
            return buildMessage;
        }
        increaseLogCount(hashMap, str);
        return "";
    }

    public static String buildMessage(String str, Pair pair) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(pair == null ? 1 : ((Integer) pair.second).intValue());
        sb.append("]:");
        sb.append(str);
        return sb.toString();
    }

    public static void increaseLogCount(HashMap hashMap, String str) {
        Pair pair = (Pair) hashMap.get(str);
        if (pair != null) {
            hashMap.put(str, new Pair((Long) pair.first, Integer.valueOf(((Integer) pair.second).intValue() + 1)));
        }
    }

    public static boolean shouldLogNow(Pair pair, long j) {
        return pair == null || j - ((Long) pair.first).longValue() > 20000;
    }
}
