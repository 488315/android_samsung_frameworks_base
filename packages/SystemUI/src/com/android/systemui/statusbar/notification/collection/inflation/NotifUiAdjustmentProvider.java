package com.android.systemui.statusbar.notification.collection.inflation;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifUiAdjustmentProvider {
    public final AppLockNotificationController appLockNotificationController;
    public final ListenerSet dirtyListeners = new ListenerSet();
    public final HighPriorityProvider highPriorityProvider;
    public boolean isSnoozeSettingsEnabled;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final NotifUiAdjustmentProvider$notifStateChangedListener$1 notifStateChangedListener;
    public final NotifUiAdjustmentProvider$onAppLockPackagesChangedListener$1 onAppLockPackagesChangedListener;
    public final NotifUiAdjustmentProvider$onSensitiveStateChangedListener$1 onSensitiveStateChangedListener;
    public final SecureSettings secureSettings;
    public final SensitiveNotificationProtectionController sensitiveNotifProtectionController;
    public final NotifUiAdjustmentProvider$settingsObserver$1 settingsObserver;
    public final NotifUiAdjustmentProvider$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$onAppLockPackagesChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$onSensitiveStateChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$settingsObserver$1] */
    public NotifUiAdjustmentProvider(final Handler handler, SecureSettings secureSettings, NotificationLockscreenUserManager notificationLockscreenUserManager, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, SectionStyleProvider sectionStyleProvider, UserTracker userTracker, GroupMembershipManager groupMembershipManager, HighPriorityProvider highPriorityProvider, AppLockNotificationController appLockNotificationController) {
        this.secureSettings = secureSettings;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.sensitiveNotifProtectionController = sensitiveNotificationProtectionController;
        this.highPriorityProvider = highPriorityProvider;
        this.appLockNotificationController = appLockNotificationController;
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                NotifUiAdjustmentProvider.this.updateSnoozeEnabled();
            }
        };
        this.userTrackerCallback = r2;
        ((UserTrackerImpl) userTracker).addCallback(r2, new HandlerExecutor(handler));
        this.notifStateChangedListener = new NotifUiAdjustmentProvider$notifStateChangedListener$1(this);
        this.onSensitiveStateChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$onSensitiveStateChangedListener$1
            @Override // java.lang.Runnable
            public final void run() {
                Iterator<E> it = NotifUiAdjustmentProvider.this.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                NotifUiAdjustmentProvider.this.updateSnoozeEnabled();
                Iterator<E> it = NotifUiAdjustmentProvider.this.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
        this.onAppLockPackagesChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$onAppLockPackagesChangedListener$1
            @Override // java.lang.Runnable
            public final void run() {
                Iterator<E> it = NotifUiAdjustmentProvider.this.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
    }

    public final void updateSnoozeEnabled() {
        this.isSnoozeSettingsEnabled = this.secureSettings.getIntForUser(SettingsHelper.INDEX_SNOOZE_SETTING, 0, -2) == 1;
    }
}
