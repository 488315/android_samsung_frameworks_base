package com.android.server.am.mars.filter.filter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class OngoingNotiPackageFilter implements IFilter {
    public static String TAG = "MARs:" + OngoingNotiPackageFilter.class.getSimpleName();
    public ConcurrentHashMap mActiveNotiMap;
    public Context mContext;
    public ArrayMap mNLSPkgMap;
    public final NotificationListenerService mNotificationListener;

    public abstract class OngoingNotiPackageFilterHolder {
        public static final OngoingNotiPackageFilter INSTANCE = new OngoingNotiPackageFilter();
    }

    public OngoingNotiPackageFilter() {
        this.mNLSPkgMap = new ArrayMap();
        this.mContext = null;
        this.mActiveNotiMap = new ConcurrentHashMap();
        this.mNotificationListener = new NotificationListenerService() { // from class: com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter.1
            @Override // android.service.notification.NotificationListenerService
            public void onListenerConnected() {
            }

            @Override // android.service.notification.NotificationListenerService
            public void onListenerDisconnected() {
            }

            @Override // android.service.notification.NotificationListenerService
            public void onNotificationPosted(StatusBarNotification statusBarNotification) {
                if (statusBarNotification != null) {
                    OngoingNotiPackageFilter.this.mActiveNotiMap.put(Integer.valueOf(statusBarNotification.getId()), statusBarNotification);
                }
            }

            @Override // android.service.notification.NotificationListenerService
            public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
                if (statusBarNotification != null) {
                    OngoingNotiPackageFilter.this.mActiveNotiMap.remove(Integer.valueOf(statusBarNotification.getId()));
                }
            }
        };
    }

    public static OngoingNotiPackageFilter getInstance() {
        return OngoingNotiPackageFilterHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        setContext(context);
        try {
            this.mNotificationListener.registerAsSystemService(context, new ComponentName(context.getPackageName(), context.getClass().getCanonicalName()), ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            Slog.e(TAG, "Failed to register notification listener, " + e.toString());
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        try {
            this.mNotificationListener.unregisterAsSystemService();
        } catch (RemoteException e) {
            Slog.e(TAG, "Failed to unregister notification listener, " + e.toString());
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        if (isNotificationListenerPresent(str, i)) {
            Slog.d(TAG, "filter(NotiListener) : " + str + "(" + i + ")");
            return 3;
        }
        ConcurrentHashMap concurrentHashMap = this.mActiveNotiMap;
        if (concurrentHashMap == null) {
            return 0;
        }
        Iterator it = concurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            StatusBarNotification statusBarNotification = (StatusBarNotification) ((Map.Entry) it.next()).getValue();
            if (statusBarNotification != null && statusBarNotification.getPackageName().equals(str) && statusBarNotification.getUid() == i2 && (statusBarNotification.getNotification().flags & 34) != 0) {
                Slog.i(TAG, "filter : " + str + "(" + i + ")");
                return 3;
            }
        }
        return 0;
    }

    public final boolean isNotificationListenerPresent(String str, int i) {
        ArrayList arrayList = (ArrayList) this.mNLSPkgMap.get(Integer.valueOf(i));
        if (arrayList != null) {
            return arrayList.contains(str);
        }
        return false;
    }

    public void onNotificationListenerBinded(String str, Integer num) {
        ArrayList arrayList = (ArrayList) this.mNLSPkgMap.get(num);
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(str);
        this.mNLSPkgMap.put(num, arrayList);
    }

    public void onNotificationListenerUnBinded(String str, Integer num) {
        ArrayList arrayList = (ArrayList) this.mNLSPkgMap.get(num);
        if (arrayList != null) {
            arrayList.remove(str);
        }
        this.mNLSPkgMap.put(num, arrayList);
    }
}
