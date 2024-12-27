package com.android.internal.pm.pkg.parsing;

import android.content.pm.ApplicationInfo;

public interface ParsingPackageHidden {
    int getVersionCode();

    int getVersionCodeMajor();

    ApplicationInfo toAppInfoWithoutState();
}
