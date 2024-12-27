package android.hardware.health.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthInfo {
    public int batteryChargeCounter;
    public int batteryCurrent;
    public int batteryCycleCount;
    public int batteryFullCharge;
    public int batteryHealth;
    public int batteryLevel;
    public boolean batteryPresent;
    public int batteryStatus;
    public String batteryTechnology;
    public int batteryTemperature;
    public int batteryVoltage;
    public boolean chargerAcOnline;
    public boolean chargerUsbOnline;
    public boolean chargerWirelessOnline;
    public int maxChargingCurrent;
    public int maxChargingVoltage;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HealthInfo.class) {
            return false;
        }
        HealthInfo healthInfo = (HealthInfo) obj;
        return this.chargerAcOnline == healthInfo.chargerAcOnline
                && this.chargerUsbOnline == healthInfo.chargerUsbOnline
                && this.chargerWirelessOnline == healthInfo.chargerWirelessOnline
                && this.maxChargingCurrent == healthInfo.maxChargingCurrent
                && this.maxChargingVoltage == healthInfo.maxChargingVoltage
                && this.batteryStatus == healthInfo.batteryStatus
                && this.batteryHealth == healthInfo.batteryHealth
                && this.batteryPresent == healthInfo.batteryPresent
                && this.batteryLevel == healthInfo.batteryLevel
                && this.batteryVoltage == healthInfo.batteryVoltage
                && this.batteryTemperature == healthInfo.batteryTemperature
                && this.batteryCurrent == healthInfo.batteryCurrent
                && this.batteryCycleCount == healthInfo.batteryCycleCount
                && this.batteryFullCharge == healthInfo.batteryFullCharge
                && this.batteryChargeCounter == healthInfo.batteryChargeCounter
                && HidlSupport.deepEquals(this.batteryTechnology, healthInfo.batteryTechnology);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.chargerAcOnline),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.chargerUsbOnline),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.chargerWirelessOnline),
                AudioConfig$$ExternalSyntheticOutline0.m(this.maxChargingCurrent),
                AudioConfig$$ExternalSyntheticOutline0.m(this.maxChargingVoltage),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryStatus),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryHealth),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.batteryPresent),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryLevel),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryVoltage),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryTemperature),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCurrent),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCycleCount),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryFullCharge),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryChargeCounter),
                Integer.valueOf(HidlSupport.deepHashCode(this.batteryTechnology)));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.chargerAcOnline = ");
        sb.append(this.chargerAcOnline);
        sb.append(", .chargerUsbOnline = ");
        sb.append(this.chargerUsbOnline);
        sb.append(", .chargerWirelessOnline = ");
        sb.append(this.chargerWirelessOnline);
        sb.append(", .maxChargingCurrent = ");
        sb.append(this.maxChargingCurrent);
        sb.append(", .maxChargingVoltage = ");
        sb.append(this.maxChargingVoltage);
        sb.append(", .batteryStatus = ");
        int i = this.batteryStatus;
        sb.append(
                i == 1
                        ? "UNKNOWN"
                        : i == 2
                                ? "CHARGING"
                                : i == 3
                                        ? "DISCHARGING"
                                        : i == 4
                                                ? "NOT_CHARGING"
                                                : i == 5
                                                        ? "FULL"
                                                        : AudioChannelMask$$ExternalSyntheticOutline0
                                                                .m(new StringBuilder("0x"), i));
        sb.append(", .batteryHealth = ");
        int i2 = this.batteryHealth;
        sb.append(
                i2 != 1
                        ? i2 == 2
                                ? "GOOD"
                                : i2 == 3
                                        ? "OVERHEAT"
                                        : i2 == 4
                                                ? "DEAD"
                                                : i2 == 5
                                                        ? "OVER_VOLTAGE"
                                                        : i2 == 6
                                                                ? "UNSPECIFIED_FAILURE"
                                                                : i2 == 7
                                                                        ? "COLD"
                                                                        : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        new StringBuilder(
                                                                                                "0x"),
                                                                                        i2)
                        : "UNKNOWN");
        sb.append(", .batteryPresent = ");
        sb.append(this.batteryPresent);
        sb.append(", .batteryLevel = ");
        sb.append(this.batteryLevel);
        sb.append(", .batteryVoltage = ");
        sb.append(this.batteryVoltage);
        sb.append(", .batteryTemperature = ");
        sb.append(this.batteryTemperature);
        sb.append(", .batteryCurrent = ");
        sb.append(this.batteryCurrent);
        sb.append(", .batteryCycleCount = ");
        sb.append(this.batteryCycleCount);
        sb.append(", .batteryFullCharge = ");
        sb.append(this.batteryFullCharge);
        sb.append(", .batteryChargeCounter = ");
        sb.append(this.batteryChargeCounter);
        sb.append(", .batteryTechnology = ");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.batteryTechnology, "}");
    }
}
