package com.android.server.pm;

import android.R;
import android.apex.ApexInfo;
import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.dex.DexMetadataHelper;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SELinux;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.security.Flags;
import android.system.ErrnoException;
import android.system.Os;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.app.ResolverActivity;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.PackageParserException;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.ArrayUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.security.FileIntegrityService;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.install.PreloadDuplicateApps;
import com.samsung.android.server.pm.monetization.MonetizationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public final class InstallPackageHelper {
    public final ApexManager mApexManager;
    public final AppDataHelper mAppDataHelper;
    public final BroadcastHelper mBroadcastHelper;
    public final Context mContext;
    public final DeletePackageHelper mDeletePackageHelper;
    public final DexManager mDexManager;
    public final IncrementalManager mIncrementalManager;
    public final PackageManagerServiceInjector mInjector;
    public final MonetizationUtils mMonetizationUtils;
    public final PackageAbiHelper mPackageAbiHelper;
    public final PackageManagerService mPm;
    public final PreloadDuplicateApps mPreloadDuplicateApps = new PreloadDuplicateApps();
    public final RemovePackageHelper mRemovePackageHelper;
    public final SharedLibrariesImpl mSharedLibraries;
    public final UpdateOwnershipHelper mUpdateOwnershipHelper;

    public InstallPackageHelper(
            PackageManagerService packageManagerService,
            AppDataHelper appDataHelper,
            RemovePackageHelper removePackageHelper,
            DeletePackageHelper deletePackageHelper,
            BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        this.mInjector = packageManagerServiceInjector;
        this.mAppDataHelper = appDataHelper;
        this.mBroadcastHelper = broadcastHelper;
        this.mRemovePackageHelper = removePackageHelper;
        this.mDeletePackageHelper = deletePackageHelper;
        this.mIncrementalManager =
                (IncrementalManager)
                        packageManagerServiceInjector.mIncrementalManagerProducer.get(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        PackageManagerServiceInjector packageManagerServiceInjector2 =
                packageManagerService.mInjector;
        this.mApexManager =
                (ApexManager)
                        packageManagerServiceInjector2.mApexManagerProducer.get(
                                packageManagerServiceInjector2.mPackageManager,
                                packageManagerServiceInjector2);
        this.mDexManager =
                (DexManager)
                        packageManagerServiceInjector2.mDexManagerProducer.get(
                                packageManagerServiceInjector2.mPackageManager,
                                packageManagerServiceInjector2);
        Context context = packageManagerServiceInjector2.mContext;
        this.mContext = context;
        this.mPackageAbiHelper = packageManagerServiceInjector2.mAbiHelper;
        this.mSharedLibraries = packageManagerServiceInjector2.getSharedLibrariesImpl();
        this.mUpdateOwnershipHelper =
                (UpdateOwnershipHelper)
                        packageManagerServiceInjector2.mUpdateOwnershipHelperProducer.get(
                                packageManagerServiceInjector2.mPackageManager,
                                packageManagerServiceInjector2);
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
    }

    public static boolean checkNoAppStorageIsConsistent(
            AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        if (androidPackage == null) {
            return true;
        }
        PackageManager.Property property =
                (PackageManager.Property)
                        androidPackage
                                .getProperties()
                                .get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        PackageManager.Property property2 =
                (PackageManager.Property)
                        androidPackage2
                                .getProperties()
                                .get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        return (property == null || !property.getBoolean())
                ? property2 == null || !property2.getBoolean()
                : property2 != null && property2.getBoolean();
    }

    public static boolean isAdminApplication(AndroidPackage androidPackage) {
        int size = androidPackage.getReceivers().size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = (ParsedActivity) androidPackage.getReceivers().get(i);
            if (parsedActivity != null
                    && parsedActivity.getPermission() != null
                    && parsedActivity.getName() != null
                    && parsedActivity
                            .getPermission()
                            .equals("android.permission.BIND_DEVICE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public static void onInstallComplete(int i, Context context, IntentSender intentSender) {
        Intent intent = new Intent();
        intent.putExtra(
                "android.content.pm.extra.STATUS", PackageManager.installStatusToPublicStatus(i));
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public static void setUpFsVerity(AndroidPackage androidPackage) {
        boolean z = PackageManagerServiceUtils.DEBUG;
        if (Flags.deprecateFsvSig()) {
            return;
        }
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 30
                || SystemProperties.getInt("ro.apk_verity.mode", 0) == 2) {
            if (!IncrementalManager.isIncrementalPath(androidPackage.getPath())
                    || IncrementalManager.getVersion() >= 2) {
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put(
                        androidPackage.getBaseApkPath(),
                        VerityUtils.getFsveritySignatureFilePath(androidPackage.getBaseApkPath()));
                String buildDexMetadataPathForApk =
                        DexMetadataHelper.buildDexMetadataPathForApk(
                                androidPackage.getBaseApkPath());
                if (BatteryService$$ExternalSyntheticOutline0.m45m(buildDexMetadataPathForApk)) {
                    arrayMap.put(
                            buildDexMetadataPathForApk,
                            VerityUtils.getFsveritySignatureFilePath(buildDexMetadataPathForApk));
                }
                for (String str : androidPackage.getSplitCodePaths()) {
                    arrayMap.put(str, VerityUtils.getFsveritySignatureFilePath(str));
                    String buildDexMetadataPathForApk2 =
                            DexMetadataHelper.buildDexMetadataPathForApk(str);
                    if (BatteryService$$ExternalSyntheticOutline0.m45m(
                            buildDexMetadataPathForApk2)) {
                        arrayMap.put(
                                buildDexMetadataPathForApk2,
                                VerityUtils.getFsveritySignatureFilePath(
                                        buildDexMetadataPathForApk2));
                    }
                }
                FileIntegrityService fileIntegrityService =
                        (FileIntegrityService) LocalServices.getService(FileIntegrityService.class);
                for (Map.Entry entry : arrayMap.entrySet()) {
                    try {
                        String str2 = (String) entry.getKey();
                        if (!VerityUtils.hasFsverity(str2)) {
                            String str3 = (String) entry.getValue();
                            if (new File(str3).exists()) {
                                VerityUtils.setUpFsverity(str2);
                                if (!fileIntegrityService.verifyPkcs7DetachedSignature(
                                        str3, str2)) {
                                    throw new PrepareFailure(
                                            -118,
                                            "fs-verity signature does not verify against a known"
                                                + " key");
                                }
                            } else {
                                continue;
                            }
                        }
                    } catch (IOException e) {
                        throw new PrepareFailure(-118, "Failed to enable fs-verity: " + e);
                    }
                }
            }
        }
    }

    public static void tryToRecover(ApexInfo apexInfo) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                new StringBuilder("!@ Unexpected exception occurred while parsing "),
                apexInfo.modulePath,
                "PackageManager");
        String[] split = apexInfo.modulePath.split("/|@");
        if (split.length > 4 && "data".equals(split[1]) && "decompressed".equals(split[3])) {
            SystemProperties.set("sys.apexd.restore.module", split[4]);
        } else if (split.length > 4 && "data".equals(split[1]) && "active".equals(split[3])) {
            SystemProperties.set("sys.apexd.restore.module", "active");
        }
        Slog.i("PackageManager", "!@ reboot by ApexManager");
        SystemProperties.set("sys.powerctl", "reboot,recoveryDecompressedApex");
    }

    public static void updateDigest(MessageDigest messageDigest, File file) {
        DigestInputStream digestInputStream =
                new DigestInputStream(new FileInputStream(file), messageDigest);
        do {
            try {
            } catch (Throwable th) {
                try {
                    digestInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } while (digestInputStream.read() != -1);
        digestInputStream.close();
    }

    public final AndroidPackage addForInitLI(
            ParsedPackage parsedPackage,
            int i,
            int i2,
            UserHandle userHandle,
            ApexManager.ActiveApexInfo activeApexInfo) {
        PackageSetting disabledSystemPkgLPr;
        String str;
        List reconcilePackages;
        boolean z;
        PackageSetting packageSetting;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            if (activeApexInfo == null) {
                try {
                    if (parsedPackage.isStaticSharedLibrary()) {
                        PackageManagerService.renameStaticSharedLibraryPackage(parsedPackage);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            disabledSystemPkgLPr =
                    this.mPm.mSettings.getDisabledSystemPkgLPr(parsedPackage.getPackageName());
            if (activeApexInfo != null && disabledSystemPkgLPr != null) {
                String str2 = activeApexInfo.apexModuleName;
                PackageStateUnserialized packageStateUnserialized = disabledSystemPkgLPr.pkgState;
                packageStateUnserialized.mApexModuleName = str2;
                packageStateUnserialized.mPackageSetting.onChanged$2();
            }
        }
        Pair scanSystemPackageLI = scanSystemPackageLI(parsedPackage, i, i2, userHandle);
        ScanResult scanResult = (ScanResult) scanSystemPackageLI.first;
        boolean booleanValue = ((Boolean) scanSystemPackageLI.second).booleanValue();
        InstallRequest installRequest =
                new InstallRequest(
                        parsedPackage, i, i2, userHandle, scanResult, disabledSystemPkgLPr);
        synchronized (this.mPm.mLock) {
            try {
                PackageSetting packageLPr =
                        this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
                str = packageLPr != null ? packageLPr.pkgState.mApexModuleName : null;
            } finally {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        if (activeApexInfo != null) {
            installRequest.mApexModuleName = activeApexInfo.apexModuleName;
        } else if (disabledSystemPkgLPr != null) {
            installRequest.mApexModuleName = disabledSystemPkgLPr.pkgState.mApexModuleName;
        } else if (str != null) {
            installRequest.mApexModuleName = str;
        }
        synchronized (this.mPm.mLock) {
            boolean z4 = false;
            try {
                try {
                    String str3 = scanResult.mPkgSetting.mName;
                    List singletonList = Collections.singletonList(installRequest);
                    PackageManagerService packageManagerService = this.mPm;
                    WatchedArrayMap watchedArrayMap = packageManagerService.mPackages;
                    Map singletonMap =
                            Collections.singletonMap(
                                    str3,
                                    packageManagerService.getSettingsVersionForPackage(
                                            parsedPackage));
                    SharedLibrariesImpl sharedLibrariesImpl = this.mSharedLibraries;
                    PackageManagerService packageManagerService2 = this.mPm;
                    Settings settings = packageManagerService2.mSettings;
                    reconcilePackages =
                            ReconcilePackageUtils.reconcilePackages(
                                    singletonList,
                                    watchedArrayMap,
                                    singletonMap,
                                    sharedLibrariesImpl,
                                    settings.mKeySetManagerService,
                                    settings,
                                    packageManagerService2.mInjector.getSystemConfig());
                    if ((i2 & 67108864) == 0) {
                        z = optimisticallyRegisterAppId(installRequest);
                    } else {
                        installRequest.assertScanResultExists();
                        installRequest.mScanResult.mPkgSetting.setAppId(-1);
                        z = false;
                    }
                } catch (PackageManagerException e) {
                    e = e;
                }
                try {
                    commitReconciledScanResultLocked(
                            (ReconciledPackage) ((ArrayList) reconcilePackages).get(0),
                            this.mPm.mUserManager.getUserIds());
                } catch (PackageManagerException e2) {
                    e = e2;
                    z4 = z;
                    if (z4) {
                        cleanUpAppIdCreation(installRequest);
                    }
                    throw e;
                }
            } finally {
                boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        if (booleanValue) {
            synchronized (this.mPm.mLock) {
                try {
                    this.mPm.mSettings.disableSystemPackageLPw(parsedPackage.getPackageName());
                } finally {
                }
            }
        }
        if (this.mIncrementalManager != null
                && IncrementalManager.isIncrementalPath(parsedPackage.getPath())
                && (packageSetting = scanResult.mPkgSetting) != null
                && packageSetting.isLoading()) {
            this.mIncrementalManager.registerLoadingProgressCallback(
                    parsedPackage.getPath(),
                    new IncrementalProgressListener(this.mPm, parsedPackage.getPackageName()));
        }
        if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags
                .aslInApkAppMetadataSource()) {
            PackageSetting packageSetting2 = scanResult.mPkgSetting;
            if (packageSetting2.mAppMetadataSource == 1
                    && !PackageManagerServiceUtils.extractAppMetadataFromApk(
                            parsedPackage,
                            packageSetting2.mAppMetadataFilePath,
                            packageSetting2.isSystem())) {
                synchronized (this.mPm.mLock) {
                    try {
                        PackageSetting packageSetting3 = scanResult.mPkgSetting;
                        packageSetting3.mAppMetadataFilePath = null;
                        packageSetting3.onChanged$2();
                        packageSetting3.setAppMetadataSource(0);
                    } finally {
                    }
                }
            }
        }
        return scanResult.mPkgSetting.pkg;
    }

    public final void assertOverlayIsValid(AndroidPackage androidPackage, int i, int i2) {
        PackageSetting packageLPr;
        PackageSetting packageLPr2;
        PackageSetting packageLPr3;
        if ((65536 & i2) != 0) {
            if ((i & 16) == 0) {
                if (this.mPm.mOverlayConfig.isMutable(androidPackage.getPackageName())) {
                    return;
                }
                throw PackageManagerException.ofInternalError(
                        -15,
                        "Overlay "
                                + androidPackage.getPackageName()
                                + " is static and cannot be upgraded.");
            }
            if ((524288 & i2) != 0) {
                if (androidPackage.getTargetSdkVersion()
                        < ScanPackageUtils.getVendorPartitionVersion()) {
                    Slog.w(
                            "PackageManager",
                            "System overlay "
                                    + androidPackage.getPackageName()
                                    + " targets an SDK below the required SDK level of vendor"
                                    + " overlays ("
                                    + ScanPackageUtils.getVendorPartitionVersion()
                                    + "). This will become an install error in a future release");
                    return;
                }
                return;
            }
            int targetSdkVersion = androidPackage.getTargetSdkVersion();
            int i3 = Build.VERSION.SDK_INT;
            if (targetSdkVersion < i3) {
                Slog.w(
                        "PackageManager",
                        "System overlay "
                                + androidPackage.getPackageName()
                                + " targets an SDK below the required SDK level of system overlays"
                                + " ("
                                + i3
                                + "). This will become an install error in a future release");
                return;
            }
            return;
        }
        if (androidPackage.getTargetSdkVersion() < 29) {
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    packageLPr3 = this.mPm.mSettings.getPackageLPr("android");
                } finally {
                }
            }
            if (!PackageManagerServiceUtils.comparePackageSignatures(
                    packageLPr3, androidPackage.getSigningDetails())) {
                throw PackageManagerException.ofInternalError(
                        -16,
                        "Overlay "
                                + androidPackage.getPackageName()
                                + " must target Q or later, or be signed with the platform"
                                + " certificate");
            }
        }
        if (androidPackage.getOverlayTargetOverlayableName() == null) {
            PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock2) {
                try {
                    packageLPr =
                            this.mPm.mSettings.getPackageLPr(androidPackage.getOverlayTarget());
                } finally {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                }
            }
            if (packageLPr == null
                    || PackageManagerServiceUtils.comparePackageSignatures(
                            packageLPr, androidPackage.getSigningDetails())) {
                return;
            }
            PackageManagerService packageManagerService = this.mPm;
            if (packageManagerService.mOverlayConfigSignaturePackage == null) {
                throw PackageManagerException.ofInternalError(
                        -17,
                        "Overlay "
                                + androidPackage.getPackageName()
                                + " and target "
                                + androidPackage.getOverlayTarget()
                                + " signed with different certificates, and the overlay lacks"
                                + " <overlay android:targetName>");
            }
            synchronized (packageManagerService.mLock) {
                try {
                    PackageManagerService packageManagerService2 = this.mPm;
                    packageLPr2 =
                            packageManagerService2.mSettings.getPackageLPr(
                                    packageManagerService2.mOverlayConfigSignaturePackage);
                } finally {
                }
            }
            if (PackageManagerServiceUtils.comparePackageSignatures(
                    packageLPr2, androidPackage.getSigningDetails())) {
                return;
            }
            throw PackageManagerException.ofInternalError(
                    -18,
                    "Overlay "
                            + androidPackage.getPackageName()
                            + " signed with a different certificate than both the reference package"
                            + " and target "
                            + androidPackage.getOverlayTarget()
                            + ", and the overlay lacks <overlay android:targetName>");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x01fa, code lost:

       if (r10.getLongVersionCode() > r12.versionCode) goto L113;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void assertPackageIsValid(
            com.android.server.pm.pkg.AndroidPackage r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 896
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.assertPackageIsValid(com.android.server.pm.pkg.AndroidPackage,"
                    + " int, int):void");
    }

    public final void assertPackageWithSharedUserIdIsPrivileged(AndroidPackage androidPackage) {
        PackageSetting packageLPr;
        if (AndroidPackageLegacyUtils.isPrivileged(androidPackage)
                || androidPackage.getSharedUserId() == null
                || androidPackage.isLeavingSharedUser()) {
            return;
        }
        SharedUserSetting sharedUserSetting = null;
        try {
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    sharedUserSetting =
                            this.mPm.mSettings.getSharedUserLPw(
                                    androidPackage.getSharedUserId(), false);
                } finally {
                }
            }
        } catch (PackageManagerException unused) {
        }
        if (sharedUserSetting == null || !sharedUserSetting.isPrivileged()) {
            return;
        }
        PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock2) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr("android");
            } finally {
            }
        }
        if (PackageManagerServiceUtils.comparePackageSignatures(
                packageLPr, androidPackage.getSigningDetails())) {
            return;
        }
        throw new PackageManagerException(
                "Apps that share a user with a privileged app must themselves be marked as"
                    + " privileged. "
                        + androidPackage.getPackageName()
                        + " shares privileged user "
                        + androidPackage.getSharedUserId()
                        + ".",
                -19);
    }

    public final void assertStaticSharedLibraryVersionCodeIsValid(AndroidPackage androidPackage) {
        WatchedLongSparseArray sharedLibraryInfos =
                this.mSharedLibraries.getSharedLibraryInfos(
                        androidPackage.getStaticSharedLibraryName());
        long j = Long.MIN_VALUE;
        long j2 = Long.MAX_VALUE;
        if (sharedLibraryInfos != null) {
            int size = sharedLibraryInfos.mStorage.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                SharedLibraryInfo sharedLibraryInfo =
                        (SharedLibraryInfo) sharedLibraryInfos.mStorage.valueAt(i);
                long longVersionCode = sharedLibraryInfo.getDeclaringPackage().getLongVersionCode();
                if (sharedLibraryInfo.getLongVersion()
                        >= androidPackage.getStaticSharedLibraryVersion()) {
                    if (sharedLibraryInfo.getLongVersion()
                            <= androidPackage.getStaticSharedLibraryVersion()) {
                        j = longVersionCode;
                        j2 = j;
                        break;
                    }
                    j2 = Math.min(j2, longVersionCode - 1);
                } else {
                    j = Math.max(j, longVersionCode + 1);
                }
                i++;
            }
        }
        if (androidPackage.getLongVersionCode() < j || androidPackage.getLongVersionCode() > j2) {
            throw PackageManagerException.ofInternalError(
                    -14, "Static shared lib version codes must be ordered as lib versions");
        }
    }

    public final void cleanUpAppIdCreation(InstallRequest installRequest) {
        if (installRequest.getScannedPackageSetting() == null
                || installRequest.getScannedPackageSetting().mAppId <= 0) {
            return;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.removeAppIdLPw(installRequest.getScannedPackageSetting().mAppId);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void commitPackageSettings(
            AndroidPackage androidPackage,
            PackageSetting packageSetting,
            PackageSetting packageSetting2,
            ReconciledPackage reconciledPackage) {
        boolean z;
        AndroidPackage androidPackage2;
        int i;
        PackageManagerTracedLock packageManagerTracedLock;
        String str;
        String packageName = androidPackage.getPackageName();
        InstallRequest installRequest = reconciledPackage.mInstallRequest;
        installRequest.assertScanResultExists();
        AndroidPackage androidPackage3 = installRequest.mScanResult.mRequest.mOldPkg;
        int i2 = installRequest.mScanFlags;
        boolean z2 = (installRequest.mParseFlags & Integer.MIN_VALUE) != 0;
        ComponentName componentName = this.mPm.mCustomResolverComponentName;
        if (componentName == null
                || !componentName.getPackageName().equals(androidPackage.getPackageName())) {
            z = z2;
            androidPackage2 = androidPackage3;
            i = 0;
        } else {
            PackageManagerService packageManagerService = this.mPm;
            synchronized (packageManagerService.mLock) {
                try {
                    try {
                        packageManagerService.mResolverReplaced = true;
                        str = "Replacing default ResolverActivity with custom activity: ";
                        z = z2;
                        androidPackage2 = androidPackage3;
                        i = 0;
                        ApplicationInfo generateApplicationInfo =
                                PackageInfoUtils.generateApplicationInfo(
                                        androidPackage,
                                        0L,
                                        PackageUserStateInternal.DEFAULT,
                                        0,
                                        packageSetting);
                        ActivityInfo activityInfo = packageManagerService.mResolveActivity;
                        activityInfo.applicationInfo = generateApplicationInfo;
                        activityInfo.name =
                                packageManagerService.mCustomResolverComponentName.getClassName();
                        packageManagerService.mResolveActivity.packageName =
                                androidPackage.getPackageName();
                        packageManagerService.mResolveActivity.processName =
                                androidPackage.getProcessName();
                        ActivityInfo activityInfo2 = packageManagerService.mResolveActivity;
                        activityInfo2.launchMode = 0;
                        activityInfo2.flags = 66336;
                        activityInfo2.theme = 0;
                        activityInfo2.exported = true;
                        activityInfo2.enabled = true;
                        ResolveInfo resolveInfo = packageManagerService.mResolveInfo;
                        resolveInfo.activityInfo = activityInfo2;
                        resolveInfo.priority = 0;
                        resolveInfo.preferredOrder = 0;
                        resolveInfo.match = 0;
                        packageManagerService.mResolveComponentName =
                                packageManagerService.mCustomResolverComponentName;
                        PackageManagerService.onChange();
                        Slog.i("PackageManager", str + packageManagerService.mResolveComponentName);
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        if (packageSetting2 != null
                && packageSetting2.lastUpdateTime < packageSetting.lastUpdateTime) {
            packageSetting.setAppMetadataFilePath(null);
            packageSetting.setAppMetadataSource(i);
        }
        if (packageSetting.mAppMetadataFilePath == null) {
            String path = androidPackage.getPath();
            if (packageSetting.isSystem()) {
                path =
                        Environment.getDataDirectoryPath()
                                + "/app-metadata/"
                                + androidPackage.getPackageName();
            }
            String m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            path, "/app.metadata");
            if (installRequest.mHasAppMetadataFileFromInstaller) {
                packageSetting.setAppMetadataFilePath(m$1);
                if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags
                        .aslInApkAppMetadataSource()) {
                    packageSetting.setAppMetadataSource(2);
                }
            } else if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags
                            .aslInApkAppMetadataSource()
                    && androidPackage
                            .getProperties()
                            .containsKey("android.content.PROPERTY_ANDROID_SAFETY_LABEL")) {
                packageSetting.setAppMetadataFilePath(m$1);
                packageSetting.setAppMetadataSource(1);
            }
        }
        if (androidPackage.getPackageName().equals("android")) {
            PackageManagerService packageManagerService2 = this.mPm;
            PackageManagerTracedLock packageManagerTracedLock2 = packageManagerService2.mLock;
            synchronized (packageManagerTracedLock2) {
                try {
                } catch (Throwable th3) {
                    th = th3;
                    packageManagerTracedLock = str;
                }
                try {
                    packageManagerService2.mPlatformPackage = androidPackage;
                    ApplicationInfo generateApplicationInfo2 =
                            PackageInfoUtils.generateApplicationInfo(
                                    androidPackage,
                                    0L,
                                    PackageUserStateInternal.DEFAULT,
                                    0,
                                    packageSetting);
                    packageManagerService2.mAndroidApplication = generateApplicationInfo2;
                    if (!packageManagerService2.mResolverReplaced) {
                        ActivityInfo activityInfo3 = packageManagerService2.mResolveActivity;
                        activityInfo3.applicationInfo = generateApplicationInfo2;
                        activityInfo3.name = ResolverActivity.class.getName();
                        ActivityInfo activityInfo4 = packageManagerService2.mResolveActivity;
                        activityInfo4.packageName =
                                packageManagerService2.mAndroidApplication.packageName;
                        activityInfo4.processName = "system:ui";
                        activityInfo4.launchMode = i;
                        activityInfo4.documentLaunchMode = 3;
                        activityInfo4.flags = 70176;
                        activityInfo4.theme = R.style.Theme.Material.Dialog.Alert;
                        activityInfo4.exported = true;
                        activityInfo4.enabled = true;
                        activityInfo4.resizeMode = 2;
                        activityInfo4.configChanges = 3504;
                        ResolveInfo resolveInfo2 = packageManagerService2.mResolveInfo;
                        resolveInfo2.activityInfo = activityInfo4;
                        resolveInfo2.priority = i;
                        resolveInfo2.preferredOrder = i;
                        resolveInfo2.match = i;
                        packageManagerService2.mResolveComponentName =
                                new ComponentName(
                                        packageManagerService2.mAndroidApplication.packageName,
                                        packageManagerService2.mResolveActivity.name);
                    }
                    PackageManagerService.onChange();
                } catch (Throwable th4) {
                    th = th4;
                    packageManagerTracedLock = packageManagerTracedLock2;
                    throw th;
                }
            }
            packageManagerService2.applyUpdatedSystemOverlayPaths();
        }
        SharedLibrariesImpl sharedLibrariesImpl = this.mSharedLibraries;
        List list = reconciledPackage.mAllowedSharedLibraryInfos;
        ArrayMap arrayMap =
                new ArrayMap(
                        reconciledPackage.mInstallRequests.size()
                                + reconciledPackage.mAllPackages.size());
        arrayMap.putAll(reconciledPackage.mAllPackages);
        for (InstallRequest installRequest2 : reconciledPackage.mInstallRequests) {
            arrayMap.put(
                    installRequest2.getScannedPackageSetting().mName,
                    installRequest2.mParsedPackage);
        }
        ArrayList commitSharedLibraryChanges =
                sharedLibrariesImpl.commitSharedLibraryChanges(
                        androidPackage, packageSetting, list, arrayMap, i2);
        installRequest.mLibraryConsumers = commitSharedLibraryChanges;
        if ((i2 & 16) == 0 && (i2 & 1024) == 0 && (i2 & 2048) == 0) {
            this.mPm.snapshotComputer().checkPackageFrozen(packageName);
        }
        boolean z3 = installRequest.mReplace;
        if (commitSharedLibraryChanges != null
                && (androidPackage.getStaticSharedLibraryName() == null || z3)) {
            for (int i3 = i; i3 < commitSharedLibraryChanges.size(); i3++) {
                AndroidPackage androidPackage4 =
                        (AndroidPackage) commitSharedLibraryChanges.get(i3);
                String packageName2 = androidPackage4.getPackageName();
                PackageManagerService packageManagerService3 = this.mPm;
                int uid = androidPackage4.getUid();
                packageManagerService3.getClass();
                PackageManagerService.killApplication(packageName2, uid, -1, "update lib", 12);
            }
        }
        Trace.traceBegin(262144L, "updateSettings");
        PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock3) {
            try {
                Settings settings = this.mPm.mSettings;
                settings.getClass();
                if (packageSetting.signatures.mSigningDetails.getSignatures() == null) {
                    packageSetting.signatures.mSigningDetails = androidPackage.getSigningDetails();
                    packageSetting.onChanged$2();
                }
                SharedUserSetting sharedUserSettingLPr =
                        settings.getSharedUserSettingLPr(packageSetting);
                if (sharedUserSettingLPr != null) {
                    PackageSignatures packageSignatures = sharedUserSettingLPr.signatures;
                    if (packageSignatures.mSigningDetails.getSignatures() == null) {
                        packageSignatures.mSigningDetails = androidPackage.getSigningDetails();
                    }
                }
                settings.addPackageSettingLPw(packageSetting, sharedUserSettingLPr);
                this.mPm.mPackages.put(androidPackage.getPackageName(), androidPackage);
                if ((8388608 & i2) != 0) {
                    this.mApexManager.registerApkInApex(androidPackage);
                }
                if ((this.mPm.isDeviceUpgrading() && packageSetting.isSystem()) || z3) {
                    int[] userIds = this.mPm.mUserManager.getUserIds();
                    int length = userIds.length;
                    for (int i4 = i; i4 < length; i4++) {
                        packageSetting.restoreComponentSettings(userIds[i4]);
                    }
                }
                if ((67108864 & i2) == 0) {
                    this.mPm.mSettings.mKeySetManagerService.addScannedPackageLPw(androidPackage);
                }
                Computer snapshotComputer = this.mPm.snapshotComputer();
                PackageManagerService packageManagerService4 = this.mPm;
                boolean z5 = z;
                packageManagerService4.mComponentResolver.addAllComponents(
                        androidPackage,
                        z5,
                        packageManagerService4.mSetupWizardPackage,
                        snapshotComputer);
                this.mPm.mAppsFilter.addPackage(
                        snapshotComputer, packageSetting, z3, (i2 & 1024) != 0 ? 1 : i);
                this.mPm.addAllPackageProperties(androidPackage);
                int installFlags = installRequest.getInstallFlags();
                int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
                if ((installFlags & 134217728) == 0) {
                    if (packageSetting2 != null && packageSetting2.pkg != null) {
                        ((DomainVerificationService) this.mPm.mDomainVerificationManager)
                                .migrateState(
                                        packageSetting2,
                                        packageSetting,
                                        installRequest.mPreVerifiedDomains);
                    }
                    ((DomainVerificationService) this.mPm.mDomainVerificationManager)
                            .addPackage(packageSetting, installRequest.mPreVerifiedDomains);
                }
                int size = ArrayUtils.size(androidPackage.getInstrumentations());
                StringBuilder sb = null;
                for (int i5 = i; i5 < size; i5++) {
                    ParsedInstrumentation parsedInstrumentation =
                            (ParsedInstrumentation) androidPackage.getInstrumentations().get(i5);
                    ComponentMutateUtils.setPackageName(
                            parsedInstrumentation, androidPackage.getPackageName());
                    this.mPm.mInstrumentation.put(
                            parsedInstrumentation.getComponentName(), parsedInstrumentation);
                    if (z5) {
                        if (sb == null) {
                            sb = new StringBuilder(256);
                        } else {
                            sb.append(' ');
                        }
                        sb.append(parsedInstrumentation.getName());
                    }
                }
                List protectedBroadcasts = androidPackage.getProtectedBroadcasts();
                if (!protectedBroadcasts.isEmpty()) {
                    synchronized (this.mPm.mProtectedBroadcasts) {
                        this.mPm.mProtectedBroadcasts.addAll(protectedBroadcasts);
                    }
                }
                PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageAdded(
                        packageSetting, (i2 & 8192) != 0 ? 1 : i, androidPackage2);
            } catch (Throwable th5) {
                boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                throw th5;
            }
        }
        boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
        Trace.traceEnd(262144L);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void commitPackagesLocked(java.util.List r30, int[] r31) {
        /*
            Method dump skipped, instructions count: 1946
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.commitPackagesLocked(java.util.List,"
                    + " int[]):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.pkg.AndroidPackage commitReconciledScanResultLocked(
            com.android.server.pm.ReconciledPackage r38, int[] r39) {
        /*
            Method dump skipped, instructions count: 984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.commitReconciledScanResultLocked(com.android.server.pm.ReconciledPackage,"
                    + " int[]):com.android.server.pm.pkg.AndroidPackage");
    }

    public final void disableStubPackage(
            DeletePackageAction deletePackageAction, PackageSetting packageSetting, int[] iArr) {
        PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(packageSetting.mName);
        if (packageLPr != null) {
            UserHandle userHandle = deletePackageAction.mUser;
            int identifier = userHandle == null ? -1 : userHandle.getIdentifier();
            if (identifier != -1) {
                if (identifier >= 0) {
                    packageLPr.setEnabled(2, identifier, "android");
                }
            } else {
                for (int i : iArr) {
                    packageLPr.setEnabled(2, i, "android");
                }
            }
        }
    }

    public final void doRenameLI(InstallRequest installRequest, ParsedPackage parsedPackage) {
        MoveInfo moveInfo;
        MoveInfo moveInfo2;
        MoveInfo moveInfo3;
        int i = installRequest.mReturnCode;
        String str = installRequest.mReturnMsg;
        boolean isInstallMove = installRequest.isInstallMove();
        String str2 = null;
        RemovePackageHelper removePackageHelper = this.mRemovePackageHelper;
        if (isInstallMove) {
            if (i == 1) {
                return;
            }
            InstallArgs installArgs = installRequest.mInstallArgs;
            String str3 =
                    (installArgs == null || (moveInfo3 = installArgs.mMoveInfo) == null)
                            ? null
                            : moveInfo3.mToUuid;
            String str4 =
                    (installArgs == null || (moveInfo2 = installArgs.mMoveInfo) == null)
                            ? null
                            : moveInfo2.mPackageName;
            if (installArgs != null && (moveInfo = installArgs.mMoveInfo) != null) {
                str2 = moveInfo.mFromCodePath;
            }
            removePackageHelper.cleanUpForMoveInstall(str3, str4, str2);
            throw new PrepareFailure(i, str);
        }
        if (i != 1) {
            removePackageHelper.removeCodePath(installRequest.getCodeFile());
            throw new PrepareFailure(i, str);
        }
        File dataAppDirectory =
                (installRequest.getInstallFlags() & 2097152) != 0
                        ? Environment.getDataAppDirectory(null)
                        : installRequest.getCodeFile().getParentFile();
        File codeFile = installRequest.getCodeFile();
        File nextCodePath =
                PackageManagerServiceUtils.getNextCodePath(
                        dataAppDirectory, parsedPackage.getPackageName());
        PackageManagerService packageManagerService = this.mPm;
        boolean z =
                packageManagerService.mIncrementalManager != null
                        && IncrementalManager.isIncrementalPath(codeFile.getAbsolutePath());
        try {
            PackageManagerServiceUtils.makeDirRecursive(nextCodePath.getParentFile(), 505);
            if (z) {
                packageManagerService.mIncrementalManager.linkCodePath(codeFile, nextCodePath);
            } else {
                Os.rename(codeFile.getAbsolutePath(), nextCodePath.getAbsolutePath());
            }
            if (!z && !SELinux.restoreconRecursive(nextCodePath)) {
                Slog.w("PackageManager", "Failed to restorecon");
                throw new PrepareFailure(-20, "Failed to restorecon");
            }
            installRequest.setCodeFile(nextCodePath);
            try {
                parsedPackage.setPath(nextCodePath.getCanonicalPath());
                parsedPackage.setBaseApkPath(
                        FileUtils.rewriteAfterRename(
                                codeFile, nextCodePath, parsedPackage.getBaseApkPath()));
                parsedPackage.setSplitCodePaths(
                        FileUtils.rewriteAfterRename(
                                codeFile, nextCodePath, parsedPackage.getSplitCodePaths()));
            } catch (IOException e) {
                Slog.e("PackageManager", "Failed to get path: " + nextCodePath, e);
                throw new PrepareFailure(
                        -20,
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                nextCodePath, "Failed to get path: "));
            }
        } catch (ErrnoException | IOException e2) {
            Slog.w("PackageManager", "Failed to rename", e2);
            throw new PrepareFailure(-4, "Failed to rename");
        }
    }

    public final boolean doesSignatureMatchForPermissions(
            String str, ParsedPackage parsedPackage, int i) {
        PackageSetting packageLPr;
        KeySetManagerService keySetManagerService;
        SharedUserSetting sharedUserSettingLPr;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(str);
                Settings settings = this.mPm.mSettings;
                keySetManagerService = settings.mKeySetManagerService;
                sharedUserSettingLPr = settings.getSharedUserSettingLPr(packageLPr);
            } finally {
            }
        }
        SigningDetails signingDetails =
                packageLPr == null ? SigningDetails.UNKNOWN : packageLPr.signatures.mSigningDetails;
        if (str.equals(parsedPackage.getPackageName())
                && keySetManagerService.shouldCheckUpgradeKeySetLocked(
                        packageLPr, sharedUserSettingLPr, i)) {
            return keySetManagerService.checkUpgradeKeySetLocked(packageLPr, parsedPackage);
        }
        if (signingDetails.checkCapability(parsedPackage.getSigningDetails(), 4)) {
            return true;
        }
        if (!parsedPackage.getSigningDetails().checkCapability(signingDetails, 4)) {
            return false;
        }
        synchronized (this.mPm.mLock) {
            try {
                packageLPr.signatures.mSigningDetails = parsedPackage.getSigningDetails();
                packageLPr.onChanged$2();
            } finally {
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:292:0x07c1, code lost:

       android.util.Slog.e("SPEG", "Can't release " + r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x07db, code lost:

       com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0.m("SPEG can't delete ", r3, "SPEG");
    */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x060e, code lost:

       r6 = r13;
       android.util.Slog.d("SPEG", r4.getName() + " state is on at iteration " + r5);
       r4 = r27.startActivity((android.app.IApplicationThread) null, "com.samsung.speg", r11, r31, (android.os.IBinder) null, (java.lang.String) null, 0, 0, (android.app.ProfilerInfo) null, r15.toBundle());
    */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x0648, code lost:

       if (android.app.ActivityManager.isStartResultSuccessful(r4) == false) goto L491;
    */
    /* JADX WARN: Code restructure failed: missing block: B:340:0x064a, code lost:

       java.lang.Thread.sleep(2000);
       r4 = r2.getPidOf(r7, r0.getProcessName());
    */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x0658, code lost:

       if (r4 == (-1)) goto L338;
    */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x0711, code lost:

       throw new com.android.server.pm.Installer.InstallerException("getPidOf failed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x0707, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x065a, code lost:

       android.util.Slog.d("SPEG", "Send signal to dump profiles in app, pid=" + r4);
       android.os.Process.sendSignal(r4, 10);
       r1 = r2.storePrimaryProf(r7, r0.getBaseApkPath(), r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:350:0x067d, code lost:

       r9.mFreezer = r2.mPm.freezePackage(r10, 0, "SPEG", 13, r9);
       com.android.server.pm.SpegService.waitForProcessDeath(r4);
       r12.release();
    */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x06a5, code lost:

       if (((android.hardware.display.DisplayManager) r2.mContext.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(r6) == null) goto L321;
    */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x06a7, code lost:

       android.util.Slog.e("SPEG", "Can't release " + r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x06bf, code lost:

       if (r2.createOrDeleteMarkerFiles(r7, r3, false) != false) goto L324;
    */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x06c1, code lost:

       com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0.m("SPEG can't delete ", r3, "SPEG");
    */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x06c8, code lost:

       r2.spegClearPackage(r7, r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:358:0x06cb, code lost:

       r8.spegRestrictNetworkConnection(r7, false);
    */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x06cf, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x06d0, code lost:

       android.util.Slog.e("SPEG", "Failed to restore network connection for uid " + r7, r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x0730, code lost:

       throw new com.android.server.pm.Installer.InstallerException("Failed to start " + r10 + ", res=" + r4);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void executePostCommitStepsLIF(java.util.List r41) {
        /*
            Method dump skipped, instructions count: 2516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.executePostCommitStepsLIF(java.util.List):void");
    }

    public final PackageSetting getOriginalPackageLocked(
            AndroidPackage androidPackage, String str) {
        if (androidPackage.getOriginalPackages().contains(str)) {
            return null;
        }
        for (int size = ArrayUtils.size(androidPackage.getOriginalPackages()) - 1;
                size >= 0;
                size--) {
            PackageManagerService packageManagerService = this.mPm;
            PackageSetting packageLPr =
                    packageManagerService.mSettings.getPackageLPr(
                            (String) androidPackage.getOriginalPackages().get(size));
            if (packageLPr != null) {
                if ((packageLPr.mPkgFlags & 1) == 0) {
                    Slog.w(
                            "PackageManager",
                            "Unable to update from "
                                    + packageLPr.mName
                                    + " to "
                                    + androidPackage.getPackageName()
                                    + ": old package not in system partition");
                } else {
                    if (packageManagerService.mPackages.mStorage.get(packageLPr.mName) == null) {
                        Settings settings = packageManagerService.mSettings;
                        if (settings.getSharedUserSettingLPr(packageLPr) != null) {
                            String str2 = settings.getSharedUserSettingLPr(packageLPr).name;
                            if (!str2.equals(androidPackage.getSharedUserId())) {
                                Slog.w(
                                        "PackageManager",
                                        "Unable to migrate data from "
                                                + packageLPr.mName
                                                + " to "
                                                + androidPackage.getPackageName()
                                                + ": old shared user settings name "
                                                + str2
                                                + " differs from "
                                                + androidPackage.getSharedUserId());
                            }
                        }
                        return packageLPr;
                    }
                    Slog.w(
                            "PackageManager",
                            "Unable to update from "
                                    + packageLPr.mName
                                    + " to "
                                    + androidPackage.getPackageName()
                                    + ": old package still exists");
                }
            }
        }
        return null;
    }

    public final AndroidPackage initPackageLI(File file, int i, int i2) {
        Trace.traceBegin(262144L, "parsePackage");
        try {
            try {
                PackageManagerServiceInjector packageManagerServiceInjector = this.mPm.mInjector;
                PackageParser2 packageParser2 =
                        (PackageParser2)
                                packageManagerServiceInjector.mScanningPackageParserProducer
                                        .produce(
                                                packageManagerServiceInjector.mPackageManager,
                                                packageManagerServiceInjector);
                try {
                    ParsedPackage parsePackage = packageParser2.parsePackage(file, i, false);
                    packageParser2.close();
                    Trace.traceEnd(262144L);
                    return addForInitLI(parsePackage, i, i2, new UserHandle(0), null);
                } catch (Throwable th) {
                    if (packageParser2 != null) {
                        try {
                            packageParser2.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (PackageParserException e) {
                throw new PackageManagerException(e.error, e.getMessage(), e);
            }
        } catch (Throwable th3) {
            Trace.traceEnd(262144L);
            throw th3;
        }
    }

    public final AndroidPackage initPackageTracedLI(File file, int i, int i2) {
        Trace.traceBegin(262144L, "scanPackage [" + file.toString() + "]");
        try {
            return initPackageLI(file, i, i2);
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:192:0x04a2, code lost:

       if (r4 == com.android.server.pm.PackageManagerService.sPersonaManager.getAppSeparationId()) goto L235;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Pair installExistingPackageAsUser(
            java.lang.String r28, int r29, int r30, int r31, android.content.IntentSender r32) {
        /*
            Method dump skipped, instructions count: 1241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.installExistingPackageAsUser(java.lang.String,"
                    + " int, int, int, android.content.IntentSender):android.util.Pair");
    }

    public final void installPackageFromSystemLIF(String str, int[] iArr, int[] iArr2, boolean z) {
        File file = new File(str);
        PackageManagerService packageManagerService = this.mPm;
        AndroidPackage initPackageTracedLI =
                initPackageTracedLI(
                        file,
                        packageManagerService.mDefParseFlags | 17,
                        packageManagerService.getSystemPackageScanFlags(file));
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                try {
                    this.mSharedLibraries.updateSharedLibraries(
                            initPackageTracedLI,
                            this.mPm.mSettings.getPackageLPr(initPackageTracedLI.getPackageName()),
                            null,
                            null,
                            Collections.unmodifiableMap(this.mPm.mPackages));
                } catch (PackageManagerException e) {
                    Slog.e(
                            "PackageManager",
                            "updateAllSharedLibrariesLPw failed: " + e.getMessage());
                }
            } finally {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (this.mPm.mLock) {
            try {
                PackageSetting packageLPr =
                        this.mPm.mSettings.getPackageLPr(initPackageTracedLI.getPackageName());
                boolean z5 = iArr2 != null;
                if (z5) {
                    boolean z6 = false;
                    for (int i : iArr) {
                        boolean contains = ArrayUtils.contains(iArr2, i);
                        if (contains != packageLPr.getInstalled(i)) {
                            z6 = true;
                        }
                        packageLPr.setInstalled(i, contains);
                        if (contains) {
                            packageLPr.setUninstallReason(0, i);
                        }
                    }
                    this.mPm.mSettings.writeAllUsersPackageRestrictionsLPr(false);
                    if (z6) {
                        this.mPm.mSettings.writeKernelMappingLPr(packageLPr);
                    }
                }
                this.mPm.mPermissionManager.onPackageInstalled(
                        initPackageTracedLI,
                        PermissionManagerServiceInternal$PackageInstalledParams.DEFAULT,
                        -1);
                for (int i2 : iArr) {
                    if (z5) {
                        this.mPm.mSettings.mRuntimePermissionsPersistence.writeStateForUserAsync(
                                i2);
                    }
                }
                if (z) {
                    this.mPm.writeSettingsLPrTEMP(false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
        this.mAppDataHelper.prepareAppDataAfterInstallLIF(initPackageTracedLI);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void installPackagesFromDir(
            java.io.File r23,
            int r24,
            int r25,
            com.android.internal.pm.parsing.PackageParser2 r26,
            java.util.concurrent.ExecutorService r27,
            com.android.server.pm.ApexManager.ActiveApexInfo r28) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.installPackagesFromDir(java.io.File,"
                    + " int, int, com.android.internal.pm.parsing.PackageParser2,"
                    + " java.util.concurrent.ExecutorService,"
                    + " com.android.server.pm.ApexManager$ActiveApexInfo):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void installPackagesLI(java.util.List r32) {
        /*
            Method dump skipped, instructions count: 1813
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.installPackagesLI(java.util.List):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0074, code lost:

       com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, "Failed to decompress; pkg: " + r3 + ", file: " + r13);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.pkg.AndroidPackage installStubPackageLI(
            com.android.server.pm.pkg.AndroidPackage r16, int r17, int r18) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.installStubPackageLI(com.android.server.pm.pkg.AndroidPackage,"
                    + " int, int):com.android.server.pm.pkg.AndroidPackage");
    }

    public final boolean optimisticallyRegisterAppId(InstallRequest installRequest) {
        boolean registerAppIdLPw;
        installRequest.assertScanResultExists();
        if (installRequest.mScanResult.mExistingSettingCopied) {
            installRequest.assertScanResultExists();
            installRequest.mScanResult.getClass();
            return false;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Settings settings = this.mPm.mSettings;
                PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
                installRequest.assertScanResultExists();
                installRequest.mScanResult.getClass();
                registerAppIdLPw = settings.registerAppIdLPw(scannedPackageSetting);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return registerAppIdLPw;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.ScanRequest prepareInitialScanRequest(
            com.android.internal.pm.parsing.pkg.ParsedPackage r16,
            int r17,
            int r18,
            android.os.UserHandle r19,
            java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.prepareInitialScanRequest(com.android.internal.pm.parsing.pkg.ParsedPackage,"
                    + " int, int, android.os.UserHandle,"
                    + " java.lang.String):com.android.server.pm.ScanRequest");
    }

    /* JADX WARN: Code restructure failed: missing block: B:351:0x0951, code lost:

       if (r28 != false) goto L411;
    */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x0953, code lost:

       if (r29 != false) goto L409;
    */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x095f, code lost:

       throw new com.android.server.pm.PrepareFailure(-116, "Cannot update a system app with an instant app");
    */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x0960, code lost:

       com.android.server.pm.PmHook.auditLogInstallFail(r46.getUser().getIdentifier(), r13.getPackageName(), true);
    */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x0979, code lost:

       throw new com.android.server.pm.PrepareFailure(-19, "Cannot install updates to system apps on sdcard");
    */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x0e57, code lost:

       r3 = r10;
       r5 = null;
       r9 = false;
       r10 = null;
       r11 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:718:0x0333, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:720:0x0367, code lost:

       com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0.m(r0, new java.lang.StringBuilder("RemoteException: "), "PackageManager");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void preparePackageLI(com.android.server.pm.InstallRequest r46) {
        /*
            Method dump skipped, instructions count: 3964
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.preparePackageLI(com.android.server.pm.InstallRequest):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restoreAndPostInstall(final com.android.server.pm.InstallRequest r20) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.restoreAndPostInstall(com.android.server.pm.InstallRequest):void");
    }

    public final List scanApexPackages(
            ApexInfo[] apexInfoArr,
            int i,
            int i2,
            PackageParser2 packageParser2,
            ExecutorService executorService) {
        int i3;
        int i4;
        if (apexInfoArr == null) {
            return Collections.EMPTY_LIST;
        }
        ParallelPackageParser parallelPackageParser =
                new ParallelPackageParser(packageParser2, executorService);
        final ArrayMap arrayMap = new ArrayMap();
        for (ApexInfo apexInfo : apexInfoArr) {
            File file = new File(apexInfo.modulePath);
            parallelPackageParser.mExecutorService.submit(
                    new ParallelPackageParser$$ExternalSyntheticLambda0(
                            parallelPackageParser, file, i));
            arrayMap.put(file, apexInfo);
        }
        ArrayList arrayList = new ArrayList(arrayMap.size());
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            arrayList.add(parallelPackageParser.take());
        }
        Collections.sort(
                arrayList,
                new Comparator() { // from class:
                                   // com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda5
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        ArrayMap arrayMap2 = arrayMap;
                        return Boolean.compare(
                                ((ApexInfo)
                                                arrayMap2.get(
                                                        ((ParallelPackageParser.ParseResult) obj2)
                                                                .scanFile))
                                        .isFactory,
                                ((ApexInfo)
                                                arrayMap2.get(
                                                        ((ParallelPackageParser.ParseResult) obj)
                                                                .scanFile))
                                        .isFactory);
                    }
                });
        ArrayList arrayList2 = new ArrayList(arrayMap.size());
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            ParallelPackageParser.ParseResult parseResult =
                    (ParallelPackageParser.ParseResult) arrayList.get(i6);
            Throwable th = parseResult.throwable;
            ApexInfo apexInfo2 = (ApexInfo) arrayMap.get(parseResult.scanFile);
            File file2 = parseResult.scanFile;
            PackageManagerService packageManagerService = this.mPm;
            int systemPackageScanFlags =
                    i2 | 67108864 | packageManagerService.getSystemPackageScanFlags(file2);
            if (apexInfo2.isFactory) {
                i3 = i;
                i4 = systemPackageScanFlags;
            } else {
                i4 = systemPackageScanFlags | 4;
                i3 = i & (-17);
            }
            if (th != null) {
                if (!(th instanceof PackageManagerException)) {
                    throw new IllegalStateException(
                            "Unexpected exception occurred while parsing " + apexInfo2.modulePath,
                            th);
                }
                tryToRecover(apexInfo2);
                throw new IllegalStateException("Unable to parse: " + apexInfo2.modulePath, th);
            }
            try {
                addForInitLI(
                        parseResult.parsedPackage,
                        i3,
                        i4,
                        null,
                        new ApexManager.ActiveApexInfo(apexInfo2));
                AndroidPackageInternal hideAsFinal = parseResult.parsedPackage.hideAsFinal();
                if (apexInfo2.isFactory && !apexInfo2.isActive) {
                    packageManagerService.mSettings.disableSystemPackageLPw(
                            hideAsFinal.getPackageName());
                }
                arrayList2.add(
                        new ApexManager.ScanResult(
                                apexInfo2, hideAsFinal, hideAsFinal.getPackageName()));
            } catch (PackageManagerException e) {
                tryToRecover(apexInfo2);
                throw new IllegalStateException("Failed to scan: " + apexInfo2.modulePath, e);
            }
        }
        return arrayList2;
    }

    public final ScanResult scanPackageNewLI(
            ParsedPackage parsedPackage, int i, int i2, long j, UserHandle userHandle, String str) {
        int i3;
        ScanResult scanPackageOnlyLI;
        SharedUserSetting sharedUserSetting;
        ScanRequest prepareInitialScanRequest =
                prepareInitialScanRequest(parsedPackage, i, i2, userHandle, str);
        PackageSetting packageSetting = prepareInitialScanRequest.mPkgSetting;
        PackageSetting packageSetting2 = prepareInitialScanRequest.mDisabledPkgSetting;
        boolean z =
                packageSetting != null
                        ? packageSetting.pkgState.updatedSystemApp
                        : packageSetting2 != null;
        PackageSetting packageSetting3 =
                ((i2 & 4) == 0
                                || packageSetting2 != null
                                || packageSetting == null
                                || !packageSetting.isSystem())
                        ? packageSetting2
                        : packageSetting;
        if (packageSetting3 != null) {
            i3 = i2 | EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            int i4 = packageSetting3.mPkgPrivateFlags;
            if ((i4 & 8) != 0) {
                i3 = i2 | 196608;
            }
            if ((i4 & 131072) != 0) {
                i3 |= 262144;
            }
            if ((i4 & 262144) != 0) {
                i3 |= 524288;
            }
            if ((i4 & 524288) != 0) {
                i3 |= 1048576;
            }
            if ((i4 & 2097152) != 0) {
                i3 |= 2097152;
            }
            if ((i4 & 1073741824) != 0) {
                i3 |= 4194304;
            }
        } else {
            i3 = i2;
        }
        if (packageSetting != null) {
            int identifier = userHandle == null ? 0 : userHandle.getIdentifier();
            if (packageSetting.getInstantApp(identifier)) {
                i3 |= 8192;
            }
            if (packageSetting.readUserState(identifier).isVirtualPreload()) {
                i3 |= 32768;
            }
        }
        boolean z2 = (i3 & 524288) != 0 && ScanPackageUtils.getVendorPartitionVersion() < 28;
        if ((i3 & 131072) == 0
                && !AndroidPackageLegacyUtils.isPrivileged(parsedPackage)
                && parsedPackage.getSharedUserId() != null
                && !z2
                && !parsedPackage.isLeavingSharedUser()) {
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    try {
                        sharedUserSetting =
                                this.mPm.mSettings.getSharedUserLPw(
                                        parsedPackage.getSharedUserId(), false);
                    } finally {
                    }
                } catch (PackageManagerException unused) {
                    sharedUserSetting = null;
                }
                if (sharedUserSetting != null
                        && sharedUserSetting.isPrivileged()
                        && PackageManagerServiceUtils.compareSignatures(
                                        this.mPm.mSettings.getPackageLPr("android")
                                                .signatures
                                                .mSigningDetails,
                                        parsedPackage.getSigningDetails())
                                != 0) {
                    i3 |= 131072;
                }
            }
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
        }
        ScanPackageUtils.applyPolicy(parsedPackage, i3, this.mPm.mPlatformPackage, z);
        PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock2) {
            try {
                assertPackageIsValid(parsedPackage, i, i3);
                ScanRequest scanRequest =
                        new ScanRequest(
                                parsedPackage,
                                prepareInitialScanRequest.mOldSharedUserSetting,
                                prepareInitialScanRequest.mOldPkg,
                                packageSetting,
                                prepareInitialScanRequest.mSharedUserSetting,
                                packageSetting2,
                                prepareInitialScanRequest.mOriginalPkgSetting,
                                prepareInitialScanRequest.mRealPkgName,
                                i,
                                i2,
                                prepareInitialScanRequest.mIsPlatformPackage,
                                userHandle,
                                str);
                PackageManagerService packageManagerService = this.mPm;
                scanPackageOnlyLI =
                        ScanPackageUtils.scanPackageOnlyLI(
                                scanRequest,
                                packageManagerService.mInjector,
                                packageManagerService.mFactoryTest,
                                j);
            } finally {
            }
        }
        return scanPackageOnlyLI;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Pair scanSystemPackageLI(
            com.android.internal.pm.parsing.pkg.ParsedPackage r31,
            int r32,
            int r33,
            android.os.UserHandle r34) {
        /*
            Method dump skipped, instructions count: 1383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallPackageHelper.scanSystemPackageLI(com.android.internal.pm.parsing.pkg.ParsedPackage,"
                    + " int, int, android.os.UserHandle):android.util.Pair");
    }
}
