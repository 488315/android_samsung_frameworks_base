package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StateImplKt$calm$$inlined$filterImpl$1 implements Function3 {
    public final /* synthetic */ StateDerived $state$inlined;

    public StateImplKt$calm$$inlined$filterImpl$1(StateDerived stateDerived) {
        this.$state$inlined = stateDerived;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EvalScope evalScope = (EvalScope) obj;
        ((Number) obj3).intValue();
        StateDerived stateDerived = this.$state$inlined;
        if (Intrinsics.areEqual(obj2, stateDerived.getCurrentWithEpoch(evalScope).component1())) {
            Maybe.Companion.getClass();
            return Maybe.Companion.absent;
        }
        long epoch = evalScope.getEpoch();
        stateDerived.cache = obj2;
        long j = epoch + 1;
        stateDerived.validatedEpoch = j;
        stateDerived.invalidatedEpoch = j;
        Maybe.Companion.getClass();
        return Maybe.Present.m2573boximpl(obj2);
    }
}
