package com.android.server.usage;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.usage.AppStandbyController;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppStandbyController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppStandbyController f$0;

    public /* synthetic */ AppStandbyController$$ExternalSyntheticLambda0(AppStandbyController appStandbyController, int i) {
        this.$r8$classId = i;
        this.f$0 = appStandbyController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AppStandbyController appStandbyController = this.f$0;
        switch (i) {
            case 0:
                AppStandbyController.Injector injector = appStandbyController.mInjector;
                if (injector.mBootPhase < 500) {
                    return;
                }
                try {
                    String[] fullPowerWhitelistExceptIdle = injector.mDeviceIdleController.getFullPowerWhitelistExceptIdle();
                    synchronized (injector.mPowerWhitelistedApps) {
                        try {
                            injector.mPowerWhitelistedApps.clear();
                            for (String str : fullPowerWhitelistExceptIdle) {
                                injector.mPowerWhitelistedApps.add(str);
                            }
                        } finally {
                        }
                    }
                } catch (RemoteException e) {
                    Slog.wtf("AppStandbyController", "Failed to get power whitelist", e);
                }
                appStandbyController.postOneTimeCheckIdleStates();
                return;
            default:
                appStandbyController.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                List installedPackagesAsUser = appStandbyController.mPackageManager.getInstalledPackagesAsUser(1835520, 0);
                List queryIntentActivitiesAsUser = appStandbyController.mPackageManager.queryIntentActivitiesAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), 1835520, 0);
                ArraySet arraySet = new ArraySet();
                Iterator it = queryIntentActivitiesAsUser.iterator();
                while (it.hasNext()) {
                    arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
                }
                int size = installedPackagesAsUser.size();
                for (int i2 = 0; i2 < size; i2++) {
                    PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i2);
                    if (packageInfo != null) {
                        String str2 = packageInfo.packageName;
                        if (appStandbyController.updateHeadlessSystemAppCache(str2, !arraySet.contains(str2))) {
                            Flags.avoidIdleCheck();
                            appStandbyController.mHandler.obtainMessage(11, 0, -1, str2).sendToTarget();
                        }
                    }
                }
                long uptimeMillis2 = SystemClock.uptimeMillis();
                StringBuilder sb = new StringBuilder("Loaded headless system app cache in ");
                sb.append(uptimeMillis2 - uptimeMillis);
                sb.append(" ms: appIdleEnabled=");
                AnyMotionDetector$$ExternalSyntheticOutline0.m("AppStandbyController", sb, appStandbyController.mAppIdleEnabled);
                return;
        }
    }
}
