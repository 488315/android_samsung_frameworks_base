package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class StateImplKt$calm$$inlined$filterImpl$2 implements EventsImpl {
    public final /* synthetic */ EventsImpl $this_calm$inlined;
    public final /* synthetic */ Function3 $transform$inlined;

    public StateImplKt$calm$$inlined$filterImpl$2(Function3 function3, EventsImpl eventsImpl) {
        this.$transform$inlined = function3;
        this.$this_calm$inlined = eventsImpl;
    }

    @Override // com.android.systemui.kairos.internal.EventsImpl
    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
        ActivationResult activationResultActivate = this.$this_calm$inlined.activate(evalScope, schedulable);
        if (activationResultActivate == null) {
            return null;
        }
        NodeConnection nodeConnection = activationResultActivate.connection;
        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, this.$transform$inlined), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
    }
}
