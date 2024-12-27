package com.android.systemui.mediaprojection.taskswitcher.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ActivityTaskManagerTasksRepository$getRunningTasks$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ActivityTaskManagerTasksRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTaskManagerTasksRepository$getRunningTasks$2(ActivityTaskManagerTasksRepository activityTaskManagerTasksRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = activityTaskManagerTasksRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTaskManagerTasksRepository$getRunningTasks$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityTaskManagerTasksRepository$getRunningTasks$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.activityTaskManager.getTasks(Integer.MAX_VALUE, false, false, -1);
    }
}
