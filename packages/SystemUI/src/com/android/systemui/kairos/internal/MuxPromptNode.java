package com.android.systemui.kairos.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.MuxNode;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.UtilKt;
import com.android.systemui.kairos.util.Maybe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MuxPromptNode extends MuxNode {
    public final String name;
    public Iterable patchData;
    public PatchNode patches;
    public final MuxActivator spec;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PatchNode implements SchedulableNode {
        public final Schedulable.N schedulable = new Schedulable.N(this);
        public NodeConnection upstream;

        public PatchNode() {
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void adjustDirectUpstream(SchedulerImpl schedulerImpl, int i, int i2) {
            Integer valueOf = Integer.valueOf(i);
            MuxPromptNode muxPromptNode = MuxPromptNode.this;
            DepthTracker depthTracker = muxPromptNode.depthTracker;
            if (depthTracker.addDirectUpstream(i2, valueOf)) {
                depthTracker.schedule(schedulerImpl, muxPromptNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void adjustIndirectUpstream(SchedulerImpl schedulerImpl, int i, int i2, SetBuilder setBuilder, SetBuilder setBuilder2) {
            MuxPromptNode.this.adjustIndirectUpstream(schedulerImpl, i, i2, setBuilder, setBuilder2);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void moveDirectUpstreamToIndirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
            MuxPromptNode.this.moveDirectUpstreamToIndirect(i, i2, schedulerImpl, setBuilder);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void moveIndirectUpstreamToDirect(int i, int i2, SchedulerImpl schedulerImpl, SetBuilder setBuilder) {
            MuxPromptNode.this.moveIndirectUpstreamToDirect(i, i2, schedulerImpl, setBuilder);
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void removeDirectUpstream(SchedulerImpl schedulerImpl, int i) {
            MuxPromptNode muxPromptNode = MuxPromptNode.this;
            muxPromptNode.patches = null;
            DepthTracker depthTracker = muxPromptNode.depthTracker;
            if (depthTracker.removeDirectUpstream(i)) {
                depthTracker.schedule(schedulerImpl, muxPromptNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void removeIndirectUpstream(SchedulerImpl schedulerImpl, int i, SetBuilder setBuilder) {
            MuxPromptNode muxPromptNode = MuxPromptNode.this;
            muxPromptNode.patches = null;
            DepthTracker depthTracker = muxPromptNode.depthTracker;
            if (depthTracker.removeIndirectUpstream(i) || DepthTracker.updateIndirectRoots$default(depthTracker, null, setBuilder, null, 5)) {
                depthTracker.schedule(schedulerImpl, muxPromptNode);
            }
        }

        @Override // com.android.systemui.kairos.internal.SchedulableNode
        public final void schedule(EvalScope evalScope) {
            NodeConnection nodeConnection = this.upstream;
            if (nodeConnection == null) {
                nodeConnection = null;
            }
            Iterable iterable = (Iterable) nodeConnection.directUpstream.getPushEvent(evalScope);
            MuxPromptNode muxPromptNode = MuxPromptNode.this;
            muxPromptNode.patchData = iterable;
            muxPromptNode.depthTracker.schedule(evalScope.getScheduler(), muxPromptNode);
        }
    }

    public MuxPromptNode(String str, MuxLifecycle muxLifecycle, MuxActivator muxActivator, MutableMapK.Factory factory) {
        super(muxLifecycle, factory, null);
        this.name = str;
        this.spec = muxActivator;
    }

    @Override // com.android.systemui.kairos.internal.MuxNode
    public final void doDeactivate() {
        MuxLifecycle muxLifecycle = this.lifecycle;
        if (muxLifecycle.lifecycleState instanceof MuxLifecycleState.Active) {
            muxLifecycle.lifecycleState = new MuxLifecycleState.Inactive(this.spec);
            MutableMapK mutableMapK = this.switchedIn;
            if (mutableMapK == null) {
                mutableMapK = null;
            }
            Iterator it = mutableMapK.entrySet().iterator();
            while (it.hasNext()) {
                MuxNode.BranchNode branchNode = (MuxNode.BranchNode) ((Map.Entry) it.next()).getValue();
                NodeConnection nodeConnection = branchNode.upstream;
                if (nodeConnection == null) {
                    nodeConnection = null;
                }
                EventsImplKt.removeDownstreamAndDeactivateIfNeeded(nodeConnection, branchNode.schedulable);
            }
            PatchNode patchNode = this.patches;
            if (patchNode != null) {
                NodeConnection nodeConnection2 = patchNode.upstream;
                EventsImplKt.removeDownstreamAndDeactivateIfNeeded(nodeConnection2 != null ? nodeConnection2 : null, patchNode.schedulable);
            }
        }
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final Object getPushEvent(EvalScope evalScope) {
        TransactionCache transactionCache = this.transactionCache;
        transactionCache.getClass();
        return (MapK) evalScope.getTransactionStore().get(transactionCache.key);
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(MuxPromptNode.class).getSimpleName();
        String hashString = UtilKt.getHashString(this);
        String str = this.name;
        String m = str != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", str, "]") : null;
        if (m == null) {
            m = "";
        }
        return simpleName + "@" + hashString + m;
    }

    @Override // com.android.systemui.kairos.internal.MuxNode
    public final void visit(EvalScopeImpl evalScopeImpl) {
        if (this.transactionCache.epoch >= evalScopeImpl.$$delegate_0.getEpoch()) {
            throw new IllegalStateException("node unexpectedly visited multiple times in transaction");
        }
        Iterable<Map.Entry> iterable = this.patchData;
        this.patchData = null;
        if (iterable != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry entry : iterable) {
                Object key = entry.getKey();
                Maybe maybe = (Maybe) entry.getValue();
                if (maybe instanceof Maybe.Present) {
                    arrayList.add(new Pair(key, ((Maybe.Present) maybe).value));
                } else {
                    if (!Intrinsics.areEqual(maybe, Maybe.Absent.INSTANCE)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    arrayList2.add(key);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            boolean z = false;
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                MutableMapK mutableMapK = this.switchedIn;
                if (mutableMapK == null) {
                    mutableMapK = null;
                }
                MuxNode.BranchNode branchNode = (MuxNode.BranchNode) mutableMapK.remove(obj);
                if (branchNode != null) {
                    if (this.name != null) {
                        toString();
                        Objects.toString(obj);
                    }
                    NodeConnection nodeConnection = branchNode.upstream;
                    if (nodeConnection == null) {
                        nodeConnection = null;
                    }
                    arrayList3.add(nodeConnection);
                    nodeConnection.schedulerUpstream.removeDownstream(branchNode.schedulable);
                    if (nodeConnection.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                        this.depthTracker.removeDirectUpstream(nodeConnection.schedulerUpstream.getDepthTracker().snapshotDirectDepth);
                    } else {
                        this.depthTracker.removeIndirectUpstream(nodeConnection.schedulerUpstream.getDepthTracker().snapshotIndirectDepth);
                        DepthTracker.updateIndirectRoots$default(this.depthTracker, null, CollectionsKt___CollectionsKt.toSet(nodeConnection.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, 5);
                    }
                }
            }
            int size2 = arrayList.size();
            boolean z2 = false;
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList.get(i2);
                i2++;
                Pair pair = (Pair) obj2;
                Object component1 = pair.component1();
                EventsImpl eventsImpl = (EventsImpl) pair.component2();
                MutableMapK mutableMapK2 = this.switchedIn;
                if (mutableMapK2 == null) {
                    mutableMapK2 = null;
                }
                MuxNode.BranchNode branchNode2 = (MuxNode.BranchNode) mutableMapK2.remove(component1);
                if (branchNode2 != null) {
                    if (this.name != null) {
                        toString();
                        Objects.toString(component1);
                    }
                    NodeConnection nodeConnection2 = branchNode2.upstream;
                    if (nodeConnection2 == null) {
                        nodeConnection2 = null;
                    }
                    arrayList3.add(nodeConnection2);
                    nodeConnection2.schedulerUpstream.removeDownstream(branchNode2.schedulable);
                    if (nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                        this.depthTracker.removeDirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotDirectDepth);
                    } else {
                        this.depthTracker.removeIndirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIndirectDepth);
                        DepthTracker.updateIndirectRoots$default(this.depthTracker, null, CollectionsKt___CollectionsKt.toSet(nodeConnection2.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, 5);
                    }
                }
                MuxNode.BranchNode branchNode3 = new MuxNode.BranchNode(component1);
                ActivationResult activate = eventsImpl.activate(evalScopeImpl, branchNode3.schedulable);
                if (activate != null) {
                    NodeConnection nodeConnection3 = activate.connection;
                    boolean z3 = activate.needsEval;
                    branchNode3.upstream = nodeConnection3;
                    if (this.name != null) {
                        toString();
                        Objects.toString(component1);
                    }
                    MutableMapK mutableMapK3 = this.switchedIn;
                    if (mutableMapK3 == null) {
                        mutableMapK3 = null;
                    }
                    mutableMapK3.put(component1, branchNode3);
                    if (z3) {
                        MutableMapK mutableMapK4 = this.upstreamData;
                        if (mutableMapK4 == null) {
                            mutableMapK4 = null;
                        }
                        NodeConnection nodeConnection4 = branchNode3.upstream;
                        if (nodeConnection4 == null) {
                            nodeConnection4 = null;
                        }
                        mutableMapK4.put(component1, nodeConnection4.directUpstream);
                    } else {
                        z2 = true;
                    }
                    NodeConnection nodeConnection5 = branchNode3.upstream;
                    if (nodeConnection5 == null) {
                        nodeConnection5 = null;
                    }
                    DepthTracker depthTracker = nodeConnection5.schedulerUpstream.getDepthTracker();
                    if (depthTracker.snapshotIsDirect) {
                        this.depthTracker.addDirectUpstream(depthTracker.snapshotDirectDepth, null);
                    } else {
                        this.depthTracker.addIndirectUpstream(depthTracker.snapshotIndirectDepth, null);
                        DepthTracker.updateIndirectRoots$default(this.depthTracker, CollectionsKt___CollectionsKt.toSet(depthTracker._snapshotIndirectRoots), null, null, 2);
                    }
                }
            }
            int size3 = arrayList3.size();
            int i3 = 0;
            while (i3 < size3) {
                Object obj3 = arrayList3.get(i3);
                i3++;
                ((NodeConnection) obj3).schedulerUpstream.scheduleDeactivationIfNeeded(evalScopeImpl);
            }
            DepthTracker depthTracker2 = this.depthTracker;
            if (depthTracker2.snapshotDirectDepth < depthTracker2.dirty_directDepth || (!depthTracker2.snapshotIsDirect && !depthTracker2.dirty_directUpstreamDepths.isEmpty())) {
                z = true;
            }
            if (z2 || z) {
                if (z) {
                    this.depthTracker.schedule(evalScopeImpl.$$delegate_0.getCompactor(), this);
                }
                if (this.name != null) {
                    toString();
                }
                this.depthTracker.schedule(evalScopeImpl.$$delegate_0.getScheduler(), this);
                return;
            }
        }
        MutableMapK mutableMapK5 = this.upstreamData;
        if (mutableMapK5 == null) {
            mutableMapK5 = null;
        }
        MapK readOnlyCopy = mutableMapK5.readOnlyCopy();
        MutableMapK mutableMapK6 = this.upstreamData;
        (mutableMapK6 != null ? mutableMapK6 : null).clear();
        boolean isEmpty = readOnlyCopy.isEmpty();
        boolean isDirty = this.depthTracker.isDirty();
        if (!isEmpty || isDirty) {
            if (isDirty) {
                DepthTracker depthTracker3 = this.depthTracker;
                if (depthTracker3.snapshotDirectDepth < depthTracker3.dirty_directDepth || !(depthTracker3.snapshotIsDirect || depthTracker3.dirty_directUpstreamDepths.isEmpty())) {
                    depthTracker3.schedule(evalScopeImpl.$$delegate_0.getCompactor(), this);
                } else if (depthTracker3.isDirty()) {
                    depthTracker3.applyChanges(evalScopeImpl.$$delegate_0.getScheduler(), this.downstreamSet, this);
                }
            }
            if (isEmpty) {
                return;
            }
            TransactionCache transactionCache = this.transactionCache;
            transactionCache.getClass();
            transactionCache.epoch = evalScopeImpl.getEpoch();
            evalScopeImpl.getTransactionStore().set(transactionCache.key, readOnlyCopy);
            if (GraphKt.scheduleAll(this.downstreamSet, evalScopeImpl)) {
                return;
            }
            evalScopeImpl.scheduleDeactivation(this);
        }
    }
}
