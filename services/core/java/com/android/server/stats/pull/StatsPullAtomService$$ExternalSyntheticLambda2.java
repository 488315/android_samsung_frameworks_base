package com.android.server.stats.pull;

import android.net.NetworkStats;
import android.view.Display;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda2
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NetworkStats.Entry entry = (NetworkStats.Entry) obj;
                int i = StatsPullAtomService.RANDOM_SEED;
                return new NetworkStats.Entry(
                        (String) null,
                        entry.getUid(),
                        -1,
                        0,
                        -1,
                        -1,
                        -1,
                        entry.getRxBytes(),
                        entry.getRxPackets(),
                        entry.getTxBytes(),
                        entry.getTxPackets(),
                        0L);
            case 1:
                NetworkStats.Entry entry2 = (NetworkStats.Entry) obj;
                int i2 = StatsPullAtomService.RANDOM_SEED;
                return new NetworkStats.Entry(
                        (String) null,
                        entry2.getUid(),
                        -1,
                        entry2.getTag(),
                        entry2.getMetered(),
                        -1,
                        -1,
                        entry2.getRxBytes(),
                        entry2.getRxPackets(),
                        entry2.getTxBytes(),
                        entry2.getTxPackets(),
                        0L);
            case 2:
                NetworkStats.Entry entry3 = (NetworkStats.Entry) obj;
                int i3 = StatsPullAtomService.RANDOM_SEED;
                return new NetworkStats.Entry(
                        (String) null,
                        -1,
                        entry3.getSet(),
                        0,
                        -1,
                        -1,
                        -1,
                        entry3.getRxBytes(),
                        entry3.getRxPackets(),
                        entry3.getTxBytes(),
                        entry3.getTxPackets(),
                        0L);
            case 3:
                NetworkStats.Entry entry4 = (NetworkStats.Entry) obj;
                int i4 = StatsPullAtomService.RANDOM_SEED;
                return new NetworkStats.Entry(
                        (String) null,
                        entry4.getUid(),
                        entry4.getSet(),
                        0,
                        -1,
                        -1,
                        -1,
                        entry4.getRxBytes(),
                        entry4.getRxPackets(),
                        entry4.getTxBytes(),
                        entry4.getTxPackets(),
                        0L);
            default:
                return ((Display.Mode) obj).getSupportedHdrTypes();
        }
    }
}
