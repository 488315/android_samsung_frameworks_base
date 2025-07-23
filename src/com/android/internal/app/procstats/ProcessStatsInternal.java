package com.android.internal.app.procstats;

import android.util.SparseArray;

/* loaded from: classes5.dex */
public abstract class ProcessStatsInternal {
    public abstract SparseArray<long[]> getUidProcStateStatsOverTime(long j);
}
