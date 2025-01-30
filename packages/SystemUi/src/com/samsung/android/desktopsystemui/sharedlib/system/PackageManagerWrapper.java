package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PackageManagerWrapper {
    public static final String ACTION_PREFERRED_ACTIVITY_CHANGED = "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED";
    private static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
    private static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    private PackageManagerWrapper() {
    }

    public static PackageManagerWrapper getInstance() {
        return sInstance;
    }

    public void deletePackageAsUser(PackageManager packageManager, String str, int i) {
        packageManager.deletePackageAsUser(str, null, 0, i);
    }

    public ActivityInfo getActivityInfo(ComponentName componentName, int i) {
        try {
            return mIPackageManager.getActivityInfo(componentName, 128L, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBadgedActivityLabel(PackageManager packageManager, ActivityInfo activityInfo, int i) {
        String charSequence = activityInfo.loadLabel(packageManager).toString();
        return i != UserHandle.myUserId() ? packageManager.getUserBadgedLabel(charSequence, new UserHandle(i)).toString() : charSequence;
    }

    public ComponentName getHomeActivities(List<ResolveInfo> list) {
        try {
            return mIPackageManager.getHomeActivities(list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getInstallTimeOfPackage(String str, int i) {
        try {
            return mIPackageManager.getPackageInfo(str, 0L, i).firstInstallTime;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public Drawable getUserBadgeForDensityNoBackground(PackageManager packageManager, UserHandle userHandle, int i) {
        return packageManager.getUserBadgeForDensityNoBackground(userHandle, i);
    }

    public ResolveInfo resolveActivity(Intent intent, int i) {
        try {
            return mIPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(AppGlobals.getInitialApplication().getContentResolver()), i, UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) {
        try {
            mIPackageManager.setApplicationEnabledSetting(str, i, i2, i3, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
