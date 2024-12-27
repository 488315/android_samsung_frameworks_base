package com.android.server.notification;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.util.Slog;
import com.android.server.notification.PreferencesHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService f$0;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda1(NotificationManagerService notificationManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationManagerService;
    }

    /* JADX INFO: Infinite loop detected, blocks: 8, insns: 0 */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PreferencesHelper preferencesHelper = this.f$0.mPreferencesHelper;
                preferencesHelper.getClass();
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                List<ApplicationInfo> installedApplications = preferencesHelper.mPm.getInstalledApplications(0);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                try {
                    for (ApplicationInfo applicationInfo : installedApplications) {
                        if (preferencesHelper.getNotificationChannels(applicationInfo.uid, applicationInfo.packageName, false).getList().size() > 0) {
                            arrayList.add(applicationInfo.packageName);
                        }
                    }
                    for (ResolveInfo resolveInfo : preferencesHelper.mPm.queryIntentActivities(intent, 0)) {
                        if (arrayList.contains(resolveInfo.activityInfo.packageName)) {
                            arrayList2.add(resolveInfo.activityInfo.packageName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (preferencesHelper.mLock) {
                    try {
                        int size = preferencesHelper.mPackagePreferences.size();
                        for (int i = 0; i < size; i++) {
                            PreferencesHelper.PackagePreferences packagePreferences = (PreferencesHelper.PackagePreferences) preferencesHelper.mPackagePreferences.valueAt(i);
                            if (arrayList2.contains(packagePreferences.pkg)) {
                                Slog.d("NotificationPrefHelper", "resetDefaultAllowEdgeLightingExceptSettingApps - excepPackage = " + packagePreferences.pkg);
                            } else {
                                packagePreferences.allowEdgeLighting = true;
                            }
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                NotificationManagerService notificationManagerService = this.f$0;
                synchronized (notificationManagerService.mNotificationLock) {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        Iterator it = notificationManagerService.mNotificationList.iterator();
                        while (it.hasNext()) {
                            NotificationRecord notificationRecord = (NotificationRecord) it.next();
                            if ((notificationRecord.sbn.getNotification().semFlags & 262144) != 0) {
                                if (NotificationManagerService.DBG) {
                                    Slog.e("NotificationService", "skip already set insignificant flag");
                                }
                            } else if ((notificationRecord.sbn.getNotification().semFlags & 131072) != 0) {
                                if (NotificationManagerService.DBG) {
                                    Slog.e("NotificationService", "skip highlight noti");
                                }
                            } else if (!notificationRecord.mStats.hasSeen()) {
                                Slog.e("NotificationService", "skip non-seen noti : " + notificationRecord.sbn.getPackageName());
                            } else if (currentTimeMillis - notificationRecord.mUpdateTimeMs < 172800000) {
                                Slog.e("NotificationService", "skip 3th midnight is not passed package = " + notificationRecord.sbn.getPackageName());
                            } else {
                                Notification notification = notificationRecord.sbn.getNotification();
                                notification.semFlags = 262144 | notification.semFlags;
                                notificationManagerService.mListeners.notifyPostedLocked(notificationRecord, notificationRecord);
                            }
                        }
                    } finally {
                    }
                }
                return;
            case 2:
                NotificationManagerService notificationManagerService2 = this.f$0;
                notificationManagerService2.getClass();
                while (true) {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException unused) {
                    }
                    notificationManagerService2.mInternalService.removeBitmaps();
                }
            default:
                this.f$0.registerConversationAppPolicyScpm();
                return;
        }
    }
}
