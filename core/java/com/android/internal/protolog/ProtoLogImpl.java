package com.android.internal.protolog;

import android.tracing.Flags;

import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;

import java.util.TreeMap;

public class ProtoLogImpl {
    private static Runnable sCacheUpdater;
    private static String sLegacyOutputFilePath;
    private static String sLegacyViewerConfigPath;
    private static TreeMap<String, IProtoLogGroup> sLogGroups;
    private static IProtoLog sServiceInstance = null;
    private static String sViewerConfigPath;

    public static void d(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance()
                .log(LogLevel.DEBUG, group, messageHash, paramsMask, messageString, args);
    }

    public static void v(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance()
                .log(LogLevel.VERBOSE, group, messageHash, paramsMask, messageString, args);
    }

    public static void i(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance().log(LogLevel.INFO, group, messageHash, paramsMask, messageString, args);
    }

    public static void w(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance().log(LogLevel.WARN, group, messageHash, paramsMask, messageString, args);
    }

    public static void e(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance()
                .log(LogLevel.ERROR, group, messageHash, paramsMask, messageString, args);
    }

    public static void wtf(
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object... args) {
        getSingleInstance().log(LogLevel.WTF, group, messageHash, paramsMask, messageString, args);
    }

    public static boolean isEnabled(IProtoLogGroup group, LogLevel level) {
        return getSingleInstance().isEnabled(group, level);
    }

    public static synchronized IProtoLog getSingleInstance() {
        IProtoLog iProtoLog;
        synchronized (ProtoLogImpl.class) {
            if (sServiceInstance == null) {
                if (Flags.perfettoProtologTracing()) {
                    sServiceInstance =
                            new PerfettoProtoLogImpl(sViewerConfigPath, sLogGroups, sCacheUpdater);
                } else {
                    sServiceInstance =
                            new LegacyProtoLogImpl(
                                    sLegacyOutputFilePath,
                                    sLegacyViewerConfigPath,
                                    sLogGroups,
                                    sCacheUpdater);
                }
                sCacheUpdater.run();
            }
            iProtoLog = sServiceInstance;
        }
        return iProtoLog;
    }

    public static synchronized void setSingleInstance(IProtoLog instance) {
        synchronized (ProtoLogImpl.class) {
            sServiceInstance = instance;
        }
    }
}
