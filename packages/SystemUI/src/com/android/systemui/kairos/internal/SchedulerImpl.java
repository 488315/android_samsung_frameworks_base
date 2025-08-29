package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.util.LogIndent;
import java.util.Comparator;
import java.util.PriorityQueue;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes2.dex */
public final class SchedulerImpl {
    public final Function1 enqueue;
    public final PriorityQueue scheduledQ = new PriorityQueue(new Comparator() { // from class: com.android.systemui.kairos.internal.SchedulerImpl$special$$inlined$compareBy$1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((Comparable) ((Pair) obj).getFirst(), (Comparable) ((Pair) obj2).getFirst());
        }
    });

    public SchedulerImpl(Function1 function1) {
        this.enqueue = function1;
    }

    public final void drainCompact$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos() {
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        while (!this.scheduledQ.isEmpty()) {
            Pair pair = (Pair) this.scheduledQ.peek();
            if (pair == null) {
                throw new IllegalStateException("Unexpected empty scheduler");
            }
            new SchedulerImpl$drain$1(((Number) pair.getFirst()).intValue(), this, ref$IntRef).invoke(LogIndent.m2585boximpl(), new Function2() { // from class: com.android.systemui.kairos.internal.SchedulerImpl$drainCompact$1$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i = ((LogIndent) obj).currentLogIndent;
                    MuxNode muxNode = (MuxNode) obj2;
                    muxNode.markedForCompaction = false;
                    SchedulerImpl schedulerImpl = this.this$0;
                    DepthTracker depthTracker = muxNode.depthTracker;
                    if (depthTracker.isDirty()) {
                        depthTracker.applyChanges(schedulerImpl, muxNode.downstreamSet, muxNode);
                    }
                    return Unit.INSTANCE;
                }
            });
        }
    }
}
