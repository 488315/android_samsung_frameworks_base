package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DerivedFlatten extends StateDerived {
    public final Function1 upstream;

    public DerivedFlatten(Function1 function1) {
        super(null);
        this.upstream = function1;
    }

    @Override // com.android.systemui.kairos.internal.StateDerived
    public final Pair recalc(EvalScope evalScope) {
        Pair currentWithEpoch = ((StateImpl) this.upstream.mo779invoke(evalScope)).store.getCurrentWithEpoch(evalScope);
        StateImpl stateImpl = (StateImpl) currentWithEpoch.component1();
        long longValue = ((Number) currentWithEpoch.component2()).longValue();
        Pair currentWithEpoch2 = stateImpl.store.getCurrentWithEpoch(evalScope);
        return new Pair(currentWithEpoch2.component1(), Long.valueOf(Math.max(longValue, ((Number) currentWithEpoch2.component2()).longValue())));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedFlatten.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
