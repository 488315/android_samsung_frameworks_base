package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityTransitionAnimator$Runner$setUp$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator$Runner$setUp$1(ActivityTransitionAnimator.Runner runner, Continuation continuation) {
        super(continuation);
        this.this$0 = runner;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ActivityTransitionAnimator.Runner.access$setUp(this.this$0, null, this);
    }
}
