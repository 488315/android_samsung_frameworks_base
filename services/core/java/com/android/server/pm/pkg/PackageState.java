package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.SigningInfo;
import android.os.UserHandle;
import android.util.SparseArray;

import java.io.File;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface PackageState {
    AndroidPackage getAndroidPackage();

    String getApexModuleName();

    int getAppId();

    int getCategoryOverride();

    String getCpuAbiOverride();

    int getHiddenApiEnforcementPolicy();

    long getLastModifiedTime();

    long[] getLastPackageUsageTime();

    long getLastUpdateTime();

    Map getMimeGroups();

    String getPackageName();

    File getPath();

    String getPrimaryCpuAbi();

    byte[] getRestrictUpdateHash();

    String getSeInfo();

    String getSecondaryCpuAbi();

    List getSharedLibraryDependencies();

    int getSharedUserAppId();

    SigningInfo getSigningInfo();

    PackageUserState getStateForUser(UserHandle userHandle);

    int getTargetSdkVersion();

    default PackageUserState getUserStateOrDefault(int i) {
        PackageUserState packageUserState = (PackageUserState) getUserStates().get(i);
        return packageUserState == null ? PackageUserState.DEFAULT : packageUserState;
    }

    SparseArray getUserStates();

    List getUsesLibraryFiles();

    String[] getUsesSdkLibraries();

    boolean[] getUsesSdkLibrariesOptional();

    long[] getUsesSdkLibrariesVersionsMajor();

    String[] getUsesStaticLibraries();

    long[] getUsesStaticLibrariesVersions();

    long getVersionCode();

    String getVolumeUuid();

    boolean hasSharedUser();

    boolean isApex();

    boolean isApkInUpdatedApex();

    boolean isDebuggable();

    boolean isDefaultToDeviceProtectedStorage();

    boolean isExternalStorage();

    boolean isForceQueryableOverride();

    boolean isHiddenUntilInstalled();

    boolean isInstallPermissionsFixed();

    boolean isOdm();

    boolean isOem();

    boolean isPendingRestore();

    boolean isPersistent();

    boolean isPrivileged();

    boolean isProduct();

    boolean isRequiredForSystemUser();

    boolean isScannedAsStoppedSystemApp();

    boolean isSystem();

    boolean isSystemExt();

    boolean isUpdateAvailable();

    boolean isUpdatedSystemApp();

    boolean isVendor();
}
