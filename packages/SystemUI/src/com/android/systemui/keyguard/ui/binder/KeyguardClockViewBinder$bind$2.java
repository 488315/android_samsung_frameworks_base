package com.android.systemui.keyguard.ui.binder;

import android.transition.TransitionManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.plugins.clocks.AodClockBurnInModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardClockViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AodBurnInViewModel $aodBurnInViewModel;
    final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
    final /* synthetic */ ClockSection $clockSection;
    final /* synthetic */ ConstraintLayout $keyguardRootView;
    final /* synthetic */ KeyguardRootViewModel $rootViewModel;
    final /* synthetic */ KeyguardClockViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AodBurnInViewModel $aodBurnInViewModel;
        final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
        final /* synthetic */ ClockSection $clockSection;
        final /* synthetic */ ConstraintLayout $keyguardRootView;
        final /* synthetic */ KeyguardRootViewModel $rootViewModel;
        final /* synthetic */ KeyguardClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01601 extends SuspendLambda implements Function2 {
            final /* synthetic */ ClockSection $clockSection;
            final /* synthetic */ ConstraintLayout $keyguardRootView;
            final /* synthetic */ Ref$ObjectRef<ClockController> $lastClock;
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01601(KeyguardClockViewModel keyguardClockViewModel, Ref$ObjectRef<ClockController> ref$ObjectRef, ConstraintLayout constraintLayout, ClockSection clockSection, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
                this.$lastClock = ref$ObjectRef;
                this.$keyguardRootView = constraintLayout;
                this.$clockSection = clockSection;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01601(this.$viewModel, this.$lastClock, this.$keyguardRootView, this.$clockSection, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = keyguardClockViewModel.currentClock;
                    final Ref$ObjectRef<ClockController> ref$ObjectRef = this.$lastClock;
                    final ConstraintLayout constraintLayout = this.$keyguardRootView;
                    final ClockSection clockSection = this.$clockSection;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.1.1
                        /* JADX WARN: Type inference failed for: r6v1, types: [T, com.android.systemui.plugins.clocks.ClockController, java.lang.Object] */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ?? r6 = (ClockController) obj2;
                            Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                            boolean areEqual = Intrinsics.areEqual(ref$ObjectRef2.element, (Object) r6);
                            KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                            ConstraintLayout constraintLayout2 = constraintLayout;
                            if (!areEqual) {
                                KeyguardClockViewBinder keyguardClockViewBinder = KeyguardClockViewBinder.INSTANCE;
                                ClockController clockController = (ClockController) ref$ObjectRef2.element;
                                AodBurnInLayer aodBurnInLayer = keyguardClockViewModel2.burnInLayer;
                                keyguardClockViewBinder.getClass();
                                KeyguardClockViewBinder.cleanupClockViews(clockController, constraintLayout2, aodBurnInLayer);
                                ref$ObjectRef2.element = r6;
                            }
                            KeyguardClockViewBinder.INSTANCE.updateBurnInLayer(constraintLayout2, keyguardClockViewModel2, (ClockSize) keyguardClockViewModel2.clockSize.$$delegate_0.getValue());
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(constraintLayout2);
                            clockSection.applyConstraints(constraintSet);
                            TransitionManager.beginDelayedTransition(constraintLayout2);
                            constraintSet.applyTo(constraintLayout2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
            final /* synthetic */ ConstraintLayout $keyguardRootView;
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardClockViewModel keyguardClockViewModel, ConstraintLayout constraintLayout, KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
                this.$keyguardRootView = constraintLayout;
                this.$blueprintInteractor = keyguardBlueprintInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$keyguardRootView, this.$blueprintInteractor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = keyguardClockViewModel.clockSize;
                    final ConstraintLayout constraintLayout = this.$keyguardRootView;
                    final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardClockViewBinder.INSTANCE.updateBurnInLayer(ConstraintLayout.this, keyguardClockViewModel, (ClockSize) obj2);
                            keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.ClockSize);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardClockViewModel keyguardClockViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
                this.$blueprintInteractor = keyguardBlueprintInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$blueprintInteractor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = keyguardClockViewModel.clockShouldBeCentered;
                    final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            ClockController clockController = (ClockController) KeyguardClockViewModel.this.currentClock.$$delegate_0.getValue();
                            if (clockController != null) {
                                boolean hasCustomPositionUpdatedAnimation = clockController.getLargeClock().getConfig().getHasCustomPositionUpdatedAnimation();
                                KeyguardBlueprintInteractor keyguardBlueprintInteractor2 = keyguardBlueprintInteractor;
                                if (hasCustomPositionUpdatedAnimation) {
                                    keyguardBlueprintInteractor2.refreshBlueprint(IntraBlueprintTransition.Type.DefaultClockStepping);
                                } else {
                                    keyguardBlueprintInteractor2.refreshBlueprint(IntraBlueprintTransition.Type.DefaultTransition);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
            final /* synthetic */ KeyguardRootViewModel $rootViewModel;
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function3 {
                /* synthetic */ boolean Z$0;
                /* synthetic */ boolean Z$1;
                int label;

                public AnonymousClass2(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
                    anonymousClass2.Z$0 = booleanValue;
                    anonymousClass2.Z$1 = booleanValue2;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Boolean.valueOf(this.Z$0 && this.Z$1);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardClockViewModel keyguardClockViewModel, KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
                this.$rootViewModel = keyguardRootViewModel;
                this.$blueprintInteractor = keyguardBlueprintInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$rootViewModel, this.$blueprintInteractor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.hasAodIcons;
                    final StateFlow stateFlow = this.$rootViewModel.isNotifIconContainerVisible;
                    Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L54
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.util.ui.AnimatedValue r5 = (com.android.systemui.util.ui.AnimatedValue) r5
                                    boolean r6 = r5 instanceof com.android.systemui.util.ui.AnimatedValue.Animating
                                    if (r6 == 0) goto L3f
                                    com.android.systemui.util.ui.AnimatedValue$Animating r5 = (com.android.systemui.util.ui.AnimatedValue.Animating) r5
                                    java.lang.Object r5 = r5.getValue()
                                    goto L49
                                L3f:
                                    boolean r6 = r5 instanceof com.android.systemui.util.ui.AnimatedValue.NotAnimating
                                    if (r6 == 0) goto L57
                                    com.android.systemui.util.ui.AnimatedValue$NotAnimating r5 = (com.android.systemui.util.ui.AnimatedValue.NotAnimating) r5
                                    java.lang.Object r5 = r5.getValue()
                                L49:
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L54
                                    return r1
                                L54:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                L57:
                                    kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                                    r4.<init>()
                                    throw r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new AnonymousClass2(null)));
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.5.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            ClockController clockController = (ClockController) KeyguardClockViewModel.this.currentClock.$$delegate_0.getValue();
                            if (clockController != null && clockController.getConfig().getUseCustomClockScene()) {
                                keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.DefaultTransition);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ AodBurnInViewModel $aodBurnInViewModel;
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(AodBurnInViewModel aodBurnInViewModel, KeyguardClockViewModel keyguardClockViewModel, Continuation continuation) {
                super(2, continuation);
                this.$aodBurnInViewModel = aodBurnInViewModel;
                this.$viewModel = keyguardClockViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$aodBurnInViewModel, this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$aodBurnInViewModel.movement;
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ClockFaceController largeClock;
                            ClockFaceLayout layout;
                            BurnInModel burnInModel = (BurnInModel) obj2;
                            ClockController clockController = (ClockController) KeyguardClockViewModel.this.currentClock.$$delegate_0.getValue();
                            if (clockController != null && (largeClock = clockController.getLargeClock()) != null && (layout = largeClock.getLayout()) != null) {
                                layout.applyAodBurnIn(new AodClockBurnInModel(burnInModel.scale, burnInModel.translationX, burnInModel.translationY));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(KeyguardClockViewModel keyguardClockViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                    ChannelFlowTransformLatest channelFlowTransformLatest = keyguardClockViewModel.largeClockTextSize;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ClockFaceController largeClock;
                            ClockFaceEvents events;
                            int intValue = ((Number) obj2).intValue();
                            ClockController clockController = (ClockController) KeyguardClockViewModel.this.currentClock.$$delegate_0.getValue();
                            if (clockController != null && (largeClock = clockController.getLargeClock()) != null && (events = largeClock.getEvents()) != null) {
                                events.onFontSettingChanged(intValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardClockViewModel keyguardClockViewModel, ConstraintLayout constraintLayout, ClockSection clockSection, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardClockViewModel;
            this.$keyguardRootView = constraintLayout;
            this.$clockSection = clockSection;
            this.$blueprintInteractor = keyguardBlueprintInteractor;
            this.$rootViewModel = keyguardRootViewModel;
            this.$aodBurnInViewModel = aodBurnInViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$keyguardRootView, this.$clockSection, this.$blueprintInteractor, this.$rootViewModel, this.$aodBurnInViewModel, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            StandaloneCoroutine launchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01601(this.$viewModel, ref$ObjectRef, this.$keyguardRootView, this.$clockSection, null), 7);
            final ConstraintLayout constraintLayout = this.$keyguardRootView;
            final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
            launchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    KeyguardClockViewBinder keyguardClockViewBinder = KeyguardClockViewBinder.INSTANCE;
                    Ref$ObjectRef ref$ObjectRef2 = Ref$ObjectRef.this;
                    ClockController clockController = (ClockController) ref$ObjectRef2.element;
                    AodBurnInLayer aodBurnInLayer = keyguardClockViewModel.burnInLayer;
                    keyguardClockViewBinder.getClass();
                    KeyguardClockViewBinder.cleanupClockViews(clockController, constraintLayout, aodBurnInLayer);
                    ref$ObjectRef2.element = null;
                    return Unit.INSTANCE;
                }
            });
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$keyguardRootView, this.$blueprintInteractor, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$blueprintInteractor, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$rootViewModel, this.$blueprintInteractor, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$aodBurnInViewModel, this.$viewModel, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardClockViewBinder$bind$2(KeyguardClockViewModel keyguardClockViewModel, ConstraintLayout constraintLayout, ClockSection clockSection, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardClockViewModel;
        this.$keyguardRootView = constraintLayout;
        this.$clockSection = clockSection;
        this.$blueprintInteractor = keyguardBlueprintInteractor;
        this.$rootViewModel = keyguardRootViewModel;
        this.$aodBurnInViewModel = aodBurnInViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardClockViewBinder$bind$2 keyguardClockViewBinder$bind$2 = new KeyguardClockViewBinder$bind$2(this.$viewModel, this.$keyguardRootView, this.$clockSection, this.$blueprintInteractor, this.$rootViewModel, this.$aodBurnInViewModel, (Continuation) obj3);
        keyguardClockViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardClockViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$keyguardRootView, this.$clockSection, this.$blueprintInteractor, this.$rootViewModel, this.$aodBurnInViewModel, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
