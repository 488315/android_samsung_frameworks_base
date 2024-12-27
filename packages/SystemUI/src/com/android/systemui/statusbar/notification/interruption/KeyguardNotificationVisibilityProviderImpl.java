package com.android.systemui.statusbar.notification.interruption;

import android.app.INotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.IndentingPrintWriter;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

public final class KeyguardNotificationVisibilityProviderImpl implements CoreStartable, KeyguardNotificationVisibilityProvider {
    public final GlobalSettings globalSettings;
    public final Handler handler;
    public boolean hideSilentNotificationsOnLockscreen;
    public final HighPriorityProvider highPriorityProvider;
    public final INotificationManager iNotificationManager;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final SecureSettings secureSettings;
    public final Uri showSilentNotifsUri;
    public final SysuiStatusBarStateController statusBarStateController;
    public final UserTracker userTracker;
    public final ListenerSet onStateChangedListeners = new ListenerSet();
    public final KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl = KeyguardNotificationVisibilityProviderImpl.this;
            keyguardNotificationVisibilityProviderImpl.readShowSilentNotificationSetting();
            if (keyguardNotificationVisibilityProviderImpl.isLockedOrLocking()) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(keyguardNotificationVisibilityProviderImpl, "onUserSwitched");
            }
            keyguardNotificationVisibilityProviderImpl.readShowSilentNotificationSetting();
        }
    };

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1] */
    public KeyguardNotificationVisibilityProviderImpl(Handler handler, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HighPriorityProvider highPriorityProvider, SysuiStatusBarStateController sysuiStatusBarStateController, UserTracker userTracker, SecureSettings secureSettings, GlobalSettings globalSettings, FeatureFlagsClassic featureFlagsClassic, INotificationManager iNotificationManager) {
        this.handler = handler;
        this.keyguardStateController = keyguardStateController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.highPriorityProvider = highPriorityProvider;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.userTracker = userTracker;
        this.secureSettings = secureSettings;
        this.globalSettings = globalSettings;
        this.iNotificationManager = iNotificationManager;
        this.showSilentNotifsUri = secureSettings.getUriFor(SettingsHelper.INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN);
    }

    public static final void access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl, String str) {
        Iterator<E> it = keyguardNotificationVisibilityProviderImpl.onStateChangedListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(str);
        }
    }

    public static final boolean userSettingsDisallowNotification$disallowForUser(KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl, NotificationEntry notificationEntry, int i) {
        ExpandableNotificationRow expandableNotificationRow;
        if (keyguardNotificationVisibilityProviderImpl.keyguardUpdateMonitor.isUserInLockdown(i)) {
            return true;
        }
        NotificationLockscreenUserManager notificationLockscreenUserManager = keyguardNotificationVisibilityProviderImpl.lockscreenUserManager;
        if (((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isLockscreenPublicMode(i) && (!NotiRune.NOTI_INSIGNIFICANT || (expandableNotificationRow = notificationEntry.row) == null || !expandableNotificationRow.isInsignificantSummary())) {
            if (notificationEntry.mRanking.getLockscreenVisibilityOverride() == -1) {
                return true;
            }
            if ((notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getLockscreenVisibility() == -1) || !((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).userAllowsNotificationsInPublic(i)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.println(asIndenting, "isLockedOrLocking", Boolean.valueOf(isLockedOrLocking()));
        asIndenting.increaseIndent();
        try {
            DumpUtilsKt.println(asIndenting, "keyguardStateController.isShowing", Boolean.valueOf(((KeyguardStateControllerImpl) this.keyguardStateController).mShowing));
            DumpUtilsKt.println(asIndenting, "statusBarStateController.currentOrUpcomingState", Integer.valueOf(((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState));
            asIndenting.decreaseIndent();
            DumpUtilsKt.println(asIndenting, "hideSilentNotificationsOnLockscreen", Boolean.valueOf(this.hideSilentNotificationsOnLockscreen));
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean isLockedOrLocking() {
        return ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || ((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState == 1;
    }

    public final void readShowSilentNotificationSetting() {
        this.hideSilentNotificationsOnLockscreen = !this.secureSettings.getBoolForUser(SettingsHelper.INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN, true, -2);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldHideIfEntrySilent(com.android.systemui.statusbar.notification.collection.ListEntry r5) {
        /*
            r4 = this;
            boolean r0 = r4.hideSilentNotificationsOnLockscreen
            r1 = 1
            if (r0 == 0) goto L2f
            if (r5 != 0) goto L9
        L7:
            r0 = r1
            goto L2c
        L9:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r5.getRepresentativeEntry()
            if (r0 != 0) goto L10
            goto L7
        L10:
            android.app.INotificationManager r2 = r4.iNotificationManager     // Catch: android.os.RemoteException -> L23
            android.service.notification.StatusBarNotification r3 = r0.mSbn     // Catch: android.os.RemoteException -> L23
            java.lang.String r3 = r3.getPackageName()     // Catch: android.os.RemoteException -> L23
            android.service.notification.StatusBarNotification r0 = r0.mSbn     // Catch: android.os.RemoteException -> L23
            int r0 = r0.getUid()     // Catch: android.os.RemoteException -> L23
            boolean r0 = r2.getNotificationAlertsEnabledForPackage(r3, r0)     // Catch: android.os.RemoteException -> L23
            goto L2c
        L23:
            r0 = move-exception
            java.lang.String r2 = "KeyguardNotificationVisibilityProviderImpl"
            java.lang.String r3 = "Unable to get AlertsEnabledForPackage"
            android.util.Log.e(r2, r3, r0)
            goto L7
        L2c:
            if (r0 != 0) goto L2f
            goto L5d
        L2f:
            com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider r0 = r4.highPriorityProvider
            r2 = 0
            boolean r0 = r0.isHighPriority(r5, r2)
            if (r0 == 0) goto L3a
        L38:
            r1 = r2
            goto L5d
        L3a:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r5.getRepresentativeEntry()
            if (r0 == 0) goto L4a
            android.service.notification.NotificationListenerService$Ranking r0 = r0.mRanking
            boolean r0 = r0.isAmbient()
            if (r0 != r1) goto L4a
            r0 = r1
            goto L4b
        L4a:
            r0 = r2
        L4b:
            if (r0 == 0) goto L4e
            goto L5d
        L4e:
            boolean r0 = r4.hideSilentNotificationsOnLockscreen
            if (r0 == 0) goto L53
            goto L5d
        L53:
            com.android.systemui.statusbar.notification.collection.GroupEntry r5 = r5.getParent()
            if (r5 == 0) goto L38
            r4.shouldHideIfEntrySilent(r5)
            goto L38
        L5d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl.shouldHideIfEntrySilent(com.android.systemui.statusbar.notification.collection.ListEntry):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0062, code lost:
    
        if ((r0.getNotification().visibility == -1 && (!"com.nttdocomo.android.atf".equals(r0.getPackageName()) || ((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r6.keyguardStateController).mSecure)) != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldHideNotification(com.android.systemui.statusbar.notification.collection.NotificationEntry r7) {
        /*
            r6 = this;
            boolean r0 = r6.isLockedOrLocking()
            r1 = 0
            if (r0 != 0) goto L9
            goto L6c
        L9:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r6.lockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            boolean r2 = r0.mShowLockscreenNotifications
            r3 = 1
            if (r2 != 0) goto L14
        L12:
            r1 = r3
            goto L6c
        L14:
            int r0 = r0.mCurrentUserId
            android.service.notification.StatusBarNotification r2 = r7.mSbn
            android.os.UserHandle r2 = r2.getUser()
            int r2 = r2.getIdentifier()
            boolean r4 = userSettingsDisallowNotification$disallowForUser(r6, r7, r0)
            r5 = -1
            if (r4 == 0) goto L29
            r0 = r3
            goto L34
        L29:
            if (r2 != r5) goto L2d
        L2b:
            r0 = r1
            goto L34
        L2d:
            if (r2 != r0) goto L30
            goto L2b
        L30:
            boolean r0 = userSettingsDisallowNotification$disallowForUser(r6, r7, r2)
        L34:
            if (r0 == 0) goto L37
            goto L12
        L37:
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            android.app.Notification r0 = r0.getNotification()
            int r0 = r0.visibility
            if (r0 != r5) goto L65
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            android.app.Notification r2 = r0.getNotification()
            int r2 = r2.visibility
            if (r2 != r5) goto L5f
            java.lang.String r0 = r0.getPackageName()
            java.lang.String r2 = "com.nttdocomo.android.atf"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L61
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r6.keyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0
            boolean r0 = r0.mSecure
            if (r0 != 0) goto L61
        L5f:
            r0 = r1
            goto L62
        L61:
            r0 = r3
        L62:
            if (r0 == 0) goto L65
            goto L12
        L65:
            boolean r6 = r6.shouldHideIfEntrySilent(r7)
            if (r6 == 0) goto L6c
            goto L12
        L6c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl.shouldHideNotification(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        readShowSilentNotificationSetting();
        ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$start$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onKeyguardShowingChanged");
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onUnlockedChanged");
            }
        });
        this.keyguardUpdateMonitor.registerCallback(new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$start$2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onStrongAuthStateChanged");
            }
        });
        final Handler handler = this.handler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$start$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, KeyguardNotificationVisibilityProviderImpl.this.showSilentNotifsUri)) {
                    KeyguardNotificationVisibilityProviderImpl.this.readShowSilentNotificationSetting();
                }
                if (KeyguardNotificationVisibilityProviderImpl.this.isLockedOrLocking()) {
                    KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "Settings " + uri + " changed");
                }
            }
        };
        SecureSettings secureSettings = this.secureSettings;
        secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, contentObserver, -1);
        secureSettings.registerContentObserverForUserSync("lock_screen_allow_private_notifications", true, contentObserver, -1);
        this.globalSettings.registerContentObserverSync("zen_mode", contentObserver);
        secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN, contentObserver, -1);
        ((StatusBarStateControllerImpl) this.statusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$start$3
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onStatusBarStateChanged");
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onUpcomingStateChanged(int i) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onStatusBarUpcomingStateChanged");
            }
        });
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, new HandlerExecutor(handler));
    }
}
