package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackagePartitions;
import android.content.pm.UserInfo;
import android.content.pm.VersionedPackage;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.os.Environment;
import android.os.FileUtils;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.pm.Settings;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StorageEventHelper extends StorageEventListener {
    public final AsecInstallHelper mAsecInstallHelper;
    public final BroadcastHelper mBroadcastHelper;
    public final DeletePackageHelper mDeletePackageHelper;
    public final ArraySet mLoadedVolumes = new ArraySet();
    public final PackageManagerService mPm;
    public final RemovePackageHelper mRemovePackageHelper;

    public StorageEventHelper(PackageManagerService packageManagerService, DeletePackageHelper deletePackageHelper, RemovePackageHelper removePackageHelper, AsecInstallHelper asecInstallHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = new BroadcastHelper(packageManagerService.mInjector);
        this.mDeletePackageHelper = deletePackageHelper;
        this.mRemovePackageHelper = removePackageHelper;
        this.mAsecInstallHelper = asecInstallHelper;
    }

    public void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
        DiskInfo diskInfo;
        if (volumeInfo.type == 1) {
            int i3 = volumeInfo.state;
            if (i3 == 2) {
                String fsUuid = volumeInfo.getFsUuid();
                this.mPm.mUserManager.reconcileUsers(fsUuid);
                reconcileApps(this.mPm.snapshotComputer(), fsUuid);
                this.mPm.mInstallerService.onPrivateVolumeMounted(fsUuid);
                loadPrivatePackages(volumeInfo);
            } else if (i3 == 5) {
                unloadPrivatePackages(volumeInfo);
            }
        }
        if (volumeInfo.type == 0 && (diskInfo = volumeInfo.disk) != null && diskInfo.isSd()) {
            int i4 = volumeInfo.state;
            if (i4 == 2) {
                this.mAsecInstallHelper.updatePreMountState(true);
                Log.i("PackageManager", "SD Card is mounted, updateExternalMediaStatus");
                this.mAsecInstallHelper.updateExternalMediaStatus(true, false);
            } else if (i4 == 5) {
                this.mAsecInstallHelper.updatePreMountState(false);
                Log.i("PackageManager", "SD Card is unmounted, updateExternalMediaStatus");
                this.mAsecInstallHelper.updateExternalMediaStatus(false, false);
            }
        }
    }

    public void onVolumeForgotten(String str) {
        if (TextUtils.isEmpty(str)) {
            Slog.e("PackageManager", "Forgetting internal storage is probably a mistake; ignoring");
            return;
        }
        synchronized (this.mPm.mLock) {
            for (PackageStateInternal packageStateInternal : this.mPm.mSettings.getVolumePackagesLPr(str)) {
                Slog.d("PackageManager", "Destroying " + packageStateInternal.getPackageName() + " because volume was forgotten");
                this.mPm.deletePackageVersioned(new VersionedPackage(packageStateInternal.getPackageName(), -1), new PackageManager.LegacyPackageDeleteObserver((IPackageDeleteObserver) null).getBinder(), 0, 2);
                AttributeCache.instance().removePackage(packageStateInternal.getPackageName());
            }
            this.mPm.mSettings.onVolumeForgotten(str);
            this.mPm.writeSettingsLPrTEMP();
        }
    }

    public final void loadPrivatePackages(final VolumeInfo volumeInfo) {
        this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StorageEventHelper.this.lambda$loadPrivatePackages$0(volumeInfo);
            }
        });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:34|(2:58|(2:60|61)(2:62|49))(2:38|39)|40|41|124|46|47|48|49|32) */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0130, code lost:
    
        android.util.Slog.w("PackageManager", "Failed to prepare storage: " + r0);
     */
    /* renamed from: loadPrivatePackagesInner, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void lambda$loadPrivatePackages$0(VolumeInfo volumeInfo) {
        Settings.VersionInfo findOrCreateVersion;
        List<PackageStateInternal> volumePackagesLPr;
        int i;
        String str = volumeInfo.fsUuid;
        if (TextUtils.isEmpty(str)) {
            Slog.e("PackageManager", "Loading internal storage is probably a mistake; ignoring");
            return;
        }
        AppDataHelper appDataHelper = new AppDataHelper(this.mPm);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int defParseFlags = this.mPm.getDefParseFlags() | 8;
        InstallPackageHelper installPackageHelper = new InstallPackageHelper(this.mPm);
        synchronized (this.mPm.mLock) {
            findOrCreateVersion = this.mPm.mSettings.findOrCreateVersion(str);
            volumePackagesLPr = this.mPm.mSettings.getVolumePackagesLPr(str);
        }
        for (PackageStateInternal packageStateInternal : volumePackagesLPr) {
            arrayList.add(this.mPm.freezePackage(packageStateInternal.getPackageName(), -1, "loadPrivatePackagesInner", 13));
            synchronized (this.mPm.mInstallLock) {
                try {
                    arrayList2.add(installPackageHelper.initPackageTracedLI(packageStateInternal.getPath(), defParseFlags, 512));
                } catch (PackageManagerException e) {
                    Slog.w("PackageManager", "Failed to scan " + packageStateInternal.getPath() + ": " + e.getMessage());
                }
                if (!PackagePartitions.FINGERPRINT.equals(findOrCreateVersion.fingerprint)) {
                    appDataHelper.clearAppDataLIF(packageStateInternal.getPkg(), -1, 131111);
                }
            }
        }
        StorageManager storageManager = (StorageManager) this.mPm.mInjector.getSystemService(StorageManager.class);
        UserManagerInternal userManagerInternal = this.mPm.mInjector.getUserManagerInternal();
        StorageManagerInternal storageManagerInternal = (StorageManagerInternal) this.mPm.mInjector.getLocalService(StorageManagerInternal.class);
        for (UserInfo userInfo : this.mPm.mUserManager.getUsers(false)) {
            if (StorageManager.isUserKeyUnlocked(userInfo.id) && storageManagerInternal.isCeStoragePrepared(userInfo.id)) {
                i = 3;
            } else if (userManagerInternal.isUserRunning(userInfo.id)) {
                i = 1;
            } else {
                continue;
            }
            storageManager.prepareUserStorage(str, userInfo.id, userInfo.serialNumber, i);
            synchronized (this.mPm.mInstallLock) {
                appDataHelper.reconcileAppsDataLI(str, userInfo.id, i, true);
            }
        }
        synchronized (this.mPm.mLock) {
            boolean z = !PackagePartitions.FINGERPRINT.equals(findOrCreateVersion.fingerprint);
            if (z) {
                PackageManagerServiceUtils.logCriticalInfo(4, "Partitions fingerprint changed from " + findOrCreateVersion.fingerprint + " to " + PackagePartitions.FINGERPRINT + "; regranting permissions for " + str);
            }
            this.mPm.mPermissionManager.onStorageVolumeMounted(str, z);
            findOrCreateVersion.forceCurrent();
            this.mPm.writeSettingsLPrTEMP();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((PackageFreezer) it.next()).close();
        }
        sendResourcesChangedBroadcast(true, false, arrayList2);
        synchronized (this.mLoadedVolumes) {
            this.mLoadedVolumes.add(volumeInfo.getId());
        }
    }

    public final void unloadPrivatePackages(final VolumeInfo volumeInfo) {
        this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                StorageEventHelper.this.lambda$unloadPrivatePackages$1(volumeInfo);
            }
        });
    }

    /* renamed from: unloadPrivatePackagesInner, reason: merged with bridge method [inline-methods] */
    public final void lambda$unloadPrivatePackages$1(VolumeInfo volumeInfo) {
        String str = volumeInfo.fsUuid;
        if (TextUtils.isEmpty(str)) {
            Slog.e("PackageManager", "Unloading internal storage is probably a mistake; ignoring");
            return;
        }
        int[] userIds = this.mPm.mUserManager.getUserIds();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mPm.mInstallLock) {
            synchronized (this.mPm.mLock) {
                for (PackageStateInternal packageStateInternal : this.mPm.mSettings.getVolumePackagesLPr(str)) {
                    if (packageStateInternal.getPkg() != null) {
                        AndroidPackageInternal pkg = packageStateInternal.getPkg();
                        PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo(this.mPm);
                        PackageFreezer freezePackageForDelete = this.mPm.freezePackageForDelete(packageStateInternal.getPackageName(), -1, 1, "unloadPrivatePackagesInner", 13);
                        try {
                            if (this.mDeletePackageHelper.deletePackageLIF(packageStateInternal.getPackageName(), null, false, userIds, 1, packageRemovedInfo, false)) {
                                arrayList.add(pkg);
                            } else {
                                Slog.w("PackageManager", "Failed to unload " + packageStateInternal.getPath());
                            }
                            if (freezePackageForDelete != null) {
                                freezePackageForDelete.close();
                            }
                            AttributeCache.instance().removePackage(packageStateInternal.getPackageName());
                        } finally {
                        }
                    }
                }
                this.mPm.writeSettingsLPrTEMP();
            }
        }
        sendResourcesChangedBroadcast(false, false, arrayList);
        synchronized (this.mLoadedVolumes) {
            this.mLoadedVolumes.remove(volumeInfo.getId());
        }
        ResourcesManager.getInstance().invalidatePath(volumeInfo.getPath().getAbsolutePath());
        for (int i = 0; i < 3; i++) {
            System.gc();
            System.runFinalization();
        }
    }

    public final void sendResourcesChangedBroadcast(boolean z, boolean z2, ArrayList arrayList) {
        int size = arrayList.size();
        String[] strArr = new String[size];
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            AndroidPackage androidPackage = (AndroidPackage) arrayList.get(i);
            strArr[i] = androidPackage.getPackageName();
            iArr[i] = androidPackage.getUid();
        }
        BroadcastHelper broadcastHelper = this.mBroadcastHelper;
        PackageManagerService packageManagerService = this.mPm;
        Objects.requireNonNull(packageManagerService);
        broadcastHelper.sendResourcesChangedBroadcast(new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService), z, z2, strArr, iArr);
    }

    public void reconcileApps(Computer computer, String str) {
        List collectAbsoluteCodePaths = collectAbsoluteCodePaths(computer);
        ArrayList arrayList = null;
        for (File file : FileUtils.listFilesOrEmpty(Environment.getDataAppDirectory(str))) {
            boolean z = true;
            if ((ApkLiteParseUtils.isApkFile(file) || file.isDirectory()) && !PackageInstallerService.isStageName(file.getName())) {
                String absolutePath = file.getAbsolutePath();
                int size = collectAbsoluteCodePaths.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z = false;
                        break;
                    } else if (((String) collectAbsoluteCodePaths.get(i)).startsWith(absolutePath)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(file);
                }
            }
        }
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                File file2 = (File) arrayList.get(i2);
                PackageManagerServiceUtils.logCriticalInfo(5, "Destroying orphaned at " + file2);
                this.mRemovePackageHelper.removeCodePath(file2);
            }
        }
    }

    public final List collectAbsoluteCodePaths(Computer computer) {
        ArrayList arrayList = new ArrayList();
        ArrayMap packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(((PackageStateInternal) packageStates.valueAt(i)).getPath().getAbsolutePath());
        }
        return arrayList;
    }

    public void dumpLoadedVolumes(PrintWriter printWriter, DumpState dumpState) {
        if (dumpState.onTitlePrinted()) {
            printWriter.println();
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ", 120);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Loaded volumes:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLoadedVolumes) {
            if (this.mLoadedVolumes.size() == 0) {
                indentingPrintWriter.println("(none)");
            } else {
                for (int i = 0; i < this.mLoadedVolumes.size(); i++) {
                    indentingPrintWriter.println((String) this.mLoadedVolumes.valueAt(i));
                }
            }
        }
        indentingPrintWriter.decreaseIndent();
    }
}
