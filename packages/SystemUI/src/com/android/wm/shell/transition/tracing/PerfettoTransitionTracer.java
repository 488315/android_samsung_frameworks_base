package com.android.wm.shell.transition.tracing;

import android.os.SystemClock;
import android.os.Trace;
import android.tracing.perfetto.DataSourceParams;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.tracing.transition.TransitionDataSource;
import android.util.proto.ProtoOutputStream;
import com.android.wm.shell.transition.Transitions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PerfettoTransitionTracer {
    public final AtomicInteger mActiveTraces;
    public final TransitionDataSource mDataSource;
    public final Map mHandlerMapping;

    public PerfettoTransitionTracer() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        this.mActiveTraces = atomicInteger;
        final int i = 0;
        final int i2 = 2;
        final int i3 = 1;
        TransitionDataSource transitionDataSource = new TransitionDataSource(new Runnable() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i;
                Object obj = atomicInteger;
                switch (i4) {
                    case 0:
                        ((AtomicInteger) obj).incrementAndGet();
                        break;
                    case 1:
                        ((AtomicInteger) obj).decrementAndGet();
                        break;
                    default:
                        final PerfettoTransitionTracer perfettoTransitionTracer = (PerfettoTransitionTracer) obj;
                        perfettoTransitionTracer.mDataSource.trace(new TraceFunction() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda5
                            public final void trace(TracingContext tracingContext) {
                                PerfettoTransitionTracer perfettoTransitionTracer2 = PerfettoTransitionTracer.this;
                                perfettoTransitionTracer2.getClass();
                                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                                long start = newTracePacket.start(1146756268129L);
                                for (Map.Entry entry : ((HashMap) perfettoTransitionTracer2.mHandlerMapping).entrySet()) {
                                    String str = (String) entry.getKey();
                                    int intValue = ((Integer) entry.getValue()).intValue();
                                    long start2 = newTracePacket.start(2246267895809L);
                                    newTracePacket.write(1120986464257L, intValue);
                                    newTracePacket.write(1138166333442L, str);
                                    newTracePacket.end(start2);
                                }
                                newTracePacket.end(start);
                            }
                        });
                        break;
                }
            }
        }, new Runnable() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i2;
                Object obj = this;
                switch (i4) {
                    case 0:
                        ((AtomicInteger) obj).incrementAndGet();
                        break;
                    case 1:
                        ((AtomicInteger) obj).decrementAndGet();
                        break;
                    default:
                        final PerfettoTransitionTracer perfettoTransitionTracer = (PerfettoTransitionTracer) obj;
                        perfettoTransitionTracer.mDataSource.trace(new TraceFunction() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda5
                            public final void trace(TracingContext tracingContext) {
                                PerfettoTransitionTracer perfettoTransitionTracer2 = PerfettoTransitionTracer.this;
                                perfettoTransitionTracer2.getClass();
                                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                                long start = newTracePacket.start(1146756268129L);
                                for (Map.Entry entry : ((HashMap) perfettoTransitionTracer2.mHandlerMapping).entrySet()) {
                                    String str = (String) entry.getKey();
                                    int intValue = ((Integer) entry.getValue()).intValue();
                                    long start2 = newTracePacket.start(2246267895809L);
                                    newTracePacket.write(1120986464257L, intValue);
                                    newTracePacket.write(1138166333442L, str);
                                    newTracePacket.end(start2);
                                }
                                newTracePacket.end(start);
                            }
                        });
                        break;
                }
            }
        }, new Runnable() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i3;
                Object obj = atomicInteger;
                switch (i4) {
                    case 0:
                        ((AtomicInteger) obj).incrementAndGet();
                        break;
                    case 1:
                        ((AtomicInteger) obj).decrementAndGet();
                        break;
                    default:
                        final PerfettoTransitionTracer perfettoTransitionTracer = (PerfettoTransitionTracer) obj;
                        perfettoTransitionTracer.mDataSource.trace(new TraceFunction() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda5
                            public final void trace(TracingContext tracingContext) {
                                PerfettoTransitionTracer perfettoTransitionTracer2 = PerfettoTransitionTracer.this;
                                perfettoTransitionTracer2.getClass();
                                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                                long start = newTracePacket.start(1146756268129L);
                                for (Map.Entry entry : ((HashMap) perfettoTransitionTracer2.mHandlerMapping).entrySet()) {
                                    String str = (String) entry.getKey();
                                    int intValue = ((Integer) entry.getValue()).intValue();
                                    long start2 = newTracePacket.start(2246267895809L);
                                    newTracePacket.write(1120986464257L, intValue);
                                    newTracePacket.write(1138166333442L, str);
                                    newTracePacket.end(start2);
                                }
                                newTracePacket.end(start);
                            }
                        });
                        break;
                }
            }
        });
        this.mDataSource = transitionDataSource;
        this.mHandlerMapping = new HashMap();
        Producer.init(InitArguments.DEFAULTS);
        transitionDataSource.register(new DataSourceParams.Builder().setBufferExhaustedPolicy(0).build());
    }

    public final void logDispatched(final int i, final Transitions.TransitionHandler transitionHandler) {
        if (this.mActiveTraces.get() > 0) {
            Trace.traceBegin(32L, "logDispatched");
            try {
                this.mDataSource.trace(new TraceFunction() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda6
                    public final void trace(TracingContext tracingContext) {
                        int i2;
                        PerfettoTransitionTracer perfettoTransitionTracer = PerfettoTransitionTracer.this;
                        Transitions.TransitionHandler transitionHandler2 = transitionHandler;
                        int i3 = i;
                        synchronized (perfettoTransitionTracer.mHandlerMapping) {
                            try {
                                if (((HashMap) perfettoTransitionTracer.mHandlerMapping).containsKey(transitionHandler2.getClass().getName())) {
                                    i2 = ((Integer) ((HashMap) perfettoTransitionTracer.mHandlerMapping).get(transitionHandler2.getClass().getName())).intValue();
                                } else {
                                    int size = ((HashMap) perfettoTransitionTracer.mHandlerMapping).size() + 1;
                                    ((HashMap) perfettoTransitionTracer.mHandlerMapping).put(transitionHandler2.getClass().getName(), Integer.valueOf(size));
                                    i2 = size;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                        long start = newTracePacket.start(1146756268128L);
                        newTracePacket.write(1120986464257L, i3);
                        newTracePacket.write(1112396529668L, SystemClock.elapsedRealtimeNanos());
                        newTracePacket.write(1120986464268L, i2);
                        newTracePacket.end(start);
                    }
                });
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }
}
