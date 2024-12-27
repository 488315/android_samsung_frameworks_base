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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Finally extract failed */
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

    /* JADX WARN: Multi-variable type inference failed */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04e7 A[Catch: all -> 0x035c, TryCatch #5 {all -> 0x035c, blocks: (B:91:0x0328, B:93:0x0332, B:96:0x033a, B:100:0x0344, B:102:0x0347, B:106:0x0357, B:108:0x0353, B:110:0x0340, B:113:0x0363, B:115:0x0367, B:118:0x0379, B:120:0x037d, B:122:0x0390, B:124:0x039a, B:125:0x03a2, B:127:0x03a8, B:129:0x03b4, B:133:0x03f1, B:134:0x03c6, B:138:0x03d8, B:144:0x0403, B:148:0x040a, B:150:0x0410, B:151:0x0467, B:152:0x04f7, B:154:0x0509, B:156:0x050d, B:158:0x0514, B:160:0x0540, B:162:0x0544, B:165:0x0549, B:167:0x0553, B:171:0x056c, B:173:0x0570, B:175:0x057c, B:177:0x0582, B:179:0x0594, B:183:0x05b3, B:185:0x05bb, B:187:0x05bf, B:188:0x05cb, B:190:0x05cf, B:192:0x05d7, B:194:0x05db, B:197:0x05de, B:199:0x05f1, B:200:0x05fe, B:202:0x0604, B:204:0x061f, B:206:0x0628, B:208:0x063c, B:209:0x0641, B:212:0x0649, B:214:0x0659, B:215:0x065e, B:216:0x0669, B:217:0x0688, B:219:0x0647, B:220:0x0632, B:223:0x0638, B:224:0x0613, B:230:0x0619, B:231:0x0597, B:233:0x05a1, B:236:0x0428, B:237:0x043e, B:241:0x0448, B:243:0x0452, B:246:0x0463, B:247:0x0444, B:249:0x0474, B:251:0x0478, B:253:0x048a, B:255:0x0499, B:257:0x04f5, B:258:0x04b1, B:260:0x04c0, B:266:0x04d5, B:268:0x04da, B:272:0x04e7, B:273:0x04eb, B:274:0x04e3), top: B:90:0x0328, inners: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x04e3 A[Catch: all -> 0x035c, TryCatch #5 {all -> 0x035c, blocks: (B:91:0x0328, B:93:0x0332, B:96:0x033a, B:100:0x0344, B:102:0x0347, B:106:0x0357, B:108:0x0353, B:110:0x0340, B:113:0x0363, B:115:0x0367, B:118:0x0379, B:120:0x037d, B:122:0x0390, B:124:0x039a, B:125:0x03a2, B:127:0x03a8, B:129:0x03b4, B:133:0x03f1, B:134:0x03c6, B:138:0x03d8, B:144:0x0403, B:148:0x040a, B:150:0x0410, B:151:0x0467, B:152:0x04f7, B:154:0x0509, B:156:0x050d, B:158:0x0514, B:160:0x0540, B:162:0x0544, B:165:0x0549, B:167:0x0553, B:171:0x056c, B:173:0x0570, B:175:0x057c, B:177:0x0582, B:179:0x0594, B:183:0x05b3, B:185:0x05bb, B:187:0x05bf, B:188:0x05cb, B:190:0x05cf, B:192:0x05d7, B:194:0x05db, B:197:0x05de, B:199:0x05f1, B:200:0x05fe, B:202:0x0604, B:204:0x061f, B:206:0x0628, B:208:0x063c, B:209:0x0641, B:212:0x0649, B:214:0x0659, B:215:0x065e, B:216:0x0669, B:217:0x0688, B:219:0x0647, B:220:0x0632, B:223:0x0638, B:224:0x0613, B:230:0x0619, B:231:0x0597, B:233:0x05a1, B:236:0x0428, B:237:0x043e, B:241:0x0448, B:243:0x0452, B:246:0x0463, B:247:0x0444, B:249:0x0474, B:251:0x0478, B:253:0x048a, B:255:0x0499, B:257:0x04f5, B:258:0x04b1, B:260:0x04c0, B:266:0x04d5, B:268:0x04da, B:272:0x04e7, B:273:0x04eb, B:274:0x04e3), top: B:90:0x0328, inners: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0328 A[EXC_TOP_SPLITTER, SYNTHETIC] */
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

    /* JADX WARN: Removed duplicated region for block: B:68:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0275  */
    /* JADX WARN: Type inference failed for: r2v26, types: [boolean] */
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
    /* JADX WARN: Removed duplicated region for block: B:190:0x0907  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0990 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x08cb  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x07c1  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x07db  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0851  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x086b  */
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
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0293 A[Catch: all -> 0x0219, TRY_ENTER, TryCatch #6 {all -> 0x0219, blocks: (B:70:0x01dd, B:72:0x01ed, B:75:0x01f7, B:77:0x01fd, B:82:0x020c, B:83:0x0214, B:86:0x021b, B:92:0x023c, B:101:0x0251, B:102:0x025e, B:103:0x028e, B:109:0x0293, B:113:0x02a3, B:114:0x02b4, B:117:0x02b9, B:119:0x02c3, B:121:0x02d1, B:123:0x02e0, B:127:0x02e7, B:128:0x02ef, B:132:0x02f6, B:134:0x02fc, B:136:0x0350, B:214:0x029b, B:216:0x0221, B:219:0x0235), top: B:69:0x01dd }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x039b A[Catch: all -> 0x01d2, TryCatch #1 {all -> 0x01d2, blocks: (B:61:0x01be, B:63:0x01ca, B:66:0x01d6, B:68:0x01dc, B:142:0x0397, B:144:0x039b, B:146:0x03ad, B:149:0x03ba, B:151:0x03c0, B:155:0x03cc, B:156:0x03cf, B:158:0x03d3, B:160:0x03dc, B:161:0x03ed, B:164:0x0413, B:175:0x0422, B:174:0x041f, B:176:0x0423, B:179:0x042a, B:180:0x0442, B:184:0x044d, B:201:0x0475, B:202:0x0477, B:228:0x04d2, B:229:0x04d4, B:182:0x0443, B:183:0x044c, B:163:0x040a, B:169:0x0419), top: B:60:0x01be, inners: #2, #5, #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0488  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0249  */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0213  */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x061b  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x06cb  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0682  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x042e A[Catch: all -> 0x03b5, TryCatch #10 {all -> 0x03b5, blocks: (B:95:0x02dd, B:162:0x0365, B:313:0x034f, B:314:0x0352, B:402:0x042a, B:404:0x042e, B:405:0x0432, B:406:0x0435, B:368:0x03d3, B:370:0x03d7, B:371:0x03db, B:411:0x0436, B:413:0x043d, B:10:0x0059, B:12:0x0063, B:13:0x0066), top: B:9:0x0059, inners: #34 }] */
    /* JADX WARN: Removed duplicated region for block: B:456:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ac A[Catch: all -> 0x0332, TRY_ENTER, TRY_LEAVE, TryCatch #9 {all -> 0x0332, blocks: (B:7:0x0052, B:18:0x0070, B:23:0x00d1, B:29:0x00e5, B:66:0x0214, B:72:0x0275, B:79:0x0298, B:83:0x02ac, B:85:0x02ba, B:128:0x0290), top: B:6:0x0052 }] */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r12v10, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v13 */
    /* JADX WARN: Type inference failed for: r12v23 */
    /* JADX WARN: Type inference failed for: r12v24 */
    /* JADX WARN: Type inference failed for: r12v25 */
    /* JADX WARN: Type inference failed for: r12v26 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4, types: [int] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r14v0, types: [android.util.ArrayMap, java.util.Map] */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8, types: [int] */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r1v67, types: [com.android.server.pm.PackageMetrics] */
    /* JADX WARN: Type inference failed for: r2v110 */
    /* JADX WARN: Type inference failed for: r2v111 */
    /* JADX WARN: Type inference failed for: r2v114 */
    /* JADX WARN: Type inference failed for: r2v115 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v37 */
    /* JADX WARN: Type inference failed for: r2v42, types: [int[]] */
    /* JADX WARN: Type inference failed for: r31v0, types: [com.android.server.pm.InstallPackageHelper] */
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
    /* JADX WARN: Removed duplicated region for block: B:14:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0108  */
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x00bd A[Catch: all -> 0x0085, TryCatch #0 {all -> 0x0085, blocks: (B:4:0x000c, B:6:0x002b, B:8:0x0034, B:10:0x003e, B:13:0x0049, B:14:0x004c, B:16:0x006a, B:17:0x0088, B:19:0x0097, B:25:0x00a7, B:27:0x00ad, B:29:0x00bd, B:30:0x00c8, B:45:0x00a0), top: B:3:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c7  */
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

    /* JADX WARN: Can't wrap try/catch for region: R(17:103|(1:105)(1:742)|106|107|(2:(1:110)|111)(2:737|(1:739)(2:740|741))|112|(2:113|114)|(10:116|117|118|119|120|121|122|(4:124|125|(2:127|128)|722)(1:730)|723|(2:725|(7:135|(1:137)(1:721)|138|139|(1:141)(1:717)|142|(8:144|145|146|(4:151|(2:154|(2:156|(17:158|3d1|336|(4:340|341|342|(2:344|345))|348|(3:673|674|(2:681|682))|357|(3:359|988|366)(3:629|(13:632|633|9d6|(1:662)(1:641)|642|(1:644)(1:661)|645|(1:647)(1:660)|(1:659)(1:651)|652|(2:656|657)|658|657)|631)|367|(4:369|(1:371)(1:619)|372|373)(1:620)|374|(2:(1:377)(1:613)|378)(1:614)|(5:431|432|af4|437|(5:448|(1:450)(1:597)|451|452|b28)(3:442|443|444))(9:380|381|382|383|e41|419|420|421|422)|393|394|395|(2:397|398)(1:399))(2:706|707))(1:708))|709|(0)(0))|710|(2:154|(0)(0))|709|(0)(0))(2:714|715))(2:133|134))(2:726|727))|735|(1:131)|135|(0)(0)|138|139|(0)(0)|142|(0)(0)) */
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
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x032e A[Catch: RemoteException -> 0x0333, TryCatch #15 {RemoteException -> 0x0333, blocks: (B:139:0x0328, B:141:0x032e, B:142:0x0338, B:714:0x035a, B:715:0x0366), top: B:138:0x0328 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03b4 A[Catch: Exception -> 0x0394, TryCatch #24 {Exception -> 0x0394, blocks: (B:146:0x0373, B:148:0x0385, B:154:0x039c, B:156:0x03b4, B:708:0x03bd), top: B:145:0x0373 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x040e A[Catch: all -> 0x0404, TryCatch #21 {all -> 0x0404, blocks: (B:161:0x03d2, B:163:0x03df, B:165:0x03f1, B:167:0x03fb, B:170:0x040e, B:174:0x041d, B:175:0x044e, B:176:0x044f, B:178:0x0453, B:182:0x0472, B:184:0x047a, B:187:0x048f, B:190:0x0497, B:194:0x04a0, B:195:0x04bc, B:198:0x04c6, B:200:0x04cc, B:202:0x04d8, B:204:0x04e0, B:205:0x04f7, B:207:0x04fd, B:209:0x0505, B:211:0x0513, B:213:0x0523, B:215:0x0531, B:216:0x0551, B:219:0x0556, B:222:0x056a, B:225:0x057a, B:227:0x058c, B:228:0x0590, B:237:0x059d, B:238:0x059f, B:242:0x05b2, B:244:0x05bd, B:248:0x05cd, B:249:0x05c5, B:250:0x05de, B:251:0x05eb, B:253:0x05f6, B:255:0x0619, B:259:0x0626, B:261:0x062a, B:264:0x0637, B:268:0x063e, B:269:0x0683, B:272:0x0684, B:274:0x0689, B:276:0x0697, B:279:0x06bb, B:281:0x06f6, B:283:0x0700, B:285:0x0708, B:287:0x0738, B:288:0x076f, B:289:0x0770, B:291:0x077c, B:293:0x0785, B:295:0x078b, B:296:0x07bd, B:298:0x07c3, B:304:0x07d3, B:310:0x07f3, B:312:0x080c, B:314:0x0816, B:317:0x0822, B:318:0x0876, B:321:0x08ca, B:323:0x0877, B:324:0x08be, B:306:0x07ed, B:335:0x08d1, B:690:0x05a2, B:691:0x05ad), top: B:160:0x03d2, inners: #27 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x05f6 A[Catch: all -> 0x0404, TryCatch #21 {all -> 0x0404, blocks: (B:161:0x03d2, B:163:0x03df, B:165:0x03f1, B:167:0x03fb, B:170:0x040e, B:174:0x041d, B:175:0x044e, B:176:0x044f, B:178:0x0453, B:182:0x0472, B:184:0x047a, B:187:0x048f, B:190:0x0497, B:194:0x04a0, B:195:0x04bc, B:198:0x04c6, B:200:0x04cc, B:202:0x04d8, B:204:0x04e0, B:205:0x04f7, B:207:0x04fd, B:209:0x0505, B:211:0x0513, B:213:0x0523, B:215:0x0531, B:216:0x0551, B:219:0x0556, B:222:0x056a, B:225:0x057a, B:227:0x058c, B:228:0x0590, B:237:0x059d, B:238:0x059f, B:242:0x05b2, B:244:0x05bd, B:248:0x05cd, B:249:0x05c5, B:250:0x05de, B:251:0x05eb, B:253:0x05f6, B:255:0x0619, B:259:0x0626, B:261:0x062a, B:264:0x0637, B:268:0x063e, B:269:0x0683, B:272:0x0684, B:274:0x0689, B:276:0x0697, B:279:0x06bb, B:281:0x06f6, B:283:0x0700, B:285:0x0708, B:287:0x0738, B:288:0x076f, B:289:0x0770, B:291:0x077c, B:293:0x0785, B:295:0x078b, B:296:0x07bd, B:298:0x07c3, B:304:0x07d3, B:310:0x07f3, B:312:0x080c, B:314:0x0816, B:317:0x0822, B:318:0x0876, B:321:0x08ca, B:323:0x0877, B:324:0x08be, B:306:0x07ed, B:335:0x08d1, B:690:0x05a2, B:691:0x05ad), top: B:160:0x03d2, inners: #27 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0697 A[Catch: all -> 0x0404, TryCatch #21 {all -> 0x0404, blocks: (B:161:0x03d2, B:163:0x03df, B:165:0x03f1, B:167:0x03fb, B:170:0x040e, B:174:0x041d, B:175:0x044e, B:176:0x044f, B:178:0x0453, B:182:0x0472, B:184:0x047a, B:187:0x048f, B:190:0x0497, B:194:0x04a0, B:195:0x04bc, B:198:0x04c6, B:200:0x04cc, B:202:0x04d8, B:204:0x04e0, B:205:0x04f7, B:207:0x04fd, B:209:0x0505, B:211:0x0513, B:213:0x0523, B:215:0x0531, B:216:0x0551, B:219:0x0556, B:222:0x056a, B:225:0x057a, B:227:0x058c, B:228:0x0590, B:237:0x059d, B:238:0x059f, B:242:0x05b2, B:244:0x05bd, B:248:0x05cd, B:249:0x05c5, B:250:0x05de, B:251:0x05eb, B:253:0x05f6, B:255:0x0619, B:259:0x0626, B:261:0x062a, B:264:0x0637, B:268:0x063e, B:269:0x0683, B:272:0x0684, B:274:0x0689, B:276:0x0697, B:279:0x06bb, B:281:0x06f6, B:283:0x0700, B:285:0x0708, B:287:0x0738, B:288:0x076f, B:289:0x0770, B:291:0x077c, B:293:0x0785, B:295:0x078b, B:296:0x07bd, B:298:0x07c3, B:304:0x07d3, B:310:0x07f3, B:312:0x080c, B:314:0x0816, B:317:0x0822, B:318:0x0876, B:321:0x08ca, B:323:0x0877, B:324:0x08be, B:306:0x07ed, B:335:0x08d1, B:690:0x05a2, B:691:0x05ad), top: B:160:0x03d2, inners: #27 }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0980  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0a75  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0abf  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0e35  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0e6a  */
    /* JADX WARN: Removed duplicated region for block: B:399:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0ee8  */
    /* JADX WARN: Removed duplicated region for block: B:409:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0aea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ef A[Catch: all -> 0x00f6, PackageParserException -> 0x00fd, TRY_ENTER, TRY_LEAVE, TryCatch #22 {PackageParserException -> 0x00fd, blocks: (B:49:0x00b6, B:57:0x00ef, B:769:0x0f69, B:768:0x0f66), top: B:48:0x00b6, outer: #17 }] */
    /* JADX WARN: Removed duplicated region for block: B:614:0x0ae3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x0aab  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x09ca  */
    /* JADX WARN: Removed duplicated region for block: B:685:0x05ea  */
    /* JADX WARN: Removed duplicated region for block: B:706:0x0ef4  */
    /* JADX WARN: Removed duplicated region for block: B:708:0x03bd A[Catch: Exception -> 0x0394, TRY_LEAVE, TryCatch #24 {Exception -> 0x0394, blocks: (B:146:0x0373, B:148:0x0385, B:154:0x039c, B:156:0x03b4, B:708:0x03bd), top: B:145:0x0373 }] */
    /* JADX WARN: Removed duplicated region for block: B:714:0x035a A[Catch: RemoteException -> 0x0333, TryCatch #15 {RemoteException -> 0x0333, blocks: (B:139:0x0328, B:141:0x032e, B:142:0x0338, B:714:0x035a, B:715:0x0366), top: B:138:0x0328 }] */
    /* JADX WARN: Removed duplicated region for block: B:717:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:721:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:746:0x0f29  */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v57 */
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0196  */
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

    /* JADX WARN: Removed duplicated region for block: B:105:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x04b1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0529  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0360 A[Catch: all -> 0x054b, TRY_LEAVE, TryCatch #3 {all -> 0x054b, blocks: (B:212:0x034d, B:214:0x0360, B:216:0x054d, B:217:0x055e), top: B:211:0x034d }] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x054d A[Catch: all -> 0x054b, TRY_ENTER, TryCatch #3 {all -> 0x054b, blocks: (B:212:0x034d, B:214:0x0360, B:216:0x054d, B:217:0x055e), top: B:211:0x034d }] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0342  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0171  */
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
