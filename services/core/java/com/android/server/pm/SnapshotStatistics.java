package com.android.server.pm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.internal.util.FrameworkStatsLog;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class SnapshotStatistics {
    public static final long SNAPSHOT_LOG_INTERVAL_US = TimeUnit.DAYS.toMicros(1);
    public final AnonymousClass1 mHandler;
    public long mLastLogTimeUs;
    public final Stats[] mLong;
    public int mPackageCount;
    public final Stats[] mShort;
    public final Object mLock = new Object();
    public int mEventsReported = 0;
    public final BinMap mTimeBins = new BinMap(new int[]{1, 2, 5, 10, 20, 50, 100});
    public final BinMap mUseBins = new BinMap(new int[]{1, 10, 100, 1000, 10000});

    public final class BinMap {
        public final int mCount;
        public final int mMaxBin;
        public final int[] mUserKey;

        public BinMap(int[] iArr) {
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            this.mUserKey = copyOf;
            this.mCount = copyOf.length + 1;
            this.mMaxBin = copyOf[copyOf.length - 1] + 1;
        }

        public final int getBin(int i) {
            int[] iArr = this.mUserKey;
            int i2 = this.mMaxBin;
            if (i < 0 || i >= i2) {
                if (i >= i2) {
                    return iArr.length;
                }
                return 0;
            }
            for (int i3 = 0; i3 < iArr.length; i3++) {
                if (i <= iArr[i3]) {
                    return i3;
                }
            }
            return 0;
        }
    }

    public final class Stats {
        public int mBigBuilds;
        public int mMaxBuildTimeUs;
        public int mMaxUsedCount;
        public int mShortLived;
        public final long mStartTimeUs;
        public long mStopTimeUs;
        public final int[] mTimes;
        public int mTotalBuilds;
        public long mTotalTimeUs;
        public int mTotalUsed;
        public final int[] mUsed;

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m778$$Nest$mdump(Stats stats, PrintWriter printWriter, long j, boolean z, String str) {
            stats.getClass();
            if (str.equals("stats")) {
                stats.dumpPrefix(printWriter, j, z, "Summary stats");
                if (z) {
                    printWriter.format(Locale.US, "  %10s  %10s  %10s  %10s  %10s  %10s", "TotBlds", "TotUsed", "BigBlds", "ShortLvd", "TotTime", "MaxTime");
                } else {
                    printWriter.format(Locale.US, "  %10d  %10d  %10d  %10d  %10d  %10d", Integer.valueOf(stats.mTotalBuilds), Integer.valueOf(stats.mTotalUsed), Integer.valueOf(stats.mBigBuilds), Integer.valueOf(stats.mShortLived), Long.valueOf(stats.mTotalTimeUs / 1000), Integer.valueOf(stats.mMaxBuildTimeUs / 1000));
                }
                printWriter.println();
                return;
            }
            boolean equals = str.equals("times");
            SnapshotStatistics snapshotStatistics = SnapshotStatistics.this;
            int i = 0;
            if (equals) {
                stats.dumpPrefix(printWriter, j, z, "Build times");
                if (!z) {
                    while (true) {
                        int[] iArr = stats.mTimes;
                        if (i >= iArr.length) {
                            break;
                        }
                        printWriter.format(Locale.US, "  %10d", Integer.valueOf(iArr[i]));
                        i++;
                    }
                } else {
                    int[] iArr2 = snapshotStatistics.mTimeBins.mUserKey;
                    while (i < iArr2.length) {
                        printWriter.format(Locale.US, "  %10s", TextUtils.formatSimple("<= %dms", new Object[]{Integer.valueOf(iArr2[i])}));
                        i++;
                    }
                    printWriter.format(Locale.US, "  %10s", TextUtils.formatSimple("> %dms", new Object[]{Integer.valueOf(iArr2[iArr2.length - 1])}));
                }
                printWriter.println();
                return;
            }
            if (!str.equals("usage")) {
                throw new IllegalArgumentException("unrecognized choice: ".concat(str));
            }
            stats.dumpPrefix(printWriter, j, z, "Use counters");
            if (!z) {
                while (true) {
                    int[] iArr3 = stats.mUsed;
                    if (i >= iArr3.length) {
                        break;
                    }
                    printWriter.format(Locale.US, "  %10d", Integer.valueOf(iArr3[i]));
                    i++;
                }
            } else {
                int[] iArr4 = snapshotStatistics.mUseBins.mUserKey;
                while (i < iArr4.length) {
                    printWriter.format(Locale.US, "  %10s", TextUtils.formatSimple("<= %d", new Object[]{Integer.valueOf(iArr4[i])}));
                    i++;
                }
                printWriter.format(Locale.US, "  %10s", TextUtils.formatSimple("> %d", new Object[]{Integer.valueOf(iArr4[iArr4.length - 1])}));
            }
            printWriter.println();
        }

        /* renamed from: -$$Nest$mrebuild, reason: not valid java name */
        public static void m779$$Nest$mrebuild(Stats stats, int i, int i2, int i3, int i4, boolean z, boolean z2) {
            stats.mTotalBuilds++;
            int[] iArr = stats.mTimes;
            iArr[i3] = iArr[i3] + 1;
            if (i2 >= 0) {
                stats.mTotalUsed += i2;
                int[] iArr2 = stats.mUsed;
                iArr2[i4] = iArr2[i4] + 1;
            }
            stats.mTotalTimeUs += i;
            if (z) {
                stats.mBigBuilds++;
            }
            if (z2) {
                stats.mShortLived++;
            }
            if (stats.mMaxBuildTimeUs < i) {
                stats.mMaxBuildTimeUs = i;
            }
            if (stats.mMaxUsedCount < i2) {
                stats.mMaxUsedCount = i2;
            }
        }

        public Stats(long j) {
            this.mStopTimeUs = 0L;
            this.mTotalBuilds = 0;
            this.mTotalUsed = 0;
            this.mBigBuilds = 0;
            this.mShortLived = 0;
            this.mTotalTimeUs = 0L;
            this.mMaxBuildTimeUs = 0;
            this.mMaxUsedCount = 0;
            this.mStartTimeUs = j;
            this.mTimes = new int[SnapshotStatistics.this.mTimeBins.mCount];
            this.mUsed = new int[SnapshotStatistics.this.mUseBins.mCount];
        }

        public Stats(Stats stats) {
            this.mStartTimeUs = 0L;
            this.mStopTimeUs = 0L;
            this.mTotalBuilds = 0;
            this.mTotalUsed = 0;
            this.mBigBuilds = 0;
            this.mShortLived = 0;
            this.mTotalTimeUs = 0L;
            this.mMaxBuildTimeUs = 0;
            this.mMaxUsedCount = 0;
            this.mStartTimeUs = stats.mStartTimeUs;
            this.mStopTimeUs = stats.mStopTimeUs;
            int[] iArr = stats.mTimes;
            this.mTimes = Arrays.copyOf(iArr, iArr.length);
            int[] iArr2 = stats.mUsed;
            this.mUsed = Arrays.copyOf(iArr2, iArr2.length);
            this.mTotalBuilds = stats.mTotalBuilds;
            this.mTotalUsed = stats.mTotalUsed;
            this.mBigBuilds = stats.mBigBuilds;
            this.mShortLived = stats.mShortLived;
            this.mTotalTimeUs = stats.mTotalTimeUs;
            this.mMaxBuildTimeUs = stats.mMaxBuildTimeUs;
            this.mMaxUsedCount = stats.mMaxUsedCount;
        }

        public static String durationToString(long j) {
            int i = (int) (j / 1000000);
            int i2 = i / 60;
            int i3 = i % 60;
            int i4 = i2 / 60;
            int i5 = i2 % 60;
            int i6 = i4 / 24;
            int i7 = i4 % 24;
            return i6 != 0 ? TextUtils.formatSimple("%2d:%02d:%02d:%02d", new Object[]{Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i5), Integer.valueOf(i3)}) : i7 != 0 ? TextUtils.formatSimple("%2s %02d:%02d:%02d", new Object[]{"", Integer.valueOf(i7), Integer.valueOf(i5), Integer.valueOf(i3)}) : TextUtils.formatSimple("%2s %2s %2d:%02d", new Object[]{"", "", Integer.valueOf(i5), Integer.valueOf(i3)});
        }

        public final void dumpPrefix(PrintWriter printWriter, long j, boolean z, String str) {
            printWriter.print("    ");
            if (z) {
                printWriter.format(Locale.US, "%-23s", str);
                return;
            }
            Locale locale = Locale.US;
            printWriter.format(locale, "%11s", durationToString(j - this.mStartTimeUs));
            long j2 = this.mStopTimeUs;
            if (j2 != 0) {
                printWriter.format(locale, " %11s", durationToString(j - j2));
            } else {
                printWriter.format(locale, " %11s", "now");
            }
        }
    }

    public SnapshotStatistics() {
        this.mHandler = null;
        long currentTimeMicro = SystemClock.currentTimeMicro();
        Stats[] statsArr = new Stats[2];
        this.mLong = statsArr;
        statsArr[0] = new Stats(currentTimeMicro);
        Stats[] statsArr2 = new Stats[10];
        this.mShort = statsArr2;
        statsArr2[0] = new Stats(currentTimeMicro);
        this.mLastLogTimeUs = currentTimeMicro;
        ?? r1 = new Handler(Looper.getMainLooper()) { // from class: com.android.server.pm.SnapshotStatistics.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                SnapshotStatistics snapshotStatistics = SnapshotStatistics.this;
                synchronized (snapshotStatistics.mLock) {
                    try {
                        long currentTimeMicro2 = SystemClock.currentTimeMicro();
                        if (currentTimeMicro2 - snapshotStatistics.mLastLogTimeUs > SnapshotStatistics.SNAPSHOT_LOG_INTERVAL_US) {
                            snapshotStatistics.shift(snapshotStatistics.mLong, currentTimeMicro2);
                            snapshotStatistics.mLastLogTimeUs = currentTimeMicro2;
                            Stats stats = snapshotStatistics.mLong[r2.length - 1];
                            int i = snapshotStatistics.mPackageCount;
                            int i2 = stats.mTotalBuilds;
                            FrameworkStatsLog.write(FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED, stats.mTimes, stats.mUsed, stats.mMaxBuildTimeUs, stats.mMaxUsedCount, i2 == 0 ? 0L : stats.mTotalTimeUs / i2, i2 == 0 ? 0 : stats.mTotalUsed / i2, i);
                        }
                        snapshotStatistics.shift(snapshotStatistics.mShort, currentTimeMicro2);
                        snapshotStatistics.mEventsReported = 0;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                snapshotStatistics.mHandler.sendEmptyMessageDelayed(0, 60000L);
            }
        };
        this.mHandler = r1;
        r1.sendEmptyMessageDelayed(0, 60000L);
    }

    public static void dump(PrintWriter printWriter, long j, Stats[] statsArr, Stats[] statsArr2, String str) {
        Stats.m778$$Nest$mdump(statsArr[0], printWriter, j, true, str);
        for (Stats stats : statsArr2) {
            if (stats != null) {
                Stats.m778$$Nest$mdump(stats, printWriter, j, false, str);
            }
        }
        for (Stats stats2 : statsArr) {
            if (stats2 != null) {
                Stats.m778$$Nest$mdump(stats2, printWriter, j, false, str);
            }
        }
    }

    public final void shift(Stats[] statsArr, long j) {
        statsArr[0].mStopTimeUs = j;
        for (int length = statsArr.length - 1; length > 0; length--) {
            statsArr[length] = statsArr[length - 1];
        }
        statsArr[0] = new Stats(j);
    }
}
