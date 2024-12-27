package com.android.internal.protolog;

import android.hardware.display.SemWifiDisplayParameter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.text.TextUtils;
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
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class LegacyProtoLogImpl implements IProtoLog {
    private static final int BUFFER_CAPACITY = 1048576;
    private static final long MAGIC_NUMBER_VALUE = 5138409603453637200L;
    private static final int PER_CHUNK_SIZE = 1024;
    static final String PROTOLOG_VERSION = "2.0.0";
    private static final String TAG = "ProtoLog";
    private final TraceBuffer mBuffer;
    private final Runnable mCacheUpdater;
    private final String mLegacyViewerConfigFilename;
    private final File mLogFile;
    private final TreeMap<String, IProtoLogGroup> mLogGroups;
    private final int mPerChunkSize;
    private boolean mProtoLogEnabled;
    private final Object mProtoLogEnabledLock;
    private boolean mProtoLogEnabledLockFree;
    private final LegacyProtoLogViewerConfigReader mViewerConfig;

    public LegacyProtoLogImpl(
            String outputFile,
            String viewerConfigFilename,
            TreeMap<String, IProtoLogGroup> logGroups,
            Runnable cacheUpdater) {
        this(
                new File(outputFile),
                viewerConfigFilename,
                1048576,
                new LegacyProtoLogViewerConfigReader(),
                1024,
                logGroups,
                cacheUpdater);
    }

    public LegacyProtoLogImpl(
            File file,
            String viewerConfigFilename,
            int bufferCapacity,
            LegacyProtoLogViewerConfigReader viewerConfig,
            int perChunkSize,
            TreeMap<String, IProtoLogGroup> logGroups,
            Runnable cacheUpdater) {
        this.mProtoLogEnabledLock = new Object();
        this.mLogFile = file;
        this.mBuffer = new TraceBuffer(bufferCapacity);
        this.mLegacyViewerConfigFilename = viewerConfigFilename;
        this.mViewerConfig = viewerConfig;
        this.mPerChunkSize = perChunkSize;
        this.mLogGroups = logGroups;
        this.mCacheUpdater = cacheUpdater;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(
            LogLevel level,
            IProtoLogGroup group,
            long messageHash,
            int paramsMask,
            String messageString,
            Object[] args) {
        if (group.isLogToProto()) {
            logToProto(messageHash, paramsMask, args);
        }
        if (group.isLogToLogcat()) {
            logToLogcat(group.getTag(), level, messageHash, messageString, args);
        }
    }

    private void logToLogcat(
            String tag, LogLevel level, long messageHash, String messageString, Object[] args) {
        String message = null;
        if (messageString == null) {
            messageString = this.mViewerConfig.getViewerString(messageHash);
        }
        if (messageString != null) {
            if (args != null) {
                try {
                    message = TextUtils.formatSimple(messageString, args);
                } catch (Exception ex) {
                    Slog.w(TAG, "Invalid ProtoLog format string.", ex);
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

    private void logToProto(long messageHash, int paramsMask, Object[] args) {
        ProtoOutputStream os;
        long token;
        ArrayList<Long> longParams;
        ArrayList<Double> doubleParams;
        ArrayList<Boolean> booleanParams;
        int length;
        int i;
        int argIndex;
        Object[] objArr = args;
        if (!isProtoEnabled()) {
            return;
        }
        try {
            os = new ProtoOutputStream(this.mPerChunkSize);
            token = os.start(2246267895812L);
            os.write(ProtoLogMessage.MESSAGE_HASH, messageHash);
            os.write(ProtoLogMessage.ELAPSED_REALTIME_NANOS, SystemClock.elapsedRealtimeNanos());
        } catch (Exception e) {
            e = e;
        }
        if (objArr != null) {
            try {
                longParams = new ArrayList<>();
                doubleParams = new ArrayList<>();
                booleanParams = new ArrayList<>();
                length = objArr.length;
                i = 0;
                argIndex = 0;
            } catch (Exception e2) {
                e = e2;
            }
            while (i < length) {
                Object o = objArr[i];
                int type = LogDataType.bitmaskToLogDataType(paramsMask, argIndex);
                switch (type) {
                    case 0:
                        os.write(2237677961219L, o.toString());
                        continue;
                        argIndex++;
                        i++;
                        objArr = args;
                    case 1:
                        longParams.add(Long.valueOf(((Number) o).longValue()));
                        continue;
                        argIndex++;
                        i++;
                        objArr = args;
                    case 2:
                        doubleParams.add(Double.valueOf(((Number) o).doubleValue()));
                        continue;
                        argIndex++;
                        i++;
                        objArr = args;
                    case 3:
                        try {
                            booleanParams.add(Boolean.valueOf(((Boolean) o).booleanValue()));
                            continue;
                        } catch (ClassCastException ex) {
                            os.write(2237677961219L, "(INVALID PARAMS_MASK) " + o.toString());
                            Slog.e(TAG, "Invalid ProtoLog paramsMask", ex);
                        }
                        argIndex++;
                        i++;
                        objArr = args;
                    default:
                        argIndex++;
                        i++;
                        objArr = args;
                }
                e = e2;
                Slog.e(TAG, "Exception while logging to proto", e);
                return;
            }
            if (longParams.size() > 0) {
                os.writePackedSInt64(
                        ProtoLogMessage.SINT64_PARAMS,
                        longParams.stream()
                                .mapToLong(
                                        new ToLongFunction() { // from class:
                                                               // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda4
                                            @Override // java.util.function.ToLongFunction
                                            public final long applyAsLong(Object obj) {
                                                long longValue;
                                                longValue = ((Long) obj).longValue();
                                                return longValue;
                                            }
                                        })
                                .toArray());
            }
            if (doubleParams.size() > 0) {
                os.writePackedDouble(
                        ProtoLogMessage.DOUBLE_PARAMS,
                        doubleParams.stream()
                                .mapToDouble(
                                        new ToDoubleFunction() { // from class:
                                                                 // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda5
                                            @Override // java.util.function.ToDoubleFunction
                                            public final double applyAsDouble(Object obj) {
                                                double doubleValue;
                                                doubleValue = ((Double) obj).doubleValue();
                                                return doubleValue;
                                            }
                                        })
                                .toArray());
            }
            if (booleanParams.size() > 0) {
                boolean[] arr = new boolean[booleanParams.size()];
                for (int i2 = 0; i2 < booleanParams.size(); i2++) {
                    arr[i2] = booleanParams.get(i2).booleanValue();
                }
                os.writePackedBool(ProtoLogMessage.BOOLEAN_PARAMS, arr);
            }
        }
        os.end(token);
        this.mBuffer.add(os);
    }

    public void startProtoLog(PrintWriter pw) {
        if (isProtoEnabled()) {
            return;
        }
        synchronized (this.mProtoLogEnabledLock) {
            logAndPrintln(pw, "Start logging to " + this.mLogFile + MediaMetrics.SEPARATOR);
            this.mBuffer.resetBuffer();
            this.mProtoLogEnabled = true;
            this.mProtoLogEnabledLockFree = true;
        }
    }

    public void stopProtoLog(PrintWriter pw, boolean writeToFile) {
        if (!isProtoEnabled()) {
            return;
        }
        synchronized (this.mProtoLogEnabledLock) {
            logAndPrintln(pw, "Stop logging to " + this.mLogFile + ". Waiting for log to flush.");
            this.mProtoLogEnabledLockFree = false;
            this.mProtoLogEnabled = false;
            if (writeToFile) {
                writeProtoLogToFileLocked();
                logAndPrintln(pw, "Log written to " + this.mLogFile + MediaMetrics.SEPARATOR);
                this.mBuffer.resetBuffer();
            }
            if (this.mProtoLogEnabled) {
                logAndPrintln(pw, "ERROR: logging was re-enabled while waiting for flush.");
                throw new IllegalStateException("logging enabled while waiting for flush.");
            }
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mProtoLogEnabledLockFree;
    }

    private int setLogging(
            boolean setTextLogging, boolean value, ILogger logger, String... groups) {
        for (String group : groups) {
            IProtoLogGroup g = this.mLogGroups.get(group);
            if (g != null) {
                if (setTextLogging) {
                    g.setLogToLogcat(value);
                } else {
                    g.setLogToProto(value);
                }
            } else {
                logger.log("No IProtoLogGroup named " + group);
                return -1;
            }
        }
        this.mCacheUpdater.run();
        return 0;
    }

    private int unknownCommand(PrintWriter pw) {
        pw.println("Unknown command");
        pw.println("Window manager logging options:");
        pw.println("  start: Start proto logging");
        pw.println("  stop: Stop proto logging");
        pw.println("  enable [group...]: Enable proto logging for given groups");
        pw.println("  disable [group...]: Disable proto logging for given groups");
        pw.println("  enable-text [group...]: Enable logcat logging for given groups");
        pw.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
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
                                // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda3
                    @Override // com.android.internal.protolog.common.ILogger
                    public final void log(String str) {
                        LegacyProtoLogImpl.logAndPrintln(pw, str);
                    }
                };
        String[] groups = (String[]) args.toArray(new String[args.size()]);
        switch (cmd.hashCode()) {
            case -1475003593:
                if (cmd.equals("enable-text")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1298848381:
                if (cmd.equals("enable")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1032071950:
                if (cmd.equals("disable-text")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -892481550:
                if (cmd.equals("status")) {
                    c = 2;
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
            case 1671308008:
                if (cmd.equals(SemWifiDisplayParameter.VALUE_DISABLE)) {
                    c = 5;
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
                startProtoLog(pw);
                break;
            case 1:
                stopProtoLog(pw, true);
                break;
            case 2:
                logAndPrintln(pw, getStatus());
                break;
            case 4:
                this.mViewerConfig.loadViewerConfig(logger, this.mLegacyViewerConfigFilename);
                break;
        }
        return unknownCommand(pw);
    }

    public String getStatus() {
        return "ProtoLog status: "
                + (isProtoEnabled() ? "Enabled" : "Disabled")
                + "\nEnabled log groups: \n  Proto: "
                + ((String)
                        this.mLogGroups.values().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return LegacyProtoLogImpl.lambda$getStatus$3(
                                                        (IProtoLogGroup) obj);
                                            }
                                        })
                                .map(
                                        new Function() { // from class:
                                                         // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ((IProtoLogGroup) obj).name();
                                            }
                                        })
                                .collect(Collectors.joining(" ")))
                + "\n  Logcat: "
                + ((String)
                        this.mLogGroups.values().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return LegacyProtoLogImpl.lambda$getStatus$4(
                                                        (IProtoLogGroup) obj);
                                            }
                                        })
                                .map(
                                        new Function() { // from class:
                                                         // com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ((IProtoLogGroup) obj).name();
                                            }
                                        })
                                .collect(Collectors.joining(" ")))
                + "\nLogging definitions loaded: "
                + this.mViewerConfig.knownViewerStringsNumber();
    }

    static /* synthetic */ boolean lambda$getStatus$3(IProtoLogGroup it) {
        return it.isEnabled() && it.isLogToProto();
    }

    static /* synthetic */ boolean lambda$getStatus$4(IProtoLogGroup it) {
        return it.isEnabled() && it.isLogToLogcat();
    }

    private void writeProtoLogToFileLocked() {
        try {
            long offset =
                    System.currentTimeMillis() - (SystemClock.elapsedRealtimeNanos() / 1000000);
            ProtoOutputStream proto = new ProtoOutputStream(this.mPerChunkSize);
            proto.write(1125281431553L, MAGIC_NUMBER_VALUE);
            proto.write(1138166333442L, PROTOLOG_VERSION);
            proto.write(1125281431555L, offset);
            this.mBuffer.writeTraceToFile(this.mLogFile, proto);
        } catch (IOException e) {
            Slog.e(TAG, "Unable to write buffer to file", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void logAndPrintln(PrintWriter pw, String msg) {
        Slog.i(TAG, msg);
        if (pw != null) {
            pw.println(msg);
            pw.flush();
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] groups, ILogger logger) {
        this.mViewerConfig.loadViewerConfig(logger, this.mLegacyViewerConfigFilename);
        return setLogging(true, true, logger, groups);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] groups, ILogger logger) {
        return setLogging(true, false, logger, groups);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup group, LogLevel level) {
        return group.isLogToLogcat() || (group.isLogToProto() && isProtoEnabled());
    }
}
