package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlwaysNode implements PushNode {
    public static final AlwaysNode INSTANCE = new AlwaysNode();
    public static final DepthTracker depthTracker = new DepthTracker();

    private AlwaysNode() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof AlwaysNode);
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final DepthTracker getDepthTracker() {
        return depthTracker;
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final /* bridge */ /* synthetic */ Object getPushEvent(EvalScope evalScope) {
        return Unit.INSTANCE;
    }

    public final int hashCode() {
        return 1518855222;
    }

    public final String toString() {
        return "AlwaysNode";
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void removeDownstream(Schedulable.N n) {
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void removeDownstreamAndDeactivateIfNeeded(Schedulable schedulable) {
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void scheduleDeactivationIfNeeded(EvalScopeImpl evalScopeImpl) {
    }

    @Override // com.android.systemui.kairos.internal.PushNode
    public final void deactivateIfNeeded() {
    }
}
