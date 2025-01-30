package vendor.samsung.hardware.health;

/* loaded from: classes2.dex */
public abstract class Translate {
    public static SehHealthInfo h2saTranslate(vendor.samsung.hardware.health.V2_0.SehHealthInfo sehHealthInfo) {
        SehHealthInfo sehHealthInfo2 = new SehHealthInfo();
        sehHealthInfo2.aospHealthInfo = android.hardware.health.Translate.h2aTranslate(sehHealthInfo.legacy);
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
