package com.android.server.pm;

import android.apex.ApexInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.parsing.PackageLite;
import android.content.pm.verify.domain.DomainSet;
import android.os.Build;
import android.os.UserHandle;
import android.util.ExceptionUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.server.art.model.DexoptResult;
import com.android.server.pm.pkg.AndroidPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InstallRequest {
    public ApexInfo mApexInfo;
    public String mApexModuleName;
    public int mAppId;
    public ArchivedPackageParcel mArchivedPackage;
    public boolean mClearCodeCache;
    public PackageSetting mDisabledPs;
    public int[] mFirstTimeBroadcastInstantUserIds;
    public int[] mFirstTimeBroadcastUserIds;
    public PackageFreezer mFreezer;
    public final boolean mHasAppMetadataFileFromInstaller;
    public final InstallArgs mInstallArgs;
    public final int mInstallerUidForInstallExisting;
    public int mInternalErrorCode;
    public final boolean mIsInstallForUsers;
    public final boolean mIsInstallInherit;
    public ArrayList mLibraryConsumers;
    public String mName;
    public boolean mNeedToMove;
    public int[] mNewUsers;
    public String mOrigPackage;
    public String mOrigPermission;
    public int[] mOrigUsers;
    public PackageSetting mOriginalPs;
    public final PackageLite mPackageLite;
    public final PackageMetrics mPackageMetrics;
    public int mParseFlags;
    public ParsedPackage mParsedPackage;
    public AndroidPackage mPkg;
    public Runnable mPostInstallRunnable;
    public final DomainSet mPreVerifiedDomains;
    public PackageRemovedInfo mRemovedInfo;
    public boolean mReplace;
    public final int mRequireUserAction;
    public SparseArray mResponsibleInstallerTitles;
    public int mReturnCode;
    public String mReturnMsg;
    public int mScanFlags;
    public ScanResult mScanResult;
    public final int mSessionId;
    public boolean mSpqrProfileGenerated;
    public boolean mSystem;
    public int[] mUpdateBroadcastInstantUserIds;
    public int[] mUpdateBroadcastUserIds;
    public final int mUserId;
    public final ArrayList mWarnings;

    public InstallRequest(
            int i,
            AndroidPackage androidPackage,
            int[] iArr,
            InstallPackageHelper$$ExternalSyntheticLambda1
                    installPackageHelper$$ExternalSyntheticLambda1,
            int i2,
            int i3,
            boolean z) {
        this.mAppId = -1;
        this.mNeedToMove = false;
        int[] iArr2 = PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastUserIds = iArr2;
        this.mFirstTimeBroadcastInstantUserIds = iArr2;
        this.mUpdateBroadcastUserIds = iArr2;
        this.mUpdateBroadcastInstantUserIds = iArr2;
        this.mWarnings = new ArrayList();
        this.mInstallerUidForInstallExisting = -1;
        this.mUserId = i;
        this.mInstallArgs = null;
        this.mReturnCode = 1;
        this.mPkg = androidPackage;
        this.mNewUsers = iArr;
        this.mPostInstallRunnable = installPackageHelper$$ExternalSyntheticLambda1;
        this.mPackageMetrics = new PackageMetrics(this);
        this.mIsInstallForUsers = true;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
        this.mAppId = i2;
        this.mInstallerUidForInstallExisting = i3;
        this.mSystem = z;
        this.mHasAppMetadataFileFromInstaller = false;
    }

    public InstallRequest(
            ParsedPackage parsedPackage,
            int i,
            int i2,
            UserHandle userHandle,
            ScanResult scanResult,
            PackageSetting packageSetting) {
        this.mAppId = -1;
        this.mNeedToMove = false;
        int[] iArr = PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastUserIds = iArr;
        this.mFirstTimeBroadcastInstantUserIds = iArr;
        this.mUpdateBroadcastUserIds = iArr;
        this.mUpdateBroadcastInstantUserIds = iArr;
        this.mWarnings = new ArrayList();
        this.mInstallerUidForInstallExisting = -1;
        if (userHandle != null) {
            this.mUserId = userHandle.getIdentifier();
        } else {
            this.mUserId = 0;
        }
        this.mInstallArgs = null;
        this.mParsedPackage = parsedPackage;
        this.mArchivedPackage = null;
        this.mParseFlags = i;
        this.mScanFlags = i2;
        this.mScanResult = scanResult;
        this.mPackageMetrics = null;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
        this.mDisabledPs = packageSetting;
        this.mHasAppMetadataFileFromInstaller = false;
    }

    public InstallRequest(InstallingSession installingSession) {
        this.mAppId = -1;
        this.mNeedToMove = false;
        int[] iArr = PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastUserIds = iArr;
        this.mFirstTimeBroadcastInstantUserIds = iArr;
        this.mUpdateBroadcastUserIds = iArr;
        this.mUpdateBroadcastInstantUserIds = iArr;
        this.mWarnings = new ArrayList();
        this.mInstallerUidForInstallExisting = -1;
        this.mUserId = installingSession.mUser.getIdentifier();
        this.mInstallArgs =
                new InstallArgs(
                        installingSession.mOriginInfo,
                        installingSession.mMoveInfo,
                        installingSession.mObserver,
                        installingSession.mInstallFlags,
                        installingSession.mDevelopmentInstallFlags,
                        installingSession.mInstallSource,
                        installingSession.mVolumeUuid,
                        installingSession.mPackageAbiOverride,
                        installingSession.mPermissionStates,
                        installingSession.mAllowlistedRestrictedPermissions,
                        installingSession.mAutoRevokePermissionsMode,
                        installingSession.mTraceMethod,
                        installingSession.mTraceCookie,
                        installingSession.mSigningDetails,
                        installingSession.mInstallReason,
                        installingSession.mInstallScenario,
                        installingSession.mForceQueryableOverride,
                        installingSession.mDataLoaderType,
                        installingSession.mPackageSource,
                        installingSession.mApplicationEnabledSettingPersistent,
                        installingSession.mDexoptCompilerFilter);
        this.mPackageLite = installingSession.mPackageLite;
        this.mPackageMetrics = new PackageMetrics(this);
        this.mIsInstallInherit = installingSession.mIsInherit;
        this.mSessionId = installingSession.mSessionId;
        this.mRequireUserAction = installingSession.mRequireUserAction;
        this.mPreVerifiedDomains = installingSession.mPreVerifiedDomains;
        this.mHasAppMetadataFileFromInstaller = installingSession.mHasAppMetadataFile;
    }

    public final void assertScanResultExists() {
        if (this.mScanResult == null) {
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                throw new IllegalStateException("ScanResult cannot be null.");
            }
            Slog.e("PackageManager", "ScanResult is null and it should not happen");
        }
    }

    public final void closeFreezer() {
        PackageFreezer packageFreezer = this.mFreezer;
        if (packageFreezer != null) {
            packageFreezer.close();
        }
    }

    public final File getCodeFile() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return null;
        }
        return installArgs.mCodeFile;
    }

    public final String getCodePath() {
        File file;
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null || (file = installArgs.mCodeFile) == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    public final int getDataLoaderType() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return 0;
        }
        return installArgs.mDataLoaderType;
    }

    public final int getInstallFlags() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return 0;
        }
        return installArgs.mInstallFlags;
    }

    public final int getInstallReason() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return 0;
        }
        return installArgs.mInstallReason;
    }

    public final InstallSource getInstallSource() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return null;
        }
        return installArgs.mInstallSource;
    }

    public final String getInstallerPackageName() {
        InstallSource installSource;
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null || (installSource = installArgs.mInstallSource) == null) {
            return null;
        }
        return installSource.mInstallerPackageName;
    }

    public final PackageSetting getScannedPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mPkgSetting;
    }

    public final UserHandle getUser() {
        return new UserHandle(this.mUserId);
    }

    public final int getUserId() {
        return this.mUserId;
    }

    public final String getVolumeUuid() {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs == null) {
            return null;
        }
        return installArgs.mVolumeUuid;
    }

    public final boolean isInstallMove() {
        InstallArgs installArgs = this.mInstallArgs;
        return (installArgs == null || installArgs.mMoveInfo == null) ? false : true;
    }

    public final void onDexoptFinished(DexoptResult dexoptResult) {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs != null && (installArgs.mInstallFlags & 32) != 0) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = dexoptResult.getPackageDexoptResults().iterator();
            while (it.hasNext()) {
                Iterator it2 =
                        ((DexoptResult.PackageDexoptResult) it.next())
                                .getDexContainerFileDexoptResults()
                                .iterator();
                while (it2.hasNext()) {
                    linkedHashSet.addAll(
                            ((DexoptResult.DexContainerFileDexoptResult) it2.next())
                                    .getExternalProfileErrors());
                }
            }
            if (!linkedHashSet.isEmpty()) {
                this.mWarnings.add(
                        "Error occurred during dexopt when processing external profiles:\n  "
                                + String.join("\n  ", linkedHashSet));
            }
        }
        PackageMetrics packageMetrics = this.mPackageMetrics;
        if (packageMetrics == null || dexoptResult.getFinalStatus() != 20) {
            return;
        }
        Iterator it3 = dexoptResult.getPackageDexoptResults().iterator();
        long j = 0;
        while (it3.hasNext()) {
            Iterator it4 =
                    ((DexoptResult.PackageDexoptResult) it3.next())
                            .getDexContainerFileDexoptResults()
                            .iterator();
            while (it4.hasNext()) {
                j +=
                        ((DexoptResult.DexContainerFileDexoptResult) it4.next())
                                .getDex2oatWallTimeMillis();
            }
        }
        packageMetrics.mInstallSteps.put(5, new PackageMetrics.InstallStep(j));
    }

    public final void setCodeFile(File file) {
        InstallArgs installArgs = this.mInstallArgs;
        if (installArgs != null) {
            installArgs.mCodeFile = file;
        }
    }

    public final void setError(int i, String str) {
        this.mReturnCode = i;
        this.mReturnMsg = str;
        Slog.w("PackageManager", str);
        PackageMetrics packageMetrics = this.mPackageMetrics;
        if (packageMetrics != null) {
            packageMetrics.reportInstallationStats(false);
        }
    }

    public final void setError(String str, PackageManagerException packageManagerException) {
        this.mInternalErrorCode = packageManagerException.internalErrorCode;
        this.mReturnCode = packageManagerException.error;
        this.mReturnMsg = ExceptionUtils.getCompleteMessage(str, packageManagerException);
        Slog.w("PackageManager", str, packageManagerException);
        PackageMetrics packageMetrics = this.mPackageMetrics;
        if (packageMetrics != null) {
            packageMetrics.reportInstallationStats(false);
        }
    }

    public final void setOriginUsers(int[] iArr) {
        this.mOrigUsers = iArr;
    }

    public final void setPrepareResult(
            boolean z,
            int i,
            int i2,
            PackageSetting packageSetting,
            ParsedPackage parsedPackage,
            ArchivedPackageParcel archivedPackageParcel,
            boolean z2,
            boolean z3,
            PackageSetting packageSetting2,
            PackageSetting packageSetting3) {
        this.mReplace = z;
        this.mScanFlags = i;
        this.mParseFlags = i2;
        this.mParsedPackage = parsedPackage;
        this.mArchivedPackage = archivedPackageParcel;
        this.mClearCodeCache = z2;
        this.mSystem = z3;
        this.mOriginalPs = packageSetting2;
        this.mDisabledPs = packageSetting3;
    }

    public final void setRemovedInfo(PackageRemovedInfo packageRemovedInfo) {
        this.mRemovedInfo = packageRemovedInfo;
    }

    public final void setReturnCode(int i) {
        this.mReturnCode = i;
    }
}
