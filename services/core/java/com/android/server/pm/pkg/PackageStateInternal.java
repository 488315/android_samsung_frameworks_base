package com.android.server.pm.pkg;

import android.content.pm.SigningDetails;
import android.util.SparseArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.InstallSource;
import com.android.server.pm.PackageKeySetData;
import com.android.server.pm.permission.LegacyPermissionState;

import java.util.Set;
import java.util.UUID;

public interface PackageStateInternal extends PackageState {
    String getAppMetadataFilePath();

    int getAppMetadataSource();

    UUID getDomainSetId();

    int getFlags();

    InstallSource getInstallSource();

    PackageKeySetData getKeySetData();

    LegacyPermissionState getLegacyPermissionState();

    long getLoadingCompletedTime();

    float getLoadingProgress();

    Set getOldPaths();

    String getPathString();

    AndroidPackageInternal getPkg();

    @Deprecated
    String getPrimaryCpuAbiLegacy();

    int getPrivateFlags();

    String getRealName();

    String getSecondaryCpuAbiLegacy();

    SigningDetails getSigningDetails();

    PackageStateUnserialized getTransientState();

    @Override // com.android.server.pm.pkg.PackageState
    default PackageUserStateInternal getUserStateOrDefault(int i) {
        PackageUserStateInternal packageUserStateInternal =
                (PackageUserStateInternal) getUserStates().get(i);
        return packageUserStateInternal == null
                ? PackageUserStateInternal.DEFAULT
                : packageUserStateInternal;
    }

    @Override // com.android.server.pm.pkg.PackageState
    SparseArray getUserStates();

    boolean isLoading();
}
