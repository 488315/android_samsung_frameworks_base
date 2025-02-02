package com.android.server.backup.transport;

import android.content.ComponentName;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

/* loaded from: classes.dex */
public class TransportStats {
  public final Object mStatsLock = new Object();
  public final Map mTransportStats = new HashMap();

  public void registerConnectionTime(ComponentName componentName, long j) {
    synchronized (this.mStatsLock) {
      Stats stats = (Stats) this.mTransportStats.get(componentName);
      if (stats == null) {
        stats = new Stats();
        this.mTransportStats.put(componentName, stats);
      }
      stats.register(j);
    }
  }

  public void dump(PrintWriter printWriter) {
    synchronized (this.mStatsLock) {
      Optional reduce =
          this.mTransportStats.values().stream()
              .reduce(
                  new BinaryOperator() { // from class:
                                         // com.android.server.backup.transport.TransportStats$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                      return TransportStats.Stats.merge(
                          (TransportStats.Stats) obj, (TransportStats.Stats) obj2);
                    }
                  });
      if (reduce.isPresent()) {
        dumpStats(printWriter, "", (Stats) reduce.get());
      }
      if (!this.mTransportStats.isEmpty()) {
        printWriter.println("Per transport:");
        for (ComponentName componentName : this.mTransportStats.keySet()) {
          Stats stats = (Stats) this.mTransportStats.get(componentName);
          printWriter.println("    " + componentName.flattenToShortString());
          dumpStats(printWriter, "        ", stats);
        }
      }
    }
  }

  public static void dumpStats(PrintWriter printWriter, String str, Stats stats) {
    Locale locale = Locale.US;
    printWriter.println(
        String.format(
            locale, "%sAverage connection time: %.2f ms", str, Double.valueOf(stats.average)));
    printWriter.println(
        String.format(locale, "%sMax connection time: %d ms", str, Long.valueOf(stats.max)));
    printWriter.println(
        String.format(locale, "%sMin connection time: %d ms", str, Long.valueOf(stats.min)));
    printWriter.println(
        String.format(locale, "%sNumber of connections: %d ", str, Integer.valueOf(stats.f1648n)));
  }

  public final class Stats {
    public double average;
    public long max;
    public long min;

    /* renamed from: n */
    public int f1648n;

    public static Stats merge(Stats stats, Stats stats2) {
      int i = stats.f1648n;
      int i2 = stats2.f1648n;
      return new Stats(
          i + i2,
          ((stats.average * i) + (stats2.average * i2)) / (i + i2),
          Math.max(stats.max, stats2.max),
          Math.min(stats.min, stats2.min));
    }

    public Stats() {
      this.f1648n = 0;
      this.average = 0.0d;
      this.max = 0L;
      this.min = Long.MAX_VALUE;
    }

    public Stats(int i, double d, long j, long j2) {
      this.f1648n = i;
      this.average = d;
      this.max = j;
      this.min = j2;
    }

    public final void register(long j) {
      double d = this.average;
      int i = this.f1648n;
      this.average = ((d * i) + j) / (i + 1);
      this.f1648n = i + 1;
      this.max = Math.max(this.max, j);
      this.min = Math.min(this.min, j);
    }
  }
}
