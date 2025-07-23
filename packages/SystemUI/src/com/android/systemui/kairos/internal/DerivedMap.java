package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DerivedMap extends StateDerived {
    public final Function2 transform;
    public final Function1 upstream;

    public DerivedMap(Function1 function1, Function2 function2) {
        super(null);
        this.upstream = function1;
        this.transform = function2;
    }

    @Override // com.android.systemui.kairos.internal.StateDerived
    public final Pair recalc(EvalScope evalScope) {
        Pair currentWithEpoch = ((StateImpl) this.upstream.mo779invoke(evalScope)).store.getCurrentWithEpoch(evalScope);
        Object component1 = currentWithEpoch.component1();
        long longValue = ((Number) currentWithEpoch.component2()).longValue();
        if (longValue > this.validatedEpoch) {
            return new Pair(this.transform.invoke(evalScope, component1), Long.valueOf(longValue));
        }
        return null;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedMap.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
