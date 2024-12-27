package com.android.server.am;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.pmm.PersonalizedMemoryManager;
import com.android.server.chimera.ChimeraDataInfo;
import com.android.server.chimera.heimdall.Heimdall;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.stats.pull.ProcfsMemoryUtil;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KillPolicyManager {
    public ActivityManagerService mAm;
    public long mAppLaunchCount;
    public int mBigdataIndex;
    public int mBigdataMetric;
    public Context mContext;
    public KpmState mCurrentState;
    public AnonymousClass2 mDailyRandomSampleReceiver;
    public String mDisplaySizeStr;
    public boolean mHasPsiCpuPermission;
    public boolean mHasPsiIoPermission;
    public boolean mHasPsiMemoryPermission;
    public SemHqmManager mHqmManager;
    public final AnonymousClass1 mIdleModeReceiver;
    public boolean mIsFirstAppLaunch;
    public final KpmRaw[] mKpmRawBigdata;
    public final KpmRaw[] mKpmRawPolicy;
    public long mKpmStartTime;
    public long mLastAwakeRealTimeMillis;
    public long mLastAwakeUpTimeMillis;
    public long mLastIdleEnterRealTimeMillis;
    public long mLastIdleExitRealTimeMillis;
    public long mLastIdleExitUpTimeMillis;
    public long mLastIdleRealTimeMillis;
    public final LmkdCountReader mLmkdReader;
    public final MemoryFloodDetector mMemoryFloodDetector;
    public final MemoryStabilityEventManager mMemoryStabilityEventManager;
    public long mNumberOfStay;
    public final long mPlatformStartUpTimeMillis;
    public int mPolicyIndex;
    public int mPolicyMetric;
    public KpmState mPolicyState;
    public String mPrevPackage;
    public final ArrayList mPrevProcessList;
    public KpmState mPrevState;
    public final ProcessMemoryUsageInfo mProcMemDumpBigdata;
    public final ProcessMemoryUsageInfo mProcMemDumpPolicy;
    public final ProcessMemoryHeavyInfo mProcessHeavyMemory;
    public ChimeraDataInfo mRecentChimeraData;
    public final SwappinessController mSwappinessController;
    public int mTotalMem;
    public int mTotalSwap;
    public int mTrigger;
    public HashMap mVmStats;
    public final AnonymousClass1 policyBroadcastReceiver;
    public static final int[] SWAPPINESS = {100, 100, 130, 130, 145};
    public static boolean KPM_POLICY_ENABLE =
            Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_policy_enable", "true"));
    public static boolean KPM_DEBUG = false;
    public static int sWarmUpTrigger =
            Integer.parseInt(SystemProperties.get("ro.slmk.kpm_warm_up_trigger", "20"));
    public static int sPolicyTrigger =
            Integer.parseInt(
                    SystemProperties.get("ro.slmk.kpm_policy_trigger", Integer.toString(50)));
    public static int sWarmUpCycles =
            Integer.parseInt(SystemProperties.get("ro.slmk.kpm_warm_up_cycles", "3"));
    public static boolean KPM_BTIME_ENABLE =
            Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_boot_enable", "true"));
    public static final ArrayList resumeSkipPackage = new ArrayList();
    public static final boolean MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO =
            Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_use_cri_pkg_ratio", "true"));
    public static int MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH = 100;
    public static int MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 100;
    public static ServiceThread brHandlerThread = null;
    public static Handler brHandler = null;
    public static boolean sPmmEnabledBySpcm =
            Boolean.parseBoolean(SystemProperties.get("persist.sys.kpm_onoff", "true"));
    public static final ValueRange RANGE_AUTO_RESTART_MIN_TRIGGER_SIZE = ValueRange.of(1, 100);
    public static final ValueRange RANGE_AUTO_RESTART_NEVER_COLLECT_WITHIN = ValueRange.of(1, 100);
    public static final ValueRange RANGE_AUTO_RESTART_COMPACT_TRIGGER_SIZE = ValueRange.of(2, 1000);
    public static final ValueRange RANGE_AUTO_RESTART_NATIVE_FLOOD_RATIO = ValueRange.of(0, 1000);
    public static final ValueRange RANGE_AUTO_RESTART_SYSPERS_FLOOD_RATIO = ValueRange.of(0, 1000);
    public static int sMinTriggerSize = 10;
    public static int sNeverCollectWithin = 12;
    public static int sCompactTriggerSize = 300;
    public static int sNativeFloodRatio = 30;
    public static int sSyspersFloodRatio = 30;
    public static boolean mIsChimeraPmmKillTriggered = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class AutoRestartParameterReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("MIN_TRIGGER_SIZE", 10);
            int intExtra2 = intent.getIntExtra("NEVER_COLLECT_WITHIN", 12);
            int intExtra3 = intent.getIntExtra("COMPACT_TRIGGER_SIZE", 300);
            int intExtra4 = intent.getIntExtra("NATIVE_FLOOD_RATIO", 30);
            int intExtra5 = intent.getIntExtra("SYSPERS_FLOOD_RATIO", 30);
            if (KillPolicyManager.RANGE_AUTO_RESTART_MIN_TRIGGER_SIZE.isValidIntValue(intExtra)) {
                KillPolicyManager.sMinTriggerSize = intExtra;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_NEVER_COLLECT_WITHIN.isValidIntValue(
                    intExtra2)) {
                KillPolicyManager.sNeverCollectWithin = intExtra2;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_COMPACT_TRIGGER_SIZE.isValidIntValue(
                    intExtra3)) {
                KillPolicyManager.sCompactTriggerSize = intExtra3;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_NATIVE_FLOOD_RATIO.isValidIntValue(
                    intExtra4)) {
                KillPolicyManager.sNativeFloodRatio = intExtra4;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_SYSPERS_FLOOD_RATIO.isValidIntValue(
                    intExtra5)) {
                KillPolicyManager.sSyspersFloodRatio = intExtra5;
            }
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb =
                        new StringBuilder("Auto Restart Parameter Updated. {sMinTriggerSize=");
                sb.append(KillPolicyManager.sMinTriggerSize);
                sb.append(", sNeverCollectWithin=");
                sb.append(KillPolicyManager.sNeverCollectWithin);
                sb.append(", sCompactTriggerSize=");
                sb.append(KillPolicyManager.sCompactTriggerSize);
                sb.append(", sNativeFloodRatio=");
                sb.append(KillPolicyManager.sNativeFloodRatio);
                sb.append(", sSyspersFloodRatio=");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb, KillPolicyManager.sSyspersFloodRatio, "}", "ActivityManager_kpm");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChimeraTriggerManager {
        public static final float PMM_CRITICAL_SCORE_THRESHOLD =
                Float.parseFloat(
                        SystemProperties.get(
                                "persist.sys.kpm.debug.chimera_critical_score_threshold", "3.0"));
        public static final long TRIGGER_MIN_INTERVAL =
                Long.parseLong(
                        SemSystemProperties.get(
                                "persist.sys.kpm.debug.chimera_trigger_min_interval",
                                Duration.ofDays(1).toMillis() + ""));
        public static ChimeraTriggerManager mInstance = null;
        public final Context mContext;
        public float mLastPolicyScore = -1.0f;
        public boolean mChimeraTriggerRequired = false;
        public long mLastTriggeredTime = 0;

        /* renamed from: -$$Nest$smgetInstance, reason: not valid java name */
        public static ChimeraTriggerManager m189$$Nest$smgetInstance(Context context) {
            ChimeraTriggerManager chimeraTriggerManager;
            synchronized (ChimeraTriggerManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new ChimeraTriggerManager(context);
                    }
                    chimeraTriggerManager = mInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return chimeraTriggerManager;
        }

        public ChimeraTriggerManager(Context context) {
            Log.d("ActivityManager", "ChimeraTriggerManager() - start");
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class DeviceInfoHelper {
        public static final Uri URI_ISSUE_TRACKER =
                Uri.parse("content://issuetracker_provider/user_list");
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DumpHeavyProcessInfo {
        public ActivityManagerService.MemDumpInfo memoryInfo;
        public String packageName;
        public String packageVersion;
        public long processVersion;
        public ArrayList pssUssRssInfo;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DumpPssUssRssInfo {
        public int avgPss;
        public int avgRss;
        public int avgUss;
        public int maxPss;
        public int maxRss;
        public int maxUss;
        public int memoryFactorIndex;
        public int minPss;
        public int minRss;
        public int minUss;
        public int sampleCount;
        public int screenIndex;
        public int stateIndex;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class KpmClassLazy {
        public static final KillPolicyManager INSTANCE = new KillPolicyManager();
        public static boolean isinitKpmClass = false;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KpmRaw {
        public int appCnt;
        public int cachedClientKillCount;
        public int cachedEmptyKillCount;
        public int cachedNormalKillCount;
        public ChimeraDataInfo chimeraDataInfo;
        public final long createTime;
        public final KpmState curKpmState;
        public final int cycleNum;
        public HashMap dumpHeavyMemoryUsageByAdj;
        public ArrayList dumpHeavyProcessList;
        public boolean isDailyBigdata;
        public boolean isGetPssDump;
        public boolean isStateChanged;
        public boolean isWarmUpCycle;
        public long lastUpdateTime;
        public final Map launchedAndKilledPackageMap;
        public final LmkdCounter lmkdCounter;
        public final ArrayList lmkdKilledProcessInfo;
        public LmkdState lmkdState;
        public ProcessSampler mProcessSampler;
        public int maxSwap;
        public KpmState nextKpmState;
        public int peakLmkdKillAdj;
        public final KpmState policyState;
        public final long prevLmkCnt;
        public final long prevLmkdCric;
        public final long prevLmkdMed;
        public int prevTinyCachedNormalKillCount;
        public ProcMemInfo procMemInfo;
        public HashMap procsAdjPss;
        public double psiCpuMax;
        public double psiCpuSum;
        public double psiIoMax;
        public double psiIoSum;
        public double psiMemoryMax;
        public double psiMemorySum;
        public String timeStamp;
        public long uptimeMillis;
        public final long[] lmkdStateCount = new long[LmkdState.values().length];
        public final long[] lmkdCurrentCount = new long[LmkdCount.values().length];
        public final long[] lmkdPrevCount = new long[LmkdCount.values().length];
        public final KpmState[] tinyKpmState = new KpmState[10];
        public int minMem = Integer.MAX_VALUE;
        public int accMem = 0;
        public int maxMem = Integer.MIN_VALUE;
        public int minSwap = Integer.MAX_VALUE;
        public int avlMem = 0;
        public int accSwap = 0;
        public int hotRatio = 0;
        public int hotCount = 0;
        public int warmCount = 0;
        public int coldCount = 0;
        public int prKilledRatio = 0;
        public int svKilledRatio = 0;
        public int killedPackageRatio = 0;
        public int killedPackageCount = 0;
        public int launchedPackageCount = 0;
        public int resetStatus = 0;
        public int cachedTotalCnt = 0;
        public int cachedActTotalCnt = 0;
        public int currentCachedActCnt = 0;
        public int pickedTotalCnt = 0;
        public int pickedActTotalCnt = 0;
        public long lmkCnt = 0;
        public long lmkdMed = 0;
        public long lmkdCric = 0;

        /* renamed from: -$$Nest$mcheckMemCriticalLowTH, reason: not valid java name */
        public static void m190$$Nest$mcheckMemCriticalLowTH(KpmRaw kpmRaw) {
            int i = kpmRaw.prKilledRatio;
            if (KillPolicyManager.KPM_DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i,
                                "checkMemCriticalLowTH() killRatio = ",
                                "%, killedPackageRatio = "),
                        kpmRaw.killedPackageRatio,
                        "%",
                        "ActivityManager_kpm");
            }
            int i2 = KillPolicyManager.MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH;
            KillPolicyManager killPolicyManager = KillPolicyManager.this;
            int i3 = 0;
            if (i >= i2) {
                if (KillPolicyManager.MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO) {
                    int i4 = kpmRaw.killedPackageRatio;
                    killPolicyManager.getClass();
                    if (i4 < KillPolicyManager.MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH) {
                        if (KillPolicyManager.KPM_DEBUG) {
                            Slog.d(
                                    "ActivityManager_kpm",
                                    "checkMemCriticalLowTH() killRatio is over thrshold but not"
                                        + " occurred many apps, so result not accepted");
                        }
                    }
                }
                i3 = 1;
            }
            kpmRaw.resetStatus = i3;
            if (KillPolicyManager.KPM_DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("checkMemCriticalLowTH() memory critical status is "),
                        kpmRaw.resetStatus,
                        "ActivityManager_kpm");
            }
            if (SystemClock.uptimeMillis() > Duration.ofDays(1L).toMillis()
                    && Boolean.parseBoolean(
                            SystemProperties.get(
                                    "persist.sys.kpm_cri_mem_detect", String.valueOf(true)))
                    && !kpmRaw.isWarmUpCycle) {
                killPolicyManager.getClass();
                Intent intent = new Intent();
                intent.setAction("com.samsung.KPM_CRITICAL_MEMORY_STATUS");
                intent.putExtra("res", i3);
                intent.setPackage(
                        SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                                        "com.samsung.android.lool"));
                killPolicyManager.mContext.sendBroadcast(intent);
                Slog.i("ActivityManager_kpm", "memory Critical Low intent RESULT : " + i3);
            }
            if (KillPolicyManager.mIsChimeraPmmKillTriggered) {
                KpmState kpmState = kpmRaw.nextKpmState;
                if (kpmState == KpmState.HEAVY || kpmState == KpmState.CRITICAL) {
                    MemoryFloodDetector memoryFloodDetector =
                            killPolicyManager.mMemoryFloodDetector;
                    if (((ArrayList) memoryFloodDetector.mNativeMemDumpList).size()
                            >= KillPolicyManager.sMinTriggerSize) {
                        Pair linearRegressionFactor =
                                MemoryFloodDetector.getLinearRegressionFactor(
                                        memoryFloodDetector.mNativeMemDumpList);
                        int size = ((ArrayList) memoryFloodDetector.mNativeMemDumpList).size() - 1;
                        double doubleValue = ((Double) linearRegressionFactor.second).doubleValue();
                        double doubleValue2 =
                                (((Double) linearRegressionFactor.first).doubleValue() * size)
                                        + doubleValue;
                        Slog.i(
                                "ActivityManager_kpm",
                                "nativeFactor : "
                                        + linearRegressionFactor.first
                                        + ", "
                                        + linearRegressionFactor.second
                                        + " => "
                                        + doubleValue
                                        + ", "
                                        + doubleValue2);
                        memoryFloodDetector.mLastCalculatedNative =
                                new Pair(
                                        Integer.valueOf((int) doubleValue),
                                        Integer.valueOf((int) doubleValue2));
                    }
                    if (((ArrayList) memoryFloodDetector.mSystemMemDumpList).size()
                            >= KillPolicyManager.sMinTriggerSize) {
                        Pair linearRegressionFactor2 =
                                MemoryFloodDetector.getLinearRegressionFactor(
                                        memoryFloodDetector.mSystemMemDumpList);
                        Pair linearRegressionFactor3 =
                                MemoryFloodDetector.getLinearRegressionFactor(
                                        memoryFloodDetector.mPersistentMemDumpList);
                        int size2 = ((ArrayList) memoryFloodDetector.mSystemMemDumpList).size() - 1;
                        double doubleValue3 =
                                ((Double) linearRegressionFactor3.second).doubleValue()
                                        + ((Double) linearRegressionFactor2.second).doubleValue();
                        double d = size2;
                        double doubleValue4 =
                                (((Double) linearRegressionFactor3.first).doubleValue() * d)
                                        + (((Double) linearRegressionFactor2.first).doubleValue()
                                                * d)
                                        + doubleValue3;
                        Slog.i(
                                "ActivityManager_kpm",
                                "sysFactor : "
                                        + linearRegressionFactor2.first
                                        + ", "
                                        + linearRegressionFactor2.second
                                        + " persFactor : "
                                        + linearRegressionFactor3.first
                                        + ", "
                                        + linearRegressionFactor3.second
                                        + " => "
                                        + doubleValue3
                                        + ", "
                                        + doubleValue4);
                        memoryFloodDetector.mLastCalculatedSysPers =
                                new Pair(
                                        Integer.valueOf((int) doubleValue3),
                                        Integer.valueOf((int) doubleValue4));
                    }
                    MemoryFloodDetector memoryFloodDetector2 =
                            killPolicyManager.mMemoryFloodDetector;
                    if (((ArrayList) memoryFloodDetector2.mNativeMemDumpList).size()
                            >= KillPolicyManager.sMinTriggerSize) {
                        if (((Integer) memoryFloodDetector2.mLastCalculatedNative.first).intValue()
                                        * (KillPolicyManager.sNativeFloodRatio + 100)
                                < ((Integer) memoryFloodDetector2.mLastCalculatedNative.second)
                                                .intValue()
                                        * 100.0d) {
                            KillPolicyManager.m188$$Nest$mreportResetState(
                                    killPolicyManager, "KERNEL");
                            return;
                        }
                    }
                    if (((ArrayList) memoryFloodDetector2.mSystemMemDumpList).size()
                            < KillPolicyManager.sMinTriggerSize) {
                        return;
                    }
                    if (((Integer) memoryFloodDetector2.mLastCalculatedSysPers.first).intValue()
                                    * (KillPolicyManager.sSyspersFloodRatio + 100)
                            < ((Integer) memoryFloodDetector2.mLastCalculatedSysPers.second)
                                            .intValue()
                                    * 100.0d) {
                        KillPolicyManager.m188$$Nest$mreportResetState(
                                killPolicyManager, "PLATFORM");
                    }
                }
            }
        }

        /* renamed from: -$$Nest$mmarkKilledPackage, reason: not valid java name */
        public static void m191$$Nest$mmarkKilledPackage(KpmRaw kpmRaw, String str, int i) {
            if (str == null) {
                kpmRaw.getClass();
                return;
            }
            if (!((HashMap) kpmRaw.launchedAndKilledPackageMap).containsKey(str)
                    || ((Integer) ((HashMap) kpmRaw.launchedAndKilledPackageMap).get(str))
                                    .intValue()
                            <= i) {
                return;
            }
            ((HashMap) kpmRaw.launchedAndKilledPackageMap).put(str, Integer.valueOf(i));
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder m =
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i, "markKilledPackage : ", str, ", adj ", ", killed adj : ");
                m.append(((HashMap) kpmRaw.launchedAndKilledPackageMap).get(str));
                Slog.d("ActivityManager_kpm", m.toString());
            }
        }

        /* renamed from: -$$Nest$mupdateLmkdStateAndCount, reason: not valid java name */
        public static void m192$$Nest$mupdateLmkdStateAndCount(KpmRaw kpmRaw, LmkdState lmkdState) {
            if (kpmRaw.lmkdState.getLevel() < lmkdState.getLevel()) {
                kpmRaw.lmkdState = lmkdState;
            }
            int ordinal = lmkdState.ordinal();
            long[] jArr = kpmRaw.lmkdStateCount;
            jArr[ordinal] = jArr[ordinal] + 1;
        }

        public KpmRaw(int i) {
            this.cycleNum = i;
            int i2 = 0;
            this.prevLmkCnt = 0L;
            this.prevLmkdMed = 0L;
            this.prevLmkdCric = 0L;
            this.prevLmkdCric = SecLmkdStats.getTotalCriticalKillCount();
            this.prevLmkdMed =
                    SecLmkdStats.getKillCountFromSlotRange(0, 15, false, false) - this.prevLmkdCric;
            long[] jArr = {0};
            Process.readProcFile(
                    "/sys/module/lowmemorykiller/parameters/lmkcount",
                    new int[] {8224},
                    null,
                    jArr,
                    null);
            this.prevLmkCnt = jArr[0];
            this.curKpmState = KillPolicyManager.this.mCurrentState;
            this.nextKpmState = KpmState.NORMAL;
            this.policyState = KillPolicyManager.this.mPolicyState;
            this.appCnt = 0;
            long currentTimeMillis = System.currentTimeMillis();
            this.createTime = currentTimeMillis;
            this.lastUpdateTime = currentTimeMillis;
            this.timeStamp = null;
            this.isWarmUpCycle = false;
            this.isStateChanged = false;
            this.isDailyBigdata = false;
            this.isGetPssDump = false;
            this.peakLmkdKillAdj = 1001;
            this.procsAdjPss = null;
            this.cachedNormalKillCount = 0;
            this.cachedEmptyKillCount = 0;
            this.cachedClientKillCount = 0;
            this.prevTinyCachedNormalKillCount = 0;
            this.psiCpuSum = 0.0d;
            this.psiMemorySum = 0.0d;
            this.psiIoSum = 0.0d;
            this.psiCpuMax = 0.0d;
            this.psiMemoryMax = 0.0d;
            this.psiIoMax = 0.0d;
            this.chimeraDataInfo = new ChimeraDataInfo();
            this.lmkdState = LmkdState.LEVEL_0;
            int i3 = 0;
            while (true) {
                long[] jArr2 = this.lmkdStateCount;
                if (i3 >= jArr2.length) {
                    break;
                }
                jArr2[i3] = 0;
                i3++;
            }
            int i4 = 0;
            while (true) {
                long[] jArr3 = this.lmkdCurrentCount;
                if (i4 >= jArr3.length) {
                    break;
                }
                jArr3[i4] = 0;
                i4++;
            }
            KillPolicyManager.fillLmkdCounts(this.lmkdPrevCount);
            this.lmkdCounter =
                    KillPolicyManager.this
                    .new LmkdCounter(this, KillPolicyManager.this.mLmkdReader);
            this.launchedAndKilledPackageMap = new HashMap();
            this.lmkdKilledProcessInfo = new ArrayList();
            this.dumpHeavyProcessList = null;
            this.procMemInfo = null;
            this.dumpHeavyMemoryUsageByAdj = null;
            while (true) {
                KpmState[] kpmStateArr = this.tinyKpmState;
                if (i2 >= kpmStateArr.length) {
                    return;
                }
                kpmStateArr[i2] = KpmState.NONE;
                i2++;
            }
        }

        public final StringBuilder getKpmData(int i) {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, " ");
            int i2 = this.cycleNum;
            if (i2 == i) {
                return m;
            }
            if (this.appCnt == 0) {
                m.append("appCnt is 0");
                return m;
            }
            m.append(i2);
            m.append(",");
            m.append((int) ((this.accMem / this.appCnt) + 0.5d));
            m.append(",");
            m.append((int) ((this.avlMem / this.appCnt) + 0.5d));
            m.append(",");
            m.append((int) ((this.accSwap / this.appCnt) + 0.5d));
            m.append(",");
            m.append(this.hotCount);
            m.append(",");
            LmkdCounter lmkdCounter = this.lmkdCounter;
            m.append(lmkdCounter.previousKillOccurredCount);
            m.append(",");
            m.append(lmkdCounter.bServiceKillOccurredCount);
            m.append(",");
            m.append(this.launchedPackageCount);
            m.append(",");
            m.append(this.killedPackageCount);
            m.append(",");
            m.append(lmkdCounter.cycleTotalKillCount);
            m.append(",");
            m.append(lmkdCounter.cyclePreviousKillCount);
            m.append(",");
            m.append(lmkdCounter.cycleBServiceKillCount);
            m.append(",");
            m.append(lmkdCounter.cyclePickedKillCount);
            m.append(",");
            m.append(this.lmkdCric);
            m.append(",");
            m.append(this.lmkCnt);
            m.append(",");
            m.append(this.cachedNormalKillCount);
            m.append(",");
            m.append(this.cachedEmptyKillCount);
            m.append(",");
            m.append(this.curKpmState.ordinal());
            m.append(",");
            m.append(this.nextKpmState.ordinal());
            m.append(",");
            m.append(this.policyState.ordinal());
            m.append(",");
            m.append((int) ((this.psiCpuSum / this.appCnt) + 0.5d));
            m.append(",");
            m.append((int) ((this.psiMemorySum / this.appCnt) + 0.5d));
            m.append(",");
            m.append((int) ((this.psiIoSum / this.appCnt) + 0.5d));
            m.append(",");
            m.append((int) (this.psiCpuMax + 0.5d));
            m.append(",");
            m.append((int) (this.psiMemoryMax + 0.5d));
            m.append(",");
            m.append((int) (this.psiIoMax + 0.5d));
            m.append(",");
            m.append(this.resetStatus);
            m.append(",");
            m.append(this.lmkdState.getLevel());
            m.append(",[");
            long[] jArr = this.lmkdStateCount;
            m.append(jArr[0]);
            for (int i3 = 1; i3 < jArr.length; i3++) {
                m.append(" ");
                m.append(jArr[i3]);
            }
            m.append("],[");
            long[] jArr2 = this.lmkdCurrentCount;
            m.append(jArr2[0]);
            for (int i4 = 1; i4 < jArr2.length; i4++) {
                m.append(" ");
                m.append(jArr2[i4]);
            }
            m.append("],[");
            KpmState[] kpmStateArr = this.tinyKpmState;
            m.append(kpmStateArr[0].ordinal());
            for (int i5 = 1; i5 < kpmStateArr.length; i5++) {
                m.append(" ");
                m.append(kpmStateArr[i5].ordinal());
            }
            m.append("],");
            m.append(this.timeStamp);
            return m;
        }

        public final StringBuilder getKpmHeavyPssData() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, " ");
            m.append(this.cycleNum);
            if (this.dumpHeavyProcessList == null) {
                m.append(",didn't happen Previous App Kill by LMKD");
            } else {
                m.append(",");
                m.append(this.peakLmkdKillAdj);
                ProcMemInfo procMemInfo = this.procMemInfo;
                KillPolicyManager killPolicyManager = KillPolicyManager.this;
                if (procMemInfo != null) {
                    m.append(",[");
                    m.append(this.procMemInfo.memTotal);
                    m.append(" ");
                    m.append(this.procMemInfo.memFree);
                    m.append(" ");
                    m.append(this.procMemInfo.memAvailable);
                    m.append(" ");
                    m.append(this.procMemInfo.cached);
                    m.append(" ");
                    m.append(this.procMemInfo.activeFile);
                    m.append(" ");
                    m.append(this.procMemInfo.inactiveFile);
                    m.append(" ");
                    m.append(this.procMemInfo.rbinFree);
                    m.append(" ");
                    m.append(this.procMemInfo.rbinCached);
                    m.append(" ");
                    m.append(killPolicyManager.mTotalSwap);
                    m.append(" ");
                    m.append(this.procMemInfo.swapFree);
                    m.append(" ");
                    m.append(this.procMemInfo.kReclaimable);
                    m.append(" ");
                    m.append(this.procMemInfo.sReclaimable);
                    m.append(" ");
                    m.append(this.procMemInfo.sUnreclaim);
                    m.append(" 0 ");
                    m.append(this.procMemInfo.gpuSwap);
                    m.append(" ");
                    m.append(this.procMemInfo.systemCached);
                    m.append(" ");
                    m.append(this.procMemInfo.zram);
                    m.append(" ");
                    m.append(this.procMemInfo.gpuTotal);
                    m.append(" ");
                    m.append(this.procMemInfo.vmallocUsed);
                    m.append(" ");
                    m.append(this.procMemInfo.systemUncached);
                    m.append("]");
                } else {
                    m.append(", [null]");
                }
                if (this.dumpHeavyMemoryUsageByAdj != null) {
                    m.append(",[");
                    for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                        if (this.dumpHeavyMemoryUsageByAdj.containsKey(str)) {
                            long[] jArr = (long[]) this.dumpHeavyMemoryUsageByAdj.get(str);
                            m.append("{");
                            Objects.requireNonNull(killPolicyManager.mProcessHeavyMemory);
                            m.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                            m.append(" ");
                            Objects.requireNonNull(killPolicyManager.mProcessHeavyMemory);
                            m.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                            m.append(" ");
                            Objects.requireNonNull(killPolicyManager.mProcessHeavyMemory);
                            m.append((int) ((jArr[2] / 1024.0d) + 0.5d));
                            m.append("}");
                        } else {
                            m.append("{-1 -1 -1}");
                        }
                    }
                    m.append("]");
                } else {
                    m.append(", [null]");
                }
                for (int i = 0; i < this.dumpHeavyProcessList.size(); i++) {
                    DumpHeavyProcessInfo dumpHeavyProcessInfo =
                            (DumpHeavyProcessInfo) this.dumpHeavyProcessList.get(i);
                    m.append(",[");
                    m.append(dumpHeavyProcessInfo.memoryInfo.procName);
                    m.append(", ver: ");
                    m.append(dumpHeavyProcessInfo.processVersion);
                    if (dumpHeavyProcessInfo.packageName != null) {
                        m.append(", PackageName: ");
                        m.append(dumpHeavyProcessInfo.packageName);
                    } else {
                        m.append(", PackageName: null");
                    }
                    if (dumpHeavyProcessInfo.packageVersion != null) {
                        m.append(", PackageVer: ");
                        m.append(dumpHeavyProcessInfo.packageVersion);
                    } else {
                        m.append(", PackageVer: null");
                    }
                    m.append(", adj: ");
                    m.append(dumpHeavyProcessInfo.memoryInfo.label);
                    m.append(", pss: ");
                    m.append((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d));
                    m.append(" mb, swap: ");
                    m.append((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d));
                    m.append(" mb, rss: ");
                    m.append((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                    m.append(" mb, ");
                    ProcessMemoryHeavyInfo processMemoryHeavyInfo =
                            killPolicyManager.mProcessHeavyMemory;
                    ArrayList arrayList = dumpHeavyProcessInfo.pssUssRssInfo;
                    processMemoryHeavyInfo.getClass();
                    m.append(ProcessMemoryHeavyInfo.dumpPssUssRssToString(arrayList));
                    m.append("]");
                }
            }
            return m;
        }

        public final void updateKillInfo() {
            long totalCriticalKillCount = SecLmkdStats.getTotalCriticalKillCount();
            this.lmkdCric = totalCriticalKillCount - this.prevLmkdCric;
            this.lmkdMed =
                    (SecLmkdStats.getKillCountFromSlotRange(0, 15, false, false)
                                    - totalCriticalKillCount)
                            - this.prevLmkdMed;
            long[] jArr = {0};
            Process.readProcFile(
                    "/sys/module/lowmemorykiller/parameters/lmkcount",
                    new int[] {8224},
                    null,
                    jArr,
                    null);
            this.lmkCnt = jArr[0] - this.prevLmkCnt;
        }

        public final void updateMemInfo(ProcMemInfo procMemInfo) {
            int i = procMemInfo.memFree + procMemInfo.cached;
            int i2 = KillPolicyManager.this.mTotalSwap - procMemInfo.swapFree;
            this.accMem += i;
            this.accSwap += i2;
            this.avlMem += procMemInfo.memAvailable;
            if (this.minMem > i) {
                this.minMem = i;
            }
            if (this.maxMem < i) {
                this.maxMem = i;
            }
            if (this.minSwap > i2) {
                this.minSwap = i2;
            }
            if (this.maxSwap < i2) {
                this.maxSwap = i2;
            }
        }

        public final void updateProcessStateInfo(int i, String str) {
            String str2;
            this.appCnt++;
            String str3 = KillPolicyManager.this.mPrevPackage;
            if (str3 != null && !((HashMap) this.launchedAndKilledPackageMap).containsKey(str3)) {
                ((HashMap) this.launchedAndKilledPackageMap).put(str3, 1000);
                if (KillPolicyManager.KPM_DEBUG
                        && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "add addLaunchedPackage : ", str3, ", cur count : ");
                    m.append(((HashMap) this.launchedAndKilledPackageMap).size());
                    Slog.d("ActivityManager_kpm", m.toString());
                }
            }
            LmkdCounter lmkdCounter = this.lmkdCounter;
            LmkdCountReader lmkdCountReader = KillPolicyManager.this.mLmkdReader;
            lmkdCounter.getClass();
            lmkdCounter.totalKillCount =
                    lmkdCountReader.totalCountOfLmkd - lmkdCounter.prevTotalCountOfLmkd;
            lmkdCounter.cachedMinKillCount =
                    lmkdCountReader.cachedMinCountOfLmkd - lmkdCounter.prevCachedMinCountOfLmkd;
            lmkdCounter.pickedKillCount =
                    lmkdCountReader.pickedCountOfLmkd - lmkdCounter.prevPickedCountOfLmkd;
            lmkdCounter.bServiceKillCount =
                    lmkdCountReader.bServiceCountOfLmkd - lmkdCounter.prevBServiceCountOfLmkd;
            long j = lmkdCountReader.previousCountOfLmkd - lmkdCounter.prevPreviousCountOfLmkd;
            lmkdCounter.previousKillCount = j;
            KillPolicyManager killPolicyManager = KillPolicyManager.this;
            KpmRaw kpmRaw = lmkdCounter.kpmRaw;
            if (j > 0) {
                lmkdCounter.previousKillOccurredCount++;
                if (kpmRaw != null) {
                    m191$$Nest$mmarkKilledPackage(kpmRaw, killPolicyManager.mPrevPackage, 700);
                }
            }
            if (lmkdCounter.bServiceKillCount > 0) {
                lmkdCounter.bServiceKillOccurredCount++;
                if (kpmRaw != null) {
                    m191$$Nest$mmarkKilledPackage(kpmRaw, killPolicyManager.mPrevPackage, 800);
                }
            }
            if (KillPolicyManager.KPM_DEBUG
                    && kpmRaw == killPolicyManager.getCurrentKpmRawPolicy()) {
                StringBuilder m2 =
                        BootReceiver$$ExternalSyntheticOutline0.m(128, "prevKillCount : (pE : ");
                m2.append(lmkdCounter.prevTotalCountOfLmkd);
                m2.append(", pPr : ");
                m2.append(lmkdCounter.prevPreviousCountOfLmkd);
                m2.append(", pSv : ");
                m2.append(lmkdCounter.prevBServiceCountOfLmkd);
                m2.append(", pPi : ");
                m2.append(lmkdCounter.prevPickedCountOfLmkd);
                m2.append(", pCm : ");
                m2.append(lmkdCounter.prevCachedMinCountOfLmkd);
                m2.append(")");
                Slog.d("ActivityManager_kpm", m2.toString());
                m2.setLength(0);
                m2.append("currentKillCount : (cE : ");
                m2.append(lmkdCounter.totalKillCount);
                m2.append(", cPr : ");
                m2.append(lmkdCounter.previousKillCount);
                m2.append(", cSv : ");
                m2.append(lmkdCounter.bServiceKillCount);
                m2.append(", cPi : ");
                m2.append(lmkdCounter.pickedKillCount);
                m2.append(", cCm : ");
                m2.append(lmkdCounter.cachedMinKillCount);
                m2.append(")");
                Slog.d("ActivityManager_kpm", m2.toString());
            }
            lmkdCounter.prevTotalCountOfLmkd = lmkdCountReader.totalCountOfLmkd;
            lmkdCounter.prevCachedMinCountOfLmkd = lmkdCountReader.cachedMinCountOfLmkd;
            lmkdCounter.prevPickedCountOfLmkd = lmkdCountReader.pickedCountOfLmkd;
            lmkdCounter.prevBServiceCountOfLmkd = lmkdCountReader.bServiceCountOfLmkd;
            lmkdCounter.prevPreviousCountOfLmkd = lmkdCountReader.previousCountOfLmkd;
            if (i == 9) {
                this.hotCount++;
                str2 = "HOT(" + Integer.toString(i) + ")";
            } else if (i == 8) {
                this.warmCount++;
                str2 = "WARM(" + Integer.toString(i) + ")";
            } else if (i == 7) {
                this.coldCount++;
                str2 = "COLD(" + Integer.toString(i) + ")";
            } else {
                str2 = "";
            }
            int i2 = this.hotCount;
            int i3 = this.warmCount + i2 + this.coldCount;
            this.hotRatio = i3 > 0 ? (int) (((i2 * 100.0d) / i3) + 0.5d) : 0;
            if (KillPolicyManager.KPM_DEBUG
                    && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "updateLaunchState() pkg ", str, " type ", str2, "ActivityManager_kpm");
            }
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            for (int i8 = 0;
                    i8 < KillPolicyManager.this.mAm.mProcessList.mLruProcesses.size();
                    i8++) {
                try {
                    try {
                        ProcessRecord processRecord =
                                (ProcessRecord)
                                        KillPolicyManager.this.mAm.mProcessList.mLruProcesses.get(
                                                i8);
                        if (processRecord != null) {
                            int i9 = processRecord.mState.mCurAdj;
                            if (i9 >= 900 && i9 <= 999) {
                                i4++;
                            }
                            if (i9 == 850) {
                                i5++;
                            }
                            if (processRecord.mWindowProcessController.mHasActivities
                                    && processRecord.mState.mCurProcState == 16) {
                                if (i9 == 850) {
                                    i7++;
                                } else {
                                    i6++;
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                    }
                } catch (Exception e2) {
                    Slog.e("ActivityManager_kpm", Log.getStackTraceString(e2));
                }
            }
            this.cachedTotalCnt += i4;
            this.pickedTotalCnt += i5;
            this.cachedActTotalCnt += i6;
            this.pickedActTotalCnt += i7;
            this.currentCachedActCnt = i6;
            int i10 = this.appCnt;
            LmkdCounter lmkdCounter2 = this.lmkdCounter;
            this.prKilledRatio =
                    i10 > 0
                            ? (int)
                                    (((lmkdCounter2.previousKillOccurredCount * 100.0d) / i10)
                                            + 0.5d)
                            : 0;
            this.svKilledRatio =
                    i10 > 0
                            ? (int)
                                    (((lmkdCounter2.bServiceKillOccurredCount * 100.0d) / i10)
                                            + 0.5d)
                            : 0;
            this.launchedPackageCount = ((HashMap) this.launchedAndKilledPackageMap).size();
            Iterator it = ((HashMap) this.launchedAndKilledPackageMap).keySet().iterator();
            int i11 = 0;
            while (it.hasNext()) {
                if (((Integer) ((HashMap) this.launchedAndKilledPackageMap).get((String) it.next()))
                                .intValue()
                        <= 700) {
                    i11++;
                }
            }
            this.killedPackageCount = i11;
            int i12 = this.launchedPackageCount;
            KillPolicyManager.this.getClass();
            this.killedPackageRatio = i12 > 0 ? (int) (((i11 * 100.0d) / i12) + 0.5d) : 0;
            if (KillPolicyManager.KPM_DEBUG
                    && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                StringBuilder m3 =
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                128, "updateProcessStateInfo() appCnt - ");
                m3.append(this.appCnt);
                m3.append(" launch total - ");
                m3.append(this.hotCount + this.warmCount + this.coldCount);
                m3.append(" hot launch(");
                m3.append(this.hotRatio);
                m3.append("%) hot - ");
                m3.append(this.hotCount);
                m3.append(" warm - ");
                m3.append(this.warmCount);
                m3.append(" cold - ");
                m3.append(this.coldCount);
                Slog.d("ActivityManager_kpm", m3.toString());
                m3.setLength(0);
                m3.append("cur previousKillRatio = ");
                m3.append(this.prKilledRatio);
                m3.append("%, serviceKillRatio = ");
                m3.append(this.svKilledRatio);
                m3.append("%, killedPackageRatio = ");
                m3.append(this.killedPackageRatio);
                m3.append("%");
                Slog.d("ActivityManager_kpm", m3.toString());
                m3.setLength(0);
                m3.append("updateRawProcessCount() Average Cached (T:");
                m3.append((int) ((this.cachedTotalCnt / this.appCnt) + 0.5d));
                m3.append("/A:");
                m3.append((int) ((this.cachedActTotalCnt / this.appCnt) + 0.5d));
                m3.append("), Picked (T:");
                m3.append((int) ((this.pickedTotalCnt / this.appCnt) + 0.5d));
                m3.append("/A:");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m3,
                        (int) ((this.pickedActTotalCnt / this.appCnt) + 0.5d),
                        ")",
                        "ActivityManager_kpm");
            }
        }

        public final void updatePsiInfo(double d, double d2, double d3) {
            this.psiCpuSum += d;
            this.psiMemorySum += d2;
            this.psiIoSum += d3;
            if (this.psiCpuMax < d) {
                this.psiCpuMax = d;
            }
            if (this.psiMemoryMax < d2) {
                this.psiMemoryMax = d2;
            }
            if (this.psiIoMax < d3) {
                this.psiIoMax = d3;
            }
            if (KillPolicyManager.KPM_DEBUG
                    && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                Slog.d(
                        "ActivityManager_kpm",
                        "Psi info cpu : " + d + ", memory : " + d2 + ", io : " + d3);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum KpmState {
        NONE(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG),
        LIGHT("Light"),
        NORMAL(Constants.RLC_STATE_NORMAL),
        HEAVY("Heavy"),
        CRITICAL("Critical");

        private String mName;

        KpmState(String str) {
            this.mName = str;
        }

        public final String getName() {
            return this.mName;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum LmkdCount {
        /* JADX INFO: Fake field, exist only in values array */
        EF7("CACHED_APP_MAX_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF17("CACHED_APP_MIN_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF27("SEEDBED_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF37("PICKED_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF47("ARCHIVED_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF57("SERVICE_B_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF67("PREVIOUS_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF77("HOME_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF88("SERVICE_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF99("HEAVY_WEIGHT_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF110("BORA_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF121("BACKUP_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF132("PERCEPTIBLE_LOW_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF145("PERCEPTIBLE_MEDIUM_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF158("PERCEPTIBLE_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF171("VISIBLE_APP_ADJ"),
        /* JADX INFO: Fake field, exist only in values array */
        EF183("FOREGROUND_APP_ADJ");

        private int mADJ;

        LmkdCount(String str) {
            this.mADJ = r2;
        }

        public final int getADJ() {
            return this.mADJ;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LmkdCountReader {
        public long bServiceCountOfLmkd;
        public long cachedMinCountOfLmkd;
        public long homeToPercCountOfLmkd;
        public long pickedCountOfLmkd;
        public long previousCountOfLmkd;
        public long totalCountOfLmkd;
        public long visToFgCountOfLmkd;

        public final void readLmkdKillCount() {
            this.totalCountOfLmkd = SecLmkdStats.getKillCountFromSlotRange(0, 15, false, false);
            this.cachedMinCountOfLmkd =
                    SecLmkdStats.getKillCountFromSlotRange(14, 14, false, false);
            this.pickedCountOfLmkd = SecLmkdStats.getKillCountFromSlotRange(12, 13, true, false);
            this.bServiceCountOfLmkd = SecLmkdStats.getKillCountFromSlotRange(11, 12, true, false);
            this.previousCountOfLmkd = SecLmkdStats.getKillCountFromSlotRange(10, 11, true, false);
            this.homeToPercCountOfLmkd =
                    SecLmkdStats.getKillCountFromSlotRange(3, 10, false, false);
            this.visToFgCountOfLmkd = SecLmkdStats.getKillCountFromSlotRange(0, 3, false, true);
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder m =
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                128, "readLmkdKillCount() : (E : ");
                m.append(this.totalCountOfLmkd);
                m.append(", Pr : ");
                m.append(this.previousCountOfLmkd);
                m.append(", Sv : ");
                m.append(this.bServiceCountOfLmkd);
                m.append(", Pi : ");
                m.append(this.pickedCountOfLmkd);
                m.append(", Cm : ");
                m.append(this.cachedMinCountOfLmkd);
                m.append(")");
                Slog.d("ActivityManager_kpm", m.toString());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LmkdCounter {
        public int bServiceKillOccurredCount;
        public final KpmRaw kpmRaw;
        public long prevBServiceCountOfLmkd;
        public long prevCachedMinCountOfLmkd;
        public long prevCycleBServiceCountOfLmkd;
        public long prevCycleCachedMinCountOfLmkd;
        public long prevCycleHomeToPercCountOfLmkd;
        public long prevCyclePickedCountOfLmkd;
        public long prevCyclePreviousCountOfLmkd;
        public long prevCycleTotalCountOfLmkd;
        public long prevCycleVisToFgCountOfLmkd;
        public long prevPickedCountOfLmkd;
        public long prevPreviousCountOfLmkd;
        public long prevTinyCycleBServiceCountOfLmkd;
        public long prevTinyCycleCachedMinCountOfLmkd;
        public long prevTinyCyclePickedCountOfLmkd;
        public long prevTinyCyclePreviousCountOfLmkd;
        public long prevTotalCountOfLmkd;
        public int previousKillOccurredCount;
        public long tinyCycleBServiceKillCount;
        public long tinyCycleCachedMinKillCount;
        public long tinyCyclePickedKillCount;
        public long tinyCyclePreviousKillCount;
        public long cycleTotalKillCount = 0;
        public long cycleCachedMinKillCount = 0;
        public long cyclePickedKillCount = 0;
        public long cycleBServiceKillCount = 0;
        public long cyclePreviousKillCount = 0;
        public long cycleHomeToPercKillCount = 0;
        public long cycleVisToFgKillCount = 0;
        public long totalKillCount = 0;
        public long cachedMinKillCount = 0;
        public long pickedKillCount = 0;
        public long bServiceKillCount = 0;
        public long previousKillCount = 0;

        public LmkdCounter(KpmRaw kpmRaw, LmkdCountReader lmkdCountReader) {
            long j = lmkdCountReader.totalCountOfLmkd;
            this.prevCycleTotalCountOfLmkd = j;
            long j2 = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevCycleCachedMinCountOfLmkd = j2;
            long j3 = lmkdCountReader.pickedCountOfLmkd;
            this.prevCyclePickedCountOfLmkd = j3;
            long j4 = lmkdCountReader.bServiceCountOfLmkd;
            this.prevCycleBServiceCountOfLmkd = j4;
            long j5 = lmkdCountReader.previousCountOfLmkd;
            this.prevCyclePreviousCountOfLmkd = j5;
            this.prevCycleHomeToPercCountOfLmkd = lmkdCountReader.homeToPercCountOfLmkd;
            this.prevCycleVisToFgCountOfLmkd = lmkdCountReader.visToFgCountOfLmkd;
            this.prevTotalCountOfLmkd = j;
            this.prevCachedMinCountOfLmkd = j2;
            this.prevPickedCountOfLmkd = j3;
            this.prevBServiceCountOfLmkd = j4;
            this.prevPreviousCountOfLmkd = j5;
            this.previousKillOccurredCount = 0;
            this.bServiceKillOccurredCount = 0;
            this.tinyCycleCachedMinKillCount = 0L;
            this.tinyCyclePickedKillCount = 0L;
            this.tinyCycleBServiceKillCount = 0L;
            this.tinyCyclePreviousKillCount = 0L;
            this.prevTinyCycleCachedMinCountOfLmkd = j2;
            this.prevTinyCyclePickedCountOfLmkd = j3;
            this.prevTinyCycleBServiceCountOfLmkd = j4;
            this.prevTinyCyclePreviousCountOfLmkd = j5;
            this.kpmRaw = kpmRaw;
        }

        public final void getCycleLmkdKillCountByADJ(LmkdCountReader lmkdCountReader) {
            this.cycleTotalKillCount =
                    lmkdCountReader.totalCountOfLmkd - this.prevCycleTotalCountOfLmkd;
            this.cycleCachedMinKillCount =
                    lmkdCountReader.cachedMinCountOfLmkd - this.prevCycleCachedMinCountOfLmkd;
            this.cyclePickedKillCount =
                    lmkdCountReader.pickedCountOfLmkd - this.prevCyclePickedCountOfLmkd;
            this.cycleBServiceKillCount =
                    lmkdCountReader.bServiceCountOfLmkd - this.prevCycleBServiceCountOfLmkd;
            this.cyclePreviousKillCount =
                    lmkdCountReader.previousCountOfLmkd - this.prevCyclePreviousCountOfLmkd;
            this.cycleHomeToPercKillCount =
                    lmkdCountReader.homeToPercCountOfLmkd - this.prevCycleHomeToPercCountOfLmkd;
            this.cycleVisToFgKillCount =
                    lmkdCountReader.visToFgCountOfLmkd - this.prevCycleVisToFgCountOfLmkd;
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder m =
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                128, "Cycle currentKillCountLmkd : (cE : ");
                m.append(this.cycleTotalKillCount);
                m.append(", cPr : ");
                m.append(this.cyclePreviousKillCount);
                m.append(", cSv : ");
                m.append(this.cycleBServiceKillCount);
                m.append(", cPi : ");
                m.append(this.cyclePickedKillCount);
                m.append(", cCm : ");
                m.append(this.cycleCachedMinKillCount);
                m.append(")");
                Slog.d("ActivityManager_kpm", m.toString());
            }
            this.prevCycleTotalCountOfLmkd = lmkdCountReader.totalCountOfLmkd;
            this.prevCycleCachedMinCountOfLmkd = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevCyclePickedCountOfLmkd = lmkdCountReader.pickedCountOfLmkd;
            this.prevCycleBServiceCountOfLmkd = lmkdCountReader.bServiceCountOfLmkd;
            this.prevCyclePreviousCountOfLmkd = lmkdCountReader.previousCountOfLmkd;
            this.prevCycleHomeToPercCountOfLmkd = lmkdCountReader.homeToPercCountOfLmkd;
            this.prevCycleVisToFgCountOfLmkd = lmkdCountReader.visToFgCountOfLmkd;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum LmkdState {
        LEVEL_0("LEVEL_0"),
        LEVEL_1("LEVEL_1"),
        LEVEL_2("LEVEL_2"),
        LEVEL_3("LEVEL_3"),
        LEVEL_4("LEVEL_4"),
        LEVEL_5("LEVEL_5"),
        /* JADX INFO: Fake field, exist only in values array */
        EF6("LEVEL_6"),
        /* JADX INFO: Fake field, exist only in values array */
        EF7("LEVEL_7"),
        LEVEL_8("LEVEL_8"),
        /* JADX INFO: Fake field, exist only in values array */
        EF9("LEVEL_9"),
        LEVEL_10("LEVEL_10"),
        /* JADX INFO: Fake field, exist only in values array */
        EF11("LEVEL_11"),
        LEVEL_12("LEVEL_12"),
        /* JADX INFO: Fake field, exist only in values array */
        EF13("LEVEL_13"),
        LEVEL_14("LEVEL_14"),
        /* JADX INFO: Fake field, exist only in values array */
        EF157("LEVEL_15");

        private int mLevel;

        LmkdState(String str) {
            this.mLevel = r2;
        }

        public final int getLevel() {
            return this.mLevel;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MemoryDumpItem {
        public final long mDumpSize;
        public final MemoryLoggingType mMemoryType;

        public MemoryDumpItem(long j, MemoryLoggingType memoryLoggingType) {
            this.mDumpSize = j;
            this.mMemoryType = memoryLoggingType;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MemoryFloodDetector {
        public Pair mLastCalculatedNative;
        public Pair mLastCalculatedSysPers;
        public long mLastRealTimeMillis;
        public int mPmmCycleCountOnPlatformReset;
        public List mNativeMemDumpList = new ArrayList();
        public List mSystemMemDumpList = new ArrayList();
        public List mPersistentMemDumpList = new ArrayList();
        public long mLastUptimeMillis = 0;

        public MemoryFloodDetector() {
            SystemClock.uptimeMillis();
            this.mLastRealTimeMillis = System.currentTimeMillis();
            this.mPmmCycleCountOnPlatformReset = 0;
            this.mLastCalculatedNative = new Pair(0, 0);
            this.mLastCalculatedSysPers = new Pair(0, 0);
        }

        public static void dumpItem(PrintWriter printWriter, String str, List list) {
            printWriter.print(str);
            printWriter.print(" : ");
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                MemoryDumpItem memoryDumpItem = (MemoryDumpItem) it.next();
                printWriter.print(
                        memoryDumpItem.mMemoryType == MemoryLoggingType.IdleDump ? " I " : " P ");
                printWriter.print(memoryDumpItem.mDumpSize);
            }
            printWriter.println();
        }

        public static List getCompactList(List list) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                ArrayList arrayList2 = (ArrayList) list;
                if (i >= arrayList2.size()) {
                    return arrayList;
                }
                arrayList.add((MemoryDumpItem) arrayList2.get(i));
                i += 2;
            }
        }

        public static JSONArray getJsonSummary(List list) {
            JSONArray jSONArray = new JSONArray();
            LongSummaryStatistics summaryStatistics =
                    list.stream()
                            .mapToLong(
                                    new KillPolicyManager$MemoryFloodDetector$$ExternalSyntheticLambda0())
                            .summaryStatistics();
            jSONArray
                    .put(
                            (int)
                                    ((((MemoryDumpItem) list.get(list.size() - 1)).mDumpSize / 1024)
                                            + 0.5d))
                    .put(summaryStatistics.getMin())
                    .put(summaryStatistics.getMax())
                    .put((int) (summaryStatistics.getAverage() + 0.5d))
                    .put(list.size());
            return jSONArray;
        }

        public static Pair getLinearRegressionFactor(List list) {
            int size = list.size();
            double d = 0.0d;
            double d2 = 0.0d;
            double d3 = 0.0d;
            for (int i = 0; i < size; i++) {
                d2 += i;
                d3 += ((MemoryDumpItem) list.get(i)).mDumpSize;
            }
            double d4 = size;
            double d5 = d2 / d4;
            double d6 = d3 / d4;
            double d7 = 0.0d;
            for (int i2 = 0; i2 < size; i2++) {
                double d8 = i2 - d5;
                d7 += d8 * d8;
                long j = ((MemoryDumpItem) list.get(i2)).mDumpSize;
                long j2 = ((MemoryDumpItem) list.get(i2)).mDumpSize;
                d += (((MemoryDumpItem) list.get(i2)).mDumpSize - d6) * d8;
            }
            double d9 = d / d7;
            return new Pair(Double.valueOf(d9), Double.valueOf(d6 - (d5 * d9)));
        }

        public static long getPmmMinDump(String str, KpmRaw kpmRaw, long j) {
            if (!kpmRaw.procsAdjPss.containsKey(str)) {
                return j;
            }
            long[] jArr = (long[]) kpmRaw.procsAdjPss.get(str);
            long j2 = jArr[0] + jArr[1];
            return j2 < j ? j2 : j;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class MemoryLoggingType {
        public static final /* synthetic */ MemoryLoggingType[] $VALUES;
        public static final MemoryLoggingType IdleDump;
        public static final MemoryLoggingType PmmDump;

        static {
            MemoryLoggingType memoryLoggingType = new MemoryLoggingType("IdleDump", 0);
            IdleDump = memoryLoggingType;
            MemoryLoggingType memoryLoggingType2 = new MemoryLoggingType("PmmDump", 1);
            PmmDump = memoryLoggingType2;
            $VALUES = new MemoryLoggingType[] {memoryLoggingType, memoryLoggingType2};
        }

        public static MemoryLoggingType valueOf(String str) {
            return (MemoryLoggingType) Enum.valueOf(MemoryLoggingType.class, str);
        }

        public static MemoryLoggingType[] values() {
            return (MemoryLoggingType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MemoryStabilityEventManager {
        public final Reporter mReporter = new Reporter();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Counter {
            public static final int[] FORMAT_ZRAM_ERROR = {8202, 8224, 8224};
            public final long[] countOfEvents = new long[2];
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Reporter {
            public boolean mHasReportedAtLeastOnce;
            public int mLastRealtimeMinutes;
            public int mLastUptimeMinutes;
            public final Counter mLastCounter = new Counter();
            public final Counter mTempCounter = new Counter();
            public final Counter mDiffCounter = new Counter();

            public Reporter() {}
        }

        public MemoryStabilityEventManager() {}

        public final void report() {
            int i;
            int i2;
            boolean z;
            Reporter reporter = this.mReporter;
            reporter.getClass();
            int uptimeMillis = (int) (SystemClock.uptimeMillis() / 60000);
            int elapsedRealtime = (int) (SystemClock.elapsedRealtime() / 60000);
            if (reporter.mHasReportedAtLeastOnce) {
                int i3 = uptimeMillis - reporter.mLastUptimeMinutes;
                i2 = elapsedRealtime - reporter.mLastRealtimeMinutes;
                i = i3;
            } else {
                i = uptimeMillis;
                i2 = elapsedRealtime;
            }
            if (!KillPolicyManager.KPM_DEBUG && i2 < 1400) {
                HermesService$3$$ExternalSyntheticOutline0.m(
                        i2, "reportDiff() Skipped. diffRealtimeMinutes=", "ActivityManager_kpm");
                return;
            }
            Counter counter = reporter.mTempCounter;
            counter.getClass();
            long[] jArr = new long[3];
            try {
                z =
                        Process.readProcFile(
                                "/sys/block/zram0/mm_stat",
                                Counter.FORMAT_ZRAM_ERROR,
                                null,
                                jArr,
                                null);
            } catch (NullPointerException | OutOfMemoryError e) {
                Slog.e("ActivityManager_kpm", e.getMessage());
                z = false;
            }
            long[] jArr2 = counter.countOfEvents;
            if (z) {
                jArr2[0] = jArr[1];
                jArr2[1] = jArr[2];
            } else {
                jArr2[0] = -1;
                jArr2[1] = -1;
            }
            Slog.i("ActivityManager_kpm", "readProcFile(mmstat) success=" + z);
            Counter counter2 = reporter.mDiffCounter;
            counter2.getClass();
            for (int i4 = 1; i4 >= 0; i4--) {
                counter2.countOfEvents[i4] = counter.countOfEvents[i4];
            }
            boolean z2 = reporter.mHasReportedAtLeastOnce;
            long[] jArr3 = counter2.countOfEvents;
            Counter counter3 = reporter.mLastCounter;
            if (z2) {
                for (int i5 = 1; i5 >= 0; i5--) {
                    long min = Math.min(jArr3[i5], counter3.countOfEvents[i5]);
                    if (min >= 0) {
                        min = jArr3[i5] - counter3.countOfEvents[i5];
                    }
                    jArr3[i5] = min;
                }
            }
            reporter.mLastUptimeMinutes = uptimeMillis;
            reporter.mLastRealtimeMinutes = elapsedRealtime;
            counter3.getClass();
            for (int i6 = 1; i6 >= 0; i6--) {
                counter3.countOfEvents[i6] = counter.countOfEvents[i6];
            }
            if (!reporter.mHasReportedAtLeastOnce) {
                reporter.mHasReportedAtLeastOnce = true;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("UTDIFF", i);
                jSONObject.put("RTDIFF", i2);
                jSONObject.put("ZRERR", new JSONArray(jArr3));
                String jSONObject2 = jSONObject.toString();
                String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
                KillPolicyManager killPolicyManager = KillPolicyManager.this;
                if (killPolicyManager.mHqmManager == null) {
                    SemHqmManager semHqmManager =
                            (SemHqmManager)
                                    killPolicyManager.mContext.getSystemService(
                                            "HqmManagerService");
                    killPolicyManager.mHqmManager = semHqmManager;
                    if (semHqmManager == null) {
                        if (KillPolicyManager.KPM_DEBUG) {
                            Slog.d("ActivityManager_kpm", "HQM services is not working");
                            return;
                        }
                        return;
                    }
                }
                if (killPolicyManager.mHqmManager.sendHWParamToHQM(
                        KillPolicyManager.KPM_DEBUG ? 1 : 0,
                        "Sluggish",
                        "MSEC",
                        "ph",
                        "1.1",
                        "sec",
                        "",
                        substring,
                        "")) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            "Success to report 'MSEC' : ", substring, "ActivityManager_kpm");
                } else {
                    Slog.d("ActivityManager_kpm", "Failed to send to server");
                }
            } catch (JSONException e2) {
                Slog.e("ActivityManager_kpm", "JSON Exception: " + e2.getMessage());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcMemInfo {
        public int activeFile;
        public int cached;
        public int gpuSwap;
        public int gpuTotal;
        public int inactiveFile;
        public int kReclaimable;
        public int memAvailable;
        public int memFree;
        public int memTotal;
        public int rbinCached;
        public int rbinFree;
        public int sReclaimable;
        public int sUnreclaim;
        public int swapFree;
        public int systemCached;
        public int systemUncached;
        public int vmallocUsed;
        public int zram;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessMemoryHeavyInfo {
        public AnonymousClass1 mProcessMemoryDumpThread;
        public boolean isThreadRunning = false;
        public int peakLmkdKillAdj = 1001;
        public List procMemDumpInfoList = new ArrayList();
        public ArrayList dumpHeavyProcessList = new ArrayList();
        public ArrayList fgsMemDumpList = new ArrayList();
        public ProcMemInfo procMemInfo = null;
        public HashMap memoryUsageByAdj = new HashMap();
        public HashMap dumpHeavyProcessByAdj = new HashMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class PssComparator implements Comparator {
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                ActivityManagerService.MemDumpInfo memDumpInfo =
                        (ActivityManagerService.MemDumpInfo) obj;
                ActivityManagerService.MemDumpInfo memDumpInfo2 =
                        (ActivityManagerService.MemDumpInfo) obj2;
                long j = memDumpInfo.pss + memDumpInfo.swap_out;
                long j2 = memDumpInfo2.pss + memDumpInfo2.swap_out;
                if (j < j2) {
                    return 1;
                }
                return j > j2 ? -1 : 0;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x011a  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x016c  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x019f  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x01e1  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x013a  */
        /* renamed from: -$$Nest$mgetProcessMemoryDumpInformation, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void m193$$Nest$mgetProcessMemoryDumpInformation(
                com.android.server.am.KillPolicyManager.ProcessMemoryHeavyInfo r30,
                boolean r31,
                com.android.server.am.KillPolicyManager.ProcMemInfo r32) {
            /*
                Method dump skipped, instructions count: 726
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.KillPolicyManager.ProcessMemoryHeavyInfo.m193$$Nest$mgetProcessMemoryDumpInformation(com.android.server.am.KillPolicyManager$ProcessMemoryHeavyInfo,"
                        + " boolean, com.android.server.am.KillPolicyManager$ProcMemInfo):void");
        }

        /* renamed from: -$$Nest$mreportHeavyProcessTop5ByAdj, reason: not valid java name */
        public static void m194$$Nest$mreportHeavyProcessTop5ByAdj(
                ProcessMemoryHeavyInfo processMemoryHeavyInfo) {
            String[] strArr;
            int i;
            int i2;
            ArrayList arrayList;
            int i3;
            String str;
            int length;
            HashMap hashMap = processMemoryHeavyInfo.dumpHeavyProcessByAdj;
            if (hashMap == null) {
                Slog.i("ActivityManager_kpm", "dumpHeavyProcessByAdj is null");
                return;
            }
            if (hashMap.size() == 0) {
                Slog.i("ActivityManager_kpm", "dumpHeavyProcessByAdj is 0 size");
                return;
            }
            JSONObject jSONObject = new JSONObject();
            long uptimeMillis = (SystemClock.uptimeMillis() / 1000) / 60;
            try {
                String[] strArr2 = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL;
                int length2 = strArr2.length;
                int i4 = 0;
                while (i4 < length2) {
                    String str2 = strArr2[i4];
                    if (!processMemoryHeavyInfo.dumpHeavyProcessByAdj.containsKey(str2)
                            || (arrayList =
                                            (ArrayList)
                                                    processMemoryHeavyInfo.dumpHeavyProcessByAdj
                                                            .get(str2))
                                    == null
                            || arrayList.size() <= 0) {
                        strArr = strArr2;
                        i = length2;
                        i2 = i4;
                    } else {
                        String str3 = str2.toUpperCase() + "TF";
                        JSONArray jSONArray = new JSONArray();
                        int i5 = 0;
                        while (i5 < arrayList.size()) {
                            DumpHeavyProcessInfo dumpHeavyProcessInfo =
                                    (DumpHeavyProcessInfo) arrayList.get(i5);
                            int length3 = jSONObject.toString().length();
                            int length4 = str3.length();
                            String[] strArr3 = strArr2;
                            int length5 = jSONArray.toString().length();
                            int i6 = length2;
                            int length6 = dumpHeavyProcessInfo.memoryInfo.procName.length();
                            ArrayList arrayList2 = arrayList;
                            String str4 = dumpHeavyProcessInfo.packageVersion;
                            if (str4 != null) {
                                int length7 = str4.length();
                                str = str3;
                                i3 = i4;
                                length = length7;
                            } else {
                                i3 = i4;
                                str = str3;
                                length =
                                        String.valueOf(dumpHeavyProcessInfo.processVersion)
                                                .length();
                            }
                            int i7 = i3;
                            if (length3 + length4 + length5 + length6 + length > 2020) {
                                if (KillPolicyManager.KPM_DEBUG) {
                                    StringBuilder sb = new StringBuilder(128);
                                    sb.append("reportHeavyProcessTop5ByAdj ");
                                    sb.append(length3);
                                    sb.append(" ");
                                    sb.append(length4);
                                    sb.append(" ");
                                    sb.append(length5);
                                    sb.append(" ");
                                    sb.append(length6);
                                    sb.append(" ");
                                    sb.append(length);
                                    Slog.d("ActivityManager_kpm", sb.toString());
                                }
                                processMemoryHeavyInfo.sendHqmHeavyProcessTop5ByAdj(
                                        jSONObject, uptimeMillis);
                                jSONObject = new JSONObject();
                            }
                            jSONArray.put(dumpHeavyProcessInfo.memoryInfo.procName);
                            String str5 = dumpHeavyProcessInfo.packageVersion;
                            if (str5 != null) {
                                jSONArray.put(str5);
                            } else {
                                jSONArray.put(String.valueOf(dumpHeavyProcessInfo.processVersion));
                            }
                            jSONArray
                                    .put(
                                            (int)
                                                    ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d)
                                                            + 0.5d))
                                    .put(
                                            (int)
                                                    ((dumpHeavyProcessInfo.memoryInfo.swap_out
                                                                    / 1024.0d)
                                                            + 0.5d))
                                    .put(
                                            (int)
                                                    ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d)
                                                            + 0.5d));
                            i5++;
                            length2 = i6;
                            strArr2 = strArr3;
                            arrayList = arrayList2;
                            str3 = str;
                            i4 = i7;
                        }
                        strArr = strArr2;
                        i = length2;
                        i2 = i4;
                        jSONObject.put(str3, jSONArray);
                    }
                    i4 = i2 + 1;
                    length2 = i;
                    strArr2 = strArr;
                }
            } catch (JSONException unused) {
                Slog.d("ActivityManager_kpm", "failed to create the KPUT");
            }
            processMemoryHeavyInfo.sendHqmHeavyProcessTop5ByAdj(jSONObject, uptimeMillis);
        }

        /* renamed from: -$$Nest$mshowDebugAdjMemory, reason: not valid java name */
        public static void m195$$Nest$mshowDebugAdjMemory(
                ProcessMemoryHeavyInfo processMemoryHeavyInfo) {
            processMemoryHeavyInfo.getClass();
            for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                if (processMemoryHeavyInfo.memoryUsageByAdj.containsKey(str)) {
                    long[] jArr = (long[]) processMemoryHeavyInfo.memoryUsageByAdj.get(str);
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("[ADJ Label] : ");
                    sb.append(str);
                    sb.append(" - Pss: ");
                    sb.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                    sb.append(" mb, swapPss: ");
                    sb.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                    sb.append(" mb, Rss: ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            sb, (int) ((jArr[2] / 1024.0d) + 0.5d), " mb", "ActivityManager_kpm");
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "[ADJ Label] : ", str, " - this ADJ not found", "ActivityManager_kpm");
                }
            }
        }

        public ProcessMemoryHeavyInfo() {}

        public static String dumpPssUssRssToString(ArrayList arrayList) {
            StringBuilder sb = new StringBuilder(128);
            if (arrayList == null) {
                return "null";
            }
            Iterator it = arrayList.iterator();
            boolean z = true;
            while (it.hasNext()) {
                DumpPssUssRssInfo dumpPssUssRssInfo = (DumpPssUssRssInfo) it.next();
                if (dumpPssUssRssInfo.sampleCount == -1) {
                    return "empty";
                }
                String str =
                        DumpUtils.ADJ_SCREEN_NAMES_CSV[dumpPssUssRssInfo.screenIndex]
                                + "_"
                                + DumpUtils.ADJ_MEM_NAMES_CSV[dumpPssUssRssInfo.memoryFactorIndex]
                                + "_"
                                + DumpUtils.STATE_NAMES_CSV[dumpPssUssRssInfo.stateIndex];
                if (z) {
                    z = false;
                } else {
                    sb.append(",");
                }
                sb.append(str);
                sb.append(":");
                sb.append(dumpPssUssRssInfo.sampleCount);
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.minPss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.avgPss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.maxPss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.minUss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.avgUss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.maxUss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.minRss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.avgRss / 1024.0d) + 0.5d));
                sb.append(":");
                sb.append((int) ((dumpPssUssRssInfo.maxRss / 1024.0d) + 0.5d));
            }
            return sb.toString();
        }

        public static ArrayList getPssUssRssInfo(ProcessState processState) {
            ArrayList arrayList = new ArrayList();
            if (processState == null) {
                return null;
            }
            for (int i = 0; i < ProcessStats.ALL_SCREEN_ADJ.length; i++) {
                for (int i2 = 0; i2 < ProcessStats.ALL_MEM_ADJ.length; i2++) {
                    int i3 = 0;
                    while (true) {
                        int[] iArr = ProcessStats.ALL_PROC_STATES;
                        if (i3 < iArr.length) {
                            int i4 =
                                    ((ProcessStats.ALL_SCREEN_ADJ[i] + ProcessStats.ALL_MEM_ADJ[i2])
                                                    * 16)
                                            + iArr[i3];
                            long pssSampleCount = processState.getPssSampleCount(i4);
                            if (pssSampleCount > 0) {
                                DumpPssUssRssInfo dumpPssUssRssInfo = new DumpPssUssRssInfo();
                                dumpPssUssRssInfo.screenIndex = i;
                                dumpPssUssRssInfo.memoryFactorIndex = i2;
                                dumpPssUssRssInfo.stateIndex = i3;
                                dumpPssUssRssInfo.sampleCount = (int) pssSampleCount;
                                dumpPssUssRssInfo.minPss = (int) processState.getPssMinimum(i4);
                                dumpPssUssRssInfo.avgPss = (int) processState.getPssAverage(i4);
                                dumpPssUssRssInfo.maxPss = (int) processState.getPssMaximum(i4);
                                dumpPssUssRssInfo.minUss = (int) processState.getPssUssMinimum(i4);
                                dumpPssUssRssInfo.avgUss = (int) processState.getPssUssAverage(i4);
                                dumpPssUssRssInfo.maxUss = (int) processState.getPssUssMaximum(i4);
                                dumpPssUssRssInfo.minRss = (int) processState.getPssRssMinimum(i4);
                                dumpPssUssRssInfo.avgRss = (int) processState.getPssRssAverage(i4);
                                dumpPssUssRssInfo.maxRss = (int) processState.getPssRssMaximum(i4);
                                arrayList.add(dumpPssUssRssInfo);
                            }
                            i3++;
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                DumpPssUssRssInfo dumpPssUssRssInfo2 = new DumpPssUssRssInfo();
                dumpPssUssRssInfo2.sampleCount = -1;
                arrayList.add(dumpPssUssRssInfo2);
            }
            return arrayList;
        }

        public static void showDebugHeavyProcess(ArrayList arrayList) {
            if (arrayList == null || arrayList.size() <= 0) {
                Slog.d("ActivityManager_kpm", "ProcessMemoryHeavy not found");
                return;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                DumpHeavyProcessInfo dumpHeavyProcessInfo = (DumpHeavyProcessInfo) arrayList.get(i);
                StringBuilder m =
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                128, "showDebugHeavyProcess() process : ");
                m.append(dumpHeavyProcessInfo.memoryInfo.procName);
                m.append(", ver: ");
                m.append(dumpHeavyProcessInfo.processVersion);
                if (dumpHeavyProcessInfo.packageName != null) {
                    m.append(", PackageName: ");
                    m.append(dumpHeavyProcessInfo.packageName);
                }
                if (dumpHeavyProcessInfo.packageVersion != null) {
                    m.append(", PackageVer: ");
                    m.append(dumpHeavyProcessInfo.packageVersion);
                }
                m.append(", adj: ");
                m.append(dumpHeavyProcessInfo.memoryInfo.label);
                m.append(", pss: ");
                m.append((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d));
                m.append(" mb, swap: ");
                m.append((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d));
                m.append(" mb, rss: ");
                m.append((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                m.append(" mb, ");
                m.append(dumpPssUssRssToString(dumpHeavyProcessInfo.pssUssRssInfo));
                Slog.d("ActivityManager_kpm", m.toString());
            }
        }

        public final void fillDumpHeavyProcessTop5ByAdj(
                ActivityManagerService.MemDumpInfo memDumpInfo, ProcessState processState) {
            if (!this.dumpHeavyProcessByAdj.containsKey(memDumpInfo.label)) {
                ArrayList arrayList = new ArrayList();
                this.dumpHeavyProcessByAdj.put(memDumpInfo.label, arrayList);
                DumpHeavyProcessInfo dumpHeavyProcessInfo = new DumpHeavyProcessInfo();
                arrayList.add(dumpHeavyProcessInfo);
                dumpHeavyProcessInfo.memoryInfo = memDumpInfo;
                fillProcessPackageNameAndVersion(processState, dumpHeavyProcessInfo);
                dumpHeavyProcessInfo.pssUssRssInfo = getPssUssRssInfo(processState);
                return;
            }
            ArrayList arrayList2 = (ArrayList) this.dumpHeavyProcessByAdj.get(memDumpInfo.label);
            if (arrayList2.size() < 5) {
                DumpHeavyProcessInfo dumpHeavyProcessInfo2 = new DumpHeavyProcessInfo();
                arrayList2.add(dumpHeavyProcessInfo2);
                dumpHeavyProcessInfo2.memoryInfo = memDumpInfo;
                fillProcessPackageNameAndVersion(processState, dumpHeavyProcessInfo2);
                dumpHeavyProcessInfo2.pssUssRssInfo = getPssUssRssInfo(processState);
            }
        }

        public final void fillProcessPackageNameAndVersion(
                ProcessState processState, DumpHeavyProcessInfo dumpHeavyProcessInfo) {
            if (processState != null) {
                try {
                    if (processState.getPackage() != null) {
                        dumpHeavyProcessInfo.processVersion = processState.getVersion();
                        dumpHeavyProcessInfo.packageName = processState.getPackage();
                        dumpHeavyProcessInfo.packageVersion =
                                KillPolicyManager.this
                                        .mContext
                                        .getPackageManager()
                                        .getPackageInfo(processState.getPackage(), 0)
                                        .versionName;
                    } else {
                        dumpHeavyProcessInfo.processVersion = processState.getVersion();
                        dumpHeavyProcessInfo.packageName = null;
                        dumpHeavyProcessInfo.packageVersion = null;
                    }
                } catch (Exception e) {
                    Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                }
            }
        }

        public final void sendHqmHeavyProcessTop5ByAdj(JSONObject jSONObject, long j) {
            String str;
            String str2 = null;
            try {
                jSONObject.put("TYPE", 2);
                jSONObject.put("UPTM", j);
                str2 = jSONObject.toString();
                str = str2.substring(1, str2.length() - 1);
            } catch (JSONException unused) {
                Slog.d("ActivityManager_kpm", "failed to create the KPUT");
                str = str2;
            }
            KillPolicyManager.this.sendHqmBigData(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessMemoryUsageInfo {
        public AnonymousClass1 mProcDumpMemThread;
        public final ProcessSamplingManager mProcessSamplingManager;
        public HashMap procsMemoryPssKbByADJ;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.am.KillPolicyManager$ProcessMemoryUsageInfo$1, reason: invalid class name */
        public final class AnonymousClass1 extends Thread {
            public final /* synthetic */ KpmRaw val$lastCycle;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KpmRaw kpmRaw) {
                super("KPMProcessMemoryDumpThread");
                this.val$lastCycle = kpmRaw;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    try {
                        Slog.i("ActivityManager_kpm", "KPM end cycle get pss");
                        if (this.val$lastCycle.isDailyBigdata) {
                            ProcessMemoryUsageInfo processMemoryUsageInfo =
                                    ProcessMemoryUsageInfo.this;
                            processMemoryUsageInfo.mProcessSamplingManager.activate(
                                    KillPolicyManager.this.mContext);
                        }
                        ProcessMemoryUsageInfo.m196$$Nest$mgetProcDumpMemInfo(
                                ProcessMemoryUsageInfo.this);
                        if (KillPolicyManager.KPM_DEBUG) {
                            ProcessMemoryUsageInfo.m197$$Nest$mshowDebugAdjPss(
                                    ProcessMemoryUsageInfo.this);
                        }
                        KpmRaw kpmRaw = this.val$lastCycle;
                        ProcessMemoryUsageInfo processMemoryUsageInfo2 =
                                ProcessMemoryUsageInfo.this;
                        kpmRaw.procsAdjPss = processMemoryUsageInfo2.procsMemoryPssKbByADJ;
                        if (kpmRaw.isStateChanged) {
                            KillPolicyManager.this.reportUserTrend(0, kpmRaw);
                        }
                        KpmRaw kpmRaw2 = this.val$lastCycle;
                        if (kpmRaw2.isDailyBigdata) {
                            KillPolicyManager.this.reportUserTrend(1, kpmRaw2);
                        }
                        ProcessMemoryUsageInfo.this.mProcessSamplingManager.report(
                                this.val$lastCycle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ProcessMemoryUsageInfo.this.mProcessSamplingManager.deactivate();
                    ProcessMemoryUsageInfo processMemoryUsageInfo3 = ProcessMemoryUsageInfo.this;
                    processMemoryUsageInfo3.getClass();
                    processMemoryUsageInfo3.procsMemoryPssKbByADJ = new HashMap();
                } catch (Throwable th) {
                    ProcessMemoryUsageInfo.this.mProcessSamplingManager.deactivate();
                    throw th;
                }
            }
        }

        /* renamed from: -$$Nest$mgetProcDumpMemInfo, reason: not valid java name */
        public static void m196$$Nest$mgetProcDumpMemInfo(
                ProcessMemoryUsageInfo processMemoryUsageInfo) {
            ArrayList arrayList;
            processMemoryUsageInfo.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            SparseArray sparseArray = new SparseArray();
            ActivityManagerService activityManagerService = KillPolicyManager.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    arrayList =
                            new ArrayList(KillPolicyManager.this.mAm.mProcessList.mLruProcesses);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ProcessRecord processRecord = (ProcessRecord) it.next();
                        sparseArray.put(processRecord.mPid, processRecord);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            KillPolicyManager.this.mAm.mAppProfiler.forAllCpuStats(
                    new KillPolicyManager$PsiFile$$ExternalSyntheticLambda0(
                            processMemoryUsageInfo, sparseArray));
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ProcessRecord processRecord2 = (ProcessRecord) arrayList.get(size);
                int setAdjWithServices = processRecord2.mState.getSetAdjWithServices();
                int i = processRecord2.mState.mSetProcState;
                String str = processRecord2.processName;
                int i2 = 0;
                while (true) {
                    int[] iArr = ActivityManagerService.DUMP_MEM_OOM_ADJ;
                    if (i2 >= iArr.length) {
                        i2 = -1;
                        break;
                    } else if (i2 != iArr.length - 1
                            && (setAdjWithServices < iArr[i2]
                                    || setAdjWithServices >= iArr[i2 + 1])) {
                        i2++;
                    }
                }
                processMemoryUsageInfo.getProcDumpMemInfoInternal(processRecord2.mPid, i2, i, str);
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.i(
                        "ActivityManager_kpm",
                        "getProcDumpMemInfo() elapsed time " + currentTimeMillis2 + " ms");
            }
        }

        /* renamed from: -$$Nest$mshowDebugAdjPss, reason: not valid java name */
        public static void m197$$Nest$mshowDebugAdjPss(
                ProcessMemoryUsageInfo processMemoryUsageInfo) {
            processMemoryUsageInfo.getClass();
            for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                if (processMemoryUsageInfo.procsMemoryPssKbByADJ.containsKey(str)) {
                    long[] jArr = (long[]) processMemoryUsageInfo.procsMemoryPssKbByADJ.get(str);
                    long j = jArr[0] + jArr[1];
                    if (j < 0) {
                        j = 0;
                    }
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("[ADJ Label] : ");
                    sb.append(str);
                    sb.append(" - total: ");
                    sb.append((int) ((j / 1024.0d) + 0.5d));
                    sb.append(" mb, pss: ");
                    sb.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                    sb.append(" mb, swappss: ");
                    sb.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                    sb.append(" mb, writeback: ");
                    sb.append((int) ((jArr[2] / 1024.0d) + 0.5d));
                    sb.append(" mb, count: ");
                    BatteryService$$ExternalSyntheticOutline0.m(sb, jArr[3], "ActivityManager_kpm");
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "[ADJ Label] : ", str, " - this ADJ not found", "ActivityManager_kpm");
                }
            }
        }

        public ProcessMemoryUsageInfo() {
            new ArrayList();
            this.procsMemoryPssKbByADJ = new HashMap();
            this.mProcessSamplingManager = KillPolicyManager.this.new ProcessSamplingManager();
        }

        public final void getProcDumpMemInfoInternal(int i, int i2, int i3, String str) {
            long[] jArr;
            int i4;
            String str2;
            if (i <= 0) {
                jArr = null;
            } else {
                String[] strArr = {"Pss:", "SwapPss:", "Writeback:"};
                String m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "/proc/", "/smaps_rollup");
                long[] jArr2 = new long[3];
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(m));
                    int i5 = 0;
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (readLine.startsWith(strArr[i5])) {
                                if (readLine.split("\\s+").length == 3) {
                                    jArr2[i5] = Integer.parseInt(r5[1]);
                                }
                                i5++;
                                if (i5 == 3) {
                                    break;
                                }
                            }
                        } finally {
                        }
                    }
                    bufferedReader.close();
                } catch (IOException unused) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            "failed to read ", m, "ActivityManager");
                } catch (RuntimeException unused2) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            "failed to read ", m, "ActivityManager");
                }
                jArr = jArr2;
            }
            if (jArr == null) {
                return;
            }
            long[] readMemtrackMemory = Heimdall.readMemtrackMemory(i);
            if (readMemtrackMemory != null
                    && readMemtrackMemory.length >= 4
                    && readMemtrackMemory[3] == 0) {
                jArr[0] =
                        readMemtrackMemory[0]
                                + readMemtrackMemory[1]
                                + readMemtrackMemory[2]
                                + jArr[0];
            }
            String str3 = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL[i2];
            if (this.procsMemoryPssKbByADJ.containsKey(str3)) {
                long[] jArr3 = (long[]) this.procsMemoryPssKbByADJ.get(str3);
                jArr3[0] = jArr3[0] + jArr[0];
                jArr3[1] = jArr3[1] + jArr[1];
                i4 = 2;
                jArr3[2] = jArr3[2] + jArr[2];
                jArr3[3] = jArr3[3] + 1;
            } else {
                this.procsMemoryPssKbByADJ.put(str3, new long[] {jArr[0], jArr[1], jArr[2], 1});
                i4 = 2;
            }
            long j = jArr[0];
            long j2 = jArr[1];
            long j3 = jArr[i4];
            ProcessSamplingManager processSamplingManager = this.mProcessSamplingManager;
            if (processSamplingManager.mCurrentState == i4) {
                if (str == null) {
                    try {
                        str2 = ProcfsMemoryUtil.readCmdlineFromProcfs(i);
                    } catch (OutOfMemoryError unused3) {
                        str2 = "";
                    }
                    if ("".equals(str2)) {
                        str2 = "<invalid>";
                    }
                } else {
                    str2 = str;
                }
                ProcessSampler processSampler = processSamplingManager.mSampler;
                SampleProcessInfo sampleProcessInfo = new SampleProcessInfo();
                sampleProcessInfo.processName = str2;
                sampleProcessInfo.adjLabelIndex = i2;
                sampleProcessInfo.procState = i3;
                sampleProcessInfo.pssInMb = (int) (((j + j2) / 1024.0d) + 0.5d);
                sampleProcessInfo.swapPssInMb = (int) ((j2 / 1024.0d) + 0.5d);
                sampleProcessInfo.writebackInMb = (int) ((j3 / 1024.0d) + 0.5d);
                processSampler.mTotalProcessInfos.add(sampleProcessInfo);
            }
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("getProcDumpMemInfo() adj: ");
                sb.append(str3);
                sb.append(", pss: ");
                sb.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                sb.append(" mb, swappss: ");
                sb.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                sb.append(" mb, writeback:");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb, (int) ((jArr[2] / 1024.0d) + 0.5d), " mb", "ActivityManager_kpm");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessSampler {
        public static final int[] ADJ_PRIORITY_BY_LABEL_INDEX = {
            3, 3, 3, 3, 4, 0, 1, 1, 1, 5, 5, 2, 5, 4, 2, 6, 6
        };
        public static final boolean[] ALWAYS_RUNNING_ADJ_BY_LABEL_INDEX = {
            true, true, true, true, true, true, true, true, true, true, true, true, true, false,
            true, false, false
        };
        public boolean mIsUserTrialDevice;
        public ArrayList mTotalProcessInfos;

        public final List sampleAdjPriority(int i, boolean z) {
            if (z) {
                final int i2 = 0;
                this.mTotalProcessInfos.sort(
                        Comparator.comparingInt(
                                new ToIntFunction() { // from class:
                                                      // com.android.server.am.KillPolicyManager$ProcessSampler$$ExternalSyntheticLambda0
                                    @Override // java.util.function.ToIntFunction
                                    public final int applyAsInt(Object obj) {
                                        KillPolicyManager.SampleProcessInfo sampleProcessInfo =
                                                (KillPolicyManager.SampleProcessInfo) obj;
                                        switch (i2) {
                                            case 0:
                                                int i3 = sampleProcessInfo.adjLabelIndex;
                                                if (!((i3 < 0 || i3 >= 17)
                                                                ? false
                                                                : KillPolicyManager.ProcessSampler
                                                                        .ALWAYS_RUNNING_ADJ_BY_LABEL_INDEX[
                                                                        i3])
                                                        || i3 < 0
                                                        || i3 >= 17) {
                                                    return 1000;
                                                }
                                                return KillPolicyManager.ProcessSampler
                                                        .ADJ_PRIORITY_BY_LABEL_INDEX[i3];
                                            default:
                                                int i4 = sampleProcessInfo.adjLabelIndex;
                                                if (i4 < 0 || i4 >= 17) {
                                                    return 1000;
                                                }
                                                return KillPolicyManager.ProcessSampler
                                                        .ADJ_PRIORITY_BY_LABEL_INDEX[i4];
                                        }
                                    }
                                }));
                int i3 = 0;
                while (i3 < this.mTotalProcessInfos.size()) {
                    int i4 = ((SampleProcessInfo) this.mTotalProcessInfos.get(i3)).adjLabelIndex;
                    if (!((i4 < 0 || i4 >= 17) ? false : ALWAYS_RUNNING_ADJ_BY_LABEL_INDEX[i4])) {
                        break;
                    }
                    i3++;
                }
                if (i > i3) {
                    i = i3;
                }
            } else {
                final int i5 = 1;
                this.mTotalProcessInfos.sort(
                        Comparator.comparingInt(
                                new ToIntFunction() { // from class:
                                                      // com.android.server.am.KillPolicyManager$ProcessSampler$$ExternalSyntheticLambda0
                                    @Override // java.util.function.ToIntFunction
                                    public final int applyAsInt(Object obj) {
                                        KillPolicyManager.SampleProcessInfo sampleProcessInfo =
                                                (KillPolicyManager.SampleProcessInfo) obj;
                                        switch (i5) {
                                            case 0:
                                                int i32 = sampleProcessInfo.adjLabelIndex;
                                                if (!((i32 < 0 || i32 >= 17)
                                                                ? false
                                                                : KillPolicyManager.ProcessSampler
                                                                        .ALWAYS_RUNNING_ADJ_BY_LABEL_INDEX[
                                                                        i32])
                                                        || i32 < 0
                                                        || i32 >= 17) {
                                                    return 1000;
                                                }
                                                return KillPolicyManager.ProcessSampler
                                                        .ADJ_PRIORITY_BY_LABEL_INDEX[i32];
                                            default:
                                                int i42 = sampleProcessInfo.adjLabelIndex;
                                                if (i42 < 0 || i42 >= 17) {
                                                    return 1000;
                                                }
                                                return KillPolicyManager.ProcessSampler
                                                        .ADJ_PRIORITY_BY_LABEL_INDEX[i42];
                                        }
                                    }
                                }));
                if (i > this.mTotalProcessInfos.size()) {
                    i = this.mTotalProcessInfos.size();
                }
            }
            return this.mTotalProcessInfos.subList(0, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessSamplingManager {
        public int mCurrentState;
        public boolean mIsSampleDevice;
        public ProcessSampler mSampler;
        public int mSkipCount;

        public ProcessSamplingManager() {}

        /* JADX WARN: Removed duplicated region for block: B:34:0x0125  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x012a  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0135  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x013f  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x014a  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0173  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0141  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x012c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void activate(android.content.Context r17) {
            /*
                Method dump skipped, instructions count: 423
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.KillPolicyManager.ProcessSamplingManager.activate(android.content.Context):void");
        }

        public final void deactivate() {
            if (this.mCurrentState != 2) {
                return;
            }
            this.mSampler.mTotalProcessInfos.clear();
            this.mCurrentState = 1;
            Slog.i("ActivityManager_kpm", "Process sampler deactivated!");
        }

        public final void report(KpmRaw kpmRaw) {
            String str;
            String str2;
            int i;
            String str3;
            long j;
            int i2;
            long j2;
            long j3;
            if (this.mCurrentState != 2) {
                return;
            }
            kpmRaw.mProcessSampler = this.mSampler;
            KillPolicyManager killPolicyManager = KillPolicyManager.this;
            killPolicyManager.getClass();
            if (KillPolicyManager.KPM_POLICY_ENABLE) {
                String str4 = "ActivityManager_kpm";
                if (kpmRaw.appCnt == 0) {
                    Slog.d("ActivityManager_kpm", "it has 'zero' samples.");
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("TYPE", 5);
                    jSONObject.put("DRAM", killPolicyManager.mTotalMem * 1024);
                    jSONObject.put("SWPT", killPolicyManager.mTotalSwap);
                    jSONObject.put("UPTM", (SystemClock.uptimeMillis() / 1000) / 60);
                    jSONObject.put("KVER", "4.6");
                    JSONArray jSONArray = new JSONArray();
                    char c = 0;
                    if (kpmRaw.procsAdjPss != null) {
                        String[] strArr = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL;
                        int length = strArr.length;
                        int i3 = 0;
                        while (i3 < length) {
                            String str5 = strArr[i3];
                            if (kpmRaw.procsAdjPss.containsKey(str5)) {
                                i2 = length;
                                j2 = (long) (((r2[c] + r2[1]) / 1024.0d) + 0.5d);
                                j3 = ((long[]) kpmRaw.procsAdjPss.get(str5))[3];
                            } else {
                                i2 = length;
                                j2 = 0;
                                j3 = 0;
                            }
                            jSONArray.put(j2).put(j3);
                            i3++;
                            length = i2;
                            c = 0;
                        }
                        for (String str6 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                            jSONArray.put(
                                    kpmRaw.procsAdjPss.containsKey(str6)
                                            ? (long)
                                                    ((((long[]) kpmRaw.procsAdjPss.get(r10))[1]
                                                                    / 1024.0d)
                                                            + 0.5d)
                                            : 0L);
                        }
                        String[] strArr2 = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL;
                        int length2 = strArr2.length;
                        int i4 = 0;
                        while (i4 < length2) {
                            if (kpmRaw.procsAdjPss.containsKey(strArr2[i4])) {
                                str3 = str4;
                                try {
                                    j =
                                            (long)
                                                    ((((long[]) kpmRaw.procsAdjPss.get(r10))[2]
                                                                    / 1024.0d)
                                                            + 0.5d);
                                } catch (JSONException unused) {
                                    str = str3;
                                    str2 = null;
                                    Slog.d(str, "failed to create the KPUT");
                                    killPolicyManager.sendHqmBigData(str2);
                                }
                            } else {
                                str3 = str4;
                                j = 0;
                            }
                            jSONArray.put(j);
                            i4++;
                            str4 = str3;
                        }
                        str3 = str4;
                        i = 2;
                    } else {
                        i = 2;
                        str3 = "ActivityManager_kpm";
                    }
                    jSONObject.put("PRST", jSONArray);
                    JSONArray jSONArray2 = new JSONArray();
                    ProcessSampler processSampler = kpmRaw.mProcessSampler;
                    if (processSampler != null) {
                        List sampleAdjPriority =
                                processSampler.sampleAdjPriority(
                                        processSampler.mTotalProcessInfos.size(),
                                        !kpmRaw.mProcessSampler.mIsUserTrialDevice);
                        int i5 = i;
                        for (int i6 = 0; i6 < sampleAdjPriority.size(); i6++) {
                            JSONArray jsonArray =
                                    ((SampleProcessInfo) sampleAdjPriority.get(i6)).toJsonArray();
                            if (i6 > 0) {
                                i5++;
                            }
                            i5 += jsonArray.toString().length();
                            if (i5 > 19500) {
                                break;
                            }
                            jSONArray2.put(jsonArray);
                        }
                    }
                    jSONObject.put("PRS", jSONArray2);
                    str = str3;
                    try {
                        Slog.i(
                                str,
                                "ProcessSampleCount="
                                        + jSONArray2.length()
                                        + " ProcessSamplesJsonStringSize="
                                        + jSONArray2.toString().length());
                        str2 = jSONObject.toString();
                        try {
                            str2 = str2.substring(1, str2.length() - 1);
                        } catch (JSONException unused2) {
                            Slog.d(str, "failed to create the KPUT");
                            killPolicyManager.sendHqmBigData(str2);
                        }
                    } catch (JSONException unused3) {
                        str2 = null;
                        Slog.d(str, "failed to create the KPUT");
                        killPolicyManager.sendHqmBigData(str2);
                    }
                } catch (JSONException unused4) {
                    str = str4;
                }
                killPolicyManager.sendHqmBigData(str2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum PsiDataType {
        AVG10("avg10"),
        TOTAL("total");

        private String mPath;

        PsiDataType(String str) {
            this.mPath = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PsiFile {
        public boolean mIsEmptyFile;
        public double mSomeAvg10;

        public static String getItem(int i, String str, String str2) {
            String[] split = str.split(" ");
            String concat = str2.concat("=");
            return (split.length <= i || !split[i].startsWith(concat))
                    ? "0"
                    : split[i].replace(concat, "");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum PsiFileType {
        IO("/proc/pressure/io"),
        MEMORY("/proc/pressure/memory"),
        CPU("/proc/pressure/cpu");

        private String mPath;

        PsiFileType(String str) {
            this.mPath = str;
        }

        public final String getPath() {
            return this.mPath;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SampleProcessInfo {
        public int adjLabelIndex;
        public int procState;
        public String processName;
        public int pssInMb;
        public int swapPssInMb;
        public int writebackInMb;

        public final JSONArray toJsonArray() {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(this.processName);
            jSONArray.put(this.adjLabelIndex);
            jSONArray.put(this.procState);
            jSONArray.put(this.pssInMb);
            jSONArray.put(this.swapPssInMb);
            jSONArray.put(this.writebackInMb);
            return jSONArray;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SwappinessController {
        public int mCurrentValue;
        public final int mDefaultValue;

        public SwappinessController() {
            int swappinessFromProc = getSwappinessFromProc();
            this.mDefaultValue = swappinessFromProc;
            this.mCurrentValue = swappinessFromProc;
            HermesService$3$$ExternalSyntheticOutline0.m(
                    swappinessFromProc,
                    "SwappinessController() - default value: ",
                    "ActivityManager");
        }

        public static int getSwappinessFromProc() {
            long[] jArr = {0};
            Process.readProcFile("/proc/sys/vm/swappiness", new int[] {8224}, null, jArr, null);
            return (int) jArr[0];
        }
    }

    /* renamed from: -$$Nest$mreportResetState, reason: not valid java name */
    public static void m188$$Nest$mreportResetState(
            KillPolicyManager killPolicyManager, String str) {
        killPolicyManager.getClass();
        Intent intent = new Intent();
        intent.setAction("com.samsung.KPM_CRITICAL_MEMORY_STATUS");
        intent.setPackage(
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                                "com.samsung.android.lool"));
        intent.putExtra("resetType", str);
        MemoryFloodDetector memoryFloodDetector = killPolicyManager.mMemoryFloodDetector;
        intent.putExtra(
                "nativeStart",
                ((Integer) memoryFloodDetector.mLastCalculatedNative.first).intValue());
        intent.putExtra(
                "nativeEnd",
                ((Integer) memoryFloodDetector.mLastCalculatedNative.second).intValue());
        intent.putExtra(
                "sysPersStart",
                ((Integer) memoryFloodDetector.mLastCalculatedSysPers.first).intValue());
        intent.putExtra(
                "sysPersEnd",
                ((Integer) memoryFloodDetector.mLastCalculatedSysPers.second).intValue());
        intent.putExtra("pmmCyclePlatform", killPolicyManager.mPolicyMetric);
        intent.putExtra(
                "pmmCycleKernel",
                killPolicyManager.mPolicyMetric
                        + memoryFloodDetector.mPmmCycleCountOnPlatformReset);
        intent.putExtra(
                "uptimeSystemBoot",
                SystemClock.uptimeMillis() - killPolicyManager.mPlatformStartUpTimeMillis);
        killPolicyManager.mContext.sendBroadcast(intent);
        Slog.i("ActivityManager_kpm", "reportResetState : ".concat(str));
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.server.am.KillPolicyManager$1] */
    /* JADX WARN: Type inference failed for: r0v19, types: [com.android.server.am.KillPolicyManager$1] */
    public KillPolicyManager() {
        Uri.parse("content://com.samsung.android.sm.policy/policy_item/kpm");
        Uri.parse("content://com.samsung.android.sm.policy/policy_item/policy_list");
        LmkdCountReader lmkdCountReader = new LmkdCountReader();
        lmkdCountReader.totalCountOfLmkd = 0L;
        lmkdCountReader.cachedMinCountOfLmkd = 0L;
        lmkdCountReader.pickedCountOfLmkd = 0L;
        lmkdCountReader.bServiceCountOfLmkd = 0L;
        lmkdCountReader.previousCountOfLmkd = 0L;
        lmkdCountReader.homeToPercCountOfLmkd = 0L;
        lmkdCountReader.visToFgCountOfLmkd = 0L;
        this.mLmkdReader = lmkdCountReader;
        this.mKpmRawPolicy = new KpmRaw[24];
        this.mKpmRawBigdata = new KpmRaw[30];
        this.mProcMemDumpPolicy = new ProcessMemoryUsageInfo();
        this.mProcMemDumpBigdata = new ProcessMemoryUsageInfo();
        this.mProcessHeavyMemory = new ProcessMemoryHeavyInfo();
        this.mDisplaySizeStr = "";
        this.mPrevPackage = null;
        this.mPrevProcessList = new ArrayList();
        this.mDailyRandomSampleReceiver = null;
        this.mHasPsiCpuPermission = true;
        this.mHasPsiMemoryPermission = true;
        this.mHasPsiIoPermission = true;
        this.mRecentChimeraData = new ChimeraDataInfo();
        this.mSwappinessController = new SwappinessController();
        this.mMemoryFloodDetector = new MemoryFloodDetector();
        this.mPlatformStartUpTimeMillis = SystemClock.uptimeMillis();
        this.mLastIdleEnterRealTimeMillis = System.currentTimeMillis();
        this.mLastIdleExitRealTimeMillis = System.currentTimeMillis();
        this.mLastIdleExitUpTimeMillis = SystemClock.uptimeMillis();
        this.mLastIdleRealTimeMillis = 0L;
        this.mLastAwakeRealTimeMillis = 0L;
        this.mLastAwakeUpTimeMillis = 0L;
        this.mVmStats = new HashMap();
        final int i = 0;
        this.mIdleModeReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.am.KillPolicyManager.1
                    public final /* synthetic */ KillPolicyManager this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Can't wrap try/catch for region: R(41:67|(4:69|70|71|(2:73|(9:75|(4:77|78|79|80)|195|196|(1:198)|84|(6:176|(4:178|(1:188)(2:182|(1:184)(1:187))|185|186)|189|190|(1:192)|193)(1:88)|89|(28:94|(1:96)|97|(1:99)|100|(1:102)|103|(2:105|(23:107|(6:110|(4:112|113|126|129)|134|135|129|108)|136|137|138|(1:140)(1:174)|141|(1:143)|144|(1:146)|147|148|149|(1:151)|152|(1:154)|155|(1:157)|158|159|(1:161)|162|(2:164|165)))|175|138|(0)(0)|141|(0)|144|(0)|147|148|149|(0)|152|(0)|155|(0)|158|159|(0)|162|(0)(0)))))|202|84|(1:86)|176|(0)|189|190|(0)|193|89|(1:91)|94|(0)|97|(0)|100|(0)|103|(0)|175|138|(0)(0)|141|(0)|144|(0)|147|148|149|(0)|152|(0)|155|(0)|158|159|(0)|162|(0)) */
                    /* JADX WARN: Code restructure failed: missing block: B:173:0x058d, code lost:

                       android.util.Slog.d(r6, "failed to create the KPUT");
                    */
                    /* JADX WARN: Removed duplicated region for block: B:102:0x03e6  */
                    /* JADX WARN: Removed duplicated region for block: B:105:0x03f8  */
                    /* JADX WARN: Removed duplicated region for block: B:140:0x0495  */
                    /* JADX WARN: Removed duplicated region for block: B:143:0x04aa  */
                    /* JADX WARN: Removed duplicated region for block: B:146:0x04ba  */
                    /* JADX WARN: Removed duplicated region for block: B:151:0x0549 A[Catch: JSONException -> 0x058d, TryCatch #3 {JSONException -> 0x058d, blocks: (B:149:0x04e6, B:151:0x0549, B:152:0x0554, B:154:0x055e, B:155:0x0569, B:157:0x0573, B:158:0x057e), top: B:148:0x04e6 }] */
                    /* JADX WARN: Removed duplicated region for block: B:154:0x055e A[Catch: JSONException -> 0x058d, TryCatch #3 {JSONException -> 0x058d, blocks: (B:149:0x04e6, B:151:0x0549, B:152:0x0554, B:154:0x055e, B:155:0x0569, B:157:0x0573, B:158:0x057e), top: B:148:0x04e6 }] */
                    /* JADX WARN: Removed duplicated region for block: B:157:0x0573 A[Catch: JSONException -> 0x058d, TryCatch #3 {JSONException -> 0x058d, blocks: (B:149:0x04e6, B:151:0x0549, B:152:0x0554, B:154:0x055e, B:155:0x0569, B:157:0x0573, B:158:0x057e), top: B:148:0x04e6 }] */
                    /* JADX WARN: Removed duplicated region for block: B:161:0x0597  */
                    /* JADX WARN: Removed duplicated region for block: B:164:0x05b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:174:0x04a4  */
                    /* JADX WARN: Removed duplicated region for block: B:178:0x02ed  */
                    /* JADX WARN: Removed duplicated region for block: B:192:0x0340  */
                    /* JADX WARN: Removed duplicated region for block: B:86:0x02ac  */
                    /* JADX WARN: Removed duplicated region for block: B:91:0x039f  */
                    /* JADX WARN: Removed duplicated region for block: B:96:0x03be  */
                    /* JADX WARN: Removed duplicated region for block: B:99:0x03d2  */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(
                            android.content.Context r37, android.content.Intent r38) {
                        /*
                            Method dump skipped, instructions count: 1612
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.am.KillPolicyManager.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        this.mMemoryStabilityEventManager = new MemoryStabilityEventManager();
        final int i2 = 1;
        this.policyBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.am.KillPolicyManager.1
                    public final /* synthetic */ KillPolicyManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        /*
                            Method dump skipped, instructions count: 1612
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.am.KillPolicyManager.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        Slog.i("ActivityManager_kpm", "KillPolicyManager()");
    }

    public static KpmState changeState(KpmRaw kpmRaw) {
        LmkdCounter lmkdCounter = kpmRaw.lmkdCounter;
        return lmkdCounter.cyclePreviousKillCount > 0
                ? KpmState.CRITICAL
                : lmkdCounter.cycleCachedMinKillCount + lmkdCounter.cycleBServiceKillCount > 0
                        ? KpmState.HEAVY
                        : lmkdCounter.cyclePickedKillCount + ((long) kpmRaw.cachedNormalKillCount)
                                        > 0
                                ? KpmState.NORMAL
                                : KpmState.LIGHT;
    }

    public static void fillChimeraDataIfExist(KpmRaw kpmRaw, JSONObject jSONObject) {
        ChimeraDataInfo chimeraDataInfo = kpmRaw.chimeraDataInfo;
        if (chimeraDataInfo == null) {
            return;
        }
        jSONObject.put(
                "CHWT",
                new JSONArray()
                        .put((int) ((chimeraDataInfo.mLruWeight * 100.0f) + 0.5d))
                        .put((int) ((chimeraDataInfo.mStdBktWeight * 100.0f) + 0.5d))
                        .put((int) ((chimeraDataInfo.mMemWeight * 100.0f) + 0.5d)));
        int[] iArr = chimeraDataInfo.mTriggerCntSrc;
        jSONObject.put("CHTC", iArr[2]);
        int i = 0;
        jSONObject.put("CLTC", iArr[0]);
        jSONObject.put("CBTC", iArr[1]);
        int[] iArr2 = chimeraDataInfo.mActionCntSrc;
        jSONObject.put("CHAC", iArr2[2]);
        jSONObject.put("CLAC", iArr2[0]);
        jSONObject.put("CBAC", iArr2[1]);
        jSONObject.put("CKLC", chimeraDataInfo.mKillCnt);
        JSONArray jSONArray = new JSONArray();
        int i2 = 0;
        while (true) {
            int[] iArr3 = chimeraDataInfo.mAdjKillCnt;
            if (i2 >= iArr3.length) {
                break;
            }
            jSONArray.put(iArr3[i2]);
            i2++;
        }
        jSONObject.put("CKAI", jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        while (true) {
            int[] iArr4 = chimeraDataInfo.mGroupKillCnt;
            if (i >= iArr4.length) {
                jSONObject.put("CKGI", jSONArray2);
                return;
            } else {
                jSONArray2.put(iArr4[i]);
                i++;
            }
        }
    }

    public static void fillLmkdCounts(long[] jArr) {
        LmkdCount[] values = LmkdCount.values();
        int killCountFromSlotRange = SecLmkdStats.getKillCountFromSlotRange(0, 0, false, false);
        int i = 0;
        for (int length = values.length - 1; length >= 0; length--) {
            while (true) {
                int i2 = i + 1;
                int[] iArr = SecLmkdStats.LMKD_SLOT_ADJ_VALUES;
                if (i2 < 17 && iArr[i2] <= values[length].getADJ()) {
                    killCountFromSlotRange +=
                            SecLmkdStats.getKillCountFromSlotRange(i, i2, true, false);
                    i = i2;
                }
            }
            jArr[length] = killCountFromSlotRange;
        }
    }

    public static HashMap getMemInfoFromFile(String str) {
        FileReader fileReader;
        HashMap hashMap = new HashMap();
        try {
            fileReader = new FileReader(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
            try {
                for (String readLine = bufferedReader.readLine();
                        !TextUtils.isEmpty(readLine);
                        readLine = bufferedReader.readLine()) {
                    String[] split = readLine.split("[ :]+");
                    if (split != null && split.length >= 2) {
                        try {
                            hashMap.put(split[0], Long.valueOf(Long.parseLong(split[1])));
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
                bufferedReader.close();
                fileReader.close();
                return hashMap;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.am.KillPolicyManager.PsiFile getPsiFile(
            com.android.server.am.KillPolicyManager.PsiFileType r7) {
        /*
            java.lang.String r0 = "\n"
            java.lang.String r1 = "Exception"
            java.lang.String r2 = "ActivityManager"
            java.lang.String r3 = ""
            r4 = 0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            java.lang.String r7 = r7.getPath()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
        L17:
            java.lang.String r7 = r5.readLine()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            if (r7 != 0) goto L2b
            r5.close()     // Catch: java.lang.Exception -> L21
            goto L6d
        L21:
            r7 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r1)
        L27:
            com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r7, r4, r2)
            goto L6d
        L2b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r4.<init>()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r4.append(r3)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r4.append(r7)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r4.append(r0)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            goto L17
        L3e:
            r7 = move-exception
            r4 = r5
            goto L8d
        L41:
            r7 = move-exception
            r4 = r5
            goto L47
        L44:
            r7 = move-exception
            goto L8d
        L46:
            r7 = move-exception
        L47:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L44
            r5.<init>()     // Catch: java.lang.Throwable -> L44
            java.lang.String r6 = "getPsiFile Exception"
            r5.append(r6)     // Catch: java.lang.Throwable -> L44
            java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> L44
            r5.append(r7)     // Catch: java.lang.Throwable -> L44
            java.lang.String r7 = r5.toString()     // Catch: java.lang.Throwable -> L44
            android.util.Slog.e(r2, r7)     // Catch: java.lang.Throwable -> L44
            if (r4 == 0) goto L6d
            r4.close()     // Catch: java.lang.Exception -> L66
            goto L6d
        L66:
            r7 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r1)
            goto L27
        L6d:
            com.android.server.am.KillPolicyManager$PsiFile r7 = new com.android.server.am.KillPolicyManager$PsiFile
            r7.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 != 0) goto L89
            java.lang.String[] r0 = r3.split(r0)
            java.util.stream.Stream r0 = java.util.Arrays.stream(r0)
            com.android.server.am.KillPolicyManager$PsiFile$$ExternalSyntheticLambda0 r1 = new com.android.server.am.KillPolicyManager$PsiFile$$ExternalSyntheticLambda0
            r1.<init>(r7)
            r0.forEach(r1)
            goto L8c
        L89:
            r0 = 1
            r7.mIsEmptyFile = r0
        L8c:
            return r7
        L8d:
            if (r4 == 0) goto L9c
            r4.close()     // Catch: java.lang.Exception -> L93
            goto L9c
        L93:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r0, r3, r2)
        L9c:
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.KillPolicyManager.getPsiFile(com.android.server.am.KillPolicyManager$PsiFileType):com.android.server.am.KillPolicyManager$PsiFile");
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0179 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x017a  */
    /* JADX WARN: Type inference failed for: r7v11, types: [com.android.server.am.KillPolicyManager$ProcessMemoryHeavyInfo$1, java.lang.Thread] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculateLmkdStatus(int r17) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.KillPolicyManager.calculateLmkdStatus(int):void");
    }

    public final void changePolicy(KpmState kpmState) {
        if (KPM_DEBUG) {
            Slog.d("ActivityManager_kpm", "Policy [" + kpmState + "]");
        }
        this.mPolicyState = kpmState;
        int i = SWAPPINESS[kpmState.ordinal()];
        SwappinessController swappinessController = this.mSwappinessController;
        int i2 = swappinessController.mDefaultValue;
        if (i <= i2) {
            Slog.i(
                    "ActivityManager",
                    "SwappinessController() - new value is lower than default value, go back to"
                        + " default");
            i = i2;
        }
        SystemProperties.set("sys.sysctl.swappiness", String.valueOf(i));
        Slog.i(
                "ActivityManager",
                "SwappinessController() - changed from "
                        + swappinessController.mCurrentValue
                        + " -> "
                        + i);
        swappinessController.mCurrentValue = i;
        Intent intent = new Intent();
        intent.setPackage("android");
        intent.setAction("com.samsung.KPM_STATE_CHANGED");
        intent.putExtra("kpm_level", this.mCurrentState.ordinal());
        intent.putExtra("kpm_prev_level", this.mPrevState.ordinal());
        Slog.d(
                "ActivityManager_kpm",
                "Broadcast sent: prev state = "
                        + this.mPrevState.ordinal()
                        + ", cur state = "
                        + this.mCurrentState.ordinal());
        this.mContext.sendBroadcast(intent);
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        KpmRaw[] kpmRawArr;
        KpmRaw[] kpmRawArr2;
        int i = 0;
        if (strArr != null && strArr.length > 1) {
            String str = strArr[1];
            str.getClass();
            if (str.equals("leak.dmabuf")) {
                PersonalizedMemoryManager personalizedMemoryManager =
                        PersonalizedMemoryManager.LazyHolder.INSTANCE;
                personalizedMemoryManager.mDmaBufLeakDetector.mIsTestMode = true;
                personalizedMemoryManager.onMemoryEvent(
                        this.mContext, PersonalizedMemoryManager.MemoryEventType.LMKD_KILL);
                personalizedMemoryManager.mDmaBufLeakDetector.mIsTestMode = false;
                return;
            }
            return;
        }
        int i2 = this.mPolicyIndex;
        int i3 = this.mBigdataIndex;
        if (this.mPolicyMetric >= 1) {
            printWriter.println(" KPM Stats(policy):");
            printWriter.println(
                    " [idx,avg_mem,mem_avl,avg_swap,hotCnt,prKillCnt,svKillCnt,PkgCnt,PkgKillCnt,lmkdTotal,lmkdPrev,lmkdService,lmkdPicked,lmkdSeed,lmkdCri,lmk,cachedkill,emptykill,cur_state,next_state,policy_state,psi_cpu_avg,psi_mem_avg,psi_io_avg,psi_cpu_max,psi_mem_max,psi_io_max,reset_status,lmkdState,lmkdStateCnt,lmkdCnt,tiny_nxst_history,time]");
            int min = Math.min(this.mPolicyMetric, 24);
            int i4 = i2;
            int i5 = 0;
            while (true) {
                kpmRawArr2 = this.mKpmRawPolicy;
                if (i5 >= min) {
                    break;
                }
                printWriter.println(kpmRawArr2[i4].getKpmData(this.mPolicyMetric).toString());
                i4--;
                if (i4 < 0) {
                    i4 = 23;
                }
                i5++;
            }
            printWriter.println();
            printWriter.println(" Heavy Pss List(policy):");
            printWriter.println(
                    " [idx, peakLmkdKillAdj, [procMemInfo], [{native(Pss SwapPss"
                        + " Rss)}{sys}{pers}{persvc}{fore}{vis}{percept}{perceptl}{perceptm}{backup}{heavy}{servicea}{home}{prev}{serviceb}{picked}{cached}],"
                        + " [Process name, Process version, Package name, Package version, label,"
                        + " pss, swap, rss, procstats_dumpPssUssRss], ...]");
            for (int i6 = 0; i6 < min; i6++) {
                printWriter.println(kpmRawArr2[i2].getKpmHeavyPssData().toString());
                i2--;
                if (i2 < 0) {
                    i2 = 23;
                }
            }
        }
        if (this.mBigdataMetric >= 1) {
            printWriter.println();
            printWriter.println(" KPM Stats(bigdata):");
            printWriter.println(
                    " [idx,avg_mem,mem_avl,avg_swap,hotCnt,prKillCnt,svKillCnt,PkgCnt,PkgKillCnt,lmkdTotal,lmkdPrev,lmkdService,lmkdPicked,lmkdSeed,lmkdCri,lmk,cachedkill,emptykill,cur_state,next_state,policy_state,psi_cpu_avg,psi_mem_avg,psi_io_avg,psi_cpu_max,psi_mem_max,psi_io_max,reset_status,lmkdState,lmkdStateCnt,lmkdCnt,tiny_nxst_history,time]");
            int min2 = Math.min(this.mBigdataMetric, 30);
            int i7 = 0;
            int i8 = i3;
            while (true) {
                kpmRawArr = this.mKpmRawBigdata;
                if (i7 >= min2) {
                    break;
                }
                printWriter.println(kpmRawArr[i8].getKpmData(this.mBigdataMetric).toString());
                i8--;
                if (i8 < 0) {
                    i8 = 29;
                }
                i7++;
            }
            printWriter.println();
            printWriter.println(" Heavy Pss List(bigdata):");
            printWriter.println(
                    " [idx, peakLmkdKillAdj, [procMemInfo], [{native(Pss SwapPss"
                        + " Rss)}{sys}{pers}{persvc}{fore}{vis}{percept}{perceptl}{perceptm}{backup}{heavy}{servicea}{home}{prev}{serviceb}{picked}{cached}],"
                        + " [Process name, Process version, Package name, Package version, label,"
                        + " pss, swap, rss, procstats_dumpPssUssRss], ...]");
            for (int i9 = 0; i9 < min2; i9++) {
                printWriter.println(kpmRawArr[i3].getKpmHeavyPssData().toString());
                i3--;
                if (i3 < 0) {
                    i3 = 29;
                }
            }
        }
        printWriter.println(" ");
        printWriter.println(" KPM Tunable Parameters:");
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" KPM_POLICY_ENABLE: "),
                                KPM_POLICY_ENABLE,
                                printWriter,
                                " KPM_DEBUG_ENABLE: "),
                        KPM_DEBUG,
                        printWriter,
                        " KPM_CURRENT_STATE: ");
        m.append(this.mCurrentState);
        printWriter.println(m.toString());
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(" KPM_WARM_UP_TRIGGER_TUNABLE: "),
                                        sWarmUpTrigger,
                                        printWriter,
                                        " KPM_POLICY_TRIGGER_TUNABLE: "),
                                sPolicyTrigger,
                                printWriter,
                                " KPM_WARM_UP_CYCLES_TUNABLE: "),
                        sWarmUpCycles,
                        printWriter,
                        " KPM_MEM_CRITICAL_LOW_DETECT_ENABLE: ");
        m2.append(
                Boolean.parseBoolean(
                        SystemProperties.get(
                                "persist.sys.kpm_cri_mem_detect", String.valueOf(true))));
        printWriter.println(m2.toString());
        printWriter.println(" MEMORY_CRITICAL_LOW_KILL_DETECT_ADJ : 700");
        printWriter.println(
                " MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH : "
                        + MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH
                        + "%");
        StringBuilder m3 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO : "),
                        MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO,
                        printWriter,
                        " MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH : ");
        m3.append(MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH);
        m3.append("%");
        printWriter.println(m3.toString());
        StringBuilder sb = new StringBuilder(" KPM_POLICY_SWAPPINESS_DEFAULT : ");
        SwappinessController swappinessController = this.mSwappinessController;
        StringBuilder m4 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb,
                        swappinessController.mDefaultValue,
                        printWriter,
                        " KPM_POLICY_SWAPPINESS_CURRENT : ");
        m4.append(swappinessController.mCurrentValue);
        printWriter.println(m4.toString());
        printWriter.println(" ");
        printWriter.println("\n KPM Auto Restart Parameters");
        printWriter.println(" - Min Triggger Size    : " + String.valueOf(sMinTriggerSize));
        printWriter.println(" - Never Collect Within : " + String.valueOf(sNeverCollectWithin));
        printWriter.println(" - Compact Trigger Size : " + String.valueOf(sCompactTriggerSize));
        printWriter.println(" - Native Flood Ratio   : " + String.valueOf(sNativeFloodRatio));
        printWriter.println(" - Syspers Flood Ratio  : " + String.valueOf(sSyspersFloodRatio));
        printWriter.println("");
        ChimeraTriggerManager m189$$Nest$smgetInstance =
                ChimeraTriggerManager.m189$$Nest$smgetInstance(this.mContext);
        m189$$Nest$smgetInstance.getClass();
        StringBuilder m5 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                KillPolicyManager$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                                printWriter,
                                                "\n ChimeraTrigger Stats:",
                                                " - Last Policy Score      : "),
                                        m189$$Nest$smgetInstance.mLastPolicyScore,
                                        printWriter,
                                        " - Trigger Threshold Score: "),
                                ChimeraTriggerManager.PMM_CRITICAL_SCORE_THRESHOLD,
                                printWriter,
                                " - Trigger Required       : "),
                        m189$$Nest$smgetInstance.mChimeraTriggerRequired,
                        printWriter,
                        " - Last Triggered Time(ms): ");
        m5.append(m189$$Nest$smgetInstance.mLastTriggeredTime);
        printWriter.println(m5.toString());
        printWriter.println("");
        MemoryFloodDetector memoryFloodDetector = this.mMemoryFloodDetector;
        memoryFloodDetector.getClass();
        printWriter.println("MemoryFloodDetector");
        if (((ArrayList) memoryFloodDetector.mNativeMemDumpList).size() > 0) {
            MemoryFloodDetector.dumpItem(
                    printWriter, "native", memoryFloodDetector.mNativeMemDumpList);
        }
        if (((ArrayList) memoryFloodDetector.mSystemMemDumpList).size() > 0) {
            MemoryFloodDetector.dumpItem(
                    printWriter, "sys", memoryFloodDetector.mSystemMemDumpList);
            MemoryFloodDetector.dumpItem(
                    printWriter, "pers", memoryFloodDetector.mPersistentMemDumpList);
        }
        ProcessSamplingManager processSamplingManager =
                this.mProcMemDumpBigdata.mProcessSamplingManager;
        processSamplingManager.getClass();
        printWriter.println("ProcessSamplingManager");
        printWriter.println(" Constants");
        printWriter.print("  MAX_SKIP=");
        printWriter.println(6);
        printWriter.print("  MAX_SKIP_IN_USER_TRIAL=");
        printWriter.println(0);
        printWriter.print("  PERCENT_DEVICE_SAMPLING=");
        printWriter.println(30);
        printWriter.print("  SKIP_FIRST_AFTER_BOOT=");
        printWriter.println(false);
        printWriter.print("  MAX_SIZE_JSON_STRING=");
        printWriter.println(19500);
        printWriter.println(" States");
        printWriter.print("  mIsSampleDevice=");
        printWriter.println(processSamplingManager.mIsSampleDevice);
        printWriter.print("  mSkipCount=");
        printWriter.println(processSamplingManager.mSkipCount);
        printWriter.print("  mCurrentState=");
        printWriter.println(processSamplingManager.mCurrentState);
        printWriter.println();
        MemoryStabilityEventManager memoryStabilityEventManager = this.mMemoryStabilityEventManager;
        memoryStabilityEventManager.getClass();
        printWriter.println("MemoryStabilityEventManager");
        printWriter.print("  mLastRealtimeMinutes=");
        MemoryStabilityEventManager.Reporter reporter = memoryStabilityEventManager.mReporter;
        printWriter.println(reporter.mLastRealtimeMinutes);
        printWriter.print("  mLastUptimeMinutes=");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                reporter.mLastUptimeMinutes, printWriter, "  mLastCounter=", "[");
        while (true) {
            MemoryStabilityEventManager.Counter counter = reporter.mLastCounter;
            if (i >= counter.countOfEvents.length) {
                printWriter.println("]");
                return;
            }
            if (i > 0) {
                printWriter.print(", ");
            }
            printWriter.print(counter.countOfEvents[i]);
            i++;
        }
    }

    public final void forceChangeState(String str) {
        switch (str) {
            case "CRITICAL":
                this.mCurrentState = KpmState.CRITICAL;
                break;
            case "HEAVY":
                this.mCurrentState = KpmState.HEAVY;
                break;
            case "LIGHT":
                this.mCurrentState = KpmState.LIGHT;
                break;
            default:
                this.mCurrentState = KpmState.NORMAL;
                break;
        }
        changePolicy(this.mCurrentState);
        this.mPrevState = this.mCurrentState;
    }

    public final KpmRaw getCurrentKpmRawBigdata() {
        if (this.mIsFirstAppLaunch) {
            return this.mKpmRawBigdata[this.mBigdataIndex];
        }
        return null;
    }

    public final KpmRaw getCurrentKpmRawPolicy() {
        if (this.mIsFirstAppLaunch) {
            return this.mKpmRawPolicy[this.mPolicyIndex];
        }
        return null;
    }

    public final ProcMemInfo getProcMemInfo() {
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        ProcMemInfo procMemInfo = new ProcMemInfo();
        memInfoReader.getRawInfo();
        procMemInfo.memTotal = (int) ((memInfoReader.getTotalSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.memFree = (int) ((memInfoReader.getFreeSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.memAvailable = (int) ((memInfoReader.getAvailableSize() / 1048576.0d) + 0.5d);
        procMemInfo.cached = (int) ((memInfoReader.getCachedSizeLegacy() / 1048576.0d) + 0.5d);
        procMemInfo.activeFile = (int) ((memInfoReader.getActiveFileSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.inactiveFile = (int) ((memInfoReader.getInactiveFileSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.rbinFree = (int) ((memInfoReader.getRbinFreeSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.rbinCached = (int) ((memInfoReader.getRbinCachedSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.swapFree = (int) ((memInfoReader.getSwapFreeSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.kReclaimable = (int) ((memInfoReader.getKReclaimableSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.sReclaimable = (int) ((memInfoReader.getSReclaimableSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.sUnreclaim = (int) ((memInfoReader.getSUnreclaimSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.gpuSwap = (int) ((memInfoReader.getGpuSwapSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.systemCached = (int) ((memInfoReader.getSystemCachedSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.zram = (int) ((memInfoReader.getZramTotalSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.gpuTotal = (int) ((memInfoReader.getGpuTotalSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.vmallocUsed = (int) ((memInfoReader.getVmAllocUsedSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.systemUncached =
                (int) ((memInfoReader.getSystemUncachedSizeKb() / 1024.0d) + 0.5d);
        if (this.mTotalSwap <= 0) {
            this.mTotalSwap = (int) ((memInfoReader.getSwapTotalSizeKb() / 1024.0d) + 0.5d);
        }
        return procMemInfo;
    }

    public final void reportUserTrend(int i, KpmRaw kpmRaw) {
        String str;
        String str2;
        long j;
        long j2;
        ProcessMemoryHeavyInfo processMemoryHeavyInfo = this.mProcessHeavyMemory;
        LmkdCounter lmkdCounter = kpmRaw.lmkdCounter;
        if (kpmRaw.appCnt == 0) {
            Slog.d("ActivityManager_kpm", "it has 'zero' samples.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", i);
            jSONObject.put("CRST", kpmRaw.curKpmState.getName());
            jSONObject.put("NXST", kpmRaw.nextKpmState.getName());
            jSONObject.put("PLST", kpmRaw.policyState.getName());
            jSONObject.put("DRAM", this.mTotalMem * 1024);
            jSONObject.put("SWPT", this.mTotalSwap);
            jSONObject.put("FCMA", (int) ((kpmRaw.accMem / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("AVMA", (int) ((kpmRaw.avlMem / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("SWUA", (int) ((kpmRaw.accSwap / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("DISP", this.mDisplaySizeStr);
            jSONObject.put("PCKC", (int) ((kpmRaw.pickedTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("PCKA", (int) ((kpmRaw.pickedActTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("SDBC", 0);
            jSONObject.put("CCHC", (int) ((kpmRaw.cachedTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("CCHA", (int) ((kpmRaw.cachedActTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put(
                    "LNCT",
                    new JSONArray()
                            .put(kpmRaw.hotCount)
                            .put(kpmRaw.warmCount)
                            .put(kpmRaw.coldCount));
            jSONObject.put("LPKC", kpmRaw.launchedPackageCount);
            jSONObject.put("PKPC", lmkdCounter.previousKillOccurredCount);
            jSONObject.put("SKSC", lmkdCounter.bServiceKillOccurredCount);
            jSONObject.put("SKPC", kpmRaw.killedPackageCount);
            jSONObject.put("VFAK", lmkdCounter.cycleVisToFgKillCount);
            jSONObject.put("HPAK", lmkdCounter.cycleHomeToPercKillCount);
            jSONObject.put("PRAK", lmkdCounter.cyclePreviousKillCount);
            jSONObject.put("SRVK", lmkdCounter.cycleBServiceKillCount);
            jSONObject.put("PCKK", lmkdCounter.cyclePickedKillCount);
            jSONObject.put("SBAK", 0);
            jSONObject.put("CHMK", lmkdCounter.cycleCachedMinKillCount);
            jSONObject.put("CHEK", kpmRaw.cachedEmptyKillCount);
            jSONObject.put("CHNK", kpmRaw.cachedNormalKillCount);
            jSONObject.put("CACK", kpmRaw.cachedClientKillCount);
            jSONObject.put("APPC", kpmRaw.appCnt);
            jSONObject.put("MEDK", kpmRaw.lmkdMed);
            jSONObject.put("CRIK", kpmRaw.lmkdCric);
            jSONObject.put("LMKK", kpmRaw.lmkCnt);
            if (KPM_POLICY_ENABLE) {
                jSONObject.put("SWPN", this.mSwappinessController.mCurrentValue);
            }
            JSONArray jSONArray = new JSONArray();
            double d = 1024.0d;
            if (kpmRaw.procsAdjPss != null) {
                String[] strArr = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL;
                int length = strArr.length;
                int i2 = 0;
                while (i2 < length) {
                    String str3 = strArr[i2];
                    if (kpmRaw.procsAdjPss.containsKey(str3)) {
                        j = (long) (((r10[0] + r10[1]) / d) + 0.5d);
                        j2 = ((long[]) kpmRaw.procsAdjPss.get(str3))[3];
                    } else {
                        j = 0;
                        j2 = 0;
                    }
                    jSONArray.put(j).put(j2);
                    i2++;
                    d = 1024.0d;
                }
                for (String str4 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    jSONArray.put(
                            kpmRaw.procsAdjPss.containsKey(str4)
                                    ? (long)
                                            ((((long[]) kpmRaw.procsAdjPss.get(r11))[1] / 1024.0d)
                                                    + 0.5d)
                                    : 0L);
                }
                for (String str5 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    jSONArray.put(
                            kpmRaw.procsAdjPss.containsKey(str5)
                                    ? (long)
                                            ((((long[]) kpmRaw.procsAdjPss.get(r11))[2] / 1024.0d)
                                                    + 0.5d)
                                    : 0L);
                }
            }
            jSONObject.put("PRST", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2
                    .put((int) ((kpmRaw.psiCpuSum / kpmRaw.appCnt) + 0.5d))
                    .put((int) ((kpmRaw.psiMemorySum / kpmRaw.appCnt) + 0.5d))
                    .put((int) ((kpmRaw.psiIoSum / kpmRaw.appCnt) + 0.5d))
                    .put((int) (kpmRaw.psiCpuMax + 0.5d))
                    .put((int) (kpmRaw.psiMemoryMax + 0.5d))
                    .put((int) (kpmRaw.psiIoMax + 0.5d));
            jSONObject.put("PSIS", jSONArray2);
            fillChimeraDataIfExist(kpmRaw, jSONObject);
            jSONObject.put("STAY", this.mNumberOfStay);
            jSONObject.put("ELAP", ((kpmRaw.lastUpdateTime - kpmRaw.createTime) / 1000) / 60);
            jSONObject.put("UPTM", (SystemClock.uptimeMillis() / 1000) / 60);
            jSONObject.put("RSTF", kpmRaw.resetStatus);
            jSONObject.put("KVER", "4.6");
            jSONObject.put("LS", kpmRaw.lmkdState.getLevel());
            JSONArray jSONArray3 = new JSONArray();
            for (LmkdState lmkdState : LmkdState.values()) {
                jSONArray3.put(kpmRaw.lmkdStateCount[lmkdState.ordinal()]);
            }
            jSONObject.put("LSC", jSONArray3);
            JSONArray jSONArray4 = new JSONArray();
            for (LmkdCount lmkdCount : LmkdCount.values()) {
                jSONArray4.put(kpmRaw.lmkdCurrentCount[lmkdCount.ordinal()]);
            }
            jSONObject.put("LC", jSONArray4);
            JSONArray jSONArray5 = new JSONArray();
            for (int i3 = 0; i3 < kpmRaw.lmkdKilledProcessInfo.size(); i3++) {
                jSONArray5.put(kpmRaw.lmkdKilledProcessInfo.get(i3));
                if (i3 > 5) {
                    break;
                }
            }
            jSONObject.put("LDI", jSONArray5);
            JSONArray jSONArray6 = new JSONArray();
            ArrayList arrayList = kpmRaw.dumpHeavyProcessList;
            if (arrayList != null && arrayList.size() > 0) {
                jSONArray6.put(kpmRaw.peakLmkdKillAdj);
                for (int i4 = 0; i4 < kpmRaw.dumpHeavyProcessList.size(); i4++) {
                    DumpHeavyProcessInfo dumpHeavyProcessInfo =
                            (DumpHeavyProcessInfo) kpmRaw.dumpHeavyProcessList.get(i4);
                    jSONArray6.put(dumpHeavyProcessInfo.memoryInfo.procName);
                    String str6 = dumpHeavyProcessInfo.packageVersion;
                    if (str6 != null) {
                        jSONArray6.put(str6);
                    } else {
                        jSONArray6.put(String.valueOf(dumpHeavyProcessInfo.processVersion));
                    }
                    jSONArray6
                            .put(dumpHeavyProcessInfo.memoryInfo.label)
                            .put((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d))
                            .put(
                                    (int)
                                            ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d)
                                                    + 0.5d))
                            .put((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                }
            }
            jSONObject.put("LHI", jSONArray6);
            JSONArray jSONArray7 = new JSONArray();
            ProcMemInfo procMemInfo = kpmRaw.procMemInfo;
            if (procMemInfo != null) {
                jSONArray7
                        .put(procMemInfo.memTotal)
                        .put(kpmRaw.procMemInfo.memFree)
                        .put(kpmRaw.procMemInfo.memAvailable)
                        .put(kpmRaw.procMemInfo.cached)
                        .put(kpmRaw.procMemInfo.activeFile)
                        .put(kpmRaw.procMemInfo.inactiveFile)
                        .put(kpmRaw.procMemInfo.rbinFree)
                        .put(kpmRaw.procMemInfo.rbinCached)
                        .put(this.mTotalSwap)
                        .put(kpmRaw.procMemInfo.swapFree)
                        .put(kpmRaw.procMemInfo.kReclaimable)
                        .put(kpmRaw.procMemInfo.sReclaimable)
                        .put(kpmRaw.procMemInfo.sUnreclaim)
                        .put(0)
                        .put(kpmRaw.procMemInfo.gpuSwap)
                        .put(kpmRaw.procMemInfo.systemCached)
                        .put(kpmRaw.procMemInfo.zram)
                        .put(kpmRaw.procMemInfo.gpuTotal)
                        .put(kpmRaw.procMemInfo.vmallocUsed)
                        .put(kpmRaw.procMemInfo.systemUncached);
            }
            jSONObject.put("PMI", jSONArray7);
            JSONArray jSONArray8 = new JSONArray();
            if (kpmRaw.dumpHeavyMemoryUsageByAdj != null) {
                for (String str7 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    if (kpmRaw.dumpHeavyMemoryUsageByAdj.containsKey(str7)) {
                        long[] jArr = (long[]) kpmRaw.dumpHeavyMemoryUsageByAdj.get(str7);
                        Objects.requireNonNull(processMemoryHeavyInfo);
                        jSONArray8
                                .put((int) ((jArr[0] / 1024.0d) + 0.5d))
                                .put((int) ((jArr[1] / 1024.0d) + 0.5d))
                                .put((int) ((jArr[2] / 1024.0d) + 0.5d));
                    } else {
                        jSONArray8.put(-1).put(-1).put(-1);
                    }
                }
            }
            jSONObject.put("LHMA", jSONArray8);
            JSONArray jSONArray9 = new JSONArray();
            KpmState[] kpmStateArr = kpmRaw.tinyKpmState;
            if (kpmStateArr != null) {
                for (KpmState kpmState : kpmStateArr) {
                    jSONArray9.put(kpmState.ordinal());
                }
            }
            jSONObject.put("TNXST", jSONArray9);
            str = jSONObject.toString();
            try {
                str2 = str.substring(1, str.length() - 1);
            } catch (JSONException unused) {
                Slog.d("ActivityManager_kpm", "failed to create the KPUT");
                str2 = str;
                sendHqmBigData(str2);
            }
        } catch (JSONException unused2) {
            str = null;
        }
        sendHqmBigData(str2);
    }

    public final synchronized void sendHqmBigData(String str) {
        try {
            if (this.mHqmManager == null) {
                this.mHqmManager =
                        (SemHqmManager) this.mContext.getSystemService("HqmManagerService");
            }
            SemHqmManager semHqmManager = this.mHqmManager;
            if (semHqmManager == null) {
                if (KPM_DEBUG) {
                    Slog.d("ActivityManager_kpm", "HQM services is not working");
                }
                return;
            }
            if (str != null) {
                boolean sendHWParamToHQM =
                        semHqmManager.sendHWParamToHQM(
                                KPM_DEBUG ? 1 : 0,
                                "Sluggish",
                                "KPUT",
                                "ph",
                                "1.1",
                                "sec",
                                "",
                                str,
                                "");
                if (KPM_DEBUG) {
                    if (sendHWParamToHQM) {
                        Slog.d("ActivityManager_kpm", "Success to report 'KPUT' : ".concat(str));
                    } else {
                        Slog.d("ActivityManager_kpm", "failed to send to server");
                    }
                }
            } else if (KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "failed to send to server.");
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
