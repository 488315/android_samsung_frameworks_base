package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.DemuxLifecycleState;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.SetBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DemuxNode implements SchedulableNode {
    public final MutableMapK branchNodeByKey;
    public final DemuxLifecycle lifecycle;
    public final DemuxActivator spec;
    public NodeConnection upstreamConnection;
    public final Schedulable.N schedulable = new Schedulable.N(this);
    public volatile long epoch = Long.MIN_VALUE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BranchNode implements PushNode {
        public final DownstreamSet downstreamSet = new DownstreamSet();
        public final Object key;

        public BranchNode(Object obj) {
            this.key = obj;
        }

        @Override // com.android.systemui.kairos.internal.PushNode
        public final void deactivateIfNeeded() {
            if (GraphKt.isEmpty(this.downstreamSet)) {
                DemuxNode.this.removeDownstreamAndDeactivateIfNeeded(this.key);
            }
        }

        @Override // com.android.systemui.kairos.internal.PushNode
        public final DepthTracker getDepthTracker() {
            NodeConnection nodeConnection = DemuxNode.this.upstreamConnection;
            if (nodeConnection == null) {
                nodeConnection = null;
            }
            return nodeConnection.schedulerUpstream.getDepthTracker();
        }

        @Override // com.android.systemui.kairos.internal.PullNode
        public final Object getPushEvent(EvalScope evalScope) {
            DemuxNode demuxNode = DemuxNode.this;
            demuxNode.getClass();
            Object obj = this.key;
            Objects.toString(obj);
            NodeConnection nodeConnection = demuxNode.upstreamConnection;
            if (nodeConnection == null) {
                nodeConnection = null;
            }
            return MapsKt__MapsKt.getValue(obj, (Map) nodeConnection.directUpstream.getPushEvent(evalScope));
        }

        @Override // com.android.systemui.kairos.internal.PushNode
        public final void removeDownstream(Schedulable.N n) {
            this.downstreamSet.remove(n);
        }

        @Override // com.android.systemui.kairos.internal.PushNode
        public final void removeDownstreamAndDeactivateIfNeeded(Schedulable schedulable) {
            DownstreamSet downstreamSet = this.downstreamSet;
            downstreamSet.remove(schedulable);
            if (GraphKt.isEmpty(downstreamSet)) {
                DemuxNode.this.removeDownstreamAndDeactivateIfNeeded(this.key);
            }
        }

        @Override // com.android.systemui.kairos.internal.PushNode
        public final void scheduleDeactivationIfNeeded(EvalScopeImpl evalScopeImpl) {
            if (GraphKt.isEmpty(this.downstreamSet)) {
                evalScopeImpl.scheduleDeactivation(this);
            }
        }
    }

    public DemuxNode(MutableMapK mutableMapK, DemuxLifecycle demuxLifecycle, DemuxActivator demuxActivator) {
        this.branchNodeByKey = mutableMapK;
        this.lifecycle = demuxLifecycle;
        this.spec = demuxActivator;
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void adjustDirectUpstream(SchedulerImpl schedulerImpl, int i, int i2) {
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.nodes.iterator();
            while (it2.hasNext()) {
                ((SchedulableNode) it2.next()).adjustDirectUpstream(schedulerImpl, i, i2);
            }
        }
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2) {
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            SchedulerImpl schedulerImpl2 = schedulerImpl;
            int i3 = i;
            int i4 = i2;
            SetBuilder setBuilder3 = setBuilder;
            SetBuilder setBuilder4 = setBuilder2;
            ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.adjustIndirectUpstream(schedulerImpl2, i3, i4, setBuilder3, setBuilder4);
            schedulerImpl = schedulerImpl2;
            i = i3;
            i2 = i4;
            setBuilder = setBuilder3;
            setBuilder2 = setBuilder4;
        }
    }

    public final boolean hasCurrentValueLocked(EvalScope evalScope, Object obj) {
        if (evalScope.getEpoch() != this.epoch) {
            return false;
        }
        NodeConnection nodeConnection = this.upstreamConnection;
        if (nodeConnection == null) {
            nodeConnection = null;
        }
        return ((Map) nodeConnection.directUpstream.getPushEvent(evalScope)).containsKey(obj);
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.moveDirectUpstreamToIndirect(i, i2, schedulerImpl, setBuilder);
        }
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.moveIndirectUpstreamToDirect(i, i2, schedulerImpl, setBuilder);
        }
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void removeDirectUpstream(SchedulerImpl schedulerImpl, int i) {
        this.lifecycle.lifecycleState = DemuxLifecycleState.Dead.INSTANCE;
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.removeDirectUpstream(schedulerImpl, i);
        }
    }

    public final void removeDownstreamAndDeactivateIfNeeded(Object obj) {
        this.branchNodeByKey.remove(obj);
        if (this.branchNodeByKey.isEmpty()) {
            this.lifecycle.lifecycleState = new DemuxLifecycleState.Inactive(this.spec);
            NodeConnection nodeConnection = this.upstreamConnection;
            if (nodeConnection == null) {
                nodeConnection = null;
            }
            EventsImplKt.removeDownstreamAndDeactivateIfNeeded(nodeConnection, this.schedulable);
        }
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void removeIndirectUpstream(SchedulerImpl schedulerImpl, int i, SetBuilder setBuilder) {
        this.lifecycle.lifecycleState = DemuxLifecycleState.Dead.INSTANCE;
        Iterator it = this.branchNodeByKey.entrySet().iterator();
        while (it.hasNext()) {
            ((BranchNode) ((Map.Entry) it.next()).getValue()).downstreamSet.removeIndirectUpstream(schedulerImpl, i, setBuilder);
        }
    }

    @Override // com.android.systemui.kairos.internal.SchedulableNode
    public final void schedule(EvalScope evalScope) {
        NodeConnection nodeConnection = this.upstreamConnection;
        if (nodeConnection == null) {
            nodeConnection = null;
        }
        MapK mapK = (MapK) nodeConnection.directUpstream.getPushEvent(evalScope);
        this.epoch = evalScope.getEpoch();
        Iterator it = mapK.entrySet().iterator();
        while (it.hasNext()) {
            Object key = ((Map.Entry) it.next()).getKey();
            MutableMapK mutableMapK = this.branchNodeByKey;
            if (mutableMapK.containsKey(key)) {
                BranchNode branchNode = (BranchNode) MapsKt__MapsKt.getValue(key, mutableMapK);
                Objects.toString(branchNode.key);
                if (!GraphKt.scheduleAll(branchNode.downstreamSet, evalScope)) {
                    evalScope.scheduleDeactivation(branchNode);
                }
            }
        }
    }
}
