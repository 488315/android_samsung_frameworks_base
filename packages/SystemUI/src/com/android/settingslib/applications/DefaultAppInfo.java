package com.android.settingslib.applications;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.android.settingslib.widget.CandidateInfo;

/* loaded from: classes.dex */
public class DefaultAppInfo extends CandidateInfo {
    public final ComponentName componentName;
    public final PackageManager mPm;
    public final PackageItemInfo packageItemInfo;
    public final int userId;

    public DefaultAppInfo(Context context, PackageManager packageManager, int i, ComponentName componentName) {
        this(context, packageManager, i, componentName, (String) null, true);
    }

    public CharSequence loadLabel() {
        ComponentInfo activityInfo;
        int i = this.userId;
        if (this.componentName == null) {
            PackageItemInfo packageItemInfo = this.packageItemInfo;
            if (packageItemInfo != null) {
                return packageItemInfo.loadLabel(this.mPm);
            }
            return null;
        }
        try {
            try {
                activityInfo = AppGlobals.getPackageManager().getActivityInfo(this.componentName, 0L, i);
                if (activityInfo == null) {
                    activityInfo = AppGlobals.getPackageManager().getServiceInfo(this.componentName, 0L, i);
                }
            } catch (RemoteException unused) {
                activityInfo = null;
            }
            return activityInfo != null ? activityInfo.loadLabel(this.mPm) : this.mPm.getApplicationInfoAsUser(this.componentName.getPackageName(), 0, i).loadLabel(this.mPm);
        } catch (PackageManager.NameNotFoundException unused2) {
            return null;
        }
    }

    public DefaultAppInfo(Context context, PackageManager packageManager, int i, PackageItemInfo packageItemInfo) {
        this(context, packageManager, i, packageItemInfo, (String) null, true);
    }

    public DefaultAppInfo(Context context, PackageManager packageManager, int i, ComponentName componentName, String str, boolean z) {
        super(z);
        this.mPm = packageManager;
        this.packageItemInfo = null;
        this.userId = i;
        this.componentName = componentName;
    }

    public DefaultAppInfo(Context context, PackageManager packageManager, int i, PackageItemInfo packageItemInfo, String str, boolean z) {
        super(z);
        this.mPm = packageManager;
        this.userId = i;
        this.packageItemInfo = packageItemInfo;
        this.componentName = null;
    }
}
