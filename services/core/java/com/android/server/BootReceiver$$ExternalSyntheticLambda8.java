package com.android.server;

import java.io.File;
import java.io.FilenameFilter;

public final /* synthetic */ class BootReceiver$$ExternalSyntheticLambda8
        implements FilenameFilter {
    public final /* synthetic */ int $r8$classId;

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        switch (this.$r8$classId) {
            case 0:
                int i = BootReceiver.LOG_SIZE;
                return str.startsWith("anr_");
            default:
                int i2 = BootReceiver.LOG_SIZE;
                return str.endsWith("zip") || str.endsWith("gz");
        }
    }
}
