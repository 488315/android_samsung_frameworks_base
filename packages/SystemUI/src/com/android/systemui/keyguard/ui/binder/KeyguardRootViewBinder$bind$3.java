package com.android.systemui.keyguard.ui.binder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.BurnInParameters;
import com.android.systemui.keyguard.ui.viewmodel.BurnInScaleViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.keyguard.ui.viewmodel.TransitionData;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.ui.AnimatedValue;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewBinder$bind$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardBlueprintViewModel $blueprintViewModel;
    final /* synthetic */ MutableStateFlow $burnInParams;
    final /* synthetic */ Map<Integer, View> $childViews;
    final /* synthetic */ ChipbarCoordinator $chipbarCoordinator;
    final /* synthetic */ ConfigurationState $configuration;
    final /* synthetic */ DeviceEntryHapticsInteractor $deviceEntryHapticsInteractor;
    final /* synthetic */ MSDLPlayer $msdlPlayer;
    final /* synthetic */ OccludingAppDeviceEntryMessageViewModel $occludingAppDeviceEntryMessageViewModel;
    final /* synthetic */ ShadeInteractor $shadeInteractor;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardRootViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ KeyguardBlueprintViewModel $blueprintViewModel;
        final /* synthetic */ MutableStateFlow $burnInParams;
        final /* synthetic */ Map<Integer, View> $childViews;
        final /* synthetic */ ChipbarCoordinator $chipbarCoordinator;
        final /* synthetic */ ConfigurationState $configuration;
        final /* synthetic */ DeviceEntryHapticsInteractor $deviceEntryHapticsInteractor;
        final /* synthetic */ MSDLPlayer $msdlPlayer;
        final /* synthetic */ OccludingAppDeviceEntryMessageViewModel $occludingAppDeviceEntryMessageViewModel;
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardRootViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$childViews, continuation);
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
                    Flow flow = this.$viewModel.burnInLayerVisibility;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                            if (view != null) {
                                view.setVisibility(intValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$childViews, continuation);
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
                    Flow flow = this.$viewModel.scale;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BurnInScaleViewModel burnInScaleViewModel = (BurnInScaleViewModel) obj2;
                            if (burnInScaleViewModel.scaleClockOnly) {
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.largeClockId, map);
                                if (view != null) {
                                    float f = burnInScaleViewModel.scale;
                                    view.setScaleX(f);
                                    view.setScaleY(f);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintViewModel $blueprintViewModel;
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardBlueprintViewModel keyguardBlueprintViewModel, Map<Integer, View> map, KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
                super(2, continuation);
                this.$blueprintViewModel = keyguardBlueprintViewModel;
                this.$childViews = map;
                this.$viewModel = keyguardRootViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$blueprintViewModel, this.$childViews, this.$viewModel, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = this.$blueprintViewModel.currentTransition;
                    final Map<Integer, View> map = this.$childViews;
                    final KeyguardRootViewModel keyguardRootViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (((TransitionData) obj2) == null) {
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.nsslPlaceholderId, map);
                                if (view != null) {
                                    float top = view.getTop();
                                    float bottom = view.getBottom();
                                    KeyguardRootViewModel keyguardRootViewModel2 = keyguardRootViewModel;
                                    keyguardRootViewModel2.getClass();
                                    NotificationContainerBounds notificationContainerBounds = new NotificationContainerBounds(top, bottom, true);
                                    KeyguardInteractor keyguardInteractor = keyguardRootViewModel2.keyguardInteractor;
                                    keyguardInteractor.getClass();
                                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                    int i2 = SceneContainerFlag.$r8$clinit;
                                    keyguardInteractor._notificationPlaceholderBounds.updateState(null, notificationContainerBounds);
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintViewModel $blueprintViewModel;
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ ConfigurationState $configuration;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(ConfigurationState configurationState, KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$configuration = configurationState;
                this.$viewModel = keyguardRootViewModel;
                this.$blueprintViewModel = keyguardBlueprintViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass6 anonymousClass6 = new AnonymousClass6(this.$configuration, this.$viewModel, this.$blueprintViewModel, this.$childViews, continuation);
                anonymousClass6.L$0 = obj;
                return anonymousClass6;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:13:0x004c, code lost:
            
                if (r6.collect(r1, r5) == r0) goto L15;
             */
            /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
            
                return r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:
            
                if (r6 == r0) goto L15;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r6) {
                /*
                    r5 = this;
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r5.label
                    r2 = 2
                    r3 = 1
                    if (r1 == 0) goto L1c
                    if (r1 == r3) goto L18
                    if (r1 == r2) goto L14
                    java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                    java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                    r5.<init>(r6)
                    throw r5
                L14:
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L4f
                L18:
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L37
                L1c:
                    kotlin.ResultKt.throwOnFailure(r6)
                    java.lang.Object r6 = r5.L$0
                    kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
                    com.android.systemui.common.ui.ConfigurationState r1 = r5.$configuration
                    r4 = 2131170599(0x7f071527, float:1.795556E38)
                    com.android.systemui.common.ui.ConfigurationStateImpl r1 = (com.android.systemui.common.ui.ConfigurationStateImpl) r1
                    com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1 r1 = r1.getDimensionPixelSize(r4)
                    r5.label = r3
                    java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.stateIn(r1, r6, r5)
                    if (r6 != r0) goto L37
                    goto L4e
                L37:
                    kotlinx.coroutines.flow.StateFlow r6 = (kotlinx.coroutines.flow.StateFlow) r6
                    com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel r6 = r5.$viewModel
                    kotlinx.coroutines.flow.StateFlow r6 = r6.isNotifIconContainerVisible
                    com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$6$1 r1 = new com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$6$1
                    com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel r3 = r5.$blueprintViewModel
                    java.util.Map<java.lang.Integer, android.view.View> r4 = r5.$childViews
                    r1.<init>()
                    r5.label = r2
                    java.lang.Object r5 = r6.collect(r1, r5)
                    if (r5 != r0) goto L4f
                L4e:
                    return r0
                L4f:
                    kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
                    r5.<init>()
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3.AnonymousClass1.AnonymousClass6.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintViewModel $blueprintViewModel;
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$blueprintViewModel = keyguardBlueprintViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$blueprintViewModel, this.$childViews, continuation);
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
                    StateFlow stateFlow = this.$viewModel.isAodPromotedNotifVisible;
                    final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$blueprintViewModel;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Object value;
                            Object value2;
                            Object value3;
                            final AnimatedValue animatedValue = (AnimatedValue) obj2;
                            boolean z = animatedValue instanceof AnimatedValue.Animating;
                            if (z) {
                                value = ((AnimatedValue.Animating) animatedValue).getValue();
                            } else {
                                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                            }
                            if (((Boolean) value).booleanValue()) {
                                KeyguardBlueprintViewModel.this.keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.NoTransition);
                            }
                            View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodPromotedNotificationId, map);
                            if (view != null) {
                                KeyguardRootViewBinder.INSTANCE.getClass();
                                view.animate().cancel();
                                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$setAodPromotedNotifIsVisible$animatorListener$1
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        AnimatedValue animatedValue2 = animatedValue;
                                        if (animatedValue2 instanceof AnimatedValue.Animating) {
                                            ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating().invoke();
                                        }
                                    }
                                };
                                if (z) {
                                    if (animatedValue instanceof AnimatedValue.Animating) {
                                        value3 = ((AnimatedValue.Animating) animatedValue).getValue();
                                    } else {
                                        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        value3 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                    }
                                    if (((Boolean) value3).booleanValue()) {
                                        view.setAlpha(0.0f);
                                        view.setVisibility(0);
                                        CrossFadeHelper.fadeIn(view, animatorListenerAdapter);
                                    } else {
                                        CrossFadeHelper.fadeOut(view, animatorListenerAdapter);
                                    }
                                } else {
                                    if (z) {
                                        value2 = ((AnimatedValue.Animating) animatedValue).getValue();
                                    } else {
                                        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        value2 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                    }
                                    if (((Boolean) value2).booleanValue()) {
                                        view.setAlpha(1.0f);
                                        view.setVisibility(0);
                                    } else {
                                        view.setAlpha(0.0f);
                                        view.setVisibility(8);
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ ShadeInteractor $shadeInteractor;
            final /* synthetic */ ViewGroup $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(ShadeInteractor shadeInteractor, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$shadeInteractor = shadeInteractor;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.$shadeInteractor, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) this.$shadeInteractor).isAnyFullyExpanded;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.8.1
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $burnInParams;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(MutableStateFlow mutableStateFlow, KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
                super(2, continuation);
                this.$burnInParams = mutableStateFlow;
                this.$viewModel = keyguardRootViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$burnInParams, this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MutableStateFlow mutableStateFlow = this.$burnInParams;
                    final KeyguardRootViewModel keyguardRootViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BurnInParameters burnInParameters = (BurnInParameters) obj2;
                            AodBurnInViewModel aodBurnInViewModel = KeyguardRootViewModel.this.aodBurnInViewModel;
                            aodBurnInViewModel.getClass();
                            if (burnInParameters.minViewY < burnInParameters.topInset) {
                                Log.w("AodBurnInViewModel", "minViewY is below topInset: " + burnInParameters);
                                burnInParameters = BurnInParameters.copy$default(burnInParameters, 0, burnInParameters.topInset, null, null, 13);
                            }
                            aodBurnInViewModel.burnInParams.updateState(null, burnInParameters);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(ViewGroup viewGroup, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, LifecycleOwner lifecycleOwner, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, ShadeInteractor shadeInteractor, MutableStateFlow mutableStateFlow, MSDLPlayer mSDLPlayer, Continuation continuation) {
            super(2, continuation);
            this.$view = viewGroup;
            this.$deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
            this.$vibratorHelper = vibratorHelper;
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
            this.$chipbarCoordinator = chipbarCoordinator;
            this.$viewModel = keyguardRootViewModel;
            this.$childViews = map;
            this.$blueprintViewModel = keyguardBlueprintViewModel;
            this.$configuration = configurationState;
            this.$shadeInteractor = shadeInteractor;
            this.$burnInParams = mutableStateFlow;
            this.$msdlPlayer = mSDLPlayer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, this.$$this$repeatWhenAttached, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$childViews, this.$blueprintViewModel, this.$configuration, this.$shadeInteractor, this.$burnInParams, this.$msdlPlayer, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$childViews, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$childViews, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$blueprintViewModel, this.$childViews, this.$viewModel, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$configuration, this.$viewModel, this.$blueprintViewModel, this.$childViews, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$blueprintViewModel, this.$childViews, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.$shadeInteractor, this.$view, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$burnInParams, this.$viewModel, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRootViewBinder$bind$3(ViewGroup viewGroup, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, ShadeInteractor shadeInteractor, MutableStateFlow mutableStateFlow, MSDLPlayer mSDLPlayer, Continuation continuation) {
        super(3, continuation);
        this.$view = viewGroup;
        this.$deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
        this.$vibratorHelper = vibratorHelper;
        this.$occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
        this.$chipbarCoordinator = chipbarCoordinator;
        this.$viewModel = keyguardRootViewModel;
        this.$childViews = map;
        this.$blueprintViewModel = keyguardBlueprintViewModel;
        this.$configuration = configurationState;
        this.$shadeInteractor = shadeInteractor;
        this.$burnInParams = mutableStateFlow;
        this.$msdlPlayer = mSDLPlayer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardRootViewBinder$bind$3 keyguardRootViewBinder$bind$3 = new KeyguardRootViewBinder$bind$3(this.$view, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$childViews, this.$blueprintViewModel, this.$configuration, this.$shadeInteractor, this.$burnInParams, this.$msdlPlayer, (Continuation) obj3);
        keyguardRootViewBinder$bind$3.L$0 = (LifecycleOwner) obj;
        return keyguardRootViewBinder$bind$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, lifecycleOwner, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$childViews, this.$blueprintViewModel, this.$configuration, this.$shadeInteractor, this.$burnInParams, this.$msdlPlayer, null);
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
