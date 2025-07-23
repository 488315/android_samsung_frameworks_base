package com.android.systemui.navigationbar.gestural.domain;

import android.app.ActivityManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GestureInteractor$getTopActivity$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ GestureInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureInteractor$getTopActivity$2(GestureInteractor gestureInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = gestureInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GestureInteractor$getTopActivity$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureInteractor$getTopActivity$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActivityManager.RunningTaskInfo runningTask = this.this$0.activityManagerWrapper.getRunningTask();
        if (runningTask != null) {
            return new TaskInfo(runningTask.topActivity, runningTask.getActivityType());
        }
        return null;
    }
}
