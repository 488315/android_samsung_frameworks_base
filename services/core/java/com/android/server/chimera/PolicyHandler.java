package com.android.server.chimera;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseIntArray;
import com.android.internal.util.RingBuffer;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.chimera.ChimeraAppInfo;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.ChimeraQuotaMonitor;
import com.android.server.chimera.SkipReasonLogger;
import com.android.server.chimera.SystemEventListener;
import com.android.server.chimera.SystemRepository;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import libcore.io.IoUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class PolicyHandler implements SystemEventListener.BottleNeckHintListener, SystemEventListener.PmmCriticalListener, SystemEventListener.PmmStateChangeListener, SystemEventListener.LmkdEventListener, SystemEventListener.HomeLaunchListener, SystemEventListener.CarModeChangeListener, SystemRepository.ChimeraProcessObserver, SystemEventListener.AppLaunchIntentListener, SystemEventListener.DeviceIdleListener, SystemEventListener.CameraStateListener, SystemEventListener.AlwaysRunningQuotaExceedListener {
    public AbnormalFgsDetector mAbnormalFgsDetector;
    public ChimeraAppManager mAppManager;
    public final SystemRepository.CameraProcInfo mCameraAppInfo;
    public final SystemRepository.CameraProcInfo mCameraProviderInfo;
    public final List mCameraRelateInfos;
    public final SystemRepository.CameraProcInfo mCameraServerInfo;
    public int mCemPkgKillIntervalMs;
    public final ChimeraAppReclaim mChimeraAppReclaim;
    public ChimeraStrategy mChimeraStrategy;
    public final int mDefaultQuickReclaimAdditionalMemory;
    public int mDynamicQuickReclaimAdditionalMemory;
    public PolicyEventHandler mHandler;
    public boolean mIsDynamicCameraMemorySuccess;
    public RingBuffer mKillHistoryBuffer;
    public int mPkgKillIntervalMs;
    public int[][] mPkgProtectedParameters;
    public PmmStateHistory mPmmStateHistory;
    public List mPowerWhitelistedApps;
    public long mQuickReclaimLastFilterTime;
    public final SettingRepository mSettingRepository;
    public SkipReasonLogger mSkipReasonLogger;
    public final SystemRepository mSystemRepository;
    public WakeLockManager mWakeLockManager;
    public static final Long NOT_HEAVY_PSS = -1L;
    public static final String CEM_PKG_KILL_INTERVAL_DEFAULT = String.valueOf(600000);
    public static int PICKED_OOM_ADJ = 850;
    public static boolean mIsBubEnabled = false;
    public static final int[] CMDLINE_OUT = {IInstalld.FLAG_USE_QUOTA};
    public static int mQuotaExceedCnt = 0;
    public static int mQuotaKillCnt = 0;
    public SparseIntArray mAdjKillCnt = new SparseIntArray(32);
    public ArrayMap mAppKillCnt = new ArrayMap();
    public float mWeightLru = 0.3f;
    public float mWeightStandbyBucket = 0.3f;
    public float mWeightMem = 0.4f;
    public boolean mIsKillBoostModeOnNormal = false;
    public boolean mIsKillBoostModeOnHeavy = false;
    public int mPMMTriggerCnt = 0;
    public int mPMMKillCnt = 0;
    public int mTriggerCnt = 0;
    public int[] mTriggerCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
    public int[] mActionCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
    public int mActionCnt = 0;
    public int mNoActionCnt = 0;
    public int mKillCnt = 0;
    public int[] mKillCntByGrp = {0, 0, 0};
    public long mAvgAvailableMem = 0;
    public long mAvgReleasedMem = 0;
    public long[] mAppFileCacheRecliamCnt = {0, 0, 0, 0};
    public Map mLastKilledTimeMap = new HashMap();
    public boolean mIsCarMode = false;
    public final List mQuickReclaimPreKillApps = new ArrayList();
    public final Map mBigAppPssMap = new ArrayMap();
    public ProtectLevel mCurProtectLevel = ProtectLevel.NORMAL;
    public int mPkgKillIntervalHeavy = 43200000;
    public int mPkgKillIntervalDefault = 1800000;
    public final AtomicInteger mQuickReclaimKillCnt = new AtomicInteger();
    public final ThreadPoolExecutor mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public enum ProtectLevel {
        NORMAL,
        HEAVY
    }

    public enum ProtectedReason {
        NONE,
        ALREADY_DIED,
        EXECUTING_SERVICE,
        RUNNING_INTENT,
        ACTIVITY_TIME,
        VISIBLE_ADJ,
        HAS_CONNECTION_PROVIDER
    }

    public abstract void dump(PrintWriter printWriter, String[] strArr);

    public abstract int executePolicy(ChimeraCommonUtil.TriggerSource triggerSource, int i);

    public abstract boolean hasProtectedAdjOrProcState(ChimeraAppInfo chimeraAppInfo);

    public PolicyHandler(ChimeraAppManager chimeraAppManager, ChimeraStrategy chimeraStrategy, SystemRepository systemRepository, SettingRepository settingRepository, AbnormalFgsDetector abnormalFgsDetector, Looper looper) {
        SystemRepository.CameraProcInfo cameraProcInfo = new SystemRepository.CameraProcInfo("vendor.samsung.hardware.camera.provider");
        this.mCameraProviderInfo = cameraProcInfo;
        SystemRepository.CameraProcInfo cameraProcInfo2 = new SystemRepository.CameraProcInfo("cameraserver");
        this.mCameraServerInfo = cameraProcInfo2;
        SystemRepository.CameraProcInfo cameraProcInfo3 = new SystemRepository.CameraProcInfo("com.sec.android.app.camera");
        this.mCameraAppInfo = cameraProcInfo3;
        this.mCameraRelateInfos = Arrays.asList(cameraProcInfo, cameraProcInfo2, cameraProcInfo3);
        this.mIsDynamicCameraMemorySuccess = false;
        this.mAppManager = chimeraAppManager;
        this.mChimeraStrategy = chimeraStrategy;
        this.mSystemRepository = systemRepository;
        this.mPmmStateHistory = new PmmStateHistory(systemRepository);
        this.mWakeLockManager = new WakeLockManager(systemRepository);
        this.mChimeraAppReclaim = new ChimeraAppReclaim(systemRepository, this.mAppManager);
        this.mKillHistoryBuffer = new RingBuffer(String.class, 200);
        this.mSettingRepository = settingRepository;
        this.mSkipReasonLogger = new SkipReasonLogger(systemRepository);
        this.mHandler = new PolicyEventHandler(looper);
        int quickReclaimDefaultThreshold = this.mChimeraStrategy.getQuickReclaimDefaultThreshold() * 1024;
        this.mDefaultQuickReclaimAdditionalMemory = quickReclaimDefaultThreshold;
        this.mDynamicQuickReclaimAdditionalMemory = quickReclaimDefaultThreshold;
        this.mAbnormalFgsDetector = abnormalFgsDetector;
        readSystemProperties();
        registerProcessObserver();
    }

    public final void readSystemProperties() {
        this.mPkgKillIntervalMs = Integer.parseInt(this.mSystemRepository.getSystemProperty("persist.sys.chimera_pkg_kill_interval_ms", String.valueOf(this.mPkgKillIntervalDefault)));
        this.mCemPkgKillIntervalMs = Integer.parseInt(this.mSystemRepository.getSystemProperty("ro.slmk.chimera_cem_pkg_kill_interval_ms", CEM_PKG_KILL_INTERVAL_DEFAULT));
        this.mPkgProtectedParameters = new int[][]{new int[]{200, 850, this.mPkgKillIntervalMs}, new int[]{100, 850, this.mPkgKillIntervalHeavy}};
    }

    public boolean hasImportantAdjWithSystemUid(ChimeraAppInfo chimeraAppInfo) {
        if (chimeraAppInfo.uid < 10000 && chimeraAppInfo.statsAndOomScores != null) {
            int i = 0;
            while (true) {
                ProcessStatsAndOomScores processStatsAndOomScores = chimeraAppInfo.statsAndOomScores;
                if (i >= processStatsAndOomScores.mPids.length) {
                    break;
                }
                if (processStatsAndOomScores.mScores[i] < 800) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public void dumpCommonInfo(PrintWriter printWriter) {
        printWriter.println(System.lineSeparator() + "[Chimera Stats]");
        if (this.mSettingRepository.isConservativeMode()) {
            printWriter.println("Using Conservative mode");
        } else {
            printWriter.println("Using Aggressive mode");
        }
        ChimeraCommonUtil.TriggerSource[] triggerSourceArr = {ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_BOTTLENECK_HINT, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_DEVICE_IDLE, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_APP_LAUNCH_INTENT, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_QUOTA};
        printWriter.println("Total Trigger Count: " + this.mTriggerCnt);
        StringBuilder sb = new StringBuilder("ActionCnt: " + this.mActionCnt);
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            ChimeraCommonUtil.TriggerSource triggerSource = triggerSourceArr[i2];
            printWriter.println("Triggered by " + triggerSource.name + ": " + this.mTriggerCntSrc[triggerSource.ordinal()]);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("Action by ");
            sb.append(triggerSource.name);
            sb.append(": ");
            sb.append(this.mActionCntSrc[triggerSource.ordinal()]);
        }
        printWriter.println(sb);
        printWriter.println("KillCnt: " + this.mKillCnt);
        while (i < 3) {
            int i3 = i + 1;
            printWriter.println(String.format("     G%d: %d", Integer.valueOf(i3), Integer.valueOf(this.mKillCntByGrp[i])));
            i = i3;
        }
        printWriter.println("NoActionCnt: " + this.mNoActionCnt);
        printWriter.println("PmmCriticalTrigger: " + this.mPMMTriggerCnt);
        printWriter.println("PmmCriticalKillCnt: " + this.mPMMKillCnt);
        printWriter.println("AvgAvailableMem: " + this.mAvgAvailableMem);
        printWriter.println("AvgReleasedMem: " + this.mAvgReleasedMem);
        printWriter.println("LastPmmState:" + this.mPmmStateHistory.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mIsGcenable: ");
        sb2.append(this.mSettingRepository.isGcEnabled() ? "on" : "off");
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("mIsReclaimPageCache: ");
        sb3.append(this.mSettingRepository.isReclaimPageCacheEnabled() ? "on" : "off");
        printWriter.println(sb3.toString());
        printWriter.println("mActionGcCnt: " + this.mChimeraAppReclaim.mActionGcCnt);
        printWriter.println("ActionReclaimCnt: " + this.mChimeraAppReclaim.mActionReclaimCnt);
        printWriter.println("SkipReclaimCnt: " + this.mChimeraAppReclaim.mSkipReclaimCnt);
        printWriter.println("Custom mode : " + this.mSettingRepository.isCustomMode());
        printWriter.println("mIsQuickReclaimEnabled: " + this.mSettingRepository.isQuickReclaimEnable() + ", heavy apps : " + getHeavyAppsToString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("QuickReclaimKillCnt: ");
        sb4.append(this.mQuickReclaimKillCnt);
        printWriter.println(sb4.toString());
        printWriter.println("QuickReclaimDynamicThreshold: " + this.mDynamicQuickReclaimAdditionalMemory);
        printWriter.println("Protected AccessibilityPackges: " + String.join(", ", this.mSystemRepository.getAccessibilityServicePackages()));
        printWriter.println("App File Cache Reclaim Enable: " + this.mSettingRepository.isAppCacheReclaimEnable());
        printWriter.println("App File Cache Reclaim: " + Arrays.toString(this.mAppFileCacheRecliamCnt));
        printWriter.println("Fast Madvise Enable: " + this.mSettingRepository.isFastMadviseEnable());
        if (ChimeraCommonUtil.isQuotaEnable()) {
            printWriter.println("AlwaysRunningQuotaPPN Trigger Cnt: " + PerProcessNandswap.mAlwaysRunningQuotaPPNTriggerCnt);
            printWriter.println("AlwaysRunningQuotaPPN Cnt: " + PerProcessNandswap.mAlwaysRunningQuotaPPNCnt);
            ChimeraQuotaMonitor.getInstance().dumpTaskHistory(printWriter);
        }
    }

    public void dumpAdjInfo(PrintWriter printWriter) {
        int[] killCntByAdj = getKillCntByAdj();
        for (int i = 0; i < killCntByAdj.length; i++) {
            printWriter.println("kills at or below oom_adj " + ChimeraCommonUtil.ADJ_LEVELS[i] + ": " + killCntByAdj[i]);
        }
        printWriter.println();
        printWriter.println("details:");
        for (int size = this.mAdjKillCnt.size() - 1; size >= 0; size += -1) {
            int keyAt = this.mAdjKillCnt.keyAt(size);
            printWriter.println("killed " + this.mAdjKillCnt.valueAt(size) + " at adj " + keyAt);
        }
        printWriter.println();
    }

    public int[] getKillCntByAdj() {
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        int length = iArr.length - 1;
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < this.mAdjKillCnt.size(); i++) {
            int keyAt = this.mAdjKillCnt.keyAt(i);
            while (keyAt > ChimeraCommonUtil.ADJ_LEVELS[length] && length - 1 >= 0) {
                iArr2[length] = iArr2[length + 1];
            }
            if (length < 0) {
                break;
            }
            iArr2[length] = iArr2[length] + this.mAdjKillCnt.valueAt(i);
        }
        while (length > 0) {
            length--;
            iArr2[length] = iArr2[length + 1];
        }
        return iArr2;
    }

    public void dumpAppInfo(PrintWriter printWriter) {
        printWriter.println("App-KillCount List :");
        for (Map.Entry entry : this.mAppKillCnt.entrySet()) {
            printWriter.println(((String) entry.getKey()) + ": " + entry.getValue());
        }
    }

    public void updateActionStatistics(ChimeraCommonUtil.TriggerSource triggerSource) {
        this.mActionCnt++;
        int[] iArr = this.mActionCntSrc;
        int ordinal = triggerSource.ordinal();
        iArr[ordinal] = iArr[ordinal] + 1;
    }

    public void updateKillStatistics(ChimeraAppInfo chimeraAppInfo, ChimeraCommonUtil.TriggerSource triggerSource) {
        int valueOf;
        this.mKillCnt++;
        int i = chimeraAppInfo.group;
        if (i > 0 && i <= 3) {
            int[] iArr = this.mKillCntByGrp;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        Integer num = (Integer) this.mAppKillCnt.get(chimeraAppInfo.packageName);
        if (num == null) {
            valueOf = 1;
        } else {
            valueOf = Integer.valueOf(num.intValue() + 1);
        }
        this.mAppKillCnt.put(chimeraAppInfo.packageName, valueOf);
        if (chimeraAppInfo.statsAndOomScores != null) {
            int i3 = 0;
            while (true) {
                int[] iArr2 = chimeraAppInfo.statsAndOomScores.mScores;
                if (i3 >= iArr2.length) {
                    break;
                }
                int i4 = iArr2[i3];
                if (i4 >= -1000 && i4 <= 1000) {
                    this.mAdjKillCnt.put(i4, this.mAdjKillCnt.get(i4) + 1);
                }
                i3++;
            }
        }
        addKillInfoToHistory(chimeraAppInfo, triggerSource);
    }

    public ChimeraDataInfo getChimeraStat() {
        ChimeraDataInfo chimeraDataInfo = new ChimeraDataInfo();
        chimeraDataInfo.setTriggerCntSrc(this.mTriggerCntSrc);
        chimeraDataInfo.setActionCntSrc(this.mActionCntSrc);
        chimeraDataInfo.setKillCnt(this.mKillCnt);
        chimeraDataInfo.setAvgReleasedMem(this.mAvgReleasedMem);
        chimeraDataInfo.setAvgAvaMem(this.mAvgAvailableMem);
        chimeraDataInfo.setLruWight(this.mWeightLru);
        chimeraDataInfo.setStdBktWeight(this.mWeightStandbyBucket);
        chimeraDataInfo.setMemWeight(this.mWeightMem);
        chimeraDataInfo.setTargetAvaMem(this.mChimeraStrategy.getFreeMemTarget(ChimeraCommonUtil.getAvailableMemoryKb(this.mSystemRepository)));
        chimeraDataInfo.setDynamicStrategyUse(false);
        chimeraDataInfo.setAdjKillCnt(getKillCntByAdj());
        chimeraDataInfo.setGroupKillCnt(this.mKillCntByGrp);
        return chimeraDataInfo;
    }

    @Override // com.android.server.chimera.SystemEventListener.BottleNeckHintListener
    public void onBottleNeckHintTriggered() {
        this.mSystemRepository.logDebug("PolicyHandler", "onBottleNeckHintTriggered()");
        executePolicy(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_BOTTLENECK_HINT, 0);
    }

    @Override // com.android.server.chimera.SystemEventListener.CarModeChangeListener
    public void onCarModeChanged(boolean z) {
        this.mIsCarMode = z;
    }

    @Override // com.android.server.chimera.SystemEventListener.PmmCriticalListener
    public void onPmmCriticalTriggered() {
        this.mSystemRepository.logDebug("PolicyHandler", "onPmmCriticalTriggered()");
        handlePMMCritical();
    }

    @Override // com.android.server.chimera.SystemEventListener.PmmStateChangeListener
    public void onPmmStateChanged(int i) {
        this.mSystemRepository.logDebug("PolicyHandler", "onPmmStateChange()");
        handlePmmStateChange(i);
    }

    public void handlePmmStateChange(int i) {
        this.mPmmStateHistory.onStateChanged(i);
    }

    @Override // com.android.server.chimera.SystemEventListener.LmkdEventListener
    public void onLmkdEventTriggered(int i, int i2, int i3) {
        this.mSystemRepository.logDebug("PolicyHandler", "onLmkdKillTriggered() - lmkdLevel: " + i + " type: " + i3);
        if (i3 != 1) {
            return;
        }
        executePolicy(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD, i);
        this.mAbnormalFgsDetector.reportAbnormalHeavyAppIfExist();
    }

    @Override // com.android.server.chimera.SystemEventListener.HomeLaunchListener
    public void onHomeLaunched() {
        this.mSystemRepository.logDebug("PolicyHandler", "onHomeLaunched()");
        executePolicy(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE, 0);
        filterAppListForQuickReclaim();
    }

    @Override // com.android.server.chimera.SystemEventListener.AppLaunchIntentListener
    public void onAppLaunchIntent(String str) {
        if (this.mQuickReclaimPreKillApps.isEmpty()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mQuickReclaimLastFilterTime > BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
            this.mSystemRepository.log("PolicyHandler", "filter overtime, quit quick reclaim");
            clearQuickReclaimFields();
            return;
        }
        boolean equals = "com.sec.android.app.camera".equals(str);
        boolean z = this.mSystemRepository.getCameraApps().contains(str) || (isQuickReclaimBigGameEnable() && this.mSystemRepository.getBigGameApps().contains(str));
        if (!equals && !z) {
            clearQuickReclaimFields();
        } else {
            quickKill(this.mQuickReclaimPreKillApps, str, currentTimeMillis, equals);
        }
    }

    public long getQuickReclaimReleaseTarget(long j) {
        return this.mChimeraStrategy.getFreeMemTarget(j);
    }

    public void quickKill(List list, String str, long j, boolean z) {
        long releaseTargetForBigApps;
        long quickReclaimReleaseTarget;
        Iterator it;
        long availableMemoryKb = ChimeraCommonUtil.getAvailableMemoryKb(this.mSystemRepository);
        if (z) {
            releaseTargetForBigApps = this.mChimeraStrategy.getFreeMemTarget(availableMemoryKb);
            quickReclaimReleaseTarget = this.mDynamicQuickReclaimAdditionalMemory;
        } else {
            releaseTargetForBigApps = getReleaseTargetForBigApps(str);
            quickReclaimReleaseTarget = getQuickReclaimReleaseTarget(availableMemoryKb);
        }
        long j2 = (releaseTargetForBigApps + quickReclaimReleaseTarget) - availableMemoryKb;
        if (j2 < 0) {
            clearQuickReclaimFields();
            this.mSystemRepository.log("PolicyHandler", "available memory: " + availableMemoryKb + ", quit chimera quick reclaim");
            return;
        }
        ChimeraCommonUtil.TriggerSource triggerSource = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_APP_LAUNCH_INTENT;
        this.mTriggerCnt++;
        int[] iArr = this.mTriggerCntSrc;
        int ordinal = triggerSource.ordinal();
        iArr[ordinal] = iArr[ordinal] + 1;
        updateActionStatistics(triggerSource);
        boolean supportsPidFd = Process.supportsPidFd();
        Iterator it2 = list.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        while (it2.hasNext()) {
            final ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it2.next();
            if (z2 || str.equals(chimeraAppInfo.packageName)) {
                it = it2;
                closeProcPidFds(chimeraAppInfo);
            } else if (isPidDeadOrReused(chimeraAppInfo)) {
                closeProcPidFds(chimeraAppInfo);
                it = it2;
                this.mSystemRepository.log("PolicyHandler", "quick reclaim skip " + chimeraAppInfo.getPidList() + ": " + chimeraAppInfo.packageName + " for process changed");
            } else {
                it = it2;
                if (chimeraAppInfo.procList.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$quickKill$0;
                        lambda$quickKill$0 = PolicyHandler.this.lambda$quickKill$0(chimeraAppInfo, (ChimeraAppInfo.ProcessInfo) obj);
                        return lambda$quickKill$0;
                    }
                })) {
                    closeProcPidFds(chimeraAppInfo);
                    this.mSystemRepository.log("PolicyHandler", "quick reclaim skip " + chimeraAppInfo.getPidList() + ": " + chimeraAppInfo.packageName + " for hasConnectionProvider");
                } else {
                    this.mLastKilledTimeMap.put(chimeraAppInfo.packageName, Long.valueOf(System.currentTimeMillis()));
                    SystemRepository systemRepository = this.mSystemRepository;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Killed on trigger");
                    sb.append(triggerSource.ordinal());
                    sb.append(" : ");
                    sb.append(chimeraAppInfo.packageName);
                    sb.append(chimeraAppInfo.getPidList());
                    sb.append(", freed: ");
                    long j3 = j2;
                    sb.append(chimeraAppInfo.pss);
                    systemRepository.log("PolicyHandler", sb.toString());
                    addRescheduleExceptionPackage(chimeraAppInfo.packageName);
                    for (final ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
                        if (!supportsPidFd) {
                            Process.killProcessQuiet(processInfo.pid);
                            this.mSystemRepository.log("PolicyHandler", "quick reclaim kill " + processInfo.pid + ": " + processInfo.processName + " without pidfd");
                        } else if (processInfo.pidFd != null) {
                            this.mThreadPoolExecutor.execute(new Runnable() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PolicyHandler.this.lambda$quickKill$1(processInfo);
                                }
                            });
                        }
                        i++;
                    }
                    i3 = (int) (i3 + chimeraAppInfo.pss);
                    i2++;
                    updateKillStatistics(chimeraAppInfo, triggerSource);
                    j2 = j3;
                    z2 = isRelTargetEnough(j2, i3) || i >= 4;
                }
            }
            it2 = it;
        }
        this.mQuickReclaimKillCnt.addAndGet(i);
        this.mQuickReclaimPreKillApps.clear();
        this.mSystemRepository.logDebug("PolicyHandler", "quick reclaim kill before " + str + " complete: killed " + i2 + " apps, freed " + i3 + " KB, before kill relTarget: " + j2 + " KB and Processing time(ms): " + (System.currentTimeMillis() - j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$quickKill$0(ChimeraAppInfo chimeraAppInfo, ChimeraAppInfo.ProcessInfo processInfo) {
        return this.mSystemRepository.hasConnectionProvider(processInfo.processName, chimeraAppInfo.uid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$quickKill$1(ChimeraAppInfo.ProcessInfo processInfo) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (Process.killProcessWithMrelease(processInfo.pidFd.getInt$())) {
                this.mSystemRepository.log("PolicyHandler", "finish quick reclaim for " + processInfo.pid + ": " + processInfo.processName + " and Processing time(ms)" + (System.currentTimeMillis() - currentTimeMillis));
            } else {
                this.mSystemRepository.log("PolicyHandler", "quick reclaim failed " + processInfo.pid + ": " + processInfo.processName + " by killProcessWithMrelease");
                this.mQuickReclaimKillCnt.decrementAndGet();
            }
        } catch (Exception e) {
            this.mSystemRepository.log("PolicyHandler", "quick reclaim kill with exception " + e.getMessage());
            this.mQuickReclaimKillCnt.decrementAndGet();
        }
    }

    public final boolean isPidDeadOrReused(ChimeraAppInfo chimeraAppInfo) {
        if (!chimeraAppInfo.procList.isEmpty() && chimeraAppInfo.packageName.equals(this.mSystemRepository.getPackageNameByPid(((ChimeraAppInfo.ProcessInfo) chimeraAppInfo.procList.get(0)).pid))) {
            return chimeraAppInfo.procList.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$isPidDeadOrReused$2;
                    lambda$isPidDeadOrReused$2 = PolicyHandler.this.lambda$isPidDeadOrReused$2((ChimeraAppInfo.ProcessInfo) obj);
                    return lambda$isPidDeadOrReused$2;
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isPidDeadOrReused$2(ChimeraAppInfo.ProcessInfo processInfo) {
        return !this.mSystemRepository.isThreadGroupLeader(processInfo.pid);
    }

    public boolean isRelTargetEnough(long j, int i) {
        long j2 = i;
        if (j2 > j) {
            return true;
        }
        if (j - j2 >= 20480) {
            return false;
        }
        this.mSystemRepository.log("PolicyHandler", "relTarget - released < 20480, stop kill");
        return true;
    }

    public void filterAppListForQuickReclaim() {
        if (this.mSettingRepository.isQuickReclaimEnable()) {
            long availableMemoryKb = ChimeraCommonUtil.getAvailableMemoryKb(this.mSystemRepository);
            if ((this.mChimeraStrategy.getFreeMemTarget(availableMemoryKb) - availableMemoryKb) + this.mDynamicQuickReclaimAdditionalMemory <= 0) {
                return;
            }
            List appsToKill = this.mAppManager.getAppsToKill(this.mSkipReasonLogger, this.mChimeraStrategy.getProtectedCountOnHomeTrigger() >> 1, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE);
            if (!isAppsEnough(appsToKill)) {
                SystemRepository systemRepository = this.mSystemRepository;
                StringBuilder sb = new StringBuilder();
                sb.append("executePolicy() - getAppsToKill return ");
                sb.append(appsToKill != null ? appsToKill.size() : 0);
                systemRepository.logDebug("PolicyHandler", sb.toString());
                return;
            }
            if (isCameraPidChanged()) {
                getCameraRelatedPids();
            }
            calcAppScoresIncludeSwap(appsToKill);
            appsToKill.sort(new Comparator() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$filterAppListForQuickReclaim$3;
                    lambda$filterAppListForQuickReclaim$3 = PolicyHandler.lambda$filterAppListForQuickReclaim$3((ChimeraAppInfo) obj, (ChimeraAppInfo) obj2);
                    return lambda$filterAppListForQuickReclaim$3;
                }
            });
            filterPreKillApp(appsToKill);
        }
    }

    public static /* synthetic */ int lambda$filterAppListForQuickReclaim$3(ChimeraAppInfo chimeraAppInfo, ChimeraAppInfo chimeraAppInfo2) {
        return Float.compare(chimeraAppInfo2.finalScore, chimeraAppInfo.finalScore);
    }

    public boolean isAppsEnough(List list) {
        return list != null && list.size() >= 3;
    }

    public void filterPreKillApp(List list) {
        this.mQuickReclaimLastFilterTime = System.currentTimeMillis();
        clearQuickReclaimFields();
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
            if (chimeraAppInfo.group >= 4) {
                this.mSystemRepository.logDebug("PolicyHandler", "killing stopped to group 4");
                return;
            } else if (isAppKillable(chimeraAppInfo) && (!Process.supportsPidFd() || openPidFds(chimeraAppInfo))) {
                i += chimeraAppInfo.procList.size();
                this.mQuickReclaimPreKillApps.add(chimeraAppInfo);
                if (i >= 4) {
                    return;
                }
            }
        }
    }

    public final boolean openPidFds(ChimeraAppInfo chimeraAppInfo) {
        ArrayList arrayList = new ArrayList();
        for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
            try {
                FileDescriptor openPidFd = Process.openPidFd(processInfo.pid, 0);
                if (openPidFd == null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        closePidFd((FileDescriptor) it.next());
                    }
                    return false;
                }
                arrayList.add(openPidFd);
                processInfo.pidFd = openPidFd;
            } catch (IOException unused) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    closePidFd((FileDescriptor) it2.next());
                }
                this.mSystemRepository.logDebug("PolicyHandler", "open pid " + processInfo.pid + ": " + processInfo.processName + " failed!");
                return false;
            }
        }
        return true;
    }

    public boolean isExpiredKillInterval(ChimeraAppInfo chimeraAppInfo) {
        long currentTimeMillis = System.currentTimeMillis() - ((Long) this.mLastKilledTimeMap.getOrDefault(chimeraAppInfo.packageName, 0L)).longValue();
        long j = (chimeraAppInfo.statsAndOomScores.isAllCachedEmptyProcess() || isService(chimeraAppInfo)) ? this.mCemPkgKillIntervalMs : this.mPkgProtectedParameters[this.mCurProtectLevel.ordinal()][2];
        if (currentTimeMillis >= j) {
            return true;
        }
        this.mSkipReasonLogger.mark(chimeraAppInfo, SkipReasonLogger.Reason.INTERVAL);
        this.mSystemRepository.logDebug("PolicyHandler", "Skipped by interval: " + chimeraAppInfo.packageName + ", elapsed: " + toHHmmss(currentTimeMillis) + ", interval: " + toHHmmss(j) + toAppInfoDescription(chimeraAppInfo));
        return false;
    }

    public boolean isAppKillable(ChimeraAppInfo chimeraAppInfo) {
        boolean z = false;
        if (chimeraAppInfo.group < 1) {
            this.mSystemRepository.logDebug("PolicyHandler", "Invalid app group id");
            return false;
        }
        ProcessStatsAndOomScores create = ProcessStatsAndOomScores.create(chimeraAppInfo, this.mSystemRepository);
        chimeraAppInfo.statsAndOomScores = create;
        if (create == null || hasProtectedAdjOrProcState(chimeraAppInfo)) {
            this.mSkipReasonLogger.mark(chimeraAppInfo, SkipReasonLogger.Reason.ADJ_OR_PROC_STATE);
            this.mSystemRepository.logDebug("PolicyHandler", "Skipped by adj     : " + chimeraAppInfo.packageName + toAppInfoDescription(chimeraAppInfo));
            return false;
        }
        if (this.mSystemRepository.isOnScreenWindow(chimeraAppInfo.uid)) {
            this.mSkipReasonLogger.mark(chimeraAppInfo, SkipReasonLogger.Reason.VISIBLE_SCREEN);
            this.mSystemRepository.logDebug("PolicyHandler", "Skipped by visible screen : " + chimeraAppInfo.packageName + toAppInfoDescription(chimeraAppInfo));
            return false;
        }
        if (this.mWakeLockManager.contains(chimeraAppInfo.packageName)) {
            markSkipReason(chimeraAppInfo, SkipReasonLogger.Reason.WAKELOCK);
            return false;
        }
        if ((chimeraAppInfo.appType & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) == 131072) {
            int hasProtectedServices = hasProtectedServices(chimeraAppInfo);
            ProtectedReason protectedReason = ProtectedReason.values()[hasProtectedServices];
            if ((this.mCurProtectLevel == ProtectLevel.NORMAL && hasProtectedServices > ProtectedReason.NONE.ordinal()) || (this.mCurProtectLevel == ProtectLevel.HEAVY && hasProtectedServices > ProtectedReason.NONE.ordinal() && protectedReason != ProtectedReason.ACTIVITY_TIME)) {
                this.mSkipReasonLogger.mark(chimeraAppInfo, SkipReasonLogger.Reason.SERVICE);
                this.mSystemRepository.logDebug("PolicyHandler", "Skipped by Normal Service condition: " + chimeraAppInfo.packageName + toAppInfoDescription(chimeraAppInfo) + " reason: " + protectedReason);
                return false;
            }
        }
        if (hasImportantAdjWithSystemUid(chimeraAppInfo)) {
            this.mSkipReasonLogger.mark(chimeraAppInfo, SkipReasonLogger.Reason.UID);
            this.mSystemRepository.logDebug("PolicyHandler", "Skipped by uid     : " + chimeraAppInfo.packageName + toAppInfoDescription(chimeraAppInfo));
            return false;
        }
        if (this.mSystemRepository.isLockTaskPackage(chimeraAppInfo.packageName)) {
            return false;
        }
        Iterator it = chimeraAppInfo.procList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ChimeraAppInfo.ProcessInfo processInfo = (ChimeraAppInfo.ProcessInfo) it.next();
            if (!this.mSystemRepository.isThreadGroupLeader(processInfo.pid)) {
                this.mSystemRepository.log("PolicyHandler", "Skipped by Thread Group Leader condition: " + chimeraAppInfo.packageName + " pid: " + processInfo.pid);
                z = true;
                break;
            }
        }
        return !z;
    }

    public int hasProtectedServices(ChimeraAppInfo chimeraAppInfo) {
        int hasChimeraProtectedProc;
        if (chimeraAppInfo != null && chimeraAppInfo.statsAndOomScores != null) {
            int i = 0;
            while (true) {
                ProcessStatsAndOomScores processStatsAndOomScores = chimeraAppInfo.statsAndOomScores;
                if (i >= processStatsAndOomScores.mPids.length) {
                    break;
                }
                if (processStatsAndOomScores.mScores[i] <= this.mPkgProtectedParameters[this.mCurProtectLevel.ordinal()][1] && chimeraAppInfo.statsAndOomScores.mScores[i] >= -1000 && (hasChimeraProtectedProc = this.mSystemRepository.hasChimeraProtectedProc(((ChimeraAppInfo.ProcessInfo) chimeraAppInfo.procList.get(i)).processName, chimeraAppInfo.uid)) > 0) {
                    return hasChimeraProtectedProc;
                }
                i++;
            }
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.chimera.SystemEventListener.DeviceIdleListener
    public void onDeviceIdle() {
        this.mSystemRepository.logDebug("PolicyHandler", "onDeviceIdle()");
        boolean isAppsIdleKillEnabled = this.mSettingRepository.isAppsIdleKillEnabled();
        int i = isAppsIdleKillEnabled;
        if (this.mSettingRepository.isNativeProcessesIdleKillEnabled()) {
            i = (isAppsIdleKillEnabled ? 1 : 0) | 2;
        }
        handleDeviceIdle(i);
    }

    public final void handleDeviceIdle(int i) {
        List<ChimeraAppInfo> appsToDeviceIdle = this.mAppManager.getAppsToDeviceIdle(i, this.mChimeraStrategy.getDeviceIdleAppThreshold() * 1024, this.mChimeraStrategy.getDeviceIdleNativeThreshold() * 1024);
        if (appsToDeviceIdle == null || appsToDeviceIdle.size() <= 0) {
            this.mSystemRepository.logDebug("PolicyHandler", "deviceIdle NULL!");
            return;
        }
        this.mSystemRepository.logDebug("PolicyHandler", "handleDiveceIdleApps kill enter");
        int i2 = 0;
        long j = 0;
        for (ChimeraAppInfo chimeraAppInfo : appsToDeviceIdle) {
            this.mSystemRepository.logDebug("PolicyHandler", "killHandleDiveceIdleApps " + chimeraAppInfo.packageName);
            chimeraAppInfo.statsAndOomScores = ProcessStatsAndOomScores.create(chimeraAppInfo, this.mSystemRepository);
            int i3 = i2;
            long j2 = j;
            long j3 = 0;
            for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
                long procPss = ChimeraCommonUtil.getProcPss(this.mSystemRepository, processInfo.pid);
                killProcessForDeviceIdle(chimeraAppInfo.packageName, chimeraAppInfo.uid, processInfo.processName, processInfo.pid, procPss, chimeraAppInfo.idleKillAdj);
                j3 += procPss;
                i3++;
                j2 += procPss;
            }
            updateKillStatistics(chimeraAppInfo, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_DEVICE_IDLE);
            reportIdleKill(chimeraAppInfo.packageName, (int) j3, chimeraAppInfo.idleKillAdj);
            i2 = i3;
            j = j2;
        }
        this.mSystemRepository.log("PolicyHandler", "handleDiveceIdleApps kill end. killed count:" + i2 + " released:" + j);
    }

    public void killProcessForDeviceIdle(String str, int i, String str2, int i2, long j, int i3) {
        if (i3 == -1000) {
            Process.killProcessQuiet(i2);
        } else {
            addRescheduleExceptionPackage(str);
            this.mSystemRepository.killProcessForChimera(str2, i, "Chimera DeviceIdle");
        }
        this.mSystemRepository.log("PolicyHandler", "killProcessForDeviceIdle - killed:" + i + "/" + str + " pid:" + i2 + " pss:" + j);
    }

    public final void clearRescheduleExceptionPackages() {
        RestartImmediatePackages.getInstance().clearAll();
    }

    public final void updatePowerWhitelistedApps() {
        this.mPowerWhitelistedApps = this.mSystemRepository.getFullPowerWhitelist();
    }

    public boolean isPowerWhitelistedApp(String str) {
        List list = this.mPowerWhitelistedApps;
        return list != null && list.contains(str);
    }

    /* renamed from: com.android.server.chimera.PolicyHandler$1 */
    public abstract /* synthetic */ class AbstractC09801 {

        /* renamed from: $SwitchMap$com$android$server$chimera$ChimeraCommonUtil$TriggerSource */
        public static final /* synthetic */ int[] f1668x30947b05;

        static {
            int[] iArr = new int[ChimeraCommonUtil.TriggerSource.values().length];
            f1668x30947b05 = iArr;
            try {
                iArr[ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1668x30947b05[ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public boolean prepareForTrigger(ChimeraCommonUtil.TriggerSource triggerSource) {
        int i = AbstractC09801.f1668x30947b05[triggerSource.ordinal()];
        if ((i == 1 || i == 2) && this.mSystemRepository.isScreenOff()) {
            this.mSystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: screen off");
            return false;
        }
        if (this.mSystemRepository.isInCall()) {
            this.mSystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: incall");
            return false;
        }
        if (this.mSystemRepository.isSmartSwitchWorking()) {
            this.mSystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: smart switch");
            return false;
        }
        if (this.mIsCarMode) {
            this.mSystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: car mode");
            return false;
        }
        this.mWakeLockManager.update();
        clearRescheduleExceptionPackages();
        if (this.mChimeraStrategy.isEnableDynamicFreeMem()) {
            this.mIsKillBoostModeOnNormal = false;
            this.mIsKillBoostModeOnHeavy = false;
        }
        updatePowerWhitelistedApps();
        return true;
    }

    public void handlePMMCritical() {
        if (!prepareForTrigger(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_PMM_CRITICAL)) {
            this.mSystemRepository.log("PolicyHandler", "handlePMMCritical() - prepareForTrigger fails");
            return;
        }
        this.mPMMTriggerCnt++;
        Pair appsForCritical = this.mAppManager.getAppsForCritical(null);
        if (appsForCritical == null) {
            this.mSystemRepository.logDebug("PolicyHandler", "getAppsForPMMCritical fail!");
        } else {
            handlePMMCriticalLockScreenApps((List) appsForCritical.first);
            handlePMMCriticalOtherApps((List) appsForCritical.second);
        }
    }

    public void handlePMMCriticalOtherApps(List list) {
        int i;
        long j;
        int[] iArr;
        int i2;
        String[] strArr;
        this.mSystemRepository.logDebug("PolicyHandler", "handlePMMCriticalOtherApps enter");
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                i3 += ((ChimeraAppInfo) it.next()).procList.size();
            }
            if (i3 > 0) {
                int[] iArr2 = new int[i3];
                int[] iArr3 = new int[i3];
                long[] jArr = new long[i3];
                int[] iArr4 = new int[i3];
                String[] strArr2 = new String[i3];
                String[] strArr3 = new String[i3];
                Iterator it2 = list.iterator();
                int i4 = 0;
                int i5 = 0;
                while (it2.hasNext()) {
                    ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it2.next();
                    for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
                        iArr3[i4] = i5;
                        jArr[i4] = processInfo.pss;
                        iArr2[i4] = processInfo.pid;
                        iArr4[i4] = chimeraAppInfo.uid;
                        strArr2[i4] = processInfo.processName;
                        strArr3[i4] = chimeraAppInfo.packageName;
                        i4++;
                        iArr3 = iArr3;
                    }
                    i5++;
                }
                Pair processStatesAndOomScoresForPIDs = this.mSystemRepository.getProcessStatesAndOomScoresForPIDs(iArr2);
                if (processStatesAndOomScoresForPIDs != null && (iArr = (int[]) processStatesAndOomScoresForPIDs.second) != null) {
                    int i6 = 0;
                    int i7 = 0;
                    long j2 = 0;
                    while (i6 < i3) {
                        int i8 = iArr[i6];
                        if (i8 < 100 || i8 >= 300 || this.mAppManager.getAppStandbyBucket(strArr3[i6], iArr4[i6]) == 5) {
                            i2 = i6;
                            strArr = strArr3;
                        } else {
                            i2 = i6;
                            strArr = strArr3;
                            killProcessForPMMCritical(strArr3[i6], iArr4[i6], strArr2[i6], iArr2[i6], iArr[i6], jArr[i6], false);
                            i7++;
                            j2 += jArr[i2];
                        }
                        i6 = i2 + 1;
                        strArr3 = strArr;
                    }
                    i = i7;
                    j = j2;
                    this.mPMMKillCnt += i;
                    this.mSystemRepository.log("PolicyHandler", "handlePMMCriticalOtherApps end. killed count:" + i + " released:" + j);
                }
            }
        }
        i = 0;
        j = 0;
        this.mPMMKillCnt += i;
        this.mSystemRepository.log("PolicyHandler", "handlePMMCriticalOtherApps end. killed count:" + i + " released:" + j);
    }

    public void handlePMMCriticalLockScreenApps(List list) {
        this.mSystemRepository.logDebug("PolicyHandler", "handlePMMCriticalLockScreenApps enter");
        int i = 0;
        long j = 0;
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            long j2 = 0;
            while (it.hasNext()) {
                ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
                this.mSystemRepository.logDebug("PolicyHandler", "handlePMMCriticalLockScreenApps " + chimeraAppInfo.packageName);
                int i2 = i;
                long j3 = j2;
                for (ChimeraAppInfo.ProcessInfo processInfo : chimeraAppInfo.procList) {
                    if (processInfo.initialIdlePss > 0) {
                        long procPss = ChimeraCommonUtil.getProcPss(this.mSystemRepository, processInfo.pid);
                        this.mSystemRepository.logDebug("PolicyHandler", "handlePMMCriticalLockScreenApps : pid:" + processInfo.pid + " " + procPss + "/" + processInfo.initialIdlePss);
                        if (procPss - processInfo.initialIdlePss > 102400 && procPss > ((int) (r1 * 1.2f))) {
                            killProcessForPMMCritical(chimeraAppInfo.packageName, chimeraAppInfo.uid, processInfo.processName, processInfo.pid, -1, procPss, true);
                            i2++;
                            j3 += procPss;
                        }
                    }
                }
                i = i2;
                j2 = j3;
            }
            j = j2;
        }
        this.mPMMKillCnt += i;
        this.mSystemRepository.log("PolicyHandler", "handlePMMCriticalLockScreenApps end. killed count:" + i + " released:" + j);
    }

    public void killProcessForPMMCritical(String str, int i, String str2, int i2, int i3, long j, boolean z) {
        addRescheduleExceptionPackage(str);
        this.mSystemRepository.killProcessForChimera(str2, i, z ? "Chimera PMM2" : "Chimera PMM1");
        addPmmKillInfoToHistory(str, i2, i3, j);
        SystemRepository systemRepository = this.mSystemRepository;
        StringBuilder sb = new StringBuilder();
        sb.append("killProcessForPMMCritical - killed:");
        sb.append(i);
        sb.append("/");
        sb.append(str);
        sb.append(" adj:");
        sb.append(i3);
        sb.append(" pss:");
        sb.append(j);
        sb.append(z ? " LockScreenApp" : "");
        systemRepository.log("PolicyHandler", sb.toString());
    }

    @Override // com.android.server.chimera.SystemEventListener.AlwaysRunningQuotaExceedListener
    public void onQuotaExceed(ChimeraQuotaMonitor.QuotaReclaimTarget quotaReclaimTarget) {
        Pair appsForCritical;
        Object obj;
        this.mSystemRepository.log("PolicyHandler", "onQuotaExceed()");
        mQuotaExceedCnt++;
        long j = quotaReclaimTarget.releaseTarget;
        List list = quotaReclaimTarget.killTargets;
        if (list != null && !list.isEmpty() && (appsForCritical = this.mAppManager.getAppsForCritical(list)) != null && (obj = appsForCritical.second) != null) {
            List<ChimeraAppInfo> list2 = (List) obj;
            if (!list2.isEmpty()) {
                list2.sort(new Comparator() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        int lambda$onQuotaExceed$4;
                        lambda$onQuotaExceed$4 = PolicyHandler.this.lambda$onQuotaExceed$4((ChimeraAppInfo) obj2, (ChimeraAppInfo) obj3);
                        return lambda$onQuotaExceed$4;
                    }
                });
                ChimeraCommonUtil.TriggerSource triggerSource = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_QUOTA;
                this.mTriggerCnt++;
                int[] iArr = this.mTriggerCntSrc;
                int ordinal = triggerSource.ordinal();
                iArr[ordinal] = iArr[ordinal] + 1;
                int i = 0;
                for (ChimeraAppInfo chimeraAppInfo : list2) {
                    if (isVisibleOrPerceptible(chimeraAppInfo) && this.mAppManager.getAppStandbyBucket(chimeraAppInfo.packageName, chimeraAppInfo.uid) != 5) {
                        Iterator it = chimeraAppInfo.procList.iterator();
                        long j2 = 0;
                        while (it.hasNext()) {
                            j2 += ((ChimeraAppInfo.ProcessInfo) it.next()).DRAMUsed;
                        }
                        if (handleQuotaKill(chimeraAppInfo, triggerSource, j2)) {
                            i++;
                            mQuotaKillCnt++;
                            updateKillStatistics(chimeraAppInfo, triggerSource);
                            this.mLastKilledTimeMap.put(chimeraAppInfo.packageName, Long.valueOf(System.currentTimeMillis()));
                            j -= j2;
                            if (j < 0) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                long j3 = quotaReclaimTarget.releaseTarget - j;
                if (j3 != 0) {
                    updateActionStatistics(triggerSource);
                    this.mSystemRepository.logDebug("PolicyHandler", "Quota kill complete: killed " + i + " apps, freed " + j3 + " KB");
                } else {
                    this.mSystemRepository.logDebug("PolicyHandler", "No Quota kill target");
                }
                if (j < 0) {
                    return;
                }
            }
        }
        this.mSystemRepository.logDebug("PolicyHandler", "Quota is still not enough, need PPN do more " + j);
        PerProcessNandswap.getInstance().handleAlwaysRunningQuotaPPN(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$onQuotaExceed$4(ChimeraAppInfo chimeraAppInfo, ChimeraAppInfo chimeraAppInfo2) {
        int i = chimeraAppInfo.group - chimeraAppInfo2.group;
        return i != 0 ? i : Long.compare(getDRAMUsedByApp(chimeraAppInfo2), getDRAMUsedByApp(chimeraAppInfo));
    }

    public void addKillInfoToHistory(ChimeraAppInfo chimeraAppInfo, ChimeraCommonUtil.TriggerSource triggerSource) {
        if (this.mKillHistoryBuffer == null || chimeraAppInfo == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(formatDateTimeWithoutYear(this.mSystemRepository.currentTimeMillis()));
        sb.append(" ");
        sb.append(chimeraAppInfo.toBriefString());
        sb.append(" ");
        if (chimeraAppInfo.statsAndOomScores != null) {
            int i = 0;
            while (true) {
                int[] iArr = chimeraAppInfo.statsAndOomScores.mScores;
                if (i >= iArr.length) {
                    break;
                }
                sb.append(iArr[i]);
                if (i < chimeraAppInfo.statsAndOomScores.mScores.length - 1) {
                    sb.append(",");
                }
                i++;
            }
        }
        sb.append(" ");
        sb.append(triggerSource.ordinal());
        this.mKillHistoryBuffer.append(sb.toString());
    }

    public final void addPmmKillInfoToHistory(String str, int i, int i2, long j) {
        if (this.mKillHistoryBuffer == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(formatDateTimeWithoutYear(this.mSystemRepository.currentTimeMillis()));
        sb.append(" [PMM] packageName: " + str);
        sb.append(", pid: " + i);
        sb.append(", adj: " + i2);
        sb.append(", pss: " + j);
        this.mKillHistoryBuffer.append(sb.toString());
    }

    public void addRescheduleExceptionPackage(String str) {
        RestartImmediatePackages.getInstance().addPackage(str);
    }

    public final String formatDateTimeWithoutYear(long j) {
        if (j == 0) {
            return String.format("%18s", "null");
        }
        return ChimeraDataCache.getDateFormat("MM/dd HH:mm:ss.SSS").format(new Date(j));
    }

    public void dumpHistoryBuffer(PrintWriter printWriter) {
        if (this.mKillHistoryBuffer.size() > 0) {
            printWriter.println("Date Time PackageName AppType StandbyBucket PSS PIDs OomScores TriggerSource:");
            for (String str : (String[]) this.mKillHistoryBuffer.toArray()) {
                printWriter.println(str);
            }
            printWriter.println("");
        }
    }

    public String toAppInfoDescription(ChimeraAppInfo chimeraAppInfo) {
        StringBuilder sb = new StringBuilder();
        if (chimeraAppInfo != null) {
            sb.append(", ");
            sb.append(chimeraAppInfo.toString(false));
            if (chimeraAppInfo.statsAndOomScores != null) {
                sb.append(", ");
                sb.append(chimeraAppInfo.statsAndOomScores.toString());
            }
        }
        return sb.toString();
    }

    public String toHHmmss(long j) {
        long j2 = j / 1000;
        return String.format("%02d:%02d:%02d", Long.valueOf(j2 / 3600), Long.valueOf((j2 % 3600) / 60), Long.valueOf(j2 % 60));
    }

    public void resetLastKilledTime(String str) {
        this.mLastKilledTimeMap.put(str, 0L);
    }

    public final void registerProcessObserver() {
        this.mSystemRepository.registerProcessObserver(this);
    }

    public void calcAppScores(List list) {
        Iterator it = list.iterator();
        long j = 0;
        int i = 1;
        while (it.hasNext()) {
            ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
            long j2 = chimeraAppInfo.reclaimGain;
            if (j2 > j) {
                j = j2;
            }
            int i2 = chimeraAppInfo.lruIdx;
            if (i2 > i) {
                i = i2;
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChimeraAppInfo chimeraAppInfo2 = (ChimeraAppInfo) it2.next();
            float f = (((this.mWeightLru * chimeraAppInfo2.lruIdx) / i) + ((this.mWeightStandbyBucket * chimeraAppInfo2.cacStandbyBucket) / 50.0f) + ((this.mWeightMem * chimeraAppInfo2.reclaimGain) / j)) * 100.0f;
            chimeraAppInfo2.score = f;
            chimeraAppInfo2.finalScore = ((4 - chimeraAppInfo2.group) * 100.0f) + f;
        }
    }

    public void calcAppScoresIncludeSwap(List list) {
        Iterator it = list.iterator();
        long j = 0;
        int i = 1;
        while (it.hasNext()) {
            ChimeraAppInfo chimeraAppInfo = (ChimeraAppInfo) it.next();
            long j2 = chimeraAppInfo.reclaimGain - chimeraAppInfo.swapPss;
            if (j2 > j) {
                j = j2;
            }
            int i2 = chimeraAppInfo.lruIdx;
            if (i2 > i) {
                i = i2;
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChimeraAppInfo chimeraAppInfo2 = (ChimeraAppInfo) it2.next();
            float f = (((chimeraAppInfo2.lruIdx * 0.5f) / i) + ((chimeraAppInfo2.cacStandbyBucket * 0.1f) / 50.0f) + (((chimeraAppInfo2.reclaimGain - chimeraAppInfo2.swapPss) * 0.4f) / j)) * 100.0f;
            chimeraAppInfo2.score = f;
            chimeraAppInfo2.finalScore = ((4 - chimeraAppInfo2.group) * 100.0f) + f;
        }
    }

    public void printAllAppInfo(List list) {
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            sb.append((ChimeraAppInfo) it.next());
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        this.mSystemRepository.logDebug("PolicyHandler", sb.toString());
    }

    public void performGcAndReclaim() {
        if (this.mSettingRepository.isGcEnabled()) {
            this.mChimeraAppReclaim.performGc(this.mAppManager.getAppsToGc());
        }
        if (this.mSettingRepository.isReclaimPageCacheEnabled()) {
            this.mChimeraAppReclaim.performReclaimCache(this.mAppManager.getAppsToReclaim());
        }
    }

    public boolean isGcReclaimEnabled() {
        return this.mSettingRepository.isGcEnabled() || this.mSettingRepository.isReclaimPageCacheEnabled();
    }

    public boolean isReusedPid(ChimeraAppInfo chimeraAppInfo) {
        return chimeraAppInfo.procList.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isReusedPid$5;
                lambda$isReusedPid$5 = PolicyHandler.this.lambda$isReusedPid$5((ChimeraAppInfo.ProcessInfo) obj);
                return lambda$isReusedPid$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isReusedPid$5(ChimeraAppInfo.ProcessInfo processInfo) {
        return !this.mSystemRepository.isThreadGroupLeader(processInfo.pid);
    }

    public boolean isSystemPid(ChimeraAppInfo chimeraAppInfo) {
        final int systemPid = this.mSystemRepository.getSystemPid();
        return chimeraAppInfo.procList.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isSystemPid$6;
                lambda$isSystemPid$6 = PolicyHandler.lambda$isSystemPid$6(systemPid, (ChimeraAppInfo.ProcessInfo) obj);
                return lambda$isSystemPid$6;
            }
        });
    }

    public static /* synthetic */ boolean lambda$isSystemPid$6(int i, ChimeraAppInfo.ProcessInfo processInfo) {
        return processInfo.pid == i;
    }

    public void markSkipReason(ChimeraAppInfo chimeraAppInfo, SkipReasonLogger.Reason reason) {
        this.mSkipReasonLogger.mark(chimeraAppInfo, reason);
        this.mSystemRepository.logDebug("PolicyHandler", "Skipped by " + reason.toString() + " " + chimeraAppInfo.packageName + " " + toAppInfoDescription(chimeraAppInfo));
    }

    public void markSkipReason(ChimeraAppInfo chimeraAppInfo, SkipReasonLogger.Reason reason, String str) {
        this.mSkipReasonLogger.mark(chimeraAppInfo, reason);
        this.mSystemRepository.logDebug("PolicyHandler", "Skipped by " + reason.toString() + " " + chimeraAppInfo.packageName + " " + toAppInfoDescription(chimeraAppInfo) + " " + str);
    }

    public final boolean isQuickReclaimBigGameEnable() {
        return SystemProperties.getBoolean("ro.slmk.chimera.quickreclaim_big_game_enable", false);
    }

    public final String getHeavyAppsToString() {
        String str = "Camera:" + this.mSystemRepository.getCameraApps();
        if (!isQuickReclaimBigGameEnable()) {
            return str;
        }
        return str + ", BigGame:" + this.mSystemRepository.getBigGameApps();
    }

    public final long getReleaseTargetForBigApps(String str) {
        Long l = (Long) this.mBigAppPssMap.get(str);
        if (l == null) {
            return 102400L;
        }
        long longValue = ((l.longValue() - 409600) / 2) + 102400;
        if (longValue < 102400) {
            return 102400L;
        }
        int i = this.mDefaultQuickReclaimAdditionalMemory;
        return longValue > ((long) i) ? i : longValue;
    }

    public final void updateBigApps(int i, String str) {
        if (!this.mSettingRepository.isQuickReclaimEnable() || this.mSystemRepository.getBigGameApps().contains(str) || "com.sec.android.app.camera".equals(str)) {
            return;
        }
        Long l = (Long) this.mBigAppPssMap.get(str);
        Long l2 = NOT_HEAVY_PSS;
        if (l2.equals(l)) {
            return;
        }
        if (l == null) {
            boolean contains = this.mSystemRepository.getGameApps().contains(str);
            boolean contains2 = this.mSystemRepository.getCameraApps().contains(str);
            if (!contains && !contains2) {
                this.mBigAppPssMap.put(str, l2);
                return;
            }
            long pss = this.mSystemRepository.getPss(i, null);
            if (contains && pss > 409600) {
                this.mSystemRepository.getBigGameApps().update(str);
            }
            this.mBigAppPssMap.put(str, Long.valueOf(pss));
            return;
        }
        long pss2 = this.mSystemRepository.getPss(i, null);
        if (!this.mSystemRepository.getGameApps().contains(str)) {
            this.mBigAppPssMap.put(str, Long.valueOf(pss2));
        } else if (pss2 > 409600) {
            this.mSystemRepository.getBigGameApps().update(str);
            this.mBigAppPssMap.put(str, Long.valueOf(pss2));
        }
    }

    public final void clearQuickReclaimFields() {
        if (this.mQuickReclaimPreKillApps.isEmpty()) {
            return;
        }
        Iterator it = this.mQuickReclaimPreKillApps.iterator();
        while (it.hasNext()) {
            closeProcPidFds((ChimeraAppInfo) it.next());
        }
        this.mQuickReclaimPreKillApps.clear();
    }

    public final void closePidFd(FileDescriptor fileDescriptor) {
        try {
            IoUtils.closeQuietly(fileDescriptor);
        } catch (RuntimeException unused) {
        }
    }

    public final void closeProcPidFds(ChimeraAppInfo chimeraAppInfo) {
        Iterator it = chimeraAppInfo.procList.iterator();
        while (it.hasNext()) {
            FileDescriptor fileDescriptor = ((ChimeraAppInfo.ProcessInfo) it.next()).pidFd;
            if (fileDescriptor != null) {
                closePidFd(fileDescriptor);
            }
        }
    }

    public final boolean isCameraPidChanged() {
        String str;
        if (this.mIsDynamicCameraMemorySuccess) {
            return false;
        }
        for (SystemRepository.CameraProcInfo cameraProcInfo : this.mCameraRelateInfos) {
            if (cameraProcInfo.pid == 0) {
                return true;
            }
            String[] strArr = new String[1];
            if (!Process.readProcFile("/proc/" + cameraProcInfo.pid + "/cmdline", CMDLINE_OUT, strArr, null, null) || (str = strArr[0]) == null || !str.contains(cameraProcInfo.name)) {
                return true;
            }
        }
        return false;
    }

    public final long getRssAndSwap(int i) {
        long[] rss = this.mSystemRepository.getRss(i);
        return rss[0] + rss[3];
    }

    public final void getCameraRelatedPids() {
        String str;
        SystemRepository.CameraProcInfo cameraProcInfo;
        long currentTimeMillis = System.currentTimeMillis();
        String[] list = new File("/proc").list();
        if (list == null) {
            return;
        }
        String[] strArr = new String[1];
        int i = 0;
        for (Integer num : getPidsFromProcName(list)) {
            if (num.intValue() >= 100) {
                if (Process.readProcFile("/proc/" + num + "/cmdline", CMDLINE_OUT, strArr, null, null) && (str = strArr[0]) != null) {
                    if (str.contains(this.mCameraProviderInfo.name)) {
                        cameraProcInfo = this.mCameraProviderInfo;
                    } else if (str.contains(this.mCameraAppInfo.name)) {
                        cameraProcInfo = this.mCameraAppInfo;
                    } else if (str.contains(this.mCameraServerInfo.name)) {
                        cameraProcInfo = this.mCameraServerInfo;
                    } else {
                        continue;
                    }
                    i++;
                    cameraProcInfo.pid = num.intValue();
                    this.mSystemRepository.log("PolicyHandler", "get camera process's pid: " + cameraProcInfo.pid + " name: " + cameraProcInfo.name);
                    if (i >= this.mCameraRelateInfos.size()) {
                        break;
                    }
                }
            }
        }
        this.mSystemRepository.log("PolicyHandler", "get camera process's pid success and Processing time(ms): " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public final List getPidsFromProcName(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            try {
                arrayList.add(Integer.valueOf(Integer.parseInt(str)));
            } catch (NumberFormatException unused) {
            }
        }
        return arrayList;
    }

    public static /* synthetic */ boolean lambda$isCameraPidInit$7(SystemRepository.CameraProcInfo cameraProcInfo) {
        return cameraProcInfo.pid != 0;
    }

    public final boolean isCameraPidInit() {
        return this.mCameraRelateInfos.stream().allMatch(new Predicate() { // from class: com.android.server.chimera.PolicyHandler$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isCameraPidInit$7;
                lambda$isCameraPidInit$7 = PolicyHandler.lambda$isCameraPidInit$7((SystemRepository.CameraProcInfo) obj);
                return lambda$isCameraPidInit$7;
            }
        });
    }

    @Override // com.android.server.chimera.SystemEventListener.CameraStateListener
    public void onCameraOpen() {
        if (this.mIsDynamicCameraMemorySuccess || !isCameraPidInit()) {
            return;
        }
        for (SystemRepository.CameraProcInfo cameraProcInfo : this.mCameraRelateInfos) {
            cameraProcInfo.openRss = getRssAndSwap(cameraProcInfo.pid);
            this.mSystemRepository.logDebug("PolicyHandler", "pid: " + cameraProcInfo.pid + " name: " + cameraProcInfo.name + " openRss: " + cameraProcInfo.openRss);
        }
    }

    @Override // com.android.server.chimera.SystemEventListener.CameraStateListener
    public void onCameraClose() {
        if (this.mIsDynamicCameraMemorySuccess || !isCameraPidInit()) {
            return;
        }
        long j = 0;
        for (SystemRepository.CameraProcInfo cameraProcInfo : this.mCameraRelateInfos) {
            cameraProcInfo.closeRss = getRssAndSwap(cameraProcInfo.pid);
            this.mSystemRepository.logDebug("PolicyHandler", "pid: " + cameraProcInfo.pid + " name: " + cameraProcInfo.name + " closeRss: " + cameraProcInfo.closeRss);
            j += cameraProcInfo.openRss - cameraProcInfo.closeRss;
        }
        int i = (int) j;
        int i2 = this.mDefaultQuickReclaimAdditionalMemory >> 1;
        int min = Math.min(Math.max(i2, i), this.mDefaultQuickReclaimAdditionalMemory + i2);
        this.mIsDynamicCameraMemorySuccess = true;
        this.mDynamicQuickReclaimAdditionalMemory = min;
        this.mSystemRepository.logDebug("PolicyHandler", "get dynamic quick reclaim additional memory successfully " + min + " (MB)");
    }

    public void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
        String str = strArr[0];
        if (!z2) {
            if (z) {
                if (this.mSettingRepository.isGcEnabled()) {
                    this.mChimeraAppReclaim.updateGcPackage(str);
                }
                if (this.mSettingRepository.isReclaimPageCacheEnabled()) {
                    this.mChimeraAppReclaim.updatePackageCacheReclaimable(str);
                }
                resetLastKilledTime(str);
            } else {
                updateBigApps(i, str);
            }
        }
        if (str.equals("com.samsung.android.permissioncontroller") || str.equals("com.google.android.permissioncontroller")) {
            return;
        }
        if (!this.mSettingRepository.isAppCacheReclaimEnable()) {
            ChimeraCommonUtil.clearAppLaunchInfoMap();
            return;
        }
        if (z2 || z) {
            return;
        }
        if (ChimeraCommonUtil.isColdLaunch(str) || ChimeraCommonUtil.isWarmLaunch(str)) {
            Message obtain = Message.obtain(this.mHandler, 1);
            obtain.obj = str;
            this.mHandler.sendMessage(obtain);
        } else {
            this.mSystemRepository.logDebug("PolicyHandler", "Chimera AppFileCacheReclaim do reclaimAppCaches skip: " + str);
        }
    }

    public final void reclaimAppCaches(String str) {
        List<String> appFilePathsByPackageName = this.mSystemRepository.getAppFilePathsByPackageName(str);
        if (appFilePathsByPackageName != null) {
            for (String str2 : appFilePathsByPackageName) {
                if (ChimeraCommonUtil.doReclaimPageCacheByFilePath(str2)) {
                    String[] split = str2.split("/");
                    String str3 = split[split.length - 1];
                    str3.hashCode();
                    switch (str3) {
                        case "base.odex":
                            long[] jArr = this.mAppFileCacheRecliamCnt;
                            jArr[1] = jArr[1] + 1;
                            break;
                        case "base.vdex":
                            long[] jArr2 = this.mAppFileCacheRecliamCnt;
                            jArr2[2] = jArr2[2] + 1;
                            break;
                        case "base.apk":
                            long[] jArr3 = this.mAppFileCacheRecliamCnt;
                            jArr3[0] = jArr3[0] + 1;
                            break;
                        default:
                            long[] jArr4 = this.mAppFileCacheRecliamCnt;
                            jArr4[3] = jArr4[3] + 1;
                            break;
                    }
                } else {
                    this.mSystemRepository.logDebug("PolicyHandler", "Chimera AppFileCacheReclaim doReclaimPageCacheByFilePath failed.");
                }
            }
        }
    }

    public void reportIdleKill(String str, int i, int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", 101);
            jSONObject.put("PNAME", str);
            jSONObject.put("PSS", i);
            jSONObject.put("ADJ", i2);
            String jSONObject2 = jSONObject.toString();
            String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
            if (TextUtils.isEmpty(substring)) {
                return;
            }
            this.mSystemRepository.sendHqmBigData("KPUT", substring);
        } catch (JSONException unused) {
            this.mSystemRepository.log("PolicyHandler", "failed to create the KPUT");
        }
    }

    public class PolicyEventHandler extends Handler {
        public PolicyEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            String str = (String) message.obj;
            if (str != null) {
                PolicyHandler.this.reclaimAppCaches(str);
            }
            ChimeraCommonUtil.clearAppLaunchInfoMap();
        }
    }

    public boolean isService(ChimeraAppInfo chimeraAppInfo) {
        if ((chimeraAppInfo.appType & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) == 0) {
            return false;
        }
        for (int i : chimeraAppInfo.statsAndOomScores.mScores) {
            if (i == 200 || i == 225 || i == 250 || i == 500 || i == 800) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleOrPerceptible(ChimeraAppInfo chimeraAppInfo) {
        ProcessStatsAndOomScores create = ProcessStatsAndOomScores.create(chimeraAppInfo, this.mSystemRepository);
        chimeraAppInfo.statsAndOomScores = create;
        if (create == null) {
            return false;
        }
        for (int i : create.mScores) {
            if (i < 100 || i > 250) {
                return false;
            }
        }
        return true;
    }

    public long getDRAMUsedByApp(ChimeraAppInfo chimeraAppInfo) {
        Iterator it = chimeraAppInfo.procList.iterator();
        long j = 0;
        while (it.hasNext()) {
            j += ((ChimeraAppInfo.ProcessInfo) it.next()).DRAMUsed;
        }
        return j;
    }

    public boolean handleQuotaKill(ChimeraAppInfo chimeraAppInfo, ChimeraCommonUtil.TriggerSource triggerSource, long j) {
        String str;
        int i = chimeraAppInfo.group;
        if (i == 1) {
            if (chimeraAppInfo.hasRestartService(this.mSystemRepository)) {
                SystemRepository systemRepository = this.mSystemRepository;
                systemRepository.forceStop(chimeraAppInfo.packageName, systemRepository.getUserId(chimeraAppInfo.uid));
                str = "Force-stop";
            } else {
                Iterator it = chimeraAppInfo.procList.iterator();
                while (it.hasNext()) {
                    this.mSystemRepository.killProcessForChimera(((ChimeraAppInfo.ProcessInfo) it.next()).processName, chimeraAppInfo.uid, "Chimera Quota Kill");
                }
                str = "Killed";
            }
        } else {
            if (i > 3) {
                return false;
            }
            Iterator it2 = chimeraAppInfo.procList.iterator();
            while (it2.hasNext()) {
                this.mSystemRepository.killProcessForChimera(((ChimeraAppInfo.ProcessInfo) it2.next()).processName, chimeraAppInfo.uid, "Chimera Quota Kill");
            }
            RestartImmediatePackages.getInstance().addPackage(chimeraAppInfo.packageName, Boolean.TRUE);
            str = "Kill and delay";
        }
        this.mSystemRepository.log("PolicyHandler", str + " on trigger" + triggerSource.ordinal() + " : " + chimeraAppInfo.packageName + chimeraAppInfo.getPidList() + ", freed: " + j);
        return true;
    }
}
