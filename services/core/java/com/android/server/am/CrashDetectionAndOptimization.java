package com.android.server.am;

import android.app.AppGlobals;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.LocalManagerRegistry;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.dex.DexoptOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class CrashDetectionAndOptimization {
  public static List dexOptimizedPackages = new ArrayList();
  public static List crashPackages = new ArrayList();

  enum CrashType {
    JAVA_CRASH,
    NATIVE_CRASH
  }

  public class CrashPackage {
    public int crashCount;
    public long mTimeStamp;
    public String packageName;

    public CrashPackage() {
      this.mTimeStamp = -1L;
      this.crashCount = 0;
    }

    public void addCrashPackage(String str) {
      this.mTimeStamp = System.currentTimeMillis();
      this.packageName = str;
      this.crashCount = 1;
    }

    public int incrementCrashCount() {
      int i = this.crashCount + 1;
      this.crashCount = i;
      return i;
    }
  }

  public class DexOptimizationThread extends Thread {
    public final int maxPossibleCount;
    public final ProcessRecord processRecord;

    public final int getDexoptFlags() {
      return 1031;
    }

    public final void printDexOptState(String str) {}

    public DexOptimizationThread(ProcessRecord processRecord, CrashType crashType) {
      this.processRecord = processRecord;
      this.maxPossibleCount = crashType == CrashType.JAVA_CRASH ? 2 : 4;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      handleCrashPackages();
    }

    public final void handleCrashPackages() {
      boolean z;
      if (CrashDetectionAndOptimization.crashPackages == null) {
        return;
      }
      synchronized (CrashDetectionAndOptimization.crashPackages) {
        printDexOptState("Before");
        String currentProcessName = getCurrentProcessName();
        CrashDetectionAndOptimization.crashPackages.isEmpty();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = CrashDetectionAndOptimization.crashPackages.iterator();
        while (true) {
          if (!it.hasNext()) {
            z = false;
            break;
          }
          CrashPackage crashPackage = (CrashPackage) it.next();
          if (isValidCrashPackage(crashPackage, currentProcessName)) {
            if (getTimeGapSeconds(currentTimeMillis, crashPackage.mTimeStamp) >= 240) {
              removeCrashPackage(crashPackage);
            } else if (crashPackage.incrementCrashCount() >= this.maxPossibleCount
                && !hasAlreadyDexopted(
                    currentProcessName, CrashDetectionAndOptimization.dexOptimizedPackages)) {
              doForceReDexOpt(crashPackage, currentProcessName);
            }
            z = true;
          }
        }
        if (!z) {
          if (CrashDetectionAndOptimization.crashPackages.size() == 8) {
            removeCrashPackage(0);
          } else if (CrashDetectionAndOptimization.crashPackages.size() < 8) {
            addCrashPackage(currentProcessName);
          }
        }
        printDexOptState("After");
      }
    }

    public final String getCurrentProcessName() {
      return this.processRecord.info.packageName;
    }

    public final boolean isValidCrashPackage(CrashPackage crashPackage, String str) {
      String str2;
      return (crashPackage == null
              || (str2 = crashPackage.packageName) == null
              || !str2.equals(str))
          ? false
          : true;
    }

    public final long getTimeGapSeconds(long j, long j2) {
      return (j - j2) / 1000;
    }

    public final boolean hasAlreadyDexopted(String str, List list) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        if (((String) it.next()).contains(str)) {
          return true;
        }
      }
      return false;
    }

    public final void doForceReDexOpt(CrashPackage crashPackage, String str) {
      String str2 = crashPackage.packageName;
      if (DexOptHelper.useArtService() ? dexoptWithArtService(str2) : dexoptWithLegacy(str2)) {
        addOptimizedPackage(str);
        removeCrashPackage(crashPackage);
      }
    }

    public final void addOptimizedPackage(String str) {
      if (CrashDetectionAndOptimization.dexOptimizedPackages.size() >= 4) {
        CrashDetectionAndOptimization.dexOptimizedPackages.remove(0);
      }
      CrashDetectionAndOptimization.dexOptimizedPackages.add(str);
    }

    public final void addCrashPackage(String str) {
      CrashPackage crashPackage = new CrashPackage();
      crashPackage.addCrashPackage(str);
      CrashDetectionAndOptimization.crashPackages.add(crashPackage);
    }

    public final void removeCrashPackage(CrashPackage crashPackage) {
      CrashDetectionAndOptimization.crashPackages.remove(crashPackage);
    }

    public final void removeCrashPackage(int i) {
      CrashDetectionAndOptimization.crashPackages.remove(i);
    }

    public final boolean dexoptWithArtService(String str) {
      PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
          ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class))
              .withFilteredSnapshot();
      try {
        Slog.i("CRASH_DEXOPT", "Try to re-compile: " + str);
        if (DexOptHelper.getArtManagerLocal()
                .dexoptPackage(
                    withFilteredSnapshot,
                    str,
                    new DexoptOptions(str, 24, "speed-profile", null, getDexoptFlags())
                        .convertToDexoptParams(0))
                .getFinalStatus()
            != 30) {
          if (withFilteredSnapshot == null) {
            return true;
          }
          withFilteredSnapshot.close();
          return true;
        }
        Slog.i("CRASH_DEXOPT", "dexopt fail: " + str);
        if (withFilteredSnapshot != null) {
          withFilteredSnapshot.close();
        }
        return false;
      } catch (Throwable th) {
        if (withFilteredSnapshot != null) {
          try {
            withFilteredSnapshot.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    }

    public final boolean dexoptWithLegacy(String str) {
      IPackageManager packageManager = AppGlobals.getPackageManager();
      if (packageManager == null) {
        return false;
      }
      try {
        Slog.i("CRASH_DEXOPT", "Try to re-complie: " + str);
        if (packageManager.performDexOptMode(
            str, true, "speed-profile", true, true, (String) null)) {
          return true;
        }
        Slog.i("CRASH_DEXOPT", "dexopt fail: " + str);
        return false;
      } catch (RemoteException unused) {
        Slog.w("CRASH_DEXOPT", "An error occurred when executing performDexOptMode");
        return false;
      }
    }
  }

  public void start(ProcessRecord processRecord, String str, String str2) {
    if (processRecord == null || processRecord.processName == null) {
      return;
    }
    if ("Native crash".equals(str)) {
      new DexOptimizationThread(processRecord, CrashType.NATIVE_CRASH).start();
    } else if (str2.contains("java.lang.ClassNotFoundException")) {
      new DexOptimizationThread(processRecord, CrashType.JAVA_CRASH).start();
    }
  }
}
