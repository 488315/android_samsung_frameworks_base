package com.android.server.power.stats;

import android.hardware.power.stats.EnergyConsumerAttribution;
import android.hardware.power.stats.EnergyConsumerResult;
import android.os.PersistableBundle;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseLongArray;

import com.android.internal.os.PowerStats;

public class EnergyConsumerPowerStatsCollector extends PowerStatsCollector {
    public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
    public int[] mEnergyConsumerIds;
    public final String mEnergyConsumerName;
    public final int mEnergyConsumerType;
    public boolean mFirstCollection;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;
    public boolean mIsInitialized;
    public long mLastConsumedEnergyUws;
    public final SparseLongArray mLastConsumerEnergyPerUid;
    public long mLastUpdateTimestamp;
    public int mLastVoltageMv;
    public final EnergyConsumerPowerStatsLayout mLayout;
    public final int mPowerComponentId;
    public final String mPowerComponentName;
    public PowerStats mPowerStats;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0 mVoltageSupplier;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EnergyConsumerPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8,
            int r9,
            java.lang.String r10,
            int r11,
            com.android.server.power.stats.BinaryStatePowerStatsLayout r12) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            long r3 = r8.getPowerStatsCollectionThrottlePeriod(r10)
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r1 = r7
            r1.<init>(r2, r3, r5, r6)
            r0 = -1
            r7.mLastConsumedEnergyUws = r0
            android.util.SparseLongArray r0 = new android.util.SparseLongArray
            r0.<init>()
            r7.mLastConsumerEnergyPerUid = r0
            r0 = 1
            r7.mFirstCollection = r0
            r7.mInjector = r8
            r7.mPowerComponentId = r9
            r7.mPowerComponentName = r10
            r7.mEnergyConsumerType = r11
            r8 = 0
            r7.mEnergyConsumerName = r8
            r7.mLayout = r12
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.EnergyConsumerPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector,"
                    + " int, java.lang.String, int,"
                    + " com.android.server.power.stats.BinaryStatePowerStatsLayout):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EnergyConsumerPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8,
            int r9,
            java.lang.String r10,
            int r11,
            com.android.server.power.stats.EnergyConsumerPowerStatsLayout r12) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            long r3 = r8.getPowerStatsCollectionThrottlePeriod(r10)
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r1 = r7
            r1.<init>(r2, r3, r5, r6)
            r0 = -1
            r7.mLastConsumedEnergyUws = r0
            android.util.SparseLongArray r0 = new android.util.SparseLongArray
            r0.<init>()
            r7.mLastConsumerEnergyPerUid = r0
            r0 = 1
            r7.mFirstCollection = r0
            r7.mInjector = r8
            r7.mPowerComponentId = r9
            r7.mPowerComponentName = r10
            int[] r8 = new int[]{r11}
            r7.mEnergyConsumerIds = r8
            r8 = 0
            r7.mEnergyConsumerType = r8
            r8 = 0
            r7.mEnergyConsumerName = r8
            r7.mLayout = r12
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.EnergyConsumerPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector,"
                    + " int, java.lang.String, int,"
                    + " com.android.server.power.stats.EnergyConsumerPowerStatsLayout):void");
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final PowerStats collectStats() {
        long j;
        long j2;
        boolean z = this.mIsInitialized;
        EnergyConsumerPowerStatsLayout energyConsumerPowerStatsLayout = this.mLayout;
        if (!z) {
            if (!this.mEnabled) {
                return null;
            }
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector =
                    this.mInjector;
            PowerStatsCollector.ConsumedEnergyRetrieverImpl consumedEnergyRetrieverImpl =
                    powerStatsCollectorInjector.mConsumedEnergyRetriever;
            this.mConsumedEnergyRetriever = consumedEnergyRetrieverImpl;
            this.mVoltageSupplier =
                    new BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0(
                            powerStatsCollectorInjector);
            if (this.mEnergyConsumerIds == null) {
                this.mEnergyConsumerIds =
                        consumedEnergyRetrieverImpl.getEnergyConsumerIds(
                                this.mEnergyConsumerType, this.mEnergyConsumerName);
            }
            PersistableBundle persistableBundle = new PersistableBundle();
            energyConsumerPowerStatsLayout.toExtras(persistableBundle);
            this.mPowerStats =
                    new PowerStats(
                            new PowerStats.Descriptor(
                                    this.mPowerComponentId,
                                    this.mPowerComponentName,
                                    energyConsumerPowerStatsLayout.mDeviceStatsArrayLength,
                                    (SparseArray) null,
                                    0,
                                    energyConsumerPowerStatsLayout.mUidStatsArrayLength,
                                    persistableBundle));
            this.mIsInitialized = true;
        }
        int[] iArr = this.mEnergyConsumerIds;
        if (iArr.length == 0) {
            return null;
        }
        EnergyConsumerResult[] consumedEnergy =
                this.mConsumedEnergyRetriever.getConsumedEnergy(iArr);
        long j3 = 0;
        if (consumedEnergy != null) {
            j = 0;
            for (int length = consumedEnergy.length - 1; length >= 0; length--) {
                long j4 = consumedEnergy[length].energyUWs;
                if (j4 != -1) {
                    j += j4;
                }
            }
        } else {
            j = 0;
        }
        long j5 = this.mLastConsumedEnergyUws;
        long j6 = j5 != -1 ? j - j5 : 0L;
        this.mLastConsumedEnergyUws = j;
        if (j6 < 0) {
            j6 = 0;
        }
        if (j6 == 0 && !this.mFirstCollection) {
            return null;
        }
        int asInt = this.mVoltageSupplier.getAsInt();
        int i = 0;
        if (asInt <= 0) {
            Slog.wtf(
                    "EnergyConsumerPowerStatsCollector",
                    "Unexpected battery voltage (" + asInt + " mV) when querying energy consumers");
            asInt = 0;
        }
        int i2 = this.mLastVoltageMv;
        int i3 = i2 != 0 ? (i2 + asInt) / 2 : asInt;
        this.mLastVoltageMv = asInt;
        energyConsumerPowerStatsLayout.setConsumedEnergy(
                this.mPowerStats.stats, 0, PowerStatsCollector.uJtoUc(i3, j6));
        for (int size = this.mPowerStats.uidStats.size() - 1; size >= 0; size--) {
            ((long[]) this.mPowerStats.uidStats.valueAt(size))
                            [energyConsumerPowerStatsLayout.mUidEnergyConsumerPosition] =
                    0;
        }
        if (consumedEnergy != null) {
            int length2 = consumedEnergy.length - 1;
            while (length2 >= 0) {
                EnergyConsumerAttribution[] energyConsumerAttributionArr =
                        consumedEnergy[length2].attribution;
                if (energyConsumerAttributionArr != null) {
                    int length3 = energyConsumerAttributionArr.length;
                    int i4 = i;
                    while (i4 < length3) {
                        EnergyConsumerAttribution energyConsumerAttribution =
                                energyConsumerAttributionArr[i4];
                        int mapUid = this.mUidResolver.mapUid(energyConsumerAttribution.uid);
                        long j7 = this.mLastConsumerEnergyPerUid.get(mapUid, -1L);
                        this.mLastConsumerEnergyPerUid.put(
                                mapUid, energyConsumerAttribution.energyUWs);
                        if (j7 == -1) {
                            j2 = 0;
                        } else {
                            long j8 = energyConsumerAttribution.energyUWs - j7;
                            j2 = 0;
                            if (j8 > 0) {
                                long[] jArr = (long[]) this.mPowerStats.uidStats.get(mapUid);
                                if (jArr == null) {
                                    jArr =
                                            new long
                                                    [energyConsumerPowerStatsLayout
                                                            .mUidStatsArrayLength];
                                    this.mPowerStats.uidStats.put(mapUid, jArr);
                                }
                                jArr[energyConsumerPowerStatsLayout.mUidEnergyConsumerPosition] =
                                        PowerStatsCollector.uJtoUc(i3, j8)
                                                + jArr[
                                                        energyConsumerPowerStatsLayout
                                                                .mUidEnergyConsumerPosition];
                            }
                        }
                        i4++;
                        j3 = j2;
                    }
                }
                length2--;
                j3 = j3;
                i = 0;
            }
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        PowerStats powerStats = this.mPowerStats;
        powerStats.durationMs = elapsedRealtime - this.mLastUpdateTimestamp;
        this.mLastUpdateTimestamp = elapsedRealtime;
        this.mFirstCollection = false;
        return powerStats;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final void onUidRemoved(int i) {
        this.mLastConsumerEnergyPerUid.delete(i);
    }
}
