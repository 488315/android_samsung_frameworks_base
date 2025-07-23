package com.android.internal.protolog;

import android.media.MediaMetrics;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogDataType;
import com.android.internal.protolog.common.LogLevel;
import com.android.internal.util.TraceBuffer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class LegacyProtoLogImpl implements IProtoLog {
    private static final int BUFFER_CAPACITY = 1048576;
    private static final long MAGIC_NUMBER_VALUE = 5138409603453637200L;
    private static final int PER_CHUNK_SIZE = 1024;
    static final String PROTOLOG_VERSION = "2.0.0";
    private static final String TAG = "ProtoLog";
    private final TraceBuffer mBuffer;
    private final ProtoLogCacheUpdater mCacheUpdater;
    private final String mLegacyViewerConfigFilename;
    private final File mLogFile;
    private final Map<String, IProtoLogGroup> mLogGroups;
    private final int mPerChunkSize;
    private boolean mProtoLogEnabled;
    private final Object mProtoLogEnabledLock;
    private boolean mProtoLogEnabledLockFree;
    private final LegacyProtoLogViewerConfigReader mViewerConfig;

    public LegacyProtoLogImpl(String str, String str2, ProtoLogCacheUpdater protoLogCacheUpdater) {
        this(new File(str), str2, 1048576, new LegacyProtoLogViewerConfigReader(), 1024, protoLogCacheUpdater);
    }

    public LegacyProtoLogImpl(File file, String str, int i, LegacyProtoLogViewerConfigReader legacyProtoLogViewerConfigReader, int i2, ProtoLogCacheUpdater protoLogCacheUpdater) {
        this.mLogGroups = new TreeMap();
        this.mProtoLogEnabledLock = new Object();
        this.mLogFile = file;
        this.mBuffer = new TraceBuffer(i);
        this.mLegacyViewerConfigFilename = str;
        this.mViewerConfig = legacyProtoLogViewerConfigReader;
        this.mPerChunkSize = i2;
        this.mCacheUpdater = protoLogCacheUpdater;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, long j, int i, Object[] objArr) {
        if (iProtoLogGroup.isLogToProto()) {
            logToProto(j, i, objArr);
        }
        if (iProtoLogGroup.isLogToLogcat()) {
            logToLogcat(iProtoLogGroup.getTag(), logLevel, j, objArr);
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        throw new IllegalStateException("Not implemented. Only implemented for PerfettoProtoLogImpl.");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void logToLogcat(java.lang.String r4, com.android.internal.protolog.common.LogLevel r5, long r6, java.lang.Object[] r8) {
        /*
            r3 = this;
            com.android.internal.protolog.LegacyProtoLogViewerConfigReader r0 = r3.mViewerConfig
            java.lang.String r0 = r0.getViewerString(r6)
            if (r0 == 0) goto L17
            if (r8 == 0) goto L18
            java.lang.String r0 = android.text.TextUtils.formatSimple(r0, r8)     // Catch: java.lang.Exception -> Lf
            goto L18
        Lf:
            r0 = move-exception
            java.lang.String r1 = "ProtoLog"
            java.lang.String r2 = "Invalid ProtoLog format string."
            android.util.Slog.w(r1, r2, r0)
        L17:
            r0 = 0
        L18:
            if (r0 != 0) goto L49
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "UNKNOWN MESSAGE ("
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r6 = ")"
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6)
            if (r8 == 0) goto L45
            int r6 = r8.length
            r7 = 0
        L36:
            if (r7 >= r6) goto L45
            r1 = r8[r7]
            java.lang.String r2 = " "
            r0.append(r2)
            r0.append(r1)
            int r7 = r7 + 1
            goto L36
        L45:
            java.lang.String r0 = r0.toString()
        L49:
            r3.passToLogcat(r4, r5, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.protolog.LegacyProtoLogImpl.logToLogcat(java.lang.String, com.android.internal.protolog.common.LogLevel, long, java.lang.Object[]):void");
    }

    /* renamed from: com.android.internal.protolog.LegacyProtoLogImpl$1, reason: invalid class name */
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

    private void logToProto(long j, int i, Object[] objArr) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (isProtoEnabled()) {
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream(this.mPerChunkSize);
                long start = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(ProtoLogMessage.MESSAGE_HASH, j);
                protoOutputStream.write(ProtoLogMessage.ELAPSED_REALTIME_NANOS, SystemClock.elapsedRealtimeNanos());
                if (objArr != null) {
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    int length = objArr.length;
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < length) {
                        Object obj = objArr[i2];
                        int bitmaskToLogDataType = LogDataType.bitmaskToLogDataType(i, i3);
                        int i4 = i2;
                        if (bitmaskToLogDataType == 0) {
                            protoOutputStream.write(2237677961219L, obj.toString());
                        } else if (bitmaskToLogDataType == 1) {
                            arrayList3.add(Long.valueOf(((Number) obj).longValue()));
                        } else if (bitmaskToLogDataType == 2) {
                            arrayList4.add(Double.valueOf(((Number) obj).doubleValue()));
                        } else if (bitmaskToLogDataType == 3) {
                            try {
                                Boolean bool = (Boolean) obj;
                                bool.booleanValue();
                                arrayList5.add(bool);
                            } catch (ClassCastException e) {
                                arrayList2 = arrayList3;
                                arrayList = arrayList4;
                                protoOutputStream.write(2237677961219L, "(INVALID PARAMS_MASK) " + obj.toString());
                                Slog.e(TAG, "Invalid ProtoLog paramsMask", e);
                            }
                        }
                        arrayList2 = arrayList3;
                        arrayList = arrayList4;
                        i3++;
                        arrayList3 = arrayList2;
                        arrayList4 = arrayList;
                        i2 = i4 + 1;
                    }
                    ArrayList arrayList6 = arrayList3;
                    ArrayList arrayList7 = arrayList4;
                    if (arrayList6.size() > 0) {
                        protoOutputStream.writePackedSInt64(ProtoLogMessage.SINT64_PARAMS, arrayList6.stream().mapToLong(new ToLongFunction() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda6
                            @Override // java.util.function.ToLongFunction
                            public final long applyAsLong(Object obj2) {
                                long longValue;
                                longValue = ((Long) obj2).longValue();
                                return longValue;
                            }
                        }).toArray());
                    }
                    if (arrayList7.size() > 0) {
                        protoOutputStream.writePackedDouble(ProtoLogMessage.DOUBLE_PARAMS, arrayList7.stream().mapToDouble(new ToDoubleFunction() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda7
                            @Override // java.util.function.ToDoubleFunction
                            public final double applyAsDouble(Object obj2) {
                                double doubleValue;
                                doubleValue = ((Double) obj2).doubleValue();
                                return doubleValue;
                            }
                        }).toArray());
                    }
                    if (arrayList5.size() > 0) {
                        boolean[] zArr = new boolean[arrayList5.size()];
                        for (int i5 = 0; i5 < arrayList5.size(); i5++) {
                            zArr[i5] = ((Boolean) arrayList5.get(i5)).booleanValue();
                        }
                        protoOutputStream.writePackedBool(ProtoLogMessage.BOOLEAN_PARAMS, zArr);
                    }
                }
                protoOutputStream.end(start);
                this.mBuffer.add(protoOutputStream);
            } catch (Exception e2) {
                Slog.e(TAG, "Exception while logging to proto", e2);
            }
        }
    }

    public void startProtoLog(PrintWriter printWriter) {
        if (isProtoEnabled()) {
            return;
        }
        synchronized (this.mProtoLogEnabledLock) {
            logAndPrintln(printWriter, "Start logging to " + this.mLogFile + MediaMetrics.SEPARATOR);
            this.mBuffer.resetBuffer();
            this.mProtoLogEnabled = true;
            this.mProtoLogEnabledLockFree = true;
        }
    }

    public void stopProtoLog(PrintWriter printWriter, boolean z) {
        if (isProtoEnabled()) {
            synchronized (this.mProtoLogEnabledLock) {
                logAndPrintln(printWriter, "Stop logging to " + this.mLogFile + ". Waiting for log to flush.");
                this.mProtoLogEnabledLockFree = false;
                this.mProtoLogEnabled = false;
                if (z) {
                    writeProtoLogToFileLocked();
                    logAndPrintln(printWriter, "Log written to " + this.mLogFile + MediaMetrics.SEPARATOR);
                    this.mBuffer.resetBuffer();
                }
                if (this.mProtoLogEnabled) {
                    logAndPrintln(printWriter, "ERROR: logging was re-enabled while waiting for flush.");
                    throw new IllegalStateException("logging enabled while waiting for flush.");
                }
            }
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mProtoLogEnabledLockFree;
    }

    private int setLogging(boolean z, boolean z2, ILogger iLogger, String... strArr) {
        for (String str : strArr) {
            IProtoLogGroup iProtoLogGroup = this.mLogGroups.get(str);
            if (iProtoLogGroup == null) {
                iLogger.log("No IProtoLogGroup named " + str);
                return -1;
            }
            if (z) {
                iProtoLogGroup.setLogToLogcat(z2);
            } else {
                iProtoLogGroup.setLogToProto(z2);
            }
        }
        this.mCacheUpdater.update(this);
        return 0;
    }

    private int unknownCommand(PrintWriter printWriter) {
        printWriter.println("Unknown command");
        printWriter.println("Window manager logging options:");
        printWriter.println("  start: Start proto logging");
        printWriter.println("  stop: Stop proto logging");
        printWriter.println("  enable [group...]: Enable proto logging for given groups");
        printWriter.println("  disable [group...]: Disable proto logging for given groups");
        printWriter.println("  enable-text [group...]: Enable logcat logging for given groups");
        printWriter.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
    }

    public int onShellCommand(ShellCommand shellCommand) {
        ILogger iLogger;
        final PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
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
        iLogger = new ILogger() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda5
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                LegacyProtoLogImpl.logAndPrintln(outPrintWriter, str);
            }
        };
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        nextArg.hashCode();
        switch (nextArg) {
            case "enable-text":
                this.mViewerConfig.loadViewerConfig(iLogger, this.mLegacyViewerConfigFilename);
                break;
            case "status":
                logAndPrintln(outPrintWriter, getStatus());
                break;
            case "stop":
                stopProtoLog(outPrintWriter, true);
                break;
            case "start":
                startProtoLog(outPrintWriter);
                break;
        }
        return unknownCommand(outPrintWriter);
    }

    public String getStatus() {
        StringBuilder sb = new StringBuilder("ProtoLog status: ");
        sb.append(isProtoEnabled() ? "Enabled" : "Disabled");
        sb.append("\nEnabled log groups: \n  Proto: ");
        sb.append((String) this.mLogGroups.values().stream().filter(new Predicate() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LegacyProtoLogImpl.lambda$getStatus$3((IProtoLogGroup) obj);
            }
        }).map(new Function() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IProtoLogGroup) obj).name();
            }
        }).collect(Collectors.joining(" ")));
        sb.append("\n  Logcat: ");
        sb.append((String) this.mLogGroups.values().stream().filter(new Predicate() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LegacyProtoLogImpl.lambda$getStatus$4((IProtoLogGroup) obj);
            }
        }).map(new Function() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IProtoLogGroup) obj).name();
            }
        }).collect(Collectors.joining(" ")));
        sb.append("\nLogging definitions loaded: ");
        sb.append(this.mViewerConfig.knownViewerStringsNumber());
        return sb.toString();
    }

    static /* synthetic */ boolean lambda$getStatus$3(IProtoLogGroup iProtoLogGroup) {
        return iProtoLogGroup.isEnabled() && iProtoLogGroup.isLogToProto();
    }

    static /* synthetic */ boolean lambda$getStatus$4(IProtoLogGroup iProtoLogGroup) {
        return iProtoLogGroup.isEnabled() && iProtoLogGroup.isLogToLogcat();
    }

    private void writeProtoLogToFileLocked() {
        try {
            long currentTimeMillis = System.currentTimeMillis() - (SystemClock.elapsedRealtimeNanos() / 1000000);
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(this.mPerChunkSize);
            protoOutputStream.write(1125281431553L, MAGIC_NUMBER_VALUE);
            protoOutputStream.write(1138166333442L, PROTOLOG_VERSION);
            protoOutputStream.write(1125281431555L, currentTimeMillis);
            this.mBuffer.writeTraceToFile(this.mLogFile, protoOutputStream);
        } catch (IOException e) {
            Slog.e(TAG, "Unable to write buffer to file", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void logAndPrintln(PrintWriter printWriter, String str) {
        Slog.i(TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] strArr, ILogger iLogger) {
        this.mViewerConfig.loadViewerConfig(iLogger, this.mLegacyViewerConfigFilename);
        return setLogging(true, true, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] strArr, ILogger iLogger) {
        return setLogging(true, false, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        if (iProtoLogGroup.isLogToLogcat()) {
            return true;
        }
        return iProtoLogGroup.isLogToProto() && isProtoEnabled();
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public List<IProtoLogGroup> getRegisteredGroups() {
        return this.mLogGroups.values().stream().toList();
    }

    public void registerGroups(IProtoLogGroup... iProtoLogGroupArr) {
        for (IProtoLogGroup iProtoLogGroup : iProtoLogGroupArr) {
            this.mLogGroups.put(iProtoLogGroup.name(), iProtoLogGroup);
        }
        boolean anyMatch = Arrays.stream(iProtoLogGroupArr).anyMatch(new Predicate() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IProtoLogGroup) obj).isLogToLogcat();
            }
        });
        ILogger iLogger = new ILogger() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                Slog.i(LegacyProtoLogImpl.TAG, str);
            }
        };
        if (anyMatch) {
            this.mViewerConfig.loadViewerConfig(iLogger, this.mLegacyViewerConfigFilename);
        }
    }
}
