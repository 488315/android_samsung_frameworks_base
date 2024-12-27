package com.android.systemui.statusbar.phone;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarTransitionAnimatorController implements ActivityTransitionAnimator.Controller {
    public static final long ANIMATION_DELAY_ICON_FADE_IN;
    public final CommandQueue commandQueue;
    public final ActivityTransitionAnimator.Controller delegate;
    public final int displayId;
    public boolean hideIconsDuringLaunchAnimation;
    public final boolean isLaunchForActivity;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public final ShadeAnimationInteractor shadeAnimationInteractor;
    public final ShadeController shadeController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        ANIMATION_DELAY_ICON_FADE_IN = ((ActivityTransitionAnimator.TIMINGS.totalDuration - 320) - 50) - 48;
    }

    public StatusBarTransitionAnimatorController(ActivityTransitionAnimator.Controller controller, ShadeAnimationInteractor shadeAnimationInteractor, ShadeController shadeController, NotificationShadeWindowController notificationShadeWindowController, CommandQueue commandQueue, int i, boolean z) {
        this.delegate = controller;
        this.shadeAnimationInteractor = shadeAnimationInteractor;
        this.shadeController = shadeController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.commandQueue = commandQueue;
        this.displayId = i;
        this.isLaunchForActivity = z;
        this.hideIconsDuringLaunchAnimation = true;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final View getOpeningWindowSyncView() {
        return ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).mWindowRootView;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return this.delegate.getTransitionContainer();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
        return this.delegate.getTransitionCookie();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isBelowAnimatingWindow() {
        return this.delegate.isBelowAnimatingWindow();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.delegate.isLaunching();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
        if (z) {
            this.shadeAnimationInteractor.setIsLaunchingActivity(true);
        } else {
            this.shadeController.collapseOnMainThread();
        }
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled(Boolean bool) {
        this.delegate.onTransitionAnimationCancelled(null);
        this.shadeAnimationInteractor.setIsLaunchingActivity(false);
        BaseShadeControllerImpl baseShadeControllerImpl = (BaseShadeControllerImpl) this.shadeController;
        NotificationPresenter notificationPresenter = baseShadeControllerImpl.notifPresenter;
        if (notificationPresenter == null) {
            notificationPresenter = null;
        }
        if (((StatusBarNotificationPresenter) notificationPresenter).mPanelExpansionInteractor.isFullyCollapsed()) {
            NotificationPresenter notificationPresenter2 = baseShadeControllerImpl.notifPresenter;
            if (!((StatusBarNotificationPresenter) (notificationPresenter2 != null ? notificationPresenter2 : null)).isCollapsing() && this.isLaunchForActivity) {
                baseShadeControllerImpl.onClosingFinished$1();
                return;
            }
        }
        baseShadeControllerImpl.collapseShade(true);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        this.delegate.onTransitionAnimationEnd(z);
        this.shadeAnimationInteractor.setIsLaunchingActivity(false);
        BaseShadeControllerImpl baseShadeControllerImpl = (BaseShadeControllerImpl) this.shadeController;
        NotificationPresenter notificationPresenter = baseShadeControllerImpl.notifPresenter;
        if (notificationPresenter == null) {
            notificationPresenter = null;
        }
        if (!((StatusBarNotificationPresenter) notificationPresenter).isCollapsing()) {
            baseShadeControllerImpl.onClosingFinished$1();
        }
        if (z) {
            baseShadeControllerImpl.instantCollapseShade();
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        this.delegate.onTransitionAnimationProgress(state, f, f2);
        TransitionAnimator.Companion companion = TransitionAnimator.Companion;
        TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
        companion.getClass();
        boolean z = TransitionAnimator.Companion.getProgress(timings, f2, ANIMATION_DELAY_ICON_FADE_IN, 100L) == 0.0f;
        if (z != this.hideIconsDuringLaunchAnimation) {
            this.hideIconsDuringLaunchAnimation = z;
            if (z) {
                return;
            }
            this.commandQueue.recomputeDisableFlags(this.displayId, true);
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        this.delegate.onTransitionAnimationStart(z);
        this.shadeAnimationInteractor.setIsLaunchingActivity(true);
        if (z) {
            return;
        }
        this.shadeController.collapseWithDuration((int) ActivityTransitionAnimator.TIMINGS.totalDuration);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.delegate.setTransitionContainer(viewGroup);
    }

    public /* synthetic */ StatusBarTransitionAnimatorController(ActivityTransitionAnimator.Controller controller, ShadeAnimationInteractor shadeAnimationInteractor, ShadeController shadeController, NotificationShadeWindowController notificationShadeWindowController, CommandQueue commandQueue, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(controller, shadeAnimationInteractor, shadeController, notificationShadeWindowController, commandQueue, i, (i2 & 64) != 0 ? true : z);
    }
}
