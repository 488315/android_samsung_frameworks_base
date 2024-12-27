package com.android.keyguard;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface KeyguardViewController {
    void blockPanelExpansionFromCurrentTouch();

    void dismissAndCollapse();

    default Bundle getBouncerMessage() {
        return null;
    }

    default Bundle getIncorrectBouncerMessage() {
        return null;
    }

    default ViewGroup getLockIconContainer() {
        return null;
    }

    ViewRootImpl getViewRootImpl();

    void hide(long j, long j2);

    void hideAlternateBouncer(boolean z);

    default boolean interceptRestKey(KeyEvent keyEvent) {
        return false;
    }

    boolean isBouncerShowing();

    boolean isGoingToNotificationShade();

    default boolean isLaunchEditMode() {
        return false;
    }

    default boolean isPanelFullyCollapsed() {
        return false;
    }

    boolean isUnlockWithWallpaper();

    void keyguardGoingAway();

    void notifyKeyguardAuthenticated(boolean z);

    void onFinishedGoingToSleep();

    void onStartedGoingToSleep();

    void onStartedWakingUp();

    boolean primaryBouncerIsOrWillBeShowing();

    void registerCentralSurfaces(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view);

    void reset(boolean z);

    void setKeyguardGoingAwayState(boolean z);

    void setNeedsInput(boolean z);

    void setOccluded(boolean z, boolean z2);

    void shouldSubtleWindowAnimationsForUnlock();

    void show(Bundle bundle);

    void showPrimaryBouncer(boolean z);

    void startPreHideAnimation(Runnable runnable);

    default void folderOpenAndDismiss() {
    }

    default void onDismissCancelled() {
    }

    default void onWakeAndUnlock() {
    }

    default void prepareSafeUIBouncer() {
    }

    default void resetKeyguardDismissAction() {
    }

    default void setLaunchEditMode() {
    }

    default void updateDlsNaviBarVisibility() {
    }

    default void updateKeyguardUnlocking() {
    }

    default void updateLastKeyguardUnlocking() {
    }

    default void updateLockoutWarningMessage() {
    }

    default void updateNavigationBarVisibility() {
    }

    default void onCoverSwitchStateChanged(boolean z) {
    }

    default void onTrimMemory(int i) {
    }

    default void postSetOccluded(boolean z) {
    }

    default void registerLockIconContainer(ViewGroup viewGroup) {
    }

    default void requestUnlock(String str) {
    }

    default void setShowSwipeBouncer(boolean z) {
    }

    default void updateBouncerNavigationBar(boolean z) {
    }

    default void sendKeyguardViewState(boolean z, boolean z2, boolean z3) {
    }

    default void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, boolean z2, boolean z3) {
    }
}
