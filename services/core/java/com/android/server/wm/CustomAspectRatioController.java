package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class CustomAspectRatioController extends PackagesChange implements BoundsCompatController {
  public float mDefaultDisplayAspectRatio;
  public float mDisplayMaxAspectRatio;
  public float mDisplayMaxAspectRatioWithCutout;
  public final PackageFeatureUserChange.DumpInterface mDumpInterface;
  public final FullScreenAppsSupportUtils mFullScreenUtils;
  public CustomAspectRatioLegacyController mLegacyController;
  public ConcurrentHashMap mPackageWithAspectRatioApplied;
  public boolean mSupportsMaxAspectRatio;
  public final PackageFeatureUserChange mUserChange;

  public static boolean isFullScreenMode(int i) {
    return i == 1 || i == 2;
  }

  @Override // com.android.server.wm.BoundsCompatController
  public void adjustBounds(ActivityRecord activityRecord, Configuration configuration) {}

  @Override // com.android.server.wm.BoundsCompatController
  public boolean shouldUpdatePosition() {
    return false;
  }

  @Override // com.android.server.wm.BoundsCompatController
  public boolean supportsCustomLetterbox() {
    return false;
  }

  public static /* synthetic */ String lambda$new$0(int i, String str, Integer num) {
    return policyToString(num.intValue());
  }

  public CustomAspectRatioController(ActivityTaskManagerService activityTaskManagerService) {
    super(activityTaskManagerService);
    PackageFeatureUserChange.DumpInterface dumpInterface =
        new PackageFeatureUserChange
            .DumpInterface() { // from class:
                               // com.android.server.wm.CustomAspectRatioController$$ExternalSyntheticLambda0
          @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
          public final String valueToString(int i, String str, Object obj) {
            String lambda$new$0;
            lambda$new$0 = CustomAspectRatioController.lambda$new$0(i, str, (Integer) obj);
            return lambda$new$0;
          }
        };
    this.mDumpInterface = dumpInterface;
    PackageFeatureUserChange packageFeatureUserChange =
        new PackageFeatureUserChange(
            2,
            PackageFeatureUserChangePersister.CONVENTIONAL_MODE_DIRECTORY,
            "CustomAspectRatioPackageMap",
            dumpInterface);
    this.mUserChange = packageFeatureUserChange;
    this.mFullScreenUtils = FullScreenAppsSupportUtils.get();
    setUserChanges(packageFeatureUserChange);
  }

  public void onConfigurationChanged(DisplayContent displayContent) {
    this.mDefaultDisplayAspectRatio = getBoundsCompatUtils().getDefaultDisplayAspectRatio();
    this.mDisplayMaxAspectRatio = this.mFullScreenUtils.getDisplayMaxAspectRatio(false);
    this.mDisplayMaxAspectRatioWithCutout = this.mFullScreenUtils.getDisplayMaxAspectRatio(true);
    this.mSupportsMaxAspectRatio = this.mFullScreenUtils.supportsMaxAspectRatio();
    this.mDisplayMaxAspectRatio =
        Math.min(this.mDisplayMaxAspectRatio, this.mDefaultDisplayAspectRatio);
  }

  public int getMaxAspectRatioPolicy(ApplicationInfo applicationInfo, ActivityInfo activityInfo) {
    if (!this.mSupportsMaxAspectRatio) {
      return 0;
    }
    CustomAspectRatioLegacyController customAspectRatioLegacyController = this.mLegacyController;
    if (customAspectRatioLegacyController != null) {
      customAspectRatioLegacyController.migrateIfNeeded();
      this.mLegacyController = null;
    }
    int userId = UserHandle.getUserId(applicationInfo.uid);
    String str = applicationInfo.packageName;
    Integer num = (Integer) this.mUserChange.getValue(userId, str);
    if (num != null) {
      if (isUnchangeableFullScreenMode(userId, applicationInfo, activityInfo)) {
        return 2;
      }
      return num.intValue();
    }
    if (!this.mFullScreenUtils.containsInDefaultFullScreenList(str)) {
      return ((activityInfo == null
                  || !(BoundsCompatRecord.hasDefinedAspectRatio(activityInfo.getMaxAspectRatio())
                      || BoundsCompatRecord.hasDefinedAspectRatio(
                          activityInfo.getMinAspectRatio())))
              && isUnchangeableFullScreenMode(userId, applicationInfo, activityInfo))
          ? 2
          : 0;
    }
    setMaxAspectRatioPolicy(str, applicationInfo.uid, true, -1, false);
    return isUnchangeableFullScreenMode(userId, applicationInfo, activityInfo) ? 2 : 1;
  }

  public boolean isUnchangeableFullScreenMode(
      int i, ApplicationInfo applicationInfo, ActivityInfo activityInfo) {
    String str = applicationInfo.packageName;
    ConcurrentHashMap concurrentHashMap = this.mPackageWithAspectRatioApplied;
    if (concurrentHashMap != null && concurrentHashMap.containsKey(str)) {
      return false;
    }
    if (activityInfo != null) {
      if (isUsableAspectRatio(activityInfo.getMaxAspectRatio())) {
        return false;
      }
    } else {
      Iterator it = getLauncherActivities(str, i).iterator();
      while (it.hasNext()) {
        if (isUsableAspectRatio(((ResolveInfo) it.next()).activityInfo.getMaxAspectRatio())) {
          return false;
        }
      }
    }
    if ((applicationInfo.privateFlags & 5120) != 0) {
      return true;
    }
    if (isUsableAspectRatio(applicationInfo.maxAspectRatio)) {
      return false;
    }
    Bundle bundle = applicationInfo.metaData;
    return (bundle == null || !isUsableAspectRatio(bundle.getFloat("android.max_aspect")))
        && applicationInfo.targetSdkVersion >= 26;
  }

  public final boolean isUsableAspectRatio(float f) {
    return BoundsCompatRecord.hasDefinedAspectRatio(f) && f < this.mDisplayMaxAspectRatio;
  }

  public void onAspectRatioApplied(ActivityRecord activityRecord, float f) {
    String packageName = activityRecord.mActivityComponent.getPackageName();
    if (this.mPackageWithAspectRatioApplied == null) {
      this.mPackageWithAspectRatioApplied = new ConcurrentHashMap();
    }
    if (this.mPackageWithAspectRatioApplied.containsKey(packageName)) {
      return;
    }
    this.mPackageWithAspectRatioApplied.put(
        packageName,
        activityRecord.mActivityComponent.getShortClassName() + ", MaxAspectRatio=" + f);
  }

  public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2, boolean z2) {
    int userId = UserHandle.getUserId(i);
    boolean z3 = true;
    Integer num =
        (Integer)
            this.mUserChange.putValue(
                userId,
                str,
                Integer.valueOf(z ? 1 : 0),
                (z || this.mFullScreenUtils.containsInDefaultFullScreenList(str)) ? false : true);
    if (!z2 || (num != null && num.intValue() == z)) {
      z3 = false;
    }
    if (z3) {
      PackagesChange.removeTaskWithoutRemoveFromRecents(
          this.mAtmService, str, userId, "setMaxAspectRatioPolicy");
    }
    StringBuilder sb = new StringBuilder();
    sb.append("setMaxAspectRatioPolicy: ");
    sb.append(z ? "enabled" : "disabled");
    sb.append(", package=");
    sb.append(str);
    sb.append(", restartTaskId=");
    sb.append(i2);
    sb.append(", updated=");
    sb.append(z3);
    Slog.d("PackageSettingsManager", sb.toString());
  }

  public ConcurrentHashMap getChangeValuesAsUser(int i) {
    return this.mUserChange.getChangeValuesAsUser(i);
  }

  @Override // com.android.server.wm.PackagesChange
  public void dump(PrintWriter printWriter, String str) {
    printWriter.println(str + "mDisplayMaxAspectRatio=" + this.mDisplayMaxAspectRatio);
    printWriter.println(
        str + "mDisplayMaxAspectRatioWithCutout=" + this.mDisplayMaxAspectRatioWithCutout);
    printWriter.println(str + "mDefaultDisplayAspectRatio=" + this.mDefaultDisplayAspectRatio);
    if (this.mSupportsMaxAspectRatio) {
      printWriter.println(str + "mSupportsMaxAspectRatio=true");
    }
    ConcurrentHashMap concurrentHashMap = this.mPackageWithAspectRatioApplied;
    if (concurrentHashMap == null || concurrentHashMap.isEmpty()) {
      return;
    }
    printWriter.println(str + "mPackageWithAspectRatioApplied");
    for (Map.Entry entry : this.mPackageWithAspectRatioApplied.entrySet()) {
      printWriter.println(
          str + "  " + ((String) entry.getKey()) + "/" + ((String) entry.getValue()));
    }
  }

  public static String policyToString(int i) {
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 3 ? Integer.toString(i) : "UnchangeableAspectRatio"
                : "UnchangeableFullScreen"
            : "FullScreen"
        : "Default";
  }
}
