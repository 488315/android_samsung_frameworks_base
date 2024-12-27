package com.samsung.android.server.pm.install;

import java.io.File;
import java.io.FilenameFilter;

public final /* synthetic */ class PrePackageInstallerBase$$ExternalSyntheticLambda5
        implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.endsWith(".apk") || str.endsWith(".apk.gz");
    }
}
