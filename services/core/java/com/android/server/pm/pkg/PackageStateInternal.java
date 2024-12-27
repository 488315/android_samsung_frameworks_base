package com.android.server.pm.pkg;

import android.content.pm.SigningDetails;
import android.util.SparseArray;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.InstallSource;
import com.android.server.pm.PackageKeySetData;
import com.android.server.pm.permission.LegacyPermissionState;
import java.util.Set;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
        PackageUserStateInternal packageUserStateInternal = (PackageUserStateInternal) getUserStates().get(i);
        return packageUserStateInternal == null ? PackageUserStateInternal.DEFAULT : packageUserStateInternal;
    }

    @Override // com.android.server.pm.pkg.PackageState
    SparseArray getUserStates();

    boolean isLoading();
}
