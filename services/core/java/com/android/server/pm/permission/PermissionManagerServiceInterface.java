package com.android.server.pm.permission;

import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.permission.IOnPermissionsChangeListener;
import android.permission.PermissionManagerInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface PermissionManagerServiceInterface extends PermissionManagerInternal {
    boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2);

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener);

    boolean addPermission(PermissionInfo permissionInfo, boolean z);

    boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2);

    boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2);

    int checkPermission(String str, String str2, String str3, int i);

    int checkUidPermission(int i, String str, String str2);

    void dump(PrintWriter printWriter, String[] strArr);

    Map getAllAppOpPermissionPackages();

    List getAllPermissionGroups(int i);

    Map getAllPermissionStates(String str, String str2, int i);

    List getAllPermissionsWithProtection();

    List getAllPermissionsWithProtectionFlags();

    List getAllowlistedRestrictedPermissions(String str, int i, int i2);

    String[] getAppOpPermissionPackages(String str);

    String getDefaultPermissionGrantFingerprint(int i);

    int[] getGidsForUid(int i);

    Set getGrantedPermissions(int i, String str);

    Set getInstalledPermissions(String str);

    LegacyPermissionState getLegacyPermissionState(int i);

    List getLegacyPermissions();

    List getPackageGrantedPermissionsForMDM(String str);

    int getPermissionFlags(String str, String str2, String str3, int i);

    int[] getPermissionGids(int i, String str);

    PermissionGroupInfo getPermissionGroupInfo(String str, int i);

    PermissionInfo getPermissionInfo(int i, String str, String str2);

    Permission getPermissionTEMP(String str);

    List getRequestedRuntimePermissionsForMDM(String str);

    List getSplitPermissions();

    void grantRuntimePermission(String str, String str2, String str3, int i);

    boolean isPermissionRevokedByPolicy(int i, String str, String str2, String str3);

    boolean isPermissionsReviewRequired(int i, String str);

    void onPackageAdded(PackageState packageState, boolean z, AndroidPackage androidPackage);

    void onPackageInstalled(AndroidPackage androidPackage, PermissionManagerServiceInternal$PackageInstalledParams permissionManagerServiceInternal$PackageInstalledParams, int i);

    void onPackageRemoved(AndroidPackage androidPackage);

    void onPackageUninstalled(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int i2);

    void onStorageVolumeMounted(String str, boolean z);

    void onSystemReady();

    void onUserCreated(int i);

    void onUserRemoved(int i);

    List queryPermissionsByGroup(int i, String str);

    void readLegacyPermissionStateTEMP();

    void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);

    boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2);

    void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener);

    void removePackageGrantedPermissionsForMDM(String str);

    void removePermission(String str);

    void resetRuntimePermissions(AndroidPackage androidPackage, int i);

    void resetRuntimePermissionsForUser(int i);

    void revokePostNotificationPermissionWithoutKillForTest(String str, int i);

    void revokeRuntimePermission(String str, String str2, String str3, int i, String str4);

    void setDefaultPermissionGrantFingerprint(int i, String str);

    int setLicensePermissionsForMDM(String str, Set set);

    boolean shouldShowRequestPermissionRationale(int i, String str, String str2, String str3);

    void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3);

    void updatePermissionFlagsForAllApps(int i, int i2, int i3);

    void writeLegacyPermissionStateTEMP();

    void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);
}
