package android.content.pm;

import android.app.SemAppIconSolution;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
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
        String trim = getActivityInfo().loadLabel(this.mPm).toString().trim();
        if (isVisible(trim)) {
            return trim;
        }
        String trim2 = getApplicationInfo().loadLabel(this.mPm).toString().trim();
        return isVisible(trim2) ? trim2 : getComponentName().getPackageName();
    }

    public float getLoadingProgress() {
        return this.mInternal.getIncrementalStatesInfo().getProgress();
    }

    public Drawable getIcon(int i) {
        return getIcon(i, useThemeIcon());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.drawable.Drawable getIcon(int r3, boolean r4) {
        /*
            r2 = this;
            if (r4 == 0) goto Lf
            android.content.pm.LauncherActivityInfoInternal r3 = r2.mInternal
            android.content.pm.ActivityInfo r3 = r3.getActivityInfo()
            android.content.pm.PackageManager r2 = r2.mPm
            android.graphics.drawable.Drawable r2 = r3.loadIcon(r2)
            return r2
        Lf:
            android.content.pm.ActivityInfo r4 = r2.getActivityInfo()
            int r4 = r4.getIconResource()
            if (r3 == 0) goto L34
            if (r4 == 0) goto L34
            android.content.pm.ActivityInfo r0 = r2.getActivityInfo()
            boolean r0 = r0.isArchived
            if (r0 != 0) goto L34
            android.content.pm.PackageManager r0 = r2.mPm     // Catch: java.lang.Throwable -> L34
            android.content.pm.ActivityInfo r1 = r2.getActivityInfo()     // Catch: java.lang.Throwable -> L34
            android.content.pm.ApplicationInfo r1 = r1.applicationInfo     // Catch: java.lang.Throwable -> L34
            android.content.res.Resources r0 = r0.getResourcesForApplication(r1)     // Catch: java.lang.Throwable -> L34
            android.graphics.drawable.Drawable r3 = r0.getDrawableForDensity(r4, r3)     // Catch: java.lang.Throwable -> L34
            goto L35
        L34:
            r3 = 0
        L35:
            if (r3 != 0) goto L41
            android.content.pm.ActivityInfo r3 = r2.getActivityInfo()
            android.content.pm.PackageManager r2 = r2.mPm
            android.graphics.drawable.Drawable r3 = r3.loadIcon(r2)
        L41:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.LauncherActivityInfo.getIcon(int, boolean):android.graphics.drawable.Drawable");
    }

    public Drawable getUnthemedIcon(int i) {
        int iconResource = getActivityInfo().getIconResource();
        Drawable drawable = null;
        if (iconResource != 0) {
            try {
                drawable = this.mPm.getResourcesForApplication(getActivityInfo().applicationInfo).getDrawable(iconResource, null);
            } catch (Exception e) {
                Log.i(TAG, "Failed to get original icon from resources: " + getActivityInfo().packageName, e);
            }
            if (drawable != null) {
                if (drawable instanceof AdaptiveIconDrawable) {
                    return drawable;
                }
                Log.i(TAG, "Need to process non-adaptive icon: " + getActivityInfo().packageName);
                drawable = this.mPm.semGetDrawableForIconTray(drawable, 2);
            }
        }
        if (drawable != null) {
            return drawable;
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
        Drawable drawable;
        ActivityInfo activityInfo = this.mInternal.getActivityInfo();
        String str = activityInfo.packageName;
        boolean useThemeIcon = useThemeIcon();
        boolean z = true;
        if (PmUtils.supportLiveIcon(activityInfo.applicationInfo, this.mContext)) {
            Log.i(TAG, "Trying to load live icon for " + str);
            drawable = this.mContext.getPackageManager().loadUnbadgedItemIcon(activityInfo, activityInfo.applicationInfo, true, 48);
        } else {
            drawable = null;
        }
        if (drawable == null) {
            drawable = getIcon(i, useThemeIcon);
            if (activityInfo.getIconResource() != 0 && !activityInfo.isArchived) {
                z = false;
            }
            if (!useThemeIcon && !z && (this.mPm.semCheckComponentMetadataForIconTray(str, activityInfo.name) || this.mPm.semShouldPackIntoIconTray(str))) {
                drawable = this.mPm.semGetDrawableForIconTray(drawable, 48, str, i);
            }
        }
        Drawable badgedIconIfNeed = getBadgedIconIfNeed(drawable);
        if (badgedIconIfNeed != null) {
            Log.i(TAG, "packageName: " + str + ", useThemeIcon: " + useThemeIcon + ", height: " + badgedIconIfNeed.getIntrinsicHeight() + ", width: " + badgedIconIfNeed.getIntrinsicWidth() + ", density: " + i);
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
