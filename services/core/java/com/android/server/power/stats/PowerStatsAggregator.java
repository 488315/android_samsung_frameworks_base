package com.android.server.power.stats;

import android.os.BatteryStats;
import android.util.SparseBooleanArray;

import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.internal.os.PowerStats;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class PowerStatsAggregator {
    public final AggregatedPowerStatsConfig mAggregatedPowerStatsConfig;
    public final BatteryStatsHistory mHistory;
    public AggregatedPowerStats mStats;
    public final SparseBooleanArray mEnabledComponents = new SparseBooleanArray(29);
    public int mCurrentBatteryState = 0;
    public int mCurrentScreenState = 1;

    public PowerStatsAggregator(
            AggregatedPowerStatsConfig aggregatedPowerStatsConfig,
            BatteryStatsHistory batteryStatsHistory) {
        this.mAggregatedPowerStatsConfig = aggregatedPowerStatsConfig;
        this.mHistory = batteryStatsHistory;
    }

    public final void aggregatePowerStats(long j, long j2, Consumer consumer) {
        PowerStats.Descriptor descriptor;
        synchronized (this) {
            try {
                if (this.mStats == null) {
                    this.mStats =
                            new AggregatedPowerStats(
                                    this.mAggregatedPowerStatsConfig, this.mEnabledComponents);
                }
                AggregatedPowerStats aggregatedPowerStats = this.mStats;
                int i = 0;
                for (int i2 = 0; i2 < aggregatedPowerStats.mPowerComponentStats.size(); i2++) {
                    PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats =
                            (PowerComponentAggregatedPowerStats)
                                    aggregatedPowerStats.mPowerComponentStats.valueAt(i2);
                    if (powerComponentAggregatedPowerStats.mProcessor == null) {
                        Supplier supplier =
                                powerComponentAggregatedPowerStats.mConfig.mProcessorSupplier;
                        powerComponentAggregatedPowerStats.mProcessor =
                                supplier == null
                                        ? AggregatedPowerStatsConfig.NO_OP_PROCESSOR
                                        : (PowerStatsProcessor) supplier.get();
                    }
                    powerComponentAggregatedPowerStats.mProcessor.start(
                            powerComponentAggregatedPowerStats, j);
                }
                long j3 = 0;
                long j4 = j > 0 ? j : -1L;
                BatteryStatsHistoryIterator iterate = this.mHistory.iterate(j, j2);
                int i3 = -1;
                int i4 = -1;
                boolean z = false;
                while (iterate.hasNext()) {
                    try {
                        BatteryStats.HistoryItem next = iterate.next();
                        if (z) {
                            byte b = next.cmd;
                            if (b == 5 || b == 7) {
                                this.mStats.addClockUpdate(next.time, next.currentTime);
                            }
                        } else {
                            this.mStats.addClockUpdate(next.time, next.currentTime);
                            if (j4 == -1) {
                                j4 = next.time;
                            }
                            z = true;
                        }
                        long j5 = next.time;
                        int i5 = (next.states & 524288) != 0 ? 1 : i;
                        if (i5 != this.mCurrentBatteryState) {
                            this.mStats.setDeviceState(i, i5, j5);
                            this.mCurrentBatteryState = i5;
                        }
                        int i6 = (next.states & 1048576) != 0 ? i : 1;
                        if (i6 != this.mCurrentScreenState) {
                            this.mStats.setDeviceState(1, i6, next.time);
                            this.mCurrentScreenState = i6;
                        }
                        if ((next.states & 549453824) != i3 || (next.states2 & 1210057088) != i4) {
                            AggregatedPowerStats aggregatedPowerStats2 = this.mStats;
                            for (int i7 = 0;
                                    i7 < aggregatedPowerStats2.mPowerComponentStats.size();
                                    i7++) {
                                PowerComponentAggregatedPowerStats
                                        powerComponentAggregatedPowerStats2 =
                                                (PowerComponentAggregatedPowerStats)
                                                        aggregatedPowerStats2.mPowerComponentStats
                                                                .valueAt(i7);
                                powerComponentAggregatedPowerStats2.mProcessor.noteStateChange(
                                        powerComponentAggregatedPowerStats2, next);
                            }
                            i3 = next.states & 549453824;
                            i4 = next.states2 & 1210057088;
                        }
                        BatteryStats.ProcessStateChange processStateChange =
                                next.processStateChange;
                        if (processStateChange != null) {
                            this.mStats.setUidState(
                                    processStateChange.uid,
                                    processStateChange.processState,
                                    next.time);
                        }
                        PowerStats powerStats = next.powerStats;
                        if (powerStats != null) {
                            AggregatedPowerStats aggregatedPowerStats3 = this.mStats;
                            aggregatedPowerStats3.getClass();
                            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats3 =
                                    (PowerComponentAggregatedPowerStats)
                                            aggregatedPowerStats3.mPowerComponentStats.get(
                                                    powerStats.descriptor.powerComponentId);
                            if (powerComponentAggregatedPowerStats3 == null
                                    || ((descriptor =
                                                            powerComponentAggregatedPowerStats3
                                                                    .mPowerStatsDescriptor)
                                                    != null
                                            && !descriptor.equals(powerStats.descriptor))) {
                                if (j5 > j4) {
                                    AggregatedPowerStats aggregatedPowerStats4 = this.mStats;
                                    aggregatedPowerStats4.mDurationMs = j5 - j4;
                                    for (int i8 = 0;
                                            i8 < aggregatedPowerStats4.mPowerComponentStats.size();
                                            i8++) {
                                        PowerComponentAggregatedPowerStats
                                                powerComponentAggregatedPowerStats4 =
                                                        (PowerComponentAggregatedPowerStats)
                                                                aggregatedPowerStats4
                                                                        .mPowerComponentStats
                                                                        .valueAt(i8);
                                        powerComponentAggregatedPowerStats4.mProcessor.finish(
                                                powerComponentAggregatedPowerStats4, j5);
                                    }
                                    consumer.accept(this.mStats);
                                }
                                this.mStats.reset();
                                this.mStats.addClockUpdate(next.time, next.currentTime);
                                j4 = next.time;
                                j5 = j4;
                            }
                            this.mStats.addPowerStats(next.powerStats, next.time);
                        }
                        j3 = j5;
                        i = 0;
                    } catch (Throwable th) {
                        if (iterate == null) {
                            throw th;
                        }
                        try {
                            iterate.close();
                            throw th;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                            throw th;
                        }
                    }
                }
                iterate.close();
                if (j3 > j4) {
                    AggregatedPowerStats aggregatedPowerStats5 = this.mStats;
                    aggregatedPowerStats5.mDurationMs = j3 - j4;
                    for (int i9 = 0; i9 < aggregatedPowerStats5.mPowerComponentStats.size(); i9++) {
                        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats5 =
                                (PowerComponentAggregatedPowerStats)
                                        aggregatedPowerStats5.mPowerComponentStats.valueAt(i9);
                        powerComponentAggregatedPowerStats5.mProcessor.finish(
                                powerComponentAggregatedPowerStats5, j3);
                    }
                    consumer.accept(this.mStats);
                }
                this.mStats.reset();
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }
}
