package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Intent;
import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ActionIntentExecutor$launchIntentAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    final /* synthetic */ ActivityOptions $options;
    final /* synthetic */ boolean $overrideTransition;
    final /* synthetic */ ExitTransitionCoordinator $transitionCoordinator;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntentAsync$1(ActionIntentExecutor actionIntentExecutor, Intent intent, UserHandle userHandle, boolean z, ActivityOptions activityOptions, ExitTransitionCoordinator exitTransitionCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = actionIntentExecutor;
        this.$intent = intent;
        this.$user = userHandle;
        this.$overrideTransition = z;
        this.$options = activityOptions;
        this.$transitionCoordinator = exitTransitionCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActionIntentExecutor$launchIntentAsync$1(this.this$0, this.$intent, this.$user, this.$overrideTransition, this.$options, this.$transitionCoordinator, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionIntentExecutor$launchIntentAsync$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActionIntentExecutor actionIntentExecutor = this.this$0;
            Intent intent = this.$intent;
            UserHandle userHandle = this.$user;
            boolean z = this.$overrideTransition;
            ActivityOptions activityOptions = this.$options;
            ExitTransitionCoordinator exitTransitionCoordinator = this.$transitionCoordinator;
            this.label = 1;
            if (actionIntentExecutor.launchIntent(intent, userHandle, z, activityOptions, exitTransitionCoordinator, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
