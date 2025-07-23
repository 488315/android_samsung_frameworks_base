package com.android.internal.inputmethod;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.util.TraceBuffer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
class ImeTracingServerImpl extends ImeTracing {
    private static final int BUFFER_CAPACITY = 4194304;
    private static final long MAGIC_NUMBER_CLIENTS_VALUE = 4990904633913462089L;
    private static final long MAGIC_NUMBER_IMMS_VALUE = 4990904633914117449L;
    private static final long MAGIC_NUMBER_IMS_VALUE = 4990904633914510665L;
    private static final String TRACE_DIRNAME = "/data/misc/wmtrace/";
    private static final String TRACE_FILENAME_CLIENTS = "ime_trace_clients.winscope";
    private static final String TRACE_FILENAME_IMMS = "ime_trace_managerservice.winscope";
    private static final String TRACE_FILENAME_IMS = "ime_trace_service.winscope";
    private final Object mEnabledLock = new Object();
    private final TraceBuffer mBufferClients = new TraceBuffer(4194304);
    private final File mTraceFileClients = new File("/data/misc/wmtrace/ime_trace_clients.winscope");
    private final TraceBuffer mBufferIms = new TraceBuffer(4194304);
    private final File mTraceFileIms = new File("/data/misc/wmtrace/ime_trace_service.winscope");
    private final TraceBuffer mBufferImms = new TraceBuffer(4194304);
    private final File mTraceFileImms = new File("/data/misc/wmtrace/ime_trace_managerservice.winscope");

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(String str, InputMethodManager inputMethodManager, byte[] bArr) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(String str, ImeTracing.ServiceDumper serviceDumper, byte[] bArr) {
    }

    ImeTracingServerImpl() {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(ProtoOutputStream protoOutputStream, int i) {
        if (isAvailable() && isEnabled()) {
            if (i == 0) {
                this.mBufferClients.add(protoOutputStream);
                return;
            }
            if (i == 1) {
                this.mBufferIms.add(protoOutputStream);
            } else if (i == 2) {
                this.mBufferImms.add(protoOutputStream);
            } else {
                Log.w("imeTracing", "Request to add to buffer, but source not recognised.");
            }
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(String str, ImeTracing.ServiceDumper serviceDumper) {
        if (isEnabled() && isAvailable()) {
            synchronized (this.mDumpInProgressLock) {
                if (this.mDumpInProgress) {
                    return;
                }
                this.mDumpInProgress = true;
                try {
                    sendToService(null, 2, str);
                } finally {
                    this.mDumpInProgress = false;
                }
            }
        }
    }

    private void writeTracesToFilesLocked() {
        try {
            long nanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos();
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            protoOutputStream.write(1125281431553L, MAGIC_NUMBER_CLIENTS_VALUE);
            protoOutputStream.write(1125281431555L, nanos);
            this.mBufferClients.writeTraceToFile(this.mTraceFileClients, protoOutputStream);
            ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
            protoOutputStream2.write(1125281431553L, MAGIC_NUMBER_IMS_VALUE);
            protoOutputStream2.write(1125281431555L, nanos);
            this.mBufferIms.writeTraceToFile(this.mTraceFileIms, protoOutputStream2);
            ProtoOutputStream protoOutputStream3 = new ProtoOutputStream();
            protoOutputStream3.write(1125281431553L, MAGIC_NUMBER_IMMS_VALUE);
            protoOutputStream3.write(1125281431555L, nanos);
            this.mBufferImms.writeTraceToFile(this.mTraceFileImms, protoOutputStream3);
            resetBuffers();
        } catch (IOException e) {
            Log.e("imeTracing", "Unable to write buffer to file", e);
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(PrintWriter printWriter) {
        if (Build.IS_USER) {
            Log.w("imeTracing", "Warn: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                Log.w("imeTracing", "Warn: Tracing is already started.");
                return;
            }
            logAndPrintln(printWriter, "Starting tracing in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
            sEnabled = true;
            resetBuffers();
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(PrintWriter printWriter) {
        if (Build.IS_USER) {
            Log.w("imeTracing", "Warn: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                logAndPrintln(printWriter, "Stopping tracing and writing traces in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
                sEnabled = false;
                writeTracesToFilesLocked();
                return;
            }
            Log.w("imeTracing", "Warn: Tracing is not available or not started.");
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void saveForBugreport(PrintWriter printWriter) {
        if (Build.IS_USER) {
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                sEnabled = false;
                logAndPrintln(printWriter, "Writing traces in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
                writeTracesToFilesLocked();
                sEnabled = true;
            }
        }
    }

    private void resetBuffers() {
        this.mBufferClients.resetBuffer();
        this.mBufferIms.resetBuffer();
        this.mBufferImms.resetBuffer();
    }
}
