package android.util;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.internal.R;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class IconDrawableFactory {
    protected final Context mContext;
    protected final DevicePolicyManager mDpm;
    protected final boolean mEmbedShadow;
    protected final LauncherIcons mLauncherIcons;
    protected final PackageManager mPm;
    protected final UserManager mUm;

    private IconDrawableFactory(Context context, boolean z) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mLauncherIcons = new LauncherIcons(context);
        this.mEmbedShadow = z;
    }

    protected boolean needsBadging(ApplicationInfo applicationInfo, int i) {
        return applicationInfo.isInstantApp() || this.mUm.hasBadge(i);
    }

    public Drawable getBadgedIcon(ApplicationInfo applicationInfo) {
        return getBadgedIcon(applicationInfo, UserHandle.getUserId(applicationInfo.uid));
    }

    public Drawable getBadgedIcon(ApplicationInfo applicationInfo, int i) {
        return getBadgedIcon(applicationInfo, applicationInfo, i);
    }

    public Drawable getBadgedIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo, final int i) {
        Drawable loadUnbadgedItemIcon = this.mPm.loadUnbadgedItemIcon(packageItemInfo, applicationInfo, true, 1);
        if (!this.mEmbedShadow && !needsBadging(applicationInfo, i)) {
            return loadUnbadgedItemIcon;
        }
        Drawable shadowedIcon = getShadowedIcon(loadUnbadgedItemIcon);
        if (SemPersonaManager.isKnoxId(i)) {
            return this.mPm.getUserBadgedIcon(shadowedIcon, new UserHandle(i));
        }
        if (SemDualAppManager.isDualAppId(i)) {
            return this.mPm.getUserBadgedIcon(shadowedIcon, new UserHandle(i));
        }
        if (applicationInfo.isInstantApp()) {
            int color = Resources.getSystem().getColor(R.color.instant_app_badge, null);
            shadowedIcon = this.mLauncherIcons.getBadgedDrawable(shadowedIcon, this.mContext.getDrawable(R.drawable.ic_instant_icon_badge_bolt), color);
        }
        if (!this.mUm.hasBadge(i)) {
            return shadowedIcon;
        }
        return this.mLauncherIcons.getBadgedDrawable(shadowedIcon, this.mDpm.getResources().getDrawable(getUpdatableUserIconBadgeId(i), DevicePolicyResources.Drawables.Style.SOLID_COLORED, new Supplier() { // from class: android.util.IconDrawableFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable lambda$getBadgedIcon$0;
                lambda$getBadgedIcon$0 = IconDrawableFactory.this.lambda$getBadgedIcon$0(i);
                return lambda$getBadgedIcon$0;
            }
        }), this.mUm.getUserBadgeColor(i));
    }

    private String getUpdatableUserIconBadgeId(int i) {
        return this.mUm.isManagedProfile(i) ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE : DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserIconBadge, reason: merged with bridge method [inline-methods] */
    public Drawable lambda$getBadgedIcon$0(int i) {
        return this.mContext.getResources().getDrawable(this.mUm.getUserIconBadgeResId(i));
    }

    public Drawable getShadowedIcon(Drawable drawable) {
        return this.mLauncherIcons.wrapIconDrawableWithShadow(drawable);
    }

    public static IconDrawableFactory newInstance(Context context) {
        return new IconDrawableFactory(context, true);
    }

    public static IconDrawableFactory newInstance(Context context, boolean z) {
        return new IconDrawableFactory(context, z);
    }
}
