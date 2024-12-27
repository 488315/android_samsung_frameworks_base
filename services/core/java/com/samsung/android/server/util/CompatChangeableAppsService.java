package com.samsung.android.server.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.util.Slog;

import com.samsung.android.core.CompatChangeableApps;
import com.samsung.android.core.CompatChangeablePackageInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CompatChangeableAppsService {
    public PackageManager mPackageManager;

    public final class BuilderWrapper extends CompatChangeablePackageInfo.Builder {
        public final ApplicationInfo mApplicationInfo;
        public final String mPackageName;
        public final PackageManager mPm;

        public BuilderWrapper(
                ApplicationInfo applicationInfo, PackageManager packageManager, String str) {
            this.mPm = packageManager;
            this.mPackageName = str;
            this.mApplicationInfo = applicationInfo;
        }

        public final CompatChangeablePackageInfo build() {
            setPackageName(this.mPackageName);
            setHasLauncherActivity(true);
            ApplicationInfo applicationInfo = this.mApplicationInfo;
            if (applicationInfo != null) {
                setUid(applicationInfo.uid);
                setHasGameCategory(this.mApplicationInfo.category == 0);
            }
            Boolean bool = Boolean.FALSE;
            setIsResizeableActivityOverrideDisallowed(
                    equalsProperty(
                            bool,
                            "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES"));
            setIsOrientationOverrideDisallowed(
                    equalsProperty(
                            bool, "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE"));
            setIsMinAspectRatioOverrideDisallowed(
                    equalsProperty(
                            bool,
                            "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE",
                            "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE"));
            setIsActivityEmbeddingSplitsEnabled(
                    equalsProperty(
                            Boolean.TRUE,
                            "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED"));
            return super.build();
        }

        public final boolean equalsProperty(Boolean bool, String... strArr) {
            for (String str : strArr) {
                if (bool.equals(
                        CompatChangeableApps.readComponentProperty(
                                this.mPm, this.mPackageName, str))) {
                    return true;
                }
            }
            return false;
        }
    }

    public abstract class LazyHolder {
        public static final CompatChangeableAppsService sService =
                new CompatChangeableAppsService();
    }

    public static ParceledListSlice getCompatChangeablePackageInfoList(String str, int i) {
        String str2;
        Context context;
        PackageManager packageManager;
        CompatChangeableAppsService compatChangeableAppsService = LazyHolder.sService;
        if (compatChangeableAppsService.mPackageManager == null) {
            SafetySystemService.Manager manager = SafetySystemService.LazyHolder.sSingleton;
            synchronized (manager) {
                context = manager.mSystemContext;
            }
            if (context == null) {
                Slog.w(
                        "SafetySystemService",
                        "PackageManager".concat(" should be called after system ready."));
                packageManager = null;
            } else {
                packageManager = context.getPackageManager();
            }
            compatChangeableAppsService.mPackageManager = packageManager;
        }
        PackageManager packageManager2 = compatChangeableAppsService.mPackageManager;
        if (packageManager2 == null) {
            Slog.w("CompatChangeableApps", "PackageManager is null.");
            return ParceledListSlice.emptyList();
        }
        List queryIntentActivitiesAsUser =
                packageManager2.queryIntentActivitiesAsUser(
                        new Intent("android.intent.action.MAIN")
                                .addCategory("android.intent.category.LAUNCHER")
                                .setPackage(str),
                        786432,
                        i);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        boolean z = false;
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            if (activityInfo != null
                    && (str2 = activityInfo.packageName) != null
                    && !arrayList2.contains(str2)) {
                arrayList2.add(str2);
                if (!z && (str == null || str.equals(str2))) {
                    z = true;
                }
                arrayList.add(
                        new BuilderWrapper(activityInfo.applicationInfo, packageManager2, str2)
                                .build());
            }
        }
        if (!z && str != null) {
            arrayList.add(new CompatChangeablePackageInfo.Builder().setPackageName(str).build());
        }
        return new ParceledListSlice(arrayList);
    }
}
