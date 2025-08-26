package com.android.compose.animation.scene;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class SwipeAnimation$animateOffset$4 extends ContinuationImpl {
    float F$0;
    float F$1;
    float F$2;
    float F$3;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SwipeAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SwipeAnimation$animateOffset$4(SwipeAnimation swipeAnimation, Continuation continuation) {
        super(continuation);
        this.this$0 = swipeAnimation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return SwipeAnimation.access$animateOffset(this.this$0, null, 0.0f, 0.0f, null, this);
    }
}
