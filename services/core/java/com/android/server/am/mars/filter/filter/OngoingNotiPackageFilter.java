package com.android.server.am.mars.filter.filter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class OngoingNotiPackageFilter implements IFilter {
    public final ArrayMap mNLSPkgMap = new ArrayMap();
    public final ConcurrentHashMap mActiveNotiMap = new ConcurrentHashMap();
    public final AnonymousClass1 mNotificationListener =
            new NotificationListenerService() { // from class:
                                                // com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter.1
                @Override // android.service.notification.NotificationListenerService
                public final void onListenerConnected() {}

                @Override // android.service.notification.NotificationListenerService
                public final void onListenerDisconnected() {}

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationPosted(
                        StatusBarNotification statusBarNotification) {
                    if (statusBarNotification != null) {
                        OngoingNotiPackageFilter.this.mActiveNotiMap.put(
                                Integer.valueOf(statusBarNotification.getId()),
                                statusBarNotification);
                    }
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationRemoved(
                        StatusBarNotification statusBarNotification) {
                    if (statusBarNotification != null) {
                        OngoingNotiPackageFilter.this.mActiveNotiMap.remove(
                                Integer.valueOf(statusBarNotification.getId()));
                    }
                }
            };

    public abstract class OngoingNotiPackageFilterHolder {
        public static final OngoingNotiPackageFilter INSTANCE = new OngoingNotiPackageFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        try {
            unregisterAsSystemService();
        } catch (RemoteException e) {
            Slog.e(
                    "MARs:OngoingNotiPackageFilter",
                    "Failed to unregister notification listener, " + e.toString());
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        ArrayList arrayList = (ArrayList) this.mNLSPkgMap.get(Integer.valueOf(i));
        if (arrayList != null ? arrayList.contains(str) : false) {
            Slog.d(
                    "MARs:OngoingNotiPackageFilter",
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            i, "filter(NotiListener) : ", str, "(", ")"));
            return 3;
        }
        ConcurrentHashMap concurrentHashMap = this.mActiveNotiMap;
        if (concurrentHashMap != null) {
            Iterator it = concurrentHashMap.entrySet().iterator();
            while (it.hasNext()) {
                StatusBarNotification statusBarNotification =
                        (StatusBarNotification) ((Map.Entry) it.next()).getValue();
                if (statusBarNotification != null
                        && statusBarNotification.getPackageName().equals(str)
                        && statusBarNotification.getUid() == i2
                        && (statusBarNotification.getNotification().flags & 34) != 0) {
                    Slog.i(
                            "MARs:OngoingNotiPackageFilter",
                            AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                    i, "filter : ", str, "(", ")"));
                    return 3;
                }
            }
        }
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        try {
            registerAsSystemService(
                    context,
                    new ComponentName(
                            context.getPackageName(), context.getClass().getCanonicalName()),
                    ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            Slog.e(
                    "MARs:OngoingNotiPackageFilter",
                    "Failed to register notification listener, " + e.toString());
        }
    }
}
