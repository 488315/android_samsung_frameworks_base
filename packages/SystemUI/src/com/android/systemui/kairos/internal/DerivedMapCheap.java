package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class DerivedMapCheap extends StateStore {
    public final Function2 transform;
    public final Init upstream;

    public DerivedMapCheap(Init init, Function2 function2) {
        super(null);
        this.upstream = init;
        this.transform = function2;
    }

    @Override // com.android.systemui.kairos.internal.StateStore
    public final Pair getCurrentWithEpoch(EvalScope evalScope) {
        Pair currentWithEpoch = ((StateImpl) this.upstream.connect(evalScope)).store.getCurrentWithEpoch(evalScope);
        return new Pair(this.transform.invoke(evalScope, currentWithEpoch.component1()), Long.valueOf(((Number) currentWithEpoch.component2()).longValue()));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedMapCheap.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
