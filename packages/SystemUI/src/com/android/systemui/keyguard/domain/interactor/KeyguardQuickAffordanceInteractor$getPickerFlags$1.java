package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardQuickAffordanceInteractor$getPickerFlags$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    public KeyguardQuickAffordanceInteractor$getPickerFlags$1(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getPickerFlags(this);
    }
}
