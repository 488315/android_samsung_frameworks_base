package com.android.systemui.shade;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ShadeViewControllerEmptyImpl implements ShadeViewController, ShadeBackActionInteractor, ShadeLockscreenInteractor, PanelExpansionInteractor {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 legacyPanelExpansion;
    public final ShadeHeadsUpTrackerEmptyImpl shadeHeadsUpTracker = new ShadeHeadsUpTrackerEmptyImpl();
    public final ShadeFoldAnimatorEmptyImpl shadeFoldAnimator = new ShadeFoldAnimatorEmptyImpl();

    public ShadeViewControllerEmptyImpl() {
        Float valueOf = Float.valueOf(0.0f);
        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(valueOf);
        StateFlowKt.MutableStateFlow(valueOf);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean canBeCollapsed() {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public void closeQsIfPossible() {
        throw null;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean closeUserSwitcherIfOpen() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final int getBarState() {
        return 0;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final KeyguardBottomAreaViewController getKeyguardBottomAreaViewController() {
        return null;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
        return getNotificationStackScrollLayoutController();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final ShadeFoldAnimator getShadeFoldAnimator() {
        return this.shadeFoldAnimator;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final ShadeHeadsUpTracker getShadeHeadsUpTracker$1() {
        return this.shadeHeadsUpTracker;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean handleExternalInterceptTouch(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean handleExternalTouch(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isCollapsing() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final boolean isExpanded() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor, com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyExpanded() {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isInFaceWidgetContainer(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isInLockStarContainer(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isLaunchTransitionFinished() {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isLaunchTransitionRunning() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isPanelExpanded() {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isTouchableArea(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isTracking() {
        return false;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isViewEnabled() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z) {
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean shouldHideStatusBarIconsWhenExpanded() {
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z, boolean z2) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void blockExpansionForCurrentTouch() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void cancelInputFocusTransfer() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void dozeTimeTick() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void onBackPressed() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void onDismissCancelled() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViewGroupFade() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardStatusBarAlpha() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void startBouncerPreHideAnimation() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void startExpandLatencyTracking() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void startInputFocusTransfer() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void unregisterAODDoubleTouchListener() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateSystemUiStateFlags() {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateTouchableRegion() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void animateCollapseQs(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void finishInputFocusTransfer(float f) {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setAlphaChangeAnimationEndAction(BrightnessMirrorController$$ExternalSyntheticLambda0 brightnessMirrorController$$ExternalSyntheticLambda0) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardTransitionProgress(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setPulsing(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setQsScrimEnabled(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setAlpha(int i, boolean z) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void transitionToExpandedShade(long j, boolean z) {
    }
}
