package com.android.systemui.statusbar.iconsOnly;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.util.SafeUIState;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationIconTransitionController implements StatusBarStateController.StateListener, WakefulnessLifecycle.Observer {
    public final AnimationCreator mAnimationCreator;
    public AnimatorSet mAppearingIconAnimSet;
    public boolean mChildAnimatable;
    public float mDetailedCardScale;
    public ValueAnimator mDisappearingDetailScaleAnim;
    public View mIconContainer;
    public boolean mIsNeedDelay;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public boolean mNeedAnimForRemoval = true;
    public NotificationStackScrollLayout mNotificationStackScrollLayout;
    public boolean misTransformAnimating;

    public NotificationIconTransitionController(Context context, LockscreenNotificationManager lockscreenNotificationManager, StatusBarStateController statusBarStateController, PluginLockData pluginLockData, KeyguardBypassController keyguardBypassController) {
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        statusBarStateController.addCallback(this);
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).addObserver(this);
        }
        this.mAnimationCreator = new AnimationCreator(this);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedGoingToSleep() {
        resetTransformAnimation();
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        if (lockscreenNotificationManager.mIsDetail) {
            lockscreenNotificationManager.mIsDetail = false;
            lockscreenNotificationManager.updateNotificationType();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (i != 1) {
            resetTransformAnimation();
        }
    }

    public final void resetTransformAnimation() {
        Optional.ofNullable(null).ifPresent(new NotificationIconTransitionController$$ExternalSyntheticLambda0(0));
        Optional.ofNullable(this.mDisappearingDetailScaleAnim).ifPresent(new NotificationIconTransitionController$$ExternalSyntheticLambda0(1));
        Optional.ofNullable(null).ifPresent(new NotificationIconTransitionController$$ExternalSyntheticLambda0(2));
        Optional.ofNullable(this.mAppearingIconAnimSet).ifPresent(new NotificationIconTransitionController$$ExternalSyntheticLambda0(3));
        this.misTransformAnimating = false;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayout;
        if (notificationStackScrollLayout != null) {
            notificationStackScrollLayout.setScaleX(1.0f);
            this.mNotificationStackScrollLayout.setScaleY(1.0f);
        }
        View view = this.mIconContainer;
        if (view != null) {
            view.setScaleX(1.0f);
            this.mIconContainer.setScaleY(1.0f);
        }
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        synchronized (lockscreenNotificationManager.mLock) {
            lockscreenNotificationManager.mHandler.removeMessages(100);
            lockscreenNotificationManager.mHandler.obtainMessage(100, lockscreenNotificationManager.mCurrentNotificationType, 0, null).sendToTarget();
        }
    }
}
