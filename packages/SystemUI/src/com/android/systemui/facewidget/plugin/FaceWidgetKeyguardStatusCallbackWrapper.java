package com.android.systemui.facewidget.plugin;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda18;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda7;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public class FaceWidgetKeyguardStatusCallbackWrapper implements PluginKeyguardStatusCallback {
    public NotificationPanelViewController.AnonymousClass10 mStatusCallback;

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final ArrayList getShortCutAreaViews() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 == null) {
            return null;
        }
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = NotificationPanelViewController.this.mKeyguardSecBottomAreaViewController;
        keyguardSecBottomAreaViewController.getClass();
        ArrayList arrayList = new ArrayList();
        arrayList.add((View) keyguardSecBottomAreaViewController.leftShortcutEffectview$delegate.getValue());
        arrayList.add((View) keyguardSecBottomAreaViewController.rightShortcutEffectview$delegate.getValue());
        return arrayList;
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
            return NotificationPanelViewController.this.isKeyguardShowing$1();
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
            notificationPanelViewController.mNotificationStackScrollLayoutController.getClass();
            PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) notificationPanelViewController.mPluginLockStarManagerLazy.get();
            if (pluginLockStarManager != null && pluginLockStarManager.mPluginLockStar != null) {
                AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onMediaNowBarExpandStateChanged: isExpanded= ", "LStar|PluginLockStarManager", z);
                try {
                    pluginLockStarManager.mPluginLockStar.onMediaNowBarExpandStateChanged(z);
                } catch (Throwable th) {
                    Log.e("LStar|PluginLockStarManager", "onMediaNowBarExpandStateChanged: error = " + th.getMessage());
                }
            }
            ValueAnimator valueAnimator = notificationPanelViewController.mStackScrollerAlphaAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator duration = ValueAnimator.ofFloat(z ? 1.0f : 0.0f, z ? 0.0f : 1.0f).setDuration(z ? 150L : 300L);
            notificationPanelViewController.mStackScrollerAlphaAnimator = duration;
            duration.setStartDelay(z ? 0L : 150L);
            notificationPanelViewController.mStackScrollerAlphaAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            notificationPanelViewController.mStackScrollerAlphaAnimator.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda7(notificationPanelViewController, 2));
            notificationPanelViewController.mStackScrollerAlphaAnimator.start();
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
        PluginNotiCenter pluginNotiCenter;
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setNowBarExpandMode() enabled = " + z + ", duration = " + j + ", listener = " + animatorListener + ", mKeyguardSecBottomArea = " + notificationPanelViewController.mKeyguardSecBottomArea);
            notificationPanelViewController.mUpdateMonitor.setNowBarExpandMode(z);
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = notificationPanelViewController.mSecAffordanceHelper;
            if (keyguardSecAffordanceHelper != null) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
                keyguardSecAffordanceView.getClass();
                keyguardSecAffordanceView.setNowBarExpandMode(z);
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
                keyguardSecAffordanceView2.getClass();
                keyguardSecAffordanceView2.setNowBarExpandMode(z);
            }
            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = notificationPanelViewController.mKeyguardSecBottomAreaViewController;
            if (keyguardSecBottomAreaViewController != null) {
                keyguardSecBottomAreaViewController.isNowBarExpanded = z;
            }
            notificationPanelViewController.mKeyguardIndicationController.setNowBarExpandMode(z);
            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor = notificationPanelViewController.mSecQuickSettingsAffordanceInteractor;
            if (secQuickSettingsAffordanceInteractor != null) {
                secQuickSettingsAffordanceInteractor.hideEffectIfNeeded("setNowBarExpandMode", z);
            }
            NotiCenterPlugin.INSTANCE.getClass();
            if (!NotiCenterPlugin.isNotiCenterPluginConnected() || (pluginNotiCenter = NotiCenterPlugin.plugin) == null) {
                return;
            }
            pluginNotiCenter.setNowBarExpandMode(z);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setNowBarVisibility(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setNowBarVisibility() isVisible = " + z);
            notificationPanelViewController.mKeyguardSecBottomAreaViewController.setNowBarVisibility(z);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void showOneCardAnimation(boolean z) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "showOneCardAnimation() show = " + z);
            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = notificationPanelViewController.mKeyguardSecBottomAreaViewController;
            if (keyguardSecBottomAreaViewController != null) {
                keyguardSecBottomAreaViewController.showShortcutAnimation(0L, z);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void startActivity(PendingIntent pendingIntent) {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FACE_WIDGET);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            ActivityStarter activityStarter = notificationPanelViewController.mActivityStarter;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = notificationPanelViewController.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new NotificationPanelViewController$$ExternalSyntheticLambda18(statusBarKeyguardViewManager, 15));
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void userActivity() {
        NotificationPanelViewController.AnonymousClass10 anonymousClass10 = this.mStatusCallback;
        if (anonymousClass10 != null) {
            NotificationPanelViewController.this.mCentralSurfaces.userActivity();
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
