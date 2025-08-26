package com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007f, code lost:
    
        if (r1.emit(r8, r7) != r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        TaskSwitcherNotificationUiState taskSwitcherNotificationUiState;
        FlowCollector flowCollector2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            taskSwitcherNotificationUiState = (TaskSwitcherNotificationUiState) this.L$1;
            this.L$0 = flowCollector;
            this.L$1 = taskSwitcherNotificationUiState;
            this.label = 1;
            if (flowCollector.emit(taskSwitcherNotificationUiState, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            taskSwitcherNotificationUiState = (TaskSwitcherNotificationUiState) this.L$1;
            FlowCollector flowCollector3 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector = flowCollector3;
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            TaskSwitcherNotificationViewModel.Companion.getClass();
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Auto hiding notification after ", Duration.m3466toStringimpl(TaskSwitcherNotificationViewModel.NOTIFICATION_MAX_SHOW_DURATION), "TaskSwitchNotifVM");
            TaskSwitcherNotificationUiState.NotShowing notShowing = TaskSwitcherNotificationUiState.NotShowing.INSTANCE;
            this.L$0 = null;
            this.label = 3;
        }
        if (taskSwitcherNotificationUiState instanceof TaskSwitcherNotificationUiState.Showing) {
            TaskSwitcherNotificationViewModel.Companion.getClass();
            long j = TaskSwitcherNotificationViewModel.NOTIFICATION_MAX_SHOW_DURATION;
            this.L$0 = flowCollector;
            this.L$1 = null;
            this.label = 2;
            if (DelayKt.m3469delayVtjQ1oo(j, this) != coroutineSingletons) {
                flowCollector2 = flowCollector;
                TaskSwitcherNotificationViewModel.Companion.getClass();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Auto hiding notification after ", Duration.m3466toStringimpl(TaskSwitcherNotificationViewModel.NOTIFICATION_MAX_SHOW_DURATION), "TaskSwitchNotifVM");
                TaskSwitcherNotificationUiState.NotShowing notShowing2 = TaskSwitcherNotificationUiState.NotShowing.INSTANCE;
                this.L$0 = null;
                this.label = 3;
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
