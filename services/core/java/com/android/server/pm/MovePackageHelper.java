package com.android.server.pm;

import android.R;
import android.content.Intent;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.pm.Installer;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUtils;
import com.samsung.android.rune.PMRune;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class MovePackageHelper {
    public static Map sMoveIdMapForSd = new HashMap();
    public static ArrayList sPendingMoves = new ArrayList();
    public final PackageManagerService mPm;

    public MovePackageHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public void movePackageInternal(final String str, String str2, final int i, int i2, UserHandle userHandle) {
        String absolutePath;
        final PackageFreezer freezePackage;
        File dataAppDirectory;
        int i3;
        boolean z;
        File path;
        long j;
        boolean z2;
        int i4;
        MoveInfo moveInfo;
        StorageManager storageManager = (StorageManager) this.mPm.mInjector.getSystemService(StorageManager.class);
        PackageManager packageManager = this.mPm.mContext.getPackageManager();
        Computer snapshotComputer = this.mPm.snapshotComputer();
        PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, i2, userHandle.getIdentifier());
        if (packageStateForInstalledAndFiltered == null || packageStateForInstalledAndFiltered.getPkg() == null) {
            throw new PackageManagerException(-2, "Missing package");
        }
        AndroidPackageInternal pkg = packageStateForInstalledAndFiltered.getPkg();
        if (packageStateForInstalledAndFiltered.isSystem()) {
            throw new PackageManagerException(-3, "Cannot move system application");
        }
        boolean equals = "private".equals(str2);
        boolean z3 = this.mPm.mContext.getResources().getBoolean(R.bool.config_allowAllRotations);
        if (equals && !z3) {
            throw new PackageManagerException(-9, "3rd party apps are not allowed on internal storage");
        }
        String volumeUuid = packageStateForInstalledAndFiltered.getVolumeUuid();
        boolean isExternalAsec = AsecInstallHelper.isExternalAsec(pkg);
        if (!isExternalAsec) {
            File file = new File(pkg.getPath());
            File file2 = new File(file, "oat");
            VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(str2);
            if (!file.isDirectory() || (!file2.isDirectory() && findVolumeByUuid != null && findVolumeByUuid.getType() != 0)) {
                throw new PackageManagerException(-6, "Move only supported for modern cluster style installs");
            }
        }
        if (AsecInstallHelper.isExternalAsec(pkg) && str2 != null && !str2.equals("primary_physical")) {
            throw new PackageManagerException(-6, "Package already moved to " + str2);
        }
        if (Objects.equals(volumeUuid, str2) && !AsecInstallHelper.isExternalAsec(pkg)) {
            throw new PackageManagerException(-6, "Package already moved to " + str2);
        }
        if (!pkg.isExternalStorage() && this.mPm.isPackageDeviceAdminOnAnyUser(snapshotComputer, str)) {
            throw new PackageManagerException(-8, "Device admin cannot be moved");
        }
        if (snapshotComputer.getFrozenPackages().containsKey(str)) {
            throw new PackageManagerException(-7, "Failed to move already frozen package");
        }
        final boolean isExternalStorage = pkg.isExternalStorage();
        File file3 = new File(pkg.getPath());
        InstallSource installSource = packageStateForInstalledAndFiltered.getInstallSource();
        String cpuAbiOverride = packageStateForInstalledAndFiltered.getCpuAbiOverride();
        int appId = UserHandle.getAppId(pkg.getUid());
        String seInfo = packageStateForInstalledAndFiltered.getSeInfo();
        String valueOf = String.valueOf(packageManager.getApplicationLabel(AndroidPackageUtils.generateAppInfoWithoutState(pkg)));
        int targetSdkVersion = pkg.getTargetSdkVersion();
        int[] queryInstalledUsers = PackageStateUtils.queryInstalledUsers(packageStateForInstalledAndFiltered, this.mPm.mUserManager.getUserIds(), true);
        if (file3.getParentFile().getName().startsWith("~~")) {
            absolutePath = file3.getParentFile().getAbsolutePath();
        } else {
            absolutePath = file3.getAbsolutePath();
        }
        String str3 = absolutePath;
        synchronized (this.mPm.mLock) {
            freezePackage = this.mPm.freezePackage(str, -1, "movePackageInternal", 10);
        }
        Bundle bundle = new Bundle();
        bundle.putString("android.intent.extra.PACKAGE_NAME", str);
        bundle.putString("android.intent.extra.TITLE", valueOf);
        if (PMRune.PM_INSTALL_TO_SDCARD) {
            synchronized (sMoveIdMapForSd) {
                if (sMoveIdMapForSd.containsKey(Integer.valueOf(i))) {
                    bundle.putString("moveCaller", "smartmanager");
                }
            }
        }
        this.mPm.mMoveCallbacks.notifyCreated(i, bundle);
        int i5 = 16;
        if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str2)) {
            dataAppDirectory = Environment.getDataAppDirectory(str2);
            i3 = 16;
            z = !isExternalAsec;
        } else {
            if ("primary_physical".equals(str2)) {
                path = storageManager.getPrimaryPhysicalVolume().getPath();
            } else {
                VolumeInfo findVolumeByUuid2 = storageManager.findVolumeByUuid(str2);
                if (findVolumeByUuid2 != null && findVolumeByUuid2.getType() == 0) {
                    path = storageManager.findVolumeByUuid(str2).getPath();
                    Slog.i("PackageManager", "measurePath: " + path.toString());
                    i5 = 8;
                } else {
                    if (findVolumeByUuid2 == null || findVolumeByUuid2.getType() != 1 || !findVolumeByUuid2.isMountedWritable()) {
                        freezePackage.close();
                        throw new PackageManagerException(-6, "Move location not mounted private volume");
                    }
                    dataAppDirectory = Environment.getDataAppDirectory(str2);
                    i3 = 16;
                    z = true;
                }
            }
            dataAppDirectory = path;
            i3 = i5;
            z = false;
        }
        if (z) {
            for (int i6 : queryInstalledUsers) {
                if (StorageManager.isFileEncrypted() && !StorageManager.isUserKeyUnlocked(i6)) {
                    freezePackage.close();
                    throw new PackageManagerException(-10, "User " + i6 + " must be unlocked");
                }
            }
        }
        PackageStats packageStats = new PackageStats(null, -1);
        synchronized (this.mPm.mInstallLock) {
            for (int i7 : queryInstalledUsers) {
                if (!getPackageSizeInfoLI(str, i7, packageStats)) {
                    freezePackage.close();
                    throw new PackageManagerException(-6, "Failed to measure package size");
                }
            }
        }
        final long usableSpace = dataAppDirectory.getUsableSpace();
        if (z) {
            j = packageStats.codeSize + packageStats.dataSize;
        } else {
            j = packageStats.codeSize;
        }
        if (j > storageManager.getStorageBytesUntilLow(dataAppDirectory)) {
            freezePackage.close();
            throw new PackageManagerException(-6, "Not enough free space to move");
        }
        this.mPm.mMoveCallbacks.notifyStatusChanged(i, 10);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final File file4 = dataAppDirectory;
        IPackageInstallObserver2.Stub stub = new IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.MovePackageHelper.1
            public void onUserActionRequired(Intent intent) {
                freezePackage.close();
                throw new IllegalStateException();
            }

            public void onPackageInstalled(String str4, int i8, String str5, Bundle bundle2) {
                countDownLatch.countDown();
                freezePackage.close();
                if (PMRune.PM_INSTALL_TO_SDCARD) {
                    synchronized (MovePackageHelper.sMoveIdMapForSd) {
                        if (!AsecInstallHelper.getPreMountState()) {
                            if (MovePackageHelper.sPendingMoves.size() > 0) {
                                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(((SdcardParams) MovePackageHelper.sPendingMoves.get(0)).moveId, -6);
                            }
                            MovePackageHelper.sMoveIdMapForSd.clear();
                            MovePackageHelper.sPendingMoves.clear();
                        } else if (MovePackageHelper.sMoveIdMapForSd.containsKey(Integer.valueOf(i))) {
                            MovePackageHelper.sPendingMoves.remove(0);
                            if (MovePackageHelper.sPendingMoves.size() > 0) {
                                MovePackageHelper.this.startMovePackage((SdcardParams) MovePackageHelper.sPendingMoves.get(0));
                            }
                        }
                    }
                }
                int installStatusToPublicStatus = PackageManager.installStatusToPublicStatus(i8);
                if (installStatusToPublicStatus == 0) {
                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, 100);
                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -100);
                    MovePackageHelper.this.logAppMovedStorage(str, isExternalStorage);
                } else if (installStatusToPublicStatus == 6) {
                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -1);
                } else {
                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -6);
                }
            }
        };
        if (z) {
            final long j2 = j;
            z2 = true;
            new Thread(new Runnable() { // from class: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MovePackageHelper.this.lambda$movePackageInternal$0(countDownLatch, usableSpace, file4, j2, i);
                }
            }).start();
            moveInfo = new MoveInfo(i, volumeUuid, str2, str, appId, seInfo, targetSdkVersion, str3);
            i4 = 0;
        } else {
            z2 = true;
            i4 = 0;
            final long j3 = j;
            new Thread(new Runnable() { // from class: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MovePackageHelper.this.lambda$movePackageInternal$1(countDownLatch, usableSpace, file4, j3, i);
                }
            }).start();
            moveInfo = null;
        }
        int i8 = i3 | 2;
        OriginInfo fromExistingFile = OriginInfo.fromExistingFile(file3);
        ParseResult parsePackageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing(), new File(fromExistingFile.mResolvedPath), i4);
        InstallingSession installingSession = new InstallingSession(fromExistingFile, moveInfo, (IPackageInstallObserver2) stub, i8, installSource, str2, userHandle, cpuAbiOverride, 0, parsePackageLite.isSuccess() ? (PackageLite) parsePackageLite.getResult() : null, this.mPm);
        installingSession.mIsMoveRequest = z2;
        installingSession.movePackage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(8:8|(4:10|(1:12)(1:16)|13|14)(2:17|18)|15|2|3|4|6|5) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$movePackageInternal$0(CountDownLatch countDownLatch, long j, File file, long j2, int i) {
        SecureRandom secureRandom = new SecureRandom();
        int i2 = 0;
        while (!countDownLatch.await(1L, TimeUnit.SECONDS)) {
            int constrain = ((int) MathUtils.constrain(((j - file.getUsableSpace()) * 80) / j2, 0L, 80L)) + 10;
            if (i2 >= constrain) {
                i2 += i2 < 90 ? secureRandom.nextInt(5) : 0;
            } else {
                i2 = constrain;
            }
            this.mPm.mMoveCallbacks.notifyStatusChanged(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(9:8|(1:10)(1:21)|(4:12|(1:14)(1:18)|15|16)(2:19|20)|17|2|3|4|6|5) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$movePackageInternal$1(CountDownLatch countDownLatch, long j, File file, long j2, int i) {
        SecureRandom secureRandom = new SecureRandom();
        int i2 = 0;
        while (!countDownLatch.await(1L, TimeUnit.SECONDS)) {
            int constrain = j2 != 0 ? ((int) MathUtils.constrain(((j - file.getFreeSpace()) * 80) / j2, 0L, 80L)) + 10 : 100;
            if (i2 >= constrain) {
                i2 += i2 < 90 ? secureRandom.nextInt(10) : 0;
            } else {
                i2 = constrain;
            }
            this.mPm.mMoveCallbacks.notifyStatusChanged(i, i2);
        }
    }

    public final void logAppMovedStorage(String str, boolean z) {
        AndroidPackage androidPackage = this.mPm.snapshotComputer().getPackage(str);
        if (androidPackage == null) {
            return;
        }
        int packageExternalStorageType = PackageManagerServiceUtils.getPackageExternalStorageType(((StorageManager) this.mPm.mInjector.getSystemService(StorageManager.class)).findVolumeByUuid(StorageManager.convert(androidPackage.getVolumeUuid()).toString()), androidPackage.isExternalStorage());
        if (!z && androidPackage.isExternalStorage()) {
            FrameworkStatsLog.write(183, packageExternalStorageType, 1, str);
        } else {
            if (!z || androidPackage.isExternalStorage()) {
                return;
            }
            FrameworkStatsLog.write(183, packageExternalStorageType, 2, str);
        }
    }

    public final boolean getPackageSizeInfoLI(String str, int i, PackageStats packageStats) {
        PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str);
        if (packageStateInternal == null) {
            Slog.w("PackageManager", "Failed to find settings for " + str);
            return false;
        }
        try {
            this.mPm.mInstaller.getAppSize(packageStateInternal.getVolumeUuid(), new String[]{str}, i, 0, packageStateInternal.getAppId(), new long[]{packageStateInternal.getUserStateOrDefault(i).getCeDataInode()}, new String[]{packageStateInternal.getPathString()}, packageStats);
            if (PackageManagerServiceUtils.isSystemApp(packageStateInternal) && !PackageManagerServiceUtils.isUpdatedSystemApp(packageStateInternal)) {
                packageStats.codeSize = 0L;
            }
            packageStats.dataSize -= packageStats.cacheSize;
            return true;
        } catch (Installer.InstallerException e) {
            Slog.w("PackageManager", String.valueOf(e));
            return false;
        }
    }

    public class MoveCallbacks extends Handler {
        public final RemoteCallbackList mCallbacks;
        public final SparseIntArray mLastStatus;

        public MoveCallbacks(Looper looper) {
            super(looper);
            this.mCallbacks = new RemoteCallbackList();
            this.mLastStatus = new SparseIntArray();
        }

        public void register(IPackageMoveObserver iPackageMoveObserver) {
            this.mCallbacks.register(iPackageMoveObserver);
        }

        public void unregister(IPackageMoveObserver iPackageMoveObserver) {
            this.mCallbacks.unregister(iPackageMoveObserver);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SomeArgs someArgs = (SomeArgs) message.obj;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    invokeCallback((IPackageMoveObserver) this.mCallbacks.getBroadcastItem(i), message.what, someArgs);
                } catch (RemoteException unused) {
                }
            }
            this.mCallbacks.finishBroadcast();
            someArgs.recycle();
        }

        public final void invokeCallback(IPackageMoveObserver iPackageMoveObserver, int i, SomeArgs someArgs) {
            if (i == 1) {
                iPackageMoveObserver.onCreated(someArgs.argi1, (Bundle) someArgs.arg2);
            } else {
                if (i != 2) {
                    return;
                }
                iPackageMoveObserver.onStatusChanged(someArgs.argi1, someArgs.argi2, ((Long) someArgs.arg3).longValue());
            }
        }

        public void notifyCreated(int i, Bundle bundle) {
            Slog.v("PackageManager", "Move " + i + " created " + bundle.toString());
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg2 = bundle;
            obtainMessage(1, obtain).sendToTarget();
        }

        public void notifyStatusChanged(int i, int i2) {
            notifyStatusChanged(i, i2, -1L);
        }

        public void notifyStatusChanged(int i, int i2, long j) {
            Slog.v("PackageManager", "Move " + i + " status " + i2);
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                synchronized (MovePackageHelper.sMoveIdMapForSd) {
                    if (MovePackageHelper.sMoveIdMapForSd.containsKey(Integer.valueOf(i))) {
                        try {
                            ((IMemorySaverPackageMoveObserver) MovePackageHelper.sMoveIdMapForSd.get(Integer.valueOf(i))).onStatusChanged(i, i2, j);
                            if (i2 == -100 || i2 == -1 || i2 == -6) {
                                Slog.v("PackageManager", "Multi move id " + i + " status " + i2 + " sPendingMoves " + MovePackageHelper.sPendingMoves + " sMoveIdMapForSd " + MovePackageHelper.sMoveIdMapForSd);
                                MovePackageHelper.sMoveIdMapForSd.remove(Integer.valueOf(i));
                            }
                        } catch (Exception unused) {
                            Slog.w("PackageManager", "Exception while multi sd move");
                            MovePackageHelper.sMoveIdMapForSd.remove(Integer.valueOf(i));
                        }
                    }
                }
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.arg3 = Long.valueOf(j);
            obtainMessage(2, obtain).sendToTarget();
            synchronized (this.mLastStatus) {
                this.mLastStatus.put(i, i2);
            }
        }
    }

    public class SdcardParams {
        public final int callingUid;
        public final int moveId;
        public final String packageName;
        public final UserHandle user;
        public final String volumeUuid;

        public SdcardParams(String str, String str2, int i, UserHandle userHandle, int i2) {
            this.packageName = str;
            this.volumeUuid = str2;
            this.callingUid = i;
            this.user = userHandle;
            this.moveId = i2;
        }

        public String toString() {
            return "SdcardParams{" + Integer.toHexString(System.identityHashCode(this)) + " PackageName= " + this.packageName + " Volume= " + this.volumeUuid + "}";
        }
    }

    public final void startMovePackage(final SdcardParams sdcardParams) {
        this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.MovePackageHelper.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    MovePackageHelper movePackageHelper = MovePackageHelper.this;
                    SdcardParams sdcardParams2 = sdcardParams;
                    movePackageHelper.movePackageInternal(sdcardParams2.packageName, sdcardParams2.volumeUuid, sdcardParams2.moveId, sdcardParams2.callingUid, sdcardParams2.user);
                } catch (PackageManagerException e) {
                    Slog.w("PackageManager", "Failed to move " + sdcardParams.packageName, e);
                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(sdcardParams.moveId, -6);
                    synchronized (MovePackageHelper.sMoveIdMapForSd) {
                        if (MovePackageHelper.sPendingMoves.size() > 0) {
                            MovePackageHelper.sPendingMoves.remove(0);
                        }
                        if (e.error == -6) {
                            Iterator it = MovePackageHelper.sPendingMoves.iterator();
                            while (it.hasNext()) {
                                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(((SdcardParams) it.next()).moveId, -1);
                            }
                            MovePackageHelper.sPendingMoves.clear();
                        }
                    }
                }
            }
        });
    }

    public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) {
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.MOVE_PACKAGE", null);
        synchronized (sMoveIdMapForSd) {
            if (!AsecInstallHelper.getPreMountState()) {
                try {
                    iMemorySaverPackageMoveObserver.onStatusChanged(-1, -6, -1L);
                } catch (Exception e) {
                    Log.w("PackageManager", " remote exception on observer " + e);
                }
                sMoveIdMapForSd.clear();
                sPendingMoves.clear();
                return -1;
            }
            int callingUid = Binder.getCallingUid();
            UserHandle userHandle = new UserHandle(UserHandle.getUserId(callingUid));
            int andIncrement = this.mPm.mNextMoveId.getAndIncrement();
            if (iMemorySaverPackageMoveObserver != null) {
                sMoveIdMapForSd.put(Integer.valueOf(andIncrement), iMemorySaverPackageMoveObserver);
            }
            SdcardParams sdcardParams = new SdcardParams(str, str2, callingUid, userHandle, andIncrement);
            sPendingMoves.add(sPendingMoves.size(), sdcardParams);
            if (sPendingMoves.size() == 1) {
                startMovePackage(sdcardParams);
            }
            return andIncrement;
        }
    }
}
