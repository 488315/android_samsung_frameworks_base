package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    public KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getSlotPickerRepresentations(this);
    }
}
