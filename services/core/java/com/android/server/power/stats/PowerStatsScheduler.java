package com.android.server.power.stats;

import android.app.AlarmManager;
import android.os.Handler;

import com.android.internal.os.Clock;
import com.android.internal.os.MonotonicClock;
import com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda14;
import com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda15;
import com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatsScheduler {
    public final long mAggregatedPowerStatsSpanDuration;
    public final BatteryStatsService$$ExternalSyntheticLambda14 mAlarmScheduler;
    public final Clock mClock;
    public final Supplier mEarliestAvailableBatteryHistoryTimeMs;
    public final Handler mHandler;
    public long mLastSavedSpanEndMonotonicTime;
    public final MonotonicClock mMonotonicClock;
    public final long mPowerStatsAggregationPeriod;
    public final PowerStatsAggregator mPowerStatsAggregator;
    public final Runnable mPowerStatsCollector;
    public final PowerStatsStore mPowerStatsStore;
    public static final long MINUTE_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);
    public static final long HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);

    public PowerStatsScheduler(
            BatteryStatsService$$ExternalSyntheticLambda15
                    batteryStatsService$$ExternalSyntheticLambda15,
            PowerStatsAggregator powerStatsAggregator,
            long j,
            long j2,
            PowerStatsStore powerStatsStore,
            BatteryStatsService$$ExternalSyntheticLambda14
                    batteryStatsService$$ExternalSyntheticLambda14,
            Clock clock,
            MonotonicClock monotonicClock,
            BatteryStatsService$$ExternalSyntheticLambda16
                    batteryStatsService$$ExternalSyntheticLambda16,
            Handler handler) {
        this.mPowerStatsAggregator = powerStatsAggregator;
        this.mAggregatedPowerStatsSpanDuration = j;
        this.mPowerStatsAggregationPeriod = j2;
        this.mPowerStatsStore = powerStatsStore;
        this.mAlarmScheduler = batteryStatsService$$ExternalSyntheticLambda14;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mHandler = handler;
        this.mPowerStatsCollector = batteryStatsService$$ExternalSyntheticLambda15;
        this.mEarliestAvailableBatteryHistoryTimeMs =
                batteryStatsService$$ExternalSyntheticLambda16;
    }

    public static long alignToWallClock(long j, long j2, long j3, long j4) {
        long timeInMillis;
        long j5 = (j - j3) + j4;
        long j6 = MINUTE_IN_MILLIS;
        if (j2 < j6 || TimeUnit.HOURS.toMillis(1L) % j2 != 0) {
            long j7 = HOUR_IN_MILLIS;
            if (j2 < j7 || TimeUnit.DAYS.toMillis(1L) % j2 != 0) {
                return j;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((j5 + j7) - 1);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            int i = (int) (j2 / j7);
            calendar.set(11, (((calendar.get(11) + i) - 1) / i) * i);
            timeInMillis = calendar.getTimeInMillis();
        } else {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis((j5 + j6) - 1);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            int i2 = (int) (j2 / j6);
            calendar2.set(12, (((calendar2.get(12) + i2) - 1) / i2) * i2);
            timeInMillis = calendar2.getTimeInMillis();
        }
        return (timeInMillis - j5) + j;
    }

    public final long getLastSavedSpanEndMonotonicTime() {
        long j = this.mLastSavedSpanEndMonotonicTime;
        if (j != 0) {
            return j;
        }
        this.mLastSavedSpanEndMonotonicTime = -1L;
        for (PowerStatsSpan.Metadata metadata : this.mPowerStatsStore.getTableOfContents()) {
            if (((ArrayList) metadata.mSections).contains("aggregated-power-stats")) {
                Iterator it = ((ArrayList) metadata.mTimeFrames).iterator();
                while (it.hasNext()) {
                    PowerStatsSpan.TimeFrame timeFrame = (PowerStatsSpan.TimeFrame) it.next();
                    long j2 = timeFrame.startMonotonicTime + timeFrame.duration;
                    if (j2 > this.mLastSavedSpanEndMonotonicTime) {
                        this.mLastSavedSpanEndMonotonicTime = j2;
                    }
                }
            }
        }
        return this.mLastSavedSpanEndMonotonicTime;
    }

    public final void scheduleNextPowerStatsAggregation() {
        ((AlarmManager) this.mAlarmScheduler.f$0.mContext.getSystemService(AlarmManager.class))
                .set(
                        3,
                        this.mClock.elapsedRealtime() + this.mPowerStatsAggregationPeriod,
                        "PowerStats",
                        new AlarmManager
                                .OnAlarmListener() { // from class:
                                                     // com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda4
                            @Override // android.app.AlarmManager.OnAlarmListener
                            public final void onAlarm() {
                                PowerStatsScheduler powerStatsScheduler = PowerStatsScheduler.this;
                                powerStatsScheduler.schedulePowerStatsAggregation();
                                powerStatsScheduler.mHandler.post(
                                        new PowerStatsScheduler$$ExternalSyntheticLambda0(
                                                powerStatsScheduler, 1));
                            }
                        },
                        this.mHandler);
    }

    public void schedulePowerStatsAggregation() {
        this.mPowerStatsCollector.run();
        this.mHandler.post(new PowerStatsScheduler$$ExternalSyntheticLambda0(this, 0));
    }
}
