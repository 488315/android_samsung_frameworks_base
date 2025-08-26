package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.shade.SamsungShadeHeaderControllerExt;
import com.android.systemui.shade.ShadeHeaderController.CustomizerAnimationListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class NotificationsQSContainerController extends ViewController implements QSContainerController {
    public final AmbientState ambientState;
    public final DelayableExecutor delayableExecutor;
    public final NotificationsQSContainerController$delayedInsetSetter$1 delayedInsetSetter;
    public int footerActionsOffset;
    public final FragmentService fragmentService;
    public boolean isQSCustomizerAnimating;
    public boolean isQSCustomizing;
    public final Lazy largeScreenHeaderHelperLazy;
    public boolean largeScreenShadeHeaderActive;
    public int largeScreenShadeHeaderHeight;
    public final LauncherProxyService launcherProxyService;
    public final NavigationModeController navigationModeController;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public int panelMarginHorizontal;
    public final PanelPopOverManager panelPopOverManager;
    public final Lazy resourcePickerLazy;
    public int scrimShadeBottomMargin;
    public final ShadeHeaderController shadeHeaderController;
    public int shadeHeaderHeight;
    public final ShadeInteractor shadeInteractor;
    public final SplitShadeStateController splitShadeStateController;
    public final NotificationsQSContainerController$taskbarVisibilityListener$1 taskbarVisibilityListener;

    /* renamed from: com.android.systemui.shade.NotificationsQSContainerController$onInit$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.shade.NotificationsQSContainerController$onInit$1$1, reason: invalid class name and collision with other inner class name */
        final class C04611 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ NotificationsQSContainerController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04611(NotificationsQSContainerController notificationsQSContainerController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationsQSContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04611(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04611) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow stateFlowIsQsExpanded = ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.isQsExpanded();
                    final NotificationsQSContainerController notificationsQSContainerController = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.NotificationsQSContainerController.onInit.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            ((NotificationsQuickSettingsContainer) ((ViewController) notificationsQSContainerController).mView).invalidate();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlowIsQsExpanded.collect(flowCollector, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = NotificationsQSContainerController.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new C04611(NotificationsQSContainerController.this, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shade.NotificationsQSContainerController$onViewAttached$2, reason: invalid class name and case insensitive filesystem */
    public final class C10362 implements Consumer {
        public C10362() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) throws Resources.NotFoundException {
            NotificationsQSContainerController.this.updateResources$1();
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1] */
    public NotificationsQSContainerController(NotificationsQuickSettingsContainer notificationsQuickSettingsContainer, NavigationModeController navigationModeController, LauncherProxyService launcherProxyService, ShadeHeaderController shadeHeaderController, ShadeInteractor shadeInteractor, FragmentService fragmentService, DelayableExecutor delayableExecutor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SplitShadeStateController splitShadeStateController, Lazy lazy, Lazy lazy2, PanelPopOverManager panelPopOverManager, AmbientState ambientState) {
        super(notificationsQuickSettingsContainer);
        this.navigationModeController = navigationModeController;
        this.launcherProxyService = launcherProxyService;
        this.shadeHeaderController = shadeHeaderController;
        this.shadeInteractor = shadeInteractor;
        this.fragmentService = fragmentService;
        this.delayableExecutor = delayableExecutor;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.splitShadeStateController = splitShadeStateController;
        this.largeScreenHeaderHelperLazy = lazy;
        this.resourcePickerLazy = lazy2;
        this.panelPopOverManager = panelPopOverManager;
        this.ambientState = ambientState;
        this.taskbarVisibilityListener = new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1
            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
                this.this$0.getClass();
            }
        };
        this.delayedInsetSetter = new NotificationsQSContainerController$delayedInsetSetter$1(this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        RepeatWhenAttachedKt.repeatWhenAttached(this.mView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        this.navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onInit$currentMode$1
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                QuickStepContract.isGesturalMode(i);
                this.this$0.getClass();
            }
        });
        boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        ((NotificationsQuickSettingsContainer) this.mView).mStackScroller = this.notificationStackScrollLayoutController.mView;
        this.shadeHeaderController.header.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController.onInit.2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NotificationsQSContainerController.this.updateConstraints$1();
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        updateResources$1();
        this.launcherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.mInsetsChangedListener = this.delayedInsetSetter;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.NotificationsQSContainerController.onViewAttached.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QS) obj).setContainerController(NotificationsQSContainerController.this);
            }
        };
        notificationsQuickSettingsContainer.mQSFragmentAttachedListener = consumer;
        QS qs = notificationsQuickSettingsContainer.mQs;
        if (qs != null) {
            consumer.accept(qs);
        }
        ((NotificationsQuickSettingsContainer) this.mView).mConfigurationChangedListener = new C10362();
        this.fragmentService.getFragmentHostManager(this.mView).addTagListener(QS.TAG, (FragmentHostManager.FragmentListener) this.mView);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.launcherProxyService.removeCallback((LauncherProxyService.LauncherProxyListener) this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        notificationsQuickSettingsContainer.mInsetsChangedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0(0);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer2 = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer2.getClass();
        notificationsQuickSettingsContainer2.mQSFragmentAttachedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0(1);
        View view = this.mView;
        ((NotificationsQuickSettingsContainer) view).mConfigurationChangedListener = null;
        FragmentHostManager fragmentHostManager = this.fragmentService.getFragmentHostManager(view);
        FragmentHostManager.FragmentListener fragmentListener = (FragmentHostManager.FragmentListener) this.mView;
        ArrayList arrayList = (ArrayList) fragmentHostManager.mListeners.get(QS.TAG);
        if (arrayList != null && arrayList.remove(fragmentListener) && arrayList.size() == 0) {
            fragmentHostManager.mListeners.remove(QS.TAG);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerAnimating(boolean z) {
        if (this.isQSCustomizerAnimating != z) {
            this.isQSCustomizerAnimating = z;
            ((NotificationsQuickSettingsContainer) this.mView).invalidate();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z) {
        QSContainerController.DefaultImpls.setCustomizerShowing(this, z);
    }

    public final void updateConstraints$1() {
        ViewGroup viewGroup = (ViewGroup) this.mView;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getId() == -1) {
                childAt.setId(View.generateViewId());
            }
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) this.mView);
        constraintSet.connect(R.id.qs_frame, 7, 0, 7);
        constraintSet.setMargin(R.id.qs_frame, 6, this.panelMarginHorizontal);
        constraintSet.setMargin(R.id.qs_frame, 7, this.panelMarginHorizontal);
        Context context = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        Lazy lazy = this.resourcePickerLazy;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) lazy.get();
        ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
        constraintSet.setMargin(R.id.qs_frame, 3, shadeHeaderController.header.getMeasuredHeight());
        context.getClass();
        constraintSet.setMargin(R.id.qs_frame, 4, secQSPanelResourcePicker.getNavBarHeight(context));
        constraintSet.constrainWidth(R.id.qs_frame, secQSPanelResourcePicker.getPanelWidth(context));
        if (this.largeScreenShadeHeaderActive) {
            Lazy lazy2 = this.largeScreenHeaderHelperLazy;
            int topMargin = ((LargeScreenHeaderHelper) lazy2.get()).getTopMargin(((NotificationsQuickSettingsContainer) this.mView).getRootWindowInsets());
            LargeScreenHeaderHelper largeScreenHeaderHelper = (LargeScreenHeaderHelper) lazy2.get();
            int popOverBlankSpace = DeviceState.isShowingPopOverStatusBar(largeScreenHeaderHelper.context) ? largeScreenHeaderHelper.qsPanelResourcePicker.resourcePickHelper.getTargetPicker().getPopOverBlankSpace(largeScreenHeaderHelper.context) : 0;
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.largeScreenShadeHeaderHeight + topMargin + popOverBlankSpace);
            SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = (SamsungShadeHeaderControllerExt) shadeHeaderController.samsungExt.get();
            SamsungShadeHeaderControllerExt.SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = samsungShadeHeaderControllerExt.model;
            samsungShadeHeaderControllerExt.updateHeaderViewPaddings(samsungShadeHeaderControllerExtModel.leftPadding, topMargin, samsungShadeHeaderControllerExtModel.rightPadding, popOverBlankSpace);
            int i2 = this.largeScreenShadeHeaderHeight + topMargin + popOverBlankSpace;
            this.panelPopOverManager.blurViewTopMargin = i2;
            this.ambientState.mLargeScreenShadeHeaderHeight = i2;
        } else {
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.shadeHeaderHeight);
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = (SecQSPanelResourcePicker) lazy.get();
        Context context2 = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        context2.getClass();
        constraintSet.constrainWidth(R.id.notification_stack_scroller, secQSPanelResourcePicker2.getPanelWidth(context2));
        constraintSet.constrainWidth(R.id.split_shade_status_bar, secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getShadeHeaderWidth(context2));
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        constraintSet.applyTo(notificationsQuickSettingsContainer);
    }

    public final void updateResources$1() throws Resources.NotFoundException {
        getResources();
        ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
        this.largeScreenShadeHeaderActive = LargeScreenUtils.shouldUseLargeScreenShadeHeader(getResources());
        getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_bottom);
        this.largeScreenShadeHeaderHeight = ((LargeScreenHeaderHelper) this.largeScreenHeaderHelperLazy.get()).getLargeScreenHeaderHeight();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_header_height);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.new_qs_header_non_clickable_element_height) + (getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_min_height) * 2);
        if (dimensionPixelSize2 >= dimensionPixelSize) {
            dimensionPixelSize = dimensionPixelSize2;
        }
        this.shadeHeaderHeight = dimensionPixelSize;
        this.panelMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_horizontal);
        if (!this.largeScreenShadeHeaderActive) {
            getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_top);
        }
        updateConstraints$1();
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(this) { // from class: com.android.systemui.shade.NotificationsQSContainerController$updateResources$scrimMarginChanged$1
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return Integer.valueOf(((NotificationsQSContainerController) this.receiver).scrimShadeBottomMargin);
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                ((NotificationsQSContainerController) this.receiver).scrimShadeBottomMargin = ((Number) obj).intValue();
            }
        };
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        ((Number) mutablePropertyReference0Impl.get()).intValue();
        mutablePropertyReference0Impl.set(Integer.valueOf(dimensionPixelSize3));
        MutablePropertyReference0Impl mutablePropertyReference0Impl2 = new MutablePropertyReference0Impl(this) { // from class: com.android.systemui.shade.NotificationsQSContainerController$updateResources$footerOffsetChanged$1
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return Integer.valueOf(((NotificationsQSContainerController) this.receiver).footerActionsOffset);
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                ((NotificationsQSContainerController) this.receiver).footerActionsOffset = ((Number) obj).intValue();
            }
        };
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.qs_footer_actions_bottom_padding) + getResources().getDimensionPixelSize(R.dimen.qs_footer_action_inset);
        ((Number) mutablePropertyReference0Impl2.get()).intValue();
        mutablePropertyReference0Impl2.set(Integer.valueOf(dimensionPixelSize4));
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z, long j) {
        if (z != this.isQSCustomizing) {
            this.isQSCustomizing = z;
            ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
            shadeHeaderController.header.animate().setDuration(j).alpha(z ? 0.0f : 1.0f).setInterpolator(z ? Interpolators.ALPHA_OUT : Interpolators.ALPHA_IN).setListener(shadeHeaderController.new CustomizerAnimationListener(z)).start();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setDetailShowing(boolean z) {
    }
}
