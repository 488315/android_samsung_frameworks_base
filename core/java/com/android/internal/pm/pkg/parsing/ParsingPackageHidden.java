package com.android.internal.pm.pkg.parsing;

import android.content.pm.ApplicationInfo;

/* loaded from: classes5.dex */
public interface ParsingPackageHidden {
    int getVersionCode();

    int getVersionCodeMajor();

    ApplicationInfo toAppInfoWithoutState();
}
