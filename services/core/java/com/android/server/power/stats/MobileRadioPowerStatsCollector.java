package com.android.server.power.stats;

import android.net.NetworkStats;
import android.os.BatteryStats;
import android.os.OutcomeReceiver;
import android.os.PersistableBundle;
import android.telephony.ModemActivityInfo;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public final class MobileRadioPowerStatsCollector extends PowerStatsCollector {
    protected static final long MOBILE_RADIO_POWER_STATE_UPDATE_FREQ_MS = 600000;
    static final int[] NETWORK_TYPES = {0, 1, 2, 3, 4, 5, 6};
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda3
            mCallDurationSupplier;
    public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
    public long[] mDeviceStats;
    public int[] mEnergyConsumerIds;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;
    public boolean mIsInitialized;
    public long mLastCallDuration;
    public long[] mLastConsumedEnergyUws;
    public ModemActivityInfo mLastModemActivityInfo;
    public NetworkStats mLastNetworkStats;
    public long mLastScanDuration;
    public long mLastUpdateTimestampMillis;
    public int mLastVoltageMv;
    public MobileRadioPowerStatsLayout mLayout;
    public volatile BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1
            mNetworkStatsSupplier;
    public PowerStats mPowerStats;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda3
            mScanDurationSupplier;
    public volatile TelephonyManager mTelephonyManager;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0 mVoltageSupplier;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MobileRadioPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            r0 = 8
            java.lang.String r0 = android.os.BatteryConsumer.powerComponentIdToString(r0)
            long r3 = r8.getPowerStatsCollectionThrottlePeriod(r0)
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r1 = r7
            r1.<init>(r2, r3, r5, r6)
            r0 = 0
            int[] r0 = new int[r0]
            r7.mEnergyConsumerIds = r0
            r7.mInjector = r8
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.MobileRadioPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector):void");
    }

    public static int mapRadioAccessNetworkTypeToRadioAccessTechnology(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
                break;
            case 3:
                break;
            case 6:
                break;
            default:
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i,
                        "Unhandled RadioAccessNetworkType (",
                        "), mapping to OTHER",
                        "MobileRadioPowerStatsCollector");
                break;
        }
        return 0;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final PowerStats collectStats() {
        ModemActivityInfo modemActivityInfo = null;
        if (!this.mIsInitialized) {
            if (!this.mEnabled) {
                return null;
            }
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector =
                    this.mInjector;
            this.mConsumedEnergyRetriever = powerStatsCollectorInjector.mConsumedEnergyRetriever;
            this.mVoltageSupplier =
                    new BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0(
                            powerStatsCollectorInjector);
            this.mTelephonyManager = powerStatsCollectorInjector.mTelephonyManager;
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector2 =
                    this.mInjector;
            powerStatsCollectorInjector2.getClass();
            this.mNetworkStatsSupplier =
                    new BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1(
                            powerStatsCollectorInjector2, 0);
            final BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector3 =
                    this.mInjector;
            powerStatsCollectorInjector3.getClass();
            final int i = 1;
            this.mCallDurationSupplier =
                    new LongSupplier() { // from class:
                                         // com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda3
                        @Override // java.util.function.LongSupplier
                        public final long getAsLong() {
                            int i2 = i;
                            BatteryStatsImpl.PowerStatsCollectorInjector
                                    powerStatsCollectorInjector4 = powerStatsCollectorInjector3;
                            switch (i2) {
                                case 0:
                                    BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                                    return batteryStatsImpl.mPhoneSignalScanningTimer
                                            .getTotalTimeLocked(
                                                    batteryStatsImpl.mClock.elapsedRealtime()
                                                            * 1000,
                                                    0);
                                default:
                                    BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                                    return batteryStatsImpl2.mPhoneOnTimer.getTotalTimeLocked(
                                            batteryStatsImpl2.mClock.elapsedRealtime() * 1000, 0);
                            }
                        }
                    };
            final BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector4 =
                    this.mInjector;
            powerStatsCollectorInjector4.getClass();
            final int i2 = 0;
            this.mScanDurationSupplier =
                    new LongSupplier() { // from class:
                                         // com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda3
                        @Override // java.util.function.LongSupplier
                        public final long getAsLong() {
                            int i22 = i2;
                            BatteryStatsImpl.PowerStatsCollectorInjector
                                    powerStatsCollectorInjector42 = powerStatsCollectorInjector4;
                            switch (i22) {
                                case 0:
                                    BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                                    return batteryStatsImpl.mPhoneSignalScanningTimer
                                            .getTotalTimeLocked(
                                                    batteryStatsImpl.mClock.elapsedRealtime()
                                                            * 1000,
                                                    0);
                                default:
                                    BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                                    return batteryStatsImpl2.mPhoneOnTimer.getTotalTimeLocked(
                                            batteryStatsImpl2.mClock.elapsedRealtime() * 1000, 0);
                            }
                        }
                    };
            int[] energyConsumerIds = this.mConsumedEnergyRetriever.getEnergyConsumerIds(5, null);
            this.mEnergyConsumerIds = energyConsumerIds;
            long[] jArr = new long[energyConsumerIds.length];
            this.mLastConsumedEnergyUws = jArr;
            Arrays.fill(jArr, -1L);
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout =
                    new MobileRadioPowerStatsLayout();
            this.mLayout = mobileRadioPowerStatsLayout;
            mobileRadioPowerStatsLayout.mDeviceSleepTimePosition =
                    mobileRadioPowerStatsLayout.addDeviceSection(1, 0, "sleep");
            mobileRadioPowerStatsLayout.mDeviceIdleTimePosition =
                    mobileRadioPowerStatsLayout.addDeviceSection(1, 0, "idle");
            mobileRadioPowerStatsLayout.mDeviceScanTimePosition =
                    mobileRadioPowerStatsLayout.addDeviceSection(1, 0, "scan");
            mobileRadioPowerStatsLayout.mDeviceCallTimePosition =
                    mobileRadioPowerStatsLayout.addDeviceSection(1, 1, "call");
            this.mLayout.addDeviceSectionEnergyConsumers(this.mEnergyConsumerIds.length);
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout2 = this.mLayout;
            int i3 = mobileRadioPowerStatsLayout2.mStateStatsArrayLength;
            mobileRadioPowerStatsLayout2.mStateStatsArrayLength = i3 + 1;
            PowerStatsLayout.appendFormat(
                    mobileRadioPowerStatsLayout2.mStateFormat, i3, 1, "rx", 0);
            mobileRadioPowerStatsLayout2.mStateRxTimePosition = i3;
            int numTxPowerLevels = ModemActivityInfo.getNumTxPowerLevels();
            mobileRadioPowerStatsLayout2.mStateTxTimesCount = numTxPowerLevels;
            int i4 = mobileRadioPowerStatsLayout2.mStateStatsArrayLength;
            mobileRadioPowerStatsLayout2.mStateStatsArrayLength = i4 + numTxPowerLevels;
            PowerStatsLayout.appendFormat(
                    mobileRadioPowerStatsLayout2.mStateFormat, i4, numTxPowerLevels, "tx", 0);
            mobileRadioPowerStatsLayout2.mStateTxTimesPosition = i4;
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout3 = this.mLayout;
            mobileRadioPowerStatsLayout3.mUidRxPacketsPosition =
                    mobileRadioPowerStatsLayout3.addUidSection(1, 0, "rx-pkts");
            mobileRadioPowerStatsLayout3.mUidRxBytesPosition =
                    mobileRadioPowerStatsLayout3.addUidSection(1, 0, "rx-B");
            mobileRadioPowerStatsLayout3.mUidTxPacketsPosition =
                    mobileRadioPowerStatsLayout3.addUidSection(1, 0, "tx-pkts");
            mobileRadioPowerStatsLayout3.mUidTxBytesPosition =
                    mobileRadioPowerStatsLayout3.addUidSection(1, 0, "tx-B");
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout4 = this.mLayout;
            mobileRadioPowerStatsLayout4.mDeviceDurationPosition =
                    mobileRadioPowerStatsLayout4.addDeviceSection(1, 1, "usage");
            this.mLayout.addDeviceSectionPowerEstimate();
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout5 = this.mLayout;
            mobileRadioPowerStatsLayout5.mUidPowerEstimatePosition =
                    mobileRadioPowerStatsLayout5.addUidSection(1, 5, "power");
            SparseArray sparseArray = new SparseArray();
            int i5 = 0;
            while (i5 < 3) {
                int i6 = i5 == 2 ? 5 : 1;
                for (int i7 = 0; i7 < i6; i7++) {
                    int i8 = i5 == 2 ? (i7 << 8) | i5 : i5;
                    StringBuilder sb = new StringBuilder();
                    if (i5 != 0) {
                        sb.append(BatteryStats.RADIO_ACCESS_TECHNOLOGY_NAMES[i5]);
                    }
                    if (i7 != 0) {
                        if (!sb.isEmpty()) {
                            sb.append(" ");
                        }
                        sb.append(ServiceState.frequencyRangeToString(i7));
                    }
                    sparseArray.put(i8, !sb.isEmpty() ? sb.toString() : "other");
                }
                i5++;
            }
            PersistableBundle persistableBundle = new PersistableBundle();
            this.mLayout.toExtras(persistableBundle);
            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout6 = this.mLayout;
            PowerStats powerStats =
                    new PowerStats(
                            new PowerStats.Descriptor(
                                    8,
                                    mobileRadioPowerStatsLayout6.mDeviceStatsArrayLength,
                                    sparseArray,
                                    mobileRadioPowerStatsLayout6.mStateStatsArrayLength,
                                    mobileRadioPowerStatsLayout6.mUidStatsArrayLength,
                                    persistableBundle));
            this.mPowerStats = powerStats;
            this.mDeviceStats = powerStats.stats;
            this.mIsInitialized = true;
        }
        Arrays.fill(this.mPowerStats.stats, 0L);
        this.mPowerStats.uidStats.clear();
        if (this.mTelephonyManager != null) {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mTelephonyManager.requestModemActivityInfo(
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new OutcomeReceiver() { // from class:
                                            // com.android.server.power.stats.MobileRadioPowerStatsCollector.1
                        @Override // android.os.OutcomeReceiver
                        public final void onError(Throwable th) {
                            Slog.w(
                                    "MobileRadioPowerStatsCollector",
                                    "error reading modem stats:"
                                            + ((TelephonyManager.ModemActivityInfoException) th));
                            completableFuture.complete(null);
                        }

                        @Override // android.os.OutcomeReceiver
                        public final void onResult(Object obj) {
                            completableFuture.complete((ModemActivityInfo) obj);
                        }
                    });
            try {
                modemActivityInfo =
                        (ModemActivityInfo) completableFuture.get(20000L, TimeUnit.MILLISECONDS);
            } catch (Exception unused) {
                Slog.e("MobileRadioPowerStatsCollector", "Cannot acquire ModemActivityInfo");
            }
            if (modemActivityInfo != null) {
                ModemActivityInfo modemActivityInfo2 = this.mLastModemActivityInfo;
                ModemActivityInfo delta =
                        modemActivityInfo2 == null
                                ? modemActivityInfo.getDelta(modemActivityInfo)
                                : modemActivityInfo2.getDelta(modemActivityInfo);
                this.mLastModemActivityInfo = modemActivityInfo;
                long timestampMillis = delta.getTimestampMillis();
                this.mPowerStats.durationMs =
                        Math.max(timestampMillis - this.mLastUpdateTimestampMillis, 0L);
                this.mLastUpdateTimestampMillis = timestampMillis;
                this.mDeviceStats[this.mLayout.mDeviceSleepTimePosition] =
                        delta.getSleepTimeMillis();
                this.mDeviceStats[this.mLayout.mDeviceIdleTimePosition] = delta.getIdleTimeMillis();
                long asLong = getAsLong();
                long j = this.mLastCallDuration;
                if (asLong >= j) {
                    this.mDeviceStats[this.mLayout.mDeviceCallTimePosition] = asLong - j;
                }
                this.mLastCallDuration = asLong;
                long asLong2 = getAsLong();
                long j2 = this.mLastScanDuration;
                if (asLong2 >= j2) {
                    this.mDeviceStats[this.mLayout.mDeviceScanTimePosition] = asLong2 - j2;
                }
                this.mLastScanDuration = asLong2;
                SparseArray sparseArray2 = this.mPowerStats.stateStats;
                sparseArray2.clear();
                if (delta.getSpecificInfoLength() == 0) {
                    this.mLayout.addRxTxTimesForRat(
                            sparseArray2,
                            0,
                            0,
                            delta.getReceiveTimeMillis(),
                            delta.getTransmitTimeMillis());
                } else {
                    for (int i9 = 0; i9 < NETWORK_TYPES.length; i9++) {
                        if (i9 == 6) {
                            for (int i10 = 0; i10 < 5; i10++) {
                                this.mLayout.addRxTxTimesForRat(
                                        sparseArray2,
                                        i9,
                                        i10,
                                        delta.getReceiveTimeMillis(i9, i10),
                                        delta.getTransmitTimeMillis(i9, i10));
                            }
                        } else {
                            this.mLayout.addRxTxTimesForRat(
                                    sparseArray2,
                                    i9,
                                    0,
                                    delta.getReceiveTimeMillis(i9),
                                    delta.getTransmitTimeMillis(i9));
                        }
                    }
                }
            }
        }
        NetworkStats networkStats = (NetworkStats) this.mNetworkStatsSupplier.get();
        if (networkStats != null) {
            List computeDelta = BatteryStatsImpl.computeDelta(networkStats, this.mLastNetworkStats);
            this.mLastNetworkStats = networkStats;
            ArrayList arrayList = (ArrayList) computeDelta;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                BatteryStatsImpl.NetworkStatsDelta networkStatsDelta =
                        (BatteryStatsImpl.NetworkStatsDelta) arrayList.get(size);
                long j3 = networkStatsDelta.mRxBytes;
                long j4 = networkStatsDelta.mTxBytes;
                long j5 = networkStatsDelta.mRxPackets;
                long j6 = networkStatsDelta.mTxPackets;
                if (j3 != 0 || j4 != 0 || j5 != 0 || j6 != 0) {
                    int mapUid = this.mUidResolver.mapUid(networkStatsDelta.mUid);
                    long[] jArr2 = (long[]) this.mPowerStats.uidStats.get(mapUid);
                    if (jArr2 == null) {
                        long[] jArr3 = new long[this.mLayout.mUidStatsArrayLength];
                        this.mPowerStats.uidStats.put(mapUid, jArr3);
                        MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout7 = this.mLayout;
                        jArr3[mobileRadioPowerStatsLayout7.mUidRxBytesPosition] = j3;
                        jArr3[mobileRadioPowerStatsLayout7.mUidTxBytesPosition] = j4;
                        jArr3[mobileRadioPowerStatsLayout7.mUidRxPacketsPosition] = j5;
                        jArr3[mobileRadioPowerStatsLayout7.mUidTxPacketsPosition] = j6;
                    } else {
                        MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout8 = this.mLayout;
                        int i11 = mobileRadioPowerStatsLayout8.mUidRxBytesPosition;
                        jArr2[i11] = jArr2[i11] + j3;
                        int i12 = mobileRadioPowerStatsLayout8.mUidTxBytesPosition;
                        jArr2[i12] = jArr2[i12] + j4;
                        int i13 = mobileRadioPowerStatsLayout8.mUidRxPacketsPosition;
                        jArr2[i13] = jArr2[i13] + j5;
                        int i14 = mobileRadioPowerStatsLayout8.mUidTxPacketsPosition;
                        jArr2[i14] = jArr2[i14] + j6;
                    }
                }
            }
        }
        if (this.mEnergyConsumerIds.length != 0) {
            int asInt = this.mVoltageSupplier.getAsInt();
            if (asInt <= 0) {
                Slog.wtf(
                        "MobileRadioPowerStatsCollector",
                        "Unexpected battery voltage ("
                                + asInt
                                + " mV) when querying energy consumers");
            } else {
                int i15 = this.mLastVoltageMv;
                int i16 = i15 != 0 ? (i15 + asInt) / 2 : asInt;
                this.mLastVoltageMv = asInt;
                long[] consumedEnergyUws =
                        this.mConsumedEnergyRetriever.getConsumedEnergyUws(this.mEnergyConsumerIds);
                if (consumedEnergyUws != null) {
                    for (int length = consumedEnergyUws.length - 1; length >= 0; length--) {
                        long j7 = this.mLastConsumedEnergyUws[length];
                        long j8 = j7 != -1 ? consumedEnergyUws[length] - j7 : 0L;
                        if (j8 < 0) {
                            j8 = 0;
                        }
                        this.mLayout.setConsumedEnergy(
                                this.mPowerStats.stats,
                                length,
                                PowerStatsCollector.uJtoUc(i16, j8));
                        this.mLastConsumedEnergyUws[length] = consumedEnergyUws[length];
                    }
                }
            }
        }
        if (this.mPowerStats.durationMs == 0) {
            long elapsedRealtime = this.mClock.elapsedRealtime();
            this.mPowerStats.durationMs =
                    Math.max(elapsedRealtime - this.mLastUpdateTimestampMillis, 0L);
            this.mLastUpdateTimestampMillis = elapsedRealtime;
        }
        return this.mPowerStats;
    }
}
