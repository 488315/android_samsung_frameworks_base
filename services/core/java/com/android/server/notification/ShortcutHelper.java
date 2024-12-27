package com.android.server.notification;

import android.content.IntentFilter;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutServiceInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.notification.NotificationManagerService.EnqueueNotificationRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ShortcutHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HashMap mActiveShortcutBubbles = new HashMap();
    public final AnonymousClass1 mLauncherAppsCallback =
            new LauncherApps
                    .Callback() { // from class: com.android.server.notification.ShortcutHelper.1
                @Override // android.content.pm.LauncherApps.Callback
                public final void onPackageAdded(String str, UserHandle userHandle) {}

                @Override // android.content.pm.LauncherApps.Callback
                public final void onPackageChanged(String str, UserHandle userHandle) {}

                @Override // android.content.pm.LauncherApps.Callback
                public final void onPackageRemoved(String str, UserHandle userHandle) {}

                @Override // android.content.pm.LauncherApps.Callback
                public final void onPackagesAvailable(
                        String[] strArr, UserHandle userHandle, boolean z) {}

                @Override // android.content.pm.LauncherApps.Callback
                public final void onPackagesUnavailable(
                        String[] strArr, UserHandle userHandle, boolean z) {}

                @Override // android.content.pm.LauncherApps.Callback
                public final void onShortcutsChanged(String str, List list, UserHandle userHandle) {
                    String packageName;
                    HashMap hashMap = (HashMap) ShortcutHelper.this.mActiveShortcutBubbles.get(str);
                    ArrayList arrayList = new ArrayList();
                    if (hashMap != null) {
                        Iterator it = new HashSet(hashMap.keySet()).iterator();
                        while (it.hasNext()) {
                            String str2 = (String) it.next();
                            int i = 0;
                            while (true) {
                                if (i >= list.size()) {
                                    arrayList.add((String) hashMap.get(str2));
                                    hashMap.remove(str2);
                                    if (hashMap.isEmpty()) {
                                        ShortcutHelper.this.mActiveShortcutBubbles.remove(str);
                                        ShortcutHelper shortcutHelper = ShortcutHelper.this;
                                        if (shortcutHelper.mLauncherAppsCallbackRegistered
                                                && shortcutHelper.mActiveShortcutBubbles
                                                        .isEmpty()) {
                                            ShortcutHelper shortcutHelper2 = ShortcutHelper.this;
                                            shortcutHelper2.mLauncherAppsService.unregisterCallback(
                                                    shortcutHelper2.mLauncherAppsCallback);
                                            ShortcutHelper.this.mLauncherAppsCallbackRegistered =
                                                    false;
                                        }
                                    }
                                } else if (((ShortcutInfo) list.get(i)).getId().equals(str2)) {
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        String str3 = (String) arrayList.get(i2);
                        NotificationManagerService.AnonymousClass2 anonymousClass2 =
                                ShortcutHelper.this.mShortcutListener;
                        if (anonymousClass2 != null) {
                            synchronized (NotificationManagerService.this.mNotificationLock) {
                                try {
                                    NotificationRecord notificationRecord =
                                            (NotificationRecord)
                                                    NotificationManagerService.this
                                                            .mNotificationsByKey.get(str3);
                                    packageName =
                                            notificationRecord != null
                                                    ? notificationRecord.sbn.getPackageName()
                                                    : null;
                                } finally {
                                }
                            }
                            boolean z =
                                    packageName != null
                                            && NotificationManagerService.this
                                                            .getPackageImportanceWithIdentity(
                                                                    packageName)
                                                    == 100;
                            synchronized (NotificationManagerService.this.mNotificationLock) {
                                try {
                                    NotificationRecord notificationRecord2 =
                                            (NotificationRecord)
                                                    NotificationManagerService.this
                                                            .mNotificationsByKey.get(str3);
                                    if (notificationRecord2 != null) {
                                        notificationRecord2.mShortcutInfo = null;
                                        notificationRecord2.sbn.getNotification().flags |= 8;
                                        NotificationManagerService notificationManagerService =
                                                NotificationManagerService.this;
                                        NotificationManagerService.WorkerHandler workerHandler =
                                                notificationManagerService.mHandler;
                                        int identifier =
                                                notificationRecord2.sbn.getUser().getIdentifier();
                                        NotificationManagerService.this
                                                .mPostNotificationTrackerFactory.getClass();
                                        workerHandler.post(
                                                notificationManagerService
                                                .new EnqueueNotificationRunnable(
                                                        identifier,
                                                        notificationRecord2,
                                                        z,
                                                        new NotificationManagerService
                                                                .PostNotificationTracker(null)));
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                }
            };
    public boolean mLauncherAppsCallbackRegistered;
    public LauncherApps mLauncherAppsService;
    public final NotificationManagerService.AnonymousClass2 mShortcutListener;
    public ShortcutServiceInternal mShortcutServiceInternal;
    public UserManager mUserManager;

    static {
        try {
            new IntentFilter().addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Slog.e("ShortcutHelper", "Bad mime type", e);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.notification.ShortcutHelper$1] */
    public ShortcutHelper(
            LauncherApps launcherApps,
            NotificationManagerService.AnonymousClass2 anonymousClass2,
            ShortcutServiceInternal shortcutServiceInternal,
            UserManager userManager) {
        this.mLauncherAppsService = launcherApps;
        this.mShortcutListener = anonymousClass2;
        this.mShortcutServiceInternal = shortcutServiceInternal;
        this.mUserManager = userManager;
    }

    public final ShortcutInfo getValidShortcutInfo(String str, UserHandle userHandle, String str2) {
        if (this.mLauncherAppsService != null && this.mUserManager.isUserUnlocked(userHandle)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (str != null && str2 != null && userHandle != null) {
                try {
                    LauncherApps.ShortcutQuery shortcutQuery = new LauncherApps.ShortcutQuery();
                    shortcutQuery.setPackage(str2);
                    shortcutQuery.setShortcutIds(Arrays.asList(str));
                    shortcutQuery.setQueryFlags(3089);
                    List<ShortcutInfo> shortcuts =
                            this.mLauncherAppsService.getShortcuts(shortcutQuery, userHandle);
                    ShortcutInfo shortcutInfo =
                            (shortcuts == null || shortcuts.size() <= 0) ? null : shortcuts.get(0);
                    userHandle.getIdentifier();
                    if (shortcutInfo != null && shortcutInfo.isLongLived()) {
                        if (shortcutInfo.isEnabled()) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return shortcutInfo;
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return null;
    }

    public final void maybeListenForShortcutChangesForBubbles(
            NotificationRecord notificationRecord, boolean z, Handler handler) {
        ShortcutInfo shortcutInfo;
        String shortcutId =
                notificationRecord.sbn.getNotification().getBubbleMetadata() != null
                        ? notificationRecord
                                .sbn
                                .getNotification()
                                .getBubbleMetadata()
                                .getShortcutId()
                        : null;
        AnonymousClass1 anonymousClass1 = this.mLauncherAppsCallback;
        if (!z
                && !TextUtils.isEmpty(shortcutId)
                && (shortcutInfo = notificationRecord.mShortcutInfo) != null
                && shortcutInfo.getId().equals(shortcutId)) {
            HashMap hashMap =
                    (HashMap)
                            this.mActiveShortcutBubbles.get(
                                    notificationRecord.sbn.getPackageName());
            if (hashMap == null) {
                hashMap = new HashMap();
            }
            hashMap.put(shortcutId, notificationRecord.sbn.getKey());
            this.mActiveShortcutBubbles.put(notificationRecord.sbn.getPackageName(), hashMap);
            if (this.mLauncherAppsCallbackRegistered) {
                return;
            }
            this.mLauncherAppsService.registerCallback(anonymousClass1, handler);
            this.mLauncherAppsCallbackRegistered = true;
            return;
        }
        HashMap hashMap2 =
                (HashMap) this.mActiveShortcutBubbles.get(notificationRecord.sbn.getPackageName());
        if (hashMap2 != null) {
            if (TextUtils.isEmpty(shortcutId)) {
                Iterator it = new HashSet(hashMap2.keySet()).iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (notificationRecord.sbn.getKey().equals((String) hashMap2.get(str))) {
                        hashMap2.remove(str);
                    }
                }
            } else {
                hashMap2.remove(shortcutId);
            }
            if (hashMap2.isEmpty()) {
                this.mActiveShortcutBubbles.remove(notificationRecord.sbn.getPackageName());
            }
        }
        if (this.mLauncherAppsCallbackRegistered && this.mActiveShortcutBubbles.isEmpty()) {
            this.mLauncherAppsService.unregisterCallback(anonymousClass1);
            this.mLauncherAppsCallbackRegistered = false;
        }
    }

    public void setLauncherApps(LauncherApps launcherApps) {
        this.mLauncherAppsService = launcherApps;
    }

    public void setShortcutServiceInternal(ShortcutServiceInternal shortcutServiceInternal) {
        this.mShortcutServiceInternal = shortcutServiceInternal;
    }

    public void setUserManager(UserManager userManager) {
        this.mUserManager = userManager;
    }
}
