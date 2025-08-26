package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

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
        Pair currentWithEpoch = ((StateImpl) this.upstream.mo781invoke(evalScope)).store.getCurrentWithEpoch(evalScope);
        Object objComponent1 = currentWithEpoch.component1();
        long jLongValue = ((Number) currentWithEpoch.component2()).longValue();
        if (jLongValue > this.validatedEpoch) {
            return new Pair(this.transform.invoke(evalScope, objComponent1), Long.valueOf(jLongValue));
        }
        return null;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedMap.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
