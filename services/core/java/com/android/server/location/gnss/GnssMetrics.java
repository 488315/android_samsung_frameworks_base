package com.android.server.location.gnss;

import android.app.StatsManager;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.android.internal.app.IBatteryStats;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.location.gnss.hal.GnssNative;

import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssMetrics {
    public boolean[] mConstellationTypes;
    public final GnssNative mGnssNative;
    public final GnssPowerMetrics mGnssPowerMetrics;
    public long mL5SvStatusReports;
    public long mL5SvStatusReportsUsedInFix;
    public final Statistics mL5TopFourAverageCn0DbmHzReportsStatistics;
    public final Statistics mLocationFailureReportsStatistics;
    public long mLogStartInElapsedRealtimeMs;
    public int mNumL5SvStatus;
    public int mNumL5SvStatusUsedInFix;
    public int mNumSvStatus;
    public int mNumSvStatusUsedInFix;
    public final Statistics mPositionAccuracyMetersReportsStatistics;
    public long mSvStatusReports;
    public long mSvStatusReportsUsedInFix;
    public final Statistics mTimeToFirstFixMilliSReportsStatistics;
    public final Statistics mTopFourAverageCn0DbmHzReportsStatistics;
    public final Statistics mLocationFailureStatistics = new Statistics();
    public final Statistics mTimeToFirstFixSecStatistics = new Statistics();
    public final Statistics mPositionAccuracyMeterStatistics = new Statistics();
    public final Statistics mTopFourAverageCn0Statistics = new Statistics();
    public final Statistics mTopFourAverageCn0StatisticsL5 = new Statistics();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GnssPowerMetrics {
        public final IBatteryStats mBatteryStats;
        public double mLastAverageCn0 = -100.0d;
        public int mLastSignalLevel = -1;

        public GnssPowerMetrics(IBatteryStats iBatteryStats) {
            this.mBatteryStats = iBatteryStats;
        }

        public final void reportSignalQuality(float[] fArr) {
            double d = 0.0d;
            if (fArr != null && fArr.length > 0) {
                for (int max = Math.max(0, fArr.length - 4); max < fArr.length; max++) {
                    d += fArr[max];
                }
                d /= Math.min(fArr.length, 4);
            }
            if (Math.abs(d - this.mLastAverageCn0) < 1.0d) {
                return;
            }
            int i = d > 20.0d ? 1 : 0;
            if (i != this.mLastSignalLevel) {
                FrameworkStatsLog.write(69, i);
                this.mLastSignalLevel = i;
            }
            try {
                this.mBatteryStats.noteGpsSignalQuality(i);
                this.mLastAverageCn0 = d;
            } catch (RemoteException e) {
                Log.w("GnssMetrics", e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Statistics {
        public int mCount;
        public long mLongSum;
        public double mSum;
        public double mSumSquare;

        public final synchronized void addItem(double d) {
            this.mCount++;
            this.mSum += d;
            this.mSumSquare = (d * d) + this.mSumSquare;
            this.mLongSum = (long) (this.mLongSum + d);
        }

        public final synchronized int getCount() {
            return this.mCount;
        }

        public final synchronized double getMean() {
            return this.mSum / this.mCount;
        }

        public final synchronized double getStandardDeviation() {
            double d = this.mSum;
            int i = this.mCount;
            double d2 = d / i;
            double d3 = d2 * d2;
            double d4 = this.mSumSquare / i;
            if (d4 <= d3) {
                return 0.0d;
            }
            return Math.sqrt(d4 - d3);
        }

        public final synchronized void reset() {
            this.mCount = 0;
            this.mSum = 0.0d;
            this.mSumSquare = 0.0d;
            this.mLongSum = 0L;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {}

        public final int onPullAtom(int i, List list) {
            long j;
            long j2;
            long j3;
            long j4;
            long j5;
            if (i != 10074) {
                if (i != 10101) {
                    throw new UnsupportedOperationException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId = "));
                }
                GnssPowerStats requestPowerStatsBlocking =
                        GnssMetrics.this.mGnssNative.requestPowerStatsBlocking();
                if (requestPowerStatsBlocking == null) {
                    return 1;
                }
                double[] dArr = new double[10];
                double[] dArr2 = requestPowerStatsBlocking.mOtherModesEnergyMilliJoule;
                System.arraycopy(dArr2, 0, dArr, 0, Math.min(dArr2.length, 10));
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                (long) requestPowerStatsBlocking.mElapsedRealtimeUncertaintyNanos,
                                (long) (requestPowerStatsBlocking.mTotalEnergyMilliJoule * 1000.0d),
                                (long)
                                        (requestPowerStatsBlocking
                                                        .mSinglebandTrackingModeEnergyMilliJoule
                                                * 1000.0d),
                                (long)
                                        (requestPowerStatsBlocking
                                                        .mMultibandTrackingModeEnergyMilliJoule
                                                * 1000.0d),
                                (long)
                                        (requestPowerStatsBlocking
                                                        .mSinglebandAcquisitionModeEnergyMilliJoule
                                                * 1000.0d),
                                (long)
                                        (requestPowerStatsBlocking
                                                        .mMultibandAcquisitionModeEnergyMilliJoule
                                                * 1000.0d),
                                (long) (dArr[0] * 1000.0d),
                                (long) (dArr[1] * 1000.0d),
                                (long) (dArr[2] * 1000.0d),
                                (long) (dArr[3] * 1000.0d),
                                (long) (dArr[4] * 1000.0d),
                                (long) (dArr[5] * 1000.0d),
                                (long) (dArr[6] * 1000.0d),
                                (long) (dArr[7] * 1000.0d),
                                (long) (dArr[8] * 1000.0d),
                                (long) (dArr[9] * 1000.0d)));
                return 0;
            }
            GnssMetrics gnssMetrics = GnssMetrics.this;
            Statistics statistics = gnssMetrics.mLocationFailureReportsStatistics;
            long count = statistics.getCount();
            synchronized (statistics) {
                j = statistics.mLongSum;
            }
            Statistics statistics2 = gnssMetrics.mTimeToFirstFixMilliSReportsStatistics;
            long count2 = statistics2.getCount();
            synchronized (statistics2) {
                j2 = statistics2.mLongSum;
            }
            Statistics statistics3 = gnssMetrics.mPositionAccuracyMetersReportsStatistics;
            long count3 = statistics3.getCount();
            synchronized (statistics3) {
                j3 = statistics3.mLongSum;
            }
            Statistics statistics4 = gnssMetrics.mTopFourAverageCn0DbmHzReportsStatistics;
            long count4 = statistics4.getCount();
            synchronized (statistics4) {
                j4 = statistics4.mLongSum;
            }
            Statistics statistics5 = gnssMetrics.mL5TopFourAverageCn0DbmHzReportsStatistics;
            long count5 = statistics5.getCount();
            synchronized (statistics5) {
                j5 = statistics5.mLongSum;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            count,
                            j,
                            count2,
                            j2,
                            count3,
                            j3,
                            count4,
                            j4,
                            count5,
                            j5,
                            gnssMetrics.mSvStatusReports,
                            gnssMetrics.mSvStatusReportsUsedInFix,
                            gnssMetrics.mL5SvStatusReports,
                            gnssMetrics.mL5SvStatusReportsUsedInFix));
            return 0;
        }
    }

    public GnssMetrics(Context context, IBatteryStats iBatteryStats, GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        this.mGnssPowerMetrics = new GnssPowerMetrics(iBatteryStats);
        reset();
        this.mLocationFailureReportsStatistics = new Statistics();
        this.mTimeToFirstFixMilliSReportsStatistics = new Statistics();
        this.mPositionAccuracyMetersReportsStatistics = new Statistics();
        this.mTopFourAverageCn0DbmHzReportsStatistics = new Statistics();
        this.mL5TopFourAverageCn0DbmHzReportsStatistics = new Statistics();
        StatsManager statsManager = (StatsManager) context.getSystemService("stats");
        StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new StatsPullAtomCallbackImpl();
        Executor executor = ConcurrentUtils.DIRECT_EXECUTOR;
        statsManager.setPullAtomCallback(
                FrameworkStatsLog.GNSS_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomCallbackImpl);
        statsManager.setPullAtomCallback(
                FrameworkStatsLog.GNSS_POWER_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomCallbackImpl);
    }

    public static boolean isL5Sv(float f) {
        double d = f;
        return d >= 1.164E9d && d <= 1.189E9d;
    }

    public final void reset() {
        this.mLogStartInElapsedRealtimeMs = SystemClock.elapsedRealtime();
        this.mLocationFailureStatistics.reset();
        this.mTimeToFirstFixSecStatistics.reset();
        this.mPositionAccuracyMeterStatistics.reset();
        this.mTopFourAverageCn0Statistics.reset();
        this.mConstellationTypes = new boolean[8];
        this.mTopFourAverageCn0StatisticsL5.reset();
        this.mNumSvStatus = 0;
        this.mNumL5SvStatus = 0;
        this.mNumSvStatusUsedInFix = 0;
        this.mNumL5SvStatusUsedInFix = 0;
    }
}
