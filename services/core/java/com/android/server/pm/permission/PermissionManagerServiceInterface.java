package com.android.server.pm.permission;

import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.permission.IOnPermissionsChangeListener;
import android.permission.PermissionManagerInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public interface PermissionManagerServiceInterface extends PermissionManagerInternal {
    boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2);

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener);

    void addOnRuntimePermissionStateChangedListener(PermissionManagerServiceInternal.OnRuntimePermissionStateChangedListener onRuntimePermissionStateChangedListener);

    boolean addPermission(PermissionInfo permissionInfo, boolean z);

    boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2);

    boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2);

    int checkPermission(String str, String str2, int i);

    int checkUidPermission(int i, String str);

    Map getAllAppOpPermissionPackages();

    List getAllPermissionGroups(int i);

    List getAllPermissionsWithProtection(int i);

    List getAllPermissionsWithProtectionFlags(int i);

    List getAllowlistedRestrictedPermissions(String str, int i, int i2);

    String[] getAppOpPermissionPackages(String str);

    int[] getGidsForUid(int i);

    Set getGrantedPermissions(String str, int i);

    Set getInstalledPermissions(String str);

    LegacyPermissionState getLegacyPermissionState(int i);

    List getLegacyPermissions();

    List getPackageGrantedPermissionsForMDM(String str);

    int getPermissionFlags(String str, String str2, int i);

    int[] getPermissionGids(String str, int i);

    PermissionGroupInfo getPermissionGroupInfo(String str, int i);

    PermissionInfo getPermissionInfo(String str, int i, String str2);

    Permission getPermissionTEMP(String str);

    List getRequestedRuntimePermissionsForMDM(String str);

    List getSplitPermissions();

    void grantInstallPermission(String str, String str2);

    void grantRuntimePermission(String str, String str2, int i);

    boolean isPermissionRevokedByPolicy(String str, String str2, int i);

    boolean isPermissionsReviewRequired(String str, int i);

    void onPackageAdded(PackageState packageState, boolean z, AndroidPackage androidPackage);

    void onPackageInstalled(AndroidPackage androidPackage, int i, PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2);

    void onPackageRemoved(AndroidPackage androidPackage);

    void onPackageUninstalled(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int i2);

    void onStorageVolumeMounted(String str, boolean z);

    void onSystemReady();

    void onUserCreated(int i);

    void onUserRemoved(int i);

    List queryPermissionsByGroup(String str, int i);

    void readLegacyPermissionStateTEMP();

    void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);

    boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2);

    void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener);

    void removePackageGrantedPermissionsForMDM(String str);

    void removePermission(String str);

    void resetRuntimePermissions(AndroidPackage androidPackage, int i);

    void resetRuntimePermissionsForUser(int i);

    void revokeInstallPermission(String str, String str2);

    void revokePostNotificationPermissionWithoutKillForTest(String str, int i);

    void revokeRuntimePermission(String str, String str2, int i, String str3);

    int setLicensePermissionsForMDM(String str, Set set);

    boolean shouldShowRequestPermissionRationale(String str, String str2, int i);

    void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, int i3);

    void updatePermissionFlagsForAllApps(int i, int i2, int i3);

    void writeLegacyPermissionStateTEMP();

    void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);
}
