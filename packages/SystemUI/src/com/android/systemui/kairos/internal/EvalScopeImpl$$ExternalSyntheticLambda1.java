package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.Transactional;
import com.android.systemui.kairos.internal.TransactionalImpl;
import kotlin.Lazy;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final /* synthetic */ class EvalScopeImpl$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ EvalScopeImpl f$1;

    public /* synthetic */ EvalScopeImpl$$ExternalSyntheticLambda1(EvalScopeImpl evalScopeImpl, Transactional transactional) {
        this.$r8$classId = 1;
        this.f$1 = evalScopeImpl;
        this.f$0 = transactional;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object objDeferAsync;
        Lazy lazy;
        switch (this.$r8$classId) {
            case 0:
                KProperty[] kPropertyArr = EvalScopeImpl.$$delegatedProperties;
                Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = ((State) this.f$0).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                EvalScopeImpl evalScopeImpl = this.f$1;
                return ((StateImpl) init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos.connect(evalScopeImpl)).store.getCurrentWithEpoch(evalScopeImpl).getFirst();
            case 1:
                final EvalScopeImpl evalScopeImpl2 = this.f$1;
                Transactional transactional = (Transactional) this.f$0;
                KProperty[] kPropertyArr2 = EvalScopeImpl.$$delegatedProperties;
                TransactionalImpl transactionalImpl = (TransactionalImpl) evalScopeImpl2.sample(transactional.impl);
                if (transactionalImpl instanceof TransactionalImpl.Const) {
                    lazy = ((TransactionalImpl.Const) transactionalImpl).value;
                } else {
                    if (!(transactionalImpl instanceof TransactionalImpl.Impl)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    TransactionCache transactionCache = ((TransactionalImpl.Impl) transactionalImpl).cache;
                    if (transactionCache.epoch < evalScopeImpl2.$$delegate_0.getEpoch()) {
                        transactionCache.epoch = evalScopeImpl2.$$delegate_0.getEpoch();
                        final TransactionalImpl.Impl impl = (TransactionalImpl.Impl) transactionalImpl;
                        objDeferAsync = evalScopeImpl2.$$delegate_1.deferAsync(new Function0() { // from class: com.android.systemui.kairos.internal.TransactionalImplKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return impl.block.mo781invoke(evalScopeImpl2);
                            }
                        });
                        evalScopeImpl2.$$delegate_0.getTransactionStore().set(transactionCache.key, objDeferAsync);
                    } else {
                        objDeferAsync = evalScopeImpl2.$$delegate_0.getTransactionStore().get(transactionCache.key);
                    }
                    lazy = (Lazy) objDeferAsync;
                }
                return lazy.getValue();
            default:
                KProperty[] kPropertyArr3 = EvalScopeImpl.$$delegatedProperties;
                return ((StateScope$DefaultImpls$$ExternalSyntheticLambda3) this.f$0).mo781invoke(this.f$1);
        }
    }

    public /* synthetic */ EvalScopeImpl$$ExternalSyntheticLambda1(Object obj, EvalScopeImpl evalScopeImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = evalScopeImpl;
    }
}
