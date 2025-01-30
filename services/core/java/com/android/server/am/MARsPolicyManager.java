package com.android.server.am;

import android.app.usage.AppStandbyInfo;
import android.content.Context;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.IDeviceIdleController;
import android.os.INetworkManagementService;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.am.mars.HistoryBuffer;
import com.android.server.am.mars.MARsBigData;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsFreezeStateRecord;
import com.android.server.am.mars.MARsHistoryBuffer;
import com.android.server.am.mars.MARsHistoryLog;
import com.android.server.am.mars.database.FASEntity;
import com.android.server.am.mars.database.FASEntityBuilder;
import com.android.server.am.mars.database.FASTableContract;
import com.android.server.am.mars.database.MARsComponentTracker;
import com.android.server.am.mars.database.MARsDBManager;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.ActiveSensorFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.CarConnectedFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.filter.filter.DeviceAdminPackageFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.LockScreenFilter;
import com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter;
import com.android.server.am.mars.filter.filter.TopPackageFilter;
import com.android.server.am.mars.filter.filter.VPNPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.usage.AppStandbyInternal;
import com.android.server.wm.WindowManagerService;
import com.att.iqi.lib.BuildConfig;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.sdhms.SemBatteryStats;
import com.samsung.android.sdhms.SemDeviceHealthManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MARsPolicyManager {
  public static boolean MARs_ENABLE = false;
  public final String BUB_ONOFF;
  public boolean ENABLE_RESTRICTED_BUCKET;
  public long KEEP_NO_FILTER_MIN_DURATION;
  public double THRESHOLD_POWER_USAGE;
  public double THRESHOLD_POWER_USAGE_BACKUP;
  public Policy appLockerPolicy;
  public Policy autoRunPolicy;
  public Policy disablePolicy;
  public Policy freecessPolicy;
  public Policy gamePolicy;
  public boolean isTimeChangedForDebug;
  public int mAllPoliciesOn;
  public ActivityManagerService mAm;
  public IAppOpsService mAppOpsService;
  public AppStandbyInternal mAppStandby;
  public long mAutoDeepSleepTimeForDebug;
  public long mAutoDisabledLockingTime;
  public final HashMap mBackupExpirationUptimeMap;
  public long mCalibrationResetTime;
  public boolean mCarModeOn;
  public Context mContext;
  public String mContextPackageName;
  public int mCurrentUserId;
  public boolean mDualAppEnabled;
  public int mDualAppUserId;
  public int[] mEnabledProfileUserIds;
  public final HashMap mFGServiceStartTimeMap;
  public HistoryBuffer mFilterHistoryBufferArray;
  public boolean mFirstTimeUpdatePackages;
  public HistoryBuffer mHistoryBufferArray;
  public boolean mInitDisabledPackage;
  public boolean mIsDeviceIdleMode;
  public boolean mIsLastNotiSentTimeForDisabledDismiss;
  public boolean mIsManualMode;
  public boolean mIsOLAFEnabled;
  public long mLastNotiSentTimeForDisabled;
  public String mLastPkgName;
  public long mLastTriggerTime;
  public int mLastUid;
  public long mLastUpdateTime;
  public boolean mLockingTimeChanged;
  public MARsPkgMap mMARsRestrictedPackages;
  public MARsPkgMap mMARsTargetPackages;
  public boolean mManagedProfileEnabled;
  public INetworkManagementService mNMs;
  public ArrayList mNeedtoDisablePackages;
  public HistoryBuffer mNetHistoryBufferArray;
  public final HashSet mScpmList;
  public boolean mScreenOn;
  public long mUnusedLockingTime;
  public Policy mpsmPolicy;
  public Policy sbikePolicy;
  public Policy udsPolicy;
  public static final String SMART_MANAGER_PKG_NAME =
      SemFloatingFeature.getInstance()
          .getString(
              "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool");
  public static final Lock MARsLock = new Lock();
  public static int AUFAllowBucketSize =
      Integer.parseInt(SystemProperties.get("sys.config.mars_auf_bucket", "0"));
  public static final String SERIAL_NO = SystemProperties.get("ro.serialno", "none");
  public static boolean ENABLE_KILL_LONG_RUNNING_PROCESS = true;
  public static boolean GlobalModelWithChinaSIM = false;
  public static boolean isChinaModel = false;
  public static boolean App_StartUp_History = false;
  public static int FGS_BATTERY_USAGE_THRESHOLD = 100;

  public class Lock {}

  public abstract class MARsPolicyManagerHolder {
    public static final MARsPolicyManager INSTANCE = new MARsPolicyManager();
  }

  public final int convertPolicyNumToImportantType(int i) {
    switch (i) {
      case 0:
        return 1;
      case 1:
        return 2;
      case 2:
        return 3;
      case 3:
      case 7:
      default:
        return -1;
      case 4:
        return 4;
      case 5:
        return 5;
      case 6:
        return 6;
      case 8:
        return 7;
      case 9:
        return 9;
      case 10:
        return 16;
    }
  }

  public final boolean isDisabledByUser(int i) {
    return i == 2 || i == 32;
  }

  public void setFakeTopActivityList(ArrayList arrayList) {}

  public MARsPolicyManager() {
    this.mCurrentUserId = 0;
    this.mContextPackageName = null;
    this.mLastUid = -10;
    this.mLastUpdateTime = 0L;
    this.mEnabledProfileUserIds = new int[0];
    this.mFGServiceStartTimeMap = new HashMap();
    this.mBackupExpirationUptimeMap = new HashMap();
    this.mScpmList = new HashSet();
    this.mNeedtoDisablePackages = new ArrayList();
    this.ENABLE_RESTRICTED_BUCKET = true;
    this.KEEP_NO_FILTER_MIN_DURATION =
        BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
    this.mScreenOn = false;
    this.mCarModeOn = false;
    this.mIsDeviceIdleMode = false;
    this.mIsOLAFEnabled = false;
    this.mFirstTimeUpdatePackages = true;
    this.mAllPoliciesOn = 1;
    this.mIsManualMode = false;
    this.mMARsTargetPackages = new MARsPkgMap();
    this.mMARsRestrictedPackages = new MARsPkgMap();
    this.mLockingTimeChanged = false;
    this.mUnusedLockingTime = 259200000L;
    this.mAutoDisabledLockingTime = 1382400000L;
    this.mLastNotiSentTimeForDisabled = 0L;
    this.mIsLastNotiSentTimeForDisabledDismiss = false;
    this.mCalibrationResetTime = 259200000 - ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    this.THRESHOLD_POWER_USAGE = 1.0d;
    this.THRESHOLD_POWER_USAGE_BACKUP = 1.0d;
    this.isTimeChangedForDebug = false;
    this.mAutoDeepSleepTimeForDebug = 0L;
    this.appLockerPolicy = null;
    this.autoRunPolicy = null;
    this.freecessPolicy = null;
    this.udsPolicy = null;
    this.sbikePolicy = null;
    this.disablePolicy = null;
    this.gamePolicy = null;
    this.mpsmPolicy = null;
    this.mLastTriggerTime = 0L;
    this.BUB_ONOFF = SystemProperties.get("persist.sys.bub_onoff", "1");
  }

  public static MARsPolicyManager getInstance() {
    return MARsPolicyManagerHolder.INSTANCE;
  }

  public final Context getContextForUser(UserHandle userHandle) {
    try {
      Context context = this.mContext;
      return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
    } catch (PackageManager.NameNotFoundException unused) {
      return this.mContext;
    }
  }

  public void init(ActivityManagerService activityManagerService, Context context) {
    this.mAm = activityManagerService;
    this.mContext = context;
    this.mCurrentUserId = context.getUserId();
    this.mContextPackageName = this.mContext.getPackageName();
    FreecessController.getInstance().recoverFreezerStateIfTHAWED();
    MARsHandler.getInstance().init(this.mContext);
    MARsTrigger.getInstance().init(this.mContext);
    MARsDBManager.getInstance().init(this.mContext);
    if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
      MARsHistoryBuffer.getInstance().init();
      MARsHistoryLog.getInstance().init();
    }
  }

  public void postInit(boolean z) {
    if (MARs_ENABLE) {
      return;
    }
    MARsTrigger.getInstance().registerEmStateReceiver();
    MARsTrigger.getInstance().registerSMDBChangedReceiver();
    if (!MARsDBManager.getInstance().isSMProviderExist()) {
      MARs_ENABLE = false;
      return;
    }
    MARs_ENABLE = true;
    try {
      SystemProperties.set("sys.config.mars_version", "9.00");
    } catch (IllegalArgumentException unused) {
      Slog.e("MARsPolicyManager", "init(), we cannot set system property");
    }
    if ("CHINA".equalsIgnoreCase(SemSystemProperties.getCountryCode())) {
      isChinaModel = true;
    }
    Slog.v("MARsPolicyManager", "isChinaModel = " + isChinaModel);
    if (z) {
      if (!MARsDebugConfig.DEBUG_MID && !MARsDebugConfig.DEBUG_HIGH) {
        this.mHistoryBufferArray = new HistoryBuffer();
      }
      this.mNetHistoryBufferArray = new HistoryBuffer();
      this.mFilterHistoryBufferArray = new HistoryBuffer();
    }
    setScreenOnState(((PowerManager) this.mContext.getSystemService("power")).isInteractive());
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d("MARsPolicyManager", "mScreenOn : " + getScreenOnState());
    }
    MARsDBManager.getInstance().sendGetMARsPolicyConditionMsgToDBHandler(z);
    FilterManager.getInstance().init(this.mContext);
    MARsTrigger.getInstance().registerReceiver(true);
    MARsDBManager.getInstance().sendInitSettingMsgToDBHandler();
    MARsDBManager.getInstance().registerContentObservers(this.mContext);
    UidStateMgr.getInstance().init(this.mAm, this.mContext);
    ForegroundServiceMgr.getInstance().init();
    if (!isChinaPolicyEnabled()) {
      registerAppIdleStateReceiver();
    }
    MARsHandler.getInstance().sendUpdatePkgMsgToMainHandler(true);
    MARsHandler.getInstance().sendGetBatteryStatMsgToMainHandler(true);
    MARsHandler.getInstance().sendUpdateDisableMsgToMainHandler(false);
    MARsDBManager.getInstance().sendUpdateDisableResetTimeToDBHandler(true);
    Slog.d("MARsPolicyManager", "sendUpdateDisableResetTimeToDBHandler enter");
    try {
      int parseInt = Integer.parseInt(SystemProperties.get("sys.dualapp.profile_id", "-1"));
      this.mDualAppUserId = parseInt;
      if (parseInt >= 95 && parseInt <= 99) {
        this.mDualAppEnabled = true;
        MARsHandler.getInstance().sendInitDisabledMsgToMainHandler(this.mDualAppUserId);
      }
    } catch (NumberFormatException unused2) {
      Slog.e("MARsPolicyManager", "init() get DualAppUserId failed!");
    }
    setSubUserIds();
  }

  public void setSubUserIds() {
    UserManager userManager = (UserManager) this.mContext.getSystemService("user");
    if (userManager != null) {
      Iterator it = userManager.getEnabledProfiles().iterator();
      while (it.hasNext()) {
        int identifier = ((UserHandle) it.next()).getIdentifier();
        if (identifier != 0) {
          synchronized (this.mEnabledProfileUserIds) {
            this.mEnabledProfileUserIds =
                ArrayUtils.appendInt(this.mEnabledProfileUserIds, identifier);
          }
          MARsHandler.getInstance().sendInitDisabledMsgToMainHandler(identifier);
        }
      }
    }
  }

  public void switchUser(int i) {
    addDebugInfoToHistory("DEV", "switchUser");
    boolean screenOnFreecessEnabled = FreecessController.getInstance().getScreenOnFreecessEnabled();
    if (screenOnFreecessEnabled) {
      FreecessController.getInstance().setScreenOnFreecessEnabled(false);
      FreecessController.getInstance().removeBgTriggerMsg();
    }
    if (FreecessController.getInstance().getFreecessEnabled()) {
      FreecessController.getInstance().unFreezePackage("MUM");
    }
    this.mFirstTimeUpdatePackages = true;
    if (screenOnFreecessEnabled) {
      FreecessController.getInstance().setScreenOnFreecessEnabled(true);
    }
    FilterManager.getInstance().deInit();
    initCurrentUser(i);
    MARsHandler.getInstance().sendInitDisabledMsgToMainHandler(i);
  }

  public void initDisabledPackage(int i) {
    PackageManager packageManager = this.mContext.getPackageManager();
    List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(32768, i);
    for (int i2 = 0; i2 < installedPackagesAsUser.size(); i2++) {
      PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i2);
      if (packageInfo != null) {
        String str = packageInfo.packageName;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo != null) {
          int i3 = applicationInfo.uid;
          if (applicationInfo.enabledSetting == 4
              && !isMARsTarget(str, i)
              && packageManager.isPackageAutoDisabled(str, i3)) {
            setEnabledSetting(str, i, 1, 1);
          }
        }
      }
    }
  }

  public void initCurrentUser(int i) {
    this.mCurrentUserId = i;
    this.mLastNotiSentTimeForDisabled = 0L;
    Context contextForUser = getContextForUser(new UserHandle(i));
    MARsDBManager.getInstance().switchUser(contextForUser);
    FilterManager.getInstance().init(contextForUser);
  }

  public boolean isCurrentUser(int i) {
    boolean z;
    boolean z2;
    if (this.mCurrentUserId != 0) {
      z = false;
      z2 = false;
    } else if (!this.mDualAppEnabled || i < 95 || i > 99) {
      synchronized (this.mEnabledProfileUserIds) {
        int[] iArr = this.mEnabledProfileUserIds;
        int length = iArr.length;
        int i2 = 0;
        while (true) {
          if (i2 >= length) {
            z2 = false;
            break;
          }
          if (i == iArr[i2]) {
            z2 = true;
            break;
          }
          i2++;
        }
      }
      z = false;
    } else {
      z = true;
      z2 = false;
    }
    return this.mCurrentUserId == i || z || z2;
  }

  public void initInternal(boolean z) {
    Policy policy;
    if (isChinaModel && (policy = this.autoRunPolicy) != null) {
      policy.enabled = true;
    }
    Policy policy2 = this.freecessPolicy;
    if (policy2 != null) {
      policy2.enabled = FreecessController.getInstance().getFreecessEnabledConfig();
    }
    if (isAppStartUpHistoryEnabled()) {
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.d("MARsPolicyManager", "App StartUp History is enabled");
      }
      App_StartUp_History = true;
    }
    Policy policy3 = this.udsPolicy;
    if (policy3 != null && policy3.enabled) {
      MARsTrigger.getInstance().registerUDSReceiver();
    }
    Policy policy4 = this.sbikePolicy;
    if (policy4 != null && policy4.enabled) {
      MARsTrigger.getInstance().registerSBikeReceiver();
    }
    Policy policy5 = this.gamePolicy;
    if (policy5 != null && policy5.enabled) {
      MARsTrigger.getInstance().registerGameReceiver();
    }
    MARsTrigger.getInstance().registerTCPUReceiver();
    Policy policy6 = this.mpsmPolicy;
    if (policy6 != null && policy6.enabled) {
      MARsTrigger.getInstance().registerMPSMReceiver();
    }
    if (!MARsDebugConfig.DEBUG_MARs
        || this.autoRunPolicy == null
        || this.freecessPolicy == null
        || this.udsPolicy == null
        || this.sbikePolicy == null
        || this.disablePolicy == null
        || this.gamePolicy == null
        || this.mpsmPolicy == null) {
      return;
    }
    Slog.v(
        "MARsPolicyManager",
        "FC = "
            + this.freecessPolicy.enabled
            + ", AR = "
            + this.autoRunPolicy.enabled
            + ", PD = "
            + this.disablePolicy.enabled
            + ", UD = "
            + this.udsPolicy.enabled
            + ", SB = "
            + this.sbikePolicy.enabled
            + ", GA = "
            + this.gamePolicy.enabled
            + ", MP = "
            + this.mpsmPolicy.enabled);
  }

  public boolean getMARsEnabled() {
    return MARs_ENABLE;
  }

  public synchronized boolean getIsManualMode() {
    return this.mIsManualMode;
  }

  public synchronized void setIsManualMode(boolean z) {
    this.mIsManualMode = z;
    if (!z) {
      setLastNotiSentTimeForDisabled(0L);
      MARsHandler.getInstance().sendUpdateDisableMsgToMainHandler(false);
      MARsHandler.getInstance().sendUpdatePkgMsgToMainHandler(true);
    }
    StringBuilder sb = new StringBuilder();
    sb.append("ManualMode ");
    sb.append(z ? "ON" : "OFF");
    addDebugInfoToHistory("DEV", sb.toString());
  }

  public synchronized boolean getScreenOnState() {
    return this.mScreenOn;
  }

  public synchronized void setScreenOnState(boolean z) {
    this.mScreenOn = z;
    StringBuilder sb = new StringBuilder();
    sb.append("SCR ");
    sb.append(z ? "ON" : "OFF");
    addDebugInfoToHistory("SYS", sb.toString());
  }

  public synchronized boolean getCarModeOnState() {
    return this.mCarModeOn;
  }

  public synchronized void setCarModeOnState(boolean z) {
    this.mCarModeOn = z;
  }

  public synchronized boolean getDeviceIdleModeState() {
    return this.mIsDeviceIdleMode;
  }

  public synchronized void setDeviceIdleModeState(boolean z) {
    this.mIsDeviceIdleMode = z;
  }

  public synchronized boolean getFirstTimeUpdatePkgsState() {
    return this.mFirstTimeUpdatePackages;
  }

  public synchronized void setFirstTimeUpdatePkgsState(boolean z) {
    this.mFirstTimeUpdatePackages = z;
  }

  public synchronized int getDualAppUserId() {
    return this.mDualAppUserId;
  }

  public synchronized void setDualAppUserId(int i) {
    this.mDualAppUserId = i;
  }

  public synchronized void setDualAppEnabled(boolean z) {
    this.mDualAppEnabled = z;
  }

  public synchronized void setManagedProfileEnabled(boolean z, int i) {
    this.mManagedProfileEnabled = z;
    synchronized (this.mEnabledProfileUserIds) {
      if (z) {
        this.mEnabledProfileUserIds = ArrayUtils.appendInt(this.mEnabledProfileUserIds, i);
      } else {
        this.mEnabledProfileUserIds = ArrayUtils.removeInt(this.mEnabledProfileUserIds, i);
      }
    }
  }

  public boolean getPackageDisablerEnabled() {
    Policy policy = this.disablePolicy;
    if (policy != null) {
      return policy.enabled;
    }
    return false;
  }

  public void setPackageDisablerEnabled(boolean z) {
    Policy policy = this.disablePolicy;
    if (policy == null || policy.enabled == z) {
      return;
    }
    policy.enabled = z;
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d("MARsPolicyManager", "setPackageDisablerEnabled " + this.disablePolicy.enabled);
    }
    addDebugInfoToHistory("DEV", "disabler_switch : " + z);
  }

  public String getSharedUidName(String str, int i) {
    try {
      PackageInfo packageInfoAsUser =
          this.mContext.getPackageManager().getPackageInfoAsUser(str, 4202496, i);
      if (packageInfoAsUser != null) {
        return packageInfoAsUser.sharedUserId;
      }
      return null;
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.e("MARsPolicyManager", "NameNotFoundException occurred for package : " + str);
      return null;
    } catch (NullPointerException e) {
      Slog.e(
          "MARsPolicyManager",
          "NullPointerException occurred in getSharedUidName() " + e.getMessage());
      return null;
    }
  }

  public boolean checkIsChinaModel() {
    return isChinaModel;
  }

  public boolean isChinaPolicyEnabled() {
    return isChinaModel || GlobalModelWithChinaSIM;
  }

  public boolean isFirstTimeTriggerAutorun() {
    return isChinaPolicyEnabled() && getFirstTimeUpdatePkgsState();
  }

  public boolean isAutoRunOn(String str, int i) {
    boolean z;
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      z = (mARsPackageInfo == null || mARsPackageInfo.getFASEnabled()) ? false : true;
    }
    return z;
  }

  public boolean isAutoRunBlockedApp(String str, int i) {
    if (!isChinaPolicyEnabled()) {
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.i("MARsPolicyManager", "AR not enabled");
      }
      return false;
    }
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null && mARsPackageInfo.getFASEnabled()) {
        if (isInPolicyExceptionList(str, i, 2)) {
          return false;
        }
        if (FilterManager.getInstance()
                .filterForSpecificPolicy(19, str, i, mARsPackageInfo.getUid())
            > 0) {
          return false;
        }
        if (!MARsDebugConfig.DEBUG_ENG) {
          return true;
        }
        Slog.i("MARsPolicyManager", str + " Auto run OFF, userId = " + i);
        return true;
      }
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.i("MARsPolicyManager", str + " Auto run ON, userId = " + i);
      }
      return false;
    }
  }

  public boolean isAppStartUpHistoryEnabled() {
    PackageManager packageManager = this.mContext.getPackageManager();
    Intent intent = new Intent();
    intent.setAction("com.samsung.android.sm.ACTION_AUTO_RUN");
    intent.setPackage(SMART_MANAGER_PKG_NAME);
    ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
    return resolveActivity != null && resolveActivity.activityInfo.isEnabled();
  }

  public void setAllPoliciesOnOffState(int i, boolean z) {
    if (MARsDebugConfig.DEBUG_MARs) {
      Slog.v("MARsPolicyManager", "setAllPoliciesOnOffState on = " + i);
      addDebugInfoToHistory("DEV", "spcm_switch : " + i);
    }
    if (this.mAllPoliciesOn != i) {
      if (i == 0) {
        setIsManualMode(true);
        if (getPackageDisablerEnabled()) {
          setPackageDisablerEnabled(false);
        }
      } else if (i == 1) {
        if (!isChinaModel && GlobalModelWithChinaSIM) {
          GlobalModelWithChinaSIM = false;
          switchPolicies(1, z);
        }
        if (!getPackageDisablerEnabled()) {
          setPackageDisablerEnabled(true);
        }
        setIsManualMode(false);
      } else if (i == 3) {
        GlobalModelWithChinaSIM = true;
        switchPolicies(2, z);
        if (!getPackageDisablerEnabled()) {
          setPackageDisablerEnabled(true);
        }
      } else if (i == 9999999) {
        setIsManualMode(true);
        if (getPackageDisablerEnabled()) {
          setPackageDisablerEnabled(false);
        }
      }
      this.mAllPoliciesOn = i;
    }
  }

  public void setPackagesUnusedLockingTime(int i) {
    if (MARsDebugConfig.DEBUG_MARs) {
      Slog.v("MARsPolicyManager", "setPackagesUnusedLockingTime hours = " + i);
    }
    long j = this.mUnusedLockingTime;
    long j2 = i * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    if (j != j2) {
      this.mLockingTimeChanged = true;
    }
    this.mUnusedLockingTime = j2;
    this.mCalibrationResetTime = j2 - ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    if (i == 1) {
      callRestrictAppForAllPkgs();
      if (this.ENABLE_RESTRICTED_BUCKET) {
        callSetAppStandbyBuckets(this.mContext.getUserId(), 45, true);
      }
      addDebugInfoToHistory("DEV", "Auto restriction's battery condition changed !");
      this.THRESHOLD_POWER_USAGE_BACKUP = this.THRESHOLD_POWER_USAGE;
      this.THRESHOLD_POWER_USAGE = -2.0d;
      this.isTimeChangedForDebug = true;
      this.mAutoDeepSleepTimeForDebug = 7200000L;
      MARsHandler.getInstance().sendUpdateDisableMsgToMainHandler(true);
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.v("MARsPolicyManager", "DEBUGGING mode turned on, skip to check battery usage !");
        return;
      }
      return;
    }
    this.THRESHOLD_POWER_USAGE = this.THRESHOLD_POWER_USAGE_BACKUP;
    this.isTimeChangedForDebug = false;
    MARsHandler.getInstance().sendUpdateDisableMsgToMainHandler(false);
  }

  public final void callRestrictAppForAllPkgs() {
    if (this.mAppStandby == null) {
      this.mAppStandby = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
    }
    if (this.mAppStandby != null) {
      synchronized (this.mMARsTargetPackages) {
        for (int i = 0; i < this.mMARsTargetPackages.getMap().size(); i++) {
          SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i);
          for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
            if (mARsPackageInfo != null) {
              this.mAppStandby.restrictApp(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), 4);
            }
          }
        }
      }
    }
  }

  public final void switchPolicies(int i, boolean z) {
    Policy policy;
    if (i == 2) {
      Policy policy2 = this.autoRunPolicy;
      if (policy2 != null && (policy = this.appLockerPolicy) != null) {
        policy.enabled = false;
      }
      if (policy2 != null) {
        policy2.enabled = true;
      }
    } else if (i == 1) {
      Policy policy3 = this.appLockerPolicy;
      if (policy3 != null) {
        policy3.enabled = true;
      }
      Policy policy4 = this.autoRunPolicy;
      if (policy4 != null) {
        policy4.enabled = false;
      }
    }
    if (z) {
      return;
    }
    if (FreecessController.getInstance().getFreecessEnabled()) {
      FreecessController.getInstance().unFreezePackage("switchPolicy");
    }
    synchronized (MARsLock) {
      MARsPkgMap mARsPkgMap = this.mMARsTargetPackages;
      if (mARsPkgMap != null) {
        mARsPkgMap.clear();
      }
      MARsPkgMap mARsPkgMap2 = this.mMARsRestrictedPackages;
      if (mARsPkgMap2 != null) {
        mARsPkgMap2.clear();
      }
    }
    MARsDBManager.getInstance().sendInitSettingMsgToDBHandler();
  }

  public boolean isForegroundPackage(String str, int i) {
    if (str == null) {
      return false;
    }
    if (isMARsTarget(str, i)) {
      return TopPackageFilter.getInstance().isInTopPkgList(str, i);
    }
    return true;
  }

  public void updateForegroundPackageToPkgStatus(String str, int i, int i2, boolean z) {
    synchronized (this.mFGServiceStartTimeMap) {
      if (z) {
        this.mFGServiceStartTimeMap.putIfAbsent(
            Integer.valueOf(i2), Long.valueOf(SystemClock.uptimeMillis()));
      } else {
        this.mFGServiceStartTimeMap.remove(Integer.valueOf(i2));
      }
    }
  }

  public boolean isForegroundServicePkg(int i) {
    return getForegroundServiceStartTime(i) != 0;
  }

  public long getForegroundServiceStartTime(int i) {
    long longValue;
    synchronized (this.mFGServiceStartTimeMap) {
      longValue =
          ((Long) this.mFGServiceStartTimeMap.getOrDefault(Integer.valueOf(i), 0L)).longValue();
    }
    return longValue;
  }

  public void setKeyguardPkgInfo(String str, int i) {
    LockScreenFilter.getInstance().setKeyguardInfo(str, i);
  }

  public void updateBackupServicePkg(int i, boolean z) {
    synchronized (this.mBackupExpirationUptimeMap) {
      if (z) {
        this.mBackupExpirationUptimeMap.put(
            Integer.valueOf(i),
            Long.valueOf(
                SystemClock.uptimeMillis()
                    + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS));
      } else {
        this.mBackupExpirationUptimeMap.remove(Integer.valueOf(i));
      }
    }
    if (z && MARs_ENABLE && FreecessController.getInstance().getFreecessEnabled()) {
      FreecessController.getInstance().protectFreezePackage(i, "BackupService", 180000L);
    }
    addDebugInfoToHistory("DEV", "BackupService uid=" + i + ", isStart=" + z);
  }

  public boolean isBackupServicePkg(int i) {
    synchronized (this.mBackupExpirationUptimeMap) {
      if (this.mBackupExpirationUptimeMap.containsKey(Integer.valueOf(i))) {
        if (SystemClock.uptimeMillis()
            < ((Long) this.mBackupExpirationUptimeMap.get(Integer.valueOf(i))).longValue()) {
          return true;
        }
        this.mBackupExpirationUptimeMap.remove(Integer.valueOf(i));
      }
      return false;
    }
  }

  public void onSpecialIntentActions(String str, Intent intent, int i) {
    String action;
    if (str == null || (action = intent.getAction()) == null) {
      return;
    }
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        if ("android.appwidget.action.APPWIDGET_ENABLED".equals(action)) {
          onAppUsed(mARsPackageInfo);
          WidgetPkgFilter.getInstance().onAppWidgetEnabled(str, i);
        } else if ("android.appwidget.action.APPWIDGET_DISABLED".equals(action)) {
          WidgetPkgFilter.getInstance().onAppWidgetDisabled(str, i);
        } else if ("android.appwidget.action.APPWIDGET_UPDATE".equals(action)) {
          onAppUsed(mARsPackageInfo);
        } else if ("android.app.action.DEVICE_ADMIN_ENABLED".equals(action)) {
          onAppUsed(mARsPackageInfo);
          DeviceAdminPackageFilter.getInstance().onDeviceAdminEnabled(str, i);
        } else if ("android.app.action.DEVICE_ADMIN_DISABLED".equals(action)) {
          DeviceAdminPackageFilter.getInstance().onDeviceAdminDisabled(str, i);
        }
      }
    }
  }

  public void setTTSPkgInfo(int i) {
    ActiveMusicRecordFilter.getInstance().onTTSPkgBind(Integer.valueOf(i));
    Slog.d("MARsPolicyManager", "setTTSPkgInfo : " + i);
  }

  public void clearTTSPkgInfo() {
    ActiveMusicRecordFilter.getInstance().onTTSPkgUnBindAll();
    Slog.d("MARsPolicyManager", "clearTTSPkgInfo");
  }

  public ParceledListSlice getInstalledPackageListFromMARs(int i, int i2) {
    ParceledListSlice parceledListSlice = null;
    if (!hasUserPermission()) {
      return null;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        ParceledListSlice parceledListSlice2 =
            new ParceledListSlice(
                this.mContext.getPackageManager().getInstalledPackagesAsUser(i, i2));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        parceledListSlice = parceledListSlice2;
      } catch (Exception e) {
        Slog.e("MARsPolicyManager", "getInstalledPackageListFromMARs exception:" + e);
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
      return parceledListSlice;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean hasUserPermission() {
    return hasPermission("android.permission.INTERACT_ACROSS_USERS")
        || hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
  }

  public static boolean isSpecialProviderName(String str) {
    if (str == null) {
      return false;
    }
    return str.contains(".CapsuleProvider");
  }

  public static void onSpecialProviderActions(String str, String str2, int i) {
    if (str2 != null && "com.samsung.android.bixby.agent".equals(str)) {
      getInstance().cancelDisablePolicy(str2.split(".CapsuleProvider")[0], i, 1);
    }
  }

  public void onSpecialBindServiceActions(String str, String str2, int i, String str3) {
    if (str == null) {
      return;
    }
    if ("com.google.android.projection.gearhead".equals(str3)) {
      CarConnectedFilter.getInstance().onAutoAppBind(str, i);
    }
    if (str2 == null) {
      return;
    }
    if ("android.intent.action.TTS_SERVICE".equals(str2) && str3 != null) {
      ActiveMusicRecordFilter.getInstance().onTTSPkgBinded(str3, Integer.valueOf(i));
    }
    if ("android.net.VpnService".equals(str2)) {
      VPNPackageFilter.getInstance().onVpnPkgBinded(str, Integer.valueOf(i));
    }
    if ("android.service.notification.NotificationListenerService".equals(str2)) {
      OngoingNotiPackageFilter.getInstance().onNotificationListenerBinded(str, Integer.valueOf(i));
    }
    if (("android.service.wallpaper.WallpaperService".equals(str2)
            || "com.samsung.android.service.wallpaper.LiveWallpaperService".equals(str2)
            || "com.samsung.android.service.wallpaper.CoverWallpaperService".equals(str2))
        && "android".equals(str3)) {
      WallPaperFilter.getInstance().onWallPaperPkgBinded(str, Integer.valueOf(i));
    }
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null
          && ("android.view.InputMethod".equals(str2)
              || "org.androidprinting.intent.ACTION_GET_PRINT_SERVICE".equals(str2))) {
        onAppUsed(mARsPackageInfo);
      }
    }
  }

  public void onSpecialUnBindServiceActions(String str, String str2, int i, String str3) {
    if (str == null) {
      return;
    }
    if ("com.google.android.projection.gearhead".equals(str3)) {
      CarConnectedFilter.getInstance().onAutoAppUnbind(str, i);
    }
    if (str2 == null) {
      return;
    }
    if ("android.intent.action.TTS_SERVICE".equals(str2) && str3 != null) {
      ActiveMusicRecordFilter.getInstance().onTTSPkgUnBinded(str3, Integer.valueOf(i));
    }
    if ("android.net.VpnService".equals(str2)) {
      VPNPackageFilter.getInstance().onVpnPkgUnBinded(str, Integer.valueOf(i));
    }
    if ("android.service.notification.NotificationListenerService".equals(str2)) {
      OngoingNotiPackageFilter.getInstance()
          .onNotificationListenerUnBinded(str, Integer.valueOf(i));
    }
    if (("android.service.wallpaper.WallpaperService".equals(str2)
            || "com.samsung.android.service.wallpaper.LiveWallpaperService".equals(str2)
            || "com.samsung.android.service.wallpaper.CoverWallpaperService".equals(str2))
        && "android".equals(str3)) {
      WallPaperFilter.getInstance().onWallPaperPkgUnBinded(str, Integer.valueOf(i));
    }
  }

  public void onAppUsed(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        if (getScreenOnState()) {
          mARsPackageInfo.setLastUsedTime(SystemClock.elapsedRealtime());
          mARsPackageInfo.setDisableResetTime(System.currentTimeMillis());
          LatestProtectedPackageFilter.getInstance().setLatestProtectedPkg(str, i);
        }
        onAppUsed(mARsPackageInfo);
      }
    }
  }

  public void onPackageResumedFG(
      List list, String str, String str2, boolean z, Intent intent, int i) {
    String str3;
    if ("com.android.systemui".equals(str)) {
      return;
    }
    long uptimeMillis = SystemClock.uptimeMillis();
    boolean z2 = true;
    boolean z3 = uptimeMillis - this.mLastUpdateTime >= 100;
    this.mLastUpdateTime = uptimeMillis;
    if (z3 || this.mLastUid != i || (str3 = this.mLastPkgName) == null || !str3.equals(str)) {
      this.mLastPkgName = str;
      this.mLastUid = i;
      if (MARsDebugConfig.DEBUG_MARs && str != null && !str.contains(".iqi")) {
        Slog.d("MARsPolicyManager", "onPackageResumedFG pkgName = " + str + ", userId = " + i);
      }
      if (getScreenOnState()) {
        if (list != null) {
          TopPackageFilter.getInstance().updateTopPkgList((ArrayList) list, i);
        } else if (str != null) {
          ArrayList arrayList = new ArrayList();
          arrayList.add(str);
          TopPackageFilter.getInstance().updateTopPkgList(arrayList, i);
        }
      }
      if (str != null && str.equals(DefaultAppFilter.getInstance().getDefaultHomePackage())) {
        FreecessController.getInstance().unfreezeWallPaperPackage();
        return;
      }
      if (str != null) {
        if (FreecessController.getInstance().mFreecessOlafUpdate.get()
            && i == FreecessController.getInstance().mOlafTargetUserId
            && str.equals(FreecessController.getInstance().mOlafTargetPkg)) {
          z2 = false;
        }
        if (z2) {
          onAppUsed(str, i);
        }
      }
    }
  }

  public void onPackagePausedBG(String str, String str2, boolean z, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (this.mScreenOn && isChinaPolicyEnabled() && mARsPackageInfo != null) {
        mARsPackageInfo.setLastUsedTime(SystemClock.elapsedRealtime());
      }
      if (!str.equals(str2) || z) {}
    }
  }

  public final boolean isNeedOptimizedApp(MARsPackageInfo mARsPackageInfo) {
    if (!isChinaPolicyEnabled()) {
      return false;
    }
    boolean z = !mARsPackageInfo.getFASEnabled();
    return !z || (z && mARsPackageInfo.getState() == 4 && mARsPackageInfo.getFasType() != 256);
  }

  public boolean isMARsTarget(String str, int i) {
    boolean z;
    synchronized (MARsLock) {
      z = getMARsPackageInfo(this.mMARsTargetPackages, str, i) != null;
    }
    return z;
  }

  public boolean checkMARsRestrictedAlarmTarget(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      boolean z = false;
      if (mARsPackageInfo == null) {
        return false;
      }
      if (!isChinaPolicyEnabled() || mARsPackageInfo.getFASEnabled()) {
        return !((mARsPackageInfo.optionFlag & 2) != 0)
            && FreecessController.getInstance()
                .matchFreezeState(str, i, Boolean.TRUE, null, Boolean.FALSE);
      }
      if ((mARsPackageInfo.optionFlag & 8) != 0
          && FreecessController.getInstance()
              .matchFreezeState(str, i, Boolean.TRUE, null, Boolean.FALSE)) {
        z = true;
      }
      return z;
    }
  }

  public MARsPackageInfo getMARsPackageInfo(MARsPkgMap mARsPkgMap, String str, int i) {
    if (mARsPkgMap == null || mARsPkgMap.totalSize() == 0) {
      return null;
    }
    return (MARsPackageInfo) mARsPkgMap.get(str, i);
  }

  public MARsPkgMap getMARsTargetPkgMap() {
    return this.mMARsTargetPackages;
  }

  public int getAutorunForFreezedPackage(String str, int i) {
    if (!isChinaPolicyEnabled()) {
      return -1;
    }
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        return mARsPackageInfo.getFASEnabled() ? 0 : 1;
      }
      return -1;
    }
  }

  public void clearAllPackages() {
    synchronized (MARsLock) {
      if (this.mMARsTargetPackages.size() > 0) {
        this.mMARsTargetPackages.clear();
      }
      if (this.mMARsRestrictedPackages.size() > 0) {
        this.mMARsRestrictedPackages.clear();
      }
    }
  }

  public void updateMARsTargetPackages(ArrayList arrayList) {
    int i;
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    synchronized (MARsLock) {
      i = this.mMARsTargetPackages.totalSize();
    }
    if (arrayList != null) {
      this.mNeedtoDisablePackages.clear();
      getPkgInfoFromSMToMARs(arrayList);
      deletePkgInfoInMARs(arrayList);
      if (!this.mInitDisabledPackage) {
        this.mInitDisabledPackage = true;
        MARsHandler.getInstance().sendInitDisabledMsgToMainHandler(this.mContext.getUserId());
      }
    }
    for (int i2 = 0; i2 < this.mNeedtoDisablePackages.size(); i2++) {
      String str = (String) ((Pair) this.mNeedtoDisablePackages.get(i2)).first;
      Integer num = (Integer) ((Pair) this.mNeedtoDisablePackages.get(i2)).second;
      arrayList2.add(str);
      arrayList3.add(num);
      disablePackageForSpecific(str, num.intValue(), "added_from_mars_auto_specific");
    }
    if (arrayList2.size() > 0) {
      MARsHandler.getInstance()
          .sendNotifyDeviceCareMsgToMainHandler("deepsleepspecific", arrayList2, arrayList3);
    }
    Slog.d("MARsPolicyManager", "updateMARsTargetPackages mMARsTargetPackages.size-" + i);
  }

  public final void getPkgInfoFromSMToMARs(ArrayList arrayList) {
    boolean contains;
    String str;
    int i;
    int i2;
    IPackageManager asInterface =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    if (isChinaPolicyEnabled() && getFirstTimeUpdatePkgsState()) {
      WidgetPkgFilter.getInstance().getBoundAppWidgetPackages();
    }
    boolean z = false;
    for (int i3 = 0; i3 < arrayList.size(); i3++) {
      FASEntity fASEntity = (FASEntity) arrayList.get(i3);
      MARsPackageInfo mARsPackageInfo = new MARsPackageInfo(fASEntity);
      fASEntity.getStrPkgName();
      int convertFASReasonToValue =
          FASTableContract.convertFASReasonToValue(fASEntity.getStrFasReason());
      try {
        i2 = Integer.parseInt(fASEntity.getStrMode());
      } catch (NumberFormatException e) {
        Slog.e("MARsPolicyManager", "NumberFormatException !" + e);
        i2 = 0;
      }
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo2 =
            getMARsPackageInfo(
                this.mMARsTargetPackages, mARsPackageInfo.getName(), mARsPackageInfo.getUserId());
        if (mARsPackageInfo2 != null) {
          if (mARsPackageInfo2.getFASEnabled() == i2
              && mARsPackageInfo2.getFasType() == convertFASReasonToValue) {
            if (mARsPackageInfo2.getDisabled() && mARsPackageInfo2.getDisableReason() == 0) {
              mARsPackageInfo2.setDisableReason(1);
            }
          }
          mARsPackageInfo2.setFasType(convertFASReasonToValue);
          mARsPackageInfo2.setFasReason(fASEntity.getStrFasReason());
          if (i2 == 1) {
            levelChange(32, mARsPackageInfo2);
          } else {
            levelChange(256, mARsPackageInfo2);
            if (mARsPackageInfo2.getDisabled()
                || isDisabledByUser(mARsPackageInfo2.getDisableReason())) {
              if (isDisabledByUser(mARsPackageInfo2.getDisableReason())) {
                mARsPackageInfo2.setDisableReason(0);
              }
              arrayList2.add(mARsPackageInfo2);
            }
          }
        } else {
          arrayList3.add(
              new Pair(mARsPackageInfo.getName(), Integer.valueOf(mARsPackageInfo.getUid())));
          mARsPackageInfo.setSharedUidName(
              getSharedUidName(mARsPackageInfo.getName(), mARsPackageInfo.getUserId()));
          if ((mARsPackageInfo.getPackageType() & 1) != 0) {
            mARsPackageInfo.setHasAppIcon(true);
          }
          try {
            if (asInterface.isPackageAutoDisabled(
                mARsPackageInfo.getName(), mARsPackageInfo.getUid())) {
              mARsPackageInfo.setMaxLevel(4);
              mARsPackageInfo.setCurLevel(4);
              mARsPackageInfo.setDisabled(true);
              mARsPackageInfo.setAppliedPolicy(convertLevelToPolicy(4));
              if (!isDisabledByUser(mARsPackageInfo.getDisableReason())
                  && mARsPackageInfo.getDisableReason() != 16) {
                mARsPackageInfo.setDisableReason(1);
                mARsPackageInfo.setState(16);
              }
              this.mMARsRestrictedPackages.put(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
            } else if (mARsPackageInfo.getMaxLevel() == 4
                && isDisabledByUser(mARsPackageInfo.getDisableReason())) {
              mARsPackageInfo.setAppliedPolicy(convertLevelToPolicy(4));
              this.mMARsRestrictedPackages.put(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
            }
          } catch (RemoteException e2) {
            Slog.e("MARsPolicyManager", "getPkgInfoFromSMToMARs exception:" + e2);
          }
          mARsPackageInfo.initOptionFlag();
          this.mMARsTargetPackages.put(
              mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
        }
      }
      if (isChinaPolicyEnabled()
          && getFirstTimeUpdatePkgsState()
          && mARsPackageInfo.getFASEnabled()) {
        z = true;
      }
    }
    for (int i4 = 0; i4 < arrayList2.size(); i4++) {
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) arrayList2.get(i4);
        if (mARsPackageInfo3 != null) {
          str = mARsPackageInfo3.getName();
          i = mARsPackageInfo3.getUserId();
        } else {
          str = null;
          i = -1;
        }
      }
      cancelDisablePolicy(str, i, 0);
    }
    for (int i5 = 0; i5 < arrayList3.size(); i5++) {
      String str2 = (String) ((Pair) arrayList3.get(i5)).first;
      Integer num = (Integer) ((Pair) arrayList3.get(i5)).second;
      synchronized (this.mScpmList) {
        contains = this.mScpmList.contains(str2);
      }
      if (contains) {
        this.mNeedtoDisablePackages.add(new Pair(str2, num));
      }
      Slog.d("MARsPolicyManager", "new Package : " + str2 + ", SCPMTarget : " + contains);
    }
    if (z) {
      MARsHandler.getInstance().sendTriggerPolicyMsgToMainHandler();
    }
  }

  public final void deletePkgInfoInMARs(ArrayList arrayList) {
    int i;
    boolean z;
    if (arrayList == null || arrayList.size() <= 0) {
      return;
    }
    ArrayList arrayList2 = new ArrayList();
    Slog.d("MARsPolicyManager", "deletePkgInfoInMARs size = " + arrayList.size());
    synchronized (MARsLock) {
      int size = this.mMARsTargetPackages.getMap().size() - 1;
      while (true) {
        if (size < 0) {
          break;
        }
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(size);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          int i3 = 0;
          while (true) {
            if (i3 >= arrayList.size()) {
              z = false;
              break;
            }
            MARsPackageInfo mARsPackageInfo2 = new MARsPackageInfo((FASEntity) arrayList.get(i3));
            if (mARsPackageInfo.getName().equals(mARsPackageInfo2.getName())
                && mARsPackageInfo.getUid() == mARsPackageInfo2.getUid()) {
              mARsPackageInfo2.setDisabled(mARsPackageInfo.getDisabled());
              mARsPackageInfo.updatePackageInfo(mARsPackageInfo2);
              z = true;
              break;
            }
            i3++;
          }
          if (!z) {
            arrayList2.add(mARsPackageInfo);
          }
        }
        size--;
      }
      for (i = 0; i < arrayList2.size(); i++) {
        MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) arrayList2.get(i);
        this.mMARsTargetPackages.remove(mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId());
        this.mMARsRestrictedPackages.remove(
            mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId());
      }
    }
  }

  public boolean getHasAppIcon(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      return mARsPackageInfo != null && mARsPackageInfo.getHasAppIcon();
    }
  }

  public long getLastUsedTime(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo == null) {
        return -900000L;
      }
      return mARsPackageInfo.getLastUsedTime();
    }
  }

  public final void registerAppIdleStateReceiver() {
    AppStandbyInternal appStandbyInternal =
        (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
    this.mAppStandby = appStandbyInternal;
    if (appStandbyInternal != null) {
      appStandbyInternal.addListener(new MARsAppIdleStateChangeListener());
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.d("MARsPolicyManager", "registerAppIdleStateReceiver");
      }
    }
  }

  public class MARsAppIdleStateChangeListener
      extends AppStandbyInternal.AppIdleStateChangeListener {
    public MARsAppIdleStateChangeListener() {}

    /* JADX WARN: Removed duplicated region for block: B:21:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
      FASEntity fASEntity;
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.d(
            "MARsPolicyManager",
            "onAppIdleStateChanged() " + str + "|bucket : " + i2 + "|reason : " + i3);
      }
      synchronized (MARsPolicyManager.MARsLock) {
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.this;
        MARsPackageInfo mARsPackageInfo =
            mARsPolicyManager.getMARsPackageInfo(mARsPolicyManager.mMARsTargetPackages, str, i);
        if (mARsPackageInfo != null) {
          if (mARsPackageInfo.getFasType() != 2 && mARsPackageInfo.getFasType() != 256) {
            if (i2 >= 40) {
              mARsPackageInfo.setResetTime(System.currentTimeMillis());
              fASEntity =
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                      .build();
              if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(
                    "MARsPolicyManager",
                    "update resetTime : "
                        + str
                        + "["
                        + MARsPolicyManager.this.formatDateTimeWithoutYear(
                            mARsPackageInfo.getResetTime())
                        + "]");
              }
            } else if (i2 <= 10
                && !mARsPackageInfo.getDisabled()
                && MARsPolicyManager.this.levelChange(1024, mARsPackageInfo)
                && mARsPackageInfo.getFasType() == 128) {
              fASEntity =
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                      .setStrFasReason(
                          FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                      .setStrDisableReason(
                          FASTableContract.convertDisableReasonToDBValue(
                              mARsPackageInfo.getDisableReason()))
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                      .build();
            }
            if (fASEntity == null) {
              MARsDBManager.getInstance().sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
              return;
            }
            return;
          }
          return;
        }
        fASEntity = null;
        if (fASEntity == null) {}
      }
    }
  }

  public void updateResetTime() {
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int size = this.mMARsTargetPackages.getMap().size() - 1; size >= 0; size--) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(size);
        for (int i = 0; i < sparseArray.size(); i++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i);
          if (mARsPackageInfo != null) {
            arrayList.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                    .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                    .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                    .setStrDisableResetTime(Long.toString(mARsPackageInfo.getDisableResetTime()))
                    .build());
          }
        }
      }
    }
    Slog.d("MARsPolicyManager", "updateResetTime");
    if (arrayList.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:44:0x00eb A[Catch: all -> 0x01b6, TryCatch #0 {, blocks: (B:22:0x004b, B:24:0x0057, B:25:0x0065, B:27:0x006b, B:29:0x007a, B:31:0x0080, B:33:0x008a, B:35:0x0092, B:39:0x00ee, B:40:0x00a5, B:42:0x00bc, B:44:0x00eb, B:49:0x00f7, B:51:0x00ff), top: B:21:0x004b }] */
  /* JADX WARN: Removed duplicated region for block: B:46:0x00ee A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void checkUnusedTargetForDeepSleep() {
    int i;
    SparseArray sparseArray;
    int i2;
    PkgStatusInfo pkgStatusInfo;
    if (getIsManualMode()) {
      Slog.d("MARsPolicyManager", "Now manual mode is on, we will not update anything!");
      return;
    }
    ArrayList arrayList = new ArrayList();
    StringBuilder sb = new StringBuilder();
    long currentTimeMillis = System.currentTimeMillis();
    long j = this.mLastNotiSentTimeForDisabled;
    long j2 = 0;
    if (j != 0) {
      if (currentTimeMillis - j
          <= (this.isTimeChangedForDebug ? this.mAutoDeepSleepTimeForDebug * 2 : 1296000000L)) {
        return;
      }
    }
    ArrayList arrayList2 = new ArrayList();
    long j3 =
        this.isTimeChangedForDebug
            ? this.mAutoDeepSleepTimeForDebug
            : this.mAutoDisabledLockingTime;
    synchronized (MARsLock) {
      int i3 = 0;
      while (i3 < this.mMARsTargetPackages.getMap().size()) {
        SparseArray sparseArray2 = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i3);
        int i4 = 0;
        while (i4 < sparseArray2.size()) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray2.valueAt(i4);
          if (mARsPackageInfo.getFasType() != 256
              && !mARsPackageInfo.getDisabled()
              && isCurrentUser(mARsPackageInfo.getUserId())) {
            if (mARsPackageInfo.getDisableResetTime() > j2
                && System.currentTimeMillis() - mARsPackageInfo.getDisableResetTime() < j3) {
              i = i4;
              sparseArray = sparseArray2;
              i2 = i3;
              i4 = i + 1;
              sparseArray2 = sparseArray;
              i3 = i2;
              j2 = 0;
            } else if (FilterManager.getInstance()
                    .filterForSpecificPolicy(
                        7,
                        mARsPackageInfo.getName(),
                        mARsPackageInfo.getUserId(),
                        mARsPackageInfo.getUid())
                == 0) {
              i = i4;
              sparseArray = sparseArray2;
              i2 = i3;
              pkgStatusInfo =
                  new PkgStatusInfo(
                      mARsPackageInfo.getName(),
                      mARsPackageInfo.getUid(),
                      mARsPackageInfo.getUserId());
              updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
              if (pkgStatusInfo == null) {
                arrayList.add(pkgStatusInfo);
              }
              i4 = i + 1;
              sparseArray2 = sparseArray;
              i3 = i2;
              j2 = 0;
            }
          }
          i = i4;
          sparseArray = sparseArray2;
          i2 = i3;
          pkgStatusInfo = null;
          if (pkgStatusInfo == null) {}
          i4 = i + 1;
          sparseArray2 = sparseArray;
          i3 = i2;
          j2 = 0;
        }
        i3++;
        j2 = 0;
      }
    }
    for (int i5 = 0; i5 < arrayList.size(); i5++) {
      PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) arrayList.get(i5);
      if (!getPackageStoppedState(pkgStatusInfo2.name, pkgStatusInfo2.userId)) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo2 =
              getMARsPackageInfo(
                  this.mMARsTargetPackages, pkgStatusInfo2.name, pkgStatusInfo2.userId);
          if (mARsPackageInfo2 != null && levelChange(4, mARsPackageInfo2)) {
            arrayList2.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo2.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                    .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                    .setStrExtras(
                        FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.getState()))
                    .build());
            sb.append(" " + mARsPackageInfo2.getUid());
          }
        }
      }
    }
    if (arrayList2.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList2);
    }
    addDebugInfoToHistory("NOTI", "D-Candi:" + sb.toString());
  }

  public void onAppUsed(MARsPackageInfo mARsPackageInfo) {
    FASEntity fASEntity;
    levelChange(1024, mARsPackageInfo);
    if (mARsPackageInfo.getFasType() == 128) {
      fASEntity =
          new FASEntityBuilder()
              .setStrPkgName(mARsPackageInfo.getName())
              .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
              .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0")
              .setStrFasReason(
                  FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
              .setStrDisableReason(
                  FASTableContract.convertDisableReasonToDBValue(
                      mARsPackageInfo.getDisableReason()))
              .setStrExtras(
                  FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
              .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
              .setStrDisableResetTime(Long.toString(mARsPackageInfo.getDisableResetTime()))
              .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
              .build();
    } else {
      fASEntity = null;
    }
    cancelPolicy(
        mARsPackageInfo.getName(),
        mARsPackageInfo.getAppliedPolicy() != null ? mARsPackageInfo.getAppliedPolicy().num : 0,
        mARsPackageInfo.getUserId());
    if (fASEntity != null) {
      MARsDBManager.getInstance().sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
    }
    if (mARsPackageInfo.nextKillTimeForLongRunningProcess != 0) {
      mARsPackageInfo.nextKillTimeForLongRunningProcess =
          SystemClock.uptimeMillis() + this.KEEP_NO_FILTER_MIN_DURATION;
    }
  }

  public void onAppUsedForTimeChanged(long j) {
    if (MARsDebugConfig.DEBUG_MARs) {
      Slog.v("MARsPolicyManager", "onAppUsedForTimeChanged -- SystemTime Changed : " + j);
    }
    if (Math.abs(j) < 1800000) {
      if (MARsDebugConfig.DEBUG_MARs) {
        Slog.v("MARsPolicyManager", "SystemTime Changed Less than 30 min, didn't care!!");
        return;
      }
      return;
    }
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i = 0; i < this.mMARsTargetPackages.getMap().size(); i++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          if (mARsPackageInfo.getResetTime() != 0) {
            mARsPackageInfo.setResetTime(j);
            mARsPackageInfo.setDisableResetTime(j);
            arrayList.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                    .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                    .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                    .build());
          }
        }
      }
    }
    if (arrayList.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
  }

  public void getBatteryStats() {
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d("MARsPolicyManager", "getBatteryStats called!");
    }
    ArrayMap arrayMap = new ArrayMap();
    SemDeviceHealthManager semDeviceHealthManager = new SemDeviceHealthManager();
    new ArrayList();
    new ArrayList();
    long currentTimeMillis = System.currentTimeMillis();
    ArrayList arrayList =
        (ArrayList) semDeviceHealthManager.getBatteryStats(1, 0L, currentTimeMillis, true);
    if (arrayList != null) {
      Iterator it = arrayList.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        SemBatteryStats semBatteryStats = (SemBatteryStats) it.next();
        if (semBatteryStats != null
            && !isSameDay(semBatteryStats.getEndTimestamp(), currentTimeMillis)) {
          List<SemBatteryStats.AppDetailUsage> appDetailUsages =
              semBatteryStats.getAppDetailUsages();
          if (appDetailUsages != null) {
            for (SemBatteryStats.AppDetailUsage appDetailUsage : appDetailUsages) {
              arrayMap.put(
                  Integer.valueOf(appDetailUsage.getUid()),
                  Double.valueOf(appDetailUsage.getPowerUsage()));
              if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(
                    "MARsPolicyManager",
                    semBatteryStats.getEndTimestamp()
                        + " getBatteryStats(D) app : "
                        + appDetailUsage.getUid()
                        + "|"
                        + appDetailUsage.getPowerUsage()
                        + "|"
                        + appDetailUsage.getScreenPowerUsage());
              }
            }
          }
        }
      }
    }
    synchronized (MARsLock) {
      for (int i = 0; i < this.mMARsTargetPackages.getMap().size(); i++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          if (arrayMap.size() > 0) {
            Double d = (Double) arrayMap.get(Integer.valueOf(mARsPackageInfo.getUid()));
            if (d != null) {
              mARsPackageInfo.setBatteryUsage(d.doubleValue());
            } else {
              mARsPackageInfo.setBatteryUsage(-1.0d);
            }
          } else {
            mARsPackageInfo.setBatteryUsage(-1.0d);
          }
        }
      }
    }
  }

  public final boolean isSameDay(long j, long j2) {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTimeInMillis(j);
    GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
    gregorianCalendar2.setTimeInMillis(j2);
    return gregorianCalendar.get(1) == gregorianCalendar2.get(1)
        && gregorianCalendar.get(2) == gregorianCalendar2.get(2)
        && gregorianCalendar.get(5) == gregorianCalendar2.get(5);
  }

  public void doUpdatePackages(boolean z) {
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d("MARsPolicyManager", "doUpdatePackages called!");
    }
    updateFromMARsMainThread();
  }

  public void notifyAppSleepToDC(String str, ArrayList arrayList, ArrayList arrayList2) {
    if (this.mContext != null) {
      Intent intent = new Intent();
      intent.addFlags(16777216);
      intent.setAction("com.sec.android.mars.APP_SLEEP_NOTIFY");
      intent.setPackage(SMART_MANAGER_PKG_NAME);
      intent.putExtra("type", str);
      intent.putExtra("specificpackagelist", arrayList);
      intent.putExtra("specificpackageUidlist", arrayList2);
      this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
      addDebugInfoToHistory("NOTI", str);
    }
  }

  public void notifyAnomalyApp(String str, int i, String str2) {
    if (this.mContext != null) {
      Intent intent = new Intent();
      intent.addFlags(16777216);
      intent.setAction("com.sec.android.sdhms.action.FGS_ANOMALY");
      intent.putExtra("pkgName", str);
      intent.putExtra("userId", UserHandle.getUserId(i));
      intent.putExtra("uid", i);
      intent.putExtra("type", str2);
      intent.setPackage("com.sec.android.sdhms");
      this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
      Slog.d(
          "MARsPolicyManager", "notifyAnomalyApp pkgname:" + str + " uid:" + i + " type:" + str2);
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(str2);
      sb.append("]");
      sb.append(i);
      addDebugInfoToHistory("NOTI", sb.toString());
    }
  }

  public void setLastNotiSentTimeForDisabled(long j) {
    this.mLastNotiSentTimeForDisabled = j;
  }

  public final void updateFromMARsMainThread() {
    boolean z;
    int i;
    long j;
    String[] strArr = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    String[] strArr2 = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    if (getIsManualMode()) {
      Slog.d("MARsPolicyManager", "Now manual mode is on, we will not update anything!");
      return;
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.v("MARsPolicyManager", "updateFromMARsThread");
    }
    long currentTimeMillis = System.currentTimeMillis();
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      int i2 = 0;
      z = false;
      while (i2 < this.mMARsTargetPackages.getMap().size()) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i2);
        int i3 = 0;
        while (i3 < sparseArray.size()) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
          if (isCurrentUser(mARsPackageInfo.getUserId())
              && mARsPackageInfo.getFasType() != 256
              && (mARsPackageInfo.getMaxLevel() != 4
                  || !isDisabledByUser(mARsPackageInfo.getDisableReason()))) {
            if (!mARsPackageInfo.getDisabled()
                && mARsPackageInfo.isSCPMTarget()
                && mARsPackageInfo.getDisableReason() != 8
                && levelChange(IInstalld.FLAG_FORCE, mARsPackageInfo)) {
              mARsPackageInfo.setDisableReason(16);
              if (!z) {
                z = true;
              }
              strArr[4] = strArr[4] + " " + mARsPackageInfo.getUid();
              arrayList.add(
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : null)
                      .setStrNew(mARsPackageInfo.getFASEnabled() ? "1" : null)
                      .setStrFasReason(
                          mARsPackageInfo.getFasType() == 1
                              ? FASTableContract.convertFASTypeToReason(
                                  mARsPackageInfo.getFasType())
                              : null)
                      .setStrDisableReason(
                          FASTableContract.convertDisableReasonToDBValue(
                              mARsPackageInfo.getDisableReason()))
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .build());
            }
            i = i2;
            if (this.mLastNotiSentTimeForDisabled != 0 && mARsPackageInfo.getState() == 8) {
              if (currentTimeMillis - this.mLastNotiSentTimeForDisabled
                  > (this.isTimeChangedForDebug
                      ? this.mAutoDeepSleepTimeForDebug
                      : BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)) {
                j = currentTimeMillis;
                int filterForSpecificPolicy =
                    FilterManager.getInstance()
                        .filterForSpecificPolicy(
                            7,
                            mARsPackageInfo.getName(),
                            mARsPackageInfo.getUserId(),
                            mARsPackageInfo.getUid());
                if (filterForSpecificPolicy != 0) {
                  strArr2[4] =
                      strArr2[4]
                          + " "
                          + mARsPackageInfo.getUid()
                          + XmlUtils.STRING_ARRAY_SEPARATOR
                          + filterForSpecificPolicy;
                } else if (levelChange(8, mARsPackageInfo)) {
                  if (!z) {
                    z = true;
                  }
                  strArr[4] = strArr[4] + " " + mARsPackageInfo.getUid();
                  arrayList.add(
                      new FASEntityBuilder()
                          .setStrPkgName(mARsPackageInfo.getName())
                          .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                          .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : null)
                          .setStrNew(mARsPackageInfo.getFASEnabled() ? "1" : null)
                          .setStrFasReason(
                              mARsPackageInfo.getFasType() == 1
                                  ? FASTableContract.convertFASTypeToReason(
                                      mARsPackageInfo.getFasType())
                                  : null)
                          .setStrDisableReason(FASTableContract.convertDisableReasonToDBValue(1))
                          .setStrExtras(
                              FASTableContract.convertStateToDBExtrasValue(
                                  mARsPackageInfo.getState()))
                          .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                          .build());
                  if (!this.mIsLastNotiSentTimeForDisabledDismiss) {
                    this.mIsLastNotiSentTimeForDisabledDismiss = true;
                    MARsHandler.getInstance()
                        .sendNotifyDeviceCareMsgToMainHandler("deepsleepdismiss", null, null);
                  }
                } else {
                  strArr2[4] = strArr2[4] + " " + mARsPackageInfo.getUid();
                }
                i3++;
                i2 = i;
                currentTimeMillis = j;
              }
            }
            j = currentTimeMillis;
            if (mARsPackageInfo.getMaxLevel() == 2
                && mARsPackageInfo.getFASEnabled()
                && 1 == mARsPackageInfo.getFasType()
                && levelChange(1024, mARsPackageInfo)
                && mARsPackageInfo.getFasType() == 128) {
              arrayList.add(
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                      .setStrFasReason(
                          FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                      .setStrDisableReason(
                          FASTableContract.convertDisableReasonToDBValue(
                              mARsPackageInfo.getDisableReason()))
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                      .setStrDisableResetTime(Long.toString(mARsPackageInfo.getDisableResetTime()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .build());
            }
            if (mARsPackageInfo.getState() < 4 && !isChinaPolicyEnabled()) {
              UidStateMgr.getInstance().isUidActive(mARsPackageInfo.getUid());
            }
            i3++;
            i2 = i;
            currentTimeMillis = j;
          }
          j = currentTimeMillis;
          i = i2;
          i3++;
          i2 = i;
          currentTimeMillis = j;
        }
        i2++;
      }
    }
    if (z) {
      addDebugInfoToHistory("LVU", convertLevelChangeInfoToString(strArr, strArr2));
    }
    if (arrayList.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:87:0x0290, code lost:

     if ((java.lang.System.currentTimeMillis() - r12.mLastNotiSentTimeForDisabled) > (r12.isTimeChangedForDebug ? r12.mAutoDeepSleepTimeForDebug * 2 : 1296000000)) goto L91;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean levelChange(int i, MARsPackageInfo mARsPackageInfo) {
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(
          "MARsPolicyManager",
          "levelChange:"
              + mARsPackageInfo.getName()
              + "("
              + mARsPackageInfo.getUid()
              + ") state:"
              + i);
    }
    if (i <= 8
        && (AllowListFilter.getInstance().isInDefaultAllowList(mARsPackageInfo.getName())
            || !mARsPackageInfo.getHasAppIcon())) {
      return false;
    }
    if (i == 1) {
      mARsPackageInfo.setState(2);
    } else if (i == 2) {
      mARsPackageInfo.setCurLevel(2);
      mARsPackageInfo.setMaxLevel(2);
      if (!mARsPackageInfo.getFASEnabled()) {
        MARsHandler.getInstance()
            .sendCallSetModeMsgToMainHandler(
                mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
        if (this.ENABLE_RESTRICTED_BUCKET) {
          MARsHandler.getInstance()
              .sendCallSetAppStandbyBucketMsgToMainHandler(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), 45, false);
        }
        mARsPackageInfo.setFASEnabled(true);
        mARsPackageInfo.setState(4);
        mARsPackageInfo.setFasType(1);
        mARsPackageInfo.setFasReason(
            FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
      }
    } else {
      switch (i) {
        case 4:
          if (!MARsVersionManager.getInstance()
              .isAdjustRestrictionMatch(21, mARsPackageInfo.getName(), null, null)) {
            if (this.mLastNotiSentTimeForDisabled != 0) {
              break;
            }
            this.mLastNotiSentTimeForDisabled = System.currentTimeMillis();
            this.mIsLastNotiSentTimeForDisabledDismiss = false;
            MARsHandler.getInstance().sendNotifyDeviceCareMsgToMainHandler("deepsleep", null, null);
            mARsPackageInfo.setState(8);
            break;
          } else {
            return false;
          }
        case 8:
          mARsPackageInfo.setMaxLevel(4);
          mARsPackageInfo.setState(16);
          mARsPackageInfo.setDisableReason(1);
          if (!mARsPackageInfo.getFASEnabled()) {
            MARsHandler.getInstance()
                .sendCallSetModeMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
            mARsPackageInfo.setFASEnabled(true);
            mARsPackageInfo.setFasType(1);
            mARsPackageInfo.setFasReason(
                FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
            break;
          }
          break;
        case 16:
        case 32:
          mARsPackageInfo.setCurLevel(2);
          mARsPackageInfo.setMaxLevel(2);
          mARsPackageInfo.setFASEnabled(true);
          mARsPackageInfo.setState(4);
          break;
        case 64:
          mARsPackageInfo.setCurLevel(2);
          mARsPackageInfo.setMaxLevel(2);
          mARsPackageInfo.setFASEnabled(true);
          MARsHandler.getInstance()
              .sendCallSetModeMsgToMainHandler(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
          if (mARsPackageInfo.getState() == 8) {
            mARsPackageInfo.setState(4);
          }
          if (this.ENABLE_RESTRICTED_BUCKET) {
            mARsPackageInfo.setIsInRestrictedBucket(true);
            MARsHandler.getInstance()
                .sendCallSetAppStandbyBucketMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), 45, true);
            break;
          }
          break;
        case 128:
          mARsPackageInfo.setMaxLevel(4);
          mARsPackageInfo.setFasType(2);
          mARsPackageInfo.setFasReason(
              FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
          mARsPackageInfo.setDisableReason(2);
          if (!mARsPackageInfo.getFASEnabled()) {
            MARsHandler.getInstance()
                .sendCallSetModeMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
            mARsPackageInfo.setFASEnabled(true);
            break;
          }
          break;
        case 256:
          mARsPackageInfo.setCurLevel(0);
          mARsPackageInfo.setMaxLevel(1);
          mARsPackageInfo.setFASEnabled(false);
          mARsPackageInfo.setResetTime(0L);
          mARsPackageInfo.setState(1);
          if (this.ENABLE_RESTRICTED_BUCKET) {
            mARsPackageInfo.setIsInRestrictedBucket(false);
            MARsHandler.getInstance()
                .sendCallSetAppStandbyBucketMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), 10, true);
            break;
          }
          break;
        case 512:
          mARsPackageInfo.setMaxLevel(1);
          mARsPackageInfo.setState(1);
          MARsHandler.getInstance()
              .sendCallSetModeMsgToMainHandler(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 0);
          mARsPackageInfo.setFASEnabled(false);
          mARsPackageInfo.setResetTime(0L);
          mARsPackageInfo.setFasType(256);
          mARsPackageInfo.setFasReason(
              FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
          mARsPackageInfo.setDisableReason(8);
          break;
        case 1024:
          if (!isDisabledByUser(mARsPackageInfo.getDisableReason())) {
            if (mARsPackageInfo.getFASEnabled()) {
              if (mARsPackageInfo.getFasType() == 1
                  || mARsPackageInfo.getFasType() == 64
                  || (!isChinaPolicyEnabled() && mARsPackageInfo.getFasType() == 0)) {
                mARsPackageInfo.setMaxLevel(1);
                MARsHandler.getInstance()
                    .sendCallSetModeMsgToMainHandler(
                        mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 0);
                mARsPackageInfo.setFASEnabled(false);
                mARsPackageInfo.setFasType(128);
                mARsPackageInfo.setFasReason(
                    FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
              } else {
                if (this.ENABLE_RESTRICTED_BUCKET) {
                  mARsPackageInfo.setIsInRestrictedBucket(false);
                }
                mARsPackageInfo.setMaxLevel(2);
              }
            } else {
              mARsPackageInfo.setMaxLevel(1);
            }
            if (mARsPackageInfo.getState() == 8 || mARsPackageInfo.getState() == 2) {
              mARsPackageInfo.setFasType(128);
            }
            mARsPackageInfo.setResetTime(0L);
            mARsPackageInfo.setState(1);
            if (isChinaPolicyEnabled()
                && mARsPackageInfo.getFASEnabled()
                && (!FreecessController.getInstance().getFreecessEnabled()
                    || mARsPackageInfo.getSharedUidName() != null)) {
              mARsPackageInfo.setMaxLevel(3);
            }
          }
          mARsPackageInfo.setIsInUsageStats(false);
          mARsPackageInfo.setCurLevel(0);
          break;
        case IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES /* 2048 */:
          mARsPackageInfo.setMaxLevel(isChinaPolicyEnabled() ? 3 : 2);
          mARsPackageInfo.setState(4);
          if (!mARsPackageInfo.getFASEnabled()) {
            MARsHandler.getInstance()
                .sendCallSetModeMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
            mARsPackageInfo.setFASEnabled(true);
            mARsPackageInfo.setFasType(1);
            mARsPackageInfo.setFasReason(
                FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
            break;
          }
          break;
        case IInstalld.FLAG_USE_QUOTA /* 4096 */:
          mARsPackageInfo.setCurLevel(0);
          break;
        case IInstalld.FLAG_FORCE /* 8192 */:
          mARsPackageInfo.setMaxLevel(4);
          mARsPackageInfo.setState(16);
          if (!mARsPackageInfo.getFASEnabled()) {
            MARsHandler.getInstance()
                .sendCallSetModeMsgToMainHandler(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 1);
            mARsPackageInfo.setFASEnabled(true);
            mARsPackageInfo.setFasType(1);
            mARsPackageInfo.setFasReason(
                FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
            break;
          }
          break;
      }
    }
    return true;
  }

  public void updateFasState(String str, int i, int i2) {
    try {
      if (this.mAppOpsService == null) {
        this.mAppOpsService = this.mAm.getAppOpsService();
      }
      IAppOpsService iAppOpsService = this.mAppOpsService;
      if (iAppOpsService != null) {
        iAppOpsService.setMode(70, i, str, i2);
      }
    } catch (RemoteException e) {
      Slog.e("MARsPolicyManager", "updateFasState exception:" + e);
    }
  }

  public void callSetAppStandbyBucket(String str, int i, int i2, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (this.mAppStandby == null) {
          this.mAppStandby =
              (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
        }
        this.mAppStandby.setAppStandbyBucketForMARs(str, i, i2, 1792, false, z);
      } catch (Exception e) {
        Slog.e("MARsPolicyManager", "callSetAppStandbyBucket exception:" + e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void callSetAppStandbyBuckets(int i, int i2, boolean z) {
    if (this.mAppStandby == null) {
      this.mAppStandby = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
    }
    if (this.mAppStandby != null) {
      ArrayList arrayList = new ArrayList();
      synchronized (this.mMARsTargetPackages) {
        for (int i3 = 0; i3 < this.mMARsTargetPackages.getMap().size(); i3++) {
          SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i3);
          for (int i4 = 0; i4 < sparseArray.size(); i4++) {
            MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i4);
            if (mARsPackageInfo != null) {
              arrayList.add(new AppStandbyInfo(mARsPackageInfo.getName(), i2));
            }
          }
        }
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAppStandby.setAppStandbyBucketsForMARs(arrayList, i, i2, 1792, false, z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void updateSpecificPolicyTargetPackages(ArrayList arrayList, int i, int i2) {
    for (int i3 = 0; i3 < arrayList.size(); i3++) {
      String str = (String) arrayList.get(i3);
      if (str != null) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i2);
          if (mARsPackageInfo != null && mARsPackageInfo.getMaxLevel() != 4) {
            if (i == 5) {
              mARsPackageInfo.setUds(1);
            }
            if (i == 6) {
              mARsPackageInfo.setSBike(1);
            }
            if (i == 10) {
              mARsPackageInfo.setMpsm(1);
            }
          }
        }
      }
    }
  }

  public boolean isSCPMTarget(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
      if (mARsPackageInfo == null) {
        return false;
      }
      return mARsPackageInfo.isSCPMTarget();
    }
  }

  public boolean cancelDisablePolicy(String str, int i, int i2) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
      if (mARsPackageInfo == null) {
        return false;
      }
      Slog.d(
          "MARsPolicyManager",
          "cancelDisablePolicy "
              + str
              + "("
              + i
              + ") "
              + mARsPackageInfo.getDisabled()
              + "|"
              + mARsPackageInfo.getDisableReason()
              + "|"
              + mARsPackageInfo.getAppliedPolicy());
      if (mARsPackageInfo.getAppliedPolicy() != null
          && mARsPackageInfo.getAppliedPolicy().num == 8) {
        if (!mARsPackageInfo.getDisabled()
            && isDisabledByUser(mARsPackageInfo.getDisableReason())) {
          return true;
        }
        int disableType = mARsPackageInfo.getDisableType();
        if (disableType == -1 || disableType == 4) {
          disableType = 0;
        }
        if (!setEnabledSetting(str, i, disableType, i2)) {
          return false;
        }
        changeAutoDisabledAppState(str, i, i2 != 0);
        return true;
      }
      return false;
    }
  }

  public final boolean disableAction(PkgStatusInfo pkgStatusInfo) {
    int enabledStateIfCanBeDisabled;
    if (!pkgStatusInfo.isDisabled
        && pkgStatusInfo.isFASEnabled
        && (enabledStateIfCanBeDisabled =
                getEnabledStateIfCanBeDisabled(pkgStatusInfo.name, pkgStatusInfo.userId))
            >= 0
        && setEnabledSetting(pkgStatusInfo.name, pkgStatusInfo.userId, 4, 0)) {
      pkgStatusInfo.disableType = enabledStateIfCanBeDisabled;
      pkgStatusInfo.isDisabled = true;
      pkgStatusInfo.currentLevel = 4;
    }
    return pkgStatusInfo.isDisabled;
  }

  public final boolean enableAction(PkgStatusInfo pkgStatusInfo) {
    if (pkgStatusInfo.isDisabled) {
      if (setEnabledSetting(
          pkgStatusInfo.name,
          pkgStatusInfo.userId,
          (pkgStatusInfo.disableType == -1 || pkgStatusInfo.disableType == 4)
              ? 0
              : pkgStatusInfo.disableType,
          1)) {
        pkgStatusInfo.disableType = -1;
        pkgStatusInfo.isDisabled = false;
        pkgStatusInfo.currentLevel = 0;
      }
    }
    return !pkgStatusInfo.isDisabled;
  }

  public final int getEnabledStateIfCanBeDisabled(String str, int i) {
    try {
      IPackageManager asInterface =
          IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
      int applicationEnabledSetting = asInterface.getApplicationEnabledSetting(str, i);
      if (applicationEnabledSetting != 0 && applicationEnabledSetting != 1) {
        return -1;
      }
      if (asInterface.isPackageSuspendedForUser(str, i)) {
        return -1;
      }
      return applicationEnabledSetting;
    } catch (Exception e) {
      Slog.e("MARsPolicyManager", "Error occurred in getEnabledStateIfCanBeDisabled()" + e);
      return -1;
    }
  }

  public final boolean getPackageStoppedState(String str, int i) {
    try {
      return ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
          .wasPackageStopped(str, i);
    } catch (IllegalArgumentException e) {
      Slog.w("MARsPolicyManager", "Failed getPackageStoppedState : " + str + ": " + e);
      return true;
    }
  }

  public final boolean setEnabledSetting(String str, int i, int i2, int i3) {
    IPackageManager asInterface =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        asInterface.setApplicationEnabledSetting(str, i2, i3, i, "auto_disabler");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
      } catch (Exception e) {
        Slog.e("MARsPolicyManager", "Error occurred in setEnabledSetting()" + e);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean forceStopAction(PkgStatusInfo pkgStatusInfo) {
    if (!pkgStatusInfo.isFASEnabled) {
      return false;
    }
    closeSocketsForUid(pkgStatusInfo.uid);
    boolean forceStopPackageForMARsLocked =
        this.mAm.forceStopPackageForMARsLocked(
            pkgStatusInfo.name,
            "MARs #" + convertLevelToPolicyNum(3),
            false,
            0,
            true,
            pkgStatusInfo.userId,
            pkgStatusInfo.uid);
    pkgStatusInfo.forceStopTime = System.currentTimeMillis();
    return forceStopPackageForMARsLocked;
  }

  public void closeSocketsForUid(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (this.mNMs == null) {
      this.mNMs =
          INetworkManagementService.Stub.asInterface(
              ServiceManager.getService("network_management"));
    }
    INetworkManagementService iNetworkManagementService = this.mNMs;
    try {
      if (iNetworkManagementService != null) {
        try {
          iNetworkManagementService.closeSocketsForUid(i);
        } catch (Exception e) {
          Slog.e("MARsPolicyManager", "Error occurred while closeSocketsForUid: " + e);
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean forceKillPackage(String str, Policy policy, int i, int i2) {
    String str2 = "MARs #" + policy.num;
    boolean z = true;
    boolean z2 = false;
    boolean z3 = false;
    int i3 = 0;
    boolean z4 = false;
    for (int i4 = 1; i4 <= 16; i4 <<= 1) {
      int i5 = policy.action & i4;
      if (i5 != 1) {
        if (i5 != 2) {
          if (i5 == 4) {
            i3 = 500;
            z3 = true;
          } else if (i5 == 8) {
            z4 = true;
          }
        }
        z3 = false;
        i3 = 0;
      } else {
        z2 = true;
      }
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d("MARsPolicyManager", "forceKillPackage: pkgName: " + str);
    }
    closeSocketsForUid(i2);
    if (z2) {
      this.mAm.forceStopPackage(str, i);
    } else {
      z = this.mAm.forceStopPackageForMARsLocked(str, str2, z3, i3, z4, i, i2);
      if (!z && MARsDebugConfig.DEBUG_MARs) {
        Slog.d(
            "MARsPolicyManager",
            "forceKillPackage: don't force stop package = " + str + ", userId = " + i);
      }
    }
    return z;
  }

  public final void killAction(PkgStatusInfo pkgStatusInfo) {
    this.mAm.mProcessList.killPackageProcessesLSP(
        pkgStatusInfo.name,
        UserHandle.getAppId(pkgStatusInfo.uid),
        pkgStatusInfo.userId,
        0,
        false,
        false,
        true,
        false,
        true,
        false,
        13,
        0,
        "MARs #" + convertLevelToPolicyNum(3));
    pkgStatusInfo.forceStopTime = System.currentTimeMillis();
  }

  public void killPackageProcs(String str, int i, int i2, int i3, boolean z, String str2) {
    this.mAm.killProcessForMARs(str, i, i2, i3, z, str2);
  }

  public void restrictAbusiveApp(String str, int i, int i2) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo =
          getMARsPackageInfo(this.mMARsTargetPackages, str, UserHandle.getUserId(i));
      if (mARsPackageInfo != null) {
        Slog.d(
            "MARsPolicyManager",
            "updateAbusiveAppFromBartender uid:"
                + i
                + " pkgname:"
                + str
                + " type:excessive_unfreez");
        if (MARsFreezeStateRecord.UnfreezeReasonType.UNFREEZE_REASON_ALARM.getTypeNum() == i2) {
          mARsPackageInfo.optionFlag |= 8;
        } else if (MARsFreezeStateRecord.UnfreezeReasonType.UNFREEZE_REASON_PACKET.getTypeNum()
            == i2) {
          mARsPackageInfo.optionFlag |= 16;
          FreecessController.getInstance()
              .updateAbnormalAppFirewall(mARsPackageInfo.getUid(), false);
          closeSocketsForUid(mARsPackageInfo.getUid());
        }
        MARsHandler.getInstance()
            .sendAnomalyMsgToMainHandler(
                mARsPackageInfo.getName(), mARsPackageInfo.getUid(), "excessive_unfreeze");
        addDebugInfoToHistory("Abusive", "[excessive_unfreez]" + i);
      }
    }
  }

  public void cancelRestrict(String str, int i) {
    Slog.d("MARsPolicyManager", "cancelRestrict uid:" + i + " pkgname:" + str);
    MARsHandler.getInstance().sendResetAbusiveEventsMsgToMainHandler(str, i);
    StringBuilder sb = new StringBuilder();
    sb.append("[cancel_restrict]");
    sb.append(i);
    addDebugInfoToHistory("Abusive", sb.toString());
  }

  public void resetAbusiveEvents(String str, int i) {
    Slog.d("MARsPolicyManager", "resetAbusiveEvents uid:" + i + " pkgname:" + str);
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo =
          getMARsPackageInfo(this.mMARsTargetPackages, str, UserHandle.getUserId(i));
      if (mARsPackageInfo != null) {
        Slog.d(
            "MARsPolicyManager",
            "disable restriction uid:" + i + " pkgname:" + str + " type:excessive_unfreeze");
        resetAbusiveFlag(mARsPackageInfo);
      }
    }
  }

  public final void resetAbusiveFlag(MARsPackageInfo mARsPackageInfo) {
    Slog.d(
        "MARsPolicyManager",
        "resetAbusiveFlag uid:"
            + mARsPackageInfo.getUid()
            + " pkgname:"
            + mARsPackageInfo.getName());
    synchronized (MARsLock) {
      resetAbusiveAlarm(mARsPackageInfo);
      resetAbusivePacket(mARsPackageInfo);
    }
  }

  public final void resetAbusiveAlarm(MARsPackageInfo mARsPackageInfo) {
    int i = mARsPackageInfo.optionFlag;
    if ((i & 8) == 8) {
      mARsPackageInfo.optionFlag = i & (-9);
    }
  }

  public final void resetAbusivePacket(MARsPackageInfo mARsPackageInfo) {
    int i = mARsPackageInfo.optionFlag;
    if ((i & 16) == 16) {
      mARsPackageInfo.optionFlag = i & (-17);
      FreecessController.getInstance().updateAbnormalAppFirewall(mARsPackageInfo.getUid(), true);
    }
  }

  public void reportStatusWithMARs(String str, int i, String str2, boolean z) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null && !z) {
        mARsPackageInfo.setCurLevel(0);
        if (isChinaModel) {
          mARsPackageInfo.setCheckJobRunningCount(0);
        }
      }
    }
  }

  public static boolean isIntentProhibited(Intent intent, String str) {
    if (intent == null
        || !"com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY".equals(intent.getAction())) {
      return false;
    }
    return !"com.samsung.android.game.gos".equals(str);
  }

  public void forceStopForRecentKill(String str) {
    int userId = this.mContext.getUserId();
    if (str != null && str.contains(",")) {
      String[] split = str.split(",");
      if (split != null && split.length == 2) {
        String str2 = split[0];
        r1 = str2 != null ? str2 : null;
        try {
          String str3 = split[1];
          if (str3 != null) {
            userId = Integer.parseInt(str3);
          }
        } catch (NumberFormatException unused) {
          Slog.e("MARsPolicyManager", "forceStopForRecentKill parseInt error!");
        }
      }
      str = r1;
    }
    if (str != null) {
      this.mAm.forceStopPackage(str, userId);
    }
  }

  public boolean forceRunPolicyForRecentKill(int i, String str) {
    StringBuilder sb = new StringBuilder();
    Policy policy = getPolicy(i);
    if (policy == null || !policy.enabled) {
      Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
      return false;
    }
    synchronized (MARsLock) {
      int userId = this.mContext.getUserId();
      if (str != null && str.contains(",")) {
        String[] split = str.split(",");
        if (split != null && split.length == 2) {
          String str2 = split[0];
          r7 = str2 != null ? str2 : null;
          try {
            String str3 = split[1];
            if (str3 != null) {
              userId = Integer.parseInt(str3);
            }
          } catch (NumberFormatException unused) {
            Slog.e("MARsPolicyManager", "forceRunPolicyForRecentKill parseInt error!");
          }
        }
        str = r7;
      }
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
      if (mARsPackageInfo == null) {
        return false;
      }
      PkgStatusInfo pkgStatusInfo =
          new PkgStatusInfo(
              mARsPackageInfo.getName(), mARsPackageInfo.getUid(), mARsPackageInfo.getUserId());
      updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
      ActivityManagerService activityManagerService = this.mAm;
      ActivityManagerService.boostPriorityForLockedSection();
      synchronized (activityManagerService) {
        try {
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(pkgStatusInfo.name, pkgStatusInfo.userId)) {
            FreecessController.getInstance()
                .unFreezePackage(pkgStatusInfo.name, pkgStatusInfo.userId, "RecentKill");
          }
          if (!forceKillPackage(
              pkgStatusInfo.name, policy, pkgStatusInfo.userId, pkgStatusInfo.uid)) {
            ActivityManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          pkgStatusInfo.forceStopTime = System.currentTimeMillis();
          pkgStatusInfo.currentLevel = 3;
          sb.append(" " + pkgStatusInfo.uid);
          ActivityManagerService.resetPriorityAfterLockedSection();
          synchronized (MARsLock) {
            updateInfoToMARsPkgStatus(pkgStatusInfo, mARsPackageInfo);
            if (mARsPackageInfo.getAppliedPolicy() != this.disablePolicy) {
              mARsPackageInfo.setAppliedPolicy(policy);
              if (i == 2) {
                levelChange(2, mARsPackageInfo);
              }
            }
            if (this.mMARsRestrictedPackages.get(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUserId())
                == null) {
              this.mMARsRestrictedPackages.put(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
            } else if (mARsPackageInfo.getAppliedPolicy() == this.disablePolicy
                && isDisabledByUser(mARsPackageInfo.getDisableReason())) {
              mARsPackageInfo.setCurLevel(4);
              this.mMARsRestrictedPackages.put(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
            }
            addDebugInfoToHistory("EXE", "Recent " + sb.toString());
            Slog.v(
                "MARsPolicyManager",
                "Recent_Kill: add mRestrictedPackages "
                    + mARsPackageInfo.getName()
                    + " policy --"
                    + mARsPackageInfo.getAppliedPolicy());
          }
          return true;
        } catch (Throwable th) {
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
  }

  public void forceRunPolicyForSpecificPolicy(int i, ArrayList arrayList) {
    int i2;
    SparseArray sparseArray;
    int i3;
    String str;
    MARsPkgMap mARsPkgMap = new MARsPkgMap();
    ArrayList arrayList2 = new ArrayList();
    StringBuilder sb = new StringBuilder();
    Policy policy = getPolicy(i);
    if (policy == null || !policy.enabled) {
      Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
      return;
    }
    int convertPolicyNumToLevel = convertPolicyNumToLevel(policy);
    this.mLastTriggerTime = System.currentTimeMillis();
    ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
    synchronized (MARsLock) {
      int i4 = 0;
      if (arrayList != null) {
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
          String str2 = (String) arrayList.get(i5);
          int userId = this.mContext.getUserId();
          if (str2 != null && str2.contains(", ")) {
            String[] split = str2.split(", ");
            if (split == null || split.length != 2) {
              str = null;
            } else {
              str = split[0];
              if (str == null) {
                str = null;
              }
              try {
                String str3 = split[1];
                if (str3 != null) {
                  userId = UserHandle.getUserId(Integer.parseInt(str3));
                }
              } catch (NumberFormatException unused) {
                Slog.e("MARsPolicyManager", "forceRunPolicyForGamePolicy parseInt error!");
              }
            }
            mARsPkgMap.put(str, userId, 1);
          }
        }
      }
      int i6 = 0;
      while (i6 < this.mMARsTargetPackages.getMap().size()) {
        SparseArray sparseArray2 = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i6);
        int i7 = i4;
        while (i7 < sparseArray2.size()) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray2.valueAt(i7);
          if (i == 9) {
            if (isChinaPolicyEnabled() && !mARsPackageInfo.getFASEnabled()) {
              if (MARsDebugConfig.DEBUG_ENG) {
                Slog.v(
                    "MARsPolicyManager",
                    "package "
                        + mARsPackageInfo.getName()
                        + " is not game policy target, don't execute this policy "
                        + policy);
              }
            } else if (mARsPkgMap.get(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())
                != null) {
              if (MARsDebugConfig.DEBUG_ENG) {
                Slog.v(
                    "MARsPolicyManager",
                    "package "
                        + mARsPackageInfo.getName()
                        + " in survivePkgs, don't execute this policy "
                        + policy);
              }
            }
            i2 = i7;
            sparseArray = sparseArray2;
            i3 = i6;
            i7 = i2 + 1;
            sparseArray2 = sparseArray;
            i6 = i3;
          }
          if (isInPolicyExceptionList(
              mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), policy.num)) {
            if (MARsDebugConfig.DEBUG_ENG) {
              Slog.v(
                  "MARsPolicyManager",
                  "package "
                      + mARsPackageInfo.getName()
                      + " inPolicyAllowList, don't execute this policy "
                      + policy);
            }
            i2 = i7;
            sparseArray = sparseArray2;
            i3 = i6;
            i7 = i2 + 1;
            sparseArray2 = sparseArray;
            i6 = i3;
          } else {
            int i8 = i7;
            if (FilterManager.getInstance()
                    .filterForSpecificPolicy(
                        convertPolicyNumToImportantType(policy.num),
                        mARsPackageInfo.getName(),
                        mARsPackageInfo.getUserId(),
                        mARsPackageInfo.getUid())
                > 0) {
              i2 = i8;
              sparseArray = sparseArray2;
              i3 = i6;
              i7 = i2 + 1;
              sparseArray2 = sparseArray;
              i6 = i3;
            } else {
              i2 = i8;
              sparseArray = sparseArray2;
              i3 = i6;
              PkgStatusInfo pkgStatusInfo =
                  new PkgStatusInfo(
                      mARsPackageInfo.getName(),
                      mARsPackageInfo.getUid(),
                      mARsPackageInfo.getUserId());
              updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
              if (i == 5) {
                if (mARsPackageInfo.getUds() == 1) {
                  arrayList2.add(pkgStatusInfo);
                }
              } else if (i == 6) {
                if (mARsPackageInfo.getSBike() == 1) {
                  arrayList2.add(pkgStatusInfo);
                }
              } else if (i == 10) {
                if (mARsPackageInfo.getMpsm() == 1) {
                  arrayList2.add(pkgStatusInfo);
                }
              } else {
                arrayList2.add(pkgStatusInfo);
              }
              i7 = i2 + 1;
              sparseArray2 = sparseArray;
              i6 = i3;
            }
          }
        }
        i6++;
        i4 = 0;
      }
    }
    long uptimeMillis = SystemClock.uptimeMillis();
    boolean z = false;
    for (int size = arrayList2.size() - 1; size >= 0; size--) {
      if (SystemClock.uptimeMillis() - uptimeMillis >= 50) {
        try {
          Thread.sleep(10L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        uptimeMillis = SystemClock.uptimeMillis();
      }
      ActivityManagerService activityManagerService = this.mAm;
      ActivityManagerService.boostPriorityForLockedSection();
      synchronized (activityManagerService) {
        try {
          PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) arrayList2.get(size);
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(pkgStatusInfo2.name, pkgStatusInfo2.userId)) {
            FreecessController.getInstance()
                .unFreezePackage(pkgStatusInfo2.name, pkgStatusInfo2.userId, policy.name);
          }
          if (forceKillPackage(
              pkgStatusInfo2.name, policy, pkgStatusInfo2.userId, pkgStatusInfo2.uid)) {
            pkgStatusInfo2.forceStopTime = System.currentTimeMillis();
            pkgStatusInfo2.currentLevel = convertPolicyNumToLevel;
            sb.append(" " + pkgStatusInfo2.uid);
            ActivityManagerService.resetPriorityAfterLockedSection();
            z = true;
          } else {
            arrayList2.remove(size);
            ActivityManagerService.resetPriorityAfterLockedSection();
          }
        } catch (Throwable th) {
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
    synchronized (MARsLock) {
      for (int i9 = 0; i9 < arrayList2.size(); i9++) {
        PkgStatusInfo pkgStatusInfo3 = (PkgStatusInfo) arrayList2.get(i9);
        MARsPackageInfo mARsPackageInfo2 =
            getMARsPackageInfo(
                this.mMARsTargetPackages, pkgStatusInfo3.name, pkgStatusInfo3.userId);
        if (mARsPackageInfo2 != null) {
          updateInfoToMARsPkgStatus(pkgStatusInfo3, mARsPackageInfo2);
          if (mARsPackageInfo2.getAppliedPolicy() != this.disablePolicy) {
            mARsPackageInfo2.setAppliedPolicy(policy);
          }
          if (this.mMARsRestrictedPackages.get(
                  mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())
              == null) {
            this.mMARsRestrictedPackages.put(
                mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), mARsPackageInfo2);
          }
          if (MARsDebugConfig.DEBUG_ENG) {
            Slog.v(
                "MARsPolicyManager",
                "add mRestrictedPackages " + mARsPackageInfo2.getName() + " policy --" + policy);
          }
        }
      }
    }
    if (z) {
      addDebugInfoToHistory("EXE", policy.name + " " + sb.toString());
    }
  }

  public void forceRunPolicyForSpecificPackage(int i, ArrayList arrayList) {
    boolean z;
    int i2;
    SparseArray sparseArray;
    MARsPackageInfo mARsPackageInfo;
    MARsPkgMap mARsPkgMap = new MARsPkgMap();
    ArrayList arrayList2 = new ArrayList();
    StringBuilder sb = new StringBuilder();
    Policy policy = getPolicy(i);
    if (policy == null || !policy.enabled) {
      Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
      return;
    }
    this.mLastTriggerTime = System.currentTimeMillis();
    ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
    synchronized (MARsLock) {
      int i3 = 0;
      for (int i4 = 0; i4 < arrayList.size(); i4++) {
        String str = (String) arrayList.get(i4);
        int userId = this.mContext.getUserId();
        if (str != null && str.contains(",")) {
          String[] split = str.split(",");
          mARsPackageInfo = null;
          mARsPackageInfo = null;
          if (split != null && split.length == 2) {
            String str2 = split[0];
            String str3 = str2 != null ? str2 : null;
            try {
              String str4 = split[1];
              if (str4 != null) {
                userId = Integer.parseInt(str4);
              }
            } catch (NumberFormatException unused) {
              Slog.e("MARsPolicyManager", "forceRunPolicyForSpecificPackage parseInt error!");
            }
            mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str3, userId);
          }
        } else {
          mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
        }
        if (mARsPackageInfo != null) {
          mARsPkgMap.put(mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
        }
      }
      int i5 = 0;
      while (i5 < mARsPkgMap.getMap().size()) {
        SparseArray sparseArray2 = (SparseArray) mARsPkgMap.getMap().valueAt(i5);
        int i6 = i3;
        while (i6 < sparseArray2.size()) {
          MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) sparseArray2.valueAt(i6);
          if (isInPolicyExceptionList(
              mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), policy.num)) {
            if (MARsDebugConfig.DEBUG_ENG) {
              Slog.v(
                  "MARsPolicyManager",
                  "package "
                      + mARsPackageInfo2.getName()
                      + " inPolicyAllowList, don't execute this policy "
                      + policy);
            }
          } else if (FilterManager.getInstance()
                  .filterForSpecificPolicy(
                      1,
                      mARsPackageInfo2.getName(),
                      mARsPackageInfo2.getUserId(),
                      mARsPackageInfo2.getUid())
              <= 0) {
            i2 = i6;
            sparseArray = sparseArray2;
            PkgStatusInfo pkgStatusInfo =
                new PkgStatusInfo(
                    mARsPackageInfo2.getName(),
                    mARsPackageInfo2.getUid(),
                    mARsPackageInfo2.getUserId());
            updateInfoToPkgStatus(mARsPackageInfo2, pkgStatusInfo);
            arrayList2.add(pkgStatusInfo);
            i6 = i2 + 1;
            sparseArray2 = sparseArray;
          }
          i2 = i6;
          sparseArray = sparseArray2;
          i6 = i2 + 1;
          sparseArray2 = sparseArray;
        }
        i5++;
        i3 = 0;
      }
    }
    ActivityManagerService activityManagerService = this.mAm;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        z = false;
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
          PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) arrayList2.get(size);
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(pkgStatusInfo2.name, pkgStatusInfo2.userId)) {
            FreecessController.getInstance()
                .unFreezePackage(pkgStatusInfo2.name, pkgStatusInfo2.userId, "SMKill");
          }
          if (forceKillPackage(
              pkgStatusInfo2.name, policy, pkgStatusInfo2.userId, pkgStatusInfo2.uid)) {
            pkgStatusInfo2.forceStopTime = System.currentTimeMillis();
            pkgStatusInfo2.currentLevel = 3;
            sb.append(" " + pkgStatusInfo2.uid);
            z = true;
          } else {
            arrayList2.remove(size);
          }
        }
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
    synchronized (MARsLock) {
      for (int i7 = 0; i7 < arrayList2.size(); i7++) {
        PkgStatusInfo pkgStatusInfo3 = (PkgStatusInfo) arrayList2.get(i7);
        MARsPackageInfo mARsPackageInfo3 =
            getMARsPackageInfo(
                this.mMARsTargetPackages, pkgStatusInfo3.name, pkgStatusInfo3.userId);
        if (mARsPackageInfo3 != null) {
          updateInfoToMARsPkgStatus(pkgStatusInfo3, mARsPackageInfo3);
          if (mARsPackageInfo3.getAppliedPolicy() != this.disablePolicy) {
            mARsPackageInfo3.setAppliedPolicy(policy);
            if (i == 2) {
              levelChange(2, mARsPackageInfo3);
            }
          }
          if (this.mMARsRestrictedPackages.get(
                  mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId())
              == null) {
            this.mMARsRestrictedPackages.put(
                mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId(), mARsPackageInfo3);
          } else if (mARsPackageInfo3.getAppliedPolicy() == this.disablePolicy
              && isDisabledByUser(mARsPackageInfo3.getDisableReason())) {
            mARsPackageInfo3.setCurLevel(4);
            this.mMARsRestrictedPackages.put(
                mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId(), mARsPackageInfo3);
          }
        }
        Slog.v(
            "MARsPolicyManager",
            "add mRestrictedPackages "
                + mARsPackageInfo3.getName()
                + " policy --"
                + mARsPackageInfo3.getAppliedPolicy());
      }
    }
    if (z) {
      addDebugInfoToHistory("SM", sb.toString());
    }
  }

  public final boolean skipTriggerAction(MARsPackageInfo mARsPackageInfo) {
    int maxLevel = mARsPackageInfo.getMaxLevel();
    if (mARsPackageInfo.isSCPMTarget()) {
      return false;
    }
    if ((maxLevel > 2 && maxLevel == mARsPackageInfo.getCurLevel()) || maxLevel > 4) {
      return true;
    }
    if (maxLevel == 4) {
      if (isChinaPolicyEnabled()) {
        return false;
      }
      return mARsPackageInfo.getDisabled()
          || UidStateMgr.getInstance().isUidActive(mARsPackageInfo.getUid());
    }
    if (isFirstTimeTriggerAutorun()) {
      return false;
    }
    if (isChinaPolicyEnabled()) {
      if (!UidStateMgr.getInstance().isUidRunning(mARsPackageInfo.getUid())) {
        if (mARsPackageInfo.getCurLevel() > 2 || !mARsPackageInfo.getFASEnabled()) {
          return true;
        }
        mARsPackageInfo.setMaxLevel(3);
      }
      return false;
    }
    if (!UidStateMgr.getInstance().isUidRunning(mARsPackageInfo.getUid())) {
      return true;
    }
    if (UidStateMgr.getInstance().isUidActive(mARsPackageInfo.getUid())) {
      return (getDeviceIdleModeState() && maxLevel < 3 && (mARsPackageInfo.optionFlag & 4) == 0)
          ? false
          : true;
    }
    return false;
  }

  public void triggerAction() {
    boolean z;
    ArrayList arrayList;
    String[] strArr;
    int[] iArr;
    int i;
    int i2;
    boolean z2;
    WindowManagerService windowManagerService;
    int i3;
    SparseArray sparseArray;
    int i4;
    String[] strArr2;
    int[] iArr2;
    PkgStatusInfo pkgStatusInfo;
    int i5;
    String str;
    int i6;
    PkgStatusInfo pkgStatusInfo2;
    PkgStatusInfo pkgStatusInfo3;
    int i7;
    Slog.d("MARsPolicyManager", "triggerAction called!");
    ArrayList arrayList2 = new ArrayList();
    String[] strArr3 = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    String[] strArr4 = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    int[] iArr3 = new int[0];
    if (FreecessController.getInstance().getIsSmartSwitchWorking()) {
      return;
    }
    this.mLastTriggerTime = System.currentTimeMillis();
    long uptimeMillis = SystemClock.uptimeMillis();
    FreecessController.getInstance().updateRunningLocationPackages();
    BlueToothConnectedFilter.getInstance().updateBTUsingPackages();
    ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
    if (getDeviceIdleModeState()) {
      ActiveSensorFilter.getInstance().getActiveSensorList();
    }
    if (FreecessController.getInstance().getFreecessEnabled()
        && FreecessController.getInstance().getScreenOnState()
        && !isFirstTimeTriggerAutorun()) {
      return;
    }
    synchronized (MARsLock) {
      z = false;
      int i8 = 0;
      arrayList = null;
      while (true) {
        int i9 = 1;
        if (i8 >= this.mMARsTargetPackages.getMap().size()) {
          break;
        }
        SparseArray sparseArray2 = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i8);
        ArrayList arrayList3 = arrayList;
        boolean z3 = z;
        int i10 = 0;
        while (i10 < sparseArray2.size()) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray2.valueAt(i10);
          if (mARsPackageInfo != null
              && isCurrentUser(mARsPackageInfo.getUserId())
              && !skipTriggerAction(mARsPackageInfo)) {
            int uid = mARsPackageInfo.getUid();
            int maxLevel = mARsPackageInfo.getMaxLevel();
            int i11 = i8;
            String name = mARsPackageInfo.getName();
            int userId = mARsPackageInfo.getUserId();
            int i12 = i10;
            if (maxLevel == i9 || maxLevel == 2) {
              sparseArray = sparseArray2;
              iArr2 = iArr3;
              i4 = i11;
              i3 = i12;
              if (!isChinaPolicyEnabled() || !mARsPackageInfo.getFASEnabled()) {
                pkgStatusInfo = null;
              } else if (MARsVersionManager.getInstance()
                      .isAdjustRestrictionMatch(10, name, null, null)
                  && FilterManager.getInstance().filterForSpecificPolicy(14, name, userId, uid)
                      == 0) {
                mARsPackageInfo.setMaxLevel(3);
                pkgStatusInfo3 =
                    new PkgStatusInfo(
                        mARsPackageInfo.getName(),
                        mARsPackageInfo.getUid(),
                        mARsPackageInfo.getUserId());
                updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
                strArr2 = strArr3;
              } else {
                pkgStatusInfo = null;
              }
              if (FreecessController.getInstance().isInFreecessExcludeList(mARsPackageInfo)) {
                if (isNeedOptimizedApp(mARsPackageInfo)) {
                  mARsPackageInfo.setMaxLevel(3);
                  Slog.d(
                      "MARsPolicyManager", "Level up freecess excluded app : " + name + "|userId");
                }
                strArr2 = strArr3;
                i10 = i3 + 1;
                strArr3 = strArr2;
                sparseArray2 = sparseArray;
                iArr3 = iArr2;
                i8 = i4;
                i9 = 1;
              } else {
                int filterForSpecificPolicy =
                    FilterManager.getInstance().filterForSpecificPolicy(15, name, userId, uid);
                if (filterForSpecificPolicy == 0) {
                  i5 = filterForSpecificPolicy;
                  strArr2 = strArr3;
                  i6 = uid;
                  str = name;
                  pkgStatusInfo2 =
                      new PkgStatusInfo(
                          mARsPackageInfo.getName(),
                          mARsPackageInfo.getUid(),
                          mARsPackageInfo.getUserId());
                  updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo2);
                } else {
                  i5 = filterForSpecificPolicy;
                  str = name;
                  strArr2 = strArr3;
                  i6 = uid;
                  if (isChinaModel && i5 == 18) {
                    int checkJobRunningCount = mARsPackageInfo.getCheckJobRunningCount() + 1;
                    mARsPackageInfo.setCheckJobRunningCount(checkJobRunningCount);
                    if (checkJobRunningCount >= 5) {
                      ArrayList arrayList4 = arrayList3 == null ? new ArrayList() : arrayList3;
                      arrayList4.add(Integer.valueOf(mARsPackageInfo.getUid()));
                      arrayList3 = arrayList4;
                    }
                  }
                  strArr4[maxLevel] =
                      strArr4[maxLevel]
                          + " "
                          + mARsPackageInfo.getUid()
                          + XmlUtils.STRING_ARRAY_SEPARATOR
                          + i5;
                  pkgStatusInfo2 = pkgStatusInfo;
                  z3 = true;
                }
                if (ENABLE_KILL_LONG_RUNNING_PROCESS) {
                  long foregroundServiceStartTime = getForegroundServiceStartTime(i6);
                  boolean z4 = foregroundServiceStartTime != 0;
                  if (i5 == 0) {
                    if (z4) {
                      long j = mARsPackageInfo.nextKillTimeForLongRunningProcess;
                      if (j == 0) {
                        mARsPackageInfo.nextKillTimeForLongRunningProcess =
                            this.KEEP_NO_FILTER_MIN_DURATION + uptimeMillis;
                      } else if (uptimeMillis >= j
                          && uptimeMillis
                              >= foregroundServiceStartTime + this.KEEP_NO_FILTER_MIN_DURATION) {
                        mARsPackageInfo.nextKillTimeForLongRunningProcess = 0L;
                        MARsHandler.getInstance()
                            .sendKillPackageProcsMsgToMainHandler(
                                str, i6, userId, 200, false, "KLRP uid " + i6);
                        addDebugInfoToHistory("KLRP", "uid " + i6);
                      }
                    }
                  } else if (i5 != 8 && mARsPackageInfo.nextKillTimeForLongRunningProcess != 0) {
                    mARsPackageInfo.nextKillTimeForLongRunningProcess =
                        this.KEEP_NO_FILTER_MIN_DURATION + uptimeMillis;
                  }
                }
                pkgStatusInfo3 = pkgStatusInfo2;
              }
            } else if (maxLevel == 3) {
              sparseArray = sparseArray2;
              iArr2 = iArr3;
              i4 = i11;
              i3 = i12;
              if (isChinaPolicyEnabled()) {
                if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(10, name, null, null)
                    && FilterManager.getInstance().filterForSpecificPolicy(14, name, userId, uid)
                        == 0) {
                  pkgStatusInfo3 =
                      new PkgStatusInfo(
                          mARsPackageInfo.getName(),
                          mARsPackageInfo.getUid(),
                          mARsPackageInfo.getUserId());
                  updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
                } else {
                  int filterForSpecificPolicy2 =
                      FilterManager.getInstance().filterForSpecificPolicy(3, name, userId, uid);
                  if (filterForSpecificPolicy2 == 0) {
                    pkgStatusInfo3 =
                        new PkgStatusInfo(
                            mARsPackageInfo.getName(),
                            mARsPackageInfo.getUid(),
                            mARsPackageInfo.getUserId());
                    updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
                  } else {
                    strArr4[maxLevel] =
                        strArr4[maxLevel]
                            + " "
                            + mARsPackageInfo.getUid()
                            + XmlUtils.STRING_ARRAY_SEPARATOR
                            + filterForSpecificPolicy2;
                    if (13 != filterForSpecificPolicy2 && 12 != filterForSpecificPolicy2) {
                      if (4 != filterForSpecificPolicy2) {
                        if (10 == filterForSpecificPolicy2) {}
                        strArr2 = strArr3;
                        pkgStatusInfo3 = null;
                        z3 = true;
                      }
                    }
                    mARsPackageInfo.setMaxLevel(2);
                    strArr2 = strArr3;
                    z3 = true;
                    i10 = i3 + 1;
                    strArr3 = strArr2;
                    sparseArray2 = sparseArray;
                    iArr3 = iArr2;
                    i8 = i4;
                    i9 = 1;
                  }
                }
                strArr2 = strArr3;
              } else {
                int filterForSpecificPolicy3 =
                    FilterManager.getInstance().filterForSpecificPolicy(2, name, userId, uid);
                if (filterForSpecificPolicy3 == 0) {
                  pkgStatusInfo3 =
                      new PkgStatusInfo(
                          mARsPackageInfo.getName(),
                          mARsPackageInfo.getUid(),
                          mARsPackageInfo.getUserId());
                  updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
                  strArr2 = strArr3;
                } else {
                  strArr4[maxLevel] =
                      strArr4[maxLevel]
                          + " "
                          + mARsPackageInfo.getUid()
                          + XmlUtils.STRING_ARRAY_SEPARATOR
                          + filterForSpecificPolicy3;
                  strArr2 = strArr3;
                  pkgStatusInfo3 = null;
                  z3 = true;
                }
              }
            } else if (maxLevel != 4) {
              sparseArray = sparseArray2;
              strArr2 = strArr3;
              iArr2 = iArr3;
              i4 = i11;
              i3 = i12;
              pkgStatusInfo3 = null;
            } else {
              if (!mARsPackageInfo.getDisabled() && mARsPackageInfo.isSCPMTarget()) {
                i3 = i12;
                sparseArray = sparseArray2;
                iArr2 = iArr3;
                i4 = i11;
                pkgStatusInfo3 =
                    new PkgStatusInfo(
                        mARsPackageInfo.getName(),
                        mARsPackageInfo.getUid(),
                        mARsPackageInfo.getUserId());
                updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
              } else {
                sparseArray = sparseArray2;
                iArr2 = iArr3;
                i4 = i11;
                i3 = i12;
                if (MARsVersionManager.getInstance()
                    .isAdjustRestrictionMatch(21, name, null, null)) {
                  i7 = 0;
                } else {
                  i7 = FilterManager.getInstance().filterForSpecificPolicy(7, name, userId, uid);
                  if (i7 == 0) {
                    pkgStatusInfo3 =
                        new PkgStatusInfo(
                            mARsPackageInfo.getName(),
                            mARsPackageInfo.getUid(),
                            mARsPackageInfo.getUserId());
                    updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo3);
                  }
                }
                strArr4[maxLevel] =
                    strArr4[maxLevel]
                        + " "
                        + mARsPackageInfo.getUid()
                        + XmlUtils.STRING_ARRAY_SEPARATOR
                        + i7;
                strArr2 = strArr3;
                pkgStatusInfo3 = null;
                z3 = true;
              }
              strArr2 = strArr3;
            }
            if (pkgStatusInfo3 != null) {
              pkgStatusInfo3.possibleLevel = pkgStatusInfo3.maxLevel;
              arrayList2.add(pkgStatusInfo3);
            }
            i10 = i3 + 1;
            strArr3 = strArr2;
            sparseArray2 = sparseArray;
            iArr3 = iArr2;
            i8 = i4;
            i9 = 1;
          }
          i3 = i10;
          sparseArray = sparseArray2;
          i4 = i8;
          strArr2 = strArr3;
          iArr2 = iArr3;
          i10 = i3 + 1;
          strArr3 = strArr2;
          sparseArray2 = sparseArray;
          iArr3 = iArr2;
          i8 = i4;
          i9 = 1;
        }
        i8++;
        arrayList = arrayList3;
        z = z3;
      }
      strArr = strArr3;
      iArr = iArr3;
    }
    if (arrayList != null && !arrayList.isEmpty()) {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        restrictJobsByUid(((Integer) it.next()).intValue(), true);
      }
    }
    if (getFirstTimeUpdatePkgsState()) {
      i = 0;
      setFirstTimeUpdatePkgsState(false);
    } else {
      i = 0;
    }
    int i13 = i;
    int[] iArr4 = iArr;
    for (int i14 = i13; i14 < arrayList2.size(); i14++) {
      PkgStatusInfo pkgStatusInfo4 = (PkgStatusInfo) arrayList2.get(i14);
      if (isChinaPolicyEnabled()
          || (windowManagerService = this.mAm.mWindowManager) == null
          || !windowManagerService.hasFloatingOrOnScreenWindow(pkgStatusInfo4.uid)) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
          try {
            int i15 = pkgStatusInfo4.possibleLevel;
            if (i15 == 1) {
              i2 = 1;
            } else if (i15 != 2) {
              if (i15 == 3) {
                if (isChinaPolicyEnabled()) {
                  if (forceStopAction(pkgStatusInfo4)) {
                    iArr4 = ArrayUtils.appendInt(iArr4, pkgStatusInfo4.uid);
                  } else {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                  }
                } else {
                  killAction(pkgStatusInfo4);
                }
                i2 = 1;
                pkgStatusInfo4.currentLevel = pkgStatusInfo4.possibleLevel;
                StringBuilder sb = new StringBuilder();
                int i16 = pkgStatusInfo4.currentLevel;
                sb.append(strArr[i16]);
                sb.append(" ");
                sb.append(pkgStatusInfo4.uid);
                strArr[i16] = sb.toString();
                ActivityManagerService.resetPriorityAfterLockedSection();
                i13 = i2;
              } else {
                if (i15 == 4) {
                  if (FreecessController.getInstance().getFreecessEnabled()
                      && FreecessController.getInstance()
                          .isFreezedPackage(pkgStatusInfo4.name, pkgStatusInfo4.userId)) {
                    FreecessController.getInstance()
                        .unFreezePackage(
                            pkgStatusInfo4.name, pkgStatusInfo4.userId, "triggerAction");
                  }
                  if (!getPackageDisablerEnabled() || !disableAction(pkgStatusInfo4)) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                  }
                }
                i2 = 1;
                pkgStatusInfo4.currentLevel = pkgStatusInfo4.possibleLevel;
                StringBuilder sb2 = new StringBuilder();
                int i162 = pkgStatusInfo4.currentLevel;
                sb2.append(strArr[i162]);
                sb2.append(" ");
                sb2.append(pkgStatusInfo4.uid);
                strArr[i162] = sb2.toString();
                ActivityManagerService.resetPriorityAfterLockedSection();
                i13 = i2;
              }
            } else if (this.ENABLE_RESTRICTED_BUCKET
                && pkgStatusInfo4.fasType == 2
                && !pkgStatusInfo4.isInRestrictedBucket) {
              i2 = 1;
              pkgStatusInfo4.isInRestrictedBucket = true;
              MARsHandler.getInstance()
                  .sendCallSetAppStandbyBucketMsgToMainHandler(
                      pkgStatusInfo4.name, pkgStatusInfo4.userId, 45, true);
            } else {
              i2 = 1;
            }
            boolean monitorPacketFlag =
                FreecessController.getInstance()
                    .getMonitorPacketFlag(pkgStatusInfo4.isFASEnabled ? 0 : i2);
            boolean restrictNetworkFlag =
                FreecessController.getInstance()
                    .getRestrictNetworkFlag(pkgStatusInfo4.isFASEnabled ? 0 : i2);
            if (this.mAm.isPendingBroadcastPackageLocked(pkgStatusInfo4.uid)) {
              Slog.d(
                  "MARsPolicyManager",
                  pkgStatusInfo4.name + " has pending broadcast, skip to freeze");
              ActivityManagerService.resetPriorityAfterLockedSection();
            } else {
              if (FreecessController.getInstance().getFreecessEnabled()) {
                if (isChinaModel) {
                  FreecessController.getInstance().makePkgIdleIfNeeded(pkgStatusInfo4.uid);
                }
                z2 =
                    FreecessController.getInstance()
                        .freezePackage(
                            pkgStatusInfo4.uid, "LEV", 2, monitorPacketFlag, restrictNetworkFlag);
              } else {
                z2 = false;
              }
              if (!z2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
              }
              pkgStatusInfo4.currentLevel = pkgStatusInfo4.possibleLevel;
              StringBuilder sb22 = new StringBuilder();
              int i1622 = pkgStatusInfo4.currentLevel;
              sb22.append(strArr[i1622]);
              sb22.append(" ");
              sb22.append(pkgStatusInfo4.uid);
              strArr[i1622] = sb22.toString();
              ActivityManagerService.resetPriorityAfterLockedSection();
              i13 = i2;
            }
          } catch (Throwable th) {
            ActivityManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
      } else {
        Slog.d(
            "MARsPolicyManager",
            pkgStatusInfo4.name + " has floating or onScreen window, skip to freeze");
      }
    }
    ArrayList arrayList5 = new ArrayList();
    synchronized (MARsLock) {
      for (int i17 = 0; i17 < arrayList2.size(); i17++) {
        PkgStatusInfo pkgStatusInfo5 = (PkgStatusInfo) arrayList2.get(i17);
        MARsPackageInfo mARsPackageInfo2 =
            getMARsPackageInfo(
                this.mMARsTargetPackages, pkgStatusInfo5.name, pkgStatusInfo5.userId);
        if (mARsPackageInfo2 != null) {
          updateInfoToMARsPkgStatus(pkgStatusInfo5, mARsPackageInfo2);
          if (mARsPackageInfo2.getCurLevel() == 4) {
            arrayList5.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo2.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                    .setStrExtras(
                        FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.getState()))
                    .setStrResetTime(Long.toString(mARsPackageInfo2.getResetTime()))
                    .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                    .setStrDisableType(Integer.toString(mARsPackageInfo2.getDisableType()))
                    .setStrDisableReason(
                        FASTableContract.convertDisableReasonToDBValue(
                            mARsPackageInfo2.getDisableReason()))
                    .build());
          }
          if (mARsPackageInfo2.getCurLevel() > 2) {
            mARsPackageInfo2.setAppliedPolicy(convertLevelToPolicy(mARsPackageInfo2.getCurLevel()));
            if (this.mMARsRestrictedPackages.get(
                    mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())
                == null) {
              this.mMARsRestrictedPackages.put(
                  mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), mARsPackageInfo2);
            }
            if (MARsDebugConfig.DEBUG_ENG) {
              Slog.v(
                  "MARsPolicyManager",
                  "add mMARsRestrictedPackages "
                      + mARsPackageInfo2.getName()
                      + " level : "
                      + mARsPackageInfo2.getCurLevel()
                      + " policy --"
                      + mARsPackageInfo2.getAppliedPolicy());
            }
          }
        }
      }
    }
    if (iArr4.length > 0) {
      FreecessController.getInstance().destroySocketsForTargetUids(iArr4);
    }
    if (arrayList5.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList5);
    }
    if (i13 != 0 || z) {
      addDebugInfoToHistory("LEV", convertLevelChangeInfoToString(strArr, strArr4));
    }
  }

  public void cancelPolicy(String str, int i, int i2) {
    int i3;
    String str2;
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      if (FreecessController.getInstance().getFreecessEnabled()
          && FreecessController.getInstance()
              .protectFreezePackage(str, i2, "CancelPolicy", 3000L)) {
        return;
      }
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i2);
      if (mARsPackageInfo != null) {
        if (mARsPackageInfo.getAppliedPolicy() != null
            && mARsPackageInfo.getAppliedPolicy().num == i) {
          if (i != 8) {
            mARsPackageInfo.setAppliedPolicy(null);
            mARsPackageInfo.setCurLevel(0);
          }
          if (i == 5) {
            mARsPackageInfo.setUds(0);
          }
          if (i == 6) {
            mARsPackageInfo.setSBike(0);
          }
          if (i == 10) {
            mARsPackageInfo.setMpsm(0);
          }
          if (i == 8
              && (mARsPackageInfo.getDisabled()
                  || isDisabledByUser(mARsPackageInfo.getDisableReason()))) {
            arrayList.add(mARsPackageInfo);
          }
        }
        if (mARsPackageInfo.getAppliedPolicy() == null && i != 8) {
          this.mMARsRestrictedPackages.remove(
              mARsPackageInfo.getName(), mARsPackageInfo.getUserId());
        }
      }
      for (int i4 = 0; i4 < arrayList.size(); i4++) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i4);
          if (mARsPackageInfo2 != null) {
            str2 = mARsPackageInfo2.getName();
            i3 = mARsPackageInfo2.getUserId();
          } else {
            i3 = -1;
            str2 = null;
          }
        }
        cancelDisablePolicy(str2, i3, 0);
      }
    }
  }

  public void cancelPolicy(int i) {
    int i2;
    int i3;
    String str;
    MARsPackageInfo mARsPackageInfo;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    synchronized (MARsLock) {
      for (int i4 = 0; i4 < this.mMARsRestrictedPackages.getMap().size(); i4++) {
        SparseArray sparseArray = (SparseArray) this.mMARsRestrictedPackages.getMap().valueAt(i4);
        for (int i5 = 0; i5 < sparseArray.size(); i5++) {
          MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) sparseArray.valueAt(i5);
          mARsPackageInfo2.setCurLevel(0);
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())) {
            FreecessController.getInstance()
                .unFreezePackage(
                    mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), "CancelPolicy");
          } else {
            if (mARsPackageInfo2.getAppliedPolicy() != null
                && mARsPackageInfo2.getAppliedPolicy().num == i) {
              if (i == 5) {
                mARsPackageInfo2.setUds(0);
              }
              if (i == 6) {
                mARsPackageInfo2.setSBike(0);
              }
              if (i == 10) {
                mARsPackageInfo2.setMpsm(0);
                if (isChinaPolicyEnabled()
                    && mARsPackageInfo2.getFASEnabled()
                    && mARsPackageInfo2.getMaxLevel() != 4
                    && (mARsPackageInfo =
                            getMARsPackageInfo(
                                this.mMARsTargetPackages,
                                mARsPackageInfo2.getName(),
                                mARsPackageInfo2.getUserId()))
                        != null) {
                  mARsPackageInfo.setMaxLevel(3);
                }
              }
              if (i == 8
                  && (mARsPackageInfo2.getDisabled()
                      || isDisabledByUser(mARsPackageInfo2.getDisableReason()))) {
                arrayList.add(mARsPackageInfo2);
              } else {
                mARsPackageInfo2.setAppliedPolicy(null);
              }
            }
            if (mARsPackageInfo2.getAppliedPolicy() == null) {
              arrayList2.add(mARsPackageInfo2);
            }
          }
        }
      }
      for (int i6 = 0; i6 < arrayList2.size(); i6++) {
        MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) arrayList2.get(i6);
        this.mMARsRestrictedPackages.remove(
            mARsPackageInfo3.getName(), mARsPackageInfo3.getUserId());
      }
    }
    for (i2 = 0; i2 < arrayList.size(); i2++) {
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo4 = (MARsPackageInfo) arrayList.get(i2);
        if (mARsPackageInfo4 != null) {
          str = mARsPackageInfo4.getName();
          i3 = mARsPackageInfo4.getUserId();
        } else {
          i3 = -1;
          str = null;
        }
      }
      cancelDisablePolicy(str, i3, 1);
    }
  }

  public void addFilterDebugInfoToHistory(String str, String str2) {
    if (this.mFilterHistoryBufferArray != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
      sb.append("[" + str + "] ");
      sb.append("[" + str2 + "]\n");
      this.mFilterHistoryBufferArray.put(sb.toString());
    }
  }

  public void addNetDebugInfoToHistory(String str, String str2) {
    if (this.mNetHistoryBufferArray != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
      sb.append("[" + str + "] ");
      sb.append("[" + str2 + "]\n");
      this.mNetHistoryBufferArray.put(sb.toString());
    }
  }

  public void addDebugInfoToHistory(String str, String str2) {
    if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
      StringBuilder sb = new StringBuilder();
      sb.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
      sb.append("[" + str + "] ");
      sb.append("[" + str2 + "]\n");
      MARsHistoryBuffer.getInstance().put(sb.toString());
      return;
    }
    if (this.mHistoryBufferArray != null) {
      StringBuilder sb2 = new StringBuilder();
      sb2.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
      sb2.append("[" + str + "] ");
      sb2.append("[" + str2 + "]\n");
      this.mHistoryBufferArray.put(sb2.toString());
    }
  }

  public final String convertLevelChangeInfoToString(String[] strArr, String[] strArr2) {
    StringBuilder sb = new StringBuilder();
    if (!strArr[1].equals("[FRZ]")) {
      sb.append(strArr[1] + " ");
    }
    if (!strArr[2].equals("[FAS]")) {
      sb.append(strArr[2] + " ");
    }
    if (!strArr[3].equals("[FOS]")) {
      sb.append(strArr[3] + " ");
    }
    if (!strArr[4].equals("[DIS]")) {
      sb.append(strArr[4]);
    }
    if (!strArr2[1].equals("[FRZ]")
        || !strArr2[2].equals("[FAS]")
        || !strArr2[3].equals("[FOS]")
        || !strArr2[4].equals("[DIS]")) {
      sb.append(" [IMP] ");
    }
    if (!strArr2[1].equals("[FRZ]")) {
      sb.append(strArr2[1] + " ");
    }
    if (!strArr2[2].equals("[FAS]")) {
      sb.append(strArr2[2] + " ");
    }
    if (!strArr2[3].equals("[FOS]")) {
      sb.append(strArr2[3] + " ");
    }
    if (!strArr2[4].equals("[DIS]")) {
      sb.append(strArr2[4]);
    }
    return sb.toString();
  }

  public final String formatDateTime(long j) {
    if (j == 0) {
      return String.format("%23s", "null");
    }
    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date(j));
  }

  public String formatDateTimeWithoutYear(long j) {
    if (j == 0) {
      return String.format("%18s", "null");
    }
    return new SimpleDateFormat("MM/dd HH:mm:ss.SSS").format(new Date(j));
  }

  public void dumpMARs(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    printWriter.append((CharSequence) ("ACTIVITY MANAGER MARs (dumpsys activity mars)\n"));
    synchronized (MARsLock) {
      printWriter.println(
          "mLastNotiSentTimeForDisabled : "
              + formatDateTimeWithoutYear(this.mLastNotiSentTimeForDisabled));
      StringBuilder sb = new StringBuilder();
      sb.append("ENABLE_ALARM_WAKEUP_BLOCK=");
      sb.append(MARsDebugConfig.ENABLE_ALARM_WAKEUP_BLOCK ? "Y" : "N");
      printWriter.println(sb.toString());
      StringBuilder sb2 = new StringBuilder();
      sb2.append("ENABLE_KILL_LONG_RUNNING_PROCESS=");
      sb2.append(ENABLE_KILL_LONG_RUNNING_PROCESS ? "Y" : "N");
      printWriter.println(sb2.toString());
      printWriter.println("mMARsTargetPackages --- size " + this.mMARsTargetPackages.totalSize());
      for (int i = 0; i < this.mMARsTargetPackages.getMap().size(); i++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          if (mARsPackageInfo != null) {
            printWriter.print("-RST ");
            printWriter.print(formatDateTime(mARsPackageInfo.getDisableResetTime()));
            printWriter.print("-PT ");
            printWriter.print(
                String.format("%6d", Integer.valueOf(mARsPackageInfo.getPackageType())));
            printWriter.print("-ST ");
            printWriter.print(String.format("%2d", Integer.valueOf(mARsPackageInfo.getState())));
            printWriter.print("-SC ");
            printWriter.print(mARsPackageInfo.isSCPMTarget() ? "T" : "F");
            printWriter.print("-DT ");
            printWriter.print(
                String.format("%2d", Integer.valueOf(mARsPackageInfo.getDisableType())));
            printWriter.print("-DR ");
            printWriter.print(
                String.format("%2d", Integer.valueOf(mARsPackageInfo.getDisableReason())));
            printWriter.print("-DD ");
            printWriter.print(mARsPackageInfo.getDisabled() ? "T" : "F");
            printWriter.print("-Uid ");
            printWriter.print(String.format("%8d", Integer.valueOf(mARsPackageInfo.getUid())));
            printWriter.print("(");
            if (mARsPackageInfo.getSharedUidName() != null) {
              printWriter.print("S");
            } else {
              printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
            }
            printWriter.print(")");
            printWriter.print("-Idle");
            printWriter.print("(");
            printWriter.print(")");
            if (MARsDebugConfig.DEBUG_ENG) {
              printWriter.print("-curLv ");
            }
            printWriter.print(mARsPackageInfo.getCurLevel());
            printWriter.print("-maxLv ");
            printWriter.print(mARsPackageInfo.getMaxLevel());
            printWriter.print("-FAS ");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(mARsPackageInfo.getFASEnabled() ? "Y" : "N");
            sb3.append("/");
            sb3.append(String.format("%4d", Integer.valueOf(mARsPackageInfo.getFasType())));
            printWriter.print(sb3.toString());
            printWriter.print("(");
            printWriter.print(
                (mARsPackageInfo.getFasType() & 129) != 0
                    ? "A"
                    : PackageManagerShellCommandDataLoader.STDIN_PATH);
            printWriter.print(")");
            printWriter.print("-BU ");
            printWriter.print(
                String.format("%10f", Double.valueOf(mARsPackageInfo.getBatteryUsage())));
            printWriter.print("-Pkg ");
            printWriter.print(mARsPackageInfo.getName());
            if (UidStateMgr.getInstance().isUidRunning(mARsPackageInfo.getUid())) {
              printWriter.print("--(R)");
            }
            printWriter.println("");
          }
        }
      }
      printWriter.println(
          "mMARsRestrictedPackages --- size " + this.mMARsRestrictedPackages.totalSize());
      for (int i3 = 0; i3 < this.mMARsRestrictedPackages.getMap().size(); i3++) {
        SparseArray sparseArray2 = (SparseArray) this.mMARsRestrictedPackages.getMap().valueAt(i3);
        for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
          MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) sparseArray2.valueAt(i4);
          printWriter.print("-Uid ");
          printWriter.print(String.format("%8d", Integer.valueOf(mARsPackageInfo2.getUid())));
          printWriter.print("-Pkg ");
          printWriter.print(mARsPackageInfo2.getName());
          printWriter.print("-POL ");
          if (mARsPackageInfo2.getAppliedPolicy() != null) {
            String policy = mARsPackageInfo2.getAppliedPolicy().toString();
            printWriter.print(policy.substring(policy.length() - 3, policy.length()));
          } else {
            printWriter.print("null");
          }
          printWriter.println("");
        }
      }
    }
    printWriter.println("");
    if (MARsHandler.getInstance().mMainHandler != null) {
      MARsHandler.getInstance()
          .mMainHandler
          .dump(new PrintWriterPrinter(printWriter), "MARsHandler");
    }
  }

  public void dumpMARsCommand(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    MARsPackageInfo mARsPackageInfo;
    boolean z;
    int i;
    boolean disablePackageBySEP;
    String str;
    if (strArr.length == 1) {
      dumpMARs(fileDescriptor, printWriter);
      dumpMARsHistory(fileDescriptor, printWriter);
      if (FreecessController.getInstance().getFreecessEnabled()) {
        FreecessController.getInstance().dumpFreecess(fileDescriptor, printWriter);
        return;
      }
      return;
    }
    if ("dbtest".equals(strArr[1])) {
      MARsDBManager.getInstance().getSCPMList();
    }
    if ("ct_on".equals(strArr[1])) {
      MARsComponentTracker.getInstance().setEnabled(true);
      printWriter.println("ComponentTracker : " + MARsComponentTracker.getInstance().getEnabled());
    }
    if ("ct_off".equals(strArr[1])) {
      MARsComponentTracker.getInstance().setEnabled(false);
      printWriter.println("ComponentTracker : " + MARsComponentTracker.getInstance().getEnabled());
    }
    if ("ct_onoff".equals(strArr[1])) {
      MARsComponentTracker.getInstance()
          .setEnabled(!MARsComponentTracker.getInstance().getEnabled());
      printWriter.println("ComponentTracker : " + MARsComponentTracker.getInstance().getEnabled());
    }
    if ("restrict_onoff".equals(strArr[1])) {
      this.ENABLE_RESTRICTED_BUCKET = !this.ENABLE_RESTRICTED_BUCKET;
      printWriter.println("ENABLE_RESTRICTED_BUCKET : " + this.ENABLE_RESTRICTED_BUCKET);
    }
    if ("bigdata".equals(strArr[1])) {
      MARsBigData.getInstance(this.mContext).sendBigDataInfoToHQM();
    }
    if ("bstat".equals(strArr[1])) {
      getBatteryStats();
    }
    if ("disable".equals(strArr[1])) {
      if (strArr.length < 3) {
        if (this.disablePolicy != null) {
          setPackageDisablerEnabled(!r0.enabled);
          StringBuilder sb = new StringBuilder();
          sb.append("Disabler policy has been turned ");
          sb.append(getPackageDisablerEnabled() ? "on" : "off");
          printWriter.println(sb.toString());
        }
      } else if (strArr.length == 3) {
        String str2 = strArr[2];
        if (str2 == null) {
          printWriter.println("Disable will not work, please input proper packageName!");
          return;
        }
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo2 =
              getMARsPackageInfo(this.mMARsTargetPackages, str2, this.mContext.getUserId());
          if (mARsPackageInfo2 != null) {
            i = mARsPackageInfo2.getUid();
            z = mARsPackageInfo2.getDisabled();
          } else {
            z = false;
            i = -1;
          }
        }
        if (i != -1) {
          if (z) {
            disablePackageBySEP = enablePackageBySEP(str2, i, false);
          } else {
            disablePackageBySEP = disablePackageBySEP(str2, i, false);
          }
          StringBuilder sb2 = new StringBuilder();
          sb2.append(str2);
          if (disablePackageBySEP) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" has been ");
            sb3.append(z ? "enabled" : "disabled!");
            str = sb3.toString();
          } else {
            str = " is not in our target, we will not manage it!";
          }
          sb2.append(str);
          printWriter.println(sb2.toString());
        } else {
          printWriter.println(str2 + "is not in our target, we will not manage it!");
        }
      }
    }
    if ("filter".equals(strArr[1])) {
      try {
        printWriter.println(
            "Filter -- freecess "
                + FilterManager.getInstance()
                    .filterForSpecificPolicy(
                        Integer.parseInt(strArr[2]),
                        strArr[3],
                        Integer.parseInt(strArr[4]),
                        Integer.parseInt(strArr[5])));
      } catch (NumberFormatException unused) {
        Slog.e("MARsPolicyManager", "dumpMARsCommand(filter) parseInt error!");
      }
    }
    if ("aufbucket".equals(strArr[1])) {
      try {
        AUFAllowBucketSize = Integer.parseInt(strArr[2]);
        printWriter.println("AUFAllowBucketSize set " + AUFAllowBucketSize);
      } catch (NumberFormatException unused2) {
        Slog.e("MARsPolicyManager", "dumpMARsCommand(aufbucket) parseInt error!");
      }
    }
    if ("longrunning".equals(strArr[1])) {
      if (strArr.length >= 3) {
        if ("on".equals(strArr[2])) {
          ENABLE_KILL_LONG_RUNNING_PROCESS = true;
        } else if ("off".equals(strArr[2])) {
          ENABLE_KILL_LONG_RUNNING_PROCESS = false;
        } else if ("duration".equals(strArr[2]) && strArr.length >= 4) {
          try {
            this.KEEP_NO_FILTER_MIN_DURATION = Integer.parseInt(strArr[3]);
          } catch (NumberFormatException unused3) {
            Slog.e("MARsPolicyManager", "dumpMARsCommand(longrunning) parseInt error!");
          }
        }
      }
      printWriter.println(
          "enable=" + ENABLE_KILL_LONG_RUNNING_PROCESS + ", d=" + this.KEEP_NO_FILTER_MIN_DURATION);
    }
    if ("fgsRunningTime".equals(strArr[1])) {
      long uptimeMillis = SystemClock.uptimeMillis();
      synchronized (this.mFGServiceStartTimeMap) {
        for (Integer num : this.mFGServiceStartTimeMap.keySet()) {
          long longValue = ((Long) this.mFGServiceStartTimeMap.get(num)).longValue();
          printWriter.println(
              "uid="
                  + num
                  + ", fgsRunningTime="
                  + (uptimeMillis - longValue)
                  + ", fgsStartTime="
                  + longValue);
        }
      }
    }
    if ("history".equals(strArr[1])) {
      dumpMARsHistory(fileDescriptor, printWriter);
    }
    if ("hold".equals(strArr[1]) || BuildConfig.BUILD_TYPE.equals(strArr[1])) {
      if (strArr.length < 2) {
        printWriter.println("hold/release requires at least 1 argument: uid");
        return;
      }
      int parseInt = Integer.parseInt(strArr[2]);
      boolean equals = "hold".equals(strArr[1]);
      StringBuilder sb4 = new StringBuilder();
      sb4.append(equals ? "Hold" : "Release");
      sb4.append(" jobs by u=");
      sb4.append(parseInt);
      sb4.append(" calling by MARs.");
      printWriter.println(sb4.toString());
      restrictJobsByUid(parseInt, equals);
    }
    if ("level".equals(strArr[1])) {
      if (strArr.length < 3) {
        printWriter.println("MARstest -- need levelNum and packageName");
      } else if (strArr.length == 4) {
        try {
          int parseInt2 = Integer.parseInt(strArr[2]);
          if ("-a".equals(strArr[3])) {
            synchronized (MARsLock) {
              for (int i2 = 0; i2 < this.mMARsTargetPackages.getMap().size(); i2++) {
                SparseArray sparseArray =
                    (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i2);
                for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                  MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) sparseArray.valueAt(i3);
                  if (mARsPackageInfo3 != null) {
                    setMaxLevel(parseInt2, mARsPackageInfo3.getName());
                  }
                }
              }
            }
          } else {
            setMaxLevel(parseInt2, strArr[3]);
          }
        } catch (NumberFormatException unused4) {
          printWriter.println("MARstest -- NumberFormatException!");
        }
      }
    }
    if ("list".equals(strArr[1])) {
      dumpMARs(fileDescriptor, printWriter);
    }
    if ("close_socket".equals(strArr[1])) {
      if (strArr.length < 3) {
        printWriter.println("usage: close_socket <uid>");
      } else {
        int parseInt3 = Integer.parseInt(strArr[2]);
        printWriter.println("Calling closeSocketsForUid: u=" + parseInt3);
        closeSocketsForUid(parseInt3);
      }
    }
    if ("time_disable".equals(strArr[1])
        && (mARsPackageInfo =
                getMARsPackageInfo(this.mMARsTargetPackages, strArr[2], this.mContext.getUserId()))
            != null) {
      mARsPackageInfo.setResetTime(System.currentTimeMillis() - this.mAutoDisabledLockingTime);
    }
    if ("update".equals(strArr[1])) {
      updateFromMARsMainThread();
    }
    if ("widget".equals(strArr[1])) {
      WidgetPkgFilter.getInstance().getBoundAppWidgetPackages();
    }
    if ("deepsleep_target".equals(strArr[1])) {
      checkUnusedTargetForDeepSleep();
    }
    if ("candidate".equals(strArr[1])) {
      if (strArr.length < 3) {
        printWriter.println("MARstest -- need type and packageName");
      } else if (strArr.length == 4) {
        try {
          synchronized (MARsLock) {
            MARsPackageInfo mARsPackageInfo4 =
                getMARsPackageInfo(this.mMARsTargetPackages, strArr[3], this.mContext.getUserId());
            if (mARsPackageInfo4 != null) {
              if ("-s".equals(strArr[2])) {
                levelChange(1, mARsPackageInfo4);
              } else if ("-d".equals(strArr[2])) {
                levelChange(4, mARsPackageInfo4);
              }
            } else {
              printWriter.println("MARstest -- not mars target!");
            }
          }
        } catch (NumberFormatException unused5) {
          printWriter.println("MARstest -- NumberFormatException!");
        }
      }
    }
    if ("debug".equals(strArr[1])) {
      if ("help".equals(strArr[2])) {
        printWriter.println("MARs debug options commands:");
        printWriter.println("  help");
        printWriter.println("     Print this help text.");
        printWriter.println("  all");
        printWriter.println("     Enable/Disable all mars debug message.");
        printWriter.println("  olaf");
        printWriter.println("     Enable/Disable olaf debug message.");
        printWriter.println("  freecess");
        printWriter.println("     Enable/Disable freecess debug message.");
        printWriter.println("  database");
        printWriter.println("     Enable/Disable MARs database debug message.");
        printWriter.println("  filter");
        printWriter.println("     Enable/Disable MARs filter debug message.");
        return;
      }
      if ("all".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_MARs = !MARsDebugConfig.DEBUG_MARs;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(" mars debugging mode is ");
        sb5.append(MARsDebugConfig.DEBUG_MARs ? "on" : "off");
        sb5.append(" now!");
        printWriter.println(sb5.toString());
        return;
      }
      if ("olaf".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_OLAF = !MARsDebugConfig.DEBUG_OLAF;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(" olaf debugging mode is ");
        sb6.append(MARsDebugConfig.DEBUG_OLAF ? "on" : "off");
        sb6.append(" now!");
        printWriter.println(sb6.toString());
        return;
      }
      if ("freecess".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_FREECESS = !MARsDebugConfig.DEBUG_FREECESS;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(" freecess debugging mode is ");
        sb7.append(MARsDebugConfig.DEBUG_FREECESS ? "on" : "off");
        sb7.append(" now!");
        printWriter.println(sb7.toString());
        return;
      }
      if ("database".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_DATABASE = !MARsDebugConfig.DEBUG_DATABASE;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(" database debugging mode is ");
        sb8.append(MARsDebugConfig.DEBUG_DATABASE ? "on" : "off");
        sb8.append(" now!");
        printWriter.println(sb8.toString());
        return;
      }
      if ("filter".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_FILTER = !MARsDebugConfig.DEBUG_FILTER;
        StringBuilder sb9 = new StringBuilder();
        sb9.append(" filter debugging mode is ");
        sb9.append(MARsDebugConfig.DEBUG_FILTER ? "on" : "off");
        sb9.append(" now!");
        printWriter.println(sb9.toString());
        return;
      }
      if ("netlink".equals(strArr[2])) {
        MARsDebugConfig.DEBUG_NETLINK = !MARsDebugConfig.DEBUG_NETLINK;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(" netlink debugging mode is ");
        sb10.append(MARsDebugConfig.DEBUG_NETLINK ? "on" : "off");
        sb10.append(" now!");
        printWriter.println(sb10.toString());
        return;
      }
      printWriter.println("Error: debug command requires argument");
    }
  }

  public void dumpMARsHistory(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    printWriter.println("");
    printWriter.println("ACTIVITY MANAGER MARs HISTORY (dumpsys activity mars history)");
    int i = 0;
    if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
      ArrayList log = MARsHistoryBuffer.getInstance().getLog();
      while (i < log.size()) {
        printWriter.print((String) log.get(i));
        i++;
      }
      printWriter.println("");
    } else {
      HistoryBuffer historyBuffer = this.mHistoryBufferArray;
      if (historyBuffer != null) {
        String[] buffer = historyBuffer.getBuffer();
        while (i < this.mHistoryBufferArray.getSize()) {
          printWriter.print(buffer[i]);
          i++;
        }
        printWriter.println("");
      }
    }
    dumpNetHistory(fileDescriptor, printWriter);
    dumpFilterHistory(fileDescriptor, printWriter);
  }

  public void dumpNetHistory(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    printWriter.println("");
    printWriter.println("ACTIVITY MANAGER NET HISTORY (dumpsys activity net history)");
    HistoryBuffer historyBuffer = this.mNetHistoryBufferArray;
    if (historyBuffer != null) {
      String[] buffer = historyBuffer.getBuffer();
      for (int i = 0; i < this.mNetHistoryBufferArray.getSize(); i++) {
        printWriter.print(buffer[i]);
      }
      printWriter.println("");
    }
  }

  public void dumpFilterHistory(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    printWriter.println("");
    printWriter.println("ACTIVITY MANAGER FILTER HISTORY");
    HistoryBuffer historyBuffer = this.mFilterHistoryBufferArray;
    if (historyBuffer != null) {
      String[] buffer = historyBuffer.getBuffer();
      for (int i = 0; i < this.mFilterHistoryBufferArray.getSize(); i++) {
        printWriter.print(buffer[i]);
      }
      printWriter.println("");
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:28:0x00ca  */
  /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setMaxLevel(int i, String str) {
    FASEntity fASEntity;
    int i2;
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo =
          getMARsPackageInfo(this.mMARsTargetPackages, str, this.mContext.getUserId());
      if (mARsPackageInfo != null) {
        if (i != 1) {
          i2 = 2;
          if (i != 2) {
            i2 = i != 4 ? 0 : 8;
          } else {
            if (mARsPackageInfo.getDisabled()) {
              enablePackageBySEP(str, mARsPackageInfo.getUid(), false);
            }
            MARsHandler.getInstance()
                .sendCallSetAppStandbyBucketMsgToMainHandler(
                    mARsPackageInfo.getName(),
                    mARsPackageInfo.getUserId(),
                    this.ENABLE_RESTRICTED_BUCKET ? 45 : 40,
                    false);
          }
        } else {
          i2 = 1024;
        }
        if (i2 == 0) {
          return;
        }
        if (levelChange(i2, mARsPackageInfo)) {
          fASEntity =
              new FASEntityBuilder()
                  .setStrPkgName(mARsPackageInfo.getName())
                  .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                  .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                  .setStrNew(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                  .setStrFasReason(mARsPackageInfo.getFasReason())
                  .setStrExtras(
                      FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                  .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                  .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                  .setStrDisableReason(
                      FASTableContract.convertDisableReasonToDBValue(
                          mARsPackageInfo.getDisableReason()))
                  .build();
          if (fASEntity == null) {
            MARsDBManager.getInstance().sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
            return;
          }
          return;
        }
      }
      fASEntity = null;
      if (fASEntity == null) {}
    }
  }

  public class PkgStatusInfo {
    public int currentLevel;
    public int disableReason;
    public int disableType;
    public int fasType;
    public long forceStopTime;
    public long[] freezedTimeForLevelUp;
    public boolean isDisabled;
    public boolean isFASEnabled;
    public boolean isInRestrictedBucket;
    public int maxLevel;
    public String name;
    public int possibleLevel;
    public int state;
    public int uid;
    public int unfreezedCount;
    public int userId;

    public PkgStatusInfo(String str, int i, int i2) {
      this.name = str;
      this.uid = i;
      this.userId = i2;
      this.forceStopTime = 0L;
      this.fasType = 0;
      this.state = 1;
      this.currentLevel = 0;
      this.possibleLevel = 1;
      this.maxLevel = 1;
      this.isFASEnabled = false;
      this.isInRestrictedBucket = false;
      this.isDisabled = false;
      this.disableType = -1;
      this.freezedTimeForLevelUp = new long[2];
      this.unfreezedCount = 0;
      this.disableReason = 0;
    }
  }

  public final void updateInfoToPkgStatus(
      MARsPackageInfo mARsPackageInfo, PkgStatusInfo pkgStatusInfo) {
    pkgStatusInfo.fasType = mARsPackageInfo.getFasType();
    pkgStatusInfo.state = mARsPackageInfo.getState();
    pkgStatusInfo.isDisabled = mARsPackageInfo.getDisabled();
    pkgStatusInfo.disableType = mARsPackageInfo.getDisableType();
    pkgStatusInfo.currentLevel = mARsPackageInfo.getCurLevel();
    pkgStatusInfo.maxLevel = mARsPackageInfo.getMaxLevel();
    pkgStatusInfo.isFASEnabled = mARsPackageInfo.getFASEnabled();
    pkgStatusInfo.isInRestrictedBucket = mARsPackageInfo.getIsInRestrictedBucket();
    pkgStatusInfo.disableReason = mARsPackageInfo.getDisableReason();
  }

  public final void updateInfoToMARsPkgStatus(
      PkgStatusInfo pkgStatusInfo, MARsPackageInfo mARsPackageInfo) {
    mARsPackageInfo.setFasType(pkgStatusInfo.fasType);
    mARsPackageInfo.setState(pkgStatusInfo.state);
    mARsPackageInfo.setDisabled(pkgStatusInfo.isDisabled);
    mARsPackageInfo.setDisableType(pkgStatusInfo.disableType);
    mARsPackageInfo.setCurLevel(pkgStatusInfo.currentLevel);
    mARsPackageInfo.setMaxLevel(pkgStatusInfo.maxLevel);
    mARsPackageInfo.setFASEnabled(pkgStatusInfo.isFASEnabled);
    mARsPackageInfo.setIsInRestrictedBucket(pkgStatusInfo.isInRestrictedBucket);
    mARsPackageInfo.setDisableReason(pkgStatusInfo.disableReason);
  }

  public final class Policy {
    public final int action;
    public boolean enabled;
    public final String name;
    public final int num;
    public int restriction;

    public Policy(String str, int i, boolean z, int i2, int i3, int i4) {
      this.name = str;
      this.num = i;
      this.enabled = z;
      this.restriction = i3;
      this.action = i4;
    }

    public String toString() {
      return this.name + "(" + this.num + ")";
    }
  }

  public final Policy convertLevelToPolicy(int i) {
    if (i == 1) {
      return this.freecessPolicy;
    }
    if (i == 2) {
      return this.freecessPolicy;
    }
    if (i != 3) {
      if (i != 4) {
        return null;
      }
      return this.disablePolicy;
    }
    if (!isChinaPolicyEnabled()) {
      return this.appLockerPolicy;
    }
    return this.autoRunPolicy;
  }

  public final int convertLevelToPolicyNum(int i) {
    if (i == 1 || i == 2) {
      return 4;
    }
    return i != 3 ? i != 4 ? -1 : 8 : !isChinaPolicyEnabled() ? 1 : 2;
  }

  public final int convertPolicyNumToLevel(Policy policy) {
    switch (policy.num) {
      case 1:
      case 2:
        return 3;
      case 3:
      case 7:
      default:
        return 0;
      case 4:
        return 1;
      case 5:
        return 6;
      case 6:
        return 7;
      case 8:
        return 4;
      case 9:
        return 8;
      case 10:
        return 9;
    }
  }

  public void createPolicies() {
    for (int i = 0; i < MARsVersionManager.mPolicyInfoList.size(); i++) {
      String name =
          ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i)).getName();
      int num =
          ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i)).getNum();
      Policy policy =
          new Policy(
              name,
              num,
              ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i))
                      .getEnabled()
                  == 1,
              ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i))
                  .getTargetCategory(),
              ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i))
                  .getRestriction(),
              ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i))
                  .getAction());
      if (num == 1) {
        this.appLockerPolicy = policy;
      } else if (num == 2) {
        this.autoRunPolicy = policy;
      } else if (num == 4) {
        this.freecessPolicy = policy;
      } else if (num == 8) {
        this.disablePolicy = policy;
      }
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(
          "MARsPolicyManager",
          "createPolicies---AL = "
              + this.appLockerPolicy
              + " , AR = "
              + this.autoRunPolicy
              + " , FZ = "
              + this.freecessPolicy);
    }
    this.udsPolicy =
        new Policy(
            "udspolicy",
            5,
            SemCscFeature.getInstance()
                .getString("CscFeature_SmartManager_ConfigSubFeatures")
                .contains("UDS"),
            22,
            0,
            9);
    this.sbikePolicy =
        new Policy(
            "sbkiepolicy",
            6,
            SemCscFeature.getInstance()
                .getString("CscFeature_Common_ConfigBikeMode")
                .contains("bikemode"),
            23,
            0,
            9);
    this.gamePolicy =
        new Policy(
            "gamePolicy",
            9,
            SystemProperties.getBoolean("sys.config.mars.game_policy", true),
            27,
            0,
            10);
    this.mpsmPolicy = new Policy("mpsmpolicy", 10, true, 28, 0, 9);
  }

  public Policy getPolicy(int i) {
    if (i == 1) {
      return this.appLockerPolicy;
    }
    if (i == 2) {
      return this.autoRunPolicy;
    }
    if (i == 4) {
      return this.freecessPolicy;
    }
    if (i == 5) {
      return this.udsPolicy;
    }
    if (i == 6) {
      return this.sbikePolicy;
    }
    if (i == 8) {
      return this.disablePolicy;
    }
    if (i == 9) {
      return this.gamePolicy;
    }
    if (i == 10) {
      return this.mpsmPolicy;
    }
    return null;
  }

  public final boolean isTargetMatch(String str, String str2, String str3) {
    if ("equals".equals(str2)) {
      return str.equals(str3);
    }
    if ("contains".equals(str2)) {
      return str.contains(str3);
    }
    if ("startsWith".equals(str2)) {
      return str.startsWith(str3);
    }
    if ("endsWith".equals(str2)) {
      return str.endsWith(str3);
    }
    if ("equalsIgnoreCase".equals(str2)) {
      return str.equalsIgnoreCase(str3);
    }
    return false;
  }

  public boolean isInPolicyExceptionList(String str, int i, int i2) {
    MARsPackageInfo mARsPackageInfo;
    boolean z = false;
    for (int i3 = 0; i3 < MARsVersionManager.getInstance().mExcludeTargetList.size(); i3++) {
      int i4 =
          ((MARsVersionManager.AdjustTargetExcludePackage)
                  MARsVersionManager.getInstance().mExcludeTargetList.get(i3))
              .policyNum;
      int i5 =
          ((MARsVersionManager.AdjustTargetExcludePackage)
                  MARsVersionManager.getInstance().mExcludeTargetList.get(i3))
              .condition;
      String str2 =
          ((MARsVersionManager.AdjustTargetExcludePackage)
                  MARsVersionManager.getInstance().mExcludeTargetList.get(i3))
              .pkgNameMatchType;
      String str3 =
          ((MARsVersionManager.AdjustTargetExcludePackage)
                  MARsVersionManager.getInstance().mExcludeTargetList.get(i3))
              .packageName;
      if (i4 == 0) {
        z = isTargetMatch(str, str2, str3);
      } else if (i4 != 2) {
        if (i4 != 4) {
          if (i4 == 5 && i2 == 10) {
            z = isTargetMatch(str, str2, str3);
          }
        } else if (i2 == 4) {
          z = isTargetMatch(str, str2, str3);
        }
      } else if (i2 == 2
          && (mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i)) != null
          && i5 == (!mARsPackageInfo.getFASEnabled())) {
        z = isTargetMatch(mARsPackageInfo.getName(), str2, str3);
      }
      if (z) {
        return true;
      }
    }
    return false;
  }

  public void cancelAllPolicy() {
    int i;
    String str;
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i2 = 0; i2 < this.mMARsRestrictedPackages.getMap().size(); i2++) {
        SparseArray sparseArray = (SparseArray) this.mMARsRestrictedPackages.getMap().valueAt(i2);
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())) {
            FreecessController.getInstance()
                .unFreezePackage(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), "CancelPolicy");
          } else if (mARsPackageInfo.getAppliedPolicy() != null) {
            if (!mARsPackageInfo.getDisabled()
                && !isDisabledByUser(mARsPackageInfo.getDisableReason())) {
              mARsPackageInfo.setAppliedPolicy(null);
              mARsPackageInfo.setCurLevel(0);
            }
            if (isDisabledByUser(mARsPackageInfo.getDisableReason())) {
              mARsPackageInfo.setDisableReason(0);
              if (!getInstance().isChinaPolicyEnabled()) {
                mARsPackageInfo.setFasType(1);
              }
            }
            arrayList.add(mARsPackageInfo);
          }
        }
      }
    }
    for (int i4 = 0; i4 < arrayList.size(); i4++) {
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i4);
        if (mARsPackageInfo2 != null) {
          str = mARsPackageInfo2.getName();
          i = mARsPackageInfo2.getUserId();
        } else {
          i = -1;
          str = null;
        }
      }
      cancelDisablePolicy(str, i, 0);
    }
    synchronized (MARsLock) {
      if (this.mMARsRestrictedPackages.size() > 0) {
        this.mMARsRestrictedPackages.clear();
      }
    }
  }

  public void resetAutoDisabledAppState(String str, int i, boolean z) {
    boolean z2;
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
      if (z && mARsPackageInfo != null && isDisabledByUser(mARsPackageInfo.getDisableReason())) {
        mARsPackageInfo.setDisableReason(0);
        if (!isChinaPolicyEnabled()) {
          z2 = true;
          mARsPackageInfo.setFasType(1);
          mARsPackageInfo.setState(1);
        }
      }
      z2 = false;
    }
    if (z2) {
      z = false;
    }
    changeAutoDisabledAppState(str, i, z);
  }

  public void changeAutoDisabledAppState(String str, int i, boolean z) {
    FASEntity fASEntity;
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsRestrictedPackages, str, i);
      fASEntity = null;
      if (mARsPackageInfo != null
          && mARsPackageInfo.getAppliedPolicy() != null
          && mARsPackageInfo.getAppliedPolicy().num == 8) {
        if (isDisabledByUser(mARsPackageInfo.getDisableReason())) {
          levelChange(1024, mARsPackageInfo);
        } else {
          if (z) {
            levelChange(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, mARsPackageInfo);
            mARsPackageInfo.setDisableReason(8);
          } else {
            levelChange(1024, mARsPackageInfo);
            mARsPackageInfo.setDisableReason(4);
          }
          if (z && isChinaPolicyEnabled()) {
            mARsPackageInfo.setCurLevel(2);
            mARsPackageInfo.setAppliedPolicy(this.autoRunPolicy);
          } else {
            mARsPackageInfo.setCurLevel(0);
            mARsPackageInfo.setAppliedPolicy(null);
            this.mMARsRestrictedPackages.remove(
                mARsPackageInfo.getName(), mARsPackageInfo.getUserId());
          }
          mARsPackageInfo.setDisableType(-1);
          fASEntity =
              new FASEntityBuilder()
                  .setStrPkgName(mARsPackageInfo.getName())
                  .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                  .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                  .setStrNew(mARsPackageInfo.getFASEnabled() ? "1" : "0")
                  .setStrFasReason(
                      FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                  .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                  .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                  .setStrDisableType(Integer.toString(mARsPackageInfo.getDisableType()))
                  .setStrDisableReason(
                      FASTableContract.convertDisableReasonToDBValue(
                          mARsPackageInfo.getDisableReason()))
                  .setStrExtras(
                      FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                  .build();
        }
        mARsPackageInfo.setDisabled(false);
      }
    }
    if (fASEntity != null) {
      MARsDBManager.getInstance().sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
    }
  }

  public List getPkgsTypeForChimera(List list) {
    FreecessController.getInstance().updateRunningLocationPackages();
    ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String[] split = ((String) it.next()).split("/");
      if (split != null) {
        String str = split[0];
        try {
          int parseInt = Integer.parseInt(split[1]);
          arrayList.add(
              str
                  + "/"
                  + parseInt
                  + "/"
                  + FilterManager.getInstance()
                      .filterForChimera(str, parseInt, Integer.parseInt(split[2])));
        } catch (NumberFormatException unused) {
          Slog.d("MARsPolicyManager", "NumberFormatException!");
        }
      }
    }
    return arrayList;
  }

  public void addRestrictListForCalmMode(String str, int i, String str2) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        mARsPackageInfo.setAppliedPolicy(this.gamePolicy);
        mARsPackageInfo.setCurLevel(8);
        if (this.mMARsRestrictedPackages.get(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())
            == null) {
          this.mMARsRestrictedPackages.put(
              mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
        }
      }
    }
    addDebugInfoToHistory("EXE", str2 + " " + str);
  }

  public void removeRestrictListForCalmMode(StringBuilder sb) {
    StringBuilder sb2 = new StringBuilder();
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i = 0; i < this.mMARsRestrictedPackages.getMap().size(); i++) {
        SparseArray sparseArray = (SparseArray) this.mMARsRestrictedPackages.getMap().valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          mARsPackageInfo.setCurLevel(0);
          if (mARsPackageInfo.getAppliedPolicy() != null
              && mARsPackageInfo.getAppliedPolicy().num == 9) {
            mARsPackageInfo.setAppliedPolicy(null);
          }
          if (mARsPackageInfo.getAppliedPolicy() == null) {
            arrayList.add(mARsPackageInfo);
          }
        }
      }
      for (int i3 = 0; i3 < arrayList.size(); i3++) {
        MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i3);
        this.mMARsRestrictedPackages.remove(
            mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId());
        sb2.append(mARsPackageInfo2.getUid() + " ");
      }
    }
    addDebugInfoToHistory("Calm", "CancelPolicy-" + sb2.toString() + " UFZ-" + sb.toString());
  }

  public void addRestrictListAvoidAssoicationLaunch(String str, int i, String str2, String str3) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        mARsPackageInfo.setAppliedPolicy(this.autoRunPolicy);
        mARsPackageInfo.setCurLevel(3);
        if (this.mMARsRestrictedPackages.get(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())
            == null) {
          this.mMARsRestrictedPackages.put(
              mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), mARsPackageInfo);
        }
      }
    }
    addDebugInfoToHistory(str3, str2 + " " + str);
  }

  public SemAppRestrictionManager.RestrictionInfo getRestrictionInfoBySEP(
      int i, String str, int i2) {
    int i3;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")) {
      return null;
    }
    String str2 = "default";
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo =
          getMARsPackageInfo(this.mMARsTargetPackages, str, UserHandle.getUserId(i2));
      i3 = 0;
      if (mARsPackageInfo != null && isCurrentUser(mARsPackageInfo.getUserId())) {
        if (i == 0) {
          if (mARsPackageInfo.getMaxLevel() == 4) {
            str2 =
                FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.getDisableReason());
            i3 = 1;
          } else if (mARsPackageInfo.getDisableReason() == 0) {
            str2 = "default";
          } else {
            str2 =
                FASTableContract.convertDisableReasonToDBValue(mARsPackageInfo.getDisableReason());
            i3 = 2;
          }
        } else if (i == 1) {
          if (mARsPackageInfo.getFASEnabled()) {
            str2 = FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
            i3 = 1;
          } else if (mARsPackageInfo.getFasType() == 0) {
            str2 = "default";
          } else {
            str2 = FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
            i3 = 2;
          }
        } else if (i == 2) {
          if (mARsPackageInfo.getState() == 8) {
            str2 = "added_from_mars_auto";
            i3 = 1;
          } else {
            str2 = "added_from_mars_auto";
            i3 = 2;
          }
        } else if (i == 3) {
          if (mARsPackageInfo.getFASEnabled() || mARsPackageInfo.getFasType() != 256) {
            str2 = "default";
            i3 = 2;
          } else {
            str2 = "default";
            i3 = 1;
          }
        }
      }
    }
    return new SemAppRestrictionManager.RestrictionInfo(i, i3, str2);
  }

  public boolean canRestrictBySEP(int i, String str, int i2) {
    if (hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")) {
      return canRestrictBySEPWithoutPermissionCheck(i, str, i2);
    }
    return false;
  }

  public final boolean canRestrictBySEPWithoutPermissionCheck(int i, String str, int i2) {
    int userId = UserHandle.getUserId(i2);
    if (isCurrentUser(userId) && str != null && isMARsTarget(str, userId)) {
      if (i == 0) {
        if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(21, str, null, null)) {
          return false;
        }
        try {
          int applicationEnabledSetting =
              IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
                  .getApplicationEnabledSetting(str, userId);
          if (applicationEnabledSetting != 2
              && applicationEnabledSetting != 3
              && FilterManager.getInstance().filterForSpecificPolicy(8, str, userId, i2) == 0) {
            return true;
          }
        } catch (Exception e) {
          Slog.e("MARsPolicyManager", "Exception " + e);
          return false;
        }
      } else {
        if (i == 1 || i == 3) {
          return true;
        }
        if (i == 2) {
          synchronized (MARsLock) {
            MARsPackageInfo mARsPackageInfo =
                getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
            if (mARsPackageInfo != null
                && !AllowListFilter.getInstance().isInDefaultAllowList(mARsPackageInfo.getName())
                && mARsPackageInfo.getHasAppIcon()) {
              if (FilterManager.getInstance().filterForSpecificPolicy(7, str, userId, i2) == 0) {
                return true;
              }
            }
            return false;
          }
        }
      }
    }
    return false;
  }

  public boolean restrictBySEP(int i, int i2, boolean z, String str, int i3) {
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")
        || !isCurrentUser(UserHandle.getUserId(i3))) {
      return false;
    }
    ArrayList arrayList = new ArrayList();
    SemAppRestrictionManager.RestrictionInfo restrictionInfo =
        new SemAppRestrictionManager.RestrictionInfo(
            i,
            i2,
            i2 == 1
                ? z ? "added_from_user_manual" : "added_from_mars_auto"
                : i2 == 2 ? z ? "deleted_from_user_manual" : "deleted_from_mars_auto" : "default");
    arrayList.add(new SemAppRestrictionManager.AppRestrictionInfo(str, i3, restrictionInfo));
    return updateRestrictionInfo(restrictionInfo, arrayList);
  }

  public final boolean disablePackageBySEP(String str, int i, boolean z) {
    return restrictBySEP(0, 1, z, str, i);
  }

  public final boolean enablePackageBySEP(String str, int i, boolean z) {
    return restrictBySEP(0, 2, z, str, i);
  }

  public void setGoogleEnabled(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        mARsPackageInfo.setCurLevel(0);
        mARsPackageInfo.setDisabled(false);
      }
    }
  }

  public List getRestrictableList(int i) {
    SemAppRestrictionManager.RestrictionInfo restrictionInfo;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i2 = 0; i2 < this.mMARsTargetPackages.getMap().size(); i2++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i2);
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
          if (mARsPackageInfo != null
              && canRestrictBySEPWithoutPermissionCheck(
                  i, mARsPackageInfo.getName(), mARsPackageInfo.getUid())) {
            if (i == 1) {
              if (mARsPackageInfo.getFASEnabled()) {
                restrictionInfo =
                    new SemAppRestrictionManager.RestrictionInfo(
                        i,
                        1,
                        FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
                arrayList.add(
                    new SemAppRestrictionManager.AppRestrictionInfo(
                        mARsPackageInfo.getName(), mARsPackageInfo.getUid(), restrictionInfo));
              }
              restrictionInfo = null;
              arrayList.add(
                  new SemAppRestrictionManager.AppRestrictionInfo(
                      mARsPackageInfo.getName(), mARsPackageInfo.getUid(), restrictionInfo));
            } else {
              if (i == 0 && mARsPackageInfo.getMaxLevel() == 4) {
                restrictionInfo =
                    new SemAppRestrictionManager.RestrictionInfo(
                        i,
                        1,
                        FASTableContract.convertDisableReasonToDBValue(
                            mARsPackageInfo.getDisableReason()));
                arrayList.add(
                    new SemAppRestrictionManager.AppRestrictionInfo(
                        mARsPackageInfo.getName(), mARsPackageInfo.getUid(), restrictionInfo));
              }
              restrictionInfo = null;
              arrayList.add(
                  new SemAppRestrictionManager.AppRestrictionInfo(
                      mARsPackageInfo.getName(), mARsPackageInfo.getUid(), restrictionInfo));
            }
          }
        }
      }
    }
    return arrayList;
  }

  public List getAllRestrictedList() {
    SemAppRestrictionManager.RestrictionInfo restrictionInfo;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i = 0; i < this.mMARsTargetPackages.getMap().size(); i++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i2);
          if (mARsPackageInfo != null && isCurrentUser(mARsPackageInfo.getUserId())) {
            if (mARsPackageInfo.getMaxLevel() == 4) {
              restrictionInfo =
                  new SemAppRestrictionManager.RestrictionInfo(
                      0,
                      1,
                      FASTableContract.convertDisableReasonToDBValue(
                          mARsPackageInfo.getDisableReason()));
            } else if (mARsPackageInfo.getFASEnabled()) {
              restrictionInfo =
                  new SemAppRestrictionManager.RestrictionInfo(
                      1, 1, FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()));
            } else {
              restrictionInfo =
                  mARsPackageInfo.getFasType() == 256
                      ? new SemAppRestrictionManager.RestrictionInfo(
                          3,
                          1,
                          FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                      : null;
            }
            arrayList.add(
                new SemAppRestrictionManager.AppRestrictionInfo(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUid(), restrictionInfo));
          }
        }
      }
    }
    return arrayList;
  }

  /* JADX WARN: Removed duplicated region for block: B:28:0x00a1 A[Catch: all -> 0x00c0, TryCatch #0 {, blocks: (B:9:0x0014, B:11:0x0020, B:12:0x002d, B:14:0x0033, B:16:0x003b, B:28:0x00a1, B:32:0x0052, B:34:0x0058, B:36:0x0060, B:37:0x0069, B:39:0x0071, B:41:0x0077, B:42:0x0080, B:44:0x0086, B:45:0x008f, B:47:0x0095, B:30:0x00b6, B:51:0x00ba, B:53:0x00be), top: B:8:0x0014 }] */
  /* JADX WARN: Removed duplicated region for block: B:31:0x00b6 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public List getRestrictedList(int i) {
    String convertDisableReasonToDBValue;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    synchronized (MARsLock) {
      for (int i2 = 0; i2 < this.mMARsTargetPackages.getMap().size(); i2++) {
        SparseArray sparseArray = (SparseArray) this.mMARsTargetPackages.getMap().valueAt(i2);
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
          MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
          if (mARsPackageInfo != null && isCurrentUser(mARsPackageInfo.getUserId())) {
            if (i == 0) {
              if (mARsPackageInfo.getMaxLevel() == 4) {
                convertDisableReasonToDBValue =
                    FASTableContract.convertDisableReasonToDBValue(
                        mARsPackageInfo.getDisableReason());
                if (convertDisableReasonToDBValue == null) {}
              }
              convertDisableReasonToDBValue = null;
              if (convertDisableReasonToDBValue == null) {}
            } else if (i == 1) {
              if (mARsPackageInfo.getFASEnabled()) {
                convertDisableReasonToDBValue =
                    FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
                if (convertDisableReasonToDBValue == null) {}
              }
              convertDisableReasonToDBValue = null;
              if (convertDisableReasonToDBValue == null) {}
            } else if (i == 2) {
              if (mARsPackageInfo.getState() == 8 && mARsPackageInfo.getMaxLevel() != 4) {
                convertDisableReasonToDBValue =
                    FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
                if (convertDisableReasonToDBValue == null) {}
              }
              convertDisableReasonToDBValue = null;
              if (convertDisableReasonToDBValue == null) {}
            } else {
              if (i == 3
                  && !mARsPackageInfo.getFASEnabled()
                  && mARsPackageInfo.getFasType() == 256) {
                convertDisableReasonToDBValue =
                    FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
                if (convertDisableReasonToDBValue == null) {
                  arrayList.add(
                      new SemAppRestrictionManager.AppRestrictionInfo(
                          mARsPackageInfo.getName(),
                          mARsPackageInfo.getUid(),
                          new SemAppRestrictionManager.RestrictionInfo(
                              i, 1, convertDisableReasonToDBValue)));
                }
              }
              convertDisableReasonToDBValue = null;
              if (convertDisableReasonToDBValue == null) {}
            }
          }
        }
      }
    }
    return arrayList;
  }

  public boolean updateRestrictionInfo(
      SemAppRestrictionManager.RestrictionInfo restrictionInfo, List list) {
    List removeRestrictedInfo;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")
        || restrictionInfo == null
        || list == null) {
      return false;
    }
    int type = restrictionInfo.getType();
    int state = restrictionInfo.getState();
    String reason = restrictionInfo.getReason();
    if (type != 0) {
      if (type != 1) {
        if (type != 2) {
          if (type == 3) {
            if (state == 1) {
              removeRestrictedInfo = removeRestrictedInfo(list, "deleted_from_user_manual");
            } else {
              removeRestrictedInfo = removeRestrictedInfo(list, "default");
            }
          }
          removeRestrictedInfo = null;
        } else if (state == 1) {
          removeRestrictedInfo = updateDisableCandidateInfo(list);
        } else {
          if (state == 2) {
            removeRestrictedInfo = updateDoNotDisableInfo(list);
          }
          removeRestrictedInfo = null;
        }
      } else if (state == 1) {
        removeRestrictedInfo = sleepPackageList(list, reason);
      } else if (state == 2) {
        removeRestrictedInfo = awakePackageList(list, reason);
      } else {
        removeRestrictedInfo = removeRestrictedInfo(list, "default");
      }
    } else if (state == 1) {
      if ("added_from_anomaly_manual".equals(reason)) {
        removeRestrictedInfo = disablePackageListForSpecific(list, reason);
      } else if ("added_from_mars_auto_specific".equals(reason)
          || "added_from_mars_manual_specific".equals(reason)) {
        if (!removeInDozeAllowList(list)) {
          removeRestrictedInfo = disablePackageListForSpecific(list, reason);
        } else {
          MARsHandler.getInstance().sendSpecificDisableMsgToMainHandler(list);
          removeRestrictedInfo = null;
        }
      } else {
        removeRestrictedInfo = disablePackageList(list, reason);
      }
    } else if (state == 2) {
      removeRestrictedInfo = enablePackageList(list, reason);
    } else {
      removeRestrictedInfo = removeRestrictedInfo(list, "default");
    }
    if (removeRestrictedInfo == null || list.isEmpty()) {
      return false;
    }
    addDebugInfoToHistory(
        "SEP",
        appRestrictionInfoToString(restrictionInfo)
            + " "
            + removeRestrictedInfo.toString()
            + " f: "
            + (list.size() - removeRestrictedInfo.size()));
    return removeRestrictedInfo.size() == list.size();
  }

  public final String appRestrictionInfoToString(
      SemAppRestrictionManager.RestrictionInfo restrictionInfo) {
    StringBuilder sb = new StringBuilder();
    int type = restrictionInfo.getType();
    if (type == 0) {
      sb.append("DIS");
    } else if (type == 1) {
      sb.append("SLP");
    } else if (type == 2) {
      sb.append("DIS-C");
    } else if (type == 3) {
      sb.append("NSLP");
    }
    int state = restrictionInfo.getState();
    if (state == 0) {
      sb.append(" NONE");
    } else if (state == 1) {
      sb.append(" ON");
    } else if (state == 2) {
      sb.append(" OFF");
    }
    return sb.toString();
  }

  public boolean clearRestrictionInfo(List list) {
    List removeRestrictedInfo;
    if (!hasPermission("com.samsung.android.permission.SEM_APP_RESTRICTION")
        || list == null
        || (removeRestrictedInfo = removeRestrictedInfo(list, "default")) == null
        || list.isEmpty()) {
      return false;
    }
    addDebugInfoToHistory(
        "SEP",
        "CLR "
            + removeRestrictedInfo.toString()
            + " f: "
            + (list.size() - removeRestrictedInfo.size()));
    return removeRestrictedInfo.size() == list.size();
  }

  public final List removeRestrictedInfo(List list, String str) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo =
              getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
          if (mARsPackageInfo != null) {
            if (mARsPackageInfo.getMaxLevel() == 4) {
              mARsPackageInfo.setDisableReason(FASTableContract.convertDBValueToDisableReason(str));
              mARsPackageInfo.setFasType(FASTableContract.convertFASReasonToValue(str));
              arrayList.add(appRestrictionInfo);
            } else if (mARsPackageInfo.getFASEnabled() && !checkIsChinaModel()) {
              mARsPackageInfo.setDisableReason(FASTableContract.convertDBValueToDisableReason(str));
              arrayList2.add(appRestrictionInfo);
            } else {
              if (!checkIsChinaModel()) {
                mARsPackageInfo.setFasType(FASTableContract.convertFASReasonToValue(str));
                mARsPackageInfo.setFasReason(str);
              }
              mARsPackageInfo.setDisableReason(0);
              mARsPackageInfo.setState(1);
              arrayList3.add(
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrFasReason(
                          FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                      .setStrDisableReason(
                          FASTableContract.convertDisableReasonToDBValue(
                              mARsPackageInfo.getDisableReason()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .build());
              arrayList4.add(Integer.valueOf(mARsPackageInfo.getUid()));
            }
          }
        }
      }
    }
    if (!arrayList.isEmpty()) {
      arrayList4.addAll(enablePackageList(arrayList, str));
    }
    if (!arrayList2.isEmpty()) {
      arrayList4.addAll(awakePackageList(arrayList2, str));
    }
    if (!arrayList3.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList3);
    }
    return arrayList4;
  }

  public final List disablePackageList(List list, String str) {
    boolean z;
    PkgStatusInfo pkgStatusInfo;
    String str2 = str;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    int i = getChangedByUserFromReason(str2) ? 128 : 8;
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        if (isMARsTarget(packageName, userId)) {
          Lock lock = MARsLock;
          synchronized (lock) {
            MARsPackageInfo mARsPackageInfo =
                getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
            if (mARsPackageInfo == null) {
              z = false;
              pkgStatusInfo = null;
            } else if (mARsPackageInfo.getDisabled()
                && str2.equals(Integer.valueOf(mARsPackageInfo.getDisableReason()))) {
              if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(
                    "MARsPolicyManager",
                    "pkg : "
                        + mARsPackageInfo.getName()
                        + " uid : "
                        + mARsPackageInfo.getUid()
                        + " is already disabled, so we will not disable");
              }
            } else {
              z = levelChange(i, mARsPackageInfo);
              if (!z) {
                if (MARsDebugConfig.DEBUG_ENG) {
                  Slog.d(
                      "MARsPolicyManager",
                      "pkg "
                          + mARsPackageInfo.getName()
                          + " uid "
                          + mARsPackageInfo.getUid()
                          + " can't be disabled, so we will not disable");
                }
              } else {
                PkgStatusInfo pkgStatusInfo2 =
                    new PkgStatusInfo(
                        mARsPackageInfo.getName(),
                        mARsPackageInfo.getUid(),
                        mARsPackageInfo.getUserId());
                updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo2);
                pkgStatusInfo = pkgStatusInfo2;
              }
            }
            if (z && pkgStatusInfo != null) {
              if (FreecessController.getInstance().getFreecessEnabled()
                  && FreecessController.getInstance().isFreezedPackage(packageName, userId)) {
                FreecessController.getInstance()
                    .unFreezePackage(packageName, userId, "manualDisable");
              }
              boolean disableAction = disableAction(pkgStatusInfo);
              synchronized (lock) {
                MARsPackageInfo mARsPackageInfo2 =
                    getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
                if (mARsPackageInfo2 != null) {
                  updateInfoToMARsPkgStatus(pkgStatusInfo, mARsPackageInfo2);
                  if (disableAction) {
                    mARsPackageInfo2.setAppliedPolicy(this.disablePolicy);
                    if (this.mMARsRestrictedPackages.get(
                            mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())
                        == null) {
                      this.mMARsRestrictedPackages.put(
                          mARsPackageInfo2.getName(),
                          mARsPackageInfo2.getUserId(),
                          mARsPackageInfo2);
                    }
                  }
                  arrayList.add(
                      new FASEntityBuilder()
                          .setStrPkgName(mARsPackageInfo2.getName())
                          .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                          .setStrMode(mARsPackageInfo2.getFASEnabled() ? "1" : null)
                          .setStrFasReason(
                              mARsPackageInfo2.getFASEnabled()
                                  ? FASTableContract.convertFASTypeToReason(
                                      mARsPackageInfo2.getFasType())
                                  : null)
                          .setStrExtras(
                              FASTableContract.convertStateToDBExtrasValue(
                                  mARsPackageInfo2.getState()))
                          .setStrResetTime(Long.toString(mARsPackageInfo2.getResetTime()))
                          .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                          .setStrDisableType(Integer.toString(mARsPackageInfo2.getDisableType()))
                          .setStrDisableReason(
                              FASTableContract.convertDisableReasonToDBValue(
                                  mARsPackageInfo2.getDisableReason()))
                          .build());
                  arrayList2.add(Integer.valueOf(mARsPackageInfo2.getUid()));
                }
              }
            }
          }
        } else {
          continue;
        }
      }
      str2 = str;
    }
    if (!arrayList.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
    return arrayList2;
  }

  public final boolean removeInDozeAllowList(List list) {
    IDeviceIdleController asInterface =
        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
    Iterator it = list.iterator();
    boolean z = false;
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      UserHandle.getUserId(appRestrictionInfo.getUid());
      try {
        if (asInterface.isPowerSaveWhitelistApp(packageName)) {
          z = true;
          asInterface.removePowerSaveWhitelistApp(packageName);
        }
      } catch (Exception e) {
        Slog.e("MARsPolicyManager", "Doze not available : " + e.toString());
      }
    }
    return z;
  }

  public List disablePackageListForSpecific(List list, String str) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int uid = appRestrictionInfo.getUid();
      if (disablePackageForSpecific(packageName, uid, str)) {
        arrayList.add(Integer.valueOf(uid));
      }
    }
    if (!list.isEmpty()) {
      addDebugInfoToHistory(
          "SEP", "DIS_SPE " + arrayList.toString() + " f: " + (list.size() - arrayList.size()));
    }
    return arrayList;
  }

  public final boolean disablePackageForSpecific(String str, int i, String str2) {
    FASEntity fASEntity;
    boolean z;
    PkgStatusInfo pkgStatusInfo;
    boolean z2;
    int userId = UserHandle.getUserId(i);
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, userId);
      fASEntity = null;
      fASEntity = null;
      fASEntity = null;
      z = false;
      if (mARsPackageInfo != null) {
        if (!"added_from_anomaly_manual".equals(str2)) {
          mARsPackageInfo.setIsSCPMTarget(true);
        }
        if (FreecessController.getInstance().getFreecessEnabled()
            && FreecessController.getInstance()
                .isFreezedPackage(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())) {
          FreecessController.getInstance()
              .unFreezePackage(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), "specificDisable");
        }
        pkgStatusInfo =
            new PkgStatusInfo(
                mARsPackageInfo.getName(), mARsPackageInfo.getUid(), mARsPackageInfo.getUserId());
        z2 = levelChange(IInstalld.FLAG_FORCE, mARsPackageInfo);
        mARsPackageInfo.setDisableReason(FASTableContract.convertDBValueToDisableReason(str2));
        updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
        if (getInstance().isChinaPolicyEnabled()) {
          resetAbusiveFlag(mARsPackageInfo);
        }
      } else {
        pkgStatusInfo = null;
        z2 = false;
      }
    }
    if (z2 && pkgStatusInfo != null) {
      try {
        IPackageManager asInterface =
            IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        int applicationEnabledSetting =
            asInterface.getApplicationEnabledSetting(pkgStatusInfo.name, pkgStatusInfo.userId);
        if (applicationEnabledSetting != 0 && applicationEnabledSetting != 1) {
          if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(
                "MARsPolicyManager",
                "pkg "
                    + pkgStatusInfo.name
                    + " uid "
                    + pkgStatusInfo.uid
                    + " is already disabled, so we will not disable");
          }
        }
        if (!asInterface.isPackageSuspendedForUser(pkgStatusInfo.name, pkgStatusInfo.userId)) {
          asInterface.setApplicationEnabledSetting(
              pkgStatusInfo.name, 4, 0, pkgStatusInfo.userId, "auto_disabler");
          pkgStatusInfo.disableType = applicationEnabledSetting;
          pkgStatusInfo.isDisabled = true;
        }
        z = true;
      } catch (Exception e) {
        Slog.e("MARsPolicyManager", "Error occurred in disable package : " + e);
      }
    }
    if (pkgStatusInfo != null && z) {
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo2 =
            getMARsPackageInfo(this.mMARsTargetPackages, pkgStatusInfo.name, pkgStatusInfo.userId);
        if (mARsPackageInfo2 != null) {
          updateInfoToMARsPkgStatus(pkgStatusInfo, mARsPackageInfo2);
          mARsPackageInfo2.setAppliedPolicy(this.disablePolicy);
          if (this.mMARsRestrictedPackages.get(
                  mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())
              == null) {
            this.mMARsRestrictedPackages.put(
                mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), mARsPackageInfo2);
          }
          fASEntity =
              new FASEntityBuilder()
                  .setStrPkgName(mARsPackageInfo2.getName())
                  .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                  .setStrMode(mARsPackageInfo2.getFASEnabled() ? "1" : null)
                  .setStrNew(mARsPackageInfo2.getFASEnabled() ? "1" : null)
                  .setStrFasReason(
                      mARsPackageInfo2.getFasType() == 1
                          ? FASTableContract.convertFASTypeToReason(mARsPackageInfo2.getFasType())
                          : null)
                  .setStrDisableReason(
                      FASTableContract.convertDisableReasonToDBValue(
                          mARsPackageInfo2.getDisableReason()))
                  .setStrExtras(
                      FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.getState()))
                  .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                  .build();
        }
      }
    }
    if (fASEntity != null) {
      MARsDBManager.getInstance().sendUpdateResetTimeSpecificMsgToDBHandler(fASEntity);
    }
    return z;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final List enablePackageList(List list, String str) {
    int i;
    PkgStatusInfo pkgStatusInfo;
    Iterator it;
    Policy policy;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    if (checkIsChinaModel()) {
      i = IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
    } else {
      i = getChangedByUserFromReason(str) ? 512 : 1024;
    }
    int i2 = i;
    Iterator it2 = list.iterator();
    while (it2.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it2.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        if (isMARsTarget(packageName, userId)) {
          Lock lock = MARsLock;
          synchronized (lock) {
            MARsPackageInfo mARsPackageInfo =
                getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
            pkgStatusInfo = null;
            if (mARsPackageInfo != null) {
              it = it2;
              policy = null;
              PkgStatusInfo pkgStatusInfo2 =
                  new PkgStatusInfo(
                      mARsPackageInfo.getName(),
                      mARsPackageInfo.getUid(),
                      mARsPackageInfo.getUserId());
              updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo2);
              pkgStatusInfo = pkgStatusInfo2;
            } else {
              it = it2;
              policy = null;
            }
          }
          if (pkgStatusInfo != null) {
            boolean enableAction = enableAction(pkgStatusInfo);
            synchronized (lock) {
              MARsPackageInfo mARsPackageInfo2 =
                  getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
              if (mARsPackageInfo2 != null) {
                updateInfoToMARsPkgStatus(pkgStatusInfo, mARsPackageInfo2);
                if (enableAction && levelChange(i2, mARsPackageInfo2)) {
                  if (checkIsChinaModel()) {
                    mARsPackageInfo2.setAppliedPolicy(this.autoRunPolicy);
                  } else {
                    mARsPackageInfo2.setAppliedPolicy(policy);
                    this.mMARsRestrictedPackages.remove(
                        mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId());
                  }
                  mARsPackageInfo2.setDisableReason(
                      FASTableContract.convertDBValueToDisableReason(str));
                }
                arrayList.add(
                    new FASEntityBuilder()
                        .setStrPkgName(mARsPackageInfo2.getName())
                        .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                        .setStrMode(mARsPackageInfo2.getFASEnabled() ? policy : "0")
                        .setStrFasReason(
                            mARsPackageInfo2.getFASEnabled()
                                ? policy
                                : FASTableContract.convertFASTypeToReason(
                                    mARsPackageInfo2.getFasType()))
                        .setStrExtras(
                            FASTableContract.convertStateToDBExtrasValue(
                                mARsPackageInfo2.getState()))
                        .setStrResetTime(Long.toString(mARsPackageInfo2.getResetTime()))
                        .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                        .setStrDisableType(Integer.toString(mARsPackageInfo2.getDisableType()))
                        .setStrDisableReason(
                            FASTableContract.convertDisableReasonToDBValue(
                                mARsPackageInfo2.getDisableReason()))
                        .build());
                arrayList2.add(Integer.valueOf(mARsPackageInfo2.getUid()));
              }
            }
          }
          it2 = it;
        } else {
          continue;
        }
      }
    }
    if (!arrayList.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
    return arrayList2;
  }

  /* JADX WARN: Removed duplicated region for block: B:26:0x00c2  */
  /* JADX WARN: Removed duplicated region for block: B:38:0x00c5  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final List sleepPackageList(List list, String str) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    int i = getChangedByUserFromReason(str) ? 64 : 2;
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int uid = appRestrictionInfo.getUid();
      int userId = UserHandle.getUserId(uid);
      if (isCurrentUser(userId)) {
        boolean z = !this.mAm.getActiveUids().containsKey(Integer.valueOf(uid));
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo =
              getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
          if (mARsPackageInfo != null) {
            if (mARsPackageInfo.getMaxLevel() == 4) {
              PkgStatusInfo pkgStatusInfo =
                  new PkgStatusInfo(
                      mARsPackageInfo.getName(),
                      mARsPackageInfo.getUid(),
                      mARsPackageInfo.getUserId());
              updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo);
              arrayList.add(pkgStatusInfo);
            } else {
              if (mARsPackageInfo.getFASEnabled()) {
                if (mARsPackageInfo.getMaxLevel() < 2) {}
                mARsPackageInfo.setFasType(FASTableContract.convertFASReasonToValue(str));
                mARsPackageInfo.setFasReason(str);
                arrayList2.add(
                    new FASEntityBuilder()
                        .setStrPkgName(mARsPackageInfo.getName())
                        .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                        .setStrMode(!mARsPackageInfo.getFASEnabled() ? "1" : "0")
                        .setStrFasReason(
                            FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                        .setStrExtras(
                            FASTableContract.convertStateToDBExtrasValue(
                                mARsPackageInfo.getState()))
                        .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                        .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                        .build());
                arrayList3.add(Integer.valueOf(mARsPackageInfo.getUid()));
                if (isChinaPolicyEnabled() && i == 64 && z) {
                  addRestrictListAvoidAssoicationLaunch(
                      packageName, userId, "auto run off", "UserChangeFAS");
                }
              }
              levelChange(i, mARsPackageInfo);
              mARsPackageInfo.setFasType(FASTableContract.convertFASReasonToValue(str));
              mARsPackageInfo.setFasReason(str);
              arrayList2.add(
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrMode(!mARsPackageInfo.getFASEnabled() ? "1" : "0")
                      .setStrFasReason(
                          FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType()))
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .build());
              arrayList3.add(Integer.valueOf(mARsPackageInfo.getUid()));
              if (isChinaPolicyEnabled()) {
                addRestrictListAvoidAssoicationLaunch(
                    packageName, userId, "auto run off", "UserChangeFAS");
              }
            }
          }
        }
      }
    }
    Iterator it2 = arrayList.iterator();
    while (it2.hasNext()) {
      PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) it2.next();
      boolean enableAction = enableAction(pkgStatusInfo2);
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo2 =
            getMARsPackageInfo(
                this.mMARsTargetPackages, pkgStatusInfo2.name, pkgStatusInfo2.userId);
        if (mARsPackageInfo2 != null) {
          updateInfoToMARsPkgStatus(pkgStatusInfo2, mARsPackageInfo2);
          if (enableAction && levelChange(i, mARsPackageInfo2)) {
            if (mARsPackageInfo2.getState() > 4) {
              mARsPackageInfo2.setState(4);
            }
            mARsPackageInfo2.setFasType(FASTableContract.convertFASReasonToValue(str));
            mARsPackageInfo2.setFasReason(str);
            mARsPackageInfo2.setDisableReason(4);
            if (checkIsChinaModel()) {
              mARsPackageInfo2.setAppliedPolicy(this.autoRunPolicy);
            } else {
              mARsPackageInfo2.setAppliedPolicy(null);
              this.mMARsRestrictedPackages.remove(
                  mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId());
            }
            arrayList2.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo2.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                    .setStrMode(mARsPackageInfo2.getFASEnabled() ? "1" : "0")
                    .setStrFasReason(
                        FASTableContract.convertFASTypeToReason(mARsPackageInfo2.getFasType()))
                    .setStrExtras(
                        FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo2.getState()))
                    .setStrResetTime(Long.toString(mARsPackageInfo2.getResetTime()))
                    .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                    .setStrDisableReason(
                        FASTableContract.convertDisableReasonToDBValue(
                            mARsPackageInfo2.getDisableReason()))
                    .build());
            arrayList3.add(Integer.valueOf(mARsPackageInfo2.getUid()));
          }
        }
      }
    }
    if (!arrayList2.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList2);
    }
    return arrayList3;
  }

  public final List awakePackageList(List list, String str) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo =
              getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
          if (mARsPackageInfo != null) {
            if (mARsPackageInfo.getMaxLevel() == 4) {
              arrayList2.add(appRestrictionInfo);
            } else {
              if (mARsPackageInfo.getFASEnabled() || mARsPackageInfo.getMaxLevel() > 1) {
                levelChange(256, mARsPackageInfo);
                MARsHandler.getInstance()
                    .sendCallSetModeMsgToMainHandler(
                        mARsPackageInfo.getName(), mARsPackageInfo.getUid(), 0);
              }
              mARsPackageInfo.setFasType(FASTableContract.convertFASReasonToValue(str));
              mARsPackageInfo.setFasReason(str);
              String str2 = null;
              mARsPackageInfo.setAppliedPolicy(null);
              this.mMARsRestrictedPackages.remove(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUserId());
              FASEntityBuilder strMode =
                  new FASEntityBuilder()
                      .setStrPkgName(mARsPackageInfo.getName())
                      .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                      .setStrMode(mARsPackageInfo.getFASEnabled() ? "1" : "0");
              if (!mARsPackageInfo.getFASEnabled()) {
                str2 = FASTableContract.convertFASTypeToReason(mARsPackageInfo.getFasType());
              }
              arrayList.add(
                  strMode
                      .setStrFasReason(str2)
                      .setStrExtras(
                          FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                      .setStrResetTime(Long.toString(mARsPackageInfo.getResetTime()))
                      .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                      .setStrDisableType(Integer.toString(mARsPackageInfo.getDisableType()))
                      .setStrDisableReason(
                          FASTableContract.convertDisableReasonToDBValue(
                              mARsPackageInfo.getDisableReason()))
                      .build());
              arrayList3.add(Integer.valueOf(mARsPackageInfo.getUid()));
            }
          }
        }
      }
    }
    if (!arrayList2.isEmpty()) {
      arrayList3.addAll(enablePackageList(list, str));
    }
    if (!arrayList.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
    return arrayList3;
  }

  public final List updateDoNotDisableInfo(List list) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo =
              getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
          if (mARsPackageInfo != null) {
            mARsPackageInfo.setDisableReason(8);
            mARsPackageInfo.setState(mARsPackageInfo.getFASEnabled() ? 4 : 1);
            arrayList.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                    .setStrExtras(
                        FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                    .setStrDisableReason(
                        FASTableContract.convertDisableReasonToDBValue(
                            mARsPackageInfo.getDisableReason()))
                    .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                    .build());
            arrayList2.add(Integer.valueOf(mARsPackageInfo.getUid()));
          }
        }
      }
    }
    if (!arrayList.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
    return arrayList2;
  }

  public final List updateDisableCandidateInfo(List list) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    this.mLastNotiSentTimeForDisabled = 0L;
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo =
          (SemAppRestrictionManager.AppRestrictionInfo) it.next();
      String packageName = appRestrictionInfo.getPackageName();
      int userId = UserHandle.getUserId(appRestrictionInfo.getUid());
      if (isCurrentUser(userId)) {
        synchronized (MARsLock) {
          MARsPackageInfo mARsPackageInfo =
              getMARsPackageInfo(this.mMARsTargetPackages, packageName, userId);
          if (mARsPackageInfo != null
              && mARsPackageInfo.getMaxLevel() < 4
              && FilterManager.getInstance()
                      .filterForSpecificPolicy(
                          7,
                          mARsPackageInfo.getName(),
                          mARsPackageInfo.getUserId(),
                          mARsPackageInfo.getUid())
                  == 0
              && levelChange(4, mARsPackageInfo)) {
            arrayList.add(
                new FASEntityBuilder()
                    .setStrPkgName(mARsPackageInfo.getName())
                    .setStrUid(Integer.toString(mARsPackageInfo.getUid()))
                    .setStrExtras(
                        FASTableContract.convertStateToDBExtrasValue(mARsPackageInfo.getState()))
                    .setStrLevel(Integer.toString(mARsPackageInfo.getMaxLevel()))
                    .build());
            arrayList2.add(Integer.valueOf(mARsPackageInfo.getUid()));
          }
        }
      }
    }
    if (!arrayList.isEmpty()) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList);
    }
    return arrayList2;
  }

  public final boolean getChangedByUserFromReason(String str) {
    return "added_from_user_manual".equals(str)
        || "added_from_anomaly_manual".equals(str)
        || "deleted_from_user_manual".equals(str);
  }

  public final boolean hasPermission(String str) {
    int callingUid = Binder.getCallingUid();
    IPackageManager asInterface =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    try {
      if (1000 == UserHandle.getAppId(callingUid) || asInterface == null) {
        return true;
      }
      return asInterface.checkUidPermission(str, callingUid) == 0;
    } catch (RemoteException e) {
      Slog.e("MARsPolicyManager", "hasPermission exception:" + e);
      return true;
    }
  }

  public boolean isAlarmWakeupAllowList(int i, String str, String str2, int i2) {
    return MARsVersionManager.getInstance().isAdjustRestrictionMatch(24, str, null, str2);
  }

  public boolean isAlarmWakeupBlockList(int i, String str, String str2, int i2) {
    return MARsDebugConfig.ENABLE_ALARM_WAKEUP_BLOCK
        && MARsVersionManager.getInstance().isAdjustRestrictionMatch(23, str, null, str2);
  }

  public boolean isAlarmForceSetWindow(String str, String str2) {
    if (MARsDebugConfig.ENABLE_ALARM_WAKEUP_BLOCK) {
      return MARsVersionManager.getInstance().isAdjustRestrictionMatch(25, str, null, str2);
    }
    return false;
  }

  public void restrictJobsByUid(int i, boolean z) {
    if (isChinaModel && this.mContext != null) {
      Intent intent = new Intent();
      intent.setAction("android.intent.ACTION_JOB_RESTRICT_UID");
      intent.putExtra("uid", i);
      intent.putExtra("restrict", z);
      Slog.d("MARsPolicyManager", "restrictJobsByUid: u=" + i + ", restrict=" + z);
      this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:36:0x016f A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:39:0x0174 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:70:0x0251 A[ADDED_TO_REGION, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void setSCPMList(ArrayList arrayList) {
    char c;
    Iterator it;
    int i;
    PkgStatusInfo pkgStatusInfo;
    boolean z;
    int i2;
    boolean z2;
    IPackageManager asInterface;
    int applicationEnabledSetting;
    String[] strArr = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    String[] strArr2 = {"", "[FRZ]", "[FAS]", "[FOS]", "[DIS]"};
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    synchronized (this.mScpmList) {
      this.mScpmList.clear();
      this.mScpmList.addAll(arrayList);
    }
    int i3 = 0;
    for (Iterator it2 = arrayList.iterator(); it2.hasNext(); it2 = it) {
      String str = (String) it2.next();
      synchronized (MARsLock) {
        MARsPackageInfo mARsPackageInfo =
            getMARsPackageInfo(this.mMARsTargetPackages, str, this.mContext.getUserId());
        if (mARsPackageInfo == null || mARsPackageInfo.getMaxLevel() == 4) {
          c = 4;
          it = it2;
          i = 16;
          pkgStatusInfo = null;
          z = false;
        } else {
          mARsPackageInfo.setIsSCPMTarget(true);
          if (FreecessController.getInstance().getFreecessEnabled()
              && FreecessController.getInstance()
                  .isFreezedPackage(mARsPackageInfo.getName(), mARsPackageInfo.getUserId())) {
            FreecessController.getInstance()
                .unFreezePackage(
                    mARsPackageInfo.getName(), mARsPackageInfo.getUserId(), "specificDisable");
          }
          c = 4;
          it = it2;
          i = 16;
          PkgStatusInfo pkgStatusInfo2 =
              new PkgStatusInfo(
                  mARsPackageInfo.getName(), mARsPackageInfo.getUid(), mARsPackageInfo.getUserId());
          z = levelChange(IInstalld.FLAG_FORCE, mARsPackageInfo);
          mARsPackageInfo.setDisableReason(16);
          updateInfoToPkgStatus(mARsPackageInfo, pkgStatusInfo2);
          pkgStatusInfo = pkgStatusInfo2;
        }
      }
      if (!z || pkgStatusInfo == null) {
        i2 = 1;
      } else {
        try {
          asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
          applicationEnabledSetting =
              asInterface.getApplicationEnabledSetting(pkgStatusInfo.name, pkgStatusInfo.userId);
          i2 = 1;
        } catch (Exception e) {
          e = e;
          i2 = 1;
        }
        if (applicationEnabledSetting == 0 || applicationEnabledSetting == 1) {
          if (!asInterface.isPackageSuspendedForUser(pkgStatusInfo.name, pkgStatusInfo.userId)) {
            asInterface.setApplicationEnabledSetting(
                pkgStatusInfo.name, 4, 0, pkgStatusInfo.userId, "auto_disabler");
            pkgStatusInfo.disableType = applicationEnabledSetting;
            pkgStatusInfo.isDisabled = true;
          }
          z2 = true;
          if (pkgStatusInfo == null && z2) {
            synchronized (MARsLock) {
              MARsPackageInfo mARsPackageInfo2 =
                  getMARsPackageInfo(
                      this.mMARsTargetPackages, pkgStatusInfo.name, pkgStatusInfo.userId);
              if (mARsPackageInfo2 != null) {
                updateInfoToMARsPkgStatus(pkgStatusInfo, mARsPackageInfo2);
                mARsPackageInfo2.setAppliedPolicy(this.disablePolicy);
                if (this.mMARsRestrictedPackages.get(
                        mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId())
                    == null) {
                  this.mMARsRestrictedPackages.put(
                      mARsPackageInfo2.getName(), mARsPackageInfo2.getUserId(), mARsPackageInfo2);
                }
                arrayList3.add(mARsPackageInfo2.getName());
                arrayList4.add(Integer.valueOf(mARsPackageInfo2.getUid()));
                if (i3 == 0) {
                  i3 = i2;
                }
                strArr[c] = strArr[c] + " " + mARsPackageInfo2.getUid();
                arrayList2.add(
                    new FASEntityBuilder()
                        .setStrPkgName(mARsPackageInfo2.getName())
                        .setStrUid(Integer.toString(mARsPackageInfo2.getUid()))
                        .setStrMode(mARsPackageInfo2.getFASEnabled() ? "1" : null)
                        .setStrNew(mARsPackageInfo2.getFASEnabled() ? "1" : null)
                        .setStrFasReason(
                            mARsPackageInfo2.getFasType() == i2
                                ? FASTableContract.convertFASTypeToReason(
                                    mARsPackageInfo2.getFasType())
                                : null)
                        .setStrDisableReason(FASTableContract.convertDisableReasonToDBValue(i))
                        .setStrExtras(
                            FASTableContract.convertStateToDBExtrasValue(
                                mARsPackageInfo2.getState()))
                        .setStrLevel(Integer.toString(mARsPackageInfo2.getMaxLevel()))
                        .build());
              }
            }
          }
        } else {
          try {
            if (MARsDebugConfig.DEBUG_ENG) {
              Slog.d(
                  "MARsPolicyManager",
                  "pkg "
                      + pkgStatusInfo.name
                      + " uid "
                      + pkgStatusInfo.uid
                      + " is already disabled, so we will not disable");
            }
          } catch (Exception e2) {
            e = e2;
            Slog.e("MARsPolicyManager", "Error occurred in disable package : " + e);
            z2 = false;
            if (pkgStatusInfo == null) {}
          }
        }
      }
      z2 = false;
      if (pkgStatusInfo == null) {
        synchronized (MARsLock) {
        }
      }
    }
    if (i3 != 0) {
      addDebugInfoToHistory("LVU", convertLevelChangeInfoToString(strArr, strArr2));
    }
    if (arrayList2.size() > 0) {
      MARsDBManager.getInstance().sendUpdateResetTimeMsgToDBHandler(arrayList2);
      MARsHandler.getInstance()
          .sendNotifyDeviceCareMsgToMainHandler("deepsleepspecific", arrayList3, arrayList4);
    }
  }

  public void checkUpdatedDBFromSDHMS() {
    boolean z;
    synchronized (MARsLock) {
      z = this.mMARsTargetPackages.size() > 0;
    }
    if (z) {
      return;
    }
    MARsDBManager.getInstance().sendSdhmsDBCompleteMsgToDBHandler();
  }

  public void setFGSRestrictionTarget(String str, int i) {
    MARsPackageInfo mARsPackageInfo;
    synchronized (MARsLock) {
      mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, UserHandle.getUserId(i));
      if (mARsPackageInfo == null
          || mARsPackageInfo.getDisableResetTime() <= 0
          || System.currentTimeMillis() - mARsPackageInfo.getDisableResetTime() < 259200000
          || mARsPackageInfo.getBatteryUsage() < FGS_BATTERY_USAGE_THRESHOLD) {
        mARsPackageInfo = null;
      } else {
        MARsHandler.getInstance()
            .sendAnomalyMsgToMainHandler(
                mARsPackageInfo.getName(), mARsPackageInfo.getUid(), "excessive_fgs");
      }
    }
    if (mARsPackageInfo != null) {
      MARsBigData.getInstance(this.mContext).sendFalconBigData(mARsPackageInfo);
    }
  }

  public void onAppUsedForSpecificCase(String str, int i) {
    synchronized (MARsLock) {
      MARsPackageInfo mARsPackageInfo = getMARsPackageInfo(this.mMARsTargetPackages, str, i);
      if (mARsPackageInfo != null) {
        mARsPackageInfo.setDisableResetTime(System.currentTimeMillis());
        onAppUsed(mARsPackageInfo);
      }
    }
  }

  public void killPhantomProcessLocked(final List list) {
    new Thread() { // from class: com.android.server.am.MARsPolicyManager.1
      @Override // java.lang.Thread, java.lang.Runnable
      public void run() {
        try {
          ArrayList<Integer> arrayList = new ArrayList();
          for (Integer num : list) {
            synchronized (MARsPolicyManager.this.mAm.mPidsSelfLocked) {
              if (MARsPolicyManager.this.mAm.mPidsSelfLocked.get(num.intValue()) == null) {
                arrayList.add(num);
              }
            }
          }
          for (Integer num2 : arrayList) {
            Slog.d(
                "MARsPolicyManager",
                "TCPU : kill phantom uid "
                    + Process.getUidForPid(num2.intValue())
                    + ", pid "
                    + num2);
            Process.killProcess(num2.intValue());
          }
          MARsPolicyManager.this.addDebugInfoToHistory(
              "TCPU", "kill phantom processes " + arrayList);
        } catch (Exception e) {
          Slog.e("MARsPolicyManager", "Error occurred in killPhantomProcessLocked() " + e);
        }
      }
    }.start();
  }

  public String getPackageNameByUid(int i) {
    String[] strArr;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        strArr = this.mContext.getPackageManager().getPackagesForUid(i);
      } catch (Exception e) {
        e.printStackTrace();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        strArr = null;
      }
      if (strArr == null) {
        Slog.e("MARsPolicyManager", "package name not found for uid " + i);
        return Integer.toString(i);
      }
      return strArr[0];
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
