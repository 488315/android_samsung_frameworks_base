package com.android.server.pm;

import android.apex.ApexInfo;
import android.content.pm.DataLoaderParams;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInstaller;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.PackageLite;
import android.content.pm.verify.domain.DomainSet;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.util.Preconditions;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.install.ThermalInstallThrottlingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class InstallingSession {
    public final List mAllowlistedRestrictedPermissions;
    public final boolean mApplicationEnabledSettingPersistent;
    public final int mAutoRevokePermissionsMode;
    public final int mDataLoaderType;
    public final int mDevelopmentInstallFlags;
    public final String mDexoptCompilerFilter;
    public final boolean mForceQueryableOverride;
    public final boolean mHasAppMetadataFile;
    public int mInstallFlags;
    public final int mInstallReason;
    public final int mInstallScenario;
    public final InstallSource mInstallSource;
    public final boolean mIsInherit;
    public boolean mIsMoveRequest;
    public final MoveInfo mMoveInfo;
    public final IPackageInstallObserver2 mObserver;
    public final OriginInfo mOriginInfo;
    public final String mPackageAbiOverride;
    public final PackageLite mPackageLite;
    public final int mPackageSource;
    public MultiPackageInstallingSession mParentInstallingSession;
    public final ArrayMap mPermissionStates;
    public final PackageManagerService mPm;
    public final DomainSet mPreVerifiedDomains;
    public int mPreferredInstallLocation;
    public final int mRequireUserAction;
    public final long mRequiredInstalledVersionCode;
    public int mRet;
    public final int mSessionId;
    public final SigningDetails mSigningDetails;
    public int mTraceCookie;
    public String mTraceMethod;
    public final UserHandle mUser;
    public final String mVolumeUuid;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultiPackageInstallingSession {
        public final List mChildInstallingSessions;
        public final Set mCurrentInstallRequests;
        public final PackageManagerService mPm;

        public MultiPackageInstallingSession(
                List list, PackageManagerService packageManagerService) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() == 0) {
                throw new PackageManagerException("No child sessions found!", -20);
            }
            this.mPm = packageManagerService;
            this.mChildInstallingSessions = list;
            for (int i = 0; i < arrayList.size(); i++) {
                ((InstallingSession) arrayList.get(i)).mParentInstallingSession = this;
            }
            this.mCurrentInstallRequests = new ArraySet(this.mChildInstallingSessions.size());
        }

        public final String toString() {
            return "MultiPackageInstallingSession{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + "}";
        }
    }

    public InstallingSession(
            int i,
            File file,
            String str,
            PackageInstallerSession.AnonymousClass5 anonymousClass5,
            PackageInstaller.SessionParams sessionParams,
            InstallSource installSource,
            UserHandle userHandle,
            SigningDetails signingDetails,
            int i2,
            PackageLite packageLite,
            DomainSet domainSet,
            PackageManagerService packageManagerService,
            boolean z) {
        this.mIsMoveRequest = false;
        this.mPreferredInstallLocation = -1;
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        if (file != null) {
            this.mOriginInfo = new OriginInfo(file, true, false, null);
        } else {
            this.mOriginInfo = new OriginInfo(null, true, false, str);
        }
        this.mMoveInfo = null;
        String str2 = installSource.mInstallerPackageName;
        int i3 = sessionParams.installReason;
        if (packageManagerService
                        .snapshotComputer()
                        .checkUidPermission("android.permission.INSTALL_PACKAGES", i2)
                != 0) {
            String deviceOwnerOrProfileOwnerPackage =
                    packageManagerService.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(
                            UserHandle.getUserId(i2));
            if (deviceOwnerOrProfileOwnerPackage != null
                    && deviceOwnerOrProfileOwnerPackage.equals(str2)) {
                i3 = 1;
            } else if (i3 == 1) {
                i3 = 0;
            }
        }
        this.mInstallReason = i3;
        this.mInstallScenario = sessionParams.installScenario;
        this.mObserver = anonymousClass5;
        this.mInstallFlags = sessionParams.installFlags;
        this.mDevelopmentInstallFlags = sessionParams.developmentInstallFlags;
        this.mInstallSource = installSource;
        this.mVolumeUuid = sessionParams.volumeUuid;
        this.mPackageAbiOverride = sessionParams.abiOverride;
        this.mPermissionStates = sessionParams.getPermissionStates();
        this.mAllowlistedRestrictedPermissions = sessionParams.whitelistedRestrictedPermissions;
        this.mAutoRevokePermissionsMode = sessionParams.autoRevokePermissionsMode;
        this.mSigningDetails = signingDetails;
        this.mForceQueryableOverride = sessionParams.forceQueryableOverride;
        DataLoaderParams dataLoaderParams = sessionParams.dataLoaderParams;
        this.mDataLoaderType = dataLoaderParams != null ? dataLoaderParams.getType() : 0;
        this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
        this.mPackageSource = sessionParams.packageSource;
        this.mPackageLite = packageLite;
        this.mIsInherit = sessionParams.mode == 2;
        this.mSessionId = i;
        this.mRequireUserAction = sessionParams.requireUserAction;
        this.mApplicationEnabledSettingPersistent =
                sessionParams.applicationEnabledSettingPersistent;
        this.mPreVerifiedDomains = domainSet;
        this.mHasAppMetadataFile = z;
        this.mDexoptCompilerFilter = sessionParams.dexoptCompilerFilter;
    }

    public InstallingSession(
            OriginInfo originInfo,
            MoveInfo moveInfo,
            MovePackageHelper.AnonymousClass1 anonymousClass1,
            int i,
            InstallSource installSource,
            String str,
            UserHandle userHandle,
            String str2,
            PackageLite packageLite,
            PackageManagerService packageManagerService) {
        this.mIsMoveRequest = false;
        this.mPreferredInstallLocation = -1;
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        this.mOriginInfo = originInfo;
        this.mMoveInfo = moveInfo;
        this.mObserver = anonymousClass1;
        this.mInstallFlags = i;
        this.mDevelopmentInstallFlags = 0;
        this.mInstallSource = (InstallSource) Preconditions.checkNotNull(installSource);
        this.mVolumeUuid = str;
        this.mPackageAbiOverride = str2;
        this.mPermissionStates = new ArrayMap();
        this.mAllowlistedRestrictedPermissions = null;
        this.mAutoRevokePermissionsMode = 3;
        this.mSigningDetails = SigningDetails.UNKNOWN;
        this.mInstallReason = 0;
        this.mInstallScenario = 0;
        this.mForceQueryableOverride = false;
        this.mDataLoaderType = 0;
        this.mRequiredInstalledVersionCode = -1L;
        this.mPackageSource = 0;
        this.mPackageLite = packageLite;
        this.mIsInherit = false;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
        this.mApplicationEnabledSettingPersistent = false;
        this.mPreVerifiedDomains = null;
        this.mHasAppMetadataFile = false;
        this.mDexoptCompilerFilter = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0122 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleReturnCode(com.android.server.pm.InstallRequest r18) {
        /*
            Method dump skipped, instructions count: 913
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallingSession.handleReturnCode(com.android.server.pm.InstallRequest):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleStartCopy(com.android.server.pm.InstallRequest r22) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstallingSession.handleStartCopy(com.android.server.pm.InstallRequest):void");
    }

    public final void installApexPackages(List list) {
        OriginInfo originInfo;
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != 1) {
            throw new IllegalStateException(
                    "Only a non-staged install of a single APEX is supported");
        }
        InstallRequest installRequest = (InstallRequest) arrayList.get(0);
        InstallArgs installArgs = installRequest.mInstallArgs;
        boolean z = ((installArgs == null ? 0 : installArgs.mDevelopmentInstallFlags) & 1) != 0;
        PackageManagerService packageManagerService = this.mPm;
        if (installArgs == null) {
            originInfo = null;
        } else {
            try {
                originInfo = installArgs.mOriginInfo;
            } catch (PackageManagerException e) {
                installRequest.setError("APEX installation failed", e);
                PackageManagerService.invalidatePackageInfoCache();
                packageManagerService.notifyInstallObserver(installRequest);
                return;
            }
        }
        File file = originInfo.mResolvedFile;
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new PackageManagerException(file.getAbsolutePath() + " is not a directory", -36);
        }
        if (listFiles.length != 1) {
            throw new PackageManagerException(
                    "Expected exactly one .apex file under "
                            + file.getAbsolutePath()
                            + " got: "
                            + listFiles.length,
                    -37);
        }
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        PackageParser2 packageParser2 =
                (PackageParser2)
                        packageManagerServiceInjector.mScanningPackageParserProducer.produce(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        try {
            ApexManager apexManager = packageManagerService.mApexManager;
            File file2 = listFiles[0];
            ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) apexManager;
            apexManagerImpl.getClass();
            try {
                ApexInfo installAndActivatePackage =
                        apexManagerImpl
                                .waitForApexService()
                                .installAndActivatePackage(file2.getAbsolutePath(), z);
                installRequest.mApexInfo = installAndActivatePackage;
                installRequest.mApexModuleName = installAndActivatePackage.moduleName;
                packageManagerService.mHandler.post(
                        new InstallingSession$$ExternalSyntheticLambda2(this, list, 1));
                if (packageParser2 != null) {
                    packageParser2.close();
                }
            } catch (RemoteException unused) {
                throw new PackageManagerException(-110, "apexservice not available");
            } catch (Exception e2) {
                throw new PackageManagerException(-110, e2.getMessage());
            }
        } finally {
        }
    }

    public final void installStage() {
        this.mTraceMethod = "installStage";
        this.mTraceCookie = System.identityHashCode(this);
        Trace.asyncTraceBegin(262144L, "installStage", System.identityHashCode(this));
        Trace.asyncTraceBegin(262144L, "queueInstall", System.identityHashCode(this));
        StringBuilder sb = new StringBuilder("START INSTALL PACKAGE: observer{");
        IPackageInstallObserver2 iPackageInstallObserver2 = this.mObserver;
        sb.append(
                iPackageInstallObserver2 != null
                        ? Integer.valueOf(iPackageInstallObserver2.hashCode())
                        : "null");
        sb.append("}\n          stagedDir{");
        OriginInfo originInfo = this.mOriginInfo;
        sb.append(originInfo.mFile);
        sb.append("}\n");
        sb.append(
                (!PMRune.PM_INSTALL_TO_SDCARD || originInfo.mCid == null)
                        ? ""
                        : AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                new StringBuilder("          stagedCid{"), originInfo.mCid, "}\n"));
        sb.append("          pkg{");
        PackageLite packageLite = this.mPackageLite;
        sb.append(packageLite != null ? packageLite.getPackageName() : "null");
        sb.append("}\n          versionCode{");
        PackageLite packageLite2 = this.mPackageLite;
        sb.append(packageLite2 != null ? Integer.valueOf(packageLite2.getVersionCode()) : "null");
        sb.append("}\n          Request from{");
        sb.append(this.mInstallSource.mInstallerPackageName);
        sb.append("}");
        PmLog.logDebugInfoAndLogcat(sb.toString());
        this.mPm.mHandler.postDelayed(
                new InstallingSession$$ExternalSyntheticLambda0(this, 1),
                ThermalInstallThrottlingUtils.getInstallDelayByThermal(this.mDataLoaderType));
    }

    public final void installStage(List list) {
        PackageManagerService packageManagerService = this.mPm;
        MultiPackageInstallingSession multiPackageInstallingSession =
                new MultiPackageInstallingSession(list, packageManagerService);
        this.mTraceMethod = "installStageMultiPackage";
        this.mTraceCookie = System.identityHashCode(multiPackageInstallingSession);
        Trace.asyncTraceBegin(
                262144L,
                "installStageMultiPackage",
                System.identityHashCode(multiPackageInstallingSession));
        Trace.asyncTraceBegin(
                262144L, "queueInstall", System.identityHashCode(multiPackageInstallingSession));
        StringBuilder sb = new StringBuilder("START INSTALL MULTI PACKAGE:\n");
        for (InstallingSession installingSession :
                multiPackageInstallingSession.mChildInstallingSessions) {
            StringBuilder sb2 = new StringBuilder("          observer{");
            IPackageInstallObserver2 iPackageInstallObserver2 = installingSession.mObserver;
            Object obj = "null";
            sb2.append(
                    iPackageInstallObserver2 != null
                            ? Integer.valueOf(iPackageInstallObserver2.hashCode())
                            : "null");
            sb2.append("}\n");
            sb.append(sb2.toString());
            sb.append("          stagedDir{" + installingSession.mOriginInfo.mFile + "}\n");
            StringBuilder sb3 = new StringBuilder("          versionCode{");
            PackageLite packageLite = installingSession.mPackageLite;
            if (packageLite != null) {
                obj = Integer.valueOf(packageLite.getVersionCode());
            }
            sb3.append(obj);
            sb3.append("}\n");
            sb.append(sb3.toString());
            sb.append(
                    "          Request from{"
                            + installingSession.mInstallSource.mInstallerPackageName
                            + "}\n");
        }
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            sb.append(
                    "          pkg{"
                            + ((InstallingSession) it.next()).mPackageLite.getPackageName()
                            + "}\n");
        }
        PmLog.logDebugInfoAndLogcat(sb.toString());
        packageManagerService.mHandler.postDelayed(
                new InstallingSession$$ExternalSyntheticLambda4(multiPackageInstallingSession, 0),
                ThermalInstallThrottlingUtils.getInstallDelayByThermal(this.mDataLoaderType));
    }

    public final void processApkInstallRequests(List list, boolean z) {
        MoveInfo moveInfo;
        MoveInfo moveInfo2;
        MoveInfo moveInfo3;
        PackageManagerService packageManagerService = this.mPm;
        if (z) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                InstallRequest installRequest = (InstallRequest) it.next();
                if (AsecInstallHelper.installOnExternalAsec(installRequest.getInstallFlags())) {
                    String extractCidFromCodePath =
                            AsecInstallHelper.extractCidFromCodePath(installRequest.getCodePath());
                    if (installRequest.mReturnCode != 1) {
                        PackageHelperExt.destroySdDir(extractCidFromCodePath);
                    } else if (!PackageHelperExt.isContainerMounted(extractCidFromCodePath)) {
                        String mountSdDir =
                                PackageHelperExt.mountSdDir(
                                        extractCidFromCodePath,
                                        AsecInstallHelper.getEncryptKey(),
                                        1000);
                        if (mountSdDir != null) {
                            AsecInstallHelper.setMountPath(mountSdDir, installRequest);
                        } else {
                            installRequest.setError(-18, "Error while mounting SdDir");
                        }
                    }
                }
            }
            InstallPackageHelper installPackageHelper = packageManagerService.mInstallPackageHelper;
            installPackageHelper.getClass();
            try {
                PackageManagerTracedLock packageManagerTracedLock =
                        installPackageHelper.mPm.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                try {
                    Trace.traceBegin(262144L, "installPackages");
                    installPackageHelper.installPackagesLI(list);
                    packageManagerTracedLock.close();
                    Trace.traceEnd(262144L);
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        InstallRequest installRequest2 = (InstallRequest) it2.next();
                        RemovePackageHelper removePackageHelper =
                                packageManagerService.mRemovePackageHelper;
                        MoveInfo moveInfo4 = this.mMoveInfo;
                        if (moveInfo4 != null) {
                            int i = installRequest2.mReturnCode;
                            String str = moveInfo4.mFromCodePath;
                            String str2 = moveInfo4.mPackageName;
                            if (i == 1) {
                                removePackageHelper.cleanUpForMoveInstall(
                                        moveInfo4.mFromUuid, str2, str);
                            } else {
                                removePackageHelper.cleanUpForMoveInstall(
                                        moveInfo4.mToUuid, str2, str);
                            }
                        } else if (AsecInstallHelper.installOnExternalAsec(
                                installRequest2.getInstallFlags())) {
                            String extractCidFromCodePath2 =
                                    AsecInstallHelper.extractCidFromCodePath(
                                            installRequest2.getCodePath());
                            if (installRequest2.mReturnCode != 1) {
                                PackageHelperExt.destroySdDir(extractCidFromCodePath2);
                            } else if (!PackageHelperExt.isContainerMounted(
                                    extractCidFromCodePath2)) {
                                PackageHelperExt.mountSdDir(
                                        extractCidFromCodePath2,
                                        AsecInstallHelper.getEncryptKey(),
                                        Process.myUid());
                            }
                        } else if (installRequest2.mReturnCode != 1) {
                            removePackageHelper.removeCodePath(installRequest2.getCodeFile());
                        }
                    }
                } finally {
                }
            } catch (Throwable th) {
                Trace.traceEnd(262144L);
                throw th;
            }
        } else {
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                InstallRequest installRequest3 = (InstallRequest) it3.next();
                if (installRequest3.mReturnCode != 1) {
                    if (installRequest3.isInstallMove()) {
                        String str3 = null;
                        InstallArgs installArgs = installRequest3.mInstallArgs;
                        String str4 =
                                (installArgs == null || (moveInfo3 = installArgs.mMoveInfo) == null)
                                        ? null
                                        : moveInfo3.mToUuid;
                        String str5 =
                                (installArgs == null || (moveInfo2 = installArgs.mMoveInfo) == null)
                                        ? null
                                        : moveInfo2.mPackageName;
                        if (installArgs != null && (moveInfo = installArgs.mMoveInfo) != null) {
                            str3 = moveInfo.mFromCodePath;
                        }
                        packageManagerService.mRemovePackageHelper.cleanUpForMoveInstall(
                                str4, str5, str3);
                    } else if (AsecInstallHelper.installOnExternalAsec(this.mInstallFlags)) {
                        String extractCidFromCodePath3 =
                                AsecInstallHelper.extractCidFromCodePath(
                                        installRequest3.getCodePath());
                        if (extractCidFromCodePath3 != null) {
                            PackageHelperExt.destroySdDir(extractCidFromCodePath3);
                        }
                    } else {
                        packageManagerService.mRemovePackageHelper.removeCodePath(
                                installRequest3.getCodeFile());
                    }
                }
            }
        }
        Iterator it4 = list.iterator();
        while (it4.hasNext()) {
            packageManagerService.mInstallPackageHelper.restoreAndPostInstall(
                    (InstallRequest) it4.next());
        }
    }

    public final void processInstallRequests(List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InstallRequest installRequest = (InstallRequest) it.next();
            if ((installRequest.getInstallFlags() & 131072) != 0) {
                arrayList.add(installRequest);
            } else {
                arrayList2.add(installRequest);
            }
        }
        if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
            throw new IllegalStateException(
                    "Attempted to do a multi package install of both APEXes and APKs");
        }
        if (arrayList.isEmpty()) {
            processApkInstallRequests(list, z);
        } else if (z) {
            new Thread(
                            new InstallingSession$$ExternalSyntheticLambda2(this, arrayList, 0),
                            "installApexPackages")
                    .start();
        } else {
            this.mPm.notifyInstallObserver((InstallRequest) arrayList.get(0));
        }
    }

    public final String toString() {
        return "InstallingSession{"
                + Integer.toHexString(System.identityHashCode(this))
                + " file="
                + this.mOriginInfo.mFile
                + "}";
    }
}
