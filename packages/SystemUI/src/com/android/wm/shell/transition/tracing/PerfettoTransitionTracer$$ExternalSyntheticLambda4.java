package com.android.wm.shell.transition.tracing;

import android.os.SystemClock;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.proto.ProtoOutputStream;

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
                ProtoOutputStream protoOutputStreamNewTracePacket = tracingContext.newTracePacket();
                long jStart = protoOutputStreamNewTracePacket.start(1146756268128L);
                protoOutputStreamNewTracePacket.write(1120986464257L, i);
                protoOutputStreamNewTracePacket.write(1112396529670L, SystemClock.elapsedRealtimeNanos());
                protoOutputStreamNewTracePacket.write(1120986464271L, i2);
                protoOutputStreamNewTracePacket.end(jStart);
                break;
            default:
                int i3 = this.f$0;
                int i4 = this.f$1;
                ProtoOutputStream protoOutputStreamNewTracePacket2 = tracingContext.newTracePacket();
                long jStart2 = protoOutputStreamNewTracePacket2.start(1146756268128L);
                protoOutputStreamNewTracePacket2.write(1120986464257L, i3);
                protoOutputStreamNewTracePacket2.write(1112396529669L, SystemClock.elapsedRealtimeNanos());
                protoOutputStreamNewTracePacket2.write(1120986464271L, i4);
                protoOutputStreamNewTracePacket2.end(jStart2);
                break;
        }
    }
}
