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
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifUiAdjustmentProvider {
    public final ListenerSet dirtyListeners = new ListenerSet();
    public final HighPriorityProvider highPriorityProvider;
    public boolean isSnoozeSettingsEnabled;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final NotifUiAdjustmentProvider$notifStateChangedListener$1 notifStateChangedListener;
    public final SecureSettings secureSettings;
    public final NotifUiAdjustmentProvider$settingsObserver$1 settingsObserver;
    public final NotifUiAdjustmentProvider$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$notifStateChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$settingsObserver$1] */
    public NotifUiAdjustmentProvider(final Handler handler, SecureSettings secureSettings, NotificationLockscreenUserManager notificationLockscreenUserManager, SectionStyleProvider sectionStyleProvider, UserTracker userTracker, HighPriorityProvider highPriorityProvider) {
        this.secureSettings = secureSettings;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.highPriorityProvider = highPriorityProvider;
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                NotifUiAdjustmentProvider.this.updateSnoozeEnabled();
            }
        };
        this.userTrackerCallback = r2;
        ((UserTrackerImpl) userTracker).addCallback(r2, new HandlerExecutor(handler));
        this.notifStateChangedListener = new NotificationLockscreenUserManager.NotificationStateChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$notifStateChangedListener$1
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.NotificationStateChangedListener
            public final void onNotificationStateChanged() {
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
    }

    public final void updateSnoozeEnabled() {
        this.isSnoozeSettingsEnabled = this.secureSettings.getIntForUser(0, -2, "show_notification_snooze") == 1;
    }
}
