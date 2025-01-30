package com.android.systemui.statusbar.notification;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DynamicPrivacyController_Factory implements Provider {
    public final Provider keyguardStateControllerProvider;
    public final Provider notificationLockscreenUserManagerProvider;
    public final Provider stateControllerProvider;

    public DynamicPrivacyController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notificationLockscreenUserManagerProvider = provider;
        this.keyguardStateControllerProvider = provider2;
        this.stateControllerProvider = provider3;
    }

    public static DynamicPrivacyController newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController) {
        return new DynamicPrivacyController(notificationLockscreenUserManager, keyguardStateController, statusBarStateController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DynamicPrivacyController((NotificationLockscreenUserManager) this.notificationLockscreenUserManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (StatusBarStateController) this.stateControllerProvider.get());
    }
}
