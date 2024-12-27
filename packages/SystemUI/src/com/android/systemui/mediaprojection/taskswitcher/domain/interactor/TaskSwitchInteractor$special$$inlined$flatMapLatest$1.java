package com.android.systemui.mediaprojection.taskswitcher.domain.interactor;

import android.app.ActivityManager;
import android.util.Log;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository;
import com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TaskSwitchInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ TaskSwitchInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskSwitchInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, TaskSwitchInteractor taskSwitchInteractor) {
        super(3, continuation);
        this.this$0 = taskSwitchInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TaskSwitchInteractor$special$$inlined$flatMapLatest$1 taskSwitchInteractor$special$$inlined$flatMapLatest$1 = new TaskSwitchInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        taskSwitchInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        taskSwitchInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return taskSwitchInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaProjectionState mediaProjectionState = (MediaProjectionState) this.L$1;
            Log.d("TaskSwitchInteractor", "MediaProjectionState -> " + mediaProjectionState);
            if (mediaProjectionState instanceof MediaProjectionState.Projecting.SingleTask) {
                final ActivityManager.RunningTaskInfo runningTaskInfo = ((MediaProjectionState.Projecting.SingleTask) mediaProjectionState).task;
                final TaskSwitchInteractor taskSwitchInteractor = this.this$0;
                final ReadonlySharedFlow readonlySharedFlow = ((ActivityTaskManagerTasksRepository) taskSwitchInteractor.tasksRepository).foregroundTask;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ ActivityManager.RunningTaskInfo $projectedTask$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ TaskSwitchInteractor this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, TaskSwitchInteractor taskSwitchInteractor, ActivityManager.RunningTaskInfo runningTaskInfo) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = taskSwitchInteractor;
                            this.$projectedTask$inlined = runningTaskInfo;
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
                                boolean r0 = r6 instanceof com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L71
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                android.app.ActivityManager$RunningTaskInfo r5 = (android.app.ActivityManager.RunningTaskInfo) r5
                                android.app.ActivityManager$RunningTaskInfo r6 = r4.$projectedTask$inlined
                                int r2 = com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor.$r8$clinit
                                com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor r2 = r4.this$0
                                r2.getClass()
                                int r6 = r6.taskId
                                int r2 = r5.taskId
                                if (r6 == r2) goto L64
                                android.content.Intent r6 = r5.baseIntent
                                java.lang.String r2 = "android.intent.category.HOME"
                                boolean r6 = r6.hasCategory(r2)
                                if (r6 == 0) goto L5c
                                android.content.Intent r6 = r5.baseIntent
                                java.lang.String r6 = r6.getAction()
                                java.lang.String r2 = "android.intent.action.MAIN"
                                boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                                if (r6 == 0) goto L5c
                                goto L64
                            L5c:
                                com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState$TaskSwitched r6 = new com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState$TaskSwitched
                                android.app.ActivityManager$RunningTaskInfo r2 = r4.$projectedTask$inlined
                                r6.<init>(r2, r5)
                                goto L66
                            L64:
                                com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState$TaskUnchanged r6 = com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState.TaskUnchanged.INSTANCE
                            L66:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r6, r0)
                                if (r4 != r1) goto L71
                                return r1
                            L71:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, taskSwitchInteractor, runningTaskInfo), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                if (!(mediaProjectionState instanceof MediaProjectionState.Projecting.EntireScreen ? true : mediaProjectionState instanceof MediaProjectionState.NotProjecting)) {
                    throw new NoWhenBranchMatchedException();
                }
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TaskSwitchState.NotProjectingTask.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
