package com.samsung.android.server.pm.install;

import android.util.ArrayMap;
import android.util.Log;

import com.android.server.pm.pkg.AndroidPackage;

import java.io.File;
import java.util.Map;

public final class PreloadDuplicateApps {
    public final Object mLock = new Object();
    public final Map mDuplicateDataPackages = new ArrayMap();
    public final Map mSystemPackages = new ArrayMap();

    public final void addDuplicatePackage(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return;
        }
        Log.d(
                "PreloadDuplicateApps",
                "Add duplicate package "
                        + androidPackage.getPackageName()
                        + " ("
                        + androidPackage.getLongVersionCode()
                        + ")");
        synchronized (this.mLock) {
            ((ArrayMap) this.mDuplicateDataPackages)
                    .put(androidPackage.getPackageName(), new File(androidPackage.getPath()));
        }
    }

    public final void addSystemPackage(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return;
        }
        Log.d(
                "PreloadDuplicateApps",
                "Add system package "
                        + androidPackage.getPackageName()
                        + " ("
                        + androidPackage.getLongVersionCode()
                        + ")");
        synchronized (this.mLock) {
            ((ArrayMap) this.mSystemPackages)
                    .put(androidPackage.getPackageName(), new File(androidPackage.getPath()));
        }
    }
}
