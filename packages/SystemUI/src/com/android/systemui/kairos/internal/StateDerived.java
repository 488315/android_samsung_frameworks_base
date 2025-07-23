package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StateDerived extends StateStore {
    public volatile Object cache;
    public volatile long invalidatedEpoch;
    public final TransactionCache transactionCache;
    public volatile long validatedEpoch;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object obj;
        TransactionCache transactionCache = this.transactionCache;
        if (transactionCache.epoch < evalScope.getEpoch()) {
            transactionCache.epoch = evalScope.getEpoch();
            obj = evalScope.deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.StateDerived$$ExternalSyntheticLambda0
                /* JADX WARN: Code restructure failed: missing block: B:8:0x002c, code lost:
                
                    if (r1 == null) goto L10;
                 */
                @Override // kotlin.jvm.functions.Function0
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke() {
                    /*
                        r6 = this;
                        com.android.systemui.kairos.internal.StateDerived r0 = com.android.systemui.kairos.internal.StateDerived.this
                        com.android.systemui.kairos.internal.EvalScope r6 = r2
                        kotlin.Pair r6 = r0.recalc(r6)
                        if (r6 == 0) goto L2e
                        java.lang.Object r1 = r6.component1()
                        java.lang.Object r6 = r6.component2()
                        java.lang.Number r6 = (java.lang.Number) r6
                        long r2 = r6.longValue()
                        long r4 = r0.validatedEpoch
                        int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                        if (r6 <= 0) goto L2c
                        r0.validatedEpoch = r2
                        java.lang.Object r6 = r0.cache
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r1)
                        if (r6 != 0) goto L2c
                        r0.cache = r1
                        r0.invalidatedEpoch = r2
                    L2c:
                        if (r1 != 0) goto L30
                    L2e:
                        java.lang.Object r1 = r0.cache
                    L30:
                        long r2 = r0.invalidatedEpoch
                        java.lang.Long r6 = java.lang.Long.valueOf(r2)
                        kotlin.Pair r0 = new kotlin.Pair
                        r0.<init>(r1, r6)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.StateDerived$$ExternalSyntheticLambda0.invoke():java.lang.Object");
                }
            });
            evalScope.getTransactionStore().set(transactionCache.key, obj);
        } else {
            obj = evalScope.getTransactionStore().get(transactionCache.key);
        }
        return (Pair) ((Lazy) obj).getValue();
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
