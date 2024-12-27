package com.android.server.power.batterysaver;

import android.os.BatteryManagerInternal;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.server.LocalServices;

import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BatterySavingStats {
    public final Object mLock;
    public int mCurrentState = -1;
    final SparseArray mStats = new SparseArray();
    public int mBatterySaverEnabledCount = 0;
    public long mLastBatterySaverEnabledTime = 0;
    public long mLastBatterySaverDisabledTime = 0;
    public int mAdaptiveBatterySaverEnabledCount = 0;
    public long mLastAdaptiveBatterySaverEnabledTime = 0;
    public long mLastAdaptiveBatterySaverDisabledTime = 0;
    public BatteryManagerInternal mBatteryManagerInternal =
            (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Stat {
        public int startBatteryLevel;
        public int startBatteryPercent;
        public long startTime;
        public int totalBatteryDrain;
        public int totalBatteryDrainPercent;
        public long totalTimeMillis;

        public final double drainPerHour() {
            long j = this.totalTimeMillis;
            if (j == 0) {
                return 0.0d;
            }
            return this.totalBatteryDrain / (j / 3600000.0d);
        }

        public String toStringForTest() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(this.totalTimeMillis / 60000);
            sb.append("m,");
            sb.append(this.totalBatteryDrain);
            sb.append(",");
            sb.append(String.format("%.2f", Double.valueOf(drainPerHour())));
            sb.append("uA/H,");
            long j = this.totalTimeMillis;
            sb.append(
                    String.format(
                            "%.2f",
                            Double.valueOf(
                                    j == 0
                                            ? 0.0d
                                            : this.totalBatteryDrainPercent / (j / 3600000.0d))));
            sb.append("%}");
            return sb.toString();
        }
    }

    public BatterySavingStats(Object obj) {
        this.mLock = obj;
    }

    public static String stateToString(int i) {
        if (i == -1) {
            return "NotInitialized";
        }
        return "BS="
                + (i & 3)
                + ",I="
                + ((i >> 2) & 1)
                + ",D="
                + ((i >> 3) & 3)
                + ",P="
                + ((i >> 5) & 1);
    }

    public static int statesToIndex(int i, int i2, int i3, int i4) {
        return (i & 3) | ((i2 & 1) << 2) | ((i3 & 3) << 3) | ((i4 & 1) << 5);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Battery saving stats:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                long injectCurrentTime = injectCurrentTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                indentingPrintWriter.print("Battery Saver is currently: ");
                int i = this.mCurrentState & 3;
                if (i == 0) {
                    indentingPrintWriter.println("OFF");
                } else if (i == 1) {
                    indentingPrintWriter.println("ON");
                } else if (i == 2) {
                    indentingPrintWriter.println("ADAPTIVE");
                }
                indentingPrintWriter.increaseIndent();
                if (this.mLastBatterySaverEnabledTime > 0) {
                    indentingPrintWriter.print("Last ON time: ");
                    indentingPrintWriter.print(
                            simpleDateFormat.format(
                                    new Date(
                                            (currentTimeMillis - injectCurrentTime)
                                                    + this.mLastBatterySaverEnabledTime)));
                    indentingPrintWriter.print(" ");
                    TimeUtils.formatDuration(
                            this.mLastBatterySaverEnabledTime,
                            injectCurrentTime,
                            indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.mLastBatterySaverDisabledTime > 0) {
                    indentingPrintWriter.print("Last OFF time: ");
                    indentingPrintWriter.print(
                            simpleDateFormat.format(
                                    new Date(
                                            (currentTimeMillis - injectCurrentTime)
                                                    + this.mLastBatterySaverDisabledTime)));
                    indentingPrintWriter.print(" ");
                    TimeUtils.formatDuration(
                            this.mLastBatterySaverDisabledTime,
                            injectCurrentTime,
                            indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Times full enabled: ");
                indentingPrintWriter.println(this.mBatterySaverEnabledCount);
                if (this.mLastAdaptiveBatterySaverEnabledTime > 0) {
                    indentingPrintWriter.print("Last ADAPTIVE ON time: ");
                    indentingPrintWriter.print(
                            simpleDateFormat.format(
                                    new Date(
                                            (currentTimeMillis - injectCurrentTime)
                                                    + this.mLastAdaptiveBatterySaverEnabledTime)));
                    indentingPrintWriter.print(" ");
                    TimeUtils.formatDuration(
                            this.mLastAdaptiveBatterySaverEnabledTime,
                            injectCurrentTime,
                            indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.mLastAdaptiveBatterySaverDisabledTime > 0) {
                    indentingPrintWriter.print("Last ADAPTIVE OFF time: ");
                    indentingPrintWriter.print(
                            simpleDateFormat.format(
                                    new Date(
                                            (currentTimeMillis - injectCurrentTime)
                                                    + this.mLastAdaptiveBatterySaverDisabledTime)));
                    indentingPrintWriter.print(" ");
                    TimeUtils.formatDuration(
                            this.mLastAdaptiveBatterySaverDisabledTime,
                            injectCurrentTime,
                            indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Times adaptive enabled: ");
                indentingPrintWriter.println(this.mAdaptiveBatterySaverEnabledCount);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Drain stats:");
                indentingPrintWriter.println(
                        "                   Battery saver OFF                          ON");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 0, "NonDoze");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 0, "       ");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 2, "Deep   ");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 2, "       ");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 1, "Light  ");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 1, "       ");
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpLineLocked(
            IndentingPrintWriter indentingPrintWriter, int i, String str, int i2, String str2) {
        indentingPrintWriter.print(str2);
        indentingPrintWriter.print(" ");
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(": ");
        Stat stat = getStat(statesToIndex(0, i, i2, 0));
        Stat stat2 = getStat(statesToIndex(1, i, i2, 0));
        indentingPrintWriter.println(
                String.format(
                        "%6dm %6dmAh(%3d%%) %8.1fmAh/h     %6dm %6dmAh(%3d%%) %8.1fmAh/h",
                        Long.valueOf(stat.totalTimeMillis / 60000),
                        Integer.valueOf(stat.totalBatteryDrain / 1000),
                        Integer.valueOf(stat.totalBatteryDrainPercent),
                        Double.valueOf(stat.drainPerHour() / 1000.0d),
                        Long.valueOf(stat2.totalTimeMillis / 60000),
                        Integer.valueOf(stat2.totalBatteryDrain / 1000),
                        Integer.valueOf(stat2.totalBatteryDrainPercent),
                        Double.valueOf(stat2.drainPerHour() / 1000.0d)));
    }

    public Stat getStat(int i) {
        Stat stat;
        synchronized (this.mLock) {
            try {
                stat = (Stat) this.mStats.get(i);
                if (stat == null) {
                    stat = new Stat();
                    this.mStats.put(i, stat);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return stat;
    }

    public int injectBatteryLevel() {
        if (this.mBatteryManagerInternal == null) {
            BatteryManagerInternal batteryManagerInternal =
                    (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
            this.mBatteryManagerInternal = batteryManagerInternal;
            if (batteryManagerInternal == null) {
                Slog.wtf("BatterySavingStats", "BatteryManagerInternal not initialized");
            }
        }
        BatteryManagerInternal batteryManagerInternal2 = this.mBatteryManagerInternal;
        if (batteryManagerInternal2 == null) {
            return 0;
        }
        return batteryManagerInternal2.getBatteryChargeCounter();
    }

    public int injectBatteryPercent() {
        if (this.mBatteryManagerInternal == null) {
            BatteryManagerInternal batteryManagerInternal =
                    (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
            this.mBatteryManagerInternal = batteryManagerInternal;
            if (batteryManagerInternal == null) {
                Slog.wtf("BatterySavingStats", "BatteryManagerInternal not initialized");
            }
        }
        BatteryManagerInternal batteryManagerInternal2 = this.mBatteryManagerInternal;
        if (batteryManagerInternal2 == null) {
            return 0;
        }
        return batteryManagerInternal2.getBatteryLevel();
    }

    public long injectCurrentTime() {
        return SystemClock.elapsedRealtime();
    }

    public final void transitionStateLocked(int i) {
        if (this.mCurrentState == i) {
            return;
        }
        long injectCurrentTime = injectCurrentTime();
        int injectBatteryLevel = injectBatteryLevel();
        int injectBatteryPercent = injectBatteryPercent();
        int i2 = this.mCurrentState;
        int i3 = i2 < 0 ? 0 : i2 & 3;
        int i4 = i >= 0 ? i & 3 : 0;
        if (i3 != i4) {
            if (i4 != 0) {
                if (i4 == 1) {
                    this.mBatterySaverEnabledCount++;
                    this.mLastBatterySaverEnabledTime = injectCurrentTime;
                    if (i3 == 2) {
                        this.mLastAdaptiveBatterySaverDisabledTime = injectCurrentTime;
                    }
                } else if (i4 == 2) {
                    this.mAdaptiveBatterySaverEnabledCount++;
                    this.mLastAdaptiveBatterySaverEnabledTime = injectCurrentTime;
                    if (i3 == 1) {
                        this.mLastBatterySaverDisabledTime = injectCurrentTime;
                    }
                }
            } else if (i3 == 1) {
                this.mLastBatterySaverDisabledTime = injectCurrentTime;
            } else {
                this.mLastAdaptiveBatterySaverDisabledTime = injectCurrentTime;
            }
        }
        if (i2 >= 0) {
            Stat stat = getStat(i2);
            stat.getClass();
            long j = injectCurrentTime - stat.startTime;
            int i5 = stat.startBatteryLevel - injectBatteryLevel;
            int i6 = stat.startBatteryPercent - injectBatteryPercent;
            long j2 = stat.totalTimeMillis + j;
            stat.totalTimeMillis = j2;
            int i7 = stat.totalBatteryDrain + i5;
            stat.totalBatteryDrain = i7;
            int i8 = stat.totalBatteryDrainPercent + i6;
            stat.totalBatteryDrainPercent = i8;
            int i9 = this.mCurrentState;
            EventLog.writeEvent(
                    27390,
                    Integer.valueOf(i9 & 3),
                    Integer.valueOf((i9 >> 2) & 1),
                    Integer.valueOf((i9 >> 3) & 3),
                    Long.valueOf(j),
                    Integer.valueOf(i5),
                    Integer.valueOf(i6),
                    Long.valueOf(j2),
                    Integer.valueOf(i7),
                    Integer.valueOf(i8));
        }
        this.mCurrentState = i;
        if (i < 0) {
            return;
        }
        Stat stat2 = getStat(i);
        stat2.startBatteryLevel = injectBatteryLevel;
        stat2.startBatteryPercent = injectBatteryPercent;
        stat2.startTime = injectCurrentTime;
    }
}
