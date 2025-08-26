package com.android.systemui.mediaprojection.taskswitcher.domain.interactor;

import android.app.ActivityManager;
import android.app.TaskInfo;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

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

                    /* renamed from: com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor$taskSwitchChanges$lambda$1$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ ActivityManager.RunningTaskInfo $projectedTask$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ TaskSwitchInteractor this$0;

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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.$projectedTask$inlined;
                                int i3 = TaskSwitchInteractor.$r8$clinit;
                                this.this$0.getClass();
                                Object taskSwitched = (((TaskInfo) runningTaskInfo2).taskId == ((TaskInfo) runningTaskInfo).taskId || (((TaskInfo) runningTaskInfo).baseIntent.hasCategory("android.intent.category.HOME") && Intrinsics.areEqual(((TaskInfo) runningTaskInfo).baseIntent.getAction(), "android.intent.action.MAIN"))) ? TaskSwitchState.TaskUnchanged.INSTANCE : new TaskSwitchState.TaskSwitched(this.$projectedTask$inlined, runningTaskInfo);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(taskSwitched, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector2, taskSwitchInteractor, runningTaskInfo), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            } else {
                if (!(mediaProjectionState instanceof MediaProjectionState.Projecting.EntireScreen) && !(mediaProjectionState instanceof MediaProjectionState.Projecting.NoScreen) && !(mediaProjectionState instanceof MediaProjectionState.NotProjecting)) {
                    throw new NoWhenBranchMatchedException();
                }
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TaskSwitchState.NotProjectingTask.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
