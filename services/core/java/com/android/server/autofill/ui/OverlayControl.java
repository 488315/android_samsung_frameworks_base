package com.android.server.autofill.ui;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
