package com.android.server.chimera;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.IProcessObserver;
import android.app.IUidObserver;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Message;
import android.os.RemoteException;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityManager;

import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.LocalServices;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerProcLock;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda5;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.ProcessRecord;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class SystemRepository {
    public static final boolean IS_SHIP_BUILD =
            SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c");
    public final String[] fileCacheReclaimTarget;
    public final ActivityManager mActivityManager;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityManagerService mActivityManagerService;
    public AudioManager mAudioManager;
    public final ICollectionCache$CameraAppsCache mBigGameApps;
    public final ICollectionCache$CameraAppsCache mCameraApps;
    public final AnonymousClass3 mCameraDeviceStateCallback;
    public final List mChimeraProcessObservers;
    public final Context mContext;
    public IDeviceIdleController mDeviceIdleController;
    public DevicePolicyManager mDevicePolicyManager;
    public ForegroundActivityManager mFGActivityManager;
    public final ICollectionCache$GameAppsCache mGameApps;
    public Pair mLastForegroundApp;
    public final PackageManager mPackageManager;
    public final AnonymousClass2 mProcessObserver;
    public volatile SharedPreferences mSharedPreferences;
    public final SmartSwitchEventReceiver mSmartSwitchEventReceiver;
    public Handler mSystemEventListenerHandler;
    public final UsageStatsManagerInternal mUsageStatsService;
    public final Map mPkgIconMap = new ArrayMap();
    public final List mChimeraUidObservers = new ArrayList();

    public final class CameraProcInfo {
        public long closeRss;
        public final String name;
        public long openRss;
        public int pid;

        public CameraProcInfo(String str) {
            this.name = str;
        }
    }

    public interface ChimeraProcessObserver {
        void onForegroundActivitiesChanged(
                int i, int i2, boolean z, int i3, String[] strArr, boolean z2);
    }

    public final class ForegroundActivityManager {
        public final SparseArray mForegroundActivities = new SparseArray();
    }

    public final class RunningAppProcessInfo {
        public long avgPss;
        public int flags;
        public int importance;
        public boolean isProtectedInPicked;
        public long lastActivityTime;
        public long lastPss;
        public long lastSwapPss;
        public int pid;
        public String[] pkgList;
        public String processName;
        public int processState;
        public int uid;
    }

    public final class SmartSwitchEventReceiver extends BroadcastReceiver {
        public boolean mOnStart;
        public boolean mOnTransfer;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "com.samsung.android.intent.action.SMARTSWITCH_WORK_START":
                case "com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING":
                    this.mOnStart = true;
                    break;
                case "com.samsung.android.intent.SMARTSWITCH_TRANSFER":
                    this.mOnTransfer = intent.getBooleanExtra("smartswitch_transfer", false);
                    break;
                case "com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH":
                    this.mOnStart = false;
                    break;
            }
            StringBuilder sb = new StringBuilder("SmartSwitchEventReceiver() - mOnStart: ");
            sb.append(this.mOnStart);
            sb.append(", mOnTransfer: ");
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "SystemRepositoryDefault", sb, this.mOnTransfer);
        }
    }

    public SystemRepository(Context context, ActivityManagerService activityManagerService) {
        new IUidObserver.Stub() { // from class: com.android.server.chimera.SystemRepository.1
            public final void onUidActive(int i) {}

            public final void onUidCachedChanged(int i, boolean z) {}

            public final void onUidGone(int i, boolean z) {
                synchronized (SystemRepository.this.mChimeraUidObservers) {
                    try {
                        Iterator it =
                                ((ArrayList) SystemRepository.this.mChimeraUidObservers).iterator();
                        if (it.hasNext()) {
                            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                            throw null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onUidIdle(int i, boolean z) {}

            public final void onUidProcAdjChanged(int i, int i2) {}

            public final void onUidStateChanged(int i, int i2, long j, int i3) {}
        };
        this.mChimeraProcessObservers = new ArrayList();
        this.mProcessObserver =
                new IProcessObserver
                        .Stub() { // from class: com.android.server.chimera.SystemRepository.2
                    public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                        synchronized (SystemRepository.this.mChimeraProcessObservers) {
                            try {
                                String[] packagesForUid =
                                        SystemRepository.this.mPackageManager.getPackagesForUid(i2);
                                if (packagesForUid == null) {
                                    packagesForUid = new String[0];
                                }
                                if (packagesForUid.length == 0) {
                                    return;
                                }
                                int userId = UserHandle.getUserId(i2);
                                SystemRepository.this.getClass();
                                boolean equals =
                                        SystemRepository.getCurrentHomePackageName()
                                                .equals(packagesForUid[0]);
                                Iterator it =
                                        ((ArrayList) SystemRepository.this.mChimeraProcessObservers)
                                                .iterator();
                                while (it.hasNext()) {
                                    ((ChimeraProcessObserver) it.next())
                                            .onForegroundActivitiesChanged(
                                                    i, i2, z, userId, packagesForUid, equals);
                                }
                                if (!equals && z) {
                                    SystemRepository.this.mLastForegroundApp =
                                            Pair.create(
                                                    packagesForUid[0],
                                                    Long.valueOf(System.currentTimeMillis()));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                    public final void onProcessDied(int i, int i2) {
                        HeimdallAlwaysRunningMonitor heimdallAlwaysRunningMonitor =
                                HeimdallAlwaysRunningMonitor.INSTANCE;
                        heimdallAlwaysRunningMonitor.getClass();
                        try {
                            if (heimdallAlwaysRunningMonitor.isEnable()) {
                                Message obtain = Message.obtain();
                                obtain.arg1 = i;
                                obtain.what = 2;
                                heimdallAlwaysRunningMonitor.mHandler.sendMessage(obtain);
                            }
                        } catch (Exception e) {
                            RCPManagerService$$ExternalSyntheticOutline0.m(
                                    e,
                                    new StringBuilder("Handler onProcessDied catch exception "),
                                    "HeimdallAlwaysRunningMonitor");
                        }
                        ForegroundActivityManager foregroundActivityManager =
                                SystemRepository.this.mFGActivityManager;
                        if (foregroundActivityManager != null) {
                            synchronized (foregroundActivityManager) {
                                foregroundActivityManager.mForegroundActivities.remove(i);
                            }
                        }
                    }

                    public final void onProcessStarted(
                            int i, int i2, int i3, String str, String str2) {}
                };
        this.mCameraDeviceStateCallback =
                new CameraManager
                        .SemCameraDeviceStateCallback() { // from class:
                                                          // com.android.server.chimera.SystemRepository.3
                    public final void onCameraDeviceStateChanged(
                            String str, int i, int i2, String str2) {
                        SettingRepository settingRepository;
                        if ("android.system".equals(str2)
                                || "com.sec.android.app.tinym".equals(str2)) {
                            return;
                        }
                        boolean z = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                        if (MARsPolicyManager.isChinaPolicyEnabled()
                                && SystemRepository.this.mSystemEventListenerHandler != null
                                && !"com.sec.android.app.camera".equals(str2)) {
                            if (i2 == 0) {
                                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                FreecessController.FreecessControllerHolder.INSTANCE.enterOLAF(
                                        -1, 2000, null);
                            } else if (i2 == 3) {
                                boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                FreecessController.FreecessControllerHolder.INSTANCE.exitOLAF();
                            }
                        }
                        RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
                        SystemRepository systemRepository = SystemRepository.this;
                        synchronized (repositoryFactory) {
                            try {
                                if (repositoryFactory.mSettingRepository == null) {
                                    repositoryFactory.mSettingRepository =
                                            new SettingRepository(systemRepository);
                                }
                                settingRepository = repositoryFactory.mSettingRepository;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (settingRepository.mQuickReclaimEnable) {
                            if (SystemRepository.this.mSystemEventListenerHandler != null
                                    && "com.sec.android.app.camera".equals(str2)) {
                                if (i2 == 1) {
                                    SystemRepository.this.mSystemEventListenerHandler
                                            .sendEmptyMessageDelayed(12, 1000L);
                                } else if (i2 == 3) {
                                    SystemRepository.this.mSystemEventListenerHandler
                                            .sendEmptyMessageDelayed(13, 1000L);
                                    return;
                                }
                            }
                            if (i2 == 1) {
                                SystemRepository systemRepository2 = SystemRepository.this;
                                if (systemRepository2.mLastForegroundApp == null
                                        || systemRepository2.mCameraApps.contains(str2)
                                        || !TextUtils.equals(
                                                (CharSequence)
                                                        SystemRepository.this
                                                                .mLastForegroundApp
                                                                .first,
                                                str2)
                                        || System.currentTimeMillis()
                                                        - ((Long)
                                                                        SystemRepository.this
                                                                                .mLastForegroundApp
                                                                                .second)
                                                                .longValue()
                                                >= 3000) {
                                    return;
                                }
                                SystemRepository.this.mCameraApps.update(str2);
                            }
                        }
                    }
                };
        this.fileCacheReclaimTarget =
                new String[] {
                    "base.apk",
                    "oat/arm/base.odex",
                    "oat/arm/base.vdex",
                    "oat/arm64/base.odex",
                    "oat/arm64/base.vdex"
                };
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mPackageManager = context.getPackageManager();
        this.mActivityManagerService = activityManagerService;
        this.mUsageStatsService =
                (UsageStatsManagerInternal)
                        LocalServices.getService(UsageStatsManagerInternal.class);
        SmartSwitchEventReceiver smartSwitchEventReceiver = new SmartSwitchEventReceiver();
        smartSwitchEventReceiver.mOnStart = false;
        smartSwitchEventReceiver.mOnTransfer = false;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.intent.SMARTSWITCH_TRANSFER");
        intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_START");
        intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING");
        intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH");
        context.registerReceiver(
                smartSwitchEventReceiver,
                intentFilter,
                "com.wssnps.permission.COM_WSSNPS",
                null,
                2);
        this.mSmartSwitchEventReceiver = smartSwitchEventReceiver;
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mBigGameApps = new ICollectionCache$CameraAppsCache(this, 1);
        this.mCameraApps = new ICollectionCache$CameraAppsCache(this, 0);
        this.mGameApps = new ICollectionCache$GameAppsCache(this);
    }

    public static String convertToChimeraTag(String str) {
        return !str.startsWith("Chimera") ? "Chimera".concat(str) : str;
    }

    public static String getCurrentHomePackageName() {
        return (String)
                Optional.ofNullable(DefaultAppFilter.DefaultAppFilterHolder.INSTANCE)
                        .map(new SystemRepository$$ExternalSyntheticLambda1(1))
                        .orElse(KnoxCustomManagerService.LAUNCHER_PACKAGE);
    }

    public static void log(String str, String str2) {
        Log.i(convertToChimeraTag(str), str2);
    }

    public static void logDebug(String str, String str2) {
        if (IS_SHIP_BUILD) {
            return;
        }
        Log.d(convertToChimeraTag(str), str2);
    }

    public final List getAccessibilityServicePackages() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList =
                AccessibilityManager.getInstance(this.mContext)
                        .getEnabledAccessibilityServiceList(-1);
        return enabledAccessibilityServiceList == null
                ? Collections.emptyList()
                : (List)
                        enabledAccessibilityServiceList.stream()
                                .filter(new SystemRepository$$ExternalSyntheticLambda0())
                                .map(new SystemRepository$$ExternalSyntheticLambda1(0))
                                .collect(Collectors.toList());
    }

    public final long getAvailableMemory() {
        long j;
        FileReader fileReader;
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        this.mActivityManager.getMemoryInfo(memoryInfo);
        long j2 = memoryInfo.availMem;
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        try {
            fileReader = new FileReader("/proc/meminfo");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    fileReader.close();
                    j = 0;
                    return j2 - (j * 1024);
                }
                int indexOf = readLine.indexOf("Shmem:");
                while (indexOf < 0) {
                    readLine = bufferedReader.readLine();
                    indexOf = readLine.indexOf("Shmem:");
                }
                j = Integer.parseInt(readLine.substring(indexOf).replaceAll("\\D+", ""));
                bufferedReader.close();
                fileReader.close();
                return j2 - (j * 1024);
            } finally {
            }
        } catch (Throwable th) {
            try {
                fileReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final long getMemmoryOfPid(int i) {
        long j = 0;
        try {
            for (int i2 = 0;
                    i2 < this.mActivityManager.getProcessMemoryInfo(new int[] {i}).length;
                    i2++) {
                j += r5[i2].getTotalPss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    public final List getNativeProcesses(Set set) {
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        activityManagerService.getClass();
        ArrayList arrayList = new ArrayList();
        Set set2 = set;
        if (set == null) {
            HashSet hashSet = new HashSet();
            ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerProcLock) {
                try {
                    Iterator it = activityManagerService.mProcessList.mLruProcesses.iterator();
                    while (it.hasNext()) {
                        hashSet.add(Integer.valueOf(((ProcessRecord) it.next()).mPid));
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            set2 = hashSet;
        }
        activityManagerService.updateCpuStats();
        activityManagerService.mAppProfiler.forAllCpuStats(
                new ActivityManagerService$$ExternalSyntheticLambda5(set2, arrayList));
        return arrayList;
    }

    public final int[] getProcStateAndOomScoreForPid(int i) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        this.mActivityManagerService.getProcessStatesAndOomScoresForPIDs(
                new int[] {i}, iArr, iArr2);
        return new int[] {iArr[0], iArr2[0]};
    }

    public final Pair getProcessStatesAndOomScoresForPIDs(int[] iArr) {
        int length = iArr.length;
        if (length <= 0) {
            return null;
        }
        int[] iArr2 = new int[length];
        int[] iArr3 = new int[length];
        this.mActivityManagerService.getProcessStatesAndOomScoresForPIDs(iArr, iArr2, iArr3);
        return new Pair(iArr2, iArr3);
    }

    public final List getRunningAppProcesses() {
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                this.mActivityManagerService.getRunningAppProcesses()) {
            RunningAppProcessInfo runningAppProcessInfo2 = new RunningAppProcessInfo();
            runningAppProcessInfo2.processName = runningAppProcessInfo.processName;
            runningAppProcessInfo2.pid = runningAppProcessInfo.pid;
            runningAppProcessInfo2.uid = runningAppProcessInfo.uid;
            runningAppProcessInfo2.pkgList = runningAppProcessInfo.pkgList;
            runningAppProcessInfo2.flags = runningAppProcessInfo.flags;
            runningAppProcessInfo2.importance = runningAppProcessInfo.importance;
            runningAppProcessInfo2.processState = runningAppProcessInfo.processState;
            runningAppProcessInfo2.lastActivityTime = runningAppProcessInfo.lastActivityTime;
            runningAppProcessInfo2.lastPss = runningAppProcessInfo.lastPss;
            runningAppProcessInfo2.lastSwapPss = runningAppProcessInfo.lastSwapPss;
            runningAppProcessInfo2.isProtectedInPicked = runningAppProcessInfo.isProtectedInPicked;
            runningAppProcessInfo2.avgPss = runningAppProcessInfo.avgPss;
            arrayList.add(runningAppProcessInfo2);
        }
        return arrayList;
    }

    public final boolean hasConnectionProvider(int i, String str) {
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        activityManagerService.getClass();
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ProcessRecord processRecordLocked =
                        activityManagerService.mProcessList.getProcessRecordLocked(i, str);
                if (processRecordLocked == null) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean hasContentProviderConnection =
                        ActivityManagerService.hasContentProviderConnection(processRecordLocked);
                ActivityManagerService.resetPriorityAfterLockedSection();
                return hasContentProviderConnection;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean killGenieProcess(int i, String str) {
        log("SystemRepositoryDefault", "killGenieProcess pkg: " + str + ", adj threshold:" + i);
        if (str != null) {
            try {
                int i2 =
                        this.mContext
                                .getPackageManager()
                                .getPackageInfo(str, 0)
                                .applicationInfo
                                .uid;
                log("SystemRepositoryDefault", "uid of " + str + " is" + i2);
                return this.mActivityManagerService.killGenieProcess(i2, i, str);
            } catch (PackageManager.NameNotFoundException e) {
                log("SystemRepositoryDefault", "Exception:" + e.toString());
            }
        }
        return false;
    }

    public final void killProcessForChimera(int i, String str, String str2, boolean z) {
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        activityManagerService.getClass();
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ProcessRecord processRecordLocked =
                        activityManagerService.mProcessList.getProcessRecordLocked(i, str);
                if (processRecordLocked != null) {
                    KernelMemoryProxy$ReclaimerLog.write(
                            "B|killProcessForChimera " + str + "(" + processRecordLocked.mPid + ")",
                            false);
                    if (processRecordLocked.mPid == ActivityManagerService.MY_PID) {
                        Slog.d(
                                "ActivityManager",
                                "Chimera kill failed, this is system process: " + str);
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    activityManagerService.mProcessList.removeProcessLocked(
                            processRecordLocked, false, z, 13, 0, str2, true);
                    KernelMemoryProxy$ReclaimerLog.write(
                            "E|killProcessForChimera " + str + "(" + processRecordLocked.mPid + ")",
                            false);
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void registerProcessObserver(ChimeraProcessObserver chimeraProcessObserver) {
        synchronized (this.mChimeraProcessObservers) {
            ((ArrayList) this.mChimeraProcessObservers).add(chimeraProcessObserver);
            if (((ArrayList) this.mChimeraProcessObservers).size() == 1) {
                try {
                    ActivityManagerNative.getDefault()
                            .registerProcessObserver(this.mProcessObserver);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void sendHqmBigData(String str) {
        SemHqmManager semHqmManager =
                (SemHqmManager) this.mContext.getSystemService("HqmManagerService");
        if (semHqmManager != null) {
            log(
                    "SystemRepositoryDefault",
                    "sending bigdata : "
                            + semHqmManager.sendHWParamToHQM(
                                    0, "Sluggish", "KPUT", "ph", "1.1", "sec", "", str, ""));
            logDebug("SystemRepositoryDefault", "feature : KPUT, json : " + str);
        }
    }
}
