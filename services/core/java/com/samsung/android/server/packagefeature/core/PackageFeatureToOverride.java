package com.samsung.android.server.packagefeature.core;

import android.os.ServiceManager;

import com.android.server.compat.PlatformCompat;

public class PackageFeatureToOverride {
    public PlatformCompat mPlatformCompat;

    public abstract class LazyHolder {
        public static final PackageFeatureToOverride sInstance = new PackageFeatureToOverride(0);
    }

    private PackageFeatureToOverride() {}

    public /* synthetic */ PackageFeatureToOverride(int i) {
        this();
    }

    public static PackageFeatureToOverride getInstance() {
        return LazyHolder.sInstance;
    }

    public PlatformCompat getPlatformCompat() {
        if (this.mPlatformCompat == null) {
            this.mPlatformCompat = (PlatformCompat) ServiceManager.getService("platform_compat");
        }
        return this.mPlatformCompat;
    }
}
