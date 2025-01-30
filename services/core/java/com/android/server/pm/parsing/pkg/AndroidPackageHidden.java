package com.android.server.pm.parsing.pkg;

import android.content.pm.ApplicationInfo;

/* loaded from: classes3.dex */
public interface AndroidPackageHidden {
    String getPrimaryCpuAbi();

    String getSecondaryCpuAbi();

    boolean isOdm();

    boolean isOem();

    boolean isPrivileged();

    boolean isProduct();

    boolean isSystem();

    boolean isSystemExt();

    boolean isVendor();

    ApplicationInfo toAppInfoWithoutState();
}
