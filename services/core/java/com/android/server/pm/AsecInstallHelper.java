package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.SigningDetails;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.security.SystemKeyStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;

import libcore.util.EmptyArray;

import com.samsung.android.core.pm.containerservice.IContainerService;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class AsecInstallHelper {
    public static Context mContext;
    public final BroadcastHelper mBroadcastHelper;
    public final DeletePackageHelper mDeletePackageHelper;
    public final PackageManagerServiceInjector mInjector;
    public final InstallPackageHelper mInstallPackageHelper;
    public final PackageInstallerService mInstallerService;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public final PackageManagerService mPm;
    public final UserManagerService mUserManager;
    public static final String ASEC_INTERNAL_PATH =
            new File(Environment.getDataDirectory(), "app-asec").getPath();
    public static boolean sPreMounted = false;
    public static final long UNBIND_DELAY = TimeUnit.MINUTES.toMillis(5);
    public static final ComponentName CONTAINER_COMPONENT =
            new ComponentName(
                    "com.samsung.android.container",
                    "com.samsung.android.container.ContainerService");
    public final Object mAvailableMountLock = new Object();
    public CountDownLatch mAvailableMountLatch = null;
    public boolean mMediaMounted = false;

    /* renamed from: com.android.server.pm.AsecInstallHelper$3, reason: invalid class name */
    public final class AnonymousClass3 extends IIntentReceiver.Stub {
        public final /* synthetic */ Set val$keys;
        public final /* synthetic */ boolean val$reportStatus;

        public AnonymousClass3(boolean z, Set set) {
            this.val$reportStatus = z;
            this.val$keys = set;
        }

        public final void performReceive(
                Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            Log.i("PackageManager", "do force gc after sending broadcast: " + intent);
            final boolean z3 = this.val$reportStatus;
            final Set set = this.val$keys;
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.pm.AsecInstallHelper$3$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AsecInstallHelper.AnonymousClass3 anonymousClass3 =
                                            AsecInstallHelper.AnonymousClass3.this;
                                    boolean z4 = z3;
                                    Set set2 = set;
                                    anonymousClass3.getClass();
                                    Runtime.getRuntime().gc();
                                    AsecInstallHelper.this.updateMediaStatus(z4 ? 1 : 0, set2);
                                    AsecInstallHelper.this.setAvailableMountSync(false);
                                    Log.i("PackageManager", "setAvailableMountSync false");
                                }
                            })
                    .start();
        }
    }

    public final class MyServiceConnection implements ServiceConnection {
        public static MyServiceConnection sInstance;
        public boolean mBinding;
        public IContainerService mContainerService;
        public Runnable mUnbindingRunnable;

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                this.mContainerService =
                        IContainerService.Stub.asInterface(Binder.allowBlocking(iBinder));
                this.mBinding = false;
                notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (this) {
                this.mBinding = false;
                this.mContainerService = null;
            }
        }

        public final void scheduleAutoUnbound(final Context context, Handler handler) {
            if (this.mUnbindingRunnable == null) {
                Runnable runnable =
                        new Runnable() { // from class:
                                         // com.android.server.pm.AsecInstallHelper.MyServiceConnection.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                if (MyServiceConnection.sInstance != null) {
                                    synchronized (this) {
                                        context.unbindService(MyServiceConnection.sInstance);
                                        MyServiceConnection myServiceConnection =
                                                MyServiceConnection.this;
                                        myServiceConnection.mContainerService = null;
                                        myServiceConnection.mBinding = false;
                                        myServiceConnection.mUnbindingRunnable = null;
                                    }
                                }
                            }
                        };
                this.mUnbindingRunnable = runnable;
                handler.postDelayed(runnable, AsecInstallHelper.UNBIND_DELAY);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateExternalMediaStatusInner, reason: not valid java name */
    public static void m752$$Nest$mupdateExternalMediaStatusInner(
            AsecInstallHelper asecInstallHelper, boolean z, boolean z2) {
        asecInstallHelper.getClass();
        ArrayMap arrayMap = new ArrayMap();
        int[] iArr = EmptyArray.INT;
        String[] secureContainerList = PackageHelperExt.getSecureContainerList();
        if (ArrayUtils.isEmpty(secureContainerList)) {
            Log.i("PackageManager", "No secure containers found");
        } else {
            PackageManagerTracedLock packageManagerTracedLock = asecInstallHelper.mPm.mLock;
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    Iterator it = new ArraySet(secureContainerList).iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (!PackageInstallerService.isStageName(str)) {
                            String asecPackageName = getAsecPackageName(str);
                            if (asecPackageName == null) {
                                Slog.i(
                                        "PackageManager",
                                        "Found stale container " + str + " with no package name");
                            } else {
                                PackageSetting packageSetting =
                                        (PackageSetting)
                                                asecInstallHelper.mPm.mSettings.mPackages.mStorage
                                                        .get(asecPackageName);
                                if (packageSetting == null) {
                                    Slog.i(
                                            "PackageManager",
                                            "Found stale container "
                                                    + str
                                                    + " with no matching settings");
                                } else if (z || (packageSetting.mPkgFlags & 262144) != 0) {
                                    Log.i(
                                            "PackageManager",
                                            "isMounted: " + z + ", PreMounted: " + sPreMounted);
                                    boolean z4 = sPreMounted;
                                    if (z != z4 && !z2 && !z4) {
                                        Log.i("PackageManager", "Already unmounted!! " + z);
                                        boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                                        return;
                                    }
                                    InstructionSets.getAppDexInstructionSets(
                                            packageSetting.getPrimaryCpuAbi(),
                                            packageSetting.getSecondaryCpuAbi());
                                    PackageManagerService packageManagerService =
                                            asecInstallHelper.mPm;
                                    int i =
                                            PackageHelperExt.getSdFilesystem(str)
                                                                    .startsWith(ASEC_INTERNAL_PATH)
                                                            ^ true
                                                    ? 8
                                                    : 0;
                                    InstallSource installSource = InstallSource.EMPTY;
                                    SigningDetails signingDetails = SigningDetails.UNKNOWN;
                                    AsecInstallArgs asecInstallArgs =
                                            new AsecInstallArgs(
                                                    i, installSource, packageManagerService);
                                    asecInstallArgs.cid = str;
                                    try {
                                        asecInstallArgs.setMountPath(
                                                PackageHelperExt.getSdDir(str));
                                    } catch (NullPointerException unused) {
                                        Slog.i("PackageManager", "Catch nullpointer exception");
                                    }
                                    String str2 = packageSetting.mPathString;
                                    if (str2 == null
                                            || !str2.startsWith(asecInstallArgs.packagePath)) {
                                        Slog.i(
                                                "PackageManager",
                                                "Found stale container "
                                                        + str
                                                        + ": expected codePath="
                                                        + packageSetting.mPathString);
                                    } else {
                                        arrayMap.put(asecInstallArgs, packageSetting.mPathString);
                                        int i2 = packageSetting.mAppId;
                                        if (i2 != -1) {
                                            iArr = ArrayUtils.appendInt(iArr, i2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                    Arrays.sort(iArr);
                } finally {
                    boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                }
            }
        }
        if (z) {
            asecInstallHelper.loadMediaPackages(arrayMap);
            PackageInstallerService packageInstallerService = asecInstallHelper.mInstallerService;
            synchronized (packageInstallerService.mSessions) {
                try {
                    ArraySet arraySet = new ArraySet();
                    for (String str3 : PackageHelperExt.getSecureContainerList()) {
                        if (PackageInstallerService.isStageName(str3)) {
                            arraySet.add(str3);
                        }
                    }
                    for (int i3 = 0; i3 < packageInstallerService.mSessions.size(); i3++) {
                        String str4 =
                                ((PackageInstallerSession)
                                                packageInstallerService.mSessions.valueAt(i3))
                                        .stageCid;
                        if (arraySet.remove(str4)) {
                            PackageHelperExt.mountSdDir(str4, getEncryptKey(), 1000);
                        }
                    }
                    Iterator it2 = arraySet.iterator();
                    while (it2.hasNext()) {
                        String str5 = (String) it2.next();
                        Slog.i("PackageInstaller", "Deleting orphan container " + str5);
                        PackageHelperExt.destroySdDir(str5);
                    }
                } finally {
                }
            }
        } else {
            asecInstallHelper.unloadMediaPackages(arrayMap, z2);
        }
        if (!PMRune.PM_INSTALL_TO_SDCARD || z) {
            return;
        }
        Map map = MovePackageHelper.sMoveIdMapForSd;
        synchronized (map) {
            try {
                ArrayList arrayList = MovePackageHelper.sPendingMoves;
                if (arrayList.size() > 0) {
                    asecInstallHelper.mPm.mMoveCallbacks.notifyStatusChanged(
                            ((MovePackageHelper.SdcardParams) arrayList.get(0)).moveId, -1);
                }
                ((HashMap) map).clear();
                arrayList.clear();
            } finally {
            }
        }
    }

    public AsecInstallHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        this.mInjector = packageManagerServiceInjector;
        mContext = packageManagerServiceInjector.mContext;
        this.mInstallerService = packageManagerServiceInjector.getPackageInstallerService();
        AppDataHelper appDataHelper = new AppDataHelper(packageManagerService);
        PackageManagerServiceInjector packageManagerServiceInjector2 =
                packageManagerService.mInjector;
        BroadcastHelper broadcastHelper = new BroadcastHelper(packageManagerServiceInjector2);
        this.mBroadcastHelper = broadcastHelper;
        RemovePackageHelper removePackageHelper =
                new RemovePackageHelper(packageManagerService, appDataHelper, broadcastHelper);
        DeletePackageHelper deletePackageHelper =
                new DeletePackageHelper(
                        packageManagerService, removePackageHelper, broadcastHelper);
        this.mDeletePackageHelper = deletePackageHelper;
        this.mInstallPackageHelper =
                new InstallPackageHelper(
                        packageManagerService,
                        appDataHelper,
                        removePackageHelper,
                        deletePackageHelper,
                        broadcastHelper);
        this.mUserManager = packageManagerServiceInjector2.getUserManagerService();
        this.mPermissionManager =
                (PermissionManagerService.PermissionManagerServiceInternalImpl)
                        packageManagerServiceInjector2.mPermissionManagerServiceProducer.get(
                                packageManagerServiceInjector2.mPackageManager,
                                packageManagerServiceInjector2);
    }

    public static boolean canInstallOnExternal(int i, int i2, String str) {
        if (i2 == 0 && "com.android.vending".equals(str)) {
            int i3 =
                    Settings.System.getInt(
                            mContext.getContentResolver(), "installToSdCardState", -1);
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i3, "checkSettingsForDirectSdInstall value : ", "PackageManager");
            if (i3 == 1) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    StorageVolume[] volumeList = PackageHelperExt.getVolumeList(i2, "android", 0);
                    if (volumeList != null) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= volumeList.length) {
                                break;
                            }
                            StorageVolume storageVolume = volumeList[i4];
                            if (!"sd".equals(storageVolume.getSubSystem())
                                    || !storageVolume.isRemovable()
                                    || storageVolume.getState() != "mounted") {
                                i4++;
                            } else if (i == 0 || i == 2) {
                                return true;
                            }
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return false;
    }

    public static void doRenameAsec(InstallRequest installRequest, ParsedPackage parsedPackage) {
        String sb;
        String sdDir;
        int i = installRequest.mReturnCode;
        String str = installRequest.mReturnMsg;
        if (i != 1) {
            throw new PrepareFailure(i, str);
        }
        String packageName = parsedPackage.getPackageName();
        SecureRandom secureRandom = new SecureRandom();
        do {
            StringBuilder sb2 = new StringBuilder(16);
            for (int i2 = 0; i2 < 16; i2++) {
                int nextFloat = (int) (secureRandom.nextFloat() * 61.0f);
                if (nextFloat < 10) {
                    sb2.append((char) (nextFloat + 48));
                } else if (nextFloat < 36) {
                    sb2.append((char) (nextFloat + 55));
                } else {
                    sb2.append((char) (nextFloat + 61));
                }
            }
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            packageName, PackageManagerShellCommandDataLoader.STDIN_PATH);
            m.append(sb2.toString());
            sb = m.toString();
        } while (new File("/mnt/asec", sb).exists());
        Slog.i("PackageManager", "nextCodePath for ASEC: " + sb);
        String extractCidFromCodePath = extractCidFromCodePath(installRequest.getCodePath());
        if (!PackageHelperExt.isContainerMounted(extractCidFromCodePath)
                || PackageHelperExt.unMountSdDir(extractCidFromCodePath, true)) {
            if (!PackageHelperExt.renameSdDir(extractCidFromCodePath, sb)) {
                Slog.e(
                        "PackageManager",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Failed to rename ",
                                extractCidFromCodePath,
                                " to ",
                                sb,
                                " which might be stale. Will try to clean up"));
                if (!PackageHelperExt.destroySdDir(sb)) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Very strange. Cannot clean up stale container ", sb, "PackageManager");
                } else if (!PackageHelperExt.renameSdDir(extractCidFromCodePath, sb)) {
                    Slog.e(
                            "PackageManager",
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Failed to rename ",
                                    extractCidFromCodePath,
                                    " to ",
                                    sb,
                                    " inspite of cleaning it up"));
                }
            }
            if (PackageHelperExt.isContainerMounted(sb)) {
                sdDir = PackageHelperExt.getSdDir(sb);
            } else {
                Slog.i("PackageManager", "Mounting container " + sb);
                sdDir = PackageHelperExt.mountSdDir(sb, getEncryptKey(), 1000);
            }
            if (sdDir != null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                "Successfully renamed ",
                                extractCidFromCodePath,
                                " to ",
                                sb,
                                " at new path: "),
                        sdDir,
                        "PackageManager");
                if (PackageHelperExt.fixSdPermissions(sb, -1, (String) null)) {
                    File codeFile = installRequest.getCodeFile();
                    setMountPath(sdDir, installRequest);
                    File codeFile2 = installRequest.getCodeFile();
                    parsedPackage.setPath(codeFile2.getAbsolutePath());
                    parsedPackage.setBaseApkPath(
                            FileUtils.rewriteAfterRename(
                                    codeFile, codeFile2, parsedPackage.getBaseApkPath()));
                    parsedPackage.setSplitCodePaths(
                            FileUtils.rewriteAfterRename(
                                    codeFile, codeFile2, parsedPackage.getSplitCodePaths()));
                    return;
                }
                String str2 = "Failed to fixPerms " + sb;
                Slog.e("PackageManager", str2);
                PackageHelperExt.destroySdDir(sb);
                installRequest.setError(-18, str2);
            } else {
                Slog.i("PackageManager", "Failed to get cache path for  " + sb);
            }
        } else {
            BootReceiver$$ExternalSyntheticOutline0.m58m(
                    "Failed to unmount ",
                    extractCidFromCodePath,
                    " before renaming",
                    "PackageManager");
        }
        throw new PrepareFailure(-4, "Failed rename");
    }

    public static String extractCidFromCodePath(String str) {
        if (str != null) {
            return str.substring(str.lastIndexOf("/") + 1, str.length());
        }
        return null;
    }

    public static String getAsecPackageName(String str) {
        int lastIndexOf = str.lastIndexOf(PackageManagerShellCommandDataLoader.STDIN_PATH);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public static String getEncryptKey() {
        try {
            String retrieveKeyHexString =
                    SystemKeyStore.getInstance().retrieveKeyHexString("AppsOnSD");
            if (retrieveKeyHexString != null
                    || (retrieveKeyHexString =
                                    SystemKeyStore.getInstance()
                                            .generateNewKeyHexString(128, "AES", "AppsOnSD"))
                            != null) {
                return retrieveKeyHexString;
            }
            Slog.e("PackageManager", "Failed to create encryption keys");
            return null;
        } catch (IOException e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Failed to retrieve encryption keys with exception: ", e, "PackageManager");
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Slog.e("PackageManager", "Failed to create encryption keys with exception: " + e2);
            return null;
        }
    }

    public static boolean installOnExternalAsec(int i) {
        return (i & 16) == 0 && (i & 8) != 0;
    }

    public static boolean isExternalAsec(AndroidPackage androidPackage) {
        return androidPackage != null
                && androidPackage.isExternalStorage()
                && TextUtils.isEmpty(androidPackage.getVolumeUuid());
    }

    public static void setMountPath(String str, InstallRequest installRequest) {
        File file = new File(str);
        File file2 = new File(file, "pkg.apk");
        if (file2.exists()) {
            installRequest.setCodeFile(file2);
        } else {
            installRequest.setCodeFile(file);
        }
    }

    public final void cleanUpAsecResources(File file, String[] strArr) {
        String absolutePath = file.getAbsolutePath();
        InstallSource installSource = InstallSource.EMPTY;
        SigningDetails signingDetails = SigningDetails.UNKNOWN;
        AsecInstallArgs asecInstallArgs = new AsecInstallArgs(8, installSource, this.mPm);
        if (!absolutePath.endsWith("pkg.apk")) {
            absolutePath = new File(absolutePath, "pkg.apk").getAbsolutePath();
        }
        int lastIndexOf = absolutePath.lastIndexOf("/");
        String substring = absolutePath.substring(0, lastIndexOf);
        asecInstallArgs.cid = substring.substring(substring.lastIndexOf("/") + 1, lastIndexOf);
        asecInstallArgs.setMountPath(substring);
        asecInstallArgs.pendingPostDeleteLI(0, true);
    }

    public final VolumeInfo getMountedExternalVolume() {
        DiskInfo disk;
        for (VolumeInfo volumeInfo :
                ((StorageManager)
                                this.mInjector.mGetSystemServiceProducer.produce(
                                        StorageManager.class))
                        .getVolumes()) {
            if (volumeInfo.getType() == 0
                    && (disk = volumeInfo.getDisk()) != null
                    && disk.isSd()
                    && volumeInfo.state == 2) {
                return volumeInfo;
            }
        }
        return null;
    }

    public final void loadMediaPackages(ArrayMap arrayMap) {
        String str;
        StringBuilder sb;
        String str2;
        AndroidPackage androidPackage;
        ArrayList arrayList = new ArrayList();
        Set keySet = arrayMap.keySet();
        try {
            synchronized (this.mAvailableMountLock) {
                try {
                    if (this.mAvailableMountLatch != null) {
                        Log.i("PackageManager", "checkAvailableMount Waiting Latch");
                        if (this.mAvailableMountLatch.await(240L, TimeUnit.SECONDS)) {
                            Log.i("PackageManager", "checkAvailableMount Wake Latch");
                            this.mAvailableMountLatch = null;
                        } else {
                            Log.i("PackageManager", "checkAvailableMount still wait......");
                        }
                    }
                } finally {
                }
            }
        } catch (InterruptedException unused) {
            Log.i("PackageManager", "checkAvailableMount Latch Exception");
        }
        Iterator it = keySet.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        Settings.VersionInfo versionInfo =
                                (Settings.VersionInfo)
                                        this.mPm.mSettings.mVersion.mStorage.get(
                                                "primary_physical");
                        if (versionInfo.sdkVersion != this.mPm.mSdkVersion) {
                            PackageManagerServiceUtils.logCriticalInfo(
                                    4,
                                    "Platform changed from "
                                            + versionInfo.sdkVersion
                                            + " to "
                                            + this.mPm.mSdkVersion
                                            + "; regranting permissions for external");
                        }
                        PermissionManagerService.PermissionManagerServiceInternalImpl
                                permissionManagerServiceInternalImpl = this.mPermissionManager;
                        if (versionInfo.sdkVersion == this.mPm.mSdkVersion) {
                            z = false;
                        }
                        PermissionManagerService.this.mPermissionManagerServiceImpl
                                .onStorageVolumeMounted("primary_physical", z);
                        versionInfo.forceCurrent();
                        this.mPm.mSettings.writeLPr(this.mPm.snapshotComputer(), false);
                    } catch (Throwable th) {
                        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                if (arrayList.size() > 0) {
                    this.mBroadcastHelper.sendResourcesChangedBroadcastAndNotify(
                            this.mPm.snapshotComputer(), true, false, arrayList, null);
                    return;
                }
                return;
            }
            AsecInstallArgs asecInstallArgs = (AsecInstallArgs) it.next();
            String str3 = (String) arrayMap.get(asecInstallArgs);
            int i = -18;
            try {
                try {
                    str2 = asecInstallArgs.cid;
                } catch (Throwable th2) {
                    if (i != 1) {
                        Log.i(
                                "PackageManager",
                                "Container " + asecInstallArgs.cid + " is stale, retCode=" + i);
                    }
                    throw th2;
                }
            } catch (RuntimeException unused2) {
                String str4 = "loadMediaPackage Exception: " + asecInstallArgs.cid;
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, str4);
                if (i != 1) {
                    str = "PackageManager";
                    sb = new StringBuilder("Container ");
                }
            }
            if (!PackageHelperExt.isContainerMounted(str2)) {
                String mountSdDir = PackageHelperExt.mountSdDir(str2, getEncryptKey(), 1000);
                if (mountSdDir != null) {
                    asecInstallArgs.setMountPath(mountSdDir);
                } else {
                    Slog.e(
                            "PackageManager",
                            "Failed to mount cid: "
                                    + asecInstallArgs.cid
                                    + " when installing from sdcard");
                    str = "PackageManager";
                    sb = new StringBuilder("Container ");
                    sb.append(asecInstallArgs.cid);
                    sb.append(" is stale, retCode=-18");
                    Log.i(str, sb.toString());
                }
            }
            if (str3 != null && str3.startsWith(asecInstallArgs.packagePath)) {
                PackageManagerService packageManagerService = this.mPm;
                int i2 = packageManagerService.mDefParseFlags;
                if ((asecInstallArgs.mInstallFlags & 8) != 0) {
                    i2 |= 8;
                }
                PackageManagerTracedLock packageManagerTracedLock2 =
                        packageManagerService.mInstallLock;
                boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock2) {
                    try {
                        try {
                            androidPackage =
                                    this.mInstallPackageHelper.initPackageTracedLI(
                                            new File(str3), i2, 2048);
                        } catch (Throwable th3) {
                            th = th3;
                            boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                            throw th;
                        }
                    } catch (PackageManagerException e) {
                        Slog.i("PackageManager", "Failed to scan " + str3 + ": " + e.getMessage());
                        androidPackage = null;
                    }
                    if (androidPackage != null) {
                        PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                        boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock3) {
                            try {
                                arrayList.add(androidPackage);
                                int uid = androidPackage.getUid();
                                String str5 = asecInstallArgs.cid;
                                if (uid >= 10000
                                        && PackageHelperExt.fixSdPermissions(
                                                str5, -1, (String) null)) {
                                    if (!PackageHelperExt.isContainerMounted(str5)) {
                                        PackageHelperExt.mountSdDir(
                                                str5, getEncryptKey(), Process.myUid());
                                    }
                                }
                                Slog.e("PackageManager", "Failed to finalize " + str5);
                                PackageHelperExt.destroySdDir(str5);
                            } catch (Throwable th4) {
                                try {
                                    boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                                    throw th4;
                                } catch (Throwable th5) {
                                    th = th5;
                                    i = 1;
                                    boolean z62 = PackageManagerService.DEBUG_COMPRESSION;
                                    throw th;
                                }
                            }
                        }
                        i = 1;
                    } else {
                        Slog.i(
                                "PackageManager",
                                "Failed to install pkg from  " + str3 + " from sdcard");
                    }
                }
                boolean z9 = PackageManagerService.DEBUG_COMPRESSION;
                if (i != 1) {
                    str = "PackageManager";
                    sb = new StringBuilder("Container ");
                    sb.append(asecInstallArgs.cid);
                    sb.append(" is stale, retCode=");
                    sb.append(i);
                    Log.i(str, sb.toString());
                }
            }
            Slog.e(
                    "PackageManager",
                    "Container "
                            + asecInstallArgs.cid
                            + " cachepath "
                            + asecInstallArgs.packagePath
                            + " does not match one in settings "
                            + str3);
            str = "PackageManager";
            sb = new StringBuilder("Container ");
            sb.append(asecInstallArgs.cid);
            sb.append(" is stale, retCode=-18");
            Log.i(str, sb.toString());
        }
    }

    public final void setAvailableMountSync(boolean z) {
        if (!z) {
            if (this.mAvailableMountLatch != null) {
                Log.i("PackageManager", "setAvailableMountSync Latch CountDown");
                this.mAvailableMountLatch.countDown();
                return;
            }
            return;
        }
        synchronized (this.mAvailableMountLock) {
            try {
                if (this.mAvailableMountLatch == null) {
                    Log.i("PackageManager", "setAvailableMountSync Create Latch");
                    this.mAvailableMountLatch = new CountDownLatch(1);
                } else {
                    Log.i("PackageManager", "setAvailableMountSync Unknown Latch");
                }
            } finally {
            }
        }
    }

    public final void unloadAllContainers(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            AsecInstallArgs asecInstallArgs = (AsecInstallArgs) it.next();
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mInstallLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    asecInstallArgs.pendingPostDeleteLI(0, false);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
    }

    public final void unloadMediaPackages(ArrayMap arrayMap, boolean z) {
        PackageManagerTracedLock packageManagerTracedLock;
        PackageFreezer freezePackageForDelete;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Set<AsecInstallArgs> keySet = arrayMap.keySet();
        for (AsecInstallArgs asecInstallArgs : keySet) {
            String asecPackageName = getAsecPackageName(asecInstallArgs.cid);
            AndroidPackageInternal androidPackageInternal =
                    this.mPm.mSettings.getPackageLPr(asecPackageName).pkg;
            PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo();
            PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mInstallLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock2) {
                try {
                    freezePackageForDelete =
                            this.mPm.freezePackageForDelete(
                                    -1, 1, asecPackageName, "unloadMediaPackages");
                    try {
                        packageManagerTracedLock = packageManagerTracedLock2;
                    } catch (Throwable th) {
                        th = th;
                        packageManagerTracedLock = packageManagerTracedLock2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    packageManagerTracedLock = packageManagerTracedLock2;
                }
                try {
                    boolean deletePackageLIF =
                            this.mDeletePackageHelper.deletePackageLIF(
                                    asecPackageName,
                                    null,
                                    false,
                                    this.mUserManager.getUserIds(),
                                    1,
                                    packageRemovedInfo,
                                    false);
                    try {
                        freezePackageForDelete.close();
                        if (deletePackageLIF) {
                            arrayList.add(androidPackageInternal);
                            arrayList2.add(asecInstallArgs.packagePath);
                        } else {
                            Slog.e(
                                    "PackageManager",
                                    "Failed to delete pkg from sdcard: " + asecPackageName);
                        }
                        Log.i(
                                "PackageManager",
                                "!@Remove package from AttributeCache: " + asecPackageName);
                        AttributeCache.instance().removePackage(asecPackageName);
                    } catch (Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    Throwable th5 = th;
                    try {
                        freezePackageForDelete.close();
                        throw th5;
                    } catch (Throwable th6) {
                        th5.addSuppressed(th6);
                        throw th5;
                    }
                }
            }
        }
        PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock3) {
            try {
                this.mPm.mSettings.writeLPr(this.mPm.snapshotComputer(), false);
            } finally {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        if (arrayList.size() <= 0) {
            updateMediaStatus(z ? 1 : 0, keySet);
            return;
        }
        setAvailableMountSync(true);
        Log.i("PackageManager", "setAvailableMountSync true");
        this.mBroadcastHelper.sendResourcesChangedBroadcastAndNotify(
                this.mPm.snapshotComputer(),
                false,
                false,
                arrayList,
                new AnonymousClass3(z, keySet));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ResourcesManager.getInstance().invalidatePath((String) it.next());
        }
        Runtime.getRuntime().gc();
        System.runFinalization();
        Log.i("PackageManager", "Flush ResourceManager path: " + arrayList2);
    }

    public final void updateExternalMediaStatus(final boolean z, final boolean z2) {
        PackageManagerServiceUtils.enforceSystemOrRoot(
                "Media status can only be updated by the system");
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                StringBuilder sb = new StringBuilder("Updating external media status from ");
                sb.append(this.mMediaMounted ? "mounted" : "unmounted");
                sb.append(" to ");
                sb.append(z ? "mounted" : "unmounted");
                Log.i("PackageManager", sb.toString());
                if (z == this.mMediaMounted) {
                    this.mPm.mHandler.sendMessage(
                            this.mPm.mHandler.obtainMessage(12, z2 ? 1 : 0, -1));
                } else {
                    this.mMediaMounted = z;
                    this.mPm.mHandler.post(
                            new Runnable() { // from class:
                                // com.android.server.pm.AsecInstallHelper.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    try {
                                        AsecInstallHelper
                                                .m752$$Nest$mupdateExternalMediaStatusInner(
                                                        AsecInstallHelper.this, z, z2);
                                    } catch (RuntimeException e) {
                                        Log.i(
                                                "PackageManager",
                                                "updateExternalMediaStatus RuntimeException",
                                                e);
                                        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                        PackageManagerServiceUtils.logCriticalInfo(
                                                5,
                                                "updateExternalMediaStatus runtime exception: is"
                                                    + " asec cmd timeout?");
                                    }
                                }
                            });
                }
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void updateMediaStatus(int i, Set set) {
        Log.i("PackageManager", "Got message UPDATED_MEDIA_STATUS!");
        boolean z = i == 1;
        Log.i("PackageManager", "reportStatus: " + z + ", doGc: false");
        Log.i("PackageManager", "Unloading all containers");
        unloadAllContainers(set);
        if (z) {
            try {
                Log.i("PackageManager", "Invoking MountService call back");
                PackageHelperExt.getStorageManagerExt().finishMediaUpdate();
            } catch (RemoteException unused) {
                Log.e("PackageManager", "MountService not running?");
            }
        }
    }
}
