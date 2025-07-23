package com.android.systemui.bouncer.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SimBouncerInteractor$verifySimPuk$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SimBouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimBouncerInteractor$verifySimPuk$1(SimBouncerInteractor simBouncerInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = simBouncerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        SimBouncerInteractor simBouncerInteractor = this.this$0;
        int i = SimBouncerInteractor.$r8$clinit;
        return simBouncerInteractor.verifySimPuk(null, this);
    }
}
