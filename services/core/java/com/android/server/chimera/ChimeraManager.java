package com.android.server.chimera;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.am.MARsPolicyManager;
import com.android.server.chimera.heimdall.Heimdall;
import com.android.server.chimera.heimdall.HeimdallReportManager;
import com.android.server.chimera.ppn.ChimeraQuotaMonitor;
import com.android.server.chimera.ppn.ChimeraQuotaMonitor.AlwaysRunningMemCollectTask;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.chimera.psitracker.PSITracker;
import com.android.server.wm.ActivityMetricsLaunchObserverRegistry;
import com.android.server.wm.LaunchObserverRegistryImpl;
import com.android.server.wm.LaunchObserverRegistryImpl$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ChimeraManager {
    public AbnormalFgsDetector mAbnormalFgsDetector;
    public ChimeraAppManager mAppManager;
    public ChimeraStrategy mChimeraStrategy;
    public Context mContext;
    public GPUMemoryReclaimer mGPUMemoryReclaimer;
    public GenieUnloadPolicyHandler mGenieUnloadPolicyHandler;
    public HandlerThread mHandlerThread;
    public Heimdall mHeimdall;
    public PSITracker mPSITracker;
    public PerProcessNandswap mPerProcessNandswap;
    public PolicyHandler mPolicyHandler;
    public SettingRepository mSettingRepository;
    public SystemEventListener mSystemEventListener;
    public SystemRepository mSystemRepository;

    public final void createPolicyHandler() {
        boolean z = this.mSettingRepository.mIsConservativeMode;
        if (this.mPolicyHandler != null) {
            unRegisterSystemEventListener(z);
        }
        if (z) {
            this.mPolicyHandler =
                    new ConservativePolicyHandler(
                            this.mAppManager,
                            this.mChimeraStrategy,
                            this.mSystemRepository,
                            this.mSettingRepository,
                            this.mAbnormalFgsDetector,
                            this.mHandlerThread.getLooper());
            registerSystemEventListener(true);
        } else {
            Looper looper = this.mHandlerThread.getLooper();
            ChimeraAppManager chimeraAppManager = this.mAppManager;
            ChimeraStrategy chimeraStrategy = this.mChimeraStrategy;
            SystemRepository systemRepository = this.mSystemRepository;
            AggressivePolicyHandler aggressivePolicyHandler =
                    new AggressivePolicyHandler(
                            chimeraAppManager,
                            chimeraStrategy,
                            systemRepository,
                            this.mSettingRepository,
                            this.mAbnormalFgsDetector,
                            looper);
            aggressivePolicyHandler.mPkgKillIntervalDefault = 43200000;
            aggressivePolicyHandler.mIsAdjustTargetFree = false;
            aggressivePolicyHandler.mAdjustTargetFreeEndTime = 0L;
            aggressivePolicyHandler.mAdjustTargetFreeFactor = 1.0d;
            aggressivePolicyHandler.mCurProtectLevel = AggressivePolicyHandler.ProtectLevel.NORMAL;
            aggressivePolicyHandler.mIsHeavyLaunchOn = false;
            aggressivePolicyHandler.mHeavyLaunchBufferSize = 0;
            aggressivePolicyHandler.mHeavyLaunchPackageLimit = 0;
            aggressivePolicyHandler.mSkipReasonLogger = new SkipReasonLogger(systemRepository);
            aggressivePolicyHandler.mSystemRepository.getClass();
            String str = SystemProperties.get("ro.slmk.chimera_kill_interval_ms", "");
            if (str != null && str.length() > 0) {
                String[] split = str.split(",");
                if (split.length == 2) {
                    aggressivePolicyHandler.mPkgKillIntervalDefault = Integer.parseInt(split[0]);
                    aggressivePolicyHandler.mPkgKillIntervalHeavy = Integer.parseInt(split[1]);
                }
            }
            SystemRepository.log(
                    "AggressivePolicyHandler",
                    "PKG_KILL_INTERVAL_DEFAULT: "
                            + aggressivePolicyHandler.mPkgKillIntervalDefault);
            SystemRepository.log(
                    "AggressivePolicyHandler",
                    "PKG_KILL_INTERVAL_HEAVY: " + aggressivePolicyHandler.mPkgKillIntervalHeavy);
            int parseInt =
                    Integer.parseInt(
                            SystemProperties.get(
                                    "persist.sys.chimera_pkg_kill_interval_ms",
                                    aggressivePolicyHandler.mPkgKillIntervalDefault + ""));
            aggressivePolicyHandler.mCemPkgKillIntervalMs =
                    Integer.parseInt(
                            SystemProperties.get(
                                    "ro.slmk.chimera_cem_pkg_kill_interval_ms",
                                    PolicyHandler.CEM_PKG_KILL_INTERVAL_DEFAULT));
            aggressivePolicyHandler.mPkgProtectedParameters =
                    new int[][] {
                        new int[] {200, FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED, parseInt},
                        new int[] {
                            100,
                            FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED,
                            aggressivePolicyHandler.mPkgKillIntervalHeavy
                        }
                    };
            final int i = 0;
            aggressivePolicyHandler.mIsKillBoostModeOnNormal =
                    ((Boolean)
                                    Optional.of(
                                                    SystemProperties.get(
                                                            "ro.slmk.chimera_kill_boost_on_normal",
                                                            ""))
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            String str2 = (String) obj;
                                                            switch (i) {
                                                            }
                                                            return !TextUtils.isEmpty(str2);
                                                        }
                                                    })
                                            .map(
                                                    new AggressivePolicyHandler$$ExternalSyntheticLambda2())
                                            .orElse(
                                                    Boolean.valueOf(
                                                            ChimeraCommonUtil.getRamSizeGb() <= 3)))
                            .booleanValue();
            final int i2 = 1;
            aggressivePolicyHandler.mIsKillBoostModeOnHeavy =
                    ((Boolean)
                                    Optional.of(
                                                    SystemProperties.get(
                                                            "ro.slmk.chimera_kill_boost_on_heavy",
                                                            ""))
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            String str2 = (String) obj;
                                                            switch (i2) {
                                                            }
                                                            return !TextUtils.isEmpty(str2);
                                                        }
                                                    })
                                            .map(
                                                    new AggressivePolicyHandler$$ExternalSyntheticLambda2())
                                            .orElse(
                                                    Boolean.valueOf(
                                                            ChimeraCommonUtil.getRamSizeGb() <= 3)))
                            .booleanValue();
            PolicyHandler.mIsBubEnabled =
                    SystemProperties.get("persist.sys.bub_onoff", "on").equals("on");
            String str2 = SystemProperties.get("ro.slmk.chimera_adjust_boot_targetfree", "");
            if (!TextUtils.isEmpty(str2)) {
                String[] split2 = str2.split(",");
                if (split2.length == 2) {
                    try {
                        int parseInt2 = Integer.parseInt(split2[0]);
                        aggressivePolicyHandler.mAdjustTargetFreeEndTime =
                                System.currentTimeMillis() + (parseInt2 * 1000);
                        aggressivePolicyHandler.mAdjustTargetFreeFactor =
                                Double.parseDouble(split2[1]);
                        aggressivePolicyHandler.mIsAdjustTargetFree = true;
                        SystemRepository.log(
                                "AggressivePolicyHandler",
                                "Adjust targetfree : "
                                        + parseInt2
                                        + ", "
                                        + aggressivePolicyHandler.mAdjustTargetFreeFactor);
                    } catch (Exception unused) {
                        SystemRepository.log(
                                "AggressivePolicyHandler", "Error while adjust targetfee");
                    }
                }
            }
            if (ChimeraCommonUtil.getRamSizeGb() <= 4) {
                aggressivePolicyHandler.mHeavyLaunchBufferSize = 80;
                aggressivePolicyHandler.mHeavyLaunchPackageLimit = 25;
                aggressivePolicyHandler.mIsHeavyLaunchOn = true;
            }
            if (aggressivePolicyHandler.mIsHeavyLaunchOn) {
                aggressivePolicyHandler.mHeavyLaunchCounter =
                        new HeavyLaunchCounter(
                                aggressivePolicyHandler.mHeavyLaunchBufferSize,
                                aggressivePolicyHandler.mHeavyLaunchPackageLimit);
                SystemRepository.log(
                        "AggressivePolicyHandler",
                        "Heavy launch param : "
                                + aggressivePolicyHandler.mHeavyLaunchBufferSize
                                + ","
                                + aggressivePolicyHandler.mHeavyLaunchPackageLimit);
            }
            this.mPolicyHandler = aggressivePolicyHandler;
            registerSystemEventListener(false);
        }
        this.mSystemRepository.getClass();
        SystemRepository.log("ChimeraManager", "Chimera instance created");
    }

    public final void onMediaScanFinished() {
        SystemRepository systemRepository = this.mSystemRepository;
        systemRepository.getClass();
        SystemRepository.logDebug("ChimeraManager", "onMediaScanFinished() to start the policy ");
        SettingRepository settingRepository = this.mSettingRepository;
        settingRepository.initialize();
        Heimdall heimdall = this.mHeimdall;
        ContentResolver contentResolver = heimdall.mContext.getContentResolver();
        heimdall.updateAlwaysRunningGlobalQuota(contentResolver);
        heimdall.updateAnomalyType(contentResolver);
        heimdall.updateSpec(contentResolver);
        int i = Settings.Global.getInt(contentResolver, "heimdall_report_hour_interval", 0);
        if (i > 0) {
            heimdall.mHeimdallPhaseManager.mHeimdallProcessList.mTimeoutReportProtectedHour = i;
        }
        int i2 = Settings.Global.getInt(contentResolver, "heimdall_random_sample_rate", 10);
        heimdall.mHeimdallReportManager.getClass();
        if (i2 >= 0) {
            HeimdallReportManager.sRandomSampleRate = Math.min(i2, 1000);
        }
        Heimdall.log("retrieveSettings done");
        createPolicyHandler();
        SystemRepository.log("ChimeraManager", "createGeniePolicyHandler");
        GenieUnloadPolicyHandler genieUnloadPolicyHandler = this.mGenieUnloadPolicyHandler;
        SystemEventListener systemEventListener = this.mSystemEventListener;
        if (genieUnloadPolicyHandler != null) {
            ((ArrayList) systemEventListener.mLmkdEventListeners).remove(genieUnloadPolicyHandler);
            SystemRepository.log(
                    "ChimeraManager", "removeLmkdEventListener for mGeniePolicyHandler");
        }
        GenieUnloadPolicyHandler genieUnloadPolicyHandler2 =
                new GenieUnloadPolicyHandler(
                        this.mAppManager,
                        this.mChimeraStrategy,
                        this.mSystemRepository,
                        this.mSettingRepository,
                        this.mAbnormalFgsDetector,
                        this.mHandlerThread.getLooper());
        this.mGenieUnloadPolicyHandler = genieUnloadPolicyHandler2;
        ((ArrayList) systemEventListener.mLmkdEventListeners).add(genieUnloadPolicyHandler2);
        SystemRepository.log(
                "ChimeraManager", "create new mGeniePolicyHandler and addLmkdEventListener");
        PerProcessNandswap perProcessNandswap = PerProcessNandswap.getInstance();
        this.mPerProcessNandswap = perProcessNandswap;
        ChimeraStrategy chimeraStrategy = this.mChimeraStrategy;
        perProcessNandswap.init(systemRepository, chimeraStrategy);
        this.mGPUMemoryReclaimer = GPUMemoryReclaimer.getInstance();
        if (settingRepository.mIsPSITrackerEnable) {
            PSITracker pSITracker = this.mPSITracker;
            if (pSITracker != null) {
                ((ArrayList) systemEventListener.mTimeOrTimeZoneChangedListeners)
                        .remove(pSITracker);
            }
            PSITracker pSITracker2 =
                    new PSITracker(
                            this.mContext, this.mHandlerThread.getLooper(), systemRepository);
            this.mPSITracker = pSITracker2;
            ((ArrayList) systemEventListener.mTimeOrTimeZoneChangedListeners).add(pSITracker2);
            if (!PSITracker.mFirstTriggeredAfterBooting) {
                PSITracker.mFirstTriggeredAfterBooting = true;
                this.mPSITracker.scheduleAvailMem240PeriodRecord("MEDIA_SCAN_FINISHED");
            }
        }
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        if (SystemProperties.getBoolean("ro.slmk.chimera_quota_enable", false)) {
            ChimeraQuotaMonitor chimeraQuotaMonitor = ChimeraQuotaMonitor.INSTANCE;
            int i3 = chimeraStrategy.mAlwaysRunningProcessQuota;
            chimeraQuotaMonitor.mSystemRepository = systemRepository;
            long j = i3 * 1024;
            chimeraQuotaMonitor.mQuota = j;
            if (j <= 0) {
                SystemRepository.log(
                        "ChimeraQuotaMonitor",
                        "ChimeraQuotaMonitor invalid quota: " + chimeraQuotaMonitor.mQuota);
            } else if (chimeraQuotaMonitor.mTimer == null) {
                Timer timer = new Timer();
                chimeraQuotaMonitor.mTimer = timer;
                timer.schedule(
                        chimeraQuotaMonitor.new AlwaysRunningMemCollectTask(), 1200000L, 1200000L);
            } else {
                SystemRepository.log("ChimeraQuotaMonitor", "ChimeraQuotaMonitor already start!");
            }
        }
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaPolicyEnabled()) {
            systemRepository.mFGActivityManager = new SystemRepository.ForegroundActivityManager();
        }
    }

    public final void registerSystemEventListener(boolean z) {
        SystemEventListener systemEventListener = this.mSystemEventListener;
        if (!z) {
            ((ArrayList) systemEventListener.mLmkdEventListeners).add(this.mPolicyHandler);
            ((ArrayList) systemEventListener.mCarModeChangeListeners).add(this.mPolicyHandler);
            ((ArrayList) systemEventListener.mHomeLaunchListeners).add(this.mPolicyHandler);
            boolean z2 = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
            boolean isChinaPolicyEnabled = MARsPolicyManager.isChinaPolicyEnabled();
            SettingRepository settingRepository = this.mSettingRepository;
            if (isChinaPolicyEnabled || settingRepository.mQuickReclaimEnable) {
                systemEventListener.addCameraDeviceStateCallback(this.mContext);
            }
            if (settingRepository.mQuickReclaimEnable) {
                ((ArrayList) systemEventListener.mAppLaunchIntentListeners)
                        .add(this.mPolicyHandler);
                ActivityMetricsLaunchObserverRegistry provideLaunchObserverRegistry =
                        systemEventListener.provideLaunchObserverRegistry();
                LaunchObserverRegistryImpl launchObserverRegistryImpl =
                        (LaunchObserverRegistryImpl) provideLaunchObserverRegistry;
                launchObserverRegistryImpl.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(1),
                                launchObserverRegistryImpl,
                                systemEventListener.mAppLaunchObserver));
                ((ArrayList) systemEventListener.mCameraStateListeners).add(this.mPolicyHandler);
            }
        }
        ((ArrayList) systemEventListener.mDeviceIdleListeners).add(this.mPolicyHandler);
        if (((ArrayList) systemEventListener.mOneHourTimerListeners).size() == 0) {
            systemEventListener.startOneHourTimer();
        }
        ((ArrayList) systemEventListener.mOneHourTimerListeners).add(this.mAbnormalFgsDetector);
        boolean z3 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaPolicyEnabled()) {
            ((ArrayList) systemEventListener.mQuotaListeners).add(this.mPolicyHandler);
        }
    }

    public final void unRegisterSystemEventListener(boolean z) {
        SystemEventListener systemEventListener = this.mSystemEventListener;
        if (!z) {
            ((ArrayList) systemEventListener.mLmkdEventListeners).remove(this.mPolicyHandler);
            ((ArrayList) systemEventListener.mCarModeChangeListeners).remove(this.mPolicyHandler);
            ((ArrayList) systemEventListener.mHomeLaunchListeners).remove(this.mPolicyHandler);
            boolean z2 = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
            boolean isChinaPolicyEnabled = MARsPolicyManager.isChinaPolicyEnabled();
            SettingRepository settingRepository = this.mSettingRepository;
            if (isChinaPolicyEnabled || settingRepository.mQuickReclaimEnable) {
                ((CameraManager) this.mContext.getSystemService("camera"))
                        .unregisterSemCameraDeviceStateCallback(
                                systemEventListener.mSystemRepository.mCameraDeviceStateCallback);
            }
            if (settingRepository.mQuickReclaimEnable) {
                ((ArrayList) systemEventListener.mAppLaunchIntentListeners)
                        .remove(this.mPolicyHandler);
                ActivityMetricsLaunchObserverRegistry provideLaunchObserverRegistry =
                        systemEventListener.provideLaunchObserverRegistry();
                LaunchObserverRegistryImpl launchObserverRegistryImpl =
                        (LaunchObserverRegistryImpl) provideLaunchObserverRegistry;
                launchObserverRegistryImpl.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(0),
                                launchObserverRegistryImpl,
                                systemEventListener.mAppLaunchObserver));
                ((ArrayList) systemEventListener.mCameraStateListeners).remove(this.mPolicyHandler);
            }
        }
        ((ArrayList) systemEventListener.mDeviceIdleListeners).remove(this.mPolicyHandler);
        ((ArrayList) systemEventListener.mOneHourTimerListeners).remove(this.mAbnormalFgsDetector);
        if (((ArrayList) systemEventListener.mOneHourTimerListeners).size() == 0) {
            systemEventListener.mHandler.removeMessages(14);
        }
        boolean z3 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaPolicyEnabled()) {
            ((ArrayList) systemEventListener.mQuotaListeners).remove(this.mPolicyHandler);
        }
    }
}
