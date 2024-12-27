package com.android.server.wm;

import android.os.Trace;
import android.tracing.perfetto.DataSourceParams;
import android.tracing.perfetto.InitArguments;
import android.tracing.perfetto.Producer;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.tracing.transition.TransitionDataSource;
import android.util.proto.ProtoOutputStream;

import com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda2;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerfettoTransitionTracer implements TransitionTracer {
    public final AtomicInteger mActiveTraces;
    public final TransitionDataSource mDataSource;

    public PerfettoTransitionTracer() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        this.mActiveTraces = atomicInteger;
        Objects.requireNonNull(atomicInteger);
        final int i = 0;
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        AtomicInteger atomicInteger2 = atomicInteger;
                        switch (i2) {
                            case 0:
                                atomicInteger2.incrementAndGet();
                                break;
                            default:
                                atomicInteger2.decrementAndGet();
                                break;
                        }
                    }
                };
        InlineSuggestionFactory$$ExternalSyntheticLambda2
                inlineSuggestionFactory$$ExternalSyntheticLambda2 =
                        new InlineSuggestionFactory$$ExternalSyntheticLambda2();
        Objects.requireNonNull(atomicInteger);
        final int i2 = 1;
        TransitionDataSource transitionDataSource =
                new TransitionDataSource(
                        runnable,
                        inlineSuggestionFactory$$ExternalSyntheticLambda2,
                        new Runnable() { // from class:
                                         // com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i2;
                                AtomicInteger atomicInteger2 = atomicInteger;
                                switch (i22) {
                                    case 0:
                                        atomicInteger2.incrementAndGet();
                                        break;
                                    default:
                                        atomicInteger2.decrementAndGet();
                                        break;
                                }
                            }
                        });
        this.mDataSource = transitionDataSource;
        Producer.init(InitArguments.DEFAULTS);
        transitionDataSource.register(
                new DataSourceParams.Builder().setBufferExhaustedPolicy(0).build());
    }

    @Override // com.android.server.wm.TransitionTracer
    public final boolean isTracing() {
        return this.mActiveTraces.get() > 0;
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logAbortedTransition(Transition transition) {
        if (isTracing()) {
            Trace.traceBegin(32L, "logAbortedTransition");
            try {
                this.mDataSource.trace(
                        new PerfettoTransitionTracer$$ExternalSyntheticLambda2(1, transition));
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logFinishedTransition(Transition transition) {
        if (isTracing()) {
            Trace.traceBegin(32L, "logFinishedTransition");
            try {
                this.mDataSource.trace(
                        new PerfettoTransitionTracer$$ExternalSyntheticLambda2(0, transition));
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logRemovingStartingWindow(StartingData startingData) {
        if (isTracing()) {
            Trace.traceBegin(32L, "logRemovingStartingWindow");
            try {
                this.mDataSource.trace(
                        new PerfettoTransitionTracer$$ExternalSyntheticLambda2(2, startingData));
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logSentTransition(final Transition transition, final ArrayList arrayList) {
        if (isTracing()) {
            Trace.traceBegin(32L, "logSentTransition");
            try {
                this.mDataSource.trace(
                        new TraceFunction() { // from class:
                                              // com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda5
                            public final void trace(TracingContext tracingContext) {
                                PerfettoTransitionTracer perfettoTransitionTracer =
                                        PerfettoTransitionTracer.this;
                                Transition transition2 = transition;
                                ArrayList arrayList2 = arrayList;
                                perfettoTransitionTracer.getClass();
                                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                                long start = newTracePacket.start(1146756268128L);
                                newTracePacket.write(1120986464257L, transition2.mSyncId);
                                TransitionController.Logger logger = transition2.mLogger;
                                newTracePacket.write(1112396529666L, logger.mCreateTimeNs);
                                newTracePacket.write(1112396529667L, logger.mSendTimeNs);
                                newTracePacket.write(
                                        1116691496970L, transition2.getStartTransaction().getId());
                                newTracePacket.write(
                                        1116691496971L, transition2.getFinishTransaction().getId());
                                newTracePacket.write(1120986464269L, transition2.mType);
                                newTracePacket.write(1120986464272L, transition2.mFlags);
                                for (int i = 0; i < arrayList2.size(); i++) {
                                    Transition.ChangeInfo changeInfo =
                                            (Transition.ChangeInfo) arrayList2.get(i);
                                    boolean isValid =
                                            changeInfo.mContainer.mSurfaceControl.isValid();
                                    WindowContainer windowContainer = changeInfo.mContainer;
                                    int layerId =
                                            isValid
                                                    ? windowContainer.mSurfaceControl.getLayerId()
                                                    : -1;
                                    int identityHashCode = System.identityHashCode(windowContainer);
                                    long start2 = newTracePacket.start(2246267895822L);
                                    newTracePacket.write(1120986464257L, changeInfo.mReadyMode);
                                    newTracePacket.write(1120986464260L, changeInfo.mReadyFlags);
                                    newTracePacket.write(1120986464258L, layerId);
                                    newTracePacket.write(1120986464259L, identityHashCode);
                                    newTracePacket.end(start2);
                                }
                                newTracePacket.end(start);
                            }
                        });
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void saveForBugreport(PrintWriter printWriter) {}

    @Override // com.android.server.wm.TransitionTracer
    public final void startTrace(PrintWriter printWriter) {}

    @Override // com.android.server.wm.TransitionTracer
    public final void stopTrace(PrintWriter printWriter) {}
}
