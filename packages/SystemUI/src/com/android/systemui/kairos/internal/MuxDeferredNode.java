package com.android.systemui.kairos.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.MuxNode;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class MuxDeferredNode extends MuxNode {
    public final String name;
    public Iterable patchData;
    public NodeConnection patches;
    public final Schedulable.M schedulable;
    public final MuxActivator spec;

    public MuxDeferredNode(String str, MuxLifecycle muxLifecycle, MuxActivator muxActivator, MutableMapK.Factory factory) {
        super(muxLifecycle, factory, null);
        this.name = str;
        this.spec = muxActivator;
        this.schedulable = new Schedulable.M(this);
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
            NodeConnection nodeConnection2 = this.patches;
            if (nodeConnection2 != null) {
                EventsImplKt.removeDownstreamAndDeactivateIfNeeded(nodeConnection2, this.schedulable);
            }
        }
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final Object getPushEvent(EvalScope evalScope) {
        TransactionCache transactionCache = this.transactionCache;
        transactionCache.getClass();
        MapK mapK = (MapK) evalScope.getTransactionStore().get(transactionCache.key);
        if (this.name != null) {
            toString();
            Objects.toString(mapK);
        }
        return mapK;
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(MuxDeferredNode.class).getSimpleName();
        String hashString = UtilKt.getHashString(this);
        String str = this.name;
        String strM = str != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", str, "]") : null;
        if (strM == null) {
            strM = "";
        }
        return simpleName + "@" + hashString + strM;
    }

    @Override // com.android.systemui.kairos.internal.MuxNode
    public final void visit(EvalScopeImpl evalScopeImpl) {
        if (this.transactionCache.epoch >= evalScopeImpl.$$delegate_0.getEpoch()) {
            throw new IllegalStateException("node unexpectedly visited multiple times in transaction");
        }
        MutableMapK mutableMapK = this.upstreamData;
        if (mutableMapK == null) {
            mutableMapK = null;
        }
        boolean zIsEmpty = mutableMapK.isEmpty();
        MutableMapK mutableMapK2 = this.upstreamData;
        if (mutableMapK2 == null) {
            mutableMapK2 = null;
        }
        MapK onlyCopy = mutableMapK2.readOnlyCopy();
        MutableMapK mutableMapK3 = this.upstreamData;
        (mutableMapK3 != null ? mutableMapK3 : null).clear();
        if (this.name != null) {
            toString();
            Objects.toString(onlyCopy);
        }
        boolean zIsDirty = this.depthTracker.isDirty();
        if (!zIsEmpty || zIsDirty) {
            if (zIsDirty) {
                this.depthTracker.applyChanges(evalScopeImpl.$$delegate_0.getScheduler(), this.downstreamSet, this);
            }
            if (zIsEmpty) {
                return;
            }
            if (this.name != null) {
                toString();
            }
            TransactionCache transactionCache = this.transactionCache;
            transactionCache.getClass();
            transactionCache.epoch = evalScopeImpl.getEpoch();
            evalScopeImpl.getTransactionStore().set(transactionCache.key, onlyCopy);
            if (GraphKt.scheduleAll(this.downstreamSet, evalScopeImpl)) {
                return;
            }
            evalScopeImpl.scheduleDeactivation(this);
        }
    }
}
