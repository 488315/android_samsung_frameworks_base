package com.android.internal.app.procstats;

import android.util.SparseArray;

public abstract class ProcessStatsInternal {
    public abstract SparseArray<long[]> getUidProcStateStatsOverTime(long j);
}
