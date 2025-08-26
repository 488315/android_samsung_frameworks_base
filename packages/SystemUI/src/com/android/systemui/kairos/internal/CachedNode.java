package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final class CachedNode implements PullNode {
    public final TransactionCache transactionCache;
    public final PullNode upstream;

    public CachedNode(TransactionCache transactionCache, PullNode pullNode) {
        this.transactionCache = transactionCache;
        this.upstream = pullNode;
    }

    @Override // com.android.systemui.kairos.internal.PullNode
    public final Object getPushEvent(final EvalScope evalScope) {
        Object objDeferAsync;
        TransactionCache transactionCache = this.transactionCache;
        if (transactionCache.epoch < evalScope.getEpoch()) {
            transactionCache.epoch = evalScope.getEpoch();
            objDeferAsync = evalScope.deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.CachedNode$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$1.upstream.getPushEvent(evalScope);
                }
            });
            evalScope.getTransactionStore().set(transactionCache.key, objDeferAsync);
        } else {
            objDeferAsync = evalScope.getTransactionStore().get(transactionCache.key);
        }
        return ((Lazy) objDeferAsync).getValue();
    }
}
