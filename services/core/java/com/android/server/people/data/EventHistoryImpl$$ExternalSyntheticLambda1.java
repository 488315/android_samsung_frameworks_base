package com.android.server.people.data;

import java.io.File;
import java.io.FilenameFilter;

public final /* synthetic */ class EventHistoryImpl$$ExternalSyntheticLambda1
        implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return "events".equals(str) || "indexes".equals(str);
    }
}
