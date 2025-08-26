package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Objects;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class MuxNode implements PushNode {
    public final DepthTracker depthTracker;
    public final DownstreamSet downstreamSet;
    public final MuxLifecycle lifecycle;
    public volatile boolean markedForCompaction;
    public volatile boolean markedForEvaluation;
    public MutableMapK switchedIn;
    public final TransactionCache transactionCache;
    public MutableMapK upstreamData;

    public final class BranchNode implements SchedulableNode {
        public final Object key;
        public final Schedulable.N schedulable = new Schedulable.N(this);
        public NodeConnection upstream;

        public BranchNode(Object obj) {
            this.key = obj;
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void adjustDirectUpstream(SchedulerImpl schedulerImpl, int i, int i2) {
            Integer numValueOf = Integer.valueOf(i);
            MuxNode muxNode = MuxNode.this;
            DepthTracker depthTracker = muxNode.depthTracker;
            if (depthTracker.addDirectUpstream(i2, numValueOf)) {
                depthTracker.schedule(schedulerImpl, muxNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2) {
            MuxNode.this.adjustIndirectUpstream(schedulerImpl, i, i2, setBuilder, setBuilder2);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
            MuxNode.this.moveDirectUpstreamToIndirect(i, i2, schedulerImpl, setBuilder);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
            MuxNode.this.moveIndirectUpstreamToDirect(i, i2, schedulerImpl, setBuilder);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void removeDirectUpstream(SchedulerImpl schedulerImpl, int i) {
            MuxNode muxNode = MuxNode.this;
            MutableMapK mutableMapK = muxNode.switchedIn;
            if (mutableMapK == null) {
                mutableMapK = null;
            }
            mutableMapK.remove(this.key);
            DepthTracker depthTracker = muxNode.depthTracker;
            if (depthTracker.removeDirectUpstream(i)) {
                depthTracker.schedule(schedulerImpl, muxNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void removeIndirectUpstream(SchedulerImpl schedulerImpl, int i, SetBuilder setBuilder) {
            MuxNode muxNode = MuxNode.this;
            MutableMapK mutableMapK = muxNode.switchedIn;
            if (mutableMapK == null) {
                mutableMapK = null;
            }
            mutableMapK.remove(this.key);
            DepthTracker depthTracker = muxNode.depthTracker;
            if (depthTracker.removeIndirectUpstream(i) || DepthTracker.updateIndirectRoots$default(depthTracker, null, setBuilder, null, 5)) {
                depthTracker.schedule(schedulerImpl, muxNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void schedule(EvalScope evalScope) {
            MuxNode muxNode = MuxNode.this;
            boolean z = muxNode instanceof MuxPromptNode;
            Object obj = this.key;
            if (z && ((MuxPromptNode) muxNode).name != null) {
                Objects.toString(muxNode);
                Objects.toString(obj);
            }
            MutableMapK mutableMapK = muxNode.upstreamData;
            if (mutableMapK == null) {
                mutableMapK = null;
            }
            NodeConnection nodeConnection = this.upstream;
            mutableMapK.put(obj, (nodeConnection != null ? nodeConnection : null).directUpstream);
            muxNode.depthTracker.schedule(evalScope.getScheduler(), muxNode);
        }

        public final String toString() {
            return "MuxBranchNode(key=" + this.key + ", mux=" + MuxNode.this + ")";
        }
    }

    public /* synthetic */ MuxNode(MuxLifecycle muxLifecycle, MutableMapK.Factory factory, DefaultConstructorMarker defaultConstructorMarker) {
        this(muxLifecycle, factory);
    }

    public final void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2) {
        Integer numValueOf = Integer.valueOf(i);
        DepthTracker depthTracker = this.depthTracker;
        if (depthTracker.addIndirectUpstream(i2, numValueOf) || depthTracker.updateIndirectRoots(setBuilder2, setBuilder, this instanceof MuxDeferredNode ? (MuxDeferredNode) this : null)) {
            depthTracker.schedule(schedulerImpl, this);
        }
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void deactivateIfNeeded() {
        if (GraphKt.isEmpty(this.downstreamSet)) {
            doDeactivate();
        }
    }

    public abstract void doDeactivate();

    @Override // com.android.systemui.kairos.internal.PushNode
    public final DepthTracker getDepthTracker() {
        return this.depthTracker;
    }

    public final void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        DepthTracker depthTracker = this.depthTracker;
        if ((depthTracker.removeDirectUpstream(i) | depthTracker.addIndirectUpstream(i2, null)) || DepthTracker.updateIndirectRoots$default(depthTracker, setBuilder, null, this instanceof MuxDeferredNode ? (MuxDeferredNode) this : null, 2)) {
            depthTracker.schedule(schedulerImpl, this);
        }
    }

    public final void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
        DepthTracker depthTracker = this.depthTracker;
        if ((depthTracker.removeIndirectUpstream(i) | depthTracker.addDirectUpstream(i2, null)) || DepthTracker.updateIndirectRoots$default(depthTracker, null, setBuilder, null, 5)) {
            depthTracker.schedule(schedulerImpl, this);
        }
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
            doDeactivate();
        }
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void scheduleDeactivationIfNeeded(EvalScopeImpl evalScopeImpl) {
        if (GraphKt.isEmpty(this.downstreamSet)) {
            evalScopeImpl.scheduleDeactivation(this);
        }
    }

    public abstract void visit(EvalScopeImpl evalScopeImpl);

    private MuxNode(MuxLifecycle muxLifecycle, MutableMapK.Factory factory) {
        this.lifecycle = muxLifecycle;
        this.downstreamSet = new DownstreamSet();
        this.depthTracker = new DepthTracker();
        this.transactionCache = new TransactionCache();
    }
}
