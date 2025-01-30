package com.android.server.wm;

import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.FactoryTest;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;
import com.samsung.android.server.util.DisplayCompatPolicies;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class OrientationController extends PackagesChangeAsTask implements BoundsCompatController {
  public final List mDefaultDisabledList;
  public boolean mDefaultEnabled;
  public final List mDefaultEnabledList;
  public boolean mDisallowWhenLandscapeFixedApp;
  public final PackageFeatureUserChange.DumpInterface mDumpInterface;
  public int mRotationCompatPolicy;
  public final List mTabletRotationCompatList;
  public Rect mTmpRect;
  public final PackageFeatureUserChange mUserChange;

  public static int getRotationCompatReasonFromSizeChangesSupported(int i, boolean z) {
    if (i == 1) {
      return z ? 102 : 101;
    }
    if (i == 2) {
      return z ? 206 : 204;
    }
    if (i != 3) {
      return 0;
    }
    return z ? 207 : 205;
  }

  public boolean canRotationCompatMode(int i) {
    return i >= 100 && i < 200;
  }

  public final int getPolicyFromLegacyFlag(int i) {
    int i2 = i & (-193);
    if (i2 == 0 || i2 == 7 || i2 == 31 || i2 == 32) {
      return i2;
    }
    if ((i2 & 7) != 0) {
      return (i2 & 24) != 0 ? 31 : 7;
    }
    return 0;
  }

  public static /* synthetic */ String lambda$new$0(int i, String str, Integer num) {
    return String.valueOf(num);
  }

  public OrientationController(ActivityTaskManagerService activityTaskManagerService) {
    super(activityTaskManagerService);
    PackageFeatureUserChange.DumpInterface dumpInterface =
        new PackageFeatureUserChange
            .DumpInterface() { // from class:
                               // com.android.server.wm.OrientationController$$ExternalSyntheticLambda0
          @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
          public final String valueToString(int i, String str, Object obj) {
            String lambda$new$0;
            lambda$new$0 = OrientationController.lambda$new$0(i, str, (Integer) obj);
            return lambda$new$0;
          }
        };
    this.mDumpInterface = dumpInterface;
    PackageFeatureUserChange packageFeatureUserChange =
        new PackageFeatureUserChange(
            64,
            PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY,
            "OrientationControlPackageMap",
            dumpInterface,
            CoreRune.IS_TABLET_DEVICE);
    this.mUserChange = packageFeatureUserChange;
    this.mDefaultEnabled = false;
    this.mDisallowWhenLandscapeFixedApp = true;
    this.mRotationCompatPolicy = 2;
    setUserChanges(packageFeatureUserChange);
    this.mDefaultEnabledList =
        CoreRune.FW_IGNORE_APP_ROTATION_LIST
            ? new PackageSpecialManagementList(PackageFeature.IGNORE_APP_ROTATION)
            : null;
    this.mDefaultDisabledList =
        CoreRune.FW_IGNORE_APP_ROTATION_DISABLED_LIST
            ? new PackageSpecialManagementList(PackageFeature.IGNORE_APP_ROTATION_DISABLED)
            : null;
    this.mTabletRotationCompatList =
        CoreRune.FW_TABLET_ROTATION_COMPAT_LIST
            ? new PackageSpecialManagementList(PackageFeature.TABLET_APP_ROTATION_COMPAT)
            : null;
    if (CoreRune.FW_ORIENTATION_CONTROL_DEFAULT_ENABLED) {
      PackageFeature.REMOTE_CONTROL_FEATURES.registerCallback(
          new PackageFeatureCallback() { // from class:
                                         // com.android.server.wm.OrientationController$$ExternalSyntheticLambda1
            @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
            public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
              OrientationController.this.lambda$new$1(packageFeatureData);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1(PackageFeatureData packageFeatureData) {
    boolean contains = packageFeatureData.keySet().contains("OrientationControlDefaultEnabled");
    Slog.d(
        "OrientationController",
        "DefaultEnabled changed: " + this.mDefaultEnabled + " to " + contains);
    this.mDefaultEnabled = contains;
  }

  public void setPolicy(int i, String str, int i2) {
    setPolicyInternal(i, str, getPolicyFromLegacyFlag(i2));
  }

  public final void setPolicyInternal(int i, String str, int i2) {
    this.mUserChange.putValue(i, str, Integer.valueOf(i2));
  }

  public int getPolicy(int i, String str) {
    int defaultEnabledPolicy;
    if ((CoreRune.ONE_UI_6_1_1 && isOrientationOverrideDisallowed(str, i))
        || isExcludedPackage(str)) {
      return 0;
    }
    int adjustedUserIdIfNeeded = PackagesChange.getAdjustedUserIdIfNeeded(i);
    Integer num = (Integer) this.mUserChange.getValue(adjustedUserIdIfNeeded, str);
    if (CoreRune.FW_ORIENTATION_CONTROL_DEFAULT_ENABLED
        && this.mDefaultEnabled
        && (defaultEnabledPolicy = getDefaultEnabledPolicy(adjustedUserIdIfNeeded, str, num))
            != -1) {
      return defaultEnabledPolicy;
    }
    if (num != null) {
      return getPolicyFromLegacyFlag(num.intValue());
    }
    List list = this.mDefaultEnabledList;
    return (list == null || !list.contains(str)) ? 0 : 31;
  }

  public boolean isExcludedPackage(String str) {
    return isSettingsPackage(str);
  }

  public final int getDefaultEnabledPolicy(int i, String str, Integer num) {
    if (isOrientationOverrideDisallowed(str, i)) {
      return 0;
    }
    List list = this.mDefaultDisabledList;
    boolean z = list != null && list.contains(str);
    if (!CoreRune.IS_FACTORY_BINARY && !FactoryTest.isRunningFactoryApp()) {
      if (num != null) {
        boolean z2 = num.intValue() == 128;
        boolean z3 = num.intValue() == 64;
        if (!z2 && !z3) {
          return -1;
        }
      }
      if (!z && hasLauncherActivity(str, i) && !hasGameCategory(str, i)) {
        return 31;
      }
    }
    return 0;
  }

  public int getAdjustedOrientation(ActivityRecord activityRecord, int i, boolean z) {
    return getAdjustedOrientation(activityRecord, i, z, -2);
  }

  public int getAdjustedOrientation(ActivityRecord activityRecord, int i, boolean z, int i2) {
    if (activityRecord != null && activityRecord.mCompatRecord.mIsIgnoreOrientationRequest) {
      int requestedOrientation = activityRecord.getRequestedOrientation();
      if (isAllowedScreenOrientation(requestedOrientation)) {
        return -2;
      }
      if (z) {
        if (requestedOrientation == 3) {
          return -2;
        }
        return getAdjustedOrientation(requestedOrientation);
      }
      if (i > 0 && requestedOrientation == 3) {
        return -2;
      }
      if (i2 == 3) {
        return getAdjustedOrientation(requestedOrientation);
      }
      if (!activityRecord.mDisplayContent.mClosingApps.contains(activityRecord)
          && (activityRecord.isVisibleRequested()
              || activityRecord.mDisplayContent.mOpeningApps.contains(activityRecord))) {
        return getAdjustedOrientation(requestedOrientation);
      }
    }
    return -2;
  }

  public final int getAdjustedOrientation(int i) {
    return isDisallowWhenLandscape(i) ? i == 6 ? 6 : 11 : (i == 4 || i == 10) ? 10 : 13;
  }

  public boolean interceptSetOrientationIfNeeded(ActivityRecord activityRecord) {
    Task task;
    if (activityRecord == null
        || activityRecord.mDisplayContent == null
        || (task = activityRecord.getTask()) == null) {
      return false;
    }
    if (task.inMultiWindowMode()) {
      activityRecord.mCompatRecord.mScreenOrientationInMultiWindow =
          activityRecord.getRequestedOrientation();
      return false;
    }
    activityRecord.mCompatRecord.mScreenOrientationInMultiWindow = -2;
    if (!canSetOrientation(activityRecord, task)) {
      boolean isAllowedScreenOrientation =
          isAllowedScreenOrientation(activityRecord.getRequestedOrientation());
      int requestedConfigurationOrientation = activityRecord.getRequestedConfigurationOrientation();
      if (((requestedConfigurationOrientation == 0
                  || isAllowedScreenOrientation
                  || requestedConfigurationOrientation
                      == activityRecord.getConfiguration().orientation)
              ? false
              : true)
          || (requestedConfigurationOrientation == 0
              && activityRecord.mCompatRecord.mIsTaskOrientationMismatched)) {
        if (this.mTmpRect == null) {
          this.mTmpRect = new Rect();
        }
        this.mTmpRect.set(activityRecord.getBounds());
        activityRecord.onConfigurationChanged(task.getConfiguration());
        task.dispatchTaskInfoChangedIfNeeded(false);
        if (!this.mTmpRect.equals(activityRecord.getBounds())) {
          this.mAtmService.mChangeTransitController.handleActivityBoundsChanged(
              activityRecord, this.mTmpRect);
        }
      }
      return !isAllowedScreenOrientation;
    }
    return false;
  }

  public final boolean canSetOrientation(ActivityRecord activityRecord, Task task) {
    return (activityRecord.mCompatRecord.mIsIgnoreOrientationRequest
            && task.mOrientationControlEnabledAsAspectRatio)
        ? false
        : true;
  }

  public final boolean isAllowedScreenOrientation(int i) {
    return !ActivityInfo.isFixedOrientationPortrait(i)
        && (this.mDisallowWhenLandscapeFixedApp || !ActivityInfo.isFixedOrientationLandscape(i));
  }

  public final boolean isDisallowWhenLandscape(int i) {
    return this.mDisallowWhenLandscapeFixedApp && ActivityInfo.isFixedOrientationLandscape(i);
  }

  public boolean isIgnoreOrientationRequest(ActivityRecord activityRecord) {
    if (activityRecord.mDisplayContent == null) {
      return false;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_CAMERA_COMPAT
        && activityRecord.mLetterboxUiController.isOverrideRespectRequestedOrientationEnabled()) {
      return false;
    }
    if (WindowConfiguration.inMultiWindowMode(activityRecord.getRequestedOverrideWindowingMode())) {
      return (activityRecord.getTask().inMultiWindowMode()
              || activityRecord.inSplitScreenWindowingMode()
              || activityRecord.inFreeformWindowingMode()
              || activityRecord.inPinnedWindowingMode())
          ? false
          : true;
    }
    return true;
  }

  public int getPreferredConfigurationOrientation(ActivityRecord activityRecord, int i) {
    Task task = activityRecord.getTask();
    if (useBehindOrientation(activityRecord)) {
      ActivityRecord activityBelow = task.getActivityBelow(activityRecord);
      return (activityBelow == null
              || (CoreRune.MW_EMBED_ACTIVITY && activityBelow.isSplitEmbedded()))
          ? i
          : getPreferredConfigurationOrientation(activityBelow, i);
    }
    int requestedOrientation = activityRecord.getRequestedOrientation();
    BoundsCompatRecord boundsCompatRecord = activityRecord.mCompatRecord;
    int i2 = boundsCompatRecord.mScreenOrientationInMultiWindow;
    if (i2 != -2 && i2 == requestedOrientation) {
      return boundsCompatRecord.mPreferredOrientation;
    }
    int requestedConfigurationOrientation = activityRecord.getRequestedConfigurationOrientation();
    return requestedConfigurationOrientation != 0
        ? requestedConfigurationOrientation
        : (activityRecord.isRelaunching()
                && (requestedOrientation == -1 || requestedOrientation == -2))
            ? activityRecord.mCompatRecord.mPreferredOrientation
            : i;
  }

  public boolean useBehindOrientation(ActivityRecord activityRecord) {
    int requestedOrientation = activityRecord.getRequestedOrientation();
    return requestedOrientation == 3
        || (requestedOrientation == -1 && !activityRecord.providesOrientation());
  }

  @Override // com.android.server.wm.PackagesChangeAsTask
  public void onUpdateValueToTask(Task task, String str, boolean z) {
    boolean z2 = task.mOrientationControlEnabledAsFullScreen;
    boolean z3 = task.mOrientationControlEnabledAsAspectRatio;
    int policy = str != null ? getPolicy(task.mUserId, str) : 0;
    boolean z4 = policy == 7;
    task.mOrientationControlEnabledAsFullScreen = z4;
    boolean z5 = policy == 31;
    task.mOrientationControlEnabledAsAspectRatio = z5;
    if (z) {
      if (z4 == z2 && z5 == z3) {
        return;
      }
      task.forAllActivities(
          new Consumer() { // from class:
                           // com.android.server.wm.OrientationController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((ActivityRecord) obj).recomputeConfiguration();
            }
          });
    }
  }

  @Override // com.android.server.wm.PackagesChangeAsTask
  public void onDumpInTask(PrintWriter printWriter, String str, Task task) {
    if (isEnabled(task)) {
      printWriter.print(str);
      printWriter.print("mOrientationControlPolicy=");
      printWriter.print(policyToString(task));
      printWriter.println();
    }
  }

  @Override // com.android.server.wm.PackagesChange
  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.print("mDefaultEnabled=");
    printWriter.print(this.mDefaultEnabled);
    printWriter.print(", mDisallowWhenLandscapeFixedApp=");
    printWriter.print(this.mDisallowWhenLandscapeFixedApp);
    printWriter.println();
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
      printWriter.print(
          str
              + "mRotationCompatPolicy="
              + rotationCompatPolicyToString(this.mRotationCompatPolicy));
      printWriter.println();
    }
  }

  @Override // com.android.server.wm.PackagesChange
  public boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
    if (!CoreRune.FW_ORIENTATION_CONTROL) {
      return false;
    }
    if ("-setOrientationControlDefault".equals(str)) {
      if (assertBooleanOptionsRequires(str, strArr, printWriter)) {
        return true;
      }
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mDefaultEnabled = Boolean.parseBoolean(strArr[0]);
          printWriter.println(str + " mDefaultEnabled=" + this.mDefaultEnabled);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return true;
    }
    if ("-setDisallowWhenLandscapeFixedApp".equals(str)) {
      if (assertBooleanOptionsRequires(str, strArr, printWriter)) {
        return true;
      }
      WindowManagerGlobalLock windowManagerGlobalLock2 = this.mAtmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock2) {
        try {
          this.mDisallowWhenLandscapeFixedApp = Boolean.parseBoolean(strArr[0]);
          printWriter.println(
              str + " mDisallowWhenLandscapeFixedApp=" + this.mDisallowWhenLandscapeFixedApp);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return true;
    }
    if (!CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
        || !"-setRotationCompatPolicy".equals(str)) {
      return false;
    }
    try {
      setRotationCompatPolicy(strArr, printWriter);
    } catch (Exception unused) {
      printWriter.println(str + " [RotationCompatPolicy]");
      printWriter.println("RotationCompatPolicyList");
      int[] iArr = {0, 1, 2};
      for (int i = 0; i < 3; i++) {
        int i2 = iArr[i];
        printWriter.println(rotationCompatPolicyToString(i2) + " = " + i2);
      }
    }
    return true;
  }

  public final boolean assertBooleanOptionsRequires(
      String str, String[] strArr, PrintWriter printWriter) {
    boolean z = true;
    if (strArr.length == 1 && strArr[0] != null) {
      z = false;
    }
    if (z) {
      printWriter.println(str + " options requires: [true/false]");
    }
    return z;
  }

  /* JADX WARN: Code restructure failed: missing block: B:15:0x0026, code lost:

     r5.println(rotationCompatPolicyToString(r4) + " is already set");
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:

     com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x0042, code lost:

     return;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setRotationCompatPolicy(String[] strArr, PrintWriter printWriter) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int i = this.mRotationCompatPolicy;
        int intValue = Integer.valueOf(strArr[0]).intValue();
        if (intValue != 0 && intValue != 1 && intValue != 2) {
          throw new Exception();
        }
        this.mRotationCompatPolicy = intValue;
        printWriter.println(
            "RotationCompatPolicy is changed "
                + rotationCompatPolicyToString(i)
                + " to "
                + rotationCompatPolicyToString(intValue));
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void logInRotationForOrientation(DisplayContent displayContent) {
    StringBuilder sb;
    WindowContainer windowContainer = displayContent.mLastOrientationControlSource;
    WindowContainer lastOrientationSource = displayContent.getLastOrientationSource();
    if (windowContainer != null) {
      sb = new StringBuilder();
      sb.append("rotationForOrientation, Orientation has been adjusted");
      if (windowContainer == lastOrientationSource) {
        sb.append(", OriginalOrientation=");
        sb.append(
            ActivityInfo.screenOrientationToString(lastOrientationSource.getOverrideOrientation()));
      } else {
        sb.append(", OrientationControlSource=");
        sb.append(windowContainer);
      }
    } else if (lastOrientationSource != null) {
      ActivityRecord asActivityRecord = lastOrientationSource.asActivityRecord();
      if (asActivityRecord == null || !isEnabled(asActivityRecord.getTask())) {
        return;
      }
      StringBuilder sb2 = new StringBuilder();
      sb2.append("rotationForOrientation, Orientation is not adjusted");
      if (asActivityRecord.mCompatRecord.mIsIgnoreOrientationRequest) {
        sb2.append(", mOrientation=");
        sb2.append(ActivityInfo.screenOrientationToString(asActivityRecord.getOrientation()));
        sb2.append(", containsClosing=");
        sb2.append(displayContent.mClosingApps.contains(asActivityRecord));
        sb2.append(", containsOpening=");
        sb2.append(displayContent.mOpeningApps.contains(asActivityRecord));
        sb2.append(", isVisibleRequested=");
        sb2.append(asActivityRecord.isVisibleRequested());
      } else {
        sb2.append(", inMultiWindowMode=");
        sb2.append(asActivityRecord.inMultiWindowMode());
        sb2.append(", inSizeCompatMode=");
        sb2.append(asActivityRecord.inSizeCompatMode());
      }
      sb = sb2;
    } else {
      sb = null;
    }
    if (sb != null) {
      Slog.d("OrientationController", sb.toString());
    }
  }

  public static boolean isEnabled(Task task) {
    return task != null
        && (task.mOrientationControlEnabledAsFullScreen
            || task.mOrientationControlEnabledAsAspectRatio);
  }

  public static String orientationToString(int i) {
    return i != 1 ? i != 2 ? Integer.toString(i) : "land" : "port";
  }

  public static String policyToString(Task task) {
    return task.mOrientationControlEnabledAsFullScreen
        ? "ENABLED_AS_FULL_SCREEN"
        : task.mOrientationControlEnabledAsAspectRatio ? "ENABLED_AS_ASPECT_RATIO" : "DISABLED";
  }

  public int getRotationCompatReason(ActivityRecord activityRecord) {
    ActivityRecord activityBelow;
    int rotationCompatReasonFromSizeChangesSupported;
    ComponentName componentName;
    if (this.mRotationCompatPolicy == 0) {
      return 200;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT_FOR_FOLD
        && this.mAtmService.mWindowManager.isFolded()) {
      return 202;
    }
    if (activityRecord.getDisplayId() != 0) {
      return 209;
    }
    Task task = activityRecord.getTask();
    if (CoreRune.MT_NEW_DEX
        && (activityRecord.isDesktopModeEnabled() || task.isDesktopModeEnabled())) {
      return FrameworkStatsLog.f654xe56e23e8;
    }
    int requestedOrientation = activityRecord.getRequestedOrientation();
    if (isAllowedScreenOrientation(requestedOrientation)) {
      return FrameworkStatsLog.f653x532b6133;
    }
    if (ActivityInfo.isFixedOrientationLandscape(requestedOrientation)) {
      if (this.mDisallowWhenLandscapeFixedApp) {
        return 212;
      }
    } else if (!ActivityInfo.isFixedOrientationPortrait(requestedOrientation)) {
      return (useBehindOrientation(activityRecord)
              && (activityBelow = task.getActivityBelow(activityRecord)) != null
              && activityBelow.mCompatRecord.mCanRotationCompatMode)
          ? 104
          : 211;
    }
    List list = this.mTabletRotationCompatList;
    if (list != null
        && (componentName = task.realActivity) != null
        && list.contains(componentName.getPackageName())) {
      return 105;
    }
    if (this.mRotationCompatPolicy == 1) {
      return 100;
    }
    int rotationCompatReasonFromSizeChangesSupported2 =
        getRotationCompatReasonFromSizeChangesSupported(
            activityRecord.supportsSizeChanges(), false);
    return rotationCompatReasonFromSizeChangesSupported2 != 0
        ? rotationCompatReasonFromSizeChangesSupported2
        : (task.realActivity == null
                || (rotationCompatReasonFromSizeChangesSupported =
                        getRotationCompatReasonFromSizeChangesSupported(
                            DisplayCompatPolicies.getSizeChangesSupported(
                                DisplayCompatPolicies.getDisplayCompatPolicies()
                                    .getPolicy(task.realActivity.getPackageName())),
                            true))
                    == 0)
            ? activityRecord.isResizeable() ? 208 : 103
            : rotationCompatReasonFromSizeChangesSupported;
  }

  public void clearRotationCompatMode(ActivityRecord activityRecord, boolean z) {
    ActivityRecord.CompatDisplayInsets compatDisplayInsets =
        activityRecord.getCompatDisplayInsets();
    if (compatDisplayInsets == null) {
      return;
    }
    if (!compatDisplayInsets.mCreatedByRotationCompat) {
      compatDisplayInsets.mCanRotationCompatMode = false;
      return;
    }
    if (z) {
      compatDisplayInsets.mConfigChangeNeeded = true;
      activityRecord.mCompatRecord.mPendingCompatDisplayInsets = compatDisplayInsets;
    }
    Task task = activityRecord.getTask();
    activityRecord.clearSizeCompatMode(true, task == null || !task.inPinnedWindowingMode());
  }

  public boolean shouldCreateCompatDisplayInsets(ActivityRecord activityRecord) {
    if (!activityRecord.mCompatRecord.mCanRotationCompatMode
        || activityRecord.getCompatDisplayInsets() != null
        || activityRecord.inMultiWindowMode()
        || activityRecord.isDexMode()
        || activityRecord.getTask().isDexMode()) {
      return false;
    }
    return activityRecord.mCompatRecord.mRotationCompatModeInherited
        || activityRecord.getRequestedConfigurationOrientation() != 0;
  }

  public static String rotationCompatPolicyToString(int i) {
    return i != 0
        ? i != 1 ? i != 2 ? Integer.toString(i) : "AUTO" : "ALWAYS_ENABLED"
        : "ALWAYS_DISABLED";
  }

  public static String rotationCompatReasonToString(int i) {
    if (i == 105) {
      return "RESIZABLE_ACTIVITY_UNSUPPORTED";
    }
    switch (i) {
      case 100:
        return "ROTATION_COMPAT_MODE_ENABLED";
      case 101:
        return "SIZE_CHANGES_UNSUPPORTED_OVERRIDE";
      case 102:
        return "DISPLAY_COMPAT_POLICY_UNSUPPORTED_OVERRIDE";
      case 103:
        return "UNRESIZABLE_ACTIVITY";
      default:
        switch (i) {
          case 200:
            return "ROTATION_COMPAT_MODE_DISABLED";
          case 201:
            return "IN_MULTI_WINDOW_MODE";
          case 202:
            return "DISPLAY_FOLDED";
          case 203:
            return "POLICY_DISABLED";
          case 204:
            return "SIZE_CHANGES_SUPPORTED_METADATA";
          case 205:
            return "SIZE_CHANGES_SUPPORTED_OVERRIDE";
          case 206:
            return "DISPLAY_COMPAT_POLICY_SUPPORTED_METADATA";
          case 207:
            return "DISPLAY_COMPAT_POLICY_SUPPORTED_OVERRIDE";
          case 208:
            return "RESIZABLE_ACTIVITY";
          case 209:
            return "NON_DEFAULT_DISPLAY";
          case 210:
            return "IN_ACTIVITY_EMBEDDED";
          case 211:
            return "UNFIXED_ORIENTATION";
          case 212:
            return "FIXED_ORIENTATION_LANDSCAPE";
          case 213:
            return "IN_SPLIT_ACTIVITY_MODE";
          case FrameworkStatsLog.f654xe56e23e8 /* 214 */:
            return "IN_DESKTOP_MODE";
          default:
            return Integer.toString(i);
        }
    }
  }

  @Override // com.android.server.wm.BoundsCompatController
  public boolean shouldUseSizeCompatMode(ActivityRecord activityRecord) {
    return CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
        && activityRecord.mCompatRecord.mCanRotationCompatMode;
  }

  @Override // com.android.server.wm.BoundsCompatController
  public boolean shouldUseSandboxDisplay(ActivityRecord activityRecord) {
    return canHaveSizeCompatBounds(activityRecord);
  }

  @Override // com.android.server.wm.BoundsCompatController
  public boolean shouldUseSandboxViewBoundsAndMotionEvent(ActivityRecord activityRecord) {
    return canHaveSizeCompatBounds(activityRecord);
  }

  @Override // com.android.server.wm.BoundsCompatController
  public boolean canHaveSizeCompatBounds(ActivityRecord activityRecord) {
    return shouldUseSizeCompatMode(activityRecord)
        && activityRecord.mCompatRecord.mIsTaskOrientationMismatched;
  }

  @Override // com.android.server.wm.BoundsCompatController
  public void adjustBounds(ActivityRecord activityRecord, Configuration configuration) {
    if (canHaveSizeCompatBounds(activityRecord)) {
      getBoundsCompatUtils().adjustBoundsAsSizeCompatMode(activityRecord, configuration);
    } else {
      getBoundsCompatUtils().adjustBoundsAsMinAspectRatio(activityRecord, configuration);
    }
  }
}
