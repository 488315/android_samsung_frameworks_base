package vendor.samsung.hardware.health.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.health.V2_1.HealthInfo;
import android.os.HidlSupport;

import java.util.Objects;

public final class SehHealthInfo {
    public HealthInfo legacy = new HealthInfo();
    public int batteryCurrentNow = 0;
    public int batteryOnline = 0;
    public int batteryChargeType = 0;
    public boolean batteryPowerSharingOnline = false;
    public boolean chargerPogoOnline = false;
    public int batteryHighVoltageCharger = 0;
    public int batteryEvent = 0;
    public int batteryCurrentEvent = 0;
    public boolean chargerOtgOnline = false;
    public int wirelessPowerSharingTxEvent = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehHealthInfo.class) {
            return false;
        }
        SehHealthInfo sehHealthInfo = (SehHealthInfo) obj;
        return HidlSupport.deepEquals(this.legacy, sehHealthInfo.legacy)
                && this.batteryCurrentNow == sehHealthInfo.batteryCurrentNow
                && this.batteryOnline == sehHealthInfo.batteryOnline
                && this.batteryChargeType == sehHealthInfo.batteryChargeType
                && this.batteryPowerSharingOnline == sehHealthInfo.batteryPowerSharingOnline
                && this.chargerPogoOnline == sehHealthInfo.chargerPogoOnline
                && this.batteryHighVoltageCharger == sehHealthInfo.batteryHighVoltageCharger
                && this.batteryEvent == sehHealthInfo.batteryEvent
                && this.batteryCurrentEvent == sehHealthInfo.batteryCurrentEvent
                && this.chargerOtgOnline == sehHealthInfo.chargerOtgOnline
                && this.wirelessPowerSharingTxEvent == sehHealthInfo.wirelessPowerSharingTxEvent;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.legacy)),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCurrentNow),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryOnline),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryChargeType),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.batteryPowerSharingOnline),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.chargerPogoOnline),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryHighVoltageCharger),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryEvent),
                AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCurrentEvent),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.chargerOtgOnline),
                AudioConfig$$ExternalSyntheticOutline0.m(this.wirelessPowerSharingTxEvent));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.legacy = ");
        sb.append(this.legacy);
        sb.append(", .batteryCurrentNow = ");
        sb.append(this.batteryCurrentNow);
        sb.append(", .batteryOnline = ");
        sb.append(this.batteryOnline);
        sb.append(", .batteryChargeType = ");
        sb.append(this.batteryChargeType);
        sb.append(", .batteryPowerSharingOnline = ");
        sb.append(this.batteryPowerSharingOnline);
        sb.append(", .chargerPogoOnline = ");
        sb.append(this.chargerPogoOnline);
        sb.append(", .batteryHighVoltageCharger = ");
        sb.append(this.batteryHighVoltageCharger);
        sb.append(", .batteryEvent = ");
        sb.append(this.batteryEvent);
        sb.append(", .batteryCurrentEvent = ");
        sb.append(this.batteryCurrentEvent);
        sb.append(", .chargerOtgOnline = ");
        sb.append(this.chargerOtgOnline);
        sb.append(", .wirelessPowerSharingTxEvent = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                this.wirelessPowerSharingTxEvent, sb, "}");
    }
}
