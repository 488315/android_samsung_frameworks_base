package com.android.systemui.shade;

import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda29;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.NotImplementedError;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeSurfaceImpl extends ShadeViewControllerEmptyImpl implements ShadeSurface {
    @Override // com.android.systemui.shade.ShadeViewControllerEmptyImpl, com.android.systemui.shade.ShadeViewController
    public final NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
        return getNotificationStackScrollLayoutController();
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final KeyguardTouchAnimator getTouchAnimator() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void cancelAnimation() {
    }

    @Override // com.android.systemui.shade.ShadeViewControllerEmptyImpl, com.android.systemui.shade.ShadeViewController
    public final void closeQsIfPossible() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onAffordanceLaunchEnded() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onScreenTurningOn() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onThemeChanged() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void resetAlpha() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void resetTranslation() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateExpansionAndVisibility() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateResources$1() {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void cancelPendingCollapse(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setBouncerShowing(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setImportantForAccessibility(int i) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setTouchAndAnimationDisabled(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setUserSetupComplete$1(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setWillPlayDelayedDozeAmountAnimation(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setDozing(boolean z, boolean z2) {
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void initDependencies(CentralSurfaces centralSurfaces, CentralSurfacesImpl$$ExternalSyntheticLambda29 centralSurfacesImpl$$ExternalSyntheticLambda29, HeadsUpManager headsUpManager) {
    }
}
