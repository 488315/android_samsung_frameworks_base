package com.android.server.stats.pull;

import com.android.internal.os.KernelCpuUidTimeReader;
import com.android.internal.util.FrameworkStatsLog;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda13
        implements KernelCpuUidTimeReader.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StatsPullAtomService$$ExternalSyntheticLambda13(
            int i, int i2, List list) {
        this.$r8$classId = i2;
        this.f$0 = list;
        this.f$1 = i;
    }

    public final void onUidCpuTime(int i, Object obj) {
        switch (this.$r8$classId) {
            case 0:
                List list = this.f$0;
                int i2 = this.f$1;
                int i3 = StatsPullAtomService.RANDOM_SEED;
                list.add(FrameworkStatsLog.buildStatsEvent(i2, i, ((Long) obj).longValue()));
                break;
            case 1:
                List list2 = this.f$0;
                int i4 = this.f$1;
                long[] jArr = (long[]) obj;
                int i5 = StatsPullAtomService.RANDOM_SEED;
                list2.add(FrameworkStatsLog.buildStatsEvent(i4, i, jArr[0], jArr[1]));
                break;
            default:
                List list3 = this.f$0;
                int i6 = this.f$1;
                long[] jArr2 = (long[]) obj;
                int i7 = StatsPullAtomService.RANDOM_SEED;
                for (int i8 = 0; i8 < jArr2.length; i8++) {
                    list3.add(FrameworkStatsLog.buildStatsEvent(i6, i, i8, jArr2[i8]));
                }
                break;
        }
    }
}
