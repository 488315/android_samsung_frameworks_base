package com.android.server.pm;

import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ LauncherAppsService.LauncherAppsImpl f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ UserHandle f$3;

    public /* synthetic */ LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda2(
            LauncherAppsService.LauncherAppsImpl launcherAppsImpl,
            String str,
            int i,
            UserHandle userHandle) {
        this.f$0 = launcherAppsImpl;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = userHandle;
    }

    public final Object getOrThrow() {
        LauncherAppsService.LauncherAppsImpl launcherAppsImpl = this.f$0;
        String str = this.f$1;
        return launcherAppsImpl.mPackageManagerInternal.getApplicationInfo(
                this.f$2, this.f$3.getIdentifier(), 4294967296L, str);
    }
}
