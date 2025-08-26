package com.android.systemui.statusbar.notification;

import android.util.ArraySet;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class DynamicPrivacyController implements KeyguardStateController.Callback {
    public boolean mCacheInvalid;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mLastDynamicUnlocked;
    public final ArraySet mListeners = new ArraySet();
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final StatusBarStateController mStateController;

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
            return ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId);
        }
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).userAllowsNotificationsInPublic(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId);
    }

    public final boolean isDynamicallyUnlocked() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        return (keyguardStateControllerImpl.mCanDismissLockScreen || keyguardStateControllerImpl.mKeyguardGoingAway || keyguardStateControllerImpl.mKeyguardFadingAway) && isDynamicPrivacyEnabled();
    }

    public final boolean isInLockedDownShade() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (!keyguardStateControllerImpl.mShowing || !keyguardStateControllerImpl.mSecure) {
            return false;
        }
        int state = this.mStateController.getState();
        if (state == 0 || state == 2) {
            return ((SecPanelSplitHelper.isEnabled() && (state == 2 || keyguardStateControllerImpl.mOccluded)) || !isDynamicPrivacyEnabled() || isDynamicallyUnlocked()) ? false : true;
        }
        return false;
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
        boolean zIsDynamicallyUnlocked = isDynamicallyUnlocked();
        if (zIsDynamicallyUnlocked != this.mLastDynamicUnlocked || this.mCacheInvalid) {
            this.mLastDynamicUnlocked = zIsDynamicallyUnlocked;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((Listener) it.next()).onDynamicPrivacyChanged();
            }
        }
        this.mCacheInvalid = false;
    }
}
