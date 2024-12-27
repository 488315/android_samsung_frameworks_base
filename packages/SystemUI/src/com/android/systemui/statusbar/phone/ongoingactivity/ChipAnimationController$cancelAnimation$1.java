package com.android.systemui.statusbar.phone.ongoingactivity;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ChipAnimationController$cancelAnimation$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ChipAnimationController this$0;

    public ChipAnimationController$cancelAnimation$1(ChipAnimationController chipAnimationController, Continuation continuation) {
        super(continuation);
        this.this$0 = chipAnimationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChipAnimationController.access$cancelAnimation(this.this$0, null, this);
    }
}
