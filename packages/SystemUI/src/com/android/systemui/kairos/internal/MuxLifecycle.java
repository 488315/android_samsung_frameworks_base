package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MuxLifecycle implements EventsImpl {
    public MuxLifecycleState lifecycleState;

    public MuxLifecycle(MuxLifecycleState muxLifecycleState) {
        this.lifecycleState = muxLifecycleState;
    }

    @Override // com.android.systemui.kairos.internal.EventsImpl
    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
        MuxLifecycleState muxLifecycleState = this.lifecycleState;
        if (muxLifecycleState instanceof MuxLifecycleState.Dead) {
            return null;
        }
        if (muxLifecycleState instanceof MuxLifecycleState.Active) {
            MuxLifecycleState.Active active = (MuxLifecycleState.Active) muxLifecycleState;
            active.node.downstreamSet.add(schedulable);
            MuxNode muxNode = active.node;
            return new ActivationResult(new NodeConnection(muxNode, muxNode), active.node.transactionCache.epoch == evalScope.getEpoch());
        }
        if (!(muxLifecycleState instanceof MuxLifecycleState.Inactive)) {
            throw new NoWhenBranchMatchedException();
        }
        Pair activate = ((MuxLifecycleState.Inactive) muxLifecycleState).spec.activate(evalScope, this);
        this.lifecycleState = activate == null ? MuxLifecycleState.Dead.INSTANCE : new MuxLifecycleState.Active((MuxNode) activate.getFirst());
        if (activate == null) {
            return null;
        }
        MuxNode muxNode2 = (MuxNode) activate.component1();
        Function0 function0 = (Function0) activate.component2();
        if (function0 != null) {
            function0.invoke();
        }
        muxNode2.downstreamSet.add(schedulable);
        return new ActivationResult(new NodeConnection(muxNode2, muxNode2), false);
    }

    public final String toString() {
        return "MuxLifecycle[" + UtilKt.getHashString(this) + "][" + this.lifecycleState + "]";
    }
}
