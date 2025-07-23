package com.android.internal.pm.parsing.pkg;

import android.content.pm.ApplicationInfo;

/* loaded from: classes5.dex */
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
