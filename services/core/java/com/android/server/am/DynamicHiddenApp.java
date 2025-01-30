package com.android.server.am;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.bgslotmanager.BGSlotManager;
import com.android.server.bgslotmanager.BgAppPropManager;
import com.android.server.bgslotmanager.CameraKillModeManager;
import com.android.server.bgslotmanager.CustomEFKManager;
import com.android.server.bgslotmanager.MemInfoGetter;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class DynamicHiddenApp {
  public static boolean DEBUG = false;
  public static final boolean IS_HIGH_CAPACITY_RAM;
  public static boolean LMKD_REENTRY_MODE_ENABLE;
  public static int LMK_CRITICAL_ADJ;
  public static boolean LMK_CRITICAL_UPGRADE;
  public static int LMK_CUSTOM_SW_LIMIT;
  public static int LMK_CUSTOM_TM_LIMIT;
  public static boolean LMK_DEBUG;
  public static int LMK_DOWNGRADE_PRESSURE;
  public static boolean LMK_ENABLE_CMARBINFREE_SUB;
  public static boolean LMK_ENABLE_REENTRY_LMK;
  public static boolean LMK_ENABLE_UPGRADE_CRIADJ;
  public static boolean LMK_ENABLE_USERSPACE_LMK;
  public static boolean LMK_FREELIMIT_ENABLE;
  public static int LMK_FREELIMIT_VAL;
  public static boolean LMK_KILL_HEAVIEST_TASK;
  public static int LMK_KILL_TIMEOUT_MS;
  public static int LMK_LOW_ADJ;
  public static boolean LMK_LOW_MEM_KEEP_ENABLE;
  public static boolean LMK_LOW_RAM_DEVICE;
  public static int LMK_MEDIUM_ADJ;
  public static int LMK_PSI_CRITICAL_TH;
  public static int LMK_PSI_LOW_TH;
  public static int LMK_PSI_MEDIUM_TH;
  public static int LMK_UPGRADE_PRESSURE;
  public static boolean LMK_USE_MINFREE_LEVELS;
  public static final int MAX_NEVERKILLEDAPP_NUM;
  public static long lastStartTime;
  public static long lastTime;
  public static final long mTotalMemMb;
  public static int reentryCount;
  public static HashMap reentryMap;
  public final String ACTION_DEFAULT_HOME_CHANGE;
  public final String ACTION_DOCK_EVENT;
  public final String ACTION_DOCK_STATE;
  public int ActiveLaunchCount;
  public int ActiveLaunchLimit;
  public ArrayList ActiveLaunchSlot;
  public final String EXTRA_HOME_HUB_MODE;
  public boolean HomeHubState;
  public int MlLaunchCount;
  public ArrayList MlLaunchSlot;
  public final String PACKAGE_NAME_HOMEHUB;
  public final String TAG_HH;
  public boolean isReentryMode;
  public ActivityManagerService mAm;
  public BGProtectManager mBGProtectManager;
  public final BGSlotManager mBGSlotManager;
  public BgAppPropManager mBgAppPropManager;
  public CameraKillModeManager mCameraKillModeManager;
  public ActivityManagerConstants mConstants;
  public Context mContext;
  public CustomEFKManager mCustomEFKManager;
  public final MemInfoGetter mInfo;
  public ProcessList mProcessList;
  public static float mLMKScale = BgAppPropManager.getSlmkPropertyFloat("dha_lmk_scale", "-1");
  public static String mLMKArray = BgAppPropManager.getSlmkPropertyString("dha_lmk_array", "none");
  public static boolean PICKED_ADJ_ENABLE =
      BgAppPropManager.getSlmkPropertyBool("enable_picked_adj", "true");
  public static boolean BORA_POLICY_ENABLE =
      BgAppPropManager.getSlmkPropertyBool("bora_policy_enable", "false");
  public static boolean sHH_AMSExceptionEnable =
      BgAppPropManager.getSlmkPropertyBool("hh_ams_exception", "false");
  public static Base64.Decoder sPkgDecoder = Base64.getDecoder();
  public static ArrayList alliedProtectedProcList = new ArrayList();
  public static int max_neverkilledapp_num_default =
      BgAppPropManager.getSlmkPropertyInt("neverkill_num_lowram", "0");
  public static int max_neverkilledapp_num_8g =
      BgAppPropManager.getSlmkPropertyInt("neverkill_num_8G", "1");
  public static int max_neverkilledapp_num_12g =
      BgAppPropManager.getSlmkPropertyInt("neverkill_num_12G", "3");
  public static int max_neverkilledapp_num_16g =
      BgAppPropManager.getSlmkPropertyInt("neverkill_num_16G", "5");

  public abstract class DhaClassLazyHolder {
    public static final DynamicHiddenApp INSTANCE = new DynamicHiddenApp();
    public static boolean isinitClass = false;
  }

  public enum LmkdParameter {
    LMK_LOW_ADJ,
    LMK_MEDIUM_ADJ,
    LMK_CRITICAL_ADJ,
    LMK_DEBUG,
    LMK_CRITICAL_UPGRADE,
    LMK_UPGRADE_PRESSURE,
    LMK_DOWNGRADE_PRESSURE,
    LMK_KILL_HEAVIEST_TASK,
    LMK_KILL_TIMEOUT_MS,
    LMK_USE_MINFREE_LEVELS,
    LMK_ENABLE_USERSPACE_LMK,
    LMK_ENABLE_CMARBINFREE_SUB,
    LMK_ENABLE_UPGRADE_CRIADJ,
    LMK_FREELIMIT_ENABLE,
    LMK_FREELIMIT_VAL,
    LMK_PSI_LOW_TH,
    LMK_PSI_MEDIUM_TH,
    LMK_PSI_CRITICAL_TH,
    LMK_SET_SWAPTOTAL,
    LMK_SET_BG_KEEPING
  }

  static {
    long totalMemoryMB = MemInfoGetter.getTotalMemoryMB();
    mTotalMemMb = totalMemoryMB;
    IS_HIGH_CAPACITY_RAM = totalMemoryMB > 10240;
    if (totalMemoryMB > 12288) {
      MAX_NEVERKILLEDAPP_NUM = max_neverkilledapp_num_16g;
    } else if (totalMemoryMB > 8192) {
      MAX_NEVERKILLEDAPP_NUM = max_neverkilledapp_num_12g;
    } else if (totalMemoryMB > 6144) {
      MAX_NEVERKILLEDAPP_NUM = max_neverkilledapp_num_8g;
    } else {
      MAX_NEVERKILLEDAPP_NUM = max_neverkilledapp_num_default;
    }
    LMK_LOW_ADJ = BgAppPropManager.getSlmkPropertyInt("low", "1001");
    LMK_MEDIUM_ADJ = BgAppPropManager.getSlmkPropertyInt("medium", "850");
    LMK_CRITICAL_ADJ = BgAppPropManager.getSlmkPropertyInt("critical", "0");
    LMK_DEBUG = BgAppPropManager.getSlmkPropertyBool("debug", "false");
    LMK_CRITICAL_UPGRADE = BgAppPropManager.getSlmkPropertyBool("critical_upgrade", "false");
    LMK_UPGRADE_PRESSURE = BgAppPropManager.getSlmkPropertyInt("upgrade_pressure", "80");
    LMK_DOWNGRADE_PRESSURE = BgAppPropManager.getSlmkPropertyInt("downgrade_pressure", "100");
    LMK_KILL_HEAVIEST_TASK = BgAppPropManager.getSlmkPropertyBool("kill_heaviest_task", "true");
    LMK_LOW_RAM_DEVICE = Boolean.parseBoolean(SystemProperties.get("ro.config.low_ram", "false"));
    LMK_KILL_TIMEOUT_MS = BgAppPropManager.getSlmkPropertyInt("kill_timeout_ms", "100");
    LMK_USE_MINFREE_LEVELS = BgAppPropManager.getSlmkPropertyBool("use_minfree_levels", "false");
    LMK_ENABLE_USERSPACE_LMK = true;
    LMK_ENABLE_REENTRY_LMK = true;
    LMK_ENABLE_CMARBINFREE_SUB =
        BgAppPropManager.getSlmkPropertyBool("enable_cmarbinfree_sub", "true");
    LMK_ENABLE_UPGRADE_CRIADJ =
        BgAppPropManager.getSlmkPropertyBool("enable_upgrade_criadj", "false");
    LMK_FREELIMIT_ENABLE = BgAppPropManager.getSlmkPropertyBool("freelimit_enable", "true");
    LMK_LOW_MEM_KEEP_ENABLE = true;
    LMK_FREELIMIT_VAL = BgAppPropManager.getSlmkPropertyInt("freelimit_val", "11");
    LMK_CUSTOM_SW_LIMIT = BgAppPropManager.getSlmkPropertyInt("custom_sw_limit", "500");
    LMK_CUSTOM_TM_LIMIT = BgAppPropManager.getSlmkPropertyInt("custom_tm_limit", "1000");
    LMK_PSI_LOW_TH = BgAppPropManager.getSlmkPropertyInt("psi_low", "70");
    LMK_PSI_MEDIUM_TH = BgAppPropManager.getSlmkPropertyInt("psi_medium", "70");
    LMK_PSI_CRITICAL_TH = BgAppPropManager.getSlmkPropertyInt("psi_critical", "120");
    LMKD_REENTRY_MODE_ENABLE = BgAppPropManager.getSlmkPropertyBool("reentry_mode_enable", "true");
    reentryMap = new HashMap();
    reentryCount = 0;
    lastTime = 0L;
    lastStartTime = 0L;
  }

  public DynamicHiddenApp() {
    this.mCustomEFKManager = new CustomEFKManager();
    MemInfoGetter memInfoGetter = new MemInfoGetter();
    this.mInfo = memInfoGetter;
    this.mBGSlotManager = new BGSlotManager(memInfoGetter);
    this.mBGProtectManager = new BGProtectManager();
    this.ActiveLaunchCount = 0;
    this.MlLaunchCount = 0;
    this.ActiveLaunchLimit = 1;
    this.ActiveLaunchSlot = new ArrayList();
    this.MlLaunchSlot = new ArrayList();
    this.TAG_HH = "ActivityManager_HOME_HUB";
    this.EXTRA_HOME_HUB_MODE = "home_hub_mode";
    this.PACKAGE_NAME_HOMEHUB = "com.samsung.android.homehub";
    this.ACTION_DOCK_STATE = "android.intent.extra.DOCK_STATE";
    this.ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
    this.ACTION_DEFAULT_HOME_CHANGE = "com.samsung.android.homehub.action.DEFAULT_HOME_CHANGE";
    this.HomeHubState = false;
    this.isReentryMode = false;
  }

  public static DynamicHiddenApp getInstance() {
    return DhaClassLazyHolder.INSTANCE;
  }

  public void initDynamicHiddenApp(
      ActivityManagerService activityManagerService,
      ProcessList processList,
      ActivityManagerConstants activityManagerConstants) {
    if (DhaClassLazyHolder.isinitClass) {
      return;
    }
    this.mAm = activityManagerService;
    this.mProcessList = processList;
    if (activityManagerService != null) {
      this.mContext = activityManagerService.mContext;
    }
    this.mConstants = activityManagerConstants;
    this.mBgAppPropManager = new BgAppPropManager(this.mProcessList, this);
    this.mBGSlotManager.initBGSlotManager(this, mTotalMemMb);
    this.mBGProtectManager.initBGProtectManager(this.mContext);
    initLMKTh();
    DhaClassLazyHolder.isinitClass = true;
  }

  public void initDHAPostBoot() {
    setCameraManagerCallback();
    this.mBGProtectManager.initBGProtectManagerPostBoot();
    this.mBGSlotManager.initBGSlotManagerPostBoot();
    ProcessList.setLmkdParameter(LmkdParameter.LMK_SET_SWAPTOTAL.ordinal(), 1);
    addDHAIntentFilter();
  }

  public void addDHAIntentFilter() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.android.server.am.BROADCAST_DHA_DEBUG_ON");
    intentFilter.addAction("com.android.server.am.BROADCAST_SET_DHA_PARAMETER");
    intentFilter.addAction("com.android.server.am.BROADCAST_SET_LMKD_PARAMETER_INTENT");
    intentFilter.addAction("android.intent.action.DOCK_EVENT");
    intentFilter.addAction("com.samsung.android.homehub.action.DEFAULT_HOME_CHANGE");
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class: com.android.server.am.DynamicHiddenApp.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
              return;
            }
            if (intent.getAction().compareTo("com.android.server.am.BROADCAST_DHA_DEBUG_ON") == 0) {
              String str = SystemProperties.get("ro.debug_level", "Unknown");
              if (str.equalsIgnoreCase("Unknown") || str.equalsIgnoreCase("0x4f4c")) {
                return;
              }
              DynamicHiddenApp.DEBUG = true;
              return;
            }
            if (intent.getAction().compareTo("com.android.server.am.BROADCAST_SET_DHA_PARAMETER")
                == 0) {
              if (!"true"
                  .equals(BgAppPropManager.getSystemPropertyString("ro.product_ship", "false"))) {
                DynamicHiddenApp.this.updateParamsFile();
                return;
              } else {
                Slog.w("ActivityManager", "updateParamsFile is blocked by ship build");
                return;
              }
            }
            if ("com.android.server.am.BROADCAST_SET_LMKD_PARAMETER_INTENT"
                .equals(intent.getAction())) {
              Slog.d("ActivityManager", "BROADCAST_SET_LMKD_INTENT RECEIVED");
              Bundle extras = intent.getExtras();
              if (extras != null) {
                Slog.d("LMKD_INTENT", "send to updateParamsIntent");
                DynamicHiddenApp.this.updateParamsIntent(extras);
                return;
              } else {
                Slog.d("ActivityManager", "getExtras is null");
                return;
              }
            }
            if ("com.samsung.android.homehub.action.DEFAULT_HOME_CHANGE".equals(intent.getAction())
                || "android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
              Bundle extras2 = intent.getExtras();
              if (extras2 != null) {
                DynamicHiddenApp.this.isHomeHubMode(extras2, intent.getAction());
              } else {
                Slog.d("ActivityManager", "get ExtraHomeHub is null");
              }
            }
          }
        },
        intentFilter);
  }

  public void setCameraManagerCallback() {
    CameraKillModeManager cameraKillModeManager = CameraKillModeManager.getInstance();
    this.mCameraKillModeManager = cameraKillModeManager;
    cameraKillModeManager.initCameraKillModeManager(this.mContext, this.mAm.mHandler, this);
  }

  public void setLmkdProtectFlagAndCameraProc(ProcessRecord processRecord) {
    this.mBGProtectManager.setLmkdProtectFlag(processRecord);
    addCamListIfIsCameraProcess(processRecord.processName, processRecord.getPid());
  }

  public void initLMKTh() {
    this.mProcessList.updateLMKThreshold();
  }

  public void clearRecentActivityProcess() {
    this.mBGProtectManager.clearRecentActivityProcess();
  }

  public void addRecentActivityProcess(ProcessRecord processRecord) {
    this.mBGProtectManager.addRecentActivityProcess(processRecord);
  }

  public void addCamListIfIsCameraProcess(String str, int i) {
    CameraKillModeManager cameraKillModeManager = this.mCameraKillModeManager;
    if (cameraKillModeManager != null) {
      cameraKillModeManager.addCamListIfIsCameraProcess(str, i);
    }
  }

  public void activeLaunchKillCheck(ProcessRecord processRecord) {
    switch (processRecord.mState.getCurProcState()) {
      case 16:
      case 17:
      case 18:
        break;
      default:
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = processRecord.activeLaunchTime;
        long j2 = elapsedRealtime - j;
        if (j != -1 && j2 > 3000 && mTotalMemMb <= 11000) {
          if (processRecord.mState.getSetAdj() >= 860) {
            processRecord.killLocked("AL_Kill : over 3 sec", 13, true);
            if (DEBUG) {
              Slog.i("ActivityManager", "AL_Kill : over 3 sec: " + j2);
            }
          }
          processRecord.setActiveLaunch(false);
          processRecord.setActiveLaunchTime(-1L);
          break;
        } else if (this.ActiveLaunchCount < this.ActiveLaunchLimit) {
          this.ActiveLaunchSlot.add(0, processRecord);
          this.ActiveLaunchCount++;
          if (DEBUG) {
            Slog.i(
                "ActivityManager",
                "Active App Launch process < 2 : "
                    + processRecord.processName
                    + ", "
                    + processRecord.isActiveLaunch()
                    + ", "
                    + this.ActiveLaunchCount
                    + ", "
                    + processRecord.activeLaunchTime);
            break;
          }
        } else {
          if (processRecord.mState.getSetAdj() >= 860) {
            processRecord.killLocked("AL_Kill : over 1 slots", 13, true);
          }
          processRecord.setActiveLaunch(false);
          processRecord.setActiveLaunchTime(-1L);
          break;
        }
        break;
    }
  }

  public boolean destroyKeptProcessActivity(ProcessRecord processRecord, int i, int i2) {
    int curProcState = processRecord.mState.getCurProcState();
    if (curProcState != 16 && curProcState != 17) {
      return false;
    }
    if (i + 1 > i2 && processRecord.dhaKeepEmptyFlag == 2) {
      Slog.i("ActivityManager", "PWHL_KNOX - " + processRecord.processName + " in Cached");
      ActivityTaskManagerInternal activityTaskManagerInternal = this.mAm.mAtmInternal;
      if (activityTaskManagerInternal != null) {
        activityTaskManagerInternal.scheduleDestroyAllActivities(
            processRecord.getPid(), processRecord.uid, "Convert from hidden to empty knox");
      }
    }
    return true;
  }

  public void initActiveLaunchParam() {
    ArrayList arrayList = this.ActiveLaunchSlot;
    if (arrayList == null) {
      this.ActiveLaunchSlot = new ArrayList();
    } else {
      arrayList.clear();
    }
    this.ActiveLaunchCount = 0;
  }

  public void initMLLaunchCountParam() {
    ArrayList arrayList = this.MlLaunchSlot;
    if (arrayList == null) {
      this.MlLaunchSlot = new ArrayList();
    } else {
      arrayList.clear();
    }
    this.MlLaunchCount = 0;
  }

  public void killTimeOverEmptyProcess(ProcessRecord processRecord, int i, long j) {
    long uptimeMillis = SystemClock.uptimeMillis();
    if (processRecord.mState.getCurProcState() != 19
        || i <= this.mConstants.CUR_TRIM_EMPTY_PROCESSES
        || processRecord.getLastActivityTime() >= j) {
      return;
    }
    processRecord.killLocked(
        "empty for " + ((uptimeMillis - processRecord.getLastActivityTime()) / 1000) + "s",
        "empty for too long",
        13,
        4,
        true);
  }

  public void initDhaProcessesLocked() {
    initActiveLaunchParam();
    initMLLaunchCountParam();
  }

  public boolean isBEKCondition(ProcessRecord processRecord) {
    return this.mBGProtectManager.isBEKCondition(processRecord);
  }

  public boolean isForceKillHeavyCondition(ProcessRecord processRecord, int i) {
    return this.mBGProtectManager.isForceKillHeavyCondition(processRecord, i);
  }

  public void isHomeHubMode(Bundle bundle, String str) {
    Slog.i(
        "ActivityManager_HOME_HUB", "get action default home change, extra home hub mode intent");
    for (String str2 : bundle.keySet()) {
      Object obj = bundle.get(str2);
      if (obj == null) {
        Slog.i("ActivityManager_HOME_HUB", str2 + " - value is null");
      } else if (!BgAppPropManager.getSystemPropertyString("ro.product.model", "")
          .equals("SM-T270")) {
        Slog.i("ActivityManager_HOME_HUB", str2 + " - value is null");
      } else if (str.equals("com.samsung.android.homehub.action.DEFAULT_HOME_CHANGE")
          && str2.equals("home_hub_mode")) {
        Slog.i("ActivityManager_HOME_HUB", str2 + " - value is " + obj.toString());
        this.HomeHubState = obj.toString().equals("true");
      } else if (str.equals("android.intent.action.DOCK_EVENT")
          && str2.equals("android.intent.extra.DOCK_STATE")) {
        Slog.i("ActivityManager_HOME_HUB", str2 + " - value is " + obj.toString());
        this.HomeHubState = obj.toString().equals("1");
      }
    }
    if (this.HomeHubState) {
      BGSlotManager bGSlotManager = this.mBGSlotManager;
      if (bGSlotManager != null) {
        bGSlotManager.setHomeHubBGSlot();
      }
      Slog.i(
          "ActivityManager_HOME_HUB", "update cached,empty max slot due to launcher change to HH");
      sHH_AMSExceptionEnable = true;
      return;
    }
    BGSlotManager bGSlotManager2 = this.mBGSlotManager;
    if (bGSlotManager2 != null) {
      bGSlotManager2.restoreFromHomeHubBGSlot();
    }
    Slog.i(
        "ActivityManager_HOME_HUB", "update cached,empty max slot due to launcher change from HH");
    sHH_AMSExceptionEnable = false;
  }

  public void setAllowListCleared(boolean z) {
    BGProtectManager.allowListCleared = z;
  }

  public boolean IsAllowListCleared() {
    return BGProtectManager.allowListCleared;
  }

  public int appIsPickedProcess(String str, int i) {
    return this.mBGProtectManager.appIsPickedProcess(str, i);
  }

  public boolean isProtectedInChimera(ProcessRecord processRecord) {
    return this.mBGProtectManager.isProtectedInChimera(processRecord);
  }

  public boolean IsForceKillHeavyProcess(String str) {
    return this.mBGProtectManager.IsForceKillHeavyProcess(str);
  }

  public void addAllowlistList(boolean z) {
    this.mBGProtectManager.addAllowlistList(z);
  }

  public void removeAllowlistByBUB() {
    this.mBGProtectManager.removeAllowlistByBUB();
  }

  public void resetKillExceptFlag(ProcessRecord processRecord) {
    this.mBGProtectManager.resetKillExceptFlag(processRecord);
  }

  public void updatePickedProcessLists(List list) {
    this.mBGProtectManager.updatePickedProcessLists(list);
  }

  public void updateNapProcessProtection(ProcessRecord processRecord) {
    this.mBGProtectManager.updateNapProcessProtection(processRecord);
  }

  public int checkKeptProcess(ProcessRecord processRecord) {
    return this.mBGProtectManager.checkKeptProcess(processRecord);
  }

  public int setCustomADJAndGetProcState(ProcessRecord processRecord) {
    return this.mBGProtectManager.setCustomADJAndGetProcState(processRecord);
  }

  public void doDhaBoosterOn(String str) {
    if (LMKD_REENTRY_MODE_ENABLE) {
      long uptimeMillis = SystemClock.uptimeMillis();
      if (uptimeMillis - lastTime < 120000) {
        if (!reentryMap.containsKey(str)) {
          reentryCount++;
          reentryMap.put(str, 1);
        }
        if (!this.isReentryMode && reentryCount >= 15) {
          ProcessList.setLmkdReentryMode();
          this.mBGSlotManager.setReentryModeBGSlot();
          lastStartTime = SystemClock.uptimeMillis();
          this.isReentryMode = true;
          Slog.d("ActivityManager", "Enable ReentryMode");
        }
      } else {
        reentryMap.clear();
        reentryCount = 0;
        this.isReentryMode = false;
        lastTime = SystemClock.uptimeMillis();
      }
      long j = lastStartTime;
      if (j != 0 && uptimeMillis - j >= 120000) {
        this.mBGSlotManager.restoreFromReentryModeBGSlot();
        lastStartTime = 0L;
      }
    }
    this.mCustomEFKManager.runDecEFKBoost(str);
  }

  public static String decodeToStr(String str) {
    Base64.Decoder decoder = sPkgDecoder;
    if (decoder != null && str != null) {
      try {
        return new String(decoder.decode(str));
      } catch (IllegalArgumentException unused) {
      }
    }
    return "";
  }

  public void setLmkdCameraKillBoost(int i, int i2, int i3) {
    ProcessList.setLmkdCameraKillBoost(i, i2, i3);
  }

  public long getMemLevel(int i) {
    ProcessList processList = this.mProcessList;
    if (processList != null) {
      return processList.getMemLevel(i);
    }
    return 0L;
  }

  public void updateMaxCachedProcessesNumFHA(int i) {
    ActivityManagerConstants activityManagerConstants = this.mConstants;
    if (activityManagerConstants != null) {
      activityManagerConstants.updateMaxCachedProcessesNumFHA(i);
    }
  }

  public void updateEmptyRate(float f) {
    ActivityManagerConstants activityManagerConstants = this.mConstants;
    if (activityManagerConstants != null) {
      activityManagerConstants.updateEmptyRate(f);
    }
  }

  public void updateMaxCachedProcessesNum(int i, float f) {
    ActivityManagerConstants.DEFAULT_MAX_CACHED_PROCESSES = i;
    ActivityManagerConstants.EMPTY_RATE = f;
    ActivityManagerConstants activityManagerConstants = this.mConstants;
    if (activityManagerConstants != null) {
      activityManagerConstants.updateMaxCachedProcessesNum();
    }
  }

  public void setCurTrimProcesses(int i, int i2) {
    ActivityManagerConstants activityManagerConstants = this.mConstants;
    if (activityManagerConstants != null) {
      activityManagerConstants.setCurTrimCachedProcesses(i);
      this.mConstants.setCurTrimEmptyProcesses(i2);
    }
  }

  public void printAmcCachedEmpty(PrintWriter printWriter) {
    if (this.mConstants != null) {
      printWriter.println("  AMC_CUR_MAX_CACHED: " + this.mConstants.CUR_MAX_CACHED_PROCESSES);
      printWriter.println("  AMC_CUR_MAX_EMPTY: " + this.mConstants.CUR_MAX_EMPTY_PROCESSES);
    }
  }

  public void printLowMemDectectorEnable(PrintWriter printWriter) {
    if (this.mAm != null) {
      printWriter.println(
          "  LOWMEMDETECTOR_ENABLE " + this.mAm.mAppProfiler.getLowMemDetectorIsAvailable());
    }
  }

  public void printAppCompactorEnable(PrintWriter printWriter) {
    if (this.mAm != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("  APPCOMPACTOR_ENABLE ");
      CachedAppOptimizer cachedAppOptimizer = this.mAm.mOomAdjuster.mCachedAppOptimizer;
      sb.append(false);
      printWriter.println(sb.toString());
    }
  }

  public void dumpMLList(PrintWriter printWriter) {
    this.mBGProtectManager.dumpMLList(printWriter);
  }

  public void dumpLMKDParameter(PrintWriter printWriter) {
    BgAppPropManager bgAppPropManager = this.mBgAppPropManager;
    if (bgAppPropManager != null) {
      bgAppPropManager.dumpLMKDParameter(printWriter);
    }
  }

  public void updateParamsFile() {
    BgAppPropManager bgAppPropManager = this.mBgAppPropManager;
    if (bgAppPropManager != null) {
      bgAppPropManager.updateParamsFile();
    }
  }

  public void updateParamsIntent(Bundle bundle) {
    BgAppPropManager bgAppPropManager = this.mBgAppPropManager;
    if (bgAppPropManager != null) {
      bgAppPropManager.updateParamsIntent(bundle);
    }
  }

  public boolean setKpmParams(String str, String str2) {
    if ("ro.slmk.kpm_debug_trigger".equals(str)) {
      KillPolicyManager.sWarmUpTrigger = Integer.parseInt(str2);
      return true;
    }
    if ("ro.slmk.kpm_policy_trigger".equals(str)) {
      KillPolicyManager.sPolicyTrigger = Integer.parseInt(str2);
      return true;
    }
    if ("ro.slmk.kpm_debug_cycles".equals(str)) {
      KillPolicyManager.sWarmUpCycles = Integer.parseInt(str2);
      return true;
    }
    if (!"ro.slmk.kpm_boot_enable".equals(str)) {
      return false;
    }
    KillPolicyManager.KPM_BTIME_ENABLE = Boolean.parseBoolean(str2);
    return true;
  }

  public void bootBGSlotSettingTimer() {
    if (!"true".equals(BgAppPropManager.getSystemPropertyString("ro.product_ship", "false"))) {
      updateParamsFile();
    }
    this.mCustomEFKManager.runBootEFKBoost();
    this.mBGSlotManager.runSetBonusMaxCachedAppsPerSwapTimer();
  }

  public int getCachedMax() {
    return BGSlotManager.MAX_CACHED_APPS;
  }

  public int getEmptyMax() {
    return BGSlotManager.MAX_EMPTY_APPS;
  }

  public boolean isHomeHubState() {
    return this.HomeHubState;
  }

  public void setTaskSnapshot(int i, int i2) {
    BGSlotManager bGSlotManager = this.mBGSlotManager;
    if (bGSlotManager != null) {
      bGSlotManager.setTaskSnapshot(i, i2);
    }
  }

  public void setBGSlotByRes(int i, int i2) {
    BGSlotManager bGSlotManager = this.mBGSlotManager;
    if (bGSlotManager != null) {
      bGSlotManager.setBGSlotByRes(i, i2);
    }
  }

  public CustomEFKManager getCustomEFKManagerInstance() {
    return this.mCustomEFKManager;
  }

  public MemInfoGetter getMemInfoGetterInstance() {
    return this.mInfo;
  }

  public BGProtectManager getBGProtectManagerInstance() {
    return this.mBGProtectManager;
  }

  public BGSlotManager getBGSlotManagerInstance() {
    return this.mBGSlotManager;
  }
}
