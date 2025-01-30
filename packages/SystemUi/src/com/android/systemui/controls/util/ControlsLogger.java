package com.android.systemui.controls.util;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsLogger {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LogLevel {
        VERBOS,
        DEUBG,
        INFO,
        WARN,
        ERROR
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogLevel.values().length];
            try {
                iArr[LogLevel.VERBOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogLevel.DEUBG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static void printLog(String str, String str2, LogLevel logLevel) {
        int i = WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
        if (i == 2) {
            Log.d(str, str2);
            return;
        }
        if (i == 3) {
            Log.i(str, str2);
        } else if (i == 4) {
            Log.w(str, str2);
        } else {
            if (i != 5) {
                return;
            }
            Log.e(str, str2);
        }
    }

    public static /* synthetic */ void printLog$default(ControlsLogger controlsLogger, String str, String str2) {
        LogLevel logLevel = LogLevel.DEUBG;
        controlsLogger.getClass();
        printLog(str, str2, logLevel);
    }
}
