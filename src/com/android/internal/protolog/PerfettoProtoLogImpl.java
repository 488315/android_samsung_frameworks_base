package com.android.internal.protolog;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.internal.perfetto.protos.InternedDataOuterClass;
import android.internal.perfetto.protos.Protolog;
import android.internal.perfetto.protos.TracePacketOuterClass;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.text.TextUtils;
import android.tracing.Flags;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.IProtoLogClient;
import com.android.internal.protolog.IProtoLogConfigurationService;
import com.android.internal.protolog.ProtoLogDataSource;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.InvalidFormatStringException;
import com.android.internal.protolog.common.LogDataType;
import com.android.internal.protolog.common.LogLevel;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

/* loaded from: classes4.dex */
public abstract class PerfettoProtoLogImpl extends IProtoLogClient.Stub implements IProtoLog {
    private static final String LOG_TAG = "ProtoLog";
    public static final String NULL_STRING = "null";
    private static final int STACK_SIZE_TO_PROTO_LOG_ENTRY_CALL = 6;
    protected ExecutorService mBackgroundLoggingService;
    private final Lock mBackgroundServiceLock;
    private final ProtoLogCacheUpdater mCacheUpdater;
    private final Map<String, Integer> mCollectStackTraceGroupCounts;
    protected final IProtoLogConfigurationService mConfigurationService;
    protected final ProtoLogDataSource mDataSource;
    private final int[] mDefaultLogLevelCounts;
    protected final TreeMap<String, IProtoLogGroup> mLogGroups;
    private final Map<String, int[]> mLogLevelCounts;
    private boolean mLogcatReady;
    private final AtomicInteger mTracingInstances;

    static /* synthetic */ boolean lambda$onTracingInstanceStop$7(int i) {
        return i == 0;
    }

    protected abstract IProtoLogConfigurationService.RegisterClientArgs createConfigurationServiceRegisterClientArgs();

    @Deprecated
    abstract void dumpViewerConfig();

    abstract String getLogcatMessageString(Message message);

    protected PerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, ProtoLogCacheUpdater protoLogCacheUpdater, IProtoLogGroup[] iProtoLogGroupArr) throws ServiceManager.ServiceNotFoundException {
        this(protoLogDataSource, protoLogCacheUpdater, iProtoLogGroupArr, Flags.clientSideProtoLogging() ? IProtoLogConfigurationService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PROTOLOG_CONFIGURATION_SERVICE)) : null);
    }

    protected PerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, ProtoLogCacheUpdater protoLogCacheUpdater, IProtoLogGroup[] iProtoLogGroupArr, IProtoLogConfigurationService iProtoLogConfigurationService) {
        this.mTracingInstances = new AtomicInteger();
        this.mLogGroups = new TreeMap<>();
        this.mDefaultLogLevelCounts = new int[LogLevel.values().length];
        this.mLogLevelCounts = new ArrayMap();
        this.mCollectStackTraceGroupCounts = new ArrayMap();
        this.mBackgroundServiceLock = new ReentrantLock();
        this.mBackgroundLoggingService = Executors.newSingleThreadExecutor();
        this.mLogcatReady = false;
        this.mDataSource = protoLogDataSource;
        this.mCacheUpdater = protoLogCacheUpdater;
        this.mConfigurationService = iProtoLogConfigurationService;
        registerGroupsLocally(iProtoLogGroupArr);
    }

    public void enable() {
        Producer.init(InitArguments.DEFAULTS);
        if (Flags.clientSideProtoLogging()) {
            connectToConfigurationService();
        }
        this.mDataSource.registerOnStartCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda2(this));
        this.mDataSource.registerOnFlushCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda3(this));
        this.mDataSource.registerOnStopCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda4(this));
    }

    private void connectToConfigurationService() {
        Objects.requireNonNull(this.mConfigurationService, "A null ProtoLog Configuration Service was provided!");
        this.mBackgroundLoggingService.execute(new Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$connectToConfigurationService$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectToConfigurationService$0() {
        try {
            IProtoLogConfigurationService.RegisterClientArgs registerClientArgsCreateConfigurationServiceRegisterClientArgs = createConfigurationServiceRegisterClientArgs();
            registerClientArgsCreateConfigurationServiceRegisterClientArgs.groups = new String[this.mLogGroups.size()];
            registerClientArgsCreateConfigurationServiceRegisterClientArgs.groupsDefaultLogcatStatus = new boolean[this.mLogGroups.size()];
            List<IProtoLogGroup> list = this.mLogGroups.values().stream().toList();
            for (int i = 0; i < list.size(); i++) {
                IProtoLogGroup iProtoLogGroup = list.get(i);
                registerClientArgsCreateConfigurationServiceRegisterClientArgs.groups[i] = iProtoLogGroup.name();
                registerClientArgsCreateConfigurationServiceRegisterClientArgs.groupsDefaultLogcatStatus[i] = iProtoLogGroup.isLogToLogcat();
            }
            this.mConfigurationService.registerClient(this, registerClientArgsCreateConfigurationServiceRegisterClientArgs);
        } catch (RemoteException unused) {
            throw new RuntimeException("Failed to register ProtoLog client");
        }
    }

    public void disable() {
        this.mDataSource.unregisterOnStartCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda2(this));
        this.mDataSource.unregisterOnFlushCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda3(this));
        this.mDataSource.unregisterOnStopCallback(new PerfettoProtoLogImpl$$ExternalSyntheticLambda4(this));
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, long j, int i, Object[] objArr) throws Throwable {
        log(logLevel, iProtoLogGroup, new Message(j, i), objArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object... objArr) throws Throwable {
        try {
            log(logLevel, iProtoLogGroup, new Message(str), objArr);
        } catch (InvalidFormatStringException e) {
            Slog.e(LOG_TAG, "Invalid protolog string format", e);
            log(logLevel, iProtoLogGroup, new Message("INVALID MESSAGE"), new Object[0]);
        }
    }

    /* renamed from: com.android.internal.protolog.PerfettoProtoLogImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$protolog$common$LogLevel;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $SwitchMap$com$android$internal$protolog$common$LogLevel = iArr;
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.VERBOSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void passToLogcat(String str, LogLevel logLevel, String str2) {
        switch (AnonymousClass1.$SwitchMap$com$android$internal$protolog$common$LogLevel[logLevel.ordinal()]) {
            case 1:
                Slog.d(str, str2);
                break;
            case 2:
                Slog.v(str, str2);
                break;
            case 3:
                Slog.i(str, str2);
                break;
            case 4:
                Slog.w(str, str2);
                break;
            case 5:
                Slog.e(str, str2);
                break;
            case 6:
                Slog.wtf(str, str2);
                break;
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mTracingInstances.get() > 0;
    }

    @Override // com.android.internal.protolog.IProtoLogClient
    public void toggleLogcat(boolean z, String[] strArr) {
        ILogger iLogger = new ILogger() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda9
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                Log.d(PerfettoProtoLogImpl.LOG_TAG, str);
            }
        };
        if (z) {
            startLoggingToLogcat(strArr, iLogger);
        } else {
            stopLoggingToLogcat(strArr, iLogger);
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] strArr, ILogger iLogger) {
        return setTextLogging(true, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] strArr, ILogger iLogger) {
        return setTextLogging(false, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        int[] iArr = this.mLogLevelCounts.get(iProtoLogGroup.name());
        if (iArr != null || this.mDefaultLogLevelCounts[logLevel.ordinal()] <= 0) {
            return (iArr != null && iArr[logLevel.ordinal()] > 0) || iProtoLogGroup.isLogToLogcat();
        }
        return true;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public List<IProtoLogGroup> getRegisteredGroups() {
        return this.mLogGroups.values().stream().toList();
    }

    private void registerGroupsLocally(IProtoLogGroup[] iProtoLogGroupArr) {
        verifyNoCollisionsOrDuplicates(iProtoLogGroupArr);
        for (IProtoLogGroup iProtoLogGroup : iProtoLogGroupArr) {
            this.mLogGroups.put(iProtoLogGroup.name(), iProtoLogGroup);
        }
    }

    private void verifyNoCollisionsOrDuplicates(IProtoLogGroup[] iProtoLogGroupArr) {
        ArraySet arraySet = new ArraySet();
        for (IProtoLogGroup iProtoLogGroup : iProtoLogGroupArr) {
            if (arraySet.contains(Integer.valueOf(iProtoLogGroup.getId()))) {
                throw new RuntimeException("Group with same id (" + iProtoLogGroup.getId() + ") registered twice. Potential duplicate or hash id collision.");
            }
            arraySet.add(Integer.valueOf(iProtoLogGroup.getId()));
        }
    }

    protected void readyToLogToLogcat() {
        this.mLogcatReady = true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0043  */
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onShellCommand(ShellCommand shellCommand) {
        final PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        if (Flags.clientSideProtoLogging()) {
            outPrintWriter.println("Command deprecated. Please use 'cmd protolog_configuration' instead.");
            return -1;
        }
        String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            return unknownCommand(outPrintWriter);
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            String nextArg2 = shellCommand.getNextArg();
            if (nextArg2 == null) {
                break;
            }
            arrayList.add(nextArg2);
        }
        ILogger iLogger = new ILogger() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                PerfettoProtoLogImpl.logAndPrintln(outPrintWriter, str);
            }
        };
        char c = 0;
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        nextArg.hashCode();
        switch (nextArg.hashCode()) {
            case -1475003593:
                if (!nextArg.equals("enable-text")) {
                    c = 65535;
                    break;
                }
                break;
            case -1032071950:
                if (nextArg.equals("disable-text")) {
                    c = 1;
                    break;
                }
                break;
            case 3540994:
                if (nextArg.equals("stop")) {
                    c = 2;
                    break;
                }
                break;
            case 109757538:
                if (nextArg.equals("start")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return startLoggingToLogcat(strArr, iLogger);
            case 1:
                return stopLoggingToLogcat(strArr, iLogger);
            case 2:
            case 3:
                outPrintWriter.println("Command not supported. Please start and stop ProtoLog tracing with Perfetto.");
                return -1;
            default:
                return unknownCommand(outPrintWriter);
        }
    }

    private void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, Message message, Object[] objArr) throws Throwable {
        final PerfettoProtoLogImpl perfettoProtoLogImpl;
        final LogLevel logLevel2;
        final IProtoLogGroup iProtoLogGroup2;
        final Message message2;
        final Object[] objArr2;
        if (isProtoEnabled()) {
            final long jElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            final String strCollectStackTrace = this.mCollectStackTraceGroupCounts.getOrDefault(iProtoLogGroup.name(), 0).intValue() > 0 ? collectStackTrace() : null;
            try {
                this.mBackgroundServiceLock.lock();
                perfettoProtoLogImpl = this;
                logLevel2 = logLevel;
                iProtoLogGroup2 = iProtoLogGroup;
                message2 = message;
                objArr2 = objArr;
                try {
                    this.mBackgroundLoggingService.execute(new Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$log$3(logLevel2, iProtoLogGroup2, message2, objArr2, jElapsedRealtimeNanos, strCollectStackTrace);
                        }
                    });
                    perfettoProtoLogImpl.mBackgroundServiceLock.unlock();
                } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    perfettoProtoLogImpl.mBackgroundServiceLock.unlock();
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
                perfettoProtoLogImpl = this;
            }
        } else {
            perfettoProtoLogImpl = this;
            logLevel2 = logLevel;
            iProtoLogGroup2 = iProtoLogGroup;
            message2 = message;
            objArr2 = objArr;
        }
        if (iProtoLogGroup2.isLogToLogcat()) {
            perfettoProtoLogImpl.logToLogcat(iProtoLogGroup2.getTag(), logLevel2, message2, objArr2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracingFlush() {
        Log.d(LOG_TAG, "Executing onTracingFlush");
        try {
            this.mBackgroundServiceLock.lock();
            ExecutorService executorService = this.mBackgroundLoggingService;
            this.mBackgroundLoggingService = Executors.newSingleThreadExecutor();
            try {
                executorService.shutdown();
                if (!executorService.awaitTermination(10L, TimeUnit.SECONDS)) {
                    Log.e(LOG_TAG, "ProtoLog background tracing service didn't finish gracefully.");
                }
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Failed to wait for tracing to finish", e);
            }
            if (!Flags.clientSideProtoLogging()) {
                dumpViewerConfig();
            }
            Log.d(LOG_TAG, "Finished onTracingFlush");
        } finally {
            this.mBackgroundServiceLock.unlock();
        }
    }

    private void logToLogcat(String str, LogLevel logLevel, Message message, Object[] objArr) {
        if (!this.mLogcatReady) {
            Log.w(LOG_TAG, "Trying to log a protolog message with hash " + message.getMessageHash() + " to logcat before the service is ready to accept such requests.");
            return;
        }
        logToLogcat(str, logLevel, getLogcatMessageString(message), objArr);
    }

    private void logToLogcat(String str, LogLevel logLevel, String str2, Object[] objArr) {
        if (objArr != null) {
            try {
                str2 = TextUtils.formatSimple(str2, objArr);
            } catch (IllegalArgumentException unused) {
                str2 = "FORMAT_ERROR \"" + str2 + "\", args=(" + String.join(", ", Arrays.stream(objArr).map(new NoViewerConfigProtoLogImpl$$ExternalSyntheticLambda0()).toList()) + NavigationBarInflaterView.KEY_CODE_END;
            }
        }
        passToLogcat(str, logLevel, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: logToProto, reason: merged with bridge method [inline-methods] */
    public void lambda$log$3(final LogLevel logLevel, final IProtoLogGroup iProtoLogGroup, final Message message, final Object[] objArr, final long j, final String str) {
        this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda6
            @Override // android.tracing.perfetto.TraceFunction
            public final void trace(TracingContext tracingContext) {
                this.f$0.lambda$logToProto$6(iProtoLogGroup, logLevel, objArr, message, str, j, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$logToProto$6(IProtoLogGroup iProtoLogGroup, LogLevel logLevel, Object[] objArr, Message message, String str, long j, TracingContext tracingContext) {
        boolean z;
        int i;
        int iInternStringArg;
        Object[] objArr2 = objArr;
        ProtoLogDataSource.TlsState tlsState = (ProtoLogDataSource.TlsState) tracingContext.getCustomTlsState();
        if (logLevel.ordinal() < tlsState.getLogFromLevel(iProtoLogGroup.name()).ordinal()) {
            return;
        }
        if (objArr2 != null) {
            int i2 = 0;
            for (Object obj : objArr2) {
                if (LogDataType.bitmaskToLogDataType(message.getMessageMask(), i2) == 0) {
                    if (obj == null) {
                        internStringArg(tracingContext, NULL_STRING);
                    } else {
                        internStringArg(tracingContext, obj.toString());
                    }
                }
                i2++;
            }
        }
        int iInternStacktraceString = tlsState.getShouldCollectStacktrace(iProtoLogGroup.name()) ? internStacktraceString(tracingContext, str) : 0;
        long jLongValue = message.mMessageHash != null ? message.mMessageHash.longValue() : 0L;
        if (message.mMessageString != null) {
            jLongValue = internProtoMessage(tracingContext, logLevel, iProtoLogGroup, message.mMessageString);
            z = true;
        } else {
            z = false;
        }
        final ProtoOutputStream protoOutputStreamNewTracePacket = tracingContext.newTracePacket();
        int i3 = iInternStacktraceString;
        protoOutputStreamNewTracePacket.write(TracePacketOuterClass.TracePacket.TIMESTAMP, j);
        long jStart = protoOutputStreamNewTracePacket.start(1146756268136L);
        protoOutputStreamNewTracePacket.write(1125281431553L, jLongValue);
        if (objArr2 != null) {
            LongArray longArray = new LongArray();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int length = objArr2.length;
            int i4 = 0;
            int i5 = 0;
            boolean z2 = z;
            while (i4 < length) {
                Object obj2 = objArr2[i4];
                int iBitmaskToLogDataType = LogDataType.bitmaskToLogDataType(message.getMessageMask(), i5);
                if (iBitmaskToLogDataType == 0) {
                    i = i5;
                    if (obj2 == null) {
                        iInternStringArg = internStringArg(tracingContext, NULL_STRING);
                    } else {
                        iInternStringArg = internStringArg(tracingContext, obj2.toString());
                    }
                    protoOutputStreamNewTracePacket.write(Protolog.ProtoLogMessage.STR_PARAM_IIDS, iInternStringArg);
                    z2 = true;
                } else if (iBitmaskToLogDataType != 1) {
                    if (iBitmaskToLogDataType != 2) {
                        if (iBitmaskToLogDataType == 3) {
                            if (obj2 == null) {
                                try {
                                    arrayList2.add(false);
                                } catch (ClassCastException e) {
                                    e = e;
                                    i = i5;
                                    Slog.e(LOG_TAG, "Invalid ProtoLog paramsMask", e);
                                    i5 = i + 1;
                                    i4++;
                                    objArr2 = objArr;
                                }
                            } else {
                                Boolean bool = (Boolean) obj2;
                                bool.booleanValue();
                                arrayList2.add(bool);
                            }
                        }
                    } else if (obj2 == null) {
                        arrayList.add(Double.valueOf(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN));
                    } else {
                        arrayList.add(Double.valueOf(((Number) obj2).doubleValue()));
                    }
                    i = i5;
                } else if (obj2 == null) {
                    i = i5;
                    try {
                        longArray.add(0L);
                    } catch (ClassCastException e2) {
                        e = e2;
                        Slog.e(LOG_TAG, "Invalid ProtoLog paramsMask", e);
                        i5 = i + 1;
                        i4++;
                        objArr2 = objArr;
                    }
                } else {
                    i = i5;
                    longArray.add(((Number) obj2).longValue());
                }
                i5 = i + 1;
                i4++;
                objArr2 = objArr;
            }
            for (int i6 = 0; i6 < longArray.size(); i6++) {
                protoOutputStreamNewTracePacket.write(Protolog.ProtoLogMessage.SINT64_PARAMS, longArray.get(i6));
            }
            arrayList.forEach(new Consumer() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj3) {
                    protoOutputStreamNewTracePacket.write(Protolog.ProtoLogMessage.DOUBLE_PARAMS, ((Double) obj3).doubleValue());
                }
            });
            arrayList2.forEach(new Consumer() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj3) {
                    protoOutputStreamNewTracePacket.write(Protolog.ProtoLogMessage.BOOLEAN_PARAMS, ((Boolean) obj3).booleanValue() ? 1 : 0);
                }
            });
            z = z2;
        }
        if (tlsState.getShouldCollectStacktrace(iProtoLogGroup.name())) {
            protoOutputStreamNewTracePacket.write(1155346202630L, i3);
        }
        protoOutputStreamNewTracePacket.end(jStart);
        if (z) {
            protoOutputStreamNewTracePacket.write(1155346202637L, 2);
        }
    }

    private long internProtoMessage(TracingContext<ProtoLogDataSource.Instance, ProtoLogDataSource.TlsState, ProtoLogDataSource.IncrementalState> tracingContext, LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str) {
        ProtoLogDataSource.IncrementalState incrementalState = tracingContext.getIncrementalState();
        if (!incrementalState.clearReported) {
            tracingContext.newTracePacket().write(1155346202637L, 1);
            incrementalState.clearReported = true;
        }
        if (!incrementalState.protologGroupInterningSet.contains(Integer.valueOf(iProtoLogGroup.getId()))) {
            incrementalState.protologGroupInterningSet.add(Integer.valueOf(iProtoLogGroup.getId()));
            ProtoOutputStream protoOutputStreamNewTracePacket = tracingContext.newTracePacket();
            long jStart = protoOutputStreamNewTracePacket.start(1146756268137L);
            long jStart2 = protoOutputStreamNewTracePacket.start(2246267895810L);
            protoOutputStreamNewTracePacket.write(1155346202625L, iProtoLogGroup.getId());
            protoOutputStreamNewTracePacket.write(1138166333442L, iProtoLogGroup.name());
            protoOutputStreamNewTracePacket.write(1138166333443L, iProtoLogGroup.getTag());
            protoOutputStreamNewTracePacket.end(jStart2);
            protoOutputStreamNewTracePacket.end(jStart);
        }
        Long lHash = hash(logLevel, iProtoLogGroup.name(), str);
        if (!incrementalState.protologMessageInterningSet.contains(lHash)) {
            incrementalState.protologMessageInterningSet.add(lHash);
            ProtoOutputStream protoOutputStreamNewTracePacket2 = tracingContext.newTracePacket();
            protoOutputStreamNewTracePacket2.write(1155346202637L, 2);
            long jStart3 = protoOutputStreamNewTracePacket2.start(1146756268137L);
            long jStart4 = protoOutputStreamNewTracePacket2.start(2246267895809L);
            protoOutputStreamNewTracePacket2.write(1125281431553L, lHash.longValue());
            protoOutputStreamNewTracePacket2.write(1138166333442L, str);
            protoOutputStreamNewTracePacket2.write(1159641169923L, logLevel.id);
            protoOutputStreamNewTracePacket2.write(1155346202628L, iProtoLogGroup.getId());
            protoOutputStreamNewTracePacket2.end(jStart4);
            protoOutputStreamNewTracePacket2.end(jStart3);
        }
        return lHash.longValue();
    }

    private Long hash(LogLevel logLevel, String str, String str2) {
        return Long.valueOf(UUID.nameUUIDFromBytes((str2 + logLevel + str).getBytes()).getMostSignificantBits());
    }

    private String collectStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        for (int i = 6; i < stackTrace.length; i++) {
            try {
                printWriter.println("\tat " + stackTrace[i]);
            } catch (Throwable th) {
                try {
                    printWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        printWriter.close();
        return stringWriter.toString();
    }

    private int internStacktraceString(TracingContext<ProtoLogDataSource.Instance, ProtoLogDataSource.TlsState, ProtoLogDataSource.IncrementalState> tracingContext, String str) {
        return internString(tracingContext, tracingContext.getIncrementalState().stacktraceInterningMap, InternedDataOuterClass.InternedData.PROTOLOG_STACKTRACE, str);
    }

    private int internStringArg(TracingContext<ProtoLogDataSource.Instance, ProtoLogDataSource.TlsState, ProtoLogDataSource.IncrementalState> tracingContext, String str) {
        return internString(tracingContext, tracingContext.getIncrementalState().argumentInterningMap, 2246267895844L, str);
    }

    private int internString(TracingContext<ProtoLogDataSource.Instance, ProtoLogDataSource.TlsState, ProtoLogDataSource.IncrementalState> tracingContext, Map<String, Integer> map, long j, String str) {
        ProtoLogDataSource.IncrementalState incrementalState = tracingContext.getIncrementalState();
        if (!incrementalState.clearReported) {
            tracingContext.newTracePacket().write(1155346202637L, 1);
            incrementalState.clearReported = true;
        }
        if (!map.containsKey(str)) {
            int size = map.size() + 1;
            map.put(str, Integer.valueOf(size));
            ProtoOutputStream protoOutputStreamNewTracePacket = tracingContext.newTracePacket();
            long jStart = protoOutputStreamNewTracePacket.start(1146756268044L);
            long jStart2 = protoOutputStreamNewTracePacket.start(j);
            protoOutputStreamNewTracePacket.write(1116691496961L, size);
            protoOutputStreamNewTracePacket.write(1151051235330L, str.getBytes());
            protoOutputStreamNewTracePacket.end(jStart2);
            protoOutputStreamNewTracePacket.end(jStart);
        }
        return map.get(str).intValue();
    }

    protected boolean validateGroups(ILogger iLogger, String[] strArr) {
        for (String str : strArr) {
            if (this.mLogGroups.get(str) == null) {
                iLogger.log("No IProtoLogGroup named " + str);
                return false;
            }
        }
        return true;
    }

    private int setTextLogging(boolean z, ILogger iLogger, String... strArr) {
        if (!validateGroups(iLogger, strArr)) {
            return -1;
        }
        for (String str : strArr) {
            IProtoLogGroup iProtoLogGroup = this.mLogGroups.get(str);
            if (iProtoLogGroup != null) {
                iProtoLogGroup.setLogToLogcat(z);
            } else {
                throw new RuntimeException("No IProtoLogGroup named " + str);
            }
        }
        this.mCacheUpdater.update(this);
        return 0;
    }

    private int unknownCommand(PrintWriter printWriter) {
        printWriter.println("Unknown command");
        printWriter.println("Window manager logging options:");
        printWriter.println("  enable-text [group...]: Enable logcat logging for given groups");
        printWriter.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTracingInstanceStart(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        Log.d(LOG_TAG, "Executing onTracingInstanceStart");
        for (int iOrdinal = protoLogConfig.getDefaultGroupConfig().logFrom.ordinal(); iOrdinal < LogLevel.values().length; iOrdinal++) {
            int[] iArr = this.mDefaultLogLevelCounts;
            iArr[iOrdinal] = iArr[iOrdinal] + 1;
        }
        for (String str : protoLogConfig.getGroupTagsWithOverriddenConfigs()) {
            this.mLogLevelCounts.putIfAbsent(str, new int[LogLevel.values().length]);
            int[] iArr2 = this.mLogLevelCounts.get(str);
            for (int iOrdinal2 = protoLogConfig.getConfigFor(str).logFrom.ordinal(); iOrdinal2 < LogLevel.values().length; iOrdinal2++) {
                iArr2[iOrdinal2] = iArr2[iOrdinal2] + 1;
            }
            if (protoLogConfig.getConfigFor(str).collectStackTrace) {
                Map<String, Integer> map = this.mCollectStackTraceGroupCounts;
                map.put(str, Integer.valueOf(map.getOrDefault(str, 0).intValue() + 1));
            }
            if (protoLogConfig.getConfigFor(str).collectStackTrace) {
                Map<String, Integer> map2 = this.mCollectStackTraceGroupCounts;
                map2.put(str, Integer.valueOf(map2.getOrDefault(str, 0).intValue() + 1));
            }
        }
        this.mCacheUpdater.update(this);
        this.mTracingInstances.incrementAndGet();
        Log.d(LOG_TAG, "Finished onTracingInstanceStart");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTracingInstanceStop(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        Log.d(LOG_TAG, "Executing onTracingInstanceStop");
        this.mTracingInstances.decrementAndGet();
        for (int iOrdinal = protoLogConfig.getDefaultGroupConfig().logFrom.ordinal(); iOrdinal < LogLevel.values().length; iOrdinal++) {
            this.mDefaultLogLevelCounts[iOrdinal] = r0[iOrdinal] - 1;
        }
        for (String str : protoLogConfig.getGroupTagsWithOverriddenConfigs()) {
            int[] iArr = this.mLogLevelCounts.get(str);
            for (int iOrdinal2 = protoLogConfig.getConfigFor(str).logFrom.ordinal(); iOrdinal2 < LogLevel.values().length; iOrdinal2++) {
                iArr[iOrdinal2] = iArr[iOrdinal2] - 1;
            }
            if (Arrays.stream(iArr).allMatch(new IntPredicate() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.IntPredicate
                public final boolean test(int i2) {
                    return PerfettoProtoLogImpl.lambda$onTracingInstanceStop$7(i2);
                }
            })) {
                this.mLogLevelCounts.remove(str);
            }
            if (protoLogConfig.getConfigFor(str).collectStackTrace) {
                this.mCollectStackTraceGroupCounts.put(str, Integer.valueOf(r1.get(str).intValue() - 1));
                if (this.mCollectStackTraceGroupCounts.get(str).intValue() == 0) {
                    this.mCollectStackTraceGroupCounts.remove(str);
                }
            }
        }
        this.mCacheUpdater.update(this);
        Log.d(LOG_TAG, "Finished onTracingInstanceStop");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logAndPrintln(PrintWriter printWriter, String str) {
        Slog.i(LOG_TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }

    protected static class Message {
        private final Long mMessageHash;
        private final int mMessageMask;
        private final String mMessageString;

        private Message(long j, int i) {
            this.mMessageHash = Long.valueOf(j);
            this.mMessageMask = i;
            this.mMessageString = null;
        }

        private Message(String str) throws InvalidFormatStringException {
            this.mMessageHash = null;
            this.mMessageMask = LogDataType.logDataTypesToBitMask(LogDataType.parseFormatString(str));
            this.mMessageString = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getMessageMask() {
            return this.mMessageMask;
        }

        protected Long getMessageHash() {
            return this.mMessageHash;
        }

        protected String getMessage() {
            return this.mMessageString;
        }

        protected String getMessage(ProtoLogViewerConfigReader protoLogViewerConfigReader) {
            String str = this.mMessageString;
            if (str != null) {
                return str;
            }
            Long l = this.mMessageHash;
            if (l != null) {
                return protoLogViewerConfigReader.getViewerString(l.longValue());
            }
            throw new RuntimeException("Both mMessageString and mMessageHash should never be null");
        }
    }

    public static void waitForInitialization() throws ExecutionException, InterruptedException {
        IProtoLog singleInstance = ProtoLog.getSingleInstance();
        if (singleInstance instanceof PerfettoProtoLogImpl) {
            try {
                ((PerfettoProtoLogImpl) singleInstance).mBackgroundLoggingService.submit(new Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.i(PerfettoProtoLogImpl.LOG_TAG, "Complete initialization");
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.e(LOG_TAG, "Failed to wait for tracing service", e);
            }
        }
    }
}
