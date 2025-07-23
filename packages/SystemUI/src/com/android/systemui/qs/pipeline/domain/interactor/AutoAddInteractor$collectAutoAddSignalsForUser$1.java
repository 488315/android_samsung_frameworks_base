package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AutoAddInteractor$collectAutoAddSignalsForUser$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AutoAddInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoAddInteractor$collectAutoAddSignalsForUser$1(AutoAddInteractor autoAddInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = autoAddInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return AutoAddInteractor.access$collectAutoAddSignalsForUser(this.this$0, null, 0, this);
    }
}
