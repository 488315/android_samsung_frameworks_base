package com.android.server;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ExtconUEventObserver$ExtconInfo$$ExternalSyntheticLambda0
        implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return str.startsWith("cable.");
    }
}
