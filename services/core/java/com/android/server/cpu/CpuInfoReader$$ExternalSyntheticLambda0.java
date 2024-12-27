package com.android.server.cpu;

import java.io.File;
import java.io.FileFilter;

public final /* synthetic */ class CpuInfoReader$$ExternalSyntheticLambda0 implements FileFilter {
    public final /* synthetic */ int $r8$classId;

    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        switch (this.$r8$classId) {
            case 0:
                return file.isDirectory() && file.getName().startsWith("policy");
            case 1:
                return file.isDirectory() && file.getName().startsWith("policy");
            default:
                return file.isDirectory();
        }
    }
}
