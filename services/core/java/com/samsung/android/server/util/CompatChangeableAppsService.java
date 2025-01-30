package com.samsung.android.server.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.util.Slog;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.core.CompatChangeableApps;
import com.samsung.android.core.CompatChangeablePackageInfo;
import com.samsung.android.server.util.SafetySystemService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class CompatChangeableAppsService {
    public final Object mLock;
    public Context mSystemContext;

    public abstract class LazyHolder {
        public static final CompatChangeableAppsService sService = new CompatChangeableAppsService();
    }

    public static ParceledListSlice getCompatChangeablePackageInfoList(String str, int i) {
        return LazyHolder.sService.getList(str, i);
    }

    public CompatChangeableAppsService() {
        this.mLock = new Object();
        SafetySystemService.registerForSystemReady(new SafetySystemService.Callback() { // from class: com.samsung.android.server.util.CompatChangeableAppsService$$ExternalSyntheticLambda0
            @Override // com.samsung.android.server.util.SafetySystemService.Callback
            public final void onSystemReady(ActivityTaskManagerService activityTaskManagerService) {
                CompatChangeableAppsService.this.lambda$new$0(activityTaskManagerService);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ActivityTaskManagerService activityTaskManagerService) {
        synchronized (this.mLock) {
            this.mSystemContext = SafetySystemService.getSystemContext();
        }
    }

    public final ParceledListSlice getList(String str, int i) {
        PackageManager packageManager;
        String str2;
        synchronized (this.mLock) {
            Context context = this.mSystemContext;
            packageManager = context != null ? context.getPackageManager() : null;
        }
        if (packageManager == null) {
            Slog.w("CompatChangeableApps", "PackageManager is null.");
            return ParceledListSlice.emptyList();
        }
        List queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(str), 786432, i);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        boolean z = false;
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            if (activityInfo != null && (str2 = activityInfo.packageName) != null && !arrayList2.contains(str2)) {
                arrayList2.add(str2);
                if (!z && (str == null || str.equals(str2))) {
                    z = true;
                }
                CompatChangeablePackageInfo.Builder builder = new CompatChangeablePackageInfo.Builder();
                builder.setPackageName(str2);
                builder.setHasLauncherActivity(true);
                ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                if (applicationInfo != null) {
                    builder.setUid(applicationInfo.uid);
                    builder.setHasGameCategory(activityInfo.applicationInfo.category == 0);
                }
                Boolean bool = Boolean.FALSE;
                builder.setIsOrientationOverrideDisallowed(bool.equals(CompatChangeableApps.readComponentProperty(packageManager, str2, "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE")));
                builder.setIsMinAspectRatioOverrideDisallowed(bool.equals(CompatChangeableApps.readComponentProperty(packageManager, str2, "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE")));
                builder.setIsActivityEmbeddingSplitsEnabled(Boolean.TRUE.equals(CompatChangeableApps.readComponentProperty(packageManager, str2, "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED")));
                arrayList.add(builder.build());
            }
        }
        if (!z && str != null) {
            arrayList.add(new CompatChangeablePackageInfo.Builder().setPackageName(str).build());
        }
        return new ParceledListSlice(arrayList);
    }
}
