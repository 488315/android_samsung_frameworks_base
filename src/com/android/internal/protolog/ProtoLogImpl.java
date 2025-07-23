package com.android.internal.protolog;

import android.os.ServiceManager;
import android.tracing.Flags;
import android.util.Log;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;
import java.io.File;
import java.util.TreeMap;

/* loaded from: classes3.dex */
public class ProtoLogImpl {
    private static final String LOG_TAG = "ProtoLogImpl";
    private static ProtoLogCacheUpdater sCacheUpdater;
    private static String sLegacyOutputFilePath;
    private static String sLegacyViewerConfigPath;
    private static TreeMap<String, IProtoLogGroup> sLogGroups;
    private static IProtoLog sServiceInstance;
    private static String sViewerConfigPath;

    public static void d(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.DEBUG, iProtoLogGroup, j, i, objArr);
    }

    public static void v(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.VERBOSE, iProtoLogGroup, j, i, objArr);
    }

    public static void i(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.INFO, iProtoLogGroup, j, i, objArr);
    }

    public static void w(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.WARN, iProtoLogGroup, j, i, objArr);
    }

    public static void e(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.ERROR, iProtoLogGroup, j, i, objArr);
    }

    public static void wtf(IProtoLogGroup iProtoLogGroup, long j, int i, Object... objArr) {
        getSingleInstance().log(LogLevel.WTF, iProtoLogGroup, j, i, objArr);
    }

    public static boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return isEnabled(getSingleInstance(), iProtoLogGroup, logLevel);
    }

    private static boolean isEnabled(IProtoLog iProtoLog, IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return iProtoLog.isEnabled(iProtoLogGroup, logLevel);
    }

    public static synchronized IProtoLog getSingleInstance() {
        IProtoLog iProtoLog;
        synchronized (ProtoLogImpl.class) {
            if (sServiceInstance == null) {
                Log.i(LOG_TAG, "Setting up ProtoLogImpl with viewerConfigPath = " + sViewerConfigPath);
                IProtoLogGroup[] iProtoLogGroupArr = (IProtoLogGroup[]) sLogGroups.values().toArray(new IProtoLogGroup[0]);
                if (Flags.perfettoProtologTracing()) {
                    if (!new File(sViewerConfigPath).exists()) {
                        Log.e(LOG_TAG, "Failed to find viewer config file " + sViewerConfigPath + " when setting up ProtoLogImpl. ProtoLog will not work here!");
                        sServiceInstance = new NoViewerConfigProtoLogImpl();
                    } else {
                        try {
                            ProcessedPerfettoProtoLogImpl processedPerfettoProtoLogImpl = new ProcessedPerfettoProtoLogImpl(ProtoLog.getSharedSingleInstanceDataSource(), sViewerConfigPath, sCacheUpdater, iProtoLogGroupArr);
                            sServiceInstance = processedPerfettoProtoLogImpl;
                            processedPerfettoProtoLogImpl.enable();
                        } catch (ServiceManager.ServiceNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    sServiceInstance = createLegacyProtoLogImpl(iProtoLogGroupArr);
                }
                sCacheUpdater.update(sServiceInstance);
            }
            iProtoLog = sServiceInstance;
        }
        return iProtoLog;
    }

    private static LegacyProtoLogImpl createLegacyProtoLogImpl(IProtoLogGroup[] iProtoLogGroupArr) {
        LegacyProtoLogImpl legacyProtoLogImpl = new LegacyProtoLogImpl(sLegacyOutputFilePath, sLegacyViewerConfigPath, sCacheUpdater);
        legacyProtoLogImpl.registerGroups(iProtoLogGroupArr);
        return legacyProtoLogImpl;
    }

    public static synchronized void setSingleInstance(IProtoLog iProtoLog) {
        synchronized (ProtoLogImpl.class) {
            sServiceInstance = iProtoLog;
        }
    }
}
