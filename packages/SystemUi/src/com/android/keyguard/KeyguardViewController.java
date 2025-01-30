package com.android.keyguard;

import android.os.Bundle;
import android.view.ViewRootImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardViewController extends KeyguardSecViewController {
    void blockPanelExpansionFromCurrentTouch();

    void dismissAndCollapse();

    ViewRootImpl getViewRootImpl();

    void hide(long j, long j2);

    void hideAlternateBouncer(boolean z);

    boolean isBouncerShowing();

    boolean isGoingToNotificationShade();

    boolean isUnlockWithWallpaper();

    void keyguardGoingAway();

    void notifyKeyguardAuthenticated(boolean z);

    void onCancelClicked();

    void onFinishedGoingToSleep();

    void onStartedGoingToSleep();

    void onStartedWakingUp();

    boolean primaryBouncerIsOrWillBeShowing();

    void reset(boolean z);

    void setKeyguardGoingAwayState(boolean z);

    void setNeedsInput(boolean z);

    void setOccluded(boolean z, boolean z2);

    void shouldSubtleWindowAnimationsForUnlock();

    void show(Bundle bundle);

    void showPrimaryBouncer(boolean z);

    void startPreHideAnimation(Runnable runnable);
}
