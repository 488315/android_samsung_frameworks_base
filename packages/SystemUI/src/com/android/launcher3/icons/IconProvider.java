package com.android.launcher3.icons;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.BuildCompat;
import com.android.systemui.R;
import java.util.Calendar;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class IconProvider {
    public static final boolean ATLEAST_T;
    public final ComponentName mCalendar;
    public final ComponentName mClock;
    public final Context mContext;

    static {
        int i = BuildCompat.$r8$clinit;
        ATLEAST_T = true;
    }

    public IconProvider(Context context) {
        this.mContext = context;
        String string = context.getString(R.string.calendar_component_name);
        this.mCalendar = TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
        String string2 = context.getString(R.string.clock_component_name);
        this.mClock = TextUtils.isEmpty(string2) ? null : ComponentName.unflattenFromString(string2);
    }

    public final Drawable getIcon(ComponentInfo componentInfo) {
        return getIcon(componentInfo, this.mContext.getResources().getConfiguration().densityDpi);
    }

    public final Drawable getIcon(ComponentInfo componentInfo, int i) {
        Drawable forExtras;
        int i2;
        int i3;
        ApplicationInfo applicationInfo = componentInfo.applicationInfo;
        String str = ((PackageItemInfo) componentInfo).packageName;
        ComponentName componentName = this.mCalendar;
        boolean z = ATLEAST_T;
        Drawable drawable = null;
        if (componentName == null || !componentName.getPackageName().equals(str)) {
            ComponentName componentName2 = this.mClock;
            if (componentName2 != null && componentName2.getPackageName().equals(str)) {
                Context context = this.mContext;
                String packageName = this.mClock.getPackageName();
                int i4 = ClockDrawableWrapper.$r8$clinit;
                try {
                    PackageManager packageManager = context.getPackageManager();
                    ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo(packageName, 8320);
                    forExtras = ClockDrawableWrapper.forExtras(applicationInfo2.metaData, new ClockDrawableWrapper$$ExternalSyntheticLambda0(packageManager.getResourcesForApplication(applicationInfo2), i));
                } catch (Exception e) {
                    Log.d("ClockDrawableWrapper", "Unable to load clock drawable info", e);
                }
            }
            forExtras = null;
        } else {
            PackageManager packageManager2 = this.mContext.getPackageManager();
            try {
                Bundle bundle = packageManager2.getActivityInfo(this.mCalendar, 8320).metaData;
                Resources resourcesForApplication = packageManager2.getResourcesForApplication(this.mCalendar.getPackageName());
                int i5 = 0;
                if (bundle != null) {
                    int i6 = bundle.getInt(this.mCalendar.getPackageName() + ".dynamic_icons", 0);
                    if (i6 != 0) {
                        try {
                            i5 = resourcesForApplication.obtainTypedArray(i6).getResourceId(Calendar.getInstance().get(5) - 1, 0);
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                }
                if (i5 != 0) {
                    forExtras = resourcesForApplication.getDrawableForDensity(i5, i, null);
                    if (z) {
                        boolean z2 = forExtras instanceof AdaptiveIconDrawable;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused2) {
            }
            forExtras = null;
        }
        if (forExtras == null) {
            int i7 = BuildCompat.$r8$clinit;
            forExtras = ((PackageItemInfo) componentInfo).isArchived ? componentInfo.loadIcon(this.mContext.getPackageManager()) : null;
            if (forExtras == null && i != 0 && (((PackageItemInfo) componentInfo).icon != 0 || applicationInfo.icon != 0)) {
                try {
                    Resources resourcesForApplication2 = this.mContext.getPackageManager().getResourcesForApplication(applicationInfo);
                    if (componentInfo != applicationInfo && (i3 = ((PackageItemInfo) componentInfo).icon) != 0) {
                        try {
                            forExtras = resourcesForApplication2.getDrawableForDensity(i3, i);
                        } catch (Resources.NotFoundException unused3) {
                        }
                    }
                    if (forExtras == null && (i2 = applicationInfo.icon) != 0) {
                        try {
                            drawable = resourcesForApplication2.getDrawableForDensity(i2, i);
                        } catch (Resources.NotFoundException unused4) {
                        }
                        forExtras = drawable;
                    }
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused5) {
                }
            }
            if (forExtras == null) {
                Drawable drawableForDensity = Resources.getSystem().getDrawableForDensity(android.R.drawable.sym_def_app_icon, i);
                Objects.requireNonNull(drawableForDensity);
                forExtras = drawableForDensity;
            }
            if (z) {
                boolean z3 = forExtras instanceof AdaptiveIconDrawable;
            }
        }
        return forExtras;
    }
}
