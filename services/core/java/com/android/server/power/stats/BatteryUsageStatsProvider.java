package com.android.server.power.stats;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import com.android.internal.os.Clock;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerProfile;
import com.android.server.power.stats.PowerStatsSpan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BatteryUsageStatsProvider {
    public static boolean sErrorReported;
    public final Clock mClock;
    public final Context mContext;
    public final CpuScalingPolicies mCpuScalingPolicies;
    public List mPowerCalculators;
    public final PowerProfile mPowerProfile;
    public final PowerStatsExporter mPowerStatsExporter;
    public final PowerStatsStore mPowerStatsStore;
    public final SparseBooleanArray mPowerStatsExporterEnabled = new SparseBooleanArray();
    public final Object mLock = new Object();

    public BatteryUsageStatsProvider(Context context, PowerStatsExporter powerStatsExporter, PowerProfile powerProfile, CpuScalingPolicies cpuScalingPolicies, PowerStatsStore powerStatsStore, Clock clock) {
        this.mContext = context;
        this.mPowerStatsExporter = powerStatsExporter;
        this.mPowerStatsStore = powerStatsStore;
        this.mPowerProfile = powerProfile;
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mClock = clock;
    }

    public final BatteryUsageStats getBatteryUsageStats(BatteryStatsImpl batteryStatsImpl, BatteryUsageStatsQuery batteryUsageStatsQuery, long j) {
        long j2;
        long j3;
        final BatteryUsageStats.Builder builder;
        int i;
        boolean z;
        BatteryUsageStats.Builder builder2;
        int i2;
        long j4;
        long j5;
        boolean z2;
        BatteryUsageStats.Builder builder3;
        BatteryStats.Uid uid;
        long j6;
        if (batteryUsageStatsQuery.getToTimestamp() != 0) {
            boolean z3 = (batteryUsageStatsQuery.getFlags() & 4) != 0;
            boolean z4 = (batteryUsageStatsQuery.getFlags() & 8) != 0 && batteryStatsImpl.isProcessStateDataAvailable();
            double minConsumedPowerThreshold = batteryUsageStatsQuery.getMinConsumedPowerThreshold();
            String[] customEnergyConsumerNames = batteryStatsImpl.getCustomEnergyConsumerNames();
            BatteryUsageStats.Builder builder4 = new BatteryUsageStats.Builder(customEnergyConsumerNames, z3, z4, batteryUsageStatsQuery.isScreenStateDataNeeded(), batteryUsageStatsQuery.isPowerStateDataNeeded(), minConsumedPowerThreshold);
            PowerStatsStore powerStatsStore = this.mPowerStatsStore;
            if (powerStatsStore == null) {
                Log.e("BatteryUsageStatsProv", "PowerStatsStore is unavailable");
                return builder4.build();
            }
            for (PowerStatsSpan.Metadata metadata : powerStatsStore.getTableOfContents()) {
                if (((ArrayList) metadata.mSections).contains("battery-usage-stats")) {
                    Iterator it = ((ArrayList) metadata.mTimeFrames).iterator();
                    long j7 = Long.MAX_VALUE;
                    long j8 = 0;
                    while (it.hasNext()) {
                        PowerStatsSpan.TimeFrame timeFrame = (PowerStatsSpan.TimeFrame) it.next();
                        long j9 = timeFrame.startTime + timeFrame.duration;
                        j7 = Math.min(j7, j9);
                        j8 = Math.max(j8, j9);
                        builder4 = builder4;
                        z4 = z4;
                    }
                    BatteryUsageStats.Builder builder5 = builder4;
                    boolean z5 = z4;
                    if ((batteryUsageStatsQuery.getFromTimestamp() == 0 || j7 > batteryUsageStatsQuery.getFromTimestamp()) && (batteryUsageStatsQuery.getToTimestamp() == 0 || j8 <= batteryUsageStatsQuery.getToTimestamp())) {
                        PowerStatsSpan loadPowerStatsSpan = powerStatsStore.loadPowerStatsSpan(metadata.mId, "battery-usage-stats");
                        if (loadPowerStatsSpan != null) {
                            Iterator it2 = ((ArrayList) loadPowerStatsSpan.mSections).iterator();
                            while (it2.hasNext()) {
                                BatteryUsageStats batteryUsageStats = ((BatteryUsageStatsSection) ((PowerStatsSpan.Section) it2.next())).mBatteryUsageStats;
                                if (!Arrays.equals(batteryUsageStats.getCustomPowerComponentNames(), customEnergyConsumerNames)) {
                                    Log.w("BatteryUsageStatsProv", "Ignoring older BatteryUsageStats snapshot, which has different custom power components: " + Arrays.toString(batteryUsageStats.getCustomPowerComponentNames()));
                                } else if (!z5 || batteryUsageStats.isProcessStateDataIncluded()) {
                                    BatteryUsageStats.Builder builder6 = builder5;
                                    builder6.add(batteryUsageStats);
                                    builder5 = builder6;
                                } else {
                                    Log.w("BatteryUsageStatsProv", "Ignoring older BatteryUsageStats snapshot, which  does not include process state data");
                                }
                            }
                        }
                    }
                    builder4 = builder5;
                    z4 = z5;
                }
            }
            return builder4.build();
        }
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        long uptimeMillis = this.mClock.uptimeMillis() * 1000;
        boolean z6 = (batteryUsageStatsQuery.getFlags() & 4) != 0;
        boolean z7 = (batteryUsageStatsQuery.getFlags() & 8) != 0 && batteryStatsImpl.isProcessStateDataAvailable();
        boolean z8 = (batteryUsageStatsQuery.getFlags() & 16) != 0;
        double minConsumedPowerThreshold2 = batteryUsageStatsQuery.getMinConsumedPowerThreshold();
        synchronized (batteryStatsImpl) {
            try {
                j2 = batteryStatsImpl.mMonotonicStartTime;
                long j10 = batteryStatsImpl.mMonotonicEndTime;
                BatteryUsageStats.Builder builder7 = new BatteryUsageStats.Builder(batteryStatsImpl.getCustomEnergyConsumerNames(), z6, z7, batteryUsageStatsQuery.isScreenStateDataNeeded(), batteryUsageStatsQuery.isPowerStateDataNeeded(), minConsumedPowerThreshold2);
                builder7.setStatsStartTimestamp(batteryStatsImpl.getStartClockTime());
                builder7.setStatsEndTimestamp(j);
                SparseArray sparseArray = batteryStatsImpl.mUidStats;
                int size = sparseArray.size() - 1;
                while (size >= 0) {
                    BatteryStats.Uid uid2 = (BatteryStats.Uid) sparseArray.valueAt(size);
                    if (z8 || uid2.getUid() != 1090) {
                        z2 = z8;
                        UidBatteryConsumer.Builder timeInProcessStateMs = builder7.getOrCreateUidBatteryConsumerBuilder(uid2).setTimeInProcessStateMs(2, uid2.getProcessStateTime(3, elapsedRealtime, 0) / 1000);
                        BatteryUsageStats.Builder builder8 = builder7;
                        long processStateTime = uid2.getProcessStateTime(0, elapsedRealtime, 0);
                        BatteryStats.Timer foregroundActivityTimer = uid2.getForegroundActivityTimer();
                        if (foregroundActivityTimer != null) {
                            builder3 = builder8;
                            long totalTimeLocked = foregroundActivityTimer.getTotalTimeLocked(elapsedRealtime, 0);
                            uid = uid2;
                            j6 = totalTimeLocked;
                        } else {
                            builder3 = builder8;
                            uid = uid2;
                            j6 = 0;
                        }
                        BatteryStats.Uid uid3 = uid;
                        timeInProcessStateMs.setTimeInProcessStateMs(1, (uid3.getProcessStateTime(2, elapsedRealtime, 0) + Math.min(processStateTime, j6)) / 1000).setTimeInProcessStateMs(3, uid3.getProcessStateTime(1, elapsedRealtime, 0) / 1000);
                    } else {
                        z2 = z8;
                        builder3 = builder7;
                    }
                    size--;
                    z8 = z2;
                    builder7 = builder3;
                }
                BatteryUsageStats.Builder builder9 = builder7;
                int i3 = 0;
                int[] powerComponents = batteryUsageStatsQuery.getPowerComponents();
                List powerCalculators = getPowerCalculators();
                int size2 = powerCalculators.size();
                int i4 = 0;
                while (i4 < size2) {
                    PowerCalculator powerCalculator = (PowerCalculator) powerCalculators.get(i4);
                    if (powerComponents != null) {
                        int length = powerComponents.length;
                        for (int i5 = i3; i5 < length; i5++) {
                            if (!powerCalculator.isPowerComponentSupported(powerComponents[i5])) {
                            }
                        }
                        i2 = i4;
                        j4 = j10;
                        j5 = elapsedRealtime;
                        builder2 = builder9;
                        i4 = i2 + 1;
                        builder9 = builder2;
                        j10 = j4;
                        elapsedRealtime = j5;
                        i3 = 0;
                    }
                    builder2 = builder9;
                    i2 = i4;
                    j4 = j10;
                    j5 = elapsedRealtime;
                    powerCalculator.calculate(builder2, batteryStatsImpl, elapsedRealtime, uptimeMillis, batteryUsageStatsQuery);
                    i4 = i2 + 1;
                    builder9 = builder2;
                    j10 = j4;
                    elapsedRealtime = j5;
                    i3 = 0;
                }
                j3 = j10;
                builder = builder9;
                if ((batteryUsageStatsQuery.getFlags() & 2) != 0) {
                    builder.setBatteryHistory(batteryStatsImpl.mHistory.copy());
                }
            } finally {
            }
        }
        if (this.mPowerStatsExporterEnabled.indexOfValue(true) >= 0) {
            final PowerStatsExporter powerStatsExporter = this.mPowerStatsExporter;
            synchronized (powerStatsExporter) {
                try {
                    List tableOfContents = powerStatsExporter.mPowerStatsStore.getTableOfContents();
                    int size3 = tableOfContents.size() - 1;
                    long j11 = j2;
                    boolean z9 = false;
                    while (size3 >= 0) {
                        PowerStatsSpan.Metadata metadata2 = (PowerStatsSpan.Metadata) tableOfContents.get(size3);
                        if (((ArrayList) metadata2.mSections).contains("aggregated-power-stats")) {
                            List list = metadata2.mTimeFrames;
                            long j12 = Long.MIN_VALUE;
                            int i6 = 0;
                            long j13 = Long.MAX_VALUE;
                            while (true) {
                                ArrayList arrayList = (ArrayList) list;
                                if (i6 >= arrayList.size()) {
                                    break;
                                }
                                PowerStatsSpan.TimeFrame timeFrame2 = (PowerStatsSpan.TimeFrame) arrayList.get(i6);
                                int i7 = size3;
                                boolean z10 = z9;
                                long j14 = timeFrame2.startMonotonicTime;
                                PowerStatsSpan.Metadata metadata3 = metadata2;
                                List list2 = list;
                                long j15 = timeFrame2.duration + j14;
                                if (j14 < j13) {
                                    j13 = j14;
                                }
                                if (j15 > j12) {
                                    j12 = j15;
                                }
                                i6++;
                                size3 = i7;
                                z9 = z10;
                                metadata2 = metadata3;
                                list = list2;
                            }
                            i = size3;
                            z = z9;
                            PowerStatsSpan.Metadata metadata4 = metadata2;
                            if (j13 >= j2 && j12 < j3) {
                                if (j12 > j11) {
                                    j11 = j12;
                                }
                                PowerStatsSpan loadPowerStatsSpan2 = powerStatsExporter.mPowerStatsStore.loadPowerStatsSpan(metadata4.mId, "aggregated-power-stats");
                                if (loadPowerStatsSpan2 == null) {
                                    Slog.e("PowerStatsExporter", "Could not read PowerStatsStore section " + metadata4);
                                } else {
                                    List list3 = loadPowerStatsSpan2.mSections;
                                    z9 = z;
                                    int i8 = 0;
                                    while (true) {
                                        ArrayList arrayList2 = (ArrayList) list3;
                                        if (i8 < arrayList2.size()) {
                                            PowerStatsExporter.populateBatteryUsageStatsBuilder(builder, ((AggregatedPowerStatsSection) ((PowerStatsSpan.Section) arrayList2.get(i8))).mAggregatedPowerStats);
                                            i8++;
                                            z9 = true;
                                        }
                                    }
                                    size3 = i - 1;
                                }
                            }
                        } else {
                            i = size3;
                            z = z9;
                        }
                        z9 = z;
                        size3 = i - 1;
                    }
                    if (!z9 || j11 < j3 - powerStatsExporter.mBatterySessionTimeSpanSlackMillis) {
                        powerStatsExporter.mPowerStatsAggregator.aggregatePowerStats(j11, j3, new Consumer() { // from class: com.android.server.power.stats.PowerStatsExporter$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PowerStatsExporter.this.getClass();
                                PowerStatsExporter.populateBatteryUsageStatsBuilder(builder, (AggregatedPowerStats) obj);
                            }
                        });
                    }
                    PowerStatsAggregator powerStatsAggregator = powerStatsExporter.mPowerStatsAggregator;
                    synchronized (powerStatsAggregator) {
                        powerStatsAggregator.mStats = null;
                    }
                } finally {
                }
            }
        }
        BatteryUsageStats build = builder.build();
        if (z7 && !sErrorReported) {
            int[] iArr = {1, 8, 11, 2};
            int i9 = 4;
            int[] iArr2 = {1, 2, 3, 4};
            Iterator it3 = build.getUidBatteryConsumers().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                UidBatteryConsumer uidBatteryConsumer = (UidBatteryConsumer) it3.next();
                int i10 = 0;
                while (i10 < i9) {
                    int i11 = iArr[i10];
                    double consumedPower = uidBatteryConsumer.getConsumedPower(uidBatteryConsumer.getKey(i11));
                    double d = 0.0d;
                    int i12 = 0;
                    while (i12 < i9) {
                        d += uidBatteryConsumer.getConsumedPower(uidBatteryConsumer.getKey(i11, iArr2[i12]));
                        i12++;
                        i9 = 4;
                    }
                    if (d > 2.0d + consumedPower) {
                        String str = "Sum of states exceeds total. UID = " + uidBatteryConsumer.getUid() + " " + BatteryConsumer.powerComponentIdToString(i11) + " total = " + consumedPower + " states = " + d;
                        if (sErrorReported) {
                            Slog.e("BatteryUsageStatsProv", str);
                        } else {
                            Slog.wtf("BatteryUsageStatsProv", str);
                            sErrorReported = true;
                        }
                    } else {
                        i10++;
                        i9 = 4;
                    }
                }
            }
        }
        return build;
    }

    public final List getPowerCalculators() {
        synchronized (this.mLock) {
            try {
                if (this.mPowerCalculators == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mPowerCalculators = arrayList;
                    arrayList.add(new BatteryChargeCalculator(this.mPowerProfile));
                    if (!this.mPowerStatsExporterEnabled.get(1)) {
                        this.mPowerCalculators.add(new CpuPowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile));
                    }
                    this.mPowerCalculators.add(new MemoryPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new WakelockPowerCalculator(this.mPowerProfile));
                    if (!BatteryStats.checkWifiOnly(this.mContext)) {
                        if (!this.mPowerStatsExporterEnabled.get(8)) {
                            this.mPowerCalculators.add(new MobileRadioPowerCalculator(this.mPowerProfile));
                        }
                        if (!this.mPowerStatsExporterEnabled.get(14)) {
                            this.mPowerCalculators.add(new PhonePowerCalculator(this.mPowerProfile));
                        }
                    }
                    if (!this.mPowerStatsExporterEnabled.get(11)) {
                        this.mPowerCalculators.add(new WifiPowerCalculator(this.mPowerProfile));
                    }
                    if (!this.mPowerStatsExporterEnabled.get(2)) {
                        this.mPowerCalculators.add(new BluetoothPowerCalculator(this.mPowerProfile));
                    }
                    this.mPowerCalculators.add(new SensorPowerCalculator((SensorManager) this.mContext.getSystemService(SensorManager.class)));
                    if (!this.mPowerStatsExporterEnabled.get(10)) {
                        this.mPowerCalculators.add(new GnssPowerCalculator(this.mPowerProfile));
                    }
                    if (!this.mPowerStatsExporterEnabled.get(3)) {
                        this.mPowerCalculators.add(new CameraPowerCalculator(this.mPowerProfile));
                    }
                    if (!this.mPowerStatsExporterEnabled.get(6)) {
                        this.mPowerCalculators.add(new FlashlightPowerCalculator(this.mPowerProfile));
                    }
                    if (!this.mPowerStatsExporterEnabled.get(4)) {
                        this.mPowerCalculators.add(new AudioPowerCalculator(this.mPowerProfile));
                    }
                    if (!this.mPowerStatsExporterEnabled.get(5)) {
                        this.mPowerCalculators.add(new VideoPowerCalculator(this.mPowerProfile));
                    }
                    this.mPowerCalculators.add(new ScreenPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new AmbientDisplayPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new IdlePowerCalculator(this.mPowerProfile));
                    if (!this.mPowerStatsExporterEnabled.get(-1)) {
                        this.mPowerCalculators.add(new CustomEnergyConsumerPowerCalculator());
                    }
                    this.mPowerCalculators.add(new PowerSharingCalculator());
                    this.mPowerCalculators.add(new UserPowerCalculator());
                    if (!Flags.disableSystemServicePowerAttr()) {
                        this.mPowerCalculators.add(new SystemServicePowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mPowerCalculators;
    }

    public final void setPowerStatsExporterEnabled(int i, boolean z) {
        this.mPowerStatsExporterEnabled.put(i, z);
        PowerStatsAggregator powerStatsAggregator = this.mPowerStatsExporter.mPowerStatsAggregator;
        synchronized (powerStatsAggregator) {
            try {
                if (powerStatsAggregator.mStats != null) {
                    powerStatsAggregator.mStats = null;
                }
                powerStatsAggregator.mEnabledComponents.put(i, z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
