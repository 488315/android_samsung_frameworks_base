package com.android.systemui.kairos.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class GraphKt {
    public static final boolean isEmpty(DownstreamSet downstreamSet) {
        return downstreamSet.nodes.isEmpty() && downstreamSet.outputs.isEmpty() && downstreamSet.muxMovers.isEmpty() && ((ArrayList) downstreamSet.stateWriters).isEmpty();
    }

    public static final boolean scheduleAll(DownstreamSet downstreamSet, EvalScope evalScope) {
        Iterator it = downstreamSet.nodes.iterator();
        while (it.hasNext()) {
            ((SchedulableNode) it.next()).schedule(evalScope);
        }
        Iterator it2 = downstreamSet.muxMovers.iterator();
        while (it2.hasNext()) {
            MuxDeferredNode muxDeferredNode = (MuxDeferredNode) it2.next();
            NodeConnection nodeConnection = muxDeferredNode.patches;
            if (nodeConnection == null) {
                throw new IllegalStateException("mux mover scheduled with unset patches upstream node");
            }
            muxDeferredNode.patchData = (Iterable) nodeConnection.directUpstream.getPushEvent(evalScope);
            evalScope.scheduleMuxMover(muxDeferredNode);
        }
        Iterator it3 = downstreamSet.outputs.iterator();
        while (it3.hasNext()) {
            Output output = (Output) it3.next();
            NodeConnection nodeConnection2 = output.upstream;
            if (nodeConnection2 == null) {
                throw new IllegalStateException("output scheduled with null upstream");
            }
            output.result = nodeConnection2.directUpstream.getPushEvent(evalScope);
            evalScope.scheduleOutput(output);
        }
        ArrayList arrayList = (ArrayList) downstreamSet.stateWriters;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            evalScope.schedule((StateSource) obj);
        }
        return !isEmpty(downstreamSet);
    }
}
