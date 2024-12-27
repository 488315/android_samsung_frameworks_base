package com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel;

import com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TaskSwitcherNotificationViewModel {
    public static final Companion Companion = new Companion(null);
    public static final long NOTIFICATION_MAX_SHOW_DURATION;
    public final CoroutineDispatcher backgroundDispatcher;
    public final TaskSwitchInteractor interactor;
    public final ChannelFlowTransformLatest uiState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getNOTIFICATION_MAX_SHOW_DURATION-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m1982getNOTIFICATION_MAX_SHOW_DURATIONUwyO8pc$annotations() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        NOTIFICATION_MAX_SHOW_DURATION = DurationKt.toDuration(5, DurationUnit.SECONDS);
    }

    public TaskSwitcherNotificationViewModel(TaskSwitchInteractor taskSwitchInteractor, CoroutineDispatcher coroutineDispatcher) {
        final ChannelFlowTransformLatest channelFlowTransformLatest = taskSwitchInteractor.taskSwitchChanges;
        FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState r5 = (com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState) r5
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        java.lang.String r2 = "taskSwitchChange: "
                        r6.<init>(r2)
                        r6.append(r5)
                        java.lang.String r6 = r6.toString()
                        java.lang.String r2 = "TaskSwitchNotifVM"
                        android.util.Log.d(r2, r6)
                        boolean r6 = r5 instanceof com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState.TaskSwitched
                        if (r6 == 0) goto L58
                        com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState$Showing r6 = new com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState$Showing
                        com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState$TaskSwitched r5 = (com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState.TaskSwitched) r5
                        android.app.ActivityManager$RunningTaskInfo r2 = r5.projectedTask
                        android.app.ActivityManager$RunningTaskInfo r5 = r5.foregroundTask
                        r6.<init>(r2, r5)
                        goto L64
                    L58:
                        boolean r6 = r5 instanceof com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState.NotProjectingTask
                        if (r6 == 0) goto L5e
                        r5 = r3
                        goto L60
                    L5e:
                        boolean r5 = r5 instanceof com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState.TaskUnchanged
                    L60:
                        if (r5 == 0) goto L72
                        com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState$NotShowing r6 = com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState.NotShowing.INSTANCE
                    L64:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L6f
                        return r1
                    L6f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L72:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new TaskSwitcherNotificationViewModel$uiState$2(null));
    }
}
