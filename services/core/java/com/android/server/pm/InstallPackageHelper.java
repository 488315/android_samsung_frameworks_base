package com.android.server.pm;

import android.apex.ApexInfo;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.ApplicationPackageManager;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.backup.IBackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ASKSManager;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.UserInfo;
import android.content.pm.VerifierInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.VirtualDisplay;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.INetworkManagementService;
import android.os.Process;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.incremental.IncrementalManager;
import android.os.incremental.IncrementalStorage;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.jar.StrictJarFile;
import android.view.Display;
import com.android.internal.content.F2fsUtils;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AlarmManagerInternal;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SpegService;
import com.android.server.SpqrService;
import com.android.server.SystemConfig;
import com.android.server.apphibernation.AppHibernationManagerInternal;
import com.android.server.apphibernation.AppHibernationService;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.ApexManager;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageAbiHelper;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.ParallelPackageParser;
import com.android.server.pm.dex.ArtManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.dex.ViewCompiler;
import com.android.server.pm.parsing.PackageCacher;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.permission.Permission;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedLibraryWrapper;
import com.android.server.pm.pkg.component.ComponentMutateUtils;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedInstrumentation;
import com.android.server.pm.pkg.component.ParsedIntentInfo;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedPermissionGroup;
import com.android.server.pm.pkg.component.ParsedUsesPermission;
import com.android.server.pm.pkg.parsing.ParsingPackageUtils;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.rollback.RollbackManagerInternal;
import com.android.server.security.FileIntegrityService;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.keystore.ICertificatePolicy;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.MetaDataHelper;
import com.samsung.android.server.p025pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.p025pm.install.PackageBlockListPolicy;
import com.samsung.android.server.p025pm.install.PmConfigParser;
import com.samsung.android.server.p025pm.install.PreloadDuplicateApps;
import com.samsung.android.server.p025pm.monetization.MonetizationUtils;
import com.samsung.android.server.p025pm.p026mm.MaintenanceModeManager;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.zip.ZipFile;

/* loaded from: classes3.dex */
public final class InstallPackageHelper {
    public final ApexManager mApexManager;
    public final AppDataHelper mAppDataHelper;
    public final ArtManagerService mArtManagerService;
    public boolean mBlockContinualSpeg;
    public final BroadcastHelper mBroadcastHelper;
    public final Context mContext;
    public final DexManager mDexManager;
    public final IncrementalManager mIncrementalManager;
    public final PackageManagerServiceInjector mInjector;
    public MonetizationUtils mMonetizationUtils;
    public final PackageAbiHelper mPackageAbiHelper;
    public final PackageDexOptimizer mPackageDexOptimizer;
    public final PackageManagerService mPm;
    public final PreloadDuplicateApps mPreloadDuplicateApps;
    public final RemovePackageHelper mRemovePackageHelper;
    public final SharedLibrariesImpl mSharedLibraries;
    public SpegService mSpeg;
    public long mSpegBlockStartTime;
    public long mSpegFirstLaunchTime;
    public int mSpegLaunchesCount;
    public long mSpegPrevLaunchTime;
    public final UpdateOwnershipHelper mUpdateOwnershipHelper;
    public final ViewCompiler mViewCompiler;
    public static final boolean SPEG_DISABLE_LAUNCH = SystemProperties.getBoolean("com.samsung.speg.disable_launch", false);
    public static final boolean CAN_OVERRIDE_PROFILE = true;

    public InstallPackageHelper(PackageManagerService packageManagerService, AppDataHelper appDataHelper) {
        this.mPreloadDuplicateApps = new PreloadDuplicateApps();
        this.mBlockContinualSpeg = false;
        this.mSpegLaunchesCount = 0;
        this.mSpegFirstLaunchTime = -1L;
        this.mSpegPrevLaunchTime = -1L;
        this.mSpegBlockStartTime = -1L;
        this.mPm = packageManagerService;
        this.mInjector = packageManagerService.mInjector;
        this.mAppDataHelper = appDataHelper;
        this.mBroadcastHelper = new BroadcastHelper(packageManagerService.mInjector);
        this.mRemovePackageHelper = new RemovePackageHelper(packageManagerService);
        this.mIncrementalManager = packageManagerService.mInjector.getIncrementalManager();
        this.mApexManager = packageManagerService.mInjector.getApexManager();
        this.mDexManager = packageManagerService.mInjector.getDexManager();
        this.mArtManagerService = packageManagerService.mInjector.getArtManagerService();
        Context context = packageManagerService.mInjector.getContext();
        this.mContext = context;
        this.mPackageDexOptimizer = packageManagerService.mInjector.getPackageDexOptimizer();
        this.mPackageAbiHelper = packageManagerService.mInjector.getAbiHelper();
        this.mViewCompiler = packageManagerService.mInjector.getViewCompiler();
        this.mSharedLibraries = packageManagerService.mInjector.getSharedLibrariesImpl();
        this.mUpdateOwnershipHelper = packageManagerService.mInjector.getUpdateOwnershipHelper();
        if (CoreRune.SYSFW_APP_SPEG) {
            this.mSpeg = (SpegService) LocalServices.getService(SpegService.class);
        }
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
    }

    public InstallPackageHelper(PackageManagerService packageManagerService) {
        this(packageManagerService, new AppDataHelper(packageManagerService));
    }

    /* JADX WARN: Type inference failed for: r11v15, types: [boolean] */
    public AndroidPackage commitReconciledScanResultLocked(ReconciledPackage reconciledPackage, int[] iArr) {
        final PackageSetting packageSetting;
        String str;
        int i;
        int userId;
        PackageSetting packageLPr;
        InstallRequest installRequest = reconciledPackage.mInstallRequest;
        ParsedPackage parsedPackage = installRequest.getParsedPackage();
        if (parsedPackage != null && "android".equals(parsedPackage.getPackageName())) {
            parsedPackage.setVersionCode(this.mPm.getSdkVersion()).setVersionCodeMajor(0);
        }
        int scanFlags = installRequest.getScanFlags();
        PackageSetting scanRequestOldPackageSetting = installRequest.getScanRequestOldPackageSetting();
        PackageSetting scanRequestOriginalPackageSetting = installRequest.getScanRequestOriginalPackageSetting();
        String realPackageName = installRequest.getRealPackageName();
        List changedAbiCodePath = DexOptHelper.useArtService() ? null : installRequest.getChangedAbiCodePath();
        if (installRequest.getScanRequestPackageSetting() != null) {
            SharedUserSetting sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(installRequest.getScanRequestPackageSetting());
            SharedUserSetting sharedUserSettingLPr2 = this.mPm.mSettings.getSharedUserSettingLPr(installRequest.getScannedPackageSetting());
            if (sharedUserSettingLPr != null && sharedUserSettingLPr != sharedUserSettingLPr2) {
                sharedUserSettingLPr.removePackage(installRequest.getScanRequestPackageSetting());
                if (this.mPm.mSettings.checkAndPruneSharedUserLPw(sharedUserSettingLPr, false)) {
                    installRequest.setRemovedAppId(sharedUserSettingLPr.mAppId);
                }
            }
        }
        if (installRequest.isExistingSettingCopied()) {
            packageSetting = installRequest.getScanRequestPackageSetting();
            packageSetting.updateFrom(installRequest.getScannedPackageSetting());
        } else {
            PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
            if (scanRequestOriginalPackageSetting != null) {
                this.mPm.mSettings.addRenamedPackageLPw(AndroidPackageUtils.getRealPackageOrNull(parsedPackage, scannedPackageSetting.isSystem()), scanRequestOriginalPackageSetting.getPackageName());
                this.mPm.mTransferredPackages.add(scanRequestOriginalPackageSetting.getPackageName());
            } else {
                this.mPm.mSettings.removeRenamedPackageLPw(parsedPackage.getPackageName());
            }
            packageSetting = scannedPackageSetting;
        }
        SharedUserSetting sharedUserSettingLPr3 = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr3 != null) {
            sharedUserSettingLPr3.addPackage(packageSetting);
            if (parsedPackage.isLeavingSharedUser() && SharedUidMigration.applyStrategy(2) && sharedUserSettingLPr3.isSingleUser()) {
                this.mPm.mSettings.convertSharedUserSettingsLPw(sharedUserSettingLPr3);
            }
        }
        if (installRequest.isForceQueryableOverride()) {
            packageSetting.setForceQueryableOverride(true);
        }
        InstallSource installSource = installRequest.getInstallSource();
        boolean z = (67108864 & scanFlags) != 0;
        boolean z2 = scanRequestOldPackageSetting != null;
        String str2 = z2 ? scanRequestOldPackageSetting.getInstallSource().mUpdateOwnerPackageName : null;
        String systemAppUpdateOwnerPackageName = (z || !packageSetting.isSystem()) ? null : this.mPm.mInjector.getSystemConfig().getSystemAppUpdateOwnerPackageName(parsedPackage.getPackageName());
        List list = changedAbiCodePath;
        boolean isUpdateOwnershipDenylisted = this.mUpdateOwnershipHelper.isUpdateOwnershipDenylisted(parsedPackage.getPackageName());
        boolean z3 = str2 != null;
        boolean isSamsungApp = this.mUpdateOwnershipHelper.isSamsungApp(parsedPackage.getPackageName());
        if (installSource != null) {
            if (!PackageManagerServiceUtils.isInstalledByAdb(installSource.mInitiatingPackageName) && (packageLPr = this.mPm.mSettings.getPackageLPr(installSource.mInitiatingPackageName)) != null) {
                installSource = installSource.setInitiatingPackageSignatures(packageLPr.getSignatures());
            }
            if (z) {
                str = realPackageName;
            } else {
                int i2 = installSource.mInstallerPackageUid;
                if (i2 != -1) {
                    userId = UserHandle.getUserId(i2);
                } else {
                    userId = installRequest.getUserId();
                }
                boolean z4 = z2 && (userId < 0 ? scanRequestOldPackageSetting.getNotInstalledUserIds().length <= UserManager.isHeadlessSystemUserMode() : scanRequestOldPackageSetting.getInstalled(userId));
                boolean z5 = (installRequest.getInstallFlags() & 33554432) != 0;
                boolean equals = TextUtils.equals(str2, installSource.mInstallerPackageName);
                str = realPackageName;
                boolean isUpdateOwnershipDenyListProvider = this.mUpdateOwnershipHelper.isUpdateOwnershipDenyListProvider(installSource.mUpdateOwnerPackageName);
                if (z4) {
                    if (!equals || !z3) {
                        installSource = installSource.setUpdateOwnerPackageName(null);
                    }
                } else if (!z5 || isUpdateOwnershipDenylisted || isSamsungApp || isUpdateOwnershipDenyListProvider) {
                    installSource = installSource.setUpdateOwnerPackageName(null);
                } else if ((!z3 && z2) || (z3 && !equals)) {
                    installSource = installSource.setUpdateOwnerPackageName(null);
                }
            }
            packageSetting.setInstallSource(installSource);
        } else {
            str = realPackageName;
            if (packageSetting.isSystem()) {
                boolean z6 = z3 && TextUtils.equals(str2, systemAppUpdateOwnerPackageName);
                if (!z2 || z6) {
                    packageSetting.setUpdateOwnerPackage(systemAppUpdateOwnerPackageName);
                } else {
                    packageSetting.setUpdateOwnerPackage(null);
                }
            }
        }
        if ((8388608 & scanFlags) != 0) {
            boolean z7 = (33554432 & scanFlags) != 0;
            i = 1;
            packageSetting.getPkgState().setApkInUpdatedApex(!z7);
        } else {
            i = 1;
        }
        packageSetting.getPkgState().setApexModuleName(installRequest.getApexModuleName());
        parsedPackage.setUid(packageSetting.getAppId());
        AndroidPackageInternal hideAsFinal = parsedPackage.hideAsFinal();
        this.mPm.mSettings.writeUserRestrictionsLPw(packageSetting, scanRequestOldPackageSetting);
        if (str != null) {
            this.mPm.mTransferredPackages.add(hideAsFinal.getPackageName());
        }
        if (reconciledPackage.mCollectedSharedLibraryInfos != null || (scanRequestOldPackageSetting != null && !scanRequestOldPackageSetting.getSharedLibraryDependencies().isEmpty())) {
            this.mSharedLibraries.executeSharedLibrariesUpdate(hideAsFinal, packageSetting, null, null, reconciledPackage.mCollectedSharedLibraryInfos, iArr);
        }
        KeySetManagerService keySetManagerService = this.mPm.mSettings.getKeySetManagerService();
        if (reconciledPackage.mRemoveAppKeySetData) {
            keySetManagerService.removeAppKeySetDataLPw(hideAsFinal.getPackageName());
        }
        if (reconciledPackage.mSharedUserSignaturesChanged) {
            sharedUserSettingLPr3.signaturesChanged = Boolean.TRUE;
            sharedUserSettingLPr3.signatures.mSigningDetails = reconciledPackage.mSigningDetails;
        }
        packageSetting.setSigningDetails(reconciledPackage.mSigningDetails);
        if (list != null && list.size() > 0) {
            int size = list.size() - i;
            while (size >= 0) {
                List list2 = list;
                String str3 = (String) list2.get(size);
                try {
                    synchronized (this.mPm.mInstallLock) {
                        this.mPm.mInstaller.rmdex(str3, InstructionSets.getDexCodeInstructionSet(InstructionSets.getPreferredInstructionSet()));
                    }
                } catch (Installer.InstallerException unused) {
                    continue;
                } catch (Installer.LegacyDexoptDisabledException e) {
                    throw new RuntimeException(e);
                }
                size--;
                list = list2;
            }
        }
        int userId2 = installRequest.getUserId();
        commitPackageSettings(hideAsFinal, packageSetting, scanRequestOldPackageSetting, reconciledPackage);
        if (packageSetting.getInstantApp(userId2)) {
            this.mPm.mInstantAppRegistry.addInstantApp(userId2, packageSetting.getAppId());
        }
        if (!IncrementalManager.isIncrementalPath(packageSetting.getPathString())) {
            packageSetting.setLoadingProgress(1.0f);
        }
        if (UpdateOwnershipHelper.hasValidOwnershipDenyList(packageSetting)) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    InstallPackageHelper.this.lambda$commitReconciledScanResultLocked$0(packageSetting);
                }
            });
        }
        return hideAsFinal;
    }

    /* renamed from: handleUpdateOwnerDenyList, reason: merged with bridge method [inline-methods] */
    public final void lambda$commitReconciledScanResultLocked$0(PackageSetting packageSetting) {
        ArraySet readUpdateOwnerDenyList = this.mUpdateOwnershipHelper.readUpdateOwnerDenyList(packageSetting);
        if (readUpdateOwnerDenyList == null || readUpdateOwnerDenyList.isEmpty()) {
            return;
        }
        this.mUpdateOwnershipHelper.addToUpdateOwnerDenyList(packageSetting.getPackageName(), readUpdateOwnerDenyList);
        SystemConfig systemConfig = SystemConfig.getInstance();
        synchronized (this.mPm.mLock) {
            Iterator it = readUpdateOwnerDenyList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr != null && systemConfig.getSystemAppUpdateOwnerPackageName(str) == null) {
                    packageLPr.setUpdateOwnerPackage(null);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x012d A[Catch: all -> 0x0190, TryCatch #0 {, blocks: (B:32:0x00bb, B:34:0x00d2, B:35:0x00d7, B:37:0x00dc, B:38:0x00e7, B:41:0x0101, B:43:0x010b, B:46:0x0112, B:47:0x0121, B:49:0x012d, B:52:0x014b, B:53:0x0158, B:56:0x0153, B:55:0x015f, B:59:0x0162, B:61:0x016c, B:62:0x0170, B:70:0x017c, B:72:0x017d, B:75:0x0188, B:76:0x018b, B:80:0x011a, B:64:0x0171, B:65:0x0178), top: B:31:0x00bb, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x016c A[Catch: all -> 0x0190, TryCatch #0 {, blocks: (B:32:0x00bb, B:34:0x00d2, B:35:0x00d7, B:37:0x00dc, B:38:0x00e7, B:41:0x0101, B:43:0x010b, B:46:0x0112, B:47:0x0121, B:49:0x012d, B:52:0x014b, B:53:0x0158, B:56:0x0153, B:55:0x015f, B:59:0x0162, B:61:0x016c, B:62:0x0170, B:70:0x017c, B:72:0x017d, B:75:0x0188, B:76:0x018b, B:80:0x011a, B:64:0x0171, B:65:0x0178), top: B:31:0x00bb, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void commitPackageSettings(AndroidPackage androidPackage, PackageSetting packageSetting, PackageSetting packageSetting2, ReconciledPackage reconciledPackage) {
        int size;
        int i;
        List protectedBroadcasts;
        String packageName = androidPackage.getPackageName();
        InstallRequest installRequest = reconciledPackage.mInstallRequest;
        AndroidPackage scanRequestOldPackage = installRequest.getScanRequestOldPackage();
        int scanFlags = installRequest.getScanFlags();
        boolean z = (installRequest.getParseFlags() & Integer.MIN_VALUE) != 0;
        ComponentName componentName = this.mPm.mCustomResolverComponentName;
        if (componentName != null && componentName.getPackageName().equals(androidPackage.getPackageName())) {
            this.mPm.setUpCustomResolverActivity(androidPackage, packageSetting);
        }
        if (androidPackage.getPackageName().equals("android")) {
            this.mPm.setPlatformPackage(androidPackage, packageSetting);
        }
        boolean z2 = z;
        ArrayList commitSharedLibraryChanges = this.mSharedLibraries.commitSharedLibraryChanges(androidPackage, packageSetting, reconciledPackage.mAllowedSharedLibraryInfos, reconciledPackage.getCombinedAvailablePackages(), scanFlags);
        installRequest.setLibraryConsumers(commitSharedLibraryChanges);
        if ((scanFlags & 16) == 0 && (scanFlags & 1024) == 0 && (scanFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0) {
            this.mPm.snapshotComputer().checkPackageFrozen(packageName);
        }
        boolean isInstallReplace = installRequest.isInstallReplace();
        if (commitSharedLibraryChanges != null && (androidPackage.getStaticSharedLibraryName() == null || isInstallReplace)) {
            for (int i2 = 0; i2 < commitSharedLibraryChanges.size(); i2++) {
                AndroidPackage androidPackage2 = (AndroidPackage) commitSharedLibraryChanges.get(i2);
                this.mPm.killApplication(androidPackage2.getPackageName(), androidPackage2.getUid(), "update lib", 12);
            }
        }
        Trace.traceBegin(262144L, "updateSettings");
        synchronized (this.mPm.mLock) {
            this.mPm.mSettings.insertPackageSettingLPw(packageSetting, androidPackage);
            this.mPm.mPackages.put(androidPackage.getPackageName(), androidPackage);
            if ((8388608 & scanFlags) != 0) {
                this.mApexManager.registerApkInApex(androidPackage);
            }
            if ((67108864 & scanFlags) == 0) {
                this.mPm.mSettings.getKeySetManagerService().addScannedPackageLPw(androidPackage);
            }
            Computer snapshotComputer = this.mPm.snapshotComputer();
            PackageManagerService packageManagerService = this.mPm;
            packageManagerService.mComponentResolver.addAllComponents(androidPackage, z2, packageManagerService.mSetupWizardPackage, snapshotComputer);
            this.mPm.mAppsFilter.addPackage(snapshotComputer, packageSetting, isInstallReplace, (scanFlags & 1024) != 0);
            this.mPm.addAllPackageProperties(androidPackage);
            if (packageSetting2 != null && packageSetting2.getPkg() != null) {
                this.mPm.mDomainVerificationManager.migrateState(packageSetting2, packageSetting);
                size = ArrayUtils.size(androidPackage.getInstrumentations());
                StringBuilder sb = null;
                for (i = 0; i < size; i++) {
                    ParsedInstrumentation parsedInstrumentation = (ParsedInstrumentation) androidPackage.getInstrumentations().get(i);
                    ComponentMutateUtils.setPackageName(parsedInstrumentation, androidPackage.getPackageName());
                    this.mPm.addInstrumentation(parsedInstrumentation.getComponentName(), parsedInstrumentation);
                    if (z2) {
                        if (sb == null) {
                            sb = new StringBuilder(256);
                        } else {
                            sb.append(' ');
                        }
                        sb.append(parsedInstrumentation.getName());
                    }
                }
                protectedBroadcasts = androidPackage.getProtectedBroadcasts();
                if (!protectedBroadcasts.isEmpty()) {
                    synchronized (this.mPm.mProtectedBroadcasts) {
                        this.mPm.mProtectedBroadcasts.addAll(protectedBroadcasts);
                    }
                }
                this.mPm.mPermissionManager.onPackageAdded(packageSetting, (scanFlags & IInstalld.FLAG_FORCE) == 0, scanRequestOldPackage);
            }
            this.mPm.mDomainVerificationManager.addPackage(packageSetting);
            size = ArrayUtils.size(androidPackage.getInstrumentations());
            StringBuilder sb2 = null;
            while (i < size) {
            }
            protectedBroadcasts = androidPackage.getProtectedBroadcasts();
            if (!protectedBroadcasts.isEmpty()) {
            }
            this.mPm.mPermissionManager.onPackageAdded(packageSetting, (scanFlags & IInstalld.FLAG_FORCE) == 0, scanRequestOldPackage);
        }
        Trace.traceEnd(262144L);
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x02f6 A[Catch: all -> 0x0407, TRY_ENTER, TryCatch #3 {all -> 0x0407, blocks: (B:56:0x01c9, B:58:0x01d5, B:61:0x01de, B:62:0x01e2, B:116:0x02f6, B:118:0x0308, B:121:0x030f, B:123:0x0316, B:126:0x031e, B:127:0x0321, B:129:0x0327, B:131:0x0331, B:132:0x033c, B:133:0x0350, B:141:0x035e, B:142:0x035f, B:143:0x0372, B:147:0x037d, B:152:0x039a, B:182:0x0406, B:64:0x01e3, B:66:0x01f3, B:70:0x01fd, B:72:0x0203, B:76:0x020d, B:77:0x0215, B:80:0x021a, B:82:0x0224, B:83:0x0256, B:86:0x025b, B:88:0x0261, B:90:0x0267, B:91:0x0276, B:94:0x027b, B:96:0x0285, B:98:0x0293, B:100:0x02a0, B:104:0x02a7, B:105:0x02af, B:109:0x02b4, B:111:0x02ba, B:113:0x02ec, B:114:0x02f3, B:173:0x02e3, B:176:0x03f7, B:177:0x03ff, B:145:0x0373, B:146:0x037c, B:135:0x0351, B:136:0x035a), top: B:55:0x01c9, inners: #0, #4, #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Pair installExistingPackageAsUser(final String str, final int i, int i2, int i3, List list, IntentSender intentSender) {
        boolean z;
        boolean z2;
        int i4;
        PackageSetting packageSetting;
        final IntentSender intentSender2 = intentSender;
        int callingUid = Binder.getCallingUid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0 && this.mContext.checkCallingOrSelfPermission("com.android.permission.INSTALL_EXISTING_PACKAGES") != 0) {
            throw new SecurityException("Neither user " + callingUid + " nor current process has android.permission.INSTALL_PACKAGES.");
        }
        if (callingUid == 2000 && MaintenanceModeManager.isInMaintenanceModeFromProperty() && i != 77) {
            Slog.i("PackageManager", "Not allowed to install-existing on user " + i);
            return Pair.create(-111, intentSender2);
        }
        Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "installExistingPackage for user " + i);
        if (this.mPm.isUserRestricted(i, "no_install_apps")) {
            return Pair.create(-111, intentSender2);
        }
        if (PersonaServiceHelper.isSpfKnoxSupported() && !PersonaServiceHelper.isCallerApprovedToInstall(this.mContext, callingUid, i, false)) {
            Log.d("PackageManager", "installExistingPackageAsUser the app with uid " + callingUid + " is not installed via approved installer");
            return Pair.create(-1, intentSender2);
        }
        Log.w("PackageManager", "verifying app can be installed or not for user - " + i);
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface != null) {
            synchronized (this.mPm.mLock) {
                packageSetting = (PackageSetting) this.mPm.mSettings.mPackages.get(str);
            }
            if (packageSetting != null) {
                try {
                    if (packageSetting.getPkg() != null && !asInterface.isApplicationInstallationEnabled(str, packageSetting.getPkg().getSigningDetails().getSignatures(), packageSetting.getPkg().getRequestedPermissions(), i)) {
                        Log.w("PackageManager", "This app installation is not allowed");
                        return Pair.create(-110, intentSender2);
                    }
                } catch (RemoteException unused) {
                }
            }
        }
        IRestrictionPolicy asInterface2 = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface2 != null) {
            try {
                if (!asInterface2.isNewAdminInstallationEnabledAsUser(i, true)) {
                    synchronized (this.mPm.mLock) {
                        if (((PackageSetting) this.mPm.mSettings.mPackages.get(str)) == null) {
                            return Pair.create(-3, intentSender2);
                        }
                        if (isAdminApplication(((PackageSetting) this.mPm.mSettings.mPackages.get(str)).getPkg())) {
                            if (!asInterface2.checkPackageSource(i, str)) {
                                Log.w("PackageManager", "This admin app installation is not allowed");
                                return Pair.create(-110, intentSender2);
                            }
                            Log.w("PackageManager", "This admin app installation is allowed");
                        }
                    }
                }
            } catch (RemoteException unused2) {
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = (i2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
        boolean z4 = (i2 & 16384) != 0;
        try {
            boolean isPackageDeviceAdmin = this.mPm.isPackageDeviceAdmin(str, i);
            ProtectedPackages protectedPackages = this.mPm.mProtectedPackages;
            boolean z5 = protectedPackages != null && protectedPackages.isPackageStateProtected(i, str);
            synchronized (this.mPm.mLock) {
                Computer snapshotComputer2 = this.mPm.snapshotComputer();
                PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr != null && packageLPr.getPkg() != null) {
                    if (z3 && (packageLPr.isSystem() || packageLPr.isUpdatedSystemApp() || isPackageDeviceAdmin || z5)) {
                        return Pair.create(-3, intentSender2);
                    }
                    int checkIfInstallExistingAllowed = MultiUserInstallPolicy.checkIfInstallExistingAllowed(MetaDataHelper.getAppMetaBundle(packageLPr), i);
                    if (checkIfInstallExistingAllowed != 0) {
                        Log.i("PackageManager", "Install failed. Not allowed to install due to " + MultiUserInstallPolicy.notAllowedReasonToString(checkIfInstallExistingAllowed) + " of Package " + str + " for User " + i);
                        return Pair.create(-110, intentSender2);
                    }
                    if (PersonaServiceHelper.isSpfKnoxSupported() && PersonaServiceHelper.isDisallowedAppForKnox(packageLPr, i)) {
                        Log.d("PackageManager", "This app installation is not allowed");
                        return Pair.create(-110, intentSender2);
                    }
                    if (!snapshotComputer2.canViewInstantApps(callingUid, UserHandle.getUserId(callingUid))) {
                        boolean z6 = false;
                        for (int i5 : this.mPm.mUserManager.getUserIds()) {
                            z6 = !packageLPr.getInstantApp(i5);
                            if (z6) {
                                break;
                            }
                        }
                        if (!z6) {
                            return Pair.create(-3, intentSender2);
                        }
                    }
                    if (packageLPr.getInstalled(i)) {
                        z = false;
                        if (!z4 || !packageLPr.getInstantApp(i)) {
                            z2 = false;
                            ScanPackageUtils.setInstantAppForUser(this.mPm.mInjector, packageLPr, i, z3, z4);
                            if (z2) {
                                String str2 = packageLPr.getInstallSource().mUpdateOwnerPackageName;
                                DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) this.mInjector.getLocalService(DevicePolicyManagerInternal.class);
                                if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i)) {
                                    z = true;
                                }
                                if (!snapshotComputer.isCallerSameApp(str2, callingUid) && (!packageLPr.isSystem() || !z)) {
                                    packageLPr.setUpdateOwnerPackage(null);
                                }
                                if (packageLPr.getPkg() != null) {
                                    PermissionManagerServiceInternal.PackageInstalledParams.Builder builder = new PermissionManagerServiceInternal.PackageInstalledParams.Builder();
                                    if ((4194304 & i2) != 0) {
                                        builder.setAllowlistedRestrictedPermissions(packageLPr.getPkg().getRequestedPermissions());
                                    }
                                    this.mPm.mPermissionManager.onPackageInstalled(packageLPr.getPkg(), -1, builder.build(), i);
                                    synchronized (this.mPm.mInstallLock) {
                                        this.mAppDataHelper.prepareAppDataAfterInstallLIF(packageLPr.getPkg());
                                    }
                                }
                                PackageManagerService packageManagerService = this.mPm;
                                packageManagerService.sendPackageAddedForUser(packageManagerService.snapshotComputer(), str, packageLPr, i, 0);
                                synchronized (this.mPm.mLock) {
                                    this.mPm.updateSequenceNumberLP(packageLPr, new int[]{i});
                                }
                                restoreAndPostInstall(new InstallRequest(i, 1, packageLPr.getPkg(), new int[]{i}, new Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        InstallPackageHelper.this.lambda$installExistingPackageAsUser$1(str, i, intentSender2);
                                    }
                                }));
                                intentSender2 = null;
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            if (PersonaServiceHelper.isSpfKnoxSupported() || this.mPm.getPersonaService() == null || this.mPm.getPersonaService().getSeparationConfigfromCache() == null || !((i == 0 || i == this.mPm.getPersonaService().getAppSeparationId()) && this.mPm.getPersonaService().isAppSeparationApp(str))) {
                                i4 = 1;
                            } else {
                                i4 = 1;
                                if (this.mPm.getPersonaService().processAppSeparationInstallation(str) != 1) {
                                    return Pair.create(-110, intentSender2);
                                }
                            }
                            PmHook.installSuccesLog(str, i);
                            return Pair.create(Integer.valueOf(i4), intentSender2);
                        }
                    } else {
                        packageLPr.setInstalled(true, i);
                        z = false;
                        packageLPr.setHidden(false, i);
                        packageLPr.setInstallReason(i3, i);
                        packageLPr.setUninstallReason(0, i);
                        packageLPr.setFirstInstallTime(System.currentTimeMillis(), i);
                        this.mPm.mSettings.writePackageRestrictionsLPr(i);
                        this.mPm.mSettings.writeKernelMappingLPr(packageLPr);
                    }
                    z2 = true;
                    ScanPackageUtils.setInstantAppForUser(this.mPm.mInjector, packageLPr, i, z3, z4);
                    if (z2) {
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (PersonaServiceHelper.isSpfKnoxSupported()) {
                    }
                    i4 = 1;
                    PmHook.installSuccesLog(str, i);
                    return Pair.create(Integer.valueOf(i4), intentSender2);
                }
                return Pair.create(-3, intentSender2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$installExistingPackageAsUser$1(String str, int i, IntentSender intentSender) {
        this.mPm.restorePermissionsAndUpdateRolesForNewUserInstall(str, i);
        if (intentSender != null) {
            onInstallComplete(1, this.mContext, intentSender);
        }
    }

    public static void onInstallComplete(int i, Context context, IntentSender intentSender) {
        Intent intent = new Intent();
        intent.putExtra("android.content.pm.extra.STATUS", PackageManager.installStatusToPublicStatus(i));
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public void restoreAndPostInstall(InstallRequest installRequest) {
        int userId = installRequest.getUserId();
        boolean isUpdate = installRequest.isUpdate();
        boolean z = (isUpdate || installRequest.getPkg() == null) ? false : true;
        PackageManagerService packageManagerService = this.mPm;
        if (packageManagerService.mNextInstallToken < 0) {
            packageManagerService.mNextInstallToken = 1;
        }
        int i = packageManagerService.mNextInstallToken;
        packageManagerService.mNextInstallToken = i + 1;
        packageManagerService.mRunningInstalls.put(i, installRequest);
        if (installRequest.getReturnCode() == 1 && z) {
            installRequest.closeFreezer();
            z = performBackupManagerRestore(userId, i, installRequest);
        }
        if (installRequest.getReturnCode() == 1 && !z && isUpdate) {
            z = performRollbackManagerRestore(userId, i, installRequest);
        }
        if (z) {
            return;
        }
        Trace.asyncTraceBegin(262144L, "postInstall", i);
        this.mPm.mHandler.sendMessage(this.mPm.mHandler.obtainMessage(9, i, 0));
    }

    public final boolean performBackupManagerRestore(int i, int i2, InstallRequest installRequest) {
        if (installRequest.getPkg() == null) {
            return false;
        }
        IBackupManager iBackupManager = this.mInjector.getIBackupManager();
        if (iBackupManager != null) {
            if (i == -1) {
                i = 0;
            }
            Trace.asyncTraceBegin(262144L, "restore", i2);
            try {
                if (iBackupManager.isUserReadyForBackup(i)) {
                    iBackupManager.restoreAtInstallForUser(i, installRequest.getPkg().getPackageName(), i2);
                    return true;
                }
                Slog.w("PackageManager", "User " + i + " is not ready. Restore at install didn't take place.");
                return false;
            } catch (RemoteException unused) {
                return true;
            } catch (Exception e) {
                Slog.e("PackageManager", "Exception trying to enqueue restore", e);
                return false;
            }
        }
        Slog.e("PackageManager", "Backup Manager not found!");
        return false;
    }

    public final boolean performRollbackManagerRestore(int i, int i2, InstallRequest installRequest) {
        PackageSetting packageLPr;
        int[] iArr;
        long j;
        int i3;
        if (installRequest.getPkg() == null) {
            return false;
        }
        String packageName = installRequest.getPkg().getPackageName();
        int[] userIds = this.mPm.mUserManager.getUserIds();
        synchronized (this.mPm.mLock) {
            packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
            if (packageLPr != null) {
                i3 = packageLPr.getAppId();
                j = packageLPr.getCeDataInode(i);
                iArr = packageLPr.queryInstalledUsers(userIds, true);
            } else {
                iArr = new int[0];
                j = -1;
                i3 = -1;
            }
        }
        int installFlags = installRequest.getInstallFlags();
        boolean z = ((262144 & installFlags) == 0 && (installFlags & 128) == 0) ? false : true;
        if (packageLPr == null || !z) {
            return false;
        }
        ((RollbackManagerInternal) this.mInjector.getLocalService(RollbackManagerInternal.class)).snapshotAndRestoreUserData(packageName, UserHandle.toUserHandles(iArr), i3, j, packageLPr.getSeInfo(), i2);
        return true;
    }

    public void installPackagesTraced(List list) {
        synchronized (this.mPm.mInstallLock) {
            try {
                Trace.traceBegin(262144L, "installPackages");
                installPackagesLI(list);
            } finally {
                Trace.traceEnd(262144L);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x04f8  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x05a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x054c  */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v6, types: [long] */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [java.lang.Object, java.lang.String, long] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void installPackagesLI(List list) {
        long j;
        int i;
        long j2;
        InstallRequest installRequest;
        long j3;
        InstallRequest installRequest2;
        ArraySet arraySet = new ArraySet(list.size());
        ArrayMap arrayMap = new ArrayMap(list.size());
        ArrayMap arrayMap2 = new ArrayMap(list.size());
        ArrayMap arrayMap3 = new ArrayMap(list.size());
        int i2 = 1;
        long j4 = 262144;
        int i3 = 0;
        try {
            Trace.traceBegin(262144L, "installPackagesLI");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                try {
                    InstallRequest installRequest3 = (InstallRequest) it.next();
                    try {
                        Trace.traceBegin(j4, "preparePackage");
                        installRequest3.onPrepareStarted();
                        preparePackageLI(installRequest3);
                        installRequest3.onPrepareFinished();
                        Trace.traceEnd(j4);
                        ParsedPackage parsedPackage = installRequest3.getParsedPackage();
                        if (parsedPackage == null) {
                            installRequest3.setError(-116, "Failed to obtain package to scan");
                            Iterator it2 = list.iterator();
                            while (it2.hasNext()) {
                                InstallRequest installRequest4 = (InstallRequest) it2.next();
                                if (installRequest4.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest4.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                    cleanUpAppIdCreation(installRequest4);
                                }
                            }
                            Iterator it3 = list.iterator();
                            while (it3.hasNext()) {
                                InstallRequest installRequest5 = (InstallRequest) it3.next();
                                installRequest5.closeFreezer();
                                if (installRequest5.getReturnCode() == i2) {
                                    installRequest5.setReturnCode(i3);
                                }
                            }
                            PersonaServiceHelper.isSpfKnoxSupported();
                            Trace.traceEnd(j4);
                            return;
                        }
                        installRequest3.setReturnCode(i2);
                        String packageName = parsedPackage.getPackageName();
                        arrayMap2.put(packageName, installRequest3);
                        try {
                            installRequest3.onScanStarted();
                            j = packageName;
                            installRequest2 = installRequest3;
                            j2 = j4;
                            try {
                                installRequest2.setScanResult(scanPackageTracedLI(installRequest3.getParsedPackage(), installRequest3.getParseFlags(), installRequest3.getScanFlags(), System.currentTimeMillis(), installRequest3.getUser(), installRequest3.getAbiOverride()));
                                installRequest2.onScanFinished();
                                if (!arraySet.add(j)) {
                                    installRequest2.setError(-5, "Duplicate package " + ((String) j) + " in multi-package install request.");
                                    Iterator it4 = list.iterator();
                                    while (it4.hasNext()) {
                                        InstallRequest installRequest6 = (InstallRequest) it4.next();
                                        if (installRequest6.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest6.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                            cleanUpAppIdCreation(installRequest6);
                                        }
                                    }
                                    Iterator it5 = list.iterator();
                                    while (it5.hasNext()) {
                                        InstallRequest installRequest7 = (InstallRequest) it5.next();
                                        installRequest7.closeFreezer();
                                        if (installRequest7.getReturnCode() == 1) {
                                            installRequest7.setReturnCode(0);
                                        }
                                    }
                                    PersonaServiceHelper.isSpfKnoxSupported();
                                    return;
                                }
                                i = 0;
                                try {
                                    try {
                                        if (PackageBlockListPolicy.isRdu() && PackageBlockListPolicy.isBlocked(j)) {
                                            Log.d("PackageManager", "This package [" + ((String) j) + "] is forbidden to install");
                                            throw new PackageManagerException(-110, "This package " + ((String) j) + " is forbidden to install");
                                        }
                                        if (!checkNoAppStorageIsConsistent(installRequest2.getScanRequestOldPackage(), parsedPackage)) {
                                            installRequest2.setError(-7, "Update attempted to change value of android.internal.PROPERTY_NO_APP_DATA_STORAGE");
                                            Iterator it6 = list.iterator();
                                            while (it6.hasNext()) {
                                                InstallRequest installRequest8 = (InstallRequest) it6.next();
                                                if (installRequest8.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest8.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                                    cleanUpAppIdCreation(installRequest8);
                                                }
                                            }
                                            Iterator it7 = list.iterator();
                                            while (it7.hasNext()) {
                                                InstallRequest installRequest9 = (InstallRequest) it7.next();
                                                installRequest9.closeFreezer();
                                                if (installRequest9.getReturnCode() == 1) {
                                                    installRequest9.setReturnCode(0);
                                                }
                                            }
                                            PersonaServiceHelper.isSpfKnoxSupported();
                                            return;
                                        }
                                        if ((installRequest2.getScanFlags() & 67108864) != 0) {
                                            installRequest2.getScannedPackageSetting().setAppId(-1);
                                        } else {
                                            arrayMap3.put(j, Boolean.valueOf(optimisticallyRegisterAppId(installRequest2)));
                                        }
                                        arrayMap.put(j, this.mPm.getSettingsVersionForPackage(parsedPackage));
                                        i3 = 0;
                                        j4 = j2;
                                        i2 = 1;
                                    } catch (PackageManagerException e) {
                                        e = e;
                                        installRequest2.setError("Scanning Failed.", e);
                                        Iterator it8 = list.iterator();
                                        while (it8.hasNext()) {
                                            InstallRequest installRequest10 = (InstallRequest) it8.next();
                                            if (installRequest10.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest10.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                                cleanUpAppIdCreation(installRequest10);
                                            }
                                        }
                                        Iterator it9 = list.iterator();
                                        while (it9.hasNext()) {
                                            InstallRequest installRequest11 = (InstallRequest) it9.next();
                                            installRequest11.closeFreezer();
                                            if (installRequest11.getReturnCode() == 1) {
                                                installRequest11.setReturnCode(i);
                                            }
                                        }
                                        PersonaServiceHelper.isSpfKnoxSupported();
                                        Trace.traceEnd(j2);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    i3 = i;
                                    j = j2;
                                    if (i3 == 0) {
                                    }
                                    if (PersonaServiceHelper.isSpfKnoxSupported()) {
                                        while (r1.hasNext()) {
                                        }
                                    }
                                    throw th;
                                }
                            } catch (PackageManagerException e2) {
                                e = e2;
                                i = 0;
                            } catch (Throwable th2) {
                                th = th2;
                                i = 0;
                                i3 = i;
                                j = j2;
                                if (i3 == 0) {
                                }
                                if (PersonaServiceHelper.isSpfKnoxSupported()) {
                                }
                                throw th;
                            }
                        } catch (PackageManagerException e3) {
                            e = e3;
                            installRequest2 = installRequest3;
                            j2 = j4;
                            i = i3;
                        }
                    } catch (PrepareFailure e4) {
                        installRequest = installRequest3;
                        j3 = j4;
                        int i4 = i3;
                        try {
                            installRequest.setError(e4.error, e4.getMessage());
                            installRequest.setOriginPackage(e4.mConflictingPackage);
                            installRequest.setOriginPermission(e4.mConflictingPermission);
                            installRequest.onPrepareFinished();
                            Trace.traceEnd(j3);
                            Iterator it10 = list.iterator();
                            while (it10.hasNext()) {
                                InstallRequest installRequest12 = (InstallRequest) it10.next();
                                if (installRequest12.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest12.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                    cleanUpAppIdCreation(installRequest12);
                                }
                            }
                            Iterator it11 = list.iterator();
                            while (it11.hasNext()) {
                                InstallRequest installRequest13 = (InstallRequest) it11.next();
                                installRequest13.closeFreezer();
                                if (installRequest13.getReturnCode() == 1) {
                                    installRequest13.setReturnCode(i4);
                                }
                            }
                            PersonaServiceHelper.isSpfKnoxSupported();
                            return;
                        } catch (Throwable th3) {
                            th = th3;
                            installRequest.onPrepareFinished();
                            Trace.traceEnd(j3);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        installRequest = installRequest3;
                        j3 = j4;
                        installRequest.onPrepareFinished();
                        Trace.traceEnd(j3);
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    j2 = j4;
                    i = i3;
                }
            }
            long j5 = j4;
            i = i3;
            try {
                try {
                    try {
                        synchronized (this.mPm.mLock) {
                            j = j5;
                            try {
                                Trace.traceBegin(j, "reconcilePackages");
                                List reconcilePackages = ReconcilePackageUtils.reconcilePackages(list, Collections.unmodifiableMap(this.mPm.mPackages), arrayMap, this.mSharedLibraries, this.mPm.mSettings.getKeySetManagerService(), this.mPm.mSettings);
                                try {
                                    Trace.traceBegin(j, "commitPackages");
                                    commitPackagesLocked(reconcilePackages, this.mPm.mUserManager.getUserIds());
                                    try {
                                        Trace.traceEnd(j);
                                        try {
                                            Iterator it12 = reconcilePackages.iterator();
                                            while (it12.hasNext()) {
                                                InstallRequest installRequest14 = ((ReconciledPackage) it12.next()).mInstallRequest;
                                                String packageName2 = installRequest14.getParsedPackage().getPackageName();
                                                int identifier = installRequest14.getUser().getIdentifier();
                                                if (installRequest14.getReturnCode() == 1) {
                                                    PmHook.installSuccesLog(packageName2, identifier);
                                                }
                                            }
                                            executePostCommitStepsLIF(reconcilePackages);
                                            Iterator it13 = list.iterator();
                                            while (it13.hasNext()) {
                                                InstallRequest installRequest15 = (InstallRequest) it13.next();
                                                if (installRequest15.getDataLoaderType() == 2 && installRequest15.getSignatureSchemeVersion() == 4) {
                                                    String baseApkPath = installRequest15.getPkg().getBaseApkPath();
                                                    String[] splitCodePaths = installRequest15.getPkg().getSplitCodePaths();
                                                    Uri originUri = installRequest15.getOriginUri();
                                                    PackageManagerService packageManagerService = this.mPm;
                                                    int i5 = packageManagerService.mPendingVerificationToken;
                                                    packageManagerService.mPendingVerificationToken = i5 + 1;
                                                    VerificationUtils.broadcastPackageVerified(i5, originUri, 1, PackageManagerServiceUtils.buildVerificationRootHashString(baseApkPath, splitCodePaths), installRequest15.getDataLoaderType(), installRequest15.getUser(), this.mContext);
                                                }
                                            }
                                            if (PersonaServiceHelper.isSpfKnoxSupported() && this.mPm.getPersonaService() != null && this.mPm.getPersonaService().getSeparationConfigfromCache() != null) {
                                                for (Map.Entry entry : arrayMap2.entrySet()) {
                                                    String str = (String) entry.getKey();
                                                    if (((InstallRequest) entry.getValue()).getReturnCode() == 1) {
                                                        this.mPm.getPersonaService().processAppSeparationInstallation(str);
                                                    }
                                                }
                                            }
                                        } catch (Throwable th6) {
                                            th = th6;
                                            i3 = 1;
                                            if (i3 == 0) {
                                            }
                                            if (PersonaServiceHelper.isSpfKnoxSupported()) {
                                            }
                                            throw th;
                                        }
                                    } catch (Throwable th7) {
                                        th = th7;
                                        i3 = 1;
                                        j = j;
                                        while (true) {
                                            try {
                                                try {
                                                    throw th;
                                                } catch (Throwable th8) {
                                                    th = th8;
                                                    if (i3 == 0) {
                                                        Iterator it14 = list.iterator();
                                                        while (it14.hasNext()) {
                                                            InstallRequest installRequest16 = (InstallRequest) it14.next();
                                                            if (installRequest16.getDataLoaderType() == 2 && installRequest16.getSignatureSchemeVersion() == 4) {
                                                                String baseApkPath2 = installRequest16.getPkg().getBaseApkPath();
                                                                String[] splitCodePaths2 = installRequest16.getPkg().getSplitCodePaths();
                                                                Uri originUri2 = installRequest16.getOriginUri();
                                                                PackageManagerService packageManagerService2 = this.mPm;
                                                                int i6 = packageManagerService2.mPendingVerificationToken;
                                                                packageManagerService2.mPendingVerificationToken = i6 + 1;
                                                                VerificationUtils.broadcastPackageVerified(i6, originUri2, 1, PackageManagerServiceUtils.buildVerificationRootHashString(baseApkPath2, splitCodePaths2), installRequest16.getDataLoaderType(), installRequest16.getUser(), this.mContext);
                                                            }
                                                        }
                                                    } else {
                                                        Iterator it15 = list.iterator();
                                                        while (it15.hasNext()) {
                                                            InstallRequest installRequest17 = (InstallRequest) it15.next();
                                                            if (installRequest17.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest17.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                                                cleanUpAppIdCreation(installRequest17);
                                                            }
                                                        }
                                                        Iterator it16 = list.iterator();
                                                        while (it16.hasNext()) {
                                                            InstallRequest installRequest18 = (InstallRequest) it16.next();
                                                            installRequest18.closeFreezer();
                                                            if (installRequest18.getReturnCode() == 1) {
                                                                installRequest18.setReturnCode(i);
                                                            }
                                                        }
                                                    }
                                                    if (PersonaServiceHelper.isSpfKnoxSupported() && i3 != 0 && this.mPm.getPersonaService() != null && this.mPm.getPersonaService().getSeparationConfigfromCache() != null) {
                                                        for (Map.Entry entry2 : arrayMap2.entrySet()) {
                                                            String str2 = (String) entry2.getKey();
                                                            if (((InstallRequest) entry2.getValue()).getReturnCode() == 1) {
                                                                this.mPm.getPersonaService().processAppSeparationInstallation(str2);
                                                            }
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th9) {
                                                th = th9;
                                            }
                                        }
                                    }
                                } finally {
                                    Trace.traceEnd(j);
                                }
                            } catch (ReconcileFailure e5) {
                                Iterator it17 = list.iterator();
                                while (it17.hasNext()) {
                                    ((InstallRequest) it17.next()).setError("Reconciliation failed...", e5);
                                }
                                Trace.traceEnd(j);
                                Iterator it18 = list.iterator();
                                while (it18.hasNext()) {
                                    InstallRequest installRequest19 = (InstallRequest) it18.next();
                                    if (installRequest19.getParsedPackage() != null && ((Boolean) arrayMap3.getOrDefault(installRequest19.getParsedPackage().getPackageName(), Boolean.FALSE)).booleanValue()) {
                                        cleanUpAppIdCreation(installRequest19);
                                    }
                                }
                                Iterator it19 = list.iterator();
                                while (it19.hasNext()) {
                                    InstallRequest installRequest20 = (InstallRequest) it19.next();
                                    installRequest20.closeFreezer();
                                    if (installRequest20.getReturnCode() == 1) {
                                        installRequest20.setReturnCode(i);
                                    }
                                }
                                PersonaServiceHelper.isSpfKnoxSupported();
                            }
                        }
                    } catch (Throwable th10) {
                        throw th10;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    i3 = i;
                    j = j;
                }
            } catch (Throwable th12) {
                th = th12;
                j = j5;
                i3 = i;
            }
        } catch (Throwable th13) {
            th = th13;
            j = j4;
            i = i3;
        }
    }

    public final boolean checkNoAppStorageIsConsistent(AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        if (androidPackage == null) {
            return true;
        }
        PackageManager.Property property = (PackageManager.Property) androidPackage.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        PackageManager.Property property2 = (PackageManager.Property) androidPackage2.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        return (property == null || !property.getBoolean()) ? property2 == null || !property2.getBoolean() : property2 != null && property2.getBoolean();
    }

    /* JADX WARN: Code restructure failed: missing block: B:263:0x08f3, code lost:
    
        if (r1 != false) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x08f5, code lost:
    
        if (r19 != false) goto L343;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x0901, code lost:
    
        throw new com.android.server.pm.PrepareFailure(-116, "Cannot update a system app with an instant app");
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0902, code lost:
    
        com.android.server.pm.PmHook.installFailLog(r34.mContext, r3, r35.getUser().getIdentifier());
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0918, code lost:
    
        throw new com.android.server.pm.PrepareFailure(-19, "Cannot install updates to system apps on sdcard");
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x0dae, code lost:
    
        r3 = r8;
        r5 = null;
        r8 = false;
        r16 = null;
        r24 = null;
        r17 = r17;
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0921  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x09bd  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0d8d  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0e43  */
    /* JADX WARN: Removed duplicated region for block: B:315:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0a22 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0b86 A[Catch: all -> 0x0b8b, TRY_ENTER, TRY_LEAVE, TryCatch #34 {all -> 0x0b8b, blocks: (B:493:0x0b1b, B:495:0x0b60, B:496:0x0b77, B:501:0x0b30, B:502:0x0b34, B:504:0x0b3e, B:372:0x0b86, B:375:0x0b95, B:380:0x0ba8, B:383:0x0baf, B:384:0x0bd3, B:392:0x0be8, B:394:0x0bef, B:396:0x0bf3, B:398:0x0bfd, B:400:0x0c02, B:401:0x0c27, B:403:0x0c28, B:406:0x0c33, B:407:0x0c5c, B:505:0x0b46, B:506:0x0b5d), top: B:492:0x0b1b }] */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0b95 A[Catch: all -> 0x0b8b, TRY_ENTER, TRY_LEAVE, TryCatch #34 {all -> 0x0b8b, blocks: (B:493:0x0b1b, B:495:0x0b60, B:496:0x0b77, B:501:0x0b30, B:502:0x0b34, B:504:0x0b3e, B:372:0x0b86, B:375:0x0b95, B:380:0x0ba8, B:383:0x0baf, B:384:0x0bd3, B:392:0x0be8, B:394:0x0bef, B:396:0x0bf3, B:398:0x0bfd, B:400:0x0c02, B:401:0x0c27, B:403:0x0c28, B:406:0x0c33, B:407:0x0c5c, B:505:0x0b46, B:506:0x0b5d), top: B:492:0x0b1b }] */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0ba2 A[Catch: all -> 0x0d66, TRY_LEAVE, TryCatch #3 {all -> 0x0d66, blocks: (B:370:0x0b80, B:373:0x0b8f, B:376:0x0b9c, B:378:0x0ba2, B:385:0x0bd4, B:390:0x0be2, B:408:0x0c5d), top: B:369:0x0b80 }] */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0d38  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x0b9a  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0b8d  */
    /* JADX WARN: Removed duplicated region for block: B:561:0x09f0  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x0964  */
    /* JADX WARN: Removed duplicated region for block: B:650:0x03de A[Catch: all -> 0x0e6f, TryCatch #9 {, blocks: (B:643:0x03ab, B:645:0x03bd, B:647:0x03c7, B:650:0x03de, B:654:0x03f9, B:655:0x042a, B:656:0x042b, B:658:0x042f, B:662:0x044c, B:664:0x0454, B:667:0x0465, B:669:0x046b, B:673:0x0474, B:674:0x0494, B:146:0x049f, B:148:0x04a9, B:150:0x04af, B:152:0x04bb, B:154:0x04c1, B:155:0x04d6, B:157:0x04dc, B:159:0x04e4, B:161:0x04f2, B:163:0x050a, B:167:0x0586, B:168:0x059f, B:170:0x05aa, B:172:0x05c5, B:174:0x05cb, B:176:0x05cf, B:180:0x05d9, B:185:0x05e0, B:186:0x062d, B:179:0x062e, B:190:0x0636, B:192:0x0646, B:195:0x0666, B:197:0x06a1, B:199:0x06ab, B:201:0x06b3, B:203:0x06e3, B:204:0x071b, B:205:0x071c, B:207:0x0728, B:209:0x0731, B:211:0x0737, B:212:0x0769, B:214:0x076f, B:218:0x0778, B:224:0x0798, B:226:0x07a5, B:228:0x07af, B:234:0x07b7, B:235:0x0815, B:238:0x0816, B:239:0x0867, B:220:0x0792, B:232:0x0868, B:248:0x0875, B:614:0x0514, B:615:0x0534, B:618:0x0537, B:620:0x0562, B:621:0x0566, B:630:0x0573, B:634:0x0576, B:635:0x0581, B:679:0x03cf), top: B:642:0x03ab, inners: #20 }] */
    /* JADX WARN: Removed duplicated region for block: B:678:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:682:0x0e73  */
    /* JADX WARN: Type inference failed for: r17v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r34v0, types: [com.android.server.pm.InstallPackageHelper] */
    /* JADX WARN: Type inference failed for: r5v24, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v57 */
    /* JADX WARN: Type inference failed for: r5v58 */
    /* JADX WARN: Type inference failed for: r5v59 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void preparePackageLI(InstallRequest installRequest) {
        ParsedPackage parsePackage;
        String packageName;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        PackageFreezer packageFreezer;
        boolean z5;
        boolean z6;
        int i2;
        PackageSetting packageLPr;
        boolean z7;
        int i3;
        PackageFreezer packageFreezer2;
        int i4;
        Throwable th;
        int i5;
        PackageFreezer packageFreezer3;
        PackageSetting packageLPr2;
        Throwable th2;
        PackageSetting packageSetting;
        PackageFreezer packageFreezer4;
        String sharedUserId;
        String sharedUserId2;
        AndroidPackage androidPackage;
        PackageSetting packageSetting2;
        int i6;
        boolean z8;
        PackageFreezer packageFreezer5;
        PackageFreezer packageFreezer6;
        IApplicationPolicy asInterface;
        boolean z9;
        boolean z10;
        SharedLibraryInfo latestStaticSharedLibraVersion;
        WatchedLongSparseArray sharedLibraryInfos;
        int verifyASKStokenForPackage;
        int installFlags = installRequest.getInstallFlags();
        boolean z11 = ((installFlags & 8) == 0 && installRequest.getVolumeUuid() == null) ? false : true;
        boolean z12 = (installFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
        boolean z13 = (installFlags & 16384) != 0;
        boolean z14 = (installFlags & 65536) != 0;
        boolean z15 = (installFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0;
        boolean z16 = installRequest.getInstallReason() == 5;
        int i7 = installRequest.isInstallMove() ? 518 : 6;
        if ((installFlags & IInstalld.FLAG_USE_QUOTA) != 0) {
            i7 |= 1024;
        }
        if (z12) {
            i7 |= IInstalld.FLAG_FORCE;
        }
        if (z13) {
            i7 |= 16384;
        }
        if (z14) {
            i7 |= 32768;
        }
        if (z15) {
            i7 |= 67108864;
        }
        File file = new File(z15 ? installRequest.getApexInfo().modulePath : installRequest.getCodePath());
        if (z12 && z11) {
            Slog.i("PackageManager", "Incompatible ephemeral install; external=" + z11);
            throw new PrepareFailure(-116);
        }
        int defParseFlags = this.mPm.getDefParseFlags() | Integer.MIN_VALUE | 64 | (z11 ? 8 : 0);
        boolean z17 = z16;
        Trace.traceBegin(262144L, "parsePackage");
        try {
            try {
                PackageParser2 preparingPackageParser = this.mPm.mInjector.getPreparingPackageParser();
                try {
                    parsePackage = preparingPackageParser.parsePackage(file, defParseFlags, false);
                    AndroidPackageUtils.validatePackageDexMetadata(parsePackage);
                    preparingPackageParser.close();
                    Trace.traceEnd(262144L);
                    int checkIfInstallAllowed = MultiUserInstallPolicy.checkIfInstallAllowed(parsePackage.getMetaData(), installRequest.getUserId(), new Function() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            UserInfo lambda$preparePackageLI$2;
                            lambda$preparePackageLI$2 = InstallPackageHelper.this.lambda$preparePackageLI$2((Integer) obj);
                            return lambda$preparePackageLI$2;
                        }
                    });
                    if (checkIfInstallAllowed != 0) {
                        String str = "Install failed. Not allowed to install due to " + MultiUserInstallPolicy.notAllowedReasonToString(checkIfInstallAllowed) + " for User " + installRequest.getUserId();
                        Log.i("PackageManager", str);
                        throw new PrepareFailure(-110, str);
                    }
                    boolean z18 = (16777216 & installFlags) != 0;
                    if (!z18 && parsePackage.isTestOnly()) {
                        z18 = true;
                    }
                    if (!z18 && parsePackage.getTargetSdkVersion() < 23) {
                        Slog.w("PackageManager", "App " + parsePackage.getPackageName() + " targets deprecated sdk version");
                        throw new PrepareFailure(-29, "App package must target at least SDK version 23, but found " + parsePackage.getTargetSdkVersion());
                    }
                    if (z12) {
                        if (parsePackage.getTargetSdkVersion() < 26) {
                            Slog.w("PackageManager", "Instant app package " + parsePackage.getPackageName() + " does not target at least O");
                            throw new PrepareFailure(-116, "Instant app package must target at least O");
                        }
                        if (parsePackage.getSharedUserId() != null) {
                            Slog.w("PackageManager", "Instant app package " + parsePackage.getPackageName() + " may not declare sharedUserId.");
                            throw new PrepareFailure(-116, "Instant app package may not declare a sharedUserId");
                        }
                    }
                    if (parsePackage.isStaticSharedLibrary()) {
                        PackageManagerService.renameStaticSharedLibraryPackage(parsePackage);
                        if (z11) {
                            Slog.i("PackageManager", "Static shared libs can only be installed on internal storage.");
                            throw new PrepareFailure(-19, "Static shared libs can only be installed on internal storage.");
                        }
                    }
                    packageName = parsePackage.getPackageName();
                    installRequest.setName(packageName);
                    if (CoreRune.SYSFW_APP_SPEG && "com.samsung.speg".equals(packageName)) {
                        throw new PrepareFailure(-106, "Forbidden package name");
                    }
                    if (PersonaServiceHelper.isSpfKnoxSupported() && PersonaServiceHelper.isDisallowedAppForKnox(parsePackage.getMetaData(), installRequest.getUser().getIdentifier(), packageName)) {
                        Log.d("PackageManager", "This app installation is not allowed");
                        throw new PrepareFailure(-110, "This app installation is not allowed");
                    }
                    if (parsePackage.isTestOnly() && (installFlags & 4) == 0) {
                        throw new PrepareFailure(-15, "Failed to install test-only apk. Did you forget to add -t?");
                    }
                    if (installRequest.getSigningDetails() != SigningDetails.UNKNOWN) {
                        parsePackage.setSigningDetails(installRequest.getSigningDetails());
                    } else {
                        ParseResult signingDetails = ParsingPackageUtils.getSigningDetails((ParseInput) ParseTypeImpl.forDefaultParsing(), parsePackage, false);
                        if (signingDetails.isError()) {
                            throw new PrepareFailure("Failed collect during installPackageLI", signingDetails.getException());
                        }
                        parsePackage.setSigningDetails((SigningDetails) signingDetails.getResult());
                    }
                    AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("CollectCertificates %s succeeded", parsePackage.getPackageName()), "", installRequest.getUser().getIdentifier());
                    Log.w("PackageManager", "verifying app can be installed or not");
                    IApplicationPolicy asInterface2 = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
                    try {
                        if (asInterface2 != null && !asInterface2.isApplicationInstallationEnabled(packageName, parsePackage.getSigningDetails().getSignatures(), parsePackage.getRequestedPermissions(), installRequest.getUser().getIdentifier())) {
                            Log.w("PackageManager", "This app installation is not allowed");
                            PmHook.auditLogInstallFail(parsePackage.getPackageName(), installRequest.getUser().getIdentifier(), false);
                            throw new PrepareFailure(-110, "This app installation is not allowed");
                        }
                    } catch (RemoteException unused) {
                    }
                    if (z12 && parsePackage.getSigningDetails().getSignatureSchemeVersion() < 2) {
                        Slog.w("PackageManager", "Instant app package " + parsePackage.getPackageName() + " is not signed with at least APK Signature Scheme v2");
                        throw new PrepareFailure(-116, "Instant app package must be signed with APK Signature Scheme v2 or greater");
                    }
                    boolean z19 = (installFlags & 128) != 0;
                    try {
                        InstallSource installSource = installRequest.getInstallSource();
                        verifyASKStokenForPackage = ASKSManager.getASKSManager().verifyASKStokenForPackage(packageName, parsePackage.getBaseApkPath(), parsePackage.getLongVersionCode(), parsePackage.getSigningDetails().getSignatures(), installRequest.getInstallerPackageName(), installSource != null ? installSource.mInitiatingPackageName : null, z19);
                    } catch (RemoteException e) {
                        Log.e("PackageManager", "RemoteException: " + e.getMessage());
                    }
                    if (verifyASKStokenForPackage != -1) {
                        installRequest.setReturnCode(verifyASKStokenForPackage);
                        throw new PrepareFailure(verifyASKStokenForPackage, ASKSManager.getASKSerrorDetail(verifyASKStokenForPackage));
                    }
                    try {
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if ("com.sec.android.easyMover".equals(packageName) && ("com.android.vending".equals(installRequest.getInstallerPackageName()) || "com.sec.android.app.samsungapps".equals(installRequest.getInstallerPackageName()))) {
                        if ("TRUE".equals(this.mContext.getContentResolver().getType(Uri.parse("content://com.sec.android.easyMover.statusProvider/isOOBERunning")))) {
                            Log.w("PackageManager", "This SmartSwitch installation is not allowed due to app is running in OOBE");
                            z = true;
                            if (!z) {
                                PmHook.installFailLog(this.mContext, parsePackage, installRequest.getUser().getIdentifier());
                                throw new PrepareFailure(-110, "This SmartSwitch installation is not allowed due to app is running in OOBE");
                            }
                            synchronized (this.mPm.mLock) {
                                if ((installFlags & 2) != 0) {
                                    String renamedPackageLPr = this.mPm.mSettings.getRenamedPackageLPr(packageName);
                                    if (parsePackage.getOriginalPackages().contains(renamedPackageLPr) && this.mPm.mPackages.containsKey(renamedPackageLPr)) {
                                        parsePackage.setPackageName(renamedPackageLPr);
                                        packageName = parsePackage.getPackageName();
                                    } else if (!this.mPm.mPackages.containsKey(packageName)) {
                                        z2 = false;
                                        if (z2) {
                                            z3 = z2;
                                        } else {
                                            AndroidPackage androidPackage2 = (AndroidPackage) this.mPm.mPackages.get(packageName);
                                            int targetSdkVersion = androidPackage2.getTargetSdkVersion();
                                            int targetSdkVersion2 = parsePackage.getTargetSdkVersion();
                                            z3 = z2;
                                            if (targetSdkVersion > 22 && targetSdkVersion2 <= 22) {
                                                throw new PrepareFailure(-26, "Package " + parsePackage.getPackageName() + " new target SDK " + targetSdkVersion2 + " doesn't support runtime permissions but the old target SDK " + targetSdkVersion + " does.");
                                            }
                                            boolean z20 = !Build.IS_USER && ((PackageManagerInternal) this.mInjector.getLocalService(PackageManagerInternal.class)).getSystemUiServiceComponent().getPackageName().equals(packageName);
                                            if (packageName != null && packageName.startsWith("com.salab.issuetracker") && ((PackageManagerInternal) this.mInjector.getLocalService(PackageManagerInternal.class)).isPlatformSigned(packageName)) {
                                                z20 = true;
                                            }
                                            if (androidPackage2.isPersistent()) {
                                                if ((installFlags & 2097152) == 0 && !z20) {
                                                    throw new PrepareFailure(-2, "Package " + androidPackage2.getPackageName() + " is a persistent app. Persistent apps are not updateable.");
                                                }
                                                z4 = z3;
                                            }
                                        }
                                        z4 = z3;
                                    }
                                    z2 = true;
                                    if (z2) {
                                    }
                                    z4 = z3;
                                } else {
                                    z4 = false;
                                }
                                PackageSetting packageLPr3 = this.mPm.mSettings.getPackageLPr(packageName);
                                PackageSetting packageLPr4 = (packageLPr3 != null || !parsePackage.isSdkLibrary() || (sharedLibraryInfos = this.mSharedLibraries.getSharedLibraryInfos(parsePackage.getSdkLibraryName())) == null || sharedLibraryInfos.size() <= 0) ? packageLPr3 : this.mPm.mSettings.getPackageLPr(((SharedLibraryInfo) sharedLibraryInfos.valueAt(0)).getPackageName());
                                if (parsePackage.isStaticSharedLibrary() && (latestStaticSharedLibraVersion = this.mSharedLibraries.getLatestStaticSharedLibraVersion(parsePackage)) != null) {
                                    packageLPr4 = this.mPm.mSettings.getPackageLPr(latestStaticSharedLibraVersion.getPackageName());
                                }
                                if (packageLPr4 != null) {
                                    KeySetManagerService keySetManagerService = this.mPm.mSettings.getKeySetManagerService();
                                    SharedUserSetting sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr4);
                                    ?? shouldCheckUpgradeKeySetLocked = keySetManagerService.shouldCheckUpgradeKeySetLocked(packageLPr4, sharedUserSettingLPr, i7);
                                    if (shouldCheckUpgradeKeySetLocked != 0) {
                                        if (!keySetManagerService.checkUpgradeKeySetLocked(packageLPr4, parsePackage)) {
                                            throw new PrepareFailure(-7, "Package " + parsePackage.getPackageName() + " upgrade keys do not match the previously installed version");
                                        }
                                        i = defParseFlags;
                                        packageFreezer = shouldCheckUpgradeKeySetLocked;
                                    } else {
                                        try {
                                            i = defParseFlags;
                                            packageFreezer = null;
                                            if (PackageManagerServiceUtils.verifySignatures(packageLPr4, sharedUserSettingLPr, null, parsePackage.getSigningDetails(), ReconcilePackageUtils.isCompatSignatureUpdateNeeded(this.mPm.getSettingsVersionForPackage(parsePackage)), ReconcilePackageUtils.isRecoverSignatureUpdateNeeded(this.mPm.getSettingsVersionForPackage(parsePackage)), z17)) {
                                                synchronized (this.mPm.mLock) {
                                                    keySetManagerService.removeAppKeySetDataLPw(parsePackage.getPackageName());
                                                }
                                                packageFreezer = null;
                                            }
                                        } catch (PackageManagerException e3) {
                                            throw new PrepareFailure(e3.error, e3.getMessage());
                                        }
                                    }
                                } else {
                                    i = defParseFlags;
                                }
                                if (packageLPr3 != null) {
                                    boolean isSystem = packageLPr3.isSystem();
                                    installRequest.setOriginUsers(packageLPr3.queryInstalledUsers(this.mPm.mUserManager.getUserIds(), true));
                                    z5 = isSystem;
                                } else {
                                    z5 = false;
                                }
                                int size = ArrayUtils.size(parsePackage.getPermissionGroups());
                                int i8 = 0;
                                while (i8 < size) {
                                    ParsedPermissionGroup parsedPermissionGroup = (ParsedPermissionGroup) parsePackage.getPermissionGroups().get(i8);
                                    int i9 = installFlags;
                                    boolean z21 = z15;
                                    PermissionGroupInfo permissionGroupInfo = this.mPm.getPermissionGroupInfo(parsedPermissionGroup.getName(), 0);
                                    if (permissionGroupInfo != null && cannotInstallWithBadPermissionGroups(parsePackage)) {
                                        String str2 = permissionGroupInfo.packageName;
                                        if ((z4 || !parsePackage.getPackageName().equals(str2)) && !doesSignatureMatchForPermissions(str2, parsePackage, i7)) {
                                            EventLog.writeEvent(1397638484, "146211400", -1, parsePackage.getPackageName());
                                            throw new PrepareFailure(-126, "Package " + parsePackage.getPackageName() + " attempting to redeclare permission group " + parsedPermissionGroup.getName() + " already owned by " + str2);
                                        }
                                    }
                                    i8++;
                                    installFlags = i9;
                                    z15 = z21;
                                }
                                z6 = z15;
                                i2 = installFlags;
                                int size2 = ArrayUtils.size(parsePackage.getPermissions()) - 1;
                                while (size2 >= 0) {
                                    ParsedPermission parsedPermission = (ParsedPermission) parsePackage.getPermissions().get(size2);
                                    Permission permissionTEMP = this.mPm.mPermissionManager.getPermissionTEMP(parsedPermission.getName());
                                    if ((parsedPermission.getProtectionLevel() & IInstalld.FLAG_USE_QUOTA) == 0 || z5) {
                                        z9 = z12;
                                    } else {
                                        StringBuilder sb = new StringBuilder();
                                        z9 = z12;
                                        sb.append("Non-System package ");
                                        sb.append(parsePackage.getPackageName());
                                        sb.append(" attempting to delcare ephemeral permission ");
                                        sb.append(parsedPermission.getName());
                                        sb.append("; Removing ephemeral.");
                                        Slog.w("PackageManager", sb.toString());
                                        ComponentMutateUtils.setProtectionLevel(parsedPermission, parsedPermission.getProtectionLevel() & (-4097));
                                    }
                                    if (permissionTEMP != null) {
                                        String packageName2 = permissionTEMP.getPackageName();
                                        if (!doesSignatureMatchForPermissions(packageName2, parsePackage, i7)) {
                                            if (!packageName2.equals("android")) {
                                                throw new PrepareFailure(-112, "Package " + parsePackage.getPackageName() + " attempting to redeclare permission " + parsedPermission.getName() + " already owned by " + packageName2).conflictsWithExistingPermission(parsedPermission.getName(), packageName2);
                                            }
                                            Slog.w("PackageManager", "Package " + parsePackage.getPackageName() + " attempting to redeclare system permission " + parsedPermission.getName() + "; ignoring new declaration");
                                            parsePackage.removePermission(size2);
                                        } else if (!"android".equals(parsePackage.getPackageName()) && (parsedPermission.getProtectionLevel() & 15) == 1 && !permissionTEMP.isRuntime()) {
                                            Slog.w("PackageManager", "Package " + parsePackage.getPackageName() + " trying to change a non-runtime permission " + parsedPermission.getName() + " to runtime; keeping old protection level");
                                            ComponentMutateUtils.setProtectionLevel(parsedPermission, permissionTEMP.getProtectionLevel());
                                        }
                                    }
                                    if (parsedPermission.getGroup() != null && cannotInstallWithBadPermissionGroups(parsePackage)) {
                                        int i10 = 0;
                                        while (true) {
                                            if (i10 >= size) {
                                                z10 = false;
                                                break;
                                            } else {
                                                if (((ParsedPermissionGroup) parsePackage.getPermissionGroups().get(i10)).getName().equals(parsedPermission.getGroup())) {
                                                    z10 = true;
                                                    break;
                                                }
                                                i10++;
                                            }
                                        }
                                        if (z10) {
                                            continue;
                                        } else {
                                            PermissionGroupInfo permissionGroupInfo2 = this.mPm.getPermissionGroupInfo(parsedPermission.getGroup(), 0);
                                            if (permissionGroupInfo2 == null) {
                                                EventLog.writeEvent(1397638484, "146211400", -1, parsePackage.getPackageName());
                                                throw new PrepareFailure(-127, "Package " + parsePackage.getPackageName() + " attempting to declare permission " + parsedPermission.getName() + " in non-existing group " + parsedPermission.getGroup());
                                            }
                                            String str3 = permissionGroupInfo2.packageName;
                                            if (!"android".equals(str3) && !doesSignatureMatchForPermissions(str3, parsePackage, i7)) {
                                                EventLog.writeEvent(1397638484, "146211400", -1, parsePackage.getPackageName());
                                                throw new PrepareFailure(-127, "Package " + parsePackage.getPackageName() + " attempting to declare permission " + parsedPermission.getName() + " in group " + parsedPermission.getGroup() + " owned by package " + str3 + " with incompatible certificate");
                                            }
                                        }
                                    }
                                    size2--;
                                    z12 = z9;
                                }
                                boolean z22 = z12;
                            }
                            if (z4 && (asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"))) != null) {
                                try {
                                    if (!asInterface.isPackageUpdateAllowed(packageName, true)) {
                                        Log.w("PackageManager", "This app replacing is not allowed by MDM policy");
                                        installRequest.setReturnCode(-110);
                                        return;
                                    }
                                } catch (Exception unused2) {
                                    Log.w("PackageManager", "MDM hidden api called from non system uid");
                                }
                            }
                            IRestrictionPolicy asInterface3 = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                            if (asInterface3 != null) {
                                try {
                                    if (!asInterface3.isNewAdminInstallationEnabledAsUser(installRequest.getUser().getIdentifier(), false) && isAdminApplication(parsePackage) && !asInterface3.checkPackageSource(installRequest.getUser().getIdentifier(), installRequest.getInstallerPackageName())) {
                                        Log.w("PackageManager", "This admin app installation is not allowed");
                                        asInterface3.showRestrictionToast(this.mContext.getString(17042444));
                                        throw new PrepareFailure(-110, "Install fail, This admin app installation is not allowed");
                                    }
                                } catch (RemoteException unused3) {
                                }
                            }
                            if (!installRequest.isInstallMove()) {
                                int i11 = i7 | 1 | 256;
                                synchronized (this.mPm.mLock) {
                                    PackageSetting packageLPr5 = this.mPm.mSettings.getPackageLPr(packageName);
                                    if (packageLPr5 == null) {
                                        installRequest.setError(PackageManagerException.ofInternalError("Missing settings for moved package " + packageName, -3));
                                    }
                                    parsePackage.setPrimaryCpuAbi(packageLPr5.getPrimaryCpuAbiLegacy()).setSecondaryCpuAbi(packageLPr5.getSecondaryCpuAbiLegacy());
                                }
                                i3 = i11;
                                packageFreezer2 = packageFreezer;
                            } else {
                                int i12 = i7 | 1;
                                try {
                                    synchronized (this.mPm.mLock) {
                                        packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
                                    }
                                    boolean z23 = packageLPr != null && packageLPr.isUpdatedSystemApp();
                                    String deriveAbiOverride = PackageManagerServiceUtils.deriveAbiOverride(installRequest.getAbiOverride());
                                    boolean z24 = packageLPr != null && packageLPr.isSystem();
                                    PackageAbiHelper packageAbiHelper = this.mPackageAbiHelper;
                                    if (!z23 && !z24) {
                                        z7 = false;
                                        ParsedPackage parsedPackage = parsePackage;
                                        Pair derivePackageAbi = packageAbiHelper.derivePackageAbi(parsedPackage, z5, z7, deriveAbiOverride, ScanPackageUtils.getAppLib32InstallDir());
                                        ((PackageAbiHelper.Abis) derivePackageAbi.first).applyTo(parsePackage);
                                        ((PackageAbiHelper.NativeLibraryPaths) derivePackageAbi.second).applyTo(parsePackage);
                                        i3 = i12;
                                        packageFreezer2 = parsedPackage;
                                    }
                                    z7 = true;
                                    ParsedPackage parsedPackage2 = parsePackage;
                                    Pair derivePackageAbi2 = packageAbiHelper.derivePackageAbi(parsedPackage2, z5, z7, deriveAbiOverride, ScanPackageUtils.getAppLib32InstallDir());
                                    ((PackageAbiHelper.Abis) derivePackageAbi2.first).applyTo(parsePackage);
                                    ((PackageAbiHelper.NativeLibraryPaths) derivePackageAbi2.second).applyTo(parsePackage);
                                    i3 = i12;
                                    packageFreezer2 = parsedPackage2;
                                } catch (PackageManagerException e4) {
                                    Slog.e("PackageManager", "Error deriving application ABI", e4);
                                    throw PrepareFailure.ofInternalError("Error deriving application ABI: " + e4.getMessage(), -4);
                                }
                            }
                            if (z6) {
                                if (AsecInstallHelper.installOnExternalAsec(installRequest.getInstallFlags())) {
                                    this.mPm.getAsecInstallHelper().doRenameAsec(installRequest, parsePackage);
                                } else {
                                    doRenameLI(installRequest, parsePackage);
                                }
                                try {
                                    setUpFsVerity(parsePackage);
                                } catch (Installer.InstallerException | IOException | DigestException | NoSuchAlgorithmException e5) {
                                    throw PrepareFailure.ofInternalError("Failed to set up verity: " + e5, -5);
                                }
                            } else {
                                parsePackage.setPath(installRequest.getApexInfo().modulePath);
                                parsePackage.setBaseApkPath(installRequest.getApexInfo().modulePath);
                            }
                            int i13 = i2;
                            ?? r5 = "installPackageLI";
                            PackageFreezer freezePackageForInstall = freezePackageForInstall(packageName, -1, i13, r5, 16);
                            try {
                                PmHook.beginInstallLog(parsePackage, installRequest.getUser().getIdentifier());
                            } catch (Throwable th3) {
                                th = th3;
                                r5 = freezePackageForInstall;
                                i4 = 1;
                            }
                            if (!z4) {
                                try {
                                    String packageName3 = parsePackage.getPackageName();
                                    synchronized (this.mPm.mLock) {
                                        try {
                                            try {
                                                packageLPr2 = this.mPm.mSettings.getPackageLPr(packageName3);
                                            } catch (Throwable th4) {
                                                th = th4;
                                                while (true) {
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th5) {
                                                        th = th5;
                                                    }
                                                }
                                            }
                                        } catch (Throwable th6) {
                                            th = th6;
                                        }
                                    }
                                    AndroidPackage androidPackage3 = packageLPr2.getAndroidPackage();
                                    if (parsePackage.isStaticSharedLibrary() && androidPackage3 != null && (i2 & 32) == 0) {
                                        try {
                                            throw new PrepareFailure(-5, "Packages declaring static-shared libs cannot be updated");
                                        } catch (Throwable th7) {
                                            th = th7;
                                            packageFreezer5 = freezePackageForInstall;
                                        }
                                    } else {
                                        boolean z25 = (i3 & IInstalld.FLAG_FORCE) != 0;
                                        try {
                                            synchronized (this.mPm.mLock) {
                                                try {
                                                    PackageSetting packageLPr6 = this.mPm.mSettings.getPackageLPr(packageName3);
                                                    PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageLPr6);
                                                    SharedUserSetting sharedUserSettingLPr2 = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr6);
                                                    KeySetManagerService keySetManagerService2 = this.mPm.mSettings.getKeySetManagerService();
                                                    try {
                                                        if (keySetManagerService2.shouldCheckUpgradeKeySetLocked(packageLPr6, sharedUserSettingLPr2, i3)) {
                                                            if (!keySetManagerService2.checkUpgradeKeySetLocked(packageLPr6, parsePackage)) {
                                                                throw new PrepareFailure(-7, "New package not signed by keys specified by upgrade-keysets: " + packageName3);
                                                            }
                                                            packageSetting = disabledSystemPkgLPr;
                                                        } else {
                                                            SigningDetails signingDetails2 = parsePackage.getSigningDetails();
                                                            SigningDetails signingDetails3 = androidPackage3.getSigningDetails();
                                                            packageSetting = disabledSystemPkgLPr;
                                                            try {
                                                                if (!signingDetails2.checkCapability(signingDetails3, 1) && !signingDetails3.checkCapability(signingDetails2, 8) && (!z17 || !signingDetails3.hasAncestorOrSelf(signingDetails2))) {
                                                                    throw new PrepareFailure(-7, "New package has a different signature: " + packageName3);
                                                                }
                                                            } catch (Throwable th8) {
                                                                th = th8;
                                                                th2 = th;
                                                                throw th2;
                                                            }
                                                        }
                                                        try {
                                                            if (androidPackage3.getRestrictUpdateHash() != null) {
                                                                try {
                                                                    if (packageLPr2.isSystem()) {
                                                                        try {
                                                                            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                                                                            updateDigest(messageDigest, new File(parsePackage.getBaseApkPath()));
                                                                            if (ArrayUtils.isEmpty(parsePackage.getSplitCodePaths())) {
                                                                                packageFreezer4 = freezePackageForInstall;
                                                                            } else {
                                                                                String[] splitCodePaths = parsePackage.getSplitCodePaths();
                                                                                int length = splitCodePaths.length;
                                                                                packageFreezer4 = freezePackageForInstall;
                                                                                int i14 = 0;
                                                                                while (i14 < length) {
                                                                                    int i15 = length;
                                                                                    try {
                                                                                        try {
                                                                                            updateDigest(messageDigest, new File(splitCodePaths[i14]));
                                                                                            i14++;
                                                                                            length = i15;
                                                                                            splitCodePaths = splitCodePaths;
                                                                                        } catch (IOException | NoSuchAlgorithmException unused4) {
                                                                                            throw new PrepareFailure(-2, "Could not compute hash: " + packageName3);
                                                                                        }
                                                                                    } catch (Throwable th9) {
                                                                                        th = th9;
                                                                                        th2 = th;
                                                                                        throw th2;
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!Arrays.equals(androidPackage3.getRestrictUpdateHash(), messageDigest.digest())) {
                                                                                throw new PrepareFailure(-2, "New package fails restrict-update check: " + packageName3);
                                                                            }
                                                                            parsePackage.setRestrictUpdateHash(androidPackage3.getRestrictUpdateHash());
                                                                            packageFreezer2 = packageFreezer4;
                                                                            sharedUserId = androidPackage3.getSharedUserId() == null ? androidPackage3.getSharedUserId() : "<nothing>";
                                                                            sharedUserId2 = parsePackage.getSharedUserId() == null ? parsePackage.getSharedUserId() : "<nothing>";
                                                                            if (sharedUserId.equals(sharedUserId2)) {
                                                                                throw new PrepareFailure(-24, "Package " + parsePackage.getPackageName() + " shared user changed from " + sharedUserId + " to " + sharedUserId2);
                                                                            }
                                                                            if (androidPackage3.isLeavingSharedUser() && !parsePackage.isLeavingSharedUser()) {
                                                                                throw new PrepareFailure(-24, "Package " + parsePackage.getPackageName() + " attempting to rejoin " + sharedUserId2);
                                                                            }
                                                                            int[] userIds = this.mPm.mUserManager.getUserIds();
                                                                            try {
                                                                                int[] queryInstalledUsers = packageLPr6.queryInstalledUsers(userIds, true);
                                                                                int[] queryInstalledUsers2 = packageLPr6.queryInstalledUsers(userIds, false);
                                                                                if (z25) {
                                                                                    if (installRequest.getUserId() == -1) {
                                                                                        int length2 = userIds.length;
                                                                                        int i16 = 0;
                                                                                        while (i16 < length2) {
                                                                                            int i17 = length2;
                                                                                            int i18 = userIds[i16];
                                                                                            if (!packageLPr6.getInstantApp(i18)) {
                                                                                                Slog.w("PackageManager", "Can't replace full app with instant app: " + packageName3 + " for user: " + i18);
                                                                                                throw new PrepareFailure(-116);
                                                                                            }
                                                                                            i16++;
                                                                                            length2 = i17;
                                                                                        }
                                                                                    } else if (!packageLPr6.getInstantApp(installRequest.getUserId())) {
                                                                                        Slog.w("PackageManager", "Can't replace full app with instant app: " + packageName3 + " for user: " + installRequest.getUserId());
                                                                                        throw new PrepareFailure(-116);
                                                                                    }
                                                                                }
                                                                                try {
                                                                                    PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo(this.mPm);
                                                                                    packageRemovedInfo.mUid = androidPackage3.getUid();
                                                                                    packageRemovedInfo.mRemovedPackage = androidPackage3.getPackageName();
                                                                                    packageRemovedInfo.mInstallerPackageName = packageLPr6.getInstallSource().mInstallerPackageName;
                                                                                    packageRemovedInfo.mIsStaticSharedLib = parsePackage.getStaticSharedLibraryName() != null;
                                                                                    packageRemovedInfo.mIsUpdate = true;
                                                                                    packageRemovedInfo.mOrigUsers = queryInstalledUsers;
                                                                                    packageRemovedInfo.mInstallReasons = new SparseIntArray(queryInstalledUsers.length);
                                                                                    for (int i19 : queryInstalledUsers) {
                                                                                        try {
                                                                                            packageRemovedInfo.mInstallReasons.put(i19, packageLPr6.getInstallReason(i19));
                                                                                        } catch (Throwable th10) {
                                                                                            th = th10;
                                                                                            packageFreezer5 = packageFreezer2;
                                                                                        }
                                                                                    }
                                                                                    packageRemovedInfo.mUninstallReasons = new SparseIntArray(queryInstalledUsers2.length);
                                                                                    for (int i20 : queryInstalledUsers2) {
                                                                                        packageRemovedInfo.mUninstallReasons.put(i20, packageLPr6.getUninstallReason(i20));
                                                                                    }
                                                                                    packageRemovedInfo.mIsExternal = androidPackage3.isExternalStorage();
                                                                                    packageRemovedInfo.mRemovedPackageVersionCode = androidPackage3.getLongVersionCode();
                                                                                    installRequest.setRemovedInfo(packageRemovedInfo);
                                                                                    boolean isSystem2 = packageLPr2.isSystem();
                                                                                    if (isSystem2) {
                                                                                        i3 = i3 | 65536 | (packageLPr2.isPrivileged() ? IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES : 0) | (packageLPr2.isOem() ? 262144 : 0) | (packageLPr2.isVendor() ? 524288 : 0) | (packageLPr2.isProduct() ? 1048576 : 0) | (packageLPr2.isOdm() ? 4194304 : 0) | (packageLPr2.isSystemExt() ? 2097152 : 0);
                                                                                        installRequest.setReturnCode(1);
                                                                                        installRequest.setApexModuleName(packageLPr2.getApexModuleName());
                                                                                    }
                                                                                    androidPackage = androidPackage3;
                                                                                    packageSetting2 = packageLPr6;
                                                                                    i6 = i3;
                                                                                    z8 = isSystem2;
                                                                                    packageFreezer6 = packageFreezer2;
                                                                                } catch (Throwable th11) {
                                                                                    th = th11;
                                                                                    i13 = 1;
                                                                                    th = th;
                                                                                    i5 = i13;
                                                                                    packageFreezer3 = packageFreezer2;
                                                                                    installRequest.setFreezer(packageFreezer3);
                                                                                    if (i5 != 0) {
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th12) {
                                                                                th = th12;
                                                                                th2 = th;
                                                                                throw th2;
                                                                            }
                                                                        } catch (IOException | NoSuchAlgorithmException unused5) {
                                                                        }
                                                                    }
                                                                } catch (Throwable th13) {
                                                                    th = th13;
                                                                }
                                                            }
                                                            if (androidPackage3.getSharedUserId() == null) {
                                                            }
                                                            if (parsePackage.getSharedUserId() == null) {
                                                            }
                                                            if (sharedUserId.equals(sharedUserId2)) {
                                                            }
                                                        } catch (Throwable th14) {
                                                            th = th14;
                                                            th2 = th;
                                                            throw th2;
                                                        }
                                                        packageFreezer2 = freezePackageForInstall;
                                                    } catch (Throwable th15) {
                                                        th2 = th15;
                                                    }
                                                } catch (Throwable th16) {
                                                    th = th16;
                                                }
                                            }
                                        } catch (Throwable th17) {
                                            th = th17;
                                        }
                                    }
                                    i5 = 1;
                                    packageFreezer3 = packageFreezer5;
                                } catch (Throwable th18) {
                                    th = th18;
                                    i5 = 1;
                                    packageFreezer3 = freezePackageForInstall;
                                }
                                installRequest.setFreezer(packageFreezer3);
                                if (i5 != 0) {
                                    packageFreezer3.close();
                                    throw th;
                                }
                                throw th;
                            }
                            PackageFreezer packageFreezer7 = freezePackageForInstall;
                            i4 = 1;
                            try {
                                String packageName4 = parsePackage.getPackageName();
                                try {
                                    synchronized (this.mPm.mLock) {
                                        try {
                                            String renamedPackageLPr2 = this.mPm.mSettings.getRenamedPackageLPr(packageName4);
                                            if (renamedPackageLPr2 != null) {
                                                PmHook.installFailLog(this.mContext, parsePackage, installRequest.getUser().getIdentifier());
                                                throw new PrepareFailure(-1, "Attempt to re-install " + packageName4 + " without first uninstalling package running as " + renamedPackageLPr2);
                                            }
                                            if (this.mPm.mPackages.containsKey(packageName4)) {
                                                PmHook.installFailLog(this.mContext, parsePackage, installRequest.getUser().getIdentifier());
                                                throw new PrepareFailure(-1, "Attempt to re-install " + packageName4 + " without first uninstalling.");
                                            }
                                        } catch (Throwable th19) {
                                            th = th19;
                                            r5 = packageFreezer7;
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th20) {
                                    th = th20;
                                }
                                try {
                                    throw th;
                                } catch (Throwable th21) {
                                    th = th21;
                                    th = th;
                                    i5 = i4;
                                    packageFreezer3 = r5;
                                    installRequest.setFreezer(packageFreezer3);
                                    if (i5 != 0) {
                                    }
                                }
                            } catch (Throwable th22) {
                                th = th22;
                                r5 = packageFreezer7;
                            }
                            try {
                                installRequest.setPrepareResult(z4, i6, i, androidPackage, parsePackage, z4, z8, packageSetting2, packageSetting);
                                installRequest.setFreezer(packageFreezer6);
                            } catch (Throwable th23) {
                                packageFreezer3 = packageFreezer6;
                                th = th23;
                                i5 = 0;
                            }
                        } else {
                            Log.w("PackageManager", "This SmartSwitch installation is allowed");
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                } finally {
                }
            } catch (PackageManagerException e6) {
                throw new PrepareFailure("Failed parse during installPackageLI", e6);
            }
        } catch (Throwable th24) {
            Trace.traceEnd(262144L);
            throw th24;
        }
        if (!installRequest.isInstallMove()) {
        }
        if (z6) {
        }
        int i132 = i2;
        ?? r52 = "installPackageLI";
        PackageFreezer freezePackageForInstall2 = freezePackageForInstall(packageName, -1, i132, r52, 16);
        PmHook.beginInstallLog(parsePackage, installRequest.getUser().getIdentifier());
        if (!z4) {
        }
        installRequest.setPrepareResult(z4, i6, i, androidPackage, parsePackage, z4, z8, packageSetting2, packageSetting);
        installRequest.setFreezer(packageFreezer6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ UserInfo lambda$preparePackageLI$2(Integer num) {
        return this.mPm.mUserManager.getUserInfo(num.intValue());
    }

    public final void doRenameLI(InstallRequest installRequest, ParsedPackage parsedPackage) {
        int returnCode = installRequest.getReturnCode();
        String returnMsg = installRequest.getReturnMsg();
        if (installRequest.isInstallMove()) {
            if (returnCode == 1) {
                return;
            }
            this.mRemovePackageHelper.cleanUpForMoveInstall(installRequest.getMoveToUuid(), installRequest.getMovePackageName(), installRequest.getMoveFromCodePath());
            throw new PrepareFailure(returnCode, returnMsg);
        }
        if (returnCode != 1) {
            this.mRemovePackageHelper.removeCodePath(installRequest.getCodeFile());
            throw new PrepareFailure(returnCode, returnMsg);
        }
        File resolveTargetDir = resolveTargetDir(installRequest.getInstallFlags(), installRequest.getCodeFile());
        File codeFile = installRequest.getCodeFile();
        File nextCodePath = PackageManagerServiceUtils.getNextCodePath(resolveTargetDir, parsedPackage.getPackageName());
        boolean z = this.mPm.mIncrementalManager != null && IncrementalManager.isIncrementalPath(codeFile.getAbsolutePath());
        try {
            PackageManagerServiceUtils.makeDirRecursive(nextCodePath.getParentFile(), 505);
            if (z) {
                this.mPm.mIncrementalManager.linkCodePath(codeFile, nextCodePath);
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
                parsedPackage.setBaseApkPath(FileUtils.rewriteAfterRename(codeFile, nextCodePath, parsedPackage.getBaseApkPath()));
                parsedPackage.setSplitCodePaths(FileUtils.rewriteAfterRename(codeFile, nextCodePath, parsedPackage.getSplitCodePaths()));
            } catch (IOException e) {
                Slog.e("PackageManager", "Failed to get path: " + nextCodePath, e);
                throw new PrepareFailure(-20, "Failed to get path: " + nextCodePath);
            }
        } catch (ErrnoException | IOException e2) {
            Slog.w("PackageManager", "Failed to rename", e2);
            throw new PrepareFailure(-4, "Failed to rename");
        }
    }

    public final File resolveTargetDir(int i, File file) {
        if ((2097152 & i) != 0) {
            return Environment.getDataAppDirectory(null);
        }
        return file.getParentFile();
    }

    public static boolean cannotInstallWithBadPermissionGroups(ParsedPackage parsedPackage) {
        return parsedPackage.getTargetSdkVersion() >= 31;
    }

    public final boolean doesSignatureMatchForPermissions(String str, ParsedPackage parsedPackage, int i) {
        PackageSetting packageLPr;
        KeySetManagerService keySetManagerService;
        SharedUserSetting sharedUserSettingLPr;
        synchronized (this.mPm.mLock) {
            packageLPr = this.mPm.mSettings.getPackageLPr(str);
            keySetManagerService = this.mPm.mSettings.getKeySetManagerService();
            sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr);
        }
        SigningDetails signingDetails = packageLPr == null ? SigningDetails.UNKNOWN : packageLPr.getSigningDetails();
        if (str.equals(parsedPackage.getPackageName()) && keySetManagerService.shouldCheckUpgradeKeySetLocked(packageLPr, sharedUserSettingLPr, i)) {
            return keySetManagerService.checkUpgradeKeySetLocked(packageLPr, parsedPackage);
        }
        if (signingDetails.checkCapability(parsedPackage.getSigningDetails(), 4)) {
            return true;
        }
        if (!parsedPackage.getSigningDetails().checkCapability(signingDetails, 4)) {
            return false;
        }
        synchronized (this.mPm.mLock) {
            packageLPr.setSigningDetails(parsedPackage.getSigningDetails());
        }
        return true;
    }

    public final void setUpFsVerity(AndroidPackage androidPackage) {
        if (PackageManagerServiceUtils.isApkVerityEnabled()) {
            if (!IncrementalManager.isIncrementalPath(androidPackage.getPath()) || IncrementalManager.getVersion() >= 2) {
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put(androidPackage.getBaseApkPath(), VerityUtils.getFsveritySignatureFilePath(androidPackage.getBaseApkPath()));
                String buildDexMetadataPathForApk = DexMetadataHelper.buildDexMetadataPathForApk(androidPackage.getBaseApkPath());
                if (new File(buildDexMetadataPathForApk).exists()) {
                    arrayMap.put(buildDexMetadataPathForApk, VerityUtils.getFsveritySignatureFilePath(buildDexMetadataPathForApk));
                }
                for (String str : androidPackage.getSplitCodePaths()) {
                    arrayMap.put(str, VerityUtils.getFsveritySignatureFilePath(str));
                    String buildDexMetadataPathForApk2 = DexMetadataHelper.buildDexMetadataPathForApk(str);
                    if (new File(buildDexMetadataPathForApk2).exists()) {
                        arrayMap.put(buildDexMetadataPathForApk2, VerityUtils.getFsveritySignatureFilePath(buildDexMetadataPathForApk2));
                    }
                }
                FileIntegrityService service = FileIntegrityService.getService();
                for (Map.Entry entry : arrayMap.entrySet()) {
                    try {
                        String str2 = (String) entry.getKey();
                        if (!VerityUtils.hasFsverity(str2)) {
                            String str3 = (String) entry.getValue();
                            if (new File(str3).exists()) {
                                VerityUtils.setUpFsverity(str2);
                                if (!service.verifyPkcs7DetachedSignature(str3, str2)) {
                                    throw new PrepareFailure(-118, "fs-verity signature does not verify against a known key");
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

    public final PackageFreezer freezePackageForInstall(String str, int i, int i2, String str2, int i3) {
        if ((i2 & IInstalld.FLAG_USE_QUOTA) != 0) {
            return new PackageFreezer(this.mPm);
        }
        return this.mPm.freezePackage(str, i, str2, i3);
    }

    public static void updateDigest(MessageDigest messageDigest, File file) {
        DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), messageDigest);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013a  */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.server.pm.PackageSetting] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v5, types: [java.util.Set] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void commitPackagesLocked(List list, int[] iArr) {
        String str;
        PackageSetting packageLPr;
        AndroidPackage androidPackage;
        Object obj;
        ?? r15;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ReconciledPackage reconciledPackage = (ReconciledPackage) it.next();
            InstallRequest installRequest = reconciledPackage.mInstallRequest;
            ParsedPackage parsedPackage = installRequest.getParsedPackage();
            String packageName = parsedPackage.getPackageName();
            RemovePackageHelper removePackageHelper = new RemovePackageHelper(this.mPm);
            DeletePackageHelper deletePackageHelper = new DeletePackageHelper(this.mPm);
            AndroidPackage androidPackage2 = (AndroidPackage) this.mPm.mPackages.get(packageName);
            PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(packageName);
            if (androidPackage2 == null && packageLPr2 != null && AsecInstallHelper.isExternal(packageLPr2)) {
                final int[] iArr2 = {packageLPr2.getAppId()};
                final ArrayList arrayList = new ArrayList(1);
                arrayList.add(packageLPr2.getPackageName());
                this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        InstallPackageHelper.this.lambda$commitPackagesLocked$3(arrayList, iArr2);
                    }
                });
            }
            installRequest.onCommitStarted();
            if (installRequest.isInstallReplace()) {
                AndroidPackage androidPackage3 = (AndroidPackage) this.mPm.mPackages.get(packageName);
                PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(androidPackage3.getPackageName());
                installRequest.setScannedPackageSettingFirstInstallTimeFromReplaced(packageStateInternal, iArr);
                installRequest.setScannedPackageSettingLastUpdateTime(System.currentTimeMillis());
                PackageRemovedInfo removedInfo = installRequest.getRemovedInfo();
                PackageManagerService packageManagerService = this.mPm;
                removedInfo.mBroadcastAllowList = packageManagerService.mAppsFilter.getVisibilityAllowList(packageManagerService.snapshotComputer(), installRequest.getScannedPackageSetting(), iArr, this.mPm.mSettings.getPackagesLocked());
                if (installRequest.isInstallSystem()) {
                    removePackageHelper.removePackage(androidPackage3, true);
                    if (!disableSystemPackageLPw(androidPackage3)) {
                        installRequest.getRemovedInfo().mArgs = new InstallArgs(androidPackage3.getPath(), InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi()));
                    } else {
                        installRequest.getRemovedInfo().mArgs = null;
                        str = null;
                    }
                } else {
                    try {
                        androidPackage = androidPackage3;
                        obj = null;
                    } catch (SystemDeleteException e) {
                        e = e;
                        androidPackage = androidPackage3;
                        obj = null;
                    }
                    try {
                        deletePackageHelper.executeDeletePackage(reconciledPackage.mDeletePackageAction, packageName, true, iArr, false);
                        r15 = obj;
                    } catch (SystemDeleteException e2) {
                        e = e2;
                        r15 = obj;
                        if (this.mPm.mIsEngBuild) {
                            throw new RuntimeException("Unexpected failure", e);
                        }
                        ?? packageLPr3 = this.mPm.mSettings.getPackageLPr(installRequest.getExistingPackageName());
                        if ((installRequest.getInstallFlags() & 1) != 0) {
                        }
                        str = r15;
                        if (installRequest.getReturnCode() == 1) {
                        }
                        updateSettingsLI(commitReconciledScanResultLocked(reconciledPackage, iArr), iArr, installRequest);
                        packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
                        if (packageLPr != null) {
                        }
                        if (installRequest.getReturnCode() == 1) {
                        }
                        installRequest.onCommitFinished();
                    }
                    ?? packageLPr32 = this.mPm.mSettings.getPackageLPr(installRequest.getExistingPackageName());
                    if ((installRequest.getInstallFlags() & 1) != 0) {
                        Set oldCodePaths = packageLPr32.getOldCodePaths();
                        if (oldCodePaths == null) {
                            oldCodePaths = new ArraySet();
                        }
                        Collections.addAll(oldCodePaths, androidPackage.getBaseApkPath());
                        Collections.addAll(oldCodePaths, androidPackage.getSplitCodePaths());
                        packageLPr32.setOldCodePaths(oldCodePaths);
                    } else {
                        packageLPr32.setOldCodePaths(r15);
                    }
                    str = r15;
                    if (installRequest.getReturnCode() == 1) {
                        PackageSetting packageLPr4 = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
                        str = r15;
                        if (packageLPr4 != null) {
                            installRequest.getRemovedInfo().mRemovedForAllUsers = this.mPm.mPackages.get(packageLPr4.getPackageName()) == null;
                            str = r15;
                        }
                    }
                }
                updateSettingsLI(commitReconciledScanResultLocked(reconciledPackage, iArr), iArr, installRequest);
                packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
                if (packageLPr != null) {
                    installRequest.setNewUsers(packageLPr.queryInstalledUsers(this.mPm.mUserManager.getUserIds(), true));
                    packageLPr.setUpdateAvailable(false);
                    File file = new File(packageLPr.getPath(), "app.metadata");
                    if (file.exists()) {
                        packageLPr.setAppMetadataFilePath(file.getAbsolutePath());
                    } else {
                        packageLPr.setAppMetadataFilePath(str);
                    }
                }
                if (installRequest.getReturnCode() == 1) {
                    this.mPm.updateSequenceNumberLP(packageLPr, installRequest.getNewUsers());
                    this.mPm.updateInstantAppInstallerLocked(packageName);
                }
                installRequest.onCommitFinished();
            }
            str = null;
            updateSettingsLI(commitReconciledScanResultLocked(reconciledPackage, iArr), iArr, installRequest);
            packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
            if (packageLPr != null) {
            }
            if (installRequest.getReturnCode() == 1) {
            }
            installRequest.onCommitFinished();
        }
        ApplicationPackageManager.invalidateGetPackagesForUidCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitPackagesLocked$3(ArrayList arrayList, int[] iArr) {
        BroadcastHelper broadcastHelper = this.mBroadcastHelper;
        PackageManagerService packageManagerService = this.mPm;
        Objects.requireNonNull(packageManagerService);
        broadcastHelper.sendResourcesChangedBroadcast(new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService), true, true, (String[]) arrayList.toArray(new String[arrayList.size()]), iArr);
    }

    public final boolean disableSystemPackageLPw(AndroidPackage androidPackage) {
        return this.mPm.mSettings.disableSystemPackageLPw(androidPackage.getPackageName(), true);
    }

    public final void updateSettingsLI(AndroidPackage androidPackage, int[] iArr, InstallRequest installRequest) {
        updateSettingsInternalLI(androidPackage, iArr, installRequest);
    }

    /* JADX WARN: Removed duplicated region for block: B:173:0x01aa A[Catch: all -> 0x034f, TryCatch #1 {, blocks: (B:4:0x0030, B:6:0x003e, B:9:0x0047, B:11:0x004d, B:13:0x0051, B:17:0x005b, B:18:0x0057, B:23:0x0063, B:25:0x0067, B:28:0x0075, B:30:0x0079, B:32:0x0081, B:34:0x008f, B:35:0x009b, B:37:0x00a1, B:39:0x00b5, B:43:0x00dd, B:44:0x00c3, B:48:0x00d2, B:55:0x00ee, B:59:0x00f5, B:61:0x00fb, B:64:0x012d, B:66:0x0133, B:67:0x01b1, B:69:0x01c7, B:71:0x01cf, B:73:0x01dc, B:75:0x01fd, B:77:0x0203, B:80:0x020c, B:82:0x0218, B:84:0x0232, B:86:0x023d, B:88:0x0241, B:90:0x024d, B:92:0x0253, B:94:0x0256, B:98:0x0266, B:100:0x0270, B:102:0x0274, B:103:0x0282, B:105:0x0286, B:107:0x028e, B:109:0x0294, B:113:0x0297, B:117:0x02b1, B:118:0x02bb, B:120:0x02c1, B:122:0x02d2, B:123:0x02df, B:127:0x02ed, B:129:0x02f8, B:130:0x02fb, B:132:0x0318, B:134:0x031f, B:135:0x02f2, B:137:0x02d6, B:139:0x02dc, B:141:0x0259, B:143:0x0263, B:146:0x0114, B:149:0x013b, B:151:0x013f, B:153:0x0147, B:155:0x0155, B:157:0x0161, B:159:0x01ae, B:160:0x0178, B:162:0x0187, B:169:0x019b, B:171:0x01a0, B:173:0x01aa, B:178:0x0326, B:179:0x034a), top: B:3:0x0030, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x01ae A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSettingsInternalLI(AndroidPackage androidPackage, int[] iArr, InstallRequest installRequest) {
        boolean z;
        List allowlistedRestrictedPermissions;
        IncrementalManager incrementalManager;
        boolean z2;
        PackageSetting packageLPr;
        Trace.traceBegin(262144L, "updateSettings");
        String packageName = androidPackage.getPackageName();
        int[] originUsers = installRequest.getOriginUsers();
        int installReason = installRequest.getInstallReason();
        String installerPackageName = installRequest.getInstallerPackageName();
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        synchronized (this.mPm.mLock) {
            PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(packageName);
            int userId = installRequest.getUserId();
            if (packageLPr2 != null) {
                int i = -1;
                if (packageLPr2.isSystem()) {
                    if (originUsers != null && !installRequest.isApplicationEnabledSettingPersistent()) {
                        int length = originUsers.length;
                        int i2 = 0;
                        while (i2 < length) {
                            int i3 = originUsers[i2];
                            if (userId == i || userId == i3) {
                                packageLPr2.setEnabled(0, i3, installerPackageName);
                            }
                            i2++;
                            i = -1;
                        }
                    }
                    if (iArr != null && originUsers != null) {
                        for (int i4 : iArr) {
                            packageLPr2.setInstalled(ArrayUtils.contains(originUsers, i4), i4);
                        }
                    }
                    if (iArr != null) {
                        for (int i5 : iArr) {
                            packageLPr2.resetOverrideComponentLabelIcon(i5);
                        }
                    }
                }
                if (!packageLPr2.getPkgState().getUsesLibraryInfos().isEmpty()) {
                    Iterator it = packageLPr2.getPkgState().getUsesLibraryInfos().iterator();
                    while (it.hasNext()) {
                        SharedLibraryWrapper sharedLibraryWrapper = (SharedLibraryWrapper) it.next();
                        int[] userIds = UserManagerService.getInstance().getUserIds();
                        int length2 = userIds.length;
                        Iterator it2 = it;
                        int i6 = 0;
                        while (i6 < length2) {
                            int i7 = length2;
                            int i8 = userIds[i6];
                            int[] iArr2 = userIds;
                            if (sharedLibraryWrapper.getType() == 1 && (packageLPr = this.mPm.mSettings.getPackageLPr(sharedLibraryWrapper.getPackageName())) != null) {
                                packageLPr2.setOverlayPathsForLibrary(sharedLibraryWrapper.getName(), packageLPr.getOverlayPaths(i8), i8);
                            }
                            i6++;
                            length2 = i7;
                            userIds = iArr2;
                        }
                        it = it2;
                    }
                }
                if (userId != -1) {
                    packageLPr2.setInstalled(true, userId);
                    if (asInterface != null) {
                        try {
                        } catch (RemoteException e) {
                            Log.w("PackageManager", "RemoteException - " + e);
                        }
                        if (!asInterface.getApplicationStateEnabledAsUser(packageName, false, userId)) {
                            Log.w("PackageManager", "This app can not be enabled due to EDM policy. packageName = " + packageName);
                            z2 = false;
                            if (z2 && !installRequest.isApplicationEnabledSettingPersistent()) {
                                packageLPr2.setEnabled(0, userId, installerPackageName);
                            }
                        }
                    }
                    z2 = true;
                    if (z2) {
                        packageLPr2.setEnabled(0, userId, installerPackageName);
                    }
                } else if (iArr != null) {
                    for (int i9 : iArr) {
                        if (PersonaServiceHelper.isSpfKnoxSupported() && this.mPm.mUserManager.getUserInfo(i9).isManagedProfile()) {
                            PackageManagerService packageManagerService = this.mPm;
                            Settings settings = packageManagerService.mSettings;
                            if (Settings.isAdbInstallDisallowed(packageManagerService.mUserManager, i9)) {
                                Log.d("PackageManager", "isAdbInstallDisallowed : false , currentUserId : " + i9);
                            }
                        }
                        boolean contains = ArrayUtils.contains(originUsers, i9);
                        if (!this.mPm.isUserRestricted(i9, "no_install_apps") && !this.mPm.isUserRestricted(i9, "no_debugging_features")) {
                            z = false;
                            if (!contains && z) {
                                packageLPr2.setInstalled(false, i9);
                            }
                            packageLPr2.setInstalled(true, i9);
                            if (installRequest.isApplicationEnabledSettingPersistent()) {
                                packageLPr2.setEnabled(0, i9, installerPackageName);
                            }
                        }
                        z = true;
                        if (!contains) {
                            packageLPr2.setInstalled(false, i9);
                        }
                        packageLPr2.setInstalled(true, i9);
                        if (installRequest.isApplicationEnabledSettingPersistent()) {
                        }
                    }
                }
                this.mPm.mSettings.addInstallerPackageNames(packageLPr2.getInstallSource());
                ArraySet arraySet = new ArraySet();
                if (installRequest.getRemovedInfo() != null && installRequest.getRemovedInfo().mInstallReasons != null) {
                    int size = installRequest.getRemovedInfo().mInstallReasons.size();
                    for (int i10 = 0; i10 < size; i10++) {
                        int keyAt = installRequest.getRemovedInfo().mInstallReasons.keyAt(i10);
                        packageLPr2.setInstallReason(installRequest.getRemovedInfo().mInstallReasons.valueAt(i10), keyAt);
                        arraySet.add(Integer.valueOf(keyAt));
                    }
                }
                if (installRequest.getRemovedInfo() != null && installRequest.getRemovedInfo().mUninstallReasons != null) {
                    for (int i11 = 0; i11 < installRequest.getRemovedInfo().mUninstallReasons.size(); i11++) {
                        packageLPr2.setUninstallReason(installRequest.getRemovedInfo().mUninstallReasons.valueAt(i11), installRequest.getRemovedInfo().mUninstallReasons.keyAt(i11));
                    }
                }
                int[] userIds2 = this.mPm.mUserManager.getUserIds();
                if (userId == -1) {
                    for (int i12 : userIds2) {
                        if (!arraySet.contains(Integer.valueOf(i12)) && packageLPr2.getInstalled(i12)) {
                            packageLPr2.setInstallReason(installReason, i12);
                        }
                    }
                } else if (!arraySet.contains(Integer.valueOf(userId))) {
                    packageLPr2.setInstallReason(installReason, userId);
                }
                String pathString = packageLPr2.getPathString();
                if (IncrementalManager.isIncrementalPath(pathString) && (incrementalManager = this.mIncrementalManager) != null) {
                    incrementalManager.registerLoadingProgressCallback(pathString, new IncrementalProgressListener(packageLPr2.getPackageName(), this.mPm));
                }
                for (int i13 : userIds2) {
                    if (packageLPr2.getInstalled(i13)) {
                        packageLPr2.setUninstallReason(0, i13);
                    }
                }
                this.mPm.mSettings.writeKernelMappingLPr(packageLPr2);
                PermissionManagerServiceInternal.PackageInstalledParams.Builder builder = new PermissionManagerServiceInternal.PackageInstalledParams.Builder();
                if ((installRequest.getInstallFlags() & 256) != 0) {
                    ArrayMap arrayMap = new ArrayMap();
                    List requestedPermissions = androidPackage.getRequestedPermissions();
                    for (int i14 = 0; i14 < requestedPermissions.size(); i14++) {
                        arrayMap.put((String) requestedPermissions.get(i14), 1);
                    }
                    builder.setPermissionStates(arrayMap);
                } else {
                    ArrayMap permissionStates = installRequest.getPermissionStates();
                    if (permissionStates != null) {
                        builder.setPermissionStates(permissionStates);
                    }
                }
                if ((installRequest.getInstallFlags() & 4194304) != 0) {
                    allowlistedRestrictedPermissions = androidPackage.getRequestedPermissions();
                } else {
                    allowlistedRestrictedPermissions = installRequest.getAllowlistedRestrictedPermissions();
                }
                if (allowlistedRestrictedPermissions != null) {
                    builder.setAllowlistedRestrictedPermissions(allowlistedRestrictedPermissions);
                }
                builder.setAutoRevokePermissionsMode(installRequest.getAutoRevokePermissionsMode());
                this.mPm.mPermissionManager.onPackageInstalled(androidPackage, installRequest.getPreviousAppId(), builder.build(), userId);
                if (installRequest.getPackageSource() == 3 || installRequest.getPackageSource() == 4) {
                    enableRestrictedSettings(packageName, androidPackage.getUid());
                }
            }
            installRequest.setName(packageName);
            installRequest.setAppId(androidPackage.getUid());
            installRequest.setPkg(androidPackage);
            installRequest.setReturnCode(1);
            Trace.traceBegin(262144L, "writeSettings");
            this.mPm.writeSettingsLPrTEMP();
            Trace.traceEnd(262144L);
        }
        Trace.traceEnd(262144L);
    }

    public final void enableRestrictedSettings(String str, int i) {
        AppOpsManager appOpsManager = (AppOpsManager) this.mPm.mContext.getSystemService(AppOpsManager.class);
        for (int i2 : this.mPm.mUserManager.getUserIds()) {
            appOpsManager.setMode(119, UserHandle.getUid(i2, i), str, 2);
        }
    }

    public static boolean apkHasNumOfDexFiles(String str, int i) {
        StrictJarFile strictJarFile;
        StrictJarFile strictJarFile2 = null;
        try {
            try {
                strictJarFile = new StrictJarFile(str, false, false);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("classes");
            sb.append(i > 1 ? String.valueOf(i) : "");
            sb.append(".dex");
            boolean z = strictJarFile.findEntry(sb.toString()) != null;
            try {
                strictJarFile.close();
            } catch (IOException unused) {
            }
            return z;
        } catch (IOException e2) {
            e = e2;
            strictJarFile2 = strictJarFile;
            Slog.w("PackageManager", "Cannot read " + str + " for counting dex files, error: " + e);
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (IOException unused2) {
                }
            }
            return true;
        } catch (Throwable th2) {
            th = th2;
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
    }

    public final void spegClearPackage(int i, String str) {
        try {
            ((ActivityTaskManagerInternal) this.mInjector.getLocalService(ActivityTaskManagerInternal.class)).removeRecentTasksByPackageName(str, 0);
        } catch (Exception e) {
            Slog.e("SPEG", "Can't remove recent task for " + str + ", error: " + e);
        }
        if (!this.mPm.clearPackageAfterSpeg(str, 0)) {
            Slog.e("SPEG", "Can't clear app data for " + str);
        }
        try {
            ((UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class)).removeUriPermissionsForPackage(str, i, true, false);
        } catch (Exception e2) {
            Slog.e("SPEG", "Can't restore default permissions for " + str + ", error: " + e2);
        }
        try {
            ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class)).cancelJobsForUid(i, true, 14, 8, "clear data");
        } catch (Exception e3) {
            Slog.e("SPEG", "Can't clear scheduled jobs for " + str + ", error: " + e3);
        }
        try {
            ((AlarmManagerInternal) LocalServices.getService(AlarmManagerInternal.class)).removeAlarmsForUid(i);
        } catch (Exception e4) {
            Slog.e("SPEG", "Can't clear pending alarms for " + str + ", error: " + e4);
        }
    }

    public final boolean hasUsesPermissions(AndroidPackage androidPackage) {
        List usesPermissions = androidPackage.getUsesPermissions();
        for (int i = 0; i < usesPermissions.size(); i++) {
            if (((ParsedUsesPermission) usesPermissions.get(i)).getName().startsWith("com.samsung.android.knox.permission")) {
                return true;
            }
        }
        return false;
    }

    public final boolean checkSpegContinualLaunchesLimitViolation() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mBlockContinualSpeg) {
            int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis - this.mSpegBlockStartTime);
            if (minutes < 40) {
                Slog.d("SPEG", "Disable SPEG due to continuous installation, blockedDuration: " + minutes + " minutes");
                return true;
            }
            this.mBlockContinualSpeg = false;
            this.mSpegLaunchesCount = 0;
            this.mSpegBlockStartTime = -1L;
        }
        int i = this.mSpegLaunchesCount + 1;
        if (i >= 5) {
            if (((int) TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis - this.mSpegFirstLaunchTime)) < 10) {
                this.mBlockContinualSpeg = true;
                this.mSpegBlockStartTime = currentTimeMillis;
            } else {
                this.mSpegLaunchesCount = 0;
            }
        } else if (i > 1 && ((int) TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis - this.mSpegPrevLaunchTime)) > 3) {
            this.mSpegLaunchesCount = 0;
        }
        if (this.mSpegLaunchesCount == 0) {
            this.mSpegFirstLaunchTime = currentTimeMillis;
            Slog.d("SPEG", "Continual launches limit is reset");
        }
        this.mSpegPrevLaunchTime = currentTimeMillis;
        this.mSpegLaunchesCount++;
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:149:0x01da, code lost:
    
        r19 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x01f6, code lost:
    
        throw new com.android.server.pm.Installer.InstallerException("Failed to wait state on for " + r5.getName());
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x01f7, code lost:
    
        r19 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x01f9, code lost:
    
        android.util.Slog.i("SPEG", r5.getName() + " state is on at iteration " + r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0224, code lost:
    
        r14 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x023a, code lost:
    
        r3 = r3.startActivity((android.app.IApplicationThread) null, "com.samsung.speg", (android.content.Intent) r6, (java.lang.String) r7, (android.os.IBinder) null, (java.lang.String) null, 0, 0, (android.app.ProfilerInfo) null, r4.toBundle());
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0242, code lost:
    
        if (android.app.ActivityManager.isStartResultSuccessful(r3) == false) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0244, code lost:
    
        java.lang.Thread.sleep(2000);
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x024f, code lost:
    
        r5 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0251, code lost:
    
        r3 = r26.mSpeg.getPidOf(r0.getProcessName(), r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0256, code lost:
    
        if (r3 == (-1)) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0258, code lost:
    
        android.util.Slog.i("SPEG", "Send signal to dump profiles in app, pid=" + r3);
        android.os.Process.sendSignal(r3, 10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0279, code lost:
    
        r4 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x027b, code lost:
    
        r3 = r26.mSpeg.storePrimaryProf(r0.getBaseApkPath(), r4, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x027f, code lost:
    
        r15.setFreezer(r26.mPm.freezePackage(r14, 0, "SPEG", 13));
        r19.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x02a0, code lost:
    
        if (((android.hardware.display.DisplayManager) r26.mContext.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(r10) == null) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x02a2, code lost:
    
        android.util.Slog.e("SPEG", "Can't release " + r19);
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x02ba, code lost:
    
        if (r16 == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x02bc, code lost:
    
        android.util.Slog.i("SPEG", "Granting WAKE_LOCK to pkg " + ((java.lang.String) r14));
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02d2, code lost:
    
        r26.mPm.mPermissionManager.grantInstallPermission(r14, "android.permission.WAKE_LOCK");
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x02dc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x02dd, code lost:
    
        android.util.Slog.w("SPEG", "Cannot grant WAKE_LOCK for pkg " + ((java.lang.String) r14) + ": " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x036f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0370, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x039a, code lost:
    
        r6 = r10;
        r4 = r4;
        r5 = r5;
        r8 = r8;
        r9 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x03e6, code lost:
    
        r7 = 13;
        r10 = true;
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0369, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x036a, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0390, code lost:
    
        r6 = r10;
        r4 = r4;
        r5 = r5;
        r8 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x03d5, code lost:
    
        r7 = 13;
        r10 = 1;
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0388, code lost:
    
        throw new com.android.server.pm.Installer.InstallerException("getPidOf failed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x0393, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0394, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x0389, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x038a, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x03c9, code lost:
    
        throw new com.android.server.pm.Installer.InstallerException("Failed to start " + ((java.lang.String) r14) + ", res=" + r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x03db, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x03dc, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r18;
        r6 = r10;
        r5 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x03ca, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x03cb, code lost:
    
        r8 = r19;
        r9 = r14;
        r4 = r18;
        r6 = r10;
        r5 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x03ec, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x03ed, code lost:
    
        r6 = r10;
        r5 = r12;
        r9 = r14;
        r4 = r18;
        r8 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0461, code lost:
    
        r7 = '\r';
        r10 = 1;
        r4 = r4;
        r5 = r5;
        r6 = r6;
        r8 = r8;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x061b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x04e6  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x05b7  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x05cf  */
    /* JADX WARN: Type inference failed for: r0v26, types: [com.android.server.pm.PackageManagerService$IPackageManagerImpl] */
    /* JADX WARN: Type inference failed for: r0v42, types: [com.android.server.pm.PackageManagerService] */
    /* JADX WARN: Type inference failed for: r0v46, types: [android.hardware.display.DisplayManager] */
    /* JADX WARN: Type inference failed for: r0v48, types: [com.android.server.SpegService] */
    /* JADX WARN: Type inference failed for: r0v55, types: [com.android.server.SpegService] */
    /* JADX WARN: Type inference failed for: r10v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.hardware.display.VirtualDisplay] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v19 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v27 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r26v0, types: [com.android.server.pm.InstallPackageHelper] */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v15, types: [android.app.ActivityOptions] */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v36 */
    /* JADX WARN: Type inference failed for: r4v37 */
    /* JADX WARN: Type inference failed for: r4v38 */
    /* JADX WARN: Type inference failed for: r4v72 */
    /* JADX WARN: Type inference failed for: r4v77 */
    /* JADX WARN: Type inference failed for: r4v79 */
    /* JADX WARN: Type inference failed for: r4v81 */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16, types: [int] */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v26, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v35 */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v37 */
    /* JADX WARN: Type inference failed for: r5v38 */
    /* JADX WARN: Type inference failed for: r5v50, types: [com.android.server.pm.dex.ArtManagerService] */
    /* JADX WARN: Type inference failed for: r5v55 */
    /* JADX WARN: Type inference failed for: r5v59 */
    /* JADX WARN: Type inference failed for: r5v60 */
    /* JADX WARN: Type inference failed for: r5v62 */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [int] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v28 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v33 */
    /* JADX WARN: Type inference failed for: r6v34 */
    /* JADX WARN: Type inference failed for: r6v61 */
    /* JADX WARN: Type inference failed for: r6v66 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11, types: [int] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v39 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [android.hardware.display.VirtualDisplay, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v29 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v45 */
    /* JADX WARN: Type inference failed for: r8v55 */
    /* JADX WARN: Type inference failed for: r8v59 */
    /* JADX WARN: Type inference failed for: r8v60 */
    /* JADX WARN: Type inference failed for: r8v61 */
    /* JADX WARN: Type inference failed for: r8v63 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v45, types: [int[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean spegLaunchApp(ReconciledPackage reconciledPackage) {
        INetworkManagementService asInterface;
        String sb;
        Throwable th;
        String str;
        ?? r10;
        VirtualDisplay virtualDisplay;
        int i;
        int i2;
        int i3;
        String str2;
        ?? r5;
        boolean z;
        boolean z2;
        int i4;
        String str3;
        VirtualDisplay virtualDisplay2;
        int i5;
        INetworkManagementService iNetworkManagementService;
        char c;
        int i6;
        String str4;
        boolean storePrimaryProf;
        VirtualDisplay virtualDisplay3;
        int i7;
        int i8;
        String str5;
        VirtualDisplay virtualDisplay4;
        int i9;
        int i10;
        INetworkManagementService iNetworkManagementService2;
        String str6;
        VirtualDisplay virtualDisplay5;
        String str7;
        boolean z3;
        VirtualDisplay virtualDisplay6;
        int i11;
        int i12;
        int i13;
        String str8;
        Slog.i("SPEG", "Started, SPEG v2.2");
        IActivityManager service = ActivityManager.getService();
        if (service == null) {
            Slog.e("PackageManager", "Activity manager doesn't exist");
            return false;
        }
        asInterface = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        if (asInterface == null) {
            Slog.e("SPEG", "Network manager is not found");
            return false;
        }
        InstallRequest installRequest = reconciledPackage.mInstallRequest;
        AndroidPackageInternal pkg = installRequest.getScannedPackageSetting().getPkg();
        INetworkManagementService packageName = pkg.getPackageName();
        ?? launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage == 0 || !launchIntentForPackage.hasCategory("android.intent.category.LAUNCHER") || !launchIntentForPackage.getAction().equals("android.intent.action.MAIN")) {
            Slog.e("SPEG", "Package " + ((String) packageName) + " doesn't have launchable intent");
            return false;
        }
        ?? type = launchIntentForPackage.getType();
        launchIntentForPackage.addFlags(1073741824);
        launchIntentForPackage.addFlags(268435456);
        launchIntentForPackage.addFlags(262144);
        synchronized (this.mPm.mLock) {
            PackageSetting packageSetting = (PackageSetting) this.mPm.mSettings.mPackages.get(packageName);
            if (packageSetting == null) {
                Slog.e("SPEG", "Package manager doesn't know the package: " + ((String) packageName));
                return false;
            }
            int appId = packageSetting.getAppId();
            if (appId < 10000 || appId > 19999) {
                Slog.i("SPEG", "Skip feature because of non-regular application id: " + appId);
                return false;
            }
            boolean contains = pkg.getRequestedPermissions().contains("android.permission.WAKE_LOCK");
            if (contains) {
                Slog.i("SPEG", "Revoking WAKE_LOCK from package " + ((String) packageName));
                try {
                    this.mPm.mPermissionManager.revokeInstallPermission(packageName, "android.permission.WAKE_LOCK");
                } catch (Exception e) {
                    Slog.e("SPEG", "Cannot revoke WAKE_LOCK from pkg " + ((String) packageName) + ": " + e.getMessage());
                    return false;
                }
            }
            int uid = pkg.getUid();
            ?? createSpegVirtualDisplay = DisplayManagerGlobal.getInstance().createSpegVirtualDisplay(packageName, uid);
            if (createSpegVirtualDisplay == 0) {
                Slog.e("SPEG", "VirtualDisplay is not created");
                return false;
            }
            ?? makeBasic = ActivityOptions.makeBasic();
            int displayId = createSpegVirtualDisplay.getDisplay().getDisplayId();
            makeBasic.setLaunchDisplayId(displayId);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getDataProfilesDePackageDirectory(0, packageName));
            sb2.append(File.separator);
            INetworkManagementService iNetworkManagementService3 = null;
            INetworkManagementService iNetworkManagementService4 = null;
            sb2.append(ArtManager.getProfileName((String) null));
            ?? sb3 = sb2.toString();
            ?? sb4 = new StringBuilder();
            sb4.append(pkg.getPath());
            sb4.append("/base.speg");
            sb4.append(pkg.getUid());
            sb = sb4.toString();
            Binder.allowBlockingForCurrentThread();
            try {
                try {
                    try {
                        installRequest.closeFreezer();
                        installRequest.setFreezer(null);
                        this.mSpeg.setSpegState(packageName);
                        if (!DexOptHelper.useArtService()) {
                            try {
                                try {
                                    ?? r52 = this.mArtManagerService;
                                    try {
                                        try {
                                            ?? resolveUserIds = this.mPm.resolveUserIds(installRequest.getUserId());
                                            r52.prepareAppProfiles(pkg, resolveUserIds, false);
                                            iNetworkManagementService4 = resolveUserIds;
                                        } catch (Exception e2) {
                                            e = e2;
                                            i9 = displayId;
                                            str6 = sb3;
                                            virtualDisplay5 = createSpegVirtualDisplay;
                                            i10 = uid;
                                            iNetworkManagementService2 = asInterface;
                                            c = '\r';
                                            z = true;
                                            str3 = str6;
                                            i5 = i10;
                                            i4 = i9;
                                            virtualDisplay2 = virtualDisplay5;
                                            iNetworkManagementService = iNetworkManagementService2;
                                            createSpegVirtualDisplay = 0;
                                            makeBasic = str3;
                                            r5 = i5;
                                            launchIntentForPackage = i4;
                                            type = c;
                                            sb3 = virtualDisplay2;
                                            iNetworkManagementService4 = iNetworkManagementService;
                                            asInterface = packageName;
                                            Slog.e("SPEG", "Cancel SPEG for " + ((String) asInterface) + ": " + e.getMessage());
                                            installRequest.setFreezer(this.mPm.freezePackage(asInterface, 0, "SPEG", type));
                                            sb3.release();
                                            if (((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(launchIntentForPackage) != null) {
                                            }
                                            if (contains) {
                                            }
                                            if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, r5)) {
                                            }
                                            makeBasic.substring(0, makeBasic.lastIndexOf(".prof"));
                                            spegClearPackage(r5, asInterface);
                                            try {
                                                iNetworkManagementService4.spegRestrictNetworkConnection((int) r5, false);
                                            } catch (Exception e3) {
                                                Slog.e("SPEG", "Failed to restore network connection for uid " + r5, e3);
                                            }
                                            try {
                                                this.mPm.getIPackageManager().setPackageStoppedState(asInterface, z, 0);
                                            } catch (IllegalArgumentException e4) {
                                                Slog.e("SPEG", "Failed to set stopped state", e4);
                                            }
                                            this.mSpeg.setSpegState(createSpegVirtualDisplay);
                                            Binder.defaultBlockingForCurrentThread();
                                            z2 = false;
                                            Slog.i("SPEG", "Finished");
                                            return z2;
                                        }
                                    } catch (Throwable th2) {
                                        i7 = displayId;
                                        th = th2;
                                        str5 = sb3;
                                        virtualDisplay4 = createSpegVirtualDisplay;
                                        i8 = uid;
                                        iNetworkManagementService3 = asInterface;
                                    }
                                } catch (Installer.LegacyDexoptDisabledException e5) {
                                    throw new RuntimeException(e5);
                                }
                            } catch (Throwable th3) {
                                i12 = displayId;
                                th = th3;
                                str8 = sb3;
                                virtualDisplay6 = createSpegVirtualDisplay;
                                i13 = uid;
                                i11 = 13;
                                z3 = true;
                                str7 = null;
                                iNetworkManagementService3 = asInterface;
                            }
                        }
                        try {
                            int i14 = 1;
                            z = true;
                            z = true;
                            try {
                                try {
                                    r5 = this.mSpeg.createOrDeleteMarkerFiles(sb, true, uid);
                                    try {
                                    } catch (Exception e6) {
                                        e = e6;
                                    }
                                } catch (Exception e7) {
                                    e = e7;
                                    i4 = displayId;
                                    str3 = sb3;
                                    virtualDisplay2 = createSpegVirtualDisplay;
                                    i5 = uid;
                                    iNetworkManagementService = asInterface;
                                    c = '\r';
                                    createSpegVirtualDisplay = 0;
                                    makeBasic = str3;
                                    r5 = i5;
                                    launchIntentForPackage = i4;
                                    type = c;
                                    sb3 = virtualDisplay2;
                                    iNetworkManagementService4 = iNetworkManagementService;
                                    asInterface = packageName;
                                    Slog.e("SPEG", "Cancel SPEG for " + ((String) asInterface) + ": " + e.getMessage());
                                    installRequest.setFreezer(this.mPm.freezePackage(asInterface, 0, "SPEG", type));
                                    sb3.release();
                                    if (((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(launchIntentForPackage) != null) {
                                    }
                                    if (contains) {
                                    }
                                    if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, r5)) {
                                    }
                                    makeBasic.substring(0, makeBasic.lastIndexOf(".prof"));
                                    spegClearPackage(r5, asInterface);
                                    iNetworkManagementService4.spegRestrictNetworkConnection((int) r5, false);
                                    this.mPm.getIPackageManager().setPackageStoppedState(asInterface, z, 0);
                                    this.mSpeg.setSpegState(createSpegVirtualDisplay);
                                    Binder.defaultBlockingForCurrentThread();
                                    z2 = false;
                                    Slog.i("SPEG", "Finished");
                                    return z2;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                int i15 = displayId;
                                String str9 = sb3;
                                VirtualDisplay virtualDisplay7 = createSpegVirtualDisplay;
                                int i16 = uid;
                                iNetworkManagementService3 = asInterface;
                                char c2 = '\r';
                                createSpegVirtualDisplay = 0;
                                makeBasic = str9;
                                sb4 = i16;
                                launchIntentForPackage = i15;
                                type = c2;
                                sb3 = virtualDisplay7;
                                displayId = i14;
                                asInterface = packageName;
                                th = th;
                                str2 = makeBasic;
                                i3 = sb4;
                                i2 = launchIntentForPackage;
                                i = type;
                                virtualDisplay = sb3;
                                r10 = displayId;
                                str = createSpegVirtualDisplay;
                                installRequest.setFreezer(this.mPm.freezePackage(asInterface, 0, "SPEG", i));
                                virtualDisplay.release();
                                if (((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i2) != null) {
                                }
                                if (contains) {
                                }
                                if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, i3)) {
                                }
                                str2.substring(0, str2.lastIndexOf(".prof"));
                                spegClearPackage(i3, asInterface);
                                try {
                                    iNetworkManagementService3.spegRestrictNetworkConnection(i3, false);
                                } catch (Exception e8) {
                                    Slog.e("SPEG", "Failed to restore network connection for uid " + i3, e8);
                                }
                                try {
                                    this.mPm.getIPackageManager().setPackageStoppedState(asInterface, r10, 0);
                                } catch (IllegalArgumentException e9) {
                                    Slog.e("SPEG", "Failed to set stopped state", e9);
                                }
                                this.mSpeg.setSpegState(str);
                                Binder.defaultBlockingForCurrentThread();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            int i17 = displayId;
                            String str10 = sb3;
                            VirtualDisplay virtualDisplay8 = createSpegVirtualDisplay;
                            int i18 = uid;
                            iNetworkManagementService3 = asInterface;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        launchIntentForPackage = displayId;
                        makeBasic = sb3;
                        sb3 = createSpegVirtualDisplay;
                        sb4 = uid;
                        type = 13;
                        displayId = 1;
                        createSpegVirtualDisplay = 0;
                        iNetworkManagementService3 = asInterface;
                    }
                } catch (Exception e10) {
                    e = e10;
                    launchIntentForPackage = displayId;
                    makeBasic = sb3;
                    sb3 = createSpegVirtualDisplay;
                    r5 = uid;
                    type = 13;
                    z = true;
                    createSpegVirtualDisplay = 0;
                    iNetworkManagementService4 = asInterface;
                }
            } catch (Throwable th7) {
                th = th7;
            }
            if (r5 == 0) {
                throw new Installer.InstallerException("createSpegMarkerFile failed");
            }
            try {
                asInterface.spegRestrictNetworkConnection(uid, true);
                Display display = createSpegVirtualDisplay.getDisplay();
                int i19 = 1;
                sb3 = sb3;
                createSpegVirtualDisplay = createSpegVirtualDisplay;
                while (true) {
                    String str11 = sb3;
                    if (display.getState() == 2) {
                        break;
                    }
                    if (i19 > 24) {
                        break;
                    }
                    virtualDisplay3 = createSpegVirtualDisplay;
                    try {
                        try {
                            SystemClock.sleep(i19);
                            i19++;
                            sb3 = str11;
                            createSpegVirtualDisplay = virtualDisplay3;
                        } catch (Exception e11) {
                            e = e11;
                            i9 = displayId;
                            i10 = uid;
                            iNetworkManagementService2 = asInterface;
                            str6 = str11;
                            virtualDisplay5 = virtualDisplay3;
                            c = '\r';
                            z = true;
                            str3 = str6;
                            i5 = i10;
                            i4 = i9;
                            virtualDisplay2 = virtualDisplay5;
                            iNetworkManagementService = iNetworkManagementService2;
                            createSpegVirtualDisplay = 0;
                            makeBasic = str3;
                            r5 = i5;
                            launchIntentForPackage = i4;
                            type = c;
                            sb3 = virtualDisplay2;
                            iNetworkManagementService4 = iNetworkManagementService;
                            asInterface = packageName;
                            Slog.e("SPEG", "Cancel SPEG for " + ((String) asInterface) + ": " + e.getMessage());
                            installRequest.setFreezer(this.mPm.freezePackage(asInterface, 0, "SPEG", type));
                            sb3.release();
                            if (((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(launchIntentForPackage) != null) {
                                Slog.e("SPEG", "Can't release " + sb3);
                            }
                            if (contains) {
                                Slog.i("SPEG", "Granting WAKE_LOCK to pkg " + ((String) asInterface));
                                try {
                                    this.mPm.mPermissionManager.grantInstallPermission(asInterface, "android.permission.WAKE_LOCK");
                                } catch (Exception e12) {
                                    Slog.w("SPEG", "Cannot grant WAKE_LOCK for pkg " + ((String) asInterface) + ": " + e12.getMessage());
                                }
                            }
                            if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, r5)) {
                                Slog.w("SPEG", "SPEG can't delete " + sb);
                            }
                            makeBasic.substring(0, makeBasic.lastIndexOf(".prof"));
                            spegClearPackage(r5, asInterface);
                            iNetworkManagementService4.spegRestrictNetworkConnection((int) r5, false);
                            this.mPm.getIPackageManager().setPackageStoppedState(asInterface, z, 0);
                            this.mSpeg.setSpegState(createSpegVirtualDisplay);
                            Binder.defaultBlockingForCurrentThread();
                            z2 = false;
                            Slog.i("SPEG", "Finished");
                            return z2;
                        }
                    } catch (Throwable th8) {
                        i7 = displayId;
                        th = th8;
                        i8 = uid;
                        iNetworkManagementService3 = asInterface;
                        str5 = str11;
                        virtualDisplay4 = virtualDisplay3;
                    }
                    i7 = displayId;
                    th = th8;
                    i8 = uid;
                    iNetworkManagementService3 = asInterface;
                    str5 = str11;
                    virtualDisplay4 = virtualDisplay3;
                    i11 = 13;
                    z3 = true;
                    str7 = null;
                    str8 = str5;
                    i13 = i8;
                    i12 = i7;
                    virtualDisplay6 = virtualDisplay4;
                    asInterface = packageName;
                    str2 = str8;
                    i3 = i13;
                    i2 = i12;
                    i = i11;
                    virtualDisplay = virtualDisplay6;
                    r10 = z3;
                    str = str7;
                    installRequest.setFreezer(this.mPm.freezePackage(asInterface, 0, "SPEG", i));
                    virtualDisplay.release();
                    if (((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i2) != null) {
                        Slog.e("SPEG", "Can't release " + virtualDisplay);
                    }
                    if (contains) {
                        Slog.i("SPEG", "Granting WAKE_LOCK to pkg " + ((String) asInterface));
                        try {
                            this.mPm.mPermissionManager.grantInstallPermission(asInterface, "android.permission.WAKE_LOCK");
                        } catch (Exception e13) {
                            Slog.w("SPEG", "Cannot grant WAKE_LOCK for pkg " + ((String) asInterface) + ": " + e13.getMessage());
                        }
                    }
                    if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, i3)) {
                        Slog.w("SPEG", "SPEG can't delete " + sb);
                    }
                    str2.substring(0, str2.lastIndexOf(".prof"));
                    spegClearPackage(i3, asInterface);
                    iNetworkManagementService3.spegRestrictNetworkConnection(i3, false);
                    this.mPm.getIPackageManager().setPackageStoppedState(asInterface, r10, 0);
                    this.mSpeg.setSpegState(str);
                    Binder.defaultBlockingForCurrentThread();
                    throw th;
                }
                Slog.i("SPEG", "Finished");
                return z2;
            } catch (Exception e14) {
                throw new Installer.InstallerException("Failed to disable network connection for uid " + uid + ": " + e14.getMessage());
            }
        }
        try {
            this.mPm.getIPackageManager().setPackageStoppedState(asInterface, true, 0);
        } catch (IllegalArgumentException e15) {
            Slog.e("SPEG", "Failed to set stopped state", e15);
        }
        this.mSpeg.setSpegState(null);
        Binder.defaultBlockingForCurrentThread();
        z2 = storePrimaryProf;
        Slog.i("SPEG", "Finished");
        return z2;
        this.mSpeg.setSpegState(null);
        Binder.defaultBlockingForCurrentThread();
        z2 = storePrimaryProf;
        Slog.i("SPEG", "Finished");
        return z2;
        if (!this.mSpeg.createOrDeleteMarkerFiles(sb, false, i6)) {
            Slog.w("SPEG", "SPEG can't delete " + sb);
        }
        str4.substring(0, str4.lastIndexOf(".prof"));
        spegClearPackage(i6, asInterface);
        try {
            asInterface.spegRestrictNetworkConnection(i6, false);
        } catch (Exception e16) {
            Slog.e("SPEG", "Failed to restore network connection for uid " + i6, e16);
        }
        this.mPm.getIPackageManager().setPackageStoppedState(asInterface, true, 0);
        this.mSpeg.setSpegState(null);
        Binder.defaultBlockingForCurrentThread();
        z2 = storePrimaryProf;
        Slog.i("SPEG", "Finished");
        return z2;
    }

    public final boolean isDexoptLimited() {
        return SystemProperties.getBoolean("sys.dexopt.ctrl", false);
    }

    public final boolean isSpegSkipped(ReconciledPackage reconciledPackage, boolean z) {
        if (this.mSpeg == null) {
            Slog.i("SPEG", "Feature is disabled due to service is not started");
            return true;
        }
        InstallRequest installRequest = reconciledPackage.mInstallRequest;
        if (installRequest.isInstallReplace()) {
            Slog.d("SPEG", "Feature is disabled for existing app");
            return true;
        }
        if (PackageManagerService.FORCE_SPEG) {
            return false;
        }
        if (z) {
            Slog.i("SPEG", "Feature is disabled due to dexopt skipped");
            return true;
        }
        if (isDexoptLimited()) {
            Slog.i("SPEG", "Feature is disabled by high temperature");
            return true;
        }
        AndroidPackageInternal pkg = installRequest.getScannedPackageSetting().getPkg();
        Bundle metaData = pkg.getMetaData();
        if (metaData != null && metaData.getBoolean("com.samsung.android.speg.disabled", false)) {
            Slog.i("SPEG", "Feature is disabled in app manifest");
            return true;
        }
        String packageName = pkg.getPackageName();
        if (packageName.equals(this.mSpeg.mPrevInstalledPkg)) {
            Slog.i("SPEG", "Feature is disabled for reinstalled apps");
            this.mSpeg.mPrevInstalledPkg = null;
            return true;
        }
        PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
        if (scannedPackageSetting.isSystem() || scannedPackageSetting.isSystemExt() || scannedPackageSetting.isVendor() || scannedPackageSetting.isOem() || scannedPackageSetting.isOdm() || scannedPackageSetting.isPrivileged() || pkg.isSignedWithPlatformKey()) {
            Slog.i("SPEG", "Feature is disabled for system apps");
            return true;
        }
        if (this.mSpeg.isPackageBlockListed(packageName)) {
            Slog.i("SPEG", "Feature is disabled for package " + packageName);
            return true;
        }
        if (this.mSpeg.hasPrivilegedPermissions(pkg)) {
            Slog.i("SPEG", "Feature is disabled for privileged apps");
            return true;
        }
        if (hasUsesPermissions(pkg)) {
            Slog.i("SPEG", "Feature is disabled for apps with specific uses-permission");
            return true;
        }
        if (this.mPm.mPermissionManager.checkUidPermission(pkg.getUid(), "android.permission.SET_WALLPAPER") == 0) {
            Slog.d("SPEG", "Feature is disabled due to SET_WALLPAPER permission");
            return true;
        }
        String baseApkPath = pkg.getBaseApkPath();
        if (apkHasNumOfDexFiles(baseApkPath, 5)) {
            Slog.i("SPEG", "Feature is disabled for " + packageName + " as it has more than 4 dex files");
            return true;
        }
        if ("com.sec.android.easyMover".equals(installRequest.getInstallerPackageName())) {
            Slog.i("SPEG", "Feature is disabled for smart switch installer");
            return true;
        }
        if (this.mSpeg.isSmartSwitchBlockSpeg()) {
            Slog.i("SPEG", "Feature is disabled until SmartSwitch is finished");
            return true;
        }
        if (!this.mSpeg.isSetupWizardFinished()) {
            Slog.i("SPEG", "Feature is disabled until setup wizard is finished");
            return true;
        }
        if (this.mPm.isKidsHome()) {
            Slog.d("SPEG", "Feature is disabled for " + packageName + " due to KidsHome");
            return true;
        }
        if (pkg.getSharedUserId() != null) {
            Slog.d("PackageManager", "Feature is disabled for shared package");
            return true;
        }
        if (!checkSafeToCreateProfile("SPEG", baseApkPath)) {
            return true;
        }
        if (!checkSpegContinualLaunchesLimitViolation()) {
            return false;
        }
        Slog.i("SPEG", "Feature is disabled because of continual launches limit");
        return true;
    }

    public final boolean checkSafeToCreateProfile(String str, String str2) {
        try {
            String buildDexMetadataPathForApk = DexMetadataHelper.buildDexMetadataPathForApk(str2);
            if (new File(buildDexMetadataPathForApk).exists()) {
                if (!CAN_OVERRIDE_PROFILE) {
                    Slog.d(str, "Feature is disabled because base.dm present");
                    return false;
                }
                try {
                    ZipFile zipFile = new ZipFile(buildDexMetadataPathForApk);
                    try {
                        if (zipFile.getEntry("primary.prof") != null) {
                            Slog.d(str, "Feature is disabled because base.dm has profile");
                            zipFile.close();
                            return false;
                        }
                        Slog.d(str, "No primary.prof in base.dm");
                        zipFile.close();
                        return true;
                    } catch (Throwable th) {
                        try {
                            zipFile.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    Slog.e(str, "Feature is disabled because of exception: " + e.getMessage());
                    return false;
                }
            }
            if (!CAN_OVERRIDE_PROFILE) {
                return true;
            }
            if (!new File(str2 + ".prof").exists()) {
                return true;
            }
            Slog.d(str, "Feature is disabled because prebuilt profile already present");
            return false;
        } catch (IllegalStateException e2) {
            Slog.e(str, "Feature is disabled because of exception: " + e2.getMessage());
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0329 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0338 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x027e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0186 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x025c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void executePostCommitStepsLIF(List list) {
        boolean z;
        Iterator it;
        DexoptOptions dexoptOptions;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        DexoptOptions dexoptOptions2;
        String str;
        ArraySet arraySet = new ArraySet();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ReconciledPackage reconciledPackage = (ReconciledPackage) it2.next();
            InstallRequest installRequest = reconciledPackage.mInstallRequest;
            boolean z6 = (installRequest.getScanFlags() & IInstalld.FLAG_FORCE) != 0;
            boolean z7 = (installRequest.getScanFlags() & 67108864) != 0;
            AndroidPackageInternal pkg = installRequest.getScannedPackageSetting().getPkg();
            String packageName = pkg.getPackageName();
            String path = pkg.getPath();
            boolean z8 = this.mIncrementalManager != null && IncrementalManager.isIncrementalPath(path);
            String baseApkPath = pkg.getBaseApkPath();
            AndroidPackage existingPackage = installRequest.getExistingPackage();
            int uid = pkg.getUid();
            int appId = UserHandle.getAppId(pkg.getUid());
            SpqrService spqrService = (SpqrService) this.mInjector.getLocalService(SpqrService.class);
            if (!z8 && spqrService != null && existingPackage != null && !isDexoptLimited() && checkSafeToCreateProfile("SPQR", baseApkPath) && !spqrService.isPackageBlockListed(packageName) && !z6 && !pkg.isDebuggable() && !apkHasNumOfDexFiles(baseApkPath, 11) && (appId < 5000 || appId > 5999)) {
                File file = new File(Environment.getDataRefProfilesDePackageDirectory(packageName) + File.separator + ArtManager.getProfileName((String) null));
                if (file.length() > 0) {
                    z = spqrService.createInvariantProfileLI(existingPackage.getBaseApkPath(), baseApkPath, uid, appId, packageName, file.getAbsolutePath(), existingPackage.getVersionName(), pkg.getVersionName());
                    if (z8) {
                        IncrementalStorage openStorage = this.mIncrementalManager.openStorage(path);
                        if (openStorage == null) {
                            throw new IllegalArgumentException("Install: null storage for incremental package " + packageName);
                        }
                        arraySet.add(openStorage);
                    }
                    this.mAppDataHelper.prepareAppDataPostCommitLIF(pkg, 0);
                    if (installRequest.isClearCodeCache()) {
                        this.mAppDataHelper.clearAppDataLIF(pkg, -1, 39);
                    }
                    if (installRequest.isInstallReplace()) {
                        this.mDexManager.notifyPackageUpdated(pkg.getPackageName(), pkg.getBaseApkPath(), pkg.getSplitCodePaths());
                    }
                    int compilationReasonForInstallScenario = this.mDexManager.getCompilationReasonForInstallScenario(installRequest.getInstallScenario());
                    int i = (!(installRequest.getInstallReason() != 2 || installRequest.getInstallReason() == 3) ? IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES : 0) | 1029;
                    if (!z) {
                        it = it2;
                        dexoptOptions = new DexoptOptions(packageName, 21, "speed-profile", null, i);
                    } else {
                        it = it2;
                        dexoptOptions = new DexoptOptions(packageName, compilationReasonForInstallScenario, i);
                    }
                    DexoptOptions dexoptOptions3 = dexoptOptions;
                    if (installRequest.getReturnCode() == 1) {
                        try {
                            ASKSManager.getASKSManager().postASKSsetup(packageName, pkg.getBaseApkPath(), this.mPm.resolveUserIds(installRequest.getUserId())[0]);
                        } catch (RemoteException e) {
                            Log.e("PackageManager", "RemoteException: " + e.getMessage());
                        }
                    }
                    if (AppHibernationService.isAppHibernationEnabled()) {
                        z2 = false;
                    } else {
                        z2 = ((AppHibernationManagerInternal) this.mInjector.getLocalService(AppHibernationManagerInternal.class)).isHibernatingGlobally(packageName);
                        if (z2) {
                            Slog.i("PackageManager", packageName + ", hibernated during installation");
                        }
                    }
                    z3 = (reconciledPackage.mInstallRequest.getInstallFlags() & 67108864) == 0;
                    z4 = compilationReasonForInstallScenario != 3 && SystemProperties.getBoolean("persist.sys.app_install_boost", false);
                    if (z4) {
                        Log.i("PackageManager", "System is busy state and dexopt skipped!");
                    }
                    z5 = ((!z6 && Settings.Global.getInt(this.mContext.getContentResolver(), "instant_app_dexopt_enabled", 0) == 0) || pkg.isDebuggable() || z8 || z2 || !dexoptOptions3.isCompilationEnabled() || z3 || z4 || z7) ? false : true;
                    if (!z3) {
                        Log.d("PackageManager", "Skip dexopt by request");
                    } else if (!z5) {
                        Log.d("PackageManager", "Skip dexopt");
                    }
                    if (CoreRune.SYSFW_APP_SPEG && !SPEG_DISABLE_LAUNCH && this.mSpeg != null) {
                        if (!isSpegSkipped(reconciledPackage, z5 || z3)) {
                            this.mSpeg.mPrevInstalledPkg = packageName;
                            if (spegLaunchApp(reconciledPackage)) {
                                dexoptOptions2 = new DexoptOptions(packageName, 22, "speed-profile", null, i);
                                if (!DexOptHelper.useArtService()) {
                                    try {
                                        this.mArtManagerService.prepareAppProfiles((AndroidPackage) pkg, this.mPm.resolveUserIds(installRequest.getUserId()), true);
                                    } catch (Installer.LegacyDexoptDisabledException e2) {
                                        throw new RuntimeException(e2);
                                    }
                                }
                                if (z5) {
                                    if (SystemProperties.getBoolean("pm.precompile_layouts", false)) {
                                        Trace.traceBegin(262144L, "compileLayouts");
                                        this.mViewCompiler.compileLayouts(pkg);
                                        Trace.traceEnd(262144L);
                                    }
                                    Trace.traceBegin(262144L, "dexopt");
                                    PackageSetting realPackageSetting = installRequest.getRealPackageSetting();
                                    realPackageSetting.getPkgState().setUpdatedSystemApp(installRequest.getScannedPackageSetting().isUpdatedSystemApp());
                                    if (DexOptHelper.useArtService()) {
                                        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class)).withFilteredSnapshot();
                                        try {
                                            installRequest.onDexoptFinished(DexOptHelper.getArtManagerLocal().dexoptPackage(withFilteredSnapshot, packageName, dexoptOptions2.convertToDexoptParams(0)));
                                            if (withFilteredSnapshot != null) {
                                                withFilteredSnapshot.close();
                                            }
                                            str = packageName;
                                        } finally {
                                        }
                                    } else {
                                        try {
                                            str = packageName;
                                            this.mPackageDexOptimizer.performDexOpt(pkg, realPackageSetting, null, this.mPm.getOrCreateCompilerPackageStats(pkg), this.mDexManager.getPackageUseInfoOrDefault(packageName), dexoptOptions2);
                                        } catch (Installer.LegacyDexoptDisabledException e3) {
                                            throw new RuntimeException(e3);
                                        }
                                    }
                                    Trace.traceEnd(262144L);
                                } else {
                                    str = packageName;
                                }
                                if (!DexOptHelper.useArtService()) {
                                    try {
                                        BackgroundDexOptService.getService().notifyPackageChanged(str);
                                    } catch (Installer.LegacyDexoptDisabledException e4) {
                                        throw new RuntimeException(e4);
                                    }
                                }
                                it2 = it;
                            }
                        }
                    }
                    dexoptOptions2 = dexoptOptions3;
                    if (!DexOptHelper.useArtService()) {
                    }
                    if (z5) {
                    }
                    if (!DexOptHelper.useArtService()) {
                    }
                    it2 = it;
                }
            }
            z = false;
            if (z8) {
            }
            this.mAppDataHelper.prepareAppDataPostCommitLIF(pkg, 0);
            if (installRequest.isClearCodeCache()) {
            }
            if (installRequest.isInstallReplace()) {
            }
            int compilationReasonForInstallScenario2 = this.mDexManager.getCompilationReasonForInstallScenario(installRequest.getInstallScenario());
            int i2 = (!(installRequest.getInstallReason() != 2 || installRequest.getInstallReason() == 3) ? IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES : 0) | 1029;
            if (!z) {
            }
            DexoptOptions dexoptOptions32 = dexoptOptions;
            if (installRequest.getReturnCode() == 1) {
            }
            if (AppHibernationService.isAppHibernationEnabled()) {
            }
            if ((reconciledPackage.mInstallRequest.getInstallFlags() & 67108864) == 0) {
            }
            if (compilationReasonForInstallScenario2 != 3) {
            }
            if (z4) {
            }
            if (!z6) {
            }
            if (!z3) {
            }
            if (CoreRune.SYSFW_APP_SPEG) {
                if (!isSpegSkipped(reconciledPackage, z5 || z3)) {
                }
            }
            dexoptOptions2 = dexoptOptions32;
            if (!DexOptHelper.useArtService()) {
            }
            if (z5) {
            }
            if (!DexOptHelper.useArtService()) {
            }
            it2 = it;
        }
        PackageManagerServiceUtils.waitForNativeBinariesExtractionForIncremental(arraySet);
    }

    public Pair verifyReplacingVersionCode(PackageInfoLite packageInfoLite, long j, int i) {
        if (packageInfoLite.verifiers == null) {
            return Pair.create(-22, "Package verifiers are not set");
        }
        if ((131072 & i) != 0) {
            return verifyReplacingVersionCodeForApex(packageInfoLite, j, i);
        }
        String str = packageInfoLite.packageName;
        synchronized (this.mPm.mLock) {
            AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(str);
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
            if (androidPackage == null && packageLPr != null) {
                androidPackage = packageLPr.getPkg();
            }
            if (j != -1) {
                if (androidPackage == null) {
                    String str2 = "Required installed version code was " + j + " but package is not installed";
                    Slog.w("PackageManager", str2);
                    return Pair.create(-121, str2);
                }
                if (androidPackage.getLongVersionCode() != j) {
                    String str3 = "Required installed version code was " + j + " but actual installed version is " + androidPackage.getLongVersionCode();
                    Slog.w("PackageManager", str3);
                    return Pair.create(-121, str3);
                }
            }
            if (androidPackage != null && !androidPackage.isSdkLibrary()) {
                if (!PackageManagerServiceUtils.isDowngradePermitted(i, androidPackage.isDebuggable())) {
                    try {
                        PackageManagerServiceUtils.checkDowngrade(androidPackage, packageInfoLite);
                    } catch (PackageManagerException e) {
                        String str4 = "Downgrade detected: " + e.getMessage();
                        Slog.w("PackageManager", str4);
                        return Pair.create(-25, str4);
                    }
                } else if (packageLPr.isSystem()) {
                    PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageLPr);
                    if (disabledSystemPkgLPr != null) {
                        androidPackage = disabledSystemPkgLPr.getPkg();
                    }
                    if (!Build.IS_DEBUGGABLE && !androidPackage.isDebuggable()) {
                        try {
                            PackageManagerServiceUtils.checkDowngrade(androidPackage, packageInfoLite);
                        } catch (PackageManagerException e2) {
                            String str5 = "System app: " + str + " cannot be downgraded to older than its preloaded version on the system image. " + e2.getMessage();
                            Slog.w("PackageManager", str5);
                            return Pair.create(-25, str5);
                        }
                    }
                }
            }
            return Pair.create(1, null);
        }
    }

    public final Pair verifyReplacingVersionCodeForApex(PackageInfoLite packageInfoLite, long j, int i) {
        String str = packageInfoLite.packageName;
        PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(str, 1073741824L, 0);
        if (packageInfo == null) {
            String str2 = "Attempting to install new APEX package " + str;
            Slog.w("PackageManager", str2);
            return Pair.create(-23, str2);
        }
        long longVersionCode = packageInfo.getLongVersionCode();
        if (j != -1 && longVersionCode != j) {
            String str3 = "Installed version of APEX package " + str + " does not match required. Active version: " + longVersionCode + " required: " + j;
            Slog.w("PackageManager", str3);
            return Pair.create(-121, str3);
        }
        boolean z = (packageInfo.applicationInfo.flags & 2) != 0;
        long longVersionCode2 = packageInfoLite.getLongVersionCode();
        if (!PackageManagerServiceUtils.isDowngradePermitted(i, z) && longVersionCode2 < longVersionCode) {
            String str4 = "Downgrade of APEX package " + str + " is not allowed. Active version: " + longVersionCode + " attempted: " + longVersionCode2;
            Slog.w("PackageManager", str4);
            return Pair.create(-25, str4);
        }
        return Pair.create(1, null);
    }

    public int getUidForVerifier(VerifierInfo verifierInfo) {
        synchronized (this.mPm.mLock) {
            AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(verifierInfo.packageName);
            if (androidPackage == null) {
                return -1;
            }
            if (androidPackage.getSigningDetails().getSignatures().length != 1) {
                Slog.i("PackageManager", "Verifier package " + verifierInfo.packageName + " has more than one signature; ignoring");
                return -1;
            }
            try {
                if (!Arrays.equals(verifierInfo.publicKey.getEncoded(), androidPackage.getSigningDetails().getSignatures()[0].getPublicKey().getEncoded())) {
                    Slog.i("PackageManager", "Verifier package " + verifierInfo.packageName + " does not have the expected public key; ignoring");
                    return -1;
                }
                return androidPackage.getUid();
            } catch (CertificateException unused) {
                return -1;
            }
        }
    }

    public void sendPendingBroadcasts() {
        ArrayList arrayList;
        synchronized (this.mPm.mLock) {
            SparseArray copiedMap = this.mPm.mPendingBroadcasts.copiedMap();
            int size = copiedMap.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i += ((ArrayMap) copiedMap.valueAt(i2)).size();
            }
            if (i == 0) {
                return;
            }
            String[] strArr = new String[i];
            ArrayList[] arrayListArr = new ArrayList[i];
            int[] iArr = new int[i];
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                int keyAt = copiedMap.keyAt(i4);
                ArrayMap arrayMap = (ArrayMap) copiedMap.valueAt(i4);
                int size2 = CollectionUtils.size(arrayMap);
                for (int i5 = 0; i5 < size2; i5++) {
                    strArr[i3] = (String) arrayMap.keyAt(i5);
                    arrayListArr[i3] = (ArrayList) arrayMap.valueAt(i5);
                    PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(strArr[i3]);
                    iArr[i3] = packageLPr != null ? UserHandle.getUid(keyAt, packageLPr.getAppId()) : -1;
                    i3++;
                }
            }
            this.mPm.mPendingBroadcasts.clear();
            Computer snapshotComputer = this.mPm.snapshotComputer();
            for (int i6 = 0; i6 < i3; i6++) {
                if (PMRune.PM_WA_WORK_COMP_CHANGED && (arrayList = arrayListArr[i6]) != null && arrayList.size() == 1 && arrayListArr[i6].contains("androidx.work.impl.background.systemalarm.RescheduleReceiver")) {
                    Slog.d("PackageManager", "Don't send PACKAGE_CHANGED for " + strArr[i6] + " by WorkManager");
                } else {
                    this.mPm.sendPackageChangedBroadcast(snapshotComputer, strArr[i6], true, arrayListArr[i6], iArr[i6], null);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x04fe  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x053c  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0549  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0545 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x05d7  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x04d6  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0207  */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.server.pm.PackageManagerService] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.server.pm.AsecInstallHelper] */
    /* JADX WARN: Type inference failed for: r11v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v28, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v38, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.server.pm.InstallRequest] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18, types: [int] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.server.pm.InstallRequest] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r8v15, types: [com.android.server.pm.PackageManagerService] */
    /* JADX WARN: Type inference failed for: r9v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v5, types: [com.android.server.pm.PackageManagerService] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handlePackagePostInstall(InstallRequest installRequest, boolean z) {
        int i;
        String str;
        boolean z2;
        ?? asecInstallHelper;
        VolumeInfo mountedExternalVolume;
        String str2;
        String str3;
        int[] iArr;
        boolean z3;
        int length;
        int i2;
        Bundle bundle;
        int i3;
        InstallArgs installArgs;
        int length2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        SparseArray visibilityAllowList;
        int packageExternalStorageType;
        ICertificatePolicy asInterface;
        PackageStateInternal packageStateInternal;
        int[] iArr2;
        boolean z4;
        boolean z5;
        ?? r3 = 0;
        boolean z6 = (installRequest.getInstallFlags() & IInstalld.FLAG_USE_QUOTA) == 0;
        boolean z7 = (installRequest.getInstallFlags() & 65536) != 0;
        String installerPackageName = installRequest.getInstallerPackageName();
        int dataLoaderType = installRequest.getDataLoaderType();
        boolean z8 = installRequest.getReturnCode() == 1;
        boolean isUpdate = installRequest.isUpdate();
        String name = installRequest.getName();
        PackageStateInternal packageStateInternal2 = z8 ? this.mPm.snapshotComputer().getPackageStateInternal(name) : null;
        boolean z9 = packageStateInternal2 == null || (packageStateInternal2.isSystem() && !packageStateInternal2.getPath().getPath().equals(installRequest.getPkg().getPath()));
        if (z8 && z9) {
            Slog.e("PackageManager", name + " was removed before handlePackagePostInstall could be executed");
            installRequest.setReturnCode(-23);
            installRequest.setReturnMessage("Package was removed before install could complete.");
            this.mRemovePackageHelper.cleanUpResources(installRequest.getOldCodeFile(), installRequest.getOldInstructionSet());
            this.mPm.notifyInstallObserver(installRequest);
            return;
        }
        if (z8) {
            this.mPm.mPerUidReadTimeoutsCache = null;
            if (installRequest.getRemovedInfo() != null) {
                if (installRequest.getRemovedInfo().mIsExternal) {
                    String[] strArr = {installRequest.getRemovedInfo().mRemovedPackage};
                    int[] iArr3 = {installRequest.getRemovedInfo().mUid};
                    BroadcastHelper broadcastHelper = this.mBroadcastHelper;
                    PackageManagerService packageManagerService = this.mPm;
                    Objects.requireNonNull(packageManagerService);
                    broadcastHelper.sendResourcesChangedBroadcast(new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService), false, true, strArr, iArr3);
                }
                installRequest.getRemovedInfo().sendPackageRemovedBroadcasts(z6, false);
            }
            if (installRequest.getInstallerPackageName() != null) {
                str3 = installRequest.getInstallerPackageName();
            } else if (installRequest.getRemovedInfo() != null) {
                str3 = installRequest.getRemovedInfo().mInstallerPackageName;
            } else {
                str2 = null;
                this.mPm.notifyInstantAppPackageInstalled(installRequest.getPkg().getPackageName(), installRequest.getNewUsers());
                iArr = PackageManagerService.EMPTY_INT_ARRAY;
                z3 = installRequest.getOriginUsers() != null || installRequest.getOriginUsers().length == 0;
                int[] newUsers = installRequest.getNewUsers();
                length = newUsers.length;
                i2 = 0;
                int[] iArr4 = iArr;
                int[] iArr5 = iArr4;
                int[] iArr6 = iArr5;
                while (i2 < length) {
                    int i9 = length;
                    int i10 = newUsers[i2];
                    boolean isInstantApp = packageStateInternal2.getUserStateOrDefault(i10).isInstantApp();
                    if (z3) {
                        if (isInstantApp) {
                            iArr5 = ArrayUtils.appendInt(iArr5, i10);
                        } else {
                            iArr = ArrayUtils.appendInt(iArr, i10);
                        }
                        packageStateInternal = packageStateInternal2;
                        iArr2 = newUsers;
                        z4 = z8;
                    } else {
                        packageStateInternal = packageStateInternal2;
                        int[] originUsers = installRequest.getOriginUsers();
                        iArr2 = newUsers;
                        int length3 = originUsers.length;
                        z4 = z8;
                        int i11 = 0;
                        while (true) {
                            if (i11 >= length3) {
                                z5 = true;
                                break;
                            }
                            int i12 = length3;
                            if (originUsers[i11] == i10) {
                                z5 = false;
                                break;
                            } else {
                                i11++;
                                length3 = i12;
                            }
                        }
                        if (z5) {
                            if (isInstantApp) {
                                iArr5 = ArrayUtils.appendInt(iArr5, i10);
                            } else {
                                iArr = ArrayUtils.appendInt(iArr, i10);
                            }
                        } else if (isInstantApp) {
                            iArr6 = ArrayUtils.appendInt(iArr6, i10);
                        } else {
                            iArr4 = ArrayUtils.appendInt(iArr4, i10);
                        }
                    }
                    i2++;
                    length = i9;
                    packageStateInternal2 = packageStateInternal;
                    newUsers = iArr2;
                    z8 = z4;
                }
                z2 = z8;
                bundle = new Bundle();
                if (installerPackageName == null && installerPackageName.equals("com.android.vending") && (asInterface = ICertificatePolicy.Stub.asInterface(ServiceManager.getService("certificate_policy"))) != null) {
                    i3 = 0;
                    try {
                        if (asInterface.isSignatureIdentityInformationEnabled((ContextInfo) null, false)) {
                            bundle.putBoolean("isMarketInstallation", true);
                        }
                    } catch (RemoteException unused) {
                    }
                } else {
                    i3 = 0;
                }
                bundle.putInt("android.intent.extra.UID", installRequest.getAppId());
                if (isUpdate) {
                    bundle.putBoolean("android.intent.extra.REPLACING", true);
                }
                bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", dataLoaderType);
                if (str2 != null && installRequest.getPkg().getStaticSharedLibraryName() != null) {
                    this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", name, bundle, 0, str2, null, installRequest.getNewUsers(), null, null, null);
                }
                if (installRequest.getPkg().getStaticSharedLibraryName() != null) {
                    this.mPm.mProcessLoggingHandler.invalidateBaseApkHash(installRequest.getPkg().getBaseApkPath());
                    int appId = UserHandle.getAppId(installRequest.getAppId());
                    boolean isInstallSystem = installRequest.isInstallSystem();
                    ?? r8 = this.mPm;
                    Computer snapshotComputer = r8.snapshotComputer();
                    int[] iArr7 = iArr;
                    i = i3;
                    String str4 = str2;
                    int[] iArr8 = iArr6;
                    str = name;
                    r8.sendPackageAddedForNewUsers(snapshotComputer, name, (isInstallSystem || z7) ? 1 : i3, z7, appId, iArr7, iArr5, dataLoaderType);
                    synchronized (this.mPm.mLock) {
                        Computer snapshotComputer2 = this.mPm.snapshotComputer();
                        visibilityAllowList = this.mPm.mAppsFilter.getVisibilityAllowList(snapshotComputer2, snapshotComputer2.getPackageStateInternal(str, 1000), iArr4, this.mPm.mSettings.getPackagesLocked());
                    }
                    this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, iArr4, iArr8, visibilityAllowList, null);
                    if (str4 != null) {
                        this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str4, null, iArr4, iArr8, null, null);
                    }
                    if (BroadcastHelper.isPrivacySafetyLabelChangeNotificationsEnabled(this.mContext)) {
                        PackageManagerService packageManagerService2 = this.mPm;
                        packageManagerService2.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, packageManagerService2.mRequiredPermissionControllerPackage, null, iArr4, iArr8, null, null);
                    }
                    String[] strArr2 = this.mPm.mRequiredVerifierPackages;
                    int length4 = strArr2.length;
                    for (int i13 = i; i13 < length4; i13++) {
                        String str5 = strArr2[i13];
                        if (str5 != null && !str5.equals(str4)) {
                            this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str5, null, iArr4, iArr8, null, null);
                        }
                    }
                    PackageManagerService packageManagerService3 = this.mPm;
                    String str6 = packageManagerService3.mRequiredInstallerPackage;
                    if (str6 != null) {
                        packageManagerService3.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 16777216, str6, null, iArr7, iArr8, null, null);
                    }
                    if (isUpdate) {
                        this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, null, null, iArr4, iArr8, installRequest.getRemovedInfo().mBroadcastAllowList, null);
                        if (str4 != null) {
                            this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, str4, null, iArr4, iArr8, null, null);
                        }
                        String[] strArr3 = this.mPm.mRequiredVerifierPackages;
                        int length5 = strArr3.length;
                        for (int i14 = i; i14 < length5; i14++) {
                            String str7 = strArr3[i14];
                            if (str7 != null && !str7.equals(str4)) {
                                this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, str7, null, iArr4, iArr8, null, null);
                            }
                        }
                        this.mPm.sendPackageBroadcast("android.intent.action.MY_PACKAGE_REPLACED", null, null, 0, str, null, iArr4, iArr8, null, this.mBroadcastHelper.getTemporaryAppAllowlistBroadcastOptions(311).toBundle());
                    } else if (z && !installRequest.isInstallSystem()) {
                        iArr = iArr7;
                        String str8 = installerPackageName;
                        this.mBroadcastHelper.sendFirstLaunchBroadcast(str, str8, iArr, iArr5);
                        r3 = str8;
                        if (installRequest.getPkg().isExternalStorage()) {
                            if (!isUpdate && (packageExternalStorageType = PackageManagerServiceUtils.getPackageExternalStorageType(((StorageManager) this.mInjector.getSystemService(StorageManager.class)).findVolumeByUuid(StorageManager.convert(installRequest.getPkg().getVolumeUuid()).toString()), installRequest.getPkg().isExternalStorage())) != 0) {
                                FrameworkStatsLog.write(181, packageExternalStorageType, str);
                            }
                            int[] iArr9 = {installRequest.getPkg().getUid()};
                            BroadcastHelper broadcastHelper2 = this.mBroadcastHelper;
                            PackageManagerService packageManagerService4 = this.mPm;
                            Objects.requireNonNull(packageManagerService4);
                            broadcastHelper2.sendResourcesChangedBroadcast(new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService4), true, true, new String[]{str}, iArr9);
                        }
                    }
                    iArr = iArr7;
                    r3 = installerPackageName;
                    if (installRequest.getPkg().isExternalStorage()) {
                    }
                } else {
                    r3 = installerPackageName;
                    i = i3;
                    str = name;
                    if (!ArrayUtils.isEmpty(installRequest.getLibraryConsumers())) {
                        Computer snapshotComputer3 = this.mPm.snapshotComputer();
                        int i15 = (isUpdate || installRequest.getPkg().getStaticSharedLibraryName() == null) ? i : 1;
                        for (int i16 = i; i16 < installRequest.getLibraryConsumers().size(); i16++) {
                            AndroidPackage androidPackage = (AndroidPackage) installRequest.getLibraryConsumers().get(i16);
                            this.mPm.sendPackageChangedBroadcast(snapshotComputer3, androidPackage.getPackageName(), i15, new ArrayList(Collections.singletonList(androidPackage.getPackageName())), androidPackage.getUid(), null);
                        }
                    }
                }
                if (iArr.length > 0) {
                    int length6 = iArr.length;
                    for (int i17 = i; i17 < length6; i17++) {
                        this.mPm.restorePermissionsAndUpdateRolesForNewUserInstall(str, iArr[i17]);
                    }
                }
                if (!z3 && !isUpdate) {
                    this.mPm.notifyPackageAdded(str, installRequest.getAppId());
                } else {
                    this.mPm.notifyPackageChanged(str, installRequest.getAppId());
                }
                EventLog.writeEvent(3110, getUnknownSourcesSettings());
                installArgs = installRequest.getRemovedInfo() == null ? installRequest.getRemovedInfo().mArgs : null;
                if (installArgs != null) {
                    VMRuntime.getRuntime().requestConcurrentGC();
                } else if (!z6) {
                    this.mPm.scheduleDeferredNoKillPostDelete(installArgs);
                } else {
                    this.mRemovePackageHelper.cleanUpResources(installArgs.mCodeFile, installArgs.mInstructionSets);
                }
                Computer snapshotComputer4 = this.mPm.snapshotComputer();
                length2 = iArr.length;
                for (i4 = i; i4 < length2; i4++) {
                    int i18 = iArr[i4];
                    PackageInfo packageInfo = snapshotComputer4.getPackageInfo(str, 0L, i18);
                    if (packageInfo != null) {
                        this.mDexManager.notifyPackageInstalled(packageInfo, i18);
                    }
                }
                if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
                    if (r3 != 0) {
                        i8 = TextUtils.equals("PrePackageInstaller", r3);
                    } else if (installRequest.getInstallSource() != null && installRequest.getInstallSource().mInitiatingPackageName != null) {
                        i8 = TextUtils.equals("PrePackageInstaller", installRequest.getInstallSource().mInitiatingPackageName);
                    } else {
                        i5 = i;
                        r3 = r3;
                        if (str != null && this.mMonetizationUtils.isMonetizedPreloadApp(str) && i5 != 0) {
                            int[] newUsers2 = installRequest.getNewUsers();
                            r3 = newUsers2.length;
                            i6 = i;
                            while (true) {
                                if (i6 < r3) {
                                    i7 = i;
                                    break;
                                } else {
                                    if (newUsers2[i6] == 0) {
                                        i7 = 1;
                                        break;
                                    }
                                    i6++;
                                }
                            }
                            if (i7 != 0) {
                                r3 = 1;
                                this.mMonetizationUtils.updateSettingsForMonetization(str, true, true, true);
                            }
                        }
                    }
                    r3 = 1;
                    i5 = i8 ^ 1;
                    if (str != null) {
                        int[] newUsers22 = installRequest.getNewUsers();
                        r3 = newUsers22.length;
                        i6 = i;
                        while (true) {
                            if (i6 < r3) {
                            }
                            i6++;
                        }
                        if (i7 != 0) {
                        }
                    }
                }
            }
            str2 = str3;
            this.mPm.notifyInstantAppPackageInstalled(installRequest.getPkg().getPackageName(), installRequest.getNewUsers());
            iArr = PackageManagerService.EMPTY_INT_ARRAY;
            if (installRequest.getOriginUsers() != null) {
            }
            int[] newUsers3 = installRequest.getNewUsers();
            length = newUsers3.length;
            i2 = 0;
            int[] iArr42 = iArr;
            int[] iArr52 = iArr42;
            int[] iArr62 = iArr52;
            while (i2 < length) {
            }
            z2 = z8;
            bundle = new Bundle();
            if (installerPackageName == null) {
            }
            i3 = 0;
            bundle.putInt("android.intent.extra.UID", installRequest.getAppId());
            if (isUpdate) {
            }
            bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", dataLoaderType);
            if (str2 != null) {
                this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", name, bundle, 0, str2, null, installRequest.getNewUsers(), null, null, null);
            }
            if (installRequest.getPkg().getStaticSharedLibraryName() != null) {
            }
            if (iArr.length > 0) {
            }
            if (!z3) {
            }
            this.mPm.notifyPackageChanged(str, installRequest.getAppId());
            EventLog.writeEvent(3110, getUnknownSourcesSettings());
            if (installRequest.getRemovedInfo() == null) {
            }
            if (installArgs != null) {
            }
            Computer snapshotComputer42 = this.mPm.snapshotComputer();
            length2 = iArr.length;
            while (i4 < length2) {
            }
            if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            }
        } else {
            i = 0;
            str = name;
            z2 = z8;
        }
        if (z2 && installRequest.getNeedToMove()) {
            try {
                asecInstallHelper = this.mPm.getAsecInstallHelper();
                mountedExternalVolume = asecInstallHelper.getMountedExternalVolume();
            } catch (RuntimeException e) {
                e = e;
                r3 = installRequest;
            }
            try {
                if (mountedExternalVolume != null) {
                    if (mountedExternalVolume.fsUuid != null) {
                        r3 = installRequest;
                        asecInstallHelper.setMoveCallback(r3);
                        try {
                            getIPackageManager().movePackage(str, mountedExternalVolume.fsUuid);
                            r3 = r3;
                        } catch (RemoteException e2) {
                            Log.w("PackageManager", "RemoteException - " + e2);
                            r3 = r3;
                        }
                    }
                }
                InstallRequest installRequest2 = installRequest;
                this.mPm.notifyInstallObserver(installRequest2);
                r3 = installRequest2;
            } catch (RuntimeException e3) {
                e = e3;
                Slog.i("PackageManager", "Automatic move failed", e);
                this.mPm.notifyInstallObserver(r3);
                this.mPm.schedulePruneUnusedStaticSharedLibraries(true);
                if (installRequest.getTraceMethod() != null) {
                }
                this.mPm.mPmLifecycle.onPackageInstalled(str, null, installRequest.getNewUsers(), z2);
            }
        } else {
            if (z2 && isUpdate) {
                i = 1;
            }
            if (i == 0) {
                this.mPm.notifyInstallObserver(installRequest);
            } else if (z6) {
                this.mPm.scheduleDeferredPendingKillInstallObserver(installRequest);
            } else {
                this.mPm.scheduleDeferredNoKillInstallObserver(installRequest);
            }
        }
        this.mPm.schedulePruneUnusedStaticSharedLibraries(true);
        if (installRequest.getTraceMethod() != null) {
            Trace.asyncTraceEnd(262144L, installRequest.getTraceMethod(), installRequest.getTraceCookie());
        }
        this.mPm.mPmLifecycle.onPackageInstalled(str, null, installRequest.getNewUsers(), z2);
    }

    public final int getUnknownSourcesSettings() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "install_non_market_apps", -1, 0);
    }

    public void installSystemStubPackages(List list, int i) {
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            String str = (String) list.get(size);
            if (this.mPm.mSettings.isDisabledSystemPackageLPr(str)) {
                list.remove(size);
            } else {
                AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(str);
                if (androidPackage == null) {
                    list.remove(size);
                } else {
                    PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                    if (packageLPr != null && packageLPr.getEnabled(0) == 3) {
                        list.remove(size);
                    } else {
                        try {
                            installStubPackageLI(androidPackage, 0, i);
                            packageLPr.setEnabled(0, 0, "android");
                            list.remove(size);
                        } catch (PackageManagerException e) {
                            Slog.e("PackageManager", "Failed to parse uncompressed system package: " + e.getMessage());
                        }
                    }
                }
            }
        }
        for (int size2 = list.size() - 1; size2 >= 0; size2 += -1) {
            String str2 = (String) list.get(size2);
            this.mPm.mSettings.getPackageLPr(str2).setEnabled(2, 0, "android");
            PackageManagerServiceUtils.logCriticalInfo(6, "Stub disabled; pkg: " + str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006c A[Catch: all -> 0x009a, PackageManagerException -> 0x009f, TRY_ENTER, TRY_LEAVE, TryCatch #12 {PackageManagerException -> 0x009f, blocks: (B:20:0x006c, B:47:0x0099, B:46:0x0096), top: B:7:0x0026 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean enableCompressedPackage(AndroidPackage androidPackage, PackageSetting packageSetting) {
        PackageFreezer freezePackage;
        PackageManagerTracedLock packageManagerTracedLock;
        int defParseFlags = this.mPm.getDefParseFlags() | Integer.MIN_VALUE | 64;
        synchronized (this.mPm.mInstallLock) {
            try {
                freezePackage = this.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16);
            } catch (PackageManagerException unused) {
            }
            try {
                try {
                    AndroidPackage installStubPackageLI = installStubPackageLI(androidPackage, defParseFlags, 0);
                    this.mAppDataHelper.prepareAppDataAfterInstallLIF(installStubPackageLI);
                    PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                    try {
                        synchronized (packageManagerTracedLock2) {
                            try {
                                packageManagerTracedLock = packageManagerTracedLock2;
                                try {
                                    this.mSharedLibraries.updateSharedLibraries(installStubPackageLI, packageSetting, null, null, Collections.unmodifiableMap(this.mPm.mPackages));
                                } catch (PackageManagerException e) {
                                    e = e;
                                    Slog.w("PackageManager", "updateAllSharedLibrariesLPw failed: ", e);
                                    this.mPm.mPermissionManager.onPackageInstalled(installStubPackageLI, -1, PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT, -1);
                                    this.mPm.writeSettingsLPrTEMP();
                                    if (freezePackage != null) {
                                    }
                                    this.mAppDataHelper.clearAppDataLIF(installStubPackageLI, -1, 39);
                                    this.mDexManager.notifyPackageUpdated(installStubPackageLI.getPackageName(), installStubPackageLI.getBaseApkPath(), installStubPackageLI.getSplitCodePaths());
                                    return true;
                                }
                            } catch (PackageManagerException e2) {
                                e = e2;
                                packageManagerTracedLock = packageManagerTracedLock2;
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                            this.mPm.mPermissionManager.onPackageInstalled(installStubPackageLI, -1, PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT, -1);
                            this.mPm.writeSettingsLPrTEMP();
                            if (freezePackage != null) {
                                freezePackage.close();
                            }
                            this.mAppDataHelper.clearAppDataLIF(installStubPackageLI, -1, 39);
                            this.mDexManager.notifyPackageUpdated(installStubPackageLI.getPackageName(), installStubPackageLI.getBaseApkPath(), installStubPackageLI.getSplitCodePaths());
                            return true;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    throw th;
                } finally {
                }
            } catch (PackageManagerException unused2) {
                try {
                    try {
                        freezePackage = this.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16);
                    } catch (Throwable th3) {
                        synchronized (this.mPm.mLock) {
                            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                            if (packageLPr != null) {
                                packageLPr.setEnabled(2, 0, "android");
                            }
                            this.mPm.writeSettingsLPrTEMP();
                            throw th3;
                        }
                    }
                } catch (PackageManagerException e3) {
                    Slog.wtf("PackageManager", "Failed to restore system package:" + androidPackage.getPackageName(), e3);
                    synchronized (this.mPm.mLock) {
                        PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                        if (packageLPr2 != null) {
                            packageLPr2.setEnabled(2, 0, "android");
                        }
                        this.mPm.writeSettingsLPrTEMP();
                        return false;
                    }
                }
                try {
                    synchronized (this.mPm.mLock) {
                        this.mPm.mSettings.enableSystemPackageLPw(androidPackage.getPackageName());
                    }
                    installPackageFromSystemLIF(androidPackage.getPath(), this.mPm.mUserManager.getUserIds(), null, true);
                    if (freezePackage != null) {
                        freezePackage.close();
                    }
                    synchronized (this.mPm.mLock) {
                        PackageSetting packageLPr3 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                        if (packageLPr3 != null) {
                            packageLPr3.setEnabled(2, 0, "android");
                        }
                        this.mPm.writeSettingsLPrTEMP();
                    }
                    return false;
                } finally {
                }
            }
        }
    }

    public final AndroidPackage installStubPackageLI(AndroidPackage androidPackage, int i, int i2) {
        if (PackageManagerService.DEBUG_COMPRESSION) {
            Slog.i("PackageManager", "Uncompressing system stub; pkg: " + androidPackage.getPackageName());
        }
        File decompressPackage = decompressPackage(androidPackage.getPackageName(), androidPackage.getPath());
        if (decompressPackage == null) {
            throw PackageManagerException.ofInternalError("Unable to decompress stub at " + androidPackage.getPath(), -11);
        }
        synchronized (this.mPm.mLock) {
            this.mPm.mSettings.disableSystemPackageLPw(androidPackage.getPackageName(), true);
        }
        RemovePackageHelper removePackageHelper = new RemovePackageHelper(this.mPm);
        removePackageHelper.removePackage(androidPackage, true);
        try {
            return initPackageTracedLI(decompressPackage, i, i2);
        } catch (PackageManagerException e) {
            Slog.w("PackageManager", "Failed to install compressed system package:" + androidPackage.getPackageName(), e);
            removePackageHelper.removeCodePath(decompressPackage);
            throw e;
        }
    }

    public final File decompressPackage(String str, String str2) {
        if (!PackageManagerServiceUtils.compressedFileExists(str2)) {
            if (PackageManagerService.DEBUG_COMPRESSION) {
                Slog.i("PackageManager", "No files to decompress at: " + str2);
            }
            return null;
        }
        File nextCodePath = PackageManagerServiceUtils.getNextCodePath(Environment.getDataAppDirectory(null), str);
        int decompressFiles = PackageManagerServiceUtils.decompressFiles(str2, nextCodePath, str);
        if (decompressFiles == 1) {
            decompressFiles = PackageManagerServiceUtils.extractNativeBinaries(nextCodePath, str);
        }
        if (decompressFiles == 1) {
            if (!this.mPm.isSystemReady()) {
                PackageManagerService packageManagerService = this.mPm;
                if (packageManagerService.mReleaseOnSystemReady == null) {
                    packageManagerService.mReleaseOnSystemReady = new ArrayList();
                }
                this.mPm.mReleaseOnSystemReady.add(nextCodePath);
            } else {
                F2fsUtils.releaseCompressedBlocks(this.mContext.getContentResolver(), nextCodePath);
            }
            return nextCodePath;
        }
        if (!nextCodePath.exists()) {
            return null;
        }
        new RemovePackageHelper(this.mPm).removeCodePath(nextCodePath);
        return null;
    }

    public void restoreDisabledSystemPackageLIF(DeletePackageAction deletePackageAction, int[] iArr, boolean z) {
        PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        PackageSetting packageSetting2 = deletePackageAction.mDisabledPs;
        synchronized (this.mPm.mLock) {
            this.mPm.mSettings.enableSystemPackageLPw(packageSetting2.getPkg().getPackageName());
            PackageManagerServiceUtils.removeNativeBinariesLI(packageSetting);
        }
        try {
            try {
                synchronized (this.mPm.mInstallLock) {
                    installPackageFromSystemLIF(packageSetting2.getPathString(), iArr, packageRemovedInfo == null ? null : packageRemovedInfo.mOrigUsers, z);
                }
                if (packageSetting2.getPkg().isStub()) {
                    synchronized (this.mPm.mLock) {
                        disableStubPackage(deletePackageAction, packageSetting, iArr);
                    }
                }
            } catch (PackageManagerException e) {
                Slog.w("PackageManager", "Failed to restore system package:" + packageSetting.getPackageName() + ": " + e.getMessage());
                throw new SystemDeleteException(e);
            }
        } catch (Throwable th) {
            if (packageSetting2.getPkg().isStub()) {
                synchronized (this.mPm.mLock) {
                    disableStubPackage(deletePackageAction, packageSetting, iArr);
                }
            }
            throw th;
        }
    }

    public final void disableStubPackage(DeletePackageAction deletePackageAction, PackageSetting packageSetting, int[] iArr) {
        PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(packageSetting.getPackageName());
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

    public final void installPackageFromSystemLIF(String str, int[] iArr, int[] iArr2, boolean z) {
        File file = new File(str);
        AndroidPackage initPackageTracedLI = initPackageTracedLI(file, this.mPm.getDefParseFlags() | 1 | 16, this.mPm.getSystemPackageScanFlags(file));
        synchronized (this.mPm.mLock) {
            try {
                this.mSharedLibraries.updateSharedLibraries(initPackageTracedLI, this.mPm.mSettings.getPackageLPr(initPackageTracedLI.getPackageName()), null, null, Collections.unmodifiableMap(this.mPm.mPackages));
            } catch (PackageManagerException e) {
                Slog.e("PackageManager", "updateAllSharedLibrariesLPw failed: " + e.getMessage());
            }
        }
        this.mAppDataHelper.prepareAppDataAfterInstallLIF(initPackageTracedLI);
        setPackageInstalledForSystemPackage(initPackageTracedLI, iArr, iArr2, z);
    }

    public final void setPackageInstalledForSystemPackage(AndroidPackage androidPackage, int[] iArr, int[] iArr2, boolean z) {
        synchronized (this.mPm.mLock) {
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            boolean z2 = iArr2 != null;
            if (z2) {
                boolean z3 = false;
                for (int i : iArr) {
                    boolean contains = ArrayUtils.contains(iArr2, i);
                    if (contains != packageLPr.getInstalled(i)) {
                        z3 = true;
                    }
                    packageLPr.setInstalled(contains, i);
                    if (contains) {
                        packageLPr.setUninstallReason(0, i);
                    }
                }
                this.mPm.mSettings.writeAllUsersPackageRestrictionsLPr();
                if (z3) {
                    this.mPm.mSettings.writeKernelMappingLPr(packageLPr);
                }
            }
            this.mPm.mPermissionManager.onPackageInstalled(androidPackage, -1, PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT, -1);
            for (int i2 : iArr) {
                if (z2) {
                    this.mPm.mSettings.writePermissionStateForUserLPr(i2, false);
                }
            }
            if (z) {
                this.mPm.writeSettingsLPrTEMP();
            }
        }
    }

    public void prepareSystemPackageCleanUp(WatchedArrayMap watchedArrayMap, List list, ArrayMap arrayMap, final int[] iArr) {
        List parsePackages = this.mPm.isDeviceUpgrading() ? new PmConfigParser().parsePackages("/system/etc/system_to_data_app_list.xml") : null;
        for (int size = watchedArrayMap.size() - 1; size >= 0; size--) {
            final PackageSetting packageSetting = (PackageSetting) watchedArrayMap.valueAt(size);
            String packageName = packageSetting.getPackageName();
            if (packageSetting.isSystem()) {
                AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(packageName);
                PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageName);
                if (androidPackage != null) {
                    if (!androidPackage.isApex() && disabledSystemPkgLPr != null) {
                        PackageManagerServiceUtils.logCriticalInfo(5, "Expecting better updated system app for " + packageName + "; removing system app.  Last known codePath=" + packageSetting.getPathString() + ", versionCode=" + packageSetting.getVersionCode() + "; scanned versionCode=" + androidPackage.getLongVersionCode());
                        this.mRemovePackageHelper.removePackage(androidPackage, true);
                        arrayMap.put(packageSetting.getPackageName(), packageSetting.getPath());
                    }
                } else if (disabledSystemPkgLPr == null) {
                    if (parsePackages != null && !parsePackages.isEmpty() && parsePackages.contains(packageSetting.getName())) {
                        packageSetting.setFlags(-2);
                        PackageManagerServiceUtils.logCriticalInfo(5, "Don't remove this system package " + packageSetting.getName() + "; It will be re-installed in data partition.");
                    } else {
                        PackageManagerServiceUtils.logCriticalInfo(5, "System package " + packageName + " no longer exists; its data will be wiped");
                        this.mInjector.getHandler().post(new Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                InstallPackageHelper.this.lambda$prepareSystemPackageCleanUp$4(packageSetting, iArr);
                            }
                        });
                    }
                } else if (disabledSystemPkgLPr.getPath() == null || !disabledSystemPkgLPr.getPath().exists() || disabledSystemPkgLPr.getPkg() == null) {
                    list.add(packageName);
                } else {
                    arrayMap.put(disabledSystemPkgLPr.getPackageName(), disabledSystemPkgLPr.getPath());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareSystemPackageCleanUp$4(PackageSetting packageSetting, int[] iArr) {
        this.mRemovePackageHelper.removePackageData(packageSetting, iArr, null, 0, false);
    }

    public boolean needToRemoveNonInstalledOverlayLPr(PackageSetting packageSetting) {
        return (packageSetting != null && packageSetting.getPathString() != null && packageSetting.getPathString().startsWith("/data/overlays")) && packageSetting.getPkg() == null;
    }

    public void clearNonInstalledOverlaysLIF(ArrayList arrayList) {
        int[] userIds = this.mPm.mUserManager.getUserIds();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            PackageSetting packageSetting = (PackageSetting) arrayList.get(i);
            PackageManagerServiceUtils.logCriticalInfo(5, "Clear non-installed overlay package " + packageSetting.getPackageName());
            this.mRemovePackageHelper.removePackageDataLIF(packageSetting, userIds, null, 0, false);
        }
    }

    public void clearNoninstalledDataApps() {
        List<String> parsePackages = new PmConfigParser().parsePackages("/system/etc/system_to_data_app_list.xml");
        int[] userIds = this.mPm.mUserManager.getUserIds();
        synchronized (this.mPm.mLock) {
            boolean z = false;
            for (String str : parsePackages) {
                PackageSetting packageSetting = (PackageSetting) this.mPm.mSettings.mPackages.get(str);
                if (packageSetting != null && packageSetting.getPkg() == null && !packageSetting.isExternalStorage()) {
                    PackageManagerServiceUtils.logCriticalInfo(5, "Clear non-installed migration package " + str);
                    this.mRemovePackageHelper.removePackageDataLIF(packageSetting, userIds, null, 0, false);
                    z = true;
                }
            }
            if (z) {
                this.mPm.mSettings.writeLPr(this.mPm.snapshotComputer(), false);
            }
        }
    }

    public void updateDuplicatePreloadApps(final int i, final int i2, long j, final PackageParser2 packageParser2, ArrayMap arrayMap) {
        this.mPreloadDuplicateApps.addSystemPackagesTo(arrayMap);
        final ExecutorService makeExecutorService = ParallelPackageParser.makeExecutorService();
        this.mPreloadDuplicateApps.installDuplicatePackages(new BiConsumer() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                InstallPackageHelper.this.lambda$updateDuplicatePreloadApps$5(i, i2, packageParser2, makeExecutorService, (String) obj, (File) obj2);
            }
        });
        this.mPreloadDuplicateApps.clearPackages();
        makeExecutorService.shutdownNow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDuplicatePreloadApps$5(int i, int i2, PackageParser2 packageParser2, ExecutorService executorService, String str, File file) {
        PackageManagerServiceUtils.logCriticalInfo(5, "Update a duplicate package: " + str + ", apk: " + file);
        PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
        if (packageLPr == null) {
            return;
        }
        this.mPm.mSettings.disableSystemPackageLPw(str, true);
        this.mRemovePackageHelper.removePackage(packageLPr.getPkg(), true);
        installPackagesFromAppRootDir(file, null, i, i2, packageParser2, executorService);
    }

    public void installPackagesFromAppRootDir(File file, List list, int i, int i2, PackageParser2 packageParser2, ExecutorService executorService) {
        int i3;
        if (ArrayUtils.isEmpty(file.listFiles())) {
            Log.d("PackageManager", "No files in app dir " + file);
            return;
        }
        ParallelPackageParser parallelPackageParser = new ParallelPackageParser(packageParser2, executorService);
        parallelPackageParser.submit(file, i);
        ParallelPackageParser.ParseResult take = parallelPackageParser.take();
        Throwable th = take.throwable;
        if (th == null) {
            try {
                addForInitLI(take.parsedPackage, i, i2, null, null);
                i3 = 1;
            } catch (PackageManagerException e) {
                i3 = e.error;
                PackageManagerServiceUtils.logCriticalInfo(5, "Failed to scan " + take.scanFile + ": " + e.getMessage());
            }
        } else if (th instanceof PackageManagerException) {
            PackageManagerException packageManagerException = (PackageManagerException) th;
            i3 = packageManagerException.error;
            PackageManagerServiceUtils.logCriticalInfo(5, "Failed to parse " + take.scanFile + ": " + packageManagerException.getMessage());
        } else {
            throw new IllegalStateException("Unexpected exception occurred while parsing " + take.scanFile, th);
        }
        if ((65536 & i2) != 0 || i3 == 1) {
            return;
        }
        PackageManagerServiceUtils.logCriticalInfo(5, "Deleting invalid package at " + take.scanFile);
        this.mRemovePackageHelper.removeCodePath(take.scanFile);
    }

    public void cleanupDisabledPackageSettings(List list, int[] iArr, int i) {
        String str;
        for (int size = list.size() - 1; size >= 0; size--) {
            String str2 = (String) list.get(size);
            AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(str2);
            this.mPm.mSettings.removeDisabledSystemPackageLPw(str2);
            if (androidPackage == null) {
                str = "Updated system package " + str2 + " no longer exists; removing its data";
            } else {
                String str3 = "Updated system package " + str2 + " no longer exists; rescanning package on data";
                this.mRemovePackageHelper.removePackage(androidPackage, true);
                PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str2);
                if (packageLPr != null) {
                    packageLPr.getPkgState().setUpdatedSystemApp(false);
                }
                try {
                    File file = new File(androidPackage.getPath());
                    synchronized (this.mPm.mInstallLock) {
                        initPackageTracedLI(file, 0, i);
                    }
                } catch (PackageManagerException e) {
                    Slog.e("PackageManager", "Failed to parse updated, ex-system package: " + e.getMessage());
                }
                str = str3;
            }
            PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(str2);
            if (packageLPr2 != null && this.mPm.mPackages.get(str2) == null) {
                this.mRemovePackageHelper.removePackageData(packageLPr2, iArr, null, 0, false);
            }
            PackageManagerServiceUtils.logCriticalInfo(5, str);
        }
    }

    public List scanApexPackages(ApexInfo[] apexInfoArr, int i, int i2, PackageParser2 packageParser2, ExecutorService executorService) {
        int i3;
        int i4;
        if (apexInfoArr == null) {
            return Collections.EMPTY_LIST;
        }
        ParallelPackageParser parallelPackageParser = new ParallelPackageParser(packageParser2, executorService);
        final ArrayMap arrayMap = new ArrayMap();
        for (ApexInfo apexInfo : apexInfoArr) {
            File file = new File(apexInfo.modulePath);
            parallelPackageParser.submit(file, i);
            arrayMap.put(file, apexInfo);
        }
        ArrayList arrayList = new ArrayList(arrayMap.size());
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            arrayList.add(parallelPackageParser.take());
        }
        Collections.sort(arrayList, new Comparator() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$scanApexPackages$6;
                lambda$scanApexPackages$6 = InstallPackageHelper.lambda$scanApexPackages$6(arrayMap, (ParallelPackageParser.ParseResult) obj, (ParallelPackageParser.ParseResult) obj2);
                return lambda$scanApexPackages$6;
            }
        });
        ArrayList arrayList2 = new ArrayList(arrayMap.size());
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            ParallelPackageParser.ParseResult parseResult = (ParallelPackageParser.ParseResult) arrayList.get(i6);
            Throwable th = parseResult.throwable;
            ApexInfo apexInfo2 = (ApexInfo) arrayMap.get(parseResult.scanFile);
            int systemPackageScanFlags = i2 | 67108864 | this.mPm.getSystemPackageScanFlags(parseResult.scanFile);
            if (apexInfo2.isFactory) {
                i3 = systemPackageScanFlags;
                i4 = i;
            } else {
                i4 = i & (-17);
                i3 = systemPackageScanFlags | 4;
            }
            if (th == null) {
                try {
                    addForInitLI(parseResult.parsedPackage, i4, i3, null, new ApexManager.ActiveApexInfo(apexInfo2));
                    AndroidPackageInternal hideAsFinal = parseResult.parsedPackage.hideAsFinal();
                    if (apexInfo2.isFactory && !apexInfo2.isActive) {
                        disableSystemPackageLPw(hideAsFinal);
                    }
                    arrayList2.add(new ApexManager.ScanResult(apexInfo2, hideAsFinal, hideAsFinal.getPackageName()));
                } catch (PackageManagerException e) {
                    throw new IllegalStateException("Failed to scan: " + apexInfo2.modulePath, e);
                }
            } else if (th instanceof PackageManagerException) {
                Slog.e("PackageManager", "!@ Unexpected exception occurred while parsing " + apexInfo2.modulePath);
                String[] split = apexInfo2.modulePath.split("/|@");
                if (split.length > 4 && "data".equals(split[1]) && "decompressed".equals(split[3])) {
                    SystemProperties.set("sys.apexd.restore.module", split[4]);
                    Slog.i("PackageManager", "!@ reboot by ApexManager");
                    SystemProperties.set("sys.powerctl", "reboot,recoveryDecompressedApex");
                } else {
                    throw new IllegalStateException("Unable to parse: " + apexInfo2.modulePath, th);
                }
            } else {
                throw new IllegalStateException("Unexpected exception occurred while parsing " + apexInfo2.modulePath, th);
            }
        }
        return arrayList2;
    }

    public static /* synthetic */ int lambda$scanApexPackages$6(ArrayMap arrayMap, ParallelPackageParser.ParseResult parseResult, ParallelPackageParser.ParseResult parseResult2) {
        return Boolean.compare(((ApexInfo) arrayMap.get(parseResult2.scanFile)).isFactory, ((ApexInfo) arrayMap.get(parseResult.scanFile)).isFactory);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ad, code lost:
    
        if (r16.mPm.mCustomInjector.getSkippingApks().isSkippingApk(r4.getName() + ".apk") != false) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void installPackagesFromDir(File file, int i, int i2, PackageParser2 packageParser2, ExecutorService executorService, ApexManager.ActiveApexInfo activeApexInfo) {
        int i3;
        String str;
        ParallelPackageParser.ParseResult parseResult;
        int i4;
        String str2;
        int i5;
        ParsedPackage parsedPackage;
        File[] listFiles = file.listFiles();
        if (ArrayUtils.isEmpty(listFiles)) {
            Log.d("PackageManager", "No files in app dir " + file);
            return;
        }
        ParallelPackageParser parallelPackageParser = new ParallelPackageParser(packageParser2, executorService);
        int length = listFiles.length;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < length; i8++) {
            File file2 = listFiles[i8];
            if ((ApkLiteParseUtils.isApkFile(file2) || file2.isDirectory()) && !PackageInstallerService.isStageName(file2.getName())) {
                if ((i2 & 16777216) != 0) {
                    PackageCacher packageCacher = new PackageCacher(this.mPm.getCacheDir());
                    Log.w("PackageManager", "Dropping cache of " + file2.getAbsolutePath());
                    packageCacher.cleanCachedResult(file2);
                }
                if ((i2 & 65536) != 0 && file2.isDirectory()) {
                }
                parallelPackageParser.submit(file2, i);
                i7++;
            }
        }
        int i9 = i7;
        while (i9 > 0) {
            ParallelPackageParser.ParseResult take = parallelPackageParser.take();
            Throwable th = take.throwable;
            if (th == null) {
                try {
                    ParsedPackage parsedPackage2 = take.parsedPackage;
                    UserHandle userHandle = new UserHandle(i6);
                    i3 = 5;
                    str = ": ";
                    parseResult = take;
                    try {
                        addForInitLI(parsedPackage2, i, i2, userHandle, activeApexInfo);
                        str2 = null;
                        i4 = 1;
                    } catch (PackageManagerException e) {
                        e = e;
                        i4 = e.error;
                        str2 = "Failed to scan " + parseResult.scanFile + str + e.getMessage();
                        PackageManagerServiceUtils.logCriticalInfo(i3, str2);
                        if ((i2 & 8388608) == 0) {
                        }
                        if ((i2 & 65536) == 0) {
                            parsedPackage = parseResult.parsedPackage;
                            if (parsedPackage == null) {
                            }
                            PackageManagerServiceUtils.logCriticalInfo(i3, "Deleting invalid package at " + parseResult.scanFile);
                            this.mRemovePackageHelper.removeCodePath(parseResult.scanFile);
                        }
                        i9--;
                        i6 = 0;
                    }
                } catch (PackageManagerException e2) {
                    e = e2;
                    i3 = 5;
                    str = ": ";
                    parseResult = take;
                }
            } else {
                i3 = 5;
                parseResult = take;
                if (th instanceof PackageManagerException) {
                    PackageManagerException packageManagerException = (PackageManagerException) th;
                    i4 = packageManagerException.error;
                    str2 = "Failed to parse " + parseResult.scanFile + ": " + packageManagerException.getMessage();
                    PackageManagerServiceUtils.logCriticalInfo(5, str2);
                } else {
                    throw new IllegalStateException("Unexpected exception occurred while parsing " + parseResult.scanFile, th);
                }
            }
            if ((i2 & 8388608) == 0) {
                i5 = 1;
                if (i4 != 1) {
                    this.mApexManager.reportErrorWithApkInApex(file.getAbsolutePath(), str2);
                }
            } else {
                i5 = 1;
            }
            if ((i2 & 65536) == 0 && i4 != i5) {
                parsedPackage = parseResult.parsedPackage;
                if (parsedPackage == null && this.mPreloadDuplicateApps.hasDuplicatePackage(parsedPackage.getPackageName())) {
                    Log.d("PackageManager", "Duplicate package " + parseResult.parsedPackage.getPackageName() + ", but, It will be installed later");
                } else {
                    PackageManagerServiceUtils.logCriticalInfo(i3, "Deleting invalid package at " + parseResult.scanFile);
                    this.mRemovePackageHelper.removeCodePath(parseResult.scanFile);
                }
            }
            i9--;
            i6 = 0;
        }
    }

    public void checkExistingBetterPackages(ArrayMap arrayMap, List list, int i, int i2) {
        for (int i3 = 0; i3 < arrayMap.size(); i3++) {
            String str = (String) arrayMap.keyAt(i3);
            if (!this.mPm.mPackages.containsKey(str)) {
                File file = (File) arrayMap.valueAt(i3);
                PackageManagerServiceUtils.logCriticalInfo(5, "Expected better " + str + " but never showed up; reverting to system");
                Pair systemPackageRescanFlagsAndReparseFlags = this.mPm.getSystemPackageRescanFlagsAndReparseFlags(file, i, i2);
                int intValue = ((Integer) systemPackageRescanFlagsAndReparseFlags.first).intValue();
                int intValue2 = ((Integer) systemPackageRescanFlagsAndReparseFlags.second).intValue();
                if (intValue == 0) {
                    Slog.e("PackageManager", "Ignoring unexpected fallback path " + file);
                } else {
                    this.mPm.mSettings.enableSystemPackageLPw(str);
                    try {
                        synchronized (this.mPm.mInstallLock) {
                            if (initPackageTracedLI(file, intValue2, intValue).isStub()) {
                                list.add(str);
                            }
                        }
                    } catch (PackageManagerException e) {
                        Slog.e("PackageManager", "Failed to parse original system package: " + e.getMessage());
                    }
                }
            }
        }
    }

    public AndroidPackage initPackageTracedLI(File file, int i, int i2) {
        Trace.traceBegin(262144L, "scanPackage [" + file.toString() + "]");
        try {
            return initPackageLI(file, i, i2);
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public final AndroidPackage initPackageLI(File file, int i, int i2) {
        Trace.traceBegin(262144L, "parsePackage");
        try {
            PackageParser2 scanningPackageParser = this.mPm.mInjector.getScanningPackageParser();
            try {
                ParsedPackage parsePackage = scanningPackageParser.parsePackage(file, i, false);
                scanningPackageParser.close();
                Trace.traceEnd(262144L);
                return addForInitLI(parsePackage, i, i2, new UserHandle(0), null);
            } finally {
            }
        } catch (Throwable th) {
            Trace.traceEnd(262144L);
            throw th;
        }
    }

    public final AndroidPackage addForInitLI(ParsedPackage parsedPackage, int i, int i2, UserHandle userHandle, ApexManager.ActiveApexInfo activeApexInfo) {
        PackageSetting disabledSystemPkgLPr;
        String apexModuleName;
        boolean z;
        PackageSetting packageSetting;
        synchronized (this.mPm.mLock) {
            if (activeApexInfo == null) {
                if (parsedPackage.isStaticSharedLibrary()) {
                    PackageManagerService.renameStaticSharedLibraryPackage(parsedPackage);
                }
            }
            disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(parsedPackage.getPackageName());
            if (activeApexInfo != null && disabledSystemPkgLPr != null) {
                disabledSystemPkgLPr.setApexModuleName(activeApexInfo.apexModuleName);
            }
        }
        Pair scanSystemPackageLI = scanSystemPackageLI(parsedPackage, i, i2, userHandle);
        ScanResult scanResult = (ScanResult) scanSystemPackageLI.first;
        boolean booleanValue = ((Boolean) scanSystemPackageLI.second).booleanValue();
        InstallRequest installRequest = new InstallRequest(parsedPackage, i, i2, userHandle, scanResult);
        synchronized (this.mPm.mLock) {
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
            apexModuleName = packageLPr != null ? packageLPr.getApexModuleName() : null;
        }
        if (activeApexInfo != null) {
            installRequest.setApexModuleName(activeApexInfo.apexModuleName);
        } else if (disabledSystemPkgLPr != null) {
            installRequest.setApexModuleName(disabledSystemPkgLPr.getApexModuleName());
        } else if (apexModuleName != null) {
            installRequest.setApexModuleName(apexModuleName);
        }
        synchronized (this.mPm.mLock) {
            boolean z2 = false;
            try {
                String packageName = scanResult.mPkgSetting.getPackageName();
                List singletonList = Collections.singletonList(installRequest);
                PackageManagerService packageManagerService = this.mPm;
                List reconcilePackages = ReconcilePackageUtils.reconcilePackages(singletonList, packageManagerService.mPackages, Collections.singletonMap(packageName, packageManagerService.getSettingsVersionForPackage(parsedPackage)), this.mSharedLibraries, this.mPm.mSettings.getKeySetManagerService(), this.mPm.mSettings);
                if ((i2 & 67108864) == 0) {
                    z = optimisticallyRegisterAppId(installRequest);
                } else {
                    installRequest.setScannedPackageSettingAppId(-1);
                    z = false;
                }
                try {
                    commitReconciledScanResultLocked((ReconciledPackage) reconcilePackages.get(0), this.mPm.mUserManager.getUserIds());
                } catch (PackageManagerException e) {
                    e = e;
                    z2 = z;
                    if (z2) {
                        cleanUpAppIdCreation(installRequest);
                    }
                    throw e;
                }
            } catch (PackageManagerException e2) {
                e = e2;
            }
        }
        if (booleanValue) {
            synchronized (this.mPm.mLock) {
                this.mPm.mSettings.disableSystemPackageLPw(parsedPackage.getPackageName(), true);
            }
        }
        if (this.mIncrementalManager != null && IncrementalManager.isIncrementalPath(parsedPackage.getPath()) && (packageSetting = scanResult.mPkgSetting) != null && packageSetting.isLoading()) {
            this.mIncrementalManager.registerLoadingProgressCallback(parsedPackage.getPath(), new IncrementalProgressListener(parsedPackage.getPackageName(), this.mPm));
        }
        return scanResult.mPkgSetting.getPkg();
    }

    public final boolean optimisticallyRegisterAppId(InstallRequest installRequest) {
        boolean registerAppIdLPw;
        if (installRequest.isExistingSettingCopied() && !installRequest.needsNewAppId()) {
            return false;
        }
        synchronized (this.mPm.mLock) {
            registerAppIdLPw = this.mPm.mSettings.registerAppIdLPw(installRequest.getScannedPackageSetting(), installRequest.needsNewAppId());
        }
        return registerAppIdLPw;
    }

    public final void cleanUpAppIdCreation(InstallRequest installRequest) {
        if (installRequest.getScannedPackageSetting() == null || installRequest.getScannedPackageSetting().getAppId() <= 0) {
            return;
        }
        synchronized (this.mPm.mLock) {
            this.mPm.mSettings.removeAppIdLPw(installRequest.getScannedPackageSetting().getAppId());
        }
    }

    public final ScanResult scanPackageTracedLI(ParsedPackage parsedPackage, int i, int i2, long j, UserHandle userHandle, String str) {
        Trace.traceBegin(262144L, "scanPackage");
        try {
            return scanPackageNewLI(parsedPackage, i, i2, j, userHandle, str);
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x009a A[Catch: all -> 0x00d6, TryCatch #0 {, blocks: (B:4:0x0008, B:6:0x0024, B:7:0x0027, B:9:0x0045, B:10:0x0064, B:12:0x0073, B:18:0x0084, B:20:0x008a, B:22:0x009a, B:23:0x00a5, B:39:0x007c), top: B:3:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ScanRequest prepareInitialScanRequest(ParsedPackage parsedPackage, int i, int i2, UserHandle userHandle, String str) {
        AndroidPackage platformPackage;
        String realPackageName;
        PackageSetting originalPackageLocked;
        PackageSetting packageLPr;
        PackageSetting disabledSystemPkgLPr;
        boolean isLeavingSharedUser;
        SharedUserSetting sharedUserLPw;
        SharedUserSetting sharedUserSettingLPr;
        synchronized (this.mPm.mLock) {
            platformPackage = this.mPm.getPlatformPackage();
            boolean isSystem = AndroidPackageUtils.isSystem(parsedPackage);
            String renamedPackageLPr = this.mPm.mSettings.getRenamedPackageLPr(AndroidPackageUtils.getRealPackageOrNull(parsedPackage, isSystem));
            realPackageName = ScanPackageUtils.getRealPackageName(parsedPackage, renamedPackageLPr, isSystem);
            if (realPackageName != null) {
                ScanPackageUtils.ensurePackageRenamed(parsedPackage, renamedPackageLPr);
            }
            originalPackageLocked = getOriginalPackageLocked(parsedPackage, renamedPackageLPr);
            packageLPr = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
            if (this.mPm.mTransferredPackages.contains(parsedPackage.getPackageName())) {
                Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " was transferred to another, but its .apk remains");
            }
            disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(parsedPackage.getPackageName());
            if (packageLPr != null && packageLPr.hasSharedUser()) {
                isLeavingSharedUser = false;
                sharedUserLPw = (!isLeavingSharedUser || parsedPackage.getSharedUserId() == null) ? null : this.mPm.mSettings.getSharedUserLPw(parsedPackage.getSharedUserId(), 0, 0, true);
                sharedUserSettingLPr = packageLPr == null ? this.mPm.mSettings.getSharedUserSettingLPr(packageLPr) : null;
            }
            isLeavingSharedUser = parsedPackage.isLeavingSharedUser();
            if (isLeavingSharedUser) {
            }
            if (packageLPr == null) {
            }
        }
        return new ScanRequest(parsedPackage, sharedUserSettingLPr, packageLPr == null ? null : packageLPr.getPkg(), packageLPr, sharedUserLPw, disabledSystemPkgLPr, originalPackageLocked, realPackageName, i, i2, platformPackage != null && platformPackage.getPackageName().equals(parsedPackage.getPackageName()), userHandle, str);
    }

    public final ScanResult scanPackageNewLI(ParsedPackage parsedPackage, int i, int i2, long j, UserHandle userHandle, String str) {
        boolean z;
        ScanResult scanPackageOnlyLI;
        ScanRequest prepareInitialScanRequest = prepareInitialScanRequest(parsedPackage, i, i2, userHandle, str);
        PackageSetting packageSetting = prepareInitialScanRequest.mPkgSetting;
        PackageSetting packageSetting2 = prepareInitialScanRequest.mDisabledPkgSetting;
        if (packageSetting != null) {
            z = packageSetting.isUpdatedSystemApp();
        } else {
            z = packageSetting2 != null;
        }
        boolean z2 = z;
        int adjustScanFlags = adjustScanFlags(i2, packageSetting, packageSetting2, userHandle, parsedPackage);
        ScanPackageUtils.applyPolicy(parsedPackage, adjustScanFlags, this.mPm.getPlatformPackage(), z2);
        synchronized (this.mPm.mLock) {
            assertPackageIsValid(parsedPackage, i, adjustScanFlags);
            ScanRequest scanRequest = new ScanRequest(parsedPackage, prepareInitialScanRequest.mOldSharedUserSetting, prepareInitialScanRequest.mOldPkg, packageSetting, prepareInitialScanRequest.mSharedUserSetting, packageSetting2, prepareInitialScanRequest.mOriginalPkgSetting, prepareInitialScanRequest.mRealPkgName, i, i2, prepareInitialScanRequest.mIsPlatformPackage, userHandle, str);
            PackageManagerService packageManagerService = this.mPm;
            scanPackageOnlyLI = ScanPackageUtils.scanPackageOnlyLI(scanRequest, packageManagerService.mInjector, packageManagerService.mFactoryTest, j);
        }
        return scanPackageOnlyLI;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x038a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0213  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair scanSystemPackageLI(ParsedPackage parsedPackage, int i, int i2, UserHandle userHandle) {
        PackageManagerTracedLock packageManagerTracedLock;
        PackageSetting packageSetting;
        ScanRequest scanRequest;
        int i3;
        int i4;
        int i5;
        int i6;
        PackageSetting packageSetting2;
        boolean z;
        int i7;
        String packageName;
        PackageSetting packageSetting3;
        boolean z2 = (i & 16) != 0;
        ScanRequest prepareInitialScanRequest = prepareInitialScanRequest(parsedPackage, i, i2, userHandle, null);
        PackageSetting packageSetting4 = prepareInitialScanRequest.mPkgSetting;
        PackageSetting packageSetting5 = prepareInitialScanRequest.mOriginalPkgSetting;
        PackageSetting packageSetting6 = packageSetting5 == null ? packageSetting4 : packageSetting5;
        boolean z3 = packageSetting6 != null;
        String packageName2 = z3 ? packageSetting6.getPackageName() : parsedPackage.getPackageName();
        PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        synchronized (packageManagerTracedLock2) {
            try {
                try {
                    boolean isDeviceUpgrading = this.mPm.isDeviceUpgrading();
                    if (z2 && !z3 && this.mPm.mSettings.getDisabledSystemPkgLPr(packageName2) != null) {
                        Slog.w("PackageManager", "Inconsistent package setting of updated system app for " + packageName2 + ". To recover it, enable the system app and install it as non-updated system app.");
                        this.mPm.mSettings.removeDisabledSystemPackageLPw(packageName2);
                    }
                    PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageName2);
                    boolean z4 = disabledSystemPkgLPr != null;
                    if (z2 && z4) {
                        packageManagerTracedLock = packageManagerTracedLock2;
                        packageSetting = packageSetting6;
                        scanRequest = prepareInitialScanRequest;
                        ScanRequest scanRequest2 = new ScanRequest(parsedPackage, this.mPm.mSettings.getSharedUserSettingLPr(disabledSystemPkgLPr), null, disabledSystemPkgLPr, prepareInitialScanRequest.mSharedUserSetting, null, null, null, i, i2, prepareInitialScanRequest.mIsPlatformPackage, userHandle, null);
                        i4 = i2;
                        i3 = 1;
                        ScanPackageUtils.applyPolicy(parsedPackage, i4, this.mPm.getPlatformPackage(), true);
                        PackageManagerService packageManagerService = this.mPm;
                        ScanResult scanPackageOnlyLI = ScanPackageUtils.scanPackageOnlyLI(scanRequest2, packageManagerService.mInjector, packageManagerService.mFactoryTest, -1L);
                        if (scanPackageOnlyLI.mExistingSettingCopied && (packageSetting3 = scanPackageOnlyLI.mRequest.mPkgSetting) != null) {
                            packageSetting3.updateFrom(scanPackageOnlyLI.mPkgSetting);
                        }
                    } else {
                        packageManagerTracedLock = packageManagerTracedLock2;
                        packageSetting = packageSetting6;
                        scanRequest = prepareInitialScanRequest;
                        i3 = 1;
                        i4 = i2;
                    }
                    int i8 = (!z3 || packageSetting.getPathString().equals(parsedPackage.getPath())) ? 0 : i3;
                    int i9 = (!z3 || parsedPackage.getLongVersionCode() <= packageSetting.getVersionCode()) ? 0 : i3;
                    if (z3) {
                        ScanRequest scanRequest3 = scanRequest;
                        if (scanRequest3.mOldSharedUserSetting != scanRequest3.mSharedUserSetting) {
                            i5 = i3;
                            i6 = (z2 || !z4 || i8 == 0 || (i9 == 0 && i5 == 0)) ? 0 : i3;
                            if (i6 != 0) {
                                synchronized (this.mPm.mLock) {
                                    this.mPm.mPackages.remove(packageSetting.getPackageName());
                                }
                                PackageManagerServiceUtils.logCriticalInfo(5, "System package updated; name: " + packageSetting.getPackageName() + "; " + packageSetting.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting.getPathString() + " --> " + parsedPackage.getPath());
                                this.mRemovePackageHelper.cleanUpResources(new File(packageSetting.getPathString()), InstructionSets.getAppDexInstructionSets(packageSetting.getPrimaryCpuAbiLegacy(), packageSetting.getSecondaryCpuAbiLegacy()));
                                synchronized (this.mPm.mLock) {
                                    this.mPm.mSettings.enableSystemPackageLPw(packageSetting.getPackageName());
                                }
                            }
                            if (!z2 && z4 && i6 == 0) {
                                parsedPackage.hideAsFinal();
                                StringBuilder sb = new StringBuilder();
                                sb.append("Package ");
                                sb.append(parsedPackage.getPackageName());
                                sb.append(" at ");
                                sb.append(parsedPackage.getPath());
                                sb.append(" ignored: updated version ");
                                sb.append(z3 ? String.valueOf(packageSetting.getVersionCode()) : "unknown");
                                sb.append(" better than this ");
                                sb.append(parsedPackage.getLongVersionCode());
                                throw PackageManagerException.ofInternalError(sb.toString(), -12);
                            }
                            boolean isApkVerificationForced = !z2 ? isDeviceUpgrading : PackageManagerServiceUtils.isApkVerificationForced(packageSetting);
                            packageSetting2 = packageSetting;
                            ScanPackageUtils.collectCertificatesLI(packageSetting2, parsedPackage, this.mPm.getSettingsVersionForPackage(parsedPackage), isApkVerificationForced, (!z2 || (isApkVerificationForced && canSkipForcedPackageVerification(parsedPackage))) ? i3 : 0, this.mPm.isPreNMR1Upgrade());
                            maybeClearProfilesForUpgradesLI(packageSetting2, parsedPackage);
                            if (z2 && !z4 && z3 && !packageSetting2.isSystem()) {
                                if (parsedPackage.getSigningDetails().checkCapability(packageSetting2.getSigningDetails(), i3) && !packageSetting2.getSigningDetails().checkCapability(parsedPackage.getSigningDetails(), 8)) {
                                    PackageManagerServiceUtils.logCriticalInfo(5, "System package signature mismatch; name: " + packageSetting2.getPackageName());
                                    PackageFreezer freezePackage = this.mPm.freezePackage(parsedPackage.getPackageName(), -1, "scanPackageInternalLI", 13);
                                    try {
                                        new DeletePackageHelper(this.mPm).deletePackageLIF(parsedPackage.getPackageName(), null, true, this.mPm.mUserManager.getUserIds(), 0, null, false);
                                        if (freezePackage != null) {
                                            freezePackage.close();
                                        }
                                    } finally {
                                    }
                                } else if (i9 == 0 || i5 != 0) {
                                    PackageManagerServiceUtils.logCriticalInfo(5, "System package enabled; name: " + packageSetting2.getPackageName() + "; " + packageSetting2.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting2.getPathString() + " --> " + parsedPackage.getPath());
                                    this.mRemovePackageHelper.cleanUpResources(new File(packageSetting2.getPathString()), InstructionSets.getAppDexInstructionSets(packageSetting2.getPrimaryCpuAbiLegacy(), packageSetting2.getSecondaryCpuAbiLegacy()));
                                } else {
                                    PackageManagerServiceUtils.logCriticalInfo(4, "System package disabled; name: " + packageSetting2.getPackageName() + "; old: " + packageSetting2.getPathString() + " @ " + packageSetting2.getVersionCode() + "; new: " + parsedPackage.getPath() + " @ " + parsedPackage.getPath());
                                    z = i3;
                                    int i10 = (67108864 & i4) != 0 ? i3 : 0;
                                    if (this.mPm.mShouldStopSystemPackagesByDefault && z2 && !z3 && i10 == 0 && !parsedPackage.isOverlayIsStatic()) {
                                        packageName = parsedPackage.getPackageName();
                                        if (!"android".contentEquals(packageName) && !this.mPm.mInitialNonStoppedSystemPackages.contains(packageName) && hasLauncherEntry(parsedPackage)) {
                                            i7 = 134217728 | i4;
                                            return new Pair(scanPackageNewLI(parsedPackage, i, i7 | 2, 0L, userHandle, null), Boolean.valueOf(z));
                                        }
                                    }
                                    i7 = i4;
                                    return new Pair(scanPackageNewLI(parsedPackage, i, i7 | 2, 0L, userHandle, null), Boolean.valueOf(z));
                                }
                            }
                            z = 0;
                            if ((67108864 & i4) != 0) {
                            }
                            if (this.mPm.mShouldStopSystemPackagesByDefault) {
                                packageName = parsedPackage.getPackageName();
                                if (!"android".contentEquals(packageName)) {
                                    i7 = 134217728 | i4;
                                    return new Pair(scanPackageNewLI(parsedPackage, i, i7 | 2, 0L, userHandle, null), Boolean.valueOf(z));
                                }
                            }
                            i7 = i4;
                            return new Pair(scanPackageNewLI(parsedPackage, i, i7 | 2, 0L, userHandle, null), Boolean.valueOf(z));
                        }
                    }
                    i5 = 0;
                    if (z2) {
                    }
                    if (i6 != 0) {
                    }
                    if (!z2) {
                    }
                    if (!z2) {
                    }
                    packageSetting2 = packageSetting;
                    ScanPackageUtils.collectCertificatesLI(packageSetting2, parsedPackage, this.mPm.getSettingsVersionForPackage(parsedPackage), isApkVerificationForced, (!z2 || (isApkVerificationForced && canSkipForcedPackageVerification(parsedPackage))) ? i3 : 0, this.mPm.isPreNMR1Upgrade());
                    maybeClearProfilesForUpgradesLI(packageSetting2, parsedPackage);
                    if (z2) {
                        if (parsedPackage.getSigningDetails().checkCapability(packageSetting2.getSigningDetails(), i3)) {
                        }
                        if (i9 == 0) {
                        }
                        PackageManagerServiceUtils.logCriticalInfo(5, "System package enabled; name: " + packageSetting2.getPackageName() + "; " + packageSetting2.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting2.getPathString() + " --> " + parsedPackage.getPath());
                        this.mRemovePackageHelper.cleanUpResources(new File(packageSetting2.getPathString()), InstructionSets.getAppDexInstructionSets(packageSetting2.getPrimaryCpuAbiLegacy(), packageSetting2.getSecondaryCpuAbiLegacy()));
                    }
                    z = 0;
                    if ((67108864 & i4) != 0) {
                    }
                    if (this.mPm.mShouldStopSystemPackagesByDefault) {
                    }
                    i7 = i4;
                    return new Pair(scanPackageNewLI(parsedPackage, i, i7 | 2, 0L, userHandle, null), Boolean.valueOf(z));
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        throw th;
    }

    public static boolean hasLauncherEntry(ParsedPackage parsedPackage) {
        HashSet hashSet = new HashSet();
        hashSet.add("android.intent.category.LAUNCHER");
        List activities = parsedPackage.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            ParsedActivity parsedActivity = (ParsedActivity) activities.get(i);
            if (parsedActivity.isEnabled() && parsedActivity.isExported()) {
                List intents = parsedActivity.getIntents();
                for (int i2 = 0; i2 < intents.size(); i2++) {
                    IntentFilter intentFilter = ((ParsedIntentInfo) intents.get(i2)).getIntentFilter();
                    if (intentFilter != null && intentFilter.matchCategories(hashSet) == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean canSkipForcedPackageVerification(AndroidPackage androidPackage) {
        if (!VerityUtils.hasFsverity(androidPackage.getBaseApkPath())) {
            return false;
        }
        String[] splitCodePaths = androidPackage.getSplitCodePaths();
        if (ArrayUtils.isEmpty(splitCodePaths)) {
            return true;
        }
        for (String str : splitCodePaths) {
            if (!VerityUtils.hasFsverity(str)) {
                return false;
            }
        }
        return true;
    }

    public final void maybeClearProfilesForUpgradesLI(PackageSetting packageSetting, AndroidPackage androidPackage) {
        if (packageSetting == null || !this.mPm.isDeviceUpgrading() || packageSetting.getVersionCode() == androidPackage.getLongVersionCode()) {
            return;
        }
        this.mAppDataHelper.clearAppProfilesLIF(androidPackage);
    }

    public final PackageSetting getOriginalPackageLocked(AndroidPackage androidPackage, String str) {
        if (ScanPackageUtils.isPackageRenamed(androidPackage, str)) {
            return null;
        }
        for (int size = ArrayUtils.size(androidPackage.getOriginalPackages()) - 1; size >= 0; size--) {
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr((String) androidPackage.getOriginalPackages().get(size));
            if (packageLPr != null && verifyPackageUpdateLPr(packageLPr, androidPackage)) {
                if (this.mPm.mSettings.getSharedUserSettingLPr(packageLPr) != null) {
                    String str2 = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr).name;
                    if (!str2.equals(androidPackage.getSharedUserId())) {
                        Slog.w("PackageManager", "Unable to migrate data from " + packageLPr.getPackageName() + " to " + androidPackage.getPackageName() + ": old shared user settings name " + str2 + " differs from " + androidPackage.getSharedUserId());
                    }
                }
                return packageLPr;
            }
        }
        return null;
    }

    public final boolean verifyPackageUpdateLPr(PackageSetting packageSetting, AndroidPackage androidPackage) {
        if ((packageSetting.getFlags() & 1) == 0) {
            Slog.w("PackageManager", "Unable to update from " + packageSetting.getPackageName() + " to " + androidPackage.getPackageName() + ": old package not in system partition");
            return false;
        }
        if (this.mPm.mPackages.get(packageSetting.getPackageName()) == null) {
            return true;
        }
        Slog.w("PackageManager", "Unable to update from " + packageSetting.getPackageName() + " to " + androidPackage.getPackageName() + ": old package still exists");
        return false;
    }

    public final void assertPackageIsValid(AndroidPackage androidPackage, int i, int i2) {
        if ((i & 64) != 0) {
            ScanPackageUtils.assertCodePolicy(androidPackage);
        }
        if (androidPackage.getPath() == null) {
            throw new PackageManagerException(-2, "Code and resource paths haven't been set correctly");
        }
        boolean z = true;
        boolean z2 = (i2 & 16) == 0;
        boolean z3 = (i2 & IInstalld.FLAG_USE_QUOTA) != 0;
        boolean z4 = (67108864 & i2) != 0;
        if ((z2 || z3) && this.mPm.snapshotComputer().isApexPackage(androidPackage.getPackageName()) && !z4) {
            throw new PackageManagerException(-5, androidPackage.getPackageName() + " is an APEX package and can't be installed as an APK.");
        }
        this.mPm.mSettings.getKeySetManagerService().assertScannedPackageValid(androidPackage);
        synchronized (this.mPm.mLock) {
            if (androidPackage.getPackageName().equals("android") && this.mPm.getCoreAndroidApplication() != null) {
                Slog.w("PackageManager", "*************************************************");
                Slog.w("PackageManager", "Core android package being redefined.  Skipping.");
                Slog.w("PackageManager", " codePath=" + androidPackage.getPath());
                Slog.w("PackageManager", "*************************************************");
                throw new PackageManagerException(-5, "Core android package being redefined.  Skipping.");
            }
            int i3 = i2 & 4;
            if (i3 == 0 && this.mPm.mPackages.containsKey(androidPackage.getPackageName())) {
                PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                if (this.mPm.mSettings.getDisabledSystemPkgLPr(androidPackage.getPackageName()) == null) {
                    z = false;
                }
                if (packageLPr != null && this.mPreloadDuplicateApps.isDuplicatePackage(androidPackage, packageLPr, z, i)) {
                    this.mPreloadDuplicateApps.addDuplicatePackage(androidPackage);
                    this.mPreloadDuplicateApps.addSystemPackage(packageLPr.getPkg());
                }
                throw new PackageManagerException(-5, "Application package " + androidPackage.getPackageName() + " already installed.  Skipping duplicate.");
            }
            if (androidPackage.isStaticSharedLibrary()) {
                if (i3 == 0 && this.mPm.mPackages.containsKey(androidPackage.getManifestPackageName())) {
                    throw PackageManagerException.ofInternalError("Duplicate static shared lib provider package", -13);
                }
                ScanPackageUtils.assertStaticSharedLibraryIsValid(androidPackage, i2);
                assertStaticSharedLibraryVersionCodeIsValid(androidPackage);
            }
            if ((i2 & 128) != 0) {
                if (this.mPm.isExpectingBetter(androidPackage.getPackageName())) {
                    Slog.w("PackageManager", "Relax SCAN_REQUIRE_KNOWN requirement for package " + androidPackage.getPackageName());
                } else {
                    PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                    if (packageLPr2 != null) {
                        if (!androidPackage.getPath().equals(packageLPr2.getPathString())) {
                            throw new PackageManagerException(-23, "Application package " + androidPackage.getPackageName() + " found at " + androidPackage.getPath() + " but expected at " + packageLPr2.getPathString() + "; ignoring.");
                        }
                    } else if (this.mPm.isFirstBoot()) {
                        Log.d("PackageManager", "Application package " + androidPackage.getPackageName() + " not found at first boot, but allow data preload apps to be installed");
                    } else {
                        throw new PackageManagerException(-19, "Application package " + androidPackage.getPackageName() + " not found; ignoring.");
                    }
                }
            }
            if (i3 != 0) {
                this.mPm.mComponentResolver.assertProvidersNotDefined(androidPackage);
            }
            ScanPackageUtils.assertProcessesAreValid(androidPackage);
            assertPackageWithSharedUserIdIsPrivileged(androidPackage);
            if (androidPackage.getOverlayTarget() != null) {
                assertOverlayIsValid(androidPackage, i, i2);
            }
            ScanPackageUtils.assertMinSignatureSchemeIsValid(androidPackage, i);
        }
    }

    public final void assertStaticSharedLibraryVersionCodeIsValid(AndroidPackage androidPackage) {
        WatchedLongSparseArray sharedLibraryInfos = this.mSharedLibraries.getSharedLibraryInfos(androidPackage.getStaticSharedLibraryName());
        long j = Long.MIN_VALUE;
        long j2 = Long.MAX_VALUE;
        if (sharedLibraryInfos != null) {
            int size = sharedLibraryInfos.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) sharedLibraryInfos.valueAt(i);
                long longVersionCode = sharedLibraryInfo.getDeclaringPackage().getLongVersionCode();
                if (sharedLibraryInfo.getLongVersion() >= androidPackage.getStaticSharedLibraryVersion()) {
                    if (sharedLibraryInfo.getLongVersion() <= androidPackage.getStaticSharedLibraryVersion()) {
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
            throw PackageManagerException.ofInternalError("Static shared lib version codes must be ordered as lib versions", -14);
        }
    }

    public final void assertOverlayIsValid(AndroidPackage androidPackage, int i, int i2) {
        PackageSetting packageLPr;
        PackageSetting packageLPr2;
        PackageSetting packageLPr3;
        PackageSetting packageLPr4;
        if ((65536 & i2) != 0) {
            if ((i & 16) == 0) {
                if (this.mPm.isOverlayMutable(androidPackage.getPackageName())) {
                    return;
                }
                throw PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " is static and cannot be upgraded.", -15);
            }
            if ((524288 & i2) != 0) {
                if (androidPackage.getTargetSdkVersion() < ScanPackageUtils.getVendorPartitionVersion()) {
                    Slog.w("PackageManager", "System overlay " + androidPackage.getPackageName() + " targets an SDK below the required SDK level of vendor overlays (" + ScanPackageUtils.getVendorPartitionVersion() + "). This will become an install error in a future release");
                    return;
                }
                return;
            }
            int targetSdkVersion = androidPackage.getTargetSdkVersion();
            int i3 = Build.VERSION.SDK_INT;
            if (targetSdkVersion < i3) {
                Slog.w("PackageManager", "System overlay " + androidPackage.getPackageName() + " targets an SDK below the required SDK level of system overlays (" + i3 + "). This will become an install error in a future release");
                return;
            }
            return;
        }
        boolean z = true;
        if (androidPackage.getPackageName().startsWith("com.samsung.themedesigner")) {
            synchronized (this.mPm.mLock) {
                packageLPr4 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            }
            z = true ^ (packageLPr4 != null && SemSamsungThemeUtils.isValidThemeParkOverlay(androidPackage, packageLPr4.getLastUpdateTime()));
            Slog.i("PackageManager", "assertOverlayIsValid overlayPkgSetting " + packageLPr4 + " " + z);
        }
        if (androidPackage.getTargetSdkVersion() < 29) {
            synchronized (this.mPm.mLock) {
                packageLPr3 = this.mPm.mSettings.getPackageLPr("android");
            }
            if (z && !PackageManagerServiceUtils.comparePackageSignatures(packageLPr3, androidPackage.getSigningDetails().getSignatures())) {
                throw PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " must target Q or later, or be signed with the platform certificate", -16);
            }
        }
        if (z && androidPackage.getOverlayTargetOverlayableName() == null) {
            synchronized (this.mPm.mLock) {
                packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getOverlayTarget());
            }
            if (packageLPr == null || PackageManagerServiceUtils.comparePackageSignatures(packageLPr, androidPackage.getSigningDetails().getSignatures())) {
                return;
            }
            PackageManagerService packageManagerService = this.mPm;
            if (packageManagerService.mOverlayConfigSignaturePackage == null) {
                throw PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " and target " + androidPackage.getOverlayTarget() + " signed with different certificates, and the overlay lacks <overlay android:targetName>", -17);
            }
            synchronized (packageManagerService.mLock) {
                PackageManagerService packageManagerService2 = this.mPm;
                packageLPr2 = packageManagerService2.mSettings.getPackageLPr(packageManagerService2.mOverlayConfigSignaturePackage);
            }
            if (PackageManagerServiceUtils.comparePackageSignatures(packageLPr2, androidPackage.getSigningDetails().getSignatures())) {
                return;
            }
            throw PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " signed with a different certificate than both the reference package and target " + androidPackage.getOverlayTarget() + ", and the overlay lacks <overlay android:targetName>", -18);
        }
    }

    public final void assertPackageWithSharedUserIdIsPrivileged(AndroidPackage androidPackage) {
        PackageSetting packageLPr;
        if (AndroidPackageUtils.isPrivileged(androidPackage) || androidPackage.getSharedUserId() == null || androidPackage.isLeavingSharedUser()) {
            return;
        }
        SharedUserSetting sharedUserSetting = null;
        try {
            synchronized (this.mPm.mLock) {
                sharedUserSetting = this.mPm.mSettings.getSharedUserLPw(androidPackage.getSharedUserId(), 0, 0, false);
            }
        } catch (PackageManagerException unused) {
        }
        if (sharedUserSetting == null || !sharedUserSetting.isPrivileged()) {
            return;
        }
        synchronized (this.mPm.mLock) {
            packageLPr = this.mPm.mSettings.getPackageLPr("android");
        }
        if (PackageManagerServiceUtils.comparePackageSignatures(packageLPr, androidPackage.getSigningDetails().getSignatures())) {
            return;
        }
        throw PackageManagerException.ofInternalError("Apps that share a user with a privileged app must themselves be marked as privileged. " + androidPackage.getPackageName() + " shares privileged user " + androidPackage.getSharedUserId() + ".", -19);
    }

    public final int adjustScanFlags(int i, PackageSetting packageSetting, PackageSetting packageSetting2, UserHandle userHandle, AndroidPackage androidPackage) {
        SharedUserSetting sharedUserSetting;
        int adjustScanFlagsWithPackageSetting = ScanPackageUtils.adjustScanFlagsWithPackageSetting(i, packageSetting, packageSetting2, userHandle);
        boolean z = (524288 & adjustScanFlagsWithPackageSetting) != 0 && ScanPackageUtils.getVendorPartitionVersion() < 28;
        if ((adjustScanFlagsWithPackageSetting & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) == 0 && !AndroidPackageUtils.isPrivileged(androidPackage) && androidPackage.getSharedUserId() != null && !z && !androidPackage.isLeavingSharedUser()) {
            synchronized (this.mPm.mLock) {
                try {
                    sharedUserSetting = this.mPm.mSettings.getSharedUserLPw(androidPackage.getSharedUserId(), 0, 0, false);
                } catch (PackageManagerException unused) {
                    sharedUserSetting = null;
                }
                if (sharedUserSetting != null && sharedUserSetting.isPrivileged() && PackageManagerServiceUtils.compareSignatures(this.mPm.mSettings.getPackageLPr("android").getSigningDetails().getSignatures(), androidPackage.getSigningDetails().getSignatures()) != 0) {
                    adjustScanFlagsWithPackageSetting |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                }
            }
        }
        return adjustScanFlagsWithPackageSetting;
    }

    public final boolean isAdminApplication(AndroidPackage androidPackage) {
        int size = androidPackage.getReceivers().size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = (ParsedActivity) androidPackage.getReceivers().get(i);
            if (parsedActivity != null && parsedActivity.getPermission() != null && parsedActivity.getName() != null && parsedActivity.getPermission().equals("android.permission.BIND_DEVICE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public final IPackageManager getIPackageManager() {
        return ActivityThread.getPackageManager();
    }
}
