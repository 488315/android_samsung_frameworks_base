package com.android.server.pm;

import java.io.File;
import java.io.FileFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutDumpFiles$$ExternalSyntheticLambda1
        implements FileFilter {
    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return file.isFile();
    }
}
