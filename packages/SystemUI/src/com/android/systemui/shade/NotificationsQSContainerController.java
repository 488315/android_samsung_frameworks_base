package com.android.systemui.shade;

import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shade.ShadeHeaderController.CustomizerAnimationListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

public final class NotificationsQSContainerController extends ViewController implements QSContainerController {
    public final DelayableExecutor delayableExecutor;
    public final NotificationsQSContainerController$delayedInsetSetter$1 delayedInsetSetter;
    public int footerActionsOffset;
    public final FragmentService fragmentService;
    public boolean isQSCustomizerAnimating;
    public boolean isQSCustomizing;
    public boolean largeScreenShadeHeaderActive;
    public int largeScreenShadeHeaderHeight;
    public final NavigationModeController navigationModeController;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public final OverviewProxyService overviewProxyService;
    public int panelMarginHorizontal;
    public final Lazy resourcePickerLazy;
    public int scrimShadeBottomMargin;
    public final ShadeHeaderController shadeHeaderController;
    public int shadeHeaderHeight;
    public final ShadeInteractor shadeInteractor;
    public final SplitShadeStateController splitShadeStateController;
    public final NotificationsQSContainerController$taskbarVisibilityListener$1 taskbarVisibilityListener;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1] */
    public NotificationsQSContainerController(NotificationsQuickSettingsContainer notificationsQuickSettingsContainer, NavigationModeController navigationModeController, OverviewProxyService overviewProxyService, ShadeHeaderController shadeHeaderController, ShadeInteractor shadeInteractor, FragmentService fragmentService, DelayableExecutor delayableExecutor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SplitShadeStateController splitShadeStateController, Lazy lazy, Lazy lazy2) {
        super(notificationsQuickSettingsContainer);
        this.navigationModeController = navigationModeController;
        this.overviewProxyService = overviewProxyService;
        this.shadeHeaderController = shadeHeaderController;
        this.shadeInteractor = shadeInteractor;
        this.fragmentService = fragmentService;
        this.delayableExecutor = delayableExecutor;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.splitShadeStateController = splitShadeStateController;
        this.resourcePickerLazy = lazy2;
        this.taskbarVisibilityListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
                NotificationsQSContainerController.this.getClass();
            }
        };
        this.delayedInsetSetter = new NotificationsQSContainerController$delayedInsetSetter$1(this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        RepeatWhenAttachedKt.repeatWhenAttached(this.mView, EmptyCoroutineContext.INSTANCE, new NotificationsQSContainerController$onInit$1(this, null));
        this.navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onInit$currentMode$1
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
                NotificationsQSContainerController.this.getClass();
            }
        });
        boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        ((NotificationsQuickSettingsContainer) this.mView).mStackScroller = this.notificationStackScrollLayoutController.mView;
        this.shadeHeaderController.header.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onInit$2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NotificationsQSContainerController.this.updateConstraints$1();
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        updateResources$1();
        this.overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.mInsetsChangedListener = this.delayedInsetSetter;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onViewAttached$1
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
        ((NotificationsQuickSettingsContainer) this.mView).mConfigurationChangedListener = new Consumer() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onViewAttached$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationsQSContainerController.this.updateResources$1();
            }
        };
        this.fragmentService.getFragmentHostManager(this.mView).addTagListener(QS.TAG, (FragmentHostManager.FragmentListener) this.mView);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ArrayList) this.overviewProxyService.mConnectionCallbacks).remove(this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        notificationsQuickSettingsContainer.mInsetsChangedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(0);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer2 = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer2.getClass();
        notificationsQuickSettingsContainer2.mQSFragmentAttachedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(1);
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
        DisplayCutout displayCutout;
        ViewGroup viewGroup = (ViewGroup) this.mView;
        int childCount = viewGroup.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt.getId() == -1) {
                childAt.setId(View.generateViewId());
            }
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) this.mView);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.status_view_margin_horizontal);
        constraintSet.setMargin(R.id.keyguard_status_view, 6, dimensionPixelSize);
        constraintSet.setMargin(R.id.keyguard_status_view, 7, dimensionPixelSize);
        constraintSet.connect(R.id.qs_frame, 7, 0, 7);
        constraintSet.setMargin(R.id.qs_frame, 6, this.panelMarginHorizontal);
        constraintSet.setMargin(R.id.qs_frame, 7, this.panelMarginHorizontal);
        ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
        constraintSet.setMargin(R.id.qs_frame, 3, shadeHeaderController.header.getHeight());
        Lazy lazy = this.resourcePickerLazy;
        constraintSet.setMargin(R.id.qs_frame, 4, ((SecQSPanelResourcePicker) lazy.get()).getNavBarHeight(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        constraintSet.constrainWidth(R.id.qs_frame, ((SecQSPanelResourcePicker) lazy.get()).getPanelWidth(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        Flags.migrateClocksToBlueprint();
        constraintSet.connect(R.id.notification_stack_scroller, 6, 0, 6);
        constraintSet.setMargin(R.id.notification_stack_scroller, 6, this.panelMarginHorizontal);
        constraintSet.setMargin(R.id.notification_stack_scroller, 7, this.panelMarginHorizontal);
        constraintSet.setMargin(R.id.notification_stack_scroller, 3, 0);
        constraintSet.setMargin(R.id.notification_stack_scroller, 4, ((SecQSPanelResourcePicker) lazy.get()).getNavBarHeight(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        constraintSet.constrainWidth(R.id.notification_stack_scroller, ((SecQSPanelResourcePicker) lazy.get()).getPanelWidth(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        if (this.largeScreenShadeHeaderActive) {
            WindowInsets rootWindowInsets = ((NotificationsQuickSettingsContainer) this.mView).getRootWindowInsets();
            if (rootWindowInsets != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                i = displayCutout.getBoundingRectTop().bottom;
            }
            if (i == 0) {
                i = getResources().getDimensionPixelSize(R.dimen.shade_header_no_cutout_top_margin);
            }
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.largeScreenShadeHeaderHeight + i);
            MotionLayout motionLayout = shadeHeaderController.header;
            motionLayout.setPaddingRelative(motionLayout.getPaddingLeft(), i, motionLayout.getPaddingRight(), motionLayout.getPaddingBottom());
        } else {
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.shadeHeaderHeight);
        }
        constraintSet.constrainWidth(R.id.notification_stack_scroller, ((SecQSPanelResourcePicker) lazy.get()).getPanelWidth(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        constraintSet.constrainWidth(R.id.split_shade_status_bar, ((SecQSPanelResourcePicker) lazy.get()).getPanelWidth(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        constraintSet.applyTo(notificationsQuickSettingsContainer);
    }

    public final void updateResources$1() {
        getResources();
        ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
        this.largeScreenShadeHeaderActive = LargeScreenUtils.shouldUseLargeScreenShadeHeader(getResources());
        getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_bottom);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) this.resourcePickerLazy.get();
        this.largeScreenShadeHeaderHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getShadeHeaderHeight(getContext());
        this.shadeHeaderHeight = getResources().getDimensionPixelSize(R.dimen.qs_header_height);
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
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        ((Number) mutablePropertyReference0Impl.get()).intValue();
        mutablePropertyReference0Impl.set(Integer.valueOf(dimensionPixelSize));
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
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_footer_actions_bottom_padding) + getResources().getDimensionPixelSize(R.dimen.qs_footer_action_inset);
        ((Number) mutablePropertyReference0Impl2.get()).intValue();
        mutablePropertyReference0Impl2.set(Integer.valueOf(dimensionPixelSize2));
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
