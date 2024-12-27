package com.android.server;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BootReceiver$$ExternalSyntheticLambda8 implements FilenameFilter {
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
