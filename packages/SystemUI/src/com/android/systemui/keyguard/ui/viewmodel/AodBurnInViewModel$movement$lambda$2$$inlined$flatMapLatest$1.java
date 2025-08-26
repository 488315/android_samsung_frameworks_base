package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.StateToValue;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BurnInParameters $params$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AodBurnInViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1(Continuation continuation, AodBurnInViewModel aodBurnInViewModel, BurnInParameters burnInParameters) {
        super(3, continuation);
        this.this$0 = aodBurnInViewModel;
        this.$params$inlined = burnInParameters;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 = new AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$params$inlined);
        aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.L$1 = obj2;
        return aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final int i = 0;
        final int i2 = 1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = this.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Map map = (Map) this.L$1;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$1(null), this.this$0.keyguardInteractor.keyguardTranslationY);
            AodBurnInViewModel aodBurnInViewModel = this.this$0;
            BurnInParameters burnInParameters = this.$params$inlined;
            aodBurnInViewModel.getClass();
            Edge.Companion companion = Edge.Companion;
            KeyguardState keyguardState = KeyguardState.AOD;
            Edge.StateToState stateToStateCreate$default = Edge.Companion.create$default(companion, null, keyguardState, 1);
            KeyguardTransitionInteractor keyguardTransitionInteractor = aodBurnInViewModel.keyguardTransitionInteractor;
            Flow flowTransition = keyguardTransitionInteractor.transition(stateToStateCreate$default);
            final Flow flowTransition2 = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, keyguardState, null, 2));
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2$1, reason: invalid class name */
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
                            TransitionStep transitionStep = (TransitionStep) obj;
                            TransitionStep transitionStepCopy$default = TransitionStep.copy$default(transitionStep, 1.0f - transitionStep.value, null, 27);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(transitionStepCopy$default, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowTransition2.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final Flow flowTransition3 = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, null, KeyguardState.LOCKSCREEN, 1));
            final Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2$1, reason: invalid class name */
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
                            if (((TransitionStep) obj).from != KeyguardState.AOD) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowTransition3.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(flowTransition, flow, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2$1, reason: invalid class name */
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
                            TransitionStep transitionStepCopy$default = TransitionStep.copy$default((TransitionStep) obj, 0.0f, null, 27);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(transitionStepCopy$default, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flow2.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$2(null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2$1, reason: invalid class name */
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
                            Float f = new Float(((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(((TransitionStep) obj).value));
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
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, aodBurnInViewModel.burnInInteractor.burnIn(R.dimen.burn_in_prevention_offset_y), new AodBurnInViewModel$burnIn$5(aodBurnInViewModel, burnInParameters, null)));
            GoneToAodTransitionViewModel goneToAodTransitionViewModel = this.this$0.goneToAodTransitionViewModel;
            Object obj2 = map.get(new Integer(R.dimen.keyguard_enter_from_top_translation_y));
            obj2.getClass();
            final int iIntValue = ((Number) obj2).intValue();
            goneToAodTransitionViewModel.getClass();
            Duration.Companion companion2 = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.MILLISECONDS;
            long duration = DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit);
            long duration2 = DurationKt.toDuration(500, durationUnit);
            Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$3(null), new SafeFlow(new GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2617sharedFlowWithState74qcysc$default(goneToAodTransitionViewModel.transitionAnimation, duration2, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj3) {
                    int i4 = i2;
                    float fFloatValue = ((Float) obj3).floatValue();
                    switch (i4) {
                        case 0:
                            return Float.valueOf((fFloatValue * (-r1)) + iIntValue);
                        default:
                            return Float.valueOf((fFloatValue * (-r1)) + iIntValue);
                    }
                }
            }, duration, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), goneToAodTransitionViewModel.powerInteractor.detailedWakefulness, GoneToAodTransitionViewModel$enterFromTopTranslationY$5.INSTANCE), null)));
            GoneToAodTransitionViewModel goneToAodTransitionViewModel2 = this.this$0.goneToAodTransitionViewModel;
            Object obj3 = map.get(new Integer(R.dimen.keyguard_enter_from_side_translation_x));
            obj3.getClass();
            final int iIntValue2 = ((Number) obj3).intValue();
            goneToAodTransitionViewModel2.getClass();
            long duration3 = DurationKt.toDuration(500, durationUnit);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$4(null), new SafeFlow(new GoneToAodTransitionViewModel$enterFromSideTranslationX$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2617sharedFlowWithState74qcysc$default(goneToAodTransitionViewModel2.transitionAnimation, DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj32) {
                    int i4 = i;
                    float fFloatValue = ((Float) obj32).floatValue();
                    switch (i4) {
                        case 0:
                            return Float.valueOf((fFloatValue * (-r1)) + iIntValue2);
                        default:
                            return Float.valueOf((fFloatValue * (-r1)) + iIntValue2);
                    }
                }
            }, duration3, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), goneToAodTransitionViewModel2.powerInteractor.detailedWakefulness, GoneToAodTransitionViewModel$enterFromSideTranslationX$5.INSTANCE), null)));
            LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel = this.this$0.lockscreenToAodTransitionViewModel;
            Object obj4 = map.get(new Integer(R.dimen.keyguard_enter_from_side_translation_x));
            obj4.getClass();
            final int iIntValue3 = ((Number) obj4).intValue();
            lockscreenToAodTransitionViewModel.getClass();
            long duration4 = DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$15 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$5(null), new SafeFlow(new LockscreenToAodTransitionViewModel$enterFromSideTranslationX$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2617sharedFlowWithState74qcysc$default(lockscreenToAodTransitionViewModel.transitionAnimationOnFold, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj5) {
                    float fFloatValue = ((Float) obj5).floatValue();
                    return Float.valueOf((fFloatValue * (-r1)) + iIntValue3);
                }
            }, duration4, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), lockscreenToAodTransitionViewModel.powerInteractor.detailedWakefulness, LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5.INSTANCE), null)));
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$16 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$6(null), this.this$0.occludedToLockscreenTransitionViewModel.lockscreenTranslationY);
            AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel = this.this$0.aodToLockscreenTransitionViewModel;
            final Function0 function0 = this.$params$inlined.translationX;
            aodToLockscreenTransitionViewModel.getClass();
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            final int i4 = 1;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$17 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$7(null), KeyguardTransitionAnimationFlow.FlowBuilder.m2617sharedFlowWithState74qcysc$default(aodToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef, 1), 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i4) {
                        case 0:
                            Float f = (Float) function0.invoke();
                            ref$FloatRef.element = f != null ? f.floatValue() : 0.0f;
                            break;
                        default:
                            Float f2 = (Float) function0.invoke();
                            ref$FloatRef.element = f2 != null ? f2.floatValue() : 0.0f;
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode));
            AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel2 = this.this$0.aodToLockscreenTransitionViewModel;
            final Function0 function02 = this.$params$inlined.translationY;
            aodToLockscreenTransitionViewModel2.getClass();
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            final int i5 = 0;
            final Flow[] flowArr = {flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$15, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$16, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$17, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$8(null), KeyguardTransitionAnimationFlow.FlowBuilder.m2617sharedFlowWithState74qcysc$default(aodToLockscreenTransitionViewModel2.transitionAnimation, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef2, 0), 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i5) {
                        case 0:
                            Float f = (Float) function02.invoke();
                            ref$FloatRef2.element = f != null ? f.floatValue() : 0.0f;
                            break;
                        default:
                            Float f2 = (Float) function02.invoke();
                            ref$FloatRef2.element = f2 != null ? f2.floatValue() : 0.0f;
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode))};
            Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        float fFloatValue;
                        float fFloatValue2;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            Object[] objArr = (Object[]) this.L$1;
                            float fFloatValue3 = ((Float) objArr[0]).floatValue();
                            BurnInModel burnInModel = (BurnInModel) objArr[1];
                            StateToValue stateToValue = (StateToValue) objArr[2];
                            StateToValue stateToValue2 = (StateToValue) objArr[3];
                            StateToValue stateToValue3 = (StateToValue) objArr[4];
                            float fFloatValue4 = ((Float) objArr[5]).floatValue();
                            StateToValue stateToValue4 = (StateToValue) objArr[6];
                            StateToValue stateToValue5 = (StateToValue) objArr[7];
                            if (stateToValue5.transitionState.isTransitioning()) {
                                Float f = stateToValue5.value;
                                fFloatValue2 = f != null ? f.floatValue() : 0.0f;
                            } else {
                                if (stateToValue.transitionState.isTransitioning()) {
                                    Float f2 = stateToValue.value;
                                    fFloatValue = f2 != null ? f2.floatValue() : 0.0f;
                                    fFloatValue3 = burnInModel.translationY;
                                } else {
                                    fFloatValue = burnInModel.translationY + fFloatValue4;
                                }
                                fFloatValue2 = fFloatValue + fFloatValue3;
                            }
                            if (stateToValue4.transitionState.isTransitioning()) {
                                Float f3 = stateToValue4.value;
                                if (f3 != null) {
                                    fFloatValue = f3.floatValue();
                                }
                            } else {
                                float f4 = burnInModel.translationX;
                                Float f5 = stateToValue2.value;
                                float fFloatValue5 = f4 + (f5 != null ? f5.floatValue() : 0.0f);
                                Float f6 = stateToValue3.value;
                                fFloatValue = (f6 != null ? f6.floatValue() : 0.0f) + fFloatValue5;
                            }
                            BurnInModel burnInModel2 = new BurnInModel((int) fFloatValue, (int) fFloatValue2, burnInModel.scale, burnInModel.scaleClockOnly);
                            this.label = 1;
                            if (flowCollector.emit(burnInModel2, this) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new Object[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector2, continuation);
                    return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
