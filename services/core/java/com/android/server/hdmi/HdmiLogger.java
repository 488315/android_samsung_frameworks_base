package com.android.server.hdmi;

import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import java.util.HashMap;

public final class HdmiLogger {
    public static final boolean DEBUG = Log.isLoggable("HDMI", 3);
    public static final ThreadLocal sLogger = new ThreadLocal();
    public final HashMap mWarningTimingCache = new HashMap();
    public final HashMap mErrorTimingCache = new HashMap();

    public static final void debug(String str, Object... objArr) {
        getLogger();
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        if (DEBUG) {
            Slog.d("HDMI", str);
        }
    }

    public static final void error(Exception exc, String str, Object... objArr) {
        HdmiLogger logger = getLogger();
        String str2 = str + exc;
        if (objArr.length > 0) {
            str2 = String.format(str2, objArr);
        }
        String updateLog = updateLog(str2, logger.mErrorTimingCache);
        if (updateLog.isEmpty()) {
            return;
        }
        Slog.e("HDMI", updateLog);
    }

    public static final void error(String str, Object... objArr) {
        HdmiLogger logger = getLogger();
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        String updateLog = updateLog(str, logger.mErrorTimingCache);
        if (updateLog.isEmpty()) {
            return;
        }
        Slog.e("HDMI", updateLog);
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

    public static String updateLog(String str, HashMap hashMap) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Pair pair = (Pair) hashMap.get(str);
        if (pair != null && uptimeMillis - ((Long) pair.first).longValue() <= 20000) {
            Pair pair2 = (Pair) hashMap.get(str);
            if (pair2 == null) {
                return "";
            }
            hashMap.put(
                    str,
                    new Pair(
                            (Long) pair2.first,
                            Integer.valueOf(((Integer) pair2.second).intValue() + 1)));
            return "";
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append(pair == null ? 1 : ((Integer) pair.second).intValue());
        sb.append("]:");
        sb.append(str);
        String sb2 = sb.toString();
        hashMap.put(str, new Pair(Long.valueOf(uptimeMillis), 1));
        return sb2;
    }

    public static final void warning(String str, Object... objArr) {
        HdmiLogger logger = getLogger();
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        String updateLog = updateLog(str, logger.mWarningTimingCache);
        if (updateLog.isEmpty()) {
            return;
        }
        Slog.w("HDMI", updateLog);
    }
}
