package com.android.internal.protolog;

import android.os.ServiceManager;
import android.tracing.Flags;
import android.tracing.perfetto.DataSourceParams;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;
import java.util.Arrays;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class ProtoLog {

    @Deprecated
    public static boolean REQUIRE_PROTOLOGTOOL = true;
    private static ProtoLogDataSource sDataSource;
    private static final Object sInitLock = new Object();
    private static IProtoLog sProtoLogInstance;

    private static boolean logOnlyToLogcat$ravenwood() {
        return true;
    }

    public static void init(IProtoLogGroup... iProtoLogGroupArr) {
        if (logOnlyToLogcat()) {
            sProtoLogInstance = new LogcatOnlyProtoLogImpl();
        } else {
            initializePerfettoProtoLog(iProtoLogGroupArr);
        }
    }

    private static boolean logOnlyToLogcat() {
        return !Flags.perfettoProtologTracing();
    }

    private static void initializePerfettoProtoLog(IProtoLogGroup... iProtoLogGroupArr) {
        ProtoLogDataSource sharedSingleInstanceDataSource = getSharedSingleInstanceDataSource();
        synchronized (sInitLock) {
            HashSet hashSet = new HashSet(Arrays.stream(iProtoLogGroupArr).toList());
            IProtoLog iProtoLog = sProtoLogInstance;
            if (iProtoLog != null) {
                hashSet.addAll(iProtoLog.getRegisteredGroups());
            }
            sProtoLogInstance = createAndEnableNewPerfettoProtoLogImpl(sharedSingleInstanceDataSource, (IProtoLogGroup[]) hashSet.toArray(new IProtoLogGroup[0]));
            if (iProtoLog instanceof PerfettoProtoLogImpl) {
                ((PerfettoProtoLogImpl) iProtoLog).disable();
            }
        }
    }

    private static PerfettoProtoLogImpl createAndEnableNewPerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, IProtoLogGroup[] iProtoLogGroupArr) {
        try {
            UnprocessedPerfettoProtoLogImpl unprocessedPerfettoProtoLogImpl = new UnprocessedPerfettoProtoLogImpl(protoLogDataSource, iProtoLogGroupArr);
            unprocessedPerfettoProtoLogImpl.enable();
            return unprocessedPerfettoProtoLogImpl;
        } catch (ServiceManager.ServiceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void d(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.DEBUG, iProtoLogGroup, str, objArr);
    }

    public static void v(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.VERBOSE, iProtoLogGroup, str, objArr);
    }

    public static void i(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.INFO, iProtoLogGroup, str, objArr);
    }

    public static void w(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.WARN, iProtoLogGroup, str, objArr);
    }

    public static void e(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.ERROR, iProtoLogGroup, str, objArr);
    }

    public static void wtf(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logStringMessage(LogLevel.WTF, iProtoLogGroup, str, objArr);
    }

    public static boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return sProtoLogInstance.isEnabled(iProtoLogGroup, logLevel);
    }

    public static IProtoLog getSingleInstance() {
        return sProtoLogInstance;
    }

    public static synchronized ProtoLogDataSource getSharedSingleInstanceDataSource() {
        ProtoLogDataSource protoLogDataSource;
        synchronized (ProtoLog.class) {
            if (sDataSource == null) {
                Producer.init(InitArguments.DEFAULTS);
                sDataSource = new ProtoLogDataSource();
                sDataSource.register(new DataSourceParams.Builder().setBufferExhaustedPolicy(0).build());
            }
            protoLogDataSource = sDataSource;
        }
        return protoLogDataSource;
    }

    private static void logStringMessage(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        IProtoLog iProtoLog = sProtoLogInstance;
        if (iProtoLog == null) {
            throw new IllegalStateException("Trying to use ProtoLog before it is initialized in this process.");
        }
        if (iProtoLog.isEnabled(iProtoLogGroup, logLevel)) {
            sProtoLogInstance.log(logLevel, iProtoLogGroup, str, objArr);
        }
    }
}
