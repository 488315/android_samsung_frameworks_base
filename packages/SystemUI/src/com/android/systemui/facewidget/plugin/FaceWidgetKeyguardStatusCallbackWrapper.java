package com.android.systemui.facewidget.plugin;

import android.animation.Animator;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda31;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

public final class FaceWidgetKeyguardStatusCallbackWrapper implements PluginKeyguardStatusCallback {
    public NotificationPanelViewController.AnonymousClass10 mStatusCallback;

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final ArrayList getShortCutAreaViews() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            return NotificationPanelViewController.this.mKeyguardBottomAreaViewController.getShortCutAreaViews();
        }
        return null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final boolean isDozing() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            return NotificationPanelViewController.this.mDozing;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final boolean isKeyguardState() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            return NotificationPanelViewController.this.isKeyguardShowing$2();
        }
        return true;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void onMusicItemExpaned(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "onMusicItemExpaned() isExpanded = " + z);
            notificationPanelViewController.mMediaNowBarExpandState = z ? 1 : 0;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.mMusicItemExpanded = z;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            if (notificationStackScrollLayout != null) {
                notificationStackScrollLayout.animate().cancel();
                if (z) {
                    notificationStackScrollLayout.animate().setStartDelay(0L).setDuration(150L).alpha(0.0f).setInterpolator(InterpolatorUtils.SINE_OUT_60).start();
                } else {
                    notificationStackScrollLayout.animate().setStartDelay(150L).setDuration(300L).alpha(1.0f).setInterpolator(InterpolatorUtils.SINE_OUT_60).start();
                }
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setFullScreenMode(boolean z, long j) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            anonymousClass10.setFullScreenMode(z, j, null);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setMusicShown(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setMusicShown() shown = " + z);
            notificationPanelViewController.positionClockAndNotifications(false);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setNowBarExpandMode(boolean z, long j, Animator.AnimatorListener animatorListener) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setNowBarExpandMode() enabled = " + z + ", duration = " + j + ", listener = " + animatorListener + ", mKeyguardBottomArea = " + notificationPanelViewController.mKeyguardBottomArea);
            notificationPanelViewController.mUpdateMonitor.setNowBarExpandMode(z);
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = notificationPanelViewController.mSecAffordanceHelper;
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            keyguardSecAffordanceView.setNowBarExpandMode(z);
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            keyguardSecAffordanceView2.setNowBarExpandMode(z);
            notificationPanelViewController.mKeyguardIndicationController.setNowBarExpandMode(z);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setNowBarVisibility(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setNowBarVisibility() isVisible = " + z);
            notificationPanelViewController.mKeyguardBottomAreaViewController.setNowBarVisibility(z);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void showOneCardAnimation(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "showOneCardAnimation() show = " + z);
            KeyguardBottomAreaViewController keyguardBottomAreaViewController = notificationPanelViewController.mKeyguardBottomAreaViewController;
            if (keyguardBottomAreaViewController != null) {
                keyguardBottomAreaViewController.showShortcutAnimation(z);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void startActivity(PendingIntent pendingIntent) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            anonymousClass10.getClass();
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FACE_WIDGET);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            ActivityStarter activityStarter = notificationPanelViewController.mActivityStarter;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = notificationPanelViewController.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new NotificationPanelViewController$$ExternalSyntheticLambda31(statusBarKeyguardViewManager, 10));
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void userActivity() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setFullScreenMode(boolean z, long j, Animator.AnimatorListener animatorListener) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            anonymousClass10.setFullScreenMode(z, j, animatorListener);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void startActivity(Intent intent, boolean z, int i) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            anonymousClass10.getClass();
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FACE_WIDGET);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mIsLaunchTransitionFinished = true;
            notificationPanelViewController.mActivityStarter.startActivityDismissingKeyguard(intent, false, z, false, null, i, null, null);
        }
    }
}
