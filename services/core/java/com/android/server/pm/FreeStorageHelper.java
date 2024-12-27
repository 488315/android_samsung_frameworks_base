package com.android.server.pm;

import android.content.Context;
import android.os.SystemProperties;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FreeStorageHelper {
    public static final long FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD =
            TimeUnit.HOURS.toMillis(2);
    public final Context mContext;
    public final boolean mEnableFreeCacheV2;
    public final PackageManagerServiceInjector mInjector;
    public final PackageManagerService mPm;

    public FreeStorageHelper(PackageManagerService packageManagerService) {
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        Context context = packageManagerService.mContext;
        boolean z = SystemProperties.getBoolean("fw.free_cache_v2", true);
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mContext = context;
        this.mEnableFreeCacheV2 = z;
    }
}
