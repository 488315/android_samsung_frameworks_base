package com.android.systemui.animation;

import android.util.Log;
import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityTransitionAnimator$Runner$initAndRun$1$success$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $controllerFactory;
    final /* synthetic */ Function1 $performAnimation;
    int label;
    final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator$Runner$initAndRun$1$success$1(ActivityTransitionAnimator.Runner runner, Function1 function1, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.this$0 = runner;
        this.$controllerFactory = function1;
        this.$performAnimation = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTransitionAnimator$Runner$initAndRun$1$success$1(this.this$0, this.$controllerFactory, this.$performAnimation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityTransitionAnimator$Runner$initAndRun$1$success$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        boolean z = true;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActivityTransitionAnimator.Runner runner = this.this$0;
            Function1 function1 = this.$controllerFactory;
            this.label = 1;
            if (ActivityTransitionAnimator.Runner.access$setUp(runner, function1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ActivityTransitionAnimator.Runner runner2 = this.this$0;
        Function1 function12 = this.$performAnimation;
        int i2 = ActivityTransitionAnimator.Runner.$r8$clinit;
        ActivityTransitionAnimator.AnimationDelegate animationDelegate = runner2.delegate;
        if (animationDelegate != null) {
            ActivityTransitionAnimator.this.mainExecutor.execute(new ActivityTransitionAnimator$Runner$startAnimation$1(function12, animationDelegate));
        } else {
            Log.i("ActivityTransitionAnimator", "startAnimation called after completion");
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
