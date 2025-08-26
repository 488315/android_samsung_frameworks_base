package com.android.systemui.unfold.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepository;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl;
import com.android.systemui.unfold.data.repository.UnfoldTransitionStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class UnfoldTransitionInteractor {
    public final ConfigurationInteractor configurationInteractor;
    public final UnfoldTransitionRepository repository;
    public final Flow unfoldProgress;
    public final Flow unfoldTransitionStatus;

    /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function4 {
        final /* synthetic */ boolean $isOnStartSide;
        /* synthetic */ float F$0;
        /* synthetic */ int I$0;
        /* synthetic */ int I$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, Continuation continuation) {
            super(4, continuation);
            this.$isOnStartSide = z;
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            float fFloatValue = ((Number) obj).floatValue();
            int iIntValue = ((Number) obj2).intValue();
            int iIntValue2 = ((Number) obj3).intValue();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$isOnStartSide, (Continuation) obj4);
            anonymousClass2.F$0 = fFloatValue;
            anonymousClass2.I$0 = iIntValue;
            anonymousClass2.I$1 = iIntValue2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Float((1 - this.F$0) * this.I$0 * (this.$isOnStartSide ? 1 : -1) * this.I$1);
        }
    }

    public UnfoldTransitionInteractor(UnfoldTransitionRepository unfoldTransitionRepository, ConfigurationInteractor configurationInteractor) {
        this.repository = unfoldTransitionRepository;
        this.configurationInteractor = configurationInteractor;
        this.unfoldTransitionStatus = ((UnfoldTransitionRepositoryImpl) unfoldTransitionRepository).getTransitionStatus();
        final Flow transitionStatus = ((UnfoldTransitionRepositoryImpl) unfoldTransitionRepository).getTransitionStatus();
        this.unfoldProgress = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UnfoldTransitionInteractor$unfoldProgress$2(null), new Flow() { // from class: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        UnfoldTransitionStatus unfoldTransitionStatus = (UnfoldTransitionStatus) obj;
                        UnfoldTransitionStatus.TransitionInProgress transitionInProgress = unfoldTransitionStatus instanceof UnfoldTransitionStatus.TransitionInProgress ? (UnfoldTransitionStatus.TransitionInProgress) unfoldTransitionStatus : null;
                        Float f = new Float(transitionInProgress != null ? transitionInProgress.progress : 1.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = transitionStatus.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }));
    }

    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 unfoldTranslationX(boolean z) {
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) this.configurationInteractor;
        ChannelFlowTransformLatest channelFlowTransformLatestDimensionPixelSize = configurationInteractorImpl.dimensionPixelSize(R.dimen.notification_side_paddings);
        final Flow flow = configurationInteractorImpl.layoutDirection;
        return FlowKt.combine(this.unfoldProgress, channelFlowTransformLatestDimensionPixelSize, new Flow() { // from class: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1

            /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((Number) obj).intValue() == 1 ? -1 : 1);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass2(z, null));
    }
}
