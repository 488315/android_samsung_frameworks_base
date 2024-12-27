package com.android.systemui.screenshot.message;

import android.content.pm.PackageManager;

public final class PackageLabelIconProviderImpl implements PackageLabelIconProvider {
    public final PackageManager packageManager;

    public PackageLabelIconProviderImpl(PackageManager packageManager) {
        this.packageManager = packageManager;
    }
}
