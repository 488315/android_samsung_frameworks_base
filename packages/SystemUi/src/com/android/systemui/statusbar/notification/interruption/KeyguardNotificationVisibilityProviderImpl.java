package com.android.systemui.statusbar.notification.interruption;

import android.app.INotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteException;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.DisplayCutoutBaseView$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final SysuiStatusBarStateController statusBarStateController;
    public final UserTracker userTracker;
    public final Uri showSilentNotifsUri = Settings.Secure.getUriFor("lock_screen_show_silent_notifications");
    public final ListenerSet onStateChangedListeners = new ListenerSet();
    public final KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl = KeyguardNotificationVisibilityProviderImpl.this;
            if (keyguardNotificationVisibilityProviderImpl.isLockedOrLocking()) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(keyguardNotificationVisibilityProviderImpl, "onUserSwitched");
            }
            keyguardNotificationVisibilityProviderImpl.readShowSilentNotificationSetting();
        }
    };

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$userTrackerCallback$1] */
    public KeyguardNotificationVisibilityProviderImpl(Handler handler, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HighPriorityProvider highPriorityProvider, SysuiStatusBarStateController sysuiStatusBarStateController, UserTracker userTracker, SecureSettings secureSettings, GlobalSettings globalSettings, INotificationManager iNotificationManager) {
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
    }

    public static final void access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl, String str) {
        Iterator it = keyguardNotificationVisibilityProviderImpl.onStateChangedListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(str);
        }
    }

    public static final boolean userSettingsDisallowNotification$disallowForUser(KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl, NotificationEntry notificationEntry, int i) {
        if (keyguardNotificationVisibilityProviderImpl.keyguardUpdateMonitor.isUserInLockdown(i)) {
            return true;
        }
        NotificationLockscreenUserManager notificationLockscreenUserManager = keyguardNotificationVisibilityProviderImpl.lockscreenUserManager;
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isLockscreenPublicMode(i) && (notificationEntry.mRanking.getLockscreenVisibilityOverride() == -1 || !((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).userAllowsNotificationsInPublic(i));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("isLockedOrLocking=" + isLockedOrLocking());
        asIndenting.increaseIndent();
        try {
            asIndenting.println("keyguardStateController.isShowing=" + ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing);
            asIndenting.println("statusBarStateController.currentOrUpcomingState=" + ((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState);
            asIndenting.decreaseIndent();
            DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("hideSilentNotificationsOnLockscreen=", this.hideSilentNotificationsOnLockscreen, asIndenting);
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean isLockedOrLocking() {
        return ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || ((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState == 1;
    }

    public final void readShowSilentNotificationSetting() {
        this.hideSilentNotificationsOnLockscreen = !this.secureSettings.getBoolForUser(-2, "lock_screen_show_silent_notifications", true);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldHideIfEntrySilent(ListEntry listEntry) {
        boolean z;
        NotificationEntry representativeEntry;
        if (this.hideSilentNotificationsOnLockscreen) {
            if (listEntry != null && (representativeEntry = listEntry.getRepresentativeEntry()) != null) {
                try {
                    z = this.iNotificationManager.getNotificationAlertsEnabledForPackage(representativeEntry.mSbn.getPackageName(), representativeEntry.mSbn.getUid());
                } catch (RemoteException e) {
                    Log.e("KeyguardNotificationVisibilityProviderImpl", "Unable to get AlertsEnabledForPackage", e);
                }
                if (!z) {
                    return true;
                }
            }
            z = true;
            if (!z) {
            }
        }
        if (!this.highPriorityProvider.isHighPriority(listEntry, false)) {
            NotificationEntry representativeEntry2 = listEntry.getRepresentativeEntry();
            if ((representativeEntry2 != null && representativeEntry2.isAmbient()) || this.hideSilentNotificationsOnLockscreen) {
                return true;
            }
            GroupEntry parent = listEntry.getParent();
            if (parent != null) {
                shouldHideIfEntrySilent(parent);
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0062, code lost:
    
        if ((r0.getNotification().visibility == -1 && (!kotlin.jvm.internal.Intrinsics.areEqual("com.nttdocomo.android.atf", r0.getPackageName()) || ((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r6.keyguardStateController).mSecure)) != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldHideNotification(NotificationEntry notificationEntry) {
        if (!isLockedOrLocking()) {
            return false;
        }
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        if (notificationLockscreenUserManagerImpl.mShowLockscreenNotifications) {
            int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
            int identifier = notificationEntry.mSbn.getUser().getIdentifier();
            if (!(userSettingsDisallowNotification$disallowForUser(this, notificationEntry, i) ? true : (identifier == -1 || identifier == i) ? false : userSettingsDisallowNotification$disallowForUser(this, notificationEntry, identifier))) {
                if (notificationEntry.mSbn.getNotification().visibility == -1) {
                    StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                }
                if (!shouldHideIfEntrySilent(notificationEntry)) {
                    return false;
                }
            }
        }
        return true;
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
        secureSettings.registerContentObserverForUser("lock_screen_show_notifications", contentObserver, -1);
        secureSettings.registerContentObserverForUser("lock_screen_allow_private_notifications", true, contentObserver, -1);
        GlobalSettingsImpl globalSettingsImpl = (GlobalSettingsImpl) this.globalSettings;
        globalSettingsImpl.registerContentObserverForUser(globalSettingsImpl.getUriFor("zen_mode"), false, contentObserver, globalSettingsImpl.getUserId());
        secureSettings.registerContentObserverForUser("lock_screen_show_silent_notifications", contentObserver, -1);
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
