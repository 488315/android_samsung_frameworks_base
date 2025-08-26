package com.android.systemui.keyguard.ui.binder;

import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.AodClockBurnInModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.util.ui.AnimatedValue;
import java.util.Iterator;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class KeyguardClockViewBinder {
    public static final KeyguardClockViewBinder INSTANCE = new KeyguardClockViewBinder();

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockSize.values().length];
            try {
                iArr[ClockSize.LARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ KeyguardClockInteractor $keyguardClockInteractor;
        final /* synthetic */ ConstraintLayout $keyguardRootView;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C02701 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockInteractor $keyguardClockInteractor;
            final /* synthetic */ ConstraintLayout $keyguardRootView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02701(KeyguardClockInteractor keyguardClockInteractor, ConstraintLayout constraintLayout, Continuation continuation) {
                super(2, continuation);
                this.$keyguardClockInteractor = keyguardClockInteractor;
                this.$keyguardRootView = constraintLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02701(this.$keyguardClockInteractor, this.$keyguardRootView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02701) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$keyguardClockInteractor.clockEventController.registerListeners(this.$keyguardRootView);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardClockInteractor keyguardClockInteractor, ConstraintLayout constraintLayout, Continuation continuation) {
            super(3, continuation);
            this.$keyguardClockInteractor = keyguardClockInteractor;
            this.$keyguardRootView = constraintLayout;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$keyguardClockInteractor, this.$keyguardRootView, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02701 c02701 = new C02701(this.$keyguardClockInteractor, this.$keyguardRootView, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02701, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ AodBurnInViewModel $aodBurnInViewModel;
        final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
        final /* synthetic */ ClockSection $clockSection;
        final /* synthetic */ ConstraintLayout $keyguardRootView;
        final /* synthetic */ KeyguardRootViewModel $rootViewModel;
        final /* synthetic */ KeyguardClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02711 extends SuspendLambda implements Function2 {
                final /* synthetic */ ClockSection $clockSection;
                final /* synthetic */ ConstraintLayout $keyguardRootView;
                final /* synthetic */ Ref$ObjectRef<ClockController> $lastClock;
                final /* synthetic */ KeyguardClockViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02711(KeyguardClockViewModel keyguardClockViewModel, Ref$ObjectRef<ClockController> ref$ObjectRef, ConstraintLayout constraintLayout, ClockSection clockSection, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardClockViewModel;
                    this.$lastClock = ref$ObjectRef;
                    this.$keyguardRootView = constraintLayout;
                    this.$clockSection = clockSection;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02711(this.$viewModel, this.$lastClock, this.$keyguardRootView, this.$clockSection, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02711) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                boolean zAreEqual = Intrinsics.areEqual(ref$ObjectRef2.element, (Object) r6);
                                KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                                ConstraintLayout constraintLayout2 = constraintLayout;
                                if (!zAreEqual) {
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
                                KeyguardClockViewBinder.INSTANCE.updateBurnInLayer(constraintLayout, keyguardClockViewModel, (ClockSize) obj2);
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
                                ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
                final /* synthetic */ KeyguardRootViewModel $rootViewModel;
                final /* synthetic */ KeyguardClockViewModel $viewModel;
                int label;

                /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$2, reason: invalid class name and collision with other inner class name */
                final class C02752 extends SuspendLambda implements Function3 {
                    /* synthetic */ boolean Z$0;
                    /* synthetic */ boolean Z$1;
                    int label;

                    public C02752(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
                        C02752 c02752 = new C02752((Continuation) obj3);
                        c02752.Z$0 = zBooleanValue;
                        c02752.Z$1 = zBooleanValue2;
                        return c02752.invokeSuspend(Unit.INSTANCE);
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
                        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$5$invokeSuspend$$inlined$map$1

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

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    Object value;
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
                                        AnimatedValue animatedValue = (AnimatedValue) obj;
                                        if (animatedValue instanceof AnimatedValue.Animating) {
                                            value = ((AnimatedValue.Animating) animatedValue).getValue();
                                        } else {
                                            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                        }
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(value, anonymousClass1) == coroutineSingletons) {
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
                                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, new C02752(null)));
                        final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                        final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.2.1.5.3
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
                                if (clockController != null && clockController.getConfig().getUseCustomClockScene()) {
                                    keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.DefaultTransition);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
                                ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
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
                                int iIntValue = ((Number) obj2).intValue();
                                ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
                                if (clockController != null && (largeClock = clockController.getLargeClock()) != null && (events = largeClock.getEvents()) != null) {
                                    events.onFontSettingChanged(iIntValue);
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
                StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02711(this.$viewModel, ref$ObjectRef, this.$keyguardRootView, this.$clockSection, null), 7);
                final ConstraintLayout constraintLayout = this.$keyguardRootView;
                final KeyguardClockViewModel keyguardClockViewModel = this.$viewModel;
                standaloneCoroutineLaunchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$2$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        KeyguardClockViewBinder keyguardClockViewBinder = KeyguardClockViewBinder.INSTANCE;
                        Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
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
        public AnonymousClass2(KeyguardClockViewModel keyguardClockViewModel, ConstraintLayout constraintLayout, ClockSection clockSection, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel, Continuation continuation) {
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
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$keyguardRootView, this.$clockSection, this.$blueprintInteractor, this.$rootViewModel, this.$aodBurnInViewModel, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ KeyguardClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder$bind$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardClockViewModel keyguardClockViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardClockViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.currentClock;
                    C02781 c02781 = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder.bind.3.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ClockController clockController = (ClockController) obj2;
                            if (clockController != null) {
                                ClockFaceController smallClock = clockController.getSmallClock();
                                smallClock.getEvents().onThemeChanged(smallClock.getTheme());
                                ClockFaceController largeClock = clockController.getLargeClock();
                                largeClock.getEvents().onThemeChanged(largeClock.getTheme());
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(c02781, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(KeyguardClockViewModel keyguardClockViewModel, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardClockViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$viewModel, (Continuation) obj3);
            anonymousClass3.L$0 = (LifecycleOwner) obj;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, null);
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

    static {
        Reflection.getOrCreateKotlinClass(KeyguardClockViewBinder.class).getSimpleName().getClass();
    }

    private KeyguardClockViewBinder() {
    }

    public static final DisposableHandles bind(ClockSection clockSection, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardClockInteractor keyguardClockInteractor, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel) {
        DisposableHandles disposableHandles = new DisposableHandles();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(keyguardClockInteractor, constraintLayout, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, anonymousClass1));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, new AnonymousClass2(keyguardClockViewModel, constraintLayout, clockSection, keyguardBlueprintInteractor, keyguardRootViewModel, aodBurnInViewModel, null)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, new AnonymousClass3(keyguardClockViewModel, null)));
        return disposableHandles;
    }

    public static void cleanupClockViews(ClockController clockController, ConstraintLayout constraintLayout, AodBurnInLayer aodBurnInLayer) {
        if (clockController != null) {
            for (View view : clockController.getSmallClock().getLayout().getViews()) {
                if (aodBurnInLayer != null) {
                    aodBurnInLayer.removeView(view);
                }
                constraintLayout.removeView(view);
            }
            Iterator<T> it = clockController.getLargeClock().getLayout().getViews().iterator();
            while (it.hasNext()) {
                constraintLayout.removeView((View) it.next());
            }
        }
    }

    public final void addClockViews(ClockController clockController, ConstraintLayout constraintLayout) {
        if (clockController != null) {
            for (View view : clockController.getSmallClock().getLayout().getViews()) {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                constraintLayout.addView(view);
                Unit unit = Unit.INSTANCE;
                view.setVisibility(4);
            }
            for (View view2 : clockController.getLargeClock().getLayout().getViews()) {
                if (view2.getParent() != null) {
                    ((ViewGroup) view2.getParent()).removeView(view2);
                }
                constraintLayout.addView(view2);
                Unit unit2 = Unit.INSTANCE;
                view2.setVisibility(4);
            }
        }
    }

    public final void updateBurnInLayer(ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, ClockSize clockSize) {
        AodBurnInLayer aodBurnInLayer = keyguardClockViewModel.burnInLayer;
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[clockSize.ordinal()];
            if (i == 1) {
                for (View view : clockController.getSmallClock().getLayout().getViews()) {
                    if (aodBurnInLayer != null) {
                        aodBurnInLayer.removeView(view);
                    }
                }
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                for (View view2 : clockController.getSmallClock().getLayout().getViews()) {
                    if (aodBurnInLayer != null) {
                        aodBurnInLayer.addView(view2);
                    }
                }
            }
        }
        AodBurnInLayer aodBurnInLayer2 = keyguardClockViewModel.burnInLayer;
        if (aodBurnInLayer2 != null) {
            aodBurnInLayer2.updatePostLayout(constraintLayout);
        }
    }
}
