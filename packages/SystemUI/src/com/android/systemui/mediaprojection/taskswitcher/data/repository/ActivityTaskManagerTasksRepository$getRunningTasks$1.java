package com.android.systemui.mediaprojection.taskswitcher.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ActivityTaskManagerTasksRepository$getRunningTasks$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivityTaskManagerTasksRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTaskManagerTasksRepository$getRunningTasks$1(ActivityTaskManagerTasksRepository activityTaskManagerTasksRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = activityTaskManagerTasksRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        ActivityTaskManagerTasksRepository activityTaskManagerTasksRepository = this.this$0;
        int i = ActivityTaskManagerTasksRepository.$r8$clinit;
        return activityTaskManagerTasksRepository.getRunningTasks(this);
    }
}
