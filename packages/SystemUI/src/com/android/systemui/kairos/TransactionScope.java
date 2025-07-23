package com.android.systemui.kairos;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TransactionScope extends KairosScope {
    DeferredValue deferredTransactionScope(StateScope$DefaultImpls$$ExternalSyntheticLambda3 stateScope$DefaultImpls$$ExternalSyntheticLambda3);

    Events getNow();

    Object sample(State state);

    Object sample(Transactional transactional);

    DeferredValue sampleDeferred(State state);
}
