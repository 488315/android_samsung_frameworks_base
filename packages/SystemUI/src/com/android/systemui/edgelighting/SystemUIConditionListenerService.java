package com.android.systemui.edgelighting;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Slog;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.edgelighting.SystemUIConditionListenerService;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SystemUIConditionListenerService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mBinder = new AnonymousClass1();
    public NotifLiveDataImpl mEntries;

    /* renamed from: com.android.systemui.edgelighting.SystemUIConditionListenerService$1, reason: invalid class name */
    public class AnonymousClass1 extends ISystemUIConditionListener.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass1() {
        }

        public final NotificationEntry getEntry(String str) {
            Collection<NotificationEntry> collection;
            NotifLiveDataImpl notifLiveDataImpl = SystemUIConditionListenerService.this.mEntries;
            if (notifLiveDataImpl == null || (collection = (Collection) notifLiveDataImpl.atomicValue.get()) == null) {
                return null;
            }
            for (NotificationEntry notificationEntry : collection) {
                if (notificationEntry != null && str.equals(notificationEntry.mKey)) {
                    return notificationEntry;
                }
            }
            return null;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isAppLockEnabled() {
            return ((AppLockNotificationControllerImpl) ((AppLockNotificationController) Dependency.sDependency.getDependencyInner(AppLockNotificationController.class))).isAppLockEnabled();
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isInterrupted(String str) {
            Collection collection;
            NotificationEntry notificationEntry;
            NotifLiveDataImpl notifLiveDataImpl = SystemUIConditionListenerService.this.mEntries;
            if (notifLiveDataImpl == null || (collection = (Collection) notifLiveDataImpl.atomicValue.get()) == null) {
                return false;
            }
            Iterator it = collection.iterator();
            while (it.hasNext() && (notificationEntry = (NotificationEntry) it.next()) != null) {
                if (str.equals(notificationEntry.mKey)) {
                    return notificationEntry.interruption;
                }
            }
            return false;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isNeedToSanitize(int i, int i2, String str) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) ((NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class));
            if (!notificationLockscreenUserManagerImpl.packageHasVisibilityOverrideToShowContent(str)) {
                boolean z = ((notificationLockscreenUserManagerImpl.mCurrentManagedProfiles.contains(i) || notificationLockscreenUserManagerImpl.userAllowsPrivateNotificationsInPublic(notificationLockscreenUserManagerImpl.mCurrentUserId)) && notificationLockscreenUserManagerImpl.userAllowsPrivateNotificationsInPublic(i)) ? false : true;
                if ((notificationLockscreenUserManagerImpl.packageHasVisibilityOverride(str) || z) && notificationLockscreenUserManagerImpl.isLockscreenPublicMode(i)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isOngoingAcitivty(String str) {
            StatusBarNotification statusBarNotification;
            NotificationEntry entry = getEntry(str);
            return (entry == null || (statusBarNotification = entry.mSbn) == null || statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) != 1) ? false : true;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isPanelsEnabled() {
            return ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).panelsEnabled();
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isRowPinned(String str) {
            NotifLiveDataImpl notifLiveDataImpl = SystemUIConditionListenerService.this.mEntries;
            if (notifLiveDataImpl == null) {
                return false;
            }
            NotificationEntry notificationEntry = null;
            for (NotificationEntry notificationEntry2 : (List) notifLiveDataImpl.atomicValue.get()) {
                if (str.equals(notificationEntry2.mKey)) {
                    notificationEntry = notificationEntry2;
                }
            }
            if (notificationEntry != null) {
                return notificationEntry.isRowPinned();
            }
            return false;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isSensitiveStateActive() {
            return ((SensitiveNotificationProtectionControllerImpl) ((SensitiveNotificationProtectionController) Dependency.sDependency.getDependencyInner(SensitiveNotificationProtectionController.class))).isSensitiveStateActive();
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean isSupportAppLock() {
            ((AppLockNotificationControllerImpl) ((AppLockNotificationController) Dependency.sDependency.getDependencyInner(AppLockNotificationController.class))).getClass();
            return NotiRune.NOTI_STYLE_APP_LOCK;
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final void requestDozeStateSubScreen(boolean z) {
            ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).requestDozeState(2, z);
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final void sendClickEvent(String str) {
            NotificationClicker.AnonymousClass1 anonymousClass1;
            NotifLiveDataImpl notifLiveDataImpl = SystemUIConditionListenerService.this.mEntries;
            if (notifLiveDataImpl == null) {
                return;
            }
            NotificationEntry notificationEntry = null;
            for (NotificationEntry notificationEntry2 : (List) notifLiveDataImpl.atomicValue.get()) {
                if (str.equals(notificationEntry2.mKey)) {
                    notificationEntry = notificationEntry2;
                }
            }
            if (notificationEntry != null) {
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && Utils.isLargeCoverFlipFolded()) {
                    Slog.d("SysUIConditionListener", "sendClickEvent: coverBriefClicked");
                    ((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).post(new SystemUIConditionListenerService$$ExternalSyntheticLambda0(notificationEntry, 1));
                    return;
                }
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow == null || (anonymousClass1 = expandableNotificationRow.mOnDragSuccessListener) == null) {
                    return;
                }
                int i = NotificationBundleUi.$r8$clinit;
                NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                ((StatusBarNotificationActivityStarter) NotificationClicker.this.mNotificationActivityStarter).onDragSuccess(entryLegacy);
            }
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final void setInterruption(String str) {
            Collection collection;
            NotificationEntry notificationEntry;
            NotifLiveDataImpl notifLiveDataImpl = SystemUIConditionListenerService.this.mEntries;
            if (notifLiveDataImpl == null || (collection = (Collection) notifLiveDataImpl.atomicValue.get()) == null) {
                return;
            }
            Iterator it = collection.iterator();
            while (it.hasNext() && (notificationEntry = (NotificationEntry) it.next()) != null) {
                if (str.equals(notificationEntry.mKey)) {
                    notificationEntry.interruption = true;
                }
            }
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final boolean shouldHideNotiForAppLockByPackage(String str) {
            AppLockNotificationControllerImpl appLockNotificationControllerImpl = (AppLockNotificationControllerImpl) ((AppLockNotificationController) Dependency.sDependency.getDependencyInner(AppLockNotificationController.class));
            return appLockNotificationControllerImpl.mActivityManager != null && appLockNotificationControllerImpl.isAppLockEnabled() && appLockNotificationControllerImpl.mActivityManager.isAppLockedPackage(str);
        }

        @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
        public final void turnToHeadsUp(final String str) {
            ((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.edgelighting.SystemUIConditionListenerService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUIConditionListenerService.AnonymousClass1 anonymousClass1 = this.f$0;
                    String str2 = str;
                    int i = SystemUIConditionListenerService.AnonymousClass1.$r8$clinit;
                    anonymousClass1.getClass();
                    HeadsUpManager headsUpManager = (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                    NotificationEntry entry = anonymousClass1.getEntry(str2);
                    ArrayList arrayList = (ArrayList) ((HeadsUpManagerImpl) headsUpManager).mCallbacks;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((HeadsUpCoordinator.AnonymousClass4) obj).turnToHeadsUp(entry);
                    }
                }
            });
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        StringBuilder sb = new StringBuilder("onBind : ");
        sb.append(intent == null ? " intent is null " : intent.toString());
        Slog.d("SysUIConditionListener", sb.toString());
        ((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).post(new SystemUIConditionListenerService$$ExternalSyntheticLambda0(this, 0));
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        Slog.d("SysUIConditionListener", "onCreate ");
    }
}
