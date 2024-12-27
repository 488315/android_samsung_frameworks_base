package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Flags;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.viewmodel.BurnInParameters;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$updateBurnInParams$$inlined$launch$default$1;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

final class KeyguardRootViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ MutableStateFlow $burnInParams;
    final /* synthetic */ Map<Integer, View> $childViews;
    final /* synthetic */ ChipbarCoordinator $chipbarCoordinator;
    final /* synthetic */ KeyguardClockInteractor $clockInteractor;
    final /* synthetic */ ConfigurationState $configuration;
    final /* synthetic */ DeviceEntryHapticsInteractor $deviceEntryHapticsInteractor;
    final /* synthetic */ InteractionJankMonitor $interactionJankMonitor;
    final /* synthetic */ KeyguardViewMediator $keyguardViewMediator;
    final /* synthetic */ OccludingAppDeviceEntryMessageViewModel $occludingAppDeviceEntryMessageViewModel;
    final /* synthetic */ ScreenOffAnimationController $screenOffAnimationController;
    final /* synthetic */ ShadeInteractor $shadeInteractor;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardRootViewModel $viewModel;
    final /* synthetic */ ViewStateAccessor $viewState;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ MutableStateFlow $burnInParams;
        final /* synthetic */ Map<Integer, View> $childViews;
        final /* synthetic */ ChipbarCoordinator $chipbarCoordinator;
        final /* synthetic */ KeyguardClockInteractor $clockInteractor;
        final /* synthetic */ ConfigurationState $configuration;
        final /* synthetic */ DeviceEntryHapticsInteractor $deviceEntryHapticsInteractor;
        final /* synthetic */ InteractionJankMonitor $interactionJankMonitor;
        final /* synthetic */ KeyguardViewMediator $keyguardViewMediator;
        final /* synthetic */ OccludingAppDeviceEntryMessageViewModel $occludingAppDeviceEntryMessageViewModel;
        final /* synthetic */ ScreenOffAnimationController $screenOffAnimationController;
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardRootViewModel $viewModel;
        final /* synthetic */ ViewStateAccessor $viewState;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$13, reason: invalid class name */
        final class AnonymousClass13 extends SuspendLambda implements Function2 {
            final /* synthetic */ ShadeInteractor $shadeInteractor;
            final /* synthetic */ ViewGroup $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass13(ShadeInteractor shadeInteractor, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$shadeInteractor = shadeInteractor;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass13(this.$shadeInteractor, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass13) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) this.$shadeInteractor).isAnyFullyExpanded;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.13.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            viewGroup.setVisibility(((Boolean) obj2).booleanValue() ? 4 : 0);
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$14, reason: invalid class name */
        final class AnonymousClass14 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $burnInParams;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass14(MutableStateFlow mutableStateFlow, KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
                super(2, continuation);
                this.$burnInParams = mutableStateFlow;
                this.$viewModel = keyguardRootViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass14(this.$burnInParams, this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass14) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MutableStateFlow mutableStateFlow = this.$burnInParams;
                    final KeyguardRootViewModel keyguardRootViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.14.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BurnInParameters burnInParameters = (BurnInParameters) obj2;
                            KeyguardRootViewModel keyguardRootViewModel2 = KeyguardRootViewModel.this;
                            Job job = keyguardRootViewModel2.burnInJob;
                            if (job != null) {
                                job.cancel(null);
                            }
                            keyguardRootViewModel2.burnInJob = BuildersKt.launch$default(keyguardRootViewModel2.applicationScope, EmptyCoroutineContext.INSTANCE, null, new KeyguardRootViewModel$updateBurnInParams$$inlined$launch$default$1("KeyguardRootViewModel#aodBurnInViewModel", null, keyguardRootViewModel2, burnInParameters), 2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            final /* synthetic */ ViewStateAccessor $viewState;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardRootViewModel keyguardRootViewModel, ViewStateAccessor viewStateAccessor, ViewGroup viewGroup, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$viewState = viewStateAccessor;
                this.$view = viewGroup;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$viewState, this.$view, this.$childViews, continuation);
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
                    Flow alpha = this.$viewModel.alpha(this.$viewState);
                    FlowCollector flowCollector = new FlowCollector(this.$view, this.$childViews) { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.3.1
                        public final /* synthetic */ ViewGroup $view;

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            this.$view.setAlpha(((Number) obj2).floatValue());
                            Flags.keyguardBottomAreaRefactor();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (alpha.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, LifecycleOwner lifecycleOwner, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardRootViewModel keyguardRootViewModel, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, ConfigurationState configurationState, ScreenOffAnimationController screenOffAnimationController, KeyguardClockInteractor keyguardClockInteractor, KeyguardViewMediator keyguardViewMediator, ShadeInteractor shadeInteractor, MutableStateFlow mutableStateFlow, Continuation continuation) {
            super(2, continuation);
            this.$view = viewGroup;
            this.$interactionJankMonitor = interactionJankMonitor;
            this.$deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
            this.$vibratorHelper = vibratorHelper;
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
            this.$chipbarCoordinator = chipbarCoordinator;
            this.$viewModel = keyguardRootViewModel;
            this.$viewState = viewStateAccessor;
            this.$childViews = map;
            this.$configuration = configurationState;
            this.$screenOffAnimationController = screenOffAnimationController;
            this.$clockInteractor = keyguardClockInteractor;
            this.$keyguardViewMediator = keyguardViewMediator;
            this.$shadeInteractor = shadeInteractor;
            this.$burnInParams = mutableStateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$interactionJankMonitor, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, this.$$this$repeatWhenAttached, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$viewState, this.$childViews, this.$configuration, this.$screenOffAnimationController, this.$clockInteractor, this.$keyguardViewMediator, this.$shadeInteractor, this.$burnInParams, continuation);
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
            Flags.composeLockscreen();
            Flags.keyguardBottomAreaRefactor();
            Flags.deviceEntryUdfpsRefactor();
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$viewState, this.$view, this.$childViews, null), 3);
            Flags.migrateClocksToBlueprint();
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass13(this.$shadeInteractor, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass14(this.$burnInParams, this.$viewModel, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRootViewBinder$bind$2(ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardRootViewModel keyguardRootViewModel, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, ConfigurationState configurationState, ScreenOffAnimationController screenOffAnimationController, KeyguardClockInteractor keyguardClockInteractor, KeyguardViewMediator keyguardViewMediator, ShadeInteractor shadeInteractor, MutableStateFlow mutableStateFlow, Continuation continuation) {
        super(3, continuation);
        this.$view = viewGroup;
        this.$interactionJankMonitor = interactionJankMonitor;
        this.$deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
        this.$vibratorHelper = vibratorHelper;
        this.$occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
        this.$chipbarCoordinator = chipbarCoordinator;
        this.$viewModel = keyguardRootViewModel;
        this.$viewState = viewStateAccessor;
        this.$childViews = map;
        this.$configuration = configurationState;
        this.$screenOffAnimationController = screenOffAnimationController;
        this.$clockInteractor = keyguardClockInteractor;
        this.$keyguardViewMediator = keyguardViewMediator;
        this.$shadeInteractor = shadeInteractor;
        this.$burnInParams = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardRootViewBinder$bind$2 keyguardRootViewBinder$bind$2 = new KeyguardRootViewBinder$bind$2(this.$view, this.$interactionJankMonitor, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$viewState, this.$childViews, this.$configuration, this.$screenOffAnimationController, this.$clockInteractor, this.$keyguardViewMediator, this.$shadeInteractor, this.$burnInParams, (Continuation) obj3);
        keyguardRootViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardRootViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$interactionJankMonitor, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, lifecycleOwner, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$viewState, this.$childViews, this.$configuration, this.$screenOffAnimationController, this.$clockInteractor, this.$keyguardViewMediator, this.$shadeInteractor, this.$burnInParams, null);
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
