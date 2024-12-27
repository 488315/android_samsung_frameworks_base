package com.android.server.power.stats;

import android.os.BatteryConsumer;
import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WifiPowerCalculator extends PowerCalculator {
    public static final BatteryConsumer.Key[] UNINITIALIZED_KEYS = new BatteryConsumer.Key[0];
    public final UsageBasedPowerEstimator mBatchScanPowerEstimator;
    public final boolean mHasWifiPowerController;
    public final UsageBasedPowerEstimator mIdlePowerEstimator;
    public final UsageBasedPowerEstimator mPowerOnPowerEstimator;
    public final UsageBasedPowerEstimator mRxPowerEstimator;
    public final UsageBasedPowerEstimator mScanPowerEstimator;
    public final UsageBasedPowerEstimator mTxPowerEstimator;
    public final double mWifiPowerPerPacket;

    public WifiPowerCalculator(PowerProfile powerProfile) {
        this.mPowerOnPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.on"));
        this.mScanPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.scan"));
        this.mBatchScanPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.batchedscan"));
        UsageBasedPowerEstimator usageBasedPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.idle"));
        this.mIdlePowerEstimator = usageBasedPowerEstimator;
        UsageBasedPowerEstimator usageBasedPowerEstimator2 = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.tx"));
        this.mTxPowerEstimator = usageBasedPowerEstimator2;
        UsageBasedPowerEstimator usageBasedPowerEstimator3 = new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.rx"));
        this.mRxPowerEstimator = usageBasedPowerEstimator3;
        this.mWifiPowerPerPacket = (powerProfile.getAveragePower("wifi.active") / 3600.0d) / 61.03515625d;
        this.mHasWifiPowerController = (usageBasedPowerEstimator.mAveragePowerMahPerMs == 0.0d || usageBasedPowerEstimator2.mAveragePowerMahPerMs == 0.0d || usageBasedPowerEstimator3.mAveragePowerMahPerMs == 0.0d) ? false : true;
    }

    public final double calcPowerFromControllerDataMah(long j, long j2, long j3) {
        return (this.mIdlePowerEstimator.mAveragePowerMahPerMs * j3) + (this.mTxPowerEstimator.mAveragePowerMahPerMs * j2) + (this.mRxPowerEstimator.mAveragePowerMahPerMs * j);
    }

    public final double calcPowerWithoutControllerDataMah(long j, long j2, long j3, long j4, long j5) {
        return (this.mBatchScanPowerEstimator.mAveragePowerMahPerMs * j5) + (this.mScanPowerEstimator.mAveragePowerMahPerMs * j4) + (this.mPowerOnPowerEstimator.mAveragePowerMahPerMs * j3) + ((j + j2) * this.mWifiPowerPerPacket);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0172  */
    @Override // com.android.server.power.stats.PowerCalculator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculate(android.os.BatteryUsageStats.Builder r43, android.os.BatteryStats r44, long r45, long r47, android.os.BatteryUsageStatsQuery r49) {
        /*
            Method dump skipped, instructions count: 627
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.WifiPowerCalculator.calculate(android.os.BatteryUsageStats$Builder, android.os.BatteryStats, long, long, android.os.BatteryUsageStatsQuery):void");
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 11;
    }
}
