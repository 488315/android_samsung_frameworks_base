package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;

/* loaded from: classes2.dex */
public interface PushNode extends PullNode {
    void deactivateIfNeeded();

    DepthTracker getDepthTracker();

    void removeDownstream(Schedulable.N n);

    void removeDownstreamAndDeactivateIfNeeded(Schedulable schedulable);

    void scheduleDeactivationIfNeeded(EvalScopeImpl evalScopeImpl);
}
