package com.android.server.enterprise.application;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.samsung.android.knox.application.AppInfoLastUsage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class ApplicationUsage {
    public static volatile Handler mUsageHandler;
    public Context mContext;
    public static Map appForeGroundStats = new HashMap();
    public static Map appBackGroundStats = new HashMap();
    public static Object mStatsLock = new Object();

    public ApplicationUsage(Context context) {
        this.mContext = context;
        if (mUsageHandler == null) {
            synchronized (this) {
                if (mUsageHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("MDMAppUsageHandlerThread", 10);
                    handlerThread.start();
                    mUsageHandler = new UsageHandler(handlerThread.getLooper());
                }
            }
        }
    }

    public class UsageHandler extends Handler {
        public UsageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ApplicationUsage.this._appLaunchCount((String) message.obj);
                return;
            }
            if (i == 2) {
                ApplicationUsage.this._appPauseTime((String) message.obj);
                return;
            }
            if (i == 3) {
                ApplicationUsage.this._appServiceStartTime((ActivityManager.RunningServiceInfo) message.obj);
                return;
            }
            if (i == 4) {
                ApplicationUsage.this._appServiceStopTime((String) message.obj);
            } else {
                if (i != 5) {
                    return;
                }
                Log.i("ApplicationUsage", "handleMessage : MSG_UPDATE_USAGE_DB");
                ApplicationUsage.this._insertToAppControlDB();
            }
        }
    }

    public class AppForeGroundUsage {
        public long appLastLaunchTime;
        public long appLastPausetime;
        public int appLaunchCount;

        public AppForeGroundUsage() {
            this.appLaunchCount = 0;
            this.appLastLaunchTime = 0L;
            this.appLastPausetime = 0L;
        }
    }

    public class AppBackGroundUsage {
        public long appLastServiceStartTime;
        public long appLastServiceStopTime;

        public AppBackGroundUsage() {
            this.appLastServiceStartTime = 0L;
            this.appLastServiceStopTime = 0L;
        }
    }

    public void appLaunchCount(String str, int i) {
        mUsageHandler.obtainMessage(1, str + XmlUtils.STRING_ARRAY_SEPARATOR + i).sendToTarget();
    }

    public void _appLaunchCount(String str) {
        synchronized (mStatsLock) {
            Map map = appForeGroundStats;
            if (map == null) {
                return;
            }
            if (map.containsKey(str)) {
                AppForeGroundUsage appForeGroundUsage = (AppForeGroundUsage) appForeGroundStats.get(str);
                appForeGroundUsage.appLaunchCount++;
                appForeGroundUsage.appLastLaunchTime = System.currentTimeMillis();
                appForeGroundUsage.appLastPausetime = 0L;
            } else {
                AppForeGroundUsage appForeGroundUsage2 = new AppForeGroundUsage();
                appForeGroundUsage2.appLaunchCount++;
                appForeGroundUsage2.appLastLaunchTime = System.currentTimeMillis();
                appForeGroundStats.put(str, appForeGroundUsage2);
            }
        }
    }

    public void appPauseTime(String str, int i) {
        mUsageHandler.obtainMessage(2, str + XmlUtils.STRING_ARRAY_SEPARATOR + i).sendToTarget();
    }

    public void _appPauseTime(String str) {
        synchronized (mStatsLock) {
            if (appForeGroundStats.containsKey(str)) {
                ((AppForeGroundUsage) appForeGroundStats.get(str)).appLastPausetime = System.currentTimeMillis();
            }
        }
    }

    public void _appServiceStartTime(ActivityManager.RunningServiceInfo runningServiceInfo) {
        if (runningServiceInfo == null || appBackGroundStats == null) {
            return;
        }
        if ((runningServiceInfo.started || runningServiceInfo.clientLabel != 0) && (runningServiceInfo.flags & 8) == 0) {
            String str = runningServiceInfo.service.getPackageName() + XmlUtils.STRING_ARRAY_SEPARATOR + UserHandle.getUserId(runningServiceInfo.uid);
            synchronized (mStatsLock) {
                if (appBackGroundStats.containsKey(str)) {
                    AppBackGroundUsage appBackGroundUsage = (AppBackGroundUsage) appBackGroundStats.get(str);
                    if (appBackGroundUsage.appLastServiceStartTime == 0) {
                        appBackGroundUsage.appLastServiceStartTime = System.currentTimeMillis();
                    }
                    appBackGroundUsage.appLastServiceStopTime = 0L;
                } else {
                    AppBackGroundUsage appBackGroundUsage2 = new AppBackGroundUsage();
                    appBackGroundUsage2.appLastServiceStartTime = System.currentTimeMillis();
                    appBackGroundStats.put(str, appBackGroundUsage2);
                }
            }
        }
    }

    public void _appServiceStopTime(String str) {
        synchronized (mStatsLock) {
            Map map = appBackGroundStats;
            if (map == null) {
                return;
            }
            if (map.containsKey(str)) {
                ((AppBackGroundUsage) appBackGroundStats.get(str)).appLastServiceStopTime = System.currentTimeMillis();
            }
        }
    }

    public void _insertToAppControlDB() {
        Log.i("ApplicationUsage", "Updating Usage Statistics in DB @ " + System.currentTimeMillis());
        try {
            synchronized (mStatsLock) {
                updateForeGroundUsageData();
                updateBackGroundUsageDetails();
                updateNetworkUsage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("ApplicationUsage", "Done Updating Usage Statistics in DB @ " + System.currentTimeMillis());
    }

    public final void updateNetworkUsage() {
        ((ApplicationPolicy) EnterpriseService.getPolicyService("application_policy")).updateDataUsageDb();
    }

    public final void updateForeGroundUsageData() {
        ApplicationUsageDb applicationUsageDb = new ApplicationUsageDb(this.mContext);
        Map map = appForeGroundStats;
        if (map == null || map.isEmpty()) {
            return;
        }
        Set<String> keySet = appForeGroundStats.keySet();
        ArrayList arrayList = new ArrayList();
        for (String str : keySet) {
            AppForeGroundUsage appForeGroundUsage = (AppForeGroundUsage) appForeGroundStats.get(str);
            long j = appForeGroundUsage.appLastPausetime;
            if (j != 0) {
                applicationUsageDb.updateForeGroundUsageDetails(str, appForeGroundUsage.appLaunchCount, appForeGroundUsage.appLastLaunchTime, j);
                arrayList.add(str);
            } else {
                applicationUsageDb.updateForeGroundUsageDetails(str, appForeGroundUsage.appLaunchCount, appForeGroundUsage.appLastLaunchTime, System.currentTimeMillis());
            }
        }
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                appForeGroundStats.remove(arrayList.get(i));
            }
        }
    }

    public final void updateBackGroundUsageDetails() {
        ApplicationUsageDb applicationUsageDb = new ApplicationUsageDb(this.mContext);
        Map map = appBackGroundStats;
        if (map == null || map.isEmpty()) {
            return;
        }
        Set<String> keySet = appBackGroundStats.keySet();
        ArrayList arrayList = new ArrayList();
        for (String str : keySet) {
            AppBackGroundUsage appBackGroundUsage = (AppBackGroundUsage) appBackGroundStats.get(str);
            long j = appBackGroundUsage.appLastServiceStopTime;
            if (j != 0) {
                applicationUsageDb.updateBackGroundUsageDetails(str, appBackGroundUsage.appLastServiceStartTime, j);
                arrayList.add(str);
            } else {
                applicationUsageDb.updateBackGroundUsageDetails(str, appBackGroundUsage.appLastServiceStartTime, System.currentTimeMillis());
            }
        }
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                appBackGroundStats.remove(arrayList.get(i));
            }
        }
    }

    public AppInfoLastUsage[] getAllAppLastUsage(int i) {
        AppInfoLastUsage[] appInfoLastUsageArr;
        String[] strArr;
        _insertToAppControlDB();
        try {
            HashMap appUsageData = new ApplicationUsageDb(this.mContext).getAppUsageData();
            int i2 = 0;
            if (appUsageData == null || appUsageData.isEmpty()) {
                appInfoLastUsageArr = null;
            } else {
                Set<String> keySet = appUsageData.keySet();
                appInfoLastUsageArr = new AppInfoLastUsage[appUsageData.size()];
                int i3 = 0;
                int i4 = 0;
                for (String str : keySet) {
                    if (str.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                        String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                        strArr = split;
                        i4 = Integer.parseInt(split[1]);
                    } else {
                        strArr = new String[]{str};
                    }
                    if (i4 == i) {
                        AppInfoLastUsage appInfoLastUsage = (AppInfoLastUsage) appUsageData.get(str);
                        appInfoLastUsageArr[i3] = appInfoLastUsage;
                        appInfoLastUsage.packageName = strArr[0];
                        i3++;
                    }
                }
                i2 = i3;
            }
            if (appInfoLastUsageArr != null) {
                return filterUnInstalledApps(appInfoLastUsageArr, i2, i);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AppInfoLastUsage[] getAvgNoAppUsagePerMonth(int i) {
        int i2;
        AppInfoLastUsage[] appInfoLastUsageArr;
        int i3;
        String[] strArr;
        String[] strArr2;
        _insertToAppControlDB();
        try {
            synchronized (mStatsLock) {
                Log.e("getAvgNoUsagePerMonthOfApp==================", "called");
                HashMap hashMap = new HashMap();
                Set<String> keySet = appForeGroundStats.keySet();
                if (keySet.size() != 0) {
                    for (String str : keySet) {
                        int i4 = ((AppForeGroundUsage) appForeGroundStats.get(str)).appLaunchCount;
                        if (i4 != 0) {
                            hashMap.put(str, Integer.valueOf(calculateAvgPerMonth(i4, str, i)));
                        }
                    }
                }
                HashMap launchCountOfAllApplication = new ApplicationUsageDb(this.mContext).getLaunchCountOfAllApplication();
                i2 = 0;
                if (!hashMap.isEmpty()) {
                    if (launchCountOfAllApplication != null && !launchCountOfAllApplication.isEmpty()) {
                        for (String str2 : launchCountOfAllApplication.keySet()) {
                            if (hashMap.containsKey(str2)) {
                                int intValue = ((Integer) hashMap.get(str2)).intValue();
                                hashMap.remove(str2);
                                int intValue2 = intValue + ((Integer) launchCountOfAllApplication.get(str2)).intValue();
                                if (intValue2 != 0) {
                                    hashMap.put(str2, Integer.valueOf(intValue2));
                                }
                            } else if (((Integer) launchCountOfAllApplication.get(str2)).intValue() != 0) {
                                hashMap.put(str2, (Integer) launchCountOfAllApplication.get(str2));
                            }
                        }
                    }
                    Set<String> keySet2 = hashMap.keySet();
                    appInfoLastUsageArr = new AppInfoLastUsage[hashMap.size()];
                    i3 = 0;
                    int i5 = 0;
                    for (String str3 : keySet2) {
                        if (str3.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                            String[] split = str3.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                            strArr2 = split;
                            i5 = Integer.parseInt(split[1]);
                        } else {
                            strArr2 = new String[]{str3};
                        }
                        if (i5 == i) {
                            int intValue3 = ((Integer) hashMap.get(str3)).intValue();
                            AppInfoLastUsage appInfoLastUsage = new AppInfoLastUsage();
                            appInfoLastUsageArr[i3] = appInfoLastUsage;
                            appInfoLastUsage.packageName = strArr2[0];
                            appInfoLastUsage.launchCountPerMonth = intValue3;
                            i3++;
                        }
                    }
                } else if (launchCountOfAllApplication == null || launchCountOfAllApplication.isEmpty()) {
                    appInfoLastUsageArr = null;
                } else {
                    Set<String> keySet3 = launchCountOfAllApplication.keySet();
                    AppInfoLastUsage[] appInfoLastUsageArr2 = new AppInfoLastUsage[hashMap.size()];
                    i3 = 0;
                    int i6 = 0;
                    for (String str4 : keySet3) {
                        if (str4.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                            String[] split2 = str4.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                            strArr = split2;
                            i6 = Integer.parseInt(split2[1]);
                        } else {
                            strArr = new String[]{str4};
                        }
                        if (i6 == i) {
                            int intValue4 = ((Integer) launchCountOfAllApplication.get(str4)).intValue();
                            AppInfoLastUsage appInfoLastUsage2 = new AppInfoLastUsage();
                            appInfoLastUsageArr2[i3] = appInfoLastUsage2;
                            appInfoLastUsage2.packageName = strArr[0];
                            appInfoLastUsage2.launchCountPerMonth = intValue4;
                            i3++;
                        }
                    }
                    appInfoLastUsageArr = appInfoLastUsageArr2;
                }
                i2 = i3;
            }
            return filterUnInstalledApps(appInfoLastUsageArr, i2, i);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ApplicationUsage::getAvgNoAppUsagePerMonth", e.getMessage());
            return null;
        }
    }

    public final long getAppInstallTimeInMiliSec(String str, int i) {
        PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ApplicationInfo applicationInfo = packageManagerAdapter.getApplicationInfo(str, 128, i);
            if (applicationInfo == null) {
                Log.i("ApplicationUsage", "ApplicationUsage::getAppInstallTimeInMiliSec @ fail to get application info");
                return -1L;
            }
            return new File(applicationInfo.sourceDir).lastModified();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getMonth(long j) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        calendar.setTimeInMillis(j);
        return calendar.get(2) + 0 + 1;
    }

    public final int getYear(long j) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        calendar.setTimeInMillis(j);
        return calendar.get(1);
    }

    public final long getFatatoryDataResetTime() {
        File file = new File("/efs/sec_efs/LastResetDate.txt");
        String str = "factorydatareset";
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            try {
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                        while (true) {
                            try {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                str = readLine;
                            } catch (Exception e) {
                                e = e;
                                bufferedReader = bufferedReader2;
                                e.printStackTrace();
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(str).getTime();
                                return 0L;
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                throw th;
                            }
                        }
                        bufferedReader2.close();
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException unused2) {
            }
        }
        try {
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(str).getTime();
            return 0L;
        } catch (ParseException e3) {
            e3.printStackTrace();
            return 0L;
        }
    }

    public final int calculateAvgPerMonth(int i, String str, int i2) {
        int i3;
        int i4;
        if (i == 0) {
            return 0;
        }
        if (str.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
            str = str.split(XmlUtils.STRING_ARRAY_SEPARATOR)[0];
        }
        long appInstallTimeInMiliSec = getAppInstallTimeInMiliSec(str, i2);
        long fatatoryDataResetTime = getFatatoryDataResetTime();
        if (appInstallTimeInMiliSec < fatatoryDataResetTime) {
            appInstallTimeInMiliSec = fatatoryDataResetTime;
        }
        if (-1 != appInstallTimeInMiliSec) {
            i4 = getMonth(appInstallTimeInMiliSec);
            i3 = getYear(appInstallTimeInMiliSec);
        } else {
            i3 = 0;
            i4 = 0;
        }
        int year = getYear(System.currentTimeMillis());
        int month = getMonth(System.currentTimeMillis());
        if (i4 != 0 && i3 != 0) {
            r0 = year > i3 ? 0 + ((year - i3) * 12) : 0;
            if (month > i4) {
                r0 += month - i4;
            }
        }
        return r0 != 0 ? i / r0 : i;
    }

    public final AppInfoLastUsage[] filterUnInstalledApps(AppInfoLastUsage[] appInfoLastUsageArr, int i, int i2) {
        int i3;
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (appInfoLastUsageArr != null) {
            i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                if (applicationPolicy.isApplicationInstalled(appInfoLastUsageArr[i4].packageName, i2)) {
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
            if (applicationPolicy.isApplicationInstalled(appInfoLastUsageArr[i6].packageName, i2)) {
                appInfoLastUsageArr2[i5] = appInfoLastUsageArr[i6];
                i5++;
            }
        }
        return appInfoLastUsageArr2;
    }
}
