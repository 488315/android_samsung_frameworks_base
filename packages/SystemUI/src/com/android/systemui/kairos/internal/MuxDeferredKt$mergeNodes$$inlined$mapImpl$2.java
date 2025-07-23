package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$$inlined$mapImpl$2 implements EventsImpl {
    public final /* synthetic */ EventsImpl $mergedThese$inlined;
    public final /* synthetic */ Function3 $transform$inlined;

    public MuxDeferredKt$mergeNodes$$inlined$mapImpl$2(Function3 function3, EventsImpl eventsImpl) {
        this.$transform$inlined = function3;
        this.$mergedThese$inlined = eventsImpl;
    }

    @Override // com.android.systemui.kairos.internal.EventsImpl
    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
        ActivationResult activate = this.$mergedThese$inlined.activate(evalScope, schedulable);
        if (activate == null) {
            return null;
        }
        NodeConnection nodeConnection = activate.connection;
        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, this.$transform$inlined), nodeConnection.schedulerUpstream), activate.needsEval);
    }
}
