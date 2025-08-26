package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class StateDerived extends StateStore {
    public volatile Object cache;
    public volatile long invalidatedEpoch;
    public final TransactionCache transactionCache;
    public volatile long validatedEpoch;

    public final class EmptyCache {
        public static final EmptyCache INSTANCE = new EmptyCache();

        private EmptyCache() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof EmptyCache);
        }

        public final int hashCode() {
            return 2079003090;
        }

        public final String toString() {
            return "EmptyCache";
        }
    }

    public /* synthetic */ StateDerived(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // com.android.systemui.kairos.internal.StateStore
    public final Pair getCurrentWithEpoch(final EvalScope evalScope) {
        Object objDeferAsync;
        TransactionCache transactionCache = this.transactionCache;
        if (transactionCache.epoch < evalScope.getEpoch()) {
            transactionCache.epoch = evalScope.getEpoch();
            objDeferAsync = evalScope.deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.StateDerived$$ExternalSyntheticLambda0
                /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
                @Override // kotlin.jvm.functions.Function0
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke() {
                    Object objComponent1;
                    StateDerived stateDerived = this.f$0;
                    Pair pairRecalc = stateDerived.recalc(evalScope);
                    if (pairRecalc != null) {
                        objComponent1 = pairRecalc.component1();
                        long jLongValue = ((Number) pairRecalc.component2()).longValue();
                        if (jLongValue > stateDerived.validatedEpoch) {
                            stateDerived.validatedEpoch = jLongValue;
                            if (!Intrinsics.areEqual(stateDerived.cache, objComponent1)) {
                                stateDerived.cache = objComponent1;
                                stateDerived.invalidatedEpoch = jLongValue;
                            }
                        }
                        if (objComponent1 == null) {
                            objComponent1 = stateDerived.cache;
                        }
                    }
                    return new Pair(objComponent1, Long.valueOf(stateDerived.invalidatedEpoch));
                }
            });
            evalScope.getTransactionStore().set(transactionCache.key, objDeferAsync);
        } else {
            objDeferAsync = evalScope.getTransactionStore().get(transactionCache.key);
        }
        return (Pair) ((Lazy) objDeferAsync).getValue();
    }

    public abstract Pair recalc(EvalScope evalScope);

    private StateDerived() {
        super(null);
        this.invalidatedEpoch = Long.MIN_VALUE;
        this.validatedEpoch = Long.MIN_VALUE;
        this.cache = EmptyCache.INSTANCE;
        this.transactionCache = new TransactionCache();
    }
}
