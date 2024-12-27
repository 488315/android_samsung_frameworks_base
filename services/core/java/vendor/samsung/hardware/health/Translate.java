package vendor.samsung.hardware.health;

import android.hardware.health.DiskStats;
import android.hardware.health.StorageInfo;
import android.hardware.health.V2_1.HealthInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Translate {
    public static SehHealthInfo h2saTranslate(
            vendor.samsung.hardware.health.V2_0.SehHealthInfo sehHealthInfo) {
        SehHealthInfo sehHealthInfo2 = new SehHealthInfo();
        HealthInfo healthInfo = sehHealthInfo.legacy;
        android.hardware.health.HealthInfo healthInfo2 = new android.hardware.health.HealthInfo();
        android.hardware.health.Translate.h2aTranslateInternal(
                healthInfo2, healthInfo.legacy.legacy);
        android.hardware.health.V2_0.HealthInfo healthInfo3 = healthInfo.legacy;
        healthInfo2.batteryCurrentAverageMicroamps = healthInfo3.batteryCurrentAverage;
        healthInfo2.diskStats = new DiskStats[healthInfo3.diskStats.size()];
        for (int i = 0; i < healthInfo.legacy.diskStats.size(); i++) {
            DiskStats[] diskStatsArr = healthInfo2.diskStats;
            android.hardware.health.V2_0.DiskStats diskStats =
                    (android.hardware.health.V2_0.DiskStats) healthInfo.legacy.diskStats.get(i);
            DiskStats diskStats2 = new DiskStats();
            diskStats2.reads = diskStats.reads;
            diskStats2.readMerges = diskStats.readMerges;
            diskStats2.readSectors = diskStats.readSectors;
            diskStats2.readTicks = diskStats.readTicks;
            diskStats2.writes = diskStats.writes;
            diskStats2.writeMerges = diskStats.writeMerges;
            diskStats2.writeSectors = diskStats.writeSectors;
            diskStats2.writeTicks = diskStats.writeTicks;
            diskStats2.ioInFlight = diskStats.ioInFlight;
            diskStats2.ioTicks = diskStats.ioTicks;
            diskStats2.ioInQueue = diskStats.ioInQueue;
            diskStatsArr[i] = diskStats2;
        }
        healthInfo2.storageInfos = new StorageInfo[healthInfo.legacy.storageInfos.size()];
        for (int i2 = 0; i2 < healthInfo.legacy.storageInfos.size(); i2++) {
            StorageInfo[] storageInfoArr = healthInfo2.storageInfos;
            android.hardware.health.V2_0.StorageInfo storageInfo =
                    (android.hardware.health.V2_0.StorageInfo)
                            healthInfo.legacy.storageInfos.get(i2);
            StorageInfo storageInfo2 = new StorageInfo();
            storageInfo2.eol = storageInfo.eol;
            storageInfo2.lifetimeA = storageInfo.lifetimeA;
            storageInfo2.lifetimeB = storageInfo.lifetimeB;
            storageInfo2.version = storageInfo.version;
            storageInfoArr[i2] = storageInfo2;
        }
        healthInfo2.batteryCapacityLevel = healthInfo.batteryCapacityLevel;
        healthInfo2.batteryChargeTimeToFullNowSeconds =
                healthInfo.batteryChargeTimeToFullNowSeconds;
        healthInfo2.batteryFullChargeDesignCapacityUah =
                healthInfo.batteryFullChargeDesignCapacityUah;
        sehHealthInfo2.aospHealthInfo = healthInfo2;
        sehHealthInfo2.batteryCurrentNow = sehHealthInfo.batteryCurrentNow;
        sehHealthInfo2.batteryOnline = sehHealthInfo.batteryOnline;
        sehHealthInfo2.batteryChargeType = sehHealthInfo.batteryChargeType;
        sehHealthInfo2.batteryPowerSharingOnline = sehHealthInfo.batteryPowerSharingOnline;
        sehHealthInfo2.chargerPogoOnline = sehHealthInfo.chargerPogoOnline;
        sehHealthInfo2.batteryHighVoltageCharger = sehHealthInfo.batteryHighVoltageCharger;
        sehHealthInfo2.batteryEvent = sehHealthInfo.batteryEvent;
        sehHealthInfo2.batteryCurrentEvent = sehHealthInfo.batteryCurrentEvent;
        sehHealthInfo2.chargerOtgOnline = sehHealthInfo.chargerOtgOnline;
        sehHealthInfo2.wirelessPowerSharingTxEvent = sehHealthInfo.wirelessPowerSharingTxEvent;
        return sehHealthInfo2;
    }
}
