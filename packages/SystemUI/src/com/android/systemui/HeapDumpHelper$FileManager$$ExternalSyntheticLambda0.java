package com.android.systemui;

import java.io.File;
import java.io.FilenameFilter;

public final /* synthetic */ class HeapDumpHelper$FileManager$$ExternalSyntheticLambda0 implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.startsWith("heap-systemui");
    }
}
