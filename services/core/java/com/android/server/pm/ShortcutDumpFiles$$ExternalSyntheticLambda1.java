package com.android.server.pm;

import java.io.File;
import java.io.FileFilter;

public final /* synthetic */ class ShortcutDumpFiles$$ExternalSyntheticLambda1
        implements FileFilter {
    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return file.isFile();
    }
}
