package com.android.server.wm;

import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Binder;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.core.IFoldStarCallback;
import com.samsung.android.core.IFoldStarManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class FoldStarManagerService extends IFoldStarManager.Stub {
  public final ActivityTaskManagerService mAtm;
  public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
  public int mRegisteredCallbackCount;

  public final boolean enforceFoldStarPermission(String str) {
    return false;
  }

  public Map getDisplayCompatPackages(int i, int i2, Map map) {
    return null;
  }

  public void initAppContinuityValueWhenReset(boolean z, boolean z2) {}

  public void setAllAppContinuityMode(int i, boolean z) {}

  public void setAppContinuityMode(String str, int i, boolean z) {}

  public void setDisplayCompatPackages(int i, Map map, boolean z) {}

  public void setFrontScreenOnWhenAppContinuityMode(boolean z) {}

  public FoldStarManagerService(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
  }

  public final void getLauncherPackages(int i, List list) {
    LauncherApps launcherApps =
        (LauncherApps) SafetySystemService.getSystemService(LauncherApps.class);
    if (launcherApps == null) {
      return;
    }
    Iterator<LauncherActivityInfo> it =
        launcherApps.getActivityList(null, UserHandle.of(i)).iterator();
    while (it.hasNext()) {
      list.add(it.next().getApplicationInfo().packageName);
    }
  }

  public void registerFoldStarCallback(IFoldStarCallback iFoldStarCallback) {
    int registeredCallbackCount;
    if (iFoldStarCallback == null || !enforceFoldStarPermission("registerFoldStarCallback()")) {
      return;
    }
    try {
      synchronized (this.mCallbacks) {
        this.mCallbacks.register(iFoldStarCallback);
        registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
      }
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mRegisteredCallbackCount = registeredCallbackCount;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } catch (Exception e) {
      Slog.w("FoldStar", "Failed to registerFoldStarCallback", e);
    }
  }

  public void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) {
    int registeredCallbackCount;
    if (iFoldStarCallback == null || !enforceFoldStarPermission("unregisterFoldStarCallback()")) {
      return;
    }
    try {
      synchronized (this.mCallbacks) {
        this.mCallbacks.unregister(iFoldStarCallback);
        registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
      }
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mRegisteredCallbackCount = registeredCallbackCount;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } catch (Exception e) {
      Slog.w("FoldStar", "Failed to unregisterFoldStarCallback", e);
    }
  }

  public void setFixedAspectRatioPackages(int i, Map map, boolean z) {
    if (!CoreRune.FW_FIXED_ASPECT_RATIO_MODE || map == null) {
      return;
    }
    ActivityTaskManagerService.enforceTaskPermission("setFixedAspectRatioPackages()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ConcurrentHashMap changeValuesAsUser =
            this.mAtm.mExt.mFixedAspectRatioController.getChangeValuesAsUser(i);
        if (z) {
          changeValuesAsUser.clear();
        }
        changeValuesAsUser.putAll(map);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          for (Object obj : map.keySet()) {
            if (obj instanceof String) {
              PackagesChange.removeTaskWithoutRemoveFromRecents(
                  this.mAtm, (String) obj, i, "setFixedAspectRatioPackages");
            }
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          this.mAtm.mExt.mFixedAspectRatioController.requestToSave(i);
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      } catch (Throwable th2) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th2;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  /* JADX WARN: Removed duplicated region for block: B:60:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Map getFixedAspectRatioPackages(final int i, int i2, Map map) {
    Function function;
    WindowManagerGlobalLock windowManagerGlobalLock;
    float mergedChange;
    boolean z;
    int i3;
    if (!CoreRune.FW_FIXED_ASPECT_RATIO_MODE) {
      return null;
    }
    ActivityTaskManagerService.enforceTaskPermission("getFixedAspectRatioPackages()");
    final FixedAspectRatioController fixedAspectRatioController =
        this.mAtm.mExt.mFixedAspectRatioController;
    boolean z2 = false;
    if (i2 == 4) {
      ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
      ArrayList arrayList = new ArrayList();
      if (map != null && !map.isEmpty()) {
        arrayList.addAll(map.keySet());
      } else {
        getLauncherPackages(i, arrayList);
      }
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        WindowManagerGlobalLock windowManagerGlobalLock2 = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock2) {
          try {
            if (fixedAspectRatioController.isMinAspectRatioOverrideDisallowed(str, i)) {
              mergedChange = -1.0f;
              z = true;
            } else {
              mergedChange = fixedAspectRatioController.getMergedChange(i, str);
              z = false;
            }
          } finally {
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (z) {
          i3 = 0;
        } else {
          if (mergedChange != -1.0f
              && mergedChange != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            if (Math.abs(mergedChange - 1.7777778f) < 0.005f) {
              i3 = 2;
            } else if (Math.abs(mergedChange - 1.3333334f) < 0.005f) {
              i3 = 3;
            } else {
              Slog.w("FoldStar", mergedChange + " is an unknown policy.");
            }
          }
          i3 = 1;
        }
        concurrentHashMap.put(str, Integer.valueOf(i3));
      }
      return concurrentHashMap;
    }
    ArrayList arrayList2 = new ArrayList();
    if (i2 == 0) {
      function =
          new Function() { // from class:
                           // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
              Float lambda$getFixedAspectRatioPackages$6;
              lambda$getFixedAspectRatioPackages$6 =
                  FoldStarManagerService.lambda$getFixedAspectRatioPackages$6(
                      FixedAspectRatioController.this, i, (String) obj);
              return lambda$getFixedAspectRatioPackages$6;
            }
          };
      getLauncherPackages(i, arrayList2);
    } else if (i2 == 1) {
      function =
          new Function() { // from class:
                           // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
              Float lambda$getFixedAspectRatioPackages$7;
              lambda$getFixedAspectRatioPackages$7 =
                  FoldStarManagerService.lambda$getFixedAspectRatioPackages$7(
                      FixedAspectRatioController.this, i, (String) obj);
              return lambda$getFixedAspectRatioPackages$7;
            }
          };
      getLauncherPackages(i, arrayList2);
    } else {
      if (i2 != 2) {
        if (i2 != 3) {
          throw new IllegalArgumentException(i2 + " is an unknown option.");
        }
        if (map == null) {
          throw new IllegalArgumentException("requestedPackages is null");
        }
        function =
            new Function() { // from class:
                             // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda3
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                Float lambda$getFixedAspectRatioPackages$9;
                lambda$getFixedAspectRatioPackages$9 =
                    FoldStarManagerService.lambda$getFixedAspectRatioPackages$9(
                        FixedAspectRatioController.this, i, (String) obj);
                return lambda$getFixedAspectRatioPackages$9;
              }
            };
        arrayList2.addAll(map.keySet());
        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
              String str2 = (String) it2.next();
              float floatValue = ((Float) function.apply(str2)).floatValue();
              if (floatValue != -1.0f || !z2) {
                concurrentHashMap2.put(str2, Float.valueOf(floatValue));
              }
            }
          } finally {
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return concurrentHashMap2;
      }
      function =
          new Function() { // from class:
                           // com.android.server.wm.FoldStarManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
              Float lambda$getFixedAspectRatioPackages$8;
              lambda$getFixedAspectRatioPackages$8 =
                  FoldStarManagerService.lambda$getFixedAspectRatioPackages$8(
                      FixedAspectRatioController.this, (String) obj);
              return lambda$getFixedAspectRatioPackages$8;
            }
          };
      getLauncherPackages(i, arrayList2);
    }
    z2 = true;
    ConcurrentHashMap concurrentHashMap22 = new ConcurrentHashMap();
    windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
    }
  }

  public static /* synthetic */ Float lambda$getFixedAspectRatioPackages$6(
      FixedAspectRatioController fixedAspectRatioController, int i, String str) {
    return Float.valueOf(fixedAspectRatioController.getMergedChange(i, str));
  }

  public static /* synthetic */ Float lambda$getFixedAspectRatioPackages$7(
      FixedAspectRatioController fixedAspectRatioController, int i, String str) {
    return Float.valueOf(fixedAspectRatioController.getUserChange(i, str));
  }

  public static /* synthetic */ Float lambda$getFixedAspectRatioPackages$8(
      FixedAspectRatioController fixedAspectRatioController, String str) {
    return Float.valueOf(fixedAspectRatioController.getSystemChange(str));
  }

  public static /* synthetic */ Float lambda$getFixedAspectRatioPackages$9(
      FixedAspectRatioController fixedAspectRatioController, int i, String str) {
    return Float.valueOf(fixedAspectRatioController.getMergedChange(i, str));
  }
}
