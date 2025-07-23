package com.android.systemui.qs.external;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PackageManagerAdapter {
    public final IPackageManager mIPackageManager = AppGlobals.getPackageManager();
    public final PackageManager mPackageManager;

    public PackageManagerAdapter(Context context) {
        this.mPackageManager = context.getPackageManager();
    }
}
