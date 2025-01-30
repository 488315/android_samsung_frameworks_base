package android.content.p002pm;

import android.app.SemAppIconSolution;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.core.p036pm.PmUtils;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.rune.PMRune;

/* loaded from: classes.dex */
public class LauncherActivityInfo {
  private static final String TAG = "LauncherActivityInfo";
  private Context mContext;
  private final LauncherActivityInfoInternal mInternal;
  private final PackageManager mPm;

  LauncherActivityInfo(Context context, LauncherActivityInfoInternal internal) {
    this.mPm = context.getPackageManager();
    this.mInternal = internal;
    this.mContext = context;
  }

  public ComponentName getComponentName() {
    return this.mInternal.getComponentName();
  }

  public UserHandle getUser() {
    return this.mInternal.getUser();
  }

  public CharSequence getLabel() {
    return getActivityInfo().loadLabel(this.mPm);
  }

  public float getLoadingProgress() {
    return this.mInternal.getIncrementalStatesInfo().getProgress();
  }

  public Drawable getIcon(int density) {
    return getIcon(density, useThemeIcon());
  }

  private Drawable getIcon(int density, boolean useThemeIcon) {
    if (useThemeIcon) {
      return this.mInternal.getActivityInfo().loadIcon(this.mPm);
    }
    int iconRes = getActivityInfo().getIconResource();
    Drawable icon = null;
    if (density != 0 && iconRes != 0) {
      try {
        Resources resources =
            this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo);
        icon = resources.getDrawableForDensity(iconRes, density);
      } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
      }
    }
    if (icon == null) {
      return getActivityInfo().loadIcon(this.mPm);
    }
    return icon;
  }

  public int getApplicationFlags() {
    return getActivityInfo().flags;
  }

  public ActivityInfo getActivityInfo() {
    return this.mInternal.getActivityInfo();
  }

  public ApplicationInfo getApplicationInfo() {
    return getActivityInfo().applicationInfo;
  }

  public long getFirstInstallTime() {
    try {
      return this.mPm.getPackageInfo(getActivityInfo().packageName, 8192).firstInstallTime;
    } catch (PackageManager.NameNotFoundException e) {
      return 0L;
    }
  }

  public String getName() {
    return getActivityInfo().name;
  }

  public Drawable getBadgedIcon(int density) {
    Drawable originalIcon = getIcon(density);
    return this.mPm.getUserBadgedIcon(originalIcon, this.mInternal.getUser());
  }

  public Drawable semGetBadgedIconForIconTray(int density) {
    ActivityInfo activityInfo = this.mInternal.getActivityInfo();
    String pkgName = activityInfo.packageName;
    boolean useThemeIcon = false;
    Drawable originalIcon = null;
    if (PmUtils.supportLiveIcon(activityInfo.applicationInfo, this.mContext)) {
      Log.m98i(TAG, "Trying to load live icon for " + pkgName);
      originalIcon =
          this.mContext
              .getPackageManager()
              .loadUnbadgedItemIcon(activityInfo, activityInfo.applicationInfo, true, 48);
    }
    if (originalIcon == null) {
      useThemeIcon = useThemeIcon();
      originalIcon = getIcon(density, useThemeIcon);
      boolean isDefaultIcon = activityInfo.getIconResource() == 0;
      if (!useThemeIcon
          && !isDefaultIcon
          && (this.mPm.semCheckComponentMetadataForIconTray(pkgName, activityInfo.name)
              || this.mPm.semShouldPackIntoIconTray(pkgName))) {
        originalIcon = this.mPm.semGetDrawableForIconTray(originalIcon, 48, pkgName, density);
      }
    }
    Drawable badgedIcon = getBadgedIconIfNeed(originalIcon);
    if (badgedIcon != null) {
      Log.m98i(
          TAG,
          "packageName: "
              + pkgName
              + ", useThemeIcon: "
              + useThemeIcon
              + ", height: "
              + badgedIcon.getIntrinsicHeight()
              + ", width: "
              + badgedIcon.getIntrinsicWidth()
              + ", density: "
              + density);
    }
    return badgedIcon;
  }

  private Drawable getBadgedIconIfNeed(Drawable originalIcon) {
    if (originalIcon == null) {
      return null;
    }
    if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED
        && this.mPm.shouldAppSupportBadgeIcon(
            this.mInternal.getActivityInfo().packageName,
            this.mInternal.getUser().getIdentifier())) {
      originalIcon = this.mPm.getMonetizeBadgedIcon(originalIcon);
    }
    return this.mPm.getUserBadgedIcon(originalIcon, this.mInternal.getUser());
  }

  private boolean useThemeIcon() {
    return (SemAppIconSolution.getInstance(this.mContext).isAppIconThemePackageSet()
            || Settings.System.getString(
                    this.mContext.getContentResolver(),
                    Settings.System.SEM_CURRENT_APP_ICON_PACKAGE)
                != null)
        && !DesktopModeFeature.isDesktopMode(this.mContext);
  }
}
