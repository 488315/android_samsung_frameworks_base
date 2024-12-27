package com.android.server.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;

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
