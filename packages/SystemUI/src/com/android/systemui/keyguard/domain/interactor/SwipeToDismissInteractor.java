package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.shade.data.repository.FlingInfo;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class SwipeToDismissInteractor {
    public final ReadonlyStateFlow dismissFling;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public SwipeToDismissInteractor(CoroutineScope coroutineScope, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        final SharedFlowImpl sharedFlowImpl = ((ShadeRepositoryImpl) shadeRepository).currentFling;
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SwipeToDismissInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, SwipeToDismissInteractor swipeToDismissInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = swipeToDismissInteractor;
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
                        FlingInfo flingInfo = (FlingInfo) obj;
                        if (flingInfo != null && !flingInfo.expand) {
                            SwipeToDismissInteractor swipeToDismissInteractor = this.this$0;
                            if (swipeToDismissInteractor.keyguardInteractor.statusBarState.$$delegate_0.getValue() != StatusBarState.SHADE_LOCKED && ((TransitionStep) swipeToDismissInteractor.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue()).to == KeyguardState.LOCKSCREEN && ((Boolean) swipeToDismissInteractor.keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue()) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
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
                Object objCollect = sharedFlowImpl.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.dismissFling = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }
}
