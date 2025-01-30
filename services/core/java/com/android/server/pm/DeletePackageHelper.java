package com.android.server.pm;

import android.app.ApplicationPackageManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ASKSManager;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.pm.VersionedPackage;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.server.p025pm.PmLog;
import com.samsung.android.server.p025pm.install.SilentUninstallerList;
import dalvik.system.VMRuntime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class DeletePackageHelper {
    public final AppDataHelper mAppDataHelper;
    public final PermissionManagerServiceInternal mPermissionManager;
    public final PackageManagerService mPm;
    public final RemovePackageHelper mRemovePackageHelper;
    public final UserManagerInternal mUserManagerInternal;
    public final SilentUninstallerList mSilentUninstallerList = new SilentUninstallerList();
    public final SparseArray mRunningOverlayInstalls = new SparseArray();
    public int mNextOverlayInstallToken = 1;

    public DeletePackageHelper(PackageManagerService packageManagerService, RemovePackageHelper removePackageHelper, AppDataHelper appDataHelper) {
        this.mPm = packageManagerService;
        this.mUserManagerInternal = packageManagerService.mInjector.getUserManagerInternal();
        this.mPermissionManager = packageManagerService.mInjector.getPermissionManagerServiceInternal();
        this.mRemovePackageHelper = removePackageHelper;
        this.mAppDataHelper = appDataHelper;
    }

    public DeletePackageHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        AppDataHelper appDataHelper = new AppDataHelper(packageManagerService);
        this.mAppDataHelper = appDataHelper;
        this.mUserManagerInternal = packageManagerService.mInjector.getUserManagerInternal();
        this.mPermissionManager = packageManagerService.mInjector.getPermissionManagerServiceInternal();
        this.mRemovePackageHelper = new RemovePackageHelper(packageManagerService, appDataHelper);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00dc, code lost:
    
        if (r3.getUserInfo(r3.getProfileParentId(r32)).isAdmin() == false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int deletePackageX(String str, long j, int i, int i2, boolean z) {
        AndroidPackage androidPackage;
        PackageSetting packageSetting;
        int i3;
        SparseArray sparseArray;
        Object obj;
        int i4;
        int i5;
        boolean z2;
        PackageSetting packageLPr;
        boolean z3;
        boolean z4;
        SharedLibraryInfo sharedLibraryInfo;
        PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo(this.mPm);
        boolean z5 = (i2 & 268435456) != 0;
        int i6 = i2 & 2;
        int i7 = i6 != 0 ? -1 : i;
        if (this.mPm.isPackageDeviceAdmin(str, i7)) {
            Slog.w("PackageManager", "Not removing package " + str + ": has active device admin");
            return -2;
        }
        synchronized (this.mPm.mLock) {
            Computer snapshotComputer = this.mPm.snapshotComputer();
            PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(str);
            if (packageLPr2 == null) {
                Slog.w("PackageManager", "Not removing non-existent package " + str);
                return z5 ? 1 : -1;
            }
            if (j != -1 && packageLPr2.getVersionCode() != j) {
                Slog.w("PackageManager", "Not removing package " + str + " with versionCode " + packageLPr2.getVersionCode() + " != " + j);
                return -1;
            }
            if (PackageManagerServiceUtils.isUpdatedSystemApp(packageLPr2) && (i2 & 4) == 0) {
                UserInfo userInfo = this.mUserManagerInternal.getUserInfo(i);
                if (userInfo != null) {
                    if (!userInfo.isAdmin()) {
                        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
                    }
                }
                Slog.w("PackageManager", "Not removing package " + str + " as only admin user (or their profile) may downgrade system apps");
                EventLog.writeEvent(1397638484, "170646036", -1, str);
                return -3;
            }
            PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(str);
            AndroidPackage androidPackage2 = (AndroidPackage) this.mPm.mPackages.get(str);
            int[] userIds = this.mUserManagerInternal.getUserIds();
            if (androidPackage2 != null) {
                if (androidPackage2.getStaticSharedLibraryName() != null) {
                    sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(androidPackage2.getStaticSharedLibraryName(), androidPackage2.getStaticSharedLibraryVersion());
                } else {
                    sharedLibraryInfo = androidPackage2.getSdkLibraryName() != null ? snapshotComputer.getSharedLibraryInfo(androidPackage2.getSdkLibraryName(), androidPackage2.getSdkLibVersionMajor()) : null;
                }
                if (sharedLibraryInfo != null) {
                    int length = userIds.length;
                    int i8 = 0;
                    while (i8 < length) {
                        int i9 = userIds[i8];
                        int i10 = length;
                        if (i7 == -1 || i7 == i9) {
                            List packagesUsingSharedLibrary = snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 4202496L, 1000, i9);
                            if (!ArrayUtils.isEmpty(packagesUsingSharedLibrary)) {
                                Slog.w("PackageManager", "Not removing package " + androidPackage2.getManifestPackageName() + " hosting lib " + sharedLibraryInfo.getName() + " version " + sharedLibraryInfo.getLongVersion() + " used by " + packagesUsingSharedLibrary + " for user " + i9);
                                return -6;
                            }
                        }
                        i8++;
                        length = i10;
                    }
                }
            }
            packageRemovedInfo.mOrigUsers = packageLPr2.queryInstalledUsers(userIds, true);
            if (PackageManagerServiceUtils.isUpdatedSystemApp(packageLPr2) && (i2 & 4) == 0) {
                SparseArray sparseArray2 = new SparseArray();
                int i11 = 0;
                while (i11 < userIds.length) {
                    PackageUserStateInternal readUserState = packageLPr2.readUserState(userIds[i11]);
                    sparseArray2.put(userIds[i11], new TempUserState(readUserState.getEnabledState(), readUserState.getLastDisableAppCaller(), readUserState.isInstalled()));
                    i11++;
                    androidPackage2 = androidPackage2;
                    packageLPr2 = packageLPr2;
                }
                androidPackage = androidPackage2;
                packageSetting = packageLPr2;
                sparseArray = sparseArray2;
                i3 = -1;
            } else {
                androidPackage = androidPackage2;
                packageSetting = packageLPr2;
                i3 = i7;
                sparseArray = null;
            }
            boolean isInstallerPackage = this.mPm.mSettings.isInstallerPackage(str);
            Object obj2 = this.mPm.mInstallLock;
            synchronized (obj2) {
                try {
                    try {
                        AndroidPackage androidPackage3 = androidPackage;
                        PackageSetting packageSetting2 = packageSetting;
                        int i12 = i7;
                        PackageFreezer freezePackageForDelete = this.mPm.freezePackageForDelete(str, i3, i2, "deletePackageX", 13);
                        try {
                            obj = obj2;
                            int[] iArr = userIds;
                            int i13 = 0;
                            try {
                                boolean deletePackageLIF = deletePackageLIF(str, UserHandle.of(i12), true, userIds, i2 | Integer.MIN_VALUE, packageRemovedInfo, true);
                                if (freezePackageForDelete != null) {
                                    freezePackageForDelete.close();
                                }
                                if (deletePackageLIF && androidPackage3 != null) {
                                    synchronized (this.mPm.mLock) {
                                        z4 = this.mPm.mPackages.get(androidPackage3.getPackageName()) != null;
                                    }
                                    this.mPm.mInstantAppRegistry.onPackageUninstalled(androidPackage3, packageSetting2, packageRemovedInfo.mRemovedUsers, z4);
                                }
                                synchronized (this.mPm.mLock) {
                                    if (deletePackageLIF) {
                                        this.mPm.updateSequenceNumberLP(packageSetting2, packageRemovedInfo.mRemovedUsers);
                                        this.mPm.updateInstantAppInstallerLocked(str);
                                    }
                                }
                                ApplicationPackageManager.invalidateGetPackagesForUidCache();
                                if (deletePackageLIF) {
                                    boolean z6 = (i2 & 8) == 0;
                                    if (packageRemovedInfo.mIsExternal) {
                                        this.mPm.getAsecInstallHelper().sendResourcesChangedBroadcast(false, false, new String[]{packageRemovedInfo.mRemovedPackage}, new int[]{packageRemovedInfo.mUid});
                                    }
                                    packageRemovedInfo.sendPackageRemovedBroadcasts(z6, z);
                                    if (this.mPm.isLocaleOptimizedPackage(packageRemovedInfo.mRemovedPackage)) {
                                        checkAndInstallLocaleOverlays(packageRemovedInfo);
                                    } else {
                                        packageRemovedInfo.sendSystemPackageUpdatedBroadcasts();
                                    }
                                    i4 = i12;
                                    PackageMetrics.onUninstallSucceeded(packageRemovedInfo, i2, i4);
                                } else {
                                    i4 = i12;
                                }
                                VMRuntime.getRuntime().requestConcurrentGC();
                                synchronized (this.mPm.mInstallLock) {
                                    InstallArgs installArgs = packageRemovedInfo.mArgs;
                                    if (installArgs != null) {
                                        this.mRemovePackageHelper.cleanUpResources(installArgs.mCodeFile, installArgs.mInstructionSets);
                                    }
                                    if (sparseArray != null) {
                                        synchronized (this.mPm.mLock) {
                                            PackageSetting packageSettingForMutation = this.mPm.getPackageSettingForMutation(str);
                                            if (packageSettingForMutation != null) {
                                                AndroidPackageInternal pkg = packageSettingForMutation.getPkg();
                                                boolean z7 = pkg != null && pkg.isEnabled();
                                                boolean z8 = false;
                                                while (true) {
                                                    int[] iArr2 = iArr;
                                                    if (i13 >= iArr2.length) {
                                                        break;
                                                    }
                                                    TempUserState tempUserState = (TempUserState) sparseArray.get(iArr2[i13]);
                                                    SparseArray sparseArray3 = sparseArray;
                                                    int i14 = tempUserState.enabledState;
                                                    iArr = iArr2;
                                                    packageSettingForMutation.setEnabled(i14, iArr2[i13], tempUserState.lastDisableAppCaller);
                                                    if (!z8 && tempUserState.installed) {
                                                        if (i14 == 0 && z7) {
                                                            z3 = true;
                                                            z8 = z3;
                                                        }
                                                        z3 = true;
                                                        if (i14 != 1) {
                                                        }
                                                        z8 = z3;
                                                    }
                                                    i13++;
                                                    sparseArray = sparseArray3;
                                                }
                                                i5 = 1;
                                                z2 = z8;
                                            } else {
                                                i5 = 1;
                                                Slog.w("PackageManager", "Missing PackageSetting after uninstalling the update for system app: " + str + ". This should not happen.");
                                                z2 = false;
                                            }
                                            this.mPm.mSettings.writeAllUsersPackageRestrictionsLPr();
                                        }
                                    } else {
                                        i5 = 1;
                                        z2 = false;
                                    }
                                    AndroidPackageInternal pkg2 = disabledSystemPkgLPr == null ? null : disabledSystemPkgLPr.getPkg();
                                    if (pkg2 != null && pkg2.isStub()) {
                                        synchronized (this.mPm.mLock) {
                                            packageLPr = this.mPm.mSettings.getPackageLPr(pkg2.getPackageName());
                                        }
                                        if (packageLPr != null) {
                                            if (z2) {
                                                if (PackageManagerService.DEBUG_COMPRESSION) {
                                                    Slog.i("PackageManager", "Enabling system stub after removal; pkg: " + pkg2.getPackageName());
                                                }
                                                new InstallPackageHelper(this.mPm).enableCompressedPackage(pkg2, packageLPr);
                                            } else if (PackageManagerService.DEBUG_COMPRESSION) {
                                                Slog.i("PackageManager", "System stub disabled for all users, leaving uncompressed after removal; pkg: " + pkg2.getPackageName());
                                            }
                                        }
                                    }
                                }
                                if (deletePackageLIF && isInstallerPackage) {
                                    this.mPm.mInjector.getPackageInstallerService().onInstallerPackageDeleted(packageSetting2.getAppId(), i4);
                                }
                                if (deletePackageLIF && i == 0) {
                                    Slog.d("AASA_PackageManager_RESTRICTED", "Uninstall : userId = " + i + ", info.mRemovedAppId = " + packageRemovedInfo.mRemovedAppId + ", info.uid = " + packageRemovedInfo.mUid + ", packageName = " + str);
                                    try {
                                        ASKSManager.getASKSManager().clearASKSruleForRemovedPackage(str);
                                    } catch (RemoteException e) {
                                        Log.e("PackageManager", "RemoteException: " + e.getMessage());
                                    }
                                }
                                if (!PersonaServiceHelper.isSpfKnoxSupported() || !deletePackageLIF) {
                                    if (deletePackageLIF) {
                                        return i5;
                                    }
                                    return -1;
                                }
                                if (this.mPm.getPersonaService() == null || this.mPm.getPersonaService().getSeparationConfigfromCache() == null) {
                                    return i5;
                                }
                                if ((i != 0 && i != this.mPm.getPersonaService().getAppSeparationId()) || !this.mPm.getPersonaService().isAppSeparationApp(str) || this.mPm.getPersonaService().getAppSeparationId() == 0 || z5 || i6 != 0) {
                                    return i5;
                                }
                                int i15 = i2 | 268435456;
                                if (i == 0) {
                                    return deletePackageX(str, j, this.mPm.getPersonaService().getAppSeparationId(), i15, true);
                                }
                                return deletePackageX(str, j, 0, i15, true);
                            } catch (Throwable th) {
                                th = th;
                                Throwable th2 = th;
                                if (freezePackageForDelete == null) {
                                    throw th2;
                                }
                                try {
                                    freezePackageForDelete.close();
                                    throw th2;
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                    throw th2;
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            obj = obj2;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        obj = obj2;
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            }
        }
    }

    public final void checkAndInstallLocaleOverlays(PackageRemovedInfo packageRemovedInfo) {
        Slog.i("PackageManager", "CheckAndInstallLocaleOverlays() called with: packageName = [" + packageRemovedInfo.mRemovedPackage + "], isRemovedUpdate = [" + packageRemovedInfo.mIsRemovedPackageSystemUpdate + "]");
        if (packageRemovedInfo.mIsRemovedPackageSystemUpdate) {
            if (this.mNextOverlayInstallToken < 0) {
                this.mNextOverlayInstallToken = 1;
            }
            int i = this.mNextOverlayInstallToken;
            this.mNextOverlayInstallToken = i + 1;
            this.mRunningOverlayInstalls.put(i, packageRemovedInfo);
            this.mPm.updateLocaleOverlaysForUpdateRemovedPackage(i, packageRemovedInfo.mRemovedPackage);
        }
    }

    public void overlaysInstallComplete(int i) {
        Slog.i("PackageManager", "overlaysInstallComplete() called with: token = [" + i + "]");
        PackageRemovedInfo packageRemovedInfo = (PackageRemovedInfo) this.mRunningOverlayInstalls.get(i);
        this.mRunningOverlayInstalls.delete(i);
        if (packageRemovedInfo != null) {
            Slog.i("PackageManager", "overlaysInstallComplete(): Calling sendSystemPackageUpdatedBroadcasts for package: " + packageRemovedInfo.mRemovedPackage);
            packageRemovedInfo.sendSystemPackageUpdatedBroadcasts();
        }
    }

    public boolean deletePackageLIF(String str, UserHandle userHandle, boolean z, int[] iArr, int i, PackageRemovedInfo packageRemovedInfo, boolean z2) {
        DeletePackageAction mayDeletePackageLocked;
        synchronized (this.mPm.mLock) {
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
            mayDeletePackageLocked = mayDeletePackageLocked(packageRemovedInfo, packageLPr, this.mPm.mSettings.getDisabledSystemPkgLPr(packageLPr), i, userHandle);
        }
        if (mayDeletePackageLocked == null) {
            return false;
        }
        try {
            executeDeletePackageLIF(mayDeletePackageLocked, str, z, iArr, z2);
            return true;
        } catch (SystemDeleteException unused) {
            return false;
        }
    }

    public static DeletePackageAction mayDeletePackageLocked(PackageRemovedInfo packageRemovedInfo, PackageSetting packageSetting, PackageSetting packageSetting2, int i, UserHandle userHandle) {
        if (packageSetting == null) {
            return null;
        }
        if (PackageManagerServiceUtils.isSystemApp(packageSetting)) {
            boolean z = true;
            boolean z2 = (i & 4) != 0;
            if (userHandle != null && userHandle.getIdentifier() != -1) {
                z = false;
            }
            if ((!z2 || z) && packageSetting2 == null) {
                Slog.w("PackageManager", "Attempt to delete unknown system package " + packageSetting.getPkg().getPackageName());
                return null;
            }
        }
        return new DeletePackageAction(packageSetting, packageSetting2, packageRemovedInfo, i, userHandle);
    }

    public void executeDeletePackage(DeletePackageAction deletePackageAction, String str, boolean z, int[] iArr, boolean z2) {
        synchronized (this.mPm.mInstallLock) {
            executeDeletePackageLIF(deletePackageAction, str, z, iArr, z2);
        }
    }

    public final void executeDeletePackageLIF(DeletePackageAction deletePackageAction, String str, boolean z, int[] iArr, boolean z2) {
        boolean z3;
        boolean z4;
        PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        UserHandle userHandle = deletePackageAction.mUser;
        int i = deletePackageAction.mFlags;
        boolean isSystemApp = PackageManagerServiceUtils.isSystemApp(packageSetting);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        for (int i2 : iArr) {
            sparseBooleanArray.put(i2, this.mPm.checkPermission("android.permission.SUSPEND_APPS", str, i2) == 0);
        }
        int identifier = userHandle == null ? -1 : userHandle.getIdentifier();
        if ((isSystemApp && (i & 4) == 0) || identifier == -1) {
            z3 = true;
        } else {
            synchronized (this.mPm.mLock) {
                markPackageUninstalledForUserLPw(packageSetting, userHandle);
                if (!isSystemApp) {
                    boolean shouldKeepUninstalledPackageLPr = this.mPm.shouldKeepUninstalledPackageLPr(str);
                    if (!packageSetting.isAnyInstalled(this.mUserManagerInternal.getUserIds()) && !shouldKeepUninstalledPackageLPr) {
                        if (z && AsecInstallHelper.isExternal(packageSetting) && !this.mPm.getAsecInstallHelper().shouldAddDexOptOnAsec()) {
                            this.mPm.deleteOatArtifactsOfPackage(this.mPm.snapshotComputer(), str);
                        }
                        packageSetting.setPkg(null);
                        z3 = true;
                        packageSetting.setInstalled(true, identifier);
                        this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
                        z4 = false;
                    }
                }
                z3 = true;
                z4 = true;
            }
            if (z4) {
                clearPackageStateForUserLIF(packageSetting, identifier, packageRemovedInfo, i);
                this.mPm.scheduleWritePackageRestrictions(userHandle);
                return;
            }
        }
        if (z && !isSystemApp && identifier == -1 && AsecInstallHelper.isExternal(packageSetting) && !this.mPm.getAsecInstallHelper().shouldAddDexOptOnAsec()) {
            this.mPm.deleteOatArtifactsOfPackage(this.mPm.snapshotComputer(), str);
        }
        if (isSystemApp) {
            deleteInstalledSystemPackage(deletePackageAction, iArr, z2);
            new InstallPackageHelper(this.mPm).restoreDisabledSystemPackageLIF(deletePackageAction, iArr, z2);
        } else {
            deleteInstalledPackageLIF(packageSetting, z, i, iArr, packageRemovedInfo, z2);
        }
        int[] iArr2 = packageRemovedInfo != null ? packageRemovedInfo.mRemovedUsers : null;
        if (iArr2 == null) {
            iArr2 = this.mPm.resolveUserIds(identifier);
        }
        Computer snapshotComputer = this.mPm.snapshotComputer();
        for (int i3 : iArr2) {
            if (sparseBooleanArray.get(i3)) {
                this.mPm.unsuspendForSuspendingPackage(snapshotComputer, str, i3);
                this.mPm.removeAllDistractingPackageRestrictions(snapshotComputer, i3);
            }
        }
        if (packageRemovedInfo != null) {
            synchronized (this.mPm.mLock) {
                packageRemovedInfo.mRemovedForAllUsers = this.mPm.mPackages.get(packageSetting.getPackageName()) == null ? z3 : false;
            }
        }
    }

    public final void clearPackageStateForUserLIF(PackageSetting packageSetting, int i, PackageRemovedInfo packageRemovedInfo, int i2) {
        AndroidPackage androidPackage;
        SharedUserSetting sharedUserSettingLPr;
        int[] iArr;
        synchronized (this.mPm.mLock) {
            androidPackage = (AndroidPackage) this.mPm.mPackages.get(packageSetting.getPackageName());
            sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
        }
        this.mAppDataHelper.destroyAppProfilesLIF(androidPackage);
        List packages = sharedUserSettingLPr != null ? sharedUserSettingLPr.getPackages() : Collections.emptyList();
        PreferredActivityHelper preferredActivityHelper = new PreferredActivityHelper(this.mPm);
        if (i == -1) {
            iArr = this.mUserManagerInternal.getUserIds();
        } else {
            iArr = new int[]{i};
        }
        int[] iArr2 = iArr;
        boolean z = false;
        for (int i3 : iArr2) {
            if ((i2 & 1) == 0) {
                this.mAppDataHelper.destroyAppDataLIF(androidPackage, i3, 7);
            }
            this.mAppDataHelper.clearKeystoreData(i3, packageSetting.getAppId());
            preferredActivityHelper.clearPackagePreferredActivities(packageSetting.getPackageName(), i3);
            this.mPm.mDomainVerificationManager.clearPackageForUser(packageSetting.getPackageName(), i3);
        }
        this.mPermissionManager.onPackageUninstalled(packageSetting.getPackageName(), packageSetting.getAppId(), packageSetting, androidPackage, packages, i);
        if (packageRemovedInfo != null) {
            if ((i2 & 1) == 0) {
                packageRemovedInfo.mDataRemoved = true;
            }
            packageRemovedInfo.mRemovedPackage = packageSetting.getPackageName();
            packageRemovedInfo.mInstallerPackageName = packageSetting.getInstallSource().mInstallerPackageName;
            if (androidPackage != null && androidPackage.getStaticSharedLibraryName() != null) {
                z = true;
            }
            packageRemovedInfo.mIsStaticSharedLib = z;
            packageRemovedInfo.mRemovedAppId = packageSetting.getAppId();
            packageRemovedInfo.mRemovedUsers = iArr2;
            packageRemovedInfo.mBroadcastUsers = iArr2;
            packageRemovedInfo.mIsExternal = packageSetting.isExternalStorage();
            packageRemovedInfo.mRemovedPackageVersionCode = packageSetting.getVersionCode();
        }
    }

    public final void deleteInstalledPackageLIF(PackageSetting packageSetting, boolean z, int i, int[] iArr, PackageRemovedInfo packageRemovedInfo, boolean z2) {
        synchronized (this.mPm.mLock) {
            if (packageRemovedInfo != null) {
                packageRemovedInfo.mUid = packageSetting.getAppId();
                PackageManagerService packageManagerService = this.mPm;
                packageRemovedInfo.mBroadcastAllowList = packageManagerService.mAppsFilter.getVisibilityAllowList(packageManagerService.snapshotComputer(), packageSetting, iArr, this.mPm.mSettings.getPackagesLocked());
            }
        }
        this.mRemovePackageHelper.removePackageDataLIF(packageSetting, iArr, packageRemovedInfo, i, z2);
        if (!z || packageRemovedInfo == null) {
            return;
        }
        packageRemovedInfo.mArgs = new InstallArgs(packageSetting.getPathString(), InstructionSets.getAppDexInstructionSets(packageSetting.getPrimaryCpuAbiLegacy(), packageSetting.getSecondaryCpuAbiLegacy()));
    }

    public final void markPackageUninstalledForUserLPw(PackageSetting packageSetting, UserHandle userHandle) {
        int[] userIds;
        if (userHandle == null || userHandle.getIdentifier() == -1) {
            userIds = this.mUserManagerInternal.getUserIds();
        } else {
            userIds = new int[]{userHandle.getIdentifier()};
        }
        for (int i : userIds) {
            packageSetting.setUserState(i, 0L, 0, false, true, true, false, 0, null, false, false, null, null, null, 0, 0, null, null, 0L);
        }
        this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
    }

    public final void deleteInstalledSystemPackage(DeletePackageAction deletePackageAction, int[] iArr, boolean z) {
        int i = deletePackageAction.mFlags;
        PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        if (packageRemovedInfo != null) {
            int[] iArr2 = packageRemovedInfo.mOrigUsers;
        }
        packageSetting.getPkg();
        PackageSetting packageSetting2 = deletePackageAction.mDisabledPs;
        Slog.d("PackageManager", "Deleting system pkg from data partition");
        if (packageRemovedInfo != null) {
            packageRemovedInfo.mIsRemovedPackageSystemUpdate = true;
        }
        int i2 = (packageSetting2.getVersionCode() < packageSetting.getVersionCode() || packageSetting2.getAppId() != packageSetting.getAppId()) ? i & (-2) : i | 1;
        synchronized (this.mPm.mInstallLock) {
            deleteInstalledPackageLIF(packageSetting, true, i2, iArr, packageRemovedInfo, z);
        }
    }

    public void deletePackageVersionedInternal(VersionedPackage versionedPackage, final IPackageDeleteObserver2 iPackageDeleteObserver2, final int i, final int i2, boolean z) {
        int[] iArr;
        final int callingUid = Binder.getCallingUid();
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        final boolean canViewInstantApps = snapshotComputer.canViewInstantApps(callingUid, i);
        Preconditions.checkNotNull(versionedPackage);
        Preconditions.checkNotNull(iPackageDeleteObserver2);
        Preconditions.checkArgumentInRange(versionedPackage.getLongVersionCode(), -1L, Long.MAX_VALUE, "versionCode must be >= -1");
        final String packageName = versionedPackage.getPackageName();
        final long longVersionCode = versionedPackage.getLongVersionCode();
        if (SemSamsungThemeUtils.isThemeCenter(packageName) && i == 0) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeletePackageHelper.lambda$deletePackageVersionedInternal$0(packageName, callingUid, iPackageDeleteObserver2);
                }
            });
            return;
        }
        if (this.mPm.mProtectedPackages.isPackageDataProtected(i, packageName)) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DeletePackageHelper.lambda$deletePackageVersionedInternal$1(packageName, iPackageDeleteObserver2);
                }
            });
            return;
        }
        if (this.mPm.isRequiredSystemPackage(snapshotComputer, packageName, i)) {
            if (!(this.mPm.mSettings.getDisabledSystemPkgLPr(packageName) != null) || (i2 & 4) != 0) {
                this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeletePackageHelper.lambda$deletePackageVersionedInternal$2(packageName, callingUid, iPackageDeleteObserver2);
                    }
                });
                return;
            }
        }
        try {
            if (((ActivityTaskManagerInternal) this.mPm.mInjector.getLocalService(ActivityTaskManagerInternal.class)).isBaseOfLockedTask(packageName)) {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -7, (String) null);
                EventLog.writeEvent(1397638484, "127605586", -1, "");
                return;
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        final String resolveInternalPackageName = snapshotComputer.resolveInternalPackageName(packageName, longVersionCode);
        int callingUid2 = Binder.getCallingUid();
        if (!isOrphaned(snapshotComputer, resolveInternalPackageName) && !z && !isCallerAllowedToSilentlyUninstall(snapshotComputer, callingUid2, resolveInternalPackageName, i)) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DeletePackageHelper.lambda$deletePackageVersionedInternal$3(packageName, iPackageDeleteObserver2);
                }
            });
            return;
        }
        final boolean z2 = (i2 & 2) != 0;
        int[] userIds = z2 ? this.mUserManagerInternal.getUserIds() : new int[]{i};
        if (UserHandle.getUserId(callingUid2) != i || (z2 && userIds.length > 1)) {
            Context context = this.mPm.mContext;
            StringBuilder sb = new StringBuilder();
            iArr = userIds;
            sb.append("deletePackage for user ");
            sb.append(i);
            context.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", sb.toString());
        } else {
            iArr = userIds;
        }
        if (this.mPm.isUserRestricted(i, "no_uninstall_apps")) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    DeletePackageHelper.lambda$deletePackageVersionedInternal$4(iPackageDeleteObserver2, packageName);
                }
            });
            return;
        }
        if (!z2 && snapshotComputer.getBlockUninstallForUser(resolveInternalPackageName, i)) {
            this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    DeletePackageHelper.lambda$deletePackageVersionedInternal$5(iPackageDeleteObserver2, packageName);
                }
            });
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("START DELETE PACKAGE: observer{");
        sb2.append(iPackageDeleteObserver2 != null ? Integer.valueOf(iPackageDeleteObserver2.hashCode()) : "null");
        sb2.append("}\npkg{");
        sb2.append(packageName);
        sb2.append("}, user{");
        sb2.append(i);
        sb2.append("}, caller{");
        sb2.append(callingUid2);
        sb2.append("} flags{");
        sb2.append(i2);
        sb2.append("}");
        PmLog.logDebugInfoAndLogcat(sb2.toString());
        final int[] iArr2 = iArr;
        this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                DeletePackageHelper.this.lambda$deletePackageVersionedInternal$6(resolveInternalPackageName, callingUid, canViewInstantApps, z2, longVersionCode, i, i2, iArr2, packageName, iPackageDeleteObserver2);
            }
        });
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$0(String str, int i, IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            Slog.w("PackageManager", "Attempted to delete " + str + ", callingUid: " + i + ", observer.hashCode(): " + iPackageDeleteObserver2.hashCode());
            iPackageDeleteObserver2.onPackageDeleted(str, -1, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$1(String str, IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            Slog.w("PackageManager", "Attempted to delete protected package: " + str);
            iPackageDeleteObserver2.onPackageDeleted(str, -1, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$2(String str, int i, IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            PmLog.logDebugInfoAndLogcat("Attempted to delete system required package: " + str + " callingUid: " + i);
            iPackageDeleteObserver2.onPackageDeleted(str, -1, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$3(String str, IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
            intent.setData(Uri.fromParts("package", str, null));
            intent.putExtra("android.content.pm.extra.CALLBACK", iPackageDeleteObserver2.asBinder());
            iPackageDeleteObserver2.onUserActionRequired(intent);
        } catch (RemoteException unused) {
        }
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$4(IPackageDeleteObserver2 iPackageDeleteObserver2, String str) {
        try {
            iPackageDeleteObserver2.onPackageDeleted(str, -3, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public static /* synthetic */ void lambda$deletePackageVersionedInternal$5(IPackageDeleteObserver2 iPackageDeleteObserver2, String str) {
        try {
            iPackageDeleteObserver2.onPackageDeleted(str, -4, (String) null);
        } catch (RemoteException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deletePackageVersionedInternal$6(String str, int i, boolean z, boolean z2, long j, int i2, int i3, int[] iArr, String str2, IPackageDeleteObserver2 iPackageDeleteObserver2) {
        int i4;
        int i5;
        int i6;
        String str3;
        UserProperties userProperties;
        int i7;
        int i8;
        String str4;
        Computer snapshotComputer = this.mPm.snapshotComputer();
        PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(UserHandle.getUserId(i)).isInstantApp() || z) {
            String str5 = ", returnCode ";
            if (!z2) {
                String str6 = ", returnCode ";
                i4 = deletePackageX(str, j, i2, i3, false);
                if (i4 == 1 && packageStateInternal != null) {
                    int[] profileIds = this.mUserManagerInternal.getProfileIds(i2, true);
                    int length = profileIds.length;
                    int i9 = i4;
                    int i10 = 0;
                    while (i10 < length) {
                        int i11 = profileIds[i10];
                        if (i11 != i2 && this.mUserManagerInternal.getProfileParentId(i11) == i2 && packageStateInternal.getUserStateOrDefault(i11).isInstalled() && (userProperties = this.mUserManagerInternal.getUserProperties(i11)) != null && userProperties.getDeleteAppWithParent()) {
                            i7 = i10;
                            i8 = length;
                            int deletePackageX = deletePackageX(str, j, i11, i3, false);
                            if (deletePackageX != 1) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Package delete failed for user ");
                                sb.append(i11);
                                str4 = str6;
                                sb.append(str4);
                                sb.append(deletePackageX);
                                Slog.w("PackageManager", sb.toString());
                                i9 = -8;
                                i10 = i7 + 1;
                                str6 = str4;
                                length = i8;
                            }
                        } else {
                            i7 = i10;
                            i8 = length;
                        }
                        str4 = str6;
                        i10 = i7 + 1;
                        str6 = str4;
                        length = i8;
                    }
                    i4 = i9;
                }
            } else {
                int[] blockUninstallForUsers = getBlockUninstallForUsers(snapshotComputer, str, iArr);
                if (ArrayUtils.isEmpty(blockUninstallForUsers)) {
                    i4 = deletePackageX(str, j, i2, i3, false);
                } else {
                    int i12 = i3 & (-3);
                    int length2 = iArr.length;
                    int i13 = 0;
                    while (i13 < length2) {
                        int i14 = iArr[i13];
                        if (ArrayUtils.contains(blockUninstallForUsers, i14)) {
                            i5 = i13;
                            i6 = length2;
                            str3 = str5;
                        } else {
                            i5 = i13;
                            i6 = length2;
                            String str7 = str5;
                            int deletePackageX2 = deletePackageX(str, j, i14, i12, false);
                            if (deletePackageX2 != 1) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Package delete failed for user ");
                                sb2.append(i14);
                                str3 = str7;
                                sb2.append(str3);
                                sb2.append(deletePackageX2);
                                Slog.w("PackageManager", sb2.toString());
                            } else {
                                str3 = str7;
                            }
                        }
                        i13 = i5 + 1;
                        str5 = str3;
                        length2 = i6;
                    }
                    i4 = -4;
                }
            }
        } else {
            i4 = -1;
        }
        PmHook.uninstallLog(str2, i4 >= 0, i2);
        try {
            PmLog.logDebugInfoAndLogcat("result of delete: " + i4 + "{" + iPackageDeleteObserver2.hashCode() + "}");
            iPackageDeleteObserver2.onPackageDeleted(str2, i4, (String) null);
        } catch (RemoteException unused) {
            Log.i("PackageManager", "Observer no longer exists.");
        }
        this.mPm.schedulePruneUnusedStaticSharedLibraries(true);
    }

    public final boolean isOrphaned(Computer computer, String str) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        return packageStateInternal != null && packageStateInternal.getInstallSource().mIsOrphaned;
    }

    public final boolean isCallerAllowedToSilentlyUninstall(final Computer computer, int i, String str, int i2) {
        if (PackageManagerServiceUtils.isRootOrShell(i) || UserHandle.getAppId(i) == 1000) {
            return true;
        }
        final int userId = UserHandle.getUserId(i);
        if (i == computer.getPackageUid(computer.getInstallerPackageName(str, i2), 0L, userId)) {
            return true;
        }
        for (String str2 : this.mPm.mRequiredVerifierPackages) {
            if (i == computer.getPackageUid(str2, 0L, userId)) {
                return true;
            }
        }
        String str3 = this.mPm.mRequiredUninstallerPackage;
        if (str3 != null && i == computer.getPackageUid(str3, 0L, userId)) {
            return true;
        }
        String str4 = this.mPm.mStorageManagerPackage;
        return (str4 != null && i == computer.getPackageUid(str4, 0L, userId)) || this.mSilentUninstallerList.isCallerAllowedSilentlyInstall(i, new Function() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer lambda$isCallerAllowedToSilentlyUninstall$7;
                lambda$isCallerAllowedToSilentlyUninstall$7 = DeletePackageHelper.lambda$isCallerAllowedToSilentlyUninstall$7(Computer.this, userId, (String) obj);
                return lambda$isCallerAllowedToSilentlyUninstall$7;
            }
        }) || computer.checkUidPermission("android.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS", i) == 0;
    }

    public static /* synthetic */ Integer lambda$isCallerAllowedToSilentlyUninstall$7(Computer computer, int i, String str) {
        return Integer.valueOf(computer.getPackageUid(str, 0L, i));
    }

    public final int[] getBlockUninstallForUsers(Computer computer, String str, int[] iArr) {
        int[] iArr2 = PackageManagerService.EMPTY_INT_ARRAY;
        for (int i : iArr) {
            if (computer.getBlockUninstallForUser(str, i)) {
                iArr2 = ArrayUtils.appendInt(iArr2, i);
            }
        }
        return iArr2;
    }

    public class TempUserState {
        public final int enabledState;
        public final boolean installed;
        public final String lastDisableAppCaller;

        public TempUserState(int i, String str, boolean z) {
            this.enabledState = i;
            this.lastDisableAppCaller = str;
            this.installed = z;
        }
    }

    public void removeUnusedPackagesLPw(UserManagerService userManagerService, final int i) {
        int[] userIds = userManagerService.getUserIds();
        int size = this.mPm.mSettings.getPackagesLocked().size();
        for (int i2 = 0; i2 < size; i2++) {
            PackageSetting packageSetting = (PackageSetting) this.mPm.mSettings.getPackagesLocked().valueAt(i2);
            if (packageSetting.getPkg() != null) {
                final String packageName = packageSetting.getPkg().getPackageName();
                boolean z = true;
                if ((packageSetting.getFlags() & 1) == 0 && TextUtils.isEmpty(packageSetting.getPkg().getStaticSharedLibraryName()) && TextUtils.isEmpty(packageSetting.getPkg().getSdkLibraryName())) {
                    boolean shouldKeepUninstalledPackageLPr = this.mPm.shouldKeepUninstalledPackageLPr(packageName);
                    if (!shouldKeepUninstalledPackageLPr) {
                        for (int i3 : userIds) {
                            if (i3 != i && packageSetting.getInstalled(i3)) {
                                break;
                            }
                        }
                    }
                    z = shouldKeepUninstalledPackageLPr;
                    if (!z) {
                        this.mPm.mHandler.post(new Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeletePackageHelper.this.lambda$removeUnusedPackagesLPw$8(packageName, i);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeUnusedPackagesLPw$8(String str, int i) {
        deletePackageX(str, -1L, i, 0, true);
    }

    public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        int length;
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        Preconditions.checkNotNull(versionedPackage);
        Preconditions.checkNotNull(iPackageDeleteObserver2);
        String packageName = versionedPackage.getPackageName();
        long longVersionCode = versionedPackage.getLongVersionCode();
        synchronized (this.mPm.mLock) {
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(this.mPm.snapshotComputer().resolveInternalPackageName(packageName, longVersionCode));
            length = packageLPr != null ? packageLPr.queryInstalledUsers(this.mUserManagerInternal.getUserIds(), true).length : 0;
        }
        if (length > 1) {
            deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, 0, true);
        } else {
            try {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -1, (String) null);
            } catch (RemoteException unused) {
            }
        }
    }
}
