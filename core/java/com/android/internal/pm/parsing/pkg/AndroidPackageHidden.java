package com.android.internal.pm.parsing.pkg;

import android.content.pm.ApplicationInfo;

public interface AndroidPackageHidden {
    String getPrimaryCpuAbi();

    String getSecondaryCpuAbi();

    @Deprecated
    int getVersionCode();

    int getVersionCodeMajor();

    boolean isOdm();

    boolean isOem();

    boolean isPrivileged();

    boolean isProduct();

    boolean isSystem();

    boolean isSystemExt();

    boolean isVendor();

    ApplicationInfo toAppInfoWithoutState();
}
