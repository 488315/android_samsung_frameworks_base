package com.samsung.android.server.packagefeature.core;

import android.os.ServiceManager;
import com.android.server.compat.PlatformCompat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PackageFeatureToOverride {
    public PlatformCompat mPlatformCompat;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final PackageFeatureToOverride sInstance = new PackageFeatureToOverride(0);
    }

    private PackageFeatureToOverride() {
    }

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
