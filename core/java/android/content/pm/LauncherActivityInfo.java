package android.content.pm;

import android.app.SemAppIconSolution;
import android.content.ComponentName;
import android.content.Context;
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
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.rune.PMRune;

import java.util.Objects;

/* loaded from: classes.dex */
public class LauncherActivityInfo {
    private static final String TAG = "LauncherActivityInfo";
    private static final UnicodeSet TRIMMABLE_CHARACTERS =
            new UnicodeSet("[[:White_Space:][:Default_Ignorable_Code_Point:][:gc=Cc:]]", false)
                    .freeze();
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
        if (!Flags.lightweightInvisibleLabelDetection()) {
            return getActivityInfo().loadLabel(this.mPm);
        }
        CharSequence label = trim(getActivityInfo().loadLabel(this.mPm));
        if (TextUtils.isEmpty(label)) {
            CharSequence label2 = trim(getApplicationInfo().loadLabel(this.mPm));
            if (TextUtils.isEmpty(label2)) {
                return getComponentName().getPackageName();
            }
            return label2;
        }
        return label;
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
        if (density != 0 && iconRes != 0 && !getActivityInfo().isArchived) {
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

    public Drawable getUnthemedIcon(int density) {
        int iconRes = getActivityInfo().getIconResource();
        Drawable icon = null;
        if (iconRes != 0) {
            try {
                Resources resources =
                        this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo);
                icon = resources.getDrawable(iconRes, null);
            } catch (Exception e) {
                Log.i(
                        TAG,
                        "Failed to get original icon from resources: "
                                + getActivityInfo().packageName,
                        e);
            }
            if (icon != null) {
                if (icon instanceof AdaptiveIconDrawable) {
                    return icon;
                }
                Log.i(TAG, "Need to process non-adaptive icon: " + getActivityInfo().packageName);
                icon = this.mPm.semGetDrawableForIconTray(icon, 2);
            }
        }
        if (icon == null) {
            Log.i(TAG, "Couldn't get the unthemed icon: " + getActivityInfo().packageName);
            return getIcon(density, false);
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

    private static boolean isTrimmable(Paint paint, CharSequence ch) {
        Objects.requireNonNull(paint);
        Objects.requireNonNull(ch);
        if (TextUtils.isEmpty(ch) || Character.codePointCount(ch, 0, ch.length()) != 1) {
            return false;
        }
        return TRIMMABLE_CHARACTERS.contains(ch) || !paint.hasGlyph(ch.toString());
    }

    public static CharSequence trimStart(CharSequence sequence) {
        Objects.requireNonNull(sequence);
        if (TextUtils.isEmpty(sequence)) {
            return sequence;
        }
        Paint paint = new Paint();
        int trimCount = 0;
        int[] codePoints = sequence.codePoints().toArray();
        for (int i : codePoints) {
            String ch = new String(new int[] {i}, 0, 1);
            if (!isTrimmable(paint, ch)) {
                break;
            }
            trimCount += ch.length();
        }
        if (trimCount == 0) {
            return sequence;
        }
        return sequence.subSequence(trimCount, sequence.length());
    }

    public static CharSequence trimEnd(CharSequence sequence) {
        Objects.requireNonNull(sequence);
        if (TextUtils.isEmpty(sequence)) {
            return sequence;
        }
        Paint paint = new Paint();
        int trimCount = 0;
        int[] codePoints = sequence.codePoints().toArray();
        for (int i = codePoints.length - 1; i >= 0; i--) {
            String ch = new String(new int[] {codePoints[i]}, 0, 1);
            if (!isTrimmable(paint, ch)) {
                break;
            }
            trimCount += ch.length();
        }
        if (trimCount == 0) {
            return sequence;
        }
        return sequence.subSequence(0, sequence.length() - trimCount);
    }

    public static CharSequence trim(CharSequence sequence) {
        Objects.requireNonNull(sequence);
        if (TextUtils.isEmpty(sequence)) {
            return sequence;
        }
        CharSequence result = trimStart(sequence);
        if (TextUtils.isEmpty(result)) {
            return result;
        }
        return trimEnd(result);
    }

    public Drawable semGetBadgedIconForIconTray(int density) {
        ActivityInfo activityInfo = this.mInternal.getActivityInfo();
        String pkgName = activityInfo.packageName;
        boolean useThemeIcon = useThemeIcon();
        Drawable originalIcon = null;
        boolean z = true;
        if (PmUtils.supportLiveIcon(activityInfo.applicationInfo, this.mContext)) {
            Log.i(TAG, "Trying to load live icon for " + pkgName);
            originalIcon =
                    this.mContext
                            .getPackageManager()
                            .loadUnbadgedItemIcon(
                                    activityInfo, activityInfo.applicationInfo, true, 48);
        }
        if (originalIcon == null) {
            originalIcon = getIcon(density, useThemeIcon);
            if (activityInfo.getIconResource() != 0 && !activityInfo.isArchived) {
                z = false;
            }
            boolean isDefaultIcon = z;
            if (!useThemeIcon
                    && !isDefaultIcon
                    && (this.mPm.semCheckComponentMetadataForIconTray(pkgName, activityInfo.name)
                            || this.mPm.semShouldPackIntoIconTray(pkgName))) {
                originalIcon =
                        this.mPm.semGetDrawableForIconTray(originalIcon, 48, pkgName, density);
            }
        }
        Drawable badgedIcon = getBadgedIconIfNeed(originalIcon);
        if (badgedIcon != null) {
            Log.i(
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
