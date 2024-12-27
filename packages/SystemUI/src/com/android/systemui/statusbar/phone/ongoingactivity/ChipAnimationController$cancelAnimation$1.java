package com.android.systemui.statusbar.phone.ongoingactivity;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class ChipAnimationController$cancelAnimation$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ChipAnimationController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
