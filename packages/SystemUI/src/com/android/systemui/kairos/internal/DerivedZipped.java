package com.android.systemui.kairos.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.internal.Reflection;

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
        MutableMapK mutableMapKCreate = this.storeFactory.create(Integer.valueOf(this.upstreamSize));
        long jMax = 0;
        for (Map.Entry entry : (Iterable) this.upstream.connect(evalScope)) {
            Object key = entry.getKey();
            Pair currentWithEpoch = ((StateImpl) entry.getValue()).store.getCurrentWithEpoch(evalScope);
            Object objComponent1 = currentWithEpoch.component1();
            jMax = Math.max(jMax, ((Number) currentWithEpoch.component2()).longValue());
            mutableMapKCreate.put(key, objComponent1);
        }
        return new Pair(mutableMapKCreate, Long.valueOf(jMax));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(DerivedZipped.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
