package com.android.server.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ComputerLocked extends ComputerEngine {
    @Override // com.android.server.pm.ComputerEngine
    public final ApplicationInfo androidApplication() {
        return this.mService.mAndroidApplication;
    }

    @Override // com.android.server.pm.ComputerEngine
    public final ActivityInfo instantAppInstallerActivity() {
        return this.mService.mInstantAppInstallerActivity;
    }

    @Override // com.android.server.pm.ComputerEngine
    public final ComponentName resolveComponentName() {
        return this.mService.mResolveComponentName;
    }
}
