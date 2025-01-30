package com.android.settingslib.applications;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import com.android.settingslib.widget.CandidateInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DefaultAppInfo extends CandidateInfo {
    public final ComponentName componentName;
    public final PackageManager mPm;
    public final PackageItemInfo packageItemInfo;
    public final int userId;

    public DefaultAppInfo(Context context, PackageManager packageManager, int i, ComponentName componentName) {
        this(context, packageManager, i, componentName, (String) null, true);
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
