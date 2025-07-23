package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DerivedZipped extends StateDerived {
    public final MutableMapK.Factory storeFactory;
    public final Init upstream;
    public final int upstreamSize;

    public DerivedZipped(int i, Init init, MutableMapK.Factory factory) {
        super(null);
        this.upstreamSize = i;
        this.upstream = init;
        this.storeFactory = factory;
    }

    @Override // com.android.systemui.kairos.internal.StateDerived
    public final Pair recalc(EvalScope evalScope) {
        MutableMapK create = this.storeFactory.create(Integer.valueOf(this.upstreamSize));
        long j = 0;
        for (Map.Entry entry : (Iterable) this.upstream.connect(evalScope)) {
            Object key = entry.getKey();
            Pair currentWithEpoch = ((StateImpl) entry.getValue()).store.getCurrentWithEpoch(evalScope);
            Object component1 = currentWithEpoch.component1();
            j = Math.max(j, ((Number) currentWithEpoch.component2()).longValue());
            create.put(key, component1);
        }
        return new Pair(create, Long.valueOf(j));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedZipped.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
