package com.android.systemui.keyguard.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    public KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getAffordancePickerRepresentations(this);
    }
}
