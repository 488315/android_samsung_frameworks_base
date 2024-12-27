package com.android.internal.protolog;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.internal.perfetto.protos.InternedDataOuterClass;
import android.internal.perfetto.protos.Protolog;
import android.internal.perfetto.protos.TracePacketOuterClass;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.text.TextUtils;
import android.tracing.perfetto.DataSourceParams;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongArray;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogDataType;
import com.android.internal.protolog.common.LogLevel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class PerfettoProtoLogImpl implements IProtoLog {
    private static final String LOG_TAG = "ProtoLog";
    private static final int STACK_SIZE_TO_PROTO_LOG_ENTRY_CALL = 12;
    private final ExecutorService mBackgroundLoggingService;
    private final Runnable mCacheUpdater;
    private final ProtoLogDataSource mDataSource;
    private final Map<LogLevel, Integer> mDefaultLogLevelCounts;
    private final TreeMap<String, IProtoLogGroup> mLogGroups;
    private final Map<IProtoLogGroup, Map<LogLevel, Integer>> mLogLevelCounts;
    private final AtomicInteger mTracingInstances;
    private final ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;
    private final ProtoLogViewerConfigReader mViewerConfigReader;

    public PerfettoProtoLogImpl(
            final String viewerConfigFilePath,
            TreeMap<String, IProtoLogGroup> logGroups,
            Runnable cacheUpdater) {
        this(
                new ViewerConfigInputStreamProvider() { // from class:
                                                        // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda8
                    @Override // com.android.internal.protolog.ViewerConfigInputStreamProvider
                    public final ProtoInputStream getInputStream() {
                        return PerfettoProtoLogImpl.lambda$new$0(viewerConfigFilePath);
                    }
                },
                logGroups,
                cacheUpdater);
    }

    static /* synthetic */ ProtoInputStream lambda$new$0(String viewerConfigFilePath) {
        try {
            return new ProtoInputStream(new FileInputStream(viewerConfigFilePath));
        } catch (FileNotFoundException e) {
            Slog.w(LOG_TAG, "Failed to load viewer config file " + viewerConfigFilePath, e);
            return null;
        }
    }

    public PerfettoProtoLogImpl(
            ViewerConfigInputStreamProvider viewerConfigInputStreamProvider,
            TreeMap<String, IProtoLogGroup> logGroups,
            Runnable cacheUpdater) {
        this(
                viewerConfigInputStreamProvider,
                new ProtoLogViewerConfigReader(viewerConfigInputStreamProvider),
                logGroups,
                cacheUpdater);
    }

    public PerfettoProtoLogImpl(
            ViewerConfigInputStreamProvider viewerConfigInputStreamProvider,
            ProtoLogViewerConfigReader viewerConfigReader,
            TreeMap<String, IProtoLogGroup> logGroups,
            Runnable cacheUpdater) {
        this.mTracingInstances = new AtomicInteger();
        this.mDataSource =
                new ProtoLogDataSource(
                        new Consumer() { // from class:
                                         // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PerfettoProtoLogImpl.this.onTracingInstanceStart(
                                        (ProtoLogDataSource.ProtoLogConfig) obj);
                            }
                        },
                        new Runnable() { // from class:
                                         // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PerfettoProtoLogImpl.this.dumpTransitionTraceConfig();
                            }
                        },
                        new Consumer() { // from class:
                                         // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PerfettoProtoLogImpl.this.onTracingInstanceStop(
                                        (ProtoLogDataSource.ProtoLogConfig) obj);
                            }
                        });
        this.mDefaultLogLevelCounts = new ArrayMap();
        this.mLogLevelCounts = new ArrayMap();
        this.mBackgroundLoggingService = Executors.newSingleThreadExecutor();
        Producer.init(InitArguments.DEFAULTS);
        DataSourceParams params =
                new DataSourceParams.Builder().setBufferExhaustedPolicy(0).build();
        this.mDataSource.register(params);
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
        this.mViewerConfigReader = viewerConfigReader;
        this.mLogGroups = logGroups;
        this.mCacheUpdater = cacheUpdater;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(
            final LogLevel level,
            final IProtoLogGroup group,
            final long messageHash,
            final int paramsMask,
            String messageString,
            final Object[] args) {
        Trace.traceBegin(32L, "log");
        final long tsNanos = SystemClock.elapsedRealtimeNanos();
        try {
            this.mBackgroundLoggingService.submit(
                    new Runnable() { // from class:
                                     // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            PerfettoProtoLogImpl.this.lambda$log$1(
                                    level, group, messageHash, paramsMask, args, tsNanos);
                        }
                    });
            if (group.isLogToLogcat()) {
                logToLogcat(group.getTag(), level, messageHash, messageString, args);
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$log$1(
            LogLevel level,
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            Object[] args,
            long tsNanos) {
        logToProto(level, group.name(), messageHash, paramsMask, args, tsNanos);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpTransitionTraceConfig() {
        final ProtoInputStream pis = this.mViewerConfigInputStreamProvider.getInputStream();
        if (pis == null) {
            Slog.w(LOG_TAG, "Failed to get viewer input stream.");
        } else {
            this.mDataSource.trace(
                    new TraceFunction() { // from class:
                                          // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda6
                        @Override // android.tracing.perfetto.TraceFunction
                        public final void trace(TracingContext tracingContext) {
                            PerfettoProtoLogImpl.lambda$dumpTransitionTraceConfig$2(
                                    ProtoInputStream.this, tracingContext);
                        }
                    });
        }
    }

    static /* synthetic */ void lambda$dumpTransitionTraceConfig$2(
            ProtoInputStream pis, TracingContext ctx) {
        try {
            ProtoOutputStream os = ctx.newTracePacket();
            os.write(
                    TracePacketOuterClass.TracePacket.TIMESTAMP,
                    SystemClock.elapsedRealtimeNanos());
            long outProtologViewerConfigToken = os.start(1146756268137L);
            while (pis.nextField() != -1) {
                if (pis.getFieldNumber() == 1) {
                    writeViewerConfigMessage(pis, os);
                }
                if (pis.getFieldNumber() == 2) {
                    writeViewerConfigGroup(pis, os);
                }
            }
            os.end(outProtologViewerConfigToken);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to read ProtoLog viewer config to dump on tracing end", e);
        }
    }

    private static void writeViewerConfigGroup(ProtoInputStream pis, ProtoOutputStream os)
            throws IOException {
        long inGroupToken = pis.start(2246267895810L);
        long outGroupToken = os.start(2246267895810L);
        while (pis.nextField() != -1) {
            switch (pis.getFieldNumber()) {
                case 1:
                    int id = pis.readInt(1155346202625L);
                    os.write(1155346202625L, id);
                    break;
                case 2:
                    String name = pis.readString(1138166333442L);
                    os.write(1138166333442L, name);
                    break;
                case 3:
                    String tag = pis.readString(1138166333443L);
                    os.write(1138166333443L, tag);
                    break;
                default:
                    throw new RuntimeException("Unexpected field id " + pis.getFieldNumber());
            }
        }
        pis.end(inGroupToken);
        os.end(outGroupToken);
    }

    private static void writeViewerConfigMessage(ProtoInputStream pis, ProtoOutputStream os)
            throws IOException {
        long inMessageToken = pis.start(2246267895809L);
        long outMessagesToken = os.start(2246267895809L);
        while (pis.nextField() != -1) {
            switch (pis.getFieldNumber()) {
                case 1:
                    os.write(1125281431553L, pis.readLong(1125281431553L));
                    break;
                case 2:
                    os.write(1138166333442L, pis.readString(1138166333442L));
                    break;
                case 3:
                    os.write(1159641169923L, pis.readInt(1159641169923L));
                    break;
                case 4:
                    os.write(1155346202628L, pis.readInt(1155346202628L));
                    break;
                default:
                    throw new RuntimeException("Unexpected field id " + pis.getFieldNumber());
            }
        }
        pis.end(inMessageToken);
        os.end(outMessagesToken);
    }

    private void logToLogcat(
            String tag, LogLevel level, long messageHash, String messageString, Object[] args) {
        Trace.traceBegin(32L, "logToLogcat");
        try {
            doLogToLogcat(tag, level, messageHash, messageString, args);
        } finally {
            Trace.traceEnd(32L);
        }
    }

    private void doLogToLogcat(
            String tag, LogLevel level, long messageHash, String messageString, Object[] args) {
        String message = null;
        if (messageString == null) {
            messageString = this.mViewerConfigReader.getViewerString(messageHash);
        }
        if (messageString != null) {
            if (args != null) {
                try {
                    message = TextUtils.formatSimple(messageString, args);
                } catch (Exception ex) {
                    Slog.w(LOG_TAG, "Invalid ProtoLog format string.", ex);
                }
            } else {
                message = messageString;
            }
        }
        if (message == null) {
            StringBuilder builder =
                    new StringBuilder(
                            "UNKNOWN MESSAGE ("
                                    + messageHash
                                    + NavigationBarInflaterView.KEY_CODE_END);
            for (Object o : args) {
                builder.append(" ").append(o);
            }
            message = builder.toString();
        }
        passToLogcat(tag, level, message);
    }

    public void passToLogcat(String tag, LogLevel level, String message) {
        switch (level) {
            case DEBUG:
                Slog.d(tag, message);
                break;
            case VERBOSE:
                Slog.v(tag, message);
                break;
            case INFO:
                Slog.i(tag, message);
                break;
            case WARN:
                Slog.w(tag, message);
                break;
            case ERROR:
                Slog.e(tag, message);
                break;
            case WTF:
                Slog.wtf(tag, message);
                break;
        }
    }

    private void logToProto(
            LogLevel level,
            String groupName,
            long messageHash,
            int paramsMask,
            Object[] args,
            long tsNanos) {
        if (!isProtoEnabled()) {
            return;
        }
        Trace.traceBegin(32L, "logToProto");
        try {
            doLogToProto(level, groupName, messageHash, paramsMask, args, tsNanos);
        } finally {
            Trace.traceEnd(32L);
        }
    }

    private void doLogToProto(
            final LogLevel level,
            final String groupName,
            final long messageHash,
            final int paramsMask,
            final Object[] args,
            final long tsNanos) {
        this.mDataSource.trace(
                new TraceFunction() { // from class:
                                      // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda7
                    @Override // android.tracing.perfetto.TraceFunction
                    public final void trace(TracingContext tracingContext) {
                        PerfettoProtoLogImpl.this.lambda$doLogToProto$5(
                                groupName,
                                level,
                                args,
                                paramsMask,
                                tsNanos,
                                messageHash,
                                tracingContext);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doLogToProto$5(
            String groupName,
            LogLevel level,
            Object[] args,
            int paramsMask,
            long tsNanos,
            long messageHash,
            TracingContext ctx) {
        long token;
        ArrayList<Boolean> booleanParams;
        int i;
        LongArray longParams;
        ArrayList<Double> doubleParams;
        long token2;
        Object[] objArr = args;
        int i2 = paramsMask;
        ProtoLogDataSource.TlsState tlsState =
                (ProtoLogDataSource.TlsState) ctx.getCustomTlsState();
        LogLevel logFrom = tlsState.getLogFromLevel(groupName);
        if (level.ordinal() < logFrom.ordinal()) {
            return;
        }
        if (objArr != null) {
            int argIndex = 0;
            for (Object o : objArr) {
                int type = LogDataType.bitmaskToLogDataType(i2, argIndex);
                if (type == 0) {
                    internStringArg(ctx, o.toString());
                }
                argIndex++;
            }
        }
        int internedStacktrace = 0;
        if (tlsState.getShouldCollectStacktrace(groupName)) {
            String stacktrace = collectStackTrace();
            internedStacktrace = internStacktraceString(ctx, stacktrace);
        }
        final ProtoOutputStream os = ctx.newTracePacket();
        os.write(TracePacketOuterClass.TracePacket.TIMESTAMP, tsNanos);
        long token3 = os.start(1146756268136L);
        os.write(1125281431553L, messageHash);
        boolean needsIncrementalState = false;
        if (objArr != null) {
            LongArray longParams2 = new LongArray();
            ArrayList<Double> doubleParams2 = new ArrayList<>();
            ArrayList<Boolean> booleanParams2 = new ArrayList<>();
            int length = objArr.length;
            int argIndex2 = 0;
            boolean needsIncrementalState2 = false;
            int i3 = 0;
            while (i3 < length) {
                Object o2 = objArr[i3];
                int type2 = LogDataType.bitmaskToLogDataType(i2, argIndex2);
                switch (type2) {
                    case 0:
                        i = length;
                        longParams = longParams2;
                        doubleParams = doubleParams2;
                        booleanParams = booleanParams2;
                        token2 = token3;
                        int internedStringId = internStringArg(ctx, o2.toString());
                        os.write(Protolog.ProtoLogMessage.STR_PARAM_IIDS, internedStringId);
                        needsIncrementalState2 = true;
                        continue;
                    case 1:
                        doubleParams = doubleParams2;
                        booleanParams = booleanParams2;
                        try {
                            token2 = token3;
                            try {
                                i = length;
                                longParams = longParams2;
                                try {
                                    longParams.add(((Number) o2).longValue());
                                    continue;
                                } catch (ClassCastException e) {
                                    ex = e;
                                    break;
                                }
                            } catch (ClassCastException e2) {
                                ex = e2;
                                i = length;
                                longParams = longParams2;
                                break;
                            }
                        } catch (ClassCastException e3) {
                            ex = e3;
                            i = length;
                            token2 = token3;
                            longParams = longParams2;
                            break;
                        }
                    case 2:
                        booleanParams = booleanParams2;
                        try {
                            doubleParams = doubleParams2;
                            try {
                                doubleParams.add(Double.valueOf(((Number) o2).doubleValue()));
                                i = length;
                                token2 = token3;
                                longParams = longParams2;
                                continue;
                            } catch (ClassCastException e4) {
                                ex = e4;
                                i = length;
                                token2 = token3;
                                longParams = longParams2;
                                break;
                            }
                        } catch (ClassCastException e5) {
                            ex = e5;
                            doubleParams = doubleParams2;
                            i = length;
                            token2 = token3;
                            longParams = longParams2;
                            break;
                        }
                    case 3:
                        try {
                            booleanParams = booleanParams2;
                            try {
                                booleanParams.add(Boolean.valueOf(((Boolean) o2).booleanValue()));
                                i = length;
                                longParams = longParams2;
                                doubleParams = doubleParams2;
                                token2 = token3;
                                continue;
                            } catch (ClassCastException e6) {
                                ex = e6;
                                i = length;
                                longParams = longParams2;
                                doubleParams = doubleParams2;
                                token2 = token3;
                                break;
                            }
                        } catch (ClassCastException e7) {
                            ex = e7;
                            booleanParams = booleanParams2;
                            i = length;
                            longParams = longParams2;
                            doubleParams = doubleParams2;
                            token2 = token3;
                            break;
                        }
                    default:
                        i = length;
                        longParams = longParams2;
                        doubleParams = doubleParams2;
                        booleanParams = booleanParams2;
                        token2 = token3;
                        continue;
                }
                Slog.e(LOG_TAG, "Invalid ProtoLog paramsMask", ex);
                argIndex2++;
                i3++;
                longParams2 = longParams;
                token3 = token2;
                length = i;
                booleanParams2 = booleanParams;
                doubleParams2 = doubleParams;
                objArr = args;
                i2 = paramsMask;
            }
            LongArray longParams3 = longParams2;
            ArrayList<Double> doubleParams3 = doubleParams2;
            ArrayList<Boolean> booleanParams3 = booleanParams2;
            token = token3;
            for (int i4 = 0; i4 < longParams3.size(); i4++) {
                os.write(Protolog.ProtoLogMessage.SINT64_PARAMS, longParams3.get(i4));
            }
            doubleParams3.forEach(
                    new Consumer() { // from class:
                                     // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ProtoOutputStream.this.write(
                                    Protolog.ProtoLogMessage.DOUBLE_PARAMS,
                                    ((Double) obj).doubleValue());
                        }
                    });
            booleanParams3.forEach(
                    new Consumer() { // from class:
                                     // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ProtoOutputStream.this.write(
                                    Protolog.ProtoLogMessage.BOOLEAN_PARAMS,
                                    r4.booleanValue() ? 1 : 0);
                        }
                    });
            needsIncrementalState = needsIncrementalState2;
        } else {
            token = token3;
        }
        if (tlsState.getShouldCollectStacktrace(groupName)) {
            os.write(1155346202630L, internedStacktrace);
        }
        os.end(token);
        if (needsIncrementalState) {
            os.write(1155346202637L, 2);
        }
    }

    private String collectStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        for (int i = 12; i < stackTrace.length; i++) {
            try {
                pw.println("\tat " + stackTrace[i]);
            } catch (Throwable th) {
                try {
                    pw.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        pw.close();
        return sw.toString();
    }

    private int internStacktraceString(
            TracingContext<
                            ProtoLogDataSource.Instance,
                            ProtoLogDataSource.TlsState,
                            ProtoLogDataSource.IncrementalState>
                    ctx,
            String stacktrace) {
        ProtoLogDataSource.IncrementalState incrementalState = ctx.getIncrementalState();
        return internString(
                ctx,
                incrementalState.stacktraceInterningMap,
                InternedDataOuterClass.InternedData.PROTOLOG_STACKTRACE,
                stacktrace);
    }

    private int internStringArg(
            TracingContext<
                            ProtoLogDataSource.Instance,
                            ProtoLogDataSource.TlsState,
                            ProtoLogDataSource.IncrementalState>
                    ctx,
            String string) {
        ProtoLogDataSource.IncrementalState incrementalState = ctx.getIncrementalState();
        return internString(ctx, incrementalState.argumentInterningMap, 2246267895844L, string);
    }

    private int internString(
            TracingContext<
                            ProtoLogDataSource.Instance,
                            ProtoLogDataSource.TlsState,
                            ProtoLogDataSource.IncrementalState>
                    ctx,
            Map<String, Integer> internMap,
            long fieldId,
            String string) {
        ProtoLogDataSource.IncrementalState incrementalState = ctx.getIncrementalState();
        if (!incrementalState.clearReported) {
            ctx.newTracePacket().write(1155346202637L, 1);
            incrementalState.clearReported = true;
        }
        if (!internMap.containsKey(string)) {
            int internedIndex = internMap.size() + 1;
            internMap.put(string, Integer.valueOf(internedIndex));
            ProtoOutputStream os = ctx.newTracePacket();
            long token = os.start(1146756268044L);
            long innerToken = os.start(fieldId);
            os.write(1116691496961L, internedIndex);
            os.write(1151051235330L, string.getBytes());
            os.end(innerToken);
            os.end(token);
        }
        return internMap.get(string).intValue();
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mTracingInstances.get() > 0;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] groups, ILogger logger) {
        this.mViewerConfigReader.loadViewerConfig(logger);
        return setTextLogging(true, logger, groups);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] groups, ILogger logger) {
        this.mViewerConfigReader.unloadViewerConfig();
        return setTextLogging(false, logger, groups);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup group, LogLevel level) {
        return group.isLogToLogcat() || getLogFromLevel(group).ordinal() <= level.ordinal();
    }

    private LogLevel getLogFromLevel(IProtoLogGroup group) {
        int i = 0;
        if (this.mLogLevelCounts.containsKey(group)) {
            LogLevel[] values = LogLevel.values();
            int length = values.length;
            while (i < length) {
                LogLevel logLevel = values[i];
                if (this.mLogLevelCounts.get(group).getOrDefault(logLevel, 0).intValue() <= 0) {
                    i++;
                } else {
                    return logLevel;
                }
            }
        } else {
            LogLevel[] values2 = LogLevel.values();
            int length2 = values2.length;
            while (i < length2) {
                LogLevel logLevel2 = values2[i];
                if (this.mDefaultLogLevelCounts.getOrDefault(logLevel2, 0).intValue() <= 0) {
                    i++;
                } else {
                    return logLevel2;
                }
            }
        }
        return LogLevel.WTF;
    }

    public int startLoggingStackTrace(String[] groups, ILogger logger) {
        return -1;
    }

    public int stopLoggingStackTrace() {
        return -1;
    }

    private int setTextLogging(boolean value, ILogger logger, String... groups) {
        for (String group : groups) {
            IProtoLogGroup g = this.mLogGroups.get(group);
            if (g != null) {
                g.setLogToLogcat(value);
            } else {
                logger.log("No IProtoLogGroup named " + group);
                return -1;
            }
        }
        this.mCacheUpdater.run();
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onShellCommand(ShellCommand shell) {
        char c;
        final PrintWriter pw = shell.getOutPrintWriter();
        String cmd = shell.getNextArg();
        if (cmd == null) {
            return unknownCommand(pw);
        }
        ArrayList<String> args = new ArrayList<>();
        while (true) {
            String arg = shell.getNextArg();
            if (arg == null) {
                break;
            }
            args.add(arg);
        }
        ILogger logger =
                new ILogger() { // from class:
                                // com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda5
                    @Override // com.android.internal.protolog.common.ILogger
                    public final void log(String str) {
                        PerfettoProtoLogImpl.logAndPrintln(pw, str);
                    }
                };
        String[] groups = (String[]) args.toArray(new String[0]);
        switch (cmd.hashCode()) {
            case -1475003593:
                if (cmd.equals("enable-text")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1032071950:
                if (cmd.equals("disable-text")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (cmd.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (cmd.equals("start")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                pw.println(
                        "Command not supported. Please start and stop ProtoLog tracing with"
                            + " Perfetto.");
                break;
            case 2:
                this.mViewerConfigReader.loadViewerConfig(logger);
                break;
        }
        return unknownCommand(pw);
    }

    private int unknownCommand(PrintWriter pw) {
        pw.println("Unknown command");
        pw.println("Window manager logging options:");
        pw.println("  enable-text [group...]: Enable logcat logging for given groups");
        pw.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTracingInstanceStart(ProtoLogDataSource.ProtoLogConfig config) {
        this.mTracingInstances.incrementAndGet();
        LogLevel defaultLogFrom = config.getDefaultGroupConfig().logFrom;
        this.mDefaultLogLevelCounts.put(
                defaultLogFrom,
                Integer.valueOf(
                        this.mDefaultLogLevelCounts.getOrDefault(defaultLogFrom, 0).intValue()
                                + 1));
        Set<String> overriddenGroupTags = config.getGroupTagsWithOverriddenConfigs();
        for (String overriddenGroupTag : overriddenGroupTags) {
            IProtoLogGroup group = this.mLogGroups.get(overriddenGroupTag);
            this.mLogLevelCounts.putIfAbsent(group, new ArrayMap());
            Map<LogLevel, Integer> logLevelsCountsForGroup = this.mLogLevelCounts.get(group);
            LogLevel logFromLevel = config.getConfigFor(overriddenGroupTag).logFrom;
            logLevelsCountsForGroup.put(
                    logFromLevel,
                    Integer.valueOf(
                            logLevelsCountsForGroup.getOrDefault(logFromLevel, 0).intValue() + 1));
        }
        this.mCacheUpdater.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTracingInstanceStop(ProtoLogDataSource.ProtoLogConfig config) {
        this.mTracingInstances.decrementAndGet();
        LogLevel defaultLogFrom = config.getDefaultGroupConfig().logFrom;
        this.mDefaultLogLevelCounts.put(
                defaultLogFrom,
                Integer.valueOf(this.mDefaultLogLevelCounts.get(defaultLogFrom).intValue() - 1));
        if (this.mDefaultLogLevelCounts.get(defaultLogFrom).intValue() <= 0) {
            this.mDefaultLogLevelCounts.remove(defaultLogFrom);
        }
        Set<String> overriddenGroupTags = config.getGroupTagsWithOverriddenConfigs();
        for (String overriddenGroupTag : overriddenGroupTags) {
            IProtoLogGroup group = this.mLogGroups.get(overriddenGroupTag);
            this.mLogLevelCounts.putIfAbsent(group, new ArrayMap());
            Map<LogLevel, Integer> logLevelsCountsForGroup = this.mLogLevelCounts.get(group);
            LogLevel logFromLevel = config.getConfigFor(overriddenGroupTag).logFrom;
            logLevelsCountsForGroup.put(
                    logFromLevel,
                    Integer.valueOf(logLevelsCountsForGroup.get(logFromLevel).intValue() - 1));
            if (logLevelsCountsForGroup.get(logFromLevel).intValue() <= 0) {
                logLevelsCountsForGroup.remove(logFromLevel);
            }
            if (logLevelsCountsForGroup.isEmpty()) {
                this.mLogLevelCounts.remove(group);
            }
        }
        this.mCacheUpdater.run();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void logAndPrintln(PrintWriter pw, String msg) {
        Slog.i(LOG_TAG, msg);
        if (pw != null) {
            pw.println(msg);
            pw.flush();
        }
    }
}
