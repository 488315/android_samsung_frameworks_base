package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

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
        return Maybe.Present.m2590boximpl(obj2);
    }
}
