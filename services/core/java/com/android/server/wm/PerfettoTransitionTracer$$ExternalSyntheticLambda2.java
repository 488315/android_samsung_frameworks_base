package com.android.server.wm;

import android.os.SystemClock;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PerfettoTransitionTracer$$ExternalSyntheticLambda2
        implements TraceFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PerfettoTransitionTracer$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void trace(TracingContext tracingContext) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Transition transition = (Transition) obj;
                ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                long start = newTracePacket.start(1146756268128L);
                newTracePacket.write(1120986464257L, transition.mSyncId);
                newTracePacket.write(1112396529673L, transition.mLogger.mFinishTimeNs);
                newTracePacket.end(start);
                break;
            case 1:
                Transition transition2 = (Transition) obj;
                ProtoOutputStream newTracePacket2 = tracingContext.newTracePacket();
                long start2 = newTracePacket2.start(1146756268128L);
                newTracePacket2.write(1120986464257L, transition2.mSyncId);
                newTracePacket2.write(1112396529672L, transition2.mLogger.mAbortTimeNs);
                newTracePacket2.end(start2);
                break;
            default:
                ProtoOutputStream newTracePacket3 = tracingContext.newTracePacket();
                long start3 = newTracePacket3.start(1146756268128L);
                newTracePacket3.write(1120986464257L, ((StartingData) obj).mTransitionId);
                newTracePacket3.write(1112396529681L, SystemClock.elapsedRealtimeNanos());
                newTracePacket3.end(start3);
                break;
        }
    }
}
