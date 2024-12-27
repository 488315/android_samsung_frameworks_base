package com.android.server.location.nsflp;

import java.io.File;
import java.io.FileFilter;

public final /* synthetic */ class NSUtLogger$$ExternalSyntheticLambda0 implements FileFilter {
    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return !file.getName().equals("crash_history.txt");
    }
}
