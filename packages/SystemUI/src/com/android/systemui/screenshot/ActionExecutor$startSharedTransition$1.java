package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ActionExecutor$startSharedTransition$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    final /* synthetic */ boolean $overrideTransition;
    final /* synthetic */ UserHandle $user;
    final /* synthetic */ Pair<ActivityOptions, ExitTransitionCoordinator> $windowTransition;
    int label;
    final /* synthetic */ ActionExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionExecutor$startSharedTransition$1(ActionExecutor actionExecutor, Intent intent, UserHandle userHandle, boolean z, Pair<ActivityOptions, ExitTransitionCoordinator> pair, Continuation continuation) {
        super(2, continuation);
        this.this$0 = actionExecutor;
        this.$intent = intent;
        this.$user = userHandle;
        this.$overrideTransition = z;
        this.$windowTransition = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActionExecutor$startSharedTransition$1(this.this$0, this.$intent, this.$user, this.$overrideTransition, this.$windowTransition, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionExecutor$startSharedTransition$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActionIntentExecutor actionIntentExecutor = this.this$0.intentExecutor;
            Intent intent = this.$intent;
            UserHandle userHandle = this.$user;
            boolean z = this.$overrideTransition;
            Pair<ActivityOptions, ExitTransitionCoordinator> pair = this.$windowTransition;
            ActivityOptions activityOptions = (ActivityOptions) pair.first;
            ExitTransitionCoordinator exitTransitionCoordinator = (ExitTransitionCoordinator) pair.second;
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
