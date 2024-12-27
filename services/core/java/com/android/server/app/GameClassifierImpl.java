package com.android.server.app;

import android.content.pm.PackageManager;

public final class GameClassifierImpl {
    public final PackageManager mPackageManager;

    public GameClassifierImpl(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }
}
