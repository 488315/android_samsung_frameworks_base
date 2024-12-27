package com.android.server.autofill.ui;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;

public final class OverlayControl {
    public final AppOpsManager mAppOpsManager;
    public final IBinder mToken = new Binder();

    public OverlayControl(Context context) {
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    }

    public final void setOverlayAllowed(boolean z) {
        AppOpsManager appOpsManager = this.mAppOpsManager;
        if (appOpsManager != null) {
            boolean z2 = !z;
            appOpsManager.setUserRestrictionForUser(24, z2, this.mToken, null, -1);
            this.mAppOpsManager.setUserRestrictionForUser(45, z2, this.mToken, null, -1);
        }
    }
}
