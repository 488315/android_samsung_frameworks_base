package android.hardware.health.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthInfo {
    public int batteryCurrentAverage;
    public final ArrayList diskStats;
    public final android.hardware.health.V1_0.HealthInfo legacy;
    public final ArrayList storageInfos;

    public HealthInfo() {
        android.hardware.health.V1_0.HealthInfo healthInfo = new android.hardware.health.V1_0.HealthInfo();
        healthInfo.chargerAcOnline = false;
        healthInfo.chargerUsbOnline = false;
        healthInfo.chargerWirelessOnline = false;
        healthInfo.maxChargingCurrent = 0;
        healthInfo.maxChargingVoltage = 0;
        healthInfo.batteryStatus = 0;
        healthInfo.batteryHealth = 0;
        healthInfo.batteryPresent = false;
        healthInfo.batteryLevel = 0;
        healthInfo.batteryVoltage = 0;
        healthInfo.batteryTemperature = 0;
        healthInfo.batteryCurrent = 0;
        healthInfo.batteryCycleCount = 0;
        healthInfo.batteryFullCharge = 0;
        healthInfo.batteryChargeCounter = 0;
        healthInfo.batteryTechnology = new String();
        this.legacy = healthInfo;
        this.batteryCurrentAverage = 0;
        this.diskStats = new ArrayList();
        this.storageInfos = new ArrayList();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HealthInfo.class) {
            return false;
        }
        HealthInfo healthInfo = (HealthInfo) obj;
        return HidlSupport.deepEquals(this.legacy, healthInfo.legacy) && this.batteryCurrentAverage == healthInfo.batteryCurrentAverage && HidlSupport.deepEquals(this.diskStats, healthInfo.diskStats) && HidlSupport.deepEquals(this.storageInfos, healthInfo.storageInfos);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.legacy)), AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCurrentAverage), Integer.valueOf(HidlSupport.deepHashCode(this.diskStats)), Integer.valueOf(HidlSupport.deepHashCode(this.storageInfos)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob) {
        HwParcel hwParcel2 = hwParcel;
        android.hardware.health.V1_0.HealthInfo healthInfo = this.legacy;
        healthInfo.getClass();
        long j = 0;
        healthInfo.chargerAcOnline = hwBlob.getBool(0L);
        healthInfo.chargerUsbOnline = hwBlob.getBool(1L);
        healthInfo.chargerWirelessOnline = hwBlob.getBool(2L);
        healthInfo.maxChargingCurrent = hwBlob.getInt32(4L);
        healthInfo.maxChargingVoltage = hwBlob.getInt32(8L);
        healthInfo.batteryStatus = hwBlob.getInt32(12L);
        healthInfo.batteryHealth = hwBlob.getInt32(16L);
        healthInfo.batteryPresent = hwBlob.getBool(20L);
        healthInfo.batteryLevel = hwBlob.getInt32(24L);
        healthInfo.batteryVoltage = hwBlob.getInt32(28L);
        healthInfo.batteryTemperature = hwBlob.getInt32(32L);
        healthInfo.batteryCurrent = hwBlob.getInt32(36L);
        healthInfo.batteryCycleCount = hwBlob.getInt32(40L);
        healthInfo.batteryFullCharge = hwBlob.getInt32(44L);
        healthInfo.batteryChargeCounter = hwBlob.getInt32(48L);
        healthInfo.batteryTechnology = hwBlob.getString(56L);
        hwParcel.readEmbeddedBuffer(r8.getBytes().length + 1, hwBlob.handle(), 56L, false);
        this.batteryCurrentAverage = hwBlob.getInt32(72L);
        int int32 = hwBlob.getInt32(88L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, hwBlob.handle(), 80L, true);
        this.diskStats.clear();
        int i = 0;
        while (i < int32) {
            DiskStats diskStats = new DiskStats();
            diskStats.reads = j;
            diskStats.readMerges = j;
            diskStats.readSectors = j;
            diskStats.readTicks = j;
            diskStats.writes = j;
            diskStats.writeMerges = j;
            diskStats.writeSectors = j;
            diskStats.writeTicks = j;
            diskStats.ioInFlight = j;
            diskStats.ioTicks = j;
            diskStats.ioInQueue = j;
            StorageAttribute storageAttribute = new StorageAttribute();
            diskStats.attr = storageAttribute;
            long j2 = i * 112;
            diskStats.reads = readEmbeddedBuffer.getInt64(j2);
            diskStats.readMerges = readEmbeddedBuffer.getInt64(j2 + 8);
            diskStats.readSectors = readEmbeddedBuffer.getInt64(j2 + 16);
            diskStats.readTicks = readEmbeddedBuffer.getInt64(j2 + 24);
            diskStats.writes = readEmbeddedBuffer.getInt64(j2 + 32);
            diskStats.writeMerges = readEmbeddedBuffer.getInt64(j2 + 40);
            diskStats.writeSectors = readEmbeddedBuffer.getInt64(j2 + 48);
            diskStats.writeTicks = readEmbeddedBuffer.getInt64(56 + j2);
            diskStats.ioInFlight = readEmbeddedBuffer.getInt64(64 + j2);
            diskStats.ioTicks = readEmbeddedBuffer.getInt64(j2 + 72);
            diskStats.ioInQueue = readEmbeddedBuffer.getInt64(80 + j2);
            hwParcel2 = hwParcel;
            storageAttribute.readEmbeddedFromParcel(hwParcel2, readEmbeddedBuffer, j2 + 88);
            this.diskStats.add(diskStats);
            i++;
            j = 0;
        }
        int int322 = hwBlob.getInt32(104L);
        short s = 0;
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, hwBlob.handle(), 96L, true);
        this.storageInfos.clear();
        int i2 = 0;
        while (i2 < int322) {
            StorageInfo storageInfo = new StorageInfo();
            StorageAttribute storageAttribute2 = new StorageAttribute();
            storageInfo.attr = storageAttribute2;
            storageInfo.eol = s;
            storageInfo.lifetimeA = s;
            storageInfo.lifetimeB = s;
            storageInfo.version = new String();
            long j3 = i2 * 48;
            storageAttribute2.readEmbeddedFromParcel(hwParcel2, readEmbeddedBuffer2, j3);
            storageInfo.eol = readEmbeddedBuffer2.getInt16(j3 + 24);
            storageInfo.lifetimeA = readEmbeddedBuffer2.getInt16(26 + j3);
            storageInfo.lifetimeB = readEmbeddedBuffer2.getInt16(j3 + 28);
            long j4 = j3 + 32;
            storageInfo.version = readEmbeddedBuffer2.getString(j4);
            hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, readEmbeddedBuffer2.handle(), j4, false);
            this.storageInfos.add(storageInfo);
            i2++;
            s = 0;
        }
    }

    public final String toString() {
        return "{.legacy = " + this.legacy + ", .batteryCurrentAverage = " + this.batteryCurrentAverage + ", .diskStats = " + this.diskStats + ", .storageInfos = " + this.storageInfos + "}";
    }
}
