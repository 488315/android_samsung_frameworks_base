package com.android.server.power.stats;

import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.UidTraffic;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.PersistableBundle;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class BluetoothPowerStatsCollector extends PowerStatsCollector {
    public BatteryStatsImpl.BluetoothStatsRetrieverImpl mBluetoothStatsRetriever;
    public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
    public long[] mDeviceStats;
    public int[] mEnergyConsumerIds;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;
    public boolean mIsInitialized;
    public long[] mLastConsumedEnergyUws;
    public long mLastIdleTime;
    public long mLastRxTime;
    public long mLastTxTime;
    public int mLastVoltageMv;
    public BluetoothPowerStatsLayout mLayout;
    public PowerStats mPowerStats;
    public final SparseArray mUidStats;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0 mVoltageSupplier;

    public final class UidStats {
        public long lastRxCount;
        public long lastScanTime;
        public long lastTxCount;
        public long rxCount;
        public long scanTime;
        public long txCount;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BluetoothPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            r0 = 2
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
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r7.mUidStats = r0
            r7.mInjector = r8
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.BluetoothPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector):void");
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final PowerStats collectStats() {
        BluetoothAdapter adapter;
        BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo = null;
        if (!this.mIsInitialized) {
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
            this.mBluetoothStatsRetriever = powerStatsCollectorInjector.mBluetoothStatsRetriever;
            int[] energyConsumerIds = consumedEnergyRetrieverImpl.getEnergyConsumerIds(1, null);
            this.mEnergyConsumerIds = energyConsumerIds;
            long[] jArr = new long[energyConsumerIds.length];
            this.mLastConsumedEnergyUws = jArr;
            Arrays.fill(jArr, -1L);
            BluetoothPowerStatsLayout bluetoothPowerStatsLayout = new BluetoothPowerStatsLayout();
            this.mLayout = bluetoothPowerStatsLayout;
            bluetoothPowerStatsLayout.mDeviceRxTimePosition =
                    bluetoothPowerStatsLayout.addDeviceSection(1, 0, "rx");
            bluetoothPowerStatsLayout.mDeviceTxTimePosition =
                    bluetoothPowerStatsLayout.addDeviceSection(1, 0, "tx");
            bluetoothPowerStatsLayout.mDeviceIdleTimePosition =
                    bluetoothPowerStatsLayout.addDeviceSection(1, 0, "idle");
            bluetoothPowerStatsLayout.mDeviceScanTimePosition =
                    bluetoothPowerStatsLayout.addDeviceSection(1, 1, "scan");
            this.mLayout.addDeviceSectionEnergyConsumers(this.mEnergyConsumerIds.length);
            BluetoothPowerStatsLayout bluetoothPowerStatsLayout2 = this.mLayout;
            bluetoothPowerStatsLayout2.mDeviceDurationPosition =
                    bluetoothPowerStatsLayout2.addDeviceSection(1, 1, "usage");
            this.mLayout.addDeviceSectionPowerEstimate();
            BluetoothPowerStatsLayout bluetoothPowerStatsLayout3 = this.mLayout;
            bluetoothPowerStatsLayout3.mUidRxBytesPosition =
                    bluetoothPowerStatsLayout3.addUidSection(1, 0, "rx-B");
            bluetoothPowerStatsLayout3.mUidTxBytesPosition =
                    bluetoothPowerStatsLayout3.addUidSection(1, 0, "tx-B");
            bluetoothPowerStatsLayout3.mUidScanTimePosition =
                    bluetoothPowerStatsLayout3.addUidSection(1, 1, "scan");
            BluetoothPowerStatsLayout bluetoothPowerStatsLayout4 = this.mLayout;
            bluetoothPowerStatsLayout4.mUidPowerEstimatePosition =
                    bluetoothPowerStatsLayout4.addUidSection(1, 5, "power");
            PersistableBundle persistableBundle = new PersistableBundle();
            this.mLayout.toExtras(persistableBundle);
            BluetoothPowerStatsLayout bluetoothPowerStatsLayout5 = this.mLayout;
            PowerStats powerStats =
                    new PowerStats(
                            new PowerStats.Descriptor(
                                    2,
                                    bluetoothPowerStatsLayout5.mDeviceStatsArrayLength,
                                    (SparseArray) null,
                                    0,
                                    bluetoothPowerStatsLayout5.mUidStatsArrayLength,
                                    persistableBundle));
            this.mPowerStats = powerStats;
            this.mDeviceStats = powerStats.stats;
            this.mIsInitialized = true;
        }
        this.mPowerStats.uidStats.clear();
        final CompletableFuture completableFuture = new CompletableFuture();
        BatteryStatsImpl.BluetoothStatsRetrieverImpl bluetoothStatsRetrieverImpl =
                this.mBluetoothStatsRetriever;
        SystemServerInitThreadPool$$ExternalSyntheticLambda0
                systemServerInitThreadPool$$ExternalSyntheticLambda0 =
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0();
        BluetoothAdapter.OnBluetoothActivityEnergyInfoCallback
                onBluetoothActivityEnergyInfoCallback =
                        new BluetoothAdapter
                                .OnBluetoothActivityEnergyInfoCallback() { // from class:
                                                                           // com.android.server.power.stats.BluetoothPowerStatsCollector.1
                            public final void onBluetoothActivityEnergyInfoAvailable(
                                    BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo2) {
                                completableFuture.complete(bluetoothActivityEnergyInfo2);
                            }

                            public final void onBluetoothActivityEnergyInfoError(int i) {
                                completableFuture.completeExceptionally(
                                        new RuntimeException(
                                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                        i, "error: ")));
                            }
                        };
        BluetoothManager bluetoothManager = bluetoothStatsRetrieverImpl.mBluetoothManager;
        if (bluetoothManager != null && (adapter = bluetoothManager.getAdapter()) != null) {
            adapter.requestControllerActivityEnergyInfo(
                    systemServerInitThreadPool$$ExternalSyntheticLambda0,
                    onBluetoothActivityEnergyInfoCallback);
            try {
                bluetoothActivityEnergyInfo =
                        (BluetoothActivityEnergyInfo)
                                completableFuture.get(20000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                Slog.e(
                        "BluetoothPowerStatsCollector",
                        "Cannot acquire BluetoothActivityEnergyInfo",
                        e);
            }
            if (bluetoothActivityEnergyInfo != null) {
                long controllerRxTimeMillis =
                        bluetoothActivityEnergyInfo.getControllerRxTimeMillis();
                long max = Math.max(0L, controllerRxTimeMillis - this.mLastRxTime);
                this.mDeviceStats[this.mLayout.mDeviceRxTimePosition] = max;
                this.mLastRxTime = controllerRxTimeMillis;
                long controllerTxTimeMillis =
                        bluetoothActivityEnergyInfo.getControllerTxTimeMillis();
                long max2 = Math.max(0L, controllerTxTimeMillis - this.mLastTxTime);
                this.mDeviceStats[this.mLayout.mDeviceTxTimePosition] = max2;
                this.mLastTxTime = controllerTxTimeMillis;
                long controllerIdleTimeMillis =
                        bluetoothActivityEnergyInfo.getControllerIdleTimeMillis();
                long max3 = Math.max(0L, controllerIdleTimeMillis - this.mLastIdleTime);
                this.mDeviceStats[this.mLayout.mDeviceIdleTimePosition] = max3;
                this.mLastIdleTime = controllerIdleTimeMillis;
                this.mPowerStats.durationMs = max + max2 + max3;
                List uidTraffic = bluetoothActivityEnergyInfo.getUidTraffic();
                for (int size = uidTraffic.size() - 1; size >= 0; size--) {
                    UidTraffic uidTraffic2 = (UidTraffic) uidTraffic.get(size);
                    int mapUid = this.mUidResolver.mapUid(uidTraffic2.getUid());
                    UidStats uidStats = (UidStats) this.mUidStats.get(mapUid);
                    if (uidStats == null) {
                        uidStats = new UidStats();
                        this.mUidStats.put(mapUid, uidStats);
                    }
                    uidStats.rxCount = uidTraffic2.getRxBytes() + uidStats.rxCount;
                    uidStats.txCount = uidTraffic2.getTxBytes() + uidStats.txCount;
                }
                for (int size2 = this.mUidStats.size() - 1; size2 >= 0; size2--) {
                    UidStats uidStats2 = (UidStats) this.mUidStats.valueAt(size2);
                    long max4 = Math.max(0L, uidStats2.rxCount - uidStats2.lastRxCount);
                    uidStats2.lastRxCount = uidStats2.rxCount;
                    uidStats2.rxCount = 0L;
                    long max5 = Math.max(0L, uidStats2.txCount - uidStats2.lastTxCount);
                    uidStats2.lastTxCount = uidStats2.txCount;
                    uidStats2.txCount = 0L;
                    if (max4 != 0 || max5 != 0) {
                        int keyAt = this.mUidStats.keyAt(size2);
                        long[] jArr2 = (long[]) this.mPowerStats.uidStats.get(keyAt);
                        if (jArr2 == null) {
                            jArr2 = new long[this.mLayout.mUidStatsArrayLength];
                            this.mPowerStats.uidStats.put(keyAt, jArr2);
                        }
                        BluetoothPowerStatsLayout bluetoothPowerStatsLayout6 = this.mLayout;
                        jArr2[bluetoothPowerStatsLayout6.mUidRxBytesPosition] = max4;
                        jArr2[bluetoothPowerStatsLayout6.mUidTxBytesPosition] = max5;
                    }
                }
            }
        }
        BatteryStatsImpl.BluetoothStatsRetrieverImpl bluetoothStatsRetrieverImpl2 =
                this.mBluetoothStatsRetriever;
        BluetoothPowerStatsCollector$$ExternalSyntheticLambda0
                bluetoothPowerStatsCollector$$ExternalSyntheticLambda0 =
                        new BluetoothPowerStatsCollector$$ExternalSyntheticLambda0(this);
        synchronized (BatteryStatsImpl.this) {
            BatteryStatsImpl.m835$$Nest$mretrieveBluetoothScanTimesLocked(
                    BatteryStatsImpl.this, bluetoothPowerStatsCollector$$ExternalSyntheticLambda0);
        }
        long j = 0;
        for (int size3 = this.mUidStats.size() - 1; size3 >= 0; size3--) {
            UidStats uidStats3 = (UidStats) this.mUidStats.valueAt(size3);
            long j2 = uidStats3.scanTime;
            if (j2 != 0) {
                long max6 = Math.max(0L, j2 - uidStats3.lastScanTime);
                uidStats3.lastScanTime = uidStats3.scanTime;
                uidStats3.scanTime = 0L;
                if (max6 != 0) {
                    int keyAt2 = this.mUidStats.keyAt(size3);
                    long[] jArr3 = (long[]) this.mPowerStats.uidStats.get(keyAt2);
                    if (jArr3 == null) {
                        jArr3 = new long[this.mLayout.mUidStatsArrayLength];
                        this.mPowerStats.uidStats.put(keyAt2, jArr3);
                    }
                    jArr3[this.mLayout.mUidScanTimePosition] = max6;
                    j += max6;
                }
            }
        }
        this.mDeviceStats[this.mLayout.mDeviceScanTimePosition] = j;
        if (this.mEnergyConsumerIds.length != 0) {
            int asInt = this.mVoltageSupplier.getAsInt();
            if (asInt <= 0) {
                Slog.wtf(
                        "BluetoothPowerStatsCollector",
                        "Unexpected battery voltage ("
                                + asInt
                                + " mV) when querying energy consumers");
            } else {
                int i = this.mLastVoltageMv;
                int i2 = i != 0 ? (i + asInt) / 2 : asInt;
                this.mLastVoltageMv = asInt;
                long[] consumedEnergyUws =
                        this.mConsumedEnergyRetriever.getConsumedEnergyUws(this.mEnergyConsumerIds);
                if (consumedEnergyUws != null) {
                    for (int length = consumedEnergyUws.length - 1; length >= 0; length--) {
                        long j3 = this.mLastConsumedEnergyUws[length];
                        long j4 = j3 != -1 ? consumedEnergyUws[length] - j3 : 0L;
                        if (j4 < 0) {
                            j4 = 0;
                        }
                        this.mLayout.setConsumedEnergy(
                                this.mPowerStats.stats, length, PowerStatsCollector.uJtoUc(i2, j4));
                        this.mLastConsumedEnergyUws[length] = consumedEnergyUws[length];
                    }
                }
            }
        }
        return this.mPowerStats;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final void onUidRemoved(int i) {
        this.mUidStats.remove(i);
    }
}
