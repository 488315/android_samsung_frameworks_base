package com.android.server.chimera;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.IProcessObserver;
import android.app.IUidObserver;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemHqmManager;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityManager;
import com.android.server.LocalServices;
import com.android.server.chimera.SystemRepository;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class SystemRepositoryDefault implements SystemRepository {
    public static final boolean IS_USER_SHIP_BUILD;
    public final ActivityManager mActivityManager;
    public final ActivityManagerService mActivityManagerService;
    public AudioManager mAudioManager;
    public final Context mContext;
    public IDeviceIdleController mDeviceIdleController;
    public DevicePolicyManager mDevicePolicyManager;
    public Pair mLastForegroundApp;
    public final PackageManager mPackageManager;
    public volatile SharedPreferences mSharedPreferences;
    public final SmartSwitchEventReceiver mSmartSwitchEventReceiver;
    public Handler mSystemEventListenerHandler;
    public final Map mPkgIconMap = new ArrayMap();
    public final List mChimeraUidObservers = new ArrayList();
    public final IUidObserver mUidObserver = new IUidObserver.Stub() { // from class: com.android.server.chimera.SystemRepositoryDefault.1
        public void onUidActive(int i) {
        }

        public void onUidCachedChanged(int i, boolean z) {
        }

        public void onUidIdle(int i, boolean z) {
        }

        public void onUidProcAdjChanged(int i, int i2) {
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
        }

        public void onUidGone(int i, boolean z) {
            synchronized (SystemRepositoryDefault.this.mChimeraUidObservers) {
                Iterator it = SystemRepositoryDefault.this.mChimeraUidObservers.iterator();
                while (it.hasNext()) {
                    ((SystemRepository.ChimeraUidObserver) it.next()).onUidGone(i, z);
                }
            }
        }
    };
    public final List mChimeraProcessObservers = new ArrayList();
    public final IProcessObserver mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.chimera.SystemRepositoryDefault.2
        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public void onProcessDied(int i, int i2) {
        }

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            synchronized (SystemRepositoryDefault.this.mChimeraProcessObservers) {
                String[] packageNameFromUid = SystemRepositoryDefault.this.getPackageNameFromUid(i2);
                if (packageNameFromUid != null && packageNameFromUid.length != 0) {
                    int userId = SystemRepositoryDefault.this.getUserId(i2);
                    boolean equals = SystemRepositoryDefault.this.getCurrentHomePackageName().equals(packageNameFromUid[0]);
                    Iterator it = SystemRepositoryDefault.this.mChimeraProcessObservers.iterator();
                    while (it.hasNext()) {
                        ((SystemRepository.ChimeraProcessObserver) it.next()).onForegroundActivitiesChanged(i, i2, z, userId, packageNameFromUid, equals);
                    }
                    if (!equals && z) {
                        SystemRepositoryDefault.this.mLastForegroundApp = Pair.create(packageNameFromUid[0], Long.valueOf(System.currentTimeMillis()));
                    }
                }
            }
        }
    };
    public final CameraManager.SemCameraDeviceStateCallback mCameraDeviceStateCallback = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.server.chimera.SystemRepositoryDefault.3
        public void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
            if ("android.system".equals(str2) || "com.sec.android.app.tinym".equals(str2)) {
                return;
            }
            if (MARsPolicyManager.getInstance().isChinaPolicyEnabled() && SystemRepositoryDefault.this.mSystemEventListenerHandler != null && !"com.sec.android.app.camera".equals(str2)) {
                if (i2 == 0) {
                    FreecessController.getInstance().enterOLAF(2000);
                } else if (i2 == 3) {
                    FreecessController.getInstance().exitOLAF();
                }
            }
            if (SystemRepositoryDefault.this.mSystemEventListenerHandler != null && "com.sec.android.app.camera".equals(str2)) {
                if (i2 == 1) {
                    SystemRepositoryDefault.this.mSystemEventListenerHandler.sendEmptyMessageDelayed(12, 1000L);
                } else if (i2 == 3) {
                    SystemRepositoryDefault.this.mSystemEventListenerHandler.sendEmptyMessageDelayed(13, 1000L);
                    return;
                }
            }
            if (i2 != 1 || SystemRepositoryDefault.this.mLastForegroundApp == null || contains(str2) || !TextUtils.equals((CharSequence) SystemRepositoryDefault.this.mLastForegroundApp.first, str2) || System.currentTimeMillis() - ((Long) SystemRepositoryDefault.this.mLastForegroundApp.second).longValue() >= 3000) {
                return;
            }
            update(str2);
        }
    };
    public final String[] fileCacheReclaimTarget = {"base.apk", "oat/arm/base.odex", "oat/arm/base.vdex", "oat/arm64/base.odex", "oat/arm64/base.vdex"};
    public final UsageStatsManagerInternal mUsageStatsService = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
    public ActivityManagerInternal mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    public final ICollectionCache$BigGameAppsCache mBigGameApps = new ICollectionCache$AbstractSharedCollectionCache(this) { // from class: com.android.server.chimera.ICollectionCache$BigGameAppsCache
        @Override // com.android.server.chimera.ICollectionCache$AbstractSharedCollectionCache
        public String getKey() {
            return "BigGameAppsCache";
        }
    };
    public final ICollectionCache$CameraAppsCache mCameraApps = new ICollectionCache$AbstractSharedCollectionCache(this) { // from class: com.android.server.chimera.ICollectionCache$CameraAppsCache
        @Override // com.android.server.chimera.ICollectionCache$AbstractSharedCollectionCache
        public String getKey() {
            return "CameraAppsCache";
        }
    };
    public final ICollectionCache$GameAppsCache mGameApps = new ICollectionCache$GameAppsCache(this);

    static {
        IS_USER_SHIP_BUILD = SystemProperties.get("ro.build.type", "user").equals("user") && SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c") && SystemProperties.get("ro.product_ship", "true").equals("true");
    }

    public class SmartSwitchEventReceiver extends BroadcastReceiver {
        public boolean mOnStart = false;
        public boolean mOnTransfer = false;

        public SmartSwitchEventReceiver(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.intent.SMARTSWITCH_TRANSFER");
            intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_START");
            intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING");
            intentFilter.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH");
            context.registerReceiver(this, intentFilter, "com.wssnps.permission.COM_WSSNPS", null);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            action.hashCode();
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
            Log.i("SystemRepositoryDefault", "SmartSwitchEventReceiver() - mOnStart: " + this.mOnStart + ", mOnTransfer: " + this.mOnTransfer);
        }

        public boolean isSmartSwitchWorking() {
            return this.mOnStart || this.mOnTransfer;
        }
    }

    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.server.chimera.ICollectionCache$BigGameAppsCache] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.server.chimera.ICollectionCache$CameraAppsCache] */
    public SystemRepositoryDefault(Context context, ActivityManagerService activityManagerService) {
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mPackageManager = context.getPackageManager();
        this.mActivityManagerService = activityManagerService;
        this.mSmartSwitchEventReceiver = new SmartSwitchEventReceiver(context);
    }

    @Override // com.android.server.chimera.SystemRepository
    public String getSystemProperty(String str, String str2) {
        return SystemProperties.get(str, str2);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void setSystemProperty(String str, String str2) {
        SystemProperties.set(str, str2);
    }

    @Override // com.android.server.chimera.SystemRepository
    public long getMemmoryOfPid(int i) {
        long j = 0;
        try {
            for (int i2 = 0; i2 < this.mActivityManager.getProcessMemoryInfo(new int[]{i}).length; i2++) {
                j += r5[i2].getTotalPss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    @Override // com.android.server.chimera.SystemRepository
    public ActivityManager.MemoryInfo getMemoryInfo() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        this.mActivityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    @Override // com.android.server.chimera.SystemRepository
    public long getAvailableMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        this.mActivityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem - (ChimeraCommonUtil.getMemInfoByName("Shmem") * 1024);
    }

    @Override // com.android.server.chimera.SystemRepository
    public long getPss(int i, long[] jArr) {
        return Debug.getPss(i, jArr, null);
    }

    @Override // com.android.server.chimera.SystemRepository
    public long[] getRss(int i) {
        return Process.getRss(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isUserShipBuild() {
        return IS_USER_SHIP_BUILD;
    }

    @Override // com.android.server.chimera.SystemRepository
    public void logDebug(String str, String str2) {
        if (IS_USER_SHIP_BUILD) {
            return;
        }
        Log.d(convertToChimeraTag(str), str2);
    }

    @Override // com.android.server.chimera.SystemRepository
    public Pair getProcessStatesAndOomScoresForPIDs(int[] iArr) {
        int length = iArr.length;
        if (length <= 0) {
            return null;
        }
        int[] iArr2 = new int[length];
        int[] iArr3 = new int[length];
        this.mActivityManagerService.getProcessStatesAndOomScoresForPIDs(iArr, iArr2, iArr3);
        return new Pair(iArr2, iArr3);
    }

    @Override // com.android.server.chimera.SystemRepository
    public int[] getProcStateAndOomScoreForPid(int i) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        this.mActivityManagerService.getProcessStatesAndOomScoresForPIDs(new int[]{i}, iArr, iArr2);
        return new int[]{iArr[0], iArr2[0]};
    }

    @Override // com.android.server.chimera.SystemRepository
    public void killProcessForChimera(String str, int i, String str2) {
        this.mActivityManagerService.killProcessForChimera(str, i, str2);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isOnScreenWindow(int i) {
        return this.mActivityManagerService.isOnScreenWindow(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public int hasChimeraProtectedProc(String str, int i) {
        return this.mActivityManagerService.hasChimeraProtectedProc(str, i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isKilledByRecentTask(int i, String str) {
        return this.mActivityManagerService.isKilledByRecentTask(i, str);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean hasRestartService(String str, int i) {
        return this.mActivityManagerService.hasRestartService(str, i);
    }

    public static String convertToChimeraTag(String str) {
        if (str == null || str.startsWith("Chimera")) {
            return str;
        }
        return "Chimera" + str;
    }

    @Override // com.android.server.chimera.SystemRepository
    public void forceGc(int i) {
        this.mActivityManagerService.forceGc(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void log(String str, String str2) {
        Log.i(convertToChimeraTag(str), str2);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean hasPkgIcon(String str, int i) {
        if (this.mPkgIconMap.containsKey(str)) {
            return ((Boolean) this.mPkgIconMap.get(str)).booleanValue();
        }
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        boolean z = false;
        List queryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(intent, 0, i);
        if (queryIntentActivitiesAsUser != null && queryIntentActivitiesAsUser.size() > 0) {
            z = true;
        }
        this.mPkgIconMap.put(str, Boolean.valueOf(z));
        return z;
    }

    @Override // com.android.server.chimera.SystemRepository
    public int getAppStandbyBucket(String str, int i, long j) {
        return this.mUsageStatsService.getAppStandbyBucket(str, i, j);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isInCall() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        }
        return this.mAudioManager.getMode() >= 1;
    }

    @Override // com.android.server.chimera.SystemRepository
    public void registerUidObserver(SystemRepository.ChimeraUidObserver chimeraUidObserver) {
        synchronized (this.mChimeraUidObservers) {
            this.mChimeraUidObservers.add(chimeraUidObserver);
            if (this.mChimeraUidObservers.size() == 1) {
                try {
                    ActivityManagerNative.getDefault().registerUidObserver(this.mUidObserver, 2, -1, (String) null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isApp(int i) {
        return UserHandle.isApp(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public String[] getWakeLockPackageList() {
        return (String[]) Optional.ofNullable((PowerManager) this.mContext.getSystemService("power")).map(new Function() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PowerManager) obj).getWakeLockPackageList();
            }
        }).orElse(new String[0]);
    }

    public final synchronized IDeviceIdleController getDeviceIdleController() {
        if (this.mDeviceIdleController == null) {
            IBinder service = ServiceManager.getService("deviceidle");
            if (service != null) {
                IDeviceIdleController asInterface = IDeviceIdleController.Stub.asInterface(service);
                this.mDeviceIdleController = asInterface;
                if (asInterface != null) {
                    try {
                        service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda6
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                SystemRepositoryDefault.this.lambda$getDeviceIdleController$0();
                            }
                        }, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("SystemRepositoryDefault", "mDeviceIdleController is null!");
                }
            } else {
                Log.d("SystemRepositoryDefault", "binder is null!");
            }
        }
        return this.mDeviceIdleController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeviceIdleController$0() {
        synchronized (this) {
            this.mDeviceIdleController = null;
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getFullPowerWhitelist() {
        return (List) Optional.ofNullable(getDeviceIdleController()).map(new Function() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String[] lambda$getFullPowerWhitelist$1;
                lambda$getFullPowerWhitelist$1 = SystemRepositoryDefault.lambda$getFullPowerWhitelist$1((IDeviceIdleController) obj);
                return lambda$getFullPowerWhitelist$1;
            }
        }).map(new Function() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Arrays.asList((String[]) obj);
            }
        }).orElse(Collections.emptyList());
    }

    public static /* synthetic */ String[] lambda$getFullPowerWhitelist$1(IDeviceIdleController iDeviceIdleController) {
        try {
            return iDeviceIdleController.getFullPowerWhitelist();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getLongLiveProcessesForUser(int i) {
        return this.mActivityManagerService.getLongLiveProcessesForUser(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public Map getAppStandbyBuckets() {
        return ((UsageStatsManager) this.mContext.getSystemService(UsageStatsManager.class)).getAppStandbyBuckets();
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isSmartSwitchWorking() {
        return this.mSmartSwitchEventReceiver.isSmartSwitchWorking();
    }

    @Override // com.android.server.chimera.SystemRepository
    public int getSystemPid() {
        return ActivityManagerService.MY_PID;
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getRunningAppProcesses() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : this.mActivityManagerService.getRunningAppProcesses()) {
            SystemRepository.RunningAppProcessInfo runningAppProcessInfo2 = new SystemRepository.RunningAppProcessInfo();
            runningAppProcessInfo2.processName = runningAppProcessInfo.processName;
            runningAppProcessInfo2.pid = runningAppProcessInfo.pid;
            runningAppProcessInfo2.uid = runningAppProcessInfo.uid;
            runningAppProcessInfo2.pkgList = runningAppProcessInfo.pkgList;
            runningAppProcessInfo2.flags = runningAppProcessInfo.flags;
            runningAppProcessInfo2.importance = runningAppProcessInfo.importance;
            runningAppProcessInfo2.processState = runningAppProcessInfo.processState;
            runningAppProcessInfo2.isFocused = runningAppProcessInfo.isFocused;
            runningAppProcessInfo2.lastActivityTime = runningAppProcessInfo.lastActivityTime;
            runningAppProcessInfo2.lastPss = runningAppProcessInfo.lastPss;
            runningAppProcessInfo2.lastSwapPss = runningAppProcessInfo.lastSwapPss;
            runningAppProcessInfo2.initialIdlePss = runningAppProcessInfo.initialIdlePss;
            runningAppProcessInfo2.isProtectedInPicked = runningAppProcessInfo.isProtectedInPicked;
            runningAppProcessInfo2.lru = i;
            runningAppProcessInfo2.minPss = runningAppProcessInfo.minPss;
            runningAppProcessInfo2.avgPss = runningAppProcessInfo.avgPss;
            runningAppProcessInfo2.maxPss = runningAppProcessInfo.maxPss;
            arrayList.add(runningAppProcessInfo2);
            i++;
        }
        return arrayList;
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isScreenOff() {
        return !((PowerManager) this.mContext.getSystemService("power")).isInteractive();
    }

    @Override // com.android.server.chimera.SystemRepository
    public void registerProcessObserver(SystemRepository.ChimeraProcessObserver chimeraProcessObserver) {
        synchronized (this.mChimeraProcessObservers) {
            this.mChimeraProcessObservers.add(chimeraProcessObserver);
            if (this.mChimeraProcessObservers.size() == 1) {
                try {
                    ActivityManagerNative.getDefault().registerProcessObserver(this.mProcessObserver);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public String[] getPackageNameFromUid(int i) {
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        return packagesForUid != null ? packagesForUid : new String[0];
    }

    @Override // com.android.server.chimera.SystemRepository
    public String getCurrentHomePackageName() {
        return (String) Optional.ofNullable(DefaultAppFilter.getInstance()).map(new Function() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DefaultAppFilter) obj).getDefaultHomePackage();
            }
        }).orElse(KnoxCustomManagerService.LAUNCHER_PACKAGE);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void sendMessage(Handler handler, int i, Object obj) {
        if (handler != null) {
            handler.sendMessage(Message.obtain(handler, i, obj));
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public void sendMessageDelayed(Handler handler, int i, Object obj, long j) {
        if (handler != null) {
            handler.sendMessageDelayed(Message.obtain(handler, i, obj), j);
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public int getUserId(int i) {
        return UserHandle.getUserId(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean hasMessages(Handler handler, int i, Object obj) {
        return handler.hasMessages(i, obj);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void removeMessages(Handler handler, int i, Object obj) {
        handler.removeMessages(i, obj);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isThreadGroupLeader(int i) {
        return Process.getThreadGroupLeader(i) == i;
    }

    @Override // com.android.server.chimera.SystemRepository
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getPkgsTypeForChimera(List list) {
        return MARsPolicyManager.getInstance().getPkgsTypeForChimera(list);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void sendBroadcast(Intent intent) {
        this.mContext.sendBroadcast(intent);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isPackageInstalled(String str) {
        List<PackageInfo> installedPackages = this.mContext.getPackageManager().getInstalledPackages(0);
        if (installedPackages != null) {
            Iterator<PackageInfo> it = installedPackages.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isHomeHubDocked() {
        return DynamicHiddenApp.getInstance().isHomeHubState();
    }

    @Override // com.android.server.chimera.SystemRepository
    public synchronized SharedPreferences getSharedPreferences() {
        if (this.mSharedPreferences == null) {
            try {
                this.mSharedPreferences = this.mContext.getSharedPreferences(convertToChimeraTag("SystemRepositoryDefault"), 0);
            } catch (Exception e) {
                logDebug("SystemRepositoryDefault", e.toString());
            }
        }
        return this.mSharedPreferences;
    }

    @Override // com.android.server.chimera.SystemRepository
    public CameraManager.SemCameraDeviceStateCallback getCameraDeviceStateCallback() {
        return this.mCameraDeviceStateCallback;
    }

    @Override // com.android.server.chimera.SystemRepository
    public ICollectionCache$BigGameAppsCache getBigGameApps() {
        return this.mBigGameApps;
    }

    @Override // com.android.server.chimera.SystemRepository
    public ICollectionCache$CameraAppsCache getCameraApps() {
        return this.mCameraApps;
    }

    @Override // com.android.server.chimera.SystemRepository
    public ICollectionCache$GameAppsCache getGameApps() {
        return this.mGameApps;
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getAccessibilityServicePackages() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = AccessibilityManager.getInstance(this.mContext).getEnabledAccessibilityServiceList(-1);
        if (enabledAccessibilityServiceList == null) {
            return Collections.emptyList();
        }
        return (List) enabledAccessibilityServiceList.stream().filter(new Predicate() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getAccessibilityServicePackages$2;
                lambda$getAccessibilityServicePackages$2 = SystemRepositoryDefault.lambda$getAccessibilityServicePackages$2((AccessibilityServiceInfo) obj);
                return lambda$getAccessibilityServicePackages$2;
            }
        }).map(new Function() { // from class: com.android.server.chimera.SystemRepositoryDefault$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String lambda$getAccessibilityServicePackages$3;
                lambda$getAccessibilityServicePackages$3 = SystemRepositoryDefault.lambda$getAccessibilityServicePackages$3((AccessibilityServiceInfo) obj);
                return lambda$getAccessibilityServicePackages$3;
            }
        }).collect(Collectors.toList());
    }

    public static /* synthetic */ boolean lambda$getAccessibilityServicePackages$2(AccessibilityServiceInfo accessibilityServiceInfo) {
        return (accessibilityServiceInfo == null || accessibilityServiceInfo.getResolveInfo() == null || accessibilityServiceInfo.getResolveInfo().serviceInfo == null) ? false : true;
    }

    public static /* synthetic */ String lambda$getAccessibilityServicePackages$3(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getResolveInfo().serviceInfo.packageName;
    }

    @Override // com.android.server.chimera.SystemRepository
    public String getPackageNameByPid(int i) {
        return this.mActivityManagerInternal.getPackageNameByPid(i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void setSystemEventListenerHandler(Handler handler) {
        this.mSystemEventListenerHandler = handler;
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getNativeProcesses(Set set) {
        return this.mActivityManagerService.getNativeProcesses(set);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean hasConnectionProvider(String str, int i) {
        return this.mActivityManagerService.hasContentProviderConnection(str, i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isLockTaskPackage(String str) {
        if (this.mActivityManager.getLockTaskModeState() == 0) {
            return false;
        }
        if (this.mDevicePolicyManager == null) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
            this.mDevicePolicyManager = devicePolicyManager;
            if (devicePolicyManager == null) {
                return false;
            }
        }
        return this.mDevicePolicyManager.isLockTaskPermitted(str);
    }

    @Override // com.android.server.chimera.SystemRepository
    public List getAppFilePathsByPackageName(String str) {
        ApplicationInfo applicationInfo;
        ArrayList arrayList = new ArrayList();
        try {
            applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return null;
        }
        String str2 = applicationInfo.sourceDir;
        if (str2 != null) {
            File file = new File(str2);
            if (file.exists()) {
                File parentFile = file.getParentFile();
                if (parentFile == null) {
                    Log.e("SystemRepositoryDefault", "AppSourceDir seems not correct : " + str2);
                    return null;
                }
                String str3 = parentFile.getPath() + "/";
                for (String str4 : this.fileCacheReclaimTarget) {
                    String str5 = str3 + str4;
                    if (new File(str5).exists()) {
                        arrayList.add(str5);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.chimera.SystemRepository
    public List dumpProcessListForPPN(int i, BiFunction biFunction) {
        return this.mActivityManagerService.dumpProcessListForPPN(i, biFunction);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void sendHqmBigData(String str, String str2) {
        SemHqmManager semHqmManager = (SemHqmManager) this.mContext.getSystemService("HqmManagerService");
        if (semHqmManager != null) {
            log("SystemRepositoryDefault", "sending bigdata : " + semHqmManager.sendHWParamToHQM(0, "Sluggish", str, "ph", "1.1", "sec", "", str2, ""));
            logDebug("SystemRepositoryDefault", "feature : " + str + ", json : " + str2);
        }
    }

    @Override // com.android.server.chimera.SystemRepository
    public void forceStop(String str, int i) {
        this.mActivityManagerService.forceStopPackage(str, i);
    }

    @Override // com.android.server.chimera.SystemRepository
    public void registerBroadcastReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean useCompaction() {
        return this.mActivityManagerService.useCompaction();
    }

    @Override // com.android.server.chimera.SystemRepository
    public boolean isCharging() {
        BatteryManager batteryManager = (BatteryManager) this.mContext.getSystemService(BatteryManager.class);
        if (batteryManager == null) {
            return false;
        }
        return batteryManager.isCharging();
    }
}
