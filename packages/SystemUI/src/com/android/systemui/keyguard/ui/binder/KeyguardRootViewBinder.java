package com.android.systemui.keyguard.ui.binder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.WallpaperManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.AuthInteractionProperties;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.common.ui.ConfigurationStateImpl;
import com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1;
import com.android.systemui.common.ui.view.ViewExtKt$onApplyWindowInsets$1;
import com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$2;
import com.android.systemui.common.ui.view.ViewExtKt$onTouchListener$1;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.customization.R$id;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.StateToValue;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.AodPromotedNotificationSection;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.BurnInParameters;
import com.android.systemui.keyguard.ui.viewmodel.BurnInScaleViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.keyguard.ui.viewmodel.TransitionData;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperFocalAreaViewModel;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardRootViewBinder {
    public static final int aodNotificationIconContainerId;
    public static final int aodPromotedNotificationId;
    public static final int bcSmartspaceId;
    public static final int deviceEntryIcon;
    public static final int endButton;
    public static final int indicationArea;
    public static final int largeClockId;
    public static final int nsslPlaceholderId;
    public static final int startButton;
    public static final KeyguardRootViewBinder INSTANCE = new KeyguardRootViewBinder();
    public static final int burnInLayerId = R.id.burn_in_layer;

    public final class OnLayoutChange implements View.OnLayoutChangeListener {
        public final KeyguardBlueprintViewModel blueprintViewModel;
        public final MutableStateFlow burnInParams;
        public final Map childViews;
        public final Logger logger;
        public TransitionData prevTransition;
        public final KeyguardSmartspaceViewModel smartspaceViewModel;
        public final KeyguardRootViewModel viewModel;

        public OnLayoutChange(KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, Map<Integer, ? extends View> map, MutableStateFlow mutableStateFlow, Logger logger) {
            this.viewModel = keyguardRootViewModel;
            this.blueprintViewModel = keyguardBlueprintViewModel;
            this.smartspaceViewModel = keyguardSmartspaceViewModel;
            this.childViews = map;
            this.burnInParams = mutableStateFlow;
            this.logger = logger;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            Object value;
            BurnInParameters burnInParameters;
            int iMin;
            int iIntValue = ((Number) this.smartspaceViewModel.bcSmartspaceVisibility.$$delegate_0.getValue()).intValue();
            View view2 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.bcSmartspaceId));
            boolean z = iIntValue != (view2 != null ? view2.getVisibility() : 8);
            View view3 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.nsslPlaceholderId));
            if (view3 != null) {
                TransitionData transitionData = (TransitionData) this.blueprintViewModel.currentTransition.$$delegate_0.getValue();
                boolean z2 = transitionData != null && transitionData.config.type.getAnimateNotifChanges();
                if (Intrinsics.areEqual(this.prevTransition, transitionData) && z2 && !z) {
                    Logger.w$default(this.logger, "Skipping onNotificationContainerBoundsChanged during transition", null, 2, null);
                    return;
                }
                this.prevTransition = transitionData;
                KeyguardRootViewModel keyguardRootViewModel = this.viewModel;
                float top = view3.getTop();
                float bottom = view3.getBottom();
                boolean z3 = z2 || z;
                keyguardRootViewModel.getClass();
                NotificationContainerBounds notificationContainerBounds = new NotificationContainerBounds(top, bottom, z3);
                KeyguardInteractor keyguardInteractor = keyguardRootViewModel.keyguardInteractor;
                keyguardInteractor.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i9 = SceneContainerFlag.$r8$clinit;
                keyguardInteractor._notificationPlaceholderBounds.updateState(null, notificationContainerBounds);
            }
            MutableStateFlow mutableStateFlow = this.burnInParams;
            do {
                value = mutableStateFlow.getValue();
                burnInParameters = (BurnInParameters) value;
                iMin = Integer.MAX_VALUE;
                for (Map.Entry entry : this.childViews.entrySet()) {
                    ((Number) entry.getKey()).intValue();
                    View view4 = (View) entry.getValue();
                    iMin = Math.min(iMin, (view4.getId() == KeyguardRootViewBinder.burnInLayerId || view4.getVisibility() != 0 || view4.getWidth() <= 0 || view4.getHeight() <= 0) ? Integer.MAX_VALUE : view4.getTop());
                }
            } while (!mutableStateFlow.compareAndSet(value, BurnInParameters.copy$default(burnInParameters, 0, iMin, null, null, 13)));
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ Map<Integer, View> $childViews;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardRootViewModel $viewModel;
        final /* synthetic */ ViewStateAccessor $viewState;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            final /* synthetic */ ViewStateAccessor $viewState;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C03011 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardRootViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03011(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardRootViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03011(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03011) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final Rect rect = new Rect();
                        Flow flow = this.$viewModel.topClippingBounds;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Integer num = (Integer) obj2;
                                if (num == null) {
                                    viewGroup.setClipBounds(null);
                                } else {
                                    Rect rect2 = rect;
                                    ViewGroup viewGroup2 = viewGroup;
                                    rect2.top = num.intValue();
                                    rect2.left = viewGroup2.getLeft();
                                    rect2.right = viewGroup2.getRight();
                                    rect2.bottom = viewGroup2.getBottom();
                                    viewGroup.setClipBounds(rect);
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C03032 extends SuspendLambda implements Function2 {
                final /* synthetic */ Map<Integer, View> $childViews;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardRootViewModel $viewModel;
                final /* synthetic */ ViewStateAccessor $viewState;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03032(KeyguardRootViewModel keyguardRootViewModel, ViewStateAccessor viewStateAccessor, ViewGroup viewGroup, Map<Integer, View> map, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardRootViewModel;
                    this.$viewState = viewStateAccessor;
                    this.$view = viewGroup;
                    this.$childViews = map;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03032(this.$viewModel, this.$viewState, this.$view, this.$childViews, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03032) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow flowAlpha = this.$viewModel.alpha(this.$viewState);
                        final ViewGroup viewGroup = this.$view;
                        final Map<Integer, View> map = this.$childViews;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                float fFloatValue = ((Number) obj2).floatValue();
                                viewGroup.setAlpha(fFloatValue);
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                                if (view != null) {
                                    view.setAlpha(fFloatValue);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowAlpha.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardRootViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardRootViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$viewModel, this.$view, continuation);
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
                        Flow flow = this.$viewModel.scaleFromZoomOut;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                float fFloatValue = ((Number) obj2).floatValue();
                                viewGroup.setScaleX(fFloatValue);
                                viewGroup.setScaleY(fFloatValue);
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$4, reason: invalid class name */
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
                        Flow flow = this.$viewModel.translationY;
                        final Map<Integer, View> map = this.$childViews;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.4.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                float fFloatValue = ((Number) obj2).floatValue();
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                                if (view != null) {
                                    view.setTranslationY(fFloatValue);
                                }
                                View view2 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.largeClockId, map);
                                if (view2 != null) {
                                    view2.setTranslationY(fFloatValue);
                                }
                                View view3 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodPromotedNotificationId, map);
                                if (view3 != null) {
                                    view3.setTranslationY(fFloatValue);
                                }
                                View view4 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodNotificationIconContainerId, map);
                                if (view4 != null) {
                                    view4.setTranslationY(fFloatValue);
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ Map<Integer, View> $childViews;
                final /* synthetic */ KeyguardRootViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardRootViewModel;
                    this.$childViews = map;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.$viewModel, this.$childViews, continuation);
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
                        Flow flow = this.$viewModel.translationX;
                        final Map<Integer, View> map = this.$childViews;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.5.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                KeyguardState keyguardState;
                                StateToValue stateToValue = (StateToValue) obj2;
                                Float f = stateToValue.value;
                                if (f == null) {
                                    return Unit.INSTANCE;
                                }
                                float fFloatValue = f.floatValue();
                                KeyguardState keyguardState2 = KeyguardState.AOD;
                                KeyguardState keyguardState3 = stateToValue.from;
                                if (keyguardState3 == keyguardState2 || (keyguardState = stateToValue.to) == keyguardState2) {
                                    View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                                    if (view != null) {
                                        view.setTranslationX(fFloatValue);
                                    }
                                    View view2 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodPromotedNotificationId, map);
                                    if (view2 != null) {
                                        view2.setTranslationX(fFloatValue);
                                    }
                                    View view3 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodNotificationIconContainerId, map);
                                    if (view3 != null) {
                                        view3.setTranslationX(fFloatValue);
                                    }
                                } else {
                                    KeyguardState keyguardState4 = KeyguardState.GLANCEABLE_HUB;
                                    if (keyguardState3 == keyguardState4 || keyguardState == keyguardState4) {
                                        for (Map.Entry entry : map.entrySet()) {
                                            int iIntValue = ((Number) entry.getKey()).intValue();
                                            View view4 = (View) entry.getValue();
                                            if (iIntValue != KeyguardRootViewBinder.indicationArea && iIntValue != KeyguardRootViewBinder.startButton && iIntValue != KeyguardRootViewBinder.endButton && iIntValue != KeyguardRootViewBinder.deviceEntryIcon) {
                                                view4.setTranslationX(fFloatValue);
                                            }
                                        }
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$view = viewGroup;
                this.$viewState = viewStateAccessor;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$viewState, this.$childViews, continuation);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03011(this.$viewModel, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03032(this.$viewModel, this.$viewState, this.$view, this.$childViews, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$childViews, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$childViews, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardRootViewModel;
            this.$view = viewGroup;
            this.$viewState = viewStateAccessor;
            this.$childViews = map;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$view, this.$viewState, this.$childViews, (Continuation) obj3);
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
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$viewState, this.$childViews, null);
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$3$1$3, reason: invalid class name and collision with other inner class name */
            final class C03083 extends SuspendLambda implements Function2 {
                final /* synthetic */ Map<Integer, View> $childViews;
                final /* synthetic */ KeyguardRootViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03083(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardRootViewModel;
                    this.$childViews = map;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03083(this.$viewModel, this.$childViews, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03083) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                int iIntValue = ((Number) obj2).intValue();
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                                if (view != null) {
                                    view.setVisibility(iIntValue);
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

                /* JADX WARN: Code restructure failed: missing block: B:14:0x004c, code lost:
                
                    if (r6.collect(r1, r5) == r0) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1 dimensionPixelSize = ((ConfigurationStateImpl) this.$configuration).getDimensionPixelSize(R.dimen.shelf_appear_translation);
                        this.label = 1;
                        obj = FlowKt.stateIn(dimensionPixelSize, coroutineScope, this);
                        if (obj != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        throw new KotlinNothingValueException();
                    }
                    ResultKt.throwOnFailure(obj);
                    StateFlow stateFlow = this.$viewModel.isNotifIconContainerVisible;
                    final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$blueprintViewModel;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.3.1.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Object value;
                            Object value2;
                            Object value3;
                            int i2;
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
                                keyguardBlueprintViewModel.keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.NoTransition);
                            }
                            View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodNotificationIconContainerId, map);
                            if (view != null) {
                                KeyguardRootViewBinder.INSTANCE.getClass();
                                view.animate().cancel();
                                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$setAodNotifIconContainerIsVisible$animatorListener$1
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
                                        value2 = ((AnimatedValue.Animating) animatedValue).getValue();
                                    } else {
                                        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        value2 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                    }
                                    if (((Boolean) value2).booleanValue()) {
                                        CrossFadeHelper.fadeIn(view, animatorListenerAdapter);
                                    } else {
                                        CrossFadeHelper.fadeOut(view, animatorListenerAdapter);
                                    }
                                } else {
                                    if (z) {
                                        value3 = ((AnimatedValue.Animating) animatedValue).getValue();
                                    } else {
                                        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        value3 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                    }
                                    if (((Boolean) value3).booleanValue()) {
                                        view.setAlpha(1.0f);
                                        i2 = 0;
                                    } else {
                                        view.setAlpha(0.0f);
                                        i2 = 4;
                                    }
                                    view.setVisibility(i2);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 2;
                }
            }

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
                                    keyguardBlueprintViewModel.keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.NoTransition);
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
                                BurnInParameters burnInParametersCopy$default = (BurnInParameters) obj2;
                                AodBurnInViewModel aodBurnInViewModel = keyguardRootViewModel.aodBurnInViewModel;
                                aodBurnInViewModel.getClass();
                                if (burnInParametersCopy$default.minViewY < burnInParametersCopy$default.topInset) {
                                    Log.w("AodBurnInViewModel", "minViewY is below topInset: " + burnInParametersCopy$default);
                                    burnInParametersCopy$default = BurnInParameters.copy$default(burnInParametersCopy$default, 0, burnInParametersCopy$default.topInset, null, null, 13);
                                }
                                aodBurnInViewModel.burnInParams.updateState(null, burnInParametersCopy$default);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03083(this.$viewModel, this.$childViews, null), 7);
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
        public AnonymousClass3(ViewGroup viewGroup, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, ShadeInteractor shadeInteractor, MutableStateFlow mutableStateFlow, MSDLPlayer mSDLPlayer, Continuation continuation) {
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
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$view, this.$deviceEntryHapticsInteractor, this.$vibratorHelper, this.$occludingAppDeviceEntryMessageViewModel, this.$chipbarCoordinator, this.$viewModel, this.$childViews, this.$blueprintViewModel, this.$configuration, this.$shadeInteractor, this.$burnInParams, this.$msdlPlayer, (Continuation) obj3);
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function3 {
        final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5$1$1, reason: invalid class name and collision with other inner class name */
            final class C03161 extends SuspendLambda implements Function2 {
                final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03161(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03161(this.$wallpaperFocalAreaViewModel, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03161) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel = this.$wallpaperFocalAreaViewModel;
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = wallpaperFocalAreaViewModel.wallpaperFocalAreaBounds;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.5.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                RectF rectF = (RectF) obj2;
                                WallpaperFocalAreaRepositoryImpl wallpaperFocalAreaRepositoryImpl = (WallpaperFocalAreaRepositoryImpl) wallpaperFocalAreaViewModel.wallpaperFocalAreaInteractor.wallpaperFocalAreaRepository;
                                wallpaperFocalAreaRepositoryImpl._wallpaperFocalAreaBounds.setValue(rectF);
                                WallpaperRepositoryImpl wallpaperRepositoryImpl = (WallpaperRepositoryImpl) wallpaperFocalAreaRepositoryImpl.wallpaperRepository;
                                if (WallpaperRepositoryImpl.DEBUG) {
                                    wallpaperRepositoryImpl.getClass();
                                    Log.d(WallpaperRepositoryImpl.TAG, "sendLockScreenLayoutChangeCommand " + rectF);
                                }
                                WallpaperManager wallpaperManager = wallpaperRepositoryImpl.wallpaperManager;
                                View view = wallpaperRepositoryImpl.rootView;
                                IBinder windowToken = view != null ? view.getWindowToken() : null;
                                Bundle bundle = new Bundle();
                                bundle.putFloat("wallpaperFocalAreaLeft", rectF.left);
                                bundle.putFloat("wallpaperFocalAreaRight", rectF.right);
                                bundle.putFloat("wallpaperFocalAreaTop", rectF.top);
                                bundle.putFloat("wallpaperFocalAreaBottom", rectF.bottom);
                                Unit unit = Unit.INSTANCE;
                                wallpaperManager.sendWallpaperCommand(windowToken, "android.wallpaper.lockscreen_layout_changed", 0, 0, 0, bundle);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
            public AnonymousClass1(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
                super(2, continuation);
                this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$wallpaperFocalAreaViewModel, continuation);
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
                if (((Boolean) this.$wallpaperFocalAreaViewModel.hasFocalArea.$$delegate_0.getValue()).booleanValue()) {
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03161(this.$wallpaperFocalAreaViewModel, null), 7);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
            super(3, continuation);
            this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.$wallpaperFocalAreaViewModel, (Continuation) obj3);
            anonymousClass5.L$0 = (LifecycleOwner) obj;
            return anonymousClass5.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$wallpaperFocalAreaViewModel, null);
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
        AodPromotedNotificationSection.Companion.getClass();
        aodPromotedNotificationId = AodPromotedNotificationSection.viewId;
        aodNotificationIconContainerId = R.id.aod_notification_icon_container;
        largeClockId = R$id.lockscreen_clock_view_large;
        bcSmartspaceId = R.id.bc_smartspace_view;
        indicationArea = R.id.keyguard_indication_area;
        startButton = R.id.start_button;
        endButton = R.id.end_button;
        deviceEntryIcon = R.id.device_entry_icon_view;
        nsslPlaceholderId = R.id.nssl_placeholder;
        new AuthInteractionProperties(null, 1, null);
    }

    private KeyguardRootViewBinder() {
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1] */
    public static final DisposableHandles bind(final ViewGroup viewGroup, final KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, ShadeInteractor shadeInteractor, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, final FalsingManager falsingManager, CoroutineDispatcher coroutineDispatcher, MSDLPlayer mSDLPlayer, LogBuffer logBuffer, WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel) {
        Object value;
        Function0 function0;
        final int i;
        DisposableHandles disposableHandles = new DisposableHandles();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                FalsingManager falsingManager2 = falsingManager;
                if (falsingManager2 != null && !falsingManager2.isFalseTap(1)) {
                    KeyguardRootViewModel keyguardRootViewModel2 = keyguardRootViewModel;
                    ((KeyguardRepositoryImpl) keyguardRootViewModel2.keyguardInteractor.repository).lastRootViewTapPosition.updateState(null, new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return false;
            }
        });
        disposableHandles.plusAssign(new ViewExtKt$onTouchListener$1(viewGroup));
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new BurnInParameters(0, 0, null, null, 15, null));
        final int i2 = 2;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, coroutineDispatcher, new AnonymousClass2(keyguardRootViewModel, viewGroup, new ViewStateAccessor(new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = viewGroup;
                switch (i2) {
                    case 0:
                        View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view != null) {
                            return Float.valueOf(view.getTranslationY());
                        }
                        return null;
                    case 1:
                        View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view2 != null) {
                            return Float.valueOf(view2.getTranslationX());
                        }
                        return null;
                    default:
                        KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                        return Float.valueOf(((ViewGroup) obj).getAlpha());
                }
            }
        }, null, null, 6, null), linkedHashMap, null)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass3(viewGroup, deviceEntryHapticsInteractor, vibratorHelper, occludingAppDeviceEntryMessageViewModel, chipbarCoordinator, keyguardRootViewModel, linkedHashMap, keyguardBlueprintViewModel, configurationState, shadeInteractor, stateFlowImplMutableStateFlow, mSDLPlayer, null)));
        do {
            value = stateFlowImplMutableStateFlow.getValue();
            final int i3 = 0;
            function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object obj = linkedHashMap;
                    switch (i3) {
                        case 0:
                            View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                            if (view != null) {
                                return Float.valueOf(view.getTranslationY());
                            }
                            return null;
                        case 1:
                            View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                            if (view2 != null) {
                                return Float.valueOf(view2.getTranslationX());
                            }
                            return null;
                        default:
                            KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                            return Float.valueOf(((ViewGroup) obj).getAlpha());
                    }
                }
            };
            i = 1;
        } while (!stateFlowImplMutableStateFlow.compareAndSet(value, BurnInParameters.copy$default((BurnInParameters) value, 0, 0, function0, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = linkedHashMap;
                switch (i) {
                    case 0:
                        View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view != null) {
                            return Float.valueOf(view.getTranslationY());
                        }
                        return null;
                    case 1:
                        View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view2 != null) {
                            return Float.valueOf(view2.getTranslationX());
                        }
                        return null;
                    default:
                        KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                        return Float.valueOf(((ViewGroup) obj).getAlpha());
                }
            }
        }, 3)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass5(wallpaperFocalAreaViewModel, null)));
        OnLayoutChange onLayoutChange = new OnLayoutChange(keyguardRootViewModel, keyguardBlueprintViewModel, keyguardSmartspaceViewModel, linkedHashMap, stateFlowImplMutableStateFlow, new Logger(logBuffer, "KeyguardRootViewBinder"));
        viewGroup.addOnLayoutChangeListener(onLayoutChange);
        disposableHandles.plusAssign(new ViewExtKt$onLayoutChanged$2(viewGroup, onLayoutChange));
        viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.6
            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewAdded(View view, View view2) {
                linkedHashMap.put(Integer.valueOf(view2.getId()), view2);
            }

            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewRemoved(View view, View view2) {
                linkedHashMap.remove(Integer.valueOf(view2.getId()));
            }
        });
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.7
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                viewGroup.setOnHierarchyChangeListener(null);
                linkedHashMap.clear();
            }
        });
        viewGroup.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.8
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Object value2;
                int iSystemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
                MutableStateFlow mutableStateFlow = stateFlowImplMutableStateFlow;
                do {
                    value2 = mutableStateFlow.getValue();
                } while (!mutableStateFlow.compareAndSet(value2, BurnInParameters.copy$default((BurnInParameters) value2, windowInsets.getInsetsIgnoringVisibility(iSystemBars).top, 0, null, null, 14)));
                return windowInsets;
            }
        });
        disposableHandles.plusAssign(new ViewExtKt$onApplyWindowInsets$1(viewGroup));
        return disposableHandles;
    }
}
