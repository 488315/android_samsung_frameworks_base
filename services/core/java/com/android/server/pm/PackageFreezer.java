package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IActivityManager;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.server.LocalServices;
import dalvik.system.CloseGuard;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFreezer implements AutoCloseable {
    public final CloseGuard mCloseGuard;
    public final AtomicBoolean mClosed;
    public InstallRequest mInstallRequest;
    public final String mPackageName;
    public final PackageManagerService mPm;

    public PackageFreezer(PackageManagerService packageManagerService, InstallRequest installRequest) {
        PackageMetrics packageMetrics;
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        this.mClosed = atomicBoolean;
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPm = packageManagerService;
        this.mPackageName = null;
        atomicBoolean.set(true);
        closeGuard.open("close");
        this.mInstallRequest = installRequest;
        if (installRequest == null || (packageMetrics = installRequest.mPackageMetrics) == null || !Flags.improveInstallFreeze()) {
            return;
        }
        packageMetrics.onStepStarted(6);
    }

    public PackageFreezer(String str, int i, String str2, PackageManagerService packageManagerService, int i2, InstallRequest installRequest, boolean z) {
        PackageSetting packageLPr;
        IActivityManager service;
        boolean z2;
        IActivityManager service2;
        PackageMetrics packageMetrics;
        this.mClosed = new AtomicBoolean();
        this.mCloseGuard = CloseGuard.get();
        this.mPm = packageManagerService;
        this.mPackageName = str;
        this.mInstallRequest = installRequest;
        if (installRequest != null && (packageMetrics = installRequest.mPackageMetrics) != null && Flags.improveInstallFreeze()) {
            packageMetrics.onStepStarted(6);
        }
        PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                packageManagerService.mFrozenPackages.put(str, Integer.valueOf(((Integer) packageManagerService.mFrozenPackages.getOrDefault(str, 0)).intValue() + 1));
                packageLPr = packageManagerService.mSettings.getPackageLPr(str);
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (packageLPr != null) {
            if (z && Flags.waitApplicationKilled()) {
                String str3 = packageLPr.mName;
                int i3 = packageLPr.mAppId;
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                if (Thread.holdsLock(packageManagerService.mLock) || activityManagerInternal == null) {
                    Slog.e("PackageManager", "Holds PM's lock, unable kill application synchronized");
                    PackageManagerService.killApplication(str3, i3, i, str2, i2);
                } else {
                    KillAppBlocker killAppBlocker = new KillAppBlocker();
                    try {
                        if (!killAppBlocker.mRegistered && (service2 = ActivityManager.getService()) != null) {
                            try {
                                service2.registerUidObserver(killAppBlocker.mUidObserver, 2, -1, "pm");
                                killAppBlocker.mRegistered = true;
                            } catch (RemoteException unused) {
                            }
                        }
                        activityManagerInternal.killApplicationSync(str3, i3, i, str2, i2);
                        killAppBlocker.waitAppProcessGone(activityManagerInternal, packageManagerService.snapshotComputer(), packageManagerService.mUserManager, str3);
                        if (z2 && service != null) {
                            try {
                                killAppBlocker.mRegistered = false;
                                service.unregisterUidObserver(killAppBlocker.mUidObserver);
                            } catch (RemoteException unused2) {
                            }
                        }
                    } finally {
                        if (killAppBlocker.mRegistered && (service = ActivityManager.getService()) != null) {
                            try {
                                killAppBlocker.mRegistered = false;
                                service.unregisterUidObserver(killAppBlocker.mUidObserver);
                            } catch (RemoteException unused3) {
                            }
                        }
                    }
                }
            } else {
                PackageManagerService.killApplication(packageLPr.mName, packageLPr.mAppId, i, str2, i2);
            }
        }
        this.mCloseGuard.open("close");
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    int intValue = ((Integer) this.mPm.mFrozenPackages.getOrDefault(this.mPackageName, 0)).intValue() - 1;
                    if (intValue > 0) {
                        this.mPm.mFrozenPackages.put(this.mPackageName, Integer.valueOf(intValue));
                    } else {
                        this.mPm.mFrozenPackages.remove(this.mPackageName);
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
        InstallRequest installRequest = this.mInstallRequest;
        if (installRequest != null) {
            PackageMetrics packageMetrics = installRequest.mPackageMetrics;
            if (packageMetrics != null && Flags.improveInstallFreeze()) {
                packageMetrics.onStepFinished(6);
            }
            this.mInstallRequest = null;
        }
    }

    public final void finalize() {
        try {
            this.mCloseGuard.warnIfOpen();
            close();
        } finally {
            super.finalize();
        }
    }
}
