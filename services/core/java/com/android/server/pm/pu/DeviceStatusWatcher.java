package com.android.server.pm.pu;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.dex.ArtManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.Handler;
import android.os.IThermalService;
import android.os.IThermalStatusListener;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.art.ArtManagerLocal;
import com.android.server.art.model.DexoptResult;
import com.android.server.pm.DexOptHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.BiConsumer;

public final class DeviceStatusWatcher {
    public Handler mHandler;
    public ServiceThread mHandlerThread;
    public boolean mScreenOff;
    public final ProfileUtilizationService mService;
    public IThermalService mThermalService;
    public final ReceiverController mController = new ReceiverController();
    public boolean mBatteryLow = true;
    public int mThermalStatus = 0;
    public int mInitialThermalStatus = 0;
    public final AnonymousClass1 mThermalListener =
            new IThermalStatusListener
                    .Stub() { // from class: com.android.server.pm.pu.DeviceStatusWatcher.1
                public final void onStatusChange(int i) {
                    synchronized (DeviceStatusWatcher.this) {
                        try {
                            DeviceStatusWatcher deviceStatusWatcher = DeviceStatusWatcher.this;
                            deviceStatusWatcher.mThermalStatus = i;
                            if (deviceStatusWatcher.isDexoptingAllowed()) {
                                DeviceStatusWatcher.this.mService.mTrigger.resumeOptimizing();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
    public final AnonymousClass2 mDexoptDoneHandler =
            new ArtManagerLocal
                    .DexoptDoneCallback() { // from class:
                                            // com.android.server.pm.pu.DeviceStatusWatcher.2
                public final void onDexoptDone(DexoptResult dexoptResult) {
                    float f;
                    if (dexoptResult.getReason().equals("bg-dexopt")) {
                        DeviceStatusWatcher deviceStatusWatcher = DeviceStatusWatcher.this;
                        deviceStatusWatcher.getClass();
                        Slog.d("PU_StatusWatcher", "Calculate metrics");
                        ProfileUtilizationService profileUtilizationService =
                                deviceStatusWatcher.mService;
                        Map<String, UsageStats> queryAndAggregateUsageStats =
                                ((UsageStatsManager)
                                                profileUtilizationService.mContext.getSystemService(
                                                        "usagestats"))
                                        .queryAndAggregateUsageStats(
                                                profileUtilizationService.mStartTimeMs,
                                                System.currentTimeMillis());
                        HashSet hashSet = new HashSet();
                        Slog.d("PU_StatusWatcher", "Launched apps:");
                        for (UsageStats usageStats : queryAndAggregateUsageStats.values()) {
                            if (usageStats.mLaunchCount != 0) {
                                hashSet.add(usageStats.getPackageName());
                                StringBuilder sb = new StringBuilder();
                                sb.append(usageStats.getPackageName());
                                sb.append(", count: ");
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        sb, usageStats.mLaunchCount, "PU_StatusWatcher");
                            }
                        }
                        int size = hashSet.size();
                        int size2 = profileUtilizationService.mWrapper.mApps.size();
                        Slog.d("PU_StatusWatcher", "Hot app list:");
                        Enumeration enumeration =
                                Collections.enumeration(profileUtilizationService.mWrapper.mApps);
                        float f2 = FullScreenMagnificationGestureHandler.MAX_SCALE;
                        float f3 = 0.0f;
                        int i = 0;
                        int i2 = 0;
                        int i3 = 0;
                        float f4 = 0.0f;
                        while (enumeration.hasMoreElements()) {
                            i3++;
                            ProfileUtilizationService.App app =
                                    (ProfileUtilizationService.App) enumeration.nextElement();
                            Enumeration enumeration2 = enumeration;
                            boolean contains = hashSet.contains(app.packageName);
                            HashSet hashSet2 = hashSet;
                            String str = app.packageName;
                            if (contains) {
                                i++;
                                Slog.d(
                                        "PU_StatusWatcher",
                                        i3 + ") " + str + " - predicted and launched");
                            } else {
                                Slog.d("PU_StatusWatcher", i3 + ") " + str + " - predicted");
                            }
                            float f5 = i;
                            float f6 = f5 / i3;
                            float f7 = f5 / size;
                            float f8 = ((2.0f * f6) * f7) / (f6 + f7);
                            if (f8 > f2) {
                                f3 = f7;
                                f4 = f6;
                                i2 = i3;
                                f = f8;
                            } else {
                                f = f2;
                            }
                            Slog.d(
                                    "PU_StatusWatcher",
                                    "Current f1Score: "
                                            + f8
                                            + ", prefixCount: "
                                            + i3
                                            + ", precision: "
                                            + f6
                                            + ", recall: "
                                            + f7);
                            enumeration = enumeration2;
                            hashSet = hashSet2;
                            f2 = f;
                            f4 = f4;
                        }
                        StringBuilder m =
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        i,
                                        size2,
                                        "Predicted and launched apps: ",
                                        "\nPredicted apps: ",
                                        "\nLaunched apps: ");
                        m.append(size);
                        m.append("\nBest f1Score: ");
                        m.append(f2);
                        m.append(", prefixCount: ");
                        m.append(i2);
                        m.append(", precision: ");
                        m.append(f4);
                        m.append(", recall: ");
                        m.append(f3);
                        Slog.i("PU_StatusWatcher", m.toString());
                        String sb2 = m.toString();
                        SharedPreferences.Editor edit =
                                profileUtilizationService.mStorage.mSharedPrefsDumps.edit();
                        edit.putString("Metrics", sb2);
                        edit.apply();
                        DeviceStatusWatcher deviceStatusWatcher2 = DeviceStatusWatcher.this;
                        deviceStatusWatcher2.getClass();
                        DexOptHelper.getArtManagerLocal()
                                .removeDexoptDoneCallback(deviceStatusWatcher2.mDexoptDoneHandler);
                    }
                }
            };

    public final class ReceiverController {
        public final IntentFilter mShutdownFilter =
                new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        public final AnonymousClass1 mShutdownReceiver;
        public final IntentFilter mStatusFilter;
        public final AnonymousClass1 mStatusReceiver;

        /* JADX WARN: Type inference failed for: r4v2, types: [com.android.server.pm.pu.DeviceStatusWatcher$ReceiverController$1] */
        /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.pm.pu.DeviceStatusWatcher$ReceiverController$1] */
        public ReceiverController() {
            final int i = 0;
            this.mShutdownReceiver =
                    new BroadcastReceiver(this) { // from class:
                        // com.android.server.pm.pu.DeviceStatusWatcher.ReceiverController.1
                        public final /* synthetic */ ReceiverController this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            switch (i) {
                                case 0:
                                    if ("android.intent.action.ACTION_SHUTDOWN"
                                            .equals(intent.getAction())) {
                                        ProfileUtilizationService profileUtilizationService =
                                                DeviceStatusWatcher.this.mService;
                                        profileUtilizationService.getClass();
                                        HotAppGenerator hotAppGenerator =
                                                new HotAppGenerator(
                                                        profileUtilizationService.mContext);
                                        long currentTimeMillis = System.currentTimeMillis();
                                        HashSet hashSet = new HashSet();
                                        List<PackageInfo> installedPackages =
                                                hotAppGenerator.mPm.getInstalledPackages(0);
                                        Map appStandbyBuckets =
                                                hotAppGenerator.mUsageStatsManager
                                                        .getAppStandbyBuckets();
                                        Iterator<PackageInfo> it = installedPackages.iterator();
                                        while (it.hasNext()) {
                                            String str = it.next().packageName;
                                            Integer num = (Integer) appStandbyBuckets.get(str);
                                            if (num != null
                                                    && (num.intValue() == 10
                                                            || num.intValue() == 5
                                                            || num.intValue() == 20)) {
                                                Intent intent2 =
                                                        new Intent("android.intent.action.MAIN");
                                                intent2.addCategory(
                                                        "android.intent.category.LAUNCHER");
                                                intent2.setPackage(str);
                                                List queryIntentActivitiesAsUser =
                                                        hotAppGenerator.mPm
                                                                .queryIntentActivitiesAsUser(
                                                                        intent2, 0, 0);
                                                if (queryIntentActivitiesAsUser != null
                                                        && queryIntentActivitiesAsUser.size() > 0) {
                                                    if (new File(
                                                                            Environment
                                                                                            .getDataRefProfilesDePackageDirectory(
                                                                                                    str)
                                                                                    + File.separator
                                                                                    + ArtManager
                                                                                            .getProfileName(
                                                                                                    (String)
                                                                                                            null))
                                                                    .length()
                                                            > 0) {
                                                        hashSet.add(str);
                                                    }
                                                }
                                            }
                                        }
                                        final HashMap hashMap = new HashMap();
                                        final HashMap hashMap2 = new HashMap();
                                        final int i2 = 0;
                                        hotAppGenerator.queryAndParseUsageStats(
                                                currentTimeMillis - HotAppGenerator.MONTH_INTERVAL,
                                                currentTimeMillis,
                                                hashSet,
                                                new BiConsumer() { // from class:
                                                    // com.android.server.pm.pu.HotAppGenerator$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.BiConsumer
                                                    public final void accept(
                                                            Object obj, Object obj2) {
                                                        switch (i2) {
                                                            case 0:
                                                                hashMap.putAll((Map) obj);
                                                                hashMap2.putAll((Map) obj2);
                                                                break;
                                                            default:
                                                                hashMap.putAll((Map) obj);
                                                                hashMap2.putAll((Map) obj2);
                                                                break;
                                                        }
                                                    }
                                                });
                                        final HashMap hashMap3 = new HashMap();
                                        final HashMap hashMap4 = new HashMap();
                                        long j = currentTimeMillis - HotAppGenerator.WEEK_INTERVAL;
                                        final int i3 = 1;
                                        hotAppGenerator.queryAndParseUsageStats(
                                                j,
                                                j + HotAppGenerator.DAY_INTERVAL,
                                                hashSet,
                                                new BiConsumer() { // from class:
                                                    // com.android.server.pm.pu.HotAppGenerator$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.BiConsumer
                                                    public final void accept(
                                                            Object obj, Object obj2) {
                                                        switch (i3) {
                                                            case 0:
                                                                hashMap3.putAll((Map) obj);
                                                                hashMap4.putAll((Map) obj2);
                                                                break;
                                                            default:
                                                                hashMap3.putAll((Map) obj);
                                                                hashMap4.putAll((Map) obj2);
                                                                break;
                                                        }
                                                    }
                                                });
                                        long j2 = HotAppGenerator.HOUR_INTERVAL + j;
                                        HashMap hashMap5 = new HashMap();
                                        UsageEvents queryEventsForSelf =
                                                hotAppGenerator.mUsageStatsManager
                                                        .queryEventsForSelf(j, j2);
                                        if (queryEventsForSelf != null) {
                                            int i4 = 0;
                                            while (queryEventsForSelf.hasNextEvent()) {
                                                UsageEvents.Event event = new UsageEvents.Event();
                                                queryEventsForSelf.getNextEvent(event);
                                                if (event.getEventType() == 1) {
                                                    String packageName = event.getPackageName();
                                                    if (hashSet.contains(packageName)
                                                            && !hashMap5.containsKey(packageName)) {
                                                        i4++;
                                                        hashMap5.put(
                                                                packageName, Integer.valueOf(i4));
                                                    }
                                                }
                                            }
                                        }
                                        List<ActivityManager.RecentTaskInfo> recentTasks =
                                                ((ActivityManager)
                                                                hotAppGenerator.mContext
                                                                        .getSystemService(
                                                                                "activity"))
                                                        .getRecentTasks(Integer.MAX_VALUE, 1);
                                        HashMap hashMap6 =
                                                new HashMap(
                                                        (int)
                                                                Math.ceil(
                                                                        recentTasks.size()
                                                                                / 0.75d));
                                        int i5 = 0;
                                        while (i5 < recentTasks.size()) {
                                            ActivityManager.RecentTaskInfo recentTaskInfo =
                                                    recentTasks.get(i5);
                                            String str2 = recentTaskInfo.baseIntent.getPackage();
                                            if (str2 == null
                                                    && recentTaskInfo.baseIntent.getComponent()
                                                            != null) {
                                                str2 =
                                                        recentTaskInfo
                                                                .baseIntent
                                                                .getComponent()
                                                                .getPackageName();
                                            }
                                            if (str2 == null || hashMap6.containsKey(str2)) {
                                                str2 =
                                                        VibrationParam$1$$ExternalSyntheticOutline0
                                                                .m(i5, "null");
                                            }
                                            i5++;
                                            hashMap6.put(str2, Integer.valueOf(i5));
                                        }
                                        TreeSet treeSet = new TreeSet();
                                        int orElse =
                                                Arrays.stream(
                                                                        new int[] {
                                                                            hashMap6.size(),
                                                                            hashMap.size(),
                                                                            hashMap2.size(),
                                                                            hashMap3.size(),
                                                                            hashMap4.size(),
                                                                            hashMap5.size()
                                                                        })
                                                                .max()
                                                                .orElse(0)
                                                        + 1;
                                        Iterator it2 = hashSet.iterator();
                                        while (it2.hasNext()) {
                                            treeSet.add(
                                                    new HotAppGenerator.ComparablePackage(
                                                            ((Integer)
                                                                                    hashMap5
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap4
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap3
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap2
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap6
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue(),
                                                            (String) it2.next()));
                                        }
                                        Slog.i("PU_Generator", "Generated apps:");
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it3 = treeSet.iterator();
                                        while (it3.hasNext()) {
                                            HotAppGenerator.ComparablePackage comparablePackage =
                                                    (HotAppGenerator.ComparablePackage) it3.next();
                                            Slog.d(
                                                    "PU_Generator",
                                                    comparablePackage.name
                                                            + "\t"
                                                            + comparablePackage.weight);
                                            arrayList.add(
                                                    new ProfileUtilizationService.App(
                                                            comparablePackage.weight,
                                                            comparablePackage.name));
                                        }
                                        List<ProfileUtilizationService.App> subList =
                                                arrayList.subList(
                                                        0, Math.min(17, arrayList.size()));
                                        SharedPreferences.Editor edit =
                                                profileUtilizationService.mStorage.mSharedPrefs
                                                        .edit();
                                        edit.clear();
                                        try {
                                            JSONObject jSONObject = new JSONObject();
                                            for (ProfileUtilizationService.App app : subList) {
                                                jSONObject.put(app.packageName, app.weight);
                                            }
                                            Slog.d("PU_Storage", "Store " + jSONObject);
                                            edit.putString(
                                                    "json_apps_weights", jSONObject.toString());
                                        } catch (JSONException e) {
                                            Slog.e("PU_Storage", "Failed to store apps", e);
                                        }
                                        edit.commit();
                                        return;
                                    }
                                    return;
                                default:
                                    String action = intent.getAction();
                                    Slog.d("PU_StatusWatcher", "Received " + action);
                                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher) {
                                            deviceStatusWatcher.mScreenOff = true;
                                            if (deviceStatusWatcher.isDexoptingAllowed()) {
                                                deviceStatusWatcher.mService.mTrigger
                                                        .resumeOptimizing();
                                            }
                                        }
                                        return;
                                    }
                                    if ("android.intent.action.SCREEN_ON".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher2 =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher2) {
                                            deviceStatusWatcher2.mScreenOff = false;
                                            deviceStatusWatcher2.mService.mTrigger
                                                    .pauseOptimizing();
                                        }
                                        return;
                                    }
                                    if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher3 =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher3) {
                                            deviceStatusWatcher3.mScreenOff = false;
                                            deviceStatusWatcher3.mService.mTrigger
                                                    .pauseOptimizing();
                                        }
                                        return;
                                    }
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if ("android.intent.action.BATTERY_CHANGED"
                                                .equals(action)) {
                                            DeviceStatusWatcher deviceStatusWatcher4 =
                                                    DeviceStatusWatcher.this;
                                            deviceStatusWatcher4.getClass();
                                            boolean booleanExtra =
                                                    intent.getBooleanExtra("battery_low", true);
                                            if (booleanExtra == deviceStatusWatcher4.mBatteryLow) {
                                                return;
                                            }
                                            synchronized (deviceStatusWatcher4) {
                                                try {
                                                    deviceStatusWatcher4.mBatteryLow = booleanExtra;
                                                    if (deviceStatusWatcher4.isDexoptingAllowed()) {
                                                        deviceStatusWatcher4.mService.mTrigger
                                                                .resumeOptimizing();
                                                    }
                                                } finally {
                                                }
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    DeviceStatusWatcher deviceStatusWatcher5 =
                                            DeviceStatusWatcher.this;
                                    deviceStatusWatcher5.getClass();
                                    IThermalService asInterface =
                                            IThermalService.Stub.asInterface(
                                                    ServiceManager.getService("thermalservice"));
                                    deviceStatusWatcher5.mThermalService = asInterface;
                                    try {
                                        deviceStatusWatcher5.mThermalStatus =
                                                asInterface.getCurrentThermalStatus();
                                    } catch (RemoteException e2) {
                                        Slog.e(
                                                "PU_StatusWatcher",
                                                "Failed to get thermal status",
                                                e2);
                                        deviceStatusWatcher5.mThermalStatus = 1;
                                    }
                                    deviceStatusWatcher5.mInitialThermalStatus =
                                            deviceStatusWatcher5.mThermalStatus;
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Initial thermal status "),
                                            deviceStatusWatcher5.mInitialThermalStatus,
                                            "PU_StatusWatcher");
                                    try {
                                        deviceStatusWatcher5.mThermalService
                                                .registerThermalStatusListener(
                                                        deviceStatusWatcher5.mThermalListener);
                                    } catch (RemoteException e3) {
                                        Slog.e(
                                                "PU_StatusWatcher",
                                                "Thermal registration failed",
                                                e3);
                                    }
                                    final ProfileUtilizationService profileUtilizationService2 =
                                            DeviceStatusWatcher.this.mService;
                                    profileUtilizationService2.getClass();
                                    profileUtilizationService2.mStartTimeMs =
                                            System.currentTimeMillis();
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.pm.pu.ProfileUtilizationService$$ExternalSyntheticLambda0
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ProfileUtilizationService
                                                                    profileUtilizationService3 =
                                                                            ProfileUtilizationService
                                                                                    .this;
                                                            profileUtilizationService3.getClass();
                                                            Slog.i(
                                                                    ProfileUtilizationService
                                                                            .TAG_PU,
                                                                    "Start utilizing profiles");
                                                            Thread.currentThread()
                                                                    .setName("PU_DexoptTrigger");
                                                            HotAppsWrapper hotAppsWrapper =
                                                                    new HotAppsWrapper(
                                                                            profileUtilizationService3
                                                                                    .mStorage
                                                                                    .loadAppsList());
                                                            profileUtilizationService3.mWrapper =
                                                                    hotAppsWrapper;
                                                            DexoptTrigger dexoptTrigger =
                                                                    profileUtilizationService3
                                                                            .mTrigger;
                                                            dexoptTrigger.mWrapper = hotAppsWrapper;
                                                            Slog.i(
                                                                    "PU_DexoptTrigger",
                                                                    "Got "
                                                                            + dexoptTrigger.mWrapper
                                                                                    .mApps.size()
                                                                            + " apps, start"
                                                                            + " dexopting");
                                                            Enumeration enumeration =
                                                                    Collections.enumeration(
                                                                            dexoptTrigger
                                                                                    .mWrapper
                                                                                    .mApps);
                                                            while (enumeration.hasMoreElements()) {
                                                                synchronized (
                                                                        dexoptTrigger
                                                                                .mRunningApps) {
                                                                    while (((HashSet)
                                                                                            dexoptTrigger
                                                                                                    .mRunningApps)
                                                                                    .size()
                                                                            == DexoptTrigger
                                                                                    .DEXOPT_CONCURRENCY) {
                                                                        try {
                                                                            dexoptTrigger
                                                                                    .mRunningApps
                                                                                    .wait();
                                                                        } catch (
                                                                                InterruptedException
                                                                                        unused) {
                                                                        }
                                                                    }
                                                                }
                                                                synchronized (
                                                                        dexoptTrigger.mWatcher) {
                                                                    ProfileUtilizationService.App
                                                                            app2 =
                                                                                    (ProfileUtilizationService
                                                                                                    .App)
                                                                                            enumeration
                                                                                                    .nextElement();
                                                                    dexoptTrigger.mDexopting =
                                                                            dexoptTrigger.mWatcher
                                                                                    .isDexoptingAllowed();
                                                                    synchronized (
                                                                            dexoptTrigger
                                                                                    .mRunningApps) {
                                                                        ((HashSet)
                                                                                        dexoptTrigger
                                                                                                .mRunningApps)
                                                                                .add(app2);
                                                                        if (dexoptTrigger
                                                                                .mDexopting) {
                                                                            ProfileUtilizationService
                                                                                            .App
                                                                                            .State
                                                                                    state =
                                                                                            app2.mState;
                                                                            ProfileUtilizationService
                                                                                            .App
                                                                                            .State
                                                                                    state2 =
                                                                                            ProfileUtilizationService
                                                                                                    .App
                                                                                                    .State
                                                                                                    .OPTIMIZING;
                                                                            if (state != state2) {
                                                                                app2.mState =
                                                                                        state2;
                                                                                dexoptTrigger
                                                                                        .mExecutor
                                                                                        .execute(
                                                                                                new DexoptTrigger$$ExternalSyntheticLambda0(
                                                                                                        dexoptTrigger,
                                                                                                        app2));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            synchronized (
                                                                    dexoptTrigger.mRunningApps) {
                                                                while (!((HashSet)
                                                                                dexoptTrigger
                                                                                        .mRunningApps)
                                                                        .isEmpty()) {
                                                                    try {
                                                                        dexoptTrigger.mRunningApps
                                                                                .wait();
                                                                    } catch (
                                                                            InterruptedException
                                                                                    unused2) {
                                                                    }
                                                                }
                                                                dexoptTrigger.mDexopting = false;
                                                            }
                                                            dexoptTrigger.mExecutor.shutdown();
                                                            Slog.i(
                                                                    ProfileUtilizationService
                                                                            .TAG_PU,
                                                                    "Finish utilizing profiles");
                                                            profileUtilizationService3
                                                                            .mFinishTimeMs =
                                                                    System.currentTimeMillis();
                                                            DeviceStatusWatcher
                                                                    deviceStatusWatcher6 =
                                                                            profileUtilizationService3
                                                                                    .mWatcher;
                                                            DeviceStatusWatcher.ReceiverController
                                                                    receiverController =
                                                                            deviceStatusWatcher6
                                                                                    .mController;
                                                            DeviceStatusWatcher
                                                                    deviceStatusWatcher7 =
                                                                            DeviceStatusWatcher
                                                                                    .this;
                                                            deviceStatusWatcher7.mService.mContext
                                                                    .unregisterReceiver(
                                                                            receiverController
                                                                                    .mStatusReceiver);
                                                            IThermalService iThermalService =
                                                                    deviceStatusWatcher7
                                                                            .mThermalService;
                                                            if (iThermalService != null) {
                                                                try {
                                                                    iThermalService
                                                                            .unregisterThermalStatusListener(
                                                                                    deviceStatusWatcher7
                                                                                            .mThermalListener);
                                                                } catch (RemoteException e4) {
                                                                    Slog.e(
                                                                            "PU_StatusWatcher",
                                                                            "Thermal unregistration"
                                                                                + " failed",
                                                                            e4);
                                                                }
                                                            }
                                                            ProfileUtilizationStorage
                                                                    profileUtilizationStorage =
                                                                            profileUtilizationService3
                                                                                    .mStorage;
                                                            SharedPreferences.Editor edit2 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit2.clear();
                                                            edit2.apply();
                                                            String profileUtilizationService4 =
                                                                    profileUtilizationService3
                                                                            .toString();
                                                            SharedPreferences.Editor edit3 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit3.putString(
                                                                    "Service",
                                                                    profileUtilizationService4);
                                                            edit3.apply();
                                                            String deviceStatusWatcher8 =
                                                                    deviceStatusWatcher6.toString();
                                                            SharedPreferences.Editor edit4 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit4.putString(
                                                                    "Watcher",
                                                                    deviceStatusWatcher8);
                                                            edit4.apply();
                                                            String dexoptTrigger2 =
                                                                    profileUtilizationService3
                                                                            .mTrigger.toString();
                                                            SharedPreferences.Editor edit5 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit5.putString(
                                                                    "Trigger", dexoptTrigger2);
                                                            edit5.apply();
                                                        }
                                                    })
                                            .start();
                                    return;
                            }
                        }
                    };
            final int i2 = 1;
            this.mStatusReceiver =
                    new BroadcastReceiver(this) { // from class:
                        // com.android.server.pm.pu.DeviceStatusWatcher.ReceiverController.1
                        public final /* synthetic */ ReceiverController this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            switch (i2) {
                                case 0:
                                    if ("android.intent.action.ACTION_SHUTDOWN"
                                            .equals(intent.getAction())) {
                                        ProfileUtilizationService profileUtilizationService =
                                                DeviceStatusWatcher.this.mService;
                                        profileUtilizationService.getClass();
                                        HotAppGenerator hotAppGenerator =
                                                new HotAppGenerator(
                                                        profileUtilizationService.mContext);
                                        long currentTimeMillis = System.currentTimeMillis();
                                        HashSet hashSet = new HashSet();
                                        List<PackageInfo> installedPackages =
                                                hotAppGenerator.mPm.getInstalledPackages(0);
                                        Map appStandbyBuckets =
                                                hotAppGenerator.mUsageStatsManager
                                                        .getAppStandbyBuckets();
                                        Iterator<PackageInfo> it = installedPackages.iterator();
                                        while (it.hasNext()) {
                                            String str = it.next().packageName;
                                            Integer num = (Integer) appStandbyBuckets.get(str);
                                            if (num != null
                                                    && (num.intValue() == 10
                                                            || num.intValue() == 5
                                                            || num.intValue() == 20)) {
                                                Intent intent2 =
                                                        new Intent("android.intent.action.MAIN");
                                                intent2.addCategory(
                                                        "android.intent.category.LAUNCHER");
                                                intent2.setPackage(str);
                                                List queryIntentActivitiesAsUser =
                                                        hotAppGenerator.mPm
                                                                .queryIntentActivitiesAsUser(
                                                                        intent2, 0, 0);
                                                if (queryIntentActivitiesAsUser != null
                                                        && queryIntentActivitiesAsUser.size() > 0) {
                                                    if (new File(
                                                                            Environment
                                                                                            .getDataRefProfilesDePackageDirectory(
                                                                                                    str)
                                                                                    + File.separator
                                                                                    + ArtManager
                                                                                            .getProfileName(
                                                                                                    (String)
                                                                                                            null))
                                                                    .length()
                                                            > 0) {
                                                        hashSet.add(str);
                                                    }
                                                }
                                            }
                                        }
                                        final Map hashMap = new HashMap();
                                        final Map hashMap2 = new HashMap();
                                        final int i22 = 0;
                                        hotAppGenerator.queryAndParseUsageStats(
                                                currentTimeMillis - HotAppGenerator.MONTH_INTERVAL,
                                                currentTimeMillis,
                                                hashSet,
                                                new BiConsumer() { // from class:
                                                    // com.android.server.pm.pu.HotAppGenerator$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.BiConsumer
                                                    public final void accept(
                                                            Object obj, Object obj2) {
                                                        switch (i22) {
                                                            case 0:
                                                                hashMap.putAll((Map) obj);
                                                                hashMap2.putAll((Map) obj2);
                                                                break;
                                                            default:
                                                                hashMap.putAll((Map) obj);
                                                                hashMap2.putAll((Map) obj2);
                                                                break;
                                                        }
                                                    }
                                                });
                                        final Map hashMap3 = new HashMap();
                                        final Map hashMap4 = new HashMap();
                                        long j = currentTimeMillis - HotAppGenerator.WEEK_INTERVAL;
                                        final int i3 = 1;
                                        hotAppGenerator.queryAndParseUsageStats(
                                                j,
                                                j + HotAppGenerator.DAY_INTERVAL,
                                                hashSet,
                                                new BiConsumer() { // from class:
                                                    // com.android.server.pm.pu.HotAppGenerator$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.BiConsumer
                                                    public final void accept(
                                                            Object obj, Object obj2) {
                                                        switch (i3) {
                                                            case 0:
                                                                hashMap3.putAll((Map) obj);
                                                                hashMap4.putAll((Map) obj2);
                                                                break;
                                                            default:
                                                                hashMap3.putAll((Map) obj);
                                                                hashMap4.putAll((Map) obj2);
                                                                break;
                                                        }
                                                    }
                                                });
                                        long j2 = HotAppGenerator.HOUR_INTERVAL + j;
                                        HashMap hashMap5 = new HashMap();
                                        UsageEvents queryEventsForSelf =
                                                hotAppGenerator.mUsageStatsManager
                                                        .queryEventsForSelf(j, j2);
                                        if (queryEventsForSelf != null) {
                                            int i4 = 0;
                                            while (queryEventsForSelf.hasNextEvent()) {
                                                UsageEvents.Event event = new UsageEvents.Event();
                                                queryEventsForSelf.getNextEvent(event);
                                                if (event.getEventType() == 1) {
                                                    String packageName = event.getPackageName();
                                                    if (hashSet.contains(packageName)
                                                            && !hashMap5.containsKey(packageName)) {
                                                        i4++;
                                                        hashMap5.put(
                                                                packageName, Integer.valueOf(i4));
                                                    }
                                                }
                                            }
                                        }
                                        List<ActivityManager.RecentTaskInfo> recentTasks =
                                                ((ActivityManager)
                                                                hotAppGenerator.mContext
                                                                        .getSystemService(
                                                                                "activity"))
                                                        .getRecentTasks(Integer.MAX_VALUE, 1);
                                        HashMap hashMap6 =
                                                new HashMap(
                                                        (int)
                                                                Math.ceil(
                                                                        recentTasks.size()
                                                                                / 0.75d));
                                        int i5 = 0;
                                        while (i5 < recentTasks.size()) {
                                            ActivityManager.RecentTaskInfo recentTaskInfo =
                                                    recentTasks.get(i5);
                                            String str2 = recentTaskInfo.baseIntent.getPackage();
                                            if (str2 == null
                                                    && recentTaskInfo.baseIntent.getComponent()
                                                            != null) {
                                                str2 =
                                                        recentTaskInfo
                                                                .baseIntent
                                                                .getComponent()
                                                                .getPackageName();
                                            }
                                            if (str2 == null || hashMap6.containsKey(str2)) {
                                                str2 =
                                                        VibrationParam$1$$ExternalSyntheticOutline0
                                                                .m(i5, "null");
                                            }
                                            i5++;
                                            hashMap6.put(str2, Integer.valueOf(i5));
                                        }
                                        TreeSet treeSet = new TreeSet();
                                        int orElse =
                                                Arrays.stream(
                                                                        new int[] {
                                                                            hashMap6.size(),
                                                                            hashMap.size(),
                                                                            hashMap2.size(),
                                                                            hashMap3.size(),
                                                                            hashMap4.size(),
                                                                            hashMap5.size()
                                                                        })
                                                                .max()
                                                                .orElse(0)
                                                        + 1;
                                        Iterator it2 = hashSet.iterator();
                                        while (it2.hasNext()) {
                                            treeSet.add(
                                                    new HotAppGenerator.ComparablePackage(
                                                            ((Integer)
                                                                                    hashMap5
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap4
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap3
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap2
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue()
                                                                    + ((Integer)
                                                                                    hashMap6
                                                                                            .getOrDefault(
                                                                                                    r6,
                                                                                                    Integer
                                                                                                            .valueOf(
                                                                                                                    orElse)))
                                                                            .intValue(),
                                                            (String) it2.next()));
                                        }
                                        Slog.i("PU_Generator", "Generated apps:");
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it3 = treeSet.iterator();
                                        while (it3.hasNext()) {
                                            HotAppGenerator.ComparablePackage comparablePackage =
                                                    (HotAppGenerator.ComparablePackage) it3.next();
                                            Slog.d(
                                                    "PU_Generator",
                                                    comparablePackage.name
                                                            + "\t"
                                                            + comparablePackage.weight);
                                            arrayList.add(
                                                    new ProfileUtilizationService.App(
                                                            comparablePackage.weight,
                                                            comparablePackage.name));
                                        }
                                        List<ProfileUtilizationService.App> subList =
                                                arrayList.subList(
                                                        0, Math.min(17, arrayList.size()));
                                        SharedPreferences.Editor edit =
                                                profileUtilizationService.mStorage.mSharedPrefs
                                                        .edit();
                                        edit.clear();
                                        try {
                                            JSONObject jSONObject = new JSONObject();
                                            for (ProfileUtilizationService.App app : subList) {
                                                jSONObject.put(app.packageName, app.weight);
                                            }
                                            Slog.d("PU_Storage", "Store " + jSONObject);
                                            edit.putString(
                                                    "json_apps_weights", jSONObject.toString());
                                        } catch (JSONException e) {
                                            Slog.e("PU_Storage", "Failed to store apps", e);
                                        }
                                        edit.commit();
                                        return;
                                    }
                                    return;
                                default:
                                    String action = intent.getAction();
                                    Slog.d("PU_StatusWatcher", "Received " + action);
                                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher) {
                                            deviceStatusWatcher.mScreenOff = true;
                                            if (deviceStatusWatcher.isDexoptingAllowed()) {
                                                deviceStatusWatcher.mService.mTrigger
                                                        .resumeOptimizing();
                                            }
                                        }
                                        return;
                                    }
                                    if ("android.intent.action.SCREEN_ON".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher2 =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher2) {
                                            deviceStatusWatcher2.mScreenOff = false;
                                            deviceStatusWatcher2.mService.mTrigger
                                                    .pauseOptimizing();
                                        }
                                        return;
                                    }
                                    if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                                        DeviceStatusWatcher deviceStatusWatcher3 =
                                                DeviceStatusWatcher.this;
                                        synchronized (deviceStatusWatcher3) {
                                            deviceStatusWatcher3.mScreenOff = false;
                                            deviceStatusWatcher3.mService.mTrigger
                                                    .pauseOptimizing();
                                        }
                                        return;
                                    }
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if ("android.intent.action.BATTERY_CHANGED"
                                                .equals(action)) {
                                            DeviceStatusWatcher deviceStatusWatcher4 =
                                                    DeviceStatusWatcher.this;
                                            deviceStatusWatcher4.getClass();
                                            boolean booleanExtra =
                                                    intent.getBooleanExtra("battery_low", true);
                                            if (booleanExtra == deviceStatusWatcher4.mBatteryLow) {
                                                return;
                                            }
                                            synchronized (deviceStatusWatcher4) {
                                                try {
                                                    deviceStatusWatcher4.mBatteryLow = booleanExtra;
                                                    if (deviceStatusWatcher4.isDexoptingAllowed()) {
                                                        deviceStatusWatcher4.mService.mTrigger
                                                                .resumeOptimizing();
                                                    }
                                                } finally {
                                                }
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    DeviceStatusWatcher deviceStatusWatcher5 =
                                            DeviceStatusWatcher.this;
                                    deviceStatusWatcher5.getClass();
                                    IThermalService asInterface =
                                            IThermalService.Stub.asInterface(
                                                    ServiceManager.getService("thermalservice"));
                                    deviceStatusWatcher5.mThermalService = asInterface;
                                    try {
                                        deviceStatusWatcher5.mThermalStatus =
                                                asInterface.getCurrentThermalStatus();
                                    } catch (RemoteException e2) {
                                        Slog.e(
                                                "PU_StatusWatcher",
                                                "Failed to get thermal status",
                                                e2);
                                        deviceStatusWatcher5.mThermalStatus = 1;
                                    }
                                    deviceStatusWatcher5.mInitialThermalStatus =
                                            deviceStatusWatcher5.mThermalStatus;
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Initial thermal status "),
                                            deviceStatusWatcher5.mInitialThermalStatus,
                                            "PU_StatusWatcher");
                                    try {
                                        deviceStatusWatcher5.mThermalService
                                                .registerThermalStatusListener(
                                                        deviceStatusWatcher5.mThermalListener);
                                    } catch (RemoteException e3) {
                                        Slog.e(
                                                "PU_StatusWatcher",
                                                "Thermal registration failed",
                                                e3);
                                    }
                                    final ProfileUtilizationService profileUtilizationService2 =
                                            DeviceStatusWatcher.this.mService;
                                    profileUtilizationService2.getClass();
                                    profileUtilizationService2.mStartTimeMs =
                                            System.currentTimeMillis();
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.pm.pu.ProfileUtilizationService$$ExternalSyntheticLambda0
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ProfileUtilizationService
                                                                    profileUtilizationService3 =
                                                                            ProfileUtilizationService
                                                                                    .this;
                                                            profileUtilizationService3.getClass();
                                                            Slog.i(
                                                                    ProfileUtilizationService
                                                                            .TAG_PU,
                                                                    "Start utilizing profiles");
                                                            Thread.currentThread()
                                                                    .setName("PU_DexoptTrigger");
                                                            HotAppsWrapper hotAppsWrapper =
                                                                    new HotAppsWrapper(
                                                                            profileUtilizationService3
                                                                                    .mStorage
                                                                                    .loadAppsList());
                                                            profileUtilizationService3.mWrapper =
                                                                    hotAppsWrapper;
                                                            DexoptTrigger dexoptTrigger =
                                                                    profileUtilizationService3
                                                                            .mTrigger;
                                                            dexoptTrigger.mWrapper = hotAppsWrapper;
                                                            Slog.i(
                                                                    "PU_DexoptTrigger",
                                                                    "Got "
                                                                            + dexoptTrigger.mWrapper
                                                                                    .mApps.size()
                                                                            + " apps, start"
                                                                            + " dexopting");
                                                            Enumeration enumeration =
                                                                    Collections.enumeration(
                                                                            dexoptTrigger
                                                                                    .mWrapper
                                                                                    .mApps);
                                                            while (enumeration.hasMoreElements()) {
                                                                synchronized (
                                                                        dexoptTrigger
                                                                                .mRunningApps) {
                                                                    while (((HashSet)
                                                                                            dexoptTrigger
                                                                                                    .mRunningApps)
                                                                                    .size()
                                                                            == DexoptTrigger
                                                                                    .DEXOPT_CONCURRENCY) {
                                                                        try {
                                                                            dexoptTrigger
                                                                                    .mRunningApps
                                                                                    .wait();
                                                                        } catch (
                                                                                InterruptedException
                                                                                        unused) {
                                                                        }
                                                                    }
                                                                }
                                                                synchronized (
                                                                        dexoptTrigger.mWatcher) {
                                                                    ProfileUtilizationService.App
                                                                            app2 =
                                                                                    (ProfileUtilizationService
                                                                                                    .App)
                                                                                            enumeration
                                                                                                    .nextElement();
                                                                    dexoptTrigger.mDexopting =
                                                                            dexoptTrigger.mWatcher
                                                                                    .isDexoptingAllowed();
                                                                    synchronized (
                                                                            dexoptTrigger
                                                                                    .mRunningApps) {
                                                                        ((HashSet)
                                                                                        dexoptTrigger
                                                                                                .mRunningApps)
                                                                                .add(app2);
                                                                        if (dexoptTrigger
                                                                                .mDexopting) {
                                                                            ProfileUtilizationService
                                                                                            .App
                                                                                            .State
                                                                                    state =
                                                                                            app2.mState;
                                                                            ProfileUtilizationService
                                                                                            .App
                                                                                            .State
                                                                                    state2 =
                                                                                            ProfileUtilizationService
                                                                                                    .App
                                                                                                    .State
                                                                                                    .OPTIMIZING;
                                                                            if (state != state2) {
                                                                                app2.mState =
                                                                                        state2;
                                                                                dexoptTrigger
                                                                                        .mExecutor
                                                                                        .execute(
                                                                                                new DexoptTrigger$$ExternalSyntheticLambda0(
                                                                                                        dexoptTrigger,
                                                                                                        app2));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            synchronized (
                                                                    dexoptTrigger.mRunningApps) {
                                                                while (!((HashSet)
                                                                                dexoptTrigger
                                                                                        .mRunningApps)
                                                                        .isEmpty()) {
                                                                    try {
                                                                        dexoptTrigger.mRunningApps
                                                                                .wait();
                                                                    } catch (
                                                                            InterruptedException
                                                                                    unused2) {
                                                                    }
                                                                }
                                                                dexoptTrigger.mDexopting = false;
                                                            }
                                                            dexoptTrigger.mExecutor.shutdown();
                                                            Slog.i(
                                                                    ProfileUtilizationService
                                                                            .TAG_PU,
                                                                    "Finish utilizing profiles");
                                                            profileUtilizationService3
                                                                            .mFinishTimeMs =
                                                                    System.currentTimeMillis();
                                                            DeviceStatusWatcher
                                                                    deviceStatusWatcher6 =
                                                                            profileUtilizationService3
                                                                                    .mWatcher;
                                                            DeviceStatusWatcher.ReceiverController
                                                                    receiverController =
                                                                            deviceStatusWatcher6
                                                                                    .mController;
                                                            DeviceStatusWatcher
                                                                    deviceStatusWatcher7 =
                                                                            DeviceStatusWatcher
                                                                                    .this;
                                                            deviceStatusWatcher7.mService.mContext
                                                                    .unregisterReceiver(
                                                                            receiverController
                                                                                    .mStatusReceiver);
                                                            IThermalService iThermalService =
                                                                    deviceStatusWatcher7
                                                                            .mThermalService;
                                                            if (iThermalService != null) {
                                                                try {
                                                                    iThermalService
                                                                            .unregisterThermalStatusListener(
                                                                                    deviceStatusWatcher7
                                                                                            .mThermalListener);
                                                                } catch (RemoteException e4) {
                                                                    Slog.e(
                                                                            "PU_StatusWatcher",
                                                                            "Thermal unregistration"
                                                                                + " failed",
                                                                            e4);
                                                                }
                                                            }
                                                            ProfileUtilizationStorage
                                                                    profileUtilizationStorage =
                                                                            profileUtilizationService3
                                                                                    .mStorage;
                                                            SharedPreferences.Editor edit2 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit2.clear();
                                                            edit2.apply();
                                                            String profileUtilizationService4 =
                                                                    profileUtilizationService3
                                                                            .toString();
                                                            SharedPreferences.Editor edit3 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit3.putString(
                                                                    "Service",
                                                                    profileUtilizationService4);
                                                            edit3.apply();
                                                            String deviceStatusWatcher8 =
                                                                    deviceStatusWatcher6.toString();
                                                            SharedPreferences.Editor edit4 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit4.putString(
                                                                    "Watcher",
                                                                    deviceStatusWatcher8);
                                                            edit4.apply();
                                                            String dexoptTrigger2 =
                                                                    profileUtilizationService3
                                                                            .mTrigger.toString();
                                                            SharedPreferences.Editor edit5 =
                                                                    profileUtilizationStorage
                                                                            .mSharedPrefsDumps
                                                                            .edit();
                                                            edit5.putString(
                                                                    "Trigger", dexoptTrigger2);
                                                            edit5.apply();
                                                        }
                                                    })
                                            .start();
                                    return;
                            }
                        }
                    };
            IntentFilter intentFilter = new IntentFilter();
            this.mStatusFilter = intentFilter;
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    intentFilter,
                    "android.intent.action.SCREEN_ON",
                    "android.intent.action.SCREEN_OFF",
                    "android.intent.action.LOCKED_BOOT_COMPLETED",
                    "android.intent.action.USER_UNLOCKED");
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.pm.pu.DeviceStatusWatcher$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.pm.pu.DeviceStatusWatcher$2] */
    public DeviceStatusWatcher(ProfileUtilizationService profileUtilizationService) {
        this.mService = profileUtilizationService;
    }

    public final synchronized boolean isDexoptingAllowed() {
        boolean z;
        if (this.mScreenOff && this.mThermalStatus == 0) {
            z = this.mBatteryLow ? false : true;
        }
        return z;
    }

    public final String toString() {
        return "Initial thermal status: "
                + this.mInitialThermalStatus
                + "\nThermal status: "
                + this.mThermalStatus
                + "\nScreen off: "
                + this.mScreenOff
                + "\nLow battery: "
                + this.mBatteryLow;
    }
}
