package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.os.RemoteException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PackageManagerWrapper {
    public static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
    public static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    private PackageManagerWrapper() {
    }

    public static ActivityInfo getActivityInfo(ComponentName componentName, int i) {
        try {
            return mIPackageManager.getActivityInfo(componentName, 128L, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
