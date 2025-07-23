package com.android.keyguard.emm;

import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EngineeringModeManagerWrapper$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ EngineeringModeManagerWrapper.AnonymousClass1.C00211 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$1$1$emit$1(EngineeringModeManagerWrapper.AnonymousClass1.C00211 c00211, Continuation continuation) {
        super(continuation);
        this.this$0 = c00211;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(false, (Continuation) this);
    }
}
