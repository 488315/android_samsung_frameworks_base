package com.android.systemui;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class HeapDumpHelper$FileManager$$ExternalSyntheticLambda0 implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.startsWith("heap-systemui");
    }
}
