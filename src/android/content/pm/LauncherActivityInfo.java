package android.content.pm;

import android.app.SemAppIconSolution;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSet;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.samsung.android.core.pm.PmUtils;
import com.samsung.android.rune.PMRune;
import java.util.Objects;

/* loaded from: classes.dex */
public class LauncherActivityInfo {
    private static final UnicodeSet INVISIBLE_CHARACTERS = new UnicodeSet("[[:White_Space:][:Default_Ignorable_Code_Point:][:gc=Cc:]]", false).freeze();
    private static final int PREFIX_CONSECUTIVE_INVISIBLE_CHARACTERS_MAXIMUM = 3;
    private static final String TAG = "LauncherActivityInfo";
    private Context mContext;
    private final LauncherActivityInfoInternal mInternal;
    private final PackageManager mPm;

    LauncherActivityInfo(Context context, LauncherActivityInfoInternal launcherActivityInfoInternal) {
        this.mPm = context.getPackageManager();
        this.mInternal = launcherActivityInfoInternal;
        this.mContext = context;
    }

    public ComponentName getComponentName() {
        return this.mInternal.getComponentName();
    }

    public UserHandle getUser() {
        return this.mInternal.getUser();
    }

    public CharSequence getLabel() {
        if (!Flags.lightweightInvisibleLabelDetection()) {
            return getActivityInfo().loadLabel(this.mPm);
        }
        String strTrim = getActivityInfo().loadLabel(this.mPm).toString().trim();
        if (isVisible(strTrim)) {
            return strTrim;
        }
        String strTrim2 = getApplicationInfo().loadLabel(this.mPm).toString().trim();
        return isVisible(strTrim2) ? strTrim2 : getComponentName().getPackageName();
    }

    public float getLoadingProgress() {
        return this.mInternal.getIncrementalStatesInfo().getProgress();
    }

    public Drawable getIcon(int i) {
        return getIcon(i, useThemeIcon());
    }

    private Drawable getIcon(int i, boolean z) {
        Drawable drawableForDensity;
        if (z) {
            return this.mInternal.getActivityInfo().loadIcon(this.mPm);
        }
        int iconResource = getActivityInfo().getIconResource();
        if (i == 0 || iconResource == 0 || getActivityInfo().isArchived) {
            drawableForDensity = null;
        } else {
            try {
                drawableForDensity = this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo).getDrawableForDensity(iconResource, i);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            }
        }
        return drawableForDensity == null ? getActivityInfo().loadIcon(this.mPm) : drawableForDensity;
    }

    public Drawable getUnthemedIcon(int i) throws Resources.NotFoundException {
        int iconResource = getActivityInfo().getIconResource();
        Drawable drawableSemGetDrawableForIconTray = null;
        if (iconResource != 0) {
            try {
                drawableSemGetDrawableForIconTray = this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo).getDrawable(iconResource, null);
            } catch (Exception e) {
                Log.i(TAG, "Failed to get original icon from resources: " + getActivityInfo().packageName, e);
            }
            if (drawableSemGetDrawableForIconTray != null) {
                if (drawableSemGetDrawableForIconTray instanceof AdaptiveIconDrawable) {
                    return drawableSemGetDrawableForIconTray;
                }
                Log.i(TAG, "Need to process non-adaptive icon: " + getActivityInfo().packageName);
                drawableSemGetDrawableForIconTray = this.mPm.semGetDrawableForIconTray(drawableSemGetDrawableForIconTray, 2);
            }
        }
        if (drawableSemGetDrawableForIconTray != null) {
            return drawableSemGetDrawableForIconTray;
        }
        Log.i(TAG, "Couldn't get the unthemed icon: " + getActivityInfo().packageName);
        return getIcon(i, false);
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
        } catch (PackageManager.NameNotFoundException unused) {
            return 0L;
        }
    }

    public String getName() {
        return getActivityInfo().name;
    }

    public Drawable getBadgedIcon(int i) {
        return this.mPm.getUserBadgedIcon(getIcon(i), this.mInternal.getUser());
    }

    public boolean supportsMultiInstance() {
        return this.mInternal.supportsMultiInstance();
    }

    public static boolean isVisible(CharSequence charSequence) {
        Objects.requireNonNull(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        Paint paint = new Paint();
        int[] array = charSequence.codePoints().toArray();
        int i = 0;
        int i2 = 0;
        for (int i3 : array) {
            String str = new String(new int[]{i3}, 0, 1);
            if (!INVISIBLE_CHARACTERS.contains(str)) {
                if (paint.hasGlyph(str)) {
                    break;
                }
                i2++;
            } else {
                i++;
                if (i2 == 0 && i >= 3) {
                    return false;
                }
            }
        }
        return i + i2 < array.length;
    }

    public Drawable semGetBadgedIconForIconTray(int i) {
        Drawable icon;
        ActivityInfo activityInfo = this.mInternal.getActivityInfo();
        String str = activityInfo.packageName;
        boolean zUseThemeIcon = useThemeIcon();
        boolean z = true;
        if (PmUtils.supportLiveIcon(activityInfo.applicationInfo, this.mContext)) {
            Log.i(TAG, "Trying to load live icon for " + str);
            icon = this.mContext.getPackageManager().loadUnbadgedItemIcon(activityInfo, activityInfo.applicationInfo, true, 48);
        } else {
            icon = null;
        }
        if (icon == null) {
            icon = getIcon(i, zUseThemeIcon);
            if (activityInfo.getIconResource() != 0 && !activityInfo.isArchived) {
                z = false;
            }
            if (!zUseThemeIcon && !z && (this.mPm.semCheckComponentMetadataForIconTray(str, activityInfo.name) || this.mPm.semShouldPackIntoIconTray(str))) {
                icon = this.mPm.semGetDrawableForIconTray(icon, 48, str, i);
            }
        }
        Drawable badgedIconIfNeed = getBadgedIconIfNeed(icon);
        if (badgedIconIfNeed != null) {
            Log.i(TAG, "packageName: " + str + ", useThemeIcon: " + zUseThemeIcon + ", height: " + badgedIconIfNeed.getIntrinsicHeight() + ", width: " + badgedIconIfNeed.getIntrinsicWidth() + ", density: " + i);
        }
        return badgedIconIfNeed;
    }

    private Drawable getBadgedIconIfNeed(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED && this.mPm.shouldAppSupportBadgeIcon(this.mInternal.getActivityInfo().packageName, this.mInternal.getUser().getIdentifier())) {
            drawable = this.mPm.getMonetizeBadgedIcon(drawable);
        }
        return this.mPm.getUserBadgedIcon(drawable, this.mInternal.getUser());
    }

    private boolean useThemeIcon() {
        return SemAppIconSolution.getInstance(this.mContext).isAppIconThemePackageSet() || Settings.System.getString(this.mContext.getContentResolver(), "current_sec_appicon_theme_package") != null;
    }
}
