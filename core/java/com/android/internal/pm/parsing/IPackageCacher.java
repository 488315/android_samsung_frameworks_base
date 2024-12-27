package com.android.internal.pm.parsing;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

import java.io.File;

public interface IPackageCacher {
    void cacheResult(File file, int i, ParsedPackage parsedPackage);

    ParsedPackage getCachedResult(File file, int i);
}
