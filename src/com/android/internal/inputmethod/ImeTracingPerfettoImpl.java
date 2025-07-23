package com.android.internal.inputmethod;

import android.internal.perfetto.protos.TracePacketOuterClass;
import android.os.SystemClock;
import android.os.Trace;
import android.tracing.inputmethod.InputMethodDataSource;
import android.tracing.perfetto.DataSourceParams;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.ImeTracing;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
final class ImeTracingPerfettoImpl extends ImeTracing {
    private final InputMethodDataSource mDataSource;
    private final AtomicBoolean mIsClientDumpInProgress;
    private final AtomicBoolean mIsManagerServiceDumpInProgress;
    private final AtomicBoolean mIsServiceDumpInProgress;
    private final AtomicInteger mTracingSessionsCount;

    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(ProtoOutputStream protoOutputStream, int i) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(PrintWriter printWriter) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(PrintWriter printWriter) {
    }

    ImeTracingPerfettoImpl() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        this.mTracingSessionsCount = atomicInteger;
        this.mIsClientDumpInProgress = new AtomicBoolean(false);
        this.mIsServiceDumpInProgress = new AtomicBoolean(false);
        this.mIsManagerServiceDumpInProgress = new AtomicBoolean(false);
        Objects.requireNonNull(atomicInteger);
        Runnable runnable = new Runnable() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger.incrementAndGet();
            }
        };
        Objects.requireNonNull(atomicInteger);
        InputMethodDataSource inputMethodDataSource = new InputMethodDataSource(runnable, new Runnable() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger.decrementAndGet();
            }
        });
        this.mDataSource = inputMethodDataSource;
        Producer.init(InitArguments.DEFAULTS);
        inputMethodDataSource.register(new DataSourceParams.Builder().setBufferExhaustedPolicy(1).setNoFlush(true).setWillNotifyOnStop(false).build());
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(final String str, final InputMethodManager inputMethodManager, final byte[] bArr) {
        if (isEnabled() && isAvailable()) {
            if (this.mIsClientDumpInProgress.compareAndSet(false, true) && inputMethodManager != null) {
                try {
                    Trace.beginSection("inputmethod_client_dump");
                    this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda1
                        @Override // android.tracing.perfetto.TraceFunction
                        public final void trace(TracingContext tracingContext) {
                            ImeTracingPerfettoImpl.lambda$triggerClientDump$0(str, inputMethodManager, bArr, tracingContext);
                        }
                    });
                } finally {
                    this.mIsClientDumpInProgress.set(false);
                    Trace.endSection();
                }
            }
        }
    }

    static /* synthetic */ void lambda$triggerClientDump$0(String str, InputMethodManager inputMethodManager, byte[] bArr, TracingContext tracingContext) {
        ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        newTracePacket.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long start = newTracePacket.start(1146756268144L);
        long start2 = newTracePacket.start(1146756268033L);
        newTracePacket.write(1138166333442L, str);
        long start3 = newTracePacket.start(1146756268035L);
        inputMethodManager.dumpDebug(newTracePacket, bArr);
        newTracePacket.end(start3);
        newTracePacket.end(start2);
        newTracePacket.end(start);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(final String str, final ImeTracing.ServiceDumper serviceDumper, final byte[] bArr) {
        if (isEnabled() && isAvailable()) {
            if (this.mIsServiceDumpInProgress.compareAndSet(false, true)) {
                try {
                    Trace.beginSection("inputmethod_service_dump");
                    this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda0
                        @Override // android.tracing.perfetto.TraceFunction
                        public final void trace(TracingContext tracingContext) {
                            ImeTracingPerfettoImpl.lambda$triggerServiceDump$1(str, serviceDumper, bArr, tracingContext);
                        }
                    });
                } finally {
                    this.mIsServiceDumpInProgress.set(false);
                    Trace.endSection();
                }
            }
        }
    }

    static /* synthetic */ void lambda$triggerServiceDump$1(String str, ImeTracing.ServiceDumper serviceDumper, byte[] bArr, TracingContext tracingContext) {
        ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        newTracePacket.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long start = newTracePacket.start(1146756268144L);
        long start2 = newTracePacket.start(1146756268034L);
        newTracePacket.write(1138166333442L, str);
        serviceDumper.dumpToProto(newTracePacket, bArr);
        newTracePacket.end(start2);
        newTracePacket.end(start);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(final String str, final ImeTracing.ServiceDumper serviceDumper) {
        if (isEnabled() && isAvailable()) {
            if (this.mIsManagerServiceDumpInProgress.compareAndSet(false, true)) {
                try {
                    Trace.beginSection("inputmethod_manager_service_dump");
                    this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda2
                        @Override // android.tracing.perfetto.TraceFunction
                        public final void trace(TracingContext tracingContext) {
                            ImeTracingPerfettoImpl.lambda$triggerManagerServiceDump$2(str, serviceDumper, tracingContext);
                        }
                    });
                } finally {
                    this.mIsManagerServiceDumpInProgress.set(false);
                    Trace.endSection();
                }
            }
        }
    }

    static /* synthetic */ void lambda$triggerManagerServiceDump$2(String str, ImeTracing.ServiceDumper serviceDumper, TracingContext tracingContext) {
        ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        newTracePacket.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long start = newTracePacket.start(1146756268144L);
        long start2 = newTracePacket.start(1146756268035L);
        newTracePacket.write(1138166333442L, str);
        serviceDumper.dumpToProto(newTracePacket, null);
        newTracePacket.end(start2);
        newTracePacket.end(start);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public boolean isEnabled() {
        return this.mTracingSessionsCount.get() > 0;
    }
}
