package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.DemuxLifecycleState;
import com.android.systemui.kairos.internal.DemuxNode;
import com.android.systemui.kairos.internal.DemuxNode.BranchNode;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.EmptySet;

/* loaded from: classes2.dex */
public final class DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1 implements EventsImpl {
    public final /* synthetic */ Object $key$inlined;
    public final /* synthetic */ DemuxImpl this$0;

    public DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxImpl demuxImpl, Object obj) {
        this.this$0 = demuxImpl;
        this.$key$inlined = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000d  */
    @Override // com.android.systemui.kairos.internal.EventsImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
        Pair pair;
        Pair pair2;
        Object objKeySet;
        DemuxLifecycle demuxLifecycle = this.this$0.dmux;
        Object obj = this.$key$inlined;
        DemuxLifecycleState demuxLifecycleState = demuxLifecycle.lifecycleState;
        if (!(demuxLifecycleState instanceof DemuxLifecycleState.Dead)) {
            if (demuxLifecycleState instanceof DemuxLifecycleState.Active) {
                DemuxLifecycleState.Active active = (DemuxLifecycleState.Active) demuxLifecycleState;
                DemuxNode demuxNode = active.node;
                MutableMapK mutableMapK = demuxNode.branchNodeByKey;
                Object branchNode = mutableMapK.get(obj);
                if (branchNode == null) {
                    branchNode = demuxNode.new BranchNode(obj);
                    mutableMapK.put(obj, branchNode);
                }
                pair2 = new Pair((DemuxNode.BranchNode) branchNode, Boolean.valueOf(active.node.hasCurrentValueLocked(evalScope, obj)));
            } else {
                if (!(demuxLifecycleState instanceof DemuxLifecycleState.Inactive)) {
                    throw new NoWhenBranchMatchedException();
                }
                DemuxActivator demuxActivator = ((DemuxLifecycleState.Inactive) demuxLifecycleState).spec;
                DemuxNode demuxNode2 = new DemuxNode(demuxActivator.storeFactory.create(demuxActivator.numKeys), demuxLifecycle, demuxActivator);
                ActivationResult activationResultActivate = demuxActivator.upstream.activate(evalScope, demuxNode2.schedulable);
                if (activationResultActivate != null) {
                    NodeConnection nodeConnection = activationResultActivate.connection;
                    demuxNode2.upstreamConnection = nodeConnection;
                    if (activationResultActivate.needsEval) {
                        demuxNode2.epoch = evalScope.getEpoch();
                        objKeySet = ((MapK) nodeConnection.directUpstream.getPushEvent(evalScope)).keySet();
                    } else {
                        objKeySet = EmptySet.INSTANCE;
                    }
                    pair = new Pair(demuxNode2, objKeySet);
                } else {
                    pair = null;
                }
                demuxLifecycle.lifecycleState = pair == null ? DemuxLifecycleState.Dead.INSTANCE : new DemuxLifecycleState.Active((DemuxNode) pair.getFirst());
                if (pair != null) {
                    DemuxNode demuxNode3 = (DemuxNode) pair.component1();
                    Set set = (Set) pair.component2();
                    MutableMapK mutableMapK2 = demuxNode3.branchNodeByKey;
                    Object branchNode2 = mutableMapK2.get(obj);
                    if (branchNode2 == null) {
                        branchNode2 = demuxNode3.new BranchNode(obj);
                        mutableMapK2.put(obj, branchNode2);
                    }
                    pair2 = new Pair((DemuxNode.BranchNode) branchNode2, Boolean.valueOf(set.contains(obj)));
                } else {
                    pair2 = null;
                }
            }
        }
        if (pair2 == null) {
            return null;
        }
        DemuxNode.BranchNode branchNode3 = (DemuxNode.BranchNode) pair2.component1();
        boolean zBooleanValue = ((Boolean) pair2.component2()).booleanValue();
        branchNode3.downstreamSet.add(schedulable);
        return new ActivationResult(new NodeConnection(branchNode3, branchNode3), zBooleanValue && DemuxNode.this.hasCurrentValueLocked(evalScope, branchNode3.key));
    }
}
