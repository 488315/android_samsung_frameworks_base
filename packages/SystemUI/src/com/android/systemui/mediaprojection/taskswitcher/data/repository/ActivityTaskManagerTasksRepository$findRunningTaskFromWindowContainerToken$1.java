package com.android.systemui.mediaprojection.taskswitcher.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivityTaskManagerTasksRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1(ActivityTaskManagerTasksRepository activityTaskManagerTasksRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = activityTaskManagerTasksRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.findRunningTaskFromWindowContainerToken(null, this);
    }
}
