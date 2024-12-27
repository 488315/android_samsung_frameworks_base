package com.android.server.backup.transport;

import android.content.ComponentName;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TransportStats {
    public final Object mStatsLock = new Object();
    public final Map mTransportStats = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Stats {
        public double average;
        public long max;
        public long min;
        public int n;
    }

    public static void dumpStats(PrintWriter printWriter, String str, Stats stats) {
        printWriter.println(
                String.format(
                        Locale.US,
                        "%sAverage connection time: %.2f ms",
                        str,
                        Double.valueOf(stats.average)));
        printWriter.println(str + "Max connection time: " + stats.max + " ms");
        printWriter.println(str + "Min connection time: " + stats.min + " ms");
        printWriter.println(str + "Number of connections: " + stats.n + " ");
    }

    public final void registerConnectionTime(ComponentName componentName, long j) {
        synchronized (this.mStatsLock) {
            try {
                Stats stats = (Stats) ((HashMap) this.mTransportStats).get(componentName);
                if (stats == null) {
                    stats = new Stats();
                    stats.n = 0;
                    stats.average = 0.0d;
                    stats.max = 0L;
                    stats.min = Long.MAX_VALUE;
                    ((HashMap) this.mTransportStats).put(componentName, stats);
                }
                double d = stats.average;
                int i = stats.n;
                double d2 = (d * i) + j;
                int i2 = i + 1;
                stats.average = d2 / i2;
                stats.n = i2;
                stats.max = Math.max(stats.max, j);
                stats.min = Math.min(stats.min, j);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
