package com.android.server.pm.permission;

import android.permission.PermissionManagerInternal;
import android.util.ArrayMap;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public interface PermissionManagerServiceInternal
    extends PermissionManagerInternal, LegacyPermissionDataProvider {

  public interface HotwordDetectionServiceProvider {
    int getUid();
  }

  public interface OnRuntimePermissionStateChangedListener {
    void onRuntimePermissionStateChanged(String str, int i);
  }

  void addOnRuntimePermissionStateChangedListener(
      OnRuntimePermissionStateChangedListener onRuntimePermissionStateChangedListener);

  boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2);

  boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2);

  int checkPermission(String str, String str2, int i);

  int checkPostNotificationsPermissionGrantedOrLegacyAccess(int i);

  int checkUidPermission(int i, String str);

  List getAllPermissionsWithProtection(int i);

  List getAllPermissionsWithProtectionFlags(int i);

  String[] getAppOpPermissionPackages(String str);

  List getDelegatedShellPermissions();

  Set getGrantedPermissions(String str, int i);

  HotwordDetectionServiceProvider getHotwordDetectionServiceProvider();

  Set getInstalledPermissions(String str);

  List getPackageGrantedPermissionsForMDM(String str);

  int[] getPermissionGids(String str, int i);

  Permission getPermissionTEMP(String str);

  List getRequestedRuntimePermissionsForMDM(String str);

  void grantInstallPermission(String str, String str2);

  boolean isPermissionsReviewRequired(String str, int i);

  void onPackageAdded(PackageState packageState, boolean z, AndroidPackage androidPackage);

  void onPackageInstalled(
      AndroidPackage androidPackage, int i, PackageInstalledParams packageInstalledParams, int i2);

  void onPackageRemoved(AndroidPackage androidPackage);

  void onPackageUninstalled(
      String str,
      int i,
      PackageState packageState,
      AndroidPackage androidPackage,
      List list,
      int i2);

  void onStorageVolumeMounted(String str, boolean z);

  void onSystemReady();

  void onUserCreated(int i);

  void onUserRemoved(int i);

  void readLegacyPermissionStateTEMP();

  void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);

  void removePackageGrantedPermissionsForMDM(String str);

  void resetRuntimePermissions(AndroidPackage androidPackage, int i);

  void resetRuntimePermissionsForUser(int i);

  void revokeInstallPermission(String str, String str2);

  void setHotwordDetectionServiceProvider(
      HotwordDetectionServiceProvider hotwordDetectionServiceProvider);

  int setLicensePermissionsForMDM(String str, Set set);

  void startShellPermissionIdentityDelegation(int i, String str, List list);

  void stopShellPermissionIdentityDelegation();

  void writeLegacyPermissionStateTEMP();

  void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings);

  public final class PackageInstalledParams {
    public static final PackageInstalledParams DEFAULT = new Builder().build();
    public final List mAllowlistedRestrictedPermissions;
    public final int mAutoRevokePermissionsMode;
    public final ArrayMap mPermissionStates;

    public PackageInstalledParams(ArrayMap arrayMap, List list, int i) {
      this.mPermissionStates = arrayMap;
      this.mAllowlistedRestrictedPermissions = list;
      this.mAutoRevokePermissionsMode = i;
    }

    public ArrayMap getPermissionStates() {
      return this.mPermissionStates;
    }

    public List getAllowlistedRestrictedPermissions() {
      return this.mAllowlistedRestrictedPermissions;
    }

    public int getAutoRevokePermissionsMode() {
      return this.mAutoRevokePermissionsMode;
    }

    public final class Builder {
      public ArrayMap mPermissionStates = null;
      public List mAllowlistedRestrictedPermissions = Collections.emptyList();
      public int mAutoRevokePermissionsMode = 3;

      public Builder setPermissionStates(ArrayMap arrayMap) {
        Objects.requireNonNull(arrayMap);
        this.mPermissionStates = arrayMap;
        return this;
      }

      public void setAllowlistedRestrictedPermissions(List list) {
        Objects.requireNonNull(list);
        this.mAllowlistedRestrictedPermissions = new ArrayList(list);
      }

      public void setAutoRevokePermissionsMode(int i) {
        this.mAutoRevokePermissionsMode = i;
      }

      public PackageInstalledParams build() {
        ArrayMap arrayMap = this.mPermissionStates;
        if (arrayMap == null) {
          arrayMap = new ArrayMap();
        }
        return new PackageInstalledParams(
            arrayMap, this.mAllowlistedRestrictedPermissions, this.mAutoRevokePermissionsMode);
      }
    }
  }
}
