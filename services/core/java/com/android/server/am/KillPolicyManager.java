package com.android.server.am;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.ChimeraDataInfo;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.chimera.heimdall.Heimdall;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.KillPolicyManager;
import com.android.server.am.pmm.PersonalizedMemoryManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class KillPolicyManager {
    public ActivityManagerService mAm;
    public long mAppLaunchCount;
    public int mBigdataIndex;
    public int mBigdataMetric;
    public Context mContext;
    public KpmState mCurrentState;
    public SemHqmManager mHqmManager;
    public boolean mIsFirstAppLaunch;
    public long mKpmStartTime;
    public long mNumberOfStay;
    public int mPolicyIndex;
    public int mPolicyMetric;
    public KpmState mPolicyState;
    public KpmState mPrevState;
    public int mTotalMem;
    public int mTotalSwap;
    public int mTrigger;
    public static final int[] SWAPPINESS = {100, 100, 130, 130, 145};
    public static boolean KPM_POLICY_ENABLE = Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_policy_enable", "true"));
    public static boolean KPM_DEBUG = false;
    public static int sWarmUpTrigger = Integer.parseInt(SystemProperties.get("ro.slmk.kpm_warm_up_trigger", "20"));
    public static int sPolicyTrigger = Integer.parseInt(SystemProperties.get("ro.slmk.kpm_policy_trigger", Integer.toString(50)));
    public static int sWarmUpCycles = Integer.parseInt(SystemProperties.get("ro.slmk.kpm_warm_up_cycles", "3"));
    public static boolean KPM_BTIME_ENABLE = Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_boot_enable", "true"));
    public static ArrayList resumeSkipPackage = new ArrayList();
    public static boolean DEBUG_SCPM = true;
    public static boolean MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO = Boolean.parseBoolean(SystemProperties.get("ro.slmk.kpm_use_cri_pkg_ratio", "true"));
    public static int MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH = 100;
    public static int MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 100;
    public static boolean KPM_MEM_CRITICAL_LOW_DETECT_ENABLE = true;
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
    public final Uri SCPM_URI_KPM = Uri.parse("content://com.samsung.android.sm.policy/policy_item/kpm");
    public final Uri SCPM_URI_POLICY = Uri.parse("content://com.samsung.android.sm.policy/policy_item/policy_list");
    public final LmkdCountReader mLmkdReader = new LmkdCountReader();
    public final int PREV_PROCESS_LIST_MAX = 5;
    public final int INDEX_CURRENT_PROCESS = 1;
    public final int INDEX_PREV_PROCESS = 2;
    public final int INDEX_2ND_PREV_PROCESS = 3;
    public final int KPM_RAW_POLICY_MAX_LEN = 24;
    public final int KPM_RAW_BIGDATA_MAX_LEN = 30;
    public final KpmRaw[] mKpmRawPolicy = new KpmRaw[24];
    public final KpmRaw[] mKpmRawBigdata = new KpmRaw[30];
    public ProcessMemoryUsageInfo mProcMemDumpPolicy = new ProcessMemoryUsageInfo();
    public ProcessMemoryUsageInfo mProcMemDumpBigdata = new ProcessMemoryUsageInfo();
    public ProcessMemoryHeavyInfo mProcessHeavyMemory = new ProcessMemoryHeavyInfo();
    public String mDisplaySizeStr = "";
    public String mPrevPackage = null;
    public ArrayList mPrevProcessList = new ArrayList();
    public BroadcastReceiver mDailyRandomSampleReceiver = null;
    public boolean mHasPsiCpuPermission = true;
    public boolean mHasPsiMemoryPermission = true;
    public boolean mHasPsiIoPermission = true;
    public ChimeraDataInfo mRecentChimeraData = new ChimeraDataInfo();
    public final SwappinessController mSwappinessController = new SwappinessController();
    public MemoryFloodDetector mMemoryFloodDetector = new MemoryFloodDetector();
    public long mPlatformStartUpTimeMillis = SystemClock.uptimeMillis();
    public long mLastIdleEnterRealTimeMillis = System.currentTimeMillis();
    public long mLastIdleExitRealTimeMillis = System.currentTimeMillis();
    public long mLastIdleExitUpTimeMillis = SystemClock.uptimeMillis();
    public long mLastIdleRealTimeMillis = 0;
    public long mLastAwakeRealTimeMillis = 0;
    public long mLastAwakeUpTimeMillis = 0;
    public HashMap mVmStats = new HashMap();
    public final BroadcastReceiver mIdleModeReceiver = new BroadcastReceiver() { // from class: com.android.server.am.KillPolicyManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                boolean isDeviceIdleMode = ((PowerManager) KillPolicyManager.this.mContext.getSystemService("power")).isDeviceIdleMode();
                ChimeraTriggerManager.getInstance(KillPolicyManager.this.mContext).onDeviceIdleChanged(isDeviceIdleMode);
                if (isDeviceIdleMode) {
                    KillPolicyManager.this.mLastAwakeRealTimeMillis = System.currentTimeMillis() - KillPolicyManager.this.mLastIdleExitRealTimeMillis;
                    KillPolicyManager.this.mLastAwakeUpTimeMillis = SystemClock.uptimeMillis() - KillPolicyManager.this.mLastIdleExitUpTimeMillis;
                    KillPolicyManager.this.mLastIdleEnterRealTimeMillis = System.currentTimeMillis();
                    if (KillPolicyManager.KPM_DEBUG) {
                        Slog.i("ActivityManager_kpm", "Awake realtime : " + KillPolicyManager.this.mLastAwakeRealTimeMillis + ", Awake uptime : " + KillPolicyManager.this.mLastAwakeUpTimeMillis);
                    }
                } else {
                    KillPolicyManager.this.mLastIdleRealTimeMillis = System.currentTimeMillis() - KillPolicyManager.this.mLastIdleEnterRealTimeMillis;
                    KillPolicyManager.this.mLastIdleExitRealTimeMillis = System.currentTimeMillis();
                    KillPolicyManager.this.mLastIdleExitUpTimeMillis = SystemClock.uptimeMillis();
                    if (KillPolicyManager.KPM_DEBUG) {
                        Slog.i("ActivityManager_kpm", "Idle realtime : " + KillPolicyManager.this.mLastIdleRealTimeMillis);
                    }
                }
                KillPolicyManager.this.mMemoryFloodDetector.onDeviceIdleChanged(isDeviceIdleMode);
            }
        }
    };

    public abstract class KpmClassLazy {
        public static final KillPolicyManager INSTANCE = new KillPolicyManager();
        public static boolean isinitKpmClass = false;
    }

    enum MemoryLoggingType {
        IdleDump,
        PmmDump
    }

    public final int calcKilledPackageRatio(int i, int i2) {
        if (i2 > 0) {
            return (int) (((i * 100.0d) / i2) + 0.5d);
        }
        return 0;
    }

    public class AutoRestartParameterReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("MIN_TRIGGER_SIZE", 10);
            int intExtra2 = intent.getIntExtra("NEVER_COLLECT_WITHIN", 12);
            int intExtra3 = intent.getIntExtra("COMPACT_TRIGGER_SIZE", 300);
            int intExtra4 = intent.getIntExtra("NATIVE_FLOOD_RATIO", 30);
            int intExtra5 = intent.getIntExtra("SYSPERS_FLOOD_RATIO", 30);
            if (KillPolicyManager.RANGE_AUTO_RESTART_MIN_TRIGGER_SIZE.isValidIntValue(intExtra)) {
                KillPolicyManager.sMinTriggerSize = intExtra;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_NEVER_COLLECT_WITHIN.isValidIntValue(intExtra2)) {
                KillPolicyManager.sNeverCollectWithin = intExtra2;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_COMPACT_TRIGGER_SIZE.isValidIntValue(intExtra3)) {
                KillPolicyManager.sCompactTriggerSize = intExtra3;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_NATIVE_FLOOD_RATIO.isValidIntValue(intExtra4)) {
                KillPolicyManager.sNativeFloodRatio = intExtra4;
            }
            if (KillPolicyManager.RANGE_AUTO_RESTART_SYSPERS_FLOOD_RATIO.isValidIntValue(intExtra5)) {
                KillPolicyManager.sSyspersFloodRatio = intExtra5;
            }
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "Auto Restart Parameter Updated. {sMinTriggerSize=" + KillPolicyManager.sMinTriggerSize + ", sNeverCollectWithin=" + KillPolicyManager.sNeverCollectWithin + ", sCompactTriggerSize=" + KillPolicyManager.sCompactTriggerSize + ", sNativeFloodRatio=" + KillPolicyManager.sNativeFloodRatio + ", sSyspersFloodRatio=" + KillPolicyManager.sSyspersFloodRatio + "}");
            }
        }
    }

    public KillPolicyManager() {
        Slog.i("ActivityManager_kpm", "KillPolicyManager()");
    }

    public static boolean isKPMEnabled() {
        return ActivityManagerService.isPmmEnabled();
    }

    public static boolean isDetectCritcialLowEnabled() {
        boolean parseBoolean = Boolean.parseBoolean(SystemProperties.get("persist.sys.kpm_cri_mem_detect", String.valueOf(true)));
        KPM_MEM_CRITICAL_LOW_DETECT_ENABLE = parseBoolean;
        return parseBoolean;
    }

    public static KillPolicyManager getInstance() {
        return KpmClassLazy.INSTANCE;
    }

    public static int getCriticalLowProcessKillRatioTH() {
        return MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH;
    }

    public static int getCriticalLowPackageKillRatioTH() {
        return MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH;
    }

    public void updateSCPMParametersFromDB() {
        if (DEBUG_SCPM) {
            Slog.d("ActivityManager_kpm", "updateSCPMParametersFromDB");
        }
        if (isSCPMAvailable()) {
            if (isNeedUpdateSCPMPolicy() && hasValidItemFromDB()) {
                getSCPMPolicyItemFromDB();
            } else {
                Slog.d("ActivityManager_kpm", "scpm doesn't find the Policy name for kpm");
            }
        }
    }

    public final boolean isSCPMAvailable() {
        if (DEBUG_SCPM) {
            Slog.d("ActivityManager_kpm", "isSCPMAvailable");
        }
        return this.mContext.getPackageManager().resolveContentProvider("com.samsung.android.sm.policy", 0) != null;
    }

    public final boolean isNeedUpdateSCPMPolicy() {
        Cursor cursor;
        if (DEBUG_SCPM) {
            Slog.d("ActivityManager_kpm", "isNeedUpdateSCPMPolicy");
        }
        try {
            cursor = this.mContext.getContentResolver().query(this.SCPM_URI_POLICY, null, null, null, null);
        } catch (Exception unused) {
            Slog.d("ActivityManager_kpm", "policy list is null");
            cursor = null;
        }
        boolean z = false;
        if (cursor != null) {
            while (true) {
                if (!cursor.moveToNext()) {
                    break;
                }
                String string = cursor.getString(cursor.getColumnIndex("policyName"));
                String string2 = cursor.getString(cursor.getColumnIndex("policyVersion"));
                if ("kpm".equals(string)) {
                    Slog.d("ActivityManager_kpm", "isNeedUpdateSCPMPolicy: policyName=" + string + " , policyVersion=" + string2);
                    z = true;
                    break;
                }
            }
            cursor.close();
        }
        return z;
    }

    public final boolean hasValidItemFromDB() {
        Cursor cursor;
        String[] strArr = {"item", "category", "data1"};
        Slog.d("ActivityManager_kpm", "hasValidItemFromDB!!");
        try {
            cursor = this.mContext.getContentResolver().query(this.SCPM_URI_KPM, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e("ActivityManager_kpm", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        boolean z = false;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    cursor.getString(0);
                    String string = cursor.getString(1);
                    cursor.getString(2);
                    if ("switch".equals(string) || "kpmParams".equals(string)) {
                        z = true;
                        break;
                    }
                }
            }
            cursor.close();
        } else {
            Slog.e("ActivityManager_kpm", "hasValidItemFromDB error, no database!!");
        }
        return z;
    }

    public final void getSCPMPolicyItemFromDB() {
        Cursor cursor;
        String[] strArr = {"item", "category", "data1"};
        Slog.d("ActivityManager_kpm", "getSCPMPolicyItemFromDB!!");
        try {
            cursor = this.mContext.getContentResolver().query(this.SCPM_URI_KPM, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e("ActivityManager_kpm", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    String string3 = cursor.getString(2);
                    if (string != null && string2 != null && string3 != null && "kpmEnable".equals(string) && "switch".equals(string2)) {
                        if (string3.equalsIgnoreCase("FALSE")) {
                            ActivityManagerService.sPmmEnabledBySpcm = false;
                            SystemProperties.set("persist.sys.kpm_onoff", "false");
                        } else if (string3.equalsIgnoreCase("TRUE")) {
                            ActivityManagerService.sPmmEnabledBySpcm = true;
                            SystemProperties.set("persist.sys.kpm_onoff", "true");
                        }
                        if (DEBUG_SCPM) {
                            Slog.d("ActivityManager_kpm", "getSCPMPolicyItemFromDB: CATEGORY = " + string2 + ", SCPM PKG: data1 = " + string3 + ", KPM ENABLE = " + ActivityManagerService.sPmmEnabledBySpcm);
                        }
                    }
                }
            }
            cursor.close();
            return;
        }
        Slog.e("ActivityManager_kpm", "getSCPMPolicyItemFromDB error, no database!!");
    }

    public void initKillPolicyManager(ActivityManagerService activityManagerService, Context context, ActivityManagerConstants activityManagerConstants) {
        Slog.i("ActivityManager_kpm", "initKillPolicyManager()");
        if (KpmClassLazy.isinitKpmClass) {
            return;
        }
        this.mAm = activityManagerService;
        this.mContext = context;
        new MemInfoReader().readLightMemInfo();
        this.mTotalSwap = (int) ((r3.getSwapTotalSizeKb() / 1024.0d) + 0.5d);
        this.mTotalMem = ProcFileInfoGetter.getRAMsizeGB();
        initCriticalLowKillRatioTH();
        this.mIsFirstAppLaunch = false;
        this.mAppLaunchCount = 0L;
        this.mPolicyMetric = 0;
        this.mPolicyIndex = 0;
        this.mBigdataIndex = 0;
        this.mBigdataMetric = 0;
        this.mTrigger = sWarmUpTrigger;
        KpmState kpmState = KpmState.NORMAL;
        this.mCurrentState = kpmState;
        this.mPrevState = kpmState;
        this.mPolicyState = kpmState;
        this.mKpmStartTime = System.currentTimeMillis();
        this.mNumberOfStay = 0L;
        initSkipPackage();
        KpmClassLazy.isinitKpmClass = true;
        PersonalizedMemoryManager.getInstance().init(this.mAm, this.mContext);
    }

    public final void scheduleDailyUserTrendRandomSample() {
        long j;
        if (this.mDailyRandomSampleReceiver != null) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        if (alarmManager == null) {
            Slog.d("ActivityManager_kpm", "schedule regist failed 'Alarm get failed'");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(13, 0);
        calendar.set(14, 0);
        if (KPM_DEBUG) {
            calendar.add(12, 5);
            j = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        } else {
            calendar.add(5, 1);
            j = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        }
        final long timeInMillis = calendar.getTimeInMillis();
        Slog.d("ActivityManager_kpm", "Schedule the sample, next trigger time : " + timeInMillis);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.KPM_USER_TREND_DAILY_SAMPLING"), 67108864);
        this.mDailyRandomSampleReceiver = new BroadcastReceiver() { // from class: com.android.server.am.KillPolicyManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (KillPolicyManager.KPM_DEBUG) {
                    Slog.d("ActivityManager_kpm", "report daily random sample time : " + timeInMillis);
                }
                KillPolicyManager.this.updateKpmBigdata();
                KillPolicyManager.this.reportMemInfo();
            }
        };
        this.mContext.registerReceiver(this.mDailyRandomSampleReceiver, new IntentFilter("com.samsung.KPM_USER_TREND_DAILY_SAMPLING"));
        this.mContext.registerReceiver(this.mIdleModeReceiver, new IntentFilter("android.os.action.DEVICE_IDLE_MODE_CHANGED"));
        alarmManager.setRepeating(1, calendar.getTimeInMillis(), j, broadcast);
    }

    public final void initSkipPackage() {
        resumeSkipPackage.add(KnoxCustomManagerService.LAUNCHER_PACKAGE);
        resumeSkipPackage.add("com.google.android.permissioncontroller");
        resumeSkipPackage.add("com.google.android.googlequicksearchbox");
        resumeSkipPackage.add("com.samsung.android.MtpApplication");
    }

    public boolean isSkipPackage(String str) {
        return str.contains(this.mAm.currentLauncherName) || str.equals("android") || resumeSkipPackage.contains(str);
    }

    public final void setCriticalKillThreshold(int i) {
        int parseInt = Integer.parseInt(SystemProperties.get("ro.slmk.kpm_crikill_rate_th", "-1"));
        if (parseInt > 0 && parseInt < 100) {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH = parseInt;
        } else {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH = 50 / i;
        }
    }

    public final void setPackageKillThreshold(int i) {
        int parseInt = Integer.parseInt(SystemProperties.get("ro.slmk.kpm_pkgkill_rate_th", "-1"));
        if (parseInt > 0 && parseInt < 100) {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = parseInt;
            return;
        }
        if (i <= 4) {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 50;
        } else if (i <= 8) {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 25;
        } else {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 13;
        }
    }

    public final void initCriticalLowKillRatioTH() {
        int i = this.mTotalMem;
        if (i > 0) {
            setCriticalKillThreshold(i);
            setPackageKillThreshold(this.mTotalMem);
        } else {
            MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH = 100;
            MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH = 100;
        }
    }

    public final boolean isCriticalKilledManyPakages(int i) {
        return i >= MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH;
    }

    public final void reportMemCriticalLow(int i) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.KPM_CRITICAL_MEMORY_STATUS");
        intent.putExtra("res", i);
        intent.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
        this.mContext.sendBroadcast(intent);
        Slog.i("ActivityManager_kpm", "memory Critical Low intent RESULT : " + i);
    }

    public final void reportResetState(String str) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.KPM_CRITICAL_MEMORY_STATUS");
        intent.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
        intent.putExtra("resetType", str);
        intent.putExtra("nativeStart", ((Integer) this.mMemoryFloodDetector.mLastCalculatedNative.first).intValue());
        intent.putExtra("nativeEnd", ((Integer) this.mMemoryFloodDetector.mLastCalculatedNative.second).intValue());
        intent.putExtra("sysPersStart", ((Integer) this.mMemoryFloodDetector.mLastCalculatedSysPers.first).intValue());
        intent.putExtra("sysPersEnd", ((Integer) this.mMemoryFloodDetector.mLastCalculatedSysPers.second).intValue());
        intent.putExtra("pmmCyclePlatform", this.mPolicyMetric);
        intent.putExtra("pmmCycleKernel", this.mPolicyMetric + this.mMemoryFloodDetector.mPmmCycleCountOnPlatformReset);
        intent.putExtra("uptimeSystemBoot", SystemClock.uptimeMillis() - this.mPlatformStartUpTimeMillis);
        this.mContext.sendBroadcast(intent);
        Slog.i("ActivityManager_kpm", "reportResetState : " + str);
    }

    public void updateKpmBigdata() {
        ChimeraDataInfo chimeraStat;
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        if (currentKpmRawBigdata == null) {
            return;
        }
        this.mLmkdReader.readLmkdKillCount();
        currentKpmRawBigdata.lmkdCounter.getCycleLmkdKillCountByADJ(this.mLmkdReader);
        currentKpmRawBigdata.updateBigdataInfo();
        currentKpmRawBigdata.checkMemCriticalLowTH();
        currentKpmRawBigdata.dumpLmkdCount();
        currentKpmRawBigdata.isDailyBigdata = true;
        this.mProcMemDumpBigdata.getProcDumpMemInfoThread(currentKpmRawBigdata);
        ChimeraManagerService chimeraManagerService = (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
        if (chimeraManagerService != null && (chimeraStat = chimeraManagerService.getChimeraStat()) != null) {
            currentKpmRawBigdata.chimeraDataInfo = ChimeraDataInfo.getDiff(chimeraStat, this.mRecentChimeraData);
            this.mRecentChimeraData = chimeraStat;
        }
        currentKpmRawBigdata.launchedAndKilledPackageMap.clear();
        this.mProcMemDumpBigdata.clearAdjPss();
        newKpmRawBigdata();
    }

    public void updateKpmCycleData() {
        ChimeraDataInfo chimeraStat;
        int i;
        boolean z = this.mAppLaunchCount <= ((long) (sWarmUpTrigger * sWarmUpCycles));
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        this.mTrigger = z ? sWarmUpTrigger : sPolicyTrigger;
        if (currentKpmRawPolicy != null && (i = currentKpmRawPolicy.appCnt) != 0 && i % 5 == 0) {
            currentKpmRawPolicy.lmkdCounter.fillTinyCycleLmkdKillCountByADJ(this.mLmkdReader);
            currentKpmRawPolicy.tinyKpmState[((currentKpmRawPolicy.appCnt / 5) - 1) % 10] = changeTinyKpmState(currentKpmRawPolicy);
            if (KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "Tiny KPM New State");
            }
        }
        if (currentKpmRawPolicy == null || currentKpmRawPolicy.appCnt != this.mTrigger) {
            return;
        }
        if (z) {
            currentKpmRawPolicy.isWarmUpCycle = true;
        }
        currentKpmRawPolicy.lmkdCounter.getCycleLmkdKillCountByADJ(this.mLmkdReader);
        currentKpmRawPolicy.updateCycleInfo();
        currentKpmRawPolicy.checkMemCriticalLowTH();
        KpmState kpmState = currentKpmRawPolicy.nextKpmState;
        this.mCurrentState = kpmState;
        if (kpmState == this.mPrevState) {
            this.mNumberOfStay++;
        }
        currentKpmRawPolicy.dumpLmkdCount();
        KpmState kpmState2 = this.mCurrentState;
        if (kpmState2 != this.mPrevState && kpmState2.ordinal() < KpmState.values().length) {
            if (KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", " KPM State Change. New State: " + this.mCurrentState + " Previous State: " + this.mPrevState);
            }
            currentKpmRawPolicy.isStateChanged = true;
            this.mPrevState = this.mCurrentState;
        }
        if (KPM_POLICY_ENABLE) {
            applyPolicy();
        }
        if (currentKpmRawPolicy.isStateChanged) {
            this.mNumberOfStay = 0L;
        }
        if (!currentKpmRawPolicy.isWarmUpCycle) {
            this.mProcMemDumpPolicy.getProcDumpMemInfoThread(currentKpmRawPolicy);
        }
        ChimeraManagerService chimeraManagerService = (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
        if (chimeraManagerService != null && (chimeraStat = chimeraManagerService.getChimeraStat()) != null) {
            currentKpmRawPolicy.chimeraDataInfo = ChimeraDataInfo.getDiff(chimeraStat, this.mRecentChimeraData);
            this.mRecentChimeraData = chimeraStat;
        }
        currentKpmRawPolicy.launchedAndKilledPackageMap.clear();
        this.mProcMemDumpPolicy.clearAdjPss();
        newKpmRawPolicy();
    }

    public void updateKpmProcessData(String str, int i) {
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        if (currentKpmRawPolicy != null) {
            currentKpmRawPolicy.updateProcessStateInfo(str, i);
        }
        if (currentKpmRawBigdata != null) {
            currentKpmRawBigdata.updateProcessStateInfo(str, i);
        }
    }

    public ProcMemInfo getProcMemInfoLight() {
        new MemInfoReader().readLightMemInfo();
        ProcMemInfo procMemInfo = new ProcMemInfo();
        procMemInfo.memTotal = (int) ((r0.getTotalSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.memFree = (int) ((r0.getFreeSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.cached = (int) ((r0.getCachedSizeLegacy() / 1048576.0d) + 0.5d);
        procMemInfo.swapFree = (int) ((r0.getSwapFreeSizeKb() / 1024.0d) + 0.5d);
        procMemInfo.memAvailable = (int) ((r0.getAvailableSize() / 1048576.0d) + 0.5d);
        if (this.mTotalSwap <= 0) {
            this.mTotalSwap = (int) ((r0.getSwapTotalSizeKb() / 1024.0d) + 0.5d);
        }
        return procMemInfo;
    }

    public static HashMap getMemInfo() {
        return getMemInfoFromFile("/proc/meminfo");
    }

    public static HashMap getVmStat() {
        return getMemInfoFromFile("/proc/vmstat");
    }

    public static HashMap getMemInfoFromFile(String str) {
        HashMap hashMap = new HashMap();
        try {
            FileReader fileReader = new FileReader(str);
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader, IInstalld.FLAG_FORCE);
                try {
                    for (String readLine = bufferedReader.readLine(); !TextUtils.isEmpty(readLine); readLine = bufferedReader.readLine()) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return hashMap;
    }

    public ProcMemInfo getProcMemInfo() {
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
        procMemInfo.systemUncached = (int) ((memInfoReader.getSystemUncachedSizeKb() / 1024.0d) + 0.5d);
        if (this.mTotalSwap <= 0) {
            this.mTotalSwap = (int) ((memInfoReader.getSwapTotalSizeKb() / 1024.0d) + 0.5d);
        }
        return procMemInfo;
    }

    public void updateKpmMemData() {
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        ProcMemInfo procMemInfoLight = getProcMemInfoLight();
        if (procMemInfoLight != null) {
            if (currentKpmRawPolicy != null) {
                currentKpmRawPolicy.updateMemInfo(procMemInfoLight);
            }
            if (currentKpmRawBigdata != null) {
                currentKpmRawBigdata.updateMemInfo(procMemInfoLight);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateKpmPsiData() {
        double d;
        double d2;
        double d3;
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        if (this.mHasPsiCpuPermission) {
            PsiFile psiFile = getPsiFile(PsiFileType.CPU, PsiDataType.AVG10);
            if (psiFile.isEmpty()) {
                this.mHasPsiCpuPermission = false;
                Slog.d("ActivityManager", "No permission - psi cpu");
            } else {
                d = psiFile.getSomeAvg10();
                if (this.mHasPsiMemoryPermission) {
                    PsiFile psiFile2 = getPsiFile(PsiFileType.MEMORY, PsiDataType.AVG10);
                    if (psiFile2.isEmpty()) {
                        this.mHasPsiMemoryPermission = false;
                        Slog.d("ActivityManager", "No permission - psi memory");
                    } else {
                        d2 = psiFile2.getSomeAvg10();
                        if (this.mHasPsiIoPermission) {
                            PsiFile psiFile3 = getPsiFile(PsiFileType.IO, PsiDataType.AVG10);
                            if (psiFile3.isEmpty()) {
                                this.mHasPsiIoPermission = false;
                                Slog.d("ActivityManager", "No permission - psi io");
                            } else {
                                d3 = psiFile3.getSomeAvg10();
                                if (currentKpmRawPolicy != null) {
                                    currentKpmRawPolicy.updatePsiInfo(d, d2, d3);
                                }
                                if (currentKpmRawBigdata == null) {
                                    currentKpmRawBigdata.updatePsiInfo(d, d2, d3);
                                    return;
                                }
                                return;
                            }
                        }
                        d3 = 0.0d;
                        if (currentKpmRawPolicy != null) {
                        }
                        if (currentKpmRawBigdata == null) {
                        }
                    }
                }
                d2 = 0.0d;
                if (this.mHasPsiIoPermission) {
                }
                d3 = 0.0d;
                if (currentKpmRawPolicy != null) {
                }
                if (currentKpmRawBigdata == null) {
                }
            }
        }
        d = 0.0d;
        if (this.mHasPsiMemoryPermission) {
        }
        d2 = 0.0d;
        if (this.mHasPsiIoPermission) {
        }
        d3 = 0.0d;
        if (currentKpmRawPolicy != null) {
        }
        if (currentKpmRawBigdata == null) {
        }
    }

    public void setPrevPackage(String str) {
        this.mPrevPackage = str;
        if (KPM_DEBUG) {
            Slog.d("ActivityManager_kpm", "setPrevPackage() mPrevPackage : " + this.mPrevPackage);
        }
    }

    public boolean isKpmSafe(String str) {
        String str2 = this.mPrevPackage;
        boolean z = true;
        if (str2 != null && str2.length() > 0) {
            z = true ^ this.mPrevPackage.equals(str);
        }
        if (KPM_DEBUG) {
            Slog.d("ActivityManager_kpm", "isKpmSafe() callingPackage : " + str + ", mPrevPackage : " + this.mPrevPackage);
        }
        return z;
    }

    public void addPrevProcessList(String str) {
        synchronized (this.mPrevProcessList) {
            this.mPrevProcessList.add(str);
            if (this.mPrevProcessList.size() > 5) {
                this.mPrevProcessList.remove(0);
            }
            if (KPM_DEBUG) {
                for (int i = 0; i < this.mPrevProcessList.size(); i++) {
                    Slog.i("ActivityManager_kpm", "idx[" + i + "] Process name : " + ((String) this.mPrevProcessList.get(i)));
                }
            }
        }
    }

    public String getCurrentProcess() {
        synchronized (this.mPrevProcessList) {
            int size = this.mPrevProcessList.size();
            if (size < 1) {
                return null;
            }
            return (String) this.mPrevProcessList.get(size - 1);
        }
    }

    public String getPrevProcess() {
        synchronized (this.mPrevProcessList) {
            int size = this.mPrevProcessList.size();
            if (size < 2) {
                return null;
            }
            return (String) this.mPrevProcessList.get(size - 2);
        }
    }

    public String get2ndPrevProcess() {
        synchronized (this.mPrevProcessList) {
            int size = this.mPrevProcessList.size();
            if (size < 3) {
                return null;
            }
            return (String) this.mPrevProcessList.get(size - 3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x013f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void calculateLmkdStatus(int i) {
        int curAdj;
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        if (currentKpmRawPolicy == null || currentKpmRawBigdata == null) {
            return;
        }
        String currentProcess = getCurrentProcess();
        String prevProcess = getPrevProcess();
        String str = get2ndPrevProcess();
        ProcessRecord processRecord = null;
        for (int i2 = 0; i2 < this.mAm.mProcessList.getLruProcessesLOSP().size(); i2++) {
            try {
                try {
                    ProcessRecord processRecord2 = (ProcessRecord) this.mAm.mProcessList.getLruProcessesLOSP().get(i2);
                    if (processRecord2 != null && i == processRecord2.mPid) {
                        try {
                            if (KPM_DEBUG) {
                                Slog.i("ActivityManager_kpm", "getLruProcessesLOSP() pid[" + i + "] " + processRecord2.processName);
                            }
                            processRecord = processRecord2;
                            break;
                        } catch (IndexOutOfBoundsException e) {
                            e = e;
                            processRecord = processRecord2;
                            Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                        } catch (Exception e2) {
                            e = e2;
                            processRecord = processRecord2;
                            Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                            if (processRecord == null) {
                            }
                            curAdj = processRecord.mState.getCurAdj();
                            boolean hasActivities = processRecord.hasActivities();
                            if (KPM_DEBUG) {
                            }
                            if (curAdj == -10000) {
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        if (processRecord == null) {
            for (int i3 = 0; i3 < this.mAm.mProcessList.mRemovedProcesses.size(); i3++) {
                try {
                    try {
                        ProcessRecord processRecord3 = (ProcessRecord) this.mAm.mProcessList.mRemovedProcesses.get(i3);
                        if (processRecord3 != null && i == processRecord3.mPid) {
                            try {
                                if (KPM_DEBUG) {
                                    Slog.i("ActivityManager_kpm", "mRemovedProcesses pid[" + i + "] " + processRecord3.processName);
                                }
                                processRecord = processRecord3;
                                break;
                            } catch (IndexOutOfBoundsException e5) {
                                e = e5;
                                processRecord = processRecord3;
                                Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                            } catch (Exception e6) {
                                e = e6;
                                processRecord = processRecord3;
                                Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                                if (processRecord == null) {
                                }
                                curAdj = processRecord.mState.getCurAdj();
                                boolean hasActivities2 = processRecord.hasActivities();
                                if (KPM_DEBUG) {
                                }
                                if (curAdj == -10000) {
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e7) {
                        e = e7;
                    }
                } catch (Exception e8) {
                    e = e8;
                }
            }
            if (processRecord == null) {
                Slog.i("ActivityManager_kpm", "calculateLmkdStatus pid[" + i + "] but don't get process info");
                return;
            }
        }
        curAdj = processRecord.mState.getCurAdj();
        boolean hasActivities22 = processRecord.hasActivities();
        if (KPM_DEBUG) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("pid[");
            sb.append(i);
            sb.append("] ");
            sb.append(processRecord.processName);
            sb.append(" adj[");
            sb.append(curAdj);
            sb.append("] hasActivities ");
            sb.append(hasActivities22);
            Slog.i("ActivityManager_kpm", sb.toString());
        }
        if (curAdj == -10000) {
            return;
        }
        if (currentProcess != null && hasActivities22 && currentProcess.equals(processRecord.processName)) {
            LmkdState lmkdState = LmkdState.LEVEL_14;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState);
            String str2 = processRecord.processName + "_" + curAdj;
            Slog.i("ActivityManager_kpm", "calculateLmkdStatus currentProcess Killed " + str2);
            currentKpmRawPolicy.lmkdKilledProcessInfo.add(str2);
            currentKpmRawBigdata.lmkdKilledProcessInfo.add(str2);
        } else if (prevProcess != null && hasActivities22 && prevProcess.equals(processRecord.processName)) {
            LmkdState lmkdState2 = LmkdState.LEVEL_10;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState2);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState2);
            if (!this.mProcessHeavyMemory.isThreadRunning) {
                this.mProcessHeavyMemory.clearHeavyList();
                this.mProcessHeavyMemory.gatherHeavyProcessThread(currentKpmRawPolicy, currentKpmRawBigdata);
            }
        } else if (str != null && hasActivities22 && str.equals(processRecord.processName)) {
            LmkdState lmkdState3 = LmkdState.LEVEL_8;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState3);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState3);
        } else if (curAdj < 830) {
            if (curAdj >= 50 && curAdj <= 250) {
                LmkdState lmkdState4 = LmkdState.LEVEL_12;
                currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState4);
                currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState4);
            } else {
                LmkdState lmkdState5 = LmkdState.LEVEL_5;
                currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState5);
                currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState5);
            }
        } else if (!hasActivities22) {
            LmkdState lmkdState6 = LmkdState.LEVEL_1;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState6);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState6);
        } else if (curAdj > 900) {
            LmkdState lmkdState7 = LmkdState.LEVEL_2;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState7);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState7);
        } else if (curAdj == 900) {
            LmkdState lmkdState8 = LmkdState.LEVEL_3;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState8);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState8);
        } else if (curAdj >= 830) {
            LmkdState lmkdState9 = LmkdState.LEVEL_4;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState9);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState9);
        } else {
            LmkdState lmkdState10 = LmkdState.LEVEL_6;
            currentKpmRawPolicy.updateLmkdStateAndCount(lmkdState10);
            currentKpmRawBigdata.updateLmkdStateAndCount(lmkdState10);
        }
        if (this.mProcessHeavyMemory.isThreadRunning) {
            this.mProcessHeavyMemory.setPeakLmkdKillLevel(curAdj);
        }
        if (KPM_DEBUG) {
            StringBuilder sb2 = new StringBuilder(128);
            Slog.i("ActivityManager_kpm", "kpmRawBigdata lmkdState : " + currentKpmRawBigdata.lmkdState.getLevel());
            sb2.append("kpmRawBigdata lmkdStateCount [");
            for (LmkdState lmkdState11 : LmkdState.values()) {
                sb2.append(currentKpmRawBigdata.lmkdStateCount[lmkdState11.ordinal()]);
                sb2.append(", ");
            }
            sb2.append("]");
            Slog.i("ActivityManager_kpm", sb2.toString());
        }
    }

    public final KpmRaw getCurrentKpmRawPolicy() {
        if (this.mIsFirstAppLaunch) {
            return this.mKpmRawPolicy[this.mPolicyIndex];
        }
        return null;
    }

    public final KpmRaw getCurrentKpmRawBigdata() {
        if (this.mIsFirstAppLaunch) {
            return this.mKpmRawBigdata[this.mBigdataIndex];
        }
        return null;
    }

    public final void newKpmRawPolicy() {
        int i = this.mPolicyMetric;
        int i2 = i % 24;
        this.mPolicyIndex = i2;
        int i3 = i + 1;
        this.mPolicyMetric = i3;
        this.mKpmRawPolicy[i2] = new KpmRaw(i3);
    }

    public final void newKpmRawBigdata() {
        int i = this.mBigdataMetric;
        int i2 = i % 30;
        this.mBigdataIndex = i2;
        int i3 = i + 1;
        this.mBigdataMetric = i3;
        this.mKpmRawBigdata[i2] = new KpmRaw(i3);
    }

    public void calculateKpmStats(String str, String str2, int i) {
        if (!this.mIsFirstAppLaunch) {
            newKpmRawPolicy();
            newKpmRawBigdata();
            scheduleDailyUserTrendRandomSample();
            this.mIsFirstAppLaunch = true;
        }
        this.mAppLaunchCount++;
        this.mLmkdReader.readLmkdKillCount();
        updateKpmProcessData(str, i);
        updateKpmMemData();
        updateKpmPsiData();
        updateKpmCycleData();
        setPrevPackage(str);
        addPrevProcessList(str2);
        PersonalizedMemoryManager.getInstance().onMemoryEvent(this.mContext, PersonalizedMemoryManager.MemoryEventType.APP_LAUNCHED);
    }

    public void forceChangeState(String str) {
        str.hashCode();
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

    public KpmState changeState(KpmRaw kpmRaw) {
        LmkdCounter lmkdCounter = kpmRaw.lmkdCounter;
        if (lmkdCounter.cyclePreviousKillCount > 0) {
            return KpmState.CRITICAL;
        }
        if (lmkdCounter.cycleCachedMinKillCount + lmkdCounter.cycleBServiceKillCount > 0) {
            return KpmState.HEAVY;
        }
        if (lmkdCounter.cycleSeedbedKillCount + lmkdCounter.cyclePickedKillCount + kpmRaw.cachedNormalKillCount > 0) {
            return KpmState.NORMAL;
        }
        return KpmState.LIGHT;
    }

    public KpmState changeTinyKpmState(KpmRaw kpmRaw) {
        KpmState kpmState;
        LmkdCounter lmkdCounter = kpmRaw.lmkdCounter;
        if (lmkdCounter.tinyCyclePreviousKillCount > 0) {
            kpmState = KpmState.CRITICAL;
        } else if (lmkdCounter.tinyCycleCachedMinKillCount + lmkdCounter.tinyCycleBServiceKillCount > 0) {
            kpmState = KpmState.HEAVY;
        } else if (lmkdCounter.tinyCycleSeedbedKillCount + lmkdCounter.tinyCyclePickedKillCount + (kpmRaw.cachedNormalKillCount - kpmRaw.prevTinyCachedNormalKillCount) > 0) {
            kpmState = KpmState.NORMAL;
        } else {
            kpmState = KpmState.LIGHT;
        }
        kpmRaw.prevTinyCachedNormalKillCount = kpmRaw.cachedNormalKillCount;
        return kpmState;
    }

    public void sendKpmStateChangeIntent() {
        Intent intent = new Intent();
        intent.setPackage("android");
        intent.setAction("com.samsung.KPM_STATE_CHANGED");
        intent.putExtra("kpm_level", this.mCurrentState.ordinal());
        intent.putExtra("kpm_prev_level", this.mPrevState.ordinal());
        Slog.d("ActivityManager_kpm", "Broadcast sent: prev state = " + this.mPrevState.ordinal() + ", cur state = " + this.mCurrentState.ordinal());
        this.mContext.sendBroadcast(intent);
    }

    public void applyPolicy() {
        if (this.mPolicyMetric > 4) {
            float ordinal = (((this.mKpmRawPolicy[(r0 - 1) % 24].nextKpmState.ordinal() + this.mKpmRawPolicy[(this.mPolicyMetric - 2) % 24].nextKpmState.ordinal()) + this.mKpmRawPolicy[(this.mPolicyMetric - 3) % 24].nextKpmState.ordinal()) + this.mKpmRawPolicy[(this.mPolicyMetric - 4) % 24].nextKpmState.ordinal()) / 4.0f;
            ChimeraTriggerManager.getInstance(this.mContext).onPolicyScoreUpdated(ordinal);
            KpmState kpmState = KpmState.values()[(int) (ordinal + 0.5d)];
            if (KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "Current policy : " + kpmState + ", eval policy : " + kpmState);
            }
            if (this.mPolicyState != kpmState) {
                changePolicy(kpmState);
            }
        }
    }

    public void changePolicy(KpmState kpmState) {
        if (KPM_DEBUG) {
            Slog.d("ActivityManager_kpm", "Policy [" + kpmState + "]");
        }
        this.mPolicyState = kpmState;
        this.mSwappinessController.setValue(SWAPPINESS[kpmState.ordinal()]);
        sendKpmStateChangeIntent();
    }

    public void loggingAppDied(ProcessRecord processRecord) {
        int curProcState = processRecord.mState.getCurProcState();
        int setAdj = processRecord.mState.getSetAdj();
        boolean isKilledByAm = processRecord.isKilledByAm();
        KpmRaw currentKpmRawPolicy = getCurrentKpmRawPolicy();
        KpmRaw currentKpmRawBigdata = getCurrentKpmRawBigdata();
        if (!isKilledByAm && setAdj >= 900 && setAdj <= 999) {
            if (curProcState == 16 || curProcState == 18) {
                if (currentKpmRawPolicy != null) {
                    currentKpmRawPolicy.cachedNormalKillCount++;
                }
                if (currentKpmRawBigdata != null) {
                    currentKpmRawBigdata.cachedNormalKillCount++;
                }
            } else if (curProcState == 17) {
                if (currentKpmRawPolicy != null) {
                    currentKpmRawPolicy.cachedClientKillCount++;
                }
                if (currentKpmRawBigdata != null) {
                    currentKpmRawBigdata.cachedClientKillCount++;
                }
            } else if (curProcState == 19) {
                if (currentKpmRawPolicy != null) {
                    currentKpmRawPolicy.cachedEmptyKillCount++;
                }
                if (currentKpmRawBigdata != null) {
                    currentKpmRawBigdata.cachedEmptyKillCount++;
                }
            }
        }
        if (KPM_DEBUG) {
            int i = currentKpmRawPolicy != null ? currentKpmRawPolicy.currentCachedActCnt : 0;
            StringBuilder sb = new StringBuilder(128);
            if (!isKilledByAm) {
                sb.append("loggingAppDied Killed Process : ");
                sb.append(this.mAppLaunchCount);
            } else {
                sb.append("loggingAppDied KilledByAm Process : ");
                sb.append(this.mAppLaunchCount);
            }
            sb.append(", App PID:");
            sb.append(processRecord.mPid);
            sb.append(", App Name:");
            sb.append(processRecord.processName);
            sb.append(", App SetADJ:");
            sb.append(setAdj);
            sb.append(", App AdjType:");
            sb.append(processRecord.mState.getAdjType());
            sb.append(", Current CCH-ACT Count:");
            sb.append(i);
            sb.append(", Proc State:");
            sb.append(curProcState);
            Slog.d("ActivityManager_kpm", sb.toString());
        }
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr != null && strArr.length > 1) {
            String str = strArr[1];
            str.hashCode();
            if (str.equals("leak.dmabuf")) {
                PersonalizedMemoryManager.getInstance().setTestMode(true);
                PersonalizedMemoryManager.getInstance().onMemoryEvent(this.mContext, PersonalizedMemoryManager.MemoryEventType.LMKD_KILL);
                PersonalizedMemoryManager.getInstance().setTestMode(false);
                return;
            }
            return;
        }
        int i = this.mPolicyIndex;
        int i2 = this.mBigdataIndex;
        if (this.mPolicyMetric >= 1) {
            printWriter.println(" KPM Stats(policy):");
            printWriter.println(" [idx,avg_mem,mem_avl,avg_swap,hotCnt,prKillCnt,svKillCnt,PkgCnt,PkgKillCnt,lmkdTotal,lmkdPrev,lmkdService,lmkdPicked,lmkdSeed,lmkdCri,lmk,cachedkill,emptykill,cur_state,next_state,policy_state,psi_cpu_avg,psi_mem_avg,psi_io_avg,psi_cpu_max,psi_mem_max,psi_io_max,reset_status,lmkdState,lmkdStateCnt,lmkdCnt,tiny_nxst_history,time]");
            int min = Math.min(this.mPolicyMetric, 24);
            int i3 = i;
            for (int i4 = 0; i4 < min; i4++) {
                printWriter.println(this.mKpmRawPolicy[i3].getKpmData(this.mPolicyMetric).toString());
                i3--;
                if (i3 < 0) {
                    i3 = 23;
                }
            }
            printWriter.println();
            printWriter.println(" Heavy Pss List(policy):");
            printWriter.println(" [idx, peakLmkdKillAdj, [procMemInfo], [{native(Pss SwapPss Rss)}{sys}{pers}{persvc}{fore}{vis}{percept}{perceptl}{perceptm}{backup}{heavy}{servicea}{home}{prev}{serviceb}{archived}{picked}{cached}], [Process name, Process version, Package name, Package version, label, pss, swap, rss, procstats_dumpPssUssRss], ...]");
            for (int i5 = 0; i5 < min; i5++) {
                printWriter.println(this.mKpmRawPolicy[i].getKpmHeavyPssData(this.mPolicyMetric).toString());
                i--;
                if (i < 0) {
                    i = 23;
                }
            }
        }
        if (this.mBigdataMetric >= 1) {
            printWriter.println();
            printWriter.println(" KPM Stats(bigdata):");
            printWriter.println(" [idx,avg_mem,mem_avl,avg_swap,hotCnt,prKillCnt,svKillCnt,PkgCnt,PkgKillCnt,lmkdTotal,lmkdPrev,lmkdService,lmkdPicked,lmkdSeed,lmkdCri,lmk,cachedkill,emptykill,cur_state,next_state,policy_state,psi_cpu_avg,psi_mem_avg,psi_io_avg,psi_cpu_max,psi_mem_max,psi_io_max,reset_status,lmkdState,lmkdStateCnt,lmkdCnt,tiny_nxst_history,time]");
            int min2 = Math.min(this.mBigdataMetric, 30);
            int i6 = i2;
            for (int i7 = 0; i7 < min2; i7++) {
                printWriter.println(this.mKpmRawBigdata[i6].getKpmData(this.mBigdataMetric).toString());
                i6--;
                if (i6 < 0) {
                    i6 = 29;
                }
            }
            printWriter.println();
            printWriter.println(" Heavy Pss List(bigdata):");
            printWriter.println(" [idx, peakLmkdKillAdj, [procMemInfo], [{native(Pss SwapPss Rss)}{sys}{pers}{persvc}{fore}{vis}{percept}{perceptl}{perceptm}{backup}{heavy}{servicea}{home}{prev}{serviceb}{archived}{picked}{cached}], [Process name, Process version, Package name, Package version, label, pss, swap, rss, procstats_dumpPssUssRss], ...]");
            for (int i8 = 0; i8 < min2; i8++) {
                printWriter.println(this.mKpmRawBigdata[i2].getKpmHeavyPssData(this.mBigdataMetric).toString());
                i2--;
                if (i2 < 0) {
                    i2 = 29;
                }
            }
        }
        printWriter.println(" ");
        printWriter.println(" KPM Tunable Parameters:");
        printWriter.println(" KPM_POLICY_ENABLE: " + KPM_POLICY_ENABLE);
        printWriter.println(" KPM_DEBUG_ENABLE: " + KPM_DEBUG);
        printWriter.println(" KPM_CURRENT_STATE: " + this.mCurrentState);
        printWriter.println(" KPM_WARM_UP_TRIGGER_TUNABLE: " + sWarmUpTrigger);
        printWriter.println(" KPM_POLICY_TRIGGER_TUNABLE: " + sPolicyTrigger);
        printWriter.println(" KPM_WARM_UP_CYCLES_TUNABLE: " + sWarmUpCycles);
        printWriter.println(" KPM_MEM_CRITICAL_LOW_DETECT_ENABLE: " + isDetectCritcialLowEnabled());
        printWriter.println(" MEMORY_CRITICAL_LOW_KILL_DETECT_ADJ : 700");
        printWriter.println(" MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH : " + getCriticalLowProcessKillRatioTH() + "%");
        StringBuilder sb = new StringBuilder();
        sb.append(" MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO : ");
        sb.append(MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO);
        printWriter.println(sb.toString());
        printWriter.println(" MEMORY_CRITICAL_LOW_PROCESS_KILL_PACKAGE_RATIO_TH : " + getCriticalLowPackageKillRatioTH() + "%");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" KPM_POLICY_SWAPPINESS_DEFAULT : ");
        sb2.append(this.mSwappinessController.getDefaultValue());
        printWriter.println(sb2.toString());
        printWriter.println(" KPM_POLICY_SWAPPINESS_CURRENT : " + this.mSwappinessController.getValue());
        printWriter.println(" ");
        printWriter.println("\n KPM Auto Restart Parameters");
        printWriter.println(" - Min Triggger Size    : " + String.valueOf(sMinTriggerSize));
        printWriter.println(" - Never Collect Within : " + String.valueOf(sNeverCollectWithin));
        printWriter.println(" - Compact Trigger Size : " + String.valueOf(sCompactTriggerSize));
        printWriter.println(" - Native Flood Ratio   : " + String.valueOf(sNativeFloodRatio));
        printWriter.println(" - Syspers Flood Ratio  : " + String.valueOf(sSyspersFloodRatio));
        printWriter.println("");
        ChimeraTriggerManager.getInstance(this.mContext).dump(printWriter);
        this.mMemoryFloodDetector.dump(printWriter);
    }

    public void fillChimeraDataIfExist(KpmRaw kpmRaw, JSONObject jSONObject) {
        ChimeraDataInfo chimeraDataInfo = kpmRaw.chimeraDataInfo;
        if (chimeraDataInfo != null) {
            jSONObject.put("CHWT", new JSONArray().put((int) ((chimeraDataInfo.getLruWight() * 100.0f) + 0.5d)).put((int) ((chimeraDataInfo.getStdBktWeight() * 100.0f) + 0.5d)).put((int) ((chimeraDataInfo.getMemWeight() * 100.0f) + 0.5d)));
            int[] triggerCntSrc = chimeraDataInfo.getTriggerCntSrc();
            ChimeraCommonUtil.TriggerSource triggerSource = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE;
            jSONObject.put("CHTC", triggerCntSrc[triggerSource.ordinal()]);
            int[] triggerCntSrc2 = chimeraDataInfo.getTriggerCntSrc();
            ChimeraCommonUtil.TriggerSource triggerSource2 = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD;
            jSONObject.put("CLTC", triggerCntSrc2[triggerSource2.ordinal()]);
            int[] triggerCntSrc3 = chimeraDataInfo.getTriggerCntSrc();
            ChimeraCommonUtil.TriggerSource triggerSource3 = ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_BOTTLENECK_HINT;
            jSONObject.put("CBTC", triggerCntSrc3[triggerSource3.ordinal()]);
            jSONObject.put("CHAC", chimeraDataInfo.getActionCntSrc()[triggerSource.ordinal()]);
            jSONObject.put("CLAC", chimeraDataInfo.getActionCntSrc()[triggerSource2.ordinal()]);
            jSONObject.put("CBAC", chimeraDataInfo.getActionCntSrc()[triggerSource3.ordinal()]);
            jSONObject.put("CKLC", chimeraDataInfo.getKillCnt());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < chimeraDataInfo.getAdjKillCnt().length; i++) {
                jSONArray.put(chimeraDataInfo.getAdjKillCnt()[i]);
            }
            jSONObject.put("CKAI", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < chimeraDataInfo.getGroupKillCnt().length; i2++) {
                jSONArray2.put(chimeraDataInfo.getGroupKillCnt()[i2]);
            }
            jSONObject.put("CKGI", jSONArray2);
        }
    }

    public synchronized void sendHqmBigData(String str, String str2) {
        if (this.mHqmManager == null) {
            this.mHqmManager = (SemHqmManager) this.mContext.getSystemService("HqmManagerService");
        }
        SemHqmManager semHqmManager = this.mHqmManager;
        if (semHqmManager == null) {
            if (KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "HQM services is not working");
            }
            return;
        }
        if (str2 != null) {
            boolean sendHWParamToHQM = semHqmManager.sendHWParamToHQM(KPM_DEBUG ? 1 : 0, "Sluggish", str, "ph", "1.1", "sec", "", str2, "");
            if (KPM_DEBUG) {
                if (sendHWParamToHQM) {
                    Slog.d("ActivityManager_kpm", "Success to report '" + str + "' : " + str2);
                } else {
                    Slog.d("ActivityManager_kpm", "failed to send to server");
                }
            }
        } else if (KPM_DEBUG) {
            Slog.d("ActivityManager_kpm", "failed to send to server.");
        }
    }

    public void reportMemInfo() {
        String str;
        String[] strArr = {"MemFree", "MemAvailable", "Cached", "Active(file)", "Inactive(file)", "RbinFree", "RbinCached", "SwapTotal", "SwapFree", "KReclaimable", "SReclaimable", "SUnreclaim", "KgslShmemUsage", "KgslReclaimed", "system", "zram0", "KgslSharedmem", "PageTables", "VmallocUsed", "Active(anon)", "Inactive(anon)", "Mapped", "AnonPages", "Shmem", "MemTotal", "HugepagePool", "DmaHeapPool", "Unevictable", "system-uncached", "RbinAlloced", "RbinPool"};
        String[] strArr2 = {"workingset_refault_anon", "workingset_refault_file", "pgpgin", "pgpgout", "pswpin", "pswpout", "allocstall_normal", "allocstall_movable", "pgfault", "pgmajfault", "pgsteal_kswapd", "pgsteal_direct", "pgscan_kswapd", "pgscan_direct", "oom_kill", "cma_alloc_fail", "allocstall_dma32"};
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", 4);
            jSONObject.put("KVER", "4.0");
            jSONObject.put("DRAM", this.mTotalMem * 1024);
            jSONObject.put("SWPT", this.mTotalSwap);
            JSONArray jSONArray = new JSONArray();
            HashMap memInfo = getMemInfo();
            for (int i = 0; i < 31; i++) {
                String str3 = strArr[i];
                if (memInfo.containsKey(str3)) {
                    jSONArray.put(((Long) memInfo.get(str3)).longValue() / 1024);
                } else {
                    jSONArray.put(0);
                }
            }
            jSONObject.put("PMI", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            HashMap vmStat = getVmStat();
            for (int i2 = 0; i2 < 17; i2++) {
                String str4 = strArr2[i2];
                long j = 0;
                long longValue = this.mVmStats.containsKey(str4) ? ((Long) this.mVmStats.get(str4)).longValue() : 0L;
                if (vmStat.containsKey(str4)) {
                    j = ((Long) vmStat.get(str4)).longValue() - longValue;
                }
                jSONArray2.put(j);
            }
            jSONObject.put("VMSTAT", jSONArray2);
            this.mVmStats = vmStat;
            str2 = jSONObject.toString();
            str = str2.substring(1, str2.length() - 1);
        } catch (JSONException unused) {
            Slog.d("ActivityManager_kpm", "failed to create the KPUT");
            str = str2;
        }
        sendHqmBigData("KPUT", str);
    }

    public void reportUserTrend(int i, KpmRaw kpmRaw) {
        String str;
        String str2;
        long j;
        long j2;
        if (kpmRaw == null) {
            Slog.d("ActivityManager_kpm", "data is null");
            return;
        }
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
            double d = 0.5d;
            jSONObject.put("FCMA", (int) ((kpmRaw.accMem / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("AVMA", (int) ((kpmRaw.avlMem / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("SWUA", (int) ((kpmRaw.accSwap / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("DISP", this.mDisplaySizeStr);
            jSONObject.put("PCKC", (int) ((kpmRaw.pickedTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("PCKA", (int) ((kpmRaw.pickedActTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("SDBC", (int) ((kpmRaw.seedbedTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("CCHC", (int) ((kpmRaw.cachedTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("CCHA", (int) ((kpmRaw.cachedActTotalCnt / kpmRaw.appCnt) + 0.5d));
            jSONObject.put("LNCT", new JSONArray().put(kpmRaw.hotCount).put(kpmRaw.warmCount).put(kpmRaw.coldCount));
            jSONObject.put("LPKC", kpmRaw.launchedPackageCount);
            jSONObject.put("PKPC", kpmRaw.lmkdCounter.previousKillOccurredCount);
            jSONObject.put("SKSC", kpmRaw.lmkdCounter.bServiceKillOccurredCount);
            jSONObject.put("SKPC", kpmRaw.killedPackageCount);
            jSONObject.put("VFAK", kpmRaw.lmkdCounter.cycleVisToFgKillCount);
            jSONObject.put("HPAK", kpmRaw.lmkdCounter.cycleHomeToPercKillCount);
            jSONObject.put("PRAK", kpmRaw.lmkdCounter.cyclePreviousKillCount);
            jSONObject.put("SRVK", kpmRaw.lmkdCounter.cycleBServiceKillCount);
            jSONObject.put("PCKK", kpmRaw.lmkdCounter.cyclePickedKillCount);
            jSONObject.put("SBAK", kpmRaw.lmkdCounter.cycleSeedbedKillCount);
            jSONObject.put("CHMK", kpmRaw.lmkdCounter.cycleCachedMinKillCount);
            jSONObject.put("CHEK", kpmRaw.cachedEmptyKillCount);
            jSONObject.put("CHNK", kpmRaw.cachedNormalKillCount);
            jSONObject.put("CACK", kpmRaw.cachedClientKillCount);
            jSONObject.put("APPC", kpmRaw.appCnt);
            jSONObject.put("MEDK", kpmRaw.lmkdMed);
            jSONObject.put("CRIK", kpmRaw.lmkdCric);
            jSONObject.put("LMKK", kpmRaw.lmkCnt);
            if (KPM_POLICY_ENABLE) {
                jSONObject.put("SWPN", this.mSwappinessController.getValue());
            }
            JSONArray jSONArray = new JSONArray();
            char c = 0;
            char c2 = 1;
            if (kpmRaw.procsAdjPss != null) {
                String[] strArr = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL;
                int length = strArr.length;
                int i2 = 0;
                while (i2 < length) {
                    String str3 = strArr[i2];
                    if (kpmRaw.procsAdjPss.containsKey(str3)) {
                        j2 = (long) (((r3[c] + r3[c2]) / 1024.0d) + d);
                        j = ((long[]) kpmRaw.procsAdjPss.get(str3))[3];
                    } else {
                        j = 0;
                        j2 = 0;
                    }
                    jSONArray.put(j2).put(j);
                    i2++;
                    d = 0.5d;
                    c = 0;
                    c2 = 1;
                }
                for (String str4 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    jSONArray.put(kpmRaw.procsAdjPss.containsKey(str4) ? (long) ((((long[]) kpmRaw.procsAdjPss.get(r9))[1] / 1024.0d) + 0.5d) : 0L);
                }
                for (String str5 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    jSONArray.put(kpmRaw.procsAdjPss.containsKey(str5) ? (long) ((((long[]) kpmRaw.procsAdjPss.get(r9))[2] / 1024.0d) + 0.5d) : 0L);
                }
            }
            jSONObject.put("PRST", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put((int) ((kpmRaw.psiCpuSum / kpmRaw.appCnt) + 0.5d)).put((int) ((kpmRaw.psiMemorySum / kpmRaw.appCnt) + 0.5d)).put((int) ((kpmRaw.psiIoSum / kpmRaw.appCnt) + 0.5d)).put((int) (kpmRaw.psiCpuMax + 0.5d)).put((int) (kpmRaw.psiMemoryMax + 0.5d)).put((int) (kpmRaw.psiIoMax + 0.5d));
            jSONObject.put("PSIS", jSONArray2);
            fillChimeraDataIfExist(kpmRaw, jSONObject);
            jSONObject.put("STAY", this.mNumberOfStay);
            jSONObject.put("ELAP", kpmRaw.getElapsedTimeMin());
            jSONObject.put("UPTM", (SystemClock.uptimeMillis() / 1000) / 60);
            jSONObject.put("RSTF", kpmRaw.resetStatus);
            jSONObject.put("KVER", "4.0");
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
                    DumpHeavyProcessInfo dumpHeavyProcessInfo = (DumpHeavyProcessInfo) kpmRaw.dumpHeavyProcessList.get(i4);
                    jSONArray6.put(dumpHeavyProcessInfo.memoryInfo.procName);
                    String str6 = dumpHeavyProcessInfo.packageVersion;
                    if (str6 != null) {
                        jSONArray6.put(str6);
                    } else {
                        jSONArray6.put(String.valueOf(dumpHeavyProcessInfo.processVersion));
                    }
                    jSONArray6.put(dumpHeavyProcessInfo.memoryInfo.label).put((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d)).put((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d)).put((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                }
            }
            jSONObject.put("LHI", jSONArray6);
            JSONArray jSONArray7 = new JSONArray();
            ProcMemInfo procMemInfo = kpmRaw.procMemInfo;
            if (procMemInfo != null) {
                jSONArray7.put(procMemInfo.memTotal).put(kpmRaw.procMemInfo.memFree).put(kpmRaw.procMemInfo.memAvailable).put(kpmRaw.procMemInfo.cached).put(kpmRaw.procMemInfo.activeFile).put(kpmRaw.procMemInfo.inactiveFile).put(kpmRaw.procMemInfo.rbinFree).put(kpmRaw.procMemInfo.rbinCached).put(this.mTotalSwap).put(kpmRaw.procMemInfo.swapFree).put(kpmRaw.procMemInfo.kReclaimable).put(kpmRaw.procMemInfo.sReclaimable).put(kpmRaw.procMemInfo.sUnreclaim).put(0).put(kpmRaw.procMemInfo.gpuSwap).put(kpmRaw.procMemInfo.systemCached).put(kpmRaw.procMemInfo.zram).put(kpmRaw.procMemInfo.gpuTotal).put(kpmRaw.procMemInfo.vmallocUsed).put(kpmRaw.procMemInfo.systemUncached);
            }
            jSONObject.put("PMI", jSONArray7);
            JSONArray jSONArray8 = new JSONArray();
            if (kpmRaw.dumpHeavyMemoryUsageByAdj != null) {
                for (String str7 : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                    if (kpmRaw.dumpHeavyMemoryUsageByAdj.containsKey(str7)) {
                        long[] jArr = (long[]) kpmRaw.dumpHeavyMemoryUsageByAdj.get(str7);
                        Objects.requireNonNull(this.mProcessHeavyMemory);
                        JSONArray put = jSONArray8.put((int) ((jArr[0] / 1024.0d) + 0.5d));
                        Objects.requireNonNull(this.mProcessHeavyMemory);
                        JSONArray put2 = put.put((int) ((jArr[1] / 1024.0d) + 0.5d));
                        Objects.requireNonNull(this.mProcessHeavyMemory);
                        put2.put((int) ((jArr[2] / 1024.0d) + 0.5d));
                    } else {
                        jSONArray8.put(-1).put(-1).put(-1);
                    }
                }
            }
            jSONObject.put("LHMA", jSONArray8);
            JSONArray jSONArray9 = new JSONArray();
            if (kpmRaw.tinyKpmState != null) {
                int i5 = 0;
                while (true) {
                    KpmState[] kpmStateArr = kpmRaw.tinyKpmState;
                    if (i5 >= kpmStateArr.length) {
                        break;
                    }
                    jSONArray9.put(kpmStateArr[i5].ordinal());
                    i5++;
                }
            }
            jSONObject.put("TNXST", jSONArray9);
            str = jSONObject.toString();
        } catch (JSONException unused) {
            str = null;
        }
        try {
            str2 = str.substring(1, str.length() - 1);
        } catch (JSONException unused2) {
            Slog.d("ActivityManager_kpm", "failed to create the KPUT");
            str2 = str;
            sendHqmBigData("KPUT", str2);
        }
        sendHqmBigData("KPUT", str2);
    }

    public enum KpmState {
        NONE("None"),
        LIGHT("Light"),
        NORMAL("Normal"),
        HEAVY("Heavy"),
        CRITICAL("Critical");

        private String mName;

        KpmState(String str) {
            this.mName = str;
        }

        public String getName() {
            return this.mName;
        }
    }

    public enum LmkdState {
        LEVEL_0(0),
        LEVEL_1(1),
        LEVEL_2(2),
        LEVEL_3(3),
        LEVEL_4(4),
        LEVEL_5(5),
        LEVEL_6(6),
        LEVEL_7(7),
        LEVEL_8(8),
        LEVEL_9(9),
        LEVEL_10(10),
        LEVEL_11(11),
        LEVEL_12(12),
        LEVEL_13(13),
        LEVEL_14(14),
        LEVEL_15(15);

        private int mLevel;

        LmkdState(int i) {
            this.mLevel = i;
        }

        public int getLevel() {
            return this.mLevel;
        }
    }

    public enum LmkdCount {
        CACHED_APP_MAX_ADJ(999),
        CACHED_APP_MIN_ADJ(900),
        SEEDBED_ADJ(860),
        PICKED_ADJ(850),
        ARCHIVED_ADJ(830),
        SERVICE_B_ADJ(800),
        PREVIOUS_APP_ADJ(700),
        HOME_APP_ADJ(600),
        SERVICE_ADJ(500),
        HEAVY_WEIGHT_APP_ADJ(400),
        BORA_APP_ADJ(350),
        BACKUP_APP_ADJ(300),
        PERCEPTIBLE_LOW_APP_ADJ(FrameworkStatsLog.f446x58c6c32),
        PERCEPTIBLE_MEDIUM_APP_ADJ(225),
        PERCEPTIBLE_APP_ADJ(200),
        VISIBLE_APP_ADJ(100),
        FOREGROUND_APP_ADJ(0);

        private int mADJ;

        LmkdCount(int i) {
            this.mADJ = i;
        }

        public int getADJ() {
            return this.mADJ;
        }
    }

    public class SCPMReceiver extends BroadcastReceiver {
        public final /* synthetic */ KillPolicyManager this$0;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("sec.app.policy.UPDATE.kpm".equals(intent.getAction())) {
                Slog.v("ActivityManager_kpm", "ACTION***" + intent.getAction());
                this.this$0.updateSCPMParametersFromDB();
            }
        }
    }

    public class ProcMemInfo {
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

        public ProcMemInfo() {
        }
    }

    public class KpmRaw {
        public int appCnt;
        public int cachedClientKillCount;
        public int cachedEmptyKillCount;
        public int cachedNormalKillCount;
        public ChimeraDataInfo chimeraDataInfo;
        public long createTime;
        public KpmState curKpmState;
        public int cycleNum;
        public HashMap dumpHeavyMemoryUsageByAdj;
        public ArrayList dumpHeavyProcessList;
        public boolean isDailyBigdata;
        public boolean isGetPssDump;
        public boolean isStateChanged;
        public boolean isWarmUpCycle;
        public long lastUpdateTime;
        public Map launchedAndKilledPackageMap;
        public LmkdCounter lmkdCounter;
        public ArrayList lmkdKilledProcessInfo;
        public LmkdState lmkdState;
        public int maxSwap;
        public KpmState nextKpmState;
        public int peakLmkdKillAdj;
        public KpmState policyState;
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
        public long[] lmkdStateCount = new long[LmkdState.values().length];
        public long[] lmkdCurrentCount = new long[LmkdCount.values().length];
        public long[] lmkdPrevCount = new long[LmkdCount.values().length];
        public KpmState[] tinyKpmState = new KpmState[10];
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
        public int seedbedTotalCnt = 0;
        public int pickedTotalCnt = 0;
        public int pickedActTotalCnt = 0;
        public long lmkCnt = 0;
        public long lmkdMed = 0;
        public long lmkdCric = 0;
        public long prevLmkCnt = 0;
        public long prevLmkdMed = 0;
        public long prevLmkdCric = 0;

        public int calcHotLaunchRatio(int i, int i2, int i3) {
            int i4 = i2 + i + i3;
            if (i4 > 0) {
                return (int) (((i * 100.0d) / i4) + 0.5d);
            }
            return 0;
        }

        public int calcKillRatio(int i, int i2) {
            if (i > 0) {
                return (int) (((i2 * 100.0d) / i) + 0.5d);
            }
            return 0;
        }

        public KpmRaw(int i) {
            this.cycleNum = i;
            int i2 = 0;
            initPrevKillInfo();
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
                long[] jArr = this.lmkdStateCount;
                if (i3 >= jArr.length) {
                    break;
                }
                jArr[i3] = 0;
                i3++;
            }
            int i4 = 0;
            while (true) {
                long[] jArr2 = this.lmkdCurrentCount;
                if (i4 >= jArr2.length) {
                    break;
                }
                jArr2[i4] = 0;
                i4++;
            }
            initLmkdPrevCount();
            this.lmkdCounter = KillPolicyManager.this.new LmkdCounter(this, KillPolicyManager.this.mLmkdReader);
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

        public void updateProcessStateInfo(String str, int i) {
            this.appCnt++;
            addLaunchedPackage(KillPolicyManager.this.mPrevPackage);
            this.lmkdCounter.getLmkdPreviousAndBServiceKillCount(KillPolicyManager.this.mLmkdReader);
            updateLaunchState(str, i);
            updateRawProcessCount();
            updateRawResetStatus();
            if (KillPolicyManager.KPM_DEBUG && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("updateProcessStateInfo() appCnt - ");
                sb.append(this.appCnt);
                sb.append(" launch total - ");
                sb.append(this.hotCount + this.warmCount + this.coldCount);
                sb.append(" hot launch(");
                sb.append(this.hotRatio);
                sb.append("%) hot - ");
                sb.append(this.hotCount);
                sb.append(" warm - ");
                sb.append(this.warmCount);
                sb.append(" cold - ");
                sb.append(this.coldCount);
                Slog.d("ActivityManager_kpm", sb.toString());
                sb.setLength(0);
                sb.append("cur previousKillRatio = ");
                sb.append(this.prKilledRatio);
                sb.append("%, serviceKillRatio = ");
                sb.append(this.svKilledRatio);
                sb.append("%, killedPackageRatio = ");
                sb.append(this.killedPackageRatio);
                sb.append("%");
                Slog.d("ActivityManager_kpm", sb.toString());
                sb.setLength(0);
                sb.append("updateRawProcessCount() Average Cached (T:");
                sb.append((int) ((this.cachedTotalCnt / this.appCnt) + 0.5d));
                sb.append("/A:");
                sb.append((int) ((this.cachedActTotalCnt / this.appCnt) + 0.5d));
                sb.append("), Picked (T:");
                sb.append((int) ((this.pickedTotalCnt / this.appCnt) + 0.5d));
                sb.append("/A:");
                sb.append((int) ((this.pickedActTotalCnt / this.appCnt) + 0.5d));
                sb.append("), Seed ");
                sb.append((int) ((this.seedbedTotalCnt / this.appCnt) + 0.5d));
                Slog.d("ActivityManager_kpm", sb.toString());
            }
        }

        public void updateMemInfo(ProcMemInfo procMemInfo) {
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

        public void updatePsiInfo(double d, double d2, double d3) {
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
            if (KillPolicyManager.KPM_DEBUG && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                Slog.d("ActivityManager_kpm", "Psi info cpu : " + d + ", memory : " + d2 + ", io : " + d3);
            }
        }

        public void initPrevKillInfo() {
            if (DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK) {
                this.prevLmkdCric = ProcFileInfoGetter.getLMKDCricCountInfo();
                this.prevLmkdMed = ProcFileInfoGetter.getLMKDCountInfo() - this.prevLmkdCric;
            }
            this.prevLmkCnt = ProcFileInfoGetter.getLMKCountInfo();
        }

        public void updateKillInfo() {
            if (DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK) {
                long lMKDCricCountInfo = ProcFileInfoGetter.getLMKDCricCountInfo();
                long lMKDCountInfo = ProcFileInfoGetter.getLMKDCountInfo() - lMKDCricCountInfo;
                this.lmkdCric = lMKDCricCountInfo - this.prevLmkdCric;
                this.lmkdMed = lMKDCountInfo - this.prevLmkdMed;
            }
            this.lmkCnt = ProcFileInfoGetter.getLMKCountInfo() - this.prevLmkCnt;
        }

        public void updateCycleInfo() {
            updateKillInfo();
            if (KillPolicyManager.this.mPolicyMetric != 1) {
                if (!KillPolicyManager.KPM_BTIME_ENABLE) {
                    this.nextKpmState = KillPolicyManager.this.changeState(this);
                } else if (((System.currentTimeMillis() - KillPolicyManager.this.mKpmStartTime) / 1000) / 60 >= 180) {
                    this.nextKpmState = KillPolicyManager.this.changeState(this);
                } else {
                    this.nextKpmState = KillPolicyManager.this.mCurrentState;
                }
            }
            this.timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            this.lastUpdateTime = System.currentTimeMillis();
            this.uptimeMillis = SystemClock.uptimeMillis();
        }

        public void updateBigdataInfo() {
            updateKillInfo();
            this.nextKpmState = KillPolicyManager.this.changeState(this);
            this.timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            this.lastUpdateTime = System.currentTimeMillis();
            this.uptimeMillis = SystemClock.uptimeMillis();
        }

        public final void initLmkdPrevCount() {
            for (LmkdCount lmkdCount : LmkdCount.values()) {
                Integer lmkdKillCount = ProcessList.getLmkdKillCount(0, lmkdCount.getADJ());
                if (lmkdKillCount != null) {
                    this.lmkdPrevCount[lmkdCount.ordinal()] = lmkdKillCount.longValue();
                }
            }
        }

        public final void dumpLmkdCount() {
            for (LmkdCount lmkdCount : LmkdCount.values()) {
                Integer lmkdKillCount = ProcessList.getLmkdKillCount(0, lmkdCount.getADJ());
                if (lmkdKillCount != null) {
                    this.lmkdCurrentCount[lmkdCount.ordinal()] = lmkdKillCount.longValue() - this.lmkdPrevCount[lmkdCount.ordinal()];
                    if (KillPolicyManager.KPM_DEBUG) {
                        Slog.i("ActivityManager_kpm", "kills at or below oom_adj " + lmkdCount.getADJ() + ": " + this.lmkdCurrentCount[lmkdCount.ordinal()]);
                    }
                }
            }
        }

        public final void updateLmkdStateAndCount(LmkdState lmkdState) {
            if (this.lmkdState.getLevel() < lmkdState.getLevel()) {
                this.lmkdState = lmkdState;
            }
            long[] jArr = this.lmkdStateCount;
            int ordinal = lmkdState.ordinal();
            jArr[ordinal] = jArr[ordinal] + 1;
        }

        public final void checkMemCriticalLowTH() {
            int i = this.prKilledRatio;
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "checkMemCriticalLowTH() killRatio = " + i + "%, killedPackageRatio = " + this.killedPackageRatio + "%");
            }
            int i2 = 0;
            if (i >= KillPolicyManager.MEMORY_CRITICAL_LOW_PROCESS_KILL_RATIO_TH) {
                if (!KillPolicyManager.MEMORY_CRITICAL_LOW_USE_PACKAGE_RATIO || KillPolicyManager.this.isCriticalKilledManyPakages(this.killedPackageRatio)) {
                    i2 = 1;
                } else if (KillPolicyManager.KPM_DEBUG) {
                    Slog.d("ActivityManager_kpm", "checkMemCriticalLowTH() killRatio is over thrshold but not occurred many apps, so result not accepted");
                }
            }
            this.resetStatus = i2;
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "checkMemCriticalLowTH() memory critical status is " + this.resetStatus);
            }
            if (SystemClock.uptimeMillis() > Duration.ofDays(1L).toMillis() && KillPolicyManager.isDetectCritcialLowEnabled() && !this.isWarmUpCycle) {
                KillPolicyManager.this.reportMemCriticalLow(i2);
            }
            if (KillPolicyManager.mIsChimeraPmmKillTriggered) {
                KpmState kpmState = this.nextKpmState;
                if (kpmState == KpmState.HEAVY || kpmState == KpmState.CRITICAL) {
                    KillPolicyManager.this.mMemoryFloodDetector.calculateMemoryFlood();
                    if (KillPolicyManager.this.mMemoryFloodDetector.isRequiredKernelReset()) {
                        KillPolicyManager.this.reportResetState("KERNEL");
                    } else if (KillPolicyManager.this.mMemoryFloodDetector.isRequiredPlatformReset()) {
                        KillPolicyManager.this.reportResetState("PLATFORM");
                    }
                }
            }
        }

        public long getElapsedTimeMin() {
            return ((this.lastUpdateTime - this.createTime) / 1000) / 60;
        }

        public final void updateRawProcessCount() {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < KillPolicyManager.this.mAm.mProcessList.getLruProcessesLOSP().size(); i6++) {
                try {
                    try {
                        ProcessRecord processRecord = (ProcessRecord) KillPolicyManager.this.mAm.mProcessList.getLruProcessesLOSP().get(i6);
                        if (processRecord != null) {
                            int curAdj = processRecord.mState.getCurAdj();
                            if (curAdj >= 900 && curAdj <= 999) {
                                i++;
                            }
                            if (curAdj == 850) {
                                i2++;
                            }
                            if (curAdj == 860) {
                                i3++;
                            }
                            if (processRecord.hasActivities() && processRecord.mState.getCurProcState() == 16) {
                                if (curAdj == 850) {
                                    i5++;
                                } else {
                                    i4++;
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
            this.cachedTotalCnt += i;
            this.seedbedTotalCnt += i3;
            this.pickedTotalCnt += i2;
            this.cachedActTotalCnt += i4;
            this.pickedActTotalCnt += i5;
            this.currentCachedActCnt = i4;
        }

        public final void updateLaunchState(String str, int i) {
            String str2 = "";
            if (i == 9) {
                this.hotCount++;
                str2 = "HOT(" + Integer.toString(i) + ")";
            } else if (i == 8) {
                this.warmCount++;
                str2 = "WARM(" + Integer.toString(i) + ")";
            } else if (i == 7) {
                this.coldCount++;
                str2 = "COLD(" + Integer.toString(i) + ")";
            }
            updateHotLaunchRatio();
            if (KillPolicyManager.KPM_DEBUG && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                Slog.d("ActivityManager_kpm", "updateLaunchState() pkg " + str + " type " + str2);
            }
        }

        public final void updateHotLaunchRatio() {
            this.hotRatio = calcHotLaunchRatio(this.hotCount, this.warmCount, this.coldCount);
        }

        public final void addLaunchedPackage(String str) {
            if (str == null || this.launchedAndKilledPackageMap.containsKey(str)) {
                return;
            }
            this.launchedAndKilledPackageMap.put(str, 1000);
            if (KillPolicyManager.KPM_DEBUG && this == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                Slog.d("ActivityManager_kpm", "add addLaunchedPackage : " + str + ", cur count : " + this.launchedAndKilledPackageMap.size());
            }
        }

        public final void markKilledPackage(String str, int i) {
            if (str == null || !this.launchedAndKilledPackageMap.containsKey(str) || ((Integer) this.launchedAndKilledPackageMap.get(str)).intValue() <= i) {
                return;
            }
            this.launchedAndKilledPackageMap.put(str, Integer.valueOf(i));
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.d("ActivityManager_kpm", "markKilledPackage : " + str + ", adj " + i + ", killed adj : " + this.launchedAndKilledPackageMap.get(str));
            }
        }

        public final int getLaunchedPackageCount() {
            return this.launchedAndKilledPackageMap.size();
        }

        public final int getKilledPackageCountOfAdj(int i) {
            Iterator it = this.launchedAndKilledPackageMap.keySet().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (((Integer) this.launchedAndKilledPackageMap.get((String) it.next())).intValue() <= i) {
                    i2++;
                }
            }
            return i2;
        }

        public final void updateRawResetStatus() {
            this.prKilledRatio = calcKillRatio(this.appCnt, this.lmkdCounter.previousKillOccurredCount);
            this.svKilledRatio = calcKillRatio(this.appCnt, this.lmkdCounter.bServiceKillOccurredCount);
            this.launchedPackageCount = getLaunchedPackageCount();
            int killedPackageCountOfAdj = getKilledPackageCountOfAdj(700);
            this.killedPackageCount = killedPackageCountOfAdj;
            this.killedPackageRatio = KillPolicyManager.this.calcKilledPackageRatio(killedPackageCountOfAdj, this.launchedPackageCount);
        }

        public final void updateProcAdjPss(HashMap hashMap) {
            this.procsAdjPss = hashMap;
        }

        public final void updateProcessHeavyPssList(ArrayList arrayList, ProcMemInfo procMemInfo, int i) {
            this.dumpHeavyProcessList = arrayList;
            this.isGetPssDump = true;
            this.peakLmkdKillAdj = i;
            this.procMemInfo = procMemInfo;
        }

        public final void updateMemoryUsageByAdj(HashMap hashMap) {
            this.dumpHeavyMemoryUsageByAdj = hashMap;
        }

        public StringBuilder getKpmData(int i) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(" ");
            int i2 = this.cycleNum;
            if (i2 == i) {
                return sb;
            }
            if (this.appCnt == 0) {
                sb.append("appCnt is 0");
                return sb;
            }
            sb.append(i2);
            sb.append(",");
            sb.append((int) ((this.accMem / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append((int) ((this.avlMem / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append((int) ((this.accSwap / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append(this.hotCount);
            sb.append(",");
            sb.append(this.lmkdCounter.previousKillOccurredCount);
            sb.append(",");
            sb.append(this.lmkdCounter.bServiceKillOccurredCount);
            sb.append(",");
            sb.append(this.launchedPackageCount);
            sb.append(",");
            sb.append(this.killedPackageCount);
            sb.append(",");
            sb.append(this.lmkdCounter.cycleTotalKillCount);
            sb.append(",");
            sb.append(this.lmkdCounter.cyclePreviousKillCount);
            sb.append(",");
            sb.append(this.lmkdCounter.cycleBServiceKillCount);
            sb.append(",");
            sb.append(this.lmkdCounter.cyclePickedKillCount);
            sb.append(",");
            sb.append(this.lmkdCounter.cycleSeedbedKillCount);
            sb.append(",");
            sb.append(this.lmkdCric);
            sb.append(",");
            sb.append(this.lmkCnt);
            sb.append(",");
            sb.append(this.cachedNormalKillCount);
            sb.append(",");
            sb.append(this.cachedEmptyKillCount);
            sb.append(",");
            sb.append(this.curKpmState.ordinal());
            sb.append(",");
            sb.append(this.nextKpmState.ordinal());
            sb.append(",");
            sb.append(this.policyState.ordinal());
            sb.append(",");
            sb.append((int) ((this.psiCpuSum / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append((int) ((this.psiMemorySum / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append((int) ((this.psiIoSum / this.appCnt) + 0.5d));
            sb.append(",");
            sb.append((int) (this.psiCpuMax + 0.5d));
            sb.append(",");
            sb.append((int) (this.psiMemoryMax + 0.5d));
            sb.append(",");
            sb.append((int) (this.psiIoMax + 0.5d));
            sb.append(",");
            sb.append(this.resetStatus);
            sb.append(",");
            sb.append(this.lmkdState.getLevel());
            sb.append(",[");
            sb.append(this.lmkdStateCount[0]);
            for (int i3 = 1; i3 < this.lmkdStateCount.length; i3++) {
                sb.append(" ");
                sb.append(this.lmkdStateCount[i3]);
            }
            sb.append("],[");
            sb.append(this.lmkdCurrentCount[0]);
            for (int i4 = 1; i4 < this.lmkdCurrentCount.length; i4++) {
                sb.append(" ");
                sb.append(this.lmkdCurrentCount[i4]);
            }
            sb.append("],[");
            sb.append(this.tinyKpmState[0].ordinal());
            for (int i5 = 1; i5 < this.tinyKpmState.length; i5++) {
                sb.append(" ");
                sb.append(this.tinyKpmState[i5].ordinal());
            }
            sb.append("],");
            sb.append(this.timeStamp);
            return sb;
        }

        public StringBuilder getKpmHeavyPssData(int i) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(" ");
            sb.append(this.cycleNum);
            if (this.dumpHeavyProcessList == null) {
                sb.append(",didn't happen Previous App Kill by LMKD");
            } else {
                sb.append(",");
                sb.append(this.peakLmkdKillAdj);
                if (this.procMemInfo != null) {
                    sb.append(",[");
                    sb.append(this.procMemInfo.memTotal);
                    sb.append(" ");
                    sb.append(this.procMemInfo.memFree);
                    sb.append(" ");
                    sb.append(this.procMemInfo.memAvailable);
                    sb.append(" ");
                    sb.append(this.procMemInfo.cached);
                    sb.append(" ");
                    sb.append(this.procMemInfo.activeFile);
                    sb.append(" ");
                    sb.append(this.procMemInfo.inactiveFile);
                    sb.append(" ");
                    sb.append(this.procMemInfo.rbinFree);
                    sb.append(" ");
                    sb.append(this.procMemInfo.rbinCached);
                    sb.append(" ");
                    sb.append(KillPolicyManager.this.mTotalSwap);
                    sb.append(" ");
                    sb.append(this.procMemInfo.swapFree);
                    sb.append(" ");
                    sb.append(this.procMemInfo.kReclaimable);
                    sb.append(" ");
                    sb.append(this.procMemInfo.sReclaimable);
                    sb.append(" ");
                    sb.append(this.procMemInfo.sUnreclaim);
                    sb.append(" ");
                    sb.append(0);
                    sb.append(" ");
                    sb.append(this.procMemInfo.gpuSwap);
                    sb.append(" ");
                    sb.append(this.procMemInfo.systemCached);
                    sb.append(" ");
                    sb.append(this.procMemInfo.zram);
                    sb.append(" ");
                    sb.append(this.procMemInfo.gpuTotal);
                    sb.append(" ");
                    sb.append(this.procMemInfo.vmallocUsed);
                    sb.append(" ");
                    sb.append(this.procMemInfo.systemUncached);
                    sb.append("]");
                } else {
                    sb.append(", [null]");
                }
                if (this.dumpHeavyMemoryUsageByAdj != null) {
                    sb.append(",[");
                    for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                        if (this.dumpHeavyMemoryUsageByAdj.containsKey(str)) {
                            long[] jArr = (long[]) this.dumpHeavyMemoryUsageByAdj.get(str);
                            sb.append("{");
                            Objects.requireNonNull(KillPolicyManager.this.mProcessHeavyMemory);
                            sb.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                            sb.append(" ");
                            Objects.requireNonNull(KillPolicyManager.this.mProcessHeavyMemory);
                            sb.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                            sb.append(" ");
                            Objects.requireNonNull(KillPolicyManager.this.mProcessHeavyMemory);
                            sb.append((int) ((jArr[2] / 1024.0d) + 0.5d));
                            sb.append("}");
                        } else {
                            sb.append("{");
                            sb.append(-1);
                            sb.append(" ");
                            sb.append(-1);
                            sb.append(" ");
                            sb.append(-1);
                            sb.append("}");
                        }
                    }
                    sb.append("]");
                } else {
                    sb.append(", [null]");
                }
                for (int i2 = 0; i2 < this.dumpHeavyProcessList.size(); i2++) {
                    DumpHeavyProcessInfo dumpHeavyProcessInfo = (DumpHeavyProcessInfo) this.dumpHeavyProcessList.get(i2);
                    sb.append(",[");
                    sb.append(dumpHeavyProcessInfo.memoryInfo.procName);
                    sb.append(", ver: ");
                    sb.append(dumpHeavyProcessInfo.processVersion);
                    if (dumpHeavyProcessInfo.packageName != null) {
                        sb.append(", PackageName: ");
                        sb.append(dumpHeavyProcessInfo.packageName);
                    } else {
                        sb.append(", PackageName: ");
                        sb.append("null");
                    }
                    if (dumpHeavyProcessInfo.packageVersion != null) {
                        sb.append(", PackageVer: ");
                        sb.append(dumpHeavyProcessInfo.packageVersion);
                    } else {
                        sb.append(", PackageVer: ");
                        sb.append("null");
                    }
                    sb.append(", adj: ");
                    sb.append(dumpHeavyProcessInfo.memoryInfo.label);
                    sb.append(", pss: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d));
                    sb.append(" mb, swap: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d));
                    sb.append(" mb, rss: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                    sb.append(" mb, ");
                    sb.append(KillPolicyManager.this.mProcessHeavyMemory.dumpPssUssRssToString(dumpHeavyProcessInfo.pssUssRssInfo));
                    sb.append("]");
                }
            }
            return sb;
        }
    }

    public class LmkdCountReader {
        public long totalCountOfLmkd = 0;
        public long cachedMinCountOfLmkd = 0;
        public long seedbedCountOfLmkd = 0;
        public long pickedCountOfLmkd = 0;
        public long bServiceCountOfLmkd = 0;
        public long previousCountOfLmkd = 0;
        public long homeToPercCountOfLmkd = 0;
        public long visToFgCountOfLmkd = 0;

        public LmkdCountReader() {
        }

        public final long getKillCountForAdjRange(int i, int i2, long j) {
            Integer lmkdKillCount = ProcessList.getLmkdKillCount(i, i2);
            if (lmkdKillCount == null) {
                Slog.e("ActivityManager", "getKillCountForAdjRange() - getLmkdKillCount returns null");
                return j;
            }
            return lmkdKillCount.longValue();
        }

        public void readLmkdKillCount() {
            this.totalCountOfLmkd = getKillCountForAdjRange(0, 999, this.totalCountOfLmkd);
            this.cachedMinCountOfLmkd = getKillCountForAdjRange(900, 900, this.cachedMinCountOfLmkd);
            this.seedbedCountOfLmkd = getKillCountForAdjRange(851, 860, this.seedbedCountOfLmkd);
            this.pickedCountOfLmkd = getKillCountForAdjRange(801, 850, this.pickedCountOfLmkd);
            this.bServiceCountOfLmkd = getKillCountForAdjRange(701, 800, this.bServiceCountOfLmkd);
            this.previousCountOfLmkd = getKillCountForAdjRange(601, 700, this.previousCountOfLmkd);
            this.homeToPercCountOfLmkd = getKillCountForAdjRange(200, 600, this.homeToPercCountOfLmkd);
            this.visToFgCountOfLmkd = getKillCountForAdjRange(0, FrameworkStatsLog.f690xa4f1b8b3, this.visToFgCountOfLmkd);
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("readLmkdKillCount() : (E : ");
                sb.append(this.totalCountOfLmkd);
                sb.append(", Pr : ");
                sb.append(this.previousCountOfLmkd);
                sb.append(", Sv : ");
                sb.append(this.bServiceCountOfLmkd);
                sb.append(", Pi : ");
                sb.append(this.pickedCountOfLmkd);
                sb.append(", Sb : ");
                sb.append(this.seedbedCountOfLmkd);
                sb.append(", Cm : ");
                sb.append(this.cachedMinCountOfLmkd);
                sb.append(")");
                Slog.d("ActivityManager_kpm", sb.toString());
            }
        }
    }

    public class LmkdCounter {
        public int bServiceKillOccurredCount;
        public KpmRaw kpmRaw;
        public long prevBServiceCountOfLmkd;
        public long prevCachedMinCountOfLmkd;
        public long prevCycleBServiceCountOfLmkd;
        public long prevCycleCachedMinCountOfLmkd;
        public long prevCycleHomeToPercCountOfLmkd;
        public long prevCyclePickedCountOfLmkd;
        public long prevCyclePreviousCountOfLmkd;
        public long prevCycleSeedbedCountOfLmkd;
        public long prevCycleTotalCountOfLmkd;
        public long prevCycleVisToFgCountOfLmkd;
        public long prevPickedCountOfLmkd;
        public long prevPreviousCountOfLmkd;
        public long prevSeedbedCountOfLmkd;
        public long prevTinyCycleBServiceCountOfLmkd;
        public long prevTinyCycleCachedMinCountOfLmkd;
        public long prevTinyCyclePickedCountOfLmkd;
        public long prevTinyCyclePreviousCountOfLmkd;
        public long prevTinyCycleSeedbedCountOfLmkd;
        public long prevTotalCountOfLmkd;
        public int previousKillOccurredCount;
        public long tinyCycleBServiceKillCount;
        public long tinyCycleCachedMinKillCount;
        public long tinyCyclePickedKillCount;
        public long tinyCyclePreviousKillCount;
        public long tinyCycleSeedbedKillCount;
        public long cycleTotalKillCount = 0;
        public long cycleCachedMinKillCount = 0;
        public long cycleSeedbedKillCount = 0;
        public long cyclePickedKillCount = 0;
        public long cycleBServiceKillCount = 0;
        public long cyclePreviousKillCount = 0;
        public long cycleHomeToPercKillCount = 0;
        public long cycleVisToFgKillCount = 0;
        public long totalKillCount = 0;
        public long cachedMinKillCount = 0;
        public long seedbedKillCount = 0;
        public long pickedKillCount = 0;
        public long bServiceKillCount = 0;
        public long previousKillCount = 0;

        public LmkdCounter(KpmRaw kpmRaw, LmkdCountReader lmkdCountReader) {
            long j = lmkdCountReader.totalCountOfLmkd;
            this.prevCycleTotalCountOfLmkd = j;
            long j2 = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevCycleCachedMinCountOfLmkd = j2;
            long j3 = lmkdCountReader.seedbedCountOfLmkd;
            this.prevCycleSeedbedCountOfLmkd = j3;
            long j4 = lmkdCountReader.pickedCountOfLmkd;
            this.prevCyclePickedCountOfLmkd = j4;
            long j5 = lmkdCountReader.bServiceCountOfLmkd;
            this.prevCycleBServiceCountOfLmkd = j5;
            long j6 = lmkdCountReader.previousCountOfLmkd;
            this.prevCyclePreviousCountOfLmkd = j6;
            this.prevCycleHomeToPercCountOfLmkd = lmkdCountReader.homeToPercCountOfLmkd;
            this.prevCycleVisToFgCountOfLmkd = lmkdCountReader.visToFgCountOfLmkd;
            this.prevTotalCountOfLmkd = j;
            this.prevCachedMinCountOfLmkd = j2;
            this.prevSeedbedCountOfLmkd = j3;
            this.prevPickedCountOfLmkd = j4;
            this.prevBServiceCountOfLmkd = j5;
            this.prevPreviousCountOfLmkd = j6;
            this.previousKillOccurredCount = 0;
            this.bServiceKillOccurredCount = 0;
            this.tinyCycleCachedMinKillCount = 0L;
            this.tinyCycleSeedbedKillCount = 0L;
            this.tinyCyclePickedKillCount = 0L;
            this.tinyCycleBServiceKillCount = 0L;
            this.tinyCyclePreviousKillCount = 0L;
            this.prevTinyCycleCachedMinCountOfLmkd = j2;
            this.prevTinyCycleSeedbedCountOfLmkd = j3;
            this.prevTinyCyclePickedCountOfLmkd = j4;
            this.prevTinyCycleBServiceCountOfLmkd = j5;
            this.prevTinyCyclePreviousCountOfLmkd = j6;
            this.kpmRaw = kpmRaw;
        }

        public void getCycleLmkdKillCountByADJ(LmkdCountReader lmkdCountReader) {
            this.cycleTotalKillCount = lmkdCountReader.totalCountOfLmkd - this.prevCycleTotalCountOfLmkd;
            this.cycleCachedMinKillCount = lmkdCountReader.cachedMinCountOfLmkd - this.prevCycleCachedMinCountOfLmkd;
            this.cycleSeedbedKillCount = lmkdCountReader.seedbedCountOfLmkd - this.prevCycleSeedbedCountOfLmkd;
            this.cyclePickedKillCount = lmkdCountReader.pickedCountOfLmkd - this.prevCyclePickedCountOfLmkd;
            this.cycleBServiceKillCount = lmkdCountReader.bServiceCountOfLmkd - this.prevCycleBServiceCountOfLmkd;
            this.cyclePreviousKillCount = lmkdCountReader.previousCountOfLmkd - this.prevCyclePreviousCountOfLmkd;
            this.cycleHomeToPercKillCount = lmkdCountReader.homeToPercCountOfLmkd - this.prevCycleHomeToPercCountOfLmkd;
            this.cycleVisToFgKillCount = lmkdCountReader.visToFgCountOfLmkd - this.prevCycleVisToFgCountOfLmkd;
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("Cycle currentKillCountLmkd : (cE : ");
                sb.append(this.cycleTotalKillCount);
                sb.append(", cPr : ");
                sb.append(this.cyclePreviousKillCount);
                sb.append(", cSv : ");
                sb.append(this.cycleBServiceKillCount);
                sb.append(", cPi : ");
                sb.append(this.cyclePickedKillCount);
                sb.append(", cSb : ");
                sb.append(this.cycleSeedbedKillCount);
                sb.append(", cCm : ");
                sb.append(this.cycleCachedMinKillCount);
                sb.append(")");
                Slog.d("ActivityManager_kpm", sb.toString());
            }
            this.prevCycleTotalCountOfLmkd = lmkdCountReader.totalCountOfLmkd;
            this.prevCycleCachedMinCountOfLmkd = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevCycleSeedbedCountOfLmkd = lmkdCountReader.seedbedCountOfLmkd;
            this.prevCyclePickedCountOfLmkd = lmkdCountReader.pickedCountOfLmkd;
            this.prevCycleBServiceCountOfLmkd = lmkdCountReader.bServiceCountOfLmkd;
            this.prevCyclePreviousCountOfLmkd = lmkdCountReader.previousCountOfLmkd;
            this.prevCycleHomeToPercCountOfLmkd = lmkdCountReader.homeToPercCountOfLmkd;
            this.prevCycleVisToFgCountOfLmkd = lmkdCountReader.visToFgCountOfLmkd;
        }

        public void getLmkdPreviousAndBServiceKillCount(LmkdCountReader lmkdCountReader) {
            this.totalKillCount = lmkdCountReader.totalCountOfLmkd - this.prevTotalCountOfLmkd;
            this.cachedMinKillCount = lmkdCountReader.cachedMinCountOfLmkd - this.prevCachedMinCountOfLmkd;
            this.seedbedKillCount = lmkdCountReader.seedbedCountOfLmkd - this.prevSeedbedCountOfLmkd;
            this.pickedKillCount = lmkdCountReader.pickedCountOfLmkd - this.prevPickedCountOfLmkd;
            this.bServiceKillCount = lmkdCountReader.bServiceCountOfLmkd - this.prevBServiceCountOfLmkd;
            long j = lmkdCountReader.previousCountOfLmkd - this.prevPreviousCountOfLmkd;
            this.previousKillCount = j;
            if (j > 0) {
                this.previousKillOccurredCount++;
                KpmRaw kpmRaw = this.kpmRaw;
                if (kpmRaw != null) {
                    kpmRaw.markKilledPackage(KillPolicyManager.this.mPrevPackage, 700);
                }
            }
            if (this.bServiceKillCount > 0) {
                this.bServiceKillOccurredCount++;
                KpmRaw kpmRaw2 = this.kpmRaw;
                if (kpmRaw2 != null) {
                    kpmRaw2.markKilledPackage(KillPolicyManager.this.mPrevPackage, 800);
                }
            }
            if (KillPolicyManager.KPM_DEBUG && this.kpmRaw == KillPolicyManager.this.getCurrentKpmRawPolicy()) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("prevKillCount : (pE : ");
                sb.append(this.prevTotalCountOfLmkd);
                sb.append(", pPr : ");
                sb.append(this.prevPreviousCountOfLmkd);
                sb.append(", pSv : ");
                sb.append(this.prevBServiceCountOfLmkd);
                sb.append(", pPi : ");
                sb.append(this.prevPickedCountOfLmkd);
                sb.append(", pSb : ");
                sb.append(this.prevSeedbedCountOfLmkd);
                sb.append(", pCm : ");
                sb.append(this.prevCachedMinCountOfLmkd);
                sb.append(")");
                Slog.d("ActivityManager_kpm", sb.toString());
                sb.setLength(0);
                sb.append("currentKillCount : (cE : ");
                sb.append(this.totalKillCount);
                sb.append(", cPr : ");
                sb.append(this.previousKillCount);
                sb.append(", cSv : ");
                sb.append(this.bServiceKillCount);
                sb.append(", cPi : ");
                sb.append(this.pickedKillCount);
                sb.append(", cSb : ");
                sb.append(this.seedbedKillCount);
                sb.append(", cCm : ");
                sb.append(this.cachedMinKillCount);
                sb.append(")");
                Slog.d("ActivityManager_kpm", sb.toString());
            }
            this.prevTotalCountOfLmkd = lmkdCountReader.totalCountOfLmkd;
            this.prevCachedMinCountOfLmkd = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevSeedbedCountOfLmkd = lmkdCountReader.seedbedCountOfLmkd;
            this.prevPickedCountOfLmkd = lmkdCountReader.pickedCountOfLmkd;
            this.prevBServiceCountOfLmkd = lmkdCountReader.bServiceCountOfLmkd;
            this.prevPreviousCountOfLmkd = lmkdCountReader.previousCountOfLmkd;
        }

        public void fillTinyCycleLmkdKillCountByADJ(LmkdCountReader lmkdCountReader) {
            this.tinyCycleCachedMinKillCount = lmkdCountReader.cachedMinCountOfLmkd - this.prevTinyCycleCachedMinCountOfLmkd;
            this.tinyCycleSeedbedKillCount = lmkdCountReader.seedbedCountOfLmkd - this.prevTinyCycleSeedbedCountOfLmkd;
            this.tinyCyclePickedKillCount = lmkdCountReader.pickedCountOfLmkd - this.prevTinyCyclePickedCountOfLmkd;
            this.tinyCycleBServiceKillCount = lmkdCountReader.bServiceCountOfLmkd - this.prevTinyCycleBServiceCountOfLmkd;
            this.tinyCyclePreviousKillCount = lmkdCountReader.previousCountOfLmkd - this.prevTinyCyclePreviousCountOfLmkd;
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("TinyCycle currentKillCountLmkd : (cPr : ");
                sb.append(this.tinyCyclePreviousKillCount);
                sb.append(", cSv : ");
                sb.append(this.tinyCycleBServiceKillCount);
                sb.append(", cPi : ");
                sb.append(this.tinyCyclePickedKillCount);
                sb.append(", cSb : ");
                sb.append(this.tinyCycleSeedbedKillCount);
                sb.append(", cCm : ");
                sb.append(this.tinyCycleCachedMinKillCount);
                sb.append(")");
                Slog.d("ActivityManager_kpm", sb.toString());
            }
            this.prevTinyCycleCachedMinCountOfLmkd = lmkdCountReader.cachedMinCountOfLmkd;
            this.prevTinyCycleSeedbedCountOfLmkd = lmkdCountReader.seedbedCountOfLmkd;
            this.prevTinyCyclePickedCountOfLmkd = lmkdCountReader.pickedCountOfLmkd;
            this.prevTinyCycleBServiceCountOfLmkd = lmkdCountReader.bServiceCountOfLmkd;
            this.prevTinyCyclePreviousCountOfLmkd = lmkdCountReader.previousCountOfLmkd;
        }
    }

    public class DumpPssUssRssInfo {
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

        public DumpPssUssRssInfo() {
        }
    }

    public class DumpHeavyProcessInfo {
        public ActivityManagerService.MemDumpInfo memoryInfo;
        public String packageName;
        public String packageVersion;
        public long processVersion;
        public ArrayList pssUssRssInfo;

        public DumpHeavyProcessInfo() {
        }
    }

    public class ProcessMemoryHeavyInfo {
        public Thread mProcessMemoryDumpThread;
        public final int THREAD_SLEEP_TIME = 3000;
        public final int THRESHOLD_HEAVY_MEMORY_DETECT = 512000;
        public final int HEAVY_MEMORY_MAX_LIST = 5;
        public final boolean isThreshold = false;
        public final int PROCESS_PSS = 0;
        public final int PROCESS_SWAP_PSS = 1;
        public final int PROCESS_RSS = 2;
        public boolean isThreadRunning = false;
        public int peakLmkdKillAdj = 1001;
        public List procMemDumpInfoList = new ArrayList();
        public ArrayList dumpHeavyProcessList = new ArrayList();
        public ArrayList fgsMemDumpList = new ArrayList();
        public ProcMemInfo procMemInfo = null;
        public HashMap memoryUsageByAdj = new HashMap();
        public HashMap dumpHeavyProcessByAdj = new HashMap();

        public ProcessMemoryHeavyInfo() {
        }

        public void setPeakLmkdKillLevel(int i) {
            if (this.peakLmkdKillAdj <= i || i == -10000) {
                return;
            }
            this.peakLmkdKillAdj = i;
        }

        public class PssComparator implements Comparator {
            public PssComparator() {
            }

            @Override // java.util.Comparator
            public int compare(ActivityManagerService.MemDumpInfo memDumpInfo, ActivityManagerService.MemDumpInfo memDumpInfo2) {
                long j = memDumpInfo.pss + memDumpInfo.swap_out;
                long j2 = memDumpInfo2.pss + memDumpInfo2.swap_out;
                if (j < j2) {
                    return 1;
                }
                return j > j2 ? -1 : 0;
            }
        }

        public String dumpPssUssRssToString(ArrayList arrayList) {
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
                String str = DumpUtils.ADJ_SCREEN_NAMES_CSV[dumpPssUssRssInfo.screenIndex] + "_" + DumpUtils.ADJ_MEM_NAMES_CSV[dumpPssUssRssInfo.memoryFactorIndex] + "_" + DumpUtils.STATE_NAMES_CSV[dumpPssUssRssInfo.stateIndex];
                if (z) {
                    z = false;
                } else {
                    sb.append(",");
                }
                sb.append(str);
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append(dumpPssUssRssInfo.sampleCount);
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.minPss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.avgPss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.maxPss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.minUss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.avgUss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.maxUss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.minRss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.avgRss / 1024.0d) + 0.5d));
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                sb.append((int) ((dumpPssUssRssInfo.maxRss / 1024.0d) + 0.5d));
            }
            return sb.toString();
        }

        public final ArrayList getPssUssRssInfo(ProcessState processState) {
            ArrayList arrayList = new ArrayList();
            if (processState == null) {
                return null;
            }
            for (int i = 0; i < ProcessStats.ALL_SCREEN_ADJ.length; i++) {
                for (int i2 = 0; i2 < ProcessStats.ALL_MEM_ADJ.length; i2++) {
                    for (int i3 = 0; i3 < ProcessStats.ALL_PROC_STATES.length; i3++) {
                        int i4 = ((ProcessStats.ALL_SCREEN_ADJ[i] + ProcessStats.ALL_MEM_ADJ[i2]) * 16) + ProcessStats.ALL_PROC_STATES[i3];
                        long pssSampleCount = processState.getPssSampleCount(i4);
                        if (pssSampleCount > 0) {
                            DumpPssUssRssInfo dumpPssUssRssInfo = KillPolicyManager.this.new DumpPssUssRssInfo();
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
                    }
                }
            }
            if (arrayList.size() == 0) {
                DumpPssUssRssInfo dumpPssUssRssInfo2 = KillPolicyManager.this.new DumpPssUssRssInfo();
                dumpPssUssRssInfo2.sampleCount = -1;
                arrayList.add(dumpPssUssRssInfo2);
            }
            return arrayList;
        }

        public void gatherHeavyProcessThread(final KpmRaw kpmRaw, final KpmRaw kpmRaw2) {
            Thread thread = new Thread("PMMProcessHeavyDumpThread") { // from class: com.android.server.am.KillPolicyManager.ProcessMemoryHeavyInfo.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(3000L);
                        Slog.i("ActivityManager_kpm", "PMM gatherHeavyProcessThread");
                        ProcessMemoryHeavyInfo processMemoryHeavyInfo = ProcessMemoryHeavyInfo.this;
                        processMemoryHeavyInfo.procMemInfo = KillPolicyManager.this.getProcMemInfo();
                        KpmRaw kpmRaw3 = kpmRaw2;
                        if (kpmRaw3 != null && !kpmRaw3.isGetPssDump) {
                            ProcessMemoryHeavyInfo processMemoryHeavyInfo2 = ProcessMemoryHeavyInfo.this;
                            processMemoryHeavyInfo2.getProcessMemoryDumpInformation(true, processMemoryHeavyInfo2.procMemInfo);
                            KpmRaw kpmRaw4 = kpmRaw2;
                            ProcessMemoryHeavyInfo processMemoryHeavyInfo3 = ProcessMemoryHeavyInfo.this;
                            kpmRaw4.updateProcessHeavyPssList(processMemoryHeavyInfo3.dumpHeavyProcessList, processMemoryHeavyInfo3.procMemInfo, processMemoryHeavyInfo3.peakLmkdKillAdj);
                            kpmRaw2.updateMemoryUsageByAdj(ProcessMemoryHeavyInfo.this.memoryUsageByAdj);
                            ProcessMemoryHeavyInfo.this.reportHeavyProcessTop5ByAdj();
                        } else {
                            ProcessMemoryHeavyInfo processMemoryHeavyInfo4 = ProcessMemoryHeavyInfo.this;
                            processMemoryHeavyInfo4.getProcessMemoryDumpInformation(false, processMemoryHeavyInfo4.procMemInfo);
                        }
                        KpmRaw kpmRaw5 = kpmRaw;
                        if (kpmRaw5 != null) {
                            ProcessMemoryHeavyInfo processMemoryHeavyInfo5 = ProcessMemoryHeavyInfo.this;
                            kpmRaw5.updateProcessHeavyPssList(processMemoryHeavyInfo5.dumpHeavyProcessList, processMemoryHeavyInfo5.procMemInfo, processMemoryHeavyInfo5.peakLmkdKillAdj);
                            kpmRaw.updateMemoryUsageByAdj(ProcessMemoryHeavyInfo.this.memoryUsageByAdj);
                        }
                        if (KillPolicyManager.KPM_DEBUG) {
                            Slog.d("ActivityManager_kpm", "ProcessMemoryHeavy peakLmkdKillAdj : " + ProcessMemoryHeavyInfo.this.peakLmkdKillAdj);
                            ProcessMemoryHeavyInfo processMemoryHeavyInfo6 = ProcessMemoryHeavyInfo.this;
                            processMemoryHeavyInfo6.showDebugHeavyProcess(processMemoryHeavyInfo6.dumpHeavyProcessList);
                            ProcessMemoryHeavyInfo.this.showDebugAdjMemory();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ProcessMemoryHeavyInfo.this.isThreadRunning = false;
                }
            };
            this.mProcessMemoryDumpThread = thread;
            this.isThreadRunning = true;
            thread.start();
        }

        public final void fillProcessPackageNameAndVersion(ProcessState processState, DumpHeavyProcessInfo dumpHeavyProcessInfo) {
            if (processState != null) {
                try {
                    if (processState.getPackage() != null) {
                        dumpHeavyProcessInfo.processVersion = processState.getVersion();
                        dumpHeavyProcessInfo.packageName = processState.getPackage();
                        dumpHeavyProcessInfo.packageVersion = KillPolicyManager.this.mContext.getPackageManager().getPackageInfo(processState.getPackage(), 0).versionName;
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

        public final void fillMemoryUsageByAdj(ActivityManagerService.MemDumpInfo memDumpInfo) {
            if (!this.memoryUsageByAdj.containsKey(memDumpInfo.label)) {
                this.memoryUsageByAdj.put(memDumpInfo.label, new long[]{memDumpInfo.pss, memDumpInfo.swap_out, memDumpInfo.rss});
                return;
            }
            long[] jArr = (long[]) this.memoryUsageByAdj.get(memDumpInfo.label);
            jArr[0] = jArr[0] + memDumpInfo.pss;
            jArr[1] = jArr[1] + memDumpInfo.swap_out;
            jArr[2] = jArr[2] + memDumpInfo.rss;
        }

        public final void fillDumpHeavyProcessTop5ByAdj(ActivityManagerService.MemDumpInfo memDumpInfo, ProcessState processState) {
            if (!this.dumpHeavyProcessByAdj.containsKey(memDumpInfo.label)) {
                ArrayList arrayList = new ArrayList();
                this.dumpHeavyProcessByAdj.put(memDumpInfo.label, arrayList);
                DumpHeavyProcessInfo dumpHeavyProcessInfo = KillPolicyManager.this.new DumpHeavyProcessInfo();
                arrayList.add(dumpHeavyProcessInfo);
                dumpHeavyProcessInfo.memoryInfo = memDumpInfo;
                fillProcessPackageNameAndVersion(processState, dumpHeavyProcessInfo);
                dumpHeavyProcessInfo.pssUssRssInfo = getPssUssRssInfo(processState);
                return;
            }
            ArrayList arrayList2 = (ArrayList) this.dumpHeavyProcessByAdj.get(memDumpInfo.label);
            if (arrayList2.size() < 5) {
                DumpHeavyProcessInfo dumpHeavyProcessInfo2 = KillPolicyManager.this.new DumpHeavyProcessInfo();
                arrayList2.add(dumpHeavyProcessInfo2);
                dumpHeavyProcessInfo2.memoryInfo = memDumpInfo;
                fillProcessPackageNameAndVersion(processState, dumpHeavyProcessInfo2);
                dumpHeavyProcessInfo2.pssUssRssInfo = getPssUssRssInfo(processState);
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
            KillPolicyManager.this.sendHqmBigData("KPUT", str);
        }

        public final void reportHeavyProcessTop5ByAdj() {
            String[] strArr;
            int i;
            int i2;
            ArrayList arrayList;
            int i3;
            String str;
            int length;
            HashMap hashMap = this.dumpHeavyProcessByAdj;
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
                    if (!this.dumpHeavyProcessByAdj.containsKey(str2) || (arrayList = (ArrayList) this.dumpHeavyProcessByAdj.get(str2)) == null || arrayList.size() <= 0) {
                        strArr = strArr2;
                        i = length2;
                        i2 = i4;
                    } else {
                        String str3 = str2.toUpperCase() + "TF";
                        JSONArray jSONArray = new JSONArray();
                        int i5 = 0;
                        while (i5 < arrayList.size()) {
                            DumpHeavyProcessInfo dumpHeavyProcessInfo = (DumpHeavyProcessInfo) arrayList.get(i5);
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
                                length = String.valueOf(dumpHeavyProcessInfo.processVersion).length();
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
                                sendHqmHeavyProcessTop5ByAdj(jSONObject, uptimeMillis);
                                jSONObject = new JSONObject();
                            }
                            jSONArray.put(dumpHeavyProcessInfo.memoryInfo.procName);
                            String str5 = dumpHeavyProcessInfo.packageVersion;
                            if (str5 != null) {
                                jSONArray.put(str5);
                            } else {
                                jSONArray.put(String.valueOf(dumpHeavyProcessInfo.processVersion));
                            }
                            jSONArray.put((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d)).put((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d)).put((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
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
            sendHqmHeavyProcessTop5ByAdj(jSONObject, uptimeMillis);
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x0144  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void getProcessMemoryDumpInformation(boolean z, ProcMemInfo procMemInfo) {
            int i;
            DumpHeavyProcessInfo dumpHeavyProcessInfo;
            boolean z2;
            long j;
            ArrayList arrayList;
            long currentTimeMillis = System.currentTimeMillis();
            if (this.procMemInfo != null) {
                i = procMemInfo.memTotal;
            } else {
                i = KillPolicyManager.this.mTotalMem * 1024;
            }
            long j2 = (long) ((i * 1024 * 0.3d) + 0.5d);
            List dumpMemoryInfo = KillPolicyManager.this.mAm.getDumpMemoryInfo(z);
            this.procMemDumpInfoList = dumpMemoryInfo;
            long j3 = 0;
            if (dumpMemoryInfo != null && dumpMemoryInfo.size() > 0) {
                ArrayList arrayList2 = new ArrayList(KillPolicyManager.this.mAm.mProcessList.getLruProcessesLOSP());
                this.procMemDumpInfoList.sort(new PssComparator());
                Iterator it = this.procMemDumpInfoList.iterator();
                while (it.hasNext()) {
                    ActivityManagerService.MemDumpInfo memDumpInfo = (ActivityManagerService.MemDumpInfo) it.next();
                    if (memDumpInfo.procName != null) {
                        Iterator it2 = it;
                        ProcessState processState = null;
                        if (this.dumpHeavyProcessList.size() < 5) {
                            DumpHeavyProcessInfo dumpHeavyProcessInfo2 = KillPolicyManager.this.new DumpHeavyProcessInfo();
                            this.dumpHeavyProcessList.add(dumpHeavyProcessInfo2);
                            dumpHeavyProcessInfo2.memoryInfo = memDumpInfo;
                            z2 = true;
                            dumpHeavyProcessInfo = dumpHeavyProcessInfo2;
                        } else {
                            dumpHeavyProcessInfo = null;
                            z2 = false;
                        }
                        if (z2 || z) {
                            j = currentTimeMillis;
                            int i2 = 0;
                            while (true) {
                                try {
                                    if (i2 >= arrayList2.size()) {
                                        arrayList = arrayList2;
                                        break;
                                    }
                                    try {
                                        ProcessRecord processRecord = (ProcessRecord) arrayList2.get(i2);
                                        if (processRecord != null) {
                                            arrayList = arrayList2;
                                            try {
                                                try {
                                                    if (memDumpInfo.procName.equals(processRecord.processName)) {
                                                        processState = processRecord.mProfile.getBaseProcessTracker();
                                                        break;
                                                    }
                                                } catch (IndexOutOfBoundsException e) {
                                                    e = e;
                                                    Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                                                    i2++;
                                                    arrayList2 = arrayList;
                                                }
                                            } catch (Exception e2) {
                                                e = e2;
                                                Slog.e("ActivityManager_kpm", Log.getStackTraceString(e));
                                                fillMemoryUsageByAdj(memDumpInfo);
                                                if (!"vis".equals(memDumpInfo.label)) {
                                                }
                                                j3 += memDumpInfo.pss + memDumpInfo.swap_out;
                                                this.fgsMemDumpList.add(memDumpInfo);
                                                if (KillPolicyManager.KPM_DEBUG) {
                                                }
                                                it = it2;
                                                currentTimeMillis = j;
                                                arrayList2 = arrayList;
                                            }
                                        } else {
                                            arrayList = arrayList2;
                                        }
                                    } catch (IndexOutOfBoundsException e3) {
                                        e = e3;
                                        arrayList = arrayList2;
                                    }
                                    i2++;
                                    arrayList2 = arrayList;
                                } catch (Exception e4) {
                                    e = e4;
                                    arrayList = arrayList2;
                                }
                            }
                            ProcessState processState2 = processState;
                            if (z2) {
                                fillProcessPackageNameAndVersion(processState2, dumpHeavyProcessInfo);
                                dumpHeavyProcessInfo.pssUssRssInfo = getPssUssRssInfo(processState2);
                            }
                            if (z) {
                                fillDumpHeavyProcessTop5ByAdj(memDumpInfo, processState2);
                            }
                        } else {
                            j = currentTimeMillis;
                            arrayList = arrayList2;
                        }
                        fillMemoryUsageByAdj(memDumpInfo);
                        if (!"vis".equals(memDumpInfo.label) || "percept".equals(memDumpInfo.label) || "perceptl".equals(memDumpInfo.label) || "perceptm".equals(memDumpInfo.label)) {
                            j3 += memDumpInfo.pss + memDumpInfo.swap_out;
                            this.fgsMemDumpList.add(memDumpInfo);
                        }
                        if (KillPolicyManager.KPM_DEBUG) {
                            StringBuilder sb = new StringBuilder(128);
                            sb.append("getProcessMemoryDumpInformation() process : ");
                            sb.append(memDumpInfo.procName);
                            sb.append(", adj: ");
                            sb.append(memDumpInfo.label);
                            sb.append(", pss: ");
                            sb.append((int) ((memDumpInfo.pss / 1024.0d) + 0.5d));
                            sb.append(" mb, swap: ");
                            sb.append((int) ((memDumpInfo.swap_out / 1024.0d) + 0.5d));
                            sb.append(" mb, rss: ");
                            sb.append((int) ((memDumpInfo.rss / 1024.0d) + 0.5d));
                            sb.append(" mb");
                            Slog.d("ActivityManager_kpm", sb.toString());
                        }
                        it = it2;
                        currentTimeMillis = j;
                        arrayList2 = arrayList;
                    }
                }
            }
            long j4 = currentTimeMillis;
            long j5 = j3;
            Slog.i("ActivityManager_kpm", "fgsMemDumpList total size " + j5 + " kb, Threshold(30%) " + j2 + " kb");
            if (KillPolicyManager.KPM_DEBUG) {
                long currentTimeMillis2 = System.currentTimeMillis() - j4;
                if (j5 >= j2) {
                    for (int i3 = 0; i3 < this.fgsMemDumpList.size(); i3++) {
                        ActivityManagerService.MemDumpInfo memDumpInfo2 = (ActivityManagerService.MemDumpInfo) this.fgsMemDumpList.get(i3);
                        StringBuilder sb2 = new StringBuilder(128);
                        sb2.append("fgsMemDumpList process : ");
                        sb2.append(memDumpInfo2.procName);
                        sb2.append(", adj: ");
                        sb2.append(memDumpInfo2.label);
                        sb2.append(", pss: ");
                        sb2.append((int) ((memDumpInfo2.pss / 1024.0d) + 0.5d));
                        sb2.append(" mb, swap: ");
                        sb2.append((int) ((memDumpInfo2.swap_out / 1024.0d) + 0.5d));
                        sb2.append(" mb, rss: ");
                        sb2.append((int) ((memDumpInfo2.rss / 1024.0d) + 0.5d));
                        sb2.append(" mb");
                        Slog.d("ActivityManager_kpm", sb2.toString());
                    }
                }
                if (z) {
                    for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                        if (this.dumpHeavyProcessByAdj.containsKey(str)) {
                            ArrayList arrayList3 = (ArrayList) this.dumpHeavyProcessByAdj.get(str);
                            Slog.d("ActivityManager_kpm", "[ADJ Label] : " + str + " - Top " + arrayList3.size());
                            showDebugHeavyProcess(arrayList3);
                        } else {
                            Slog.d("ActivityManager_kpm", "[ADJ Label] : " + str + " - this ADJ not found");
                        }
                    }
                }
                Slog.i("ActivityManager_kpm", "getProcessMemoryDumpInformation() elapsed time " + currentTimeMillis2 + " ms");
            }
        }

        public final void showDebugHeavyProcess(ArrayList arrayList) {
            if (arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    DumpHeavyProcessInfo dumpHeavyProcessInfo = (DumpHeavyProcessInfo) arrayList.get(i);
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("showDebugHeavyProcess() process : ");
                    sb.append(dumpHeavyProcessInfo.memoryInfo.procName);
                    sb.append(", ver: ");
                    sb.append(dumpHeavyProcessInfo.processVersion);
                    if (dumpHeavyProcessInfo.packageName != null) {
                        sb.append(", PackageName: ");
                        sb.append(dumpHeavyProcessInfo.packageName);
                    }
                    if (dumpHeavyProcessInfo.packageVersion != null) {
                        sb.append(", PackageVer: ");
                        sb.append(dumpHeavyProcessInfo.packageVersion);
                    }
                    sb.append(", adj: ");
                    sb.append(dumpHeavyProcessInfo.memoryInfo.label);
                    sb.append(", pss: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.pss / 1024.0d) + 0.5d));
                    sb.append(" mb, swap: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.swap_out / 1024.0d) + 0.5d));
                    sb.append(" mb, rss: ");
                    sb.append((int) ((dumpHeavyProcessInfo.memoryInfo.rss / 1024.0d) + 0.5d));
                    sb.append(" mb, ");
                    sb.append(dumpPssUssRssToString(dumpHeavyProcessInfo.pssUssRssInfo));
                    Slog.d("ActivityManager_kpm", sb.toString());
                }
                return;
            }
            Slog.d("ActivityManager_kpm", "ProcessMemoryHeavy not found");
        }

        public final void showDebugAdjMemory() {
            for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                if (this.memoryUsageByAdj.containsKey(str)) {
                    long[] jArr = (long[]) this.memoryUsageByAdj.get(str);
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("[ADJ Label] : ");
                    sb.append(str);
                    sb.append(" - Pss: ");
                    sb.append((int) ((jArr[0] / 1024.0d) + 0.5d));
                    sb.append(" mb, swapPss: ");
                    sb.append((int) ((jArr[1] / 1024.0d) + 0.5d));
                    sb.append(" mb, Rss: ");
                    sb.append((int) ((jArr[2] / 1024.0d) + 0.5d));
                    sb.append(" mb");
                    Slog.d("ActivityManager_kpm", sb.toString());
                } else {
                    Slog.d("ActivityManager_kpm", "[ADJ Label] : " + str + " - this ADJ not found");
                }
            }
        }

        public void clearHeavyList() {
            this.peakLmkdKillAdj = 1001;
            this.dumpHeavyProcessList = new ArrayList();
            this.fgsMemDumpList = new ArrayList();
            this.memoryUsageByAdj = new HashMap();
            this.dumpHeavyProcessByAdj = new HashMap();
        }
    }

    public class ProcessMemoryUsageInfo {
        public Thread mProcDumpMemThread;
        public final int PROCS_STATS_PSS = 0;
        public final int PROCS_STATS_SWAP_PSS = 1;
        public final int PROCS_STATS_WRITEBACK = 2;
        public final int PROCS_STATS_COUNT = 3;
        public List procMemDumpInfoList = new ArrayList();
        public HashMap procsMemoryPssKbByADJ = new HashMap();

        public ProcessMemoryUsageInfo() {
        }

        public void getProcDumpMemInfoThread(final KpmRaw kpmRaw) {
            Thread thread = new Thread("KPMProcessMemoryDumpThread") { // from class: com.android.server.am.KillPolicyManager.ProcessMemoryUsageInfo.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Slog.i("ActivityManager_kpm", "KPM end cycle get pss");
                        ProcessMemoryUsageInfo.this.getProcDumpMemInfo();
                        if (KillPolicyManager.KPM_DEBUG) {
                            ProcessMemoryUsageInfo.this.showDebugAdjPss();
                        }
                        kpmRaw.updateProcAdjPss(ProcessMemoryUsageInfo.this.procsMemoryPssKbByADJ);
                        KpmRaw kpmRaw2 = kpmRaw;
                        if (kpmRaw2.isStateChanged) {
                            KillPolicyManager.this.reportUserTrend(0, kpmRaw2);
                        }
                        KpmRaw kpmRaw3 = kpmRaw;
                        if (kpmRaw3.isDailyBigdata) {
                            KillPolicyManager.this.reportUserTrend(1, kpmRaw3);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            this.mProcDumpMemThread = thread;
            thread.start();
        }

        public final long[] smapsRollupPid(int i) {
            if (i <= 0) {
                return null;
            }
            String[] strArr = {"Pss:", "SwapPss:", "Writeback:"};
            String str = "/proc/" + i + "/smaps_rollup";
            long[] jArr = new long[3];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
                int i2 = 0;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.startsWith(strArr[i2])) {
                            if (readLine.split("\\s+").length == 3) {
                                jArr[i2] = Integer.parseInt(r6[1]);
                            }
                            i2++;
                            if (i2 == 3) {
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
            } catch (IOException unused) {
                Slog.d("ActivityManager", "failed to read " + str);
            } catch (RuntimeException unused2) {
                Slog.d("ActivityManager", "failed to read " + str);
            }
            return jArr;
        }

        public final void getProcDumpMemInfoInternal(int i, String str) {
            long[] smapsRollupPid = smapsRollupPid(i);
            if (smapsRollupPid == null) {
                return;
            }
            long[] readMemtrackMemory = Heimdall.readMemtrackMemory(i);
            if (readMemtrackMemory != null && readMemtrackMemory.length >= 4 && readMemtrackMemory[3] == 0) {
                smapsRollupPid[0] = smapsRollupPid[0] + readMemtrackMemory[0] + readMemtrackMemory[1] + readMemtrackMemory[2];
            }
            if (!this.procsMemoryPssKbByADJ.containsKey(str)) {
                this.procsMemoryPssKbByADJ.put(str, new long[]{smapsRollupPid[0], smapsRollupPid[1], smapsRollupPid[2], 1});
            } else {
                long[] jArr = (long[]) this.procsMemoryPssKbByADJ.get(str);
                jArr[0] = jArr[0] + smapsRollupPid[0];
                jArr[1] = jArr[1] + smapsRollupPid[1];
                jArr[2] = jArr[2] + smapsRollupPid[2];
                jArr[3] = jArr[3] + 1;
            }
            if (KillPolicyManager.KPM_DEBUG) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("getProcDumpMemInfo() ");
                sb.append("adj: ");
                sb.append(str);
                sb.append(", pss: ");
                sb.append((int) ((smapsRollupPid[0] / 1024.0d) + 0.5d));
                sb.append(" mb, swappss: ");
                sb.append((int) ((smapsRollupPid[1] / 1024.0d) + 0.5d));
                sb.append(" mb, writeback:");
                sb.append((int) ((smapsRollupPid[2] / 1024.0d) + 0.5d));
                sb.append(" mb");
                Slog.d("ActivityManager_kpm", sb.toString());
            }
        }

        public final void getProcDumpMemInfo() {
            ArrayList arrayList;
            String str;
            long currentTimeMillis = System.currentTimeMillis();
            final SparseArray sparseArray = new SparseArray();
            ActivityManagerService activityManagerService = KillPolicyManager.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    arrayList = new ArrayList(KillPolicyManager.this.mAm.mProcessList.getLruProcessesLOSP());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ProcessRecord processRecord = (ProcessRecord) it.next();
                        sparseArray.put(processRecord.getPid(), processRecord);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            KillPolicyManager.this.mAm.mAppProfiler.forAllCpuStats(new Consumer() { // from class: com.android.server.am.KillPolicyManager$ProcessMemoryUsageInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KillPolicyManager.ProcessMemoryUsageInfo.this.lambda$getProcDumpMemInfo$0(sparseArray, (ProcessCpuTracker.Stats) obj);
                }
            });
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ProcessRecord processRecord2 = (ProcessRecord) arrayList.get(size);
                int setAdjWithServices = processRecord2.mState.getSetAdjWithServices();
                int i = 0;
                while (true) {
                    int[] iArr = ActivityManagerService.DUMP_MEM_OOM_ADJ;
                    if (i >= iArr.length) {
                        str = null;
                        break;
                    } else if (i == iArr.length - 1 || (setAdjWithServices >= iArr[i] && setAdjWithServices < iArr[i + 1])) {
                        break;
                    } else {
                        i++;
                    }
                }
                str = ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL[i];
                getProcDumpMemInfoInternal(processRecord2.getPid(), str);
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.i("ActivityManager_kpm", "getProcDumpMemInfo() elapsed time " + currentTimeMillis2 + " ms");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getProcDumpMemInfo$0(SparseArray sparseArray, ProcessCpuTracker.Stats stats) {
            if (stats.vsize <= 0 || sparseArray.indexOfKey(stats.pid) >= 0) {
                return;
            }
            getProcDumpMemInfoInternal(stats.pid, "native");
        }

        public final void showDebugAdjPss() {
            for (String str : ActivityManagerService.DUMP_MEM_OOM_COMPACT_LABEL) {
                if (this.procsMemoryPssKbByADJ.containsKey(str)) {
                    long[] jArr = (long[]) this.procsMemoryPssKbByADJ.get(str);
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
                    sb.append(jArr[3]);
                    Slog.d("ActivityManager_kpm", sb.toString());
                } else {
                    Slog.d("ActivityManager_kpm", "[ADJ Label] : " + str + " - this ADJ not found");
                }
            }
        }

        public final void clearAdjPss() {
            this.procsMemoryPssKbByADJ = new HashMap();
        }
    }

    public enum PsiFileType {
        IO("/proc/pressure/io"),
        MEMORY("/proc/pressure/memory"),
        CPU("/proc/pressure/cpu");

        private String mPath;

        PsiFileType(String str) {
            this.mPath = str;
        }

        public String getPath() {
            return this.mPath;
        }
    }

    public enum PsiDataType {
        AVG10("avg10"),
        TOTAL("total");

        private String mPath;

        PsiDataType(String str) {
            this.mPath = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PsiFile getPsiFile(PsiFileType psiFileType, PsiDataType psiDataType) {
        StringBuilder sb;
        String str = "";
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                BufferedReader bufferedReader4 = new BufferedReader(new FileReader(psiFileType.getPath()));
                while (true) {
                    try {
                        String readLine = bufferedReader4.readLine();
                        if (readLine != null) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str);
                            sb2.append(readLine);
                            sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                            str = sb2.toString();
                            bufferedReader2 = sb2;
                        } else {
                            try {
                                break;
                            } catch (Exception e) {
                                e = e;
                                sb = new StringBuilder();
                                sb.append("Exception");
                                sb.append(e.getMessage());
                                Slog.e("ActivityManager", sb.toString());
                                return new PsiFile(str, psiDataType);
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader3 = bufferedReader4;
                        Slog.e("ActivityManager", "getPsiFile Exception" + e.getMessage());
                        bufferedReader = bufferedReader3;
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                                bufferedReader = bufferedReader3;
                            } catch (Exception e3) {
                                e = e3;
                                sb = new StringBuilder();
                                sb.append("Exception");
                                sb.append(e.getMessage());
                                Slog.e("ActivityManager", sb.toString());
                                return new PsiFile(str, psiDataType);
                            }
                        }
                        return new PsiFile(str, psiDataType);
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader4;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e4) {
                                Slog.e("ActivityManager", "Exception" + e4.getMessage());
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader4.close();
                bufferedReader = bufferedReader2;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e5) {
            e = e5;
        }
        return new PsiFile(str, psiDataType);
    }

    public class PsiFile {
        public double mFullAvg10;
        public boolean mIsEmptyFile;
        public long mPsiFullTotal;
        public long mPsiSomeTotal;
        public double mSomeAvg10;

        public PsiFile(String str, final PsiDataType psiDataType) {
            if (!TextUtils.isEmpty(str)) {
                Arrays.stream(str.split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE)).forEach(new Consumer() { // from class: com.android.server.am.KillPolicyManager$PsiFile$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        KillPolicyManager.PsiFile.this.lambda$new$0(psiDataType, (String) obj);
                    }
                });
            } else {
                this.mIsEmptyFile = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(PsiDataType psiDataType, String str) {
            if (str.startsWith("some")) {
                if (psiDataType == PsiDataType.AVG10) {
                    this.mSomeAvg10 = Double.parseDouble(getItem(str, "avg10", 1));
                    return;
                } else {
                    if (psiDataType == PsiDataType.TOTAL) {
                        this.mPsiSomeTotal = Long.parseLong(getItem(str, "total", 4));
                        return;
                    }
                    return;
                }
            }
            if (str.startsWith("full")) {
                if (psiDataType == PsiDataType.AVG10) {
                    this.mFullAvg10 = Double.parseDouble(getItem(str, "avg10", 1));
                } else if (psiDataType == PsiDataType.TOTAL) {
                    this.mPsiFullTotal = Long.parseLong(getItem(str, "total", 4));
                }
            }
        }

        public final String getItem(String str, String str2, int i) {
            String[] split = str.split(" ");
            String str3 = str2 + "=";
            return (split.length <= i || !split[i].startsWith(str3)) ? "0" : split[i].replace(str3, "");
        }

        public double getSomeAvg10() {
            return this.mSomeAvg10;
        }

        public boolean isEmpty() {
            return this.mIsEmptyFile;
        }
    }

    public class SwappinessController {
        public int mCurrentValue;
        public final int mDefaultValue;

        public SwappinessController() {
            int swappinessFromProc = getSwappinessFromProc();
            this.mDefaultValue = swappinessFromProc;
            this.mCurrentValue = swappinessFromProc;
            Slog.i("ActivityManager", "SwappinessController() - default value: " + swappinessFromProc);
        }

        public static int getSwappinessFromProc() {
            return ProcFileInfoGetter.getCurSwappiness();
        }

        public void setValue(int i) {
            if (i <= this.mDefaultValue) {
                Slog.i("ActivityManager", "SwappinessController() - new value is lower than default value, go back to default");
                i = this.mDefaultValue;
            }
            SystemProperties.set("sys.sysctl.swappiness", String.valueOf(i));
            Slog.i("ActivityManager", "SwappinessController() - changed from " + this.mCurrentValue + " -> " + i);
            this.mCurrentValue = i;
        }

        public int getValue() {
            return this.mCurrentValue;
        }

        public int getDefaultValue() {
            return this.mDefaultValue;
        }
    }

    public class ChimeraTriggerManager {
        public static final float PMM_CRITICAL_SCORE_THRESHOLD = Float.parseFloat(SystemProperties.get("persist.sys.kpm.debug.chimera_critical_score_threshold", "3.0"));
        public static final long TRIGGER_MIN_INTERVAL = Long.parseLong(SemSystemProperties.get("persist.sys.kpm.debug.chimera_trigger_min_interval", Duration.ofDays(1).toMillis() + ""));
        public static ChimeraTriggerManager mInstance = null;
        public final Context mContext;
        public float mLastPolicyScore = -1.0f;
        public boolean mChimeraTriggerRequired = false;
        public long mLastTriggeredTime = 0;

        public static synchronized ChimeraTriggerManager getInstance(Context context) {
            ChimeraTriggerManager chimeraTriggerManager;
            synchronized (ChimeraTriggerManager.class) {
                if (mInstance == null) {
                    mInstance = new ChimeraTriggerManager(context);
                }
                chimeraTriggerManager = mInstance;
            }
            return chimeraTriggerManager;
        }

        public ChimeraTriggerManager(Context context) {
            Log.d("ActivityManager", "ChimeraTriggerManager() - start");
            this.mContext = context;
        }

        public void onPolicyScoreUpdated(float f) {
            Log.d("ActivityManager", "ChimeraTriggerManager::onPolicyScoreUpdated() - evaPolicy: " + f);
            this.mLastPolicyScore = f;
            this.mChimeraTriggerRequired = f >= PMM_CRITICAL_SCORE_THRESHOLD;
        }

        public void onDeviceIdleChanged(boolean z) {
            Log.d("ActivityManager", "ChimeraTriggerManager::onReceive() -  deviceInDoze: " + z + ", chimeraTriggerRequired: " + this.mChimeraTriggerRequired);
            if (z && this.mChimeraTriggerRequired) {
                long currentTimeMillis = System.currentTimeMillis() - this.mLastTriggeredTime;
                Log.d("ActivityManager", "ChimeraTriggerManager::onReceive() - need to trigger, elapsedTime: " + currentTimeMillis);
                if (currentTimeMillis < TRIGGER_MIN_INTERVAL) {
                    Log.d("ActivityManager", "ChimeraTriggerManager::onReceive() - Not enough time has passed since the previous trigger.");
                } else {
                    Log.d("ActivityManager", "ChimeraTriggerManager::onReceive() - PMM_CRITICAL_TRIGGER_ACTION has been fired.");
                    Intent intent = new Intent("com.samsung.PMM_CRITICAL_TRIGGER");
                    intent.setPackage("android");
                    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permission.BROADCAST_PMM_CRITICAL_TRIGGER");
                    this.mLastTriggeredTime = System.currentTimeMillis();
                    this.mLastPolicyScore = -1.0f;
                }
                KillPolicyManager.mIsChimeraPmmKillTriggered = true;
            }
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("\n ChimeraTrigger Stats:");
            printWriter.println(" - Last Policy Score      : " + this.mLastPolicyScore);
            printWriter.println(" - Trigger Threshold Score: " + PMM_CRITICAL_SCORE_THRESHOLD);
            printWriter.println(" - Trigger Required       : " + this.mChimeraTriggerRequired);
            printWriter.println(" - Last Triggered Time(ms): " + this.mLastTriggeredTime);
            printWriter.println("");
        }
    }

    public class MemoryDumpItem {
        public long mDumpSize;
        public MemoryLoggingType mMemoryType;

        public MemoryDumpItem(long j, MemoryLoggingType memoryLoggingType) {
            this.mDumpSize = j;
            this.mMemoryType = memoryLoggingType;
        }
    }

    public class MemoryFloodDetector {
        public List mNativeMemDumpList = new ArrayList();
        public List mSystemMemDumpList = new ArrayList();
        public List mPersistentMemDumpList = new ArrayList();
        public long mLastUptimeMillis = 0;
        public long mFirstUptimeMillis = SystemClock.uptimeMillis();
        public long mLastRealTimeMillis = System.currentTimeMillis();
        public int mPmmCycleCountOnPlatformReset = 0;
        public Pair mLastCalculatedNative = new Pair(0, 0);
        public Pair mLastCalculatedSysPers = new Pair(0, 0);

        public MemoryFloodDetector() {
        }

        public void onDeviceIdleChanged(boolean z) {
            if (z) {
                if (this.mNativeMemDumpList.size() == 0) {
                    loadNativeDumpProperty();
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                long currentTimeMillis = System.currentTimeMillis();
                if (uptimeMillis - this.mLastUptimeMillis > Duration.ofDays(2L).toMillis() || currentTimeMillis - this.mLastRealTimeMillis > Duration.ofDays(2L).toMillis()) {
                    recordPmmDump();
                }
                if (uptimeMillis - this.mLastUptimeMillis > Duration.ofHours(KillPolicyManager.sNeverCollectWithin).toMillis() || currentTimeMillis - this.mLastRealTimeMillis > Duration.ofHours(KillPolicyManager.sNeverCollectWithin).toMillis()) {
                    recordIdleMemDump();
                    saveNativeDumpProperty();
                }
            }
        }

        public void loadNativeDumpProperty() {
            String str = SystemProperties.get("sys.pmm.nativemse", "");
            try {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                String[] split = str.split(",");
                if (split.length >= 3) {
                    double parseDouble = Double.parseDouble(split[0]);
                    double parseDouble2 = Double.parseDouble(split[1]);
                    int parseInt = Integer.parseInt(split[2]);
                    for (int i = 0; i < parseInt; i++) {
                        this.mNativeMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem((long) ((i * parseDouble2) + parseDouble), MemoryLoggingType.IdleDump));
                    }
                    if (split.length >= 4) {
                        this.mPmmCycleCountOnPlatformReset = Integer.parseInt(split[3]);
                    }
                }
            } catch (Exception e) {
                Slog.i("ActivityManager_kpm", "loadNativeDumpProperty exception ; " + e.getMessage());
            }
        }

        public void saveNativeDumpProperty() {
            if (this.mNativeMemDumpList.size() > 1) {
                try {
                    Pair linearRegressionFactor = getLinearRegressionFactor(this.mNativeMemDumpList);
                    SystemProperties.set("sys.pmm.nativemse", Double.toString(((Double) linearRegressionFactor.first).doubleValue()) + "," + Double.toString(((Double) linearRegressionFactor.second).doubleValue()) + "," + Integer.toString(this.mNativeMemDumpList.size()) + "," + (this.mPmmCycleCountOnPlatformReset + KillPolicyManager.this.mPolicyMetric));
                } catch (Exception e) {
                    Slog.i("ActivityManager_kpm", "saveNativeDumpProperty exception ; " + e.getMessage());
                }
            }
        }

        public void recordPmmDump() {
            KpmRaw[] kpmRawArr;
            int i;
            KpmRaw[] kpmRawArr2 = KillPolicyManager.this.mKpmRawPolicy;
            int length = kpmRawArr2.length;
            int i2 = 0;
            long j = Long.MAX_VALUE;
            long j2 = Long.MAX_VALUE;
            long j3 = Long.MAX_VALUE;
            long j4 = Long.MAX_VALUE;
            while (i2 < length) {
                KpmRaw kpmRaw = kpmRawArr2[i2];
                if (kpmRaw == null || kpmRaw.procsAdjPss == null) {
                    kpmRawArr = kpmRawArr2;
                    i = length;
                } else {
                    kpmRawArr = kpmRawArr2;
                    i = length;
                    if (kpmRaw.uptimeMillis > this.mLastUptimeMillis) {
                        j = getPmmMinDump("native", kpmRaw, j);
                        j2 = getPmmMinDump("sys", kpmRaw, j2);
                        j3 = getPmmMinDump("pers", kpmRaw, j3);
                        j4 = getPmmMinDump("persvc", kpmRaw, j4);
                    }
                }
                i2++;
                kpmRawArr2 = kpmRawArr;
                length = i;
            }
            if (Long.MAX_VALUE != j) {
                List list = this.mNativeMemDumpList;
                KillPolicyManager killPolicyManager = KillPolicyManager.this;
                MemoryLoggingType memoryLoggingType = MemoryLoggingType.PmmDump;
                list.add(killPolicyManager.new MemoryDumpItem(j, memoryLoggingType));
                this.mSystemMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem(j2, memoryLoggingType));
                this.mPersistentMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem(j4 + j3, memoryLoggingType));
            }
            Slog.i("ActivityManager_kpm", "recordPmmDump (" + j + ", " + j2 + ", " + j3 + ") added. total size : " + this.mNativeMemDumpList.size());
        }

        public long getPmmMinDump(String str, KpmRaw kpmRaw, long j) {
            if (!kpmRaw.procsAdjPss.containsKey(str)) {
                return j;
            }
            long[] jArr = (long[]) kpmRaw.procsAdjPss.get(str);
            long j2 = jArr[0] + jArr[1];
            return j2 < j ? j2 : j;
        }

        public void recordIdleMemDump() {
            long j;
            long j2;
            long j3;
            if (this.mNativeMemDumpList.size() > KillPolicyManager.sCompactTriggerSize) {
                this.mNativeMemDumpList = getCompactList(this.mNativeMemDumpList);
            }
            if (this.mSystemMemDumpList.size() > KillPolicyManager.sCompactTriggerSize) {
                this.mSystemMemDumpList = getCompactList(this.mSystemMemDumpList);
            }
            if (this.mPersistentMemDumpList.size() > KillPolicyManager.sCompactTriggerSize) {
                this.mPersistentMemDumpList = getCompactList(this.mPersistentMemDumpList);
            }
            List<ActivityManagerService.MemDumpInfo> dumpMemoryInfo = KillPolicyManager.this.mAm.getDumpMemoryInfo();
            if (dumpMemoryInfo == null || dumpMemoryInfo.size() <= 0) {
                j = 0;
                j2 = 0;
                j3 = 0;
            } else {
                j = 0;
                j2 = 0;
                j3 = 0;
                for (ActivityManagerService.MemDumpInfo memDumpInfo : dumpMemoryInfo) {
                    if (!TextUtils.isEmpty(memDumpInfo.label)) {
                        String str = memDumpInfo.label;
                        str.hashCode();
                        switch (str) {
                            case "native":
                                j += memDumpInfo.pss + memDumpInfo.swap_out;
                                break;
                            case "persvc":
                            case "pers":
                                j3 += memDumpInfo.pss + memDumpInfo.swap_out;
                                break;
                            case "sys":
                                j2 += memDumpInfo.pss + memDumpInfo.swap_out;
                                break;
                        }
                    }
                }
            }
            if (j > 0) {
                this.mNativeMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem(j, MemoryLoggingType.IdleDump));
            }
            if (j2 > 0) {
                this.mSystemMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem(j2, MemoryLoggingType.IdleDump));
            }
            if (j3 > 0) {
                this.mPersistentMemDumpList.add(KillPolicyManager.this.new MemoryDumpItem(j3, MemoryLoggingType.IdleDump));
            }
            Slog.i("ActivityManager_kpm", "recordIdleMemDump (" + j + ", " + j2 + ", " + j3 + ") added. total size : " + this.mNativeMemDumpList.size());
            reportMemoryFlood2Hqm();
            this.mLastUptimeMillis = SystemClock.uptimeMillis();
            this.mLastRealTimeMillis = System.currentTimeMillis();
        }

        public JSONArray getJsonSummary(List list) {
            JSONArray jSONArray = new JSONArray();
            LongSummaryStatistics summaryStatistics = list.stream().mapToLong(new ToLongFunction() { // from class: com.android.server.am.KillPolicyManager$MemoryFloodDetector$$ExternalSyntheticLambda0
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                    long lambda$getJsonSummary$0;
                    lambda$getJsonSummary$0 = KillPolicyManager.MemoryFloodDetector.lambda$getJsonSummary$0((KillPolicyManager.MemoryDumpItem) obj);
                    return lambda$getJsonSummary$0;
                }
            }).summaryStatistics();
            jSONArray.put((int) ((((MemoryDumpItem) list.get(list.size() - 1)).mDumpSize / 1024) + 0.5d)).put(summaryStatistics.getMin()).put(summaryStatistics.getMax()).put((int) (summaryStatistics.getAverage() + 0.5d)).put(list.size());
            return jSONArray;
        }

        public static /* synthetic */ long lambda$getJsonSummary$0(MemoryDumpItem memoryDumpItem) {
            return (long) ((memoryDumpItem.mDumpSize / 1024) + 0.5d);
        }

        public void reportMemoryFlood2Hqm() {
            String str = null;
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("TYPE", 3);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put((int) ((KillPolicyManager.this.mLastAwakeRealTimeMillis / 1000.0f) + 0.5d)).put((int) ((KillPolicyManager.this.mLastAwakeUpTimeMillis / 1000.0f) + 0.5d)).put((int) ((KillPolicyManager.this.mLastIdleRealTimeMillis / 1000.0f) + 0.5d)).put((int) (((System.currentTimeMillis() - this.mLastRealTimeMillis) / 1000.0f) + 0.5d)).put((int) (((SystemClock.uptimeMillis() - this.mLastUptimeMillis) / 1000.0f) + 0.5d));
                jSONObject.put("DITI", jSONArray);
                if (this.mNativeMemDumpList.size() > 0) {
                    jSONObject.put("FNAI", getJsonSummary(this.mNativeMemDumpList));
                }
                if (this.mSystemMemDumpList.size() > 0) {
                    jSONObject.put("FSAI", getJsonSummary(this.mSystemMemDumpList));
                }
                if (this.mPersistentMemDumpList.size() > 0) {
                    jSONObject.put("FPAI", getJsonSummary(this.mPersistentMemDumpList));
                }
                String jSONObject2 = jSONObject.toString();
                str = jSONObject2.substring(1, jSONObject2.length() - 1);
            } catch (JSONException unused) {
                Slog.d("ActivityManager_kpm", "failed to create the KPUT");
            }
            if (KillPolicyManager.KPM_DEBUG) {
                Slog.i("ActivityManager_kpm", "Sending to HQM : " + str);
            }
            KillPolicyManager.this.sendHqmBigData("KPUT", str);
        }

        public List getCompactList(List list) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i += 2) {
                arrayList.add((MemoryDumpItem) list.get(i));
            }
            return arrayList;
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("MemoryFloodDetector");
            if (this.mNativeMemDumpList.size() > 0) {
                dumpItem(printWriter, "native", this.mNativeMemDumpList);
            }
            if (this.mSystemMemDumpList.size() > 0) {
                dumpItem(printWriter, "sys", this.mSystemMemDumpList);
                dumpItem(printWriter, "pers", this.mPersistentMemDumpList);
            }
        }

        public void dumpItem(PrintWriter printWriter, String str, List list) {
            printWriter.print(str);
            printWriter.print(" : ");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MemoryDumpItem memoryDumpItem = (MemoryDumpItem) it.next();
                printWriter.print(memoryDumpItem.mMemoryType == MemoryLoggingType.IdleDump ? " I " : " P ");
                printWriter.print(memoryDumpItem.mDumpSize);
            }
            printWriter.println();
        }

        public boolean isFlood(double d, double d2, boolean z) {
            return d * ((double) ((z ? KillPolicyManager.sNativeFloodRatio : KillPolicyManager.sSyspersFloodRatio) + 100)) < d2 * 100.0d;
        }

        public void calculateMemoryFlood() {
            if (this.mNativeMemDumpList.size() >= KillPolicyManager.sMinTriggerSize) {
                Pair linearRegressionFactor = getLinearRegressionFactor(this.mNativeMemDumpList);
                int size = this.mNativeMemDumpList.size() - 1;
                double doubleValue = ((Double) linearRegressionFactor.second).doubleValue();
                double doubleValue2 = (((Double) linearRegressionFactor.first).doubleValue() * size) + doubleValue;
                Slog.i("ActivityManager_kpm", "nativeFactor : " + linearRegressionFactor.first + ", " + linearRegressionFactor.second + " => " + doubleValue + ", " + doubleValue2);
                this.mLastCalculatedNative = new Pair(Integer.valueOf((int) doubleValue), Integer.valueOf((int) doubleValue2));
            }
            if (this.mSystemMemDumpList.size() >= KillPolicyManager.sMinTriggerSize) {
                Pair linearRegressionFactor2 = getLinearRegressionFactor(this.mSystemMemDumpList);
                Pair linearRegressionFactor3 = getLinearRegressionFactor(this.mPersistentMemDumpList);
                int size2 = this.mSystemMemDumpList.size() - 1;
                double doubleValue3 = ((Double) linearRegressionFactor2.second).doubleValue() + ((Double) linearRegressionFactor3.second).doubleValue();
                double d = size2;
                double doubleValue4 = (((Double) linearRegressionFactor2.first).doubleValue() * d) + (((Double) linearRegressionFactor3.first).doubleValue() * d) + doubleValue3;
                Slog.i("ActivityManager_kpm", "sysFactor : " + linearRegressionFactor2.first + ", " + linearRegressionFactor2.second + " persFactor : " + linearRegressionFactor3.first + ", " + linearRegressionFactor3.second + " => " + doubleValue3 + ", " + doubleValue4);
                this.mLastCalculatedSysPers = new Pair(Integer.valueOf((int) doubleValue3), Integer.valueOf((int) doubleValue4));
            }
        }

        public boolean isRequiredPlatformReset() {
            if (this.mSystemMemDumpList.size() < KillPolicyManager.sMinTriggerSize) {
                return false;
            }
            return isFlood(((Integer) this.mLastCalculatedSysPers.first).intValue(), ((Integer) this.mLastCalculatedSysPers.second).intValue(), false);
        }

        public boolean isRequiredKernelReset() {
            if (this.mNativeMemDumpList.size() < KillPolicyManager.sMinTriggerSize) {
                return false;
            }
            return isFlood(((Integer) this.mLastCalculatedNative.first).intValue(), ((Integer) this.mLastCalculatedNative.second).intValue(), true);
        }

        public Pair getLinearRegressionFactor(List list) {
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
                d += d8 * (((MemoryDumpItem) list.get(i2)).mDumpSize - d6);
            }
            double d9 = d / d7;
            return new Pair(Double.valueOf(d9), Double.valueOf(d6 - (d5 * d9)));
        }
    }

    public abstract class ProcFileInfoGetter {
        public static int getCurSwappiness() {
            long[] jArr = {0};
            Process.readProcFile("/proc/sys/vm/swappiness", new int[]{8224}, null, jArr, null);
            return (int) jArr[0];
        }

        public static long getLMKDCountInfo() {
            long[] jArr = {0};
            Process.readProcFile("/proc/lmkd_debug/lmkd_count", new int[]{8224}, null, jArr, null);
            return jArr[0];
        }

        public static long getLMKDCricCountInfo() {
            long[] jArr = {0};
            Process.readProcFile("/proc/lmkd_debug/lmkd_cricount", new int[]{8224}, null, jArr, null);
            return jArr[0];
        }

        public static long getLMKCountInfo() {
            long[] jArr = {0};
            Process.readProcFile("/sys/module/lowmemorykiller/parameters/lmkcount", new int[]{8224}, null, jArr, null);
            return jArr[0];
        }

        public static int getRAMsizeGB() {
            int[] iArr = {2, 3, 4, 6, 8, 12, 16};
            new MemInfoReader().readLightMemInfo();
            double totalSizeKb = r2.getTotalSizeKb() / 1048576.0d;
            for (int i = 0; i < 7; i++) {
                int i2 = iArr[i];
                if (totalSizeKb <= i2) {
                    return i2;
                }
            }
            return iArr[6];
        }
    }
}
