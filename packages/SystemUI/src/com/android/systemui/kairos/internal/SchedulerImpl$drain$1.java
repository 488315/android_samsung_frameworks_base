package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.util.LogIndent;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes2.dex */
public final class SchedulerImpl$drain$1 implements Function2 {
    public final /* synthetic */ int $maxDepth;
    public final /* synthetic */ Ref$IntRef $total;
    public final /* synthetic */ SchedulerImpl this$0;

    public SchedulerImpl$drain$1(int i, SchedulerImpl schedulerImpl, Ref$IntRef ref$IntRef) {
        this.$maxDepth = i;
        this.this$0 = schedulerImpl;
        this.$total = ref$IntRef;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = ((LogIndent) obj).currentLogIndent;
        Function2 function2 = (Function2) obj2;
        int i2 = this.$maxDepth;
        SchedulerImpl schedulerImpl = this.this$0;
        Ref$IntRef ref$IntRef = this.$total;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            Pair pair = (Pair) schedulerImpl.scheduledQ.peek();
            if (pair == null || ((Number) pair.getFirst()).intValue() > i2) {
                break;
            }
            Pair pair2 = (Pair) schedulerImpl.scheduledQ.remove();
            int iIntValue = ((Number) pair2.component1()).intValue();
            MuxNode muxNode = (MuxNode) pair2.component2();
            if (muxNode.depthTracker.dirty_directUpstreamDepths.isEmpty() || iIntValue >= muxNode.depthTracker.dirty_directDepth) {
                i4++;
                arrayList.add(muxNode);
            } else {
                schedulerImpl.scheduledQ.add(new Pair(Integer.valueOf(muxNode.depthTracker.dirty_directDepth), muxNode));
            }
        }
        int size = arrayList.size();
        while (i3 < size) {
            Object obj3 = arrayList.get(i3);
            i3++;
            function2.invoke(LogIndent.m2587boximpl(), (MuxNode) obj3);
        }
        ref$IntRef.element += i4;
        return Unit.INSTANCE;
    }
}
