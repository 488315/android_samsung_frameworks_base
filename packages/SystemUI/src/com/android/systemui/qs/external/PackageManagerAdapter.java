package com.android.systemui.qs.external;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;

public final class PackageManagerAdapter {
    public final IPackageManager mIPackageManager = AppGlobals.getPackageManager();
    public final PackageManager mPackageManager;

    public PackageManagerAdapter(Context context) {
        this.mPackageManager = context.getPackageManager();
    }
}
