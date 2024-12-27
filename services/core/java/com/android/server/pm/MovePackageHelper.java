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
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.internal.os.SomeArgs;
import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.pm.Installer;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateUtils;
import com.samsung.android.rune.PMRune;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MovePackageHelper {
    public static final Map sMoveIdMapForSd = new HashMap();
    public static final ArrayList sPendingMoves = new ArrayList();
    public final PackageManagerService mPm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.MovePackageHelper$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ SdcardParams val$sdParams;

        public AnonymousClass2(SdcardParams sdcardParams) {
            this.val$sdParams = sdcardParams;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                MovePackageHelper movePackageHelper = MovePackageHelper.this;
                SdcardParams sdcardParams = this.val$sdParams;
                movePackageHelper.movePackageInternal(sdcardParams.moveId, sdcardParams.callingUid, sdcardParams.user, sdcardParams.packageName, sdcardParams.volumeUuid);
            } catch (PackageManagerException e) {
                Slog.w("PackageManager", "Failed to move " + this.val$sdParams.packageName, e);
                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(this.val$sdParams.moveId, -6);
                synchronized (MovePackageHelper.sMoveIdMapForSd) {
                    try {
                        ArrayList arrayList = MovePackageHelper.sPendingMoves;
                        if (arrayList.size() > 0) {
                            arrayList.remove(0);
                        }
                        if (e.error == -6) {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(((SdcardParams) it.next()).moveId, -1);
                            }
                            MovePackageHelper.sPendingMoves.clear();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MoveCallbacks extends Handler {
        public final RemoteCallbackList mCallbacks;
        public final SparseIntArray mLastStatus;

        public MoveCallbacks(Looper looper) {
            super(looper);
            this.mCallbacks = new RemoteCallbackList();
            this.mLastStatus = new SparseIntArray();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SomeArgs someArgs = (SomeArgs) message.obj;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                IPackageMoveObserver broadcastItem = this.mCallbacks.getBroadcastItem(i);
                try {
                    int i2 = message.what;
                    if (i2 == 1) {
                        broadcastItem.onCreated(someArgs.argi1, (Bundle) someArgs.arg2);
                    } else if (i2 == 2) {
                        broadcastItem.onStatusChanged(someArgs.argi1, someArgs.argi2, ((Long) someArgs.arg3).longValue());
                    }
                } catch (RemoteException unused) {
                }
            }
            this.mCallbacks.finishBroadcast();
            someArgs.recycle();
        }

        public final void notifyCreated(int i, Bundle bundle) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Move ", " created ");
            m.append(bundle.toString());
            Slog.v("PackageManager", m.toString());
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg2 = bundle;
            obtainMessage(1, obtain).sendToTarget();
        }

        public final void notifyStatusChanged(int i, int i2) {
            notifyStatusChanged(i, i2, -1L);
        }

        public final void notifyStatusChanged(int i, int i2, long j) {
            Slog.v("PackageManager", "Move " + i + " status " + i2);
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                Map map = MovePackageHelper.sMoveIdMapForSd;
                synchronized (map) {
                    if (((HashMap) map).containsKey(Integer.valueOf(i))) {
                        try {
                            ((IMemorySaverPackageMoveObserver) ((HashMap) map).get(Integer.valueOf(i))).onStatusChanged(i, i2, j);
                            if (i2 == -100 || i2 == -1 || i2 == -6) {
                                Slog.v("PackageManager", "Multi move id " + i + " status " + i2 + " sPendingMoves " + MovePackageHelper.sPendingMoves + " sMoveIdMapForSd " + map);
                                ((HashMap) map).remove(Integer.valueOf(i));
                            }
                        } catch (Exception unused) {
                            Slog.w("PackageManager", "Exception while multi sd move");
                            ((HashMap) MovePackageHelper.sMoveIdMapForSd).remove(Integer.valueOf(i));
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SdcardParams {
        public final int callingUid;
        public final int moveId;
        public final String packageName;
        public final UserHandle user;
        public final String volumeUuid;

        public SdcardParams(int i, int i2, UserHandle userHandle, String str, String str2) {
            this.packageName = str;
            this.volumeUuid = str2;
            this.callingUid = i;
            this.user = userHandle;
            this.moveId = i2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SdcardParams{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" PackageName= ");
            sb.append(this.packageName);
            sb.append(" Volume= ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.volumeUuid, "}");
        }
    }

    public MovePackageHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public final boolean getPackageSizeInfoLI(String str, int i, PackageStats packageStats) {
        PackageManagerService packageManagerService = this.mPm;
        PackageSetting packageStateInternal = packageManagerService.snapshotComputer().getPackageStateInternal(str);
        if (packageStateInternal == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Failed to find settings for ", str, "PackageManager");
            return false;
        }
        try {
            packageManagerService.mInstaller.getAppSize(packageStateInternal.volumeUuid, new String[]{str}, i, 0, packageStateInternal.mAppId, new long[]{packageStateInternal.getUserStateOrDefault(i).getCeDataInode()}, new String[]{packageStateInternal.mPathString}, packageStats);
            boolean z = PackageManagerServiceUtils.DEBUG;
            if ((packageStateInternal.getFlags() & 1) != 0 && (packageStateInternal.getFlags() & 128) == 0) {
                packageStats.codeSize = 0L;
            }
            packageStats.dataSize -= packageStats.cacheSize;
            return true;
        } catch (Installer.InstallerException e) {
            Slog.w("PackageManager", String.valueOf(e));
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r29v0, types: [com.android.server.pm.MovePackageHelper$1] */
    public final void movePackageInternal(final int i, int i2, UserHandle userHandle, final String str, String str2) {
        File dataAppDirectory;
        int i3;
        boolean z;
        File path;
        MoveInfo moveInfo;
        StorageManager storageManager = (StorageManager) this.mPm.mInjector.mGetSystemServiceProducer.produce(StorageManager.class);
        PackageManager packageManager = this.mPm.mContext.getPackageManager();
        Computer snapshotComputer = this.mPm.snapshotComputer();
        PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(i2, userHandle.getIdentifier(), str);
        if (packageStateForInstalledAndFiltered == null || packageStateForInstalledAndFiltered.pkg == null) {
            throw new PackageManagerException(-2, "Missing package");
        }
        int[] queryInstalledUsers = PackageStateUtils.queryInstalledUsers(packageStateForInstalledAndFiltered, this.mPm.mUserManager.getUserIds());
        if (queryInstalledUsers.length <= 0) {
            throw new PackageManagerException(-2, "Package is not installed for any user");
        }
        UserHandle of = UserHandle.of(queryInstalledUsers[0]);
        for (int i4 : queryInstalledUsers) {
            if (snapshotComputer.shouldFilterApplicationIncludingUninstalled(packageStateForInstalledAndFiltered, i2, i4)) {
                throw new PackageManagerException(-2, "Missing package");
            }
        }
        AndroidPackageHidden androidPackageHidden = packageStateForInstalledAndFiltered.pkg;
        if (packageStateForInstalledAndFiltered.isSystem()) {
            throw new PackageManagerException(-3, "Cannot move system application");
        }
        boolean equals = "private".equals(str2);
        boolean z2 = this.mPm.mContext.getResources().getBoolean(R.bool.config_allowAutoBrightnessWhileDozing);
        if (equals && !z2) {
            throw new PackageManagerException(-9, "3rd party apps are not allowed on internal storage");
        }
        boolean isExternalAsec = AsecInstallHelper.isExternalAsec(androidPackageHidden);
        if (!isExternalAsec) {
            File file = new File(androidPackageHidden.getPath());
            File file2 = new File(file, "oat");
            VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(str2);
            if (!file.isDirectory() || (!file2.isDirectory() && findVolumeByUuid != null && findVolumeByUuid.getType() != 0)) {
                throw new PackageManagerException(-6, "Move only supported for modern cluster style installs");
            }
        }
        if (AsecInstallHelper.isExternalAsec(androidPackageHidden) && str2 != null && !str2.equals("primary_physical")) {
            throw new PackageManagerException(-6, "Package already moved to ".concat(str2));
        }
        String str3 = packageStateForInstalledAndFiltered.volumeUuid;
        if (Objects.equals(str3, str2) && !AsecInstallHelper.isExternalAsec(androidPackageHidden)) {
            throw new PackageManagerException(-6, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Package already moved to ", str2));
        }
        if (!androidPackageHidden.isExternalStorage() && this.mPm.isPackageDeviceAdminOnAnyUser(snapshotComputer, str)) {
            throw new PackageManagerException(-8, "Device admin cannot be moved");
        }
        if (snapshotComputer.getFrozenPackages().mStorage.containsKey(str)) {
            throw new PackageManagerException(-7, "Failed to move already frozen package");
        }
        final boolean isExternalStorage = androidPackageHidden.isExternalStorage();
        File file3 = new File(androidPackageHidden.getPath());
        InstallSource installSource = packageStateForInstalledAndFiltered.installSource;
        String str4 = packageStateForInstalledAndFiltered.mCpuAbiOverride;
        int appId = UserHandle.getAppId(androidPackageHidden.getUid());
        String seInfo = packageStateForInstalledAndFiltered.getSeInfo();
        String valueOf = String.valueOf(packageManager.getApplicationLabel(androidPackageHidden.toAppInfoWithoutState()));
        int targetSdkVersion = androidPackageHidden.getTargetSdkVersion();
        String absolutePath = file3.getParentFile().getName().startsWith("~~") ? file3.getParentFile().getAbsolutePath() : file3.getAbsolutePath();
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                try {
                    final PackageFreezer freezePackage = this.mPm.freezePackage(str, -1, "movePackageInternal", 10, null);
                    Bundle bundle = new Bundle();
                    bundle.putString("android.intent.extra.PACKAGE_NAME", str);
                    bundle.putString("android.intent.extra.TITLE", valueOf);
                    if (PMRune.PM_INSTALL_TO_SDCARD) {
                        Map map = sMoveIdMapForSd;
                        synchronized (map) {
                            try {
                                if (((HashMap) map).containsKey(Integer.valueOf(i))) {
                                    bundle.putString("moveCaller", "smartmanager");
                                }
                            } finally {
                            }
                        }
                    }
                    this.mPm.mMoveCallbacks.notifyCreated(i, bundle);
                    int i5 = 16;
                    if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str2)) {
                        boolean z4 = !isExternalAsec;
                        dataAppDirectory = Environment.getDataAppDirectory(str2);
                        i3 = 16;
                        z = z4;
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
                            if (StorageManager.isFileEncrypted() && !StorageManager.isCeStorageUnlocked(i6)) {
                                freezePackage.close();
                                throw new PackageManagerException(-10, BinaryTransparencyService$$ExternalSyntheticOutline0.m(i6, "User ", " must be unlocked"));
                            }
                        }
                    }
                    PackageStats packageStats = new PackageStats(null, -1);
                    PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mInstallLock;
                    packageManagerTracedLock2.mLock.lock();
                    try {
                        for (int i7 : queryInstalledUsers) {
                            if (!getPackageSizeInfoLI(str, i7, packageStats)) {
                                freezePackage.close();
                                throw new PackageManagerException(-6, "Failed to measure package size");
                            }
                        }
                        packageManagerTracedLock2.close();
                        final long usableSpace = dataAppDirectory.getUsableSpace();
                        long j = z ? packageStats.codeSize + packageStats.dataSize : packageStats.codeSize;
                        if (j > storageManager.getStorageBytesUntilLow(dataAppDirectory)) {
                            freezePackage.close();
                            throw new PackageManagerException(-6, "Not enough free space to move");
                        }
                        this.mPm.mMoveCallbacks.notifyStatusChanged(i, 10);
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        ?? r29 = new IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.MovePackageHelper.1
                            public final void onPackageInstalled(String str5, int i8, String str6, Bundle bundle2) {
                                DiskInfo disk;
                                countDownLatch.countDown();
                                freezePackage.close();
                                int i9 = 0;
                                if (PMRune.PM_INSTALL_TO_SDCARD) {
                                    Map map2 = MovePackageHelper.sMoveIdMapForSd;
                                    synchronized (map2) {
                                        try {
                                            if (!AsecInstallHelper.sPreMounted) {
                                                ArrayList arrayList = MovePackageHelper.sPendingMoves;
                                                if (arrayList.size() > 0) {
                                                    MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(((SdcardParams) arrayList.get(0)).moveId, -6);
                                                }
                                                ((HashMap) map2).clear();
                                                arrayList.clear();
                                            } else if (((HashMap) map2).containsKey(Integer.valueOf(i))) {
                                                ArrayList arrayList2 = MovePackageHelper.sPendingMoves;
                                                arrayList2.remove(0);
                                                if (arrayList2.size() > 0) {
                                                    SdcardParams sdcardParams = (SdcardParams) arrayList2.get(0);
                                                    MovePackageHelper movePackageHelper = MovePackageHelper.this;
                                                    movePackageHelper.mPm.mHandler.post(movePackageHelper.new AnonymousClass2(sdcardParams));
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                int installStatusToPublicStatus = PackageManager.installStatusToPublicStatus(i8);
                                if (installStatusToPublicStatus != 0) {
                                    if (installStatusToPublicStatus != 6) {
                                        MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -6);
                                        return;
                                    } else {
                                        MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -1);
                                        return;
                                    }
                                }
                                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, 100);
                                MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -100);
                                MovePackageHelper movePackageHelper2 = MovePackageHelper.this;
                                String str7 = str;
                                boolean z5 = isExternalStorage;
                                PackageManagerService packageManagerService = movePackageHelper2.mPm;
                                AndroidPackage androidPackage = packageManagerService.snapshotComputer().getPackage(str7);
                                if (androidPackage == null) {
                                    return;
                                }
                                VolumeInfo findVolumeByUuid3 = ((StorageManager) packageManagerService.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).findVolumeByUuid(StorageManager.convert(androidPackage.getVolumeUuid()).toString());
                                boolean isExternalStorage2 = androidPackage.isExternalStorage();
                                boolean z6 = PackageManagerServiceUtils.DEBUG;
                                if (findVolumeByUuid3 != null && (disk = findVolumeByUuid3.getDisk()) != null) {
                                    if (disk.isSd()) {
                                        i9 = 1;
                                    } else if (disk.isUsb()) {
                                        i9 = 2;
                                    } else if (isExternalStorage2) {
                                        i9 = 3;
                                    }
                                }
                                if (!z5 && androidPackage.isExternalStorage()) {
                                    FrameworkStatsLog.write(183, i9, 1, str7);
                                } else {
                                    if (!z5 || androidPackage.isExternalStorage()) {
                                        return;
                                    }
                                    FrameworkStatsLog.write(183, i9, 2, str7);
                                }
                            }

                            public final void onUserActionRequired(Intent intent) {
                                freezePackage.close();
                                throw new IllegalStateException();
                            }
                        };
                        if (z) {
                            final int i8 = 0;
                            final File file4 = dataAppDirectory;
                            final long j2 = j;
                            new Thread(new Runnable(this) { // from class: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0
                                public final /* synthetic */ MovePackageHelper f$0;

                                {
                                    this.f$0 = this;
                                }

                                /* JADX WARN: Can't wrap try/catch for region: R(5:30|(4:32|(1:34)(1:38)|35|36)(2:39|40)|37|27|28) */
                                /* JADX WARN: Can't wrap try/catch for region: R(6:7|(1:9)(1:20)|(4:11|(1:13)(1:17)|14|15)(2:18|19)|16|4|5) */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final void run() {
                                    /*
                                        r21 = this;
                                        r0 = r21
                                        int r1 = r9
                                        switch(r1) {
                                            case 0: goto L5f;
                                            default: goto L7;
                                        }
                                    L7:
                                        com.android.server.pm.MovePackageHelper r1 = r0.f$0
                                        java.util.concurrent.CountDownLatch r2 = r2
                                        long r3 = r3
                                        java.io.File r5 = r5
                                        long r6 = r6
                                        int r8 = r8
                                        r1.getClass()
                                        java.security.SecureRandom r9 = new java.security.SecureRandom
                                        r9.<init>()
                                        r10 = 0
                                        r0 = r10
                                    L1d:
                                        java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> L28
                                        r12 = 1
                                        boolean r11 = r2.await(r12, r11)     // Catch: java.lang.InterruptedException -> L28
                                        if (r11 == 0) goto L28
                                        return
                                    L28:
                                        long r11 = r5.getFreeSpace()
                                        long r11 = r3 - r11
                                        r13 = 0
                                        int r13 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
                                        r14 = 10
                                        if (r13 == 0) goto L46
                                        r15 = 80
                                        long r11 = r11 * r15
                                        long r15 = r11 / r6
                                        r17 = 0
                                        r19 = 80
                                        long r11 = android.util.MathUtils.constrain(r15, r17, r19)
                                        int r11 = (int) r11
                                        int r11 = r11 + r14
                                        goto L48
                                    L46:
                                        r11 = 100
                                    L48:
                                        if (r0 < r11) goto L56
                                        r11 = 90
                                        if (r0 >= r11) goto L53
                                        int r11 = r9.nextInt(r14)
                                        goto L54
                                    L53:
                                        r11 = r10
                                    L54:
                                        int r0 = r0 + r11
                                        goto L57
                                    L56:
                                        r0 = r11
                                    L57:
                                        com.android.server.pm.PackageManagerService r11 = r1.mPm
                                        com.android.server.pm.MovePackageHelper$MoveCallbacks r11 = r11.mMoveCallbacks
                                        r11.notifyStatusChanged(r8, r0)
                                        goto L1d
                                    L5f:
                                        com.android.server.pm.MovePackageHelper r1 = r0.f$0
                                        java.util.concurrent.CountDownLatch r2 = r2
                                        long r3 = r3
                                        java.io.File r5 = r5
                                        long r6 = r6
                                        int r0 = r8
                                        r1.getClass()
                                        java.security.SecureRandom r8 = new java.security.SecureRandom
                                        r8.<init>()
                                        r9 = 0
                                        r10 = r9
                                    L75:
                                        java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> L80
                                        r12 = 1
                                        boolean r11 = r2.await(r12, r11)     // Catch: java.lang.InterruptedException -> L80
                                        if (r11 == 0) goto L80
                                        return
                                    L80:
                                        long r11 = r5.getUsableSpace()
                                        long r11 = r3 - r11
                                        r13 = 80
                                        long r11 = r11 * r13
                                        long r13 = r11 / r6
                                        r15 = 0
                                        r17 = 80
                                        long r11 = android.util.MathUtils.constrain(r13, r15, r17)
                                        int r11 = (int) r11
                                        int r11 = r11 + 10
                                        if (r10 < r11) goto La5
                                        r11 = 90
                                        if (r10 >= r11) goto La2
                                        r11 = 5
                                        int r11 = r8.nextInt(r11)
                                        goto La3
                                    La2:
                                        r11 = r9
                                    La3:
                                        int r10 = r10 + r11
                                        goto La6
                                    La5:
                                        r10 = r11
                                    La6:
                                        com.android.server.pm.PackageManagerService r11 = r1.mPm
                                        com.android.server.pm.MovePackageHelper$MoveCallbacks r11 = r11.mMoveCallbacks
                                        r11.notifyStatusChanged(r0, r10)
                                        goto L75
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0.run():void");
                                }
                            }).start();
                            moveInfo = new MoveInfo(str3, str2, str, appId, seInfo, targetSdkVersion, absolutePath);
                        } else {
                            final int i9 = 1;
                            final File file5 = dataAppDirectory;
                            final long j3 = j;
                            new Thread(new Runnable(this) { // from class: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0
                                public final /* synthetic */ MovePackageHelper f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    /*
                                        this = this;
                                        r0 = r21
                                        int r1 = r9
                                        switch(r1) {
                                            case 0: goto L5f;
                                            default: goto L7;
                                        }
                                    L7:
                                        com.android.server.pm.MovePackageHelper r1 = r0.f$0
                                        java.util.concurrent.CountDownLatch r2 = r2
                                        long r3 = r3
                                        java.io.File r5 = r5
                                        long r6 = r6
                                        int r8 = r8
                                        r1.getClass()
                                        java.security.SecureRandom r9 = new java.security.SecureRandom
                                        r9.<init>()
                                        r10 = 0
                                        r0 = r10
                                    L1d:
                                        java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> L28
                                        r12 = 1
                                        boolean r11 = r2.await(r12, r11)     // Catch: java.lang.InterruptedException -> L28
                                        if (r11 == 0) goto L28
                                        return
                                    L28:
                                        long r11 = r5.getFreeSpace()
                                        long r11 = r3 - r11
                                        r13 = 0
                                        int r13 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
                                        r14 = 10
                                        if (r13 == 0) goto L46
                                        r15 = 80
                                        long r11 = r11 * r15
                                        long r15 = r11 / r6
                                        r17 = 0
                                        r19 = 80
                                        long r11 = android.util.MathUtils.constrain(r15, r17, r19)
                                        int r11 = (int) r11
                                        int r11 = r11 + r14
                                        goto L48
                                    L46:
                                        r11 = 100
                                    L48:
                                        if (r0 < r11) goto L56
                                        r11 = 90
                                        if (r0 >= r11) goto L53
                                        int r11 = r9.nextInt(r14)
                                        goto L54
                                    L53:
                                        r11 = r10
                                    L54:
                                        int r0 = r0 + r11
                                        goto L57
                                    L56:
                                        r0 = r11
                                    L57:
                                        com.android.server.pm.PackageManagerService r11 = r1.mPm
                                        com.android.server.pm.MovePackageHelper$MoveCallbacks r11 = r11.mMoveCallbacks
                                        r11.notifyStatusChanged(r8, r0)
                                        goto L1d
                                    L5f:
                                        com.android.server.pm.MovePackageHelper r1 = r0.f$0
                                        java.util.concurrent.CountDownLatch r2 = r2
                                        long r3 = r3
                                        java.io.File r5 = r5
                                        long r6 = r6
                                        int r0 = r8
                                        r1.getClass()
                                        java.security.SecureRandom r8 = new java.security.SecureRandom
                                        r8.<init>()
                                        r9 = 0
                                        r10 = r9
                                    L75:
                                        java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> L80
                                        r12 = 1
                                        boolean r11 = r2.await(r12, r11)     // Catch: java.lang.InterruptedException -> L80
                                        if (r11 == 0) goto L80
                                        return
                                    L80:
                                        long r11 = r5.getUsableSpace()
                                        long r11 = r3 - r11
                                        r13 = 80
                                        long r11 = r11 * r13
                                        long r13 = r11 / r6
                                        r15 = 0
                                        r17 = 80
                                        long r11 = android.util.MathUtils.constrain(r13, r15, r17)
                                        int r11 = (int) r11
                                        int r11 = r11 + 10
                                        if (r10 < r11) goto La5
                                        r11 = 90
                                        if (r10 >= r11) goto La2
                                        r11 = 5
                                        int r11 = r8.nextInt(r11)
                                        goto La3
                                    La2:
                                        r11 = r9
                                    La3:
                                        int r10 = r10 + r11
                                        goto La6
                                    La5:
                                        r10 = r11
                                    La6:
                                        com.android.server.pm.PackageManagerService r11 = r1.mPm
                                        com.android.server.pm.MovePackageHelper$MoveCallbacks r11 = r11.mMoveCallbacks
                                        r11.notifyStatusChanged(r0, r10)
                                        goto L75
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0.run():void");
                                }
                            }).start();
                            moveInfo = null;
                        }
                        int i10 = i3 | 2;
                        OriginInfo originInfo = new OriginInfo(file3, false, true, null);
                        ParseResult parsePackageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing(), new File(originInfo.mResolvedPath), 0);
                        PackageLite packageLite = parsePackageLite.isSuccess() ? (PackageLite) parsePackageLite.getResult() : null;
                        PackageManagerService packageManagerService = this.mPm;
                        InstallingSession installingSession = new InstallingSession(originInfo, moveInfo, r29, i10, installSource, str2, of, str4, packageLite, packageManagerService);
                        installingSession.mIsMoveRequest = true;
                        installingSession.mTraceMethod = "movePackage";
                        installingSession.mTraceCookie = System.identityHashCode(installingSession);
                        Trace.asyncTraceBegin(262144L, "movePackage", System.identityHashCode(installingSession));
                        Trace.asyncTraceBegin(262144L, "queueInstall", System.identityHashCode(installingSession));
                        packageManagerService.mHandler.post(new InstallingSession$$ExternalSyntheticLambda0(installingSession, 0));
                    } finally {
                    }
                } catch (Throwable th) {
                    th = th;
                    boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                boolean z52 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }
}
