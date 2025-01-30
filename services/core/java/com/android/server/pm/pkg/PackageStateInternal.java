package com.android.server.pm.pkg;

import android.content.pm.SigningDetails;
import android.util.SparseArray;
import com.android.server.pm.InstallSource;
import com.android.server.pm.PackageKeySetData;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.permission.LegacyPermissionState;
import java.util.UUID;

/* loaded from: classes3.dex */
public interface PackageStateInternal extends PackageState {
  String getAppMetadataFilePath();

  UUID getDomainSetId();

  int getFlags();

  InstallSource getInstallSource();

  PackageKeySetData getKeySetData();

  LegacyPermissionState getLegacyPermissionState();

  long getLoadingCompletedTime();

  float getLoadingProgress();

  String getPathString();

  AndroidPackageInternal getPkg();

  String getPrimaryCpuAbiLegacy();

  int getPrivateFlags();

  String getRealName();

  String getSecondaryCpuAbiLegacy();

  SigningDetails getSigningDetails();

  PackageStateUnserialized getTransientState();

  @Override // com.android.server.pm.pkg.PackageState
  SparseArray getUserStates();

  boolean isLoading();

  @Override // com.android.server.pm.pkg.PackageState
  default PackageUserStateInternal getUserStateOrDefault(int i) {
    PackageUserStateInternal packageUserStateInternal =
        (PackageUserStateInternal) getUserStates().get(i);
    return packageUserStateInternal == null
        ? PackageUserStateInternal.DEFAULT
        : packageUserStateInternal;
  }
}
