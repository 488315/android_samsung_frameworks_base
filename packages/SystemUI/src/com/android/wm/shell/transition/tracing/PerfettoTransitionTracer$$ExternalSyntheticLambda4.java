package com.android.wm.shell.transition.tracing;

import android.os.SystemClock;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PerfettoTransitionTracer$$ExternalSyntheticLambda4 implements TraceFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PerfettoTransitionTracer$$ExternalSyntheticLambda4(int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = i2;
    }

    public final void trace(TracingContext tracingContext) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                int i2 = this.f$1;
                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                long start = newTracePacket.start(1146756268128L);
                newTracePacket.write(1120986464257L, i);
                newTracePacket.write(1112396529670L, SystemClock.elapsedRealtimeNanos());
                newTracePacket.write(1120986464271L, i2);
                newTracePacket.end(start);
                break;
            default:
                int i3 = this.f$0;
                int i4 = this.f$1;
                ProtoOutputStream newTracePacket2 = tracingContext.newTracePacket();
                long start2 = newTracePacket2.start(1146756268128L);
                newTracePacket2.write(1120986464257L, i3);
                newTracePacket2.write(1112396529669L, SystemClock.elapsedRealtimeNanos());
                newTracePacket2.write(1120986464271L, i4);
                newTracePacket2.end(start2);
                break;
        }
    }
}
