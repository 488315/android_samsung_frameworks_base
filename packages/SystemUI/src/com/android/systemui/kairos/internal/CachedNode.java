package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object obj;
        TransactionCache transactionCache = this.transactionCache;
        if (transactionCache.epoch < evalScope.getEpoch()) {
            transactionCache.epoch = evalScope.getEpoch();
            obj = evalScope.deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.CachedNode$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return CachedNode.this.upstream.getPushEvent(evalScope);
                }
            });
            evalScope.getTransactionStore().set(transactionCache.key, obj);
        } else {
            obj = evalScope.getTransactionStore().get(transactionCache.key);
        }
        return ((Lazy) obj).getValue();
    }
}
