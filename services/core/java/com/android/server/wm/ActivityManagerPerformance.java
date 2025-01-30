package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.BoostFramework;
import android.util.Slog;
import com.android.server.ServiceThread;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.am.FreecessController;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class ActivityManagerPerformance {
  public static boolean AMP_ENABLE;
  public static boolean AMP_PERF_ENABLE;
  public static boolean AMP_RELAUNCH_RESUME_ON;
  public static boolean DEBUG;
  public static boolean DEBUG_TRACE;
  public static boolean NOT_USER_BINARY;
  public static int TIMEOUT_ACT_RESUME;
  public static int TIMEOUT_ACT_START;
  public static int TIMEOUT_APP_SWITCH;
  public static int TIMEOUT_PREV_RESUME;
  public static final String[] WALLPAPER_PROFILE;
  public static ActivityManagerPerformance booster;
  public static ActivityRecord curTopAct;
  public static int curTopState;
  public static final String[] gBlockedPkgs;
  public static final String[] gHotLaunchBoosterPkgs;
  public static final String[] gSystemuiPkgs;
  public static boolean isChinaModel;
  public static boolean isPerfReserveSupport;
  public static long lastMultiWindowWorkTime;
  public static final String[] mAppLaunchPackagesTimeOutLM;
  public static final String[] mAppLaunchPackagesTimeOutM;
  public static DynamicHiddenApp mDynamicHiddenApp;
  public static long mFoldListenedTime;
  public static final Object mLockinit;
  public static Base64.Decoder pkgDecoder;
  public static ActivityRecord prevSwitchActivity;
  public static final String[] sLowPerformancePkgList;
  public static HashSet sLowPerformancePkgSet;
  public boolean isMultiWindowResume;
  public long lastHomeBoostedTime;
  public long lastHomePressedTime;
  public SemDvfsManager mBoosterActResume;
  public SemDvfsManager mBoosterActStart;
  public SemDvfsManager mBoosterAppLaunch;
  public SemDvfsManager mBoosterAppSwitch;
  public SemDvfsManager mBoosterHome;
  public SemDvfsManager mBoosterLazy;
  public SemDvfsManager mBoosterPrevResume;
  public SemDvfsManager mBoosterRelaunchResume;
  public SemDvfsManager mBoosterTail;
  public final Context mContext;
  public final MainHandler mHandler;
  public final ServiceThread mHandlerThread;
  public boolean mIsTaskBoostExist;
  public SemDvfsManager mLuckyMoneyBooster;
  public final ActivityTaskManagerService mService;
  public SemDvfsManager mTaskBoostManager;
  public boolean needSkipResume;
  public ActivityRecord rCurBoostActResume;
  public ActivityRecord rCurBoostActStart;
  public ActivityRecord rCurBoostAppSwitch;
  public ActivityRecord rLastActHome;
  public ActivityRecord rLastActTail;
  public ActivityRecord rLastRelaunchResume;
  public boolean mIsSdhmsInitCompleted = false;
  public boolean mIsMidGroundCpuSetEnable = false;
  public boolean mIsFolded = false;
  public DeviceStateManager mDeviceStateManager = null;
  public final DeviceStateManager.DeviceStateCallback mDeviceStateCallback =
      new DeviceStateManager
          .DeviceStateCallback() { // from class: com.android.server.wm.ActivityManagerPerformance.1
        public void onStateChanged(int i) {
          ActivityManagerPerformance.this.mIsFolded = i == 0;
          ActivityManagerPerformance.mFoldListenedTime = SystemClock.uptimeMillis();
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "onDisplayFoldChanged: state = " + i);
          }
        }
      };
  public Object mLockActResume = new Object();
  public Object mLockActStart = new Object();
  public Object mLockAppSwitch = new Object();
  public Object mLockTail = new Object();
  public Object mLockHome = new Object();
  public Object mLockRelaunchResume = new Object();
  public Object mLockAppLaunch = new Object();
  public boolean mIsScreenOn = true;
  public final BroadcastReceiver mIntentReceiver =
      new BroadcastReceiver() { // from class: com.android.server.wm.ActivityManagerPerformance.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (action.equals("android.intent.action.SCREEN_OFF")) {
            if (ActivityManagerPerformance.DEBUG) {
              Slog.d("ActivityManagerPerformance", "Screen state changed. mIsScreenOn: false");
            }
            ActivityManagerPerformance.this.mIsScreenOn = false;
          } else {
            if (action.equals("android.intent.action.SCREEN_ON")) {
              if (ActivityManagerPerformance.DEBUG) {
                Slog.d("ActivityManagerPerformance", "Screen state changed. mIsScreenOn: true");
              }
              ActivityManagerPerformance.this.mIsScreenOn = true;
              ActivityManagerPerformance.this.needSkipResume = true;
              return;
            }
            if (action.equals("com.sec.android.sdhms.action.INIT_COMPLETED")) {
              if (ActivityManagerPerformance.DEBUG) {
                Slog.d("ActivityManagerPerformance", "SDHMS INIT_COMPLETED");
              }
              ActivityManagerPerformance.this.mIsSdhmsInitCompleted = true;
            }
          }
        }
      };

  static {
    boolean z = true;
    boolean z2 =
        ("user".equals(Build.TYPE)
                && "true".equals(SystemProperties.get("ro.product_ship", "false")))
            ? false
            : true;
    NOT_USER_BINARY = z2;
    if (!z2 && !"true".equals(SystemProperties.get("sys.config.amp_debug", "false"))) {
      z = false;
    }
    DEBUG = z;
    DEBUG_TRACE = "true".equals(SystemProperties.get("sys.config.amp_debug_trace", "false"));
    AMP_ENABLE = "true".equals(SystemProperties.get("sys.config.amp_enable", "true"));
    AMP_PERF_ENABLE = "true".equals(SystemProperties.get("sys.config.amp_perf_enable", "true"));
    AMP_RELAUNCH_RESUME_ON =
        "true".equals(SystemProperties.get("sys.config.amp_relaunch_resume", "true"));
    TIMEOUT_ACT_RESUME =
        Integer.parseInt(SystemProperties.get("sys.config.amp_to_act_resume", "1000"));
    TIMEOUT_ACT_START =
        Integer.parseInt(SystemProperties.get("sys.config.amp_to_act_start", "2000"));
    TIMEOUT_APP_SWITCH =
        Integer.parseInt(SystemProperties.get("sys.config.amp_to_app_switch", "3000"));
    TIMEOUT_PREV_RESUME =
        Integer.parseInt(SystemProperties.get("sys.config.amp_to_prev_resume", "3000"));
    isChinaModel =
        ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(
            SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
    mDynamicHiddenApp = null;
    pkgDecoder = Base64.getDecoder();
    sLowPerformancePkgList =
        new String[] {
          m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), m113x("Y29tLmFuZHJvaWQuY2hyb21l")
        };
    mAppLaunchPackagesTimeOutLM =
        new String[] {
          m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="),
          m113x("Y29tLnNhbXN1bmcuYW5kcm9pZC5ob21laHVi"),
          m113x("Y29tLm5obi5hbmRyb2lkLnNlYXJjaA=="),
          m113x("Y29tLmdvb2dsZS5hbmRyb2lkLmdt"),
          m113x("Y29tLnNhbXN1bmcuYW5kcm9pZC5lbWFpbC51aQ=="),
          m113x("Y29tLmFuZHJvaWQudmVuZGluZw=="),
          m113x("Y29tLnNhbXN1bmcuZXZlcmdsYWRlcy52aWRlbw=="),
          m113x("Y29tLnNhbXN1bmcuYW5kcm9pZC52aWRlbw=="),
          m113x("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="),
          m113x("Y29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWFwcw=="),
          m113x("Y29tLmJhaWR1LmFwcHNlYXJjaA=="),
          m113x("Y29tLnNpbmEud2VpYm8="),
          m113x("Y29tLmJhaWR1LkJhaWR1TWFw"),
          m113x("Y29tLnR3aXR0ZXIuYW5kcm9pZA==")
        };
    mAppLaunchPackagesTimeOutM = new String[] {m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE=")};
    gHotLaunchBoosterPkgs =
        new String[] {
          m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="),
          m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg==")
        };
    gBlockedPkgs = new String[] {m113x("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ==")};
    isPerfReserveSupport = false;
    lastMultiWindowWorkTime = SystemClock.uptimeMillis();
    mFoldListenedTime = SystemClock.uptimeMillis();
    mLockinit = new Object();
    prevSwitchActivity = null;
    WALLPAPER_PROFILE = new String[] {"MidgroundProcess"};
    gSystemuiPkgs =
        new String[] {
          m113x("Y29tLmFuZHJvaWQuc3lzdGVtdWk="), m113x("Y29tLnNlYy5hbmRyb2lkLmRleHN5c3RlbXVp")
        };
  }

  public static int getAppLaunchHintIdByPkg(String str, boolean z) {
    if (str == null) {
      return -999;
    }
    for (String str2 : mAppLaunchPackagesTimeOutM) {
      if (str.contains(str2)) {
        return 27;
      }
    }
    for (String str3 : mAppLaunchPackagesTimeOutLM) {
      if (str.contains(str3)) {
        return z ? 35 : 28;
      }
    }
    return z ? -999 : 32;
  }

  public final void registerFoldStateCallback() {
    if (this.mDeviceStateManager == null) {
      this.mDeviceStateManager =
          (DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class);
    }
    DeviceStateManager deviceStateManager = this.mDeviceStateManager;
    if (deviceStateManager != null) {
      deviceStateManager.registerCallback(
          new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), this.mDeviceStateCallback);
    }
  }

  public final boolean isJustFoldedState() {
    if (!this.mIsFolded || SystemClock.uptimeMillis() - mFoldListenedTime >= 500) {
      return false;
    }
    if (!DEBUG) {
      return true;
    }
    Slog.d("ActivityManagerPerformance", "mIsFolded: true && Folded just now");
    return true;
  }

  public ActivityManagerPerformance(
      ActivityTaskManagerService activityTaskManagerService, Context context) {
    this.mIsTaskBoostExist = false;
    this.mTaskBoostManager = null;
    this.mService = activityTaskManagerService;
    this.mContext = context;
    sLowPerformancePkgSet = new HashSet();
    int i = 0;
    while (true) {
      String[] strArr = sLowPerformancePkgList;
      if (i >= strArr.length) {
        break;
      }
      sLowPerformancePkgSet.add(strArr[i]);
      i++;
    }
    ServiceThread serviceThread = new ServiceThread("AmpHandlerThread", -2, false);
    this.mHandlerThread = serviceThread;
    serviceThread.start();
    this.mHandler = new MainHandler(serviceThread.getLooper());
    isPerfReserveSupport = new File("/proc/perf_reserve").exists();
    registerReceiver();
    SemDvfsManager createInstance = SemDvfsManager.createInstance(this.mContext, "TASK_BOOST");
    this.mTaskBoostManager = createInstance;
    if (createInstance != null) {
      this.mIsTaskBoostExist = createInstance.checkSysfsIdExist(4204048);
    }
    registerFoldStateCallback();
    SluggishDetector.setLockContentionInfo(
        (short) 1000, "thread_name | wait_ms[/0/] | file_name | line_number[/0/] | method_name");
  }

  public static ActivityManagerPerformance getBooster(
      ActivityTaskManagerService activityTaskManagerService, Context context) {
    ActivityManagerPerformance booster2;
    if (activityTaskManagerService == null || context == null) {
      return null;
    }
    synchronized (mLockinit) {
      if (AMP_ENABLE && booster == null) {
        booster = new ActivityManagerPerformance(activityTaskManagerService, context);
      }
      booster2 = getBooster();
    }
    return booster2;
  }

  public static ActivityManagerPerformance getBooster() {
    if (AMP_ENABLE) {
      return booster;
    }
    return null;
  }

  public final class MainHandler extends Handler {
    public MainHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      String str;
      boolean z;
      if (message == null) {}
      Object obj = message.obj;
      ActivityRecord activityRecord = null;
      if (obj != null) {
        ActivityRecord activityRecord2 =
            obj instanceof ActivityRecord ? (ActivityRecord) obj : null;
        String str2 = obj instanceof String ? (String) obj : null;
        z = obj instanceof Boolean ? ((Boolean) obj).booleanValue() : false;
        ActivityRecord activityRecord3 = activityRecord2;
        str = str2;
        activityRecord = activityRecord3;
      } else {
        str = null;
        z = false;
      }
      switch (message.what) {
        case 1:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_DISABLE_APP_SWITCH, r: " + activityRecord);
          }
          ActivityManagerPerformance.this.setBoosterAppSwitch(false, activityRecord);
          break;
        case 2:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_DISABLE_ACT_START, r: " + activityRecord);
          }
          ActivityManagerPerformance.this.setBoosterActStart(false, activityRecord);
          break;
        case 3:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_DISABLE_ACT_RESUME, r: " + activityRecord);
          }
          ActivityManagerPerformance.this.setBoosterActResume(false, activityRecord);
          break;
        case 4:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_ENABLE_ACT_RESUME_TAIL r: " + activityRecord);
          }
          ActivityManagerPerformance.this.setBoosterTail(false, activityRecord);
          break;
        case 6:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance", "Received MSG_CFMS_HINT_AMS_SWITCH pkgName: " + str);
          }
          SemPerfManager.sendCommandToSsrm("AMS_APP_SWITCH", str);
          break;
        case 7:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_CFMS_HINT_AMS_HOME pkgName: " + str);
          }
          SemPerfManager.sendCommandToSsrm("AMS_APP_HOME", str);
          break;
        case 8:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_ENABLE_APP_SWITCH, r: " + activityRecord);
          }
          ActivityManagerPerformance.this.setBoosterAppSwitch(true, activityRecord);
          break;
        case 9:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_OLAF_FREEZE_ON_OFF  value: " + z);
          }
          ActivityManagerPerformance.this.olafFreezer(z);
          break;
        case 10:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_OLAF_FREEZE_CLEAR");
          }
          ActivityManagerPerformance.this.olafFreezer(false);
          break;
        case 11:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_PERF_RESERVE_ON_OFF  value: " + str);
          }
          ActivityManagerPerformance.this.perfReserveControl(str);
          break;
        case 12:
          int intValue = ((Integer) message.obj).intValue();
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_PERF_TASK_BOOST  pid: " + intValue);
          }
          ActivityManagerPerformance.this.taskBoostAcq(intValue);
          break;
        case 13:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_PERF_TASK_BOOST_END");
          }
          ActivityManagerPerformance.this.taskBoostRel();
          break;
        case 14:
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d("ActivityManagerPerformance", "Received MSG_DHA_BOOSTER_ON");
          }
          if (ActivityManagerPerformance.mDynamicHiddenApp == null) {
            ActivityManagerPerformance.mDynamicHiddenApp = DynamicHiddenApp.getInstance();
          }
          ActivityManagerPerformance.mDynamicHiddenApp.doDhaBoosterOn(str);
          break;
        case 15:
          int intValue2 = ((Integer) message.obj).intValue();
          if (ActivityManagerPerformance.DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "Received MSG_ANIMATION_BOOSTER  timeout: " + intValue2);
          }
          SemPerfManager.sendCommandToSsrm("ANIMATION_BOOST", Integer.toString(intValue2));
          break;
      }
    }
  }

  public static String topStateToString(int i) {
    if (i == 0) {
      return "TOP_STATE_INIT";
    }
    if (i == 1) {
      return "TOP_STATE_IS_CREATING";
    }
    if (i == 2) {
      return "TOP_STATE_HOME";
    }
    if (i == 3) {
      return "TOP_STATE_RECENTS_APP";
    }
    if (i == 4) {
      return "TOP_STATE_APP";
    }
    return "NO_STATE_NUM_OF_" + i;
  }

  public static synchronized void notifyMultiWindowChanged(ActivityRecord activityRecord) {
    synchronized (ActivityManagerPerformance.class) {
      if (AMP_ENABLE && curTopAct != activityRecord) {
        if (DEBUG) {
          Slog.d(
              "ActivityManagerPerformance",
              "notifyMultiWindowChanged() focus changed\n[Activity] prev: "
                  + curTopAct
                  + ", cur: "
                  + activityRecord);
          if (DEBUG_TRACE) {
            new Exception().printStackTrace();
          }
        }
        curTopAct = activityRecord;
        lastMultiWindowWorkTime = SystemClock.uptimeMillis();
      }
    }
  }

  public static final boolean isBlockedApp(String str) {
    if (str == null) {
      return false;
    }
    for (String str2 : gBlockedPkgs) {
      if (str.contains(str2)) {
        return true;
      }
    }
    return false;
  }

  public final boolean isSpeg(String str, String str2) {
    if (!CoreRune.SYSFW_APP_SPEG || !"com.samsung.speg".equals(str2)) {
      return false;
    }
    Slog.i("SPEG", "Skipping boost of " + str);
    return true;
  }

  public final boolean checkBoostDisabledByFold() {
    if (CoreRune.SYSPERF_BOOST_DISABLE_WHEN_FOLDED) {
      return this.mService.mWindowManager.isFolded();
    }
    return false;
  }

  public void notifyTaskBoost(int i) {
    MainHandler mainHandler = this.mHandler;
    mainHandler.sendMessage(mainHandler.obtainMessage(12, Integer.valueOf(i)));
  }

  public void notifyAnimationBoost(int i) {
    if (CoreRune.SYSPERF_VI_BOOST) {
      MainHandler mainHandler = this.mHandler;
      mainHandler.sendMessage(mainHandler.obtainMessage(15, Integer.valueOf(i)));
    }
  }

  public static synchronized void notifyCurTopAct(ActivityRecord activityRecord) {
    int i;
    ActivityRecord activityRecord2;
    HashSet hashSet;
    ActivityManagerPerformance activityManagerPerformance;
    ActivityRecord activityRecord3;
    synchronized (ActivityManagerPerformance.class) {
      if (AMP_ENABLE && curTopAct != activityRecord) {
        if (activityRecord == null) {
          i = 1;
        } else if (activityRecord.isActivityTypeHome()) {
          i = 2;
        } else {
          i = activityRecord.isActivityTypeRecents() ? 3 : 4;
        }
        if (DEBUG) {
          String str =
              "notifyCurTopAct() activity changed\n[Activity] prev: "
                  + curTopAct
                  + ", cur: "
                  + activityRecord;
          if (DEBUG_TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("\n[Process] prev: ");
            ActivityRecord activityRecord4 = curTopAct;
            sb.append(activityRecord4 != null ? activityRecord4.processName : null);
            sb.append(", cur: ");
            sb.append(activityRecord != null ? activityRecord.processName : null);
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("\n[Package] prev: ");
            ActivityRecord activityRecord5 = curTopAct;
            sb3.append(activityRecord5 != null ? activityRecord5.packageName : null);
            sb3.append(", cur: ");
            sb3.append(activityRecord != null ? activityRecord.packageName : null);
            str =
                sb3.toString()
                    + "\n[TOP_STATE] prev: "
                    + topStateToString(curTopState)
                    + ", cur: "
                    + topStateToString(i);
          }
          Slog.d("ActivityManagerPerformance", str);
          if (DEBUG_TRACE) {
            new Exception().printStackTrace();
          }
        }
        if (CoreRune.ALLIED_PROC_PROTECTION_LMKD
            && (activityRecord3 = curTopAct) != null
            && activityRecord != null) {
          if (activityRecord.mLaunchSourceType == 4) {
            if (!activityRecord.packageName.equals(activityRecord3.packageName)) {
              synchronized (DynamicHiddenApp.alliedProtectedProcList) {
                ActivityRecord activityRecord6 = activityRecord.resultTo;
                if (activityRecord6 != null) {
                  if (!DynamicHiddenApp.alliedProtectedProcList.contains(
                      activityRecord6.packageName)) {
                    DynamicHiddenApp.alliedProtectedProcList.add(
                        activityRecord.resultTo.packageName);
                  }
                  if (DEBUG) {
                    Slog.d(
                        "ActivityManagerPerformance",
                        activityRecord.resultTo.packageName
                            + " allied process eligble for LMKD kill protect");
                  }
                }
                if (DynamicHiddenApp.alliedProtectedProcList.contains(activityRecord.packageName)) {
                  DynamicHiddenApp.alliedProtectedProcList.remove(activityRecord.packageName);
                }
              }
            }
          } else {
            synchronized (DynamicHiddenApp.alliedProtectedProcList) {
              DynamicHiddenApp.alliedProtectedProcList.clear();
            }
          }
        }
        curTopAct = activityRecord;
        prevSwitchActivity = null;
        int i2 = curTopState;
        if (i2 != i) {
          curTopState = i;
          if (AMP_PERF_ENABLE
              && (activityManagerPerformance = booster) != null
              && activityManagerPerformance.mIsScreenOn) {
            if (i == 2) {
              if (DEBUG) {
                Slog.d("ActivityManagerPerformance", "notifyCurTopAct() call setBoosterHome()");
              }
              booster.setBoosterHome(true, false, activityRecord);
              return;
            }
            if (i2 == 3 && (i == 4 || i == 1)) {
              try {
                ActivityRecord activityRecord7 = activityManagerPerformance.rCurBoostAppSwitch;
                if (activityRecord7 == null
                    || (activityRecord7 != activityRecord
                        && (activityRecord == null
                            || !activityRecord.processName.equals(activityRecord7.processName)))) {
                  ActivityManagerPerformance activityManagerPerformance2 = booster;
                  if (activityManagerPerformance2.isMultiWindowResume && curTopState == 4) {
                    activityManagerPerformance2.isMultiWindowResume = false;
                    if (DEBUG) {
                      Slog.d(
                          "ActivityManagerPerformance",
                          "notifyCurTopAct() skipped. isMultiWindowResume: true");
                    }
                    return;
                  } else {
                    if (DEBUG) {
                      Slog.d(
                          "ActivityManagerPerformance",
                          "notifyCurTopAct() call setBoosterAppSwitch()");
                    }
                    booster.setBoosterAppSwitch(true, activityRecord);
                    return;
                  }
                }
              } catch (NullPointerException e) {
                e.printStackTrace();
                booster.isMultiWindowResume = false;
                return;
              }
            }
          }
        }
        ActivityManagerPerformance activityManagerPerformance3 = booster;
        if (activityManagerPerformance3 != null
            && (activityRecord2 = curTopAct) != null
            && (hashSet = sLowPerformancePkgSet) != null) {
          activityManagerPerformance3.setLowPower(hashSet.contains(activityRecord2.packageName));
        }
      }
    }
  }

  public String getCurBoostInfoStr() {
    String str;
    String str2 =
        "===== ActivityManagerPerformance, getCurBoostInfoStr info =====\nAMP_ENABLE: "
            + AMP_ENABLE
            + ", AMP_PERF_ENABLE: "
            + AMP_PERF_ENABLE
            + ", mIsScreenOn: "
            + this.mIsScreenOn;
    if (AMP_PERF_ENABLE) {
      str =
          str2
              + "\nTIMEOUT_ACT_RESUME: "
              + TIMEOUT_ACT_RESUME
              + ", TIMEOUT_ACT_START: "
              + TIMEOUT_ACT_START
              + ", TIMEOUT_APP_SWITCH: "
              + TIMEOUT_APP_SWITCH;
    } else {
      str = str2 + "\nTIMEOUT_PREV_RESUME: " + TIMEOUT_PREV_RESUME;
    }
    String str3 =
        (str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE)
            + "\n[curTopState] "
            + topStateToString(curTopState);
    StringBuilder sb = new StringBuilder();
    sb.append(str3);
    sb.append("\n[rCurBoostAppSwitch] procName: ");
    ActivityRecord activityRecord = this.rCurBoostAppSwitch;
    sb.append(activityRecord != null ? activityRecord.processName : null);
    sb.append(" (");
    sb.append(this.rCurBoostAppSwitch);
    sb.append(")");
    String sb2 = sb.toString();
    StringBuilder sb3 = new StringBuilder();
    sb3.append(sb2);
    sb3.append("\n[rCurBoostActStart] procName: ");
    ActivityRecord activityRecord2 = this.rCurBoostActStart;
    sb3.append(activityRecord2 != null ? activityRecord2.processName : null);
    sb3.append(" (");
    sb3.append(this.rCurBoostActStart);
    sb3.append(")");
    String sb4 = sb3.toString();
    StringBuilder sb5 = new StringBuilder();
    sb5.append(sb4);
    sb5.append("\n[rCurBoostActResume] procName: ");
    ActivityRecord activityRecord3 = this.rCurBoostActResume;
    sb5.append(activityRecord3 != null ? activityRecord3.processName : null);
    sb5.append(" (");
    sb5.append(this.rCurBoostActResume);
    sb5.append(")");
    String sb6 = sb5.toString();
    StringBuilder sb7 = new StringBuilder();
    sb7.append(sb6);
    sb7.append("\n[rLastActTail] procName: ");
    ActivityRecord activityRecord4 = this.rLastActTail;
    sb7.append(activityRecord4 != null ? activityRecord4.processName : null);
    sb7.append(" (");
    sb7.append(this.rLastActTail);
    sb7.append(")");
    String sb8 = sb7.toString();
    StringBuilder sb9 = new StringBuilder();
    sb9.append(sb8);
    sb9.append("\n[rLastActHome] procName: ");
    ActivityRecord activityRecord5 = this.rLastActHome;
    sb9.append(activityRecord5 != null ? activityRecord5.processName : null);
    sb9.append(" (");
    sb9.append(this.rLastActHome);
    sb9.append(")");
    String sb10 = sb9.toString();
    StringBuilder sb11 = new StringBuilder();
    sb11.append(sb10);
    sb11.append("\n[rLastRelaunchResume] procName: ");
    ActivityRecord activityRecord6 = this.rLastRelaunchResume;
    sb11.append(activityRecord6 != null ? activityRecord6.processName : null);
    sb11.append(" (");
    sb11.append(this.rLastRelaunchResume);
    sb11.append(")");
    return sb11.toString() + "\n===== ActivityManagerPerformance, getCurBoostInfoStr end  =====";
  }

  /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setBoosterActResume(boolean z, ActivityRecord activityRecord) {
    String str;
    ActivityRecord activityRecord2;
    String str2 = activityRecord != null ? activityRecord.packageName : null;
    if (DEBUG) {
      String str3 = activityRecord != null ? activityRecord.processName : null;
      try {
        activityRecord2 = this.rCurBoostActResume;
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
      if (activityRecord2 != null) {
        str = activityRecord2.processName;
        Slog.d(
            "ActivityManagerPerformance",
            "setBoosterActResume() onoff: "
                + z
                + ", r: "
                + str3
                + " ("
                + activityRecord
                + "), curBoost: "
                + str
                + " ("
                + this.rCurBoostActResume
                + ")");
        if (DEBUG_TRACE) {
          Slog.d(
              "ActivityManagerPerformance", "setBoosterActResume() Trace\n" + getCurBoostInfoStr());
          new Exception().printStackTrace();
        }
      }
      str = null;
      Slog.d(
          "ActivityManagerPerformance",
          "setBoosterActResume() onoff: "
              + z
              + ", r: "
              + str3
              + " ("
              + activityRecord
              + "), curBoost: "
              + str
              + " ("
              + this.rCurBoostActResume
              + ")");
      if (DEBUG_TRACE) {}
    }
    if (this.mBoosterActResume == null) {
      this.mBoosterActResume = SemDvfsManager.createInstance(this.mContext, "AMS_ACT_RESUME", 21);
    }
    SemDvfsManager semDvfsManager = this.mBoosterActResume;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterActResume() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    if (z) {
      int i =
          (activityRecord == null || !activityRecord.isActivityTypeHome())
              ? TIMEOUT_ACT_RESUME
              : 500;
      this.mHandler.removeMessages(3);
      try {
        synchronized (this.mLockActResume) {
          semDvfsManager.acquire();
          this.rCurBoostActResume = activityRecord;
        }
        Slog.d("ActivityManagerPerformance", "AMP_acquire() ACT_RESUME");
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, activityRecord), i);
        this.mHandler.removeMessages(14);
        MainHandler mainHandler = this.mHandler;
        mainHandler.sendMessage(mainHandler.obtainMessage(14, str2));
        return;
      } catch (Exception e2) {
        ActivityRecord activityRecord3 = this.rCurBoostActResume;
        String str4 = activityRecord3 != null ? activityRecord3.processName : null;
        Slog.e("ActivityManagerPerformance", "AMP_acquire() ACT_RESUME failed. stop boosting");
        if (DEBUG) {
          Slog.e(
              "ActivityManagerPerformance",
              "AMP_acquire() ACT_RESUME failed["
                  + str4
                  + "] e: "
                  + e2
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                  + getCurBoostInfoStr());
        }
        setBoosterActResume(false, this.rCurBoostActResume);
        e2.printStackTrace();
        return;
      }
    }
    try {
      synchronized (this.mLockActResume) {
        if (this.rCurBoostActResume == null) {
          return;
        }
        semDvfsManager.release();
        this.rCurBoostActResume = null;
        Slog.d("ActivityManagerPerformance", "AMP_release() ACT_RESUME");
        this.mHandler.removeMessages(3);
      }
    } catch (Exception e3) {
      Slog.e("ActivityManagerPerformance", "AMP_release() ACT_RESUME failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "AMP_release() ACT_RESUME failed. e: "
                + e3
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e3.printStackTrace();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0059  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setBoosterActStart(boolean z, ActivityRecord activityRecord) {
    ActivityInfo activityInfo;
    String str;
    String str2;
    ActivityRecord activityRecord2;
    if (DEBUG) {
      String str3 = activityRecord != null ? activityRecord.processName : null;
      try {
        activityRecord2 = this.rCurBoostActStart;
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
      if (activityRecord2 != null) {
        str2 = activityRecord2.processName;
        Slog.d(
            "ActivityManagerPerformance",
            "setBoosterActStart() onoff: "
                + z
                + ", r: "
                + str3
                + " ("
                + activityRecord
                + "), curBoost: "
                + str2
                + " ("
                + this.rCurBoostActStart
                + ")");
        if (DEBUG_TRACE) {
          Slog.d(
              "ActivityManagerPerformance", "setBoosterActStart() Trace\n" + getCurBoostInfoStr());
          new Exception().printStackTrace();
        }
      }
      str2 = null;
      Slog.d(
          "ActivityManagerPerformance",
          "setBoosterActStart() onoff: "
              + z
              + ", r: "
              + str3
              + " ("
              + activityRecord
              + "), curBoost: "
              + str2
              + " ("
              + this.rCurBoostActStart
              + ")");
      if (DEBUG_TRACE) {}
    }
    if (checkBoostDisabledByFold() && z) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterActStart() skipped. Device folded : true");
        return;
      }
      return;
    }
    if (isChinaModel
        && activityRecord != null
        && (activityInfo = activityRecord.info) != null
        && (str = activityInfo.name) != null
        && str.contains("LuckyMoneyReceiveUI")) {
      if (z) {
        if (this.mLuckyMoneyBooster == null) {
          SemDvfsManager createInstance =
              SemDvfsManager.createInstance(this.mContext, "LuckyMoneyBooster", 12);
          this.mLuckyMoneyBooster = createInstance;
          if (createInstance != null) {
            int[] supportedFrequency = createInstance.getSupportedFrequency();
            if (supportedFrequency != null) {
              this.mLuckyMoneyBooster.setDvfsValue(supportedFrequency[0]);
            } else {
              Slog.e(
                  "ActivityManagerPerformance",
                  "setBoosterActStart() skipped. Hongbao getSupportedFrequency() failed."
                      + " AMP_PERF_ENABLE: "
                      + AMP_PERF_ENABLE);
              return;
            }
          } else {
            Slog.e(
                "ActivityManagerPerformance",
                "setBoosterActStart() skipped. Hongbao SemDvfsManager.createInstance() failed."
                    + " AMP_PERF_ENABLE: "
                    + AMP_PERF_ENABLE);
            return;
          }
        }
        this.mLuckyMoneyBooster.acquire(5000);
        return;
      }
      return;
    }
    boolean z2 = AMP_PERF_ENABLE;
    if (z2 && this.mBoosterActStart == null) {
      this.mBoosterActStart = SemDvfsManager.createInstance(this.mContext, "AMS_ACT_START", 21);
    } else if (!z2 && this.mBoosterPrevResume == null) {
      this.mBoosterPrevResume = SemDvfsManager.createInstance(this.mContext, "AMS_RESUME", 21);
    }
    SemDvfsManager semDvfsManager =
        AMP_PERF_ENABLE ? this.mBoosterActStart : this.mBoosterPrevResume;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterActStart() skipped. SemDvfsManager.createInstance() failed. AMP_PERF_ENABLE: "
              + AMP_PERF_ENABLE);
      return;
    }
    if (z) {
      int i =
          (activityRecord == null || !activityRecord.isActivityTypeHome())
              ? AMP_PERF_ENABLE ? TIMEOUT_ACT_START : TIMEOUT_PREV_RESUME
              : 500;
      this.mHandler.removeMessages(2);
      try {
        synchronized (this.mLockActStart) {
          semDvfsManager.acquire();
          this.rCurBoostActStart = activityRecord;
        }
        Slog.d("ActivityManagerPerformance", "AMP_acquire() ACT_START");
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, activityRecord), i);
        return;
      } catch (Exception e2) {
        ActivityRecord activityRecord3 = this.rCurBoostActStart;
        String str4 = activityRecord3 != null ? activityRecord3.processName : null;
        Slog.e("ActivityManagerPerformance", "AMP_acquire() ACT_START failed. stop boosting");
        if (DEBUG) {
          Slog.e(
              "ActivityManagerPerformance",
              "AMP_acquire() ACT_START failed. ["
                  + str4
                  + "] e: "
                  + e2
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                  + getCurBoostInfoStr());
        }
        setBoosterActStart(false, this.rCurBoostActStart);
        e2.printStackTrace();
        return;
      }
    }
    setBoosterTail(false, activityRecord);
    try {
      synchronized (this.mLockActStart) {
        if (this.rCurBoostActStart == null) {
          return;
        }
        semDvfsManager.release();
        this.rCurBoostActStart = null;
        Slog.d("ActivityManagerPerformance", "AMP_release() ACT_START");
        this.mHandler.removeMessages(2);
      }
    } catch (Exception e3) {
      Slog.e("ActivityManagerPerformance", "AMP_release() ACT_START failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "AMP_release() ACT_START failed. e: "
                + e3
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e3.printStackTrace();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:17:0x0067  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setBoosterAppSwitch(boolean z, ActivityRecord activityRecord) {
    String str;
    ActivityRecord activityRecord2;
    String str2 = activityRecord != null ? activityRecord.packageName : null;
    ActivityRecord activityRecord3 = prevSwitchActivity;
    String str3 = activityRecord3 != null ? activityRecord3.processName : null;
    if (DEBUG) {
      String str4 = activityRecord != null ? activityRecord.processName : null;
      try {
        activityRecord2 = this.rCurBoostAppSwitch;
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
      if (activityRecord2 != null) {
        str = activityRecord2.processName;
        Slog.d(
            "ActivityManagerPerformance",
            "setBoosterAppSwitch() onoff: "
                + z
                + ", r: "
                + str4
                + " ("
                + activityRecord
                + "), curBoost: "
                + str
                + " ("
                + this.rCurBoostAppSwitch
                + ")");
        if (DEBUG_TRACE) {
          Slog.d(
              "ActivityManagerPerformance", "setBoosterAppSwitch() Trace\n" + getCurBoostInfoStr());
          new Exception().printStackTrace();
        }
      }
      str = null;
      Slog.d(
          "ActivityManagerPerformance",
          "setBoosterAppSwitch() onoff: "
              + z
              + ", r: "
              + str4
              + " ("
              + activityRecord
              + "), curBoost: "
              + str
              + " ("
              + this.rCurBoostAppSwitch
              + ")");
      if (DEBUG_TRACE) {}
    }
    if (z && str2 != null && str2.equals(str3)) {
      if (DEBUG) {
        Slog.d(
            "ActivityManagerPerformance",
            "setBoosterAppSwitch() skipped. Background activity start on abnormal app : " + str3);
        return;
      }
      return;
    }
    if (checkBoostDisabledByFold() && z) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterAppSwitch() skipped. Device folded : true");
        return;
      }
      return;
    }
    if (this.mBoosterAppSwitch == null) {
      this.mBoosterAppSwitch = SemDvfsManager.createInstance(this.mContext, "AMS_APP_SWITCH", 21);
    }
    SemDvfsManager semDvfsManager = this.mBoosterAppSwitch;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterAppSwitch() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    if (z) {
      int i =
          (activityRecord == null || !activityRecord.isActivityTypeHome())
              ? TIMEOUT_APP_SWITCH
              : 500;
      this.mHandler.removeMessages(1);
      try {
        synchronized (this.mLockAppSwitch) {
          semDvfsManager.acquire();
          this.rCurBoostAppSwitch = activityRecord;
          prevSwitchActivity = activityRecord;
        }
        Slog.d("ActivityManagerPerformance", "AMP_acquire() APP_SWITCH");
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, activityRecord), i);
        MainHandler mainHandler = this.mHandler;
        mainHandler.sendMessage(mainHandler.obtainMessage(6, str2));
        this.mHandler.removeMessages(14);
        MainHandler mainHandler2 = this.mHandler;
        mainHandler2.sendMessage(mainHandler2.obtainMessage(14, str2));
        this.mHandler.removeMessages(10);
        MainHandler mainHandler3 = this.mHandler;
        mainHandler3.sendMessageDelayed(mainHandler3.obtainMessage(10), 3500L);
        if (FreecessController.getInstance().getFreecessEnabled()
            && !isMultiWindowScenario(curTopAct, activityRecord)) {
          FreecessController.getInstance().enterOLAF(str2, activityRecord.info.applicationInfo.uid);
        }
        MainHandler mainHandler4 = this.mHandler;
        mainHandler4.sendMessage(mainHandler4.obtainMessage(9, Boolean.TRUE));
        if (isPerfReserveSupport) {
          MainHandler mainHandler5 = this.mHandler;
          mainHandler5.sendMessage(mainHandler5.obtainMessage(11, "120"));
          return;
        }
        return;
      } catch (Exception e2) {
        ActivityRecord activityRecord4 = this.rCurBoostAppSwitch;
        String str5 = activityRecord4 != null ? activityRecord4.processName : null;
        Slog.e("ActivityManagerPerformance", "AMP_acquire() APP_SWITCH failed. stop boosting");
        if (DEBUG) {
          Slog.e(
              "ActivityManagerPerformance",
              "AMP_acquire() APP_SWITCH failed. ["
                  + str5
                  + "] e: "
                  + e2
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                  + getCurBoostInfoStr());
        }
        setBoosterAppSwitch(false, this.rCurBoostAppSwitch);
        e2.printStackTrace();
        return;
      }
    }
    setBoosterTail(true, activityRecord);
    try {
      synchronized (this.mLockAppSwitch) {
        if (this.rCurBoostAppSwitch == null) {
          return;
        }
        semDvfsManager.release();
        this.rCurBoostAppSwitch = null;
        Slog.d("ActivityManagerPerformance", "AMP_release() APP_SWITCH");
        this.mHandler.removeMessages(1);
        if (FreecessController.getInstance().getFreecessEnabled()) {
          FreecessController.getInstance().exitOLAF();
        }
        MainHandler mainHandler6 = this.mHandler;
        mainHandler6.sendMessage(mainHandler6.obtainMessage(9, Boolean.FALSE));
        if (isPerfReserveSupport) {
          MainHandler mainHandler7 = this.mHandler;
          mainHandler7.sendMessage(mainHandler7.obtainMessage(11, "0"));
        }
      }
    } catch (Exception e3) {
      Slog.e("ActivityManagerPerformance", "AMP_release() APP_SWITCH failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "setBoosterAppSwitch() AMP_release failed. e: "
                + e3
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e3.printStackTrace();
    }
  }

  public final void setBoosterTail(boolean z, ActivityRecord activityRecord) {
    if (DEBUG) {
      String str = activityRecord != null ? activityRecord.processName : null;
      StringBuilder sb = new StringBuilder();
      sb.append("setBoosterTail() from ");
      sb.append(z ? "AppSwitch" : "ActStart");
      sb.append(", r: ");
      sb.append(str);
      sb.append(" (");
      sb.append(activityRecord);
      sb.append(")");
      Slog.d("ActivityManagerPerformance", sb.toString());
      if (DEBUG_TRACE) {
        Slog.d("ActivityManagerPerformance", "setBoosterTail() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (checkBoostDisabledByFold()) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterTail() skipped. Device folded : true");
        return;
      }
      return;
    }
    if (!this.mIsScreenOn) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterTail() skipped. mIsScreenOn: false");
        return;
      }
      return;
    }
    if ((z && this.rCurBoostActStart != null) || (!z && this.rCurBoostAppSwitch != null)) {
      if (DEBUG) {
        StringBuilder sb2 = new StringBuilder();
        sb2.append("setBoosterTail() skipped. ");
        sb2.append(!z ? "AppSwitch" : "ActStart");
        sb2.append(" is not finished");
        Slog.d("ActivityManagerPerformance", sb2.toString());
        return;
      }
      return;
    }
    if (this.mBoosterTail == null) {
      this.mBoosterTail = SemDvfsManager.createInstance(this.mContext, "AMS_RESUME_TAIL", 21);
    }
    if (this.mBoosterLazy == null) {
      this.mBoosterLazy = SemDvfsManager.createInstance(this.mContext, "AMS_ACT_LAZY", 21);
    }
    SemDvfsManager semDvfsManager = this.mBoosterTail;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterTail() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    SemDvfsManager semDvfsManager2 = this.mBoosterLazy;
    if (semDvfsManager2 == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterTail() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    try {
      synchronized (this.mLockTail) {
        semDvfsManager.acquire();
        semDvfsManager2.acquire();
        this.rLastActTail = activityRecord;
      }
      Slog.d("ActivityManagerPerformance", "AMP_acquire() TAIL");
    } catch (Exception e) {
      Slog.e("ActivityManagerPerformance", "AMP_acquire() TAIL failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "AMP_acquire() TAIL failed. e: "
                + e
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e.printStackTrace();
    }
  }

  public final void setBoosterHome(boolean z, boolean z2, ActivityRecord activityRecord) {
    String str = activityRecord != null ? activityRecord.packageName : null;
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "setBoosterHome() fastBoost: "
              + z
              + ", startAct: "
              + z2
              + ", curTopState: "
              + topStateToString(curTopState)
              + ", r: "
              + (activityRecord != null ? activityRecord.processName : null)
              + " ("
              + activityRecord
              + "), pkgName : "
              + str);
      if (DEBUG_TRACE) {
        Slog.d("ActivityManagerPerformance", "setBoosterHome() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (curTopState == 0) {
      Slog.w(
          "ActivityManagerPerformance",
          "setBoosterHome() skipped. Method is called by non-system_server");
      return;
    }
    if (!this.mIsScreenOn) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterHome() skipped. mIsScreenOn: false");
        return;
      }
      return;
    }
    if (checkBoostDisabledByFold()) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "setBoosterHome() skipped. Device folded : true");
        return;
      }
      return;
    }
    if (isJustFoldedState()) {
      return;
    }
    long uptimeMillis = SystemClock.uptimeMillis();
    if (z) {
      if (activityRecord == null) {
        this.lastHomePressedTime = uptimeMillis;
      } else if (uptimeMillis - this.lastHomePressedTime < 200
          || uptimeMillis - this.lastHomeBoostedTime < 200) {
        if (this.rLastActHome != activityRecord) {
          this.rLastActHome = activityRecord;
        }
        if (DEBUG) {
          Slog.d("ActivityManagerPerformance", "setBoosterHome() skipped. uninterrupted boosting");
          return;
        }
        return;
      }
      if (activityRecord == null && curTopState == 2) {
        if (DEBUG) {
          Slog.d(
              "ActivityManagerPerformance",
              "setBoosterHome() skipped. Home key was pressed, but curTopState is already "
                  + topStateToString(curTopState));
          return;
        }
        return;
      }
    } else {
      if (uptimeMillis - this.lastHomePressedTime < 200
          || uptimeMillis - this.lastHomeBoostedTime < 200) {
        if (this.rLastActHome != activityRecord && activityRecord != null) {
          this.rLastActHome = activityRecord;
        }
        if (DEBUG) {
          Slog.d("ActivityManagerPerformance", "setBoosterHome() skipped. uninterrupted boosting");
          return;
        }
        return;
      }
      if (curTopState == 2) {
        try {
          ActivityRecord activityRecord2 = this.rCurBoostAppSwitch;
          ActivityRecord activityRecord3 = this.rCurBoostActStart;
          if (activityRecord != null
              && (activityRecord2 == activityRecord
                  || activityRecord3 == activityRecord
                  || ((activityRecord2 != null
                          && activityRecord.processName.equals(activityRecord2.processName))
                      || (activityRecord3 != null
                          && activityRecord.processName.equals(activityRecord3.processName))))) {
            if (DEBUG) {
              Slog.d(
                  "ActivityManagerPerformance",
                  "setBoosterHome() skipped. already boosted " + activityRecord.processName);
              return;
            }
            return;
          }
          if (z2) {
            if (DEBUG) {
              Slog.d("ActivityManagerPerformance", "setBoosterHome() call setBoosterActStart()");
            }
            setBoosterActStart(true, activityRecord);
            return;
          }
          try {
            ActivityRecord activityRecord4 = this.rCurBoostActResume;
            if (activityRecord != null
                && (activityRecord4 == activityRecord
                    || (activityRecord4 != null
                        && activityRecord.processName.equals(activityRecord4.processName)))) {
              if (DEBUG) {
                Slog.d(
                    "ActivityManagerPerformance",
                    "setBoosterHome() skipped. already boosted " + activityRecord.processName);
                return;
              }
              return;
            }
            if (DEBUG) {
              Slog.d("ActivityManagerPerformance", "setBoosterHome() call setBoosterActResume()");
            }
            setBoosterActResume(true, activityRecord);
            return;
          } catch (NullPointerException e) {
            e.printStackTrace();
            return;
          }
        } catch (NullPointerException e2) {
          e2.printStackTrace();
          return;
        }
      }
    }
    if (this.mBoosterHome == null) {
      this.mBoosterHome = SemDvfsManager.createInstance(this.mContext, "AMS_APP_HOME", 21);
    }
    SemDvfsManager semDvfsManager = this.mBoosterHome;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterHome() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    this.lastHomeBoostedTime = uptimeMillis;
    try {
      synchronized (this.mLockHome) {
        semDvfsManager.acquire();
        this.rLastActHome = activityRecord;
      }
      Slog.d("ActivityManagerPerformance", "AMP_acquire() HOME");
    } catch (Exception e3) {
      Slog.e("ActivityManagerPerformance", "AMP_acquire() HOME failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "AMP_acquire() HOME failed. e: "
                + e3
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e3.printStackTrace();
    }
  }

  public final void setBoosterRelaunchResume(ActivityRecord activityRecord) {
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "setBoosterRelaunchResume() r: "
              + (activityRecord != null ? activityRecord.processName : null)
              + " ("
              + activityRecord
              + ")");
      if (DEBUG_TRACE) {
        Slog.d(
            "ActivityManagerPerformance",
            "setBoosterRelaunchResume() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (this.mBoosterRelaunchResume == null) {
      this.mBoosterRelaunchResume =
          SemDvfsManager.createInstance(this.mContext, "AMS_RELAUNCH_RESUME", 21);
    }
    SemDvfsManager semDvfsManager = this.mBoosterRelaunchResume;
    if (semDvfsManager == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "setBoosterRelaunchResume() skipped. SemDvfsManager.createInstance() failed");
      return;
    }
    try {
      synchronized (this.mLockRelaunchResume) {
        semDvfsManager.acquire();
        this.rLastRelaunchResume = activityRecord;
      }
      Slog.d("ActivityManagerPerformance", "AMP_acquire() RELAUNCH_RESUME");
    } catch (Exception e) {
      Slog.e("ActivityManagerPerformance", "AMP_acquire() RELAUNCH_RESUME failed");
      if (DEBUG) {
        Slog.e(
            "ActivityManagerPerformance",
            "AMP_acquire() RELAUNCH_RESUME failed. e: "
                + e
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + getCurBoostInfoStr());
      }
      e.printStackTrace();
    }
  }

  public void writeSysfs(String str, String str2) {
    StringBuilder sb;
    FileOutputStream fileOutputStream;
    File file = new File(str);
    if (!file.exists() || !file.canWrite() || str2 == null) {
      Slog.e(
          "ActivityManagerPerformance",
          "writeSysfs:: path() : "
              + str
              + " exist() : "
              + file.exists()
              + " canWrite() : "
              + file.canWrite());
      return;
    }
    FileOutputStream fileOutputStream2 = null;
    try {
      try {
        fileOutputStream = new FileOutputStream(str);
      } catch (IOException e) {
        e = e;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      fileOutputStream.write(str2.getBytes("UTF-8"));
      fileOutputStream.flush();
      try {
        fileOutputStream.close();
      } catch (IOException e2) {
        e = e2;
        sb = new StringBuilder();
        sb.append("e = ");
        sb.append(e.getMessage());
        Slog.e("ActivityManagerPerformance", sb.toString());
      }
    } catch (IOException e3) {
      e = e3;
      fileOutputStream2 = fileOutputStream;
      Slog.e("ActivityManagerPerformance", "e = " + e.getMessage());
      if (fileOutputStream2 != null) {
        try {
          fileOutputStream2.close();
        } catch (IOException e4) {
          e = e4;
          sb = new StringBuilder();
          sb.append("e = ");
          sb.append(e.getMessage());
          Slog.e("ActivityManagerPerformance", sb.toString());
        }
      }
    } catch (Throwable th2) {
      th = th2;
      fileOutputStream2 = fileOutputStream;
      if (fileOutputStream2 != null) {
        try {
          fileOutputStream2.close();
        } catch (IOException e5) {
          Slog.e("ActivityManagerPerformance", "e = " + e5.getMessage());
        }
      }
      throw th;
    }
  }

  public final void olafFreezer(boolean z) {
    Process.doSomethingOlaf(z);
  }

  public final void perfReserveControl(String str) {
    if (isPerfReserveSupport) {
      writeSysfs("/proc/perf_reserve", str);
    }
  }

  public void onAppLaunch(ActivityRecord activityRecord, boolean z) {
    if (activityRecord == null
        || isSpeg(activityRecord.packageName, activityRecord.launchedFromPackage)
        || isBlockedApp(activityRecord.packageName)
        || isJustFoldedState()) {
      return;
    }
    synchronized (this.mLockAppLaunch) {
      if (this.mBoosterAppLaunch == null) {
        this.mBoosterAppLaunch = SemDvfsManager.createInstance(this.mContext, "AMS_APP_LAUNCH", 21);
      }
      if (this.mBoosterAppLaunch != null) {
        this.mBoosterAppLaunch.setHint(getAppLaunchHintIdByPkg(activityRecord.packageName, z));
        this.mBoosterAppLaunch.acquire();
      }
    }
  }

  public final void taskBoostAcq(int i) {
    if (CoreRune.SYSPERF_QC_TASK_BOOST_ENABLE) {
      new BoostFramework().perfLockAcquire(2000, new int[] {1086849024, i});
    }
    if (this.mTaskBoostManager == null || !this.mIsTaskBoostExist) {
      return;
    }
    this.mHandler.removeMessages(13);
    String num = Integer.toString(i);
    Trace.traceBegin(1L, "taskBoostAcq pid : " + num);
    this.mTaskBoostManager.sysfsWrite(4204048, num);
    Trace.traceEnd(1L);
    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(13), 2000L);
  }

  public final void taskBoostRel() {
    SemDvfsManager semDvfsManager = this.mTaskBoostManager;
    if (semDvfsManager == null || !this.mIsTaskBoostExist) {
      return;
    }
    semDvfsManager.sysfsWrite(4204048, "0");
  }

  public void onActivityStartLocked(ActivityRecord activityRecord) {
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "onActivityStartLocked() r: " + activityRecord.processName + " (" + activityRecord + ")");
      if (DEBUG_TRACE) {
        Slog.d(
            "ActivityManagerPerformance", "onActivityStartLocked() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (!AMP_ENABLE
        || isSpeg(activityRecord.packageName, activityRecord.launchedFromPackage)
        || isBlockedApp(activityRecord.packageName)) {
      return;
    }
    if (this.isMultiWindowResume) {
      this.isMultiWindowResume = false;
    }
    if (!this.mIsScreenOn) {
      if (DEBUG) {
        Slog.d("ActivityManagerPerformance", "onActivityStartLocked() skipped. mIsScreenOn: false");
        return;
      }
      return;
    }
    if (isJustFoldedState()) {
      return;
    }
    if (this.needSkipResume) {
      this.needSkipResume = false;
    }
    if (!AMP_PERF_ENABLE) {
      setBoosterActStart(true, activityRecord);
      return;
    }
    if (activityRecord.isActivityTypeHome()) {
      setBoosterHome(false, true, activityRecord);
      return;
    }
    if (activityRecord.isActivityTypeRecents()) {
      setBoosterActStart(true, activityRecord);
      return;
    }
    ActivityRecord activityRecord2 = curTopAct;
    if (activityRecord2 != null && activityRecord2.processName.equals(activityRecord.processName)) {
      try {
        ActivityRecord activityRecord3 = this.rCurBoostAppSwitch;
        ActivityRecord activityRecord4 = this.rCurBoostActStart;
        if (activityRecord3 == activityRecord
            || activityRecord4 == activityRecord
            || ((activityRecord3 != null
                    && activityRecord.processName.equals(activityRecord3.processName))
                || (activityRecord4 != null
                    && activityRecord.processName.equals(activityRecord4.processName)))) {
          if (DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "onActivityStartLocked() skipped. already boosted " + activityRecord.processName);
            return;
          }
          return;
        }
        setBoosterActStart(true, activityRecord);
        return;
      } catch (NullPointerException e) {
        e.printStackTrace();
        return;
      }
    }
    try {
      ActivityRecord activityRecord5 = this.rCurBoostAppSwitch;
      if (activityRecord5 == activityRecord
          || (activityRecord5 != null
              && activityRecord.processName.equals(activityRecord5.processName))) {
        if (DEBUG) {
          Slog.d(
              "ActivityManagerPerformance",
              "onActivityStartLocked() skipped. already boosted " + activityRecord.processName);
          return;
        }
        return;
      }
      MainHandler mainHandler = this.mHandler;
      mainHandler.sendMessage(mainHandler.obtainMessage(8, activityRecord));
    } catch (NullPointerException e2) {
      e2.printStackTrace();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:100:0x01d0, code lost:

     if (r8.processName.equals(r0.processName) != false) goto L127;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void onActivityResumeLocked(ActivityRecord activityRecord) {
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "onActivityResumeLocked() r: "
              + activityRecord.processName
              + " ("
              + activityRecord
              + ")");
      if (DEBUG_TRACE) {
        Slog.d(
            "ActivityManagerPerformance",
            "onActivityResumeLocked() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (!AMP_ENABLE
        || isSpeg(activityRecord.packageName, activityRecord.launchedFromPackage)
        || isBlockedApp(activityRecord.packageName)) {
      return;
    }
    if (this.isMultiWindowResume) {
      this.isMultiWindowResume = false;
    }
    if (!this.mIsScreenOn) {
      if (DEBUG) {
        Slog.d(
            "ActivityManagerPerformance", "onActivityResumeLocked() skipped. mIsScreenOn: false");
        return;
      }
      return;
    }
    if (isJustFoldedState()) {
      return;
    }
    if (this.needSkipResume) {
      if (DEBUG) {
        Slog.d(
            "ActivityManagerPerformance",
            "onActivityResumeLocked() skipped. needSkipResume is true");
      }
      this.needSkipResume = false;
      return;
    }
    if (!AMP_PERF_ENABLE) {
      try {
        ActivityRecord activityRecord2 = this.rCurBoostActStart;
        if (activityRecord2 == activityRecord
            || (activityRecord2 != null
                && activityRecord.processName.equals(activityRecord2.processName))) {
          if (DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "onActivityResumeLocked() skipped. already boosted " + activityRecord.processName);
            return;
          }
          return;
        }
        setBoosterActStart(true, activityRecord);
        return;
      } catch (NullPointerException e) {
        e.printStackTrace();
        return;
      }
    }
    if (activityRecord.isActivityTypeHome()) {
      setBoosterHome(false, false, activityRecord);
      return;
    }
    if (activityRecord.isActivityTypeRecents()) {
      try {
        ActivityRecord activityRecord3 = this.rCurBoostActResume;
        ActivityRecord activityRecord4 = this.rCurBoostActStart;
        if (activityRecord4 == activityRecord
            || activityRecord3 == activityRecord
            || ((activityRecord4 != null
                    && activityRecord.processName.equals(activityRecord4.processName))
                || (activityRecord3 != null
                    && activityRecord.processName.equals(activityRecord3.processName)))) {
          if (DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "onActivityResumeLocked() skipped. already boosted " + activityRecord.processName);
            return;
          }
          return;
        }
        setBoosterActResume(true, activityRecord);
        return;
      } catch (NullPointerException e2) {
        e2.printStackTrace();
        return;
      }
    }
    ActivityRecord activityRecord5 = curTopAct;
    if (activityRecord5 != null && activityRecord5.processName.equals(activityRecord.processName)) {
      try {
        ActivityRecord activityRecord6 = this.rCurBoostActResume;
        ActivityRecord activityRecord7 = this.rCurBoostActStart;
        ActivityRecord activityRecord8 = this.rCurBoostAppSwitch;
        if (activityRecord8 == activityRecord
            || activityRecord7 == activityRecord
            || activityRecord6 == activityRecord
            || ((activityRecord8 != null
                    && activityRecord.processName.equals(activityRecord8.processName))
                || ((activityRecord7 != null
                        && activityRecord.processName.equals(activityRecord7.processName))
                    || (activityRecord6 != null
                        && activityRecord.processName.equals(activityRecord6.processName))))) {
          if (DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "onActivityResumeLocked() skipped. already boosted " + activityRecord.processName);
            return;
          }
          return;
        }
        setBoosterActResume(true, activityRecord);
        return;
      } catch (NullPointerException e3) {
        e3.printStackTrace();
        return;
      }
    }
    ActivityRecord activityRecord9 = this.rCurBoostAppSwitch;
    if (activityRecord9 == null && this.rCurBoostActStart == null) {
      try {
        ActivityRecord activityRecord10 = this.rCurBoostActResume;
        if (activityRecord10 == activityRecord
            || (activityRecord10 != null
                && activityRecord.processName.equals(activityRecord10.processName))) {
          if (DEBUG) {
            Slog.d(
                "ActivityManagerPerformance",
                "onActivityResumeLocked() skipped. already boosted " + activityRecord.processName);
            return;
          }
          return;
        }
        this.isMultiWindowResume = true;
        setBoosterActResume(true, activityRecord);
        return;
      } catch (NullPointerException e4) {
        e4.printStackTrace();
        return;
      }
    }
    if (activityRecord9 != activityRecord) {
      if (activityRecord9 != null) {
        try {
        } catch (NullPointerException e5) {
          e5.printStackTrace();
          return;
        }
      }
      MainHandler mainHandler = this.mHandler;
      mainHandler.sendMessage(mainHandler.obtainMessage(8, activityRecord));
      return;
    }
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "onActivityResumeLocked() skipped. already boosted " + activityRecord.processName);
    }
  }

  public void onActivityRelaunchLocked(ActivityRecord activityRecord, boolean z) {
    if (DEBUG) {
      Slog.d(
          "ActivityManagerPerformance",
          "onActivityRelaunchLocked() r: "
              + (activityRecord != null ? activityRecord.processName : null)
              + " ("
              + activityRecord
              + "), andResume: "
              + z);
      if (DEBUG_TRACE) {
        Slog.d(
            "ActivityManagerPerformance",
            "onActivityRelaunchLocked() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (AMP_RELAUNCH_RESUME_ON) {
      if (!this.mIsScreenOn) {
        if (DEBUG) {
          Slog.d(
              "ActivityManagerPerformance",
              "onActivityRelaunchLocked() skipped. mIsScreenOn: false");
        }
      } else if (!isJustFoldedState() && z) {
        setBoosterRelaunchResume(activityRecord);
      }
    }
  }

  public void onActivityVisibleLocked(ActivityRecord activityRecord) {
    if (DEBUG) {
      if (activityRecord == null) {
        Slog.e("ActivityManagerPerformance", "onActivityVisibleLocked() ActivityRecord is Null");
        return;
      }
      if (activityRecord.processName == null) {
        Slog.e(
            "ActivityManagerPerformance",
            "onActivityVisibleLocked() ActivityRecord's ProcessName is Null");
      } else {
        Slog.d(
            "ActivityManagerPerformance",
            "onActivityVisibleLocked() r: "
                + activityRecord.processName
                + " ("
                + activityRecord
                + ")");
      }
      if (DEBUG_TRACE) {
        Slog.d(
            "ActivityManagerPerformance",
            "onActivityVisibleLocked() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (AMP_ENABLE && !isJustFoldedState()) {
      if (this.isMultiWindowResume) {
        this.isMultiWindowResume = false;
      }
      if (this.rCurBoostActResume != null) {
        setBoosterActResume(false, activityRecord);
      }
      if (this.rCurBoostActStart != null) {
        setBoosterActStart(false, activityRecord);
      }
      if (this.rCurBoostAppSwitch == null
          || activityRecord == null
          || activityRecord.isActivityTypeHome()
          || activityRecord.isActivityTypeRecents()) {
        return;
      }
      setBoosterAppSwitch(false, activityRecord);
    }
  }

  public void isHomeKeyPressed() {
    if (DEBUG) {
      Slog.d("ActivityManagerPerformance", "isHomeKeyPressed() called");
      if (DEBUG_TRACE) {
        Slog.d("ActivityManagerPerformance", "isHomeKeyPressed() Trace\n" + getCurBoostInfoStr());
        new Exception().printStackTrace();
      }
    }
    if (AMP_ENABLE && AMP_PERF_ENABLE) {
      setBoosterHome(true, false, null);
    }
  }

  public final void registerReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    intentFilter.addAction("com.sec.android.sdhms.action.INIT_COMPLETED");
    intentFilter.setPriority(999);
    this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
  }

  public final void setLowPower(boolean z) {
    synchronized (this.mLockActStart) {
      SemDvfsManager semDvfsManager = this.mBoosterActStart;
      if (semDvfsManager != null) {
        if (z) {
          semDvfsManager.setHint(29);
        } else {
          semDvfsManager.setHint(4);
        }
      }
    }
    synchronized (this.mLockActResume) {
      SemDvfsManager semDvfsManager2 = this.mBoosterActResume;
      if (semDvfsManager2 != null) {
        if (z) {
          semDvfsManager2.setHint(30);
        } else {
          semDvfsManager2.setHint(3);
        }
      }
    }
    synchronized (this.mLockTail) {
      SemDvfsManager semDvfsManager3 = this.mBoosterTail;
      if (semDvfsManager3 != null) {
        if (z) {
          semDvfsManager3.setHint(31);
        } else {
          semDvfsManager3.setHint(5);
        }
      }
    }
  }

  public final boolean isMultiWindowScenario(
      ActivityRecord activityRecord, ActivityRecord activityRecord2) {
    String str;
    String str2;
    long uptimeMillis = SystemClock.uptimeMillis();
    if (activityRecord != null && activityRecord.mLastReportedMultiWindowMode) {
      lastMultiWindowWorkTime = uptimeMillis;
    } else if (activityRecord2 != null && activityRecord2.mLastReportedMultiWindowMode) {
      lastMultiWindowWorkTime = uptimeMillis;
    } else if (activityRecord != null
        && (str2 = activityRecord.processName) != null
        && str2.contains("appsedge")) {
      lastMultiWindowWorkTime = uptimeMillis;
    } else if (activityRecord2 != null
        && (str = activityRecord2.processName) != null
        && str.contains("appsedge")) {
      lastMultiWindowWorkTime = uptimeMillis;
    }
    return uptimeMillis - lastMultiWindowWorkTime < 1000;
  }

  public void notifyPidOfWallpaper(int i, int i2, String str, boolean z) {
    if (isSystemui(str)) {
      return;
    }
    if (z) {
      Process.requestProcessProfile(i, i2, WALLPAPER_PROFILE);
      if (!this.mIsSdhmsInitCompleted || this.mIsMidGroundCpuSetEnable) {
        return;
      }
      SemPerfManager.sendCommandToSsrm("MIDGROUND_PROCESS_DETECT", "TRUE");
      this.mIsMidGroundCpuSetEnable = true;
      return;
    }
    if (this.mIsSdhmsInitCompleted) {
      SemPerfManager.sendCommandToSsrm("MIDGROUND_PROCESS_DETECT", "FALSE");
      this.mIsMidGroundCpuSetEnable = false;
    }
  }

  public static boolean isSystemui(String str) {
    if (str == null) {
      return false;
    }
    for (String str2 : gSystemuiPkgs) {
      if (str.equals(str2)) {
        return true;
      }
    }
    return false;
  }

  /* renamed from: x */
  public static String m113x(String str) {
    Base64.Decoder decoder = pkgDecoder;
    return decoder == null ? "" : new String(decoder.decode(str));
  }
}
