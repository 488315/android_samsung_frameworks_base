package com.android.server.am;

import com.android.internal.os.ProcessCpuTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppProfiler$$ExternalSyntheticLambda4
        implements ProcessCpuTracker.FilterStats {
    public final /* synthetic */ int $r8$classId;

    public final boolean needed(ProcessCpuTracker.Stats stats) {
        switch (this.$r8$classId) {
            case 0:
                if (stats.vsize <= 0 || stats.uid >= 10000) {}
                break;
            default:
                if (stats.vsize <= 0 || stats.uid >= 10000) {}
                break;
        }
        return false;
    }
}
