package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.net.Uri;
import android.os.Binder;
import android.util.Slog;
import android.util.SparseArray;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.CompatChangeableApps;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.util.SafetySystemService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class PackagesChange {
  public final ActivityTaskManagerService mAtmService;
  public final ConcurrentHashMap mCachedHomeActivities = new ConcurrentHashMap();
  public final String mControllerName = getClass().getSimpleName();
  public PackageManagerInternal mPackageManagerInternal;
  public PackageFeatureUserChange[] mUserChanges;
  public static final List sAllPackagesChange = new ArrayList();
  public static final List sAllPackagesChangeAsTask = new ArrayList();
  public static final String[] EXCLUDED_PACKAGE_PREFIX = {"com.samsung.", "com.sec."};

  public abstract class LazyHolder {
    public static final CompatChangeableAppsManager sCompatChangeableAppsManager =
        new CompatChangeableAppsManager();
  }

  public void dump(PrintWriter printWriter, String str) {}

  public boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
    return false;
  }

  public static void addPackagesChange(PackagesChange packagesChange) {
    sAllPackagesChange.add(packagesChange);
  }

  public static void addPackagesChangeAsTask(PackagesChangeAsTask packagesChangeAsTask) {
    sAllPackagesChangeAsTask.add(packagesChangeAsTask);
  }

  public static void updateAllValueToTask(Task task) {
    Iterator it = sAllPackagesChangeAsTask.iterator();
    while (it.hasNext()) {
      ((PackagesChangeAsTask) it.next()).updateValueToTask(task, false);
    }
  }

  public static void onAllDumpInTask(PrintWriter printWriter, String str, Task task) {
    Iterator it = sAllPackagesChangeAsTask.iterator();
    while (it.hasNext()) {
      ((PackagesChangeAsTask) it.next()).onDumpInTask(printWriter, str, task);
    }
  }

  public static void resetAllIfNeeded(int i, int i2) {
    Iterator it = sAllPackagesChange.iterator();
    while (it.hasNext()) {
      ((PackagesChange) it.next()).resetIfNeeded(i, i2);
    }
  }

  public static void dumpAll(PrintWriter printWriter, String str) {
    printWriter.println();
    printWriter.println(str + "PACKAGE SETTINGS MANAGER");
    String str2 = str + "  ";
    for (PackagesChange packagesChange : sAllPackagesChange) {
      printWriter.println(str2 + packagesChange.mControllerName);
      packagesChange.dump(printWriter, str2 + "  ");
    }
    printWriter.println(str + "PACKAGE SETTINGS MANAGER - USER CHANGES");
    Iterator it = sAllPackagesChange.iterator();
    while (it.hasNext()) {
      ((PackagesChange) it.next()).dumpUserChanges(printWriter, str2);
    }
    if (CoreRune.SAFE_DEBUG) {
      LazyHolder.sCompatChangeableAppsManager.dump(printWriter, str2);
    }
    printWriter.println();
  }

  public static boolean executeAllShellCommand(
      String str, String[] strArr, PrintWriter printWriter) {
    Iterator it = sAllPackagesChange.iterator();
    while (it.hasNext()) {
      if (((PackagesChange) it.next()).executeShellCommand(str, strArr, printWriter)) {
        return true;
      }
    }
    return false;
  }

  public PackagesChange(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtmService = activityTaskManagerService;
    addPackagesChange(this);
  }

  public final void setUserChanges(PackageFeatureUserChange... packageFeatureUserChangeArr) {
    this.mUserChanges = packageFeatureUserChangeArr;
  }

  public final void resetIfNeeded(int i, int i2) {
    PackageFeatureUserChange[] packageFeatureUserChangeArr = this.mUserChanges;
    if (packageFeatureUserChangeArr == null) {
      return;
    }
    for (PackageFeatureUserChange packageFeatureUserChange : packageFeatureUserChangeArr) {
      if ((packageFeatureUserChange.getIdentityFlag() & i2)
          == packageFeatureUserChange.getIdentityFlag()) {
        packageFeatureUserChange.reset(i);
      }
    }
  }

  public boolean isSettingsPackage(String str) {
    return "com.android.settings".equals(str);
  }

  public List getLauncherActivities(String str, int i) {
    PackageManagerInternal packageManagerInternal = this.mAtmService.mWindowManager.mPmInternal;
    this.mPackageManagerInternal = packageManagerInternal;
    if (packageManagerInternal == null) {
      return Collections.emptyList();
    }
    Intent intent =
        new Intent("android.intent.action.MAIN")
            .addCategory("android.intent.category.LAUNCHER")
            .setPackage(str);
    return this.mPackageManagerInternal.queryIntentActivities(
        intent,
        intent.resolveTypeIfNeeded(this.mAtmService.mContext.getContentResolver()),
        786432L,
        Binder.getCallingUid(),
        i);
  }

  public boolean hasLauncherActivity(String str, int i) {
    return LazyHolder.sCompatChangeableAppsManager
        .getCompatChangeableApps(i)
        .hasLauncherActivity(str);
  }

  public boolean hasGameCategory(String str, int i) {
    return LazyHolder.sCompatChangeableAppsManager.getCompatChangeableApps(i).hasGameCategory(str);
  }

  public boolean isOrientationOverrideDisallowed(String str, int i) {
    return LazyHolder.sCompatChangeableAppsManager
        .getCompatChangeableApps(i)
        .isOrientationOverrideDisallowed(str);
  }

  public boolean isMinAspectRatioOverrideDisallowed(String str, int i) {
    return LazyHolder.sCompatChangeableAppsManager
        .getCompatChangeableApps(i)
        .isMinAspectRatioOverrideDisallowed(str);
  }

  public final void dumpUserChanges(PrintWriter printWriter, String str) {
    PackageFeatureUserChange[] packageFeatureUserChangeArr = this.mUserChanges;
    if (packageFeatureUserChangeArr == null || packageFeatureUserChangeArr.length == 0) {
      return;
    }
    for (PackageFeatureUserChange packageFeatureUserChange : packageFeatureUserChangeArr) {
      packageFeatureUserChange.dump(printWriter, this.mControllerName, str);
    }
  }

  public static int getAdjustedSecureFolderUserId(int i) {
    if (SemPersonaManager.isSecureFolderId(i)) {
      return 0;
    }
    return i;
  }

  public static int getAdjustedUserIdIfNeeded(int i) {
    if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
      return 0;
    }
    return i;
  }

  public static void removeTaskWithoutRemoveFromRecents(
      final ActivityTaskManagerService activityTaskManagerService,
      final String str,
      final int i,
      final String str2) {
    WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        activityTaskManagerService.mRootWindowContainer.forAllTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.PackagesChange$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                PackagesChange.lambda$removeTaskWithoutRemoveFromRecents$0(
                    i, str, activityTaskManagerService, str2, (Task) obj);
              }
            });
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public static /* synthetic */ void lambda$removeTaskWithoutRemoveFromRecents$0(
      int i,
      String str,
      ActivityTaskManagerService activityTaskManagerService,
      String str2,
      Task task) {
    ComponentName componentName;
    if (i == task.mUserId
        && (componentName = task.realActivity) != null
        && componentName.getPackageName().equals(str)) {
      task.clearSizeCompatMode(true, true);
      activityTaskManagerService.mTaskSupervisor.removeTask(task, true, false, str2);
    }
  }

  public class CompatChangeableAppsManager {
    public ActivityTaskManagerService mAtmService;
    public SparseArray mCompatChangeableAppsArray;
    public Runnable mRegisterPackageReceiverRunnable;

    public CompatChangeableAppsManager() {
      SafetySystemService.registerForSystemReady(
          new SafetySystemService
              .Callback() { // from class:
                            // com.android.server.wm.PackagesChange$CompatChangeableAppsManager$$ExternalSyntheticLambda1
            @Override // com.samsung.android.server.util.SafetySystemService.Callback
            public final void onSystemReady(ActivityTaskManagerService activityTaskManagerService) {
              PackagesChange.CompatChangeableAppsManager.this.lambda$new$0(
                  activityTaskManagerService);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(
        ActivityTaskManagerService activityTaskManagerService) {
      synchronized (this) {
        this.mAtmService = activityTaskManagerService;
      }
    }

    public CompatChangeableApps getCompatChangeableApps(int i) {
      CompatChangeableApps compatChangeableApps;
      synchronized (this) {
        if (this.mAtmService != null && this.mRegisterPackageReceiverRunnable == null) {
          Runnable runnable =
              new Runnable() { // from class:
                               // com.android.server.wm.PackagesChange$CompatChangeableAppsManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  PackagesChange.CompatChangeableAppsManager.this.registerPackageReceiver();
                }
              };
          this.mRegisterPackageReceiverRunnable = runnable;
          this.mAtmService.f1734mH.post(runnable);
        }
      }
      synchronized (this) {
        if (this.mCompatChangeableAppsArray == null) {
          this.mCompatChangeableAppsArray = new SparseArray();
        }
        compatChangeableApps = (CompatChangeableApps) this.mCompatChangeableAppsArray.get(i);
      }
      if (compatChangeableApps == null) {
        compatChangeableApps = new CompatChangeableApps(i, true);
        synchronized (this) {
          this.mCompatChangeableAppsArray.put(i, compatChangeableApps);
        }
      }
      return compatChangeableApps;
    }

    public void dump(final PrintWriter printWriter, String str) {
      synchronized (this) {
        if (this.mCompatChangeableAppsArray == null) {
          return;
        }
        printWriter.println(str + "Compat changeable apps");
        final String str2 = str + "  ";
        forAllCompatChangeableApps(
            new Consumer() { // from class:
                             // com.android.server.wm.PackagesChange$CompatChangeableAppsManager$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((CompatChangeableApps) obj).dump(printWriter, str2);
              }
            });
      }
    }

    public final void registerPackageReceiver() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
      intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      intentFilter.addDataScheme("package");
      this.mAtmService.mContext.registerReceiver(
          new BroadcastReceiver() { // from class:
                                    // com.android.server.wm.PackagesChange.CompatChangeableAppsManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              Uri data = intent.getData();
              if (data == null) {
                Slog.w("PackageSettingsManager", "Failed to get package name in package receiver");
              }
              String schemeSpecificPart = data.getSchemeSpecificPart();
              String action = intent.getAction();
              if (action == null) {
                Slog.w("PackageSettingsManager", "Failed to get action in package receiver");
                return;
              }
              switch (action) {
                case "android.intent.action.PACKAGE_CHANGED":
                case "android.intent.action.PACKAGE_REMOVED":
                case "android.intent.action.PACKAGE_ADDED":
                  CompatChangeableAppsManager.this.removePackageInAppsArray(schemeSpecificPart);
                  break;
                default:
                  Slog.w(
                      "PackageSettingsManager",
                      "Unsupported action in package receiver: " + action);
                  break;
              }
            }
          },
          intentFilter,
          null,
          null);
    }

    public final void removePackageInAppsArray(final String str) {
      synchronized (this) {
        if (this.mCompatChangeableAppsArray == null) {
          return;
        }
        forAllCompatChangeableApps(
            new Consumer() { // from class:
                             // com.android.server.wm.PackagesChange$CompatChangeableAppsManager$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((CompatChangeableApps) obj).removeCache(str);
              }
            });
      }
    }

    public final void forAllCompatChangeableApps(Consumer consumer) {
      int size = this.mCompatChangeableAppsArray.size();
      for (int i = 0; i < size; i++) {
        consumer.accept((CompatChangeableApps) this.mCompatChangeableAppsArray.valueAt(i));
      }
    }
  }
}
