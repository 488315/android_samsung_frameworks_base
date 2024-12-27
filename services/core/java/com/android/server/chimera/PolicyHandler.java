package com.android.server.chimera;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPolicyManager;
import com.android.server.chimera.AbnormalFgsDetector;
import com.android.server.chimera.ChimeraAppInfo;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.SystemEventListener;
import com.android.server.chimera.SystemRepository;
import com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor;
import com.android.server.chimera.ppn.ChimeraQuotaMonitor;
import com.android.server.chimera.ppn.PerProcessNandswap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PolicyHandler implements SystemEventListener.LmkdEventListener, SystemEventListener.HomeLaunchListener, SystemEventListener.CarModeChangeListener, SystemRepository.ChimeraProcessObserver, SystemEventListener.AppLaunchIntentListener, SystemEventListener.CameraStateListener, SystemEventListener.AlwaysRunningQuotaExceedListener, SystemEventListener.DeviceIdleListener {
    public final AbnormalFgsDetector mAbnormalFgsDetector;
    public int mAlwaysRunningQuotaKillCnt;
    public int mAlwaysRunningQuotaKillTriggerCnt;
    public final ChimeraAppManager mAppManager;
    public final SystemRepository.CameraProcInfo mCameraAppInfo;
    public final SystemRepository.CameraProcInfo mCameraProviderInfo;
    public final List mCameraRelateInfos;
    public final SystemRepository.CameraProcInfo mCameraServerInfo;
    public int mCemPkgKillIntervalMs;
    public final ChimeraStrategy mChimeraStrategy;
    public final int mDefaultQuickReclaimAdditionalMemory;
    public int mDynamicQuickReclaimAdditionalMemory;
    public final PolicyEventHandler mHandler;
    public boolean mIsDynamicCameraMemorySuccess;
    public final RingBuffer mKillHistoryBuffer;
    public final int mPkgKillIntervalMs;
    public int[][] mPkgProtectedParameters;
    public List mPowerWhitelistedApps;
    public long mQuickReclaimLastFilterTime;
    public final SettingRepository mSettingRepository;
    public SkipReasonLogger mSkipReasonLogger;
    public final SystemRepository mSystemRepository;
    public final WakeLockManager mWakeLockManager;
    public static final String CEM_PKG_KILL_INTERVAL_DEFAULT = String.valueOf(600000);
    public static final int PICKED_OOM_ADJ = FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED;
    public static boolean mIsBubEnabled = false;
    public static final int[] CMDLINE_OUT = {4096};
    public final SparseIntArray mAdjKillCnt = new SparseIntArray(32);
    public final ArrayMap mAppKillCnt = new ArrayMap();
    public float mWeightLru = 0.3f;
    public float mWeightStandbyBucket = 0.3f;
    public float mWeightMem = 0.4f;
    public boolean mIsKillBoostModeOnNormal = false;
    public boolean mIsKillBoostModeOnHeavy = false;
    public int mTriggerCnt = 0;
    public final int[] mTriggerCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
    public final int[] mActionCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
    public int mActionCnt = 0;
    public int mNoActionCnt = 0;
    public int mKillCnt = 0;
    public final int[] mKillCntByGrp = {0, 0, 0};
    public long mAvgAvailableMem = 0;
    public long mAvgReleasedMem = 0;
    public final long[] mAppFileCacheRecliamCnt = {0, 0, 0, 0};
    public final Map mLastKilledTimeMap = new HashMap();
    public boolean mIsCarMode = false;
    public final List mQuickReclaimPreKillApps = new ArrayList();
    public final Map mBigAppPssMap = new ArrayMap();
    public final ProtectLevel mCurProtectLevel = ProtectLevel.NORMAL;
    public int mPkgKillIntervalHeavy = 43200000;
    public final AtomicInteger mQuickReclaimKillCnt = new AtomicInteger();
    public final ThreadPoolExecutor mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyEventHandler extends Handler {
        public PolicyEventHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0077  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r12) {
            /*
                Method dump skipped, instructions count: 320
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.PolicyHandler.PolicyEventHandler.handleMessage(android.os.Message):void");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProtectLevel {
        public static final /* synthetic */ ProtectLevel[] $VALUES;
        public static final ProtectLevel HEAVY;
        public static final ProtectLevel NORMAL;

        static {
            ProtectLevel protectLevel = new ProtectLevel("NORMAL", 0);
            NORMAL = protectLevel;
            ProtectLevel protectLevel2 = new ProtectLevel("HEAVY", 1);
            HEAVY = protectLevel2;
            $VALUES = new ProtectLevel[]{protectLevel, protectLevel2};
        }

        public static ProtectLevel valueOf(String str) {
            return (ProtectLevel) Enum.valueOf(ProtectLevel.class, str);
        }

        public static ProtectLevel[] values() {
            return (ProtectLevel[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProtectedReason {
        public static final /* synthetic */ ProtectedReason[] $VALUES;
        public static final ProtectedReason ACTIVITY_TIME;

        /* JADX INFO: Fake field, exist only in values array */
        ProtectedReason EF0;

        static {
            ProtectedReason protectedReason = new ProtectedReason("NONE", 0);
            ProtectedReason protectedReason2 = new ProtectedReason("ALREADY_DIED", 1);
            ProtectedReason protectedReason3 = new ProtectedReason("EXECUTING_SERVICE", 2);
            ProtectedReason protectedReason4 = new ProtectedReason("RUNNING_INTENT", 3);
            ProtectedReason protectedReason5 = new ProtectedReason("ACTIVITY_TIME", 4);
            ACTIVITY_TIME = protectedReason5;
            $VALUES = new ProtectedReason[]{protectedReason, protectedReason2, protectedReason3, protectedReason4, protectedReason5, new ProtectedReason("VISIBLE_ADJ", 5), new ProtectedReason("HAS_CONNECTION_PROVIDER", 6)};
        }

        public static ProtectedReason valueOf(String str) {
            return (ProtectedReason) Enum.valueOf(ProtectedReason.class, str);
        }

        public static ProtectedReason[] values() {
            return (ProtectedReason[]) $VALUES.clone();
        }
    }

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
        this.mWakeLockManager = new WakeLockManager(systemRepository);
        this.mKillHistoryBuffer = new RingBuffer(String.class, 200);
        this.mSettingRepository = settingRepository;
        this.mSkipReasonLogger = new SkipReasonLogger(systemRepository);
        this.mHandler = new PolicyEventHandler(looper);
        int i = chimeraStrategy.mQuickReclaimDefaultThreshold * 1024;
        this.mDefaultQuickReclaimAdditionalMemory = i;
        this.mDynamicQuickReclaimAdditionalMemory = i;
        this.mAbnormalFgsDetector = abnormalFgsDetector;
        String valueOf = String.valueOf(1800000);
        systemRepository.getClass();
        this.mPkgKillIntervalMs = Integer.parseInt(SystemProperties.get("persist.sys.chimera_pkg_kill_interval_ms", valueOf));
        this.mCemPkgKillIntervalMs = Integer.parseInt(SystemProperties.get("ro.slmk.chimera_cem_pkg_kill_interval_ms", CEM_PKG_KILL_INTERVAL_DEFAULT));
        this.mPkgProtectedParameters = new int[][]{new int[]{300, FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED, this.mPkgKillIntervalMs}, new int[]{300, FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED, this.mPkgKillIntervalHeavy}};
        systemRepository.registerProcessObserver(this);
    }

    public static void addRescheduleExceptionPackage(String str) {
        RestartImmediatePackages restartImmediatePackages = RestartImmediatePackages.getInstance();
        if (str == null) {
            restartImmediatePackages.getClass();
            return;
        }
        ((ConcurrentHashMap) restartImmediatePackages.sPackages).put(str, Boolean.FALSE);
    }

    public static void closeProcPidFds(ChimeraAppInfo chimeraAppInfo) {
        Iterator it = ((ArrayList) chimeraAppInfo.procList).iterator();
        while (it.hasNext()) {
            FileDescriptor fileDescriptor = ((ChimeraAppInfo.ProcessInfo) it.next()).pidFd;
            if (fileDescriptor != null) {
                try {
                    IoUtils.closeQuietly(fileDescriptor);
                } catch (RuntimeException unused) {
                }
            }
        }
    }

    public static long getDRAMUsedByApp(ChimeraAppInfo chimeraAppInfo) {
        Iterator it = ((ArrayList) chimeraAppInfo.procList).iterator();
        long j = 0;
        while (it.hasNext()) {
            j += ((ChimeraAppInfo.ProcessInfo) it.next()).DRAMUsed;
        }
        return j;
    }

    public static String toAppInfoDescription(ChimeraAppInfo chimeraAppInfo) {
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

    public static String toHHmmss(long j) {
        long j2 = j / 1000;
        return String.format("%02d:%02d:%02d", Long.valueOf(j2 / 3600), Long.valueOf((j2 % 3600) / 60), Long.valueOf(j2 % 60));
    }

    public final void clearQuickReclaimFields() {
        if (((ArrayList) this.mQuickReclaimPreKillApps).isEmpty()) {
            return;
        }
        Iterator it = ((ArrayList) this.mQuickReclaimPreKillApps).iterator();
        while (it.hasNext()) {
            closeProcPidFds((ChimeraAppInfo) it.next());
        }
        ((ArrayList) this.mQuickReclaimPreKillApps).clear();
    }

    public abstract void dump(PrintWriter printWriter, String[] strArr);

    public final void dumpAdjInfo(PrintWriter printWriter) {
        int[] killCntByAdj = getKillCntByAdj();
        for (int i = 0; i < 13; i++) {
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

    public final void dumpAppInfo(PrintWriter printWriter) {
        printWriter.println("App-KillCount List :");
        for (Map.Entry entry : this.mAppKillCnt.entrySet()) {
            printWriter.println(((String) entry.getKey()) + ": " + entry.getValue());
        }
    }

    public void dumpCommonInfo(PrintWriter printWriter) {
        ChimeraCommonUtil.TriggerSource[] triggerSourceArr = {ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_BOTTLENECK_HINT, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_DEVICE_IDLE, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_APP_LAUNCH_INTENT, ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_QUOTA};
        printWriter.println("Total Trigger Count: " + this.mTriggerCnt);
        StringBuilder sb = new StringBuilder("ActionCnt: " + this.mActionCnt);
        for (int i = 0; i < 6; i++) {
            ChimeraCommonUtil.TriggerSource triggerSource = triggerSourceArr[i];
            printWriter.println("Triggered by " + triggerSource.name + ": " + this.mTriggerCntSrc[triggerSource.ordinal()]);
            sb.append("\nAction by ");
            sb.append(triggerSource.name);
            sb.append(": ");
            sb.append(this.mActionCntSrc[triggerSource.ordinal()]);
        }
        printWriter.println(sb);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("KillCnt: "), this.mKillCnt, printWriter);
        int i2 = 0;
        while (i2 < 3) {
            int i3 = i2 + 1;
            printWriter.println(String.format("     G%d: %d", Integer.valueOf(i3), Integer.valueOf(this.mKillCntByGrp[i2])));
            i2 = i3;
        }
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("NoActionCnt: "), this.mNoActionCnt, printWriter, "AvgAvailableMem: "), this.mAvgAvailableMem, printWriter, "AvgReleasedMem: "), this.mAvgReleasedMem, printWriter, "mIsQuickReclaimEnabled: ");
        SettingRepository settingRepository = this.mSettingRepository;
        m.append(settingRepository.mQuickReclaimEnable);
        m.append(", heavy apps : ");
        StringBuilder sb2 = new StringBuilder("Camera:");
        SystemRepository systemRepository = this.mSystemRepository;
        sb2.append(systemRepository.mCameraApps);
        String sb3 = sb2.toString();
        if (SystemProperties.getBoolean("ro.slmk.chimera.quickreclaim_big_game_enable", false)) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(sb3, ", BigGame:");
            m2.append(systemRepository.mBigGameApps);
            sb3 = m2.toString();
        }
        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, sb3, "QuickReclaimKillCnt: ", m);
        m3.append(this.mQuickReclaimKillCnt);
        printWriter.println(m3.toString());
        StringBuilder m4 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("QuickReclaimDynamicThreshold: "), this.mDynamicQuickReclaimAdditionalMemory, printWriter, "Protected AccessibilityPackges: ");
        m4.append(String.join(", ", systemRepository.getAccessibilityServicePackages()));
        printWriter.println(m4.toString());
        StringBuilder m5 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("App File Cache Reclaim Enable: "), settingRepository.mIsAppCacheReclaimEnable, printWriter, "App File Cache Reclaim: ");
        m5.append(Arrays.toString(this.mAppFileCacheRecliamCnt));
        printWriter.println(m5.toString());
        StringBuilder m6 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Fast Madvise Enable: "), settingRepository.mIsFastMadviseEnable, printWriter, "SubProcessKillEnable: ");
        m6.append(settingRepository.mIsSubProcEnable);
        printWriter.println(m6.toString());
        dumpQuotaPPN(printWriter);
    }

    public final void dumpHistoryBuffer(PrintWriter printWriter) {
        if (this.mKillHistoryBuffer.size() > 0) {
            printWriter.println("Date Time PackageName AppType StandbyBucket PSS PIDs OomScores TriggerSource:");
            for (String str : (String[]) this.mKillHistoryBuffer.toArray()) {
                printWriter.println(str);
            }
            printWriter.println("");
        }
    }

    public final void dumpQuotaPPN(PrintWriter printWriter) {
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        if (SystemProperties.getBoolean("ro.slmk.chimera_quota_enable", false)) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("AlwaysRunningQuotaPPN Trigger Cnt: "), PerProcessNandswap.mAlwaysRunningQuotaPPNTriggerCnt, printWriter, "AlwaysRunningQuotaPPN Cnt: "), PerProcessNandswap.mAlwaysRunningQuotaPPNCnt, printWriter);
            boolean z = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
            boolean isChinaPolicyEnabled = MARsPolicyManager.isChinaPolicyEnabled();
            printWriter.println("AlwaysRunningQuotaKill Enable: " + isChinaPolicyEnabled);
            if (isChinaPolicyEnabled) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("AlwaysRunningQuotaKill Trigger Cnt: "), this.mAlwaysRunningQuotaKillTriggerCnt, printWriter, "AlwaysRunningQuotaKill Cnt: "), this.mAlwaysRunningQuotaKillCnt, printWriter);
            }
            ChimeraQuotaMonitor chimeraQuotaMonitor = ChimeraQuotaMonitor.INSTANCE;
            if (chimeraQuotaMonitor.mTaskHistory.isEmpty()) {
                return;
            }
            printWriter.println("\n************** Task History ****************");
            for (String str : (String[]) chimeraQuotaMonitor.mTaskHistory.toArray()) {
                printWriter.println(str);
            }
            printWriter.println();
        }
    }

    public abstract int executePolicy(ChimeraCommonUtil.TriggerSource triggerSource, int i);

    public final int[] getKillCntByAdj() {
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        int[] iArr2 = new int[13];
        int i = 12;
        for (int i2 = 0; i2 < this.mAdjKillCnt.size(); i2++) {
            int keyAt = this.mAdjKillCnt.keyAt(i2);
            while (true) {
                if (keyAt <= ChimeraCommonUtil.ADJ_LEVELS[i]) {
                    break;
                }
                int i3 = i - 1;
                if (i3 < 0) {
                    i = i3;
                    break;
                }
                iArr2[i3] = iArr2[i];
                i = i3;
            }
            if (i < 0) {
                break;
            }
            iArr2[i] = this.mAdjKillCnt.valueAt(i2) + iArr2[i];
        }
        while (i > 0) {
            int i4 = i - 1;
            iArr2[i4] = iArr2[i];
            i = i4;
        }
        return iArr2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x02a5, code lost:
    
        if (r3 == null) goto L154;
     */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02b0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAppKillable(com.android.server.chimera.ChimeraAppInfo r13, com.android.server.chimera.ChimeraCommonUtil.TriggerSource r14) {
        /*
            Method dump skipped, instructions count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.PolicyHandler.isAppKillable(com.android.server.chimera.ChimeraAppInfo, com.android.server.chimera.ChimeraCommonUtil$TriggerSource):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0154 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAppLaunchIntent(java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 761
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.PolicyHandler.onAppLaunchIntent(java.lang.String):void");
    }

    public final void onCameraClose() {
        if (this.mIsDynamicCameraMemorySuccess || !this.mCameraRelateInfos.stream().allMatch(new PolicyHandler$$ExternalSyntheticLambda2())) {
            return;
        }
        Iterator it = this.mCameraRelateInfos.iterator();
        long j = 0;
        while (true) {
            boolean hasNext = it.hasNext();
            SystemRepository systemRepository = this.mSystemRepository;
            if (!hasNext) {
                int i = (int) j;
                int i2 = this.mDefaultQuickReclaimAdditionalMemory;
                int i3 = i2 >> 1;
                int min = Math.min(Math.max(i3, i), i2 + i3);
                this.mIsDynamicCameraMemorySuccess = true;
                this.mDynamicQuickReclaimAdditionalMemory = min;
                systemRepository.getClass();
                SystemRepository.logDebug("PolicyHandler", "get dynamic quick reclaim additional memory successfully " + min + " (MB)");
                return;
            }
            SystemRepository.CameraProcInfo cameraProcInfo = (SystemRepository.CameraProcInfo) it.next();
            long[] rss = Process.getRss(cameraProcInfo.pid);
            cameraProcInfo.closeRss = rss[0] + rss[3];
            String str = "pid: " + cameraProcInfo.pid + " name: " + cameraProcInfo.name + " closeRss: " + cameraProcInfo.closeRss;
            systemRepository.getClass();
            SystemRepository.logDebug("PolicyHandler", str);
            j += cameraProcInfo.openRss - cameraProcInfo.closeRss;
        }
    }

    public final void onCameraOpen() {
        if (this.mIsDynamicCameraMemorySuccess || !this.mCameraRelateInfos.stream().allMatch(new PolicyHandler$$ExternalSyntheticLambda2())) {
            return;
        }
        for (SystemRepository.CameraProcInfo cameraProcInfo : this.mCameraRelateInfos) {
            long[] rss = Process.getRss(cameraProcInfo.pid);
            cameraProcInfo.openRss = rss[0] + rss[3];
            String str = "pid: " + cameraProcInfo.pid + " name: " + cameraProcInfo.name + " openRss: " + cameraProcInfo.openRss;
            this.mSystemRepository.getClass();
            SystemRepository.logDebug("PolicyHandler", str);
        }
    }

    public final void onDeviceIdle() {
        this.mSystemRepository.getClass();
        SystemRepository.logDebug("PolicyHandler", "onDeviceIdle()");
        HeimdallAlwaysRunningMonitor heimdallAlwaysRunningMonitor = HeimdallAlwaysRunningMonitor.INSTANCE;
        heimdallAlwaysRunningMonitor.getClass();
        try {
            if (heimdallAlwaysRunningMonitor.isEnable()) {
                heimdallAlwaysRunningMonitor.mHandler.sendEmptyMessage(3);
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Handler onDozeState catch exception "), "HeimdallAlwaysRunningMonitor");
        }
    }

    public void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
        String str = strArr[0];
        if (!z2) {
            if (z) {
                ((HashMap) this.mLastKilledTimeMap).put(str, 0L);
                SystemRepository.ForegroundActivityManager foregroundActivityManager = this.mSystemRepository.mFGActivityManager;
                if (foregroundActivityManager != null) {
                    synchronized (foregroundActivityManager) {
                        foregroundActivityManager.mForegroundActivities.put(i, str);
                    }
                }
            } else if (this.mSettingRepository.mQuickReclaimEnable) {
                SystemRepository systemRepository = this.mSystemRepository;
                if (!systemRepository.mBigGameApps.contains(str) && !"com.sec.android.app.camera".equals(str)) {
                    Long l = (Long) ((ArrayMap) this.mBigAppPssMap).get(str);
                    Long l2 = -1L;
                    if (!l2.equals(l)) {
                        if (l == null) {
                            boolean contains = systemRepository.mGameApps.contains(str);
                            boolean contains2 = systemRepository.mCameraApps.contains(str);
                            if (contains || contains2) {
                                long pss = Debug.getPss(i, null, null);
                                if (contains && pss > 409600) {
                                    systemRepository.mBigGameApps.update(str);
                                }
                                ((ArrayMap) this.mBigAppPssMap).put(str, Long.valueOf(pss));
                            } else {
                                ((ArrayMap) this.mBigAppPssMap).put(str, -1L);
                            }
                        } else {
                            long pss2 = Debug.getPss(i, null, null);
                            if (!systemRepository.mGameApps.contains(str)) {
                                ((ArrayMap) this.mBigAppPssMap).put(str, Long.valueOf(pss2));
                            } else if (pss2 > 409600) {
                                systemRepository.mBigGameApps.update(str);
                                ((ArrayMap) this.mBigAppPssMap).put(str, Long.valueOf(pss2));
                            }
                        }
                    }
                }
            }
        }
        if (str.equals("com.samsung.android.permissioncontroller") || str.equals("com.google.android.permissioncontroller")) {
            return;
        }
        if (!this.mSettingRepository.mIsAppCacheReclaimEnable) {
            ((ConcurrentHashMap) ChimeraCommonUtil.mAppLaunchInfoMap).clear();
            return;
        }
        if (z2 || z) {
            return;
        }
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) ChimeraCommonUtil.mAppLaunchInfoMap;
        String str2 = (String) concurrentHashMap.get(str);
        if (!(str2 != null ? str2.startsWith("COLD") : false)) {
            String str3 = (String) concurrentHashMap.get(str);
            if (!(str3 != null ? str3.startsWith("WARM") : false)) {
                SystemRepository systemRepository2 = this.mSystemRepository;
                String concat = "Chimera AppFileCacheReclaim do reclaimAppCaches skip: ".concat(str);
                systemRepository2.getClass();
                SystemRepository.logDebug("PolicyHandler", concat);
                return;
            }
        }
        Message obtain = Message.obtain(this.mHandler, 1);
        obtain.obj = str;
        this.mHandler.sendMessage(obtain);
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x011c, code lost:
    
        if (r15.contains(r1.name) != false) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0196 A[LOOP:4: B:70:0x018f->B:72:0x0196, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHomeLaunched() {
        /*
            Method dump skipped, instructions count: 684
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.PolicyHandler.onHomeLaunched():void");
    }

    public final void onLmkdEventTriggered(int i, int i2) {
        String m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "onLmkdKillTriggered() - lmkdLevel: ", " type: ");
        this.mSystemRepository.getClass();
        SystemRepository.logDebug("PolicyHandler", m);
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            executePolicy(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD_GENIE, i);
            return;
        }
        executePolicy(ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD, i);
        AbnormalFgsDetector abnormalFgsDetector = this.mAbnormalFgsDetector;
        if (((ArrayList) abnormalFgsDetector.mAbnormalHeavyApps).size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<Integer> arrayList2 = new ArrayList<>();
            ArrayList<Integer> arrayList3 = new ArrayList<>();
            Iterator it = ((ArrayList) abnormalFgsDetector.mAbnormalHeavyApps).iterator();
            while (it.hasNext()) {
                AbnormalFgsDetector.HeavyAppItem heavyAppItem = (AbnormalFgsDetector.HeavyAppItem) it.next();
                arrayList.add(heavyAppItem.processName);
                arrayList2.add(Integer.valueOf(heavyAppItem.uid));
                arrayList3.add(5000);
                String str = heavyAppItem.processName;
                long j = heavyAppItem.detectPss;
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("TYPE", 103);
                    jSONObject.put("PNAME", str);
                    jSONObject.put("PSS", j);
                    String jSONObject2 = jSONObject.toString();
                    String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
                    if (!TextUtils.isEmpty(substring)) {
                        AbnormalFgsDetector.mSystemRepository.sendHqmBigData(substring);
                    }
                } catch (JSONException unused) {
                    AbnormalFgsDetector.mSystemRepository.getClass();
                    SystemRepository.log("AbnormalFgsDetector", "failed to create the KPUT");
                }
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.sdhms.MEMORY_ABNORMAL_APP_DETECTION");
            intent.putStringArrayListExtra("package_name", arrayList);
            intent.putIntegerArrayListExtra("uid", arrayList2);
            intent.putIntegerArrayListExtra("anomaly_type", arrayList3);
            intent.setPackage("com.sec.android.sdhms");
            AbnormalFgsDetector.mSystemRepository.mContext.sendBroadcast(intent);
            SystemRepository systemRepository = AbnormalFgsDetector.mSystemRepository;
            String str2 = "reported AbnormalHeavyApp : " + Arrays.toString(abnormalFgsDetector.mAbnormalHeavyApps.stream().toArray());
            systemRepository.getClass();
            SystemRepository.logDebug("AbnormalFgsDetector", str2);
            ((ArrayList) abnormalFgsDetector.mReportedAbnormalHeavyApps).addAll(abnormalFgsDetector.mAbnormalHeavyApps);
            ((ArrayList) abnormalFgsDetector.mAbnormalHeavyApps).clear();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x029e A[LOOP:9: B:110:0x0298->B:112:0x029e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0331 A[EDGE_INSN: B:118:0x0331->B:119:0x0331 BREAK  A[LOOP:4: B:43:0x015f->B:50:0x0260], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onQuotaKill(boolean r23) {
        /*
            Method dump skipped, instructions count: 920
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.PolicyHandler.onQuotaKill(boolean):void");
    }

    public final boolean prepareForTrigger(ChimeraCommonUtil.TriggerSource triggerSource) {
        IDeviceIdleController iDeviceIdleController;
        int ordinal = triggerSource.ordinal();
        if (ordinal == 0 || ordinal == 2) {
            if (!((PowerManager) this.mSystemRepository.mContext.getSystemService("power")).isInteractive()) {
                this.mSystemRepository.getClass();
                SystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: screen off");
                return false;
            }
        } else if (ordinal == 6 && !(!((PowerManager) this.mSystemRepository.mContext.getSystemService("power")).isInteractive())) {
            return false;
        }
        SystemRepository systemRepository = this.mSystemRepository;
        if (systemRepository.mAudioManager == null) {
            systemRepository.mAudioManager = (AudioManager) systemRepository.mContext.getSystemService("audio");
        }
        if (systemRepository.mAudioManager.getMode() >= 1) {
            this.mSystemRepository.getClass();
            SystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: incall");
            return false;
        }
        SystemRepository.SmartSwitchEventReceiver smartSwitchEventReceiver = this.mSystemRepository.mSmartSwitchEventReceiver;
        if (smartSwitchEventReceiver.mOnStart || smartSwitchEventReceiver.mOnTransfer) {
            SystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: smart switch");
            return false;
        }
        if (this.mIsCarMode) {
            SystemRepository.log("PolicyHandler", "prepareForTrigger() - skip reason: car mode");
            return false;
        }
        WakeLockManager wakeLockManager = this.mWakeLockManager;
        ((HashSet) wakeLockManager.mWakeLockPackages).clear();
        Collections.addAll(wakeLockManager.mWakeLockPackages, (String[]) Optional.ofNullable((PowerManager) wakeLockManager.mSystemRepository.mContext.getSystemService("power")).map(new SystemRepository$$ExternalSyntheticLambda1(4)).orElse(new String[0]));
        ((ConcurrentHashMap) RestartImmediatePackages.getInstance().sPackages).clear();
        final SystemRepository systemRepository2 = this.mSystemRepository;
        synchronized (systemRepository2) {
            if (systemRepository2.mDeviceIdleController == null) {
                IBinder service = ServiceManager.getService("deviceidle");
                if (service != null) {
                    IDeviceIdleController asInterface = IDeviceIdleController.Stub.asInterface(service);
                    systemRepository2.mDeviceIdleController = asInterface;
                    if (asInterface != null) {
                        try {
                            service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.chimera.SystemRepository$$ExternalSyntheticLambda6
                                @Override // android.os.IBinder.DeathRecipient
                                public final void binderDied() {
                                    SystemRepository systemRepository3 = SystemRepository.this;
                                    synchronized (systemRepository3) {
                                        systemRepository3.mDeviceIdleController = null;
                                    }
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
            iDeviceIdleController = systemRepository2.mDeviceIdleController;
        }
        this.mPowerWhitelistedApps = (List) Optional.ofNullable(iDeviceIdleController).map(new SystemRepository$$ExternalSyntheticLambda1(2)).map(new SystemRepository$$ExternalSyntheticLambda1(3)).orElse(Collections.emptyList());
        return true;
    }

    public final void updateActionStatistics(ChimeraCommonUtil.TriggerSource triggerSource) {
        this.mActionCnt++;
        int ordinal = triggerSource.ordinal();
        int[] iArr = this.mActionCntSrc;
        iArr[ordinal] = iArr[ordinal] + 1;
    }

    public final void updateKillStatistics(ChimeraAppInfo chimeraAppInfo, ChimeraCommonUtil.TriggerSource triggerSource) {
        DateFormat dateFormat;
        String format;
        this.mKillCnt++;
        int i = chimeraAppInfo.group;
        if (i > 0 && i <= 3) {
            int i2 = i - 1;
            int[] iArr = this.mKillCntByGrp;
            iArr[i2] = iArr[i2] + 1;
        }
        ArrayMap arrayMap = this.mAppKillCnt;
        String str = chimeraAppInfo.packageName;
        Integer num = (Integer) arrayMap.get(str);
        this.mAppKillCnt.put(str, num == null ? 1 : Integer.valueOf(num.intValue() + 1));
        int i3 = 0;
        if (chimeraAppInfo.statsAndOomScores != null) {
            int i4 = 0;
            while (true) {
                int[] iArr2 = chimeraAppInfo.statsAndOomScores.mScores;
                if (i4 >= iArr2.length) {
                    break;
                }
                int i5 = iArr2[i4];
                if (i5 >= -1000 && i5 <= 1000) {
                    this.mAdjKillCnt.put(i5, this.mAdjKillCnt.get(i5) + 1);
                }
                i4++;
            }
        }
        if (this.mKillHistoryBuffer != null) {
            StringBuilder sb = new StringBuilder();
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis == 0) {
                format = String.format("%18s", "null");
            } else {
                ThreadLocal threadLocal = ChimeraDataCache.DATE_FORMAT_MAP;
                Map map = (Map) threadLocal.get();
                if (map == null) {
                    ArrayMap arrayMap2 = new ArrayMap();
                    threadLocal.set(arrayMap2);
                    dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss.SSS");
                    arrayMap2.put("MM/dd HH:mm:ss.SSS", dateFormat);
                } else {
                    dateFormat = (DateFormat) map.get("MM/dd HH:mm:ss.SSS");
                    if (dateFormat == null) {
                        dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss.SSS");
                        map.put("MM/dd HH:mm:ss.SSS", dateFormat);
                    }
                }
                format = dateFormat.format(new Date(currentTimeMillis));
            }
            sb.append(format);
            sb.append(" ");
            StringBuilder sb2 = new StringBuilder();
            Iterator it = ((ArrayList) chimeraAppInfo.procList).iterator();
            int i6 = 0;
            while (it.hasNext()) {
                sb2.append(((ChimeraAppInfo.ProcessInfo) it.next()).pid);
                i6++;
                if (i6 < ((ArrayList) chimeraAppInfo.procList).size()) {
                    sb2.append(",");
                }
            }
            sb.append(String.format("%s %xH %d %d %s", str, Integer.valueOf(chimeraAppInfo.appType), Integer.valueOf(chimeraAppInfo.curStandbyBucket), Long.valueOf(chimeraAppInfo.pss), sb2.toString()));
            sb.append(" ");
            if (chimeraAppInfo.statsAndOomScores != null) {
                while (true) {
                    int[] iArr3 = chimeraAppInfo.statsAndOomScores.mScores;
                    if (i3 >= iArr3.length) {
                        break;
                    }
                    sb.append(iArr3[i3]);
                    if (i3 < chimeraAppInfo.statsAndOomScores.mScores.length - 1) {
                        sb.append(",");
                    }
                    i3++;
                }
            }
            sb.append(" ");
            sb.append(triggerSource.ordinal());
            this.mKillHistoryBuffer.append(sb.toString());
        }
    }
}
