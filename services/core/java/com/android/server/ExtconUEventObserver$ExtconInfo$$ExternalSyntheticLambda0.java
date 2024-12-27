package com.android.server;

import java.io.File;
import java.io.FilenameFilter;

public final /* synthetic */ class ExtconUEventObserver$ExtconInfo$$ExternalSyntheticLambda0
        implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.startsWith("cable.");
    }
}
