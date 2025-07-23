package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.UserHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PackageManagerWrapper {
    public static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
    public static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    private PackageManagerWrapper() {
    }

    public static ActivityInfo getActivityInfo(int i, ComponentName componentName) {
        try {
            return mIPackageManager.getActivityInfo(componentName, 128L, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResolveInfo resolveActivity(Intent intent) {
        try {
            return mIPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(AppGlobals.getInitialApplication().getContentResolver()), 0, UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
