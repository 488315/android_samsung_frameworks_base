package com.android.server.appprelauncher;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ApplicationExitInfo;
import android.app.KeyguardManager;
import android.app.WaitResult;
import android.app.appprelauncher.IAppPrelaunchService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.INetworkManagementService;
import android.os.PowerManager;
import android.os.Process;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.jar.StrictJarFile;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AlarmManagerInternal;
import com.android.server.LocalServices;
import com.android.server.SpegService;
import com.android.server.am.ActivityManagerService;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;

/* loaded from: classes.dex */
public class AppPrelaunchService extends IAppPrelaunchService.Stub {
  public static final int ACTIVE_APPS_LIMIT;
  public static final boolean FORCE_PREL;
  public static final int PRELAUNCH_ATTEMPTS_LIMIT;
  public static final boolean RESTRICT_NETWORK;
  public final long HISTORY_TIME_MIN;
  public ActivityManagerService mAm;
  public final Context mContext;
  public ExecutorService mExecutorService;
  public IntentTracker mIntentTracker;
  public final Object mLock;
  public INetworkManagementService mNms;
  public PermissionManagerServiceInternal mPermissionManager;
  public PackageManagerInternal mPmInternal;
  public Queue mPrelaunchedAppHistory;
  public ArrayMap mPrelaunchedAppIds;
  public ArrayMap mPrelaunchedApps;
  public ScpmController mScpmController;
  public boolean mSetupWizardFinished;
  public boolean mSmartSwitchState;
  public final SpegService mSpeg;
  public final PrelauncherStorage mStorage;
  public Set packageBlockList;
  public static final int PREL_APP_START_WATCHDOG_TIMEOUT_MS =
      SystemProperties.getInt("com.samsung.speg.prelauncher.appstartwatchdog_ms", 5000);
  public static final long APP_MAX_IDLE_TIME_MIN =
      SystemProperties.getInt("com.samsung.speg.prelauncher.appidletime_min", 10);
  public static final long GLOBAL_WATCHDOG_SLEEP_TIME_MIN =
      SystemProperties.getInt("com.samsung.speg.prelauncher.globalwatchdog_min", 10);
  public Set nativeLibBlockList = new HashSet();
  public Set blockedActivities = new HashSet();

  static {
    FORCE_PREL =
        Build.IS_DEBUGGABLE
            && "true".equals(SystemProperties.get("com.samsung.speg.prelauncher.force"));
    RESTRICT_NETWORK =
        "true".equals(SystemProperties.get("com.samsung.speg.prelauncher.restrict_network"));
    ACTIVE_APPS_LIMIT =
        SystemProperties.getInt("com.samsung.speg.prelauncher.active_apps_limit", 3);
    PRELAUNCH_ATTEMPTS_LIMIT =
        SystemProperties.getInt("com.samsung.speg.prelauncher.prelaunch_attempts_limit", 5);
  }

  public AppPrelaunchService(
      Context context, SpegService spegService, ActivityManagerService activityManagerService) {
    Object obj = new Object();
    this.mLock = obj;
    this.mPrelaunchedApps = new ArrayMap();
    this.mPrelaunchedAppIds = new ArrayMap();
    this.mPrelaunchedAppHistory = new LinkedList();
    this.HISTORY_TIME_MIN = GLOBAL_WATCHDOG_SLEEP_TIME_MIN * 2;
    this.mContext = context;
    this.mSpeg = spegService;
    this.mAm = activityManagerService;
    this.packageBlockList =
        spegService.initPackageBlockList("/system/etc/prelauncher-package-blocklist.conf");
    PrelauncherStorage prelauncherStorage = new PrelauncherStorage(context);
    this.mStorage = prelauncherStorage;
    this.mScpmController = new ScpmController(prelauncherStorage);
    this.mIntentTracker = new IntentTracker(obj);
  }

  public boolean initCoreServices() {
    PackageManagerInternal packageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.mPmInternal = packageManagerInternal;
    if (packageManagerInternal == null) {
      Slog.e("PRELService", "Could not get package manager");
      return false;
    }
    INetworkManagementService asInterface =
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    this.mNms = asInterface;
    if (asInterface == null) {
      Slog.e("PRELService", "Cannot get NetworkManagementService");
      return false;
    }
    PermissionManagerServiceInternal permissionManagerServiceInternal =
        (PermissionManagerServiceInternal)
            LocalServices.getService(PermissionManagerServiceInternal.class);
    this.mPermissionManager = permissionManagerServiceInternal;
    if (permissionManagerServiceInternal == null) {
      Slog.e("PRELService", "Cannot get PermissionManagerServiceInternal");
      return false;
    }
    startGlobalWatchDog();
    this.mExecutorService = Executors.newCachedThreadPool();
    return true;
  }

  public final void removePrelaunchedApp(PrelaunchedApp prelaunchedApp) {
    if (Thread.holdsLock(prelaunchedApp) && !Thread.holdsLock(this.mLock)) {
      Slog.wtf("PRELService", "Wrong lock sequence for " + prelaunchedApp, new RuntimeException());
      if (Build.IS_DEBUGGABLE) {
        Process.sendSignal(Process.myPid(), 9);
      }
    }
    synchronized (this.mLock) {
      if (!this.mPrelaunchedApps.containsValue(prelaunchedApp)) {
        Slog.w("PRELService", "Already removed " + prelaunchedApp);
        return;
      }
      this.mPrelaunchedApps.remove(Integer.valueOf(prelaunchedApp.getUid()));
      String packageName = prelaunchedApp.getPackageName();
      Iterator it = this.mPrelaunchedApps.values().iterator();
      while (it.hasNext()) {
        if (packageName.equals(((PrelaunchedApp) it.next()).getPackageName())) {
          return;
        }
      }
      this.mPrelaunchedAppIds.remove(packageName);
    }
  }

  public final void startGlobalWatchDog() {
    new Thread(
            new Runnable() { // from class: com.android.server.appprelauncher.AppPrelaunchService.1
              @Override // java.lang.Runnable
              public void run() {
                try {
                  Thread.currentThread().setName("PrelGlobWatchDog");
                } catch (SecurityException unused) {
                }
                Slog.i("PRELService", "Global watchDog started");
                TimeUnit timeUnit = TimeUnit.MINUTES;
                long nanos = timeUnit.toNanos(AppPrelaunchService.APP_MAX_IDLE_TIME_MIN);
                long nanos2 = timeUnit.toNanos(AppPrelaunchService.this.HISTORY_TIME_MIN);
                while (true) {
                  try {
                    Thread.sleep(
                        TimeUnit.MINUTES.toMillis(
                            AppPrelaunchService.GLOBAL_WATCHDOG_SLEEP_TIME_MIN));
                  } catch (InterruptedException e) {
                    Slog.e("PRELService", "Global watchDog interrupted", e);
                  }
                  HashSet hashSet = new HashSet();
                  long nanoTime = System.nanoTime();
                  synchronized (AppPrelaunchService.this.mLock) {
                    for (PrelaunchedApp prelaunchedApp :
                        AppPrelaunchService.this.mPrelaunchedApps.values()) {
                      if (nanoTime - prelaunchedApp.getPrelaunchedTimeNs() > nanos) {
                        hashSet.add(prelaunchedApp);
                      }
                    }
                    while (!AppPrelaunchService.this.mPrelaunchedAppHistory.isEmpty()
                        && nanoTime
                                - ((PrelaunchedApp)
                                        AppPrelaunchService.this.mPrelaunchedAppHistory.peek())
                                    .getPrelaunchedTimeNs()
                            > nanos2) {
                      AppPrelaunchService.this.mPrelaunchedAppHistory.remove();
                    }
                  }
                  Iterator it = hashSet.iterator();
                  while (it.hasNext()) {
                    PrelaunchedApp prelaunchedApp2 = (PrelaunchedApp) it.next();
                    Slog.d(
                        "PRELService",
                        "Package " + prelaunchedApp2.getPackageName() + " unused, kill");
                    synchronized (prelaunchedApp2.getLock()) {
                      if (prelaunchedApp2.getPrelaunched()) {
                        AppPrelaunchService.this.killAppInternal(
                            prelaunchedApp2, "Global watchDog");
                        prelaunchedApp2.setStage(60);
                      }
                    }
                    AppPrelaunchService.this.removePrelaunchedApp(prelaunchedApp2);
                  }
                }
              }
            })
        .start();
  }

  public final Intent getLaunchIntentForPackage(String str, int i) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.INFO");
    intent.setPackage(str);
    List queryIntentActivitiesAsUser =
        this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 0, i);
    if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
      intent.removeCategory("android.intent.category.INFO");
      intent.addCategory("android.intent.category.LAUNCHER");
      intent.setPackage(str);
      queryIntentActivitiesAsUser =
          this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 0, i);
    }
    if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
      return null;
    }
    Intent intent2 = new Intent(intent);
    intent2.addFlags(1073741824);
    intent2.setFlags(268435456);
    intent2.addFlags(262144);
    String str2 = ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name;
    intent2.setClassName(
        ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName, str2);
    if (!this.mSpeg.isInBlockList(
        str2, this.mStorage.getBlockedActivities(this.blockedActivities))) {
      return intent2;
    }
    Slog.d("PRELService", "Activity " + str2 + " is skipped due to blocklist");
    if (FORCE_PREL) {
      return intent2;
    }
    return null;
  }

  /* renamed from: prelaunchApp, reason: merged with bridge method [inline-methods] */
  public boolean lambda$performInstallActionBroadcast$5(String str, int i) {
    return prelaunchAppTillStage(str, i, 40);
  }

  public boolean prelaunchAppTillStage(String str, int i, int i2) {
    String str2;
    StringBuilder sb;
    Slog.i("PRELService", "Started, Samsung PreL v3.1");
    boolean z = false;
    if (!isCallerUidAllowed()) {
      Slog.e("PRELService", "Caller does not have permission to prelaunch app");
      return false;
    }
    if (!UserHandle.isApp(i)) {
      Slog.w("PRELService", "Unique Id " + i + " is not an app Id");
      return false;
    }
    AndroidPackage androidPackage = this.mPmInternal.getPackage(i);
    if (shouldSkipPrelForPackage(androidPackage)) {
      if (!FORCE_PREL) {
        return false;
      }
      Slog.i("PRELService", "Forced prelaunch");
    }
    if (!androidPackage.getPackageName().equals(str)) {
      Slog.e("PRELService", "Package name " + str + " does not match its uid " + i);
      return false;
    }
    int currentUserId = this.mAm.getCurrentUserId();
    int userId = UserHandle.getUserId(i);
    if (userId != currentUserId) {
      Slog.e("PRELService", "App uid " + i + " is not related to current userId " + currentUserId);
      return false;
    }
    Intent launchIntentForPackage = getLaunchIntentForPackage(str, userId);
    if (launchIntentForPackage == null) {
      Slog.i("PRELService", "Package " + str + " doesn't have allowed launchable intent");
      return false;
    }
    if (this.mSpeg.getPidOf(androidPackage.getProcessName(), i) != -1) {
      Slog.e("PRELService", "Package " + str + " is already running");
      return false;
    }
    for (ActivityTaskManager.RootTaskInfo rootTaskInfo : this.mAm.getAllRootTaskInfos()) {
      for (int i3 = 0; i3 < rootTaskInfo.childTaskIds.length; i3++) {
        if (rootTaskInfo.childTaskUserIds[i3] == userId) {
          if (rootTaskInfo.childTaskNames[i3].startsWith(str + "/")) {
            Slog.w("PRELService", "Found task for " + str + ": " + rootTaskInfo);
            return false;
          }
        }
      }
    }
    PrelaunchedApp prelaunchedApp = new PrelaunchedApp(androidPackage, i2, 0);
    synchronized (this.mLock) {
      if (this.mIntentTracker.hasTrack(str, userId)) {
        Slog.w("PRELService", "User has already launched app " + str);
        return false;
      }
      PrelaunchedApp prelaunchedApp2 =
          (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(i));
      if (prelaunchedApp2 != null) {
        if (prelaunchedApp2.getStage() >= 40 && !prelaunchedApp2.isProcessAlive()) {
          Slog.w("PRELService", "Process did not survive for previous " + prelaunchedApp2);
          synchronized (prelaunchedApp2.getLock()) {
            prelaunchedApp2.setDeathReason("App process did not survive");
            prelaunchedApp2.setStage(60);
          }
          removePrelaunchedApp(prelaunchedApp2);
        }
        Slog.e("PRELService", "Package " + str + " is already being prelaunched");
        return false;
      }
      this.mPrelaunchedApps.put(Integer.valueOf(i), prelaunchedApp);
      this.mPrelaunchedAppIds.put(str, Integer.valueOf(UserHandle.getAppId(i)));
      this.mPrelaunchedAppHistory.add(prelaunchedApp);
      Slog.d(
          "PRELService",
          "Start prelaunching " + str + XmlUtils.STRING_ARRAY_SEPARATOR + i + " until stage " + i2);
      int i4 = 1;
      try {
        try {
          prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(true);
          restrictNetworkConnection(i, true);
          try {
          } catch (Exception e) {
            e = e;
            prelaunchedApp.setDeathReason(e.getMessage());
            Slog.e("PRELService", "Error during prelaunching of " + str + ": " + e.getMessage());
            prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(false);
            checkStateAndClearIfNeeded(prelaunchedApp);
            if (prelaunchedApp.getStage() == i4 || prelaunchedApp.getStage() == 50) {
              Slog.i(
                  "PRELService",
                  "App "
                      + prelaunchedApp.getPackageName()
                      + " prelaunched, pid: "
                      + prelaunchedApp.getPid());
              z = true;
            } else {
              removePrelaunchedApp(prelaunchedApp);
            }
            str2 = "PRELService";
            sb = new StringBuilder();
            sb.append("Finish prelaunch of ");
            sb.append(prelaunchedApp);
            Slog.i(str2, sb.toString());
            return z;
          }
        } catch (Throwable th) {
          th = th;
          prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(false);
          checkStateAndClearIfNeeded(prelaunchedApp);
          if (prelaunchedApp.getStage() != i4 || prelaunchedApp.getStage() == 50) {
            Slog.i(
                "PRELService",
                "App "
                    + prelaunchedApp.getPackageName()
                    + " prelaunched, pid: "
                    + prelaunchedApp.getPid());
          } else {
            removePrelaunchedApp(prelaunchedApp);
          }
          Slog.i("PRELService", "Finish prelaunch of " + prelaunchedApp);
          throw th;
        }
      } catch (Exception e2) {
        e = e2;
        i4 = 40;
      } catch (Throwable th2) {
        th = th2;
        i4 = 40;
        prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(false);
        checkStateAndClearIfNeeded(prelaunchedApp);
        if (prelaunchedApp.getStage() != i4) {}
        Slog.i(
            "PRELService",
            "App "
                + prelaunchedApp.getPackageName()
                + " prelaunched, pid: "
                + prelaunchedApp.getPid());
        Slog.i("PRELService", "Finish prelaunch of " + prelaunchedApp);
        throw th;
      }
      if (!prelaunchedApp.createOrDeleteMarkerFiles(true)) {
        throw new RuntimeException("createSpegMarkerFile failed");
      }
      if (this.mIntentTracker.hasTrack(str, userId) || prelaunchedApp.getStage() >= 50) {
        throw new RuntimeException("App was launched by user");
      }
      synchronized (prelaunchedApp.getLock()) {
        try {
          prelaunchedApp.setStage(10);
        } catch (Throwable th3) {
          th = th3;
          while (true) {
            try {
              throw th;
            } catch (Throwable th4) {
              th = th4;
            }
          }
        }
      }
      ActivityOptions makeBasic = ActivityOptions.makeBasic();
      makeBasic.setAvoidMoveToFront();
      makeBasic.setPrelaunch();
      runWatchDogForApp(prelaunchedApp);
      Slog.d("PRELService", "Start activity");
      WaitResult startActivityAndWait =
          this.mAm.mActivityTaskManager.startActivityAndWait(
              null,
              "com.samsung.speg",
              null,
              launchIntentForPackage,
              launchIntentForPackage.getType(),
              null,
              null,
              0,
              0,
              null,
              makeBasic.toBundle(),
              userId);
      if (!ActivityManager.isStartResultSuccessful(startActivityAndWait.result)) {
        throw new RuntimeException(
            "Failed to start " + str + ", res=" + startActivityAndWait.result);
      }
      Slog.d("PRELService", "Activity started, pid " + prelaunchedApp.getPid());
      synchronized (prelaunchedApp.getLock()) {
        prelaunchedApp.setStage(15);
      }
      waitForAppTillStageInternal(prelaunchedApp, 30);
      prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(false);
      checkStateAndClearIfNeeded(prelaunchedApp);
      if (prelaunchedApp.getStage() == 40 || prelaunchedApp.getStage() == 50) {
        Slog.i(
            "PRELService",
            "App "
                + prelaunchedApp.getPackageName()
                + " prelaunched, pid: "
                + prelaunchedApp.getPid());
        z = true;
      } else {
        removePrelaunchedApp(prelaunchedApp);
      }
      str2 = "PRELService";
      sb = new StringBuilder();
      sb.append("Finish prelaunch of ");
      sb.append(prelaunchedApp);
      Slog.i(str2, sb.toString());
      return z;
    }
  }

  public final void checkStateAndClearIfNeeded(PrelaunchedApp prelaunchedApp) {
    if (prelaunchedApp.getStage() == 50) {
      return;
    }
    if (prelaunchedApp.getStage() == 30 && prelaunchedApp.isProcessAlive()) {
      prelaunchedApp.setStage(40);
      return;
    }
    if (!prelaunchedApp.isProcessAlive()) {
      Slog.e("PRELService", "App process " + prelaunchedApp.getProcessName() + " is not alive");
      if (!prelaunchedApp.getKilled()) {
        prelaunchedApp.setStage(60);
        removePrelaunchedApp(prelaunchedApp);
      }
    }
    if (prelaunchedApp.getKilled()) {
      clearPackage(prelaunchedApp.getPackageName(), prelaunchedApp.getUid());
    }
  }

  public final void runWatchDogForApp(final PrelaunchedApp prelaunchedApp) {
    executeAsync(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.this.lambda$runWatchDogForApp$0(prelaunchedApp);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$runWatchDogForApp$0(PrelaunchedApp prelaunchedApp) {
    try {
      Thread.currentThread().setName("PrelWatchDog for " + prelaunchedApp.getPackageName());
    } catch (SecurityException unused) {
    }
    Slog.d("PRELService", "Run watchDog for " + prelaunchedApp);
    while (prelaunchedApp.getAppWatchdogRemainingTimeMs() > 0) {
      try {
        Thread.sleep(prelaunchedApp.getAppWatchdogRemainingTimeMs());
      } catch (InterruptedException e) {
        Slog.e("PRELService", "WatchDog interrupted", e);
      }
    }
    synchronized (prelaunchedApp.getLock()) {
      if (prelaunchedApp.getStage() < 30) {
        Slog.w("PRELService", "WatchDog timed out for " + prelaunchedApp.getPackageName());
        killAppInternal(prelaunchedApp, "WatchDog timed out");
        prelaunchedApp.setStage(60);
      }
    }
    if (prelaunchedApp.getStage() == 60) {
      removePrelaunchedApp(prelaunchedApp);
    }
    Slog.d("PRELService", "WatchDog finished for " + prelaunchedApp);
  }

  public final void executeAsync(final Runnable runnable) {
    this.mExecutorService.execute(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.lambda$executeAsync$1(runnable);
          }
        });
  }

  public static /* synthetic */ void lambda$executeAsync$1(Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      Slog.e("PRELService", "Exception in async task", e);
    }
  }

  public void setPidForPrelaunchedAppAsync(final int i, final int i2) {
    executeAsync(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.this.lambda$setPidForPrelaunchedAppAsync$2(i, i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setPidForPrelaunchedAppAsync$2(int i, int i2) {
    PrelaunchedApp prelaunchedApp;
    synchronized (this.mLock) {
      prelaunchedApp = (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(i));
    }
    if (prelaunchedApp == null) {
      return;
    }
    prelaunchedApp.setPid(i2);
  }

  public void setTaskProcessedForPrelaunchedAppAsync(final int i) {
    executeAsync(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.this.lambda$setTaskProcessedForPrelaunchedAppAsync$3(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setTaskProcessedForPrelaunchedAppAsync$3(int i) {
    PrelaunchedApp prelaunchedApp;
    synchronized (this.mLock) {
      prelaunchedApp = (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(i));
    }
    if (prelaunchedApp == null) {
      return;
    }
    Slog.d("PRELService", "Activity stopped " + prelaunchedApp);
    synchronized (prelaunchedApp.getLock()) {
      if (prelaunchedApp.getStage() < 30) {
        prelaunchedApp.setStage(30);
      }
    }
  }

  public boolean isAppPrelaunched(int i) {
    boolean prelaunched;
    synchronized (this.mLock) {
      PrelaunchedApp prelaunchedApp =
          (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(i));
      prelaunched = prelaunchedApp != null ? prelaunchedApp.getPrelaunched() : false;
    }
    return prelaunched;
  }

  public boolean isAppPrelaunched(String str, int i) {
    int packageUid = getPackageUid(str, i);
    if (packageUid != -1) {
      return isAppPrelaunched(packageUid);
    }
    return false;
  }

  public void setStartExecutionComplete(final String str, final int i) {
    executeAsync(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.this.lambda$setStartExecutionComplete$4(str, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setStartExecutionComplete$4(String str, int i) {
    PrelaunchedApp prelaunchedApp;
    int packageUid = getPackageUid(str, i);
    if (packageUid == -1) {
      return;
    }
    synchronized (this.mLock) {
      prelaunchedApp = (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(packageUid));
    }
    if (prelaunchedApp == null) {
      Slog.w("PRELService", "Try to set stage for non prelaunched app " + packageUid);
      return;
    }
    synchronized (prelaunchedApp.getLock()) {
      prelaunchedApp.setStage(13);
    }
  }

  public boolean stopPrelaunch(int i, String str) {
    PrelaunchedApp prelaunchedApp;
    synchronized (this.mLock) {
      prelaunchedApp = (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(i));
    }
    if (prelaunchedApp == null || !prelaunchedApp.getPrelaunched()) {
      Slog.w("PRELService", "Try to stop non-prelaunched app: " + i);
      return false;
    }
    synchronized (prelaunchedApp.getLock()) {
      if (prelaunchedApp.getStage() >= 50) {
        return false;
      }
      killAppInternal(prelaunchedApp, str);
      prelaunchedApp.setStage(60);
      removePrelaunchedApp(prelaunchedApp);
      return true;
    }
  }

  public boolean killApp(int i, String str) {
    if (!isCallerUidAllowed()) {
      Slog.e("PRELService", "Caller does not have permission to kill prelaunched app");
      return false;
    }
    return stopPrelaunch(i, str);
  }

  public final void killAppInternal(PrelaunchedApp prelaunchedApp, String str) {
    if (prelaunchedApp.getKilled()) {
      Slog.w("PRELService", "App is already killed " + prelaunchedApp);
      return;
    }
    try {
      Slog.i("PRELService", "Killing " + prelaunchedApp.getPackageName() + ". Reason: " + str);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAm.killApplication(
            prelaunchedApp.getPackageName(),
            UserHandle.getAppId(prelaunchedApp.getUid()),
            prelaunchedApp.getUserId(),
            "prel",
            13);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (prelaunchedApp.isProcessAlive()) {
          Process.waitForProcessDeath(prelaunchedApp.getPid(), 500);
        }
        Slog.i(
            "PRELService",
            "Prelaunched " + prelaunchedApp.getPackageName() + " has been killed. Reason: " + str);
        synchronized (prelaunchedApp.getLock()) {
          prelaunchedApp.setDeathReason(str);
          prelaunchedApp.setKilled();
          if (prelaunchedApp.getStage() < 30) {
            prelaunchedApp.setStage(30);
          }
        }
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (Exception e) {
      Slog.e(
          "PRELService",
          "Failed to kill " + prelaunchedApp.getPackageName() + ", pid " + prelaunchedApp.getPid(),
          e);
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (Build.IS_DEBUGGABLE) {
      Slog.d("PRELService", "Dump service requested for " + Binder.getCallingUid());
      synchronized (this.mLock) {
        printWriter.println("History: " + this.mPrelaunchedAppHistory.size());
        for (PrelaunchedApp prelaunchedApp : this.mPrelaunchedAppHistory) {
          boolean isProcessAlive = prelaunchedApp.isProcessAlive();
          printWriter.print(prelaunchedApp);
          StringBuilder sb = new StringBuilder();
          sb.append(", process is ");
          sb.append(isProcessAlive ? "still alive" : "killed");
          printWriter.println(sb.toString());
        }
        printWriter.println("");
        printWriter.println("Active prelaunched apps: " + this.mPrelaunchedApps.size());
        for (PrelaunchedApp prelaunchedApp2 : this.mPrelaunchedApps.values()) {
          printWriter.print(prelaunchedApp2);
          StringBuilder sb2 = new StringBuilder();
          sb2.append(", process is ");
          sb2.append(prelaunchedApp2.isProcessAlive() ? "still alive" : "killed");
          printWriter.println(sb2.toString());
        }
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    if (Build.IS_DEBUGGABLE) {
      new AppPrelaunchShellCommand(this)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    } else {
      resultReceiver.send(-1, null);
    }
  }

  public void registerBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
    intentFilter.addDataScheme("package");
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            AppPrelaunchService.this.performInstallActionBroadcast(intent);
          }
        },
        intentFilter);
  }

  public void bootCompletedBroadcastReceiver() {
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if (AppPrelaunchService.this.mScpmController != null) {
              AppPrelaunchService.this.mScpmController.registerScpm(
                  AppPrelaunchService.this.mContext);
            }
          }
        },
        new IntentFilter("com.samsung.intent.action.LAZY_BOOT_COMPLETE"));
  }

  public final void performInstallActionBroadcast(Intent intent) {
    if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
      if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
        if (!FORCE_PREL) {
          return;
        } else {
          Slog.d("PRELService", "Forced prelaunch existed app");
        }
      }
      Slog.d("PRELService", "Received intent: " + intent);
      final String packageNameFromIntent = getPackageNameFromIntent(intent);
      final int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
      if (packageNameFromIntent != null && intExtra != -1) {
        executeAsync(
            new Runnable() { // from class:
              // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                AppPrelaunchService.this.lambda$performInstallActionBroadcast$5(
                    packageNameFromIntent, intExtra);
              }
            });
        return;
      }
      Slog.w(
          "PRELService",
          "Received incomplete intent. Package name: "
              + packageNameFromIntent
              + ", uid: "
              + intExtra);
    }
  }

  public final boolean isDexoptLimited() {
    return SystemProperties.getBoolean("sys.dexopt.ctrl", false);
  }

  public final boolean isDeviceInteractive() {
    return ((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive()
        && !((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class))
            .isKeyguardLocked();
  }

  /* JADX WARN: Code restructure failed: missing block: B:35:0x0072, code lost:

     if (r2 == null) goto L29;
  */
  /* JADX WARN: Removed duplicated region for block: B:41:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static Set getIncludedLibrariesFromApk(String str) {
    StrictJarFile strictJarFile;
    Object e;
    HashSet hashSet = new HashSet();
    StrictJarFile strictJarFile2 = null;
    try {
      strictJarFile = new StrictJarFile(str, false, false);
      try {
        try {
          Iterator it = strictJarFile.iterator();
          while (it.hasNext()) {
            ZipEntry zipEntry = (ZipEntry) it.next();
            if (zipEntry.getName().startsWith("lib")) {
              String name = zipEntry.getName();
              int lastIndexOf = name.lastIndexOf(File.separator);
              if (lastIndexOf != -1) {
                name = name.substring(lastIndexOf + 1);
              }
              if (zipEntry.getMethod() == 0) {
                hashSet.add(name);
              }
            }
          }
        } catch (IOException | SecurityException e2) {
          e = e2;
          Slog.w("PRELService", "Cannot read " + str + ", error: " + e);
        }
      } catch (Throwable th) {
        th = th;
        strictJarFile2 = strictJarFile;
        if (strictJarFile2 != null) {
          try {
            strictJarFile2.close();
          } catch (IOException unused) {
          }
        }
        throw th;
      }
    } catch (IOException | SecurityException e3) {
      strictJarFile = null;
      e = e3;
    } catch (Throwable th2) {
      th = th2;
      if (strictJarFile2 != null) {}
      throw th;
    }
    try {
      strictJarFile.close();
    } catch (IOException unused2) {
    }
    return hashSet;
  }

  public final boolean isExtractedNativeLibInBlockList(AndroidPackage androidPackage) {
    Set blockedLibs = this.mStorage.getBlockedLibs(this.nativeLibBlockList);
    if (blockedLibs.isEmpty() || androidPackage.getNativeLibraryDir() == null) {
      return false;
    }
    File file = new File(androidPackage.getNativeLibraryDir());
    try {
      if (file.isDirectory()) {
        if (file.canRead()) {
          for (File file2 : file.listFiles()) {
            if (this.mSpeg.isInBlockList(file2.getName(), blockedLibs)) {
              return true;
            }
          }
        }
      }
      return false;
    } catch (SecurityException e) {
      Slog.e("PRELService", "SecurityException occurred while checking libraries", e);
      return true;
    }
  }

  public final boolean isIncludedNativeLibInBlockList(AndroidPackage androidPackage) {
    Set blockedLibs = this.mStorage.getBlockedLibs(this.nativeLibBlockList);
    if (blockedLibs.isEmpty()) {
      return false;
    }
    try {
      Set includedLibrariesFromApk = getIncludedLibrariesFromApk(androidPackage.getBaseApkPath());
      for (String str : androidPackage.getSplitCodePaths()) {
        includedLibrariesFromApk.addAll(getIncludedLibrariesFromApk(str));
      }
      Iterator it = includedLibrariesFromApk.iterator();
      while (it.hasNext()) {
        if (this.mSpeg.isInBlockList((String) it.next(), blockedLibs)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      Slog.e("PRELService", "Exception occurred while checking libraries", e);
      return true;
    }
  }

  public final boolean shouldSkipPrelForPackage(AndroidPackage androidPackage) {
    if (this.mStorage.getScpmStopRule()) {
      Slog.d("PRELService", "Feature is disabled by SCPM");
      return true;
    }
    if (isDexoptLimited()) {
      Slog.i("PRELService", "Feature is disabled due to high temperature");
      return true;
    }
    if (this.mSmartSwitchState) {
      Slog.d("PRELService", "Feature is disabled until SmartSwitch is finished");
      return true;
    }
    if (!this.mSetupWizardFinished) {
      Slog.d("PRELService", "Feature is disabled until setup wizard is finished");
      return true;
    }
    String packageName = androidPackage.getPackageName();
    int userId = UserHandle.getUserId(androidPackage.getUid());
    ApplicationInfo applicationInfo =
        this.mPmInternal.getApplicationInfo(packageName, 0L, 1000, userId);
    if (applicationInfo == null
        || applicationInfo.isPrivilegedApp()
        || applicationInfo.isSystemApp()
        || applicationInfo.isSystemExt()
        || applicationInfo.isVendor()
        || applicationInfo.isOem()
        || applicationInfo.isSignedWithPlatformKey()
        || applicationInfo.isOdm()) {
      Slog.i("PRELService", "Feature is disabled for system apps");
      return true;
    }
    if (isPackageBlockListed(packageName)) {
      Slog.d("PRELService", "Feature is disabled for package " + packageName);
      return true;
    }
    if (!androidPackage.getInstrumentations().isEmpty()) {
      Slog.d("PRELService", "Feature is disabled for instrumented package " + packageName);
      return true;
    }
    Bundle metaData = androidPackage.getMetaData();
    if (metaData != null
        && metaData.getBoolean("com.samsung.android.speg.prelauncher.disabled", false)) {
      Slog.d("PRELService", "Feature is disabled in app manifest " + packageName);
      return true;
    }
    if (IncrementalManager.isIncrementalPath(androidPackage.getPath())) {
      Slog.d("PRELService", "Feature is disabled for incremental-fs");
      return true;
    }
    if (androidPackage.getSharedUserId() != null) {
      Slog.d("PRELService", "Feature is disabled for shared package");
      return true;
    }
    if (this.mPmInternal.isPermissionsReviewRequired(packageName, userId)) {
      Slog.d("PRELService", "Feature is disabled for app requires permission review");
      return true;
    }
    try {
      if ("com.sec.android.easyMover"
          .equals(
              this.mContext
                  .getPackageManager()
                  .getInstallSourceInfo(packageName)
                  .getInstallingPackageName())) {
        Slog.d("PRELService", "Feature is disabled for smart switch installer");
        return true;
      }
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.e("PRELService", "Cannot find an installation info for " + packageName);
    }
    if (isKidsHome()) {
      Slog.d("PRELService", "Feature is disabled for " + packageName + " due to KidsHome");
      return true;
    }
    if (androidPackage.isDebuggable()) {
      Slog.d("PRELService", "Feature is disabled for debuggable " + packageName);
      return true;
    }
    if (androidPackage.isSaveStateDisallowed()) {
      Slog.d("PRELService", "Feature is disabled for heavy-weight " + packageName);
      return true;
    }
    for (FeatureInfo featureInfo : androidPackage.getRequestedFeatures()) {
      String str = featureInfo.name;
      if (str != null && str.startsWith("android.hardware.bluetooth") && featureInfo.flags != 0) {
        Slog.d("PRELService", "Feature is disabled for apps that require BT");
        return true;
      }
    }
    if (this.mSpeg.hasPrivilegedPermissions(androidPackage)) {
      Slog.i("PRELService", "Feature is disabled for privileged apps");
      return true;
    }
    if (this.mPermissionManager.checkUidPermission(
            androidPackage.getUid(), "android.permission.SET_WALLPAPER")
        == 0) {
      Slog.d("PRELService", "Feature is disabled, app has android.permission.SET_WALLPAPER");
      return true;
    }
    if (isExtractedNativeLibInBlockList(androidPackage)
        || isIncludedNativeLibInBlockList(androidPackage)) {
      Slog.d(
          "PRELService", "Feature is disabled for " + packageName + " due to unsupported library");
      return true;
    }
    synchronized (this.mLock) {
      if (this.mPrelaunchedApps.size() >= ACTIVE_APPS_LIMIT) {
        Slog.d("PRELService", "Feature is disabled due to limit on number of prelaunched apps");
        return true;
      }
      if (this.mPrelaunchedAppHistory.size() >= PRELAUNCH_ATTEMPTS_LIMIT) {
        Slog.d("PRELService", "Feature is disabled due to limit on number of prelaunch attempts");
        return true;
      }
      if (isDeviceInteractive()) {
        return false;
      }
      Slog.d("PRELService", "Feature is disabled in non-interactive device state");
      return true;
    }
  }

  public void setSetupWizardFinished(boolean z) {
    this.mSetupWizardFinished = z;
  }

  public void setSmartSwitchState(boolean z) {
    this.mSmartSwitchState = z;
  }

  public final boolean isKidsHome() {
    return "com.sec.android.app.kidshome"
        .equals(this.mPmInternal.getDefaultHomeActivity(0).getPackageName());
  }

  public final String getPackageNameFromIntent(Intent intent) {
    Uri data = intent.getData();
    if (data != null) {
      return data.getSchemeSpecificPart();
    }
    return null;
  }

  public final boolean isCallerUidAllowed() {
    int callingUid = Binder.getCallingUid();
    return callingUid == 1000 || callingUid == 0;
  }

  public final boolean isPackageBlockListed(String str) {
    return this.mSpeg.isInBlockList(str, this.mStorage.getBlockedPackages(this.packageBlockList));
  }

  public class PrelaunchedApp {
    public int currentStage;
    public final int finalStage;
    public boolean isBeingPrelaunched;
    public boolean isKilled;
    public final boolean mHasWakeLockPermission;
    public boolean mWakeLockRevoked;
    public final String packageName;
    public final String processName;
    public final File spegMarkerFileMisc;
    public final File spegMarkerFilePkg;
    public final int uid;
    public final boolean DEBUG = Build.IS_DEBUGGABLE;
    public int mPid = -1;
    public String deathReason = null;
    public final long prelaunchedTimeNs = System.nanoTime();
    public long mAppWatchdogTimeMs = AppPrelaunchService.PREL_APP_START_WATCHDOG_TIMEOUT_MS;

    public PrelaunchedApp(AndroidPackage androidPackage, int i, int i2) {
      this.packageName = androidPackage.getPackageName();
      this.processName = androidPackage.getProcessName();
      int uid = androidPackage.getUid();
      this.uid = uid;
      this.spegMarkerFileMisc = new File("/data/misc/speg/speg." + uid);
      this.spegMarkerFilePkg =
          new File(androidPackage.getPath() + File.separator + "base.speg" + uid);
      this.finalStage = i;
      this.currentStage = i2;
      this.isBeingPrelaunched = true;
      this.mHasWakeLockPermission =
          androidPackage.getRequestedPermissions().contains("android.permission.WAKE_LOCK");
    }

    public boolean createOrDeleteMarkerFiles(boolean z) {
      boolean z2;
      long nanoTime = System.nanoTime();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (z) {
          z2 =
              AppPrelaunchService.this.mSpeg.createOrDeleteMarkerFiles(
                  this.spegMarkerFilePkg.getAbsolutePath(), true, getUid());
        } else {
          z2 = false;
          try {
            z2 = this.spegMarkerFilePkg.delete();
            z2 &= this.spegMarkerFileMisc.delete();
          } catch (SecurityException unused) {
            Slog.e("PRELService", "Failed to delete marker files for " + getPackageName());
          }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        clearCallingIdentity = System.nanoTime();
        if (this.DEBUG) {
          StringBuilder sb = new StringBuilder();
          sb.append(z ? "Creating" : "Deleting");
          sb.append(" marker files took ");
          sb.append(TimeUnit.NANOSECONDS.toMicros(clearCallingIdentity - nanoTime));
          sb.append(" us. Ret ");
          sb.append(z2);
          Slog.i("PRELService", sb.toString());
        }
        return z2;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }

    public synchronized void setStage(int i) {
      if (i <= this.currentStage) {
        if (this.DEBUG) {
          Slog.d(
              "PRELService",
              "Try to downgrade stage " + this.currentStage + " to " + i,
              new RuntimeException());
        }
        return;
      }
      if (i >= 50) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          try {
            AppPrelaunchService.this.restrictNetworkConnection(this.uid, false);
          } catch (Exception e) {
            Slog.e("PRELService", "Failed to restore network connection for " + this.uid, e);
          }
          createOrDeleteMarkerFiles(false);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          setPrelaunched(false);
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
      this.currentStage = i;
      notifyAll();
    }

    public PrelaunchedApp getLock() {
      if (this.DEBUG && !Thread.holdsLock(this)) {
        Slog.d("PRELService", "Get lock of " + this + " from " + Debug.getCallers(0, 6));
      }
      return this;
    }

    public synchronized int getStage() {
      return this.currentStage;
    }

    public boolean getPrelaunched() {
      return this.isBeingPrelaunched;
    }

    public void setPrelaunched(boolean z) {
      this.isBeingPrelaunched = z;
    }

    public String getPackageName() {
      return this.packageName;
    }

    public int getUid() {
      return this.uid;
    }

    public String getProcessName() {
      return this.processName;
    }

    public synchronized int getPid() {
      return this.mPid;
    }

    public synchronized void setPid(int i) {
      if (this.mPid != -1) {
        return;
      }
      this.mPid = i;
    }

    public boolean isProcessAlive() {
      return (getKilled()
              || getPid() == -1
              || getPid() != AppPrelaunchService.this.mSpeg.getPidOf(getProcessName(), getUid()))
          ? false
          : true;
    }

    public int getUserId() {
      return UserHandle.getUserId(this.uid);
    }

    public void setKilled() {
      this.isKilled = true;
    }

    public boolean getKilled() {
      return this.isKilled;
    }

    public long getAppWatchogTimeoutMs() {
      return this.mAppWatchdogTimeMs;
    }

    public long getAppWatchdogRemainingTimeMs() {
      return Math.max(
          getAppWatchogTimeoutMs()
              - TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.prelaunchedTimeNs),
          0L);
    }

    public void revokeOrGrantWakeLockPermissionIfNeeded(boolean z) {
      if (this.mHasWakeLockPermission) {
        StringBuilder sb = new StringBuilder();
        sb.append(z ? "Revoking" : "Granting");
        sb.append(" WAKE_LOCK of ");
        sb.append(this.packageName);
        Slog.d("PRELService", sb.toString());
        try {
          if (z) {
            AppPrelaunchService.this.mPermissionManager.revokeInstallPermission(
                this.packageName, "android.permission.WAKE_LOCK");
            this.mWakeLockRevoked = true;
          } else if (this.mWakeLockRevoked) {
            AppPrelaunchService.this.mPermissionManager.grantInstallPermission(
                this.packageName, "android.permission.WAKE_LOCK");
            this.mWakeLockRevoked = false;
          }
        } catch (RuntimeException e) {
          Slog.w(
              "PRELService",
              "Failed to manage WAKE_LOCK of " + this.packageName + ": " + e.getMessage());
        }
      }
    }

    public void setDeathReason(String str) {
      if (this.deathReason == null) {
        this.deathReason = str;
      }
    }

    public String getDeathReason() {
      String str = this.deathReason;
      return str != null ? str : "Unknown";
    }

    public long getPrelaunchedTimeNs() {
      return this.prelaunchedTimeNs;
    }

    public String toString() {
      String str;
      long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.prelaunchedTimeNs);
      StringBuilder sb = new StringBuilder();
      sb.append("prelaunched app { ");
      sb.append(this.packageName);
      sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
      sb.append(this.uid);
      sb.append(", started: ");
      sb.append(millis / 1000.0f);
      sb.append(" s ago, stage: ");
      sb.append(this.currentStage);
      sb.append(", isUidRestricted: ");
      sb.append(this.isBeingPrelaunched);
      sb.append(", pid: ");
      sb.append(this.mPid);
      if (getStage() >= 50) {
        str = ", deathReason: " + getDeathReason();
      } else {
        str = "";
      }
      sb.append(str);
      sb.append(" }");
      return sb.toString();
    }
  }

  public final int getPackageUid(String str, int i) {
    Integer num;
    synchronized (this.mLock) {
      num = (Integer) this.mPrelaunchedAppIds.get(str);
    }
    if (num == null) {
      return -1;
    }
    return UserHandle.getUid(i, num.intValue());
  }

  public final PrelaunchedApp getPrelaunchedAppByIntent(Intent intent, int i) {
    int packageUid;
    PrelaunchedApp prelaunchedApp;
    if (intent == null
        || intent.getComponent() == null
        || (packageUid = getPackageUid(intent.getComponent().getPackageName(), i)) == -1) {
      return null;
    }
    synchronized (this.mLock) {
      prelaunchedApp = (PrelaunchedApp) this.mPrelaunchedApps.get(Integer.valueOf(packageUid));
    }
    if (prelaunchedApp == null || !prelaunchedApp.getPrelaunched()) {
      Slog.w("PRELService", "Try to handle non prelaunched activity " + intent.getComponent());
      return null;
    }
    if (prelaunchedApp.getStage() < 15 || prelaunchedApp.isProcessAlive()) {
      return prelaunchedApp;
    }
    Slog.w("PRELService", "Process did not survive for " + prelaunchedApp);
    synchronized (prelaunchedApp.getLock()) {
      prelaunchedApp.setDeathReason("App process did not survive");
      prelaunchedApp.setStage(60);
    }
    removePrelaunchedApp(prelaunchedApp);
    return null;
  }

  public int handleActivityExecution(
      Intent intent, int i, int i2, ActivityOptions activityOptions) {
    PrelaunchedApp prelaunchedAppByIntent = getPrelaunchedAppByIntent(intent, i);
    if (prelaunchedAppByIntent == null) {
      return -96;
    }
    if (prelaunchedAppByIntent.getUid() == i2) {
      Slog.d(
          "PRELService",
          "App tries to start new activity while prelaunch "
              + prelaunchedAppByIntent
              + ", callingUid "
              + i2);
      if (prelaunchedAppByIntent.getStage() < 30) {
        Slog.d("PRELService", "Allow start " + intent);
        activityOptions.setPrelaunch();
      } else {
        Slog.d("PRELService", "Don't resume " + intent);
      }
      activityOptions.setAvoidMoveToFront();
      return -96;
    }
    if (Thread.holdsLock(this.mAm.mActivityTaskManager.getGlobalLock())) {
      Slog.w("PRELService", "GlobalLock has already been acquired");
      activityOptions.setPrelaunch();
      activityOptions.setAvoidMoveToFront();
      return -96;
    }
    return startPrelaunchedAppByUser(prelaunchedAppByIntent, intent, activityOptions.toBundle());
  }

  public final int startPrelaunchedAppByUser(
      PrelaunchedApp prelaunchedApp, Intent intent, Bundle bundle) {
    boolean z;
    prelaunchedApp.getPid();
    Slog.i("PRELService", "User starts " + prelaunchedApp);
    synchronized (prelaunchedApp.getLock()) {
      if (prelaunchedApp.getStage() >= 20) {
        waitForAppTillStageInternal(prelaunchedApp, 30);
        z = false;
      } else {
        prelaunchedApp.setStage(20);
        z = true;
      }
    }
    if (z) {
      prelaunchedApp.revokeOrGrantWakeLockPermissionIfNeeded(false);
    }
    synchronized (prelaunchedApp.getLock()) {
      prelaunchedApp.setStage(50);
      prelaunchedApp.setDeathReason("Untracked because user started");
    }
    removePrelaunchedApp(prelaunchedApp);
    return -96;
  }

  public final boolean waitForAppTillStageInternal(PrelaunchedApp prelaunchedApp, int i) {
    Slog.d("PRELService", "Wait for app finish " + prelaunchedApp + " till stage: " + i);
    try {
      synchronized (prelaunchedApp.getLock()) {
        while (prelaunchedApp.getStage() < i) {
          if (prelaunchedApp.getAppWatchdogRemainingTimeMs() == 0) {
            Slog.w(
                "PRELService",
                "Possible endless waiting " + prelaunchedApp + " till stage: " + i,
                new RuntimeException());
            Slog.d("PRELService", "Finish waiting for " + prelaunchedApp + " till stage: " + i);
            return false;
          }
          try {
            prelaunchedApp.wait(prelaunchedApp.getAppWatchdogRemainingTimeMs() + 1);
          } catch (InterruptedException unused) {
          }
        }
        Slog.d("PRELService", "Finish waiting for " + prelaunchedApp + " till stage: " + i);
        return true;
      }
    } catch (Throwable th) {
      Slog.d("PRELService", "Finish waiting for " + prelaunchedApp + " till stage: " + i);
      throw th;
    }
  }

  public final void restrictNetworkConnection(final int i, final boolean z) {
    if (!RESTRICT_NETWORK) {
      Slog.i("PRELService", "Skip network connection restriction for uid " + i);
      return;
    }
    executeAsync(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.AppPrelaunchService$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            AppPrelaunchService.this.lambda$restrictNetworkConnection$6(i, z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restrictNetworkConnection$6(int i, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        this.mNms.spegRestrictNetworkConnection(i, z);
      } catch (Exception e) {
        Slog.e("PRELService", "Failed to set network connection rules for uid " + i, e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void clearPackage(String str, int i) {
    try {
      this.mAm.mAtmInternal.onPackagesSuspendedChanged(
          new String[] {str}, true, UserHandle.getUserId(i));
    } catch (Exception e) {
      Slog.e("PRELService", "Can't remove recent task for " + str + ", error: " + e);
    }
    try {
      ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class))
          .cancelJobsForUid(i, true, 14, 8, "clear data");
    } catch (Exception e2) {
      Slog.e("PRELService", "Can't clear scheduled jobs for " + str + ", error: " + e2);
    }
    try {
      ((AlarmManagerInternal) LocalServices.getService(AlarmManagerInternal.class))
          .removeAlarmsForUid(i);
    } catch (Exception e3) {
      Slog.e("PRELService", "Can't clear pending alarms for " + str + ", error: " + e3);
    }
  }

  public boolean isSkippableExitReason(ApplicationExitInfo applicationExitInfo) {
    return applicationExitInfo.getSubReason() == 0
        && (applicationExitInfo.getReason() == 0 || applicationExitInfo.getReason() == 2);
  }

  public boolean isRestartedApp(
      PrelaunchedApp prelaunchedApp, ApplicationExitInfo applicationExitInfo) {
    return prelaunchedApp.getStage() <= 15
        && TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - prelaunchedApp.getPrelaunchedTimeNs())
            < 500
        && isSkippableExitReason(applicationExitInfo);
  }

  public void handlePrelaunchedAppDied(ApplicationExitInfo applicationExitInfo) {
    PrelaunchedApp prelaunchedApp;
    synchronized (this.mLock) {
      prelaunchedApp =
          (PrelaunchedApp)
              this.mPrelaunchedApps.get(Integer.valueOf(applicationExitInfo.getPackageUid()));
    }
    if (prelaunchedApp == null) {
      return;
    }
    String reasonsAsString = applicationExitInfo.getReasonsAsString();
    if (isRestartedApp(prelaunchedApp, applicationExitInfo)) {
      Slog.d(
          "PRELService",
          "Skip handling of process death for "
              + prelaunchedApp
              + " with reason "
              + reasonsAsString);
      return;
    }
    Slog.d("PRELService", "Process die of " + prelaunchedApp + ", reason: " + reasonsAsString);
    synchronized (prelaunchedApp.getLock()) {
      killAppInternal(prelaunchedApp, reasonsAsString);
      prelaunchedApp.setStage(60);
    }
    removePrelaunchedApp(prelaunchedApp);
  }
}
