package com.android.internal.app.procstats;

import android.util.StatsEvent;
import com.android.internal.util.FrameworkStatsLog;
import java.util.List;

/* loaded from: classes5.dex */
public class StatsEventOutput {
    List<StatsEvent> mOutput;

    public StatsEventOutput(List<StatsEvent> list) {
        this.mOutput = list;
    }

    public void write(int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        this.mOutput.add(FrameworkStatsLog.buildStatsEvent(i, i2, str, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13));
    }

    public void write(int i, int i2, String str, int i3, String str2, int i4, int i5, int i6, int i7, int i8, String str3) {
        this.mOutput.add(FrameworkStatsLog.buildStatsEvent(i, i2, str, i3, str2, i4, i5, i6, i7, i8, str3));
    }
}
