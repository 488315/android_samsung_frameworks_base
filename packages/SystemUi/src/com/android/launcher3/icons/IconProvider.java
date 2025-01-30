package com.android.launcher3.icons;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.R;
import java.util.Calendar;
import java.util.function.IntFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IconProvider {
    public final ComponentName mCalendar;
    public final ComponentName mClock;
    public final Context mContext;
    public static final int CONFIG_ICON_MASK_RES_ID = Resources.getSystem().getIdentifier("config_icon_mask", "string", "android");
    public static final boolean ATLEAST_T = true;

    public IconProvider(Context context) {
        this.mContext = context;
        String string = context.getString(R.string.calendar_component_name);
        this.mCalendar = TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
        String string2 = context.getString(R.string.clock_component_name);
        this.mClock = TextUtils.isEmpty(string2) ? null : ComponentName.unflattenFromString(string2);
    }

    public final Drawable getIcon(final int i, ActivityInfo activityInfo) {
        Drawable forExtras;
        String str = activityInfo.applicationInfo.packageName;
        Drawable drawable = null;
        boolean z = ATLEAST_T;
        Context context = this.mContext;
        ComponentName componentName = this.mCalendar;
        if (componentName == null || !componentName.getPackageName().equals(str)) {
            ComponentName componentName2 = this.mClock;
            if (componentName2 != null && componentName2.getPackageName().equals(str)) {
                String packageName = componentName2.getPackageName();
                int i2 = ClockDrawableWrapper.$r8$clinit;
                try {
                    PackageManager packageManager = context.getPackageManager();
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 8320);
                    final Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo);
                    forExtras = ClockDrawableWrapper.forExtras(applicationInfo.metaData, new IntFunction() { // from class: com.android.launcher3.icons.ClockDrawableWrapper$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i3) {
                            return resourcesForApplication.getDrawableForDensity(i3, i);
                        }
                    });
                } catch (Exception e) {
                    Log.d("ClockDrawableWrapper", "Unable to load clock drawable info", e);
                }
            }
            forExtras = null;
        } else {
            PackageManager packageManager2 = context.getPackageManager();
            try {
                Bundle bundle = packageManager2.getActivityInfo(componentName, 8320).metaData;
                Resources resourcesForApplication2 = packageManager2.getResourcesForApplication(componentName.getPackageName());
                int i3 = 0;
                if (bundle != null) {
                    int i4 = bundle.getInt(componentName.getPackageName() + ".dynamic_icons", 0);
                    if (i4 != 0) {
                        try {
                            i3 = resourcesForApplication2.obtainTypedArray(i4).getResourceId(Calendar.getInstance().get(5) - 1, 0);
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                }
                if (i3 != 0) {
                    forExtras = resourcesForApplication2.getDrawableForDensity(i3, i, null);
                    if (z) {
                        boolean z2 = forExtras instanceof AdaptiveIconDrawable;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused2) {
            }
            forExtras = null;
        }
        if (forExtras == null) {
            int iconResource = activityInfo.getIconResource();
            Context context2 = this.mContext;
            if (i != 0 && iconResource != 0) {
                try {
                    drawable = context2.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo).getDrawableForDensity(iconResource, i);
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused3) {
                }
            }
            forExtras = drawable == null ? activityInfo.loadIcon(context2.getPackageManager()) : drawable;
            if (z) {
                boolean z3 = forExtras instanceof AdaptiveIconDrawable;
            }
        }
        return forExtras;
    }
}
