package com.android.server.chimera;

import android.content.Context;
import android.os.HandlerThread;
import android.os.SystemProperties;
import com.android.server.chimera.SystemEventListener;
import com.android.server.chimera.heimdall.Heimdall;
import com.android.server.chimera.psitracker.PSITracker;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.am.ActivityManagerService;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ChimeraManager implements SystemEventListener.MediaScanFinishedListener {
    public static final boolean IS_SHIP_BUILD = "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public AbnormalFgsDetector mAbnormalFgsDetector;
    public ChimeraAppManager mAppManager;
    public ChimeraStrategy mChimeraStrategy;
    public Context mContext;
    public GPUMemoryReclaimer mGPUMemoryReclaimer;
    public HandlerThread mHandlerThread;
    public Heimdall mHeimdall;
    public PerProcessNandswap mPerProcessNandswap;
    public SettingRepository mSettingRepository;
    public SystemEventListener mSystemEventListener;
    public SystemRepository mSystemRepository;
    public PolicyHandler mPolicyHandler = null;
    public PSITracker mPSITracker = null;

    public String getVersion() {
        return "2.0";
    }

    public ChimeraManager(Context context, ActivityManagerService activityManagerService) {
        this.mAppManager = null;
        this.mChimeraStrategy = null;
        this.mSystemRepository = null;
        this.mSystemEventListener = null;
        this.mSettingRepository = null;
        this.mSystemRepository = RepositoryFactory.getInstance().getSystemRepository(context, activityManagerService);
        this.mSettingRepository = RepositoryFactory.getInstance().getSettingRepository(this.mSystemRepository);
        HandlerThread handlerThread = new HandlerThread("ObserverHandler");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mChimeraStrategy = new ChimeraStrategy(new ChimeraRecentAppManager(this.mSystemRepository, this.mSettingRepository, this.mHandlerThread.getLooper()), this.mSystemRepository, this.mSettingRepository);
        this.mAppManager = new ChimeraAppManager(this.mSystemRepository);
        this.mContext = context;
        this.mSystemEventListener = new SystemEventListener(context, this.mHandlerThread.getLooper(), this.mSystemRepository);
        this.mAbnormalFgsDetector = new AbnormalFgsDetector(this.mSystemRepository);
        this.mHeimdall = new Heimdall(this.mHandlerThread.getLooper(), context, activityManagerService);
        this.mSystemEventListener.addMediaScanFinishedListener(this);
        ChimeraStandbyBucketCollectorService.schedule(context);
    }

    public final void registerSystemEventListener() {
        this.mSystemEventListener.addBottleNeckHintListener(this.mPolicyHandler);
        this.mSystemEventListener.addPmmCriticalListener(this.mPolicyHandler);
        this.mSystemEventListener.addPmmStateChangeListener(this.mPolicyHandler);
        this.mSystemEventListener.addLmkdEventListener(this.mPolicyHandler);
        this.mSystemEventListener.addCarModeChangeListener(this.mPolicyHandler);
        if (this.mSettingRepository.isQuickReclaimEnable()) {
            this.mSystemEventListener.addAppLaunchIntentListener(this.mPolicyHandler);
            this.mSystemEventListener.addCameraDeviceStateCallback(this.mContext);
            this.mSystemEventListener.addAppLaunchIntent();
            this.mSystemEventListener.addCameraStateListener(this.mPolicyHandler);
        }
        this.mSystemEventListener.addHomeLaunchListener(this.mPolicyHandler);
        if (this.mSettingRepository.isAppsIdleKillEnabled() || this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
            this.mSystemEventListener.addDeviceIdleListener(this.mPolicyHandler);
        }
        this.mSystemEventListener.addOneHourTimerListener(this.mAbnormalFgsDetector);
        if (ChimeraCommonUtil.isQuotaEnable()) {
            this.mSystemEventListener.addAlwaysRunningQuotaExceedListener(this.mPolicyHandler);
        }
    }

    public final void unRegisterSystemEventListener() {
        this.mSystemEventListener.removeBottleNeckHintListener(this.mPolicyHandler);
        this.mSystemEventListener.removePmmCriticalListener(this.mPolicyHandler);
        this.mSystemEventListener.removePmmStateChangeListener(this.mPolicyHandler);
        this.mSystemEventListener.removeLmkdEventListener(this.mPolicyHandler);
        this.mSystemEventListener.removeCarModeChangeListener(this.mPolicyHandler);
        this.mSystemEventListener.removeHomeLaunchListener(this.mPolicyHandler);
        if (this.mSettingRepository.isAppsIdleKillEnabled() || this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
            this.mSystemEventListener.removeDeviceIdleListener(this.mPolicyHandler);
        }
        if (this.mSettingRepository.isQuickReclaimEnable()) {
            this.mSystemEventListener.removeAppLaunchIntentListener(this.mPolicyHandler);
            this.mSystemEventListener.removeCameraDeviceStateCallback(this.mContext);
            this.mSystemEventListener.removeAppLaunchIntent();
            this.mSystemEventListener.removeCameraStateListener(this.mPolicyHandler);
        }
        this.mSystemEventListener.removeOneHourTimerListener(this.mAbnormalFgsDetector);
        if (ChimeraCommonUtil.isQuotaEnable()) {
            this.mSystemEventListener.removeAlwaysRunningQuotaExceedListener(this.mPolicyHandler);
        }
    }

    public void createPolicyHandler() {
        if (this.mPolicyHandler != null) {
            unRegisterSystemEventListener();
        }
        if (this.mSettingRepository.isConservativeMode()) {
            this.mPolicyHandler = new ConservativePolicyHandler(this.mAppManager, this.mChimeraStrategy, this.mSystemRepository, this.mSettingRepository, this.mAbnormalFgsDetector, this.mHandlerThread.getLooper());
        } else {
            this.mPolicyHandler = new AggressivePolicyHandler(this.mAppManager, this.mChimeraStrategy, this.mSystemRepository, this.mSettingRepository, this.mAbnormalFgsDetector, this.mHandlerThread.getLooper());
        }
        registerSystemEventListener();
        this.mSystemRepository.log("ChimeraManager", "Chimera instance created");
    }

    public void createPSITracker() {
        PSITracker pSITracker = this.mPSITracker;
        if (pSITracker != null) {
            this.mSystemEventListener.removeTimeOrTimeZoneChangedListener(pSITracker);
        }
        PSITracker pSITracker2 = new PSITracker(this.mSystemRepository, this.mContext, this.mHandlerThread.getLooper());
        this.mPSITracker = pSITracker2;
        this.mSystemEventListener.addTimeOrTimeZoneChangedListener(pSITracker2);
        if (PSITracker.mFirstTriggeredAfterBooting) {
            return;
        }
        PSITracker.mFirstTriggeredAfterBooting = true;
        this.mPSITracker.scheduleAvailMem240PeriodRecord("MEDIA_SCAN_FINISHED");
    }

    public void checkProcessInHeimdall(int i, String str, String str2) {
        try {
            this.mHeimdall.checkProcess(i, str, str2);
        } catch (Exception e) {
            Heimdall.log(e.getMessage());
        }
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        if ("-a".equals(strArr[0])) {
            printWriter.println("Chimera enabled: true");
        } else if (strArr.length > 0) {
            String str = strArr[0];
            if (str.equals("info")) {
                printWriter.println("Chimera enabled: true");
            } else if (str.equals("standby")) {
                printWriter.println(this.mAppManager.dumpStandbyBucket());
            } else if (str.equals("use_bg_keeping_policy")) {
                printWriter.println("Chimera set ConservativePolicy mode: " + strArr[1]);
                boolean equals = "true".equals(strArr[1].toLowerCase());
                if (equals != this.mSettingRepository.isConservativeMode()) {
                    this.mSettingRepository.enableConservativeMode(equals);
                    unRegisterSystemEventListener();
                    createPolicyHandler();
                }
            } else {
                if (str.equals("reclaim_cache")) {
                    this.mSettingRepository.enableReclaimPageCache("on".equals(strArr[1].toLowerCase()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("reclaim_cache: ");
                    sb.append(this.mSettingRepository.isReclaimPageCacheEnabled() ? "on" : "off");
                    printWriter.println(sb.toString());
                } else if (str.equals("gc")) {
                    this.mSettingRepository.enableGc("on".equals(strArr[1].toLowerCase()));
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("mIsGcenable: ");
                    sb2.append(this.mSettingRepository.isGcEnabled() ? "on" : "off");
                    printWriter.println(sb2.toString());
                } else if (str.equals("enable_app_idle_kill")) {
                    printWriter.println("Chimera enable samsung apps idle kill: " + strArr[1]);
                    boolean equals2 = "true".equals(strArr[1].toLowerCase());
                    if (equals2 != this.mSettingRepository.isAppsIdleKillEnabled()) {
                        this.mSettingRepository.enableAppsIdleKill(equals2);
                        if (!equals2 && !this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
                            this.mSystemEventListener.removeDeviceIdleListener(this.mPolicyHandler);
                        } else if (equals2 && !this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
                            this.mSystemEventListener.addDeviceIdleListener(this.mPolicyHandler);
                        }
                    }
                } else if (str.equals("enable_native_idle_kill")) {
                    printWriter.println("Chimera enable 3rd native processes idle kill: " + strArr[1]);
                    boolean equals3 = "true".equals(strArr[1].toLowerCase());
                    if (equals3 != this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
                        this.mSettingRepository.enableNativeProcessesIdleKill(equals3);
                        if (!equals3 && !this.mSettingRepository.isAppsIdleKillEnabled()) {
                            this.mSystemEventListener.removeDeviceIdleListener(this.mPolicyHandler);
                        } else if (equals3 && !this.mSettingRepository.isAppsIdleKillEnabled()) {
                            this.mSystemEventListener.addDeviceIdleListener(this.mPolicyHandler);
                        }
                    }
                } else if (str.equals("enable_quick_reclaim")) {
                    printWriter.println("Chimera enable quick reclaim: " + strArr[1]);
                    boolean equalsIgnoreCase = "true".equalsIgnoreCase(strArr[1]);
                    if (equalsIgnoreCase != this.mSettingRepository.isQuickReclaimEnable()) {
                        this.mSettingRepository.enableQuickReclaim(equalsIgnoreCase);
                        if (equalsIgnoreCase) {
                            this.mSystemEventListener.addCameraDeviceStateCallback(this.mContext);
                            this.mSystemEventListener.addAppLaunchIntentListener(this.mPolicyHandler);
                            this.mSystemEventListener.addAppLaunchIntent();
                        } else {
                            this.mSystemEventListener.removeCameraDeviceStateCallback(this.mContext);
                            this.mSystemEventListener.removeAppLaunchIntentListener(this.mPolicyHandler);
                            this.mSystemEventListener.removeAppLaunchIntent();
                        }
                    }
                } else if (str.equals("umr")) {
                    UnifiedMemoryReclaimer.dumpInfo(printWriter, strArr);
                } else if ("gmr".equals(str)) {
                    GPUMemoryReclaimer gPUMemoryReclaimer = this.mGPUMemoryReclaimer;
                    if (gPUMemoryReclaimer != null) {
                        gPUMemoryReclaimer.dumpInfo(printWriter, strArr);
                    }
                } else if ("ppnandswap".equals(str)) {
                    PerProcessNandswap perProcessNandswap = this.mPerProcessNandswap;
                    if (perProcessNandswap != null) {
                        perProcessNandswap.dumpInfo(printWriter, strArr);
                    }
                } else if (str.equals("psitracker")) {
                    PSITracker pSITracker = this.mPSITracker;
                    if (pSITracker != null) {
                        pSITracker.dumpInfo(printWriter, strArr);
                    }
                } else {
                    if (str.equals("enable_app_cache_reclaim")) {
                        printWriter.println("Chimera enable app cache reclaim: " + strArr[1]);
                        boolean equalsIgnoreCase2 = "true".equalsIgnoreCase(strArr[1]);
                        if (equalsIgnoreCase2 != this.mSettingRepository.isAppCacheReclaimEnable()) {
                            this.mSettingRepository.enableAppCacheReclaim(equalsIgnoreCase2);
                            if (equalsIgnoreCase2) {
                                printWriter.println("Chimera app cache reclaim enabled.");
                                return;
                            } else {
                                printWriter.println("Chimera app cache reclaim disabled.");
                                return;
                            }
                        }
                        return;
                    }
                    if (str.equals("heimdall")) {
                        this.mHeimdall.dumpInfo(printWriter, strArr);
                        return;
                    } else if (str.equals("set_quota") && !IS_SHIP_BUILD) {
                        printWriter.println("Chimera set quota: " + strArr[1]);
                        ChimeraQuotaMonitor.getInstance().setQuota(Long.parseLong(strArr[1]));
                    }
                }
            }
        }
        this.mChimeraStrategy.dump(printWriter, strArr);
        PolicyHandler policyHandler = this.mPolicyHandler;
        if (policyHandler != null) {
            policyHandler.dump(printWriter, strArr);
        }
        if ("-a".equals(strArr[0])) {
            printWriter.println();
            UnifiedMemoryReclaimer.dumpInfo(printWriter, strArr);
            printWriter.println();
            PerProcessNandswap perProcessNandswap2 = this.mPerProcessNandswap;
            if (perProcessNandswap2 != null) {
                perProcessNandswap2.dumpInfo(printWriter, strArr);
            }
            printWriter.println();
            GPUMemoryReclaimer gPUMemoryReclaimer2 = this.mGPUMemoryReclaimer;
            if (gPUMemoryReclaimer2 != null) {
                gPUMemoryReclaimer2.dumpInfo(printWriter, strArr);
            }
            AbnormalFgsDetector abnormalFgsDetector = this.mAbnormalFgsDetector;
            if (abnormalFgsDetector != null) {
                abnormalFgsDetector.dump(printWriter, strArr);
            }
            Heimdall heimdall = this.mHeimdall;
            if (heimdall != null) {
                heimdall.dumpInfo(printWriter, strArr);
            }
        }
    }

    public ChimeraDataInfo getChimeraStat() {
        return (ChimeraDataInfo) Optional.ofNullable(this.mPolicyHandler).map(new Function() { // from class: com.android.server.chimera.ChimeraManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PolicyHandler) obj).getChimeraStat();
            }
        }).orElse(new ChimeraDataInfo());
    }

    @Override // com.android.server.chimera.SystemEventListener.MediaScanFinishedListener
    public void onMediaScanFinished() {
        this.mSystemRepository.logDebug("ChimeraManager", "onMediaScanFinished() to start the policy ");
        this.mSettingRepository.initialize();
        createPolicyHandler();
        PerProcessNandswap perProcessNandswap = PerProcessNandswap.getInstance();
        this.mPerProcessNandswap = perProcessNandswap;
        perProcessNandswap.init(this.mSystemRepository, this.mChimeraStrategy);
        this.mGPUMemoryReclaimer = GPUMemoryReclaimer.getInstance();
        if (this.mSettingRepository.isPSITrackerEnabled()) {
            createPSITracker();
        }
        if (ChimeraCommonUtil.isQuotaEnable()) {
            ChimeraQuotaMonitor.getInstance().schedule(this.mSystemRepository, this.mAppManager, this.mSystemEventListener, this.mChimeraStrategy.getAlwaysRunningProcQuota());
        }
    }

    public SettingRepository getSettingRepository() {
        return this.mSettingRepository;
    }

    public PSITracker getPSITracker() {
        return this.mPSITracker;
    }
}
