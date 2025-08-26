package com.android.systemui.kairos;

/* loaded from: classes2.dex */
public interface TransactionScope extends KairosScope {
    DeferredValue deferredTransactionScope(StateScope$DefaultImpls$$ExternalSyntheticLambda3 stateScope$DefaultImpls$$ExternalSyntheticLambda3);

    Events getNow();

    Object sample(State state);

    Object sample(Transactional transactional);

    DeferredValue sampleDeferred(State state);
}
