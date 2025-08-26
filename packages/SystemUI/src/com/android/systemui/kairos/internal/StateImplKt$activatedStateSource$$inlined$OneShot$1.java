package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class StateImplKt$activatedStateSource$$inlined$OneShot$1 implements Function2 {
    public final /* synthetic */ EventsImpl $calm$inlined;
    public final /* synthetic */ StateSource $store$inlined;

    public StateImplKt$activatedStateSource$$inlined$OneShot$1(EventsImpl eventsImpl, StateSource stateSource) {
        this.$calm$inlined = eventsImpl;
        this.$store$inlined = stateSource;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        EvalScope evalScope = (EvalScope) obj;
        StateSource stateSource = this.$store$inlined;
        ActivationResult activationResultActivate = this.$calm$inlined.activate(evalScope, new Schedulable.S(stateSource));
        if (activationResultActivate != null) {
            stateSource.upstreamConnection = activationResultActivate.connection;
            if (activationResultActivate.needsEval) {
                evalScope.schedule(stateSource);
            }
        }
        return Unit.INSTANCE;
    }
}
