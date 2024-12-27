package com.android.server.compat;

import com.android.internal.compat.IPlatformCompatNative;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PlatformCompatNative extends IPlatformCompatNative.Stub {
    public final PlatformCompat mPlatformCompat;

    public PlatformCompatNative(PlatformCompat platformCompat) {
        this.mPlatformCompat = platformCompat;
    }

    public final boolean isChangeEnabledByPackageName(long j, String str, int i) {
        return this.mPlatformCompat.isChangeEnabledByPackageName(j, str, i);
    }

    public final boolean isChangeEnabledByUid(long j, int i) {
        return this.mPlatformCompat.isChangeEnabledByUid(j, i);
    }

    public final void reportChangeByPackageName(long j, String str, int i) {
        this.mPlatformCompat.reportChangeByPackageName(j, str, i);
    }

    public final void reportChangeByUid(long j, int i) {
        this.mPlatformCompat.reportChangeByUid(j, i);
    }
}
