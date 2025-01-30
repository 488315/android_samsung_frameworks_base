package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.os.Binder;
import android.os.RemoteException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiStarController implements IController {
  public static final List DEFAULT_ALLOW_LIST =
      Arrays.asList(
          "com.samsung.android.multistar", "com.android.systemui", "com.android.settings");
  public ActivityTaskManagerService mAtm;

  @Override // com.android.server.wm.IController
  public void dumpLocked(PrintWriter printWriter, String str) {}

  @Override // com.android.server.wm.IController
  public void initialize() {}

  public MultiStarController(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
  }

  public final boolean ensureCallingPkg(List list) {
    try {
      String[] packagesForUid =
          AppGlobals.getPackageManager().getPackagesForUid(Binder.getCallingUid());
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        for (String str2 : packagesForUid) {
          if (str.equals(str2)) {
            return true;
          }
        }
      }
    } catch (RemoteException unused) {
    }
    return false;
  }

  public void checkMultiStarPackageAndPermission(String str) {
    checkMultiStarPackageAndPermission(str, null);
  }

  public void checkMultiStarPackageAndPermission(String str, List list) {
    if (list == null) {
      list = new ArrayList();
    }
    list.addAll(DEFAULT_ALLOW_LIST);
    if (!ensureCallingPkg(list)) {
      throw new SecurityException(
          "Permission Denial: "
              + str
              + " from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid());
    }
    ActivityTaskManagerService.enforceTaskPermission(str);
  }

  public void setCustomDensityEnabled(int i) {
    this.mAtm.mMultiWindowEnableController.dismissMultiWindowMode(0);
    this.mAtm.mMultiWindowEnableController.setCustomDensityEnabled(i);
  }

  public boolean toggleFreeformWindowingMode() {
    ActivityRecord activityRecord;
    Task rootTask = this.mAtm.mWindowManager.getDefaultDisplayContentLocked().getRootTask(1, 1);
    if (rootTask == null
        || (activityRecord = rootTask.topRunningActivityLocked()) == null
        || activityRecord.getTask() == null
        || !activityRecord.supportsFreeform()) {
      return false;
    }
    this.mAtm.mActivityClientController.toggleFreeformWindowingMode(activityRecord.token);
    if (activityRecord.inFreeformWindowingMode()
        && (activityRecord.getTask().mLastNonFullscreenBounds == null
            || activityRecord.getTask().mLastNonFullscreenBounds.isEmpty())) {
      ActivityOptions makeBasic = ActivityOptions.makeBasic();
      makeBasic.setLaunchWindowingMode(5);
      this.mAtm
          .mTaskSupervisor
          .getLaunchParamsController()
          .layoutTask(
              activityRecord.getTask(),
              activityRecord.info.windowLayout,
              activityRecord,
              null,
              makeBasic,
              activityRecord.getTask().getDisplayId());
    }
    return true;
  }
}
