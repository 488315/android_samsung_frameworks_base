package com.android.server.usage;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.AppStandbyInfo;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileAppsInternal;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.NetworkScoreManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.SparseSetArray;
import android.util.TimeUtils;
import android.widget.Toast;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.ConcurrentUtils;
import com.android.server.AlarmManagerInternal;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.LocalServices;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.pkg.AndroidPackage;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class AppStandbyController
    implements AppStandbyInternal, UsageStatsManagerInternal.UsageEventListener {
  public final SparseArray mActiveAdminApps;
  public final CountDownLatch mAdminDataAvailableLatch;
  public final SparseArray mAdminProtectedPackages;
  public volatile boolean mAppIdleEnabled;
  public AppIdleHistory mAppIdleHistory;
  public final Object mAppIdleLock;
  public AppOpsManager mAppOpsManager;
  public long[] mAppStandbyElapsedThresholds;
  public final Map mAppStandbyProperties;
  public long[] mAppStandbyScreenThresholds;
  public AppWidgetManager mAppWidgetManager;
  public final SparseSetArray mAppsToRestoreToRare;
  public volatile String mBroadcastResponseExemptedPermissions;
  public volatile List mBroadcastResponseExemptedPermissionsList;
  public volatile String mBroadcastResponseExemptedRoles;
  public volatile List mBroadcastResponseExemptedRolesList;
  public volatile int mBroadcastResponseFgThresholdState;
  public volatile long mBroadcastResponseWindowDurationMillis;
  public volatile long mBroadcastSessionsDurationMs;
  public volatile long mBroadcastSessionsWithResponseDurationMs;
  public String mCachedDeviceProvisioningPackage;
  public volatile String mCachedNetworkScorer;
  public volatile long mCachedNetworkScorerAtMillis;
  public List mCarrierPrivilegedApps;
  public final Object mCarrierPrivilegedLock;
  public long mCheckIdleIntervalMillis;
  public final Context mContext;
  public final DisplayManager.DisplayListener mDisplayListener;
  public long mExemptedSyncScheduledDozeTimeoutMillis;
  public long mExemptedSyncScheduledNonDozeTimeoutMillis;
  public long mExemptedSyncStartTimeoutMillis;
  public final AppStandbyHandler mHandler;
  public boolean mHaveCarrierPrivilegedApps;
  public final ArraySet mHeadlessSystemApps;
  public long mInitialForegroundServiceStartTimeoutMillis;
  public Injector mInjector;
  public volatile boolean mIsCharging;
  public boolean mLinkCrossProfileApps;
  public volatile boolean mNoteResponseEventForAllBroadcastSessions;
  public int mNotificationSeenPromotedBucket;
  public long mNotificationSeenTimeoutMillis;
  public final ArrayList mPackageAccessListeners;
  public PackageManager mPackageManager;
  public final SparseLongArray mPendingIdleStateChecks;
  public boolean mPendingInitializeDefaults;
  public volatile boolean mPendingOneTimeCheckIdleStates;
  public long mPredictionTimeoutMillis;
  public boolean mRetainNotificationSeenImpactForPreTApps;
  public long mSlicePinnedTimeoutMillis;
  public long mStrongUsageTimeoutMillis;
  public long mSyncAdapterTimeoutMillis;
  public final SparseIntArray mSystemExemptionAppOpMode;
  public long mSystemInteractionTimeoutMillis;
  public final ArrayList mSystemPackagesAppIds;
  public boolean mSystemServicesReady;
  public long mSystemUpdateUsageTimeoutMillis;
  public boolean mTriggerQuotaBumpOnNotificationSeen;
  public long mUnexemptedSyncScheduledTimeoutMillis;
  static final long[] DEFAULT_SCREEN_TIME_THRESHOLDS = {
    0, 0, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, 7200000, 21600000
  };
  static final long[] MINIMUM_SCREEN_TIME_THRESHOLDS = {
    0, 0, 0, 1800000, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS
  };
  static final long[] DEFAULT_ELAPSED_TIME_THRESHOLDS = {
    0,
    43200000,
    BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS,
    172800000,
    691200000
  };
  static final long[] MINIMUM_ELAPSED_TIME_THRESHOLDS = {
    0,
    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS,
    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS,
    7200000,
    BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS
  };
  public static final int[] THRESHOLD_BUCKETS = {10, 20, 30, 40, 45};

  public class Lock {}

  public static boolean isUserUsage(int i) {
    if ((65280 & i) != 768) {
      return false;
    }
    int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    return i2 == 3 || i2 == 4;
  }

  public final int usageEventToSubReason(int i) {
    if (i == 1) {
      return 4;
    }
    if (i == 2) {
      return 5;
    }
    if (i == 6) {
      return 1;
    }
    if (i == 7) {
      return 3;
    }
    if (i == 10) {
      return 2;
    }
    if (i == 19) {
      return 15;
    }
    if (i != 13) {
      return i != 14 ? 0 : 9;
    }
    return 10;
  }

  public class Pool {
    public final Object[] mArray;
    public int mSize = 0;

    public Pool(Object[] objArr) {
      this.mArray = objArr;
    }

    public synchronized Object obtain() {
      Object obj;
      int i = this.mSize;
      if (i > 0) {
        Object[] objArr = this.mArray;
        int i2 = i - 1;
        this.mSize = i2;
        obj = objArr[i2];
      } else {
        obj = null;
      }
      return obj;
    }

    public synchronized void recycle(Object obj) {
      int i = this.mSize;
      Object[] objArr = this.mArray;
      if (i < objArr.length) {
        this.mSize = i + 1;
        objArr[i] = obj;
      }
    }
  }

  public class StandbyUpdateRecord {
    public static final Pool sPool = new Pool(new StandbyUpdateRecord[10]);
    public int bucket;
    public boolean isUserInteraction;
    public String packageName;
    public int reason;
    public int userId;

    public static StandbyUpdateRecord obtain(String str, int i, int i2, int i3, boolean z) {
      StandbyUpdateRecord standbyUpdateRecord = (StandbyUpdateRecord) sPool.obtain();
      if (standbyUpdateRecord == null) {
        standbyUpdateRecord = new StandbyUpdateRecord();
      }
      standbyUpdateRecord.packageName = str;
      standbyUpdateRecord.userId = i;
      standbyUpdateRecord.bucket = i2;
      standbyUpdateRecord.reason = i3;
      standbyUpdateRecord.isUserInteraction = z;
      return standbyUpdateRecord;
    }

    public void recycle() {
      sPool.recycle(this);
    }
  }

  public class ContentProviderUsageRecord {
    public static final Pool sPool = new Pool(new ContentProviderUsageRecord[10]);
    public String name;
    public String packageName;
    public int userId;

    public static ContentProviderUsageRecord obtain(String str, String str2, int i) {
      ContentProviderUsageRecord contentProviderUsageRecord =
          (ContentProviderUsageRecord) sPool.obtain();
      if (contentProviderUsageRecord == null) {
        contentProviderUsageRecord = new ContentProviderUsageRecord();
      }
      contentProviderUsageRecord.name = str;
      contentProviderUsageRecord.packageName = str2;
      contentProviderUsageRecord.userId = i;
      return contentProviderUsageRecord;
    }

    public void recycle() {
      sPool.recycle(this);
    }
  }

  public AppStandbyController(Context context) {
    this(new Injector(context, AppSchedulingModuleThread.get().getLooper()));
  }

  /* JADX WARN: Multi-variable type inference failed */
  public AppStandbyController(Injector injector) {
    Lock lock = new Lock();
    this.mAppIdleLock = lock;
    this.mPackageAccessListeners = new ArrayList();
    this.mCarrierPrivilegedLock = new Lock();
    this.mActiveAdminApps = new SparseArray();
    this.mAdminProtectedPackages = new SparseArray();
    this.mHeadlessSystemApps = new ArraySet();
    this.mAdminDataAvailableLatch = new CountDownLatch(1);
    this.mPendingIdleStateChecks = new SparseLongArray();
    this.mSystemExemptionAppOpMode = new SparseIntArray();
    Object[] objArr = 0;
    this.mCachedNetworkScorer = null;
    this.mCachedNetworkScorerAtMillis = 0L;
    this.mCachedDeviceProvisioningPackage = null;
    long[] jArr = DEFAULT_ELAPSED_TIME_THRESHOLDS;
    this.mCheckIdleIntervalMillis =
        Math.min(
            jArr[1] / 4, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
    this.mAppStandbyScreenThresholds = DEFAULT_SCREEN_TIME_THRESHOLDS;
    this.mAppStandbyElapsedThresholds = jArr;
    this.mStrongUsageTimeoutMillis = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    this.mNotificationSeenTimeoutMillis = 43200000L;
    this.mSlicePinnedTimeoutMillis = 43200000L;
    this.mNotificationSeenPromotedBucket = 20;
    this.mTriggerQuotaBumpOnNotificationSeen = false;
    this.mRetainNotificationSeenImpactForPreTApps = false;
    this.mSystemUpdateUsageTimeoutMillis = 7200000L;
    this.mPredictionTimeoutMillis = 43200000L;
    this.mSyncAdapterTimeoutMillis = 600000L;
    this.mExemptedSyncScheduledNonDozeTimeoutMillis = 600000L;
    this.mExemptedSyncScheduledDozeTimeoutMillis =
        BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
    this.mExemptedSyncStartTimeoutMillis = 600000L;
    this.mUnexemptedSyncScheduledTimeoutMillis = 600000L;
    this.mSystemInteractionTimeoutMillis = 600000L;
    this.mInitialForegroundServiceStartTimeoutMillis = 1800000L;
    this.mLinkCrossProfileApps = true;
    this.mBroadcastResponseWindowDurationMillis = 120000L;
    this.mBroadcastResponseFgThresholdState = 2;
    this.mBroadcastSessionsDurationMs = 120000L;
    this.mBroadcastSessionsWithResponseDurationMs = 120000L;
    this.mNoteResponseEventForAllBroadcastSessions = true;
    this.mBroadcastResponseExemptedRoles = "";
    List list = Collections.EMPTY_LIST;
    this.mBroadcastResponseExemptedRolesList = list;
    this.mBroadcastResponseExemptedPermissions = "";
    this.mBroadcastResponseExemptedPermissionsList = list;
    this.mAppStandbyProperties = new ArrayMap();
    this.mAppsToRestoreToRare = new SparseSetArray();
    this.mSystemPackagesAppIds = new ArrayList();
    this.mSystemServicesReady = false;
    this.mDisplayListener =
        new DisplayManager
            .DisplayListener() { // from class: com.android.server.usage.AppStandbyController.2
          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayAdded(int i) {}

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayRemoved(int i) {}

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayChanged(int i) {
            if (i == 0) {
              boolean isDisplayOn = AppStandbyController.this.isDisplayOn();
              synchronized (AppStandbyController.this.mAppIdleLock) {
                AppStandbyController.this.mAppIdleHistory.updateDisplay(
                    isDisplayOn, AppStandbyController.this.mInjector.elapsedRealtime());
              }
            }
          }
        };
    this.mInjector = injector;
    Context context = injector.getContext();
    this.mContext = context;
    AppStandbyHandler appStandbyHandler = new AppStandbyHandler(this.mInjector.getLooper());
    this.mHandler = appStandbyHandler;
    this.mPackageManager = context.getPackageManager();
    BroadcastReceiver deviceStateReceiver = new DeviceStateReceiver();
    IntentFilter intentFilter = new IntentFilter("android.os.action.CHARGING");
    intentFilter.addAction("android.os.action.DISCHARGING");
    intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
    context.registerReceiver(deviceStateReceiver, intentFilter);
    synchronized (lock) {
      this.mAppIdleHistory =
          new AppIdleHistory(
              this.mInjector.getDataSystemDirectory(), this.mInjector.elapsedRealtime());
    }
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addDataScheme("package");
    context.registerReceiverAsUser(
        new PackageReceiver(), UserHandle.ALL, intentFilter2, null, appStandbyHandler);
  }

  public void setAppIdleEnabled(boolean z) {
    UsageStatsManagerInternal usageStatsManagerInternal =
        (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
    if (z) {
      usageStatsManagerInternal.registerListener(this);
    } else {
      usageStatsManagerInternal.unregisterListener(this);
    }
    synchronized (this.mAppIdleLock) {
      if (this.mAppIdleEnabled != z) {
        boolean isInParole = isInParole();
        this.mAppIdleEnabled = z;
        if (isInParole() != isInParole) {
          postParoleStateChanged();
        }
      }
    }
  }

  public boolean isAppIdleEnabled() {
    return this.mAppIdleEnabled;
  }

  public void onBootPhase(int i) {
    int i2;
    boolean userFileExists;
    this.mInjector.onBootPhase(i);
    if (i != 500) {
      if (i == 1000) {
        setChargingState(this.mInjector.isCharging());
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                AppStandbyController.this.updatePowerWhitelistCache();
              }
            });
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                AppStandbyController.this.loadHeadlessSystemAppCache();
              }
            });
        return;
      }
      return;
    }
    Slog.d("AppStandbyController", "Setting app idle enabled state");
    if (this.mAppIdleEnabled) {
      ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class))
          .registerListener(this);
    }
    new ConstantsObserver(this.mHandler).start();
    this.mAppWidgetManager =
        (AppWidgetManager) this.mContext.getSystemService(AppWidgetManager.class);
    this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
    try {
      this.mInjector
          .getAppOpsService()
          .startWatchingMode(
              128,
              (String) null,
              new IAppOpsCallback
                  .Stub() { // from class: com.android.server.usage.AppStandbyController.1
                public void opChanged(int i3, int i4, String str) {
                  int userId = UserHandle.getUserId(i4);
                  synchronized (AppStandbyController.this.mSystemExemptionAppOpMode) {
                    AppStandbyController.this.mSystemExemptionAppOpMode.delete(i4);
                  }
                  AppStandbyController.this
                      .mHandler
                      .obtainMessage(11, userId, i4, str)
                      .sendToTarget();
                }
              });
    } catch (RemoteException e) {
      Slog.wtf("AppStandbyController", "Failed start watching for app op", e);
    }
    this.mInjector.registerDisplayListener(this.mDisplayListener, this.mHandler);
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.updateDisplay(isDisplayOn(), this.mInjector.elapsedRealtime());
    }
    this.mSystemServicesReady = true;
    synchronized (this.mAppIdleLock) {
      userFileExists = this.mAppIdleHistory.userFileExists(0);
    }
    if (this.mPendingInitializeDefaults || !userFileExists) {
      initializeDefaultsForSystemApps(0);
    }
    if (this.mPendingOneTimeCheckIdleStates) {
      postOneTimeCheckIdleStates();
    }
    List<ApplicationInfo> installedApplications =
        this.mPackageManager.getInstalledApplications(542908416);
    int size = installedApplications.size();
    for (i2 = 0; i2 < size; i2++) {
      this.mSystemPackagesAppIds.add(
          Integer.valueOf(UserHandle.getAppId(installedApplications.get(i2).uid)));
    }
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:? -> B:22:0x0065). Please report as a decompilation issue!!! */
  public final void reportContentProviderUsage(String str, String str2, int i) {
    int i2;
    int i3;
    Object obj;
    if (this.mAppIdleEnabled) {
      String[] syncAdapterPackagesForAuthorityAsUser =
          ContentResolver.getSyncAdapterPackagesForAuthorityAsUser(str, i);
      PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      int length = syncAdapterPackagesForAuthorityAsUser.length;
      int i4 = 0;
      while (i4 < length) {
        String str3 = syncAdapterPackagesForAuthorityAsUser[i4];
        if (!str3.equals(str2)) {
          if (this.mSystemPackagesAppIds.contains(
              Integer.valueOf(
                  UserHandle.getAppId(packageManagerInternal.getPackageUid(str3, 0L, i))))) {
            List crossProfileTargets = getCrossProfileTargets(str3, i);
            Object obj2 = this.mAppIdleLock;
            synchronized (obj2) {
              try {
                obj = obj2;
                i2 = i4;
                i3 = length;
                try {
                  reportNoninteractiveUsageCrossUserLocked(
                      str3,
                      i,
                      10,
                      8,
                      elapsedRealtime,
                      this.mSyncAdapterTimeoutMillis,
                      crossProfileTargets);
                  i4 = i2 + 1;
                  length = i3;
                } catch (Throwable th) {
                  th = th;
                  throw th;
                }
              } catch (Throwable th2) {
                th = th2;
                obj = obj2;
                throw th;
              }
            }
          }
        }
        i2 = i4;
        i3 = length;
        i4 = i2 + 1;
        length = i3;
      }
    }
  }

  public final void reportExemptedSyncScheduled(String str, int i) {
    long j;
    int i2;
    int i3;
    if (this.mAppIdleEnabled) {
      if (!this.mInjector.isDeviceIdleMode()) {
        j = this.mExemptedSyncScheduledNonDozeTimeoutMillis;
        i2 = 10;
        i3 = 11;
      } else {
        j = this.mExemptedSyncScheduledDozeTimeoutMillis;
        i2 = 20;
        i3 = 12;
      }
      long j2 = j;
      int i4 = i3;
      int i5 = i2;
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      List crossProfileTargets = getCrossProfileTargets(str, i);
      synchronized (this.mAppIdleLock) {
        reportNoninteractiveUsageCrossUserLocked(
            str, i, i5, i4, elapsedRealtime, j2, crossProfileTargets);
      }
    }
  }

  public final void reportUnexemptedSyncScheduled(String str, int i) {
    if (this.mAppIdleEnabled) {
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      synchronized (this.mAppIdleLock) {
        if (this.mAppIdleHistory.getAppStandbyBucket(str, i, elapsedRealtime) == 50) {
          reportNoninteractiveUsageCrossUserLocked(
              str,
              i,
              20,
              14,
              elapsedRealtime,
              this.mUnexemptedSyncScheduledTimeoutMillis,
              getCrossProfileTargets(str, i));
        }
      }
    }
  }

  public final void reportExemptedSyncStart(String str, int i) {
    if (this.mAppIdleEnabled) {
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      List crossProfileTargets = getCrossProfileTargets(str, i);
      synchronized (this.mAppIdleLock) {
        reportNoninteractiveUsageCrossUserLocked(
            str,
            i,
            10,
            13,
            elapsedRealtime,
            this.mExemptedSyncStartTimeoutMillis,
            crossProfileTargets);
      }
    }
  }

  public final void reportNoninteractiveUsageCrossUserLocked(
      String str, int i, int i2, int i3, long j, long j2, List list) {
    reportNoninteractiveUsageLocked(str, i, i2, i3, j, j2);
    int size = list.size();
    for (int i4 = 0; i4 < size; i4++) {
      reportNoninteractiveUsageLocked(
          str, ((UserHandle) list.get(i4)).getIdentifier(), i2, i3, j, j2);
    }
  }

  public final void reportNoninteractiveUsageLocked(
      String str, int i, int i2, int i3, long j, long j2) {
    AppIdleHistory.AppUsageHistory reportUsage =
        this.mAppIdleHistory.reportUsage(str, i, i2, i3, 0L, j + j2);
    AppStandbyHandler appStandbyHandler = this.mHandler;
    appStandbyHandler.sendMessageDelayed(appStandbyHandler.obtainMessage(11, i, -1, str), j2);
    maybeInformListeners(str, i, j, reportUsage.currentBucket, reportUsage.bucketingReason, false);
  }

  public final void triggerListenerQuotaBump(String str, int i) {
    if (this.mAppIdleEnabled) {
      synchronized (this.mPackageAccessListeners) {
        Iterator it = this.mPackageAccessListeners.iterator();
        while (it.hasNext()) {
          ((AppStandbyInternal.AppIdleStateChangeListener) it.next())
              .triggerTemporaryQuotaBump(str, i);
        }
      }
    }
  }

  public void setChargingState(boolean z) {
    if (this.mIsCharging != z) {
      this.mIsCharging = z;
      postParoleStateChanged();
    }
  }

  public boolean isInParole() {
    return !this.mAppIdleEnabled || this.mIsCharging;
  }

  public final void postParoleStateChanged() {
    this.mHandler.removeMessages(9);
    this.mHandler.sendEmptyMessage(9);
  }

  public void postCheckIdleStates(int i) {
    if (i == -1) {
      postOneTimeCheckIdleStates();
      return;
    }
    synchronized (this.mPendingIdleStateChecks) {
      this.mPendingIdleStateChecks.put(i, this.mInjector.elapsedRealtime());
    }
    this.mHandler.obtainMessage(5).sendToTarget();
  }

  public void postOneTimeCheckIdleStates() {
    if (this.mInjector.getBootPhase() < 500) {
      this.mPendingOneTimeCheckIdleStates = true;
    } else {
      this.mHandler.sendEmptyMessage(10);
      this.mPendingOneTimeCheckIdleStates = false;
    }
  }

  public boolean checkIdleStates(int i) {
    if (!this.mAppIdleEnabled) {
      return false;
    }
    try {
      int[] runningUserIds = this.mInjector.getRunningUserIds();
      if (i != -1) {
        if (!ArrayUtils.contains(runningUserIds, i)) {
          return false;
        }
      }
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      for (int i2 : runningUserIds) {
        if (i == -1 || i == i2) {
          List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i2);
          int i3 = 0;
          for (int size = installedPackagesAsUser.size(); i3 < size; size = size) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i3);
            checkAndUpdateStandbyState(
                packageInfo.packageName, i2, packageInfo.applicationInfo.uid, elapsedRealtime);
            i3++;
          }
        }
      }
      return true;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public final void checkAndUpdateStandbyState(String str, int i, int i2, long j) {
    int packageUidAsUser;
    int i3;
    boolean z;
    int i4;
    int i5;
    String str2;
    int i6;
    boolean isIdle;
    boolean z2;
    boolean isIdle2;
    PackageManager packageManager;
    if (i2 <= 0) {
      try {
        packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, i);
      } catch (PackageManager.NameNotFoundException unused) {
        return;
      }
    } else {
      packageUidAsUser = i2;
    }
    if (CoreRune.SYSFW_APP_SPEG
        && (packageManager = this.mPackageManager) != null
        && packageManager.isSpeg(str, i)) {
      return;
    }
    int appMinBucket = getAppMinBucket(str, UserHandle.getAppId(packageUidAsUser), i);
    if (appMinBucket <= 10) {
      synchronized (this.mAppIdleLock) {
        isIdle2 = this.mAppIdleHistory.isIdle(str, i, j);
        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, appMinBucket, 256);
        isIdle = this.mAppIdleHistory.isIdle(str, i, j);
      }
      maybeInformListeners(str, i, j, appMinBucket, 256, false);
      z2 = isIdle2;
      i6 = i;
      str2 = str;
    } else {
      synchronized (this.mAppIdleLock) {
        boolean isIdle3 = this.mAppIdleHistory.isIdle(str, i, j);
        AppIdleHistory.AppUsageHistory appUsageHistory =
            this.mAppIdleHistory.getAppUsageHistory(str, i, j);
        int i7 = appUsageHistory.bucketingReason;
        int i8 = 65280 & i7;
        if (i8 == 1024) {
          return;
        }
        int i9 = appUsageHistory.currentBucket;
        if (i9 == 50) {
          return;
        }
        int max = Math.max(i9, 10);
        boolean predictionTimedOut = predictionTimedOut(appUsageHistory, j);
        if (i8 == 256 || i8 == 768 || i8 == 512 || predictionTimedOut) {
          if (!predictionTimedOut && (i3 = appUsageHistory.lastPredictedBucket) >= 10 && i3 <= 40) {
            i7 = 1281;
            max = i3;
          } else if (i8 != 256
              || (appUsageHistory.bucketingReason
                      & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                  != 2) {
            max = getBucketForLocked(str, i, j);
            i7 = 512;
          }
        }
        int i10 = i7;
        long elapsedTime = this.mAppIdleHistory.getElapsedTime(j);
        int minBucketWithValidExpiryTime =
            getMinBucketWithValidExpiryTime(appUsageHistory, max, elapsedTime);
        int i11 = max;
        if (minBucketWithValidExpiryTime != -1) {
          if (minBucketWithValidExpiryTime != 10
              && appUsageHistory.currentBucket != minBucketWithValidExpiryTime) {
            i4 = 775;
            z = isIdle3;
          }
          i4 = appUsageHistory.bucketingReason;
          z = isIdle3;
        } else {
          z = isIdle3;
          i4 = i10;
          minBucketWithValidExpiryTime = i11;
        }
        long j2 = appUsageHistory.lastUsedByUserElapsedTime;
        if (j2 < 0
            || appUsageHistory.lastRestrictAttemptElapsedTime <= j2
            || elapsedTime - j2 < this.mInjector.getAutoRestrictedBucketDelayMs()) {
          i5 = i4;
        } else {
          i5 = appUsageHistory.lastRestrictReason;
          minBucketWithValidExpiryTime = 45;
        }
        if (minBucketWithValidExpiryTime <= appMinBucket) {
          appMinBucket = minBucketWithValidExpiryTime;
        }
        if (i9 == appMinBucket && !predictionTimedOut) {
          str2 = str;
          i6 = i;
          isIdle = z;
          z2 = z;
        }
        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, appMinBucket, i5);
        str2 = str;
        i6 = i;
        isIdle = this.mAppIdleHistory.isIdle(str2, i6, j);
        maybeInformListeners(str, i, j, appMinBucket, i5, false);
        z2 = z;
      }
    }
    if (z2 != isIdle) {
      notifyBatteryStats(str2, i6, isIdle);
    }
  }

  public final boolean predictionTimedOut(AppIdleHistory.AppUsageHistory appUsageHistory, long j) {
    return appUsageHistory.lastPredictedTime > 0
        && this.mAppIdleHistory.getElapsedTime(j) - appUsageHistory.lastPredictedTime
            > this.mPredictionTimeoutMillis;
  }

  public final void maybeInformListeners(String str, int i, long j, int i2, int i3, boolean z) {
    synchronized (this.mAppIdleLock) {
      if (this.mAppIdleHistory.shouldInformListeners(str, i, j, i2)) {
        StandbyUpdateRecord obtain = StandbyUpdateRecord.obtain(str, i, i2, i3, z);
        AppStandbyHandler appStandbyHandler = this.mHandler;
        appStandbyHandler.sendMessage(appStandbyHandler.obtainMessage(3, obtain));
      }
    }
  }

  public final int getBucketForLocked(String str, int i, long j) {
    int thresholdIndex =
        this.mAppIdleHistory.getThresholdIndex(
            str, i, j, this.mAppStandbyScreenThresholds, this.mAppStandbyElapsedThresholds);
    if (thresholdIndex >= 0) {
      return THRESHOLD_BUCKETS[thresholdIndex];
    }
    return 50;
  }

  public final void notifyBatteryStats(String str, int i, boolean z) {
    try {
      int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, IInstalld.FLAG_FORCE, i);
      if (z) {
        this.mInjector.noteEvent(15, str, packageUidAsUser);
      } else {
        this.mInjector.noteEvent(16, str, packageUidAsUser);
      }
    } catch (PackageManager.NameNotFoundException | RemoteException unused) {
    }
  }

  @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
  public void onUsageEvent(int i, UsageEvents.Event event) {
    if (this.mAppIdleEnabled) {
      int eventType = event.getEventType();
      if (eventType == 1
          || eventType == 2
          || eventType == 6
          || eventType == 7
          || eventType == 10
          || eventType == 14
          || eventType == 13
          || eventType == 19) {
        String packageName = event.getPackageName();
        List crossProfileTargets = getCrossProfileTargets(packageName, i);
        synchronized (this.mAppIdleLock) {
          long elapsedRealtime = this.mInjector.elapsedRealtime();
          reportEventLocked(packageName, eventType, elapsedRealtime, i);
          int size = crossProfileTargets.size();
          for (int i2 = 0; i2 < size; i2++) {
            reportEventLocked(
                packageName,
                eventType,
                elapsedRealtime,
                ((UserHandle) crossProfileTargets.get(i2)).getIdentifier());
          }
        }
      }
    }
  }

  public final void reportEventLocked(String str, int i, long j, int i2) {
    int i3;
    int i4;
    long j2;
    AppIdleHistory.AppUsageHistory appUsageHistory;
    boolean z;
    int i5;
    String str2;
    int i6;
    long j3;
    boolean isIdle = this.mAppIdleHistory.isIdle(str, i2, j);
    AppIdleHistory.AppUsageHistory appUsageHistory2 =
        this.mAppIdleHistory.getAppUsageHistory(str, i2, j);
    int i7 = appUsageHistory2.currentBucket;
    int i8 = appUsageHistory2.bucketingReason;
    int usageEventToSubReason = usageEventToSubReason(i);
    int i9 =
        usageEventToSubReason
            | FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE;
    if (i == 10) {
      if (!this.mRetainNotificationSeenImpactForPreTApps || getTargetSdkVersion(str) >= 33) {
        if (this.mTriggerQuotaBumpOnNotificationSeen) {
          this.mHandler.obtainMessage(7, i2, -1, str).sendToTarget();
        }
        i6 = this.mNotificationSeenPromotedBucket;
        j3 = this.mNotificationSeenTimeoutMillis;
      } else {
        i6 = 20;
        j3 = 43200000;
      }
      long j4 = j3;
      i3 = i9;
      i4 = i8;
      this.mAppIdleHistory.reportUsage(
          appUsageHistory2, str, i2, i6, usageEventToSubReason, 0L, j + j4);
      j2 = j4;
    } else {
      i3 = i9;
      i4 = i8;
      if (i == 14) {
        this.mAppIdleHistory.reportUsage(
            appUsageHistory2,
            str,
            i2,
            20,
            usageEventToSubReason,
            0L,
            j + this.mSlicePinnedTimeoutMillis);
        j2 = this.mSlicePinnedTimeoutMillis;
      } else if (i == 6) {
        this.mAppIdleHistory.reportUsage(
            appUsageHistory2,
            str,
            i2,
            10,
            usageEventToSubReason,
            0L,
            j + this.mSystemInteractionTimeoutMillis);
        j2 = this.mSystemInteractionTimeoutMillis;
      } else if (i != 19) {
        this.mAppIdleHistory.reportUsage(
            appUsageHistory2,
            str,
            i2,
            10,
            usageEventToSubReason,
            j,
            j + this.mStrongUsageTimeoutMillis);
        j2 = this.mStrongUsageTimeoutMillis;
      } else {
        if (i7 != 50) {
          return;
        }
        this.mAppIdleHistory.reportUsage(
            appUsageHistory2,
            str,
            i2,
            10,
            usageEventToSubReason,
            0L,
            j + this.mInitialForegroundServiceStartTimeoutMillis);
        j2 = this.mInitialForegroundServiceStartTimeoutMillis;
      }
    }
    if (appUsageHistory2.currentBucket != i7) {
      AppStandbyHandler appStandbyHandler = this.mHandler;
      appStandbyHandler.sendMessageDelayed(appStandbyHandler.obtainMessage(11, i2, -1, str), j2);
      int i10 = appUsageHistory2.currentBucket;
      boolean z2 = i10 == 10 && (i4 & 65280) != 768;
      appUsageHistory = appUsageHistory2;
      z = isIdle;
      i5 = i2;
      str2 = str;
      maybeInformListeners(str, i2, j, i10, i3, z2);
    } else {
      appUsageHistory = appUsageHistory2;
      z = isIdle;
      i5 = i2;
      str2 = str;
    }
    boolean z3 = appUsageHistory.currentBucket >= 40;
    if (z != z3) {
      notifyBatteryStats(str2, i5, z3);
    }
  }

  public final int getTargetSdkVersion(String str) {
    return this.mInjector.getPackageManagerInternal().getPackageTargetSdkVersion(str);
  }

  public final int getMinBucketWithValidExpiryTime(
      AppIdleHistory.AppUsageHistory appUsageHistory, int i, long j) {
    SparseLongArray sparseLongArray = appUsageHistory.bucketExpiryTimesMs;
    if (sparseLongArray == null) {
      return -1;
    }
    int size = sparseLongArray.size();
    for (int i2 = 0; i2 < size; i2++) {
      int keyAt = appUsageHistory.bucketExpiryTimesMs.keyAt(i2);
      if (i <= keyAt) {
        break;
      }
      if (appUsageHistory.bucketExpiryTimesMs.valueAt(i2) > j) {
        return keyAt;
      }
    }
    return -1;
  }

  public final List getCrossProfileTargets(String str, int i) {
    synchronized (this.mAppIdleLock) {
      if (this.mLinkCrossProfileApps) {
        return this.mInjector.getValidCrossProfileTargets(str, i);
      }
      return Collections.emptyList();
    }
  }

  public void forceIdleState(String str, int i, boolean z) {
    int appId;
    int idle;
    if (this.mAppIdleEnabled && (appId = getAppId(str)) >= 0) {
      int appMinBucket = getAppMinBucket(str, appId, i);
      if (z && appMinBucket < 40) {
        Slog.e(
            "AppStandbyController",
            "Tried to force an app to be idle when its min bucket is "
                + UsageStatsManager.standbyBucketToString(appMinBucket));
        return;
      }
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      boolean isAppIdleFiltered = isAppIdleFiltered(str, appId, i, elapsedRealtime);
      synchronized (this.mAppIdleLock) {
        idle = this.mAppIdleHistory.setIdle(str, i, z, elapsedRealtime);
      }
      boolean isAppIdleFiltered2 = isAppIdleFiltered(str, appId, i, elapsedRealtime);
      maybeInformListeners(str, i, elapsedRealtime, idle, 1024, false);
      if (isAppIdleFiltered != isAppIdleFiltered2) {
        notifyBatteryStats(str, i, isAppIdleFiltered2);
      }
    }
  }

  public void setLastJobRunTime(String str, int i, long j) {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.setLastJobRunTime(str, i, j);
    }
  }

  public long getTimeSinceLastJobRun(String str, int i) {
    long timeSinceLastJobRun;
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    synchronized (this.mAppIdleLock) {
      timeSinceLastJobRun = this.mAppIdleHistory.getTimeSinceLastJobRun(str, i, elapsedRealtime);
    }
    return timeSinceLastJobRun;
  }

  public void setEstimatedLaunchTime(String str, int i, long j) {
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.setEstimatedLaunchTime(str, i, elapsedRealtime, j);
    }
  }

  public long getEstimatedLaunchTime(String str, int i) {
    long estimatedLaunchTime;
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    synchronized (this.mAppIdleLock) {
      estimatedLaunchTime = this.mAppIdleHistory.getEstimatedLaunchTime(str, i, elapsedRealtime);
    }
    return estimatedLaunchTime;
  }

  public long getTimeSinceLastUsedByUser(String str, int i) {
    long timeSinceLastUsedByUser;
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    synchronized (this.mAppIdleLock) {
      timeSinceLastUsedByUser =
          this.mAppIdleHistory.getTimeSinceLastUsedByUser(str, i, elapsedRealtime);
    }
    return timeSinceLastUsedByUser;
  }

  public void onUserRemoved(int i) {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.onUserRemoved(i);
      synchronized (this.mActiveAdminApps) {
        this.mActiveAdminApps.remove(i);
      }
      synchronized (this.mAdminProtectedPackages) {
        this.mAdminProtectedPackages.remove(i);
      }
    }
  }

  public final boolean isAppIdleUnfiltered(String str, int i, long j) {
    boolean isIdle;
    synchronized (this.mAppIdleLock) {
      isIdle = this.mAppIdleHistory.isIdle(str, i, j);
    }
    return isIdle;
  }

  public void addListener(
      AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
    synchronized (this.mPackageAccessListeners) {
      if (!this.mPackageAccessListeners.contains(appIdleStateChangeListener)) {
        this.mPackageAccessListeners.add(appIdleStateChangeListener);
      }
    }
  }

  public void removeListener(
      AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
    synchronized (this.mPackageAccessListeners) {
      this.mPackageAccessListeners.remove(appIdleStateChangeListener);
    }
  }

  public int getAppId(String str) {
    try {
      return this.mPackageManager.getApplicationInfo(str, 4194816).uid;
    } catch (PackageManager.NameNotFoundException unused) {
      return -1;
    }
  }

  public boolean isAppIdleFiltered(String str, int i, long j, boolean z) {
    if (z && this.mInjector.isPackageEphemeral(i, str)) {
      return false;
    }
    return isAppIdleFiltered(str, getAppId(str), i, j);
  }

  public final int getAppMinBucket(String str, int i) {
    try {
      return getAppMinBucket(
          str, UserHandle.getAppId(this.mPackageManager.getPackageUidAsUser(str, i)), i);
    } catch (PackageManager.NameNotFoundException unused) {
      return 50;
    }
  }

  public final int getAppMinBucket(String str, int i, int i2) {
    if (str == null) {
      return 50;
    }
    if (!this.mAppIdleEnabled || i < 10000 || str.equals("android")) {
      return 5;
    }
    if (this.mSystemServicesReady) {
      if (this.mInjector.isNonIdleWhitelisted(str)
          || isActiveDeviceAdmin(str, i2)
          || isAdminProtectedPackages(str, i2)
          || isActiveNetworkScorer(str)) {
        return 5;
      }
      int uid = UserHandle.getUid(i2, i);
      synchronized (this.mSystemExemptionAppOpMode) {
        if (this.mSystemExemptionAppOpMode.indexOfKey(uid) >= 0) {
          if (this.mSystemExemptionAppOpMode.get(uid) == 0) {
            return 5;
          }
        } else {
          int checkOpNoThrow = this.mAppOpsManager.checkOpNoThrow(128, uid, str);
          this.mSystemExemptionAppOpMode.put(uid, checkOpNoThrow);
          if (checkOpNoThrow == 0) {
            return 5;
          }
        }
        AppWidgetManager appWidgetManager = this.mAppWidgetManager;
        if (appWidgetManager != null
            && this.mInjector.isBoundWidgetPackage(appWidgetManager, str, i2)) {
          return 10;
        }
        if (isDeviceProvisioningPackage(str)) {
          return 5;
        }
        if (this.mInjector.isWellbeingPackage(str)
            || this.mInjector.shouldGetExactAlarmBucketElevation(str, UserHandle.getUid(i2, i))) {
          return 20;
        }
      }
    }
    if (isCarrierApp(str)) {
      return 5;
    }
    if (isHeadlessSystemApp(str)) {
      return 10;
    }
    return this.mPackageManager.checkPermission(
                "android.permission.ACCESS_BACKGROUND_LOCATION", str)
            == 0
        ? 30
        : 50;
  }

  public final boolean isHeadlessSystemApp(String str) {
    boolean contains;
    synchronized (this.mHeadlessSystemApps) {
      contains = this.mHeadlessSystemApps.contains(str);
    }
    return contains;
  }

  public boolean isAppIdleFiltered(String str, int i, int i2, long j) {
    return this.mAppIdleEnabled
        && !this.mIsCharging
        && isAppIdleUnfiltered(str, i2, j)
        && getAppMinBucket(str, i, i2) >= 40;
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x007d  */
  /* JADX WARN: Removed duplicated region for block: B:26:0x0083  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int[] getIdleUidsForUser(int i) {
    int i2;
    int i3;
    ApplicationInfo applicationInfo;
    boolean z;
    if (!this.mAppIdleEnabled) {
      return EmptyArray.INT;
    }
    Trace.traceBegin(64L, "getIdleUidsForUser");
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    List installedApplications =
        this.mInjector.getPackageManagerInternal().getInstalledApplications(0L, i, Process.myUid());
    if (installedApplications == null) {
      return EmptyArray.INT;
    }
    SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
    int size = installedApplications.size() - 1;
    int i4 = 0;
    while (size >= 0) {
      ApplicationInfo applicationInfo2 = (ApplicationInfo) installedApplications.get(size);
      int indexOfKey = sparseBooleanArray.indexOfKey(applicationInfo2.uid);
      boolean valueAt = indexOfKey < 0 ? true : sparseBooleanArray.valueAt(indexOfKey);
      if (valueAt) {
        i2 = indexOfKey;
        i3 = size;
        applicationInfo = applicationInfo2;
        if (isAppIdleFiltered(
            applicationInfo2.packageName,
            UserHandle.getAppId(applicationInfo2.uid),
            i,
            elapsedRealtime)) {
          z = true;
          if (valueAt && !z) {
            i4++;
          }
          if (i2 >= 0) {
            sparseBooleanArray.put(applicationInfo.uid, z);
          } else {
            sparseBooleanArray.setValueAt(i2, z);
          }
          size = i3 - 1;
        }
      } else {
        i2 = indexOfKey;
        i3 = size;
        applicationInfo = applicationInfo2;
      }
      z = false;
      if (valueAt) {
        i4++;
      }
      if (i2 >= 0) {}
      size = i3 - 1;
    }
    int size2 = sparseBooleanArray.size() - i4;
    int[] iArr = new int[size2];
    for (int size3 = sparseBooleanArray.size() - 1; size3 >= 0; size3--) {
      if (sparseBooleanArray.valueAt(size3)) {
        size2--;
        iArr[size2] = sparseBooleanArray.keyAt(size3);
      }
    }
    Trace.traceEnd(64L);
    return iArr;
  }

  public void setAppIdleAsync(String str, boolean z, int i) {
    if (str == null || !this.mAppIdleEnabled) {
      return;
    }
    this.mHandler.obtainMessage(4, i, z ? 1 : 0, str).sendToTarget();
  }

  public int getAppStandbyBucket(String str, int i, long j, boolean z) {
    int appStandbyBucket;
    if (!this.mAppIdleEnabled) {
      return 5;
    }
    if (z && this.mInjector.isPackageEphemeral(i, str)) {
      return 10;
    }
    synchronized (this.mAppIdleLock) {
      appStandbyBucket = this.mAppIdleHistory.getAppStandbyBucket(str, i, j);
    }
    return appStandbyBucket;
  }

  public int getAppStandbyBucketReason(String str, int i, long j) {
    int appStandbyReason;
    synchronized (this.mAppIdleLock) {
      appStandbyReason = this.mAppIdleHistory.getAppStandbyReason(str, i, j);
    }
    return appStandbyReason;
  }

  public List getAppStandbyBuckets(int i) {
    ArrayList appStandbyBuckets;
    synchronized (this.mAppIdleLock) {
      appStandbyBuckets = this.mAppIdleHistory.getAppStandbyBuckets(i, this.mAppIdleEnabled);
    }
    return appStandbyBuckets;
  }

  public int getAppMinStandbyBucket(String str, int i, int i2, boolean z) {
    int appMinBucket;
    if (z && this.mInjector.isPackageEphemeral(i2, str)) {
      return 50;
    }
    synchronized (this.mAppIdleLock) {
      appMinBucket = getAppMinBucket(str, i, i2);
    }
    return appMinBucket;
  }

  public void restrictApp(String str, int i, int i2) {
    restrictApp(
        str,
        i,
        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
        i2);
  }

  public void restrictApp(String str, int i, int i2, int i3) {
    if (i2 != 1536 && i2 != 1024) {
      Slog.e("AppStandbyController", "Tried to restrict app " + str + " for an unsupported reason");
      return;
    }
    if (!this.mInjector.isPackageInstalled(str, 0, i)) {
      Slog.e("AppStandbyController", "Tried to restrict uninstalled app: " + str);
      return;
    }
    setAppStandbyBucket(
        str,
        i,
        45,
        (i2 & 65280) | (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
        this.mInjector.elapsedRealtime(),
        false);
  }

  public void restoreAppsToRare(Set set, final int i) {
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    Iterator it = set.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      if (!this.mInjector.isPackageInstalled(str, 0, i)) {
        Slog.i("AppStandbyController", "Tried to restore bucket for uninstalled app: " + str);
        this.mAppsToRestoreToRare.add(i, str);
      } else {
        restoreAppToRare(str, i, elapsedRealtime, 258);
      }
    }
    this.mHandler.postDelayed(
        new Runnable() { // from class:
                         // com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            AppStandbyController.this.lambda$restoreAppsToRare$0(i);
          }
        },
        28800000L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restoreAppsToRare$0(int i) {
    this.mAppsToRestoreToRare.remove(i);
  }

  public final void restoreAppToRare(String str, int i, long j, int i2) {
    if (getAppStandbyBucket(str, i, j, false) == 50) {
      setAppStandbyBucket(str, i, 40, i2, j, false);
    }
  }

  public void setAppStandbyBucket(String str, int i, int i2, int i3, int i4) {
    setAppStandbyBuckets(Collections.singletonList(new AppStandbyInfo(str, i)), i2, i3, i4);
  }

  public void setAppStandbyBucketForMARs(String str, int i, int i2, int i3, boolean z, boolean z2) {
    int i4;
    int i5 = i2;
    if (i3 != 1792) {
      return;
    }
    if (i5 < 10 || i5 > 50) {
      throw new IllegalArgumentException("Cannot set the standby bucket to " + i5);
    }
    if (!this.mInjector.isPackageInstalled(str, 0, i)) {
      Slog.e("AppStandbyController", "Tried to restrict uninstalled app: " + str);
      return;
    }
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    synchronized (this.mAppIdleLock) {
      AppIdleHistory.AppUsageHistory appUsageHistory =
          this.mAppIdleHistory.getAppUsageHistory(str, i, elapsedRealtime);
      if (!z2) {
        if ((appUsageHistory.bucketingReason & 65280) == 1792) {
          i5 = 45;
        }
      }
      i4 = i5;
      this.mAppIdleHistory.setAppStandbyBucket(str, i, elapsedRealtime, i4, i3, z);
    }
    maybeInformListeners(str, i, elapsedRealtime, i4, i3, false);
  }

  public void setAppStandbyBuckets(List list, int i, int i2, int i3) {
    int i4;
    int handleIncomingUser =
        ActivityManager.handleIncomingUser(i3, i2, i, false, true, "setAppStandbyBucket", null);
    boolean z = i2 == 0 || i2 == 2000;
    if ((!UserHandle.isSameApp(i2, 1000) || i3 == Process.myPid()) && !z) {
      i4 =
          UserHandle.isCore(i2)
              ? FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM
              : 1280;
    } else {
      i4 = 1024;
    }
    int i5 = i4;
    int size = list.size();
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    for (int i6 = 0; i6 < size; i6++) {
      AppStandbyInfo appStandbyInfo = (AppStandbyInfo) list.get(i6);
      String str = appStandbyInfo.mPackageName;
      int i7 = appStandbyInfo.mStandbyBucket;
      if (i7 < 10 || i7 > 50) {
        throw new IllegalArgumentException("Cannot set the standby bucket to " + i7);
      }
      int packageUid =
          this.mInjector
              .getPackageManagerInternal()
              .getPackageUid(str, 4980736L, handleIncomingUser);
      if (packageUid == i2) {
        throw new IllegalArgumentException("Cannot set your own standby bucket");
      }
      if (packageUid < 0) {
        throw new IllegalArgumentException(
            "Cannot set standby bucket for non existent package (" + str + ")");
      }
      setAppStandbyBucket(str, handleIncomingUser, i7, i5, elapsedRealtime, z);
    }
  }

  public void setAppStandbyBucketsForMARs(List list, int i, int i2, int i3, boolean z, boolean z2) {
    if (i3 != 1792) {
      return;
    }
    int size = list.size();
    for (int i4 = 0; i4 < size; i4++) {
      setAppStandbyBucketForMARs(((AppStandbyInfo) list.get(i4)).mPackageName, i, i2, i3, z, z2);
    }
  }

  public void setAppStandbyBucket(String str, int i, int i2, int i3) {
    setAppStandbyBucket(str, i, i2, i3, this.mInjector.elapsedRealtime(), false);
  }

  /* JADX WARN: Removed duplicated region for block: B:103:0x01ec  */
  /* JADX WARN: Removed duplicated region for block: B:104:0x01d2  */
  /* JADX WARN: Removed duplicated region for block: B:94:0x01cf  */
  /* JADX WARN: Removed duplicated region for block: B:97:0x01e9  */
  /* JADX WARN: Removed duplicated region for block: B:99:0x01f0 A[Catch: all -> 0x0204, TryCatch #0 {, blocks: (B:8:0x0014, B:10:0x001d, B:11:0x0033, B:13:0x0035, B:16:0x004b, B:18:0x0051, B:23:0x005d, B:28:0x006d, B:33:0x0077, B:43:0x008b, B:44:0x009c, B:47:0x00af, B:51:0x00c7, B:52:0x00ca, B:62:0x00da, B:66:0x00e4, B:68:0x00e6, B:71:0x00ee, B:75:0x00f5, B:77:0x0109, B:79:0x010d, B:81:0x0112, B:83:0x018e, B:87:0x01a3, B:92:0x01c1, B:95:0x01d3, B:99:0x01f0, B:100:0x01f3, B:105:0x01ab, B:108:0x01b4, B:112:0x0134, B:113:0x014c, B:115:0x015d, B:116:0x0186, B:123:0x005b), top: B:7:0x0014 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setAppStandbyBucket(String str, int i, int i2, int i3, long j, boolean z) {
    int i4;
    int i5;
    boolean z2;
    int i6;
    boolean z3;
    boolean z4;
    int i7;
    int i8;
    if (this.mAppIdleEnabled) {
      synchronized (this.mAppIdleLock) {
        if (!this.mInjector.isPackageInstalled(str, 0, i)) {
          Slog.e("AppStandbyController", "Tried to set bucket of uninstalled app: " + str);
          return;
        }
        AppIdleHistory.AppUsageHistory appUsageHistory =
            this.mAppIdleHistory.getAppUsageHistory(str, i, j);
        int i9 = i3 & 65280;
        boolean z5 = i9 == 1280;
        int i10 = appUsageHistory.currentBucket;
        if (i10 < 10) {
          return;
        }
        if ((i10 == 50 || i2 == 50) && z5) {
          return;
        }
        int i11 = appUsageHistory.bucketingReason;
        int i12 = i11 & 65280;
        int i13 = FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM;
        boolean z6 = i12 == 1536;
        if (z5) {
          if ((i11 & 65280) != 1024 && !z6) {
            i13 = FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM;
          }
          return;
        }
        boolean z7 = i9 == i13;
        if (i10 == i2 && z6 && z7) {
          if (i2 == 45) {
            i8 = 40;
            this.mAppIdleHistory.noteRestrictionAttempt(str, i, j, i3);
          } else {
            i8 = 40;
          }
          int i14 =
              (appUsageHistory.bucketingReason
                      & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                  | FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM
                  | (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
          boolean z8 = appUsageHistory.currentBucket >= i8;
          this.mAppIdleHistory.setAppStandbyBucket(str, i, j, i2, i14, z);
          boolean z9 = i2 >= i8;
          if (z8 != z9) {
            notifyBatteryStats(str, i, z9);
          }
          return;
        }
        boolean z10 = i9 == 1024;
        if (i10 == 45) {
          if ((65280 & i11) == 512) {
            if (z5 && i2 >= 40) {
              return;
            }
          } else if (!isUserUsage(i3) && !z10) {
            return;
          }
        }
        if (i2 == 45) {
          i4 = -1;
          i5 = 512;
          this.mAppIdleHistory.noteRestrictionAttempt(str, i, j, i3);
          if (z10) {
            if (Build.IS_DEBUGGABLE
                && (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) != 2) {
              z2 = false;
              Toast.makeText(
                      this.mContext,
                      this.mHandler.getLooper(),
                      this.mContext
                          .getResources()
                          .getString(R.string.config_batterymeterBoltPath, str),
                      0)
                  .show();
            } else {
              z2 = false;
              Slog.i("AppStandbyController", str + " restricted by user");
            }
          } else {
            z2 = false;
            long autoRestrictedBucketDelayMs =
                (appUsageHistory.lastUsedByUserElapsedTime
                        + this.mInjector.getAutoRestrictedBucketDelayMs())
                    - j;
            if (autoRestrictedBucketDelayMs > 0) {
              Slog.w(
                  "AppStandbyController",
                  "Tried to restrict recently used app: " + str + " due to " + i3);
              AppStandbyHandler appStandbyHandler = this.mHandler;
              appStandbyHandler.sendMessageDelayed(
                  appStandbyHandler.obtainMessage(11, i, -1, str), autoRestrictedBucketDelayMs);
              return;
            }
          }
        } else {
          i4 = -1;
          i5 = 512;
          z2 = false;
        }
        if (z5) {
          long elapsedTime = this.mAppIdleHistory.getElapsedTime(j);
          this.mAppIdleHistory.updateLastPrediction(appUsageHistory, elapsedTime, i2);
          i6 = getMinBucketWithValidExpiryTime(appUsageHistory, i2, elapsedTime);
          if (i6 != i4) {
            if (i6 != 10 && appUsageHistory.currentBucket != i6) {
              i7 = 775;
              i5 = i7;
            }
            i7 = appUsageHistory.bucketingReason;
            i5 = i7;
          } else if (i2 == 40 && getBucketForLocked(str, i, j) == 45) {
            i6 = 45;
          }
          int min = Math.min(i6, getAppMinBucket(str, i));
          z3 = appUsageHistory.currentBucket < 40 ? true : z2;
          boolean z11 = z2;
          this.mAppIdleHistory.setAppStandbyBucket(str, i, j, min, i5, z);
          z4 = min < 40 ? true : z11;
          if (z3 != z4) {
            notifyBatteryStats(str, i, z4);
          }
          maybeInformListeners(str, i, j, min, i5, false);
        }
        i5 = i3;
        i6 = i2;
        int min2 = Math.min(i6, getAppMinBucket(str, i));
        if (appUsageHistory.currentBucket < 40) {}
        boolean z112 = z2;
        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, min2, i5, z);
        if (min2 < 40) {}
        if (z3 != z4) {}
        maybeInformListeners(str, i, j, min2, i5, false);
      }
    }
  }

  public boolean isActiveDeviceAdmin(String str, int i) {
    boolean z;
    synchronized (this.mActiveAdminApps) {
      Set set = (Set) this.mActiveAdminApps.get(i);
      z = set != null && set.contains(str);
    }
    return z;
  }

  public final boolean isAdminProtectedPackages(String str, int i) {
    synchronized (this.mAdminProtectedPackages) {
      boolean z = true;
      if (this.mAdminProtectedPackages.contains(-1)
          && ((Set) this.mAdminProtectedPackages.get(-1)).contains(str)) {
        return true;
      }
      if (!this.mAdminProtectedPackages.contains(i)
          || !((Set) this.mAdminProtectedPackages.get(i)).contains(str)) {
        z = false;
      }
      return z;
    }
  }

  public void addActiveDeviceAdmin(String str, int i) {
    synchronized (this.mActiveAdminApps) {
      Set set = (Set) this.mActiveAdminApps.get(i);
      if (set == null) {
        set = new ArraySet();
        this.mActiveAdminApps.put(i, set);
      }
      set.add(str);
    }
  }

  public void setActiveAdminApps(Set set, int i) {
    synchronized (this.mActiveAdminApps) {
      if (set == null) {
        this.mActiveAdminApps.remove(i);
      } else {
        this.mActiveAdminApps.put(i, set);
      }
    }
  }

  public void setAdminProtectedPackages(Set set, int i) {
    synchronized (this.mAdminProtectedPackages) {
      if (set != null) {
        if (!set.isEmpty()) {
          this.mAdminProtectedPackages.put(i, set);
        }
      }
      this.mAdminProtectedPackages.remove(i);
    }
  }

  public void onAdminDataAvailable() {
    this.mAdminDataAvailableLatch.countDown();
  }

  public final void waitForAdminData() {
    if (this.mContext.getPackageManager().hasSystemFeature("android.software.device_admin")) {
      ConcurrentUtils.waitForCountDownNoInterrupt(
          this.mAdminDataAvailableLatch, 10000L, "Wait for admin data");
    }
  }

  public Set getActiveAdminAppsForTest(int i) {
    Set set;
    synchronized (this.mActiveAdminApps) {
      set = (Set) this.mActiveAdminApps.get(i);
    }
    return set;
  }

  public Set getAdminProtectedPackagesForTest(int i) {
    Set set;
    synchronized (this.mAdminProtectedPackages) {
      set = (Set) this.mAdminProtectedPackages.get(i);
    }
    return set;
  }

  public final boolean isDeviceProvisioningPackage(String str) {
    if (this.mCachedDeviceProvisioningPackage == null) {
      this.mCachedDeviceProvisioningPackage =
          this.mContext.getResources().getString(R.string.default_notification_channel_label);
    }
    return this.mCachedDeviceProvisioningPackage.equals(str);
  }

  public final boolean isCarrierApp(String str) {
    synchronized (this.mCarrierPrivilegedLock) {
      if (!this.mHaveCarrierPrivilegedApps) {
        fetchCarrierPrivilegedAppsCPL();
      }
      List list = this.mCarrierPrivilegedApps;
      if (list == null) {
        return false;
      }
      return list.contains(str);
    }
  }

  public void clearCarrierPrivilegedApps() {
    synchronized (this.mCarrierPrivilegedLock) {
      this.mHaveCarrierPrivilegedApps = false;
      this.mCarrierPrivilegedApps = null;
    }
  }

  public final void fetchCarrierPrivilegedAppsCPL() {
    this.mCarrierPrivilegedApps =
        ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
            .getCarrierPrivilegedPackagesForAllActiveSubscriptions();
    this.mHaveCarrierPrivilegedApps = true;
  }

  public final boolean isActiveNetworkScorer(String str) {
    long elapsedRealtime = SystemClock.elapsedRealtime();
    if (this.mCachedNetworkScorer == null
        || this.mCachedNetworkScorerAtMillis < elapsedRealtime - 5000) {
      this.mCachedNetworkScorer = this.mInjector.getActiveNetworkScorer();
      this.mCachedNetworkScorerAtMillis = elapsedRealtime;
    }
    return str.equals(this.mCachedNetworkScorer);
  }

  public final void informListeners(String str, int i, int i2, int i3, boolean z) {
    boolean z2 = i2 >= 40;
    synchronized (this.mPackageAccessListeners) {
      Iterator it = this.mPackageAccessListeners.iterator();
      while (it.hasNext()) {
        AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener =
            (AppStandbyInternal.AppIdleStateChangeListener) it.next();
        appIdleStateChangeListener.onAppIdleStateChanged(str, i, z2, i2, i3);
        if (z) {
          appIdleStateChangeListener.onUserInteractionStarted(str, i);
        }
      }
    }
  }

  public final void informParoleStateChanged() {
    boolean isInParole = isInParole();
    synchronized (this.mPackageAccessListeners) {
      Iterator it = this.mPackageAccessListeners.iterator();
      while (it.hasNext()) {
        ((AppStandbyInternal.AppIdleStateChangeListener) it.next())
            .onParoleStateChanged(isInParole);
      }
    }
  }

  public long getBroadcastResponseWindowDurationMs() {
    return this.mBroadcastResponseWindowDurationMillis;
  }

  public int getBroadcastResponseFgThresholdState() {
    return this.mBroadcastResponseFgThresholdState;
  }

  public long getBroadcastSessionsDurationMs() {
    return this.mBroadcastSessionsDurationMs;
  }

  public long getBroadcastSessionsWithResponseDurationMs() {
    return this.mBroadcastSessionsWithResponseDurationMs;
  }

  public boolean shouldNoteResponseEventForAllBroadcastSessions() {
    return this.mNoteResponseEventForAllBroadcastSessions;
  }

  public List getBroadcastResponseExemptedRoles() {
    return this.mBroadcastResponseExemptedRolesList;
  }

  public List getBroadcastResponseExemptedPermissions() {
    return this.mBroadcastResponseExemptedPermissionsList;
  }

  public String getAppStandbyConstant(String str) {
    return (String) this.mAppStandbyProperties.get(str);
  }

  public void clearLastUsedTimestampsForTest(String str, int i) {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.clearLastUsedTimestamps(str, i);
    }
  }

  public void flushToDisk() {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.writeAppIdleTimes(this.mInjector.elapsedRealtime());
      this.mAppIdleHistory.writeAppIdleDurations();
    }
  }

  public final boolean isDisplayOn() {
    return this.mInjector.isDefaultDisplayOn();
  }

  public void clearAppIdleForPackage(String str, int i) {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.clearUsage(str, i);
    }
  }

  public void maybeUnrestrictBuggyApp(String str, int i) {
    maybeUnrestrictApp(
        str,
        i,
        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
        4,
        256,
        1);
  }

  public void maybeUnrestrictApp(String str, int i, int i2, int i3, int i4, int i5) {
    int i6;
    synchronized (this.mAppIdleLock) {
      long elapsedRealtime = this.mInjector.elapsedRealtime();
      AppIdleHistory.AppUsageHistory appUsageHistory =
          this.mAppIdleHistory.getAppUsageHistory(str, i, elapsedRealtime);
      int i7 = 45;
      if (appUsageHistory.currentBucket == 45) {
        int i8 = appUsageHistory.bucketingReason;
        if ((65280 & i8) == i2) {
          if ((i8 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) == i3) {
            i6 = i4 | i5;
            i7 = 40;
          } else {
            i6 = (~i3) & i8;
          }
          this.mAppIdleHistory.setAppStandbyBucket(str, i, elapsedRealtime, i7, i6);
          maybeInformListeners(str, i, elapsedRealtime, i7, i6, false);
        }
      }
    }
  }

  public final void updatePowerWhitelistCache() {
    if (this.mInjector.getBootPhase() < 500) {
      return;
    }
    this.mInjector.updatePowerWhitelistCache();
    postCheckIdleStates(-1);
  }

  public class PackageReceiver extends BroadcastReceiver {
    public PackageReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
      int sendingUserId = getSendingUserId();
      if ("android.intent.action.PACKAGE_ADDED".equals(action)
          || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
        String[] stringArrayExtra =
            intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
        if (stringArrayExtra == null
            || (stringArrayExtra.length == 1 && schemeSpecificPart.equals(stringArrayExtra[0]))) {
          AppStandbyController.this.clearCarrierPrivilegedApps();
          AppStandbyController.this.evaluateSystemAppException(schemeSpecificPart, sendingUserId);
        }
        if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
          AppStandbyController.this
              .mHandler
              .obtainMessage(11, sendingUserId, -1, schemeSpecificPart)
              .sendToTarget();
        }
      }
      if ("android.intent.action.PACKAGE_REMOVED".equals(action)
          || "android.intent.action.PACKAGE_ADDED".equals(action)) {
        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
          AppStandbyController.this.maybeUnrestrictBuggyApp(schemeSpecificPart, sendingUserId);
        } else if (!"android.intent.action.PACKAGE_ADDED".equals(action)) {
          AppStandbyController.this.clearAppIdleForPackage(schemeSpecificPart, sendingUserId);
        } else if (AppStandbyController.this.mAppsToRestoreToRare.contains(
            sendingUserId, schemeSpecificPart)) {
          AppStandbyController appStandbyController = AppStandbyController.this;
          appStandbyController.restoreAppToRare(
              schemeSpecificPart,
              sendingUserId,
              appStandbyController.mInjector.elapsedRealtime(),
              258);
          AppStandbyController.this.mAppsToRestoreToRare.remove(sendingUserId, schemeSpecificPart);
        }
      }
      synchronized (AppStandbyController.this.mSystemExemptionAppOpMode) {
        if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
          AppStandbyController.this.mSystemExemptionAppOpMode.delete(
              UserHandle.getUid(
                  sendingUserId, AppStandbyController.this.getAppId(schemeSpecificPart)));
        }
      }
    }
  }

  public final void evaluateSystemAppException(String str, int i) {
    if (this.mSystemServicesReady) {
      try {
        maybeUpdateHeadlessSystemAppCache(
            this.mPackageManager.getPackageInfoAsUser(str, 1835520, i));
      } catch (PackageManager.NameNotFoundException unused) {
        synchronized (this.mHeadlessSystemApps) {
          this.mHeadlessSystemApps.remove(str);
        }
      }
    }
  }

  public final boolean maybeUpdateHeadlessSystemAppCache(PackageInfo packageInfo) {
    ApplicationInfo applicationInfo;
    if (packageInfo == null
        || (applicationInfo = packageInfo.applicationInfo) == null
        || !(applicationInfo.isSystemApp() || packageInfo.applicationInfo.isUpdatedSystemApp())) {
      return false;
    }
    return updateHeadlessSystemAppCache(
        packageInfo.packageName,
        ArrayUtils.isEmpty(
            this.mPackageManager.queryIntentActivitiesAsUser(
                new Intent("android.intent.action.MAIN")
                    .addCategory("android.intent.category.LAUNCHER")
                    .setPackage(packageInfo.packageName),
                1835520,
                0)));
  }

  public final boolean updateHeadlessSystemAppCache(String str, boolean z) {
    synchronized (this.mHeadlessSystemApps) {
      if (z) {
        return this.mHeadlessSystemApps.add(str);
      }
      return this.mHeadlessSystemApps.remove(str);
    }
  }

  public void initializeDefaultsForSystemApps(int i) {
    int i2;
    if (!this.mSystemServicesReady) {
      this.mPendingInitializeDefaults = true;
      return;
    }
    Slog.d(
        "AppStandbyController",
        "Initializing defaults for system apps on user "
            + i
            + ", appIdleEnabled="
            + this.mAppIdleEnabled);
    long elapsedRealtime = this.mInjector.elapsedRealtime();
    List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i);
    int size = installedPackagesAsUser.size();
    synchronized (this.mAppIdleLock) {
      int i3 = 0;
      while (i3 < size) {
        PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i3);
        String str = packageInfo.packageName;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null || !applicationInfo.isSystemApp()) {
          i2 = i3;
        } else {
          i2 = i3;
          this.mAppIdleHistory.reportUsage(
              str, i, 10, 6, 0L, elapsedRealtime + this.mSystemUpdateUsageTimeoutMillis);
        }
        i3 = i2 + 1;
      }
      this.mAppIdleHistory.writeAppIdleTimes(i, elapsedRealtime);
    }
  }

  public final Set getSystemPackagesWithLauncherActivities() {
    List queryIntentActivitiesAsUser =
        this.mPackageManager.queryIntentActivitiesAsUser(
            new Intent("android.intent.action.MAIN")
                .addCategory("android.intent.category.LAUNCHER"),
            1835520,
            0);
    ArraySet arraySet = new ArraySet();
    Iterator it = queryIntentActivitiesAsUser.iterator();
    while (it.hasNext()) {
      arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
    }
    return arraySet;
  }

  public final void loadHeadlessSystemAppCache() {
    long uptimeMillis = SystemClock.uptimeMillis();
    List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(1835520, 0);
    Set systemPackagesWithLauncherActivities = getSystemPackagesWithLauncherActivities();
    int size = installedPackagesAsUser.size();
    for (int i = 0; i < size; i++) {
      PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i);
      if (packageInfo != null) {
        String str = packageInfo.packageName;
        if (updateHeadlessSystemAppCache(
            str, !systemPackagesWithLauncherActivities.contains(str))) {
          this.mHandler.obtainMessage(11, 0, -1, str).sendToTarget();
        }
      }
    }
    Slog.d(
        "AppStandbyController",
        "Loaded headless system app cache in "
            + (SystemClock.uptimeMillis() - uptimeMillis)
            + " ms: appIdleEnabled="
            + this.mAppIdleEnabled);
  }

  public void postReportContentProviderUsage(String str, String str2, int i) {
    this.mHandler.obtainMessage(8, ContentProviderUsageRecord.obtain(str, str2, i)).sendToTarget();
  }

  public void postReportSyncScheduled(String str, int i, boolean z) {
    this.mHandler.obtainMessage(12, i, z ? 1 : 0, str).sendToTarget();
  }

  public void postReportExemptedSyncStart(String str, int i) {
    this.mHandler.obtainMessage(13, i, 0, str).sendToTarget();
  }

  public AppIdleHistory getAppIdleHistoryForTest() {
    AppIdleHistory appIdleHistory;
    synchronized (this.mAppIdleLock) {
      appIdleHistory = this.mAppIdleHistory;
    }
    return appIdleHistory;
  }

  public void dumpUsers(IndentingPrintWriter indentingPrintWriter, int[] iArr, List list) {
    synchronized (this.mAppIdleLock) {
      this.mAppIdleHistory.dumpUsers(indentingPrintWriter, iArr, list);
    }
  }

  public void dumpState(String[] strArr, PrintWriter printWriter) {
    synchronized (this.mCarrierPrivilegedLock) {
      printWriter.println(
          "Carrier privileged apps (have="
              + this.mHaveCarrierPrivilegedApps
              + "): "
              + this.mCarrierPrivilegedApps);
    }
    printWriter.println();
    printWriter.println("Settings:");
    printWriter.print("  mCheckIdleIntervalMillis=");
    TimeUtils.formatDuration(this.mCheckIdleIntervalMillis, printWriter);
    printWriter.println();
    printWriter.print("  mStrongUsageTimeoutMillis=");
    TimeUtils.formatDuration(this.mStrongUsageTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mNotificationSeenTimeoutMillis=");
    TimeUtils.formatDuration(this.mNotificationSeenTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mNotificationSeenPromotedBucket=");
    printWriter.print(
        UsageStatsManager.standbyBucketToString(this.mNotificationSeenPromotedBucket));
    printWriter.println();
    printWriter.print("  mTriggerQuotaBumpOnNotificationSeen=");
    printWriter.print(this.mTriggerQuotaBumpOnNotificationSeen);
    printWriter.println();
    printWriter.print("  mRetainNotificationSeenImpactForPreTApps=");
    printWriter.print(this.mRetainNotificationSeenImpactForPreTApps);
    printWriter.println();
    printWriter.print("  mSlicePinnedTimeoutMillis=");
    TimeUtils.formatDuration(this.mSlicePinnedTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mSyncAdapterTimeoutMillis=");
    TimeUtils.formatDuration(this.mSyncAdapterTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mSystemInteractionTimeoutMillis=");
    TimeUtils.formatDuration(this.mSystemInteractionTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mInitialForegroundServiceStartTimeoutMillis=");
    TimeUtils.formatDuration(this.mInitialForegroundServiceStartTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mPredictionTimeoutMillis=");
    TimeUtils.formatDuration(this.mPredictionTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mExemptedSyncScheduledNonDozeTimeoutMillis=");
    TimeUtils.formatDuration(this.mExemptedSyncScheduledNonDozeTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mExemptedSyncScheduledDozeTimeoutMillis=");
    TimeUtils.formatDuration(this.mExemptedSyncScheduledDozeTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mExemptedSyncStartTimeoutMillis=");
    TimeUtils.formatDuration(this.mExemptedSyncStartTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mUnexemptedSyncScheduledTimeoutMillis=");
    TimeUtils.formatDuration(this.mUnexemptedSyncScheduledTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mSystemUpdateUsageTimeoutMillis=");
    TimeUtils.formatDuration(this.mSystemUpdateUsageTimeoutMillis, printWriter);
    printWriter.println();
    printWriter.print("  mBroadcastResponseWindowDurationMillis=");
    TimeUtils.formatDuration(this.mBroadcastResponseWindowDurationMillis, printWriter);
    printWriter.println();
    printWriter.print("  mBroadcastResponseFgThresholdState=");
    printWriter.print(ActivityManager.procStateToString(this.mBroadcastResponseFgThresholdState));
    printWriter.println();
    printWriter.print("  mBroadcastSessionsDurationMs=");
    TimeUtils.formatDuration(this.mBroadcastSessionsDurationMs, printWriter);
    printWriter.println();
    printWriter.print("  mBroadcastSessionsWithResponseDurationMs=");
    TimeUtils.formatDuration(this.mBroadcastSessionsWithResponseDurationMs, printWriter);
    printWriter.println();
    printWriter.print("  mNoteResponseEventForAllBroadcastSessions=");
    printWriter.print(this.mNoteResponseEventForAllBroadcastSessions);
    printWriter.println();
    printWriter.print("  mBroadcastResponseExemptedRoles=");
    printWriter.print(this.mBroadcastResponseExemptedRoles);
    printWriter.println();
    printWriter.print("  mBroadcastResponseExemptedPermissions=");
    printWriter.print(this.mBroadcastResponseExemptedPermissions);
    printWriter.println();
    printWriter.println();
    printWriter.print("mAppIdleEnabled=");
    printWriter.print(this.mAppIdleEnabled);
    printWriter.print(" mIsCharging=");
    printWriter.print(this.mIsCharging);
    printWriter.println();
    printWriter.print("mScreenThresholds=");
    printWriter.println(Arrays.toString(this.mAppStandbyScreenThresholds));
    printWriter.print("mElapsedThresholds=");
    printWriter.println(Arrays.toString(this.mAppStandbyElapsedThresholds));
    printWriter.println();
    printWriter.println("mHeadlessSystemApps=[");
    synchronized (this.mHeadlessSystemApps) {
      for (int size = this.mHeadlessSystemApps.size() - 1; size >= 0; size--) {
        printWriter.print("  ");
        printWriter.print((String) this.mHeadlessSystemApps.valueAt(size));
        if (size != 0) {
          printWriter.println(",");
        }
      }
    }
    printWriter.println("]");
    printWriter.println();
    printWriter.println("mSystemPackagesAppIds=[");
    synchronized (this.mSystemPackagesAppIds) {
      for (int size2 = this.mSystemPackagesAppIds.size() - 1; size2 >= 0; size2--) {
        printWriter.print("  ");
        printWriter.print(this.mSystemPackagesAppIds.get(size2));
        if (size2 != 0) {
          printWriter.println(",");
        }
      }
    }
    printWriter.println("]");
    printWriter.println();
    this.mInjector.dump(printWriter);
  }

  public class Injector {
    public AlarmManagerInternal mAlarmManagerInternal;
    public BatteryManager mBatteryManager;
    public IBatteryStats mBatteryStats;
    public int mBootPhase;
    public final Context mContext;
    public CrossProfileAppsInternal mCrossProfileAppsInternal;
    public IDeviceIdleController mDeviceIdleController;
    public DisplayManager mDisplayManager;
    public final Looper mLooper;
    public PackageManagerInternal mPackageManagerInternal;
    public PowerManager mPowerManager;
    public long mAutoRestrictedBucketDelayMs = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    public final ArraySet mPowerWhitelistedApps = new ArraySet();
    public String mWellbeingApp = null;

    public Injector(Context context, Looper looper) {
      this.mContext = context;
      this.mLooper = looper;
    }

    public Context getContext() {
      return this.mContext;
    }

    public Looper getLooper() {
      return this.mLooper;
    }

    public void onBootPhase(int i) {
      if (i == 500) {
        this.mDeviceIdleController =
            IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
        this.mBatteryStats =
            IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        this.mPackageManagerInternal =
            (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mBatteryManager =
            (BatteryManager) this.mContext.getSystemService(BatteryManager.class);
        this.mCrossProfileAppsInternal =
            (CrossProfileAppsInternal) LocalServices.getService(CrossProfileAppsInternal.class);
        this.mAlarmManagerInternal =
            (AlarmManagerInternal) LocalServices.getService(AlarmManagerInternal.class);
        if (((ActivityManager) this.mContext.getSystemService("activity")).isLowRamDevice()
            || ActivityManager.isSmallBatteryDevice()) {
          this.mAutoRestrictedBucketDelayMs = 43200000L;
        }
      } else if (i == 1000) {
        this.mWellbeingApp = this.mContext.getPackageManager().getWellbeingPackageName();
      }
      this.mBootPhase = i;
    }

    public int getBootPhase() {
      return this.mBootPhase;
    }

    public long elapsedRealtime() {
      return SystemClock.elapsedRealtime();
    }

    public boolean isAppIdleEnabled() {
      return this.mContext.getResources().getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable)
          && (Settings.Global.getInt(this.mContext.getContentResolver(), "app_standby_enabled", 1)
                  == 1
              && Settings.Global.getInt(
                      this.mContext.getContentResolver(), "adaptive_battery_management_enabled", 1)
                  == 1);
    }

    public boolean isCharging() {
      return this.mBatteryManager.isCharging();
    }

    public boolean isNonIdleWhitelisted(String str) {
      boolean contains;
      if (this.mBootPhase < 500) {
        return false;
      }
      synchronized (this.mPowerWhitelistedApps) {
        contains = this.mPowerWhitelistedApps.contains(str);
      }
      return contains;
    }

    public IAppOpsService getAppOpsService() {
      return IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
    }

    public boolean isWellbeingPackage(String str) {
      return str.equals(this.mWellbeingApp);
    }

    public boolean shouldGetExactAlarmBucketElevation(String str, int i) {
      return this.mAlarmManagerInternal.shouldGetBucketElevation(str, i);
    }

    public void updatePowerWhitelistCache() {
      try {
        String[] fullPowerWhitelistExceptIdle =
            this.mDeviceIdleController.getFullPowerWhitelistExceptIdle();
        synchronized (this.mPowerWhitelistedApps) {
          this.mPowerWhitelistedApps.clear();
          for (String str : fullPowerWhitelistExceptIdle) {
            this.mPowerWhitelistedApps.add(str);
          }
        }
      } catch (RemoteException e) {
        Slog.wtf("AppStandbyController", "Failed to get power whitelist", e);
      }
    }

    public File getDataSystemDirectory() {
      return Environment.getDataSystemDirectory();
    }

    public long getAutoRestrictedBucketDelayMs() {
      return this.mAutoRestrictedBucketDelayMs;
    }

    public void noteEvent(int i, String str, int i2) {
      IBatteryStats iBatteryStats = this.mBatteryStats;
      if (iBatteryStats != null) {
        iBatteryStats.noteEvent(i, str, i2);
      }
    }

    public PackageManagerInternal getPackageManagerInternal() {
      return this.mPackageManagerInternal;
    }

    public boolean isPackageEphemeral(int i, String str) {
      return this.mPackageManagerInternal.isPackageEphemeral(i, str);
    }

    public boolean isPackageInstalled(String str, int i, int i2) {
      return this.mPackageManagerInternal.getPackageUid(str, (long) i, i2) >= 0;
    }

    public int[] getRunningUserIds() {
      return ActivityManager.getService().getRunningUserIds();
    }

    public boolean isDefaultDisplayOn() {
      return this.mDisplayManager.getDisplay(0).getState() == 2;
    }

    public void registerDisplayListener(
        DisplayManager.DisplayListener displayListener, Handler handler) {
      this.mDisplayManager.registerDisplayListener(displayListener, handler);
    }

    public String getActiveNetworkScorer() {
      return ((NetworkScoreManager) this.mContext.getSystemService("network_score"))
          .getActiveScorerPackage();
    }

    public boolean isBoundWidgetPackage(AppWidgetManager appWidgetManager, String str, int i) {
      return appWidgetManager.isBoundWidgetPackage(str, i);
    }

    public DeviceConfig.Properties getDeviceConfigProperties(String... strArr) {
      return DeviceConfig.getProperties("app_standby", strArr);
    }

    public boolean isDeviceIdleMode() {
      return this.mPowerManager.isDeviceIdleMode();
    }

    public List getValidCrossProfileTargets(String str, int i) {
      int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, i);
      AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(packageUid);
      if (packageUid < 0
          || androidPackage == null
          || !androidPackage.isCrossProfile()
          || !this.mCrossProfileAppsInternal.verifyUidHasInteractAcrossProfilePermission(
              str, packageUid)) {
        if (packageUid >= 0 && androidPackage == null) {
          Slog.wtf("AppStandbyController", "Null package retrieved for UID " + packageUid);
        }
        return Collections.emptyList();
      }
      return this.mCrossProfileAppsInternal.getTargetUserProfiles(str, i);
    }

    public void registerDeviceConfigPropertiesChangedListener(
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
      DeviceConfig.addOnPropertiesChangedListener(
          "app_standby", AppSchedulingModuleThread.getExecutor(), onPropertiesChangedListener);
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println("mPowerWhitelistedApps=[");
      synchronized (this.mPowerWhitelistedApps) {
        for (int size = this.mPowerWhitelistedApps.size() - 1; size >= 0; size--) {
          printWriter.print("  ");
          printWriter.print((String) this.mPowerWhitelistedApps.valueAt(size));
          printWriter.println(",");
        }
      }
      printWriter.println("]");
      printWriter.println();
    }
  }

  public class AppStandbyHandler extends Handler {
    public AppStandbyHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      long j;
      switch (message.what) {
        case 3:
          StandbyUpdateRecord standbyUpdateRecord = (StandbyUpdateRecord) message.obj;
          AppStandbyController.this.informListeners(
              standbyUpdateRecord.packageName,
              standbyUpdateRecord.userId,
              standbyUpdateRecord.bucket,
              standbyUpdateRecord.reason,
              standbyUpdateRecord.isUserInteraction);
          standbyUpdateRecord.recycle();
          return;
        case 4:
          AppStandbyController.this.forceIdleState(
              (String) message.obj, message.arg1, message.arg2 == 1);
          return;
        case 5:
          removeMessages(5);
          long elapsedRealtime = AppStandbyController.this.mInjector.elapsedRealtime();
          synchronized (AppStandbyController.this.mPendingIdleStateChecks) {
            j = Long.MAX_VALUE;
            for (int size = AppStandbyController.this.mPendingIdleStateChecks.size() - 1;
                size >= 0;
                size--) {
              long valueAt = AppStandbyController.this.mPendingIdleStateChecks.valueAt(size);
              if (valueAt <= elapsedRealtime) {
                int keyAt = AppStandbyController.this.mPendingIdleStateChecks.keyAt(size);
                if (AppStandbyController.this.checkIdleStates(keyAt)
                    && AppStandbyController.this.mAppIdleEnabled) {
                  AppStandbyController appStandbyController = AppStandbyController.this;
                  long j2 = appStandbyController.mCheckIdleIntervalMillis + elapsedRealtime;
                  appStandbyController.mPendingIdleStateChecks.put(keyAt, j2);
                  valueAt = j2;
                } else {
                  AppStandbyController.this.mPendingIdleStateChecks.removeAt(size);
                }
              }
              j = Math.min(j, valueAt);
            }
          }
          if (j != Long.MAX_VALUE) {
            AppStandbyController.this.mHandler.sendMessageDelayed(
                AppStandbyController.this.mHandler.obtainMessage(5), j - elapsedRealtime);
            return;
          }
          return;
        case 6:
        default:
          super.handleMessage(message);
          return;
        case 7:
          AppStandbyController.this.triggerListenerQuotaBump((String) message.obj, message.arg1);
          return;
        case 8:
          ContentProviderUsageRecord contentProviderUsageRecord =
              (ContentProviderUsageRecord) message.obj;
          AppStandbyController.this.reportContentProviderUsage(
              contentProviderUsageRecord.name,
              contentProviderUsageRecord.packageName,
              contentProviderUsageRecord.userId);
          contentProviderUsageRecord.recycle();
          return;
        case 9:
          AppStandbyController.this.informParoleStateChanged();
          return;
        case 10:
          AppStandbyController.this.mHandler.removeMessages(10);
          AppStandbyController.this.waitForAdminData();
          AppStandbyController.this.checkIdleStates(-1);
          return;
        case 11:
          AppStandbyController appStandbyController2 = AppStandbyController.this;
          appStandbyController2.checkAndUpdateStandbyState(
              (String) message.obj,
              message.arg1,
              message.arg2,
              appStandbyController2.mInjector.elapsedRealtime());
          return;
        case 12:
          if (message.arg2 > 0) {
            AppStandbyController.this.reportExemptedSyncScheduled(
                (String) message.obj, message.arg1);
            return;
          } else {
            AppStandbyController.this.reportUnexemptedSyncScheduled(
                (String) message.obj, message.arg1);
            return;
          }
        case 13:
          AppStandbyController.this.reportExemptedSyncStart((String) message.obj, message.arg1);
          return;
      }
    }
  }

  public class DeviceStateReceiver extends BroadcastReceiver {
    public DeviceStateReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      action.hashCode();
      switch (action) {
        case "android.os.action.POWER_SAVE_WHITELIST_CHANGED":
          if (AppStandbyController.this.mSystemServicesReady) {
            AppStandbyHandler appStandbyHandler = AppStandbyController.this.mHandler;
            final AppStandbyController appStandbyController = AppStandbyController.this;
            appStandbyHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.usage.AppStandbyController$DeviceStateReceiver$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    AppStandbyController.this.updatePowerWhitelistCache();
                  }
                });
            break;
          }
          break;
        case "android.os.action.DISCHARGING":
          AppStandbyController.this.setChargingState(false);
          break;
        case "android.os.action.CHARGING":
          AppStandbyController.this.setChargingState(true);
          break;
      }
    }
  }

  public class ConstantsObserver extends ContentObserver
      implements DeviceConfig.OnPropertiesChangedListener {
    public final String[] KEYS_ELAPSED_TIME_THRESHOLDS;
    public final String[] KEYS_SCREEN_TIME_THRESHOLDS;
    public final TextUtils.SimpleStringSplitter mStringPipeSplitter;

    public ConstantsObserver(Handler handler) {
      super(handler);
      this.KEYS_SCREEN_TIME_THRESHOLDS =
          new String[] {
            "screen_threshold_active",
            "screen_threshold_working_set",
            "screen_threshold_frequent",
            "screen_threshold_rare",
            "screen_threshold_restricted"
          };
      this.KEYS_ELAPSED_TIME_THRESHOLDS =
          new String[] {
            "elapsed_threshold_active",
            "elapsed_threshold_working_set",
            "elapsed_threshold_frequent",
            "elapsed_threshold_rare",
            "elapsed_threshold_restricted"
          };
      this.mStringPipeSplitter = new TextUtils.SimpleStringSplitter('|');
    }

    public void start() {
      ContentResolver contentResolver = AppStandbyController.this.mContext.getContentResolver();
      contentResolver.registerContentObserver(
          Settings.Global.getUriFor("app_standby_enabled"), false, this);
      contentResolver.registerContentObserver(
          Settings.Global.getUriFor("adaptive_battery_management_enabled"), false, this);
      AppStandbyController.this.mInjector.registerDeviceConfigPropertiesChangedListener(this);
      processProperties(
          AppStandbyController.this.mInjector.getDeviceConfigProperties(new String[0]));
      updateSettings();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      updateSettings();
      AppStandbyController.this.postOneTimeCheckIdleStates();
    }

    public void onPropertiesChanged(DeviceConfig.Properties properties) {
      processProperties(properties);
      AppStandbyController.this.postOneTimeCheckIdleStates();
    }

    public final void processProperties(DeviceConfig.Properties properties) {
      char c;
      synchronized (AppStandbyController.this.mAppIdleLock) {
        boolean z = false;
        for (String str : properties.getKeyset()) {
          if (str != null) {
            switch (str.hashCode()) {
              case -1991469656:
                if (str.equals("sync_adapter_duration")) {
                  c = '\f';
                  break;
                }
                c = 65535;
                break;
              case -1963219299:
                if (str.equals("brodacast_response_exempted_permissions")) {
                  c = 23;
                  break;
                }
                c = 65535;
                break;
              case -1794959158:
                if (str.equals("trigger_quota_bump_on_notification_seen")) {
                  c = 6;
                  break;
                }
                c = 65535;
                break;
              case -1610671326:
                if (str.equals("unexempted_sync_scheduled_duration")) {
                  c = 16;
                  break;
                }
                c = 65535;
                break;
              case -1525033432:
                if (str.equals("broadcast_sessions_with_response_duration_ms")) {
                  c = 20;
                  break;
                }
                c = 65535;
                break;
              case -1063555730:
                if (str.equals("slice_pinned_duration")) {
                  c = 7;
                  break;
                }
                c = 65535;
                break;
              case -973233853:
                if (str.equals("auto_restricted_bucket_delay_ms")) {
                  c = 0;
                  break;
                }
                c = 65535;
                break;
              case -695619964:
                if (str.equals("notification_seen_duration")) {
                  c = 3;
                  break;
                }
                c = 65535;
                break;
              case -654339791:
                if (str.equals("system_interaction_duration")) {
                  c = '\n';
                  break;
                }
                c = 65535;
                break;
              case -641750299:
                if (str.equals("note_response_event_for_all_broadcast_sessions")) {
                  c = 21;
                  break;
                }
                c = 65535;
                break;
              case -557676904:
                if (str.equals("system_update_usage_duration")) {
                  c = 11;
                  break;
                }
                c = 65535;
                break;
              case -294320234:
                if (str.equals("brodacast_response_exempted_roles")) {
                  c = 22;
                  break;
                }
                c = 65535;
                break;
              case -129077581:
                if (str.equals("broadcast_response_window_timeout_ms")) {
                  c = 17;
                  break;
                }
                c = 65535;
                break;
              case -57661244:
                if (str.equals("exempted_sync_scheduled_d_duration")) {
                  c = '\r';
                  break;
                }
                c = 65535;
                break;
              case 276460958:
                if (str.equals("retain_notification_seen_impact_for_pre_t_apps")) {
                  c = 5;
                  break;
                }
                c = 65535;
                break;
              case 456604392:
                if (str.equals("exempted_sync_scheduled_nd_duration")) {
                  c = 14;
                  break;
                }
                c = 65535;
                break;
              case 742365823:
                if (str.equals("broadcast_response_fg_threshold_state")) {
                  c = 18;
                  break;
                }
                c = 65535;
                break;
              case 938381045:
                if (str.equals("notification_seen_promoted_bucket")) {
                  c = 4;
                  break;
                }
                c = 65535;
                break;
              case 992238669:
                if (str.equals("broadcast_sessions_duration_ms")) {
                  c = 19;
                  break;
                }
                c = 65535;
                break;
              case 1105744372:
                if (str.equals("exempted_sync_start_duration")) {
                  c = 15;
                  break;
                }
                c = 65535;
                break;
              case 1288386175:
                if (str.equals("cross_profile_apps_share_standby_buckets")) {
                  c = 1;
                  break;
                }
                c = 65535;
                break;
              case 1378352561:
                if (str.equals("prediction_timeout")) {
                  c = '\t';
                  break;
                }
                c = 65535;
                break;
              case 1400233242:
                if (str.equals("strong_usage_duration")) {
                  c = '\b';
                  break;
                }
                c = 65535;
                break;
              case 1915246556:
                if (str.equals("initial_foreground_service_start_duration")) {
                  c = 2;
                  break;
                }
                c = 65535;
                break;
              default:
                c = 65535;
                break;
            }
            boolean z2 = z;
            switch (c) {
              case 0:
                AppStandbyController.this.mInjector.mAutoRestrictedBucketDelayMs =
                    Math.max(
                        BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS,
                        properties.getLong(
                            "auto_restricted_bucket_delay_ms",
                            ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS));
                z = z2;
                break;
              case 1:
                AppStandbyController.this.mLinkCrossProfileApps =
                    properties.getBoolean("cross_profile_apps_share_standby_buckets", true);
                z = z2;
                break;
              case 2:
                AppStandbyController.this.mInitialForegroundServiceStartTimeoutMillis =
                    properties.getLong("initial_foreground_service_start_duration", 1800000L);
                z = z2;
                break;
              case 3:
                AppStandbyController.this.mNotificationSeenTimeoutMillis =
                    properties.getLong("notification_seen_duration", 43200000L);
                z = z2;
                break;
              case 4:
                AppStandbyController.this.mNotificationSeenPromotedBucket =
                    properties.getInt("notification_seen_promoted_bucket", 20);
                z = z2;
                break;
              case 5:
                AppStandbyController.this.mRetainNotificationSeenImpactForPreTApps =
                    properties.getBoolean("retain_notification_seen_impact_for_pre_t_apps", false);
                z = z2;
                break;
              case 6:
                AppStandbyController.this.mTriggerQuotaBumpOnNotificationSeen =
                    properties.getBoolean("trigger_quota_bump_on_notification_seen", false);
                z = z2;
                break;
              case 7:
                AppStandbyController.this.mSlicePinnedTimeoutMillis =
                    properties.getLong("slice_pinned_duration", 43200000L);
                z = z2;
                break;
              case '\b':
                AppStandbyController.this.mStrongUsageTimeoutMillis =
                    properties.getLong(
                        "strong_usage_duration", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                z = z2;
                break;
              case '\t':
                AppStandbyController.this.mPredictionTimeoutMillis =
                    properties.getLong("prediction_timeout", 43200000L);
                z = z2;
                break;
              case '\n':
                AppStandbyController.this.mSystemInteractionTimeoutMillis =
                    properties.getLong("system_interaction_duration", 600000L);
                z = z2;
                break;
              case 11:
                AppStandbyController.this.mSystemUpdateUsageTimeoutMillis =
                    properties.getLong("system_update_usage_duration", 7200000L);
                z = z2;
                break;
              case '\f':
                AppStandbyController.this.mSyncAdapterTimeoutMillis =
                    properties.getLong("sync_adapter_duration", 600000L);
                z = z2;
                break;
              case '\r':
                AppStandbyController.this.mExemptedSyncScheduledDozeTimeoutMillis =
                    properties.getLong(
                        "exempted_sync_scheduled_d_duration",
                        BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                z = z2;
                break;
              case 14:
                AppStandbyController.this.mExemptedSyncScheduledNonDozeTimeoutMillis =
                    properties.getLong("exempted_sync_scheduled_nd_duration", 600000L);
                z = z2;
                break;
              case 15:
                AppStandbyController.this.mExemptedSyncStartTimeoutMillis =
                    properties.getLong("exempted_sync_start_duration", 600000L);
                z = z2;
                break;
              case 16:
                AppStandbyController.this.mUnexemptedSyncScheduledTimeoutMillis =
                    properties.getLong("unexempted_sync_scheduled_duration", 600000L);
                z = z2;
                break;
              case 17:
                AppStandbyController.this.mBroadcastResponseWindowDurationMillis =
                    properties.getLong("broadcast_response_window_timeout_ms", 120000L);
                z = z2;
                break;
              case 18:
                AppStandbyController.this.mBroadcastResponseFgThresholdState =
                    properties.getInt("broadcast_response_fg_threshold_state", 2);
                z = z2;
                break;
              case 19:
                AppStandbyController.this.mBroadcastSessionsDurationMs =
                    properties.getLong("broadcast_sessions_duration_ms", 120000L);
                z = z2;
                break;
              case 20:
                AppStandbyController.this.mBroadcastSessionsWithResponseDurationMs =
                    properties.getLong("broadcast_sessions_with_response_duration_ms", 120000L);
                z = z2;
                break;
              case 21:
                AppStandbyController.this.mNoteResponseEventForAllBroadcastSessions =
                    properties.getBoolean("note_response_event_for_all_broadcast_sessions", true);
                z = z2;
                break;
              case 22:
                AppStandbyController.this.mBroadcastResponseExemptedRoles =
                    properties.getString("brodacast_response_exempted_roles", "");
                AppStandbyController appStandbyController = AppStandbyController.this;
                appStandbyController.mBroadcastResponseExemptedRolesList =
                    splitPipeSeparatedString(appStandbyController.mBroadcastResponseExemptedRoles);
                z = z2;
                break;
              case 23:
                AppStandbyController.this.mBroadcastResponseExemptedPermissions =
                    properties.getString("brodacast_response_exempted_permissions", "");
                AppStandbyController appStandbyController2 = AppStandbyController.this;
                appStandbyController2.mBroadcastResponseExemptedPermissionsList =
                    splitPipeSeparatedString(
                        appStandbyController2.mBroadcastResponseExemptedPermissions);
                z = z2;
                break;
              default:
                if (!z2
                    && (str.startsWith("screen_threshold_")
                        || str.startsWith("elapsed_threshold_"))) {
                  updateTimeThresholds();
                  z = true;
                  break;
                }
                z = z2;
                break;
            }
            AppStandbyController.this.mAppStandbyProperties.put(
                str, properties.getString(str, (String) null));
          }
        }
      }
    }

    public final List splitPipeSeparatedString(String str) {
      ArrayList arrayList = new ArrayList();
      this.mStringPipeSplitter.setString(str);
      while (this.mStringPipeSplitter.hasNext()) {
        arrayList.add(this.mStringPipeSplitter.next());
      }
      return arrayList;
    }

    public final void updateTimeThresholds() {
      DeviceConfig.Properties deviceConfigProperties =
          AppStandbyController.this.mInjector.getDeviceConfigProperties(
              this.KEYS_SCREEN_TIME_THRESHOLDS);
      DeviceConfig.Properties deviceConfigProperties2 =
          AppStandbyController.this.mInjector.getDeviceConfigProperties(
              this.KEYS_ELAPSED_TIME_THRESHOLDS);
      AppStandbyController.this.mAppStandbyScreenThresholds =
          generateThresholdArray(
              deviceConfigProperties,
              this.KEYS_SCREEN_TIME_THRESHOLDS,
              AppStandbyController.DEFAULT_SCREEN_TIME_THRESHOLDS,
              AppStandbyController.MINIMUM_SCREEN_TIME_THRESHOLDS);
      AppStandbyController.this.mAppStandbyElapsedThresholds =
          generateThresholdArray(
              deviceConfigProperties2,
              this.KEYS_ELAPSED_TIME_THRESHOLDS,
              AppStandbyController.DEFAULT_ELAPSED_TIME_THRESHOLDS,
              AppStandbyController.MINIMUM_ELAPSED_TIME_THRESHOLDS);
      AppStandbyController appStandbyController = AppStandbyController.this;
      appStandbyController.mCheckIdleIntervalMillis =
          Math.min(
              appStandbyController.mAppStandbyElapsedThresholds[1] / 4,
              BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
    }

    public void updateSettings() {
      AppStandbyController appStandbyController = AppStandbyController.this;
      appStandbyController.setAppIdleEnabled(appStandbyController.mInjector.isAppIdleEnabled());
    }

    public long[] generateThresholdArray(
        DeviceConfig.Properties properties, String[] strArr, long[] jArr, long[] jArr2) {
      if (properties.getKeyset().isEmpty()) {
        return jArr;
      }
      if (strArr.length != AppStandbyController.THRESHOLD_BUCKETS.length) {
        throw new IllegalStateException(
            "# keys ("
                + strArr.length
                + ") != # buckets ("
                + AppStandbyController.THRESHOLD_BUCKETS.length
                + ")");
      }
      if (jArr.length != AppStandbyController.THRESHOLD_BUCKETS.length) {
        throw new IllegalStateException(
            "# defaults ("
                + jArr.length
                + ") != # buckets ("
                + AppStandbyController.THRESHOLD_BUCKETS.length
                + ")");
      }
      if (jArr2.length != AppStandbyController.THRESHOLD_BUCKETS.length) {
        Slog.wtf("AppStandbyController", "minValues array is the wrong size");
        jArr2 = new long[AppStandbyController.THRESHOLD_BUCKETS.length];
      }
      long[] jArr3 = new long[AppStandbyController.THRESHOLD_BUCKETS.length];
      for (int i = 0; i < AppStandbyController.THRESHOLD_BUCKETS.length; i++) {
        jArr3[i] = Math.max(jArr2[i], properties.getLong(strArr[i], jArr[i]));
      }
      return jArr3;
    }
  }
}
