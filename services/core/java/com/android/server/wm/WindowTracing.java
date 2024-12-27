package com.android.server.wm;

import android.os.Build;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.tracing.Flags;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;

import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.util.TraceBuffer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowTracing {
    public final TraceBuffer mBuffer;
    public final Choreographer mChoreographer;
    public boolean mEnabled;
    public final Object mEnabledLock;
    public volatile boolean mEnabledLockFree;
    public final WindowTracing$$ExternalSyntheticLambda0 mFrameCallback;
    public final WindowManagerGlobalLock mGlobalLock;
    public int mLogLevel;
    public boolean mLogOnFrame;
    public final IProtoLog mProtoLog;
    public boolean mScheduled;
    public final WindowManagerService mService;
    public final File mTraceFile;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.wm.WindowTracing$$ExternalSyntheticLambda0] */
    public WindowTracing(
            File file, WindowManagerService windowManagerService, Choreographer choreographer) {
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        this.mEnabledLock = new Object();
        this.mFrameCallback =
                new Choreographer
                        .FrameCallback() { // from class:
                                           // com.android.server.wm.WindowTracing$$ExternalSyntheticLambda0
                    @Override // android.view.Choreographer.FrameCallback
                    public final void doFrame(long j) {
                        WindowTracing.this.log("onFrame");
                    }
                };
        this.mLogLevel = 1;
        this.mLogOnFrame = false;
        this.mChoreographer = choreographer;
        this.mService = windowManagerService;
        this.mGlobalLock = windowManagerGlobalLock;
        this.mTraceFile = file;
        this.mBuffer = new TraceBuffer(10485760);
        setLogLevel(1, null);
        this.mProtoLog = ProtoLogImpl_54989576.getSingleInstance();
    }

    public static void logAndPrintln(PrintWriter printWriter, String str) {
        Log.i("WindowTracing", str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }

    public final String getStatus() {
        StringBuilder sb = new StringBuilder("Status: ");
        sb.append(this.mEnabledLockFree ? "Enabled" : "Disabled");
        sb.append("\nLog level: ");
        sb.append(this.mLogLevel);
        sb.append("\n");
        sb.append(this.mBuffer.getStatus());
        return sb.toString();
    }

    public final void log(String str) {
        Trace.traceBegin(32L, "traceStateLocked");
        try {
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, str);
                long start2 = protoOutputStream.start(1146756268035L);
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        Trace.traceBegin(32L, "dumpDebugLocked");
                        try {
                            this.mService.dumpDebugLocked(this.mLogLevel, protoOutputStream);
                            Trace.traceEnd(32L);
                        } finally {
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                protoOutputStream.end(start2);
                protoOutputStream.end(start);
                this.mBuffer.add(protoOutputStream);
                this.mScheduled = false;
            } catch (Exception e) {
                Log.wtf("WindowTracing", "Exception while tracing state", e);
            }
        } finally {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onShellCommand(ShellCommand shellCommand) {
        char c;
        PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        String nextArgRequired = shellCommand.getNextArgRequired();
        nextArgRequired.getClass();
        switch (nextArgRequired.hashCode()) {
            case -892481550:
                if (nextArgRequired.equals(Constants.JSON_CLIENT_DATA_STATUS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -390772652:
                if (nextArgRequired.equals("save-for-bugreport")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3530753:
                if (nextArgRequired.equals("size")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (nextArgRequired.equals("stop")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 97692013:
                if (nextArgRequired.equals("frame")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 102865796:
                if (nextArgRequired.equals("level")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (nextArgRequired.equals("start")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2141246174:
                if (nextArgRequired.equals("transaction")) {
                    c = 7;
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
                logAndPrintln(outPrintWriter, getStatus());
                return 0;
            case 1:
                saveForBugreport(outPrintWriter);
                return 0;
            case 2:
                setBufferCapacity(
                        Integer.parseInt(shellCommand.getNextArgRequired()) * 1024, outPrintWriter);
                this.mBuffer.resetBuffer();
                return 0;
            case 3:
                stopTrace(outPrintWriter);
                return 0;
            case 4:
                logAndPrintln(
                        outPrintWriter, "Setting window tracing log frequency to ".concat("frame"));
                this.mLogOnFrame = true;
                this.mBuffer.resetBuffer();
                return 0;
            case 5:
                String lowerCase = shellCommand.getNextArgRequired().toLowerCase();
                lowerCase.getClass();
                switch (lowerCase) {
                    case "all":
                        setLogLevel(0, outPrintWriter);
                        break;
                    case "trim":
                        setLogLevel(1, outPrintWriter);
                        break;
                    case "critical":
                        setLogLevel(2, outPrintWriter);
                        break;
                    default:
                        setLogLevel(1, outPrintWriter);
                        break;
                }
                this.mBuffer.resetBuffer();
                return 0;
            case 6:
                startTrace(outPrintWriter);
                return 0;
            case 7:
                logAndPrintln(
                        outPrintWriter,
                        "Setting window tracing log frequency to ".concat("transaction"));
                this.mLogOnFrame = false;
                this.mBuffer.resetBuffer();
                return 0;
            default:
                outPrintWriter.println("Unknown command: ".concat(nextArgRequired));
                outPrintWriter.println("Window manager trace options:");
                outPrintWriter.println("  start: Start logging");
                BatteryService$$ExternalSyntheticOutline0.m(
                        outPrintWriter,
                        "  stop: Stop logging",
                        "  save-for-bugreport: Save logging data to file if it's running.",
                        "  frame: Log trace once per frame",
                        "  transaction: Log each transaction");
                BatteryService$$ExternalSyntheticOutline0.m(
                        outPrintWriter,
                        "  size: Set the maximum log size (in KB)",
                        "  status: Print trace status",
                        "  level [lvl]: Set the log level between",
                        "    lvl may be one of:");
                BatteryService$$ExternalSyntheticOutline0.m(
                        outPrintWriter,
                        "      critical: Only visible windows with reduced information",
                        "      trim: All windows with reduced",
                        "      all: All window and information");
                return -1;
        }
    }

    public final void saveForBugreport(PrintWriter printWriter) {
        if (Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            try {
                if (this.mEnabled) {
                    this.mEnabledLockFree = false;
                    this.mEnabled = false;
                    logAndPrintln(
                            printWriter,
                            "Stop tracing to "
                                    + this.mTraceFile
                                    + ". Waiting for traces to flush.");
                    writeTraceToFileLocked();
                    logAndPrintln(printWriter, "Trace written to " + this.mTraceFile + ".");
                    if (!Flags.perfettoProtologTracing()) {
                        this.mProtoLog.stopProtoLog(printWriter, true);
                    }
                    logAndPrintln(printWriter, "Start tracing to " + this.mTraceFile + ".");
                    this.mBuffer.resetBuffer();
                    this.mEnabledLockFree = true;
                    this.mEnabled = true;
                    if (!Flags.perfettoProtologTracing()) {
                        this.mProtoLog.startProtoLog(printWriter);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBufferCapacity(int i, PrintWriter printWriter) {
        logAndPrintln(printWriter, "Setting window tracing buffer capacity to " + i + "bytes");
        this.mBuffer.setCapacity(i);
    }

    public final void setLogLevel(int i, PrintWriter printWriter) {
        logAndPrintln(printWriter, "Setting window tracing log level to " + i);
        this.mLogLevel = i;
        if (i == 0) {
            setBufferCapacity(20971520, printWriter);
        } else if (i == 1) {
            setBufferCapacity(10485760, printWriter);
        } else {
            if (i != 2) {
                return;
            }
            setBufferCapacity(5242880, printWriter);
        }
    }

    public final void startTrace(PrintWriter printWriter) {
        if (Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            try {
                if (!Flags.perfettoProtologTracing()) {
                    ProtoLogImpl_54989576.getSingleInstance().startProtoLog(printWriter);
                }
                logAndPrintln(printWriter, "Start tracing to " + this.mTraceFile + ".");
                this.mBuffer.resetBuffer();
                this.mEnabledLockFree = true;
                this.mEnabled = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        log("trace.enable");
    }

    public final void stopTrace(PrintWriter printWriter) {
        if (Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            logAndPrintln(
                    printWriter,
                    "Stop tracing to " + this.mTraceFile + ". Waiting for traces to flush.");
            this.mEnabledLockFree = false;
            this.mEnabled = false;
            writeTraceToFileLocked();
            logAndPrintln(printWriter, "Trace written to " + this.mTraceFile + ".");
        }
        if (Flags.perfettoProtologTracing()) {
            return;
        }
        ProtoLogImpl_54989576.getSingleInstance().stopProtoLog(printWriter, true);
    }

    public final void writeTraceToFileLocked() {
        try {
            try {
                Trace.traceBegin(32L, "writeTraceToFileLocked");
                ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                protoOutputStream.write(1125281431553L, 4990904633914181975L);
                protoOutputStream.write(
                        1125281431555L,
                        TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis())
                                - SystemClock.elapsedRealtimeNanos());
                this.mBuffer.writeTraceToFile(this.mTraceFile, protoOutputStream);
            } catch (IOException e) {
                Log.e("WindowTracing", "Unable to write buffer to file", e);
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }
}
