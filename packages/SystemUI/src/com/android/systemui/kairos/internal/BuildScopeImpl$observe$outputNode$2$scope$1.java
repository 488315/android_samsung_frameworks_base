package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.EffectScope;
import com.android.systemui.kairos.LocalNetwork;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.kairos.Transactional;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DeferredCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildScopeImpl$observe$outputNode$2$scope$1 implements EffectScope, TransactionScope {
    public final /* synthetic */ EvalScope $$delegate_0;
    public final /* synthetic */ CoroutineScope $childScope;
    public final /* synthetic */ LocalNetwork $localNetwork;

    public BuildScopeImpl$observe$outputNode$2$scope$1(EvalScope evalScope, CoroutineScope coroutineScope, LocalNetwork localNetwork) {
        this.$childScope = coroutineScope;
        this.$localNetwork = localNetwork;
        this.$$delegate_0 = evalScope;
    }

    public final DeferredCoroutine async(CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2) {
        return BuildersKt.async(this.$childScope, coroutineContext, coroutineStart, new BuildScopeImpl$observe$outputNode$2$scope$1$async$1(function2, this.$localNetwork, null));
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(State state) {
        return this.$$delegate_0.sample(state);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final DeferredValue sampleDeferred(State state) {
        return this.$$delegate_0.sampleDeferred(state);
    }

    @Override // com.android.systemui.kairos.TransactionScope
    public final Object sample(Transactional transactional) {
        return this.$$delegate_0.sample(transactional);
    }
}
