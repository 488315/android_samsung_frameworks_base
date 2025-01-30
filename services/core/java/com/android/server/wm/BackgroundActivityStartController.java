package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Process;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.server.am.PendingIntentRecord;

/* loaded from: classes3.dex */
public class BackgroundActivityStartController {
  public final ActivityTaskManagerService mService;
  public final ActivityTaskSupervisor mSupervisor;

  public static String balCodeToString(int i) {
    switch (i) {
      case 0:
        return "BAL_BLOCK";
      case 1:
        return "BAL_ALLOW_DEFAULT";
      case 2:
        return "BAL_ALLOW_ALLOWLISTED_UID";
      case 3:
        return "BAL_ALLOW_ALLOWLISTED_COMPONENT";
      case 4:
        return "BAL_ALLOW_VISIBLE_WINDOW";
      case 5:
        return "BAL_ALLOW_PENDING_INTENT";
      case 6:
        return "BAL_ALLOW_PERMISSION";
      case 7:
        return "BAL_ALLOW_SAW_PERMISSION";
      case 8:
        return "BAL_ALLOW_GRACE_PERIOD";
      case 9:
        return "BAL_ALLOW_FOREGROUND";
      case 10:
        return "BAL_ALLOW_SDK_SANDBOX";
      default:
        throw new IllegalArgumentException("Unexpected value: " + i);
    }
  }

  public BackgroundActivityStartController(
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskSupervisor activityTaskSupervisor) {
    this.mService = activityTaskManagerService;
    this.mSupervisor = activityTaskSupervisor;
  }

  public final boolean isHomeApp(int i, String str) {
    if (this.mService.mHomeProcess != null) {
      return i == this.mService.mHomeProcess.mUid;
    }
    if (str == null) {
      return false;
    }
    ComponentName defaultHomeActivity =
        this.mService
            .getPackageManagerInternalLocked()
            .getDefaultHomeActivity(UserHandle.getUserId(i));
    return defaultHomeActivity != null && str.equals(defaultHomeActivity.getPackageName());
  }

  public boolean shouldAbortBackgroundActivityStart(
      int i,
      int i2,
      String str,
      int i3,
      int i4,
      WindowProcessController windowProcessController,
      PendingIntentRecord pendingIntentRecord,
      BackgroundStartPrivileges backgroundStartPrivileges,
      Intent intent,
      ActivityOptions activityOptions) {
    return checkBackgroundActivityStart(
            i,
            i2,
            str,
            i3,
            i4,
            windowProcessController,
            pendingIntentRecord,
            backgroundStartPrivileges,
            intent,
            activityOptions)
        == 0;
  }

  /* JADX WARN: Removed duplicated region for block: B:121:0x039e  */
  /* JADX WARN: Removed duplicated region for block: B:154:0x03a9  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int checkBackgroundActivityStart(
      int i,
      int i2,
      String str,
      int i3,
      int i4,
      WindowProcessController windowProcessController,
      PendingIntentRecord pendingIntentRecord,
      BackgroundStartPrivileges backgroundStartPrivileges,
      Intent intent,
      ActivityOptions activityOptions) {
    boolean z;
    boolean z2;
    boolean z3;
    int i5;
    boolean z4;
    boolean z5;
    boolean z6;
    int i6;
    int i7;
    String str2;
    boolean z7;
    boolean z8;
    BackgroundStartPrivileges backgroundStartPrivileges2;
    int i8;
    WindowProcessController windowProcessController2;
    int i9;
    int i10;
    String str3;
    int i11;
    int logStartAllowedAndReturnCode;
    int appId = UserHandle.getAppId(i);
    boolean z9 =
        pendingIntentRecord == null
            || activityOptions == null
            || activityOptions.getPendingIntentCreatorBackgroundActivityStartMode() != 2;
    if (z9) {
      if (i == 0 || appId == 1000 || appId == 1027) {
        return logStartAllowedAndReturnCode(2, false, i, i3, intent, "Important callingUid");
      }
      if (isHomeApp(i, str)) {
        return logStartAllowedAndReturnCode(3, false, i, i3, intent, "Home app");
      }
      WindowState currentInputMethodWindow =
          this.mService.mRootWindowContainer.getCurrentInputMethodWindow();
      if (currentInputMethodWindow != null && appId == currentInputMethodWindow.mOwnerUid) {
        return logStartAllowedAndReturnCode(3, false, i, i3, intent, "Active ime");
      }
    }
    int balAppSwitchesState = this.mService.getBalAppSwitchesState();
    int uidState = this.mService.mActiveUids.getUidState(i);
    boolean hasActiveVisibleWindow = this.mService.hasActiveVisibleWindow(i);
    boolean z10 = uidState <= 1;
    boolean z11 =
        (((balAppSwitchesState == 2 || balAppSwitchesState == 1)
                    || this.mService.mActiveUids.hasNonAppVisibleWindow(i))
                && hasActiveVisibleWindow)
            || z10;
    if (z9 && z11) {
      return logStartAllowedAndReturnCode(
          4,
          false,
          i,
          i3,
          intent,
          "callingUidHasAnyVisibleWindow = "
              + i
              + ", isCallingUidPersistentSystemProcess = "
              + z10);
    }
    int uidState2 = i == i3 ? uidState : this.mService.mActiveUids.getUidState(i3);
    boolean hasActiveVisibleWindow2 =
        i == i3 ? hasActiveVisibleWindow : this.mService.hasActiveVisibleWindow(i3);
    int appId2 = UserHandle.getAppId(i3);
    if (i == i3) {
      z2 = z10;
    } else {
      if (appId2 != 1000) {
        z = true;
        if (uidState2 > 1) {
          z2 = false;
        }
      } else {
        z = true;
      }
      z2 = z;
    }
    if (Process.isSdkSandboxUid(i3)) {
      z3 = hasActiveVisibleWindow2;
      if (this.mService.hasActiveVisibleWindow(Process.getAppUidForSdkSandboxUid(i3))) {
        return logStartAllowedAndReturnCode(
            10, false, i, i3, intent, "uid in SDK sandbox has visible (non-toast) window");
      }
    } else {
      z3 = hasActiveVisibleWindow2;
    }
    String packageNameIfUnique = this.mService.getPackageNameIfUnique(i3, i4);
    BackgroundStartPrivileges backgroundStartPrivilegesAllowedByCaller =
        PendingIntentRecord.getBackgroundStartPrivilegesAllowedByCaller(
            activityOptions, i3, packageNameIfUnique);
    boolean z12 =
        activityOptions == null
            || activityOptions.getPendingIntentBackgroundActivityStartMode() == 0;
    boolean z13 = z12 || backgroundStartPrivilegesAllowedByCaller.allowsBackgroundActivityStarts();
    String str4 =
        backgroundStartPrivilegesAllowedByCaller.allowsBackgroundActivityStarts()
            ? "Activity start allowed"
            : "Activity start would be allowed if the sender granted BAL privileges";
    if (i3 == i || !z13) {
      i5 = uidState2;
      z4 = z2;
      z5 = z10;
      z6 = hasActiveVisibleWindow;
      i6 = uidState;
      i7 = balAppSwitchesState;
      str2 = packageNameIfUnique;
      z7 = z3;
      z8 = true;
      backgroundStartPrivileges2 = backgroundStartPrivilegesAllowedByCaller;
      i8 = 0;
    } else {
      z7 = z3;
      backgroundStartPrivileges2 = backgroundStartPrivilegesAllowedByCaller;
      i5 = uidState2;
      z4 = z2;
      z5 = z10;
      z6 = hasActiveVisibleWindow;
      i6 = uidState;
      i7 = balAppSwitchesState;
      str2 = packageNameIfUnique;
      z8 = true;
      i8 =
          checkPiBackgroundActivityStart(
              i, i3, backgroundStartPrivileges, intent, activityOptions, z7, z4, str4);
    }
    if (i8 != 0 && backgroundStartPrivileges2.allowsBackgroundActivityStarts() && !z12) {
      return i8;
    }
    if (z9) {
      if (ActivityTaskManagerService.checkPermission(
              "android.permission.START_ACTIVITIES_FROM_BACKGROUND", i2, i)
          == 0) {
        return logStartAllowedAndReturnCode(
            6,
            i8,
            backgroundStartPrivileges2,
            true,
            i,
            i3,
            intent,
            "START_ACTIVITIES_FROM_BACKGROUND permission granted");
      }
      if (this.mSupervisor.mRecentTasks.isCallerRecents(i)) {
        return logStartAllowedAndReturnCode(
            3, i8, backgroundStartPrivileges2, true, i, i3, intent, "Recents Component");
      }
      if (this.mService.isDeviceOwner(i)) {
        return logStartAllowedAndReturnCode(
            3, i8, backgroundStartPrivileges2, true, i, i3, intent, "Device Owner");
      }
      if (this.mService.isAffiliatedProfileOwner(i)) {
        return logStartAllowedAndReturnCode(
            3, i8, backgroundStartPrivileges2, true, i, i3, intent, "Affiliated Profile Owner");
      }
      if (this.mService.isAssociatedCompanionApp(UserHandle.getUserId(i), i)) {
        return logStartAllowedAndReturnCode(
            3, i8, backgroundStartPrivileges2, true, i, i3, intent, "Companion App");
      }
      if (this.mService.hasSystemAlertWindowPermission(i, i2, str)) {
        Slog.w(
            "ActivityTaskManager",
            "Background activity start for "
                + str
                + " allowed because SYSTEM_ALERT_WINDOW permission is granted.");
        return logStartAllowedAndReturnCode(
            7,
            i8,
            backgroundStartPrivileges2,
            true,
            i,
            i3,
            intent,
            "SYSTEM_ALERT_WINDOW permission is granted");
      }
      if (isSystemExemptFlagEnabled()
          && this.mService.getAppOpsManager().checkOpNoThrow(130, i, str) == 0) {
        return logStartAllowedAndReturnCode(
            6,
            i8,
            backgroundStartPrivileges2,
            true,
            i,
            i3,
            intent,
            "OP_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION appop is granted");
      }
    }
    boolean z14 = (windowProcessController == null && z13 && i8 == 0) ? z8 : false;
    if (z14) {
      windowProcessController2 = this.mService.getProcessController(i4, i3);
      i9 = i3;
    } else {
      windowProcessController2 = windowProcessController;
      i9 = i;
    }
    if (windowProcessController2 == null || !z9) {
      i10 = i7;
    } else {
      i10 = i7;
      int areBackgroundActivityStartsAllowed =
          windowProcessController2.areBackgroundActivityStartsAllowed(i10);
      if (areBackgroundActivityStartsAllowed == 0) {
        i11 = areBackgroundActivityStartsAllowed;
        ArraySet processes = this.mService.mProcessMap.getProcesses(i9);
        if (processes != null) {
          for (int size = processes.size() - 1; size >= 0; size--) {
            WindowProcessController windowProcessController3 =
                (WindowProcessController) processes.valueAt(size);
            int areBackgroundActivityStartsAllowed2 =
                windowProcessController3.areBackgroundActivityStartsAllowed(i10);
            if (windowProcessController3 != windowProcessController2
                && areBackgroundActivityStartsAllowed2 != 0) {
              if (z14) {
                logStartAllowedAndReturnCode =
                    logStartAllowedAndReturnCode(
                        areBackgroundActivityStartsAllowed2,
                        true,
                        i,
                        i3,
                        intent,
                        "process"
                            + windowProcessController3.getPid()
                            + " from uid "
                            + i9
                            + " is allowed",
                        str4);
              } else {
                return logStartAllowedAndReturnCode(
                    areBackgroundActivityStartsAllowed2,
                    i8,
                    backgroundStartPrivileges2,
                    true,
                    i,
                    i3,
                    intent,
                    "process"
                        + windowProcessController3.getPid()
                        + " from uid "
                        + i9
                        + " is allowed");
              }
            }
          }
        }
        if (z14) {
          int i12 = i11;
          Preconditions.checkState(
              i12 == 0 ? z8 : false, "balAllowedForCaller = " + i12 + " (should have returned)");
        } else if (i8 != 0 && backgroundStartPrivileges2.allowsBackgroundActivityStarts() && !z12) {
          return i8;
        }
      } else if (z14) {
        i11 = areBackgroundActivityStartsAllowed;
        logStartAllowedAndReturnCode =
            logStartAllowedAndReturnCode(
                areBackgroundActivityStartsAllowed,
                true,
                i,
                i3,
                intent,
                "callerApp process (pid = "
                    + windowProcessController2.getPid()
                    + ", uid = "
                    + i9
                    + ") is allowed",
                str4);
      } else {
        return logStartAllowedAndReturnCode(
            areBackgroundActivityStartsAllowed,
            i8,
            backgroundStartPrivileges2,
            true,
            i,
            i3,
            intent,
            "callerApp process (pid = "
                + windowProcessController2.getPid()
                + ", uid = "
                + i9
                + ") is allowed");
      }
      i8 = logStartAllowedAndReturnCode;
      if (z14) {}
    }
    if (str2 == null) {
      StringBuilder sb = new StringBuilder();
      sb.append(i == i3 ? str : this.mService.mContext.getPackageManager().getNameForUid(i3));
      sb.append("[debugOnly]");
      str3 = sb.toString();
    } else {
      str3 = str2;
    }
    StringBuilder sb2 = new StringBuilder();
    sb2.append(" [callingPackage: ");
    sb2.append(str);
    sb2.append("; callingUid: ");
    sb2.append(i);
    sb2.append("; appSwitchState: ");
    sb2.append(i10);
    sb2.append("; callingUidHasAnyVisibleWindow: ");
    boolean z15 = z6;
    sb2.append(z15);
    sb2.append("; callingUidProcState: ");
    int i13 = i6;
    sb2.append(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", i13));
    sb2.append("; isCallingUidPersistentSystemProcess: ");
    sb2.append(z5);
    sb2.append("; balAllowedByPiSender: ");
    BackgroundStartPrivileges backgroundStartPrivileges3 = backgroundStartPrivileges2;
    sb2.append(backgroundStartPrivileges3);
    sb2.append("; realCallingPackage: ");
    sb2.append(str3);
    sb2.append("; realCallingUid: ");
    sb2.append(i3);
    sb2.append("; realCallingUidHasAnyVisibleWindow: ");
    boolean z16 = z7;
    sb2.append(z16);
    sb2.append("; realCallingUidProcState: ");
    int i14 = i5;
    sb2.append(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", i14));
    sb2.append("; isRealCallingUidPersistentSystemProcess: ");
    sb2.append(z4);
    sb2.append("; originatingPendingIntent: ");
    boolean z17 = z12;
    sb2.append(pendingIntentRecord);
    sb2.append("; backgroundStartPrivileges: ");
    sb2.append(backgroundStartPrivileges);
    sb2.append("; intent: ");
    sb2.append(intent);
    sb2.append("; callerApp: ");
    sb2.append(windowProcessController2);
    sb2.append("; inVisibleTask: ");
    sb2.append(
        (windowProcessController2 == null || !windowProcessController2.hasActivityInVisibleTask())
            ? false
            : z8);
    sb2.append("]");
    String sb3 = sb2.toString();
    if (i8 != 0) {
      Preconditions.checkState(
          z17,
          "resultIfPiSenderAllowsBal = "
              + balCodeToString(i8)
              + " at the end but logVerdictChangeByPiDefaultChange = false");
      if (backgroundStartPrivileges3.allowsBackgroundActivityStarts()) {
        Slog.wtf(
            "ActivityTaskManager",
            "With BAL hardening this activity start would be blocked!" + sb3);
        return i8;
      }
      Slog.wtf(
          "ActivityTaskManager",
          "Without BAL hardening this activity start would be allowed!" + sb3);
    }
    Slog.w("ActivityTaskManager", "Background activity launch blocked" + sb3);
    FrameworkStatsLog.write(FrameworkStatsLog.DETECT_MALICIOUS_ACTION, str, "BAL");
    if (this.mService.isActivityStartsLoggingEnabled()) {
      this.mSupervisor
          .getActivityMetricsLogger()
          .logAbortedBgActivityStart(
              intent,
              windowProcessController2,
              i,
              str,
              i13,
              z15,
              i3,
              i14,
              z16,
              pendingIntentRecord != null ? z8 : false);
    }
    return 0;
  }

  public final int checkPiBackgroundActivityStart(
      int i,
      int i2,
      BackgroundStartPrivileges backgroundStartPrivileges,
      Intent intent,
      ActivityOptions activityOptions,
      boolean z,
      boolean z2,
      String str) {
    if (PendingIntentRecord.isPendingIntentBalAllowedByPermission(activityOptions)
        && ActivityManager.checkComponentPermission(
                "android.permission.START_ACTIVITIES_FROM_BACKGROUND", i2, -1, true)
            == 0) {
      return logStartAllowedAndReturnCode(
          5, false, i, i2, intent, "realCallingUid has BAL permission. realCallingUid: " + i2, str);
    }
    if (z) {
      return logStartAllowedAndReturnCode(
          5,
          false,
          i,
          i2,
          intent,
          "realCallingUid has visible (non-toast) window. realCallingUid: " + i2,
          str);
    }
    if (z2 && backgroundStartPrivileges.allowsBackgroundActivityStarts()) {
      return logStartAllowedAndReturnCode(
          5,
          false,
          i,
          i2,
          intent,
          "realCallingUid is persistent system process AND intent sender allowed"
              + " (allowBackgroundActivityStart = true). realCallingUid: "
              + i2,
          str);
    }
    if (!this.mService.isAssociatedCompanionApp(UserHandle.getUserId(i2), i2)) {
      return 0;
    }
    return logStartAllowedAndReturnCode(
        5, false, i, i2, intent, "realCallingUid is a companion app. realCallingUid: " + i2, str);
  }

  public static int logStartAllowedAndReturnCode(
      int i, boolean z, int i2, int i3, Intent intent, int i4, String str) {
    return logStartAllowedAndReturnCode(i, z, i2, i3, intent, "");
  }

  public static int logStartAllowedAndReturnCode(
      int i, boolean z, int i2, int i3, Intent intent, String str) {
    return logStartAllowedAndReturnCode(i, z, i2, i3, intent, str, "Activity start allowed");
  }

  public static int logStartAllowedAndReturnCode(
      int i,
      int i2,
      BackgroundStartPrivileges backgroundStartPrivileges,
      boolean z,
      int i3,
      int i4,
      Intent intent,
      String str) {
    return (i2 == 0 || !backgroundStartPrivileges.allowsBackgroundActivityStarts())
        ? logStartAllowedAndReturnCode(i, z, i3, i4, intent, str, "Activity start allowed")
        : i2;
  }

  public static int logStartAllowedAndReturnCode(
      int i, boolean z, int i2, int i3, Intent intent, String str, String str2) {
    statsLogBalAllowed(i, i2, i3, intent);
    return i;
  }

  public static boolean isSystemExemptFlagEnabled() {
    return DeviceConfig.getBoolean(
        "window_manager", "system_exempt_from_activity_bg_start_restriction_enabled", true);
  }

  public static void statsLogBalAllowed(int i, int i2, int i3, Intent intent) {
    if (i == 5 && (i2 == 1000 || i3 == 1000)) {
      FrameworkStatsLog.write(
          FrameworkStatsLog.BAL_ALLOWED,
          intent != null ? intent.getComponent().flattenToShortString() : "",
          i,
          i2,
          i3);
    }
    if (i == 6 || i == 9 || i == 7) {
      FrameworkStatsLog.write(FrameworkStatsLog.BAL_ALLOWED, "", i, i2, i3);
    }
  }
}
