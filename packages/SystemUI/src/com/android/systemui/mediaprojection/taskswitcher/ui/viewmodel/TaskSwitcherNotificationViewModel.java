package com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel;

import android.util.Log;
import com.android.systemui.mediaprojection.taskswitcher.domain.interactor.TaskSwitchInteractor;
import com.android.systemui.mediaprojection.taskswitcher.domain.model.TaskSwitchState;
import com.android.systemui.mediaprojection.taskswitcher.ui.model.TaskSwitcherNotificationUiState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
public final class TaskSwitcherNotificationViewModel {
    public static final Companion Companion = new Companion(null);
    public static final long NOTIFICATION_MAX_SHOW_DURATION;
    public final CoroutineDispatcher backgroundDispatcher;
    public final TaskSwitchInteractor interactor;
    public final ChannelFlowTransformLatest uiState;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getNOTIFICATION_MAX_SHOW_DURATION-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2638getNOTIFICATION_MAX_SHOW_DURATIONUwyO8pc$annotations() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        NOTIFICATION_MAX_SHOW_DURATION = DurationKt.toDuration(5, DurationUnit.SECONDS);
    }

    public TaskSwitcherNotificationViewModel(TaskSwitchInteractor taskSwitchInteractor, CoroutineDispatcher coroutineDispatcher) {
        this.interactor = taskSwitchInteractor;
        this.backgroundDispatcher = coroutineDispatcher;
        final ChannelFlowTransformLatest channelFlowTransformLatest = taskSwitchInteractor.taskSwitchChanges;
        this.uiState = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object showing;
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
                        TaskSwitchState taskSwitchState = (TaskSwitchState) obj;
                        Log.d("TaskSwitchNotifVM", "taskSwitchChange: " + taskSwitchState);
                        if (taskSwitchState instanceof TaskSwitchState.TaskSwitched) {
                            TaskSwitchState.TaskSwitched taskSwitched = (TaskSwitchState.TaskSwitched) taskSwitchState;
                            showing = new TaskSwitcherNotificationUiState.Showing(taskSwitched.projectedTask, taskSwitched.foregroundTask);
                        } else {
                            if (!(taskSwitchState instanceof TaskSwitchState.NotProjectingTask) && !(taskSwitchState instanceof TaskSwitchState.TaskUnchanged)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            showing = TaskSwitcherNotificationUiState.NotShowing.INSTANCE;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(showing, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new TaskSwitcherNotificationViewModel$uiState$2(null));
    }
}
