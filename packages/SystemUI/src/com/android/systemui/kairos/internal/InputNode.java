package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class InputNode implements PushNode {
    public final Function1 activate;
    public final AtomicBoolean activated;
    public final Function0 deactivate;
    public final DepthTracker depthTracker;
    public final DownstreamSet downstreamSet;
    public final TransactionCache transactionCache;

    /* JADX WARN: Multi-variable type inference failed */
    public InputNode() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void deactivateIfNeeded() {
        if (GraphKt.isEmpty(this.downstreamSet) && this.activated.getAndSet(false)) {
            this.deactivate.invoke();
        }
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final DepthTracker getDepthTracker() {
        return this.depthTracker;
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final Object getPushEvent(EvalScope evalScope) {
        TransactionCache transactionCache = this.transactionCache;
        transactionCache.getClass();
        return evalScope.getTransactionStore().get(transactionCache.key);
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void removeDownstream(Schedulable.N n) {
        this.downstreamSet.remove(n);
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void removeDownstreamAndDeactivateIfNeeded(Schedulable schedulable) {
        DownstreamSet downstreamSet = this.downstreamSet;
        downstreamSet.remove(schedulable);
        if (GraphKt.isEmpty(downstreamSet) && this.activated.getAndSet(false)) {
            this.deactivate.invoke();
        }
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void scheduleDeactivationIfNeeded(EvalScopeImpl evalScopeImpl) {
        if (GraphKt.isEmpty(this.downstreamSet)) {
            evalScopeImpl.scheduleDeactivation(this);
        }
    }

    public InputNode(Function1 function1, Function0 function0) {
        this.activate = function1;
        this.deactivate = function0;
        this.downstreamSet = new DownstreamSet();
        this.activated = new AtomicBoolean(false);
        this.transactionCache = new TransactionCache();
        this.depthTracker = new DepthTracker();
    }

    public /* synthetic */ InputNode(Function1 function1, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new InputNode$$ExternalSyntheticLambda0() : function1, (i & 2) != 0 ? new InputNode$$ExternalSyntheticLambda1() : function0);
    }
}
