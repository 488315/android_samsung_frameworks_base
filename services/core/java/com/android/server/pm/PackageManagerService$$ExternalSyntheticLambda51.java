package com.android.server.pm;

import android.content.pm.IPackageDataObserver;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;
import android.util.secutil.Slog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda51
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda51(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PackageManagerService packageManagerService = (PackageManagerService) this.f$0;
                packageManagerService.mDeletePackageHelper.deletePackageX(
                        0, 2, -1L, (String) this.f$1, true);
                return;
            case 1:
                PackageManagerService packageManagerService2 = (PackageManagerService) this.f$0;
                InstallRequest installRequest = (InstallRequest) this.f$1;
                final PackageArchiver packageArchiver =
                        packageManagerService2.mInstallerService.mPackageArchiver;
                String str = installRequest.mName;
                int i = Build.VERSION.SDK_INT;
                packageArchiver.getClass();
                if (str == null || str.isEmpty()) {
                    Slog.d("PackageArchiverService", "packageName is null or empty!");
                    return;
                }
                synchronized (packageArchiver.mArchiveVersionMap) {
                    packageArchiver.mArchiveVersionMap.put(str, Integer.valueOf(i));
                }
                PackageManagerService packageManagerService3 = packageArchiver.mPm;
                packageManagerService3.mHandler.removeCallbacks(
                        new Runnable() { // from class:
                                         // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileOutputStream fileOutputStream;
                                PackageArchiver packageArchiver2 = PackageArchiver.this;
                                synchronized (packageArchiver2.mArchiveVersionMap) {
                                    try {
                                        fileOutputStream =
                                                new FileOutputStream(
                                                        new File(PackageArchiver.FILE_PATH));
                                    } catch (Exception unused) {
                                        Slog.d("PackageArchiverService", "versionMap write error!");
                                    }
                                    try {
                                        ObjectOutputStream objectOutputStream =
                                                new ObjectOutputStream(fileOutputStream);
                                        try {
                                            objectOutputStream.writeObject(
                                                    packageArchiver2.mArchiveVersionMap);
                                            objectOutputStream.close();
                                            fileOutputStream.close();
                                            if (Build.isDebuggable()) {
                                                Slog.d(
                                                        "PackageArchiverService",
                                                        "write writeAppVersion");
                                                packageArchiver2.mArchiveVersionMap.forEach(
                                                        new PackageArchiver$$ExternalSyntheticLambda7(
                                                                1));
                                            }
                                        } finally {
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                }
                            }
                        });
                packageManagerService3.mHandler.postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileOutputStream fileOutputStream;
                                PackageArchiver packageArchiver2 = PackageArchiver.this;
                                synchronized (packageArchiver2.mArchiveVersionMap) {
                                    try {
                                        fileOutputStream =
                                                new FileOutputStream(
                                                        new File(PackageArchiver.FILE_PATH));
                                    } catch (Exception unused) {
                                        Slog.d("PackageArchiverService", "versionMap write error!");
                                    }
                                    try {
                                        ObjectOutputStream objectOutputStream =
                                                new ObjectOutputStream(fileOutputStream);
                                        try {
                                            objectOutputStream.writeObject(
                                                    packageArchiver2.mArchiveVersionMap);
                                            objectOutputStream.close();
                                            fileOutputStream.close();
                                            if (Build.isDebuggable()) {
                                                Slog.d(
                                                        "PackageArchiverService",
                                                        "write writeAppVersion");
                                                packageArchiver2.mArchiveVersionMap.forEach(
                                                        new PackageArchiver$$ExternalSyntheticLambda7(
                                                                1));
                                            }
                                        } finally {
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                }
                            }
                        },
                        5000L);
                return;
            case 2:
                try {
                    ((IPackageDataObserver) this.f$0).onRemoveCompleted((String) this.f$1, false);
                    return;
                } catch (RemoteException unused) {
                    Log.i("PackageManager", "Observer no longer exists.");
                    return;
                }
            default:
                PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                        (PackageManagerService.PackageManagerInternalImpl) this.f$0;
                PackageManagerService.this.notifyInstallObserver((String) this.f$1, true);
                return;
        }
    }
}
