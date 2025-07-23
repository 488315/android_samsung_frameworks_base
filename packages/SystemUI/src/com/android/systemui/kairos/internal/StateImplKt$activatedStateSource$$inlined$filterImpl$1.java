package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StateImplKt$activatedStateSource$$inlined$filterImpl$1 implements Function3 {
    public final /* synthetic */ StateSource $store$inlined;

    public StateImplKt$activatedStateSource$$inlined$filterImpl$1(StateSource stateSource) {
        this.$store$inlined = stateSource;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        if (Intrinsics.areEqual(obj2, this.$store$inlined.getCurrentWithEpoch((EvalScope) obj).getFirst())) {
            Maybe.Companion.getClass();
            return Maybe.Companion.absent;
        }
        Maybe.Companion.getClass();
        return Maybe.Present.m2573boximpl(obj2);
    }
}
