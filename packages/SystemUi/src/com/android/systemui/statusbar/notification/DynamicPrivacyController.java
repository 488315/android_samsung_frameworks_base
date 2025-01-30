package com.android.systemui.statusbar.notification;

import android.util.ArraySet;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DynamicPrivacyController implements KeyguardStateController.Callback {
    public boolean mCacheInvalid;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mLastDynamicUnlocked;
    public final ArraySet mListeners = new ArraySet();
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final StatusBarStateController mStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
        void onDynamicPrivacyChanged();
    }

    public DynamicPrivacyController(NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController) {
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this);
        this.mLastDynamicUnlocked = isDynamicallyUnlocked();
    }

    public boolean isDynamicPrivacyEnabled() {
        boolean z = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        if (z) {
            return ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId);
        }
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).userAllowsNotificationsInPublic(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId);
    }

    public final boolean isDynamicallyUnlocked() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return (((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen || ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway || ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway) && isDynamicPrivacyEnabled();
    }

    public final boolean isInLockedDownShade() {
        int state;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        return keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mSecure && ((state = this.mStateController.getState()) == 0 || state == 2) && isDynamicPrivacyEnabled() && !isDynamicallyUnlocked();
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardFadingAwayChanged() {
        onUnlockedChanged();
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onUnlockedChanged() {
        if (!isDynamicPrivacyEnabled()) {
            this.mCacheInvalid = true;
            return;
        }
        boolean isDynamicallyUnlocked = isDynamicallyUnlocked();
        if (isDynamicallyUnlocked != this.mLastDynamicUnlocked || this.mCacheInvalid) {
            this.mLastDynamicUnlocked = isDynamicallyUnlocked;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((Listener) it.next()).onDynamicPrivacyChanged();
            }
        }
        this.mCacheInvalid = false;
    }
}
