package com.android.server.pm;

import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.pm.pkg.PackageStateInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppsFilterImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ AppsFilterImpl f$0;
    public final /* synthetic */ PackageManagerInternal f$1;
    public final /* synthetic */ int f$2 = 1;
    public final /* synthetic */ long f$3;

    public /* synthetic */ AppsFilterImpl$$ExternalSyntheticLambda1(
            AppsFilterImpl appsFilterImpl, PackageManagerInternal packageManagerInternal, long j) {
        this.f$0 = appsFilterImpl;
        this.f$1 = packageManagerInternal;
        this.f$3 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AppsFilterImpl appsFilterImpl = this.f$0;
        PackageManagerInternal packageManagerInternal = this.f$1;
        int i = this.f$2;
        long j = this.f$3;
        char c = 0;
        if (appsFilterImpl.mCacheValid.compareAndSet(false, true)) {
            long currentTimeMicro = SystemClock.currentTimeMicro();
            ArrayMap arrayMap = new ArrayMap();
            Computer snapshotComputer =
                    ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal)
                            .mService.snapshotComputer();
            ArrayMap packageStates = snapshotComputer.getPackageStates();
            UserInfo[] userInfos = snapshotComputer.getUserInfos();
            arrayMap.ensureCapacity(packageStates.size());
            UserInfo[][] userInfoArr = {userInfos};
            int size = packageStates.size();
            int i2 = 0;
            while (i2 < size) {
                arrayMap.put(
                        (String) packageStates.keyAt(i2),
                        ((PackageStateInternal) packageStates.valueAt(i2)).getPkg());
                i2++;
                size = size;
                c = 0;
            }
            appsFilterImpl.updateEntireShouldFilterCacheInner(
                    snapshotComputer, packageStates, userInfoArr[c], -1);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_BUILD_REPORTED,
                    i,
                    SystemClock.currentTimeMicro() - currentTimeMicro,
                    userInfos.length,
                    packageStates.size(),
                    appsFilterImpl.mShouldFilterCache.mSize);
            if (!appsFilterImpl.mCacheValid.compareAndSet(true, true)) {
                Slog.i("AppsFilter", "Cache invalidated while building, retrying.");
                long min = Math.min(j * 2, 10000L);
                appsFilterImpl.mHandler.postDelayed(
                        new AppsFilterImpl$$ExternalSyntheticLambda1(
                                appsFilterImpl, packageManagerInternal, min),
                        min);
                return;
            }
            PackageManagerTracedLock packageManagerTracedLock =
                    appsFilterImpl.mImplicitlyQueryableLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    if (appsFilterImpl.mNeedToUpdateCacheForImplicitAccess) {
                        appsFilterImpl.updateShouldFilterCacheForImplicitAccess(
                                appsFilterImpl.mRetainedImplicitlyQueryable);
                        appsFilterImpl.updateShouldFilterCacheForImplicitAccess(
                                appsFilterImpl.mImplicitlyQueryable);
                        appsFilterImpl.mNeedToUpdateCacheForImplicitAccess = false;
                    }
                    appsFilterImpl.mCacheReady = true;
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            appsFilterImpl.dispatchChange(appsFilterImpl);
        }
    }
}
