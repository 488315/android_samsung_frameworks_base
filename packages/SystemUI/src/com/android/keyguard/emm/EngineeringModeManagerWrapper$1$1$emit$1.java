package com.android.keyguard.emm;

import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class EngineeringModeManagerWrapper$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ EngineeringModeManagerWrapper.AnonymousClass1.C00481 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$1$1$emit$1(EngineeringModeManagerWrapper.AnonymousClass1.C00481 c00481, Continuation continuation) {
        super(continuation);
        this.this$0 = c00481;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(false, (Continuation) this);
    }
}
