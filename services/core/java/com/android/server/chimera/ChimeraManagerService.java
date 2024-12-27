package com.android.server.chimera;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.MARsPolicyManager;
import com.android.server.chimera.genie.GenieConfigurations;
import com.android.server.chimera.genie.GenieLogger;
import com.android.server.chimera.genie.GenieMemoryManager;
import com.android.server.chimera.genie.GenieMemoryManager.ModelEventReceiver;
import com.android.server.chimera.genie.MemoryReclaimer;
import com.android.server.chimera.genie.RbinManager;
import com.android.server.chimera.heimdall.Heimdall;
import com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor;
import com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor.OomAdjHandler;
import com.android.server.chimera.heimdall.HeimdallProcessData;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.chimera.psitracker.PSITracker;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.wm.LaunchObserverRegistryImpl;
import com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda0;

import com.samsung.android.chimera.IChimera;
import com.samsung.android.chimera.genie.MemRequest;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Queue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ChimeraManagerService extends IChimera.Stub {
    public static GenieMemoryManager mGenieMemoryManager;
    public final ChimeraManager mChimeraManager;

    public ChimeraManagerService(Context context, ActivityManagerService activityManagerService) {
        SystemRepository systemRepository;
        SystemRepository systemRepository2;
        SettingRepository settingRepository;
        boolean z;
        this.mChimeraManager = null;
        String str = SystemProperties.get("persist.config.chimera.enable", "");
        if (TextUtils.isEmpty(str) || str.equals("false")) {
            SystemProperties.set("persist.config.chimera.enable", "true");
            str = "true";
        }
        boolean z2 = false;
        if (str.startsWith("true")) {
            ChimeraManager chimeraManager = new ChimeraManager();
            chimeraManager.mPolicyHandler = null;
            chimeraManager.mAppManager = null;
            chimeraManager.mChimeraStrategy = null;
            chimeraManager.mSystemRepository = null;
            chimeraManager.mSystemEventListener = null;
            chimeraManager.mSettingRepository = null;
            chimeraManager.mPSITracker = null;
            chimeraManager.mGenieUnloadPolicyHandler = null;
            RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
            synchronized (repositoryFactory) {
                try {
                    if (repositoryFactory.mSystemRepository == null) {
                        repositoryFactory.mSystemRepository =
                                new SystemRepository(context, activityManagerService);
                    }
                    systemRepository2 = repositoryFactory.mSystemRepository;
                } catch (Throwable th) {
                    throw th;
                }
            }
            chimeraManager.mSystemRepository = systemRepository2;
            RepositoryFactory repositoryFactory2 = RepositoryFactory.getInstance();
            synchronized (repositoryFactory2) {
                try {
                    if (repositoryFactory2.mSettingRepository == null) {
                        repositoryFactory2.mSettingRepository =
                                new SettingRepository(systemRepository2);
                    }
                    settingRepository = repositoryFactory2.mSettingRepository;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            chimeraManager.mSettingRepository = settingRepository;
            HandlerThread handlerThread = new HandlerThread("ObserverHandler");
            chimeraManager.mHandlerThread = handlerThread;
            handlerThread.start();
            new ChimeraRecentAppManager(
                    systemRepository2, settingRepository, handlerThread.getLooper());
            chimeraManager.mChimeraStrategy =
                    new ChimeraStrategy(systemRepository2, settingRepository);
            chimeraManager.mAppManager = new ChimeraAppManager(systemRepository2);
            chimeraManager.mContext = context;
            chimeraManager.mSystemEventListener =
                    new SystemEventListener(context, handlerThread.getLooper(), systemRepository2);
            AbnormalFgsDetector abnormalFgsDetector = new AbnormalFgsDetector();
            abnormalFgsDetector.mHeavyApps = new ArrayList();
            abnormalFgsDetector.mKillableHeavyApps = new ArrayList();
            abnormalFgsDetector.mAbnormalHeavyApps = new ArrayList();
            abnormalFgsDetector.mReportedAbnormalHeavyApps = new ArrayList();
            if (AbnormalFgsDetector.mSystemRepository == null) {
                AbnormalFgsDetector.mSystemRepository = systemRepository2;
            }
            chimeraManager.mAbnormalFgsDetector = abnormalFgsDetector;
            Heimdall heimdall =
                    new Heimdall(handlerThread.getLooper(), context, activityManagerService);
            chimeraManager.mHeimdall = heimdall;
            HeimdallAlwaysRunningMonitor heimdallAlwaysRunningMonitor =
                    HeimdallAlwaysRunningMonitor.INSTANCE;
            if (!heimdallAlwaysRunningMonitor.mIsInit) {
                heimdallAlwaysRunningMonitor.mContext = context;
                heimdallAlwaysRunningMonitor.mHeimdall = heimdall;
                heimdallAlwaysRunningMonitor.mSystemRepository = systemRepository2;
                ServiceThread serviceThread =
                        new ServiceThread(10, "HeimdallAlwaysRunningMonitor", true);
                heimdallAlwaysRunningMonitor.mServiceThread = serviceThread;
                serviceThread.start();
                heimdallAlwaysRunningMonitor.mHandler =
                        heimdallAlwaysRunningMonitor.new OomAdjHandler();
                if (heimdallAlwaysRunningMonitor
                                .mContext
                                .getPackageManager()
                                .getPackageInfo("com.salab.issuetracker", 0)
                        != null) {
                    z = true;
                    heimdallAlwaysRunningMonitor.mIsIssueTrackerInstalled = z;
                    heimdallAlwaysRunningMonitor.mIsInit = true;
                }
                z = false;
                heimdallAlwaysRunningMonitor.mIsIssueTrackerInstalled = z;
                heimdallAlwaysRunningMonitor.mIsInit = true;
            }
            ((ArrayList) chimeraManager.mSystemEventListener.mMediaScanFinishedListeners)
                    .add(chimeraManager);
            this.mChimeraManager = chimeraManager;
        }
        boolean z3 = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
        try {
            KernelMemoryProxy$ReclaimerLog.write(
                    "init: UnifiedMemoryReclaimer is disabled by config", true);
            KernelMemoryProxy$ReclaimerLog.write(
                    "init: CORERUNE_UMR_ENABLED = true, MODEL_UMR_ENABLED = "
                            + UnifiedMemoryReclaimer.MODEL_UMR_ENABLED,
                    true);
        } catch (Exception e) {
            KernelMemoryProxy$ReclaimerLog.write("init: failed by exception", true);
            e.printStackTrace();
        }
        if (mGenieMemoryManager == null) {
            RepositoryFactory repositoryFactory3 = RepositoryFactory.getInstance();
            synchronized (repositoryFactory3) {
                try {
                    if (repositoryFactory3.mSystemRepository == null) {
                        repositoryFactory3.mSystemRepository =
                                new SystemRepository(context, activityManagerService);
                    }
                    systemRepository = repositoryFactory3.mSystemRepository;
                } catch (Throwable th3) {
                    throw th3;
                }
            }
            GenieMemoryManager genieMemoryManager = new GenieMemoryManager();
            genieMemoryManager.mLock = new Object();
            genieMemoryManager.mGenieConfigurations = null;
            genieMemoryManager.mMemoryReclaimer = null;
            genieMemoryManager.mSystemRepository = null;
            genieMemoryManager.mName = null;
            genieMemoryManager.mSessionStatus = 0;
            genieMemoryManager.mTimeOutThread = null;
            genieMemoryManager.mReclaimerHandler = null;
            genieMemoryManager.mRbinManager = null;
            genieMemoryManager.mLastReclaimTime = -420000L;
            genieMemoryManager.mHasReclaimed = false;
            genieMemoryManager.mContext = context;
            genieMemoryManager.mGenieConfigurations = new GenieConfigurations();
            genieMemoryManager.mMemoryReclaimer = new MemoryReclaimer();
            genieMemoryManager.mSystemRepository = systemRepository;
            GenieMemoryManager.ModelEventReceiver modelEventReceiver =
                    genieMemoryManager.new ModelEventReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.GEN_AI_RECLAIM");
            intentFilter.addAction("AICORE_BROADCAST_ACTION_MODEL_LOADING");
            intentFilter.addAction("com.samsung.GEN_AI_RECLAIM_END");
            intentFilter.addAction("AICORE_BROADCAST_ACTION_MODEL_UNLOADED");
            systemRepository.mContext.registerReceiver(modelEventReceiver, intentFilter, 2);
            genieMemoryManager.startReclaimerHandlerCheck();
            RbinManager rbinManager = new RbinManager();
            rbinManager.mFeatureEnabled = false;
            rbinManager.mLock = new Object();
            rbinManager.mCount = 0;
            File file = new File("/sys/kernel/rbin/refill_mode");
            if (file.exists() && file.canWrite()) {
                z2 = true;
            }
            rbinManager.mFeatureEnabled = z2;
            if (!z2) {
                Log.i("RbinManager", "__setRbinBlockModePath failed");
            }
            genieMemoryManager.mRbinManager = rbinManager;
            mGenieMemoryManager = genieMemoryManager;
        }
    }

    public final void checkProcessInHeimdall(int i, int i2, String str, String str2) {
        Heimdall.PhaseHandler phaseHandler;
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager != null) {
            try {
                Heimdall heimdall = chimeraManager.mHeimdall;
                if (heimdall.DISABLED
                        || heimdall.mHeimdallPhaseManager == null
                        || (phaseHandler = heimdall.mPhaseHandler) == null
                        || str == null
                        || str2 == null) {
                    return;
                }
                int i3 = heimdall.mAnomalyType;
                if ((i3 & 16) == 0 && (i3 & 8) == 0) {
                    return;
                }
                HeimdallProcessData obtainData = HeimdallProcessData.obtainData();
                obtainData.uid = i2;
                obtainData.pid = i;
                obtainData.processName = str;
                obtainData.firstAppPackageName = str2;
                Message obtainMessage = phaseHandler.obtainMessage(1, obtainData);
                phaseHandler.mNumberOfPendingMessages.incrementAndGet();
                phaseHandler.sendMessageDelayed(obtainMessage, obtainData.delayPhase);
            } catch (Exception e) {
                Heimdall.log(e.getMessage());
            }
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr != null && strArr.length != 0 && strArr.length != 0) {
            for (String str : strArr) {
                if ("-enable_chimera".equals(str)) {
                    SystemProperties.set("persist.config.chimera.enable", "true");
                }
                if ("-disable_chimera".equals(str)) {
                    SystemProperties.set("persist.config.chimera.enable", "forcestop");
                }
                if ("genie".equals(str)) {
                    if (mGenieMemoryManager != null) {
                        printWriter.println("GenieMemoryManager Config");
                        printWriter.println("Timeout: 5000");
                        printWriter.println("ThrottleTime: 600000");
                        printWriter.println(
                                "Default Size Request: Samsung : "
                                        + GenieMemoryManager.DEFAULT_SAMSUNG_MODEL_SIZE
                                        + " kB Google : "
                                        + GenieMemoryManager.DEFAULT_GOOGLE_MODEL_SIZE
                                        + " kB");
                        synchronized (GenieLogger.sLock) {
                            try {
                                printWriter.println();
                                printWriter.println("=====================================");
                                printWriter.println("------------ GenieLogger ------------");
                                printWriter.println("=====================================");
                                Queue queue = GenieLogger.sDump;
                                if (queue != null) {
                                    Iterator it = queue.iterator();
                                    while (it.hasNext()) {
                                        Iterator it2 = ((ArrayList) it.next()).iterator();
                                        while (it2.hasNext()) {
                                            printWriter.println((String) it2.next());
                                        }
                                        printWriter.println();
                                    }
                                    printWriter.println();
                                    printWriter.println(
                                            "Total Requests : "
                                                    + GenieLogger.sRequestCount
                                                    + " | Reclaimed Requests"
                                                    + GenieLogger.sReclaimedRequests);
                                }
                            } finally {
                            }
                        }
                    } else {
                        printWriter.println("Genie Disabled");
                    }
                }
            }
        }
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager == null || strArr == null || strArr.length == 0) {
            return;
        }
        boolean equals = "-a".equals(strArr[0]);
        Heimdall heimdall = chimeraManager.mHeimdall;
        SettingRepository settingRepository = chimeraManager.mSettingRepository;
        if (equals) {
            printWriter.println("Chimera enabled: true");
            printWriter.println(System.lineSeparator() + "[Chimera Stats]");
            if (settingRepository.mIsConservativeMode) {
                printWriter.println("Using Conservative mode");
            } else {
                printWriter.println("Using Aggressive mode");
            }
        } else if (strArr.length > 0) {
            String str2 = strArr[0];
            if (str2.equals("info")) {
                printWriter.println("Chimera enabled: true");
            } else if (str2.equals("standby")) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                ChimeraAppManager chimeraAppManager = chimeraManager.mAppManager;
                sb2.append(chimeraAppManager.mStandbyInfoMap.size());
                sb2.append(" apps in mStandbyInfoMap: \n");
                sb.append(sb2.toString());
                for (String str3 : chimeraAppManager.mStandbyInfoMap.keySet()) {
                    sb.append(str3 + ": ");
                    sb.append(chimeraAppManager.mStandbyInfoMap.get(str3));
                    sb.append("\n");
                }
                printWriter.println(sb.toString());
            } else if (str2.equals("use_bg_keeping_policy")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Chimera set ConservativePolicy mode: "),
                        strArr[1],
                        printWriter);
                boolean equals2 = "true".equals(strArr[1].toLowerCase());
                if (equals2 != settingRepository.mIsConservativeMode) {
                    settingRepository.mIsConservativeMode = equals2;
                    chimeraManager.unRegisterSystemEventListener(equals2);
                    chimeraManager.createPolicyHandler();
                }
            } else if (str2.equals("enable_quick_reclaim")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Chimera enable quick reclaim: "),
                        strArr[1],
                        printWriter);
                boolean equalsIgnoreCase = "true".equalsIgnoreCase(strArr[1]);
                if (equalsIgnoreCase != settingRepository.mQuickReclaimEnable) {
                    settingRepository.mQuickReclaimEnable = equalsIgnoreCase;
                    SystemEventListener systemEventListener = chimeraManager.mSystemEventListener;
                    if (equalsIgnoreCase) {
                        systemEventListener.addCameraDeviceStateCallback(chimeraManager.mContext);
                        ((ArrayList) systemEventListener.mAppLaunchIntentListeners)
                                .add(chimeraManager.mPolicyHandler);
                        LaunchObserverRegistryImpl launchObserverRegistryImpl =
                                (LaunchObserverRegistryImpl)
                                        systemEventListener.provideLaunchObserverRegistry();
                        launchObserverRegistryImpl.mHandler.sendMessage(
                                PooledLambda.obtainMessage(
                                        new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(1),
                                        launchObserverRegistryImpl,
                                        systemEventListener.mAppLaunchObserver));
                        boolean z = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                        if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                            systemEventListener.addCameraDeviceStateCallback(
                                    chimeraManager.mContext);
                        }
                    } else {
                        Context context = chimeraManager.mContext;
                        systemEventListener.getClass();
                        ((CameraManager) context.getSystemService("camera"))
                                .unregisterSemCameraDeviceStateCallback(
                                        systemEventListener
                                                .mSystemRepository
                                                .mCameraDeviceStateCallback);
                        ((ArrayList) systemEventListener.mAppLaunchIntentListeners)
                                .remove(chimeraManager.mPolicyHandler);
                        LaunchObserverRegistryImpl launchObserverRegistryImpl2 =
                                (LaunchObserverRegistryImpl)
                                        systemEventListener.provideLaunchObserverRegistry();
                        launchObserverRegistryImpl2.mHandler.sendMessage(
                                PooledLambda.obtainMessage(
                                        new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(0),
                                        launchObserverRegistryImpl2,
                                        systemEventListener.mAppLaunchObserver));
                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                        if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                            systemEventListener.addCameraDeviceStateCallback(
                                    chimeraManager.mContext);
                        }
                    }
                }
            } else if ("gmr".equals(str2)) {
                GPUMemoryReclaimer gPUMemoryReclaimer = chimeraManager.mGPUMemoryReclaimer;
                if (gPUMemoryReclaimer != null) {
                    gPUMemoryReclaimer.dumpInfo(printWriter, strArr);
                }
            } else if ("ppnandswap".equals(str2)) {
                PerProcessNandswap perProcessNandswap = chimeraManager.mPerProcessNandswap;
                if (perProcessNandswap != null) {
                    perProcessNandswap.dumpInfo(printWriter, strArr);
                }
            } else if (str2.equals("psitracker")) {
                PSITracker pSITracker = chimeraManager.mPSITracker;
                if (pSITracker != null) {
                    if (strArr.length == 3) {
                        String str4 = strArr[1];
                        String str5 = strArr[2];
                        if ("0".equals(str4) || "0".equals(str5)) {
                            pSITracker.getPSIValueListDump(printWriter, 0L, 0L);
                        } else {
                            try {
                                pSITracker.getPSIValueListDump(
                                        printWriter, Long.parseLong(str4), Long.parseLong(str5));
                            } catch (NumberFormatException unused) {
                                pSITracker.getPSIValueListDump(printWriter, 0L, 0L);
                            }
                        }
                    } else {
                        pSITracker.getPSIValueListDump(printWriter, 0L, 0L);
                    }
                }
            } else if (str2.equals("enable_app_cache_reclaim")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Chimera enable app cache reclaim: "),
                        strArr[1],
                        printWriter);
                boolean equalsIgnoreCase2 = "true".equalsIgnoreCase(strArr[1]);
                if (equalsIgnoreCase2 != settingRepository.mIsAppCacheReclaimEnable) {
                    settingRepository.mIsAppCacheReclaimEnable = equalsIgnoreCase2;
                    if (equalsIgnoreCase2) {
                        printWriter.println("Chimera app cache reclaim enabled.");
                    } else {
                        printWriter.println("Chimera app cache reclaim disabled.");
                    }
                }
            } else {
                if (str2.equals("heimdall")) {
                    heimdall.dumpInfo(printWriter, strArr);
                    return;
                }
                if (!str2.equals("set_quota")) {
                    if (str2.equals("umr")) {
                        UnifiedMemoryReclaimer.dumpInfo(printWriter);
                    } else if (str2.equals("set_subprockill")) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Chimera enable sub process kill: "),
                                strArr[1],
                                printWriter);
                        boolean equals3 = "true".equals(strArr[1].toLowerCase());
                        if (equals3 != settingRepository.mIsSubProcEnable) {
                            settingRepository.mIsSubProcEnable = equals3;
                        }
                    }
                }
            }
        }
        ChimeraStrategy chimeraStrategy = chimeraManager.mChimeraStrategy;
        chimeraStrategy.getClass();
        if (strArr.length != 0) {
            if ("-a".equals(strArr[0])) {
                chimeraStrategy.dumpInfo(printWriter);
            } else if (strArr.length > 0) {
                String str6 = strArr[0];
                if (str6.equals("info")) {
                    chimeraStrategy.dumpInfo(printWriter);
                } else if (str6.equals("mem") && strArr.length > 1) {
                    try {
                        long parseLong = Long.parseLong(strArr[1]);
                        chimeraStrategy.mMemFreeTarget = parseLong;
                        chimeraStrategy.mSettingRepository.mIsDynamicTargetFreeEnabled = false;
                        printWriter.println("Target mem : " + parseLong);
                    } catch (NumberFormatException e) {
                        printWriter.println(e.getMessage());
                    }
                } else if (str6.equals("set_protected_count_on_lmkd") && strArr.length > 1) {
                    try {
                        chimeraStrategy.mProtectedCountOnLmkdTrigger = Integer.parseInt(strArr[1]);
                    } catch (NumberFormatException e2) {
                        printWriter.println(e2.getMessage());
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("ProtectedCount On Lmkd : "),
                            chimeraStrategy.mProtectedCountOnLmkdTrigger,
                            printWriter);
                } else if (str6.equals("set_protected_count_on_home") && strArr.length > 1) {
                    try {
                        chimeraStrategy.mProtectedCountOnHomeTrigger = Integer.parseInt(strArr[1]);
                    } catch (NumberFormatException e3) {
                        printWriter.println(e3.getMessage());
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("ProtectedCount On Home : "),
                            chimeraStrategy.mProtectedCountOnHomeTrigger,
                            printWriter);
                }
            }
        }
        PolicyHandler policyHandler = chimeraManager.mPolicyHandler;
        if (policyHandler != null) {
            policyHandler.dump(printWriter, strArr);
        }
        if ("-a".equals(strArr[0])) {
            printWriter.println();
            PerProcessNandswap perProcessNandswap2 = chimeraManager.mPerProcessNandswap;
            if (perProcessNandswap2 != null) {
                perProcessNandswap2.dumpInfo(printWriter, strArr);
            }
            printWriter.println();
            GPUMemoryReclaimer gPUMemoryReclaimer2 = chimeraManager.mGPUMemoryReclaimer;
            if (gPUMemoryReclaimer2 != null) {
                gPUMemoryReclaimer2.dumpInfo(printWriter, strArr);
            }
            AbnormalFgsDetector abnormalFgsDetector = chimeraManager.mAbnormalFgsDetector;
            if (abnormalFgsDetector != null && strArr.length != 0) {
                printWriter.println("AbnormalFgsDetector");
                printWriter.print("HeavyApps : [");
                AbnormalFgsDetector.printHeavyAppItems(printWriter, abnormalFgsDetector.mHeavyApps);
                printWriter.println("]");
                printWriter.print("KillableHeavyApps : [");
                AbnormalFgsDetector.printHeavyAppItems(
                        printWriter, abnormalFgsDetector.mKillableHeavyApps);
                printWriter.println("]");
                printWriter.print("AbnormalHeavyApps : [Current ");
                AbnormalFgsDetector.printHeavyAppItems(
                        printWriter, abnormalFgsDetector.mAbnormalHeavyApps);
                printWriter.print(" Reported ");
                AbnormalFgsDetector.printHeavyAppItems(
                        printWriter, abnormalFgsDetector.mReportedAbnormalHeavyApps);
                printWriter.println("]");
            }
            if (heimdall != null) {
                heimdall.dumpInfo(printWriter, strArr);
            }
            UnifiedMemoryReclaimer.dumpInfo(printWriter);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00e7, code lost:

       r0 = new java.util.ArrayList();
       r1 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00f1, code lost:

       if (r1 >= r4.size()) goto L46;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00f3, code lost:

       r0.add(new com.samsung.android.chimera.PSIAvailableMem(((java.lang.Long) r6.get(r1)).longValue(), ((java.lang.Long) r7.get(r1)).longValue(), ((java.lang.Long) r8.get(r1)).longValue(), ((java.lang.Long) r9.get(r1)).longValue()));
       r1 = r1 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00e4, code lost:

       if (r1 == null) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getAvailableMemInfo(long r20, long r22) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.ChimeraManagerService.getAvailableMemInfo(long,"
                    + " long):java.util.List");
    }

    public final ChimeraDataInfo getChimeraStat() {
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager != null) {
            return (ChimeraDataInfo)
                    Optional.ofNullable(chimeraManager.mPolicyHandler)
                            .map(new ChimeraManager$$ExternalSyntheticLambda0())
                            .orElse(new ChimeraDataInfo());
        }
        return null;
    }

    public final void prepareMemory(MemRequest memRequest) {
        if (mGenieMemoryManager != null) {
            Log.d("ChimeraManagerService", "prepareMemory " + memRequest.getSize());
            mGenieMemoryManager.prepareMemory(memRequest, null);
        }
    }

    public final void setGenieSessionEnd() {
        if (mGenieMemoryManager != null) {
            Log.d("ChimeraManagerService", "setGenieSessionEnd ");
            mGenieMemoryManager.setGenieSessionEnd();
            GenieMemoryManager genieMemoryManager = mGenieMemoryManager;
            genieMemoryManager.getClass();
            genieMemoryManager.startUnloadTimerLocked(
                    (String)
                            ((HashMap) GenieConfigurations.sGenAIPackageMap)
                                    .get(
                                            genieMemoryManager.mContext.getPackageManager()
                                                    .getPackagesForUid(Binder.getCallingUid())[0]));
        }
    }

    public final void setGenieSessionStart() {
        if (mGenieMemoryManager != null) {
            Log.d("ChimeraManagerService", "setGenieSessionStart ");
            mGenieMemoryManager.getClass();
            Log.i("GenieMemoryManager", "setGenieSessionStart");
        }
    }
}
