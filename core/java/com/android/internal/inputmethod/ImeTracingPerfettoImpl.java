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
    private final AtomicInteger mTracingSessionsCount = new AtomicInteger(0);
    private final AtomicBoolean mIsClientDumpInProgress = new AtomicBoolean(false);
    private final AtomicBoolean mIsServiceDumpInProgress = new AtomicBoolean(false);
    private final AtomicBoolean mIsManagerServiceDumpInProgress = new AtomicBoolean(false);

    ImeTracingPerfettoImpl() {
        final AtomicInteger atomicInteger = this.mTracingSessionsCount;
        Objects.requireNonNull(atomicInteger);
        Runnable runnable = new Runnable() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger.incrementAndGet();
            }
        };
        final AtomicInteger atomicInteger2 = this.mTracingSessionsCount;
        Objects.requireNonNull(atomicInteger2);
        this.mDataSource = new InputMethodDataSource(runnable, new Runnable() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger2.decrementAndGet();
            }
        });
        Producer.init(InitArguments.DEFAULTS);
        DataSourceParams params = new DataSourceParams.Builder().setBufferExhaustedPolicy(1).setNoFlush(true).setWillNotifyOnStop(false).build();
        this.mDataSource.register(params);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(final String where, final InputMethodManager immInstance, final byte[] icProto) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        if (!this.mIsClientDumpInProgress.compareAndSet(false, true) || immInstance == null) {
            return;
        }
        try {
            Trace.beginSection("inputmethod_client_dump");
            this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda1
                @Override // android.tracing.perfetto.TraceFunction
                public final void trace(TracingContext tracingContext) {
                    ImeTracingPerfettoImpl.lambda$triggerClientDump$0(where, immInstance, icProto, tracingContext);
                }
            });
        } finally {
            this.mIsClientDumpInProgress.set(false);
            Trace.endSection();
        }
    }

    static /* synthetic */ void lambda$triggerClientDump$0(String where, InputMethodManager immInstance, byte[] icProto, TracingContext ctx) {
        ProtoOutputStream os = ctx.newTracePacket();
        os.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long tokenWinscopeExtensions = os.start(1146756268144L);
        long tokenExtensionsField = os.start(1146756268033L);
        os.write(1138166333442L, where);
        long tokenClient = os.start(1146756268035L);
        immInstance.dumpDebug(os, icProto);
        os.end(tokenClient);
        os.end(tokenExtensionsField);
        os.end(tokenWinscopeExtensions);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(final String where, final ImeTracing.ServiceDumper dumper, final byte[] icProto) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        if (!this.mIsServiceDumpInProgress.compareAndSet(false, true)) {
            return;
        }
        try {
            Trace.beginSection("inputmethod_service_dump");
            this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda0
                @Override // android.tracing.perfetto.TraceFunction
                public final void trace(TracingContext tracingContext) {
                    ImeTracingPerfettoImpl.lambda$triggerServiceDump$1(where, dumper, icProto, tracingContext);
                }
            });
        } finally {
            this.mIsServiceDumpInProgress.set(false);
            Trace.endSection();
        }
    }

    static /* synthetic */ void lambda$triggerServiceDump$1(String where, ImeTracing.ServiceDumper dumper, byte[] icProto, TracingContext ctx) {
        ProtoOutputStream os = ctx.newTracePacket();
        os.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long tokenWinscopeExtensions = os.start(1146756268144L);
        long tokenExtensionsField = os.start(1146756268034L);
        os.write(1138166333442L, where);
        dumper.dumpToProto(os, icProto);
        os.end(tokenExtensionsField);
        os.end(tokenWinscopeExtensions);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(final String where, final ImeTracing.ServiceDumper dumper) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        if (!this.mIsManagerServiceDumpInProgress.compareAndSet(false, true)) {
            return;
        }
        try {
            Trace.beginSection("inputmethod_manager_service_dump");
            this.mDataSource.trace(new TraceFunction() { // from class: com.android.internal.inputmethod.ImeTracingPerfettoImpl$$ExternalSyntheticLambda2
                @Override // android.tracing.perfetto.TraceFunction
                public final void trace(TracingContext tracingContext) {
                    ImeTracingPerfettoImpl.lambda$triggerManagerServiceDump$2(where, dumper, tracingContext);
                }
            });
        } finally {
            this.mIsManagerServiceDumpInProgress.set(false);
            Trace.endSection();
        }
    }

    static /* synthetic */ void lambda$triggerManagerServiceDump$2(String where, ImeTracing.ServiceDumper dumper, TracingContext ctx) {
        ProtoOutputStream os = ctx.newTracePacket();
        os.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
        long tokenWinscopeExtensions = os.start(1146756268144L);
        long tokenExtensionsField = os.start(1146756268035L);
        os.write(1138166333442L, where);
        dumper.dumpToProto(os, null);
        os.end(tokenExtensionsField);
        os.end(tokenWinscopeExtensions);
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public boolean isEnabled() {
        return this.mTracingSessionsCount.get() > 0;
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(PrintWriter pw) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(PrintWriter pw) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(ProtoOutputStream proto, int source) {
    }
}
