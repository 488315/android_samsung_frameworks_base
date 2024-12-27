package com.android.server.backup.transport;

import java.util.function.BinaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TransportStats$$ExternalSyntheticLambda0
        implements BinaryOperator {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        TransportStats.Stats stats = (TransportStats.Stats) obj;
        TransportStats.Stats stats2 = (TransportStats.Stats) obj2;
        int i = stats.n;
        int i2 = stats2.n;
        int i3 = i + i2;
        double d = ((stats2.average * i2) + (stats.average * i)) / i3;
        long max = Math.max(stats.max, stats2.max);
        long min = Math.min(stats.min, stats2.min);
        TransportStats.Stats stats3 = new TransportStats.Stats();
        stats3.n = i3;
        stats3.average = d;
        stats3.max = max;
        stats3.min = min;
        return stats3;
    }
}
