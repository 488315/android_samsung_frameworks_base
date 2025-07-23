package com.android.internal.logging;

import android.content.Context;
import android.metrics.LogMaker;
import android.os.Build;

/* loaded from: classes5.dex */
public class MetricsLogger {
    public static final int LOGTAG = 524292;
    public static final int VIEW_UNKNOWN = 0;
    private static MetricsLogger sMetricsLogger;

    private static MetricsLogger getLogger() {
        if (sMetricsLogger == null) {
            sMetricsLogger = new MetricsLogger();
        }
        return sMetricsLogger;
    }

    protected void saveLog(LogMaker logMaker) {
        EventLogTags.writeSysuiMultiAction(logMaker.serialize());
    }

    public void write(LogMaker logMaker) {
        if (logMaker.getType() == 0) {
            logMaker.setType(4);
        }
        saveLog(logMaker);
    }

    public void count(String str, int i) {
        saveLog(new LogMaker(803).setCounterName(str).setCounterValue(i));
    }

    public void histogram(String str, int i) {
        saveLog(new LogMaker(804).setCounterName(str).setCounterBucket(i).setCounterValue(1));
    }

    public void visible(int i) throws IllegalArgumentException {
        if (Build.IS_DEBUGGABLE && i == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        saveLog(new LogMaker(i).setType(1));
    }

    public void hidden(int i) throws IllegalArgumentException {
        if (Build.IS_DEBUGGABLE && i == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        saveLog(new LogMaker(i).setType(2));
    }

    public void visibility(int i, boolean z) throws IllegalArgumentException {
        if (z) {
            visible(i);
        } else {
            hidden(i);
        }
    }

    public void visibility(int i, int i2) throws IllegalArgumentException {
        visibility(i, i2 == 0);
    }

    public void action(int i) {
        saveLog(new LogMaker(i).setType(4));
    }

    public void action(int i, int i2) {
        saveLog(new LogMaker(i).setType(4).setSubtype(i2));
    }

    public void action(int i, boolean z) {
        saveLog(new LogMaker(i).setType(4).setSubtype(z ? 1 : 0));
    }

    public void action(int i, String str) {
        if (Build.IS_DEBUGGABLE && i == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        saveLog(new LogMaker(i).setType(4).setPackageName(str));
    }

    @Deprecated
    public static void visible(Context context, int i) throws IllegalArgumentException {
        getLogger().visible(i);
    }

    @Deprecated
    public static void hidden(Context context, int i) throws IllegalArgumentException {
        getLogger().hidden(i);
    }

    @Deprecated
    public static void visibility(Context context, int i, boolean z) throws IllegalArgumentException {
        getLogger().visibility(i, z);
    }

    @Deprecated
    public static void visibility(Context context, int i, int i2) throws IllegalArgumentException {
        visibility(context, i, i2 == 0);
    }

    @Deprecated
    public static void action(Context context, int i) {
        getLogger().action(i);
    }

    @Deprecated
    public static void action(Context context, int i, int i2) {
        getLogger().action(i, i2);
    }

    @Deprecated
    public static void action(Context context, int i, boolean z) {
        getLogger().action(i, z);
    }

    @Deprecated
    public static void action(LogMaker logMaker) {
        getLogger().write(logMaker);
    }

    @Deprecated
    public static void action(Context context, int i, String str) {
        getLogger().action(i, str);
    }

    @Deprecated
    public static void count(Context context, String str, int i) {
        getLogger().count(str, i);
    }

    @Deprecated
    public static void histogram(Context context, String str, int i) {
        getLogger().histogram(str, i);
    }
}
