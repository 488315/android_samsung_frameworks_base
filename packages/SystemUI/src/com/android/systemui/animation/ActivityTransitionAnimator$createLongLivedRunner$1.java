package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityTransitionAnimator$createLongLivedRunner$1 extends SuspendLambda implements Function1 {
    final /* synthetic */ ActivityTransitionAnimator.ControllerFactory $controllerFactory;
    final /* synthetic */ boolean $forLaunch;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator$createLongLivedRunner$1(ActivityTransitionAnimator.ControllerFactory controllerFactory, boolean z, Continuation continuation) {
        super(1, continuation);
        this.$controllerFactory = controllerFactory;
        this.$forLaunch = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new ActivityTransitionAnimator$createLongLivedRunner$1(this.$controllerFactory, this.$forLaunch, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((ActivityTransitionAnimator$createLongLivedRunner$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        ActivityTransitionAnimator.ControllerFactory controllerFactory = this.$controllerFactory;
        boolean z = this.$forLaunch;
        this.label = 1;
        Object createController = controllerFactory.createController(z, this);
        return createController == coroutineSingletons ? coroutineSingletons : createController;
    }
}
