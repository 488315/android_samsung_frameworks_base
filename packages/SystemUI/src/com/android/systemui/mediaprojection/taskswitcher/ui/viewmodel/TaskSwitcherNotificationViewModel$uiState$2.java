package com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel;

import com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

final class TaskSwitcherNotificationViewModel$uiState$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public TaskSwitcherNotificationViewModel$uiState$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TaskSwitcherNotificationViewModel$uiState$2 taskSwitcherNotificationViewModel$uiState$2 = new TaskSwitcherNotificationViewModel$uiState$2((Continuation) obj3);
        taskSwitcherNotificationViewModel$uiState$2.L$0 = (FlowCollector) obj;
        taskSwitcherNotificationViewModel$uiState$2.L$1 = (TaskSwitcherNotificationUiState) obj2;
        return taskSwitcherNotificationViewModel$uiState$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L32
            if (r1 == r5) goto L25
            if (r1 == r4) goto L1d
            if (r1 != r3) goto L15
            kotlin.ResultKt.throwOnFailure(r8)
            goto L86
        L15:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1d:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L63
        L25:
            java.lang.Object r1 = r7.L$1
            com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState r1 = (com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState) r1
            java.lang.Object r5 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r5
            goto L4a
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            java.lang.Object r1 = r7.L$1
            com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState r1 = (com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState) r1
            r7.L$0 = r8
            r7.L$1 = r1
            r7.label = r5
            java.lang.Object r5 = r8.emit(r1, r7)
            if (r5 != r0) goto L4a
            return r0
        L4a:
            boolean r1 = r1 instanceof com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState.Showing
            if (r1 == 0) goto L86
            com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$Companion r1 = com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel.Companion
            r1.getClass()
            long r5 = com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel.NOTIFICATION_MAX_SHOW_DURATION
            r7.L$0 = r8
            r7.L$1 = r2
            r7.label = r4
            java.lang.Object r1 = kotlinx.coroutines.DelayKt.m2546delayVtjQ1oo(r5, r7)
            if (r1 != r0) goto L62
            return r0
        L62:
            r1 = r8
        L63:
            com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$Companion r8 = com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel.Companion
            r8.getClass()
            long r4 = com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel.NOTIFICATION_MAX_SHOW_DURATION
            java.lang.String r8 = kotlin.time.Duration.m2545toStringimpl(r4)
            java.lang.String r4 = "Auto hiding notification after "
            java.lang.String r8 = r4.concat(r8)
            java.lang.String r4 = "TaskSwitchNotifVM"
            android.util.Log.d(r4, r8)
            com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState$NotShowing r8 = com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState.NotShowing.INSTANCE
            r7.L$0 = r2
            r7.label = r3
            java.lang.Object r7 = r1.emit(r8, r7)
            if (r7 != r0) goto L86
            return r0
        L86:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$uiState$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
