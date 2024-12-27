package com.android.server.enterprise.application;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.enterprise.EnterpriseService;

import com.samsung.android.knox.application.AppInfoLastUsage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ApplicationUsage {
    public static volatile UsageHandler mUsageHandler;
    public Context mContext;
    public static final Map appForeGroundStats = new HashMap();
    public static final Map appBackGroundStats = new HashMap();
    public static final Object mStatsLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppBackGroundUsage {
        public long appLastServiceStartTime;
        public long appLastServiceStopTime;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppForeGroundUsage {
        public long appLastLaunchTime;
        public long appLastPausetime;
        public int appLaunchCount;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsageHandler extends Handler {
        public UsageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Map map;
            int i = message.what;
            if (i == 1) {
                ApplicationUsage applicationUsage = ApplicationUsage.this;
                String str = (String) message.obj;
                applicationUsage.getClass();
                synchronized (ApplicationUsage.mStatsLock) {
                    try {
                        Map map2 = ApplicationUsage.appForeGroundStats;
                        if (map2 == null) {
                            return;
                        }
                        if (((HashMap) map2).containsKey(str)) {
                            AppForeGroundUsage appForeGroundUsage =
                                    (AppForeGroundUsage) ((HashMap) map2).get(str);
                            appForeGroundUsage.appLaunchCount++;
                            appForeGroundUsage.appLastLaunchTime = System.currentTimeMillis();
                            appForeGroundUsage.appLastPausetime = 0L;
                        } else {
                            AppForeGroundUsage appForeGroundUsage2 = new AppForeGroundUsage();
                            appForeGroundUsage2.appLastLaunchTime = 0L;
                            appForeGroundUsage2.appLastPausetime = 0L;
                            appForeGroundUsage2.appLaunchCount = 1;
                            appForeGroundUsage2.appLastLaunchTime = System.currentTimeMillis();
                            ((HashMap) map2).put(str, appForeGroundUsage2);
                        }
                        return;
                    } finally {
                    }
                }
            }
            if (i == 2) {
                ApplicationUsage applicationUsage2 = ApplicationUsage.this;
                String str2 = (String) message.obj;
                applicationUsage2.getClass();
                synchronized (ApplicationUsage.mStatsLock) {
                    try {
                        Map map3 = ApplicationUsage.appForeGroundStats;
                        if (((HashMap) map3).containsKey(str2)) {
                            ((AppForeGroundUsage) ((HashMap) map3).get(str2)).appLastPausetime =
                                    System.currentTimeMillis();
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    Log.i("ApplicationUsage", "handleMessage : MSG_UPDATE_USAGE_DB");
                    ApplicationUsage.this._insertToAppControlDB();
                    return;
                }
                ApplicationUsage applicationUsage3 = ApplicationUsage.this;
                String str3 = (String) message.obj;
                applicationUsage3.getClass();
                synchronized (ApplicationUsage.mStatsLock) {
                    try {
                        Map map4 = ApplicationUsage.appBackGroundStats;
                        if (map4 == null) {
                            return;
                        }
                        if (((HashMap) map4).containsKey(str3)) {
                            ((AppBackGroundUsage) ((HashMap) map4).get(str3))
                                            .appLastServiceStopTime =
                                    System.currentTimeMillis();
                        }
                        return;
                    } finally {
                    }
                }
            }
            ApplicationUsage applicationUsage4 = ApplicationUsage.this;
            ActivityManager.RunningServiceInfo runningServiceInfo =
                    (ActivityManager.RunningServiceInfo) message.obj;
            applicationUsage4.getClass();
            if (runningServiceInfo == null || (map = ApplicationUsage.appBackGroundStats) == null) {
                return;
            }
            if ((runningServiceInfo.started || runningServiceInfo.clientLabel != 0)
                    && (runningServiceInfo.flags & 8) == 0) {
                String str4 =
                        runningServiceInfo.service.getPackageName()
                                + ":"
                                + UserHandle.getUserId(runningServiceInfo.uid);
                synchronized (ApplicationUsage.mStatsLock) {
                    try {
                        if (((HashMap) map).containsKey(str4)) {
                            AppBackGroundUsage appBackGroundUsage =
                                    (AppBackGroundUsage) ((HashMap) map).get(str4);
                            if (appBackGroundUsage.appLastServiceStartTime == 0) {
                                appBackGroundUsage.appLastServiceStartTime =
                                        System.currentTimeMillis();
                            }
                            appBackGroundUsage.appLastServiceStopTime = 0L;
                        } else {
                            AppBackGroundUsage appBackGroundUsage2 = new AppBackGroundUsage();
                            appBackGroundUsage2.appLastServiceStartTime = 0L;
                            appBackGroundUsage2.appLastServiceStopTime = 0L;
                            appBackGroundUsage2.appLastServiceStartTime =
                                    System.currentTimeMillis();
                            ((HashMap) map).put(str4, appBackGroundUsage2);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public static AppInfoLastUsage[] filterUnInstalledApps(
            AppInfoLastUsage[] appInfoLastUsageArr, int i, int i2) {
        int i3;
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (appInfoLastUsageArr != null) {
            i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                if (applicationPolicy.isApplicationInstalled(
                        i2, appInfoLastUsageArr[i4].packageName)) {
                    i3++;
                }
            }
        } else {
            i3 = 0;
        }
        if (i3 == 0) {
            return null;
        }
        AppInfoLastUsage[] appInfoLastUsageArr2 = new AppInfoLastUsage[i3];
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            if (applicationPolicy.isApplicationInstalled(i2, appInfoLastUsageArr[i6].packageName)) {
                appInfoLastUsageArr2[i5] = appInfoLastUsageArr[i6];
                i5++;
            }
        }
        return appInfoLastUsageArr2;
    }

    public final void _insertToAppControlDB() {
        Log.i(
                "ApplicationUsage",
                "Updating Usage Statistics in DB @ " + System.currentTimeMillis());
        try {
            synchronized (mStatsLock) {
                updateForeGroundUsageData();
                updateBackGroundUsageDetails();
                ((ApplicationPolicy) EnterpriseService.getPolicyService("application_policy"))
                        .updateDataUsageDb();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(
                "ApplicationUsage",
                "Done Updating Usage Statistics in DB @ " + System.currentTimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int calculateAvgPerMonth(int r8, int r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ApplicationUsage.calculateAvgPerMonth(int,"
                    + " int, java.lang.String):int");
    }

    public final void updateBackGroundUsageDetails() {
        ApplicationUsageDb applicationUsageDb = new ApplicationUsageDb(this.mContext);
        Map map = appBackGroundStats;
        if (map != null) {
            HashMap hashMap = (HashMap) map;
            if (hashMap.isEmpty()) {
                return;
            }
            Set<String> keySet = hashMap.keySet();
            ArrayList arrayList = new ArrayList();
            for (String str : keySet) {
                AppBackGroundUsage appBackGroundUsage =
                        (AppBackGroundUsage) ((HashMap) appBackGroundStats).get(str);
                long j = appBackGroundUsage.appLastServiceStopTime;
                if (j != 0) {
                    applicationUsageDb.updateBackGroundUsageDetails(
                            appBackGroundUsage.appLastServiceStartTime, j, str);
                    arrayList.add(str);
                } else {
                    applicationUsageDb.updateBackGroundUsageDetails(
                            appBackGroundUsage.appLastServiceStartTime,
                            System.currentTimeMillis(),
                            str);
                }
            }
            if (arrayList.size() != 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    ((HashMap) appBackGroundStats).remove(arrayList.get(i));
                }
            }
        }
    }

    public final void updateForeGroundUsageData() {
        ApplicationUsageDb applicationUsageDb = new ApplicationUsageDb(this.mContext);
        Map map = appForeGroundStats;
        if (map != null) {
            HashMap hashMap = (HashMap) map;
            if (hashMap.isEmpty()) {
                return;
            }
            Set<String> keySet = hashMap.keySet();
            ArrayList arrayList = new ArrayList();
            for (String str : keySet) {
                AppForeGroundUsage appForeGroundUsage =
                        (AppForeGroundUsage) ((HashMap) appForeGroundStats).get(str);
                long j = appForeGroundUsage.appLastPausetime;
                if (j != 0) {
                    applicationUsageDb.updateForeGroundUsageDetails(
                            appForeGroundUsage.appLaunchCount,
                            appForeGroundUsage.appLastLaunchTime,
                            j,
                            str);
                    arrayList.add(str);
                } else {
                    applicationUsageDb.updateForeGroundUsageDetails(
                            appForeGroundUsage.appLaunchCount,
                            appForeGroundUsage.appLastLaunchTime,
                            System.currentTimeMillis(),
                            str);
                }
            }
            if (arrayList.size() != 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    ((HashMap) appForeGroundStats).remove(arrayList.get(i));
                }
            }
        }
    }
}
