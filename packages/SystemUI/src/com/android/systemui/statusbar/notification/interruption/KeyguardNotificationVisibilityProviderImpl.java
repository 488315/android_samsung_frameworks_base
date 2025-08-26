package com.android.systemui.statusbar.notification.interruption;

import android.app.INotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
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

/* loaded from: classes3.dex */
public final class KeyguardNotificationVisibilityProviderImpl implements CoreStartable, KeyguardNotificationVisibilityProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
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
            int i2 = KeyguardNotificationVisibilityProviderImpl.$r8$clinit;
            KeyguardNotificationVisibilityProviderImpl keyguardNotificationVisibilityProviderImpl = this.this$0;
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
        if (!keyguardNotificationVisibilityProviderImpl.keyguardUpdateMonitor.isUserInLockdown(i)) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) keyguardNotificationVisibilityProviderImpl.lockscreenUserManager;
            if (!notificationLockscreenUserManagerImpl.isLockscreenPublicMode(i)) {
                return false;
            }
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null && expandableNotificationRow.isInsignificantSummary()) {
                return false;
            }
            if (notificationEntry.mRanking.getLockscreenVisibilityOverride() != -1 && ((notificationEntry.mRanking.getChannel() == null || notificationEntry.mRanking.getChannel().getLockscreenVisibility() != -1) && notificationLockscreenUserManagerImpl.userAllowsNotificationsInPublic(i))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isLockedOrLocking", Boolean.valueOf(isLockedOrLocking()));
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "keyguardStateController.isShowing", Boolean.valueOf(((KeyguardStateControllerImpl) this.keyguardStateController).mShowing));
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "statusBarStateController.currentOrUpcomingState", Integer.valueOf(((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState));
            indentingPrintWriterAsIndenting.decreaseIndent();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "hideSilentNotificationsOnLockscreen", Boolean.valueOf(this.hideSilentNotificationsOnLockscreen));
        } catch (Throwable th) {
            indentingPrintWriterAsIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean isLockedOrLocking() {
        return ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || ((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState == 1;
    }

    public final void readShowSilentNotificationSetting() {
        this.hideSilentNotificationsOnLockscreen = !this.secureSettings.getBoolForUser(SettingsHelper.INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN, true, -2);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldHideIfEntrySilent(PipelineEntry pipelineEntry) {
        boolean notificationAlertsEnabledForPackage;
        NotificationEntry representativeEntry;
        if (this.hideSilentNotificationsOnLockscreen) {
            if (pipelineEntry == null || (representativeEntry = pipelineEntry.getRepresentativeEntry()) == null) {
                notificationAlertsEnabledForPackage = true;
                if (!notificationAlertsEnabledForPackage) {
                }
            } else {
                try {
                    notificationAlertsEnabledForPackage = this.iNotificationManager.getNotificationAlertsEnabledForPackage(representativeEntry.mSbn.getPackageName(), representativeEntry.mSbn.getUid());
                } catch (RemoteException e) {
                    Log.e("KeyguardNotificationVisibilityProviderImpl", "Unable to get AlertsEnabledForPackage", e);
                }
                if (!notificationAlertsEnabledForPackage) {
                    return true;
                }
            }
        }
        if (!this.highPriorityProvider.isHighPriority(pipelineEntry, false)) {
            NotificationEntry representativeEntry2 = pipelineEntry.getRepresentativeEntry();
            if ((representativeEntry2 != null && representativeEntry2.mRanking.isAmbient()) || this.hideSilentNotificationsOnLockscreen) {
                return true;
            }
            PipelineEntry parent = pipelineEntry.getParent();
            if (parent != null) {
                shouldHideIfEntrySilent(parent);
            }
        }
        return false;
    }

    public final boolean shouldHideNotification(NotificationEntry notificationEntry) {
        if (isLockedOrLocking()) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            if (notificationLockscreenUserManagerImpl.mShowLockscreenNotifications) {
                int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
                int identifier = notificationEntry.mSbn.getUser().getIdentifier();
                if (!(userSettingsDisallowNotification$disallowForUser(this, notificationEntry, i) ? true : (identifier == -1 || identifier == i) ? false : userSettingsDisallowNotification$disallowForUser(this, notificationEntry, identifier))) {
                    if (notificationEntry.mSbn.getNotification().visibility == -1) {
                        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                        if (!(statusBarNotification.getNotification().visibility == -1 && (!"com.nttdocomo.android.atf".equals(statusBarNotification.getPackageName()) || ((KeyguardStateControllerImpl) this.keyguardStateController).mSecure))) {
                        }
                    }
                    if (shouldHideIfEntrySilent(notificationEntry)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        readShowSilentNotificationSetting();
        ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl.start.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onKeyguardShowingChanged");
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onUnlockedChanged");
            }
        });
        this.keyguardUpdateMonitor.registerCallback(new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl.start.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(KeyguardNotificationVisibilityProviderImpl.this, "onStrongAuthStateChanged");
            }
        });
        final Handler handler = this.handler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl$start$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, this.this$0.showSilentNotifsUri)) {
                    this.this$0.readShowSilentNotificationSetting();
                }
                if (this.this$0.isLockedOrLocking()) {
                    KeyguardNotificationVisibilityProviderImpl.access$notifyStateChanged(this.this$0, "Settings " + uri + " changed");
                }
            }
        };
        SecureSettings secureSettings = this.secureSettings;
        secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, contentObserver, -1);
        secureSettings.registerContentObserverForUserSync("lock_screen_allow_private_notifications", true, contentObserver, -1);
        this.globalSettings.registerContentObserverSync("zen_mode", contentObserver);
        secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN, contentObserver, -1);
        this.statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl.start.3
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
