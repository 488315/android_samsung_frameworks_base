package com.android.server.pm;

import dalvik.system.CloseGuard;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public final class PackageFreezer implements AutoCloseable {
    public final CloseGuard mCloseGuard;
    public final AtomicBoolean mClosed;
    public final String mPackageName;
    public final PackageManagerService mPm;

    public PackageFreezer(PackageManagerService packageManagerService) {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        this.mClosed = atomicBoolean;
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPm = packageManagerService;
        this.mPackageName = null;
        atomicBoolean.set(true);
        closeGuard.open("close");
    }

    public PackageFreezer(String str, int i, String str2, PackageManagerService packageManagerService, int i2) {
        PackageSetting packageLPr;
        this.mClosed = new AtomicBoolean();
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPm = packageManagerService;
        this.mPackageName = str;
        synchronized (packageManagerService.mLock) {
            packageManagerService.mFrozenPackages.put(str, Integer.valueOf(((Integer) packageManagerService.mFrozenPackages.getOrDefault(str, 0)).intValue() + 1));
            packageLPr = packageManagerService.mSettings.getPackageLPr(str);
        }
        if (packageLPr != null) {
            packageManagerService.killApplication(packageLPr.getPackageName(), packageLPr.getAppId(), i, str2, i2);
        }
        closeGuard.open("close");
    }

    public void finalize() {
        try {
            this.mCloseGuard.warnIfOpen();
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            synchronized (this.mPm.mLock) {
                int intValue = ((Integer) this.mPm.mFrozenPackages.getOrDefault(this.mPackageName, 0)).intValue() - 1;
                if (intValue > 0) {
                    this.mPm.mFrozenPackages.put(this.mPackageName, Integer.valueOf(intValue));
                } else {
                    this.mPm.mFrozenPackages.remove(this.mPackageName);
                }
            }
        }
    }
}
