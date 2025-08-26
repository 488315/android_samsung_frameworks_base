package com.android.systemui.animation;

import android.view.IRemoteAnimationFinishedCallback;
import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutKt;

/* loaded from: classes.dex */
final class ActivityTransitionAnimator$Runner$initAndRun$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $controllerFactory;
    final /* synthetic */ IRemoteAnimationFinishedCallback $finishedCallback;
    final /* synthetic */ Function1 $performAnimation;
    int label;
    final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator$Runner$initAndRun$1(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, ActivityTransitionAnimator.Runner runner, Function1 function1, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.$finishedCallback = iRemoteAnimationFinishedCallback;
        this.this$0 = runner;
        this.$controllerFactory = function1;
        this.$performAnimation = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTransitionAnimator$Runner$initAndRun$1(this.$finishedCallback, this.this$0, this.$controllerFactory, this.$performAnimation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityTransitionAnimator$Runner$initAndRun$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActivityTransitionAnimator$Runner$initAndRun$1$success$1 activityTransitionAnimator$Runner$initAndRun$1$success$1 = new ActivityTransitionAnimator$Runner$initAndRun$1$success$1(this.this$0, this.$controllerFactory, this.$performAnimation, null);
            this.label = 1;
            obj = TimeoutKt.withTimeoutOrNull(1000L, activityTransitionAnimator$Runner$initAndRun$1$success$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Boolean bool = (Boolean) obj;
        if (!(bool != null ? bool.booleanValue() : false) && (iRemoteAnimationFinishedCallback = this.$finishedCallback) != null) {
            iRemoteAnimationFinishedCallback.onAnimationFinished();
        }
        return Unit.INSTANCE;
    }
}
