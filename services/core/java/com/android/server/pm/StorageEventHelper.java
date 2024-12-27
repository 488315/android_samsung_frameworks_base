package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.os.Environment;
import android.os.FileUtils;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.policy.AttributeCache;
import com.android.server.pm.pkg.PackageStateInternal;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class StorageEventHelper extends StorageEventListener {
    public final BroadcastHelper mBroadcastHelper;
    public final DeletePackageHelper mDeletePackageHelper;
    public final ArraySet mLoadedVolumes = new ArraySet();
    public final PackageManagerService mPm;
    public final RemovePackageHelper mRemovePackageHelper;

    public StorageEventHelper(
            PackageManagerService packageManagerService,
            DeletePackageHelper deletePackageHelper,
            RemovePackageHelper removePackageHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = new BroadcastHelper(packageManagerService.mInjector);
        this.mDeletePackageHelper = deletePackageHelper;
        this.mRemovePackageHelper = removePackageHelper;
    }

    public final void onVolumeForgotten(String str) {
        if (TextUtils.isEmpty(str)) {
            Slog.e("PackageManager", "Forgetting internal storage is probably a mistake; ignoring");
            return;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Iterator it = ((ArrayList) this.mPm.mSettings.getVolumePackagesLPr(str)).iterator();
                while (it.hasNext()) {
                    PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
                    Slog.d(
                            "PackageManager",
                            "Destroying "
                                    + packageStateInternal.getPackageName()
                                    + " because volume was forgotten");
                    this.mPm.mDeletePackageHelper.deletePackageVersionedInternal(
                            new VersionedPackage(packageStateInternal.getPackageName(), -1),
                            new PackageManager.LegacyPackageDeleteObserver(
                                            (IPackageDeleteObserver) null)
                                    .getBinder(),
                            0,
                            2,
                            false);
                    AttributeCache.instance().removePackage(packageStateInternal.getPackageName());
                }
                this.mPm.mSettings.mVersion.remove(str);
                this.mPm.writeSettingsLPrTEMP(false);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void onVolumeStateChanged(final VolumeInfo volumeInfo, int i, int i2) {
        DiskInfo diskInfo;
        if (volumeInfo.type == 1) {
            int i3 = volumeInfo.state;
            if (i3 == 2) {
                String fsUuid = volumeInfo.getFsUuid();
                this.mPm.mUserManager.reconcileUsers(fsUuid);
                reconcileApps(this.mPm.snapshotComputer(), fsUuid);
                PackageInstallerService packageInstallerService = this.mPm.mInstallerService;
                synchronized (packageInstallerService.mSessions) {
                    packageInstallerService.reconcileStagesLocked(fsUuid);
                }
                final int i4 = 0;
                this.mPm.mHandler.post(
                        new Runnable(this) { // from class:
                            // com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0
                            public final /* synthetic */ StorageEventHelper f$0;

                            {
                                this.f$0 = this;
                            }

                            /* JADX WARN: Can't wrap try/catch for region: R(8:(2:78|(2:80|81)(2:82|58))(2:44|45)|51|52|54|55|56|57|58) */
                            /* JADX WARN: Can't wrap try/catch for region: R(8:40|(8:(2:78|(2:80|81)(2:82|58))(2:44|45)|51|52|54|55|56|57|58)|46|47|48|49|50|38) */
                            /* JADX WARN: Code restructure failed: missing block: B:59:0x0163, code lost:

                               r0 = e;
                            */
                            /* JADX WARN: Code restructure failed: missing block: B:60:0x017f, code lost:

                               android.util.Slog.w("PackageManager", "Failed to prepare storage: " + r0);
                            */
                            /* JADX WARN: Code restructure failed: missing block: B:73:0x0168, code lost:

                               r0 = th;
                            */
                            /* JADX WARN: Code restructure failed: missing block: B:74:0x0169, code lost:

                               r19 = r4;
                               r20 = r7;
                               r18 = r8;
                            */
                            /* JADX WARN: Code restructure failed: missing block: B:76:0x017a, code lost:

                               r0 = e;
                            */
                            /* JADX WARN: Code restructure failed: missing block: B:77:0x017b, code lost:

                               r20 = r7;
                               r18 = r8;
                            */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            private final void
                                    run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0() {
                                /*
                                    Method dump skipped, instructions count: 554
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0.run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0():void");
                            }

                            /* JADX WARN: Finally extract failed */
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i5;
                                int i6 = 1;
                                switch (i4) {
                                    case 0:
                                        run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0();
                                        return;
                                    default:
                                        StorageEventHelper storageEventHelper = this.f$0;
                                        VolumeInfo volumeInfo2 = volumeInfo;
                                        storageEventHelper.getClass();
                                        String str = volumeInfo2.fsUuid;
                                        if (TextUtils.isEmpty(str)) {
                                            Slog.e(
                                                    "PackageManager",
                                                    "Unloading internal storage is probably a"
                                                        + " mistake; ignoring");
                                            return;
                                        }
                                        int[] userIds =
                                                storageEventHelper.mPm.mUserManager.getUserIds();
                                        ArrayList arrayList = new ArrayList();
                                        PackageManagerTracedLock packageManagerTracedLock =
                                                storageEventHelper.mPm.mInstallLock;
                                        packageManagerTracedLock.mLock.lock();
                                        try {
                                            PackageManagerTracedLock packageManagerTracedLock2 =
                                                    storageEventHelper.mPm.mLock;
                                            boolean z = PackageManagerService.DEBUG_COMPRESSION;
                                            synchronized (packageManagerTracedLock2) {
                                                try {
                                                    Iterator it =
                                                            ((ArrayList)
                                                                            storageEventHelper.mPm
                                                                                    .mSettings
                                                                                    .getVolumePackagesLPr(
                                                                                            str))
                                                                    .iterator();
                                                    while (it.hasNext()) {
                                                        PackageStateInternal packageStateInternal =
                                                                (PackageStateInternal) it.next();
                                                        if (packageStateInternal.getPkg() != null) {
                                                            AndroidPackageInternal pkg =
                                                                    packageStateInternal.getPkg();
                                                            PackageFreezer freezePackageForDelete =
                                                                    storageEventHelper.mPm
                                                                            .freezePackageForDelete(
                                                                                    -1,
                                                                                    i6,
                                                                                    packageStateInternal
                                                                                            .getPackageName(),
                                                                                    "unloadPrivatePackagesInner");
                                                            try {
                                                                if (storageEventHelper
                                                                        .mDeletePackageHelper
                                                                        .deletePackageLIF(
                                                                                packageStateInternal
                                                                                        .getPackageName(),
                                                                                null,
                                                                                false,
                                                                                userIds,
                                                                                1,
                                                                                new PackageRemovedInfo(),
                                                                                false)) {
                                                                    arrayList.add(pkg);
                                                                } else {
                                                                    Slog.w(
                                                                            "PackageManager",
                                                                            "Failed to unload "
                                                                                    + packageStateInternal
                                                                                            .getPath());
                                                                }
                                                                freezePackageForDelete.close();
                                                                AttributeCache.instance()
                                                                        .removePackage(
                                                                                packageStateInternal
                                                                                        .getPackageName());
                                                                i6 = 1;
                                                            } catch (Throwable th) {
                                                                try {
                                                                    freezePackageForDelete.close();
                                                                    throw th;
                                                                } catch (Throwable th2) {
                                                                    th.addSuppressed(th2);
                                                                    throw th;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    storageEventHelper.mPm.writeSettingsLPrTEMP(
                                                            false);
                                                } catch (Throwable th3) {
                                                    boolean z2 =
                                                            PackageManagerService.DEBUG_COMPRESSION;
                                                    throw th3;
                                                }
                                            }
                                            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                                            packageManagerTracedLock.close();
                                            storageEventHelper.mBroadcastHelper
                                                    .sendResourcesChangedBroadcastAndNotify(
                                                            storageEventHelper.mPm
                                                                    .snapshotComputer(),
                                                            false,
                                                            false,
                                                            arrayList,
                                                            null);
                                            synchronized (storageEventHelper.mLoadedVolumes) {
                                                storageEventHelper.mLoadedVolumes.remove(
                                                        volumeInfo2.getId());
                                            }
                                            ResourcesManager.getInstance()
                                                    .invalidatePath(
                                                            volumeInfo2
                                                                    .getPath()
                                                                    .getAbsolutePath());
                                            for (i5 = 0; i5 < 3; i5++) {
                                                System.gc();
                                                System.runFinalization();
                                            }
                                            return;
                                        } finally {
                                        }
                                }
                            }
                        });
            } else if (i3 == 5) {
                final int i5 = 1;
                this.mPm.mHandler.post(
                        new Runnable(this) { // from class:
                            // com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0
                            public final /* synthetic */ StorageEventHelper f$0;

                            {
                                this.f$0 = this;
                            }

                            private final void
                                    run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0() {
                                /*
                                    Method dump skipped, instructions count: 554
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0.run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0():void");
                            }

                            /* JADX WARN: Finally extract failed */
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i52;
                                int i6 = 1;
                                switch (i5) {
                                    case 0:
                                        run$com$android$server$pm$StorageEventHelper$$ExternalSyntheticLambda0();
                                        return;
                                    default:
                                        StorageEventHelper storageEventHelper = this.f$0;
                                        VolumeInfo volumeInfo2 = volumeInfo;
                                        storageEventHelper.getClass();
                                        String str = volumeInfo2.fsUuid;
                                        if (TextUtils.isEmpty(str)) {
                                            Slog.e(
                                                    "PackageManager",
                                                    "Unloading internal storage is probably a"
                                                        + " mistake; ignoring");
                                            return;
                                        }
                                        int[] userIds =
                                                storageEventHelper.mPm.mUserManager.getUserIds();
                                        ArrayList arrayList = new ArrayList();
                                        PackageManagerTracedLock packageManagerTracedLock =
                                                storageEventHelper.mPm.mInstallLock;
                                        packageManagerTracedLock.mLock.lock();
                                        try {
                                            PackageManagerTracedLock packageManagerTracedLock2 =
                                                    storageEventHelper.mPm.mLock;
                                            boolean z = PackageManagerService.DEBUG_COMPRESSION;
                                            synchronized (packageManagerTracedLock2) {
                                                try {
                                                    Iterator it =
                                                            ((ArrayList)
                                                                            storageEventHelper.mPm
                                                                                    .mSettings
                                                                                    .getVolumePackagesLPr(
                                                                                            str))
                                                                    .iterator();
                                                    while (it.hasNext()) {
                                                        PackageStateInternal packageStateInternal =
                                                                (PackageStateInternal) it.next();
                                                        if (packageStateInternal.getPkg() != null) {
                                                            AndroidPackageInternal pkg =
                                                                    packageStateInternal.getPkg();
                                                            PackageFreezer freezePackageForDelete =
                                                                    storageEventHelper.mPm
                                                                            .freezePackageForDelete(
                                                                                    -1,
                                                                                    i6,
                                                                                    packageStateInternal
                                                                                            .getPackageName(),
                                                                                    "unloadPrivatePackagesInner");
                                                            try {
                                                                if (storageEventHelper
                                                                        .mDeletePackageHelper
                                                                        .deletePackageLIF(
                                                                                packageStateInternal
                                                                                        .getPackageName(),
                                                                                null,
                                                                                false,
                                                                                userIds,
                                                                                1,
                                                                                new PackageRemovedInfo(),
                                                                                false)) {
                                                                    arrayList.add(pkg);
                                                                } else {
                                                                    Slog.w(
                                                                            "PackageManager",
                                                                            "Failed to unload "
                                                                                    + packageStateInternal
                                                                                            .getPath());
                                                                }
                                                                freezePackageForDelete.close();
                                                                AttributeCache.instance()
                                                                        .removePackage(
                                                                                packageStateInternal
                                                                                        .getPackageName());
                                                                i6 = 1;
                                                            } catch (Throwable th) {
                                                                try {
                                                                    freezePackageForDelete.close();
                                                                    throw th;
                                                                } catch (Throwable th2) {
                                                                    th.addSuppressed(th2);
                                                                    throw th;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    storageEventHelper.mPm.writeSettingsLPrTEMP(
                                                            false);
                                                } catch (Throwable th3) {
                                                    boolean z2 =
                                                            PackageManagerService.DEBUG_COMPRESSION;
                                                    throw th3;
                                                }
                                            }
                                            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                                            packageManagerTracedLock.close();
                                            storageEventHelper.mBroadcastHelper
                                                    .sendResourcesChangedBroadcastAndNotify(
                                                            storageEventHelper.mPm
                                                                    .snapshotComputer(),
                                                            false,
                                                            false,
                                                            arrayList,
                                                            null);
                                            synchronized (storageEventHelper.mLoadedVolumes) {
                                                storageEventHelper.mLoadedVolumes.remove(
                                                        volumeInfo2.getId());
                                            }
                                            ResourcesManager.getInstance()
                                                    .invalidatePath(
                                                            volumeInfo2
                                                                    .getPath()
                                                                    .getAbsolutePath());
                                            for (i52 = 0; i52 < 3; i52++) {
                                                System.gc();
                                                System.runFinalization();
                                            }
                                            return;
                                        } finally {
                                        }
                                }
                            }
                        });
            }
        }
        if (volumeInfo.type == 0 && (diskInfo = volumeInfo.disk) != null && diskInfo.isSd()) {
            AsecInstallHelper asecInstallHelper = this.mPm.mCustomInjector.getAsecInstallHelper();
            int i6 = volumeInfo.state;
            if (i6 == 2) {
                asecInstallHelper.getClass();
                AsecInstallHelper.sPreMounted = true;
                Log.i("PackageManager", "SD Card is mounted, updateExternalMediaStatus");
                asecInstallHelper.updateExternalMediaStatus(true, false);
                return;
            }
            if (i6 == 5) {
                asecInstallHelper.getClass();
                AsecInstallHelper.sPreMounted = false;
                Log.i("PackageManager", "SD Card is unmounted, updateExternalMediaStatus");
                asecInstallHelper.updateExternalMediaStatus(false, false);
            }
        }
    }

    public final void reconcileApps(Computer computer, String str) {
        ArrayList arrayList = new ArrayList();
        ArrayMap packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(
                    ((PackageStateInternal) packageStates.valueAt(i)).getPath().getAbsolutePath());
        }
        ArrayList arrayList2 = null;
        for (File file : FileUtils.listFilesOrEmpty(Environment.getDataAppDirectory(str))) {
            if ((ApkLiteParseUtils.isApkFile(file) || file.isDirectory())
                    && !PackageInstallerService.isStageName(file.getName())) {
                String absolutePath = file.getAbsolutePath();
                int size2 = arrayList.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size2) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(file);
                    } else if (((String) arrayList.get(i2)).startsWith(absolutePath)) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        if (arrayList2 != null) {
            int size3 = arrayList2.size();
            for (int i3 = 0; i3 < size3; i3++) {
                File file2 = (File) arrayList2.get(i3);
                PackageManagerServiceUtils.logCriticalInfo(5, "Destroying orphaned at " + file2);
                this.mRemovePackageHelper.removeCodePath(file2);
            }
        }
    }
}
