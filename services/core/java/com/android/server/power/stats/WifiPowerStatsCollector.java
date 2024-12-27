package com.android.server.power.stats;

import android.net.NetworkStats;
import android.net.wifi.WifiManager;
import android.os.PersistableBundle;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WifiPowerStatsCollector extends PowerStatsCollector {
    public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
    public long[] mDeviceStats;
    public int[] mEnergyConsumerIds;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;
    public boolean mIsInitialized;
    public long[] mLastConsumedEnergyUws;
    public NetworkStats mLastNetworkStats;
    public final SparseArray mLastScanTimes;
    public int mLastVoltageMv;
    public long mLastWifiActiveDuration;
    public WifiActivityEnergyInfo mLastWifiActivityInfo;
    public WifiPowerStatsLayout mLayout;
    public volatile BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1
            mNetworkStatsSupplier;
    public boolean mPowerReportingSupported;
    public PowerStats mPowerStats;
    public final WifiScanTimes mScanTimes;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0 mVoltageSupplier;
    public volatile WifiManager mWifiManager;
    public volatile BatteryStatsImpl.AnonymousClass2 mWifiStatsRetriever;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WifiScanTimes {
        public long basicScanTimeMs;
        public long batchedScanTimeMs;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WifiPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r14) {
        /*
            r13 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            r0 = 11
            java.lang.String r0 = android.os.BatteryConsumer.powerComponentIdToString(r0)
            long r3 = r14.getPowerStatsCollectionThrottlePeriod(r0)
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r1 = r13
            r1.<init>(r2, r3, r5, r6)
            r0 = 0
            int[] r0 = new int[r0]
            r13.mEnergyConsumerIds = r0
            android.os.connectivity.WifiActivityEnergyInfo r0 = new android.os.connectivity.WifiActivityEnergyInfo
            r5 = 0
            r7 = 0
            r2 = 0
            r4 = 0
            r9 = 0
            r11 = 0
            r1 = r0
            r1.<init>(r2, r4, r5, r7, r9, r11)
            r13.mLastWifiActivityInfo = r0
            com.android.server.power.stats.WifiPowerStatsCollector$WifiScanTimes r0 = new com.android.server.power.stats.WifiPowerStatsCollector$WifiScanTimes
            r0.<init>()
            r13.mScanTimes = r0
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r13.mLastScanTimes = r0
            r13.mInjector = r14
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.WifiPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector):void");
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final PowerStats collectStats() {
        long totalTimeLocked;
        WifiActivityEnergyInfo wifiActivityEnergyInfo = null;
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
            this.mWifiManager = powerStatsCollectorInjector.mWifiManager;
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector2 =
                    this.mInjector;
            powerStatsCollectorInjector2.getClass();
            this.mNetworkStatsSupplier =
                    new BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1(
                            powerStatsCollectorInjector2, 1);
            this.mWifiStatsRetriever = BatteryStatsImpl.this.mWifiStatsRetriever;
            this.mPowerReportingSupported =
                    this.mWifiManager != null
                            && this.mWifiManager.isEnhancedPowerReportingSupported();
            int[] energyConsumerIds = this.mConsumedEnergyRetriever.getEnergyConsumerIds(6, null);
            this.mEnergyConsumerIds = energyConsumerIds;
            long[] jArr = new long[energyConsumerIds.length];
            this.mLastConsumedEnergyUws = jArr;
            Arrays.fill(jArr, -1L);
            WifiPowerStatsLayout wifiPowerStatsLayout = new WifiPowerStatsLayout();
            this.mLayout = wifiPowerStatsLayout;
            boolean z = this.mPowerReportingSupported;
            wifiPowerStatsLayout.mPowerReportingSupported = z;
            if (z) {
                wifiPowerStatsLayout.mDeviceActiveTimePosition = -1;
                wifiPowerStatsLayout.mDeviceRxTimePosition =
                        wifiPowerStatsLayout.addDeviceSection(1, 0, "rx");
                wifiPowerStatsLayout.mDeviceTxTimePosition =
                        wifiPowerStatsLayout.addDeviceSection(1, 0, "tx");
                wifiPowerStatsLayout.mDeviceIdleTimePosition =
                        wifiPowerStatsLayout.addDeviceSection(1, 0, "idle");
                wifiPowerStatsLayout.mDeviceScanTimePosition =
                        wifiPowerStatsLayout.addDeviceSection(1, 0, "scan");
            } else {
                wifiPowerStatsLayout.mDeviceActiveTimePosition =
                        wifiPowerStatsLayout.addDeviceSection(1, 0, "rx-tx");
                wifiPowerStatsLayout.mDeviceRxTimePosition = -1;
                wifiPowerStatsLayout.mDeviceTxTimePosition = -1;
                wifiPowerStatsLayout.mDeviceIdleTimePosition = -1;
                wifiPowerStatsLayout.mDeviceScanTimePosition = -1;
            }
            wifiPowerStatsLayout.mDeviceBasicScanTimePosition =
                    wifiPowerStatsLayout.addDeviceSection(1, 1, "basic-scan");
            wifiPowerStatsLayout.mDeviceBatchedScanTimePosition =
                    wifiPowerStatsLayout.addDeviceSection(1, 1, "batched-scan");
            this.mLayout.addDeviceSectionEnergyConsumers(this.mEnergyConsumerIds.length);
            WifiPowerStatsLayout wifiPowerStatsLayout2 = this.mLayout;
            wifiPowerStatsLayout2.mUidRxPacketsPosition =
                    wifiPowerStatsLayout2.addUidSection(1, 0, "rx-pkts");
            wifiPowerStatsLayout2.mUidRxBytesPosition =
                    wifiPowerStatsLayout2.addUidSection(1, 0, "rx-B");
            wifiPowerStatsLayout2.mUidTxPacketsPosition =
                    wifiPowerStatsLayout2.addUidSection(1, 0, "tx-pkts");
            wifiPowerStatsLayout2.mUidTxBytesPosition =
                    wifiPowerStatsLayout2.addUidSection(1, 0, "tx-B");
            wifiPowerStatsLayout2.mUidScanTimePosition =
                    wifiPowerStatsLayout2.addUidSection(1, 1, "scan");
            wifiPowerStatsLayout2.mUidBatchScanTimePosition =
                    wifiPowerStatsLayout2.addUidSection(1, 1, "batched-scan");
            WifiPowerStatsLayout wifiPowerStatsLayout3 = this.mLayout;
            wifiPowerStatsLayout3.mDeviceDurationPosition =
                    wifiPowerStatsLayout3.addDeviceSection(1, 1, "usage");
            this.mLayout.addDeviceSectionPowerEstimate();
            WifiPowerStatsLayout wifiPowerStatsLayout4 = this.mLayout;
            wifiPowerStatsLayout4.mUidPowerEstimatePosition =
                    wifiPowerStatsLayout4.addUidSection(1, 5, "power");
            PersistableBundle persistableBundle = new PersistableBundle();
            this.mLayout.toExtras(persistableBundle);
            WifiPowerStatsLayout wifiPowerStatsLayout5 = this.mLayout;
            PowerStats powerStats =
                    new PowerStats(
                            new PowerStats.Descriptor(
                                    11,
                                    wifiPowerStatsLayout5.mDeviceStatsArrayLength,
                                    (SparseArray) null,
                                    0,
                                    wifiPowerStatsLayout5.mUidStatsArrayLength,
                                    persistableBundle));
            this.mPowerStats = powerStats;
            this.mDeviceStats = powerStats.stats;
            this.mIsInitialized = true;
        }
        if (this.mPowerReportingSupported) {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mWifiManager.getWifiActivityEnergyInfoAsync(
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new WifiManager
                            .OnWifiActivityEnergyInfoListener() { // from class:
                                                                  // com.android.server.power.stats.WifiPowerStatsCollector$$ExternalSyntheticLambda0
                        public final void onWifiActivityEnergyInfo(
                                WifiActivityEnergyInfo wifiActivityEnergyInfo2) {
                            completableFuture.complete(wifiActivityEnergyInfo2);
                        }
                    });
            try {
                wifiActivityEnergyInfo =
                        (WifiActivityEnergyInfo)
                                completableFuture.get(20000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                Slog.e("WifiPowerStatsCollector", "Cannot acquire WifiActivityEnergyInfo", e);
            }
            if (wifiActivityEnergyInfo != null) {
                long controllerRxDurationMillis =
                        wifiActivityEnergyInfo.getControllerRxDurationMillis()
                                - this.mLastWifiActivityInfo.getControllerRxDurationMillis();
                long controllerTxDurationMillis =
                        wifiActivityEnergyInfo.getControllerTxDurationMillis()
                                - this.mLastWifiActivityInfo.getControllerTxDurationMillis();
                long controllerScanDurationMillis =
                        wifiActivityEnergyInfo.getControllerScanDurationMillis()
                                - this.mLastWifiActivityInfo.getControllerScanDurationMillis();
                long controllerIdleDurationMillis =
                        wifiActivityEnergyInfo.getControllerIdleDurationMillis()
                                - this.mLastWifiActivityInfo.getControllerIdleDurationMillis();
                WifiPowerStatsLayout wifiPowerStatsLayout6 = this.mLayout;
                long[] jArr2 = this.mDeviceStats;
                jArr2[wifiPowerStatsLayout6.mDeviceRxTimePosition] = controllerRxDurationMillis;
                jArr2[wifiPowerStatsLayout6.mDeviceTxTimePosition] = controllerTxDurationMillis;
                jArr2[wifiPowerStatsLayout6.mDeviceScanTimePosition] = controllerScanDurationMillis;
                jArr2[wifiPowerStatsLayout6.mDeviceIdleTimePosition] = controllerIdleDurationMillis;
                this.mPowerStats.durationMs =
                        controllerRxDurationMillis
                                + controllerTxDurationMillis
                                + controllerScanDurationMillis
                                + controllerIdleDurationMillis;
                this.mLastWifiActivityInfo = wifiActivityEnergyInfo;
            }
        } else {
            BatteryStatsImpl.AnonymousClass2 anonymousClass2 = this.mWifiStatsRetriever;
            synchronized (BatteryStatsImpl.this) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                totalTimeLocked =
                        batteryStatsImpl.mGlobalWifiRunningTimer.getTotalTimeLocked(
                                        batteryStatsImpl.mClock.elapsedRealtime() * 1000, 0)
                                / 1000;
            }
            this.mDeviceStats[this.mLayout.mDeviceActiveTimePosition] =
                    Math.max(0L, totalTimeLocked - this.mLastWifiActiveDuration);
            this.mLastWifiActiveDuration = totalTimeLocked;
            this.mPowerStats.durationMs = totalTimeLocked;
        }
        this.mPowerStats.uidStats.clear();
        NetworkStats networkStats = (NetworkStats) this.mNetworkStatsSupplier.get();
        if (networkStats != null) {
            List computeDelta = BatteryStatsImpl.computeDelta(networkStats, this.mLastNetworkStats);
            this.mLastNetworkStats = networkStats;
            ArrayList arrayList = (ArrayList) computeDelta;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                BatteryStatsImpl.NetworkStatsDelta networkStatsDelta =
                        (BatteryStatsImpl.NetworkStatsDelta) arrayList.get(size);
                long j = networkStatsDelta.mRxBytes;
                long j2 = networkStatsDelta.mTxBytes;
                long j3 = networkStatsDelta.mRxPackets;
                long j4 = networkStatsDelta.mTxPackets;
                if (j != 0 || j2 != 0 || j3 != 0 || j4 != 0) {
                    int mapUid = this.mUidResolver.mapUid(networkStatsDelta.mUid);
                    long[] jArr3 = (long[]) this.mPowerStats.uidStats.get(mapUid);
                    if (jArr3 == null) {
                        long[] jArr4 = new long[this.mLayout.mUidStatsArrayLength];
                        this.mPowerStats.uidStats.put(mapUid, jArr4);
                        WifiPowerStatsLayout wifiPowerStatsLayout7 = this.mLayout;
                        jArr4[wifiPowerStatsLayout7.mUidRxBytesPosition] = j;
                        jArr4[wifiPowerStatsLayout7.mUidTxBytesPosition] = j2;
                        jArr4[wifiPowerStatsLayout7.mUidRxPacketsPosition] = j3;
                        jArr4[wifiPowerStatsLayout7.mUidTxPacketsPosition] = j4;
                    } else {
                        WifiPowerStatsLayout wifiPowerStatsLayout8 = this.mLayout;
                        int i = wifiPowerStatsLayout8.mUidRxBytesPosition;
                        jArr3[i] = jArr3[i] + j;
                        int i2 = wifiPowerStatsLayout8.mUidTxBytesPosition;
                        jArr3[i2] = jArr3[i2] + j2;
                        int i3 = wifiPowerStatsLayout8.mUidRxPacketsPosition;
                        jArr3[i3] = jArr3[i3] + j3;
                        int i4 = wifiPowerStatsLayout8.mUidTxPacketsPosition;
                        jArr3[i4] = jArr3[i4] + j4;
                    }
                }
            }
        }
        WifiScanTimes wifiScanTimes = this.mScanTimes;
        wifiScanTimes.basicScanTimeMs = 0L;
        wifiScanTimes.batchedScanTimeMs = 0L;
        BatteryStatsImpl.AnonymousClass2 anonymousClass22 = this.mWifiStatsRetriever;
        WifiPowerStatsCollector$$ExternalSyntheticLambda1
                wifiPowerStatsCollector$$ExternalSyntheticLambda1 =
                        new WifiPowerStatsCollector$$ExternalSyntheticLambda1(this);
        synchronized (BatteryStatsImpl.this) {
            BatteryStatsImpl.m836$$Nest$mretrieveWifiScanTimesLocked(
                    BatteryStatsImpl.this, wifiPowerStatsCollector$$ExternalSyntheticLambda1);
        }
        WifiPowerStatsLayout wifiPowerStatsLayout9 = this.mLayout;
        long[] jArr5 = this.mDeviceStats;
        WifiScanTimes wifiScanTimes2 = this.mScanTimes;
        jArr5[wifiPowerStatsLayout9.mDeviceBasicScanTimePosition] = wifiScanTimes2.basicScanTimeMs;
        jArr5[wifiPowerStatsLayout9.mDeviceBatchedScanTimePosition] =
                wifiScanTimes2.batchedScanTimeMs;
        if (this.mEnergyConsumerIds.length != 0) {
            int asInt = this.mVoltageSupplier.getAsInt();
            if (asInt <= 0) {
                Slog.wtf(
                        "WifiPowerStatsCollector",
                        "Unexpected battery voltage ("
                                + asInt
                                + " mV) when querying energy consumers");
            } else {
                int i5 = this.mLastVoltageMv;
                int i6 = i5 != 0 ? (i5 + asInt) / 2 : asInt;
                this.mLastVoltageMv = asInt;
                long[] consumedEnergyUws =
                        this.mConsumedEnergyRetriever.getConsumedEnergyUws(this.mEnergyConsumerIds);
                if (consumedEnergyUws != null) {
                    for (int length = consumedEnergyUws.length - 1; length >= 0; length--) {
                        long j5 = this.mLastConsumedEnergyUws[length];
                        long j6 = j5 != -1 ? consumedEnergyUws[length] - j5 : 0L;
                        if (j6 < 0) {
                            j6 = 0;
                        }
                        this.mLayout.setConsumedEnergy(
                                this.mPowerStats.stats, length, PowerStatsCollector.uJtoUc(i6, j6));
                        this.mLastConsumedEnergyUws[length] = consumedEnergyUws[length];
                    }
                }
            }
        }
        return this.mPowerStats;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final void onUidRemoved(int i) {
        this.mLastScanTimes.remove(i);
    }
}
