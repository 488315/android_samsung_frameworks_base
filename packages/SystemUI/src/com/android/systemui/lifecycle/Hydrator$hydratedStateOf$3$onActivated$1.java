package com.android.systemui.lifecycle;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class Hydrator$hydratedStateOf$3$onActivated$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ Hydrator$hydratedStateOf$3 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Hydrator$hydratedStateOf$3$onActivated$1(Hydrator$hydratedStateOf$3 hydrator$hydratedStateOf$3, Continuation continuation) {
        super(continuation);
        this.this$0 = hydrator$hydratedStateOf$3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.onActivated(this);
    }
}
