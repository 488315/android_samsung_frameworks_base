package com.android.server.location.nsflp;

import android.app.Notification;
import android.location.LocationConstants;
import android.service.notification.StatusBarNotification;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class NSLocationMonitor$4$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NSLocationMonitor.AnonymousClass4 f$0;
    public final /* synthetic */ StatusBarNotification f$1;

    public /* synthetic */ NSLocationMonitor$4$$ExternalSyntheticLambda0(
            NSLocationMonitor.AnonymousClass4 anonymousClass4,
            StatusBarNotification statusBarNotification,
            int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass4;
        this.f$1 = statusBarNotification;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Notification notification;
        Notification notification2;
        switch (this.$r8$classId) {
            case 0:
                NSLocationMonitor.AnonymousClass4 anonymousClass4 = this.f$0;
                StatusBarNotification statusBarNotification = this.f$1;
                anonymousClass4.getClass();
                if (statusBarNotification != null
                        && (notification = statusBarNotification.getNotification()) != null) {
                    NSLocationMonitor nSLocationMonitor = NSLocationMonitor.this;
                    Object obj = NSLocationMonitor.MONITOR_SERVICE_LOCK;
                    nSLocationMonitor.getClass();
                    if ((notification.flags & 64) != 0) {
                        String packageName = statusBarNotification.getPackageName();
                        String key = statusBarNotification.getKey();
                        Set set =
                                (Set)
                                        ((HashMap)
                                                        NSLocationMonitor.this
                                                                .mForegroundNotificationList)
                                                .get(packageName);
                        if (set != null && set.remove(key) && set.isEmpty()) {
                            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                                    LocationConstants.STATE_TYPE.NOTIFICATION_REMOVED,
                                    AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                            "packageName", packageName));
                            break;
                        }
                    }
                }
                break;
            default:
                NSLocationMonitor.AnonymousClass4 anonymousClass42 = this.f$0;
                StatusBarNotification statusBarNotification2 = this.f$1;
                anonymousClass42.getClass();
                if (statusBarNotification2 != null
                        && (notification2 = statusBarNotification2.getNotification()) != null) {
                    NSLocationMonitor nSLocationMonitor2 = NSLocationMonitor.this;
                    Object obj2 = NSLocationMonitor.MONITOR_SERVICE_LOCK;
                    nSLocationMonitor2.getClass();
                    if ((notification2.flags & 64) != 0) {
                        String packageName2 = statusBarNotification2.getPackageName();
                        String key2 = statusBarNotification2.getKey();
                        Set set2 =
                                (Set)
                                        ((HashMap)
                                                        NSLocationMonitor.this
                                                                .mForegroundNotificationList)
                                                .computeIfAbsent(
                                                        packageName2,
                                                        new NSLocationMonitor$$ExternalSyntheticLambda3());
                        if (set2.add(key2) && set2.size() == 1) {
                            NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                                    LocationConstants.STATE_TYPE.NOTIFICATION_POSTED,
                                    AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                            "packageName", packageName2));
                            break;
                        }
                    }
                }
                break;
        }
    }
}
