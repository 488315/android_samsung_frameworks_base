package com.android.server.app;

import android.content.pm.PackageManager;
import android.content.res.Resources;

public final class GameServiceProviderSelectorImpl {
    public final PackageManager mPackageManager;
    public final Resources mResources;

    public GameServiceProviderSelectorImpl(Resources resources, PackageManager packageManager) {
        this.mResources = resources;
        this.mPackageManager = packageManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0087, code lost:

       if (r6 != null) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.app.GameServiceConfiguration get(
            com.android.server.SystemService.TargetUser r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.app.GameServiceProviderSelectorImpl.get(com.android.server.SystemService$TargetUser,"
                    + " java.lang.String):com.android.server.app.GameServiceConfiguration");
    }
}
