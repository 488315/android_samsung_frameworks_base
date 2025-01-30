package vendor.samsung.hardware.health.V2_0;

import android.hardware.health.V2_1.HealthInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.Objects;

/* loaded from: classes2.dex */
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
        return HidlSupport.deepEquals(this.legacy, sehHealthInfo.legacy) && this.batteryCurrentNow == sehHealthInfo.batteryCurrentNow && this.batteryOnline == sehHealthInfo.batteryOnline && this.batteryChargeType == sehHealthInfo.batteryChargeType && this.batteryPowerSharingOnline == sehHealthInfo.batteryPowerSharingOnline && this.chargerPogoOnline == sehHealthInfo.chargerPogoOnline && this.batteryHighVoltageCharger == sehHealthInfo.batteryHighVoltageCharger && this.batteryEvent == sehHealthInfo.batteryEvent && this.batteryCurrentEvent == sehHealthInfo.batteryCurrentEvent && this.chargerOtgOnline == sehHealthInfo.chargerOtgOnline && this.wirelessPowerSharingTxEvent == sehHealthInfo.wirelessPowerSharingTxEvent;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.legacy)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryCurrentNow))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryOnline))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryChargeType))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.batteryPowerSharingOnline))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.chargerPogoOnline))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryHighVoltageCharger))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryEvent))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.batteryCurrentEvent))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.chargerOtgOnline))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.wirelessPowerSharingTxEvent))));
    }

    public final String toString() {
        return "{.legacy = " + this.legacy + ", .batteryCurrentNow = " + this.batteryCurrentNow + ", .batteryOnline = " + this.batteryOnline + ", .batteryChargeType = " + this.batteryChargeType + ", .batteryPowerSharingOnline = " + this.batteryPowerSharingOnline + ", .chargerPogoOnline = " + this.chargerPogoOnline + ", .batteryHighVoltageCharger = " + this.batteryHighVoltageCharger + ", .batteryEvent = " + this.batteryEvent + ", .batteryCurrentEvent = " + this.batteryCurrentEvent + ", .chargerOtgOnline = " + this.chargerOtgOnline + ", .wirelessPowerSharingTxEvent = " + this.wirelessPowerSharingTxEvent + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(176L), 0L);
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.legacy.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.batteryCurrentNow = hwBlob.getInt32(136 + j);
        this.batteryOnline = hwBlob.getInt32(140 + j);
        this.batteryChargeType = hwBlob.getInt32(144 + j);
        this.batteryPowerSharingOnline = hwBlob.getBool(148 + j);
        this.chargerPogoOnline = hwBlob.getBool(149 + j);
        this.batteryHighVoltageCharger = hwBlob.getInt32(152 + j);
        this.batteryEvent = hwBlob.getInt32(156 + j);
        this.batteryCurrentEvent = hwBlob.getInt32(160 + j);
        this.chargerOtgOnline = hwBlob.getBool(164 + j);
        this.wirelessPowerSharingTxEvent = hwBlob.getInt32(j + 168);
    }
}
