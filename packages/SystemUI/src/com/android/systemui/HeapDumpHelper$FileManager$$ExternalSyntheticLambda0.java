package com.android.systemui;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HeapDumpHelper$FileManager$$ExternalSyntheticLambda0 implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.startsWith("heap-systemui");
    }
}
