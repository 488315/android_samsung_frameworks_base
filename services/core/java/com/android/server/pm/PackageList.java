package com.android.server.pm;

import android.content.pm.PackageManagerInternal;
import android.util.ArraySet;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageList implements PackageManagerInternal.PackageListObserver, AutoCloseable {
    public final List mPackageNames;
    public final PackageManagerInternal.PackageListObserver mWrappedObserver;

    public PackageList(List list, PackageManagerInternal.PackageListObserver packageListObserver) {
        this.mPackageNames = list;
        this.mWrappedObserver = packageListObserver;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        PackageObserverHelper packageObserverHelper = PackageManagerService.this.mPackageObserverHelper;
        synchronized (packageObserverHelper.mLock) {
            ArraySet arraySet = new ArraySet(packageObserverHelper.mActiveSnapshot);
            arraySet.remove(this);
            packageObserverHelper.mActiveSnapshot = arraySet;
        }
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public final void onPackageAdded(String str, int i) {
        PackageManagerInternal.PackageListObserver packageListObserver = this.mWrappedObserver;
        if (packageListObserver != null) {
            packageListObserver.onPackageAdded(str, i);
        }
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public final void onPackageChanged(int i, String str) {
        PackageManagerInternal.PackageListObserver packageListObserver = this.mWrappedObserver;
        if (packageListObserver != null) {
            packageListObserver.onPackageChanged(i, str);
        }
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public final void onPackageRemoved(String str, int i) {
        PackageManagerInternal.PackageListObserver packageListObserver = this.mWrappedObserver;
        if (packageListObserver != null) {
            packageListObserver.onPackageRemoved(str, i);
        }
    }
}
