package com.android.server.people.data;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class EventHistoryImpl$$ExternalSyntheticLambda1
        implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return "events".equals(str) || "indexes".equals(str);
    }
}
