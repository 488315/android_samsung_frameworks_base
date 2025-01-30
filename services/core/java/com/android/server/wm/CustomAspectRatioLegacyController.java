package com.android.server.wm;

import android.content.pm.ApplicationInfo;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class CustomAspectRatioLegacyController {
  public final ActivityTaskManagerService mAtmService;
  public boolean mFinished;
  public final PackageFeatureUserChange mLegacy =
      new PackageFeatureUserChange(
          512, PackageFeatureUserChangePersister.CONVENTIONAL_MODE_DIRECTORY, "PackageMap");
  public final FullScreenAppsSupportUtils mFullScreenUtils = FullScreenAppsSupportUtils.get();

  public CustomAspectRatioLegacyController(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtmService = activityTaskManagerService;
    ActivityTaskManagerServiceExt activityTaskManagerServiceExt = activityTaskManagerService.mExt;
    activityTaskManagerServiceExt.mCustomAspectRatioController.mLegacyController = this;
    activityTaskManagerServiceExt.mDisplayCutoutController.mLegacyController = this;
  }

  public synchronized void migrateIfNeeded() {
    if (this.mFinished || this.mLegacy.getUserIds().isEmpty()) {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d("PackageSettingsManager", "MigrationPackages are empty. Finished=" + this.mFinished);
      }
      return;
    }
    try {
      Slog.d("PackageSettingsManager", "Started full screen apps migration.");
      Iterator it = this.mLegacy.getUserIds().iterator();
      while (it.hasNext()) {
        migrate(((Integer) it.next()).intValue());
      }
      this.mFinished = true;
      Slog.d("PackageSettingsManager", "Finished full screen apps migration.");
    } catch (Exception e) {
      Slog.e("PackageSettingsManager", "Failed to migrate full screen apps.", e);
    }
  }

  public final void migrate(int i) {
    if (CoreRune.SAFE_DEBUG) {
      Slog.v("PackageSettingsManager", "Migrate userId=" + i);
    }
    for (Map.Entry entry : this.mLegacy.getChangeValuesAsUser(i).entrySet()) {
      String str = (String) entry.getKey();
      if (((Integer) entry.getValue()).intValue() != 1) {
        if (this.mFullScreenUtils.containsInDefaultFullScreenList(str)) {
          Slog.v("PackageSettingsManager", "DefaultFullScreenPackage, packageName=" + str);
          this.mAtmService.mExt.mCustomAspectRatioController.getChangeValuesAsUser(i).put(str, 0);
          this.mAtmService.mExt.mDisplayCutoutController.getChangeValuesAsUser(i).put(str, 0);
        }
      } else {
        ApplicationInfo applicationInfo =
            this.mAtmService.mWindowManager.mPmInternal.getApplicationInfo(str, 128L, 1000, i);
        if (applicationInfo == null) {
          Slog.v(
              "PackageSettingsManager",
              "MigrationPackage packageName=" + str + ", appInfo is null");
        } else {
          boolean isUnchangeableFullScreenMode =
              this.mAtmService.mExt.mCustomAspectRatioController.isUnchangeableFullScreenMode(
                  i, applicationInfo, null);
          if (CoreRune.SAFE_DEBUG) {
            Slog.v(
                "PackageSettingsManager",
                "MigrationPackage packageName="
                    + str
                    + ", isUnchangeable="
                    + isUnchangeableFullScreenMode);
          }
          if (!isUnchangeableFullScreenMode) {
            this.mAtmService.mExt.mCustomAspectRatioController.getChangeValuesAsUser(i).put(str, 1);
          }
          this.mAtmService.mExt.mDisplayCutoutController.getChangeValuesAsUser(i).put(str, 1);
        }
      }
    }
    PackageFeatureUserChangePersister.getInstance().requestToSave(i, 130);
    this.mLegacy.reset(i);
  }
}
